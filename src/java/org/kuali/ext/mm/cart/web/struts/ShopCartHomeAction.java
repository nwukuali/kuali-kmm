package org.kuali.ext.mm.cart.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.cart.service.ShopCartSearchService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.cart.valueobject.Breadcrumb;
import org.kuali.ext.mm.cart.valueobject.BrowseManager;
import org.kuali.ext.mm.common.sys.MMConstants;


public class ShopCartHomeAction extends StoresShoppingActionBase {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ShopCartHomeForm hForm = (ShopCartHomeForm)form;
        // proceed as usual
		if(StringUtils.isBlank(findMethodToCall(form, request))) {
			hForm.setBrowseManager(new BrowseManager(hForm.getCustomerProfile()));
			request.getSession().setAttribute("browseManager", hForm.getBrowseManager());
		}
		
		ShopCartSearchService searchService = ShopCartServiceLocator.getShopCartSearchService();
        Breadcrumb bestSellersBC = searchService.buildBreadcrumb(new BrowseManager(hForm.getCustomerProfile()), "Best Selling List", false);
            
        hForm.setBestSellingList(searchService.getBestSellingCatalogItems(bestSellersBC, 8));
		
        return super.execute(mapping, form, request, response);
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShopCartHomeForm hForm = (ShopCartHomeForm)form;

        hForm.setBrowseManager(new BrowseManager(hForm.getCustomerProfile()));
        request.getSession().setAttribute("browseManager", hForm.getBrowseManager());
        
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }


}
