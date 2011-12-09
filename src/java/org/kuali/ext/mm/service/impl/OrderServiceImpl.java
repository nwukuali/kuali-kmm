package org.kuali.ext.mm.service.impl;

import static org.kuali.ext.mm.common.sys.MMConstants.LF;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.ext.mm.b2b.cxml.types.CXML;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;
import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Agreement;
import org.kuali.ext.mm.businessobject.BackOrder;
import org.kuali.ext.mm.businessobject.CxmlPurchaseOrder;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.PunchOutVendor;
import org.kuali.ext.mm.businessobject.RecurringOrder;
import org.kuali.ext.mm.businessobject.SalesInstance;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.fp.service.FinancialDataService;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialObjectCode;
import org.kuali.ext.mm.integration.service.FinancialObjectCodeService;
import org.kuali.ext.mm.service.B2BPunchOutService;
import org.kuali.ext.mm.service.BackOrderService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.OrderService;
import org.kuali.ext.mm.service.PickListService;
import org.kuali.ext.mm.service.PunchOutVendorService;
import org.kuali.ext.mm.service.RecurringOrderService;
import org.kuali.ext.mm.service.SalesInstanceService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.PersonService;
import org.kuali.rice.kns.mail.MailMessage;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.DictionaryValidationService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.KualiConfigurationService;
import org.kuali.rice.kns.service.MailService;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.MessageMap;
import org.kuali.rice.kns.util.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author schneppd
 */
@Transactional
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = Logger.getLogger(OrderServiceImpl.class);

    private PickListService pickListService;
    private SalesInstanceService salesInstanceService;
    private BusinessObjectService businessObjectService;
    private MailService mailService;

    /**
     * @see org.kuali.ext.mm.service.OrderService#computeLineTotal(org.kuali.ext.mm.businessobject.OrderDetail)
     */
    public Double computeLineTotal(OrderDetail detail) {
        return (detail.getOrderItemPriceAmt().doubleValue() 
                + detail.getOrderItemAdditionalCostAmt().doubleValue())
                    * detail.getOrderItemQty().doubleValue();
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#computeLineTotalWithTax(org.kuali.ext.mm.businessobject.OrderDetail)
     */
    public KualiDecimal computeLineTotalWithTax(OrderDetail detail) {
        Double subTotal = computeLineTotal(detail);
        Double totalTax = detail.getOrderItemTaxAmt().doubleValue() * detail.getOrderItemQty().doubleValue();
        return new KualiDecimal(subTotal + totalTax);
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#computeSubTotal(java.util.List)
     */
    public KualiDecimal computeSubTotal(List<OrderDetail> details) {
        KualiDecimal total = new KualiDecimal(0.0);
        for (OrderDetail detail : details) {
            if (isLineApproved(detail))
                total = total.add(new KualiDecimal(computeLineTotal(detail)));
        }
        return total;
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#computeTaxTotal(java.util.List)
     */
    public Double computeTaxTotal(List<OrderDetail> details) {
        Double total = 0.0;
        for (OrderDetail detail : details) {
            if (isLineApproved(detail) && ObjectUtils.isNotNull(detail.getCatalogItem()) && detail.getCatalogItem().isTaxableInd())
                total = total + detail.getOrderItemTaxAmt().doubleValue() * detail.getOrderItemQty().doubleValue();
        }
        return total;
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#computeTotal(java.util.List)
     */
    public KualiDecimal computeTotal(List<OrderDetail> details) {
    	if (details == null || details.isEmpty()) {
            return new KualiDecimal(0.0);
        }
    	KualiDecimal total = new KualiDecimal(0);
    	for(OrderDetail detail : details) {
    		total = total.add(computeLineTotalWithTax(detail));
    	}
        return total;
    }
    

    /**
     * @see org.kuali.ext.mm.service.OrderService#computeAmountSaved(java.util.List)
     */
    public KualiDecimal computeAmountSaved(List<OrderDetail> details) {
        if (details == null || details.isEmpty())
            return new KualiDecimal(0.0);
        
        KualiDecimal total = new KualiDecimal(0);
        for(OrderDetail detail : details) {
            if(detail.getOrderItemMarkupAmt().isNegative())
                total = total.add(detail.getOrderItemMarkupAmt().abs().multiply(new MMDecimal(detail.getOrderItemQty())).kualiDecimalValue());
        }
        return total;
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#processOrderDocument(org.kuali.ext.mm.document.OrderDocument)
     */
    public void processOrderDocument(OrderDocument orderDocument) {
        RecurringOrder recurringOrder = orderDocument.getRecurringOrder();        
        if(ObjectUtils.isNotNull(recurringOrder) && orderDocument.isRecurringOrderInd() 
                && recurringOrder.getNextRecurringDt() == null) {
            Date nextRecurringDate = SpringContext.getBean(RecurringOrderService.class).computeNextRecurringDate(recurringOrder);
            recurringOrder.setNextRecurringDt(nextRecurringDate);
            recurringOrder.setActive(true);
            getBusinessObjectService().save(recurringOrder);
            setOrderDocumentStatus(orderDocument, MMConstants.OrderStatus.ORDER_LINE_CLOSED);
            //All recurring orders should be handled by the batch, do no further processing if this is a user initiated document
            return;
        }
        setOrderDocumentStatus(orderDocument, MMConstants.OrderStatus.ORDER_LINE_OPEN);
        
        if(MMConstants.OrderType.WAREHS.equals(orderDocument.getOrderTypeCode())
                || MMConstants.OrderType.PUNCH.equals(orderDocument.getOrderTypeCode())
                || MMConstants.OrderType.HOSTED.equals(orderDocument.getOrderTypeCode())
                || MMConstants.OrderType.TRUE_BUYOUT.equals(orderDocument.getOrderTypeCode())) {
            processOrderDocmuentAsSale(orderDocument);
        }
        else if(MMConstants.OrderType.STOCK.equals(orderDocument.getOrderTypeCode())) {
            prepareAndSubmitRequisition(orderDocument);
        }
    }

    /**
     * @param orderDocument
     */
    private void processOrderDocmuentAsSale(OrderDocument orderDocument) {        
        SalesInstance salesInstance = salesInstanceService.getNewSalesInstance(orderDocument);
        
        for (OrderDetail detail : getApprovedOrderDetails(orderDocument)) {
            if(MMConstants.OrderType.WAREHS.equals(orderDocument.getOrderTypeCode()))  {                
                List<PickListLine> linesFromDetail = pickListService.createPickListLinesFromOrderDetail(detail);
                salesInstance.getPickListLines().addAll(linesFromDetail);
            }
            else if(MMConstants.OrderType.TRUE_BUYOUT.equals(orderDocument.getOrderTypeCode())){
                PickListLine newLine = pickListService.createPickListLineForTrueBuyout(detail);
                salesInstance.getPickListLines().add(newLine);
            }
            salesInstance.getOrderDetails().add(detail);
        }        
                 
        getBusinessObjectService().save(salesInstance);
        boolean b2b = false;
        Agreement agreement = orderDocument.getAgreement();
        if(ObjectUtils.isNotNull(agreement) && agreement.isB2bInd()){
            b2b = true;
        }
        if ((MMConstants.OrderType.HOSTED.equals(orderDocument.getOrderTypeCode()) && b2b)
                || MMConstants.OrderType.PUNCH.equals(orderDocument.getOrderTypeCode())) {
            createAndSendCxmlPurchaseOrder(orderDocument);
        }
        else if (MMConstants.OrderType.HOSTED.equals(orderDocument.getOrderTypeCode()) && !b2b) {
            prepareAndSubmitRequisition(orderDocument);
        }
        else if(MMConstants.OrderType.TRUE_BUYOUT.equals(orderDocument.getOrderTypeCode())) {
            //True Buyout orders need to start already backordered
            salesInstance.refreshReferenceObject(MMConstants.SalesInstance.PICK_LIST_LINES);
            BackOrderService backOrderService = MMServiceLocator.getBackOrderService();
            for(PickListLine line : salesInstance.getPickListLines()) {
                backOrderService.save(backOrderService.createBackOrder(line));
            }
            MMServiceLocator.getTrueBuyoutService().finalizeTrueBuyoutStock(orderDocument);
        }
    }   

    /**
     * @param orderDocument
     */
    private CxmlPurchaseOrder createAndSendCxmlPurchaseOrder(OrderDocument orderDocument) {
        CxmlPurchaseOrder cxmlPO = new CxmlPurchaseOrder();
        String catalogId = orderDocument.getOrderDetails().get(0).getCatalogItem().getCatalogId();
        PunchOutVendorService punchOutVendorService = SpringContext.getBean(PunchOutVendorService.class);
        PunchOutVendor punchOutVendor = punchOutVendorService.getPunchOutVendorByCatalogId(catalogId);
        
        if(punchOutVendor == null)
            throw new RuntimeException("No B2B/PunchOut Vendor found for catalog with id '" + catalogId + "'.");
        
        B2BPunchOutService punchOutService = MMServiceLocator.getB2BPunchOutServiceLocator().getB2BPunchOutService(punchOutVendor.getVendorCredentialDomain(), punchOutVendor.getVendorIdentity());        
        
        String errorContent = "";
        CXML cxml = punchOutService.createPurchaseOrderRequest(orderDocument);
        cxmlPO.setKeyId(cxml.getPayloadID());
        cxmlPO.setOrderId(orderDocument.getDocumentNumber());
        cxmlPO.setProfileId(orderDocument.getCustomerProfile().getProfileId());
        cxmlPO.setPurchaseOrderUrl(punchOutVendor.getPunchOutUrl());
        cxmlPO.setPurchaseOrderXml(CxmlUtil.cxmlToString(cxml));
        try {
            errorContent = punchOutService.sendPurchaseOrderRequest(cxml, punchOutVendor.getPurchaseOrderUrl());
        }
        catch (Exception e) {
            throw new RuntimeException("B2B Purchase Order Request failed to send.", e);
        }
        if(StringUtils.isNotBlank(errorContent)) {
            throw new RuntimeException("Error in b2b response: " + LF + errorContent);
        }
        
        getBusinessObjectService().save(cxmlPO);
        
        return cxmlPO;
    }

    /**
     * @param orderDocument
     */
    private void sendConfirmationEmail(OrderDocument orderDocument) {
        MailMessage message = new MailMessage();
        KualiConfigurationService configService = KNSServiceLocator.getKualiConfigurationService();
        message.setFromAddress(configService.getPropertyString(MMKeyConstants.Shopping.SHOPPING_EMAIL_FROM_ADDRESS));
        message.setToAddresses(new HashSet<String>());
        message.getToAddresses().add(orderDocument.getCustomerProfile().getProfileEmail());
        message.setSubject(configService.getPropertyString(MMKeyConstants.Shopping.SHOPPING_EMAIL_CONFIRMATION_SUBJECT));
        
        StringBuffer emailBody = new StringBuffer();
        emailBody.append(configService.getPropertyString(MMKeyConstants.Shopping.SHOPPING_EMAIL_CONFIRMATION_HEADER));
        emailBody.append(LF + LF + "Order Summary");
        emailBody.append(LF + "Order #: " + orderDocument.getOrderId());
        emailBody.append(LF + "Subtotal: " + orderDocument.getDisplaySubTotal());
        emailBody.append(LF + "Tax: " + orderDocument.getTax());
        emailBody.append(LF + "Order Total: " + orderDocument.getDisplayTotal());
        emailBody.append(LF + "-------------");
        emailBody.append(LF + getOrderDetailEmailString(orderDocument.getOrderDetails()));
        emailBody.append(LF + "-------------");
        emailBody.append(LF + "Billing Address:");
        emailBody.append(LF + getAddressEmailString(orderDocument.getBillingAddress()));
        emailBody.append(LF + LF + "Shipping Address:");
        emailBody.append(LF + getAddressEmailString(orderDocument.getShippingAddress()));
        emailBody.append(LF + configService.getPropertyString(MMKeyConstants.Shopping.SHOPPING_EMAIL_CONFIRMATION_FOOTER));
        message.setMessage(emailBody.toString());
        try {
            mailService.sendMessage(message);
        }
        catch (Exception e) {
            // Don't stop the show if the email has problem, log it and continue.
            LOG.error("Order Confirmation: email problem. Message not sent.", e);
        }   
        
    }

    /**
     * @param billingAddress
     * @return
     */
    private Object getAddressEmailString(Address address) {
        StringBuffer addressString = new StringBuffer();
        
        addressString.append(LF + address.getAddressName());
        addressString.append(LF + address.getAddressLine1());
        if(StringUtils.isNotBlank(address.getAddressLine2()))
            addressString.append(LF + address.getAddressLine2());
        addressString.append(LF + address.getAddressCityName());
        addressString.append(", " + address.getAddressStateCode());
        addressString.append(" " + address.getAddressPostalCode());        
        addressString.append(LF + address.getAddressCountryCode());
        
        return addressString.toString();
    }

    /**
     * Gets the String summary of the Order Details to be used in an email
     * message.
     * 
     * @param orderDetails
     * @return String summary message for all order detail objects
     */
    private String getOrderDetailEmailString(List<OrderDetail> orderDetails) {
        StringBuffer detailSummary = new StringBuffer();
        for(OrderDetail detail : orderDetails) {
            detailSummary.append(LF + detail.getOrderItemQty() + "  ");
            detailSummary.append("Item #: " + detail.getDistributorNbr());
            detailSummary.append(LF + detail.getOrderItemDetailDesc());
            detailSummary.append(LF + "$" + detail.getOrderItemPriceAmt());
            detailSummary.append(LF);
        }
        return detailSummary.toString();
    }

    /**
     * @param orderDocument
     */
    private void prepareAndSubmitRequisition(OrderDocument orderDocument) {        
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (factory.isSystemAvailable()) {
            String initiator = findOrderInitiatorPrincipalName(orderDocument);
            OrderDocument financialOrder = orderDocument.generateFinancialOrder();
            factory.getFinancialPurchasingService().submitRequisition(financialOrder,
                    initiator);
            orderDocument.setReqsId(financialOrder.getReqsId());
        }
        else {
            LOG
                    .error("Order could not be submitted to Financial System because it is unavailable");
            throw new RuntimeException("Order could not be submitted to Financial System");
        }
    }
    
    /**
     * @param workflowDocument
     * @param initiator
     * @return
     */
    private String findOrderInitiatorPrincipalName(OrderDocument document) {
        String initiator = null;
        if (MMConstants.OrderType.STOCK.equals(document.getOrderTypeCode())) {
            Person person = SpringContext.getBean(PersonService.class).getPerson(
                    document.getDocumentHeader().getWorkflowDocument().getInitiatorPrincipalId());
            initiator = person.getPrincipalName();
        }
        else if (MMConstants.OrderType.HOSTED.equals(document.getOrderTypeCode())) {
            initiator = document.getWarehouse().getBillingProfile().getPrincipalName();
        }
        return initiator;
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#getApprovedOrderDetails(org.kuali.ext.mm.document.OrderDocument)
     */
    public List<OrderDetail> getApprovedOrderDetails(OrderDocument orderDocument) {
        List<OrderDetail> approvedDetails = new ArrayList<OrderDetail>();

        for (OrderDetail detail : orderDocument.getOrderDetails()) {
            if (isLineApproved(detail))
                approvedDetails.add(detail);
        }

        return approvedDetails;
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#createDefaultAccountingLine(org.kuali.ext.mm.document.OrderDocument, org.kuali.ext.mm.businessobject.Profile)
     */
    public Accounts createDefaultAccountingLine(OrderDocument order, Profile profile) {
		Accounts defaultAccount = new Accounts();
		String finObjectCd = null;

		if(ObjectUtils.isNotNull(order.getOrderDetails()))
			finObjectCd = order.getOrderDetails().get(0).getCatalogItem().getCatalog().getDefaultObjectCd();

		defaultAccount.setFinCoaCd(profile.getFinacialChartOfAccountsCode());
		defaultAccount.setAccountNbr(profile.getAccountNumber());
		defaultAccount.setSubAcctNbr(profile.getSubAccountNumber());
		defaultAccount.setFinObjectCd(finObjectCd);
		defaultAccount.setProjectCd(profile.getProjectCode());
		defaultAccount.setAmountType(MMConstants.OrderDocument.OPTION_PCT);
		defaultAccount.setAccountFixedAmt(SpringContext.getBean(OrderService.class).computeTotal(order.getOrderDetails()));
		defaultAccount.setAccountPct(new BigDecimal(100.00));

		return defaultAccount;
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#getOrderDetailAccountsTotalAmount(org.kuali.ext.mm.businessobject.OrderDetail)
     */
    public KualiDecimal getOrderDetailAccountsTotalAmount(OrderDetail orderDetail) {
        List<Accounts> accountingLines = orderDetail.getAccounts();
        Double orderDetailTotal = orderDetail.getDetailTotal().doubleValue();
        KualiDecimal total = new KualiDecimal(0.0);
        for (Accounts line : accountingLines) {
            if(MMConstants.OrderDocument.OPTION_FXD.equals(line.getAmountType()))
                total = total.add(line.getAccountFixedAmt());
            else
                total = total.add(new KualiDecimal(orderDetailTotal * line.getAccountPct().doubleValue() * 0.01));
        }
        return total;
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#distributeDocumentLevelAccountingLines(org.kuali.ext.mm.document.OrderDocument)
     */
    public void distributeDocumentLevelAccountingLines(OrderDocument document) {
        for (OrderDetail detail : document.getOrderDetails()) {
            Double difference = 0.0;
            difference = detail.getDetailTotal().doubleValue() - getOrderDetailAccountsTotalAmount(detail).doubleValue();
            if (difference > 0.0) {
                Iterator<Accounts> accountIterator = document.getAccounts().iterator();
                while (accountIterator.hasNext()) {
                    Accounts accountToAdd = new Accounts(accountIterator.next());
                    accountToAdd.setLineNbr(detail.getAccounts().size());
                    accountToAdd.setAmountType(MMConstants.OrderDocument.OPTION_FXD);
                    // if this isn't the last accounting line compute the fraction, else dump the remainder into the last account
                    // These lines should be validated before this point to insure a 100% total or this will not work.
                    Double fixedAmount = 0.0;
                    if (accountIterator.hasNext()) {
                    	fixedAmount = difference * (accountToAdd.getAccountPct().multiply(new BigDecimal(.01))).doubleValue();
                        accountToAdd.setAccountFixedAmt(new KualiDecimal(fixedAmount));
                    }
                    else {
                    	fixedAmount = detail.getDetailTotal().doubleValue() - getOrderDetailAccountsTotalAmount(detail).doubleValue();                    	
                        accountToAdd.setAccountFixedAmt(new KualiDecimal(fixedAmount));
                    }
                    Double percentAmount = fixedAmount / detail.getDetailTotal().doubleValue();
                    accountToAdd.setAccountPct(new BigDecimal(percentAmount * 100));
                    detail.getAccounts().add(accountToAdd);
                }
            }
        }
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#setOrderDocumentStatus(org.kuali.ext.mm.document.OrderDocument, java.lang.String)
     */
    public void setOrderDocumentStatus(OrderDocument document, String orderStatusCode) {
    	document.setOrderStatusCd(orderStatusCode);
    	document.refreshReferenceObject(MMConstants.OrderDocument.ORDER_STATUS);
    	for(OrderDetail detail : document.getOrderDetails()) {
    		if(!MMConstants.OrderStatus.DISAPPROVE.equals(detail.getOrderStatusCd())
    				|| !MMConstants.OrderStatus.ORDER_LINE_CANCELED.equals(detail.getOrderStatusCd())) {
    			detail.setOrderStatusCd(orderStatusCode);
    			detail.refreshReferenceObject(MMConstants.OrderDetail.ORDER_STATUS);
    		}
    	}
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#isValidAccountingLinesTotalAmount(org.kuali.ext.mm.document.OrderDocument)
     */
    public boolean isValidAccountingLinesTotalAmount(OrderDocument document) {
        Double accountsTotal = new Double(0);
        Double orderTotal = computeTotal(document.getOrderDetails()).doubleValue();
        for (Accounts account : document.getAccounts()) {
            if(MMConstants.OrderDocument.OPTION_FXD.equals(account.getAmountType()))
                accountsTotal += account.getAccountFixedAmt().doubleValue();
            else
                accountsTotal += (orderTotal * account.getAccountPct().doubleValue() * 0.01);
        }
        return new KualiDecimal(accountsTotal).compareTo(new KualiDecimal(orderTotal)) == 0;
    }
    
    /**
     * @see org.kuali.ext.mm.service.OrderService#isValidDetailLevelAccountingLinesAmount(org.kuali.ext.mm.businessobject.OrderDetail)
     */
    public boolean isValidDetailLevelAccountingLinesAmount(OrderDetail orderDetail) {
        Double orderDetailTotal = orderDetail.getDetailTotal().doubleValue();
        Double accountTotal = getOrderDetailAccountsTotalAmount(orderDetail).doubleValue();
        
        return new MMDecimal(orderDetailTotal).compareTo(new MMDecimal(accountTotal)) == 0;
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#isLineApproved(org.kuali.ext.mm.businessobject.OrderDetail)
     */
    public boolean isLineApproved(OrderDetail detail) {
        if (ObjectUtils.isNull(detail.getOrderStatus()))
            return false;

        return !(MMConstants.OrderStatus.DISAPPROVE.equals(detail.getOrderStatus().getOrderStatusCd()));
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#isAssetObjectCode(java.lang.String)
     */
    public boolean isAssetObjectCode(String finObjectCd) {
        Collection<String> values = KNSServiceLocator.getParameterService().getParameterValues(
                MMConstants.MM_NAMESPACE, MMConstants.Parameters.DOCUMENT,
                MMConstants.Parameters.ASSET_OBJECT_CODES);
        for (String code : values) {
            if (code.equals(finObjectCd))
                return true;
        }
        return false;
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#validateAccountingLine(org.kuali.ext.mm.businessobject.Accounts, java.lang.String)
     */
    public boolean validateAccountingLine(Accounts newAccountingLine, String errorPathPrefix) {
        MessageMap errorMap = GlobalVariables.getMessageMap();
        int originalErrorCount = errorMap.getErrorCount();

        // call the DD validation which checks basic data integrity
        for (PropertyDescriptor descriptor : PropertyUtils.getPropertyDescriptors(newAccountingLine
                .getClass())) {
            SpringContext.getBean(DictionaryValidationService.class)
                    .validatePrimitiveFromDescriptor(newAccountingLine.getClass().getName(),
                            newAccountingLine, descriptor, errorPathPrefix, true);
        }
        boolean valid = errorMap.getErrorCount() == originalErrorCount;
        if (valid) {
            FinancialDataService financialDataService = SpringContext
                    .getBean(FinancialDataService.class);            
            if ((newAccountingLine.getAccountFixedAmt() == null || newAccountingLine.getAccountFixedAmt().isZero())
                    && (newAccountingLine.getAccountPct() == null || newAccountingLine.getAccountPct().doubleValue() == 0.0)) {
                valid &= false;
                GlobalVariables.getMessageMap()
                        .putError(errorPathPrefix + "formAmount",
                                MMKeyConstants.Accounts.ZERO_AMOUNT);
            }
            if (!financialDataService.validateChart(newAccountingLine.getFinCoaCd())) {
                valid &= false;
                GlobalVariables.getMessageMap()
                        .putError(errorPathPrefix + "finCoaCd",
                                MMKeyConstants.Accounts.INVALID_CHART_CODE,
                                newAccountingLine.getFinCoaCd());
            }
            if (!financialDataService.validateAccount(newAccountingLine.getFinCoaCd(),
                    newAccountingLine.getAccountNbr())) {
                valid &= false;
                GlobalVariables.getMessageMap().putError(errorPathPrefix + "accountNbr",
                        MMKeyConstants.Accounts.INVALID_ACCT_NUMBER,
                        newAccountingLine.getFinCoaCd(), newAccountingLine.getAccountNbr());

            }
            if (!financialDataService.validateSubAccount(newAccountingLine.getFinCoaCd(),
                    newAccountingLine.getAccountNbr(), newAccountingLine.getSubAcctNbr())) {
                valid &= false;
                GlobalVariables.getMessageMap().putError(errorPathPrefix + "subAcctNbr",
                        MMKeyConstants.Accounts.INVALID_SUB_ACCT_NUMBER,
                        newAccountingLine.getFinCoaCd(), newAccountingLine.getAccountNbr(),
                        newAccountingLine.getSubAcctNbr());
            }
            if(!financialDataService.validateProject(newAccountingLine.getProjectCd())) {
                GlobalVariables.getMessageMap().putError(errorPathPrefix + "projectCd", MMKeyConstants.Accounts.INVALID_PROJECT_CODE, newAccountingLine.getProjectCd());
                valid &= false;
            }
            if (!financialDataService.validateObjectCode(null, newAccountingLine.getFinCoaCd(),
                    newAccountingLine.getFinObjectCd())) {
                valid &= false;
                GlobalVariables.getMessageMap().putError(errorPathPrefix + "finObjectCd",
                        MMKeyConstants.Accounts.INVALID_OBJECT_CODE,
                        newAccountingLine.getFinCoaCd(), newAccountingLine.getFinObjectCd());

            }
            else if (newAccountingLine.getFinCoaCd() != null
                    && newAccountingLine.getFinObjectCd() != null) {
                FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                        .getBean(FinancialSystemAdaptorFactory.class);
                if (financialSystemAdaptorFactory.isSystemAvailable()) {
                    FinancialObjectCodeService service = financialSystemAdaptorFactory
                            .getFinancialObjectCodeService();
                    FinancialObjectCode objCode = service.getByPrimaryId(
                            financialSystemAdaptorFactory.getFinancialUniversityDateService()
                                    .getCurrentFiscalYear(), newAccountingLine.getFinCoaCd(),
                            newAccountingLine.getFinObjectCd());
                    // OBJECT_CODES
                    if (!financialDataService.validateInternalBillingProperty("OBJECT_CODES",
                            objCode.getFinancialObjectCode())) {
                        valid &= false;
                        GlobalVariables.getMessageMap()
                                .putError(errorPathPrefix + "finObjectCd",
                                        MMKeyConstants.Accounts.NOTALLOWED_OBJECT_CODE,
                                        newAccountingLine.getFinCoaCd(),
                                        newAccountingLine.getFinObjectCd());
                    }
                    // OBJECT_TYPES
                    if (!financialDataService.validateInternalBillingProperty("OBJECT_TYPES",
                            objCode.getFinancialObjectTypeCode())) {
                        valid &= false;
                        GlobalVariables.getMessageMap()
                                .putError(errorPathPrefix + "finObjectCd",
                                        MMKeyConstants.Accounts.INVALID_OBJECT_TYPE_CODE,
                                        objCode.getFinancialObjectTypeCode(),
                                        newAccountingLine.getFinCoaCd(),
                                        newAccountingLine.getFinObjectCd());
                    }
                    // OBJECT_LEVELS
                    if (!financialDataService.validateInternalBillingProperty("OBJECT_LEVELS",
                            objCode.getFinancialObjectLevelCode())) {
                        valid &= false;
                        GlobalVariables.getMessageMap()
                                .putError(errorPathPrefix + "finObjectCd",
                                        MMKeyConstants.Accounts.INVALID_OBJECT_LEVEL_CODE,
                                        objCode.getFinancialObjectLevelCode(),
                                        newAccountingLine.getFinCoaCd(),
                                        newAccountingLine.getFinObjectCd());
                    }
                    // OBJECT_SUB_TYPES
                    if (!financialDataService.validateInternalBillingProperty("OBJECT_SUB_TYPES",
                            objCode.getFinancialObjectSubTypeCode())) {
                        valid &= false;
                        GlobalVariables.getMessageMap()
                                .putError(errorPathPrefix + "finObjectCd",
                                        MMKeyConstants.Accounts.INVALID_OBJECT_SUB_TYP_CODE,
                                        objCode.getFinancialObjectSubTypeCode(),
                                        newAccountingLine.getFinCoaCd(),
                                        newAccountingLine.getFinObjectCd());
                    }
                }
            }
            if (!financialDataService.validateSubObjectCode(null, newAccountingLine.getFinCoaCd(),
                    newAccountingLine.getAccountNbr(), newAccountingLine.getFinObjectCd(),
                    newAccountingLine.getFinSubObjectCd())) {
                valid &= false;
                GlobalVariables.getMessageMap().putError(errorPathPrefix + "subObjectCd",
                        MMKeyConstants.Accounts.INVALID_SUB_OBJ_CODE,
                        newAccountingLine.getFinCoaCd(), newAccountingLine.getAccountNbr(),
                        newAccountingLine.getFinObjectCd(), newAccountingLine.getFinSubObjectCd());
            }
        }
        return valid;
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#computeLineCostTotal(org.kuali.ext.mm.businessobject.OrderDetail)
     */
    public KualiDecimal computeLineCostTotal(OrderDetail detail) {
        MMDecimal orderItemPriceAmt = detail.getOrderItemPriceAmt();
        Integer orderItemQty = detail.getOrderItemQty();
        return (orderItemPriceAmt == null ? MMDecimal.ZERO : orderItemPriceAmt)
                .multiply(new MMDecimal(orderItemQty == null ? 0.0 : orderItemQty)).kualiDecimalValue();
    }

    /**
     * @see org.kuali.ext.mm.service.OrderService#computeLineCostTotal(org.kuali.ext.mm.document.OrderDocument)
     */
    public KualiDecimal computeLineCostTotal(OrderDocument order) {
        KualiDecimal amount = KualiDecimal.ZERO;
        List<OrderDetail> orderDetails = order.getOrderDetails();
        if (orderDetails != null) {
            for (OrderDetail orderDetail : orderDetails) {
                amount = amount.add(computeLineCostTotal(orderDetail));
            }
        }
        return amount;
    }
    
    /**
     * @see org.kuali.ext.mm.service.OrderService#isOrderDetailComplete(java.lang.Integer)
     */
    public boolean isOrderDetailComplete(Integer orderDetailId) {
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.PickListLine.ORDER_DETAIL_ID, orderDetailId);
        Collection<PickListLine> pickLines = KNSServiceLocator.getBusinessObjectService().findMatching(PickListLine.class, fieldValues);
        Collection<BackOrder> backOrders = SpringContext.getBean(BackOrderService.class)
                .getBackOrdersForOrderDetail(orderDetailId);        
                
        Iterator<PickListLine> itPickLine = pickLines.iterator();
        List<String> pickStatusCodes = new ArrayList<String>();
        pickStatusCodes.add(MMConstants.PickStatusCode.PICK_STATUS_PCKD);
        pickStatusCodes.add(MMConstants.PickStatusCode.PICK_STATUS_PBCK);
        pickStatusCodes.add(MMConstants.PickStatusCode.PICK_STATUS_BACK);
        pickStatusCodes.add(MMConstants.PickStatusCode.PICK_STATUS_CNCL);
        
        boolean isComplete = true;
        //Check for unverified pick list lines
        while(isComplete && itPickLine.hasNext()) {
            PickListLine line = itPickLine.next();
            isComplete &= pickStatusCodes.contains(line.getPickStatusCodeCd());
        }
        
        Iterator<BackOrder> itBackOrder = backOrders.iterator();
        //Check for pending back orders
        while(isComplete && itBackOrder.hasNext()) {
            BackOrder bo = itBackOrder.next();
            isComplete &= bo.isFilled() || bo.isCanceled();
        }
        
        return isComplete;
    }
    
    /**
     * @see org.kuali.ext.mm.service.OrderService#getOrderDetailQuantityShipped(org.kuali.ext.mm.businessobject.OrderDetail)
     */
    public Integer getOrderDetailQuantityShipped(OrderDetail detail) {
        List<String> pickStatusCodes = new ArrayList<String>();
        pickStatusCodes.add(MMConstants.PickStatusCode.PICK_STATUS_PCKD);
        pickStatusCodes.add(MMConstants.PickStatusCode.PICK_STATUS_PBCK);
        
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.PickListLine.ORDER_DETAIL_ID, detail.getOrderDetailId());
        fieldValues.put(MMConstants.PickListLine.PICK_STATUS_CODE_CD, pickStatusCodes);
        Collection<PickListLine> pickLines = KNSServiceLocator.getBusinessObjectService().findMatching(PickListLine.class, fieldValues);
        
        Integer qty = 0;
        for(PickListLine line : pickLines) {
            qty += line.getPickStockQty();
        }
        
        return qty;
    }

    public void setPickListService(PickListService pickListService) {
        this.pickListService = pickListService;
    }

    public PickListService getPickListService() {
        return pickListService;
    }

    public void setSalesInstanceService(SalesInstanceService salesInstanceService) {
        this.salesInstanceService = salesInstanceService;
    }

    public SalesInstanceService getSalesInstanceService() {
        return salesInstanceService;
    }

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public MailService getMailService() {
        return mailService;
    }


}
