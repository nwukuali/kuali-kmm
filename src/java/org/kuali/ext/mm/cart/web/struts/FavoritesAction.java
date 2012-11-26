package org.kuali.ext.mm.cart.web.struts;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CustomerFavDetail;
import org.kuali.ext.mm.businessobject.CustomerFavHeader;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.cart.service.ShopCartFavoriteService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.UrlFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


public class FavoritesAction extends StoresShoppingActionBase {


	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;

		String favoritesId = request.getParameter(ShopCartConstants.FAVORITES_ID);

		CustomerFavHeader header = ShopCartServiceLocator.getShopCartFavoriteService().getFavoriteHeaderById(favoritesId, favForm.getCustomerProfile().getPrincipalName());
		refreshFavoritesDisplay(favForm, header);

		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward addToFavorites(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;
		favForm.setShowAdd(true);

		favForm.setFavoriteDetails(buildFavoriteDetails(request));
		favForm.setDisplayListTitle("Item(s) to add:");

		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward createNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;

		ShopCartFavoriteService favoriteService = ShopCartServiceLocator.getShopCartFavoriteService();

		CustomerFavHeader header = favoriteService.createNewFavoritesList(favForm.getNewFavoritesName(), favForm.getCustomerProfile().getCustomer());
		if(favoriteService.validateNewFavoriteList(header)) {
			for(CustomerFavDetail detail : buildFavoriteDetails(request)) {
				favoriteService.addItemToFavorites(detail, header);
			}
			favoriteService.saveFavoritesList(header);
			request.getSession().removeAttribute(ShopCartConstants.Session.ADD_TO_FAVORITES_ITEMS);
			refreshFavoritesDisplay(favForm, header);
		}
		else {
			favForm.setShowAdd(true);
			favForm.setFavoriteDetails(buildFavoriteDetails(request));
			favForm.setDisplayListTitle("Item(s) to add:");
		}

		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward addToExisting(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;

		if(favForm.getSelectedFavoriteHeaderId() == ShopCartConstants.FAVORITES_NEW) {
			return createNew(mapping, form, request, response);
		}
		ShopCartFavoriteService favoriteService = ShopCartServiceLocator.getShopCartFavoriteService();
		CustomerFavHeader header = favoriteService.getFavoriteHeaderById(favForm.getSelectedFavoriteHeaderId(), favForm.getCustomerProfile().getPrincipalName());
		for(CustomerFavDetail detail : buildFavoriteDetails(request)) {
			favoriteService.addItemToFavorites(detail, header);
		}
		favoriteService.saveFavoritesList(header);

		request.getSession().removeAttribute(ShopCartConstants.Session.ADD_TO_FAVORITES_ITEMS);
		refreshFavoritesDisplay(favForm, header);

		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward deleteList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;

		if(StringUtils.isBlank(request.getParameter(ShopCartConstants.ACTION_PARM_CONFRIM))) {
			buildDeleteListConfirmation(favForm, request);
		}
		else {
			String favoritesId = request.getParameter(ShopCartConstants.FAVORITES_ID);
			ShopCartFavoriteService favoriteService = ShopCartServiceLocator.getShopCartFavoriteService();
			CustomerFavHeader header = favoriteService.getFavoriteHeaderById(favoritesId, favForm.getCustomerProfile().getPrincipalName());
			if(StringUtils.equals(header.getPrincipalName(), favForm.getCustomerProfile().getPrincipalName()))
				favoriteService.deleteFavoritesList(header);

			refreshFavoritesDisplay(favForm, null);
		}


		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward deleteDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;
		String itemId = String.valueOf(this.getSelectedLine(request));

		ShopCartFavoriteService favoriteService = ShopCartServiceLocator.getShopCartFavoriteService();
		CustomerFavHeader header = favoriteService.getFavoriteHeaderById(favForm.getSelectedFavoriteHeaderId(), favForm.getCustomerProfile().getPrincipalName());
		if(StringUtils.equals(header.getPrincipalName(), favForm.getCustomerProfile().getPrincipalName())) {
			if(StringUtils.isNotBlank(itemId)) {
				favoriteService.deleteFavoritesDetail(header, itemId);
			}
		}

		refreshFavoritesDisplay(favForm, header);

		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward addAllToCart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;

		ShopCartFavoriteService favoriteService = ShopCartServiceLocator.getShopCartFavoriteService();
		CustomerFavHeader header = favoriteService.getFavoriteHeaderById(favForm.getSelectedFavoriteHeaderId(), favForm.getCustomerProfile().getPrincipalName());
		for(CustomerFavDetail detail : header.getCustomerFavDetails())
			ShopCartServiceLocator.getShopCartService().addCatalogItemToSessionCart(favForm.getSessionCart(), favForm.getCustomerProfile(), detail.getCatalogItem(), 1, detail.getCatalogItem().isWillcallInd());

		Properties parameters = new Properties();
        parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.START_METHOD);

		return new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.VIEW_CART_ACTION, parameters), true);
	}

	public ActionForward addToCart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;

		String itemId = String.valueOf(this.getSelectedLine(request));
		if(StringUtils.isNotBlank(itemId)) {
			CatalogItem catalogItem = ShopCartServiceLocator.getShopCartCatalogService().getCatalogItemById(itemId);
			ShopCartServiceLocator.getShopCartService().addCatalogItemToSessionCart(favForm.getSessionCart(), favForm.getCustomerProfile(), catalogItem, 1, catalogItem.isWillcallInd());
		}

		ShopCartFavoriteService favoriteService = ShopCartServiceLocator.getShopCartFavoriteService();
		CustomerFavHeader header = favoriteService.getFavoriteHeaderById(favForm.getSelectedFavoriteHeaderId(), favForm.getCustomerProfile().getPrincipalName());
		refreshFavoritesDisplay(favForm, header);
		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward shareList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;

		ShopCartFavoriteService favoriteService = ShopCartServiceLocator.getShopCartFavoriteService();
		CustomerFavHeader header = favoriteService.getFavoriteHeaderById(favForm.getSelectedFavoriteHeaderId(), favForm.getCustomerProfile().getPrincipalName());
		if(StringUtils.equals(header.getPrincipalName(), favForm.getCustomerProfile().getPrincipalName()))
			favoriteService.shareFavoritesList(header, true);

		refreshFavoritesDisplay(favForm, header);


		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward unshareList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;

		ShopCartFavoriteService favoriteService = ShopCartServiceLocator.getShopCartFavoriteService();
		CustomerFavHeader header = favoriteService.getFavoriteHeaderById(favForm.getSelectedFavoriteHeaderId(), favForm.getCustomerProfile().getPrincipalName());
		if(StringUtils.equals(header.getPrincipalName(), favForm.getCustomerProfile().getPrincipalName()))
			favoriteService.shareFavoritesList(header, false);

		refreshFavoritesDisplay(favForm, header);
		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward viewFavorites(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;

		favForm.setViewingShared(true);

		ShopCartFavoriteService favoriteService = ShopCartServiceLocator.getShopCartFavoriteService();
		CustomerFavHeader header = favoriteService.getFavoriteHeaderById(favForm.getSelectedFavoriteHeaderId(), favForm.getCustomerProfile().getPrincipalName());

		refreshFavoritesDisplay(favForm, header);
		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward resetFavorites(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FavoritesForm favForm = (FavoritesForm)form;

		favForm.setViewingShared(false);
		favForm.setCustomerId(StringUtils.EMPTY);

		ShopCartFavoriteService favoriteService = ShopCartServiceLocator.getShopCartFavoriteService();
		CustomerFavHeader header = favoriteService.getFavoriteHeaderById(favForm.getSelectedFavoriteHeaderId(), favForm.getCustomerProfile().getPrincipalName());

		refreshFavoritesDisplay(favForm, header);
		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	private List<CustomerFavDetail> buildFavoriteDetails(HttpServletRequest request) {
		List<String> selectedItemIds = (List<String>)request.getSession().getAttribute(ShopCartConstants.Session.ADD_TO_FAVORITES_ITEMS);
		List<CustomerFavDetail> details = new ArrayList<CustomerFavDetail>();
		if(selectedItemIds != null) {
			for(String id : selectedItemIds) {
				CustomerFavDetail item = new CustomerFavDetail();
				item.setCatalogItem(ShopCartServiceLocator.getShopCartCatalogService().getCatalogItemById(id));
				item.setCatalogItemId(id);
				item.setActive(true);
				if(item.getCatalogItem() != null)
					details.add(item);
			}
		}
		return details;
	}

	private void refreshFavoritesDisplay(FavoritesForm favForm, CustomerFavHeader header) {
		if(ObjectUtils.isNull(header)) {
			Collection<CustomerFavHeader> headerList = favForm.getAvailableFavorites();
			if(!headerList.isEmpty()) {
				header = headerList.iterator().next();
			}
		}
		if(ObjectUtils.isNull(header)) {
			favForm.setDisplayListTitle("No favorite lists to display.");
		}
		else {
			ShopCartServiceLocator.getShopCartFavoriteService().checkAndUpdateFavoriteDetails(header);
			favForm.setFavoriteDetails(header.getCustomerFavDetails());
			favForm.setSelectedFavoriteHeaderId(header.getCustomerFavId());
			favForm.setSelectedFavHeader(header);
			favForm.setDisplayListTitle("Displaying: " + header.getCustomerFavName());
			if(!StringUtils.equals(header.getPrincipalName(), favForm.getCustomerProfile().getPrincipalName()))
				favForm.setViewingShared(true);
			if(StringUtils.isBlank(favForm.getCustomerId()) && favForm.isViewingShared())
				favForm.setCustomerId(header.getPrincipalName());
			for(CustomerFavDetail detail : header.getCustomerFavDetails()) {
				favForm.getActualPriceMap().put(detail.getCatalogItem().getCatalogItemId(),
				        ShopCartServiceLocator.getShopCartCatalogService().computeCatalogItemPrice(detail.getCatalogItem(), favForm.getCustomerProfile(), 1));
				Integer count = ShopCartServiceLocator.getShopCartService().getCatalogItemInCartCount(detail.getCatalogItem(), favForm.getSessionCart());
				favForm.getInCartCount().put(detail.getCatalogItemId(), count);
			}
		}
	}

	/**
	 * @param favForm
	 * @param request
	 */
	private void buildDeleteListConfirmation(FavoritesForm favForm, HttpServletRequest request) {
		Map<String, String> confirmParameters = new HashMap<String, String>();
		Map<String, String> declineParameters = new HashMap<String, String>();

		favForm.createNewConfirmAction(request);
		favForm.getConfirmAction().setConfirmAction(ShopCartConstants.FAVORITES_ACTION);
		favForm.getConfirmAction().setDeclineAction(ShopCartConstants.FAVORITES_ACTION);
		favForm.getConfirmAction().setMessage(KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(ShopCartKeyConstants.QUESTION_CONFIRM_DELETE_FAVORITE_LIST));
		confirmParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.DELETE_LIST_METHOD);
		confirmParameters.put(ShopCartConstants.FAVORITES_ID, favForm.getSelectedFavoriteHeaderId());
		declineParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.START_METHOD);
		declineParameters.put(ShopCartConstants.FAVORITES_ID, favForm.getSelectedFavoriteHeaderId());
		favForm.getConfirmAction().setConfirmParameters(confirmParameters);
		favForm.getConfirmAction().setDeclineParameters(declineParameters);
	}
}
