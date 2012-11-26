package org.kuali.ext.mm.cart.web.struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;


public class SearchAction extends StoresShoppingActionBase {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SearchForm searchForm = (SearchForm)form;

        //searchForm.setBrowseManager(new BrowseManager(searchForm.getCustomerProfile()));
        //request.getSession().setAttribute("browseManager", searchForm.getBrowseManager());
        searchForm.getBrowseManager().getAdvancedSearch().reset();

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Properties parameters = new Properties();

        parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.ADVACNED_SEARCH_METHOD);

		return new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.BROWSE_ACTION, parameters), true);

    }


}
