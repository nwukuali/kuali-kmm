package org.kuali.ext.mm.cart.web.struts;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.ShopCartDetail;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.cart.service.ShopCartService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.util.UrlFactory;


public class SavedCartsAction extends StoresShoppingActionBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SavedCartsAction.class);

	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SavedCartsForm scForm = (SavedCartsForm)form;

        scForm.setCartToLoad(getSavedCartById(request.getParameter(ShopCartConstants.CART_ID), scForm));
        if(ObjectUtils.isNotNull(scForm.getCartToLoad())) {
            scForm.getItemAuthorizationMap().clear();
            Integer i = 1;
            for(ShopCartDetail detail : scForm.getCartToLoad().getShopCartDetails()) {
                detail.setShopCartLineNbr(i++);
//                Boolean isAuthorized = MMServiceLocator.getCatalogService()
//                        .isCatalogAuthorized(detail.getCatalogItem().getCatalog(), scForm.getAuthorizationProfile());
//                scForm.getItemAuthorizationMap().put(detail.getShopCartLineNbr(), isAuthorized);
            }
            scForm.buildItemAuthorizationMap(scForm.getCartToLoad());
            ShopCartService shopCartService = ShopCartServiceLocator.getShopCartService();
            shopCartService.updateShoppingCartPricingInfo(scForm.getCartToLoad(), scForm.getCustomerProfile(), false);
            scForm.setSubTotal(shopCartService.computeShopCartSubtotal(scForm.getCartToLoad()));
            scForm.setTaxTotal(new KualiDecimal(shopCartService.computeShopCartTaxTotal(scForm.getCartToLoad())));
            scForm.setTotal(shopCartService.computeShopCartTotal(scForm.getCartToLoad()));
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward loadCart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SavedCartsForm scForm = (SavedCartsForm)form;
        scForm.setCartToLoad(getSavedCartById(request.getParameter(ShopCartConstants.CART_ID), scForm));

        scForm.getCartToLoad().setSessionCartSaved(true);
        Integer i = 1;
        for(ShopCartDetail detail : scForm.getCartToLoad().getShopCartDetails()) {
            detail.setShopCartLineNbr(i++);
        }
        request.getSession().setAttribute(ShopCartConstants.Session.SESSION_SHOP_CART, scForm.getCartToLoad());

        Properties parameters = new Properties();
        parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.START_METHOD);
		String viewCartUrl = KNSServiceLocator.getKualiConfigurationService().getPropertyString(MMKeyConstants.MM_MODULE_URL) + "/" + "viewCart.do";;


		return new ActionForward(UrlFactory.parameterizeUrl(viewCartUrl, parameters), true);

    }

    public ActionForward prepareToSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SavedCartsForm scForm = (SavedCartsForm)form;
		scForm.setSavingShopCart(true);
		scForm.setCartToLoad(scForm.getSessionCart());

		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward saveCart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SavedCartsForm scForm = (SavedCartsForm)form;

        ShopCartService shopcartService = ShopCartServiceLocator.getShopCartService();
        String originalName = scForm.getSaveShopCartName();
        scForm.getSessionCart().setShopCartHeaderName(scForm.getSaveShopCartName());
        if(shopcartService.validateNewShoppingCart(scForm.getSessionCart())) {
        	ShoppingCart cart = getSavedCartByName(scForm.getSaveShopCartName(), scForm);
        	if(cart != null) {
        		buildSaveConfirmation(scForm, request);
        	}
        	else {
        		 return completeSaveCartAction(scForm, true);
        	}
        }
        scForm.getSessionCart().setShopCartHeaderName(originalName);
		return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward saveConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SavedCartsForm scForm = (SavedCartsForm)form;

        String savedCartName = request.getParameter(ShopCartConstants.SAVED_CART_NAME);
        String confirm = request.getParameter(ShopCartConstants.ACTION_PARM_CONFRIM);
        if(StringUtils.isNotBlank(savedCartName) && StringUtils.isNotBlank(confirm)) {
	        ShoppingCart cart = getSavedCartByName(scForm.getSaveShopCartName(), scForm);
	        scForm.getSessionCart().setShoppingCartId(cart.getShoppingCartId());
	        scForm.getSessionCart().setVersionNumber(cart.getVersionNumber());
	        return completeSaveCartAction(scForm, false);
        }
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward deleteSavedCart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SavedCartsForm scForm = (SavedCartsForm)form;
        ShoppingCart cartToDelete = getSavedCartById(request.getParameter(ShopCartConstants.CART_ID), scForm);

		if(ObjectUtils.isNotNull(cartToDelete) && StringUtils.equals(cartToDelete.getCustomerProfileId(), scForm.getCustomerProfile().getProfileId())) {
			if(StringUtils.isBlank(request.getParameter(ShopCartConstants.ACTION_PARM_CONFRIM))) {
				buildDeleteCartConfirmation(scForm, request, cartToDelete);
			}
			else {
		        ShopCartServiceLocator.getShopCartService().deleteSavedShopCart(cartToDelete);
		        for(ShoppingCart cart : scForm.getSavedCarts()) {
		        	if(cart.getShoppingCartId().equals(cartToDelete.getShoppingCartId())) {
		        		scForm.getSavedCarts().remove(cart);
		        		if(cart.getShopCartHeaderName().equals(scForm.getSessionCart().getShopCartHeaderName())) {
		        			scForm.getSessionCart().setShopCartHeaderName(StringUtils.EMPTY);
		        			scForm.getSessionCart().setSessionCartSaved(false);
		        		}
		        		break;
		        	}
		        }
			}
		}
		return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	private void buildDeleteCartConfirmation(SavedCartsForm scForm, HttpServletRequest request, ShoppingCart cartToDelete) {
		Map<String, String> confirmParameters = new HashMap<String, String>();
		Map<String, String> declineParameters = new HashMap<String, String>();

		scForm.createNewConfirmAction(request);
		scForm.getConfirmAction().setConfirmAction(ShopCartConstants.SAVED_CARTS_ACTION);
		scForm.getConfirmAction().setDeclineAction(ShopCartConstants.SAVED_CARTS_ACTION);
		scForm.getConfirmAction().setMessage(KNSServiceLocator.getKualiConfigurationService().getPropertyString(ShopCartKeyConstants.QUESTION_CONFIRM_DELETE_SAVED_CART));
		confirmParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.DELETE_SAVED_CART_METHOD);
		confirmParameters.put(ShopCartConstants.CART_ID, cartToDelete.getShoppingCartId());
		declineParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.START_METHOD);
		declineParameters.put(ShopCartConstants.CART_ID, cartToDelete.getShoppingCartId());
		scForm.getConfirmAction().setConfirmParameters(confirmParameters);
		scForm.getConfirmAction().setDeclineParameters(declineParameters);
	}

	/**
	 * @param pForm
	 * @param request
	 */
	private void buildSaveConfirmation(SavedCartsForm scForm, HttpServletRequest request) {
		Map<String, String> confirmParameters = new HashMap<String, String>();
		Map<String, String> declineParameters = new HashMap<String, String>();

		scForm.createNewConfirmAction(request);
		scForm.getConfirmAction().setConfirmAction(ShopCartConstants.SAVED_CARTS_ACTION);
		scForm.getConfirmAction().setDeclineAction(ShopCartConstants.SAVED_CARTS_ACTION);
		scForm.getConfirmAction().setMessage(KNSServiceLocator.getKualiConfigurationService().getPropertyString(ShopCartKeyConstants.QUESTION_CONFIRM_CART_SAVE));
		confirmParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.SAVE_CONFRIM_METHOD);
		confirmParameters.put(ShopCartConstants.SAVED_CART_NAME, scForm.getSaveShopCartName());
		declineParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.PREPARE_TO_SAVE_METHOD);
		scForm.getConfirmAction().setConfirmParameters(confirmParameters);
		scForm.getConfirmAction().setDeclineParameters(declineParameters);
	}

	private ShoppingCart getSavedCartById(String cartId, SavedCartsForm scForm) {
		for(ShoppingCart savedCart : scForm.getSavedCarts()) {
        	if(savedCart.getShoppingCartId().equals(cartId)) {
        		return savedCart;
        	}
        }
		return null;
	}

	private ShoppingCart getSavedCartByName(String cartId, SavedCartsForm scForm) {
		for(ShoppingCart cart : scForm.getSavedCarts()) {
        	if(StringUtils.equals(scForm.getSaveShopCartName(), cart.getShopCartHeaderName())) {
        		return cart;
        	}
        }
		return null;
	}

	private ActionForward completeSaveCartAction(SavedCartsForm scForm, boolean newCart) {
		ShopCartService shopcartService = ShopCartServiceLocator.getShopCartService();
		shopcartService.setShoppingCartCustomerInfo(scForm.getSessionCart(), scForm.getCustomerProfile());
		shopcartService.saveShoppingCart(scForm.getSessionCart(), newCart);
        scForm.getSessionCart().setSessionCartSaved(true);

        Properties parameters = new Properties();
        parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.START_METHOD);
        return new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.VIEW_CART_ACTION, parameters), true);
	}

}
