/**
 *
 */
package org.kuali.ext.mm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Agreement;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.OrderDocumentLookable;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ReorderCatalogItemDetail;
import org.kuali.ext.mm.businessobject.StoresPersistableBusinessObject;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.ReturnOrderDAO;
import org.kuali.ext.mm.document.ReOrderDocument;
import org.kuali.ext.mm.service.AddressService;
import org.kuali.ext.mm.service.ProfileService;
import org.kuali.ext.mm.service.ReOrderService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;


/**
 * @author rponraj
 */
public class ReorderServiceImpl implements ReOrderService {

    private BusinessObjectService businessObjectService;

    private ReturnOrderDAO returnOrderDAO;

    /**
     * This method creates accounting information for all the order lines
     * 
     * @see org.kuali.ext.mm.service.ReOrderService#createAccountsForApprovedDocument(org.kuali.ext.mm.document.ReOrderDocument)
     */
    public void createAccountsForApprovedDocument(ReOrderDocument rdoc) {
        Warehouse warehouse = rdoc.getWarehouse();
        WarehouseAccounts wareAccount = warehouse.getCostOfGoodsAccount();
        if (ObjectUtils.isNotNull(wareAccount)) {
            for (OrderDetail odetail : rdoc.getOrderDetails()) {
               loadAccountingInfoForOrderLine(odetail, wareAccount);
            }
        }
    }

    /**
     * creates a complete order document with the order lines from the corresponding agreement
     * 
     * @param agreemnt
     * @param orderDoc
     * @return
     */
    private List<OrderDetail> createOrderDocForAllItems(Agreement agreemnt, ReOrderDocument orderDoc, String warehouseCode) {
        List<ReorderCatalogItemDetail> lisResult = this.returnOrderDAO.getCatalogItemsForReorder(
                agreemnt.getAgreementNbr(), null, null,warehouseCode);
        Map<String, ReorderCatalogItemDetail> mapData = getMappedData(lisResult);
        List<CatalogItem> reorderedItems = returnOrderDAO
                .getCatalogItemsForStockAgreementNumber(agreemnt.getAgreementNbr());
        List<OrderDetail> orderLines = new ArrayList<OrderDetail>(0);
        int index = 0;
        for (CatalogItem citem : reorderedItems) {
            ReorderCatalogItemDetail reoderItem = mapData.get(citem.getCatalogItemId());
            OrderDetail odetail = this.createOrderDetail(citem, orderDoc, agreemnt,
                    reoderItem != null ? reoderItem.getDefaultQuantity() : 0, ++index);
            orderLines.add(odetail);
        }
        return orderLines;
    }

    /**
     * creates e new reorder document for teh corresponding agrrement
     * 
     * @see org.kuali.ext.mm.service.ReOrderService#createOrderDocument(java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, org.kuali.ext.mm.document.ReOrderDocument)
     */
    public ReOrderDocument createOrderDocument(String actionCode, String agreementNum,
            String catalogGroupCode, String catalogSubGroupCode, String warehouseCode,
            ReOrderDocument orderDoc) throws Exception {
        Agreement agreement = null;
        String agreementNumber = agreementNum;
        if (!StringUtils.isEmpty(agreementNumber)) {
            agreementNumber = agreementNumber.trim();

            agreement = StoresPersistableBusinessObject.getObjectByPrimaryKey(Agreement.class,
                    agreementNumber);
        }
        setDocParams(orderDoc, agreement, warehouseCode);

        List<OrderDetail> orderLines = null;
        if (actionCode.equalsIgnoreCase(MMConstants.ReorderItem.EDIT_REORDERED_ITEMS_LABEL)) {
            orderLines = getOrderDocForReorderedItems(agreement, catalogGroupCode,
                    catalogSubGroupCode, orderDoc,warehouseCode);
        }
        else {
            orderLines = createOrderDocForAllItems(agreement, orderDoc, warehouseCode);
        }
        orderDoc.getOrderDetails().addAll(orderLines);
        this.createAccountsForApprovedDocument(orderDoc);
        return orderDoc;

    }

    /**
     * creates list of order lines for items in agreement
     * 
     * @param agreement
     * @param items
     * @param orderDoc
     * @return
     */
    private List<OrderDetail> createOrderLines(Agreement agreement,
            List<ReorderCatalogItemDetail> items, ReOrderDocument orderDoc) {
        List<OrderDetail> result = new ArrayList<OrderDetail>();
        int index = 0;
        for (ReorderCatalogItemDetail dataItem : items) {
            CatalogItem item = dataItem.getCatalogItem();
            if (item == null)
                continue;
            OrderDetail data = this.createOrderDetail(item, orderDoc, agreement, dataItem
                    .getDefaultQuantity(), ++index);
            result.add(data);
        }

        return result;
    }

    public Accounts loadAccountingInfoForOrderLine(OrderDetail odetail, WarehouseAccounts accounts) {
        Accounts account = new Accounts();
        account.setFinCoaCd(accounts.getFinCoaCd());
        account.setAmountType(MMConstants.OrderDocument.OPTION_FXD);
        account.setAccountNbr(accounts.getAccountNbr());
        account.setSubAcctNbr(accounts.getSubAcctNbr());
        account.setFinObjectCd(accounts.getFinObjectCd());
        account.setFinSubObjectCd(account.getFinSubObjectCd());
        account.setOrderDetail(odetail);
        account.setOrderDetailId(odetail.getOrderDetailId());
        account.setAccountFixedAmt(new KualiDecimal(odetail.getDisplayTotal().bigDecimalValue()));
        account.setAccountPct(new BigDecimal(100));
        odetail.getAccounts().add(account);
        return account;
    }


    public BusinessObjectService getBusinessObjectService() {
        return this.businessObjectService;
    }

    /**
     * calculates the expected time value of the order line
     * 
     * @see org.kuali.ext.mm.service.ReOrderService#getExpectedTimeValue(org.kuali.ext.mm.businessobject.Agreement)
     */
    public java.sql.Date getExpectedTimeValue(Agreement agreement) {
        Integer alagDays = 1;
        if (agreement != null) {
            alagDays = agreement.getAgreementLagDays();
            if (alagDays == null || alagDays < 1)
                alagDays = 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, +alagDays);
        return new java.sql.Date(cal.getTime().getTime());
    }

    public List<OrderDocumentLookable> getLookupSearchResults() {
        return null;
    }

    /**
     * @param lisResult
     * @return
     */
    private Map<String, ReorderCatalogItemDetail> getMappedData(
            List<ReorderCatalogItemDetail> lisResult) {

        Map<String, ReorderCatalogItemDetail> result = new HashMap<String, ReorderCatalogItemDetail>(
            0);

        for (ReorderCatalogItemDetail data : lisResult) {
            if (ObjectUtils.isNotNull(data.getCatalogItem())) {
                result.put(data.getCatalogItem().getCatalogItemId(), data);
            }
        }
        return result;
    }

    /**
     * @param agreement
     * @param catalogGroupCode
     * @param catalogSubGroupCode
     * @param orderDoc
     * @return
     */
    private List<OrderDetail> getOrderDocForReorderedItems(Agreement agreement,
            String catalogGroupCode, String catalogSubGroupCode, ReOrderDocument orderDoc, String warehouseCode) {
        String agreementNbr = agreement != null ? agreement.getAgreementNbr() : null;
        List<ReorderCatalogItemDetail> lisResult = this.returnOrderDAO.getCatalogItemsForReorder(
                agreementNbr, catalogGroupCode, catalogSubGroupCode, warehouseCode);
        if (lisResult == null || lisResult.isEmpty()) {
            return new ArrayList<OrderDetail>();
        }
        List<OrderDetail> orderLines = createOrderLines(agreement, lisResult, orderDoc);
        return orderLines;
    }

    /**
     * creates fully populated order line
     * 
     * @param item
     * @param orderDoc
     * @param agreement
     * @param quantity
     * @param index
     * @return
     */
    public OrderDetail createOrderDetail(CatalogItem item, ReOrderDocument orderDoc,
            Agreement agreement, Integer quantity, Integer index) {
        OrderDetail data = new OrderDetail();
        data.setCatalogItemId(item.getCatalogItemId());
        data.setCatalogItem(item);
        data.setItemLineNumber(index);
        data.setOrderDocumentNbr(orderDoc.getDocumentNumber());
        data.setOrderStatusCd(MMConstants.OrderStatus.INITIATED);
        data.setOrderItemQty(quantity);
        data.setStockUnitOfIssueCd(item.getCatalogUnitOfIssueCd());
        data.setOrderItemCostAmt(item.getCatalogPrc());
        data.setOrderItemPriceAmt(item.getCatalogPrc());
        data.setOrderItemMarkupAmt(MMDecimal.ZERO);
        data.setOrderItemTaxAmt(MMDecimal.ZERO);
        data.setOrderItemDetailDesc(item.getStock().getStockDesc());
        data.setDistributorNbr(item.getDistributorNbr());
        if (agreement != null) {
            data.setVendorNm(agreement.getVendorNm());
            data.setExpectedDate(getExpectedTimeValue(agreement));
            data.setVendorDetailAssignedId(agreement.getVndrDetailAsgnId());
            data.setVendorHeaderGeneratedId(agreement.getVndrHeaderGnrtdId());
        }
        else {
            data.setExpectedDate(getExpectedTimeValue(null));
        }

        return data;
    }

    public ReturnOrderDAO getReturnOrderDAO() {
        return this.returnOrderDAO;
    }


    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    /**
     * sets the document parameters of the new order document
     * 
     * @param orderDoc
     * @param agreement
     * @param warehouseCode
     * @return
     */
    private ReOrderDocument setDocParams(ReOrderDocument orderDoc, Agreement agreement,
            String warehouseCode) {
        orderDoc.setOrderId(KNSServiceLocator.getSequenceAccessorService()
                .getNextAvailableSequenceNumber(ShopCartConstants.Sequence.ORDER_ID_SEQ));
        orderDoc.setOrderTypeCode(MMConstants.OrderDocument.ORDER_TYPE_STOCK);
        orderDoc.setOrderStatusCd(MMConstants.OrderStatus.INITIATED);
        orderDoc.setWarehouseCd(warehouseCode);

        Warehouse warehouse = StoresPersistableBusinessObject.getObjectByPrimaryKey(
                Warehouse.class, warehouseCode);
        orderDoc.setWarehouse(warehouse);

        String initPrincipalId = orderDoc.getDocumentHeader().getWorkflowDocument().getInitiatorPrincipalId();
        String initiator = KIMServiceLocator.getPersonService().getPerson(initPrincipalId).getPrincipalName();
        Profile profile = SpringContext.getBean(ProfileService.class).getDefaultCustomerProfile(initiator);

        // populating the billing and shipping address
        if (profile != null) {
            orderDoc.setCampusCd(profile.getCampusCode());
            orderDoc.setCustomerProfile(profile);
            orderDoc.setCustomerProfileId(profile.getProfileId());
            orderDoc.setDeliveryBuildingCd(profile.getDeliveryBuildingCode());
            orderDoc.setDeliveryBuildingRmNbr(profile.getDeliveryBuildingRoomNumber());
            Address billingAddress = SpringContext.getBean(AddressService.class).getBillingAddress(
                    profile);
            Address shippingAddress = SpringContext.getBean(AddressService.class)
                    .getShippingAddress(profile);
            orderDoc.setBillingAddress(billingAddress);
            orderDoc.setBillingAddressId(billingAddress.getAddressId());
            orderDoc.setShippingAddress(shippingAddress);
            orderDoc.setShippingAddressId(shippingAddress.getAddressId());
        }

        if (agreement != null) {
            orderDoc.setAgreement(agreement);
            orderDoc.setVendorNm(agreement.getVendorNm());
            orderDoc.setAgreementNbr(agreement.getAgreementNbr());
            orderDoc.setVendorDetailAssignedId(agreement.getVndrDetailAsgnId());
            orderDoc.setVendorHeaderGeneratedId(agreement.getVndrHeaderGnrtdId());
            orderDoc.setVendorNm(agreement.getVendorNm());
        }

        orderDoc.setCreationDate(MMUtil.getCurrentTimestamp());
        orderDoc.getDocumentHeader().setDocumentDescription(
                "Stores Order " + orderDoc.getDocumentNumber());
        return orderDoc;
    }

    public void setReturnOrderDAO(ReturnOrderDAO returnOrderDAO) {
        this.returnOrderDAO = returnOrderDAO;
    }
}
