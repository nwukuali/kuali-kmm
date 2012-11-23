package org.kuali.ext.mm.cart.web.struts;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class ShopCartOrdersAction extends StoresShoppingActionBase {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward actionForward = super.execute(mapping, form, request, response);

        if(request.getRequestURI().contains(ShopCartConstants.ORDER_SUMMARY_ACTION)) {
			request.getSession().removeAttribute(ShopCartConstants.Session.ORDER_DOCUMENTS);
        }

        // proceed as usual
        return actionForward;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward summary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	/**
	 * Toggles the showDetails settings for an Order, determining whether or not OrderDetail and Address information
	 * will be visible.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward toggleOrderDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ShopCartOrdersForm oForm = (ShopCartOrdersForm)form;

		String toggle = getTabToToggle(request);
		boolean showDetails = oForm.getShowOrderDetails().get(toggle);
		oForm.getShowOrderDetails().put(toggle, !showDetails);
		
		refreshOrderDocumentList(oForm, request);

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
	
    /**
	 * Prepares to end a recurring order, forwarding the user to the confirmation screen. 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward endRecurrence(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShopCartOrdersForm oForm = (ShopCartOrdersForm)form;

        String documentId = getTabToToggle(request);
        buildEndRecurrenceConfirmation(oForm, request);
        request.getSession().setAttribute(ShopCartConstants.Session.RECURRING_DOC_ID, documentId);
        
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
	
	/**
	 * De-activates the recurring order information for the given order.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward endRecurrenceConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShopCartOrdersForm oForm = (ShopCartOrdersForm)form;

        String documentId = (String)request.getSession().getAttribute(ShopCartConstants.Session.RECURRING_DOC_ID);
        OrderDocument orderDocument = (OrderDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);
        orderDocument.getRecurringOrder().setActive(false);
        KRADServiceLocator.getBusinessObjectService().save(orderDocument.getRecurringOrder());
        
        request.getSession().removeAttribute(ShopCartConstants.Session.RECURRING_DOC_ID);
        
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
	
	/**
	 * Removes the documentId for the recurring order from the session and does nothing because 'No' was
	 * selected on the confirmation screen.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward confirmActionDecline(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    request.getSession().removeAttribute(ShopCartConstants.Session.RECURRING_DOC_ID);

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	/**
	 * Determines how many pages and how many order summaries to show per page.
	 * 
	 * @param oForm
	 * @param request
	 */
	protected void updatePageInfo(ShopCartOrdersForm oForm, HttpServletRequest request) {
		Double dblPageCount = Math.ceil(new Double(oForm.getOrderDocumentList().size()) / new Double(oForm.getItemsPerPage()));
		oForm.setPageCount(dblPageCount.intValue());
		String page = request.getParameter(ShopCartConstants.ACTION_PARM_PAGE);
        if(StringUtils.isNotEmpty(page))
        	oForm.setCurrentPage(Integer.parseInt(page));
		if(oForm.getCurrentPage() == null || oForm.getCurrentPage() < 1)
			oForm.setCurrentPage(1);

        if(oForm.getCurrentPage() > oForm.getPageCount() && oForm.getPageCount() != 0)
        	oForm.setCurrentPage(oForm.getPageCount());
        
        for(OrderDocument document : oForm.getOrderDocumentList()) {
            if(!oForm.getShowOrderDetails().containsKey(document.getDocumentNumber()))
                oForm.getShowOrderDetails().put(document.getDocumentNumber(), false);
            request.getSession().setAttribute(ShopCartConstants.Session.SHOW_ORDER_DETAILS_MAP, oForm.getShowOrderDetails());
        }
	}
	
	/**
	 * Builds the End Recurrence action confirmation to prompt the user.
	 * 
     * @param pForm
     * @param request
     */
    private void buildEndRecurrenceConfirmation(ShopCartOrdersForm oForm, HttpServletRequest request) {
        Map<String, String> confirmParameters = new HashMap<String, String>();
        Map<String, String> declineParameters = new HashMap<String, String>();

        oForm.createNewConfirmAction(request);
        oForm.getConfirmAction().setConfirmAction(ShopCartConstants.ORDER_HISTORY_ACTION);
        oForm.getConfirmAction().setDeclineAction(ShopCartConstants.ORDER_HISTORY_ACTION);
        oForm.getConfirmAction().setMessage(KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(ShopCartKeyConstants.QUESTION_CONFIRM_END_RECURRENCE));
        confirmParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.END_RECURRENCE_CONFRIM_METHOD);
        declineParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.CONFIRM_ACTION_DECLINE_METHOD);
        oForm.getConfirmAction().setConfirmParameters(confirmParameters);
        oForm.getConfirmAction().setDeclineParameters(declineParameters);
    }
    
    /**
     * @param oForm
     * @param request
     */
    protected void refreshOrderDocumentList(ShopCartOrdersForm oForm, HttpServletRequest request) {
        //Currently does nothing at this level        
    }
    
}
