package org.kuali.ext.mm.service;

import java.util.Collection;
import java.util.List;

import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogGroup;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CatalogSubgroup;
import org.kuali.ext.mm.businessobject.CatalogSubgroupItem;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.util.MMDecimal;

public interface CatalogService {

	public MMDecimal computeCatalogItemPrice(CatalogItem item, Profile profile, Integer quantity);
	
	public MMDecimal computeCatalogItemPriceNoPersonalUse(CatalogItem item, Profile profile, Integer quantity);

	public MMDecimal computeCatalogItemPriceByIds(String distributorNumber, String catalogId, String profileId, Integer quantity);
	
//	public MMDecimal computeCatalogItemTax(MMDecimal priceAmount, Address shippingAddress, boolean taxable);
	
	public MMDecimal computeCatalogItemTax(CatalogItem item, MMDecimal priceAmount, Address shippingAddress, boolean willCall);

	public Catalog getCatalogById(String catalogId);

	public CatalogItem getCatalogItemById(String catalogItemId);

	public CatalogItem getCatalogItem(String distributorNumber, String catalogId);

	public Collection<CatalogGroup> getCatalogGroups();

	public CatalogGroup getCatalogGroupbyId(String groupId);

	public CatalogSubgroup getCatalogSubgroupbyId(String subgroupId);

	public List<CatalogSubgroup> getDirectSubgroupsFromCatalogGroup(CatalogGroup group);

	public Collection<CatalogSubgroupItem> getCatalogItemSubgroups(CatalogItem item);
	
	public List<List<CatalogSubgroup>> getCatalogItemSubgroupHierarchy(CatalogItem item);
	
	/**
	 * @param catalogCode
	 * @return
	 */
	public Catalog getCurrentCatalogByCatalogCode(String catalogCode);

	/**
	 * @param catalog
	 * @param customerProfile
	 * @return true if profile is authorized to access catalog
	 */
	public boolean isCatalogAuthorized(Catalog catalog, Profile customerProfile);
	
	/**
	 * @param item
	 * @param chartCode
	 * @param accountNumber
	 * @param orgCode
	 * @return true if the catalog item is from an authorized catalog
	 */
	public boolean isCatalogItemAuthorized(CatalogItem item, String chartCode, String accountNumber,  String orgCode);
	
	/**
	 * @param customerProfile
	 * @param unrestricted - boolean flag whether to apply authorization
	 * @return a collection of available true buyout catalogs
	 */
	public Collection<Catalog> getTrueBuyoutCatalogs(Profile customerProfile, boolean unrestricted);

}
