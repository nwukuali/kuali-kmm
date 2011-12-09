package org.kuali.ext.mm.cart.web.struts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ShopCartDetail;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.cart.service.ShopCartService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;


public class ShopCartFormBase extends StoresShoppingFormBase {
	/**
     *
     */
    private static final long serialVersionUID = 6212163311333601052L;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ShopCartFormBase.class);

	private List<ShoppingCart> savedCarts;

	private Boolean editMode;

	private String[] selectedItems;
	
	private Map<Integer, Boolean> itemAuthorizationMap = new HashMap<Integer, Boolean>();
	
	private KualiDecimal subTotal;
	private KualiDecimal taxTotal;
	private KualiDecimal total;

	@Override
    public void populate(HttpServletRequest request) {
        super.populate(request);

        if(getEditMode() == null)
        	setEditMode(false);

        if(getCustomerProfile() != null) {
            setSavedCarts(ShopCartServiceLocator.getShopCartService().getSavedShoppingCarts(getCustomerProfile()));
            buildItemAuthorizationMap(getSessionCart());
        }
        
        ShopCartService shopCartService = ShopCartServiceLocator.getShopCartService();
        setSubTotal(shopCartService.computeShopCartSubtotal(getSessionCart()));
        setTaxTotal(new KualiDecimal(shopCartService.computeShopCartTaxTotal(getSessionCart())));
        setTotal(shopCartService.computeShopCartTotal(getSessionCart()));
	}
	
	public void buildItemAuthorizationMap(ShoppingCart cart) {
        getItemAuthorizationMap().clear();
        if(!MMUtil.isCollectionEmpty(cart.getShopCartDetails())) {
            for(ShopCartDetail detail : cart.getShopCartDetails()) {
                if(ObjectUtils.isNull(detail.getCatalogItem().getCatalog())) {
                    detail.getCatalogItem().refreshReferenceObject(MMConstants.CatalogItem.CATALOG);
                }                
                Boolean isAuthorized = MMServiceLocator.getCatalogService()
                            .isCatalogAuthorized(detail.getCatalogItem().getCatalog(), getAuthorizationProfile());
                getItemAuthorizationMap().put(detail.getShopCartLineNbr(), isAuthorized);
            }
        }
	}
	
	protected Profile getAuthorizationProfile() {
	    return getCustomerProfile();
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//setSelectedItems( new String[] {});
	}

	public void setSavedCarts(List<ShoppingCart> savedCarts) {
		this.savedCarts = savedCarts;
	}

	public List<ShoppingCart> getSavedCarts() {
		return savedCarts;
	}


	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}

	public Boolean getEditMode() {
		return editMode;
	}

	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}

	public String[] getSelectedItems() {
		return selectedItems;
	}
	

    public Map<Integer, Boolean> getItemAuthorizationMap() {
        return itemAuthorizationMap;
    }

    public void setItemAuthorizationMap(Map<Integer, Boolean> itemAuthorizationMap) {
        this.itemAuthorizationMap = itemAuthorizationMap;
    }
    
    public boolean isAllItemsAuthorized() {
        for(Integer detailNbr : getItemAuthorizationMap().keySet()) {
            if(!getItemAuthorizationMap().get(detailNbr)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the subTotal property
     * @return Returns the subTotal
     */
    public KualiDecimal getSubTotal() {
        return this.subTotal;
    }

    /**
     * Sets the subTotal property value
     * @param subTotal The subTotal to set
     */
    public void setSubTotal(KualiDecimal subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Gets the taxTotal property
     * @return Returns the taxTotal
     */
    public KualiDecimal getTaxTotal() {
        return this.taxTotal;
    }

    /**
     * Sets the taxTotal property value
     * @param taxTotal The taxTotal to set
     */
    public void setTaxTotal(KualiDecimal taxTotal) {
        this.taxTotal = taxTotal;
    }

    /**
     * Gets the total property
     * @return Returns the total
     */
    public KualiDecimal getTotal() {
        return this.total;
    }

    /**
     * Sets the total property value
     * @param total The total to set
     */
    public void setTotal(KualiDecimal total) {
        this.total = total;
    }

}
