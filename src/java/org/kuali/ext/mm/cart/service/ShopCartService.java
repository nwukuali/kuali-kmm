package org.kuali.ext.mm.cart.service;

import org.kuali.ext.mm.b2b.cxml.types.CXML;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ShopCartDetail;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.cart.valueobject.DirectEntry;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import java.util.List;

public interface ShopCartService {

	/**
	 * Adds the catalogItem to the ShopCartDetail wrapper settings its quantity, willCall status,
	 * computing its price, and finally adding it to the cart.
	 *
	 * @param cart
	 * @param profile
	 * @param catalogItem
	 * @param quantity
	 * @param willCall
	 */
	public void addCatalogItemToSessionCart(ShoppingCart cart, Profile profile, CatalogItem catalogItem, Integer quantity, boolean willCall);
	
	/**
	 * Adds a ShopCartDetail from punch out orders to the session cart. 
	 * 
	 * @param detail
	 * @param sessionCart
	 * @param cxml
	 */
	public void addPunchOutShoppingCartDetailToSessionCart(ShopCartDetail detail, ShoppingCart cart, CXML cxml);
	
	/**
	 * @param trueBuyoutItem
	 * @param cart - sessionCart
	 */
	public void addTrueBuyoutItemToSessionCart(DirectEntry trueBuyoutItem, ShoppingCart cart);

	/**
	 * Finds the ShopCartDetail object in the carts shopCartDetails list.
	 *
	 * @param cart
	 * @param shopCartDetailId
	 * @return a ShopCartDetail object from cart matching the shopCartDetailId
	 */
	public ShopCartDetail getSessionCartDetailById(ShoppingCart cart, String shopCartDetailId);

	/**
	 * Deletes cart (a saved shopping cart) from the database.
	 *
	 * @param cart
	 */
	public void deleteSavedShopCart(ShoppingCart cart);

	/**
	 * Saves or updates an already saved cart in the database
	 *
	 * @param cart
	 * @param asNew
	 */
	public void saveShoppingCart(ShoppingCart cart, boolean asNew);

	/**
	 * @param detail
	 * @return The line total without tax
	 */
	public Double computeShopCartLineTotal(ShopCartDetail detail);

	/**
	 * @param cart
	 * @return The sub-total of all the lines in the shopping cart
	 */
	public KualiDecimal computeShopCartSubtotal(ShoppingCart cart);

	/**
	 * @param cart
	 * @return The total tax amount for all the lines in the shopping cart
	 */
	public Double computeShopCartTaxTotal(ShoppingCart cart);

	/**
	 * @param cart
	 * @return The total amount cost of the shopping cart.  Sub-total + tax.
	 */
	public KualiDecimal computeShopCartTotal(ShoppingCart cart);

	/**
	 * Checks for de-activated catalogItem and updates the ShopCartDetail if
	 * possible.
	 *
	 * @param detail - ShopCartDetail to be synchronized
	 * @return an unchanged ShopCartDetail if there is no change to catalogItem.
	 * A de-activated ShopCartDetail if no matches found.  An updated ShopCartDetail
	 * pointing to the new version of CatalogItem matching the Distributor Number and
	 * Catalog Code.
	 */
	public ShopCartDetail synchronizeWithCatalogItem(ShopCartDetail detail);

	/**
	 * Checks to see if any pricing information should be udpated on the cart
	 * due to cart and system changes such as a quantity change or a markup change.
	 * If so, recomputes and updates the affected pricing info. Also computes the tax
	 * if requested.
	 *
	 * @param cart
	 * @param profile
	 * @param withTax
	 */
	public void updateShoppingCartPricingInfo(ShoppingCart cart, Profile profile, boolean withTax);

	/**
	 * @param customerProfile
	 * @return a list of saved ShoppingCart objects for the customerProfile
	 */
	public List<ShoppingCart> getSavedShoppingCarts(Profile customerProfile);

	/**
	 * @param item
	 * @param cart
	 * @return true if item is found in cart
	 */
	public boolean isCatalogItemInCart(CatalogItem item, ShoppingCart cart);

	/**
	 * @param item
	 * @param cart
	 * @return The quantity of CatalogItem item found in cart
	 */
	public Integer getCatalogItemInCartCount(CatalogItem item, ShoppingCart cart);

	/**
	 * @param cart
	 * @param lineNumber
	 * @return The shoppingCartDetail from cart by the lineNumber
	 */
	public Object getSessionCartDetailByLineNumber(ShoppingCart cart, Integer lineNumber);

	/**
	 * Updates the customer related information on the shopping cart.
	 *
	 * @param cart - ShoppingCart to update
	 * @param profile - profile to update from
	 * @return The updated ShoppingCart
	 */
	public ShoppingCart setShoppingCartCustomerInfo(ShoppingCart cart, Profile profile);

	/**
	 * Divides the shopCartDetails of cart into OrderDocuments by warehouse and vendor information
	 * (Stock items and non-stock items), then routes each document for approval.
	 *
	 * @param cart
	 * @param customerProfile
	 * @return list of OrderDocuments that were created for ShoppingCart
	 */
	public List<OrderDocument> processShoppingCart(ShoppingCart cart, Profile customerProfile);


	/**
	 * @param sessionCart
	 * @return true if the sessionCart is valid for checkout and processing
	 */
	public boolean validateNewShoppingCart(ShoppingCart sessionCart);

    /**
     * @param cart
     */
    public void cleanShoppingCartPunchOutMap(ShoppingCart cart);

}
