package org.kuali.ext.mm.cart.web.struts;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartProfileService;
import org.kuali.ext.mm.cart.service.ShopCartService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.service.AddressService;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;


public class CheckoutAction extends StoresShoppingActionBase {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {


        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward resetAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CheckoutForm cForm = (CheckoutForm)form;

        ShopCartProfileService profileService = ShopCartServiceLocator.getShopCartProfileService();
        cForm.getSessionCart().setShippingAddress(profileService.getShippingAddress(cForm.getCheckOutAsProfile()));
        cForm.getSessionCart().setBillingAddress(profileService.getBillingAddress(cForm.getCheckOutAsProfile()));
        cForm.setProfileId(cForm.getCheckOutAsProfile().getProfileId());

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward changeUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CheckoutForm cForm = (CheckoutForm)form;

        cForm.setValidated(false);

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward submitUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CheckoutForm cForm = (CheckoutForm)form;
        ShopCartProfileService profileService = ShopCartServiceLocator.getShopCartProfileService();
        AddressService addressService = SpringContext.getBean(AddressService.class);

        if(cForm.getSameAsShipping())
        	cForm.getSessionCart().setBillingAddress(profileService.copyAsBillingAddress(cForm.getSessionCart().getShippingAddress()));

        boolean isValid = addressService.validateAddress(cForm.getSessionCart().getShippingAddress(), ShopCartConstants.SessionCart.SHIPPING_ADDRESS);
        isValid &= addressService.validateAddress(cForm.getSessionCart().getBillingAddress(), ShopCartConstants.SessionCart.BILLING_ADDRESS);

        if(isValid) {
        	//Cannot process orders without being able to calculating tax
	        if(SpringContext.getBean(FinancialSystemAdaptorFactory.class).isSystemAvailable()) {
	        	cForm.setValidated(true);
	        	ShopCartServiceLocator.getShopCartService().updateShoppingCartPricingInfo(cForm.getSessionCart(), cForm.getCheckOutAsProfile(), cForm.getCheckOutAsProfile().isPersonalUseIndicator());
	        }
	        else {
	        	//forward to error page if no tax service available
	            request.getSession().setAttribute(ShopCartConstants.Session.SHOPPING_ERROR_MSG, "The system is currently preventing orders from being submitted.  It is recommended that you return to your Cart, save it, and try again later.  Sorry for the inconvenience. ");
	            request.getSession().setAttribute(ShopCartConstants.Session.SHOPPING_ERROR_RETURN_URL, cForm.getShoppingBaseUrl() + "/" + ShopCartConstants.VIEW_CART_ACTION + "?methodToCall=start");
	        	return mapping.findForward(ShopCartConstants.MAPPING_SYSTEM_ERROR);
	        }
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward placeOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
       CheckoutForm cForm = (CheckoutForm)form;
       ActionForward actionForward = mapping.findForward(MMConstants.MAPPING_PORTAL);

       cForm.setValidated(true);
       if(!cForm.getSessionCart().isOrdered()) {
    	   	ShopCartProfileService profileService = ShopCartServiceLocator.getShopCartProfileService();
			String shippingId = profileService.getShippingAddress(cForm.getCheckOutAsProfile()).getAddressId();
			Address billingAddress = profileService.getBillingAddress(cForm.getCheckOutAsProfile());
			String billingId = (billingAddress == null) ? null : billingAddress.getAddressId();
			if(!cForm.getSessionCart().getShippingAddress().equals(profileService.getShippingAddress(cForm.getCheckOutAsProfile()))) {
				shippingId = profileService.saveAddressAsNonActiveRecord(cForm.getSessionCart().getShippingAddress(), cForm.getCheckOutAsProfile());
			}
			if(!cForm.getSessionCart().getBillingAddress().equals(profileService.getBillingAddress(cForm.getCheckOutAsProfile()))) {
				billingId = profileService.saveAddressAsNonActiveRecord(cForm.getSessionCart().getBillingAddress(), cForm.getCheckOutAsProfile());
			}

			cForm.getSessionCart().setShippingAddressId(shippingId);
			cForm.getSessionCart().setBillingAddressId(billingId);
			
			ShopCartService shopCartService = ShopCartServiceLocator.getShopCartService();
            List<OrderDocument> orderDocuments = shopCartService.processShoppingCart(cForm.getSessionCart(), cForm.getCheckOutAsProfile());
            request.getSession().setAttribute(ShopCartConstants.Session.ORDER_DOCUMENTS, orderDocuments);
            
            if(cForm.getCheckOutAsProfile().isPersonalUseIndicator()) {
                actionForward = new ActionForward("/" + ShopCartConstants.ORDER_SUMMARY_ACTION, true);
            }
            else {
            if(!orderDocuments.isEmpty()) {
                Properties parameters = new Properties();
            	parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.COMPLETE_METHOD);
            	actionForward = new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.ORDER_COMPLETION_ACTION, parameters), true);
            	}
            }
            // delete the saved cart if its ordered
            if(StringUtils.isNotBlank(cForm.getSessionCart().getShoppingCartId()))
                shopCartService.deleteSavedShopCart(cForm.getSessionCart());
       }
       cForm.getSessionCart().setOrdered(true);

       return actionForward;
    }
}


