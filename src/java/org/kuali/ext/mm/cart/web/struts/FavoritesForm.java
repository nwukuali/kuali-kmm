package org.kuali.ext.mm.cart.web.struts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.CustomerFavDetail;
import org.kuali.ext.mm.businessobject.CustomerFavHeader;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.util.MMDecimal;



public class FavoritesForm extends StoresShoppingFormBase {
	/**
     *
     */
    private static final long serialVersionUID = 4610881877877865806L;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(FavoritesForm.class);

	private String customerId;

	private CustomerFavHeader selectedFavHeader;

	private String selectedFavoriteHeaderId;

	private String newFavoritesName;

	private String displayListTitle;

	private List<CustomerFavDetail> favoriteDetails;

	private boolean showAdd;

	private boolean viewingShared;

	private Map<String, Integer> inCartCount;
	
	private Map<String, MMDecimal> actualPriceMap = new HashMap<String, MMDecimal>();

	public FavoritesForm() {
		setShowAdd(false);
		setFavoriteDetails(new ArrayList<CustomerFavDetail>());
		setInCartCount(new HashMap<String,Integer>());
	}

	@Override
    public void populate(HttpServletRequest request) {
        super.populate(request);

    }

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setSelectedFavHeader(CustomerFavHeader selectedFavHeader) {
		this.selectedFavHeader = selectedFavHeader;
	}

	public CustomerFavHeader getSelectedFavHeader() {
		return selectedFavHeader;
	}

	public void setSelectedFavoriteHeaderId(String selectedFavoriteHeaderId) {
		this.selectedFavoriteHeaderId = selectedFavoriteHeaderId;
	}

	public String getSelectedFavoriteHeaderId() {
		return selectedFavoriteHeaderId;
	}

	public void setNewFavoritesName(String newFavoritesName) {
		this.newFavoritesName = newFavoritesName;
	}

	public String getNewFavoritesName() {
		return newFavoritesName;
	}

	public void setDisplayListTitle(String displayListTitle) {
		this.displayListTitle = displayListTitle;
	}

	public String getDisplayListTitle() {
		return displayListTitle;
	}

	public void setFavoriteDetails(List<CustomerFavDetail> favoriteDetails) {
		this.favoriteDetails = favoriteDetails;
	}

	public List<CustomerFavDetail> getFavoriteDetails() {
		return favoriteDetails;
	}

	public void setShowAdd(boolean showAdd) {
		this.showAdd = showAdd;
	}

	public boolean getShowAdd() {
		return showAdd;
	}

	public void setViewingShared(boolean viewingShared) {
		this.viewingShared = viewingShared;
	}

	public boolean isViewingShared() {
		return viewingShared;
	}

	public void setInCartCount(Map<String, Integer> inCartCount) {
		this.inCartCount = inCartCount;
	}

	public Map<String, Integer> getInCartCount() {
		return inCartCount;
	}

	public Collection<CustomerFavHeader> getAvailableFavorites() {
		String customerId = getCustomerId();

		if(StringUtils.isBlank(customerId) || !isViewingShared() || StringUtils.equals(customerId, getCustomerProfile().getCustomer().getPrincipalName())) {
			customerId = getCustomerProfile().getCustomer().getPrincipalName();
			setViewingShared(false);
		}

		return ShopCartServiceLocator.getShopCartFavoriteService().getFavoritesByCustomerId(customerId, isViewingShared());
	}

    public void setActualPriceMap(Map<String, MMDecimal> actualPriceMap) {
        this.actualPriceMap = actualPriceMap;
    }

    public Map<String, MMDecimal> getActualPriceMap() {
        return actualPriceMap;
    }



}
