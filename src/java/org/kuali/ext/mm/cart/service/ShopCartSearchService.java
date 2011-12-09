package org.kuali.ext.mm.cart.service;

import java.util.List;

import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.cart.valueobject.Breadcrumb;
import org.kuali.ext.mm.cart.valueobject.BrowseManager;


public interface ShopCartSearchService {

	public void synchronizeBrowserWithResultSet(BrowseManager browseManager, Breadcrumb breadcrumb);

	public List<CatalogItem> getResultSetPageByBreadcrumb(Breadcrumb breadcrumb, String sortField, boolean sortAscending, int pageNumber, int resultsPerPage, int pageCount);
	
	public List<CatalogItem> getBestSellingCatalogItems(Breadcrumb breadcrumb, Integer quantity);
	
	public Breadcrumb buildBreadcrumb(BrowseManager browseManager, String queryDescription, boolean mergeWithLast);

	/**
	 * 
	 * @param keyword
	 * @param catalogId
	 * @return a list of search suggestions based on the keyword and catlagIds
	 */
	public List<String> getSearchSuggestions(String keyword, String catalogId);
	
	public Integer getResultSetByBreadcrumbCount(Breadcrumb breadcrumb);

}
