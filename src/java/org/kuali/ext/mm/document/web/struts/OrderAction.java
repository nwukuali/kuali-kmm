package org.kuali.ext.mm.document.web.struts;


import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.service.OrderService;
import org.kuali.ext.mm.service.PunchOutVendorService;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.service.BusinessObjectDictionaryService;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;
import org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;


public class OrderAction extends KualiTransactionalDocumentActionBase {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(OrderAction.class);

    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#docHandler(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward actionForward = super.docHandler(mapping, form, request, response);
        OrderForm oForm = (OrderForm) form;
        oForm.setReturnToSenderUrl(request
                .getParameter(MMConstants.OrderDocument.RETURN_TO_SENDER_PARAM));
        // List<String> approvedItems = new ArrayList<String>();
        checkAuthorization(oForm.getOrderDocument());
        if (oForm.getOrderDocument().getDocumentHeader().getWorkflowDocument().isSaved()
        		|| oForm.getOrderDocument().getDocumentHeader().getWorkflowDocument().isInitiated()
                && oForm.getOrderDocument().getAccounts().isEmpty()) {
            OrderService orderService = SpringContext.getBean(OrderService.class);
            if(orderService.computeTotal(oForm.getOrderDocument().getOrderDetails()).isGreaterThan(KualiDecimal.ZERO)) {
                Accounts defaultAccount = orderService.createDefaultAccountingLine(oForm
                        .getOrderDocument(), oForm.getOrderDocument().getCustomerProfile());
                oForm.getOrderDocument().getAccounts().add(defaultAccount);
            }
        }

        for (OrderDetail detail : oForm.getOrderDocument().getOrderDetails()) {
            // if (!("X".equals(detail.getOrderStatus().getOrderStatusCd())))
            // approvedItems.add(String.valueOf(detail.getOrderDetailId()));

            oForm.getNewOrderDetailAccountingLines().add(new Accounts());
            if (ObjectUtils.isNull(detail.getCapitalAssetInformation())) {
                MMCapitalAssetInformation assetInfo = new MMCapitalAssetInformation();
                assetInfo.setOrderDetailId(detail.getOrderDetailId());
                detail.setCapitalAssetInformation(assetInfo);
            }

            // updateCapitalAssetInfo(detail);
        }

        if(oForm.getOrderDocument().getRecurringOrder() != null)
        	oForm.setRecurringOrder(oForm.getOrderDocument().getRecurringOrder());

        // oForm.setApprovedItems(approvedItems.toArray(new String[approvedItems.size()]));

        if(MMConstants.OrderType.PUNCH.equals(oForm.getOrderDocument().getOrderType().getOrderTypeCode())) {
            PunchOutVendorService punchOutVendorService = SpringContext.getBean(PunchOutVendorService.class);
            oForm.setPunchOutVendor(punchOutVendorService.getPunchOutVendorByCatalogId(oForm.getOrderDocument().getOrderDetails().get(0).getCatalogItem().getCatalog().getCatalogId()));
        }
        return actionForward;
    }

    /**
     * @param orderDocument
     */
    private void checkAuthorization(OrderDocument orderDocument) {
        if (MMConstants.OrderDocument.PROFILE_TYPE_PERSONAL.equals(orderDocument
                .getProfileTypeCode())) {
            if (!KimApiServiceLocator.getPermissionService().isAuthorized(
                    GlobalVariables.getUserSession().getPrincipalId(), MMConstants.MM_NAMESPACE,
                    MMConstants.OrderDocument.OPEN_PERSONAL_ORDER_PERMISSION, null)) {
                String currentPrincipalName = GlobalVariables.getUserSession().getPrincipalName();
                String principalName = orderDocument.getCustomerProfile().getPrincipalName();
                if (!currentPrincipalName.equalsIgnoreCase(principalName)) {
                    throw new AuthorizationException(currentPrincipalName, "Open Document",
                        "Personal Order Document");
                }
            }
        }
    }

    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        OrderForm oForm = (OrderForm) form;

        updateAndLinkAdditionalInfo(oForm);

        ActionForward actionForward = super.save(mapping, form, request, response);
        return actionForward;
    }

    @Override
    public ActionForward route(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        OrderForm oForm = (OrderForm) form;
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        // Don't let approval happen if financial system is not up and running
        
        if (!financialSystemAdaptorFactory.checkAndErrorSystemAvailability()) {
            return mapping.findForward(MMConstants.MAPPING_BASIC);
        }

        updateAndLinkAdditionalInfo(oForm);

        return super.route(mapping, form, request, response);
    }

    @Override
    public ActionForward blanketApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        OrderForm oForm = (OrderForm) form;
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        // Don't let approval happen if financial system is not up and running
        
        if (!financialSystemAdaptorFactory.checkAndErrorSystemAvailability()) {
            return mapping.findForward(MMConstants.MAPPING_BASIC);
        }

        updateAndLinkAdditionalInfo(oForm);

        return super.blanketApprove(mapping, form, request, response);
    }

    @Override
    public ActionForward approve(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        OrderForm oForm = (OrderForm) form;
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        // Don't let approval happen if financial system is not up and running
       
        if (!financialSystemAdaptorFactory.checkAndErrorSystemAvailability()) {
            return mapping.findForward(MMConstants.MAPPING_BASIC);
        }

        updateAndLinkAdditionalInfo(oForm);        

        return super.approve(mapping, form, request, response);
    }

	/**
     * @param oForm
     */
    private void updateAndLinkAdditionalInfo(OrderForm oForm) {
     // updateApprovedItems(oForm);
        checkAndUpdateAccountingLineInfo(oForm.getOrderDocument());
        OrderService orderService = SpringContext.getBean(OrderService.class);
        if(orderService.isValidAccountingLinesTotalAmount(oForm.getOrderDocument())) {
            orderService.distributeDocumentLevelAccountingLines(
                oForm.getOrderDocument());
        }

        addRecurringOrderInfo(oForm.getRecurringOrder(), oForm.getOrderDocument());        
    }


    public ActionForward addAccountingLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        OrderForm oForm = (OrderForm) form;

        Accounts newAccountingLine = oForm.getNewAccountingLine();
        String errorKey = MMConstants.OrderDocument.NEW_ACCOUNTING_LINE + ".";
				SpringContext.getBean(BusinessObjectDictionaryService.class).performForceUppercase(newAccountingLine);
        Double orderTotal = SpringContext.getBean(OrderService.class).computeTotal(
                oForm.getOrderDocument().getOrderDetails()).doubleValue();
        updateAccountingLineAmountValues(newAccountingLine, orderTotal, true);
        if (SpringContext.getBean(OrderService.class).validateAccountingLine(newAccountingLine,
                errorKey)) {            
            oForm.getOrderDocument().getAccounts().add(newAccountingLine);
            oForm.setNewAccountingLine(new Accounts());
            // Update capital asset info for all order detail
            // for (OrderDetail detail : oForm.getOrderDocument().getOrderDetails()) {
            // updateCapitalAssetInfo(detail);
            // }
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    public ActionForward addAccountingLineToLineItem(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        OrderForm oForm = (OrderForm) form;
        OrderService orderService = SpringContext.getBean(OrderService.class);
        Integer line = getSelectedLine(request);
        Accounts newAccountingLine = oForm.getNewOrderDetailAccountingLines().get(line);

        String errorKey = MMConstants.OrderDocument.NEW_ORDER_DETAIL_ACCOUNTING_LINES + "[" + line
                + "].";
        SpringContext.getBean(BusinessObjectDictionaryService.class).performForceUppercase(newAccountingLine);
        Double lineTotal = orderService.computeLineTotalWithTax(oForm.getOrderDocument()
                .getOrderDetails().get(line)).doubleValue();
        updateAccountingLineAmountValues(newAccountingLine, lineTotal, true);
        if (SpringContext.getBean(OrderService.class).validateAccountingLine(newAccountingLine,
                errorKey)) {
            
            oForm.getOrderDocument().getOrderDetails().get(line).getAccounts().add(
                    newAccountingLine);

            // updateCapitalAssetInfo(oForm.getOrderDocument().getOrderDetails().get(line));
            oForm.getNewOrderDetailAccountingLines().set(line, new Accounts());
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    public ActionForward deleteAccountingLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        OrderForm oForm = (OrderForm) form;

        Integer line = this.getLineToDelete(request);
        if (line < oForm.getOrderDocument().getAccounts().size() && line >= 0)
            oForm.getOrderDocument().getAccounts().remove(line.intValue());

        // Update capital asset info for all order details
        // for (OrderDetail detail : oForm.getOrderDocument().getOrderDetails()) {
        // updateCapitalAssetInfo(detail);
        // }

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    public ActionForward deleteAccountingLineFromLineItem(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        OrderForm oForm = (OrderForm) form;

        Integer line = this.getLineToDelete(request);
        Integer lineItem = this.getSelectedLineItem(request);
        List<Accounts> selectedAccounts = oForm.getOrderDocument().getOrderDetails().get(lineItem)
                .getAccounts();

        if (line < selectedAccounts.size() && line >= 0)
            selectedAccounts.remove(line.intValue());

        oForm.getOrderDocument().getOrderDetails().get(lineItem).setAccounts(selectedAccounts);

        // updateCapitalAssetInfo(oForm.getOrderDocument().getOrderDetails().get(lineItem));

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    /**
     * add the capital asset information
     */
    public ActionForward addCapitalAssetInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        OrderForm oForm = (OrderForm) form;
        Integer selectedLine = getSelectedLine(request);
        if (selectedLine != null
                && selectedLine < oForm.getOrderDocument().getOrderDetails().size()) {
            MMCapitalAssetInformation capitalAssetInformation = oForm.getOrderDocument()
                    .getOrderDetails().get(selectedLine).getCapitalAssetInformation();
            if (capitalAssetInformation == null) {
                return mapping.findForward(MMConstants.MAPPING_BASIC);
            }

            this.addCapitalAssetInfoDetailLines(capitalAssetInformation);
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    /**
     * clear up the capital asset information
     */
    public ActionForward clearCapitalAssetInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        OrderForm oForm = (OrderForm) form;

        Integer selectedLine = getSelectedLine(request);
        if (selectedLine != null
                && selectedLine < oForm.getOrderDocument().getOrderDetails().size()) {
            MMCapitalAssetInformation capitalAssetInformation = oForm.getOrderDocument()
                    .getOrderDetails().get(selectedLine).getCapitalAssetInformation();
            if (capitalAssetInformation == null) {
                return mapping.findForward(MMConstants.MAPPING_BASIC);
            }

            this.resetCapitalAssetInfo(capitalAssetInformation);
        }
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    public ActionForward deleteCapitalAssetInfoDetailLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        OrderForm oForm = (OrderForm) form;

        Integer selectedItem = getSelectedLineItem(request);
        if (selectedItem != null
                && selectedItem < oForm.getOrderDocument().getOrderDetails().size()) {
            MMCapitalAssetInformation capitalAssetInformation = oForm.getOrderDocument()
                    .getOrderDetails().get(selectedItem).getCapitalAssetInformation();
            if (capitalAssetInformation == null) {
                return mapping.findForward(MMConstants.MAPPING_BASIC);
            }

            int lineToDelete = this.getLineToDelete(request);
            List<MMCapitalAssetInformationDetail> detailLines = capitalAssetInformation
                    .getCapitalAssetInformationDetails();

            detailLines.remove(lineToDelete);
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    /**
     * add detail lines into the given capital asset information
     *
     * @param capitalAssetInformation the given capital asset information
     */
    protected void addCapitalAssetInfoDetailLines(MMCapitalAssetInformation capitalAssetInformation) {
        LOG.debug("addCapitalAssetInfoDetailLines() - start");

        if (ObjectUtils.isNull(capitalAssetInformation)) {
            return;
        }

        Integer quantity = capitalAssetInformation.getCapitalAssetQuantity();
        if (quantity == null || quantity <= 0) {
            String errorPath = MMConstants.DOCUMENT + "."
                    + MMConstants.MMCapitalAssetInformation.CAPITAL_ASSET_INFORMATION;
            GlobalVariables.getMessageMap().putError(errorPath,
                    MMKeyConstants.MMCapitalAssetInformation.ERROR_INVALID_CAPITAL_ASSET_QUANTITY);
            return;
        }

        List<MMCapitalAssetInformationDetail> detailLines = capitalAssetInformation
                .getCapitalAssetInformationDetails();
        // If details collection has old lines, this loop will add new lines to make the total equal to the quantity.
        for (int index = 1; detailLines.size() < quantity; index++) {
            MMCapitalAssetInformationDetail detailLine = new MMCapitalAssetInformationDetail();
            detailLines.add(detailLine);
        }
    }

    /**
     * reset the nonkey fields of the given capital asset information
     *
     * @param capitalAssetInformation the given capital asset information
     */
    protected void resetCapitalAssetInfo(MMCapitalAssetInformation capitalAssetInformation) {
        if (capitalAssetInformation != null) {
            capitalAssetInformation.setCapitalAssetDescription(null);
            capitalAssetInformation.setCapitalAssetManufacturerModelNumber(null);
            capitalAssetInformation.setCapitalAssetManufacturerName(null);

            capitalAssetInformation.setCapitalAssetNumber(null);
            capitalAssetInformation.setCapitalAssetTypeCode(null);
            capitalAssetInformation.setCapitalAssetQuantity(null);

            capitalAssetInformation.setVendorDetailAssignedIdentifier(null);
            capitalAssetInformation.setVendorHeaderGeneratedIdentifier(null);
            // Set the BO to null cause it won't be updated automatically when vendorDetailAssetIdentifier and
            // VendorHeanderGeneratedIndentifier set to null.
            capitalAssetInformation.setVendorDetail(null);
            capitalAssetInformation.setVendorName(null);

            capitalAssetInformation.getCapitalAssetInformationDetails().clear();
        }
    }


    private void updateAccountingLineAmountValues(Accounts accountingLine, Double total,
            boolean isNew) {
        if(total <= 0.0)
            return;
        KualiDecimal formAmount = (accountingLine.getFormAmount() != null ? accountingLine.getFormAmount() : KualiDecimal.ZERO);
        if (MMConstants.OrderDocument.OPTION_FXD.equals(accountingLine.getAmountType())) {
            if (isNew)
                accountingLine.setAccountFixedAmt(formAmount);
            Double percent = accountingLine.getAccountFixedAmt().doubleValue() / total;
            accountingLine.setAccountPct(new BigDecimal(percent * 100.0));
            accountingLine.setFormAmount(accountingLine.getAccountFixedAmt());
        }
        else {
            if (isNew)
                accountingLine.setAccountPct(formAmount.bigDecimalValue());
            accountingLine.setAccountFixedAmt(new KualiDecimal(total
                    * (accountingLine.getAccountPct().doubleValue() * new Double(.01))));
            accountingLine.setFormAmount(new KualiDecimal(accountingLine.getAccountPct()));
        }
    }


    /**
     * Recomputes accounting line amount(pct/fxd) and checks for CAMS object codes in case they were changed by the
     *        user after being added. Adds MMCapitalAssetInformation if new CAM object codes are found.
     * @param document 
     */
    private void checkAndUpdateAccountingLineInfo(OrderDocument document) {
        OrderService orderService = SpringContext.getBean(OrderService.class);
        Double orderTotal = new Double(0);
        orderTotal = orderService.computeTotal(document.getOrderDetails()).doubleValue();
        for (Accounts account : document.getAccounts()) {
            updateAccountingLineAmountValues(account, orderTotal, false);
        }
        for (OrderDetail detail : document.getOrderDetails()) {
            // updateCapitalAssetInfo(detail);
            for (Accounts detailAccount : detail.getAccounts()) {
                updateAccountingLineAmountValues(detailAccount, detail.getDetailTotal().doubleValue(), false);
            }
        }

				SpringContext.getBean(BusinessObjectDictionaryService.class).performForceUppercase(document);

    }


    /**
     * If recurring order information exists in the form, add it to the document for validation.
     *
	 * @param recurringOrder
	 * @param orderDocument
	 */
	private void addRecurringOrderInfo(RecurringOrder recurringOrder, OrderDocument orderDocument) {
		if(ObjectUtils.isNull(recurringOrder))
			return;

		if(recurringOrder.getEndDt() != null || recurringOrder.isNoEndDateInd()
				|| recurringOrder.getTimesPerYr() != null || recurringOrder.getStartDt() != null) {
			orderDocument.setRecurringOrderInd(true);
			recurringOrder.setActive(false);
			orderDocument.setRecurringOrder(recurringOrder);
		}
		else {
			orderDocument.setRecurringOrderInd(false);
			orderDocument.setRecurringOrder(null);
			orderDocument.setRecurringOrderId(null);
		}

	}


    protected int getSelectedLineItem(HttpServletRequest request) {
        int selectedLine = -1;
        String parameterName = (String) request.getAttribute(KRADConstants.METHOD_TO_CALL_ATTRIBUTE);
        if (StringUtils.isNotBlank(parameterName)) {
            String lineNumber = StringUtils.substringBetween(parameterName, ".item", ".");
            selectedLine = Integer.parseInt(lineNumber);
        }

        return selectedLine;
    }

    /**
     * If the given form has returnToShopping set to true, this method returns an ActionForward that should take the user back to
     * the Shopping Order Completion; otherwise, it returns them to the portal.
     *
     * @param form
     * @return
     */
    @Override
    protected ActionForward returnToSender(HttpServletRequest request, ActionMapping mapping,
            KualiDocumentFormBase form) {
        ActionForward dest = super.returnToSender(request, mapping, form);
        OrderForm oForm = (OrderForm) form;

        if (StringUtils.isNotBlank(oForm.getReturnToSenderUrl())) {
            dest = new ActionForward(oForm.getReturnToSenderUrl(), true);
        }
        return dest;
    }


}
