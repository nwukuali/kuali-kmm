package org.kuali.ext.mm.cart.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.cart.service.ShopCartService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.rice.kns.util.KualiDecimal;

public class SavedCartsForm extends ShopCartFormBase {
	/**
     *
     */
    private static final long serialVersionUID = -1576811780093064045L;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SavedCartsForm.class);

	private String[] selectedItems;

	private String saveShopCartName;

	private boolean savingShopCart;

	private ShoppingCart cartToLoad;

	@Override
    public void populate(HttpServletRequest request) {
        super.populate(request);

        if(StringUtils.isBlank(getSaveShopCartName()))
        	setSaveShopCartName(getSessionCart().getShopCartHeaderName());

        ShopCartService shopCartService = ShopCartServiceLocator.getShopCartService();
        if(getCartToLoad() != null) {
            setSubTotal(shopCartService.computeShopCartSubtotal(getCartToLoad()));
            setTaxTotal(new KualiDecimal(shopCartService.computeShopCartTaxTotal(getCartToLoad())));
            setTotal(shopCartService.computeShopCartTotal(getCartToLoad()));
        }
    }

	@Override
    public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}

	@Override
    public String[] getSelectedItems() {
		return selectedItems;
	}

	public void setCartToLoad(ShoppingCart cartToLoad) {
		this.cartToLoad = cartToLoad;
	}

	public ShoppingCart getCartToLoad() {
		return cartToLoad;
	}

	public void setSaveShopCartName(String saveShopCartName) {
		this.saveShopCartName = saveShopCartName;
	}

	public String getSaveShopCartName() {
		return saveShopCartName;
	}

	public void setSavingShopCart(boolean savingShopCart) {
		this.savingShopCart = savingShopCart;
	}

	public boolean isSavingShopCart() {
		return savingShopCart;
	}

}
