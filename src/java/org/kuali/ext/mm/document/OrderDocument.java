package org.kuali.ext.mm.document;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Agreement;
import org.kuali.ext.mm.businessobject.OrderAutoLimit;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.OrderDetailForReturns;
import org.kuali.ext.mm.businessobject.OrderStatus;
import org.kuali.ext.mm.businessobject.OrderType;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.RecurringOrder;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.businessobject.StoresTransactionalDocumentBase;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.MMBusinessObjectDao;
import org.kuali.ext.mm.gl.service.GeneralLedgerProcessor;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialAccount;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.ext.mm.service.AddressService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.OrderService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kew.dto.DocumentRouteStatusChangeDTO;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.ParameterService;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.util.TransactionalServiceUtils;
import org.kuali.rice.kns.workflow.service.KualiWorkflowDocument;


/**
 * OrderDoc generated by hbm2java
 */
@Entity
@Table(name = "MM_ORDER_DOC_T")
public class OrderDocument extends StoresTransactionalDocumentBase {
    private static final String REQUIRES_ORGANIZATION_REVIEW = "RequiresOrganizationReview";
    private static final Logger LOG = Logger.getLogger(OrderDocument.class);
    private static final long serialVersionUID = 4197927039662626260L;
    public static final String HAS_ACCOUNTING_LINES = "HasAccountingLines";
    public static final String AMOUNT_REQUIRES_SEPARATION_OF_DUTIES_REVIEW_SPLIT = "AmountRequiresSeparationOfDutiesReview";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_CART_DOC_NBR", nullable = false)
    private ShoppingCart shoppingCart;

    private String agreementNbr;

    private Agreement agreement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_DOC_STATUS_CD", nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_TYPE_CD", nullable = false)
    private OrderType orderType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BILLING_ADDRESS_ID")
    private Address billingAddress;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "SHIPPING_ADDRESS_ID")
    private Address shippingAddress;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_PROFILE_ID")
    private Profile customerProfile;

    private List<OrderDetail> orderDetailsForReorder = new ArrayList<OrderDetail>(0);
    // BEGIN OJB
    @Column(name = "ORDER_DOC_STATUS_CD")
    private String orderStatusCd;
    @Column(name = "BILLING_ADDRESS_ID", length = 36)
    private String billingAddressId;
    @Column(name = "SHIPPING_ADDRESS_ID", length = 36)
    private String shippingAddressId;
    @Column(name = "SHOP_CART_DOC_NBR")
    private String shoppingCartId;
    @Column(name = "CUSTOMER_PROFILE_ID", nullable = false, length = 100)
    private String customerProfileId;
    // END OJB

    private String profileTypeCode;

    protected Timestamp creationDate;

    @Column(name = "ORDER_TYPE_CD")
    private String orderTypeCode;

    @Column(name = "ORDER_ID", unique = true, nullable = false, precision = 8, scale = 0)
    private Long orderId;

    @Column(name = "RECURRING_ORDER_ID", unique = true, nullable = false, precision = 8, scale = 0)
    private Integer recurringOrderId;

    private RecurringOrder recurringOrder;

    @Column(name = "RECURRING_ORDER_IND", length = 1)
    private boolean recurringOrderInd;

    @Column(name = "VENDOR_HEADER_GENERATED_ID", precision = 10, scale = 0)
    private Integer vendorHeaderGeneratedId;

    @Column(name = "VENDOR_DETAIL_ASSIGNED_ID", precision = 10, scale = 0)
    private Integer vendorDetailAssignedId;

    @Column(name = "VENDOR_NM", length = 45)
    private String vendorNm;

    @Column(name = "WAREHOUSE_CD", nullable = false, length = 2)
    private String warehouseCd;

    private Warehouse warehouse = null;

    @Column(name = "CAMPUS_CD", nullable = false, length = 2)
    private String campusCd;

    @Column(name = "DELIVERY_BUILDING_CD", length = 4)
    private String deliveryBuildingCd;

    @Column(name = "DELIVERY_BUILDING_RM_NBR", length = 8)
    private String deliveryBuildingRmNbr;

    @Column(name = "DELIVERY_DEPARTMENT_NM", length = 45)
    private String deliveryDepartmentNm;

    @Column(name = "DELIVERY_INSTRUCTION_TXT")
    private String deliveryInstructionTxt;

    @Column(name = "REQS_ID", precision = 8, scale = 0)
    private Integer reqsId;

    @Column(name = "AR_ID", precision = 8, scale = 0)
    private Integer arId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderDocNbr")
    private List<Accounts> accounts = new ArrayList<Accounts>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderDoc")
    private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>(0);

    private List<OrderDetailForReturns> orderDetailForReturns = new ArrayList<OrderDetailForReturns>(
        0);

    public void setOrderDetailForReturns(List<OrderDetailForReturns> orderDetailForReturns) {
        this.orderDetailForReturns = orderDetailForReturns;
    }

    private List<CheckinDocument> checkinDocs = new ArrayList<CheckinDocument>(0);

    private List<ReturnDocument> returnDocs = new ArrayList<ReturnDocument>(0);

    private List<FinancialGeneralLedgerPendingEntry> financialGeneralLedgerPendingEntries = new ArrayList<FinancialGeneralLedgerPendingEntry>(
        0);

    public OrderDocument() {
    }

    public String getOrderTypeCode() {
        return orderTypeCode;
    }

    public void setOrderTypeCode(String orderTypeCode) {
        this.orderTypeCode = orderTypeCode;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setCustomerProfile(Profile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public Profile getCustomerProfile() {
        return customerProfile;
    }

    public void setProfileTypeCode(String profileTypeCode) {
        this.profileTypeCode = profileTypeCode;
    }

    public String getProfileTypeCode() {
        return profileTypeCode;
    }

    // BEGIN OJB
    public void setOrderStatusCd(String orderStatusCd) {
        this.orderStatusCd = orderStatusCd;
    }

    public String getOrderStatusCd() {
        return orderStatusCd;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public String getBillingAddressId() {
        return this.billingAddressId;
    }

    public void setBillingAddressId(String billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public String getShippingAddressId() {
        return this.shippingAddressId;
    }

    public void setShippingAddressId(String shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    // END OJB
    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public boolean isRecurringOrderInd() {
        return this.recurringOrderInd;
    }

    public void setRecurringOrderInd(boolean recurringOrderInd) {
        this.recurringOrderInd = recurringOrderInd;
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

    public String getCustomerProfileId() {
        return this.customerProfileId;
    }

    public void setCustomerProfileId(String customerProfileId) {
        this.customerProfileId = customerProfileId;
    }

    public String getWarehouseCd() {
        return this.warehouseCd;
    }

    public void setWarehouseCd(String warehouseCd) {
        this.warehouseCd = warehouseCd;
    }

    public String getCampusCd() {
        return this.campusCd;
    }

    public void setCampusCd(String campusCd) {
        this.campusCd = campusCd;
    }

    public String getDeliveryBuildingCd() {
        return this.deliveryBuildingCd;
    }

    public void setDeliveryBuildingCd(String deliveryBuildingCd) {
        this.deliveryBuildingCd = deliveryBuildingCd;
    }

    public String getDeliveryBuildingRmNbr() {
        return this.deliveryBuildingRmNbr;
    }

    public void setDeliveryBuildingRmNbr(String deliveryBuildingRmNbr) {
        this.deliveryBuildingRmNbr = deliveryBuildingRmNbr;
    }

    public String getDeliveryDepartmentNm() {
        return this.deliveryDepartmentNm;
    }

    public void setDeliveryDepartmentNm(String deliveryDepartmentNm) {
        this.deliveryDepartmentNm = deliveryDepartmentNm;
    }

    public String getDeliveryInstructionTxt() {
        return this.deliveryInstructionTxt;
    }

    public void setDeliveryInstructionTxt(String deliveryInstructionTxt) {
        this.deliveryInstructionTxt = deliveryInstructionTxt;
    }

    public Integer getReqsId() {
        return this.reqsId;
    }

    public void setReqsId(Integer reqsId) {
        this.reqsId = reqsId;
    }

    public Integer getArId() {
        return this.arId;
    }

    public void setArId(Integer arId) {
        this.arId = arId;
    }

    public List<ReturnDocument> getReturnDocs() {
        return returnDocs;
    }

    // public Set<CheckinDoc> getCheckinDocs() {
    // return this.checkinDocs;
    // }
    //
    // public void setCheckinDocs(Set<CheckinDoc> checkinDocs) {
    // this.checkinDocs = checkinDocs;
    // }

    public List<OrderDetail> getOrderDetails() {
        return this.orderDetails;
    }

    public List<OrderDetailForReturns> getOrderDetailForReturns() {
        return this.orderDetailForReturns;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Integer getRecurringOrderId() {
        return recurringOrderId;
    }

    public void setRecurringOrderId(Integer recurringOrderId) {
        this.recurringOrderId = recurringOrderId;
    }

    public void setRecurringOrder(RecurringOrder recurringOrder) {
        this.recurringOrder = recurringOrder;
    }

    public RecurringOrder getRecurringOrder() {
        return recurringOrder;
    }

    public List<CheckinDocument> getCheckinDocs() {
        return checkinDocs;
    }

    public void setCheckinDocs(List<CheckinDocument> checkinDocs) {
        this.checkinDocs = checkinDocs;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setAccounts(List<Accounts> accounts) {
        this.accounts = accounts;
    }

    public List<Accounts> getAccounts() {
        return accounts;
    }

    public KualiDecimal getDisplaySubTotal() {
        return SpringContext.getBean(OrderService.class).computeSubTotal(getOrderDetails());
    }

    public KualiDecimal getTax() {
        return new KualiDecimal(SpringContext.getBean(OrderService.class).computeTaxTotal(
                getOrderDetails()));
    }

    public KualiDecimal getDisplayTotal() {
        return SpringContext.getBean(OrderService.class).computeTotal(getOrderDetails());
    }

    public int getOrderDetailsSize() {
        return ObjectUtils.isNotNull(this.getOrderDetails()) ? this.getOrderDetails().size() : 0;
    }

    @Override
    public String getDocumentTitle() {
        if (this.documentHeader.hasWorkflowDocument()) {
            return super.getDocumentTitle();
        }
        return StringUtils.EMPTY;
    }

    public String getDocumentInitiator() {

        String princplId = null;

        if (this.documentHeader.hasWorkflowDocument())
            princplId = this.documentHeader.getWorkflowDocument().getInitiatorPrincipalId();
        else
            princplId = getDocument().getDocumentHeader().getWorkflowDocument()
                    .getInitiatorPrincipalId();

        Person person = KIMServiceLocator.getPersonService().getPerson(princplId);

        if (person != null)
            return person.getFirstName() + "  " + person.getLastName();

        KimPrincipalInfo kinfo = KIMServiceLocator.getIdentityManagementService().getPrincipal(
                princplId);

        if (kinfo != null)
            return kinfo.getPrincipalName();

        return princplId;

    }

    public Document getDocument() {
        Document doc = null;
        try {
            doc = KNSServiceLocator.getDocumentService().getByDocumentHeaderId(this.documentNumber);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return doc;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public String getAgreementNbr() {
        return this.agreementNbr;
    }

    public void setAgreementNbr(String agreementNbr) {
        this.agreementNbr = agreementNbr;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public ReturnDocument getCurrentActiveReturnDoc() {

        List<ReturnDocument> docs = this.returnDocs;

        if (MMUtil.isCollectionEmpty(docs)) {
            return null;
        }
        try {
            for (ReturnDocument cdoc : docs) {
                if (KNSServiceLocator.getDocumentService().documentExists(cdoc.getDocumentNumber())) {
                    if (!cdoc.getDocumentHeader().hasWorkflowDocument()) {
                        cdoc = (ReturnDocument) KNSServiceLocator.getDocumentService()
                                .getByDocumentHeaderId(cdoc.getDocumentNumber());
                    }

                    if (cdoc.getDocumentHeader().hasWorkflowDocument()
                            && cdoc.getDocumentHeader().getWorkflowDocument().stateIsSaved()
                            || cdoc.getDocumentHeader().getWorkflowDocument().stateIsInitiated())
                        return cdoc;
                }
            }
        }
        catch (WorkflowException we) {
            throw new RuntimeException(we);
        }
        return null;
    }

    public boolean isAllReturnDocsFinal() {

        List<ReturnDocument> docs = this.returnDocs;

        if (MMUtil.isCollectionEmpty(docs)) {
            return true;
        }
        try {
            for (ReturnDocument cdoc : docs) {
                Document doc = null;
                doc = KNSServiceLocator.getDocumentService().getByDocumentHeaderId(
                        cdoc.getDocumentNumber());
                if (doc.getDocumentHeader().hasWorkflowDocument()) {
                    if (!doc.getDocumentHeader().getWorkflowDocument().stateIsFinal())
                        return false;
                }

            }
        }
        catch (WorkflowException we) {
            throw new RuntimeException(we);
        }
        return true;
    }

    public List<OrderDetail> getOrderDetailsForReorder() {
        return this.orderDetailsForReorder;
    }

    public void setOrderDetailsForReorder(List<OrderDetail> orderDetailsForReorder) {
        this.orderDetailsForReorder = orderDetailsForReorder;
    }

    public void addOrderDetailsForReorder(OrderDetail data) {
        this.orderDetailsForReorder.add(data);
    }

    /**
     * @see org.kuali.rice.kns.document.DocumentBase#prepareForSave()
     */
    @Override
    public void prepareForSave() {
        super.prepareForSave();
        if (ObjectUtils.isNotNull(getOrderDetails())) {
            for (OrderDetail detail : getOrderDetails()) {
                if (ObjectUtils.isNotNull(detail.getCapitalAssetInformation())
                        && !detail.getCapitalAssetInformation().isEmpty())
                    SpringContext.getBean(BusinessObjectService.class).save(
                            detail.getCapitalAssetInformation());
            }
        }
    }

    /**
     * @see org.kuali.rice.kns.document.Document#doRouteStatusChange(org.kuali.rice.kew.dto.DocumentRouteStatusChangeDTO)
     */
    @Override
    public void doRouteStatusChange(DocumentRouteStatusChangeDTO statusChangeEvent) {
        super.doRouteStatusChange(statusChangeEvent);
        KualiWorkflowDocument workflowDocument = getDocumentHeader().getWorkflowDocument();
        OrderService orderService = SpringContext.getBean(OrderService.class);
        if (workflowDocument.stateIsDisapproved()) {
            orderService.setOrderDocumentStatus(this, MMConstants.OrderStatus.DISAPPROVE);
            if(MMConstants.OrderType.TRUE_BUYOUT.equals(this.getOrderTypeCode()))
                MMServiceLocator.getTrueBuyoutService().inactivateTrueBuyoutStock(this);
        }
        else if (workflowDocument.stateIsCanceled()) {
            orderService.setOrderDocumentStatus(this, MMConstants.OrderStatus.ORDER_LINE_CANCELED);
            if(MMConstants.OrderType.TRUE_BUYOUT.equals(this.getOrderTypeCode()))
                MMServiceLocator.getTrueBuyoutService().inactivateTrueBuyoutStock(this);
        }
        else if (workflowDocument.stateIsEnroute()) {
            if (MMConstants.OrderStatus.INITIATED.equals(this.getOrderStatusCd())) {
                orderService.setOrderDocumentStatus(this, MMConstants.OrderStatus.REVIEW);
            }
        }
        else if (workflowDocument.stateIsProcessed()) {
            orderService.processOrderDocument(this);
        }
    }

    /**
     * returns the number of order lines in the order document
     * 
     * @return
     */
    public int getOrderDetailsCountForReorder() {
        if (!MMUtil.isCollectionEmpty(this.orderDetails))
            return this.orderDetails.size();

        return 0;
    }

    /**
     * toStringMapper
     * 
     * @return LinkedHashMap
     */
    @Override
    public LinkedHashMap<String, String> toStringMapper() {
        LinkedHashMap<String, String> propMap = new LinkedHashMap<String, String>();
        propMap.put("WorksheetDocNumber", "WorksheetDocNumber");
        return propMap;
    }

    /**
     * This method will create a new warehouse order document that can be passed to financial system without mark up prices and
     * using warehouse accounting information
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public OrderDocument generateFinancialOrder() {
        OrderDocument warehouseOrder = new OrderDocument();
        warehouseOrder.setDocumentNumber(this.getDocumentNumber());
        warehouseOrder.setDocumentHeader(this.getDocumentHeader());
        WarehouseAccounts warehouseExpenseAcct = this.warehouse.getCostOfGoodsAccount();
        List<Accounts> deptAccounts = this.getAccounts();
        for (Accounts source : deptAccounts) {
            Accounts target = new Accounts();
            target.setAccountNbr(warehouseExpenseAcct.getAccountNbr());
            target.setFinCoaCd(warehouseExpenseAcct.getFinCoaCd());
            target.setFinObjectCd(warehouseExpenseAcct.getFinObjectCd());
            target.setFinSubObjectCd(warehouseExpenseAcct.getFinSubObjCd());
            target.setProjectCd(warehouseExpenseAcct.getProjectCd());
            target.setSubAcctNbr(warehouseExpenseAcct.getSubAcctNbr());
            target.setAccountPct(source.getAccountPct());
            target.setAmountType(source.getAmountType());
            warehouseOrder.getAccounts().add(target);
        }
        warehouseOrder.setAgreementNbr(this.getAgreementNbr());
        warehouseOrder.setAgreement(this.getAgreement());
        warehouseOrder.setArId(this.getArId());

        Address billAddress = SpringContext.getBean(AddressService.class).getBillingAddress(
                this.warehouse.getBillingProfile());
        if (billAddress != null) {
            warehouseOrder.setBillingAddressId(billAddress.getAddressId());
            warehouseOrder.setBillingAddress(billAddress);
        }
        Profile billingProfile = warehouse.getBillingProfile();
        warehouseOrder.setCampusCd(billingProfile.getCampusCode());
        warehouseOrder.setCheckinDocs(this.getCheckinDocs());
        warehouseOrder.setCreationDate(this.getCreationDate());
        warehouseOrder.setCustomerProfile(this.getCustomerProfile());
        warehouseOrder.setCustomerProfileId(this.getCustomerProfileId());
        warehouseOrder
                .setDeliveryBuildingCd(StringUtils.isBlank(this.getDeliveryBuildingCd()) ? billingProfile
                        .getDeliveryBuildingCode()
                        : this.getDeliveryBuildingCd());
        warehouseOrder.setDeliveryBuildingRmNbr(StringUtils
                .isBlank(this.getDeliveryBuildingRmNbr()) ? billingProfile
                .getDeliveryBuildingRoomNumber() : this.getDeliveryBuildingCd());
        warehouseOrder.setDeliveryDepartmentNm(this.getDeliveryDepartmentNm());
        warehouseOrder.setDeliveryInstructionTxt(this.getDeliveryInstructionTxt());

        List<OrderDetail> warehouseOrderDetails = new ArrayList<OrderDetail>();
        for (OrderDetail orderDetail : getOrderDetails()) {
            if (MMConstants.OrderStatus.ORDER_LINE_OPEN.equals(orderDetail.getOrderStatusCd())) {
                OrderDetail detail = (OrderDetail) ObjectUtils.deepCopy(orderDetail);
                if (detail.getAccounts() != null && !detail.getAccounts().isEmpty()) {
                    BigDecimal acctPct = BigDecimal.ZERO;
                    KualiDecimal acctFixedAmt = KualiDecimal.ZERO;
                    for (Accounts account : detail.getAccounts()) {
                        acctPct = acctPct.add(account.getAccountPct() == null ? BigDecimal.ZERO
                                : account.getAccountPct());
                        acctFixedAmt = acctFixedAmt
                                .add(account.getAccountFixedAmt() == null ? KualiDecimal.ZERO
                                        : account.getAccountFixedAmt());
                    }
                    Accounts target = new Accounts();
                    target.setAccountNbr(warehouseExpenseAcct.getAccountNbr());
                    target.setFinCoaCd(warehouseExpenseAcct.getFinCoaCd());
                    target.setFinObjectCd(warehouseExpenseAcct.getFinObjectCd());
                    target.setFinSubObjectCd(warehouseExpenseAcct.getFinSubObjCd());
                    target.setProjectCd(warehouseExpenseAcct.getProjectCd());
                    target.setSubAcctNbr(warehouseExpenseAcct.getSubAcctNbr());
                    target.setAccountPct(acctPct);
                    target.setAccountFixedAmt(acctFixedAmt);
                    target.setLineNbr(orderDetail.getShopCartLineNbr());
                    target.setOrderDetail(orderDetail);
                    target.setOrderDetailId(orderDetail.getOrderDetailId());
                    target.setOrderDocument(warehouseOrder);
                    target.setOrderDocumentNumber(warehouseOrder.getDocumentNumber());
                    detail.getAccounts().clear();
                    detail.getAccounts().add(target);
                }
                warehouseOrderDetails.add(detail);
            }
        }
        warehouseOrder.setOrderDetails(warehouseOrderDetails);
        warehouseOrder.setOrderId(this.getOrderId());
        warehouseOrder.setOrderStatus(this.getOrderStatus());
        warehouseOrder.setOrderStatusCd(this.getOrderStatusCd());
        warehouseOrder.setOrderType(this.getOrderType());
        warehouseOrder.setOrderTypeCode(this.getOrderTypeCode());
        warehouseOrder.setRecurringOrderId(this.getRecurringOrderId());
        warehouseOrder.setRecurringOrderInd(this.isRecurringOrderInd());
        warehouseOrder.setReqsId(this.getReqsId());
        warehouseOrder.setShippingAddress(this.getShippingAddress());
        warehouseOrder.setShippingAddressId(this.getShippingAddressId());
        warehouseOrder.setShoppingCart(this.getShoppingCart());
        warehouseOrder.setShoppingCartId(this.getShoppingCartId());
        warehouseOrder.setVendorDetailAssignedId(this.getVendorDetailAssignedId());
        warehouseOrder.setVendorHeaderGeneratedId(this.getVendorHeaderGeneratedId());
        warehouseOrder.setVendorNm(this.getVendorNm());
        warehouseOrder.setWarehouse(this.getWarehouse());
        warehouseOrder.setWarehouseCd(this.getWarehouseCd());
        return warehouseOrder;
    }

    /**
     * Gets the agreement property
     * 
     * @return Returns the agreement
     */
    public Agreement getAgreement() {
        return this.agreement;
    }

    /**
     * Sets the agreement property value
     * 
     * @param agreement The agreement to set
     */
    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List buildListOfDeletionAwareLists() {
        List buildListOfDeletionAwareLists = super.buildListOfDeletionAwareLists();
        buildListOfDeletionAwareLists.add(getAccounts());
        for(OrderDetail detail : getOrderDetails())
            buildListOfDeletionAwareLists.add(detail.getAccounts());
        return buildListOfDeletionAwareLists;
    }

    @Override
    public boolean answerSplitNodeQuestion(String nodeName) {
        if (nodeName.equals(REQUIRES_ORGANIZATION_REVIEW)) {
            return isOrganizationReviewRequired();
        }
        if (nodeName.equals(HAS_ACCOUNTING_LINES))
            return !isMissingAccountingLines();
        if (nodeName.equals(AMOUNT_REQUIRES_SEPARATION_OF_DUTIES_REVIEW_SPLIT))
            return isSeparationOfDutiesReviewRequired();
        throw new UnsupportedOperationException(
            "Cannot answer split question for this node you call \"" + nodeName + "\"");
    }

    /**
     * @return
     */
    protected boolean isOrganizationReviewRequired() {
        if (MMConstants.OrderDocument.PROFILE_TYPE_INTERNAL.equals(getProfileTypeCode())) {
            return doesAccountExceedsAutoLimit();
        }
        return false;
    }

    /**
     * @return
     */
    private boolean doesAccountExceedsAutoLimit() {
        // Default limit amount
        KualiDecimal defaultBuyLimitAmount = new KualiDecimal(500);
        if (SpringContext.getBean(ParameterService.class).parameterExists(
                MMConstants.Parameters.OrderDocument.class,
                MMConstants.Parameters.MM_ORDER_APPROVALS_BEGIN_AT_AMOUNT)) {
            defaultBuyLimitAmount = new KualiDecimal(SpringContext.getBean(ParameterService.class)
                    .getParameterValue(MMConstants.Parameters.OrderDocument.class,
                            MMConstants.Parameters.MM_ORDER_APPROVALS_BEGIN_AT_AMOUNT));
        }

        // Total order amount
        KualiDecimal computeTotal = SpringContext.getBean(OrderService.class).computeTotal(
                getOrderDetails());

        // Compute Account level total amounts
        HashMap<String, KualiDecimal> accountAmtMap = new HashMap<String, KualiDecimal>();
        for (OrderDetail orderDetail : this.orderDetails) {
            List<Accounts> accounts2 = orderDetail.getAccounts();
            for (Accounts account : accounts2) {
                String key = account.getFinCoaCd().toUpperCase() + "-"
                        + account.getAccountNbr().toUpperCase();
                if (accountAmtMap.get(key) == null) {
                    accountAmtMap.put(key, KualiDecimal.ZERO);
                }
                accountAmtMap.put(key, accountAmtMap.get(key).add(account.getAccountFixedAmt()));
            }
        }

        BusinessObjectService boService = SpringContext.getBean(BusinessObjectService.class);
        FinancialSystemAdaptorFactory finFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        Set<String> keySet = new HashSet<String>();
        keySet.addAll(accountAmtMap.keySet());
        for (String accountKey : keySet) {
            String chartCode = accountKey.split("-")[0];
            String accountNumber = accountKey.split("-")[1];
            KualiDecimal accountTotalAmount = accountAmtMap.get(accountKey);
            FinancialAccount finAccount = finFactory.getAccountService().getByPrimaryId(chartCode,
                    accountNumber);
            if (finAccount != null) {
                // check limit at account level
                HashMap<String, String> fieldValues = new HashMap<String, String>();
                fieldValues.put("chartCode", finAccount.getChartOfAccountsCode());
                fieldValues.put("accountNumber", finAccount.getAccountNumber());
                fieldValues.put("active", "Y");
                Collection found = boService.findMatching(OrderAutoLimit.class, fieldValues);
                if (found != null && !found.isEmpty()) {
                    OrderAutoLimit orderAutoLimit = (OrderAutoLimit) TransactionalServiceUtils
                            .retrieveFirstAndExhaustIterator(found.iterator());
                    MMDecimal autoLimitAmount = orderAutoLimit.getAutoLimitAmount();
                    if (autoLimitAmount.isLessThan(accountTotalAmount)) {
                        return true;
                    }
                    accountAmtMap.remove(accountKey);
                }
                else {
                    // check limit at organization level
                    fieldValues.clear();
                    fieldValues = new HashMap<String, String>();
                    fieldValues.put("chartCode", finAccount.getChartOfAccountsCode());
                    fieldValues.put("orgCode", finAccount.getOrganizationCode());
                    fieldValues.put("accountNumber", null);
                    fieldValues.put("active", "Y");
                    found = SpringContext.getBean(MMBusinessObjectDao.class).findMatching(
                            OrderAutoLimit.class, fieldValues);
                    if (found != null && !found.isEmpty()) {
                        OrderAutoLimit orderAutoLimit = (OrderAutoLimit) TransactionalServiceUtils
                                .retrieveFirstAndExhaustIterator(found.iterator());
                        MMDecimal autoLimitAmount = orderAutoLimit.getAutoLimitAmount();
                        if (autoLimitAmount.isLessThan(accountTotalAmount)) {
                            return true;
                        }
                        accountAmtMap.remove(accountKey);
                    }
                }

            }
        }
        if (!accountAmtMap.isEmpty() && defaultBuyLimitAmount.isLessThan(computeTotal)) {
            return true;
        }
        return false;
    }

    protected boolean isMissingAccountingLines() {
        List<OrderDetail> orderDetailsList = this.getOrderDetails();
        for (OrderDetail item : orderDetailsList) {
            if (item.getOrderItemPriceAmt() != null
                    && item.getOrderItemPriceAmt().isGreaterThan(MMDecimal.ZERO)
                    && item.getAccounts().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    protected boolean isSeparationOfDutiesReviewRequired() {
        try {
            Set<Person> priorApprovers = getDocumentHeader().getWorkflowDocument()
                    .getAllPriorApprovers();
            if (priorApprovers != null && !priorApprovers.isEmpty()) {
                return false;
            }
        }
        catch (WorkflowException we) {
            LOG.error("Exception while attempting to retrieve all prior approvers from workflow: "
                    + we);
            throw new RuntimeException(we);
        }
        KualiDecimal computeTotal = SpringContext.getBean(OrderService.class).computeTotal(
                getOrderDetails());
        KualiDecimal totalAmount = (computeTotal == null ? new KualiDecimal(0.0) : computeTotal);
        // default is ignore the rule
        KualiDecimal maxAllowedAmount = totalAmount;
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (factory.isSystemAvailable()) {
            maxAllowedAmount = factory.getFinancialPurchasingService()
                    .getSeparationOfDutiesDollarAmount();
        }
        if (maxAllowedAmount != null && (maxAllowedAmount.isGreaterEqual(totalAmount))) {
            return false;
        }
        return true;
    }

    /**
     * Returns the customer profile chart code
     * 
     * @return
     */
    public String getChartOfAccountsCode() {
        if (ObjectUtils.isNotNull(getCustomerProfile())) {
            return getCustomerProfile().getFinacialChartOfAccountsCode();
        }
        return null;
    }

    /**
     * Returns the customer profile organization code
     * 
     * @return
     */
    public String getOrganizationCode() {
        if (ObjectUtils.isNotNull(getCustomerProfile())) {
            return getCustomerProfile().getOrganizationCode();
        }
        return null;
    }

    /**
     * Gets all account lines from all items
     * 
     * @return
     */
    public List<Accounts> getAccountsForRouting() {
        ArrayList<Accounts> retval = new ArrayList<Accounts>();
        for (OrderDetail orderDetail : this.orderDetails) {
            List<Accounts> accountsList = orderDetail.getAccounts();
            for (Accounts account : accountsList) {
                retval.add(account);
            }
        }
        return retval;
    }

    public KualiDecimal getOrderTotalAmount() {
        return SpringContext.getBean(OrderService.class).computeTotal(getOrderDetails());
    }

    public boolean isOrderStatusInitiated() {
        return StringUtils.equals(getOrderStatusCd(), MMConstants.OrderStatus.INITIATED);
    }

    @Override
    public boolean getAllowsCopy() {
        return true;
    }

    public List<FinancialGeneralLedgerPendingEntry> getApprovedGeneralLedgerPendingEntries() {
        return SpringContext.getBean(GeneralLedgerProcessor.class)
                .getApprovedInternalGeneralLedgerPendingEntries(getDocumentNumber());
    }

    /**
     * Gets the financialGeneralLedgerPendingEntries property
     * 
     * @return Returns the financialGeneralLedgerPendingEntries
     */
    public List<FinancialGeneralLedgerPendingEntry> getFinancialGeneralLedgerPendingEntries() {
        return this.financialGeneralLedgerPendingEntries;
    }

    /**
     * Sets the financialGeneralLedgerPendingEntries property value
     * 
     * @param financialGeneralLedgerPendingEntries The financialGeneralLedgerPendingEntries to set
     */
    public void setFinancialGeneralLedgerPendingEntries(
            List<FinancialGeneralLedgerPendingEntry> financialGeneralLedgerPendingEntries) {
        this.financialGeneralLedgerPendingEntries = financialGeneralLedgerPendingEntries;
    }

}