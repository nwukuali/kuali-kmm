package org.kuali.ext.mm.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.PickVerifyDocument;
import org.kuali.ext.mm.document.dataaccess.OrderStatusDao;
import org.kuali.ext.mm.document.dataaccess.PickVerifyBillingDao;
import org.kuali.ext.mm.document.service.BusinessObjectLockingService;
import org.kuali.ext.mm.gl.GlConstants;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformation;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformationDetail;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.ext.mm.service.*;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.*;


/**
 * @author schneppd
 */
@Transactional
public class PickVerifyServiceImpl implements PickVerifyService {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(PickVerifyServiceImpl.class);

    private PickListService pickListService;
    private BackOrderService backOrderService;
    private RentalService rentalService;
    private PackListPdfService packListPdfService;
    private PickVerifyBillingDao pickVerifyBillingDao;
    private OrderStatusDao orderStatusDao;


    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#processNewVerifyDocument(org.kuali.ext.mm.document.PickVerifyDocument)
     */
    public void processNewVerifyDocument(PickVerifyDocument document) {
        PickTicket ticket = document.getPickTicket();
        // only run this process if the pick ticket is of status PRTD
        if (MMConstants.PickStatusCode.PICK_STATUS_PRTD.equals(ticket.getPickStatusCodeCd())) {
            List<PickListLine> pickedLines = processBackOrderLines(ticket);
            if (!StringUtils.equals(ticket.getPickStatusCodeCd(),
                    MMConstants.PickStatusCode.PICK_STATUS_BACK))
                processPickedLines(pickedLines, ticket, document.getDocumentNumber());
            SpringContext.getBean(BusinessObjectService.class).save(ticket);
        }
    }

    /**
     * Updates stock balance, stock history, and pick status for picked items. Also issues Rentals.
     * 
     * @param pickedLines - list of PickListLines that were completely or partially picked
     * @param ticket - The ticket from which the picked lines come
     * @param documentNumber - Pick Verify Document number posting charge
     */
    private void processPickedLines(List<PickListLine> pickedLines, PickTicket ticket,
            String documentNumber) {
        boolean partialBackOrderLine = false;
        boolean allLinesCanceled = true;
        StockService stockService = MMServiceLocator.getStockService();
        BusinessObjectLockingService lockingService = SpringContext
                .getBean(BusinessObjectLockingService.class);
        for (PickListLine line : pickedLines) {
            if (line.getPickStockQty() > 0) {
                lockingService.createAndSaveLock(documentNumber, line.getStock(),
                        MMConstants.Stock.STOCK_ID);
                StockBalance stockBalance = stockService.retrieveStockBalance(line.getBinId());
                StockBalance stockBalanceAfter = stockService.adjustStockQuantityOnHand(
                        stockBalance, -line.getPickStockQty());
                KRADServiceLocator.getBusinessObjectService().save(stockBalanceAfter);
                lockingService.deleteLocks(documentNumber);
                stockService
                        .postSaleToStockHistory(stockBalance, stockBalanceAfter, documentNumber);
                if(MMConstants.OrderType.TRUE_BUYOUT.equals(line.getOrderDetail().getOrderDocument().getOrderTypeCode())) {
                    KRADServiceLocator.getBusinessObjectService().delete(stockBalanceAfter);
                }
            }

            if (line.getBackOrderQty() > 0) {
                line.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_PBCK);
                partialBackOrderLine = true;
                allLinesCanceled = false;
            }
            else if (line.getPickStockQty() < 1) {
                line.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_CNCL);
            }
            else {
                line.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_PCKD);
                allLinesCanceled = false;
            }
            if (line.getRentals().size() > 0) {
                for (Rental rental : line.getRentals())
                    rentalService.issueRentalItem(rental, line);
            }
        }
        // If ticket is already partially back-ordered, no change needs to be made
        if (!MMConstants.PickStatusCode.PICK_STATUS_PBCK.equals(ticket.getPickStatusCodeCd())) {
            if (partialBackOrderLine)
                ticket.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_PBCK);
            else if (allLinesCanceled)
                ticket.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_CNCL);
            else
                ticket.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_PCKD);
        }
    }


    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#processInternalBilling(org.kuali.ext.mm.businessobject.Warehouse,
     *      java.util.List, java.util.List, org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformation)
     */
    public DocumentHeader processInternalBilling(Warehouse warehouse,
            List<FinancialInternalBillingItem> ibItems,
            List<FinancialAccountingLine> expenseAccountLines,
            FinancialCapitalAssetInformation assetInformation) {
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (factory.isSystemAvailable()) {
            KualiDecimal totalChargeAmt = KualiDecimal.ZERO;
            for (FinancialAccountingLine account : expenseAccountLines) {
                totalChargeAmt = totalChargeAmt.add(account.getAmount());
            }
            if (totalChargeAmt.isZero())
                return null;
            ArrayList<FinancialAccountingLine> incomeAcctLines = new ArrayList<FinancialAccountingLine>();
            FinancialAccountingLine finAcctLine = createFinancialAccountingLine(warehouse
                    .getIncomeAccount(), totalChargeAmt, "");
            incomeAcctLines.add(finAcctLine);
            return factory.getFinancialInternalBillingService().submitInternalBillingDocument(
                    warehouse.getBillingProfile(), ibItems, incomeAcctLines, expenseAccountLines,
                    assetInformation);
        }
        return null;
    }


    /**
     * Creates a financial accounting line using Warehouse Accouting information
     * 
     * @param mmAcctLine Warehouse Account
     * @param chargeAmt Charge Amount
     * @return
     */
    protected FinancialAccountingLine createFinancialAccountingLine(WarehouseAccounts mmAcctLine,
            KualiDecimal chargeAmt, String documentNumber) {
        FinancialAccountingLine finAcctLine = new FinancialAccountingLine();
        finAcctLine.setAccountNumber(mmAcctLine.getAccountNbr());
        finAcctLine.setAmount(chargeAmt);
        finAcctLine.setBalanceTypeCode("AC");
        finAcctLine.setChartOfAccountsCode(mmAcctLine.getFinCoaCd());
        finAcctLine.setFinancialDocumentLineDescription("Sale Generated");
        finAcctLine.setFinancialDocumentLineTypeCode(MMConstants.FIN_ACCT_LINE_TYP_FROM);
        finAcctLine.setFinancialObjectCode(mmAcctLine.getFinObjectCd());
        finAcctLine.setFinancialSubObjectCode(mmAcctLine.getFinSubObjCd());
        finAcctLine.setObjectBudgetOverride(false);
        finAcctLine.setObjectBudgetOverrideNeeded(false);
        finAcctLine.setOrganizationReferenceId("");
        finAcctLine.setOverrideCode("");
        finAcctLine.setPostingYear(SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .getFinancialUniversityDateService().getCurrentFiscalYear());
        finAcctLine.setProjectCode(mmAcctLine.getProjectCd());
        finAcctLine.setReferenceNumber("");
        finAcctLine.setReferenceOriginCode(GlConstants.getFinancialSystemOriginCode());
        finAcctLine.setReferenceTypeCode("");
        finAcctLine.setSalesTaxRequired(false);
        finAcctLine.setSubAccountNumber(mmAcctLine.getSubAcctNbr());
        finAcctLine.setDocumentNumber(documentNumber);
        finAcctLine.setFinancialDocumentTypeCode(MMConstants.OrderDocument.STORES_ORDER_DOCUMENT);
        return finAcctLine;
    }

    /**
     * Creates BackOrders from any lines with a BackOrder Quantity greater than zero.
     * 
     * @param pickTicket
     * @return A list of PickListLines that had items picked (partially or completely)
     */
    private List<PickListLine> processBackOrderLines(PickTicket pickTicket) {
        List<PickListLine> pickedLines = new ArrayList<PickListLine>();

        for (PickListLine line : pickTicket.getPickListLines()) {
            if (line.getBackOrderQty() > 0) {
                if (line.getBackOrderQty().equals(line.getStockQty()))
                    line.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_BACK);
                else
                    pickedLines.add(line);
                BackOrder backOrder = getBackOrderService().createBackOrder(line);
                getBackOrderService().save(backOrder);
                // line.setBackOrder(backOrder);
            }
            else
                pickedLines.add(line);
        }
        if (pickedLines.isEmpty())
            pickTicket.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_BACK);
        else if (pickedLines.size() != pickTicket.getPickListLines().size())
            pickTicket.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_PBCK);

        return pickedLines;
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#generatePackLists(org.kuali.ext.mm.businessobject.PickTicket)
     */
    public ByteArrayOutputStream generatePackLists(PickTicket ticket) {
        Map<Long, List<PickListLine>> linesByOrder = pickListService.getLinesByOrder(ticket
                .getPickListLines());

        Object document = packListPdfService.createDocument();
        for (Long orderId : linesByOrder.keySet()) {
            String messages = buildPackingListMessages(linesByOrder.get(orderId));
            packListPdfService.addPackingList(linesByOrder.get(orderId), messages);
        }

        return packListPdfService.closeDocument(document);
    }

    /**
     * Determines if there are any messages too add to the messages box on the packing list pdf
     * 
     * @param lines - A list of PickListLines
     * @return Any messages generated by the data in the packing list
     */
    private String buildPackingListMessages(List<PickListLine> lines) {
        String messages = "";

        messages += "  " + getDefaultPackingListAnnouncement();

        return messages;
    }

    /**
     * Concats all active Packing List Announcements with the default code.
     * 
     * @return String value of the default packing list announcement(s) for the system.
     */
    private String getDefaultPackingListAnnouncement() {
        String announcement = "";
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.PackListAnnouncement.PACK_LIST_ANNOUNCEMENT_CD,
                MMConstants.PackListAnnouncement.DEFAULT_CODE);
        fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
        Collection results = KRADServiceLocator.getBusinessObjectService().findMatching(
                PackListAnnouncement.class, fieldValues);
        Iterator<PackListAnnouncement> itAnnouncement = results.iterator();
        while (itAnnouncement.hasNext())
            announcement += "  " + itAnnouncement.next().getPackListAnnouncementDesc();

        return announcement;
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#setPickListService(org.kuali.ext.mm.service.PickListService)
     */
    public void setPickListService(PickListService pickListService) {
        this.pickListService = pickListService;
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#getPickListService()
     */
    public PickListService getPickListService() {
        return pickListService;
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#setBackOrderService(org.kuali.ext.mm.service.BackOrderService)
     */
    public void setBackOrderService(BackOrderService backOrderService) {
        this.backOrderService = backOrderService;
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#getBackOrderService()
     */
    public BackOrderService getBackOrderService() {
        return backOrderService;
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#getRentalService()
     */
    public RentalService getRentalService() {
        return rentalService;
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#setRentalService(org.kuali.ext.mm.service.RentalService)
     */
    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#setPackListPdfService(org.kuali.ext.mm.service.PackListPdfService)
     */
    public void setPackListPdfService(PackListPdfService packListPdfService) {
        this.packListPdfService = packListPdfService;
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#getPackListPdfService()
     */
    public PackListPdfService getPackListPdfService() {
        return packListPdfService;
    }


    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#rentalsMatchPickQuantity(org.kuali.ext.mm.businessobject.PickListLine)
     */
    public boolean rentalsMatchPickQuantity(PickListLine line) {
        int count = 0;
        for (Rental rental : line.getRentals()) {
            if (rental.getRentalSerialNumber() != null
                    && rental.getRentalSerialNumber().length() > 0)
                count++;
        }
        return line.getPickStockQty() == count;
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#isQuantityValid(org.kuali.ext.mm.businessobject.PickListLine)
     */
    public boolean isQuantityValid(PickListLine line) {
        return line.getTotalPickStockQty() + line.getBackOrderQty() <= line.getStockQty();
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#hasPickedLessThanQuantityOnHand(org.kuali.ext.mm.businessobject.PickListLine)
     */
    public boolean hasPickedLessThanQuantityOnHand(PickListLine line) {
        StockService stockService = MMServiceLocator.getStockService();
        if (ObjectUtils.isNull(line.getBin().getZone()))
            line.getBin().refreshReferenceObject(MMConstants.Bin.ZONE);

        Integer stockBalance = stockService.getStockQuantityOnHand(line.getStockId(), line.getBin()
                .getZone().getWarehouseCd());

        if (stockBalance > line.getTotalPickStockQty())
            return true;

        return false;
    }

    public Integer getTotalPickedFromBin(PickTicket ticket, Bin bin) {
        Integer pickedQty = 0;
        for (PickListLine line : ticket.getPickListLines()) {
            if (bin.getBinId().equals(line.getBinId()))
                pickedQty += line.getPickStockQty();
            for (PickListLine addLine : line.getAdditionalLines()) {
                if (bin.getBinId().equals(addLine.getBinId()))
                    pickedQty += addLine.getPickStockQty();
            }
        }
        return pickedQty;
    }

    /**
     * Gets the pickVerifyBillingDao property
     * 
     * @return Returns the pickVerifyBillingDao
     */
    public PickVerifyBillingDao getPickVerifyBillingDao() {
        return this.pickVerifyBillingDao;
    }

    /**
     * Sets the pickVerifyBillingDao property value
     * 
     * @param pickVerifyBillingDao The pickVerifyBillingDao to set
     */
    public void setPickVerifyBillingDao(PickVerifyBillingDao pickVerifyBillingDao) {
        this.pickVerifyBillingDao = pickVerifyBillingDao;
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#processInternalBilling(java.lang.String, java.lang.String)
     */
    public void processInternalBilling(PickVerifyDocument pickVerifyDoc) {
        String pickTicketNumber = pickVerifyDoc.getPickTicketNumber();
        String warehouseCode = pickVerifyDoc.getPickTicket().getPickListDocument().getWarehouseCd();
        // Create IB document only when accounting information is available
        if (!this.pickVerifyBillingDao.hasAccountsForBilling(pickTicketNumber)) {
            return;
        }
        Map<String, List<FinancialInternalBillingItem>> pickVerifiedOrderLines = this.pickVerifyBillingDao
                .getPickVerifiedOrderLines(pickTicketNumber);
        Set<String> keys = pickVerifiedOrderLines.keySet();
        Map<String, List<FinancialAccountingLine>> pickVerifiedAccountingLines = this.pickVerifyBillingDao
                .getPickVerifiedAccountingLines(pickTicketNumber);
        Warehouse warehouse = SpringContext.getBean(BusinessObjectService.class)
                .findBySinglePrimaryKey(Warehouse.class, warehouseCode);

        for (String key : keys) {
            List<FinancialInternalBillingItem> lineItems = pickVerifiedOrderLines.get(key);
            List<FinancialAccountingLine> acctLines = pickVerifiedAccountingLines.get(key);
            if (warehouse != null && warehouse.isActive()) {
                FinancialCapitalAssetInformation capitalAssetInformation = null;
                String orderDetailId = null;
                if (key.contains("-") && (orderDetailId = key.split("-")[1]) != null) {
                    MMCapitalAssetInformation assetInfo = SpringContext.getBean(
                            BusinessObjectService.class).findBySinglePrimaryKey(
                            MMCapitalAssetInformation.class, orderDetailId);
                    if (assetInfo != null) {
                        capitalAssetInformation = new FinancialCapitalAssetInformation();
                        adapt(assetInfo, capitalAssetInformation);
                        List<MMCapitalAssetInformationDetail> assetInformationDetails = assetInfo
                                .getCapitalAssetInformationDetails();
                        if (assetInformationDetails != null) {
                            for (MMCapitalAssetInformationDetail source : assetInformationDetails) {
                                FinancialCapitalAssetInformationDetail target = new FinancialCapitalAssetInformationDetail();
                                adapt(source, target);
                                capitalAssetInformation.getCapitalAssetInformationDetails().add(
                                        target);
                            }
                        }
                    }
                }
                // Do not send the line items to internal billing to avoid price calculation incongruence caused by 4 to 2 decimal
                // scale conversion.
                DocumentHeader documentHeader = processInternalBilling(warehouse,
                        new ArrayList<FinancialInternalBillingItem>(), acctLines,
                        capitalAssetInformation);
                if (documentHeader != null) {
                    if (orderDetailId != null) {
                        // this.pickVerifyBillingDao.updateChargeDocumentNumber(pickTicketNumber,
                        // orderDetailId, documentHeader.getDocumentNumber(), 1);
                        updateChargeDocumentNumber(pickVerifyDoc.getPickTicket(), orderDetailId,
                                documentHeader.getDocumentNumber(), 1);
                    }
                    else {
                        // this.pickVerifyBillingDao.updateChargeDocumentNumber(pickTicketNumber,
                        // documentHeader.getDocumentNumber(), lineItems);
                        updateChargeDocumentNumber(pickVerifyDoc.getPickTicket(), documentHeader
                                .getDocumentNumber(), lineItems);
                    }
                }
            }
            else {
                // LOG error here
                LOG.warn("Warehouse " + lineItems.get(0).getWarehouseCode()
                        + " is not valid, so batch did not post charges to the depts");
            }
        }

    }

    public void processBillingGlpes(PickVerifyDocument pickVerifyDoc,
            HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups) {
        String pickTicketNumber = pickVerifyDoc.getPickTicketNumber();
        String warehouseCode = pickVerifyDoc.getPickTicket().getPickListDocument().getWarehouseCd();
        if (!this.pickVerifyBillingDao.hasAccountsForBilling(pickTicketNumber)) {
            return;
        }

        Warehouse warehouse = SpringContext.getBean(BusinessObjectService.class)
                .findBySinglePrimaryKey(Warehouse.class, warehouseCode);
        Map<String, List<FinancialAccountingLine>> pickVerifiedAccountingLines = this.pickVerifyBillingDao
                .getPickVerifiedAccountingLines(pickTicketNumber);
        Set<String> keys = pickVerifiedAccountingLines.keySet();
        GeneralLedgerBuilderService glpeService = SpringContext
                .getBean(GeneralLedgerBuilderService.class);
        for (String key : keys) {
            String orderDoc = key;
            if(key.contains("-")){
                orderDoc=key.split("-")[0];
            }
            List<FinancialAccountingLine> expenseAccountLines = pickVerifiedAccountingLines
                    .get(key);
            KualiDecimal totalChargeAmt = KualiDecimal.ZERO;
            for (FinancialAccountingLine account : expenseAccountLines) {
                totalChargeAmt = totalChargeAmt.add(account.getAmount());
            }
            if (totalChargeAmt.isZero()) {
                continue;
            }
            ArrayList<FinancialAccountingLine> incomeAcctLines = new ArrayList<FinancialAccountingLine>();
            FinancialAccountingLine finAcctLine = createFinancialAccountingLine(warehouse
                    .getIncomeAccount(), totalChargeAmt,orderDoc);
            incomeAcctLines.add(finAcctLine);
            glpeService.buildBillingGlpes(glGroups, warehouse, incomeAcctLines,
                    expenseAccountLines, "Stores charge");
        }

    }

    /**
     * @param assetInfo MMCapitalAssetInformation
     * @param capitalAssetInformation FinancialCapitalAssetInformation
     */
    protected void adapt(MMCapitalAssetInformation assetInfo,
            FinancialCapitalAssetInformation capitalAssetInformation) {
        capitalAssetInformation.setCapitalAssetDescription(assetInfo.getCapitalAssetDescription());
        capitalAssetInformation.setCapitalAssetManufacturerModelNumber(assetInfo
                .getCapitalAssetManufacturerModelNumber());
        capitalAssetInformation.setCapitalAssetManufacturerName(assetInfo
                .getCapitalAssetManufacturerName());
        capitalAssetInformation.setCapitalAssetNumber(assetInfo.getCapitalAssetNumber());
        capitalAssetInformation.setCapitalAssetQuantity(assetInfo.getCapitalAssetQuantity());
        capitalAssetInformation.setCapitalAssetTypeCode(assetInfo.getCapitalAssetTypeCode());
        capitalAssetInformation.setVendorDetailAssignedIdentifier(assetInfo
                .getVendorDetailAssignedIdentifier());
        capitalAssetInformation.setVendorHeaderGeneratedIdentifier(assetInfo
                .getVendorHeaderGeneratedIdentifier());
        capitalAssetInformation.setVendorName(assetInfo.getVendorName());
    }

    /**
     * @param source MMCapitalAssetInformationDetail
     * @param target FinancialCapitalAssetInformationDetail
     */
    protected void adapt(MMCapitalAssetInformationDetail source,
            FinancialCapitalAssetInformationDetail target) {
        target.setBuildingCode(source.getBuildingCode());
        target.setBuildingRoomNumber(source.getBuildingRoomNumber());
        target.setBuildingSubRoomNumber(source.getBuildingSubRoomNumber());
        target.setCampusCode(source.getCampusCode());
        target.setCapitalAssetSerialNumber(source.getCapitalAssetSerialNumber());
        target.setCapitalAssetTagNumber(source.getCapitalAssetTagNumber());
    }

    /**
     * Updates order status after each pick verify is done
     * 
     * @param pickTicket Current pick ticket number
     */
    public void updateOrderStatus(PickTicket pickTicket) {

        // List<Integer> completeOrdrDetails = new ArrayList<Integer>();
        HashSet<String> orderDocs = new HashSet<String>();
        // List<OrderDetailPickStatusDTO> orderDetailsPickStatus = this.orderStatusDao
        // .getOrderDetailsPickStatus(pickTicketNumber);
        // for (OrderDetailPickStatusDTO status : orderDetailsPickStatus) {
        // if (status.getOrderItemQty().equals(status.getPickedQty())) {
        // completeOrdrDetails.add(status.getOrderDetailId());
        // orderDocs.add(status.getOrderDocumentNbr());
        // }
        // }
        PickListService pickService = SpringContext.getBean(PickListService.class);
        OrderService orderService = SpringContext.getBean(OrderService.class);
        for (OrderDetail detail : pickService.getOrderDetailsFromPickTicket(pickTicket)) {
            if (orderService.isOrderDetailComplete(detail.getOrderDetailId())) {
                detail.setOrderStatusCd(MMConstants.OrderStatus.ORDER_LINE_COMPLETE);
                KRADServiceLocator.getBusinessObjectService().save(detail);
                orderDocs.add(detail.getOrderDocumentNbr());
            }
        }
        // this.orderStatusDao.updateOrderDetailsComplete(completeOrdrDetails);
        for (String orderDocNumber : orderDocs) {
            if (this.orderStatusDao.isOrderComplete(orderDocNumber)) {
                this.orderStatusDao.updateOrderComplete(orderDocNumber);
            }
        }

    }

    /**
     * Gets the orderStatusDao property
     * 
     * @return Returns the orderStatusDao
     */
    public OrderStatusDao getOrderStatusDao() {
        return this.orderStatusDao;
    }

    /**
     * Sets the orderStatusDao property value
     * 
     * @param orderStatusDao The orderStatusDao to set
     */
    public void setOrderStatusDao(OrderStatusDao orderStatusDao) {
        this.orderStatusDao = orderStatusDao;
    }

    /**
     * Updates the picked lines with the chargeDocumentNumber and chargeDocumentItemNumber
     * 
     * @param pickTicket
     * @param orderDetailId
     * @param ibDocumentNumber
     * @param itmNumber
     * @return int value for number of lines effected
     */
    private int updateChargeDocumentNumber(PickTicket pickTicket, final String orderDetailId,
            final String ibDocumentNumber, final Integer itmNumber) {

        int count = 0;
        for (PickListLine line : pickTicket.getPickListLines()) {
            line.refreshReferenceObject(MMConstants.PickListLine.PICK_STATUS_CODE);
            if (StringUtils.equals(String.valueOf(line.getOrderDetailId()), orderDetailId)
                    && (MMConstants.PickStatusCode.PICK_STATUS_PCKD.equals(line.getPickStatusCode()
                            .getPickStatusCode()) || MMConstants.PickStatusCode.PICK_STATUS_PBCK
                            .equals(line.getPickStatusCode().getPickStatusCode()))
                    && StringUtils.equals(line.getPickTicket().getPickTicketNumber(), pickTicket
                            .getPickTicketNumber()) && line.getPickStockQty() > 0) {
                line.setChargeDocumentNumber(ibDocumentNumber);
                line.setChargeDocumentItemNumber(itmNumber);
                count++;
            }
        }
        return count;
    }

    /**
     * Updates the picked lines with the chargeDocumentNumber and chargeDocumentItemNumber
     * 
     * @param pickTicket
     * @param ibDocumentNumber
     * @param lineItems
     * @return int value for number of lines effected
     */
    private int updateChargeDocumentNumber(PickTicket pickTicket, final String ibDocumentNumber,
            final List<FinancialInternalBillingItem> lineItems) {
        int count = 0;
        for (FinancialInternalBillingItem financialInternalBillingItem : lineItems) {
            count += updateChargeDocumentNumber(pickTicket, financialInternalBillingItem
                    .getMmOrderDetailId(), ibDocumentNumber, financialInternalBillingItem
                    .getItemSequenceId());
        }
        return count;

    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#computePickedTotal(java.util.List)
     */
    public KualiDecimal computePickedTotal(List<PickListLine> pickListLines) {
        MMDecimal shipTotal = new MMDecimal(0);
        for (PickListLine line : pickListLines) {
            MMDecimal lineTotal = line.getOrderDetail().getOrderItemPriceAmt().add(
                    line.getOrderDetail().getOrderItemTaxAmt()).multiply(
                    new MMDecimal(line.getPickStockQty()));
            shipTotal = shipTotal.add(lineTotal);
        }
        return shipTotal.kualiDecimalValue();
    }

    /**
     * @see org.kuali.ext.mm.service.PickVerifyService#computePickedAmountSaved(java.util.List)
     */
    public KualiDecimal computePickedAmountSaved(List<PickListLine> pickListLines) {
        MMDecimal savedTotal = new MMDecimal(0);
        for (PickListLine line : pickListLines) {
            if (line.getOrderDetail().getOrderItemMarkupAmt().isNegative()) {
                MMDecimal lineSavedTotal = line.getOrderDetail().getOrderItemMarkupAmt().abs()
                        .multiply(new MMDecimal(line.getPickStockQty()));
                savedTotal = savedTotal.add(lineSavedTotal);
            }
        }
        return savedTotal.kualiDecimalValue();
    }
}
