package org.kuali.ext.mm.cart.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.businessobject.CatalogImage;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.util.MMDecimal;



public class ItemForm extends StoresShoppingFormBase {

	/**
     *
     */
    private static final long serialVersionUID = 580476047786250810L;

    private CatalogItem displayItem;

	private CatalogImage displayImage;

	private Integer addQuantity;

	private boolean itemInCart;

	@Override
    public void populate(HttpServletRequest request) {
        super.populate(request);

        if(getAddQuantity() == null)
        	setAddQuantity(1);

        if(getDisplayItem() != null) {
	        setDisplayItem(ShopCartServiceLocator.getShopCartCatalogService().getCatalogItemById(getDisplayItem().getCatalogItemId()));
	        if(!getDisplayItem().getCatalogItemImages().isEmpty())
	        	setDisplayImage(getDisplayItem().getCatalogItemImages().get(0).getCatalogImage());

	        setItemInCart(ShopCartServiceLocator.getShopCartService().isCatalogItemInCart(getDisplayItem(), getSessionCart()));
        }

    }

	public CatalogItem getDisplayItem() {
		return displayItem;
	}

	public void setDisplayItem(CatalogItem displayItem) {
		this.displayItem = displayItem;
	}

	public CatalogImage getDisplayImage() {
		return displayImage;
	}

	public void setDisplayImage(CatalogImage displayImage) {
		this.displayImage = displayImage;
	}

	public Integer getAddQuantity() {
		return addQuantity;
	}

	public void setAddQuantity(Integer addQuantity) {
		this.addQuantity = addQuantity;
	}

	public boolean isItemInCart() {
		return itemInCart;
	}

	public void setItemInCart(boolean itemInCart) {
		this.itemInCart = itemInCart;
	}

	public MMDecimal getItemPrice() {
		return ShopCartServiceLocator.getShopCartCatalogService().computeCatalogItemPrice(displayItem, getCustomerProfile(), 1);
	}

	public Integer getQuantityOnHand() {
		return ShopCartServiceLocator.getShopCartCatalogService().getAvailableQuantity(displayItem);
	}
	
	public boolean isWarehouseItem() {
	    return MMConstants.CatalogType.WAREHOUSE.equals(displayItem.getCatalog().getCatalogTypeCd());
	}

	@Override
    protected boolean requiresProfile() {
        return false;
    }

}
