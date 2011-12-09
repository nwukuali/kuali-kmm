package org.kuali.ext.mm.cart.service;

import java.util.Collection;
import java.util.List;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.service.CatalogService;

public interface ShopCartCatalogService extends CatalogService{

	public Collection<Catalog> getAvailableCatalogs(Profile customerProfile);
	
	public Collection<Catalog> getAvailableCatalogsPunchOut(Profile customerProfile);
	
	public Integer getAvailableQuantity(CatalogItem item);

	/**
	 * Parse catalog Id String into catalogId List.
	 * 
	 * @param catalogIdString
	 * @return List of Catalog Ids from a CatalogId String. 
	 */
	public List<String> parseCatalogIdStringToList(String catalogIdString);
	
	public List<String> getCatalogGroupCodesForItem(CatalogItem item);

}
