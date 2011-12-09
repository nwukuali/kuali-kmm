package org.kuali.ext.mm.cart.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;



public class SettingsAction extends StoresShoppingActionBase {

	public ActionForward changeProfile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SettingsForm sForm = (SettingsForm)form;

        Profile newProfile = (Profile)KNSServiceLocator.getBusinessObjectService().retrieve(sForm.getCurrentProfile());

        sForm.setCustomerProfile(newProfile);
        request.getSession().setAttribute(ShopCartConstants.Session.CUSTOMER_PROFILE, sForm.getCustomerProfile());
        
        resetBrowseManager(sForm, request);

        ShopCartService shopCartService = ShopCartServiceLocator.getShopCartService();
        shopCartService.setShoppingCartCustomerInfo(sForm.getSessionCart(), sForm.getCustomerProfile());
        shopCartService.updateShoppingCartPricingInfo(sForm.getSessionCart(), sForm.getCustomerProfile(), false);
        
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
}
