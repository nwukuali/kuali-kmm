package org.kuali.ext.mm.cart.service;

import java.util.Collection;

import org.kuali.ext.mm.businessobject.Customer;
import org.kuali.ext.mm.businessobject.CustomerFavDetail;
import org.kuali.ext.mm.businessobject.CustomerFavHeader;

public interface ShopCartFavoriteService {

	/**
	 * Adds the favorite detail to the favorite list if it is not already present.
	 *
	 * @param detail
	 * @param customerFavHeader
	 */
	public void addItemToFavorites(CustomerFavDetail detail, CustomerFavHeader customerFavHeader);

	/**
	 * Removes favorite detail from favorites list if it is present.
	 *
	 * @param customerFavHeader
	 * @param detail
	 */
	public void removeItemFromFavorites(CustomerFavHeader customerFavHeader, CustomerFavDetail detail);

	/**
	 * @param favoritesListName
	 * @param customer
	 * @return new CustomerFavHeader (favorites list) named for favoritesListName associated to customer
	 */
	public CustomerFavHeader createNewFavoritesList(String favoritesListName, Customer customer);

	/**
	 * Deletes favorites list (CustomerFavHeader)
	 *
	 * @param customerFavHeader
	 */
	public void deleteFavoritesList(CustomerFavHeader customerFavHeader);

	/**
	 * Saves favorites list (CustomerFavHeader)
	 *
	 * @param customerFavHeader
	 */
	public void saveFavoritesList(CustomerFavHeader customerFavHeader);

	/**
	 * Sets the customerFavHeader share option to either make the favorites list public or private.
	 *
	 * @param customerFavHeader
	 * @param share
	 */
	public void shareFavoritesList(CustomerFavHeader customerFavHeader, boolean share);

	/**
	 * @param customerFavHeaderId
	 * @param customerId
	 * @return CustomerFavHeader (favorites list) with matching id and customer
	 */
	public CustomerFavHeader getFavoriteHeaderById(String customerFavHeaderId, String customerId);

	/**
	 * @param customerFavHeaderName
	 * @param customerId
	 * @return CustomerFavHeader (favorites list) with matching header name and customer
	 */
	public CustomerFavHeader getFavoriteHeaderByName(String customerFavHeaderName, String customerId);

	/**
	 * @param customerId
	 * @param sharedOnly
	 * @return collection of shared only, or all, CustomerFavHeaders for the customer with customerId
	 */
	public Collection<CustomerFavHeader> getFavoritesByCustomerId(String customerId, boolean sharedOnly);

	/**
	 * @param header
	 * @param itemId
	 */
	public void deleteFavoritesDetail(CustomerFavHeader header, String itemId);

	/**
	 * @param header
	 * @return true if favorites list is valid
	 */
	public boolean validateNewFavoriteList(CustomerFavHeader header);

	/**
	 * Synchronizes favorites list with catalog items to remove items no longer carried.
	 *
	 * @param header
	 */
	public void checkAndUpdateFavoriteDetails(CustomerFavHeader header);

}
