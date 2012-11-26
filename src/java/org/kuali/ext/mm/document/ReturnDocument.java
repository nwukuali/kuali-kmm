package org.kuali.ext.mm.document;


import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.service.BusinessObjectLockingService;
import org.kuali.ext.mm.gl.GeneralLedgerPostable;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.kuali.ext.mm.gl.service.GeneralLedgerProcessor;
import org.kuali.ext.mm.gl.service.TaxLiabilityGeneralLedgerService;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorAddress;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorContract;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorDetail;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.ReturnOrderService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.rules.rule.event.KualiDocumentEvent;
import org.kuali.rice.krad.rules.rule.event.RouteDocumentEvent;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DataDictionaryService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import org.kuali.rice.kew.dto.DocumentRouteStatusChangeDTO;
//import org.kuali.rice.kew.exception.WorkflowException;
//import org.kuali.rice.kim.api.identity.Person;
//import org.kuali.rice.kim.api.services.KimApiServiceLocator;
//import org.kuali.rice.krad.UserSession;
//import org.kuali.rice.kns.rule.event.KualiDocumentEvent;
//import org.kuali.rice.krad.service.BusinessObjectService;
//import org.kuali.rice.kns.service.DataDictionaryService;
//import org.kuali.rice.kns.service.KNSServiceLocator;
//import org.kuali.rice.krad.util.GlobalVariables;
//import org.kuali.rice.core.api.util.type.KualiDecimal;
//import org.kuali.rice.krad.util.ObjectUtils;
//import org.kuali.rice.kew.api.WorkflowDocument;



@Entity
@Table(name = "MM_RETURN_DOC_T")
public class ReturnDocument extends StoresTransactionalDocumentBase implements
        java.io.Serializable, GeneralLedgerPostable, RentalTrackingDocument<ReturnDetail> {

    private static final long serialVersionUID = 4752222899871863312L;

    private FinancialVendorAddress financialVendorAddress = null;

    private FinancialVendorContract financialVendorContract = null;

    private FinancialVendorDetail financialVendorDetail = null;

    private KualiDecimal returnAmt = KualiDecimal.ZERO;

    private KualiDecimal returnTaxAmount = KualiDecimal.ZERO;

    private Integer vendorHeaderGeneratedId;

    private Integer vendorDetailAssignedId;

    private String vendorNm;

    private String customerPrincipalId;

    private String vendorReferenceNumber;

    private String returnDocumentStatusCode;

    private String returnTypeCode = MMConstants.CheckinDocument.CUSTOMER_ORDER_RETURN;

    private Integer orderId;

    private String retrievalBuildingCd;

    private String retrievalBuildingRmNbr;

    private String retrievalInstructionTxt;

    private String billingBuildingCd;

    private boolean vendorCreditInd;

    private boolean vendorReshipInd;

    private boolean vendorDispositionInd;

    private List<ReturnDetail> returnDetails = new ArrayList<ReturnDetail>(0);

    private List<String> relatedFinancialDocNumbers = new ArrayList<String>(0);

    private Profile customerProfile;

    private boolean childDocsGenerated = false;

    private List<FinancialGeneralLedgerPendingEntry> financialGeneralLedgerPendingEntries;

    private String checkinDocumentNumber;

    private List<ReturnDetail> newReturnDetails = new ArrayList<ReturnDetail>(0);

    private List<OrderDocument> dummyOrders = new ArrayList<OrderDocument>(0);

    private String orderDocumentNumber;

    private String returnOrderId;

    private ReturnType returnType;

    private ReturnStatusCode returnStatusCode;

    private ReturnDocument vendorReturnDoc;

    private String customerProfileId;

    private OrderDocument orderDocument;

    private OrderDocument reorderDocument;

    private CheckinDocument checkinDoc;

    private MMDecimal inventoryReturnAmt = MMDecimal.ZERO;

    public KualiDecimal getReturnAmt() {
        return this.returnAmt;
    }

    public void setReturnAmt(KualiDecimal returnAmt) {
        this.returnAmt = returnAmt;
    }

    public boolean isVendorDetailsRequired() {
        return ((this.vendorDetailAssignedId == null || this.vendorHeaderGeneratedId == null || this
                .getFinancialVendorAddress() == null) || (StringUtils
                .isEmpty(this.orderDocumentNumber)));
    }

    public FinancialVendorAddress getFinancialVendorAddress() {

        String addressType = null, campus = null;

        if (this.vendorDetailAssignedId != null && this.vendorHeaderGeneratedId != null) {
            FinancialSystemAdaptorFactory factory = SpringContext
                    .getBean(FinancialSystemAdaptorFactory.class);
            this.financialVendorAddress = factory.getFinancialVendorService()
                    .getVendorDefaultAddress(vendorHeaderGeneratedId, vendorDetailAssignedId,
                            addressType, campus);
        }

        return this.financialVendorAddress;
    }

    public void setFinancialVendorAddress(FinancialVendorAddress financialVendorAddress) {
        this.financialVendorAddress = financialVendorAddress;
    }

    public FinancialVendorContract getFinancialVendorContract() {
        Integer vndrContrGnrtdId = null;
        if (this.vendorDetailAssignedId != null && this.vendorHeaderGeneratedId != null) {
            FinancialSystemAdaptorFactory factory = SpringContext
                    .getBean(FinancialSystemAdaptorFactory.class);
            this.financialVendorContract = factory.getFinancialVendorService().getVendorContract(
                    vndrContrGnrtdId);
        }

        return this.financialVendorContract;
    }

    public void setFinancialVendorContract(FinancialVendorContract financialVendorContract) {
        this.financialVendorContract = financialVendorContract;
    }

    public FinancialVendorDetail getFinancialVendorDetail() {
        if (this.vendorDetailAssignedId != null && this.vendorHeaderGeneratedId != null) {
            FinancialSystemAdaptorFactory factory = SpringContext
                    .getBean(FinancialSystemAdaptorFactory.class);
            this.financialVendorDetail = factory.getFinancialVendorService().getVendorDetail(
                    vendorHeaderGeneratedId, vendorDetailAssignedId);
        }
        return this.financialVendorDetail;
    }

    public void setFinancialVendorDetail(FinancialVendorDetail financialVendorDetail) {

        this.financialVendorDetail = financialVendorDetail;
    }


    public boolean isCurrDocVendorReturnDoc() {
        return !StringUtils.isEmpty(this.returnTypeCode)
                && this.returnTypeCode.equals(MMConstants.CheckinDocument.VENDOR_RETURN_ORDER_LINE);
    }

    public void setCurrDocVendorReturnDoc(boolean currDocVendorReturnDoc) {
        if (currDocVendorReturnDoc)
            this.returnTypeCode = MMConstants.CheckinDocument.VENDOR_RETURN_ORDER_LINE;
    }


    public void setCheckinDocumentNumber(String checkinDocumentNumber) {
        this.checkinDocumentNumber = checkinDocumentNumber;
    }

    public String getCheckinDocumentNumber() {
        return checkinDocumentNumber;
    }

    public List<OrderDocument> getDummyOrders() {

        return this.dummyOrders;
    }

    public void setDummyOrders(List<OrderDocument> dummyOrders) {
        this.dummyOrders = dummyOrders;
    }

    public List<ReturnDetail> getNewReturnDetails() {
        return newReturnDetails;
    }

    public void setNewReturnDetails(List<ReturnDetail> newReturnDetails) {
        this.newReturnDetails = newReturnDetails;
    }

    public void addNewReturnDetail(ReturnDetail rdetail) {
        this.newReturnDetails.add(rdetail);
    }

    public CheckinDocument getCheckinDoc() {
        return checkinDoc;
    }

    public void setCheckinDoc(CheckinDocument checkinDoc) {
        this.checkinDoc = checkinDoc;
    }

    public ReturnDocument getVendorReturnDoc() {
        return vendorReturnDoc;
    }

    public void setVendorReturnDoc(ReturnDocument vendorReturnDoc) {
        this.vendorReturnDoc = vendorReturnDoc;
    }

    public String getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    public String getOrderDocumentNumber() {
        return orderDocumentNumber;
    }

    public void setOrderDocumentNumber(String orderDocumentNumber) {
        this.orderDocumentNumber = orderDocumentNumber;
    }

    public OrderDocument getReorderDocument() {
        return reorderDocument;
    }

    public void setReorderDocument(OrderDocument reorderDocument) {
        this.reorderDocument = reorderDocument;
    }

    public List<ReturnDetail> getReturnLinesForRTV() {

        List<ReturnDetail> lisData = new ArrayList<ReturnDetail>(0);

        for (ReturnDetail rdetail : this.returnDetails) {
            if (ObjectUtils.isNotNull(rdetail.getOrderDetailId())
                    && !StringUtils.isEmpty(rdetail.getDispostitionCode())
                    && rdetail.getDispostitionCode().equalsIgnoreCase(
                            MMConstants.DispositionCode.RETURN_TO_VENDOR)) {
                // if(!(rdetail.getReturnQuantity() == null || rdetail.getReturnQuantity() == 0)){
                lisData.add(rdetail);
            }
        }

        return lisData;
    }

    public OrderDocument getOrderDocument() {
        return orderDocument;
    }

    public void setOrderDocument(OrderDocument orderDocument) {
        this.orderDocument = orderDocument;
    }

    public String getVendorReferenceNumber() {
        return vendorReferenceNumber;
    }

    public void setVendorReferenceNumber(String vendorReferenceNumber) {
        this.vendorReferenceNumber = vendorReferenceNumber;
    }

    public String getCustomerPrincipalId() {
        return customerPrincipalId;
    }

    public void setCustomerPrincipalId(String customerPrincipalId) {
        this.customerPrincipalId = customerPrincipalId;
    }

    public String getReturnDocumentStatusCode() {
        return returnDocumentStatusCode;
    }

    public void setReturnDocumentStatusCode(String returnDocumentStatusCode) {
        this.returnDocumentStatusCode = returnDocumentStatusCode;
    }

    public String getReturnTypeCode() {
        return returnTypeCode;
    }

    public void setReturnTypeCode(String returnTypeCode) {
        this.returnTypeCode = returnTypeCode;
    }

    public ReturnDocument() {
    }

    public Profile getCustomer() {
        return this.customerProfile;
    }

    public void setCustomer(Profile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public ReturnType getReturnType() {
        return this.returnType;
    }

    public void setReturnType(ReturnType returnType) {
        this.returnType = returnType;
    }

    public ReturnStatusCode getReturnStatusCode() {
        return this.returnStatusCode;
    }

    public void setReturnStatusCode(ReturnStatusCode returnStatusCode) {
        this.returnStatusCode = returnStatusCode;
    }

    public Integer getVendorHeaderGeneratedId() {
        return this.vendorHeaderGeneratedId;
    }

    public void setVendorHeaderGeneratedId(Integer vendorHeaderGeneratedId) {
        this.vendorHeaderGeneratedId = vendorHeaderGeneratedId;
    }

    public Integer getVendorDetailAssignedId() {
        return this.vendorDetailAssignedId;
    }

    public void setVendorDetailAssignedId(Integer vendorDetailAssignedId) {
        this.vendorDetailAssignedId = vendorDetailAssignedId;
    }

    public String getVendorNm() {
        return this.vendorNm;
    }

    public void setVendorNm(String vendorNm) {
        this.vendorNm = vendorNm;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setCustomerProfileId(String customerProfileId) {
        this.customerProfileId = customerProfileId;
    }

    public String getCustomerProfileId() {
        return customerProfileId;
    }

    public String getRetrievalBuildingCd() {
        return this.retrievalBuildingCd;
    }

    public void setRetrievalBuildingCd(String retrievalBuildingCd) {
        this.retrievalBuildingCd = retrievalBuildingCd;
    }

    public boolean isChildDocsGenerated() {
        return this.childDocsGenerated;
    }

    public void setChildDocsGenerated(boolean childDocsGenerated) {
        this.childDocsGenerated = childDocsGenerated;
    }

    public String getRetrievalBuildingRmNbr() {
        return this.retrievalBuildingRmNbr;
    }

    public void setRetrievalBuildingRmNbr(String retrievalBuildingRmNbr) {
        this.retrievalBuildingRmNbr = retrievalBuildingRmNbr;
    }

    public String getRetrievalInstructionTxt() {
        return this.retrievalInstructionTxt;
    }

    public void setRetrievalInstructionTxt(String retrievalInstructionTxt) {
        this.retrievalInstructionTxt = retrievalInstructionTxt;
    }

    public String getBillingBuildingCd() {
        return this.billingBuildingCd;
    }

    public void setBillingBuildingCd(String billingBuildingCd) {
        this.billingBuildingCd = billingBuildingCd;
    }

    public boolean isVendorCreditInd() {
        return this.vendorCreditInd;
    }

    public void setVendorCreditInd(boolean vendorCreditInd) {
        this.vendorCreditInd = vendorCreditInd;
    }

    public boolean isVendorReshipInd() {
        return this.vendorReshipInd;
    }

    public void setVendorReshipInd(boolean vendorReshipInd) {
        this.vendorReshipInd = vendorReshipInd;
    }

    public boolean isVendorDispositionInd() {
        return this.vendorDispositionInd;
    }

    public void setVendorDispositionInd(boolean vendorDispositionInd) {
        this.vendorDispositionInd = vendorDispositionInd;
    }

    public List<ReturnDetail> getReturnDetails() {
        return this.returnDetails;
    }
    
    public void setReturnDetails(List<ReturnDetail> returnDetails) {
        this.returnDetails = returnDetails;
    }

    public void addReturnDetail(ReturnDetail rdetail) {
        this.returnDetails.add(rdetail);
    }
    
    /**
     * @see org.kuali.rice.kns.bo.PersistableBusinessObjectBase#buildListOfDeletionAwareLists()
     */
    @Override
    public List buildListOfDeletionAwareLists() {
        List buildListOfDeletionAwareLists = super.buildListOfDeletionAwareLists();
        buildListOfDeletionAwareLists.add(getReturnDetails());
        return buildListOfDeletionAwareLists;
    }

    @Override
    public void prepareForSave(KualiDocumentEvent event) {

        // If there are new returns then add it to the existing collection of
        // Return lines
        if (!MMUtil.isCollectionEmpty(this.newReturnDetails)) {
            this.returnDetails.addAll(this.newReturnDetails);
            this.newReturnDetails.clear();
        }
        
        ReturnOrderService returnService = MMServiceLocator.getReturnOrderService();

        // calculate the return document credit amount
        if (this.getDocumentTypeCode() != null
                && this.getDocumentTypeCode().equalsIgnoreCase(
                        MMConstants.ReturnDocument.VENDOR_RETURN_DOCUMENT)) {
            for (ReturnDetail rdetail : this.getReturnDetails()) {
                if (rdetail.isVendorCreditInd()) {
                    rdetail.setReturnCreditAmt(returnService.getReturnLineCreditAmount(rdetail));
                }
            }
        }
        else {
            for (ReturnDetail rdetail : this.getReturnDetails())
                rdetail.setReturnCreditAmt(returnService.getReturnLineCreditAmount(rdetail));
        }

        List<ReturnDetail> lisData = new ArrayList<ReturnDetail>(0);

        // boolean isDocToBeReviewed = isDocReadyToBeReviewed();

        if (event instanceof RouteDocumentEvent) {
            for (ReturnDetail rd : this.returnDetails) {
                if (!rd.isItemReturned())
                    lisData.add(rd);
            }

            if (!MMUtil.isCollectionEmpty(lisData)) {
                for (ReturnDetail rd : lisData) {
                    this.returnDetails.remove(rd);
                }
            }

        }

    }

    public boolean isReturnToVendorRequired() {

        if (isCurrDocVendorReturnDoc())
            return true;

        for (ReturnDetail rdetail : this.returnDetails) {
            if (!StringUtils.isEmpty(rdetail.getDispostitionCode())
                    && rdetail.getDispostitionCode().equalsIgnoreCase(
                            MMConstants.DispositionCode.RETURN_TO_VENDOR)) {
                // this.returnTypeCode = MMConstants.CheckinDocument.VENDOR_RETURN_ORDER_LINE;
                return true;
            }
        }
        return false;
    }

    public boolean isDocReadyToBeReviewed() {

        boolean result = false;
        if (KRADServiceLocatorWeb.getDocumentService().documentExists(this.documentNumber)) {
            if (this.getDocumentHeader().hasWorkflowDocument()) {
                result = this.getDocumentHeader().getWorkflowDocument().isEnroute();
            }
            else {
                try {
                    result = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(
                            this.documentNumber).getDocumentHeader().getWorkflowDocument()
                            .isEnroute();
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    @Override
    public void doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) {
        super.doRouteStatusChange(statusChangeEvent);
        WorkflowDocument workflowDocument = getDocumentHeader().getWorkflowDocument();
        if (workflowDocument.isProcessed()) {
            applyProcessLocks();
            MMServiceLocator.getReturnOrderService().processReturnDocument(this);
            UserSession curSession = GlobalVariables.getUserSession();
            String name = null;
						Iterator<Person> approversIterator = getPriorApprovers(this.getDocumentHeader().getWorkflowDocument()).iterator();
						if (approversIterator.hasNext())
								name = approversIterator.next().getPrincipalName();

            if (StringUtils.isBlank(name))
                name = KimApiServiceLocator.getIdentityService().getPrincipal(
                        this.getDocumentHeader().getWorkflowDocument().getRoutedByPrincipalId())
                        .getPrincipalName();
            GlobalVariables.setUserSession(new UserSession(name));
            MMServiceLocator.getReturnOrderService().processVendorReturnDocs(this);
            if (ObjectUtils.isNotNull(this.getDummyOrders()))
                MMServiceLocator.getBusinessObjectService().save(this.getDummyOrders());

            if (ObjectUtils.isNotNull(this.getReorderDocument()))
                MMServiceLocator.getBusinessObjectService().save(this.getReorderDocument());
            GlobalVariables.setUserSession(curSession);
            removeProcessLocks();
        }
        updateReturnDocStatus(workflowDocument);
        SpringContext.getBean(GeneralLedgerProcessor.class).doRouteStatusChange(this, getDocumentHeader());
    }

	/**
     * Convenience method to apply the process locks for business objects affected by this document's In-Process state
     */
    private void applyProcessLocks() {
        BusinessObjectLockingService lockingService = SpringContext
                .getBean(BusinessObjectLockingService.class);
        lockingService.deleteLocks(this.getDocumentNumber());
        for (ReturnDetail detail : this.getReturnDetails()) {
            detail.refreshReferenceObject(MMConstants.ReturnDetail.CATALOG_ITEM);
            detail.getCatalogItem().refreshReferenceObject(MMConstants.CatalogItem.STOCK);
            lockingService.createAndSaveLock(documentNumber, detail.getCatalogItem().getStock(),
                    MMConstants.Stock.STOCK_ID);
        }
    }

    /**
     * Convenience method to remove process locks for business objects affected by this document's In-Process state
     */
    private void removeProcessLocks() {
        BusinessObjectLockingService lockingService = SpringContext
                .getBean(BusinessObjectLockingService.class);
        lockingService.deleteLocks(this.getDocumentNumber());
    }

    /**
     * @param workflowDocument
     */
    private void updateReturnDocStatus(WorkflowDocument workflowDocument) {
        String statusCode = MMConstants.OrderStatus.ORDER_LINE_OPEN;
        if (workflowDocument.isEnroute()) {
            statusCode = MMConstants.OrderStatus.REVIEW;
        }
        else if (workflowDocument.isCanceled()) {
            statusCode = MMConstants.OrderStatus.ORDER_LINE_CANCELED;
        }
        else if (workflowDocument.isDisapproved()) {
            statusCode = MMConstants.OrderStatus.DISAPPROVE;
        }
        setReturnDocumentStatusCode(statusCode);
    }

    public boolean isDepartmentCreditRequired() {
        for (ReturnDetail rdetail : this.getReturnDetails()) {
            if (rdetail.isDepartmentCreditInd()
                    && (ObjectUtils.isNotNull(rdetail.getOrderDetailId())))
                return true;
        }
        return false;
    }

    /**
     * This method returns
     * 
     * @return
     */
    public KualiDecimal getReturnDocAmount() {

        MMDecimal result = MMDecimal.ZERO;

        for (ReturnDetail rdetail : this.getReturnDetails()) {
            MMDecimal amt = rdetail.getReturnItemPrice() != null ? rdetail.getReturnItemPrice()
                    .multiply(new MMDecimal(rdetail.getReturnQuantity())) : MMDecimal.ZERO;

            result = result.add(amt);
        }

        return new KualiDecimal(result.doubleValue());
    }


    public String getWarehouseCode() {

        String warehouseCode = null;

        if (this.orderDocument != null)
            warehouseCode = this.orderDocument.getWarehouseCd();

        return warehouseCode;
    }

    public List<FinancialGeneralLedgerPendingEntry> generateGlpeEntries() {
        if (ObjectUtils.isNull(this.getOrderDocument())
                || StringUtils.isEmpty(this.getOrderDocumentNumber()))
            return null;
        HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups = new HashMap<WarehouseGlGroup, WarehouseGlGroup>();
        Warehouse warehouse = SpringContext.getBean(BusinessObjectService.class)
                .findBySinglePrimaryKey(Warehouse.class, getWarehouseCode());
        GeneralLedgerBuilderService generalLedgerBuilderService = SpringContext
                .getBean(GeneralLedgerBuilderService.class);
        TaxLiabilityGeneralLedgerService taxLiaService = SpringContext
                .getBean(TaxLiabilityGeneralLedgerService.class);
        ReturnOrderService returnOrderService = SpringContext.getBean(ReturnOrderService.class);

        if (warehouse == null || !warehouse.isActive()) {
            throw new RuntimeException("Warehouse is not valid.");
        }
        if (this.isDepartmentCreditRequired()) {
            returnOrderService.processBillingGlpes(this, glGroups);
            if (getReturnTaxAmount() != null && getReturnTaxAmount().isNonZero()) {
                taxLiaService.decrementTaxLiability(glGroups, warehouse, getReturnTaxAmount(),
                        "Tax liability");
            }
        }
        if (this.getInventoryReturnAmt() != null && this.getInventoryReturnAmt().isNonZero()) {
            if (!this.isCurrDocVendorReturnDoc()) {
                generalLedgerBuilderService.incrementCostOfGoods(glGroups, warehouse,MMConstants.StockTransReason.STOCK_CUSTOMER_RETURN,
                        getInventoryReturnAmt().kualiDecimalValue(), "Customer returned");
            }
            else {
                generalLedgerBuilderService.decrementResaleItems(glGroups, warehouse,MMConstants.StockTransReason.STOCK_VENDOR_RETURN,
                        getInventoryReturnAmt().kualiDecimalValue(), "Returned to vendor");
            }
        }
        ArrayList<FinancialGeneralLedgerPendingEntry> entries = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        for (WarehouseGlGroup group : glGroups.values()) {
            entries.add(group.getTargetEntry());
        }
        return entries;
    }

    public String getDocumentTypeCode() {
        return SpringContext.getBean(DataDictionaryService.class).getDocumentTypeNameByClass(
                getClass());
    }

    public List<FinancialGeneralLedgerPendingEntry> getFinancialGeneralLedgerPendingEntries() {
        return this.financialGeneralLedgerPendingEntries;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#setFinancialGeneralLedgerPendingEntries(java.util.List)
     */
    public void setFinancialGeneralLedgerPendingEntries(
            List<FinancialGeneralLedgerPendingEntry> entries) {
        this.financialGeneralLedgerPendingEntries = entries;

    }

    /**
     * toStringMapper
     * 
     * @return LinkedHashMap
     */


    public List<FinancialGeneralLedgerPendingEntry> getApprovedGeneralLedgerPendingEntries() {
        return SpringContext.getBean(GeneralLedgerProcessor.class)
                .getApprovedGeneralLedgerPendingEntries(getDocumentNumber());
    }

    /**
     * Gets the returnTaxAmount property
     * 
     * @return Returns the returnTaxAmount
     */
    public KualiDecimal getReturnTaxAmount() {
        return this.returnTaxAmount;
    }

    /**
     * Sets the returnTaxAmount property value
     * 
     * @param returnTaxAmount The returnTaxAmount to set
     */
    public void setReturnTaxAmount(KualiDecimal returnTaxAmount) {
        this.returnTaxAmount = returnTaxAmount;
    }

    /**
     * Gets the relatedFinancialDocNumbers property
     * 
     * @return Returns the relatedFinancialDocNumbers
     */
    public List<String> getRelatedFinancialDocNumbers() {
        if (this.relatedFinancialDocNumbers.isEmpty() && ObjectUtils.isNotNull(this.returnDetails)) {
            for (ReturnDetail detail : this.returnDetails) {
                String creditDocumentNumber = detail.getCreditDocumentNumber();
                if (StringUtils.isNotBlank(creditDocumentNumber)
                        && !this.relatedFinancialDocNumbers.contains(creditDocumentNumber)) {
                    this.relatedFinancialDocNumbers.add(creditDocumentNumber);
                }
            }
        }
        return this.relatedFinancialDocNumbers;
    }

    /**
     * Sets the relatedFinancialDocNumbers property value
     * 
     * @param relatedFinancialDocNumbers The relatedFinancialDocNumbers to set
     */
    public void setRelatedFinancialDocNumbers(List<String> relatedFinancialDocNumbers) {
        this.relatedFinancialDocNumbers = relatedFinancialDocNumbers;
    }

    /**
     * Gets the inventoryReturnAmt property
     * 
     * @return Returns the inventoryReturnAmt
     */
    public MMDecimal getInventoryReturnAmt() {
        return this.inventoryReturnAmt;
    }

    /**
     * Sets the inventoryReturnAmt property value
     * 
     * @param inventoryReturnAmt The inventoryReturnAmt to set
     */
    public void setInventoryReturnAmt(MMDecimal inventoryReturnAmt) {
        this.inventoryReturnAmt = inventoryReturnAmt;
    }

    /**
     * @see org.kuali.ext.mm.document.RentalTrackingDocument#getRentalTrackingDetails()
     */
    public List<ReturnDetail> getRentalTrackingDetails() {
        return this.returnDetails;
    }
    
}
