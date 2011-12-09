/**
 * 
 */
package org.kuali.ext.mm.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.CatalogGroup;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CatalogSubgroup;
import org.kuali.ext.mm.businessobject.CatalogSubgroupItem;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ShopCartDetail;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.businessobject.Stock;
import org.kuali.ext.mm.businessobject.TrueBuyoutDetail;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.document.TrueBuyoutDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialOrganization;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.TrueBuyoutService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.PersonService;
import org.kuali.rice.kns.UserSession;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.DocumentService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.TransactionalServiceUtils;


/**
 * @author schneppd
 *
 */
public class TrueBuyoutServiceImpl implements TrueBuyoutService {

    private BusinessObjectService businessObjectService;
    
    private DocumentService documentService;
    
    /**
     * Gets the businessObjectService property
     * @return Returns the businessObjectService
     */
    public BusinessObjectService getBusinessObjectService() {
        return this.businessObjectService;
    }

    /**
     * Sets the businessObjectService property value
     * @param businessObjectService The businessObjectService to set
     */
    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }
    
    /**
     * Gets the documentService property
     * @return Returns the documentService
     */
    public DocumentService getDocumentService() {
        return this.documentService;
    }

    /**
     * Sets the documentService property value
     * @param documentService The documentService to set
     */
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }
    
    /**
     * @see org.kuali.ext.mm.service.TrueBuyoutService#processTrueBuyoutDocument(org.kuali.ext.mm.document.TrueBuyoutDocument)
     */
    public void processTrueBuyoutDocument(TrueBuyoutDocument document) {
        Map<String, List<TrueBuyoutDetail>> detailsByWarehouse = new HashMap<String, List<TrueBuyoutDetail>>();
        for (TrueBuyoutDetail detail : document.getTrueBuyoutDetails()) {
            String warehouseCode = detail.getCatalog().getWarehouseCd();
            
            Stock stock = createStockFromTrueBuyoutDetail(detail);
            getBusinessObjectService().save(stock);
            
            CatalogItem catalogItem = createCatalogItemFromTrueBuyoutDetail(detail);
            getBusinessObjectService().save(catalogItem);            
            detail.setCatalogItem(catalogItem);
            detail.setCatalogItemId(catalogItem.getCatalogItemId());
            
            assignCatalogItemToTrueBuyoutSubgroup(catalogItem);
            
            if(StringUtils.isNotBlank(warehouseCode)) {
                if (detailsByWarehouse.containsKey(warehouseCode)) {
                    detailsByWarehouse.get(warehouseCode)
                            .add(detail);
                }
                else {
                    List<TrueBuyoutDetail> detailList = new ArrayList<TrueBuyoutDetail>();
                    detailList.add(detail);
                    detailsByWarehouse.put(warehouseCode, detailList);
                }
            }
        }
        Person person = SpringContext.getBean(PersonService.class).getPerson(
                document.getDocumentHeader().getWorkflowDocument().getInitiatorPrincipalId());
        String initiator = person.getPrincipalName();
        UserSession beforeUserSession = GlobalVariables.getUserSession();
        GlobalVariables.clear();            
        GlobalVariables.setUserSession(new UserSession(initiator));
        for(String warehouse : detailsByWarehouse.keySet()) {
            saveOrderDocument(
                    createOrderDocumentForTrueBuyout(document, detailsByWarehouse.get(warehouse)));
        }
        GlobalVariables.setUserSession(beforeUserSession);
    }

    public TrueBuyoutDocument createTrueBuyoutDocument(ShoppingCart cart, Profile customerProfile, List<ShopCartDetail> details) {
        TrueBuyoutDocument doc = null;
        try {
            doc = (TrueBuyoutDocument)documentService.getNewDocument(TrueBuyoutDocument.class);
            doc.getDocumentHeader().setDocumentDescription("True Buyout for " + customerProfile.getPrincipalName());
            doc.setCustomerProfileId(customerProfile.getProfileId());
            doc.refreshReferenceObject(MMConstants.TrueBuyoutDocument.CUSTOMER_PROFILE);
            doc.setShippingAddress(cart.getShippingAddress());
            doc.setShippingAddressId(cart.getShippingAddressId());
            doc.setBillingAddress(cart.getBillingAddress());
            doc.setBillingAddressId(cart.getBillingAddressId());
            doc.setCampusCode(customerProfile.getCampusCode());
            doc.setDeliveryBuildingCode(customerProfile.getDeliveryBuildingCode());
            doc.setDeliveryBuildingRmNbr(customerProfile.getDeliveryBuildingRoomNumber());
            if(StringUtils.isNotBlank(customerProfile.getOrganizationCode())) {
                FinancialSystemAdaptorFactory adaptorFactory = SpringContext.getBean(FinancialSystemAdaptorFactory.class);
                FinancialOrganization org = adaptorFactory.getOrganizationService().getByPrimaryId(customerProfile.getFinacialChartOfAccountsCode(), customerProfile.getOrganizationCode());
                doc.setDeliveryDepartmentName(org.getOrganizationName());
            }
            
            doc.setTrueBuyoutDetails(createTrueBuyoutDetails(doc.getDocumentNumber(), details, customerProfile));
        }
        catch(WorkflowException e) {
            throw new RuntimeException("Error instantiating OrderDocument", e);
        }
        return doc;
    }

    /**
     * @param details
     * @return
     */
    private List<TrueBuyoutDetail> createTrueBuyoutDetails(String documentNumber, List<ShopCartDetail> detailList, Profile customerProfile) {
        List<TrueBuyoutDetail> trueBuyoutList = new ArrayList<TrueBuyoutDetail>();
        int i=0;
        for(ShopCartDetail detail : detailList) {
            TrueBuyoutDetail tbDetail = new TrueBuyoutDetail();
            tbDetail.setStockDistributorNumber(documentNumber + "-" + i++);
            tbDetail.setCatalogId(detail.getCatalogItem().getCatalogId());
            tbDetail.setOrderItemDescription(detail.getShopCartItemDetailDesc());
            tbDetail.setOrderItemQuantity(detail.getShopCartItemQty());
            tbDetail.setOrderItemUnitOfIssueCode(detail.getItemUnitOfIssueCd());
            tbDetail.setWillCall(detail.isWillcallInd());
            
//            tbDetail.setAccount(createDefaultAccount(customerProfile, detail.getFinObjectCode()));
            
            trueBuyoutList.add(tbDetail);
        }
        return trueBuyoutList;
    }
    
//    /**
//     * @param customerProfile
//     * @return
//     */
//    private Accounts createDefaultAccount(Profile customerProfile, String objectCode) {
//        Accounts account = new Accounts();
//        account.setFinCoaCd(customerProfile.getFinacialChartOfAccountsCode());
//        account.setAccountNbr(customerProfile.getAccountNumber());
//        account.setSubAcctNbr(customerProfile.getSubAccountNumber());
//        account.setFinObjectCd(objectCode);
//        account.setProjectCd(customerProfile.getProjectCode());
//        account.setAccountFixedAmt(new KualiDecimal(0.0));
//        account.setAccountPct(new BigDecimal(100.0));
//        account.setAmountType(MMConstants.Accounts.OPTION_PCT);
//        return account;
//    }
    
    /**
     * Creates a CatalogSubgroupItem relationship between the CatalogItem and
     * the True-Buyout CatalogSubgroup. 
     * 
     * @param catalogItem
     */
    private void assignCatalogItemToTrueBuyoutSubgroup(CatalogItem catalogItem) {
        CatalogSubgroup subgroup = getTrueBuyoutCatalogSubgroup();
        CatalogSubgroupItem catalogSubgroupItem = new CatalogSubgroupItem();
        catalogSubgroupItem.setCatalogItemId(catalogItem.getCatalogItemId());
        catalogSubgroupItem.setCatalogSubgroupId(subgroup.getCatalogSubgroupId());
        catalogSubgroupItem.setActive(true);
        catalogSubgroupItem.save();        
    }
    
    /**
     * Retrieves the CatalogSubgroup to be used by the True-Buyout process.
     * If one is not found to exist, it is created, persisted, and then returned. 
     * 
     * @return The default True-Buyout CatalogSubgroup.
     */
    public CatalogSubgroup getTrueBuyoutCatalogSubgroup() {
        Map<String,Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.CatalogSubgroup.CATALOG_GROUP_CD, MMConstants.CatalogGroup.TRUE_BUYOUT_GROUP_CODE);
        Collection<CatalogSubgroup> results = getBusinessObjectService().findMatching(CatalogSubgroup.class, fieldValues);
        CatalogSubgroup existingSubgroup = TransactionalServiceUtils.retrieveFirstAndExhaustIterator(results.iterator());
        
        if(existingSubgroup == null) {
            CatalogGroup existingGroup = getBusinessObjectService().findBySinglePrimaryKey(CatalogGroup.class, MMConstants.CatalogGroup.TRUE_BUYOUT_GROUP_CODE);
            if(existingGroup == null) {
                existingGroup = new CatalogGroup();
                existingGroup.setCatalogGroupCd(MMConstants.CatalogGroup.TRUE_BUYOUT_GROUP_CODE);
                existingGroup.setCatalogGroupNm(MMConstants.CatalogGroup.TRUE_BUYOUT_GROUP_NAME);
                existingGroup.setDisplayableInd(false);
                existingGroup.setActive(true);
                existingGroup.save();   
            }
            
            existingSubgroup = new CatalogSubgroup();
            existingSubgroup.setCatalogGroupCd(MMConstants.CatalogGroup.TRUE_BUYOUT_GROUP_CODE);
            existingSubgroup.setCatalogSubgroupCd(MMConstants.CatalogSubgroup.TRUE_BUYOUT_SUBGROUP_CODE);
            existingSubgroup.setCatalogSubgroupDesc(MMConstants.CatalogSubgroup.TRUE_BUYOUT_SUBGROUP_DESC);
            existingSubgroup.setActive(true);
            existingSubgroup.save();
        }
        return existingSubgroup;
    }

    public CatalogItem getDummyTrueBuyoutCatalogItem(String catalogId) {
        if(catalogId == null)
            return null;
        
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.CatalogItem.CATALOG_ID, catalogId);
        fieldValues.put(MMConstants.CatalogItem.DIST_NUMBER, MMConstants.CatalogItem.GENERIC_DUMMY_DISTRIBUTOR_NBR);

        Collection<CatalogItem> results = getBusinessObjectService().findMatching(CatalogItem.class, fieldValues);
        CatalogItem defaultCatalogItem = TransactionalServiceUtils.retrieveFirstAndExhaustIterator(results.iterator());
        
        if(defaultCatalogItem == null) {
            defaultCatalogItem = createDummyTrueBuyoutCatalogItem(catalogId);            
            getBusinessObjectService().save(defaultCatalogItem);
        }
        
        
        return defaultCatalogItem;
    }
    
    /**
     * Creates a dummy catalog item for use with the initial stages of TrueBuyout orders.
     * 
     * @param trueBuyoutCatalog
     * @return a minimally populated dummy catalog item for the catalog
     */
    private CatalogItem createDummyTrueBuyoutCatalogItem(String trueBuyoutCatalogId) {
        CatalogItem catalogItem = new CatalogItem();
        
        catalogItem.setCatalogId(trueBuyoutCatalogId);
        catalogItem.setDistributorNbr(MMConstants.CatalogItem.GENERIC_DUMMY_DISTRIBUTOR_NBR);
        catalogItem.setCatalogUnitOfIssueCd(MMConstants.CatalogItem.GENERIC_DUMMY_UNIT_OF_ISSUE);
        catalogItem.setCatalogDesc(MMConstants.CatalogItem.GENERIC_DUMMY_DESCRIPTION);
        catalogItem.setActive(true);
        catalogItem.setDisplayableInd(false);
        catalogItem.setStock(null);
        
        return catalogItem;
    }

    private Stock createStockFromTrueBuyoutDetail(TrueBuyoutDetail detail) {
        Stock stock = new Stock();            
        stock.setStockDistributorNbr(detail.getStockDistributorNumber());
        stock.setStockDesc(detail.getOrderItemDescription());
        stock.setStockTypeCode(detail.getStockTypeCode());
        stock.setRentalObjectCode(detail.getRentalObjectCode());
        stock.setActive(true);
        stock.setTaxableInd(true);
        stock.setMaximumOrderQty(new KualiDecimal(0.0));
        stock.setMinimumOrderQty(new KualiDecimal(0.0));
        stock.setReorderPointQty(new KualiDecimal(0.0));
        stock.setSalesUnitOfIssueCd(detail.getOrderItemUnitOfIssueCode());
        stock.setSalesUnitOfIssueRt(new MMDecimal(1.0));
        stock.setBuyUnitOfIssueCd(detail.getOrderItemUnitOfIssueCode());
        stock.setBuyUnitOfIssueRt(new MMDecimal(1.0));
        stock.setAgreementNbr(detail.getAgreementNumber());
        stock.setStockCreateDt(KNSServiceLocator.getDateTimeService().getCurrentSqlDate());
        return stock;
    }
    
    private CatalogItem createCatalogItemFromTrueBuyoutDetail(TrueBuyoutDetail detail) {
        Stock stock = MMServiceLocator.getStockService().getStockByDistributorNumber(detail.getStockDistributorNumber());
        
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setStockId(stock.getStockId());
        catalogItem.setStock(stock);
        catalogItem.setDistributorNbr(detail.getStockDistributorNumber());
        catalogItem.setCatalogDesc(detail.getOrderItemDescription());
        catalogItem.setCatalogUnitOfIssueCd(detail.getOrderItemUnitOfIssueCode());
        catalogItem.setCatalogPrc(detail.getOrderItemCost());
        catalogItem.setActive(true);
        catalogItem.setDisplayableInd(false);
        catalogItem.setCatalogId(detail.getCatalogId());
        catalogItem.setTaxableInd(true);
        
        return catalogItem;
    }
    
    public void inactivateTrueBuyoutStock(OrderDocument document) {
        for(OrderDetail detail : document.getOrderDetails()) {
            Stock stock = detail.getCatalogItem().getStock();
            stock.setActive(false);
            stock.save();
        }
    }
    
    public void finalizeTrueBuyoutStock(OrderDocument document) {
        for(OrderDetail detail : document.getOrderDetails()) {
            Stock stock = detail.getCatalogItem().getStock();
            stock.setMaximumOrderQty(new KualiDecimal(detail.getOrderItemQty()));
            stock.setMinimumOrderQty(new KualiDecimal(detail.getOrderItemQty()));
            stock.setReorderPointQty(new KualiDecimal(detail.getOrderItemQty()));
            stock.save();
        }
    }
    
    public OrderDocument createOrderDocumentForTrueBuyout(TrueBuyoutDocument trueBuyout, List<TrueBuyoutDetail> buyoutDetails) {
        
        OrderDocument doc = null;
        try {
            doc = (OrderDocument)documentService.getNewDocument(OrderDocument.class);

            doc.setOrderTypeCode(MMConstants.OrderType.TRUE_BUYOUT);
            doc.setOrderId(KNSServiceLocator.getSequenceAccessorService().getNextAvailableSequenceNumber(ShopCartConstants.Sequence.ORDER_ID_SEQ));
            doc.getDocumentHeader().setDocumentDescription(String.valueOf(doc.getOrderId()));

            doc.setOrderStatusCd(MMConstants.OrderStatus.INITIATED);
            doc.setCustomerProfileId(trueBuyout.getCustomerProfileId());
            doc.refreshReferenceObject(MMConstants.OrderDocument.CUSTOMER_PROFILE);
            doc.setCampusCd(trueBuyout.getCampusCode());            
            doc.setWarehouseCd(buyoutDetails.get(0).getCatalog().getWarehouseCd());
            doc.setShippingAddress(trueBuyout.getShippingAddress());
            doc.setShippingAddressId(trueBuyout.getShippingAddressId());
            doc.setBillingAddress(trueBuyout.getBillingAddress());
            doc.setBillingAddressId(trueBuyout.getBillingAddressId());
            doc.setDeliveryBuildingCd(trueBuyout.getDeliveryBuildingCode());
            doc.setDeliveryBuildingRmNbr(trueBuyout.getDeliveryBuildingRmNbr());
            doc.setDeliveryDepartmentNm(trueBuyout.getDeliveryDepartmentName());

            doc.setCreationDate(KNSServiceLocator.getDateTimeService().getCurrentTimestamp());
            doc.setProfileTypeCode(MMConstants.OrderDocument.PROFILE_TYPE_INTERNAL);         
            
            List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
            int i=0;
            for(TrueBuyoutDetail detail : buyoutDetails) {
                orderDetails.add(createOrderDetailForTrueBuyout(trueBuyout, detail, ++i));
            }
            doc.setOrderDetails(orderDetails);
            
//            doc.getAccounts().clear();
//            Accounts account = new Accounts(buyoutDetails.get(0).getAccount());
//            account.setAccountFixedAmt(doc.getOrderTotalAmount());
//            doc.getAccounts().add(account);
        }
        catch(WorkflowException e) {
            throw new RuntimeException("Error instantiating OrderDocument", e);
        }
        return doc;
    }

    /**
     * @param document
     * @param detail
     * @param detailLineNumber
     * @return
     */
    private OrderDetail createOrderDetailForTrueBuyout(TrueBuyoutDocument document, TrueBuyoutDetail detail, Integer detailLineNumber) {
        CatalogItem catalogItem = detail.getCatalogItem();
        MMDecimal itemPrice = MMServiceLocator.getMarkupService().applyMarkup(detail.getMarkup(), catalogItem);
        
        MMDecimal tax = new MMDecimal(0.0);
        if(detail.getCatalogItem().isTaxableInd()) {
            tax = MMServiceLocator.getCatalogService()
                    .computeCatalogItemTax(catalogItem, itemPrice, 
                            document.getShippingAddress(), detail.isWillCall());
        }
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderStatusCd(MMConstants.OrderStatus.INITIATED);
        orderDetail.setCatalogItem(catalogItem);
        orderDetail.setCatalogItemId(catalogItem.getCatalogItemId());
        orderDetail.setItemLineNumber(detailLineNumber);
        orderDetail.setDistributorNbr(detail.getStockDistributorNumber());
        orderDetail.setOrderItemQty(detail.getOrderItemQuantity());
        orderDetail.setOrderItemDetailDesc(detail.getOrderItemDescription());
        orderDetail.setOrderItemCostAmt(detail.getOrderItemCost());
        orderDetail.setOrderItemPriceAmt(itemPrice);
        orderDetail.setOrderItemMarkupAmt(itemPrice.subtract(detail.getOrderItemCost()));
        orderDetail.setOrderItemTaxAmt(tax);        
        orderDetail.setOrderItemAdditionalCostAmt(new MMDecimal(0.0));
        orderDetail.setStockUnitOfIssueCd(detail.getOrderItemUnitOfIssueCode());
        orderDetail.setWillCall(detail.isWillCall());
//        orderDetail.getAccounts().clear();
//        Accounts account = new Accounts(detail.getAccount());
//        account.setAccountFixedAmt(orderDetail.getDetailTotal());
//        account.setAmountType(MMConstants.Accounts.OPTION_FXD);
//        orderDetail.getAccounts().add(account);
                
        return orderDetail;
    }

    public void routeTrueBuyoutDocument(TrueBuyoutDocument document) {
        try {
            documentService.routeDocument(document, "", new ArrayList<Object>());
        }
        catch (WorkflowException e) {
            throw new RuntimeException("Error Routing OrderDocument", e);
        }
    }
    
    private void routeOrderDocument(OrderDocument document) {
        try {
            documentService.routeDocument(document, "", new ArrayList<Object>());
        }
        catch (WorkflowException e) {
            throw new RuntimeException("Error Routing OrderDocument", e);
        }
    }
    
    public void saveOrderDocument(OrderDocument document) {
        try {
            documentService.saveDocument(document);
        }
        catch (WorkflowException e) {
            throw new RuntimeException("Error saving OrderDocument", e);
        }
    }
    
}
