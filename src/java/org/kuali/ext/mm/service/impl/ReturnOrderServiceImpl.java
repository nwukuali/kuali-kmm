package org.kuali.ext.mm.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CreditMemoExpected;
import org.kuali.ext.mm.businessobject.MMCapitalAssetInformation;
import org.kuali.ext.mm.businessobject.MMCapitalAssetInformationDetail;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.businessobject.StagingRental;
import org.kuali.ext.mm.businessobject.Stock;
import org.kuali.ext.mm.businessobject.StoresPersistableBusinessObject;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.CheckinOrderDAO;
import org.kuali.ext.mm.dataaccess.ReturnOrderDAO;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.document.dataaccess.ReturnOrderBillingDao;
import org.kuali.ext.mm.gl.GlConstants;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformation;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformationDetail;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.ext.mm.service.IReturnCommand;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.ProfileService;
import org.kuali.ext.mm.service.RTVPDFService;
import org.kuali.ext.mm.service.ReturnActionService;
import org.kuali.ext.mm.service.ReturnOrderService;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.bo.DocumentHeader;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;


/**
 * This service implements methods needed for returning orders
 * 
 * @author rponraj
 */
public class ReturnOrderServiceImpl implements ReturnOrderService {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(ReturnOrderServiceImpl.class);

    public final static String SEPARATOR = "/";

    private ReturnOrderDAO returnOrderDAO = null;

    private CheckinOrderDAO checkinOrderDAO = null;

    private ReturnOrderBillingDao returnOrderBillingDao = null;

    private StockService stockService = null;

    public StockService getStockService() {
        return this.stockService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public ReturnOrderBillingDao getReturnOrderBillingDao() {
        return this.returnOrderBillingDao;
    }

    public void setReturnOrderBillingDao(ReturnOrderBillingDao returnOrderBillingDao) {
        this.returnOrderBillingDao = returnOrderBillingDao;
    }

    public CheckinOrderDAO getCheckinOrderDAO() {
        return checkinOrderDAO;
    }

    public void setCheckinOrderDAO(CheckinOrderDAO checkinOrderDAO) {
        this.checkinOrderDAO = checkinOrderDAO;
    }

    public ReturnOrderDAO getReturnOrderDAO() {
        return returnOrderDAO;
    }

    public void setReturnOrderDAO(ReturnOrderDAO returnOrderDAO) {
        this.returnOrderDAO = returnOrderDAO;
    }


    public IReturnCommand getReturnActionServiceForActionCode(String actionCode) {
        return ReturnActionFactory.getInstance().getReturnAction(actionCode);
    }

    public List<IReturnCommand> getReturnActionServiceForActionCode(ReturnDetail rdetail) {
        return ReturnActionFactory.getInstance().getReturnActions(rdetail);
    }

    public ReturnActionService getDispositionActionService(String actionCode) {
        return DispositionCodeActionFactory.getInstance().getDispositionAction(actionCode);
    }

    /**
     * This method created return document for a particular order document number and order line number
     */
    public ReturnDocument createReturnDocItems(ReturnDocument returnDoc, String orderDocNumber,
            String orderLineNumber) throws Exception {

        OrderDocument orderDoc = null;

        if (!StringUtils.isEmpty(orderDocNumber)) {
            if (orderDocNumber.equalsIgnoreCase(MMConstants.ReturnDocument.NEW_ORDER_DOCUMENT)) {
                returnDoc = setDocParams(null, returnDoc);
            }
            else {
                orderDoc = this.checkinOrderDAO.getOrderDocument(orderDocNumber);

                returnDoc = this.setDocParams(orderDoc, returnDoc);
                List<OrderDetail> odetails = new ArrayList<OrderDetail>(0);
                if (returnDoc.isCurrDocVendorReturnDoc())
                    odetails = orderDoc.getOrderDetails();
                else
                    odetails = this.checkinOrderDAO.getOrderLinesForCustomerReturn(orderDoc
                            .getDocumentNumber());

                List<ReturnDetail> rdetails = new ArrayList<ReturnDetail>(0);

                for (OrderDetail odetail : odetails) {

                    if (odetail.isLineReturnable()) {
                        ReturnDetail rdetail = this.createReturnDetail(odetail, returnDoc);
                        rdetails.add(rdetail);
                    }

                }

                returnDoc.setReturnDetails(rdetails);
            }
        }

        return returnDoc;
    }


    /**
     * sets the document parameters of a return document from its corresponding order document
     * 
     * @param odoc
     * @param returnDoc
     * @return
     */
    public ReturnDocument setDocParams(OrderDocument odoc, ReturnDocument returnDoc) {

        returnDoc.setReturnOrderId(returnDoc.getDocumentNumber());
        returnDoc.setReturnDocumentStatusCode(MMConstants.OrderStatus.INITIATED);
        if (returnDoc.isReturnToVendorRequired())
            returnDoc.setReturnTypeCode(MMConstants.CheckinDocument.VENDOR_RETURN_ORDER_LINE);
        else
            returnDoc.setReturnTypeCode(MMConstants.CheckinDocument.CUSTOMER_ORDER_RETURN);

        Profile profile = null;

        if (odoc != null) {
            returnDoc.setCustomerProfileId(odoc.getCustomerProfileId());
            returnDoc.setOrderDocumentNumber(odoc.getDocumentNumber());
            returnDoc.setOrderDocument(odoc);

            returnDoc.setVendorHeaderGeneratedId(odoc.getVendorHeaderGeneratedId());
            returnDoc.setVendorDetailAssignedId(odoc.getVendorDetailAssignedId());
            returnDoc.setVendorNm(odoc.getVendorNm());

            returnDoc.setRetrievalBuildingCd(odoc.getDeliveryBuildingCd());
            Warehouse warehouse = odoc.getWarehouse();

            if (warehouse == null)
                warehouse = StoresPersistableBusinessObject.getObjectByPrimaryKey(Warehouse.class,
                        odoc.getWarehouseCd());

            if (warehouse != null) {
                profile = warehouse.getBillingProfile();
            }
        }
        else {
            String initiator = GlobalVariables.getUserSession().getPerson().getPrincipalName();
            profile = SpringContext.getBean(ProfileService.class).getDefaultCustomerProfile(
                    initiator);
        }

        if (profile != null)
            returnDoc.setCustomerProfileId(profile.getProfileId());
        returnDoc.getDocumentHeader().setDocumentDescription(
                "Return of " + returnDoc.getDocumentNumber());

        return returnDoc;
    }

    private MMDecimal getStockPriceForOrderLine(OrderDetail odetail) {

        odetail = MMServiceLocator.getCheckinOrderService().getOrderDetailWithStock(odetail);

        if (StringUtils.isEmpty(odetail.getCatalogItem().getStockId()))
            return new MMDecimal(0);

        return odetail.getCatalogItem().getStock().getStockPrice();

    }

    public ByteArrayOutputStream generatePDFAfterApproval(ReturnDocument returnDoc)
            throws Exception {

        ByteArrayOutputStream manifestStream = null;

        List<ByteArrayOutputStream> RTVLabelStream = null;

        ByteArrayOutputStream finalStream = null;


        RTVPDFService rtvPDFService = MMServiceLocator.getRTVPDFService();

        List<ReturnDetail> linesByOrder = returnDoc.getReturnLinesForRTV();

        List<ByteArrayOutputStream> fileList = new ArrayList<ByteArrayOutputStream>(0);

        String messages = null;

        if (!MMUtil.isCollectionEmpty(linesByOrder)
                && (ObjectUtils.isNotNull(returnDoc.getOrderDocument()))) {
            Object document = rtvPDFService.createDocument();
            rtvPDFService.addReturnLineToList(linesByOrder, messages);

            manifestStream = rtvPDFService.closeDocument(document);
            fileList.add(manifestStream);
        }
        RTVLabelStream = MMServiceLocator.getRtvReportService()
                .generateRTVLabelReportPDF(returnDoc);

        if (RTVLabelStream != null)
            fileList.addAll(RTVLabelStream);

        if (!MMUtil.isCollectionEmpty(fileList)) {
            finalStream = MMServiceLocator.getCountWorksheetReportService()
                    .combineAndFlushReportPDFStreams(fileList);
        }

        return finalStream;
    }

    public int updateOrderstatus(final String orderDocNumber, final String orderStatus) {
        return this.returnOrderBillingDao.updateOrderstatus(orderDocNumber, orderStatus);
    }

    /**
     * This method creates a return line for a particular oreder line
     * 
     * @param odetail
     * @return
     */
    private ReturnDetail createReturnDetail(OrderDetail odetail, ReturnDocument returnDoc) {
        ReturnDetail rdetail = new ReturnDetail();

        rdetail.setCatalogItemId(odetail.getCatalogItemId());
        rdetail.setCatalogItem(odetail.getCatalogItem());
        rdetail.setReturnQuantity(0);

//        rdetail.setReturnItemPrice(this.getStockPriceForOrderLine(odetail));
        rdetail.setReturnUnitOfIssueOfCode(odetail.getStockUnitOfIssueCd());
        rdetail.setReturnUnitOfIssue(odetail.getStockUnitOfIssue());
        rdetail.setReturnItemDetailDescription(odetail.getOrderItemDetailDesc());
        rdetail.setReturnDoc(returnDoc);
        rdetail.setReturnDocNumber(returnDoc.getDocumentNumber());

        rdetail.setVendorHeaderGeneratedId(odetail.getVendorHeaderGeneratedId());
        rdetail.setVendorDetailAssignedId(odetail.getVendorDetailAssignedId());
        rdetail.setVendorNm(odetail.getVendorNm());
        rdetail.setOrderLineNumber(odetail.getItemLineNumber());

        if (returnDoc.isCurrDocVendorReturnDoc()) {
            rdetail.setReturnItemPrice(this.getVendorReturnItemPrice(odetail));
            rdetail.setReturnTypeCode(MMConstants.CheckinDocument.VENDOR_RETURN_ORDER_LINE);
            rdetail.setActionCd(MMConstants.ReturnActionCode.WAREHS);
        }
        else {
            rdetail.setReturnItemPrice(this.getCustomerReturnItemPrice(odetail));
            rdetail.setReturnTypeCode(MMConstants.CheckinDocument.CUSTOMER_ORDER_RETURN);
            rdetail.setActionCd(MMConstants.ReturnActionCode.CUSTOMER);
        }

        rdetail.setVendorCreditInd(false);
        rdetail.setVendorReshipInd(false);
        rdetail.setVendorDispositionInd(false);

        rdetail.setReqsId(odetail.getOrderDocument().getReqsId());
        rdetail.setPoId(odetail.getPoId());
        rdetail.setOrderDetailId(odetail.getOrderDetailId());
        rdetail.setOrderDetail(odetail);
        // rdetail.setr
        return rdetail;
    }

    /**
     * returns the price of the line depending upon the type of return
     * 
     * @see org.kuali.ext.mm.service.ReturnOrderService#getReturnLinePrice(boolean, org.kuali.ext.mm.businessobject.OrderDetail)
     */
    public MMDecimal getReturnLinePrice(boolean isDocCustomerReturnDoc, OrderDetail odetail) {
        if (isDocCustomerReturnDoc)
            return this.getCustomerReturnItemPrice(odetail);
        else
            return this.getVendorReturnItemPrice(odetail);
    }

    /**
     * returns the item price of a customer return document line
     * 
     * @param odetail
     * @return
     */
    private MMDecimal getCustomerReturnItemPrice(OrderDetail odetail) {
        MMDecimal result = MMDecimal.ZERO;
        MMDecimal itemCostAmt = odetail.getOrderItemPriceAmt() == null ? MMDecimal.ZERO : odetail
                .getOrderItemPriceAmt();
        
        MMDecimal itemTaxAmt = odetail.getOrderItemTaxAmt() == null ? MMDecimal.ZERO : odetail
                .getOrderItemTaxAmt();
        result = itemCostAmt.add(itemTaxAmt);
        return result;
    }

    /**
     * returns the item price of a vendor return order line
     * 
     * @param odetail
     * @return
     */
    private MMDecimal getVendorReturnItemPrice(OrderDetail odetail) {
        MMDecimal result = MMDecimal.ZERO;
        result = odetail.getOrderItemCostAmt() == null ? MMDecimal.ZERO : odetail
                .getOrderItemCostAmt();
        return result;
    }

    /**
     * populates the stock object of catalog item if it is not present
     * 
     * @see org.kuali.ext.mm.service.ReturnOrderService#getReturnDetailWithStock(org.kuali.ext.mm.businessobject.ReturnDetail)
     */
    public ReturnDetail getReturnDetailWithStock(ReturnDetail odetail) {

        Stock stock = null;

        String catalogItemId = odetail.getCatalogItemId();
        String stockDistributorNumber = odetail.getStockDistributorNumber();

        CatalogItem citem = StoresPersistableBusinessObject.getObjectByPrimaryKey(
                CatalogItem.class, catalogItemId);
        odetail.setCatalogItem(citem);
        if (citem == null)
            return null;


        if (StringUtils.isEmpty(citem.getStockId()) || ObjectUtils.isNull(citem.getStock())) {

            stock = this.getStockService().getStockByDistributorNumber(
                    odetail.getCatalogItem().getDistributorNbr());

            if (stock == null) {
                return null;
            }

            odetail.getCatalogItem().setStock(stock);
            odetail.getCatalogItem().setStockId(stock.getStockId());

        }
        else {
            stock = odetail.getCatalogItem().getStock();
            if (!stock.getStockDistributorNbr().equalsIgnoreCase(stockDistributorNumber))
                return null;
        }
        odetail.setReturnItemDetailDescription(odetail.getCatalogItem().getStock().getStockDesc());
        return odetail;

    }

    /**
     * process rental stock items
     * 
     * @param rdoc
     */
    private void processRentals(ReturnDocument rdoc) {
        for (ReturnDetail rdetail : rdoc.getReturnDetails()) {
            if (rdetail.getCatalogItem().getStock().isRental()) {

                List<StagingRental> stagingRentals = rdetail.getReturnRentals();
                
                if (!MMUtil.isCollectionEmpty(stagingRentals)) {
                    List<String> serialNumberList = new ArrayList<String>();
                    for (StagingRental stagingRental : stagingRentals) {
                        serialNumberList.add(stagingRental.getSerialNumber());
                    }
                    Collection<Rental> currentRentals = MMServiceLocator.getRentalService()
                        .getCurrentNonReturnedRentalItems(rdetail.getCatalogItem().getStock(),
                                serialNumberList);
                    for(Rental rental : currentRentals) {
                            rental.setReturnDetailId(rdetail.getReturnDetailId());
                            rental.setReturnDetail(rdetail);
                            rental.setReturnDate(KNSServiceLocator.getDateTimeService().getCurrentTimestamp());
                            rental.setRentalStatusCode(MMConstants.Rental.RENTAL_STATUS_RETURNED);
                            rdetail.getRentals().add(rental);
                    }
                    MMServiceLocator.getRentalService().saveRentalList(rdetail.getRentals());
                    
                    if(MMConstants.DispositionCode.RETURN_TO_SHELF.equals(rdetail.getDispostitionCode())) {
                        List<Rental> backToShelfRentals = new ArrayList<Rental>();
                        for(Rental rental : currentRentals) {
                            Rental newRental = new Rental();
                            newRental.setRentalStatusCode(MMConstants.Rental.RENTAL_STATUS_AVAILABLE);
                            newRental.setRentalSerialNumber(rental.getRentalSerialNumber());
                            newRental.setRentalTypeCode(rental.getRentalTypeCode());
                            newRental.setRentalType(rental.getRentalType());
                            newRental.setStockId(rental.getStockId());
                            newRental.setStock(rental.getStock());
                            backToShelfRentals.add(newRental);
                        }
                        MMServiceLocator.getRentalService().saveRentalList(backToShelfRentals);
                    }

                }                
            }
        }
    }

    /**
     * Implements the logic required for post route scenario
     * 
     * @see org.kuali.ext.mm.service.ReturnOrderService#processReturnDocument(org.kuali.ext.mm.document.ReturnDocument)
     */
    public void processReturnDocument(ReturnDocument rdoc) {
        this.processReturnDispositions(rdoc);
        this.processRentals(rdoc);
        // this.processDepartmentCredit(rdoc);
        this.computeSalesTaxAmount(rdoc);

    }

    /**
     * @param rdoc
     */
    private void computeSalesTaxAmount(ReturnDocument rdoc) {
        // compute tax amount to be adjusted
        List<ReturnDetail> returnDetails = rdoc.getReturnDetails();
        MMDecimal salesTax = MMDecimal.ZERO;
        for (ReturnDetail returnDetail : returnDetails) {
            OrderDetail odetail = returnDetail.getOrderDetail();
            if (ObjectUtils.isNotNull(odetail)) {
                MMDecimal orderItemTaxAmt = odetail.getOrderItemTaxAmt() == null ? MMDecimal.ZERO
                        : odetail.getOrderItemTaxAmt();
                salesTax = salesTax.add(orderItemTaxAmt.multiply(new MMDecimal(returnDetail
                        .getReturnQuantity())));

            }
        }
        rdoc.setReturnTaxAmount(salesTax.kualiDecimalValue());
    }

    /**
     * @see org.kuali.ext.mm.service.ReturnOrderService#createCreditMemo(org.kuali.ext.mm.document.ReturnDocument,
     *      org.kuali.ext.mm.businessobject.ReturnDetail)
     */
    public void createCreditMemo(ReturnDocument rdoc, ReturnDetail rdetail) {

        if (!rdoc.isCurrDocVendorReturnDoc())
            return;

        List<CreditMemoExpected> data = new ArrayList<CreditMemoExpected>();
        data.add(this.getCreditMemoExpected(rdoc, rdetail));
        rdetail.setCreditMemoExpected(data);

    }

    /**
     * creates a credit memo for an order line
     * 
     * @param rdoc
     * @param rdetail
     * @return
     */
    private CreditMemoExpected getCreditMemoExpected(ReturnDocument rdoc, ReturnDetail rdetail) {
        CreditMemoExpected result = new CreditMemoExpected();
        result.setExpectedCreateDate(new java.sql.Date(new java.util.Date().getTime()));
        result.setReceived(false);
        result.setReturnDetailId(rdetail.getReturnDetailId());
        result.setReturnDetail(rdetail);
        result.setWarehouseCode(rdoc.getWarehouseCode());
        return result;
    }

    /**
     * processes internal billing of a return document
     * 
     * @param rdoc
     */
    private void processDepartmentCredit(ReturnDocument rdoc) {
        if (rdoc.isDepartmentCreditRequired()) {
            this.processInternalBilling(rdoc);
        }
    }

    /**
     * process return disposition code for all the return lines. depending upon the disposition code corresponding actions would be
     * executed
     * 
     * @param rdoc
     */
    private void processReturnDispositions(ReturnDocument rdoc) {

        for (ReturnDetail rdetail : rdoc.getReturnDetails()) {
            if (!StringUtils.isEmpty(rdetail.getActionCd())) {

                List<IReturnCommand> aservices = getReturnActionServiceForActionCode(rdetail);
                if (!MMUtil.isCollectionEmpty(aservices)) {
                    try {
                        for (IReturnCommand aservice : aservices) {
                            aservice.execute(rdoc, rdetail);
                        }
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            String dispositionCode = rdetail.getDispostitionCode();

            ReturnActionService aservice = getDispositionActionService(dispositionCode);

            if (aservice != null) {
                try {
                    aservice.execute(rdoc, rdetail);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    /**
     * processes vendor return documents. These documents are created in response to disposition code "return to vendor". created
     * documents would be submitted
     * 
     * @see org.kuali.ext.mm.service.ReturnOrderService#processVendorReturnDocs(org.kuali.ext.mm.document.ReturnDocument)
     */
    public void processVendorReturnDocs(ReturnDocument rdoc) {

        if (rdoc.getVendorReturnDoc() != null) {
            ReturnDocument vdoc = rdoc.getVendorReturnDoc();
            try {

                KNSServiceLocator.getDocumentService().saveDocument(vdoc);

                if (!vdoc.getDocumentHeader().hasWorkflowDocument())
                    vdoc = (ReturnDocument) KNSServiceLocator.getDocumentService()
                            .getByDocumentHeaderId(vdoc.getDocumentNumber());

                vdoc.getDocumentHeader().getWorkflowDocument().routeDocument(
                        "Document submitted automatically");

            }
            catch (Exception e) {
                throw new RuntimeException("Vendor document could not be routed.");
            }

        }

    }

    /**
     * creates financial accounting line for the required return lines
     * 
     * @param mmAcctLine
     * @param chargeAmt
     * @return
     */
    protected FinancialAccountingLine createFinancialAccountingLine(WarehouseAccounts mmAcctLine,
            KualiDecimal chargeAmt) {
        FinancialAccountingLine finAcctLine = new FinancialAccountingLine();
        finAcctLine.setAccountNumber(mmAcctLine.getAccountNbr());
        finAcctLine.setAmount(chargeAmt);
        finAcctLine.setBalanceTypeCode("AC");
        finAcctLine.setChartOfAccountsCode(mmAcctLine.getFinCoaCd());
        finAcctLine.setFinancialDocumentLineDescription("Pay warehouse"
                + mmAcctLine.getWarehouseCd());
        finAcctLine.setFinancialDocumentLineTypeCode(MMConstants.FIN_ACCT_LINE_TYP_TO);
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
        return finAcctLine;
    }


    /**
     * Processes internal billing for the return document
     * 
     * @param warehouse
     * @param ibItems
     * @param incomeLines
     * @param assetInformation
     * @return
     */
    public DocumentHeader processInternalBilling(Warehouse warehouse,
            List<FinancialInternalBillingItem> ibItems, List<FinancialAccountingLine> incomeLines,
            FinancialCapitalAssetInformation assetInformation) {
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (factory.isSystemAvailable()) {
            KualiDecimal totalChargeAmt = KualiDecimal.ZERO;
            for (FinancialAccountingLine account : incomeLines) {
                totalChargeAmt = totalChargeAmt.add(account.getAmount());
            }
            ArrayList<FinancialAccountingLine> expenseLines = new ArrayList<FinancialAccountingLine>();
            FinancialAccountingLine finAcctLine = createFinancialAccountingLine(warehouse
                    .getIncomeAccount(), totalChargeAmt);
            expenseLines.add(finAcctLine);
            return factory.getFinancialInternalBillingService().submitInternalBillingDocument(
                    warehouse.getBillingProfile(), ibItems, incomeLines, expenseLines,
                    assetInformation);
        }
        return null;
    }

    /**
     * Processes Internal billing for the return documents
     * 
     * @see org.kuali.ext.mm.service.ReturnOrderService#processInternalBilling(org.kuali.ext.mm.document.ReturnDocument)
     */
    public void processInternalBilling(ReturnDocument rdoc) {

        if (ObjectUtils.isNull(rdoc.getOrderDocument())
                || !this.returnOrderBillingDao.hasAccountsForBilling(rdoc.getDocumentNumber()))
            return;

        String docNumber = rdoc.getDocumentNumber();

        String warehouseCode = rdoc.getOrderDocument().getWarehouseCd();

        Map<String, List<FinancialInternalBillingItem>> returnedOrderLines = this.returnOrderBillingDao
                .getReturnedOrderLines(docNumber);
        Set<String> keys = returnedOrderLines.keySet();
        Map<String, List<FinancialAccountingLine>> returnedAccountingLines = getReturnBillingAccountingLines(rdoc);
        Warehouse warehouse = rdoc.getOrderDocument().getWarehouse();

        if (warehouse == null)
            warehouse = StoresPersistableBusinessObject.getObjectByPrimaryKey(Warehouse.class,
                    warehouseCode);

        for (String key : keys) {
            List<FinancialInternalBillingItem> lineItems = returnedOrderLines.get(key);
            List<FinancialAccountingLine> acctLines = returnedAccountingLines.get(key);
            if (warehouse != null && warehouse.isActive()) {
                FinancialCapitalAssetInformation capitalAssetInformation = null;
                String astInfoId = null;
                if (key.contains("-") && (astInfoId = key.split("-")[1]) != null) {
                    capitalAssetInformation = new FinancialCapitalAssetInformation();
                    MMCapitalAssetInformation assetInfo = SpringContext.getBean(
                            BusinessObjectService.class).findBySinglePrimaryKey(
                            MMCapitalAssetInformation.class, astInfoId);
                    if (assetInfo != null) {
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
                DocumentHeader ibDocHeader = processInternalBilling(warehouse, lineItems,
                        acctLines, capitalAssetInformation);
                if (ibDocHeader != null) {
                    String ibDocNumber = ibDocHeader.getDocumentNumber();
                    if (astInfoId != null) {
                        for (ReturnDetail detail : rdoc.getReturnDetails()) {
                            if (detail.getOrderDetailId().toString().equals(astInfoId)) {
                                detail.setCreditDocumentNumber(ibDocNumber);
                            }
                        }
                    }
                    else {
                        for (ReturnDetail detail : rdoc.getReturnDetails()) {
                            detail.setCreditDocumentNumber(ibDocNumber);
                        }
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

    public void processBillingGlpes(ReturnDocument rdoc,
            HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups) {
        if (ObjectUtils.isNull(rdoc.getOrderDocument())
                || !this.returnOrderBillingDao.hasAccountsForBilling(rdoc.getDocumentNumber()))
            return;

        String docNumber = rdoc.getDocumentNumber();
        String warehouseCode = rdoc.getOrderDocument().getWarehouseCd();
        GeneralLedgerBuilderService glpeService = SpringContext
                .getBean(GeneralLedgerBuilderService.class);
         
        Map<String, List<FinancialAccountingLine>> returnedAccountingLines = getReturnBillingAccountingLines(rdoc);
        Set<String> keys = returnedAccountingLines.keySet();
        Warehouse warehouse = StoresPersistableBusinessObject.getObjectByPrimaryKey(
                Warehouse.class, warehouseCode);

        for (String key : keys) {
            List<FinancialAccountingLine> incomeAcctLines = returnedAccountingLines.get(key);
            KualiDecimal totalChargeAmt = KualiDecimal.ZERO;
            for (FinancialAccountingLine account : incomeAcctLines) {
                totalChargeAmt = totalChargeAmt.add(account.getAmount());
            }
            if (totalChargeAmt.isZero()) {
                continue;
            }
            ArrayList<FinancialAccountingLine> expenseLines = new ArrayList<FinancialAccountingLine>();
            FinancialAccountingLine finAcctLine = createFinancialAccountingLine(warehouse
                    .getIncomeAccount(), totalChargeAmt);
            expenseLines.add(finAcctLine);
            glpeService.buildBillingGlpes(glGroups, warehouse, incomeAcctLines, expenseLines,
                    "Stores credit");
        }
    }

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
     * returns the balance quantity for all the order lines
     * 
     * @see org.kuali.ext.mm.service.ReturnOrderService#getBalanceQuantityForOrder(org.kuali.ext.mm.document.ReturnDocument)
     */
    public Map<Integer, Integer> getBalanceQuantityForOrder(ReturnDocument rdoc, boolean isRouted) {
        return this.returnOrderDAO.getBalanceQuantityForOrder(rdoc, isRouted);
    }
    
    /**
     * @see org.kuali.ext.mm.service.ReturnOrderService#getQuantityAlreadyReturned(org.kuali.ext.mm.businessobject.ReturnDetail)
     */
    public Integer getQuantityAlreadyReturned(ReturnDetail returnDetail) {
        Integer qty = 0;
        
        Map<String, Object> fieldValues = new HashMap<String,Object>();
        fieldValues.put(MMConstants.ReturnDetail.ORDER_DETAIL_ID, returnDetail.getOrderDetailId());
        fieldValues.put(MMConstants.ReturnDetail.RETURN_DOCUMENT 
                + "." + MMConstants.ReturnDocument.RETURN_DOCUMENT_STATUS_CODE, MMConstants.OrderStatus.ORDER_LINE_OPEN);
        Collection<ReturnDetail> returnDetails = KNSServiceLocator.getBusinessObjectService().findMatching(ReturnDetail.class, fieldValues);
        for(ReturnDetail detail : returnDetails) {
            qty += detail.getReturnQuantity();
        }
        
        return qty;
    }
    
    /**
     * Used by the lookup framework for pulling the result for the order lines that can be returned
     * 
     * @see org.kuali.ext.mm.service.ReturnOrderService#getSearchResultsForReturns(java.util.Map, boolean)
     */
    public List<OrderDetail> getSearchResultsForReturns(Map<String, String> criteria,
            boolean isVendor) {
        return this.returnOrderDAO.getSearchResultsForReturns(criteria, isVendor);
    }
    
    public Map<String, List<FinancialAccountingLine>> getReturnBillingAccountingLines(ReturnDocument document) {
        Map<String, List<FinancialAccountingLine>> accountingLinesMap = new HashMap<String, List<FinancialAccountingLine>>();
        
        Map<Integer, ReturnDetail> orderDetails = new HashMap<Integer, ReturnDetail>();
        Map<Integer, ReturnDetail> orderDetailsAssets = new HashMap<Integer, ReturnDetail>();
        for(ReturnDetail rdetail : document.getReturnDetails()) {
            if(rdetail.isDepartmentCreditInd() 
                    && rdetail.getReturnQuantity() > 0
                    && !(MMConstants.ReturnActionCode.WAREHS.equals(rdetail.getActionCd())
                            || MMConstants.ReturnActionCode.REJECTED.equals(rdetail.getActionCd()))) {
                if(isReturningAsset(rdetail))
                    orderDetailsAssets.put(rdetail.getOrderDetailId(), rdetail);
                else                
                    orderDetails.put(rdetail.getOrderDetailId(), rdetail);                 
            }
        }
        if(!orderDetails.isEmpty()) {
            Map<String, Object> fieldValues = new HashMap<String, Object>();
            fieldValues.put(MMConstants.Accounts.ORDER_DETAIL_ID, orderDetails.keySet());
            Collection<Accounts> accountingLines = KNSServiceLocator.getBusinessObjectService().findMatching(Accounts.class, fieldValues);
            accountingLinesMap.put(document.getOrderDocumentNumber(), combineAndConvertAccountingLines(orderDetails, accountingLines, document.getOrderDocumentNumber()));
        }
        
        for(Integer orderDetailId : orderDetailsAssets.keySet()) {
            String orderDetailAssetKey = document.getOrderDocumentNumber() + "-" + String.valueOf(orderDetailId);
            Map<String, Object> fieldValuesAssets = new HashMap<String, Object>();
            fieldValuesAssets.put(MMConstants.Accounts.ORDER_DETAIL_ID, orderDetailId);
            Collection<Accounts> accountingLinesAssets = KNSServiceLocator.getBusinessObjectService().findMatching(Accounts.class, fieldValuesAssets);
            accountingLinesMap.put(orderDetailAssetKey, combineAndConvertAccountingLines(orderDetailsAssets, accountingLinesAssets, document.getOrderDocumentNumber()));
        }
        return accountingLinesMap;
    }
    
    /**
     * @param accountingLines
     * @param orderDocumentNumber
     * @return
     */
    private List<FinancialAccountingLine> combineAndConvertAccountingLines(Map<Integer, ReturnDetail> orderDetailIdMap,
            Collection<Accounts> accountingLines, String orderDocumentNumber) {
        Map<String, FinancialAccountingLine> sortingMap = new HashMap<String, FinancialAccountingLine>();
        for(Accounts account : accountingLines) {
            String key = account.getFinCoaCd() + "-" + account.getAccountNbr() 
                + "-" + MMUtil.formatNullString(account.getSubAcctNbr())
                + "-" + account.getFinObjectCd() 
                + "-" + MMUtil.formatNullString(account.getFinSubObjectCd())
                + "-" + MMUtil.formatNullString(account.getProjectCd()) 
                + "-" + MMUtil.formatNullString(account.getDepartmentReferenceText());
            
            ReturnDetail returnDetail = orderDetailIdMap.get(account.getOrderDetailId());            
            KualiDecimal addendAmount = null;
            if(MMConstants.ReturnStatusCode.RENTAL_RETURN.equals(returnDetail.getReturnDetailStatusCode()))
                addendAmount = new KualiDecimal(returnDetail.getOrderDetail().getOrderItemAdditionalCostAmt().doubleValue() * returnDetail.getReturnQuantity().doubleValue());
            else
                addendAmount = new KualiDecimal((account.getAccountFixedAmt().doubleValue() / account.getOrderDetail().getOrderItemQty().doubleValue()) * returnDetail.getReturnQuantity().doubleValue());
            
            FinancialAccountingLine finAccountLine = sortingMap.get(key);
            if(finAccountLine == null)
                sortingMap.put(key, createFinancialAccountingLine(account, addendAmount, orderDocumentNumber));
            else {
                KualiDecimal lineAmount = finAccountLine.getAmount();
                finAccountLine.setAmount(lineAmount.add(addendAmount));
                sortingMap.put(key, finAccountLine);
            }
        }
        
        List<FinancialAccountingLine> accountingLinesList = new ArrayList<FinancialAccountingLine>();
        accountingLinesList.addAll(sortingMap.values());
        return accountingLinesList;
    }

    /**
     * @param rdetail
     * @return
     */
    private boolean isReturningAsset(ReturnDetail rdetail) {
        MMCapitalAssetInformation asset = KNSServiceLocator.getBusinessObjectService()
            .findBySinglePrimaryKey(MMCapitalAssetInformation.class, rdetail.getOrderDetailId());
        return asset != null;
    }

    /**
     * @param account
     * @param lineAmount
     * @return
     */
    private FinancialAccountingLine createFinancialAccountingLine(Accounts mmAcctLine, KualiDecimal lineAmount, String orderDocumentNumber) {
        FinancialAccountingLine finAcctLine = new FinancialAccountingLine();
        
        finAcctLine.setAmount(lineAmount);        
        finAcctLine.setChartOfAccountsCode(mmAcctLine.getFinCoaCd());
        finAcctLine.setAccountNumber(mmAcctLine.getAccountNbr());
        finAcctLine.setSubAccountNumber(mmAcctLine.getSubAcctNbr());
        finAcctLine.setFinancialObjectCode(mmAcctLine.getFinObjectCd());
        finAcctLine.setFinancialSubObjectCode(mmAcctLine.getFinSubObjectCd());
        finAcctLine.setProjectCode(mmAcctLine.getProjectCd());
        finAcctLine.setOrganizationReferenceId(mmAcctLine.getDepartmentReferenceText());
        
        finAcctLine.setBalanceTypeCode(GlConstants.BALANCE_TYPE_ACTUAL);
        finAcctLine.setFinancialDocumentLineDescription("Stores Charge Order - "
                + orderDocumentNumber);
        finAcctLine.setFinancialDocumentLineTypeCode(MMConstants.FIN_ACCT_LINE_TYP_FROM);        
        finAcctLine.setObjectBudgetOverride(false);
        finAcctLine.setObjectBudgetOverrideNeeded(false);
        finAcctLine.setOverrideCode("");
        finAcctLine.setPostingYear(SpringContext.getBean(
                FinancialSystemAdaptorFactory.class)
                .getFinancialUniversityDateService()
                .getCurrentFiscalYear());
        finAcctLine
                .setReferenceOriginCode(GlConstants.getFinancialSystemOriginCode());
        finAcctLine.setReferenceTypeCode("");
        finAcctLine.setSalesTaxRequired(false);
        
        return finAcctLine;
    }
    
    public MMDecimal getReturnLineCreditAmount(ReturnDetail rdetail) {
        MMDecimal result = MMDecimal.ZERO;
        if (rdetail.isItemReturned()
                || (rdetail.getReturnQuantity() != null && rdetail.getReturnQuantity() > 0)) {
            MMDecimal qty = rdetail.getReturnQuantity() == null ? MMDecimal.ZERO : new MMDecimal(
                rdetail.getReturnQuantity());
            
            MMDecimal unitCredit = MMDecimal.ZERO;
            
            if(MMConstants.AdditionalCostType.DEPOSIT.equals(rdetail.getOrderDetail().getAdditionalCostTypeCode())) {
                unitCredit = unitCredit.add(rdetail.getOrderDetail().getOrderItemAdditionalCostAmt() == null ? 
                        MMDecimal.ZERO : rdetail.getOrderDetail().getOrderItemAdditionalCostAmt());
            }
            if(!MMConstants.ReturnStatusCode.RENTAL_RETURN.equals(rdetail.getReturnDetailStatusCode())) {
                unitCredit = unitCredit.add(rdetail.getReturnItemPrice() == null ? MMDecimal.ZERO : rdetail
                    .getReturnItemPrice());
            }
            
            MMDecimal itemPercent = rdetail.getReturnItemPercentage() == null ? MMDecimal.ZERO
                    : rdetail.getReturnItemPercentage();
            if (!itemPercent.equals(MMDecimal.ZERO))
                result = new MMDecimal(qty.multiply(unitCredit).multiply(itemPercent).doubleValue());
            else
                result = new MMDecimal(qty.multiply(unitCredit).doubleValue());
        }
        return result;
    }
    
}