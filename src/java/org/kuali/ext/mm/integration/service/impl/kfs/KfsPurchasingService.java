/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.ext.mm.integration.service.impl.kfs;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialAccount;
import org.kuali.ext.mm.integration.purap.document.FinancialPurchaseOrderAccount;
import org.kuali.ext.mm.integration.purap.document.FinancialPurchaseOrderDetail;
import org.kuali.ext.mm.integration.purap.document.FinancialPurchaseOrderDocument;
import org.kuali.ext.mm.integration.service.*;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorAddress;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorContract;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorDetail;
import org.kuali.ext.mm.service.WarehouseAccountingService;
import org.kuali.kfs.module.purap.PurapConstants;
import org.kuali.kfs.module.purap.PurapConstants.POTransmissionMethods;
import org.kuali.kfs.module.purap.PurapParameterConstants;
import org.kuali.kfs.module.purap.PurapPropertyConstants;
import org.kuali.kfs.module.purap.businessobject.*;
import org.kuali.kfs.module.purap.document.PurchaseOrderDocument;
import org.kuali.kfs.module.purap.document.RequisitionDocument;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.routeheader.service.RouteHeaderService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.exception.ValidationException;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


/**
 * @author harsha07
 */
public class KfsPurchasingService implements FinancialPurchasingService {
    private static final String COST_SOURCE_STORES = "STOR";
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(KfsPurchasingService.class);
    private FinancialDocumentService financialDocumentService;
    private FinancialVendorService financialVendorService;
    private FinancialAccountService financialAccountService;
    private FinancialBusinessObjectService financialBusinessObjectService;
    private FinancialParameterService financialParameterService;


    public KfsPurchasingService(FinancialDocumentService financialDocumentService,
            FinancialVendorService financialVendorService,
            FinancialParameterService financialParameterService,
            FinancialAccountService financialAccountService,
            FinancialBusinessObjectService financialBusinessObjectService) {
        super();
        this.financialDocumentService = financialDocumentService;
        this.financialVendorService = financialVendorService;
        this.financialAccountService = financialAccountService;
        this.financialBusinessObjectService = financialBusinessObjectService;
        this.financialParameterService = financialParameterService;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialPurchasingService#submitRequisition(org.kuali.ext.mm.document.OrderDocument,
     *      String)
     */
    @Transactional
    public Document submitRequisition(OrderDocument order, String initiator) {
        RequisitionDocument reqs = null;
        try {
            Profile customerProfile = order.getCustomerProfile();
            if (ObjectUtils.isNull(customerProfile)) {
                LOG.error("Customer Profile is not valid");
                throw new RuntimeException("Customer Profile is not valid");
            }
            Person customerPerson = SpringContext.getBean(PersonService.class)
                    .getPersonByPrincipalName(customerProfile.getPrincipalName().toLowerCase());
            if (ObjectUtils.isNull(customerPerson)) {
                LOG.error("Customer profile person is not found in KIM");
                throw new RuntimeException("Customer profile person is not found in KIM");
            }
            Address billingAddress = order.getBillingAddress();
            if (ObjectUtils.isNull(billingAddress)) {
                LOG.error("Billing Address Id is not valid");
                throw new RuntimeException("Billing Address Id is not valid");
            }
            Address shippingAddress = order.getShippingAddress();
            if (ObjectUtils.isNull(shippingAddress)) {
                LOG.error("Shipping Address Id is not valid");
                throw new RuntimeException("Shipping Address Id is not valid");
            }

            FinancialVendorDetail vendorDetail = null;
            FinancialVendorAddress vendorAddress = null;
            if (order.getVendorHeaderGeneratedId() != null
                    && order.getVendorHeaderGeneratedId() != null) {
                vendorDetail = financialVendorService.getVendorDetail(order
                        .getVendorHeaderGeneratedId(), order.getVendorDetailAssignedId());
                if (ObjectUtils.isNotNull(vendorDetail)) {
                    vendorAddress = financialVendorService.getVendorDefaultAddress(order
                            .getVendorHeaderGeneratedId(), order.getVendorDetailAssignedId(), "PO",
                            order.getCampusCd());
                }
            }
            WarehouseAccountingService acctService = SpringContext
                    .getBean(WarehouseAccountingService.class);
            WarehouseAccounts expenseAccount = acctService.findWarehouseAccounts(order
                    .getWarehouseCd(), MMConstants.WAREHOUSE_COST_GOODS_ACCT);
            if (ObjectUtils.isNull(expenseAccount)) {
                LOG.error("Resale account is not valid");
                throw new RuntimeException("Resale account is not valid");
            }
            FinancialAccount account = financialAccountService.getByPrimaryId(expenseAccount
                    .getFinCoaCd(), expenseAccount.getAccountNbr());
            if (account == null || !account.isActive() || account.isExpired()) {
                LOG.error("Account is not valid or not active :: " + expenseAccount.getFinCoaCd()
                        + "-" + expenseAccount.getAccountNbr());
                throw new RuntimeException("Account is not valid or not active.");
            }
            reqs = (RequisitionDocument) financialDocumentService.getNewDocument(
                    PurapConstants.REQUISITION_DOCUMENT_TYPE, initiator);
            populateRequisitionHeader(order, reqs, account, ObjectUtils.isNotNull(order
                    .getAgreement()) ? order.getAgreement().isNoPrintInd() : false);
            populateBillingInformation(reqs, customerPerson, billingAddress, order.getCampusCd());
            populateReceivingAddress(reqs, account.getChartOfAccountsCode(), account
                    .getOrganizationCode());
            populateDeliveryInformation(order, reqs, customerProfile, customerPerson,
                    shippingAddress);
            populateVendorDetails(order, reqs, vendorDetail, vendorAddress);
            populateOrderDetailInformation(reqs, order);
            // check for all not null field before commit, throw exception if not OK
            validateRequiredFields(reqs);

            try {
                reqs = (RequisitionDocument) financialDocumentService.routeDocument(reqs, null,
                        null, initiator);
            }
            catch (Exception e) {
                LOG.error(e);
                reqs = (RequisitionDocument) financialDocumentService.saveDocument(reqs, initiator);
            }
            order.setReqsId(reqs.getPurapDocumentIdentifier());
        }
        catch (Exception e) {
            LOG.error(e);
            throw new RuntimeException(
                "Unexpected error occured while submitting Requisition Document to KFS.", e);
        }
        return reqs;
    }

    /**
     * @param reqs
     */
    private void validateRequiredFields(RequisitionDocument reqs) {
        if (StringUtils.isBlank(reqs.getBillingName())
                || StringUtils.isBlank(reqs.getBillingLine1Address())
                || StringUtils.isBlank(reqs.getBillingCityName())
                || StringUtils.isBlank(reqs.getBillingPostalCode())
                || StringUtils.isBlank(reqs.getBillingCountryCode())
                || StringUtils.isBlank(reqs.getBillingPhoneNumber())) {
            throw new ValidationException("Billing information is incomplete.");
        }
        if (StringUtils.isBlank(reqs.getRequestorPersonPhoneNumber())
                || StringUtils.isBlank(reqs.getRequisitionSourceCode())
                || StringUtils.isBlank(reqs.getPurchaseOrderCostSourceCode())
                || StringUtils.isBlank(reqs.getDeliveryCampusCode())) {
            throw new ValidationException("Requisition information is incomplete.");

        }
    }

    /**
     * Populates requisition header information
     * 
     * @param order Order Document
     * @param reqs Requisition Document
     * @param account Financial Account
     * @param noPrint
     */
    protected void populateRequisitionHeader(OrderDocument order, RequisitionDocument reqs,
            FinancialAccount account, boolean noPrint) {
        DocumentHeader documentHeader = reqs.getDocumentHeader();
        documentHeader.setDocumentDescription("Stores Order - " + order.getDocumentNumber());
        reqs.setChartOfAccountsCode(account.getChartOfAccountsCode());
        reqs.setUseTaxIndicator(false);
        reqs.setStatusCode(PurapConstants.RequisitionStatuses.APPDOC_IN_PROCESS);
        reqs.setOrganizationCode(account.getOrganizationCode());
        // reqs.setPurchaseOrderCostSourceCode(POCostSources.ESTIMATE);
        reqs.setPurchaseOrderCostSourceCode(COST_SOURCE_STORES);
        reqs.setRequisitionSourceCode(MMConstants.ReOrderDocument.REQS_SOURCE_REORDER);
        if (noPrint) {
            reqs.setPurchaseOrderTransmissionMethodCode(POTransmissionMethods.NOPRINT);
        }
        else {
            reqs.setPurchaseOrderTransmissionMethodCode(POTransmissionMethods.PRINT);
        }
        reqs.setDocumentFundingSourceCode("INST");
        reqs.setAddressToVendorIndicator(true);
        // Pass the explanation and notes from the order document to the requisition document
        documentHeader.setExplanation(order.getDocumentHeader() != null ? order.getDocumentHeader()
                .getExplanation() : null);
    }

    /**
     * @param reqs Requisition Document
     * @param customerPerson Customer Person
     * @param billingAddress Billing Address
     */
    protected void populateBillingInformation(RequisitionDocument reqs, Person customerPerson,
            Address billingAddress, String billingCampusCode) {
        HashMap<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put("billingCampusCode", billingCampusCode);
        // first check if there is a campus level bill address, if so use that instead of value from order document
        BillingAddress defaultBillAddress = (BillingAddress) financialBusinessObjectService
                .findByPrimaryKey(BillingAddress.class, fieldValues);
        if (defaultBillAddress != null && defaultBillAddress.isActive()) {
            reqs.setBillingName(defaultBillAddress.getBillingName());
            reqs.setBillingLine1Address(defaultBillAddress.getBillingLine1Address());
            reqs.setBillingLine2Address(defaultBillAddress.getBillingLine2Address());
            reqs.setBillingCityName(defaultBillAddress.getBillingCityName());
            reqs.setBillingStateCode(defaultBillAddress.getBillingStateCode());
            reqs.setBillingPostalCode(defaultBillAddress.getBillingPostalCode());
            reqs.setBillingCountryCode(defaultBillAddress.getBillingCountryCode());
            reqs
                    .setBillingPhoneNumber(formatPhoneNumber(defaultBillAddress
                            .getBillingPhoneNumber()));
        }
        else {
            reqs.setBillingName(StringUtils.isBlank(customerPerson.getName()) ? "NA"
                    : customerPerson.getName());
            reqs
                    .setBillingLine1Address(StringUtils.isBlank(billingAddress.getAddressLine1()) ? "NA"
                            : billingAddress.getAddressLine1());
            reqs
                    .setBillingLine2Address(StringUtils.isBlank(billingAddress.getAddressLine2()) ? "NA"
                            : billingAddress.getAddressLine2());
            reqs.setBillingCityName(StringUtils.isBlank(billingAddress.getAddressCityName()) ? "NA"
                    : billingAddress.getAddressCityName());
            reqs
                    .setBillingStateCode(StringUtils.isBlank(billingAddress.getAddressStateCode()) ? "MI"
                            : billingAddress.getAddressStateCode());
            reqs
                    .setBillingPostalCode(StringUtils
                            .isBlank(billingAddress.getAddressPostalCode()) ? "99999"
                            : billingAddress.getAddressPostalCode());
            reqs
                    .setBillingCountryCode(StringUtils.isBlank(billingAddress
                            .getAddressCountryCode()) ? "US" : billingAddress
                            .getAddressCountryCode());
            reqs.setBillingPhoneNumber(formatPhoneNumber(customerPerson.getPhoneNumber()));
        }
    }

    @SuppressWarnings("unchecked")
    protected void populateReceivingAddress(RequisitionDocument reqs, String chartCode,
            String orgCode) {

        HashMap<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put("chartOfAccountsCode", chartCode);
        fieldValues.put("organizationCode", orgCode);
        fieldValues.put("defaultIndicator", true);
        // first check if there is a campus level bill address, if so use that instead of value from order document
        Collection recvAddresses = financialBusinessObjectService.findMatching(
                ReceivingAddress.class, fieldValues);
        ReceivingAddress defaultRcvAddress = null;
        if (recvAddresses != null && !recvAddresses.isEmpty()) {
            defaultRcvAddress = (ReceivingAddress) recvAddresses.iterator().next();
        }
        else {
            fieldValues.clear();
            fieldValues.put("chartOfAccountsCode", chartCode);
            fieldValues.put("defaultIndicator", true);
            recvAddresses = financialBusinessObjectService.findMatching(ReceivingAddress.class,
                    fieldValues);
            if (recvAddresses != null && !recvAddresses.isEmpty()) {
                defaultRcvAddress = (ReceivingAddress) recvAddresses.iterator().next();
            }
        }
        if (defaultRcvAddress != null) {
            reqs.setReceivingName(defaultRcvAddress.getReceivingName());
            reqs.setReceivingLine1Address(defaultRcvAddress.getReceivingLine1Address());
            reqs.setReceivingLine2Address(defaultRcvAddress.getReceivingLine2Address());
            reqs.setReceivingCityName(defaultRcvAddress.getReceivingCityName());
            reqs.setReceivingStateCode(defaultRcvAddress.getReceivingStateCode());
            reqs.setReceivingPostalCode(defaultRcvAddress.getReceivingPostalCode());
            reqs.setReceivingCountryCode(defaultRcvAddress.getReceivingCountryCode());
            reqs.setAddressToVendorIndicator(defaultRcvAddress.isUseReceivingIndicator());
        }
        else {
            reqs.setReceivingName(null);
            reqs.setReceivingLine1Address(null);
            reqs.setReceivingLine2Address(null);
            reqs.setReceivingCityName(null);
            reqs.setReceivingStateCode(null);
            reqs.setReceivingPostalCode(null);
            reqs.setReceivingCountryCode(null);
            reqs.setAddressToVendorIndicator(false);
        }
    }

    /**
     * @param order Order Document
     * @param reqs Requisition
     * @param customerProfile Customer Profile
     * @param customerPerson Customer Person
     * @param shippingAddress Shipping Address
     */
    protected void populateDeliveryInformation(OrderDocument order, RequisitionDocument reqs,
            Profile customerProfile, Person customerPerson, Address shippingAddress) {
        reqs.setDeliveryToName(StringUtils.isBlank(customerPerson.getName()) ? "MM Customer"
                : customerPerson.getName());
        reqs.setDeliveryBuildingCode(customerProfile.getDeliveryBuildingCode());
        reqs.setDeliveryBuildingLine1Address(shippingAddress.getAddressLine1());
        reqs.setDeliveryBuildingLine2Address(shippingAddress.getAddressLine2());
        reqs.setDeliveryBuildingName(customerProfile.getDeliveryBuildingRoomNumber());
        reqs.setDeliveryBuildingRoomNumber(customerProfile.getDeliveryBuildingRoomNumber());
        reqs.setDeliveryCampusCode(customerProfile.getCampusCode());
        reqs.setDeliveryCityName(shippingAddress.getAddressCityName());
        reqs.setDeliveryCountryCode(shippingAddress.getAddressCountryCode());
        reqs.setDeliveryInstructionText(order.getDeliveryInstructionTxt());
        reqs.setDeliveryPostalCode(shippingAddress.getAddressPostalCode());
        reqs.setDeliveryStateCode(shippingAddress.getAddressStateCode());
        String noreplyemail = KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(
                "MailMessage.from");
        String email = StringUtils.isBlank(customerPerson.getEmailAddressUnmasked()) ? noreplyemail
                : customerPerson.getEmailAddressUnmasked();
        LOG.info("Email address used by REQS is " + email);
        reqs.setDeliveryToEmailAddress(email);
        reqs.setDeliveryToPhoneNumber(formatPhoneNumber(customerPerson.getPhoneNumber()));
        reqs.setRequestorPersonEmailAddress(email);
        reqs.setRequestorPersonName(StringUtils.isBlank(customerPerson.getName()) ? "NA"
                : customerPerson.getName());
        reqs.setRequestorPersonPhoneNumber(formatPhoneNumber(customerPerson.getPhoneNumber()));
    }

    /**
     * @param reqstrPhone
     * @return
     */
    protected String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            // Use a default when phone number is unavailable
            return "";
        }
        return phoneNumber;
    }

    /**
     * @param order Order Document
     * @param reqs Requisition Document
     * @param vendorDetail Vendor Detail
     * @param vendorAddress Vendor Address
     */
    protected void populateVendorDetails(OrderDocument order, RequisitionDocument reqs,
            FinancialVendorDetail vendorDetail, FinancialVendorAddress vendorAddress) {
        if (ObjectUtils.isNotNull(vendorDetail)) {
            reqs.setVendorHeaderGeneratedIdentifier(vendorDetail
                    .getVendorHeaderGeneratedIdentifier());
            reqs
                    .setVendorDetailAssignedIdentifier(vendorDetail
                            .getVendorDetailAssignedIdentifier());
            reqs.setVendorName(vendorDetail.getVendorName());
            reqs.setVendorNumber(vendorDetail.getVendorNumber());
        }
        if (ObjectUtils.isNotNull(vendorAddress)) {
            reqs.setVendorAddressGeneratedIdentifier(vendorAddress
                    .getVendorAddressGeneratedIdentifier());
            reqs.setVendorAddressInternationalProvinceName(vendorAddress
                    .getVendorAddressInternationalProvinceName());
            reqs.setVendorAttentionName(vendorAddress.getVendorAttentionName());
            reqs.setVendorCityName(vendorAddress.getVendorCityName());
            reqs.setVendorCountryCode(vendorAddress.getVendorCountryCode());
            reqs.setVendorLine1Address(vendorAddress.getVendorLine1Address());
            reqs.setVendorLine2Address(vendorAddress.getVendorLine2Address());
            reqs.setVendorPostalCode(vendorAddress.getVendorZipCode());
            reqs.setVendorStateCode(vendorAddress.getVendorStateCode());
        }
        if (ObjectUtils.isNotNull(order.getAgreement())) {
            reqs.setVendorContractGeneratedIdentifier(order.getAgreement().getVndrContrGnrtdId());
            FinancialVendorContract vendorContract = financialVendorService.getVendorContract(order
                    .getAgreement().getVndrContrGnrtdId());
            if (vendorContract != null && vendorContract.getPurchaseOrderCostSourceCode() != null) {
                reqs
                        .setPurchaseOrderCostSourceCode(vendorContract
                                .getPurchaseOrderCostSourceCode());
            }
        }
    }

    /**
     * @param reqs Requisition Document
     * @param orderDetails Order Details
     */
    @SuppressWarnings("unchecked")
    protected void populateOrderDetailInformation(RequisitionDocument reqs, OrderDocument order) {
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            RequisitionItem item = new RequisitionItem();
            item.setPurapDocumentIdentifier(reqs.getPurapDocumentIdentifier());
            item.setPurapDocument(reqs);
            item.setItemLineNumber(orderDetail.getItemLineNumber());
            // Added this if-else condition so that we use right unit of issue and quantity
            if (MMConstants.OrderType.STOCK.equals(order.getOrderTypeCode())) {
                item.setItemUnitOfMeasureCode(orderDetail.getCatalogItem().getStock()
                        .getBuyUnitOfIssueCd());
                item.setItemQuantity(orderDetail.getBuyOrderQuantity());
                item.setItemUnitPrice(orderDetail.getBuyItemCostAmount().bigDecimalValue());
            }
            else {
                item.setItemUnitOfMeasureCode(orderDetail.getStockUnitOfIssueCd());
                item.setItemQuantity(new KualiDecimal(orderDetail.getOrderItemQty()));
                item.setItemUnitPrice(orderDetail.getOrderItemCostAmt().bigDecimalValue());
            }
            item.setItemCatalogNumber(orderDetail.getDistributorNbr());
            item.setItemDescription(orderDetail.getOrderItemDetailDesc());
            item.setItemRestrictedIndicator(false);
            item.setItemTypeCode(PurapConstants.ItemTypeCodes.ITEM_TYPE_ITEM_CODE);
            List<Accounts> orderAccounts = orderDetail.getAccounts();
            for (Accounts accounts : orderAccounts) {
                RequisitionAccount requisitionAccount = new RequisitionAccount();
                requisitionAccount.setChartOfAccountsCode(accounts.getFinCoaCd());
                requisitionAccount.setAccountNumber(accounts.getAccountNbr());
                requisitionAccount.setFinancialObjectCode(accounts.getFinObjectCd());
                requisitionAccount.setFinancialSubObjectCode(accounts.getFinSubObjectCd());
                BigDecimal roundedPercent = new BigDecimal(Math.round(accounts.getAccountPct()
                        .doubleValue()));
                requisitionAccount.setAccountLinePercent(roundedPercent);
                requisitionAccount.setAmount(new KualiDecimal(accounts.getAccountFixedAmt()
                        .bigDecimalValue()));
                item.getSourceAccountingLines().add(requisitionAccount);
            }
            reqs.getItems().add(item);
        }
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialPurchasingService#getPurchaseOrderIdByRequisitionId(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public Integer getPurchaseOrderIdByRequisitionId(Integer requisitionId) {
        HashMap<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(PurapPropertyConstants.REQUISITION_IDENTIFIER, requisitionId);
        fieldValues.put(PurapPropertyConstants.PURCHASE_ORDER_CURRENT_INDICATOR, "Y");
        Collection matching = financialBusinessObjectService.findMatching(
                PurchaseOrderDocument.class, fieldValues);
        if (matching != null & matching.iterator().hasNext()) {
            return ((PurchaseOrderDocument) matching.iterator().next())
                    .getPurapDocumentIdentifier();
        }
        return null;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialPurchasingService#getPurchaseOrderByRequisitionId(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public FinancialPurchaseOrderDocument getPurchaseOrderByRequisitionId(Integer requisitionId) {
        /***************************************************************************************************************************
         * SEVERE WARNING - This method is intended to be used by a batch job which reconciles POs that are open. This
         * implementation uses service bus and so needs to query each object through HTTP protocol and could cause serious
         * performance issues, so it is strongly suggested that we move away from this approach for batch jobs, this is an
         * intermediate solution
         **************************************************************************************************************************/
        HashMap<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(PurapPropertyConstants.REQUISITION_IDENTIFIER, requisitionId);
        fieldValues.put(PurapPropertyConstants.PURCHASE_ORDER_CURRENT_INDICATOR, "Y");
        Collection<PurchaseOrderDocument> orders = financialBusinessObjectService.findMatching(
                PurchaseOrderDocument.class, fieldValues);
        PurchaseOrderDocument purchaseOrderDocument = null;
        if (orders != null & orders.iterator().hasNext()) {
            purchaseOrderDocument = orders.iterator().next();
        }
        FinancialPurchaseOrderDocument financialPurchaseOrderDocument = null;
        if (purchaseOrderDocument != null
                && ("OPEN".equals(purchaseOrderDocument.getStatusCode()) || "VOID"
                        .equals(purchaseOrderDocument.getStatusCode()))) {
            DocumentRouteHeaderValue routeHeader = SpringContext.getBean(RouteHeaderService.class)
                    .getRouteHeader(purchaseOrderDocument.getDocumentNumber());
            financialPurchaseOrderDocument = new FinancialPurchaseOrderDocument();
            financialPurchaseOrderDocument.setWorkflowStatusCode(routeHeader.getDocRouteStatus());
            financialPurchaseOrderDocument.setPoStatusCode(purchaseOrderDocument.getStatusCode());
            adapt(purchaseOrderDocument, financialPurchaseOrderDocument);
            // query the items for the document
            fieldValues.clear();
            fieldValues.put("documentNumber", purchaseOrderDocument.getDocumentNumber());
            Collection<PurchaseOrderItem> items = financialBusinessObjectService.findMatching(
                    PurchaseOrderItem.class, fieldValues);
            for (PurchaseOrderItem purchaseOrderItem : items) {
                FinancialPurchaseOrderDetail financialPurchaseOrderDetail = new FinancialPurchaseOrderDetail();
                adapt(purchaseOrderItem, financialPurchaseOrderDetail);
                financialPurchaseOrderDocument.getOrderDetails().add(financialPurchaseOrderDetail);
                // query the accounts for the items
                fieldValues.clear();
                fieldValues.put("itemIdentifier", purchaseOrderItem.getItemIdentifier());
                Collection<PurchaseOrderAccount> accounts = financialBusinessObjectService
                        .findMatching(PurchaseOrderAccount.class, fieldValues);
                for (PurchaseOrderAccount purchaseOrderAccount : accounts) {
                    FinancialPurchaseOrderAccount financialPurchaseOrderAccount = new FinancialPurchaseOrderAccount();
                    adapt(purchaseOrderAccount, financialPurchaseOrderAccount);
                    financialPurchaseOrderDetail.getAccounts().add(financialPurchaseOrderAccount);
                }
            }
        }
        return financialPurchaseOrderDocument;
    }

    /**
     * @param source PurchaseOrderDocument
     * @param target FinancialPurchaseOrderDocument
     */
    protected void adapt(PurchaseOrderDocument source, FinancialPurchaseOrderDocument target) {
        target.setPoId(source.getPurapDocumentIdentifier());
        target.setCampusCd(source.getDeliveryCampusCode());
        target.setDeliveryBuildingCd(source.getDeliveryBuildingCode());
        target.setDeliveryBuildingRmNbr(source.getDeliveryBuildingRoomNumber());
        target.setReqsId(source.getRequisitionIdentifier());
        target.setVendorDetailAssignedId(source.getVendorDetailAssignedIdentifier());
        target.setVendorHeaderGeneratedId(source.getVendorHeaderGeneratedIdentifier());
        target.setVendorNm(source.getVendorName());
    }

    /**
     * @param source PurchaseOrderItem
     * @param target FinancialPurchaseOrderDetail
     */
    protected void adapt(PurchaseOrderItem source, FinancialPurchaseOrderDetail target) {
        target.setOrderItemCostAmt(source.getItemUnitPrice());
        target.setOrderItemQty(source.getItemQuantity() == null ? KualiDecimal.ZERO : source
                .getItemQuantity());
        // target.setPoId(source.getPurapDocumentIdentifier());
        target.setStockUnitOfIssueCd(source.getItemUnitOfMeasureCode());
        target.setItemLineNumber(source.getItemLineNumber());
    }

    /**
     * @param source PurchaseOrderAccount
     * @param target FinancialPurchaseOrderAccount
     */
    protected void adapt(PurchaseOrderAccount source, FinancialPurchaseOrderAccount target) {
        target.setAccountFixedAmt(source.getAmount() == null ? BigDecimal.ZERO : source.getAmount()
                .bigDecimalValue());
        target.setAccountNbr(source.getAccountNumber());
        target.setAccountPct(source.getAccountLinePercent());
        target.setFinCoaCd(source.getChartOfAccountsCode());
        target.setFinObjectCd(source.getFinancialObjectCode());
        target.setFinSubObjectCd(source.getFinancialSubObjectCode());
        target.setLineNbr(source.getSequenceNumber());
        target.setProjectCd(source.getProjectCode());
        target.setSubAcctNbr(source.getSubAccountNumber());
    }

    /**
     * @return
     */
    public KualiDecimal getSeparationOfDutiesDollarAmount() {
        String amount = financialParameterService.getParameterValueAsString(RequisitionDocument.class,
					PurapParameterConstants.SEPARATION_OF_DUTIES_DOLLAR_AMOUNT);
        return new KualiDecimal(amount == null ? "0" : amount);
    }

    /**
     * Gets the financialDocumentService property
     * 
     * @return Returns the financialDocumentService
     */
    public FinancialDocumentService getFinancialDocumentService() {
        return this.financialDocumentService;
    }

    /**
     * Sets the financialDocumentService property value
     * 
     * @param financialDocumentService The financialDocumentService to set
     */
    public void setFinancialDocumentService(FinancialDocumentService financialDocumentService) {
        this.financialDocumentService = financialDocumentService;
    }

    /**
     * Gets the financialVendorService property
     * 
     * @return Returns the financialVendorService
     */
    public FinancialVendorService getFinancialVendorService() {
        return this.financialVendorService;
    }

    /**
     * Sets the financialVendorService property value
     * 
     * @param financialVendorService The financialVendorService to set
     */
    public void setFinancialVendorService(FinancialVendorService financialVendorService) {
        this.financialVendorService = financialVendorService;
    }

    /**
     * Gets the financialAccountService property
     * 
     * @return Returns the financialAccountService
     */
    public FinancialAccountService getFinancialAccountService() {
        return this.financialAccountService;
    }

    /**
     * Sets the financialAccountService property value
     * 
     * @param financialAccountService The financialAccountService to set
     */
    public void setFinancialAccountService(FinancialAccountService financialAccountService) {
        this.financialAccountService = financialAccountService;
    }

    /**
     * Gets the financialBusinessObjectService property
     * 
     * @return Returns the financialBusinessObjectService
     */
    public FinancialBusinessObjectService getFinancialBusinessObjectService() {
        return this.financialBusinessObjectService;
    }

    /**
     * Sets the financialBusinessObjectService property value
     * 
     * @param financialBusinessObjectService The financialBusinessObjectService to set
     */
    public void setFinancialBusinessObjectService(
            FinancialBusinessObjectService financialBusinessObjectService) {
        this.financialBusinessObjectService = financialBusinessObjectService;
    }

    /**
     * Gets the financialParameterService property
     * 
     * @return Returns the financialParameterService
     */
    public FinancialParameterService getFinancialParameterService() {
        return this.financialParameterService;
    }

    /**
     * Sets the financialParameterService property value
     * 
     * @param financialParameterService The financialParameterService to set
     */
    public void setFinancialParameterService(FinancialParameterService financialParameterService) {
        this.financialParameterService = financialParameterService;
    }
}
