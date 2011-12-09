package org.kuali.ext.mm.cart.web.struts;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kns.service.KualiConfigurationService;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;


public class ShopCartOrderCompletionAction extends ShopCartOrdersAction {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward actionForward = super.execute(mapping, form, request, response);
        ShopCartOrderCompletionForm oForm = (ShopCartOrderCompletionForm)form;

        if(orderDocumentsSubmitted(oForm.getOrderDocumentList())) {
			actionForward = new ActionForward("/" + ShopCartConstants.ORDER_SUMMARY_ACTION, true);
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
	 * Retrieves all incomplete orders for the customer and populates the OrderDocumentList which will be used
	 * to display the Order summaries.  if there is only one incomplete OrderDocument, automatically forward the
	 * the user to the OrderDocument for completion.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward complete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    ShopCartOrderCompletionForm oForm = (ShopCartOrderCompletionForm)form;
	    String showAllParam = request.getParameter(ShopCartConstants.ORDER_COMPLETION_SHOW_ALL_PARAM);
	    boolean isShowAll = Boolean.valueOf(showAllParam);
	    
		if(!isShowAll && oForm.getOrderDocumentList() != null && oForm.getOrderDocumentList().size() == 1) {
			String moduleUrl = SpringContext.getBean(KualiConfigurationService.class).getPropertyString(MMKeyConstants.MM_MODULE_URL);
			Properties parameters = new Properties();
			parameters.put(KNSConstants.PARAMETER_DOC_ID, oForm.getOrderDocumentList().get(0).getDocumentNumber());
			parameters.put(KEWConstants.COMMAND_PARAMETER, KEWConstants.DOCSEARCH_COMMAND);
			parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, KNSConstants.DOC_HANDLER_METHOD);
			parameters.put(MMConstants.OrderDocument.RETURN_TO_SENDER_PARAM, moduleUrl + "/" + ShopCartConstants.ORDER_COMPLETION_ACTION);
			ActionForward actionForward = new ActionForward("/" + UrlFactory.parameterizeUrl(MMConstants.ORDER_ACTION, parameters), true);
			actionForward.setModule("");
			return actionForward;
	    }

		if(isShowAll || oForm.getOrderDocumentList() == null || oForm.getOrderDocumentList().isEmpty()) {
    		oForm.setOrderDocumentList(ShopCartServiceLocator.getShopCartOrderService()
                    .getIncompleteOrders(oForm.getCustomerProfile()));
    		request.getSession().setAttribute(ShopCartConstants.Session.ORDER_DOCUMENTS, oForm.getOrderDocumentList());
		}

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
	
	   /**
     * @param orderDocumentList
     * @return true if all order documents are submitted
     */
    protected boolean orderDocumentsSubmitted(List<OrderDocument> orderDocumentList) {
        boolean submitted = true;
        for(OrderDocument doc : orderDocumentList) {
            submitted &= !MMConstants.OrderStatus.INITIATED.equals(doc.getOrderStatusCd());
        }
        return submitted;
    }
}
