package org.kuali.ext.mm.cart.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.cart.valueobject.BrowseManager;
import org.kuali.ext.mm.common.sys.MMConstants;


public class RecurringAction extends StoresShoppingActionBase {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShopCartHomeForm hForm = (ShopCartHomeForm)form;

        hForm.setBrowseManager(new BrowseManager(hForm.getCustomerProfile()));
        request.getSession().setAttribute("browseManager", hForm.getBrowseManager());

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }


}
