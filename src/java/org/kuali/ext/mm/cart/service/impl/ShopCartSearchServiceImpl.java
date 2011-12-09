package org.kuali.ext.mm.cart.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.CatalogGroup;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CatalogSubgroup;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.businessobject.CatalogItemSearch;
import org.kuali.ext.mm.cart.dataaccess.ShopCartBusinessObjectDao;
import org.kuali.ext.mm.cart.dataaccess.ShopCartSearchDao;
import org.kuali.ext.mm.cart.service.ShopCartCatalogService;
import org.kuali.ext.mm.cart.service.ShopCartSearchService;
import org.kuali.ext.mm.cart.service.ShopCartSearchStringBuilder;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.cart.valueobject.AdvancedSearch;
import org.kuali.ext.mm.cart.valueobject.Breadcrumb;
import org.kuali.ext.mm.cart.valueobject.BrowseManager;
import org.kuali.ext.mm.cart.valueobject.PagingElement;
import org.kuali.ext.mm.cart.valueobject.SortElement;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.dataaccess.QueryElement;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.util.ObjectUtils;

import edu.emory.mathcs.backport.java.util.Arrays;

public class ShopCartSearchServiceImpl implements ShopCartSearchService {

	private ShopCartBusinessObjectDao shopCartBusinessObjectDao;
	
	private ShopCartSearchDao shopCartSearchDao;

	private ShopCartCatalogService shopCartCatalogService;

	public void setShopCartBusinessObjectDao(ShopCartBusinessObjectDao shopCartBusinessObjectDao) {
		this.shopCartBusinessObjectDao = shopCartBusinessObjectDao;
	}

	public ShopCartBusinessObjectDao getShopCartBusinessObjectDao() {
		return shopCartBusinessObjectDao;
	}

	public ShopCartSearchDao getShopCartSearchDao() {
        return shopCartSearchDao;
    }

    public void setShopCartSearchDao(ShopCartSearchDao shopCartSearchDao) {
        this.shopCartSearchDao = shopCartSearchDao;
    }

    public ShopCartCatalogService getShopCartCatalogService() {
		return shopCartCatalogService;
	}

	public void setShopCartCatalogService(ShopCartCatalogService shopCartCatalogService) {
		this.shopCartCatalogService = shopCartCatalogService;
	}
	
	public Breadcrumb buildBreadcrumb(BrowseManager browseManager, String queryDescription, boolean mergeWithLast) {
        Breadcrumb breadcrumb = new Breadcrumb(queryDescription);
        if(browseManager.getSelectedGroup() != null)
            breadcrumb.setCatalogGroup(browseManager.getSelectedGroup());
        if(browseManager.getSelectedSubgroup() != null)
            breadcrumb.setCatalogSubgroup(browseManager.getSelectedSubgroup());

        AdvancedSearch search = browseManager.getAdvancedSearch();
        
        if(StringUtils.isBlank(browseManager.getSelectedCatalogId()))
            browseManager.setSelectedCatalogId(browseManager.getAvailableCatalogIdString());
        breadcrumb.setCatalogIdList(browseManager.getSelectedCatalogIdList());

        if(mergeWithLast && browseManager.getBreadcrumbs().size() > 0) {
            AdvancedSearch lastSearch = browseManager.getBreadcrumbs().get(browseManager.getBreadcrumbs().size()-1).getAdvancedSearch();
            search.setAllKeywords((search.getAllKeywords() + " " + lastSearch.getAllKeywords()).trim());
            search.setAnyKeywords((search.getAnyKeywords() + " " + lastSearch.getAnyKeywords()).trim());
            search.setNoneKeywords((search.getNoneKeywords() + " " + lastSearch.getNoneKeywords()).trim());
        }
        breadcrumb.setAdvancedSearch(new AdvancedSearch(search));
        
        return breadcrumb;
    }

    public Integer getResultSetByBreadcrumbCount(Breadcrumb breadcrumb) {
        QueryElement distNumberSearch = buildDistributorNumberSearch(breadcrumb);
        QueryElement descriptionSearch = buildDescriptionSearch(breadcrumb);
        if(distNumberSearch == null || descriptionSearch == null)
            return 0;
        String[] primaryKeys = { ShopCartConstants.CatalogItemSearch.CATALOG_ITEM_ID };
        
        int count = getShopCartBusinessObjectDao().countMatchingDistinct(CatalogItemSearch.class, distNumberSearch, primaryKeys);
        if(count == 0)
            count = getShopCartBusinessObjectDao().countMatchingDistinct(CatalogItemSearch.class, descriptionSearch, primaryKeys);
        return count;
    }
	
	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartSearchService#getResultSetPageByBreadcrumb(org.kuali.ext.mm.cart.businessobject.Breadcrumb, java.lang.String, boolean, int, int, int)
	 */
	public List<CatalogItem> getResultSetPageByBreadcrumb(Breadcrumb breadcrumb, String sortField, boolean sortAscending, int pageNumber, int resultsPerPage, int pageCount) {
        QueryElement distNumberSearch = buildDistributorNumberSearch(breadcrumb);
        QueryElement descriptionSearch = buildDescriptionSearch(breadcrumb);
        if(distNumberSearch == null || descriptionSearch == null)
            return new ArrayList<CatalogItem>();
        
        SortElement sortElement = new SortElement();
        if(StringUtils.isEmpty(sortField))
            sortElement.addField(ShopCartConstants.CatalogItemSearch.PRIORITY_NUMBER, true);
        else
            sortElement.addField(sortField, sortAscending);
        
        sortElement.addField(ShopCartConstants.CatalogItemSearch.DISTRIBUTOR_NBR, true);
        
        PagingElement pagingElement = new PagingElement(pageNumber, resultsPerPage, pageCount);
        
        Collection rawResults = getShopCartBusinessObjectDao().findMatchingPerPageBoundedAndOrderBy(CatalogItemSearch.class, distNumberSearch, sortElement, pagingElement);
        if(MMUtil.isCollectionEmpty(rawResults)) {
            rawResults = getShopCartBusinessObjectDao().findMatchingPerPageBoundedAndOrderBy(CatalogItemSearch.class, descriptionSearch, sortElement, pagingElement);
        }
        return buildResultsList(rawResults);
    }
	
	/**
     * @param breadcrumb
     * @return QueryElement built for a search on the Catalog Item distributor number
     */
    private QueryElement buildDistributorNumberSearch(Breadcrumb breadcrumb) {
        QueryElement rootElement = buildQueryElementBase(breadcrumb);
        if(isValidAdvancedSearch(breadcrumb.getAdvancedSearch())) {
            String keyword = breadcrumb.getAdvancedSearch().getAllKeywords();
            rootElement.getAndList().add(new QueryElement(ShopCartConstants.CatalogItemSearch.DISTRIBUTOR_NBR, keyword, true));
        }
        else
            return null;
        
        return rootElement;
    }
    
    /**
     * @param breadcrumb
     * @return QueryElement built for a search on the Catalog Item description
     */
    private QueryElement buildDescriptionSearch(Breadcrumb breadcrumb) {
        QueryElement rootElement = buildQueryElementBase(breadcrumb);
        if(isValidAdvancedSearch(breadcrumb.getAdvancedSearch())) {
            QueryElement qeAdvancedSearch = buildQueryElementFromAdvancedSearch(breadcrumb.getAdvancedSearch());
            if(qeAdvancedSearch != null) {
                rootElement.getAndList().add(qeAdvancedSearch);
            }
        }
        else
            return null;
        
        return rootElement;
    }
    
    /**
     * @param advancedSearch
     * @return True if advancedSearch is either all blank, has no fields with less than two characters, and is not null.
     */
    private boolean isValidAdvancedSearch(AdvancedSearch advancedSearch) {
        boolean isValid = true;
        if(advancedSearch == null)
            return false;

        if(StringUtils.isNotBlank(advancedSearch.getAllKeywords()))
            isValid &= advancedSearch.getAllKeywords().length() > 2;
        if(StringUtils.isNotBlank(advancedSearch.getAnyKeywords()))
            isValid &= advancedSearch.getAnyKeywords().length() > 2;
        if(StringUtils.isNotBlank(advancedSearch.getNoneKeywords()))
            isValid &= advancedSearch.getNoneKeywords().length() > 2;

        return isValid;
    }

	/**
	 * @param rawResults
	 * @return a list of CatalogItems
	 */
	protected List<CatalogItem> buildResultsList(Collection rawResults) {
        List<CatalogItem> results = new ArrayList<CatalogItem>();
        for(Object item : rawResults) {
            CatalogItem currentItem = null;
            if(item instanceof CatalogItemSearch) {
                currentItem = ((CatalogItemSearch)item).getCatalogItem();
            }
            else if(item instanceof CatalogItem) {
                currentItem = (CatalogItem)item;
            }
            
            results.add(currentItem);
        }
        return results;
	}

	/**
	 * @param breadcrumb
	 * @return A QueryElement built from breadcrumb matching base search criteria for a catalog item search.
	 */
	private QueryElement buildQueryElementBase(Breadcrumb breadcrumb) {
        QueryElement rootElement = new QueryElement(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
        rootElement.getAndList().add(new QueryElement(ShopCartConstants.CatalogItemSearch.DISPLAYABLE_IND, "Y"));
        
        rootElement.getAndList().add(new QueryElement(MMConstants.CatalogItem.CATALOG_ID, breadcrumb.getCatalogIdList()));

        if(!ObjectUtils.isNull(breadcrumb.getCatalogGroup()) || !ObjectUtils.isNull(breadcrumb.getCatalogSubgroup())) {
            if(ObjectUtils.isNull(breadcrumb.getCatalogGroup()))
                rootElement.getAndList().add(new QueryElement(ShopCartConstants.CatalogItemSearch.CATALOG_GROUP_CD, breadcrumb.getCatalogSubgroup().getCatalogGroup().getCatalogGroupCd()));
            else
                rootElement.getAndList().add(new QueryElement(ShopCartConstants.CatalogItemSearch.CATALOG_GROUP_CD, breadcrumb.getCatalogGroup().getCatalogGroupCd()));
    
            if(!ObjectUtils.isNull(breadcrumb.getCatalogSubgroup()))
                rootElement.getAndList().add(new QueryElement(ShopCartConstants.CatalogItemSearch.CATALOG_SUBGROUP_ID, getSubgroupChildrenIds(breadcrumb.getCatalogSubgroup())));
        }
        return rootElement;
    }

	/**
	 * 
	 * @param advancedSearch
	 * @return 
	 */
	private QueryElement buildQueryElementFromAdvancedSearch(AdvancedSearch advancedSearch) {
		QueryElement rootElement = new QueryElement();
		String searchString = "";
		ShopCartSearchStringBuilder stringBuilder = ShopCartServiceLocator.getShopCartSearchStringBuilder();
		if(StringUtils.isNotBlank(advancedSearch.getAllKeywords()))
		    searchString = stringBuilder.appendAsAllWordsString(searchString, advancedSearch.getAllKeywords());
		if(StringUtils.isNotBlank(advancedSearch.getAnyKeywords()))
		    searchString = stringBuilder.appendAsAnyWordsString(searchString, advancedSearch.getAnyKeywords());
		if(StringUtils.isNotBlank(advancedSearch.getExactKeywords()))
		    searchString = stringBuilder.appendAsExactWordsString(searchString, advancedSearch.getExactKeywords());
		if(StringUtils.isNotBlank(advancedSearch.getNoneKeywords()))
            searchString = stringBuilder.appendAsNotWordsString(searchString, advancedSearch.getNoneKeywords());
		
		if(searchString.isEmpty())
		    return null;
		
		rootElement = new QueryElement(ShopCartConstants.CatalogItemSearch.CATALOG_DESC, searchString, false, false);
        rootElement.setTextIndexSearch(true);
        
		return rootElement;
	}

    /**
     * This embedded class is used as a place holder for CatalogSubgroup objects
     * in the synchronizeBrowserWithResultSet process, to avoid mixing 
     * value objects with persistable business objects.
     */
    private class SubgroupResults {
        public String groupCode;
        public String subgroupId;
        public String priorSubgroupId;
        public Integer count;
        public List<String> subgroupChildIds = new ArrayList<String>();
        
        public SubgroupResults() {}
        
        public SubgroupResults(SubgroupResults copy) {
            this.groupCode = copy.groupCode;
            this.subgroupId = copy.subgroupId;
            this.priorSubgroupId = copy.priorSubgroupId;
            this.count = copy.count;
            this.subgroupChildIds = copy.subgroupChildIds;
        }
    }
	
	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartSearchService#synchronizeBrowserWithResultSet(org.kuali.ext.mm.cart.businessobject.BrowseManager, org.kuali.ext.mm.cart.businessobject.Breadcrumb)
	 */
	public void synchronizeBrowserWithResultSet(BrowseManager browseManager, Breadcrumb breadcrumb) {
		AdvancedSearch search = breadcrumb.getAdvancedSearch();
		if(search == null)
			search = new AdvancedSearch();
		else if(!isValidAdvancedSearch(search))
			return;

		QueryElement distNumberSearch = buildDistributorNumberSearch(breadcrumb);
        QueryElement descriptionSearch = buildDescriptionSearch(breadcrumb);
		
		String countColumn = "count(" + ShopCartConstants.CatalogItemSearch.CATALOG_SUBGROUP_ID + ")";
		String[] attributes = {ShopCartConstants.CatalogItemSearch.CATALOG_GROUP_CD, ShopCartConstants.CatalogItemSearch.CATALOG_SUBGROUP_ID, ShopCartConstants.CatalogItemSearch.PRIOR_CATALOG_SUBGROUP_ID, countColumn};
		String[] orderByList = {countColumn};
		String[] groupByList = {ShopCartConstants.CatalogItemSearch.CATALOG_SUBGROUP_ID, ShopCartConstants.CatalogItemSearch.PRIOR_CATALOG_SUBGROUP_ID, ShopCartConstants.CatalogItemSearch.CATALOG_GROUP_CD};

		Collection<Object[]> reportResults = getShopCartBusinessObjectDao().getReport(CatalogItemSearch.class, distNumberSearch, attributes, groupByList, orderByList, false, -1);
		if(MMUtil.isCollectionEmpty(reportResults)) {
		    reportResults = getShopCartBusinessObjectDao().getReport(CatalogItemSearch.class, descriptionSearch, attributes, groupByList, orderByList, false, -1);
		}
		Map<String, SubgroupResults> subgroupMap = new HashMap<String, SubgroupResults>();
		Map<String, CatalogGroup> catalogGroupMap = new HashMap<String, CatalogGroup>();
		
		buildGroupSubgroupMaps(reportResults, subgroupMap, catalogGroupMap);		
		setBrowserValues(browseManager, breadcrumb, subgroupMap, catalogGroupMap);
	}

    /**
     * Builds the catalogGroupList and subgroupMap for all subgroupIds found in reportResults.
     * 
     * reportResults is intended to be a collection of Object[3] where:
     * Object[0] = Group Code
     * Object[1] = Catalog Subgroup Id
     * Object[2] = Prior Catalog Subgroup Id
     * Object[3] = Number of results from the search found in each CatalogSubgroup
     * 
     * @param reportResults
     * @param subgroupMap
     * @param catalogGroupList
     */ 
    private void buildGroupSubgroupMaps(Collection<Object[]> reportResults, Map<String, SubgroupResults> subgroupMap, Map<String, CatalogGroup> catalogGroupMap) {
        List<SubgroupResults> rootSubgroupList = new ArrayList<SubgroupResults>();
        for(Object[] record : reportResults) {
            if(!StringUtils.isNumeric(String.valueOf(record[1])))
                continue;
            SubgroupResults subgroupPH = new SubgroupResults();
            subgroupPH.groupCode = String.valueOf(record[0]);
            subgroupPH.subgroupId = String.valueOf(record[1]);
            subgroupPH.priorSubgroupId = (record[2] == null) ? null : String.valueOf(record[2]);
            subgroupPH.count = 0;
            
            SubgroupResults currentParent = updateAndReturnSubgroupRoot(subgroupPH, subgroupMap, Integer.parseInt(String.valueOf(record[3])));
            if(!rootSubgroupList.contains(currentParent))
                rootSubgroupList.add(currentParent);               
        }
        
        for(SubgroupResults root : rootSubgroupList) {
            CatalogSubgroup rootSubgroup = new CatalogSubgroup();
            rootSubgroup.setCatalogSubgroupId(root.subgroupId);
            if(catalogGroupMap.containsKey(root.groupCode)) {
                Integer currentCount = catalogGroupMap.get(root.groupCode).getResultSetCount();                
                catalogGroupMap.get(root.groupCode).setResultSetCount(currentCount + root.count);
            }
            else {
                CatalogGroup catalogGroup = new CatalogGroup();
                catalogGroup.setCatalogGroupCd(root.groupCode);
                catalogGroup = (CatalogGroup)getShopCartBusinessObjectDao().retrieve(catalogGroup);
                catalogGroup.setCatalogSubgroups(new ArrayList<CatalogSubgroup>());
                catalogGroup.setResultSetCount(root.count);                
                catalogGroupMap.put(root.groupCode, catalogGroup);                
            }
            catalogGroupMap.get(root.groupCode).getCatalogSubgroups().add(rootSubgroup);
        }
    }
    
    /**
     * Updates the subgroupMap for the subgroup and its parents.
     * If subgroup is not in the map already, add it and any parents not already in the map.
     * Add resultCount to each parent node's current resultSetCount. 
     *  
     * @param subgroup
     * @param subgroupMap
     * @param resultCount
     * @return A CatalogSubgroup object which is the root node of the parent tree for the given subgroup, 
     * having a resultSetCount equal to the sum of the resultSetCount of all child nodes.
     */
    private SubgroupResults updateAndReturnSubgroupRoot(SubgroupResults subgroup, Map<String, SubgroupResults> subgroupMap, Integer resultCount) {
        SubgroupResults parent = new SubgroupResults(subgroup);
        SubgroupResults current = null; 
        
        while(ObjectUtils.isNotNull(parent)) {
            current = new SubgroupResults(parent);
            String key = current.subgroupId;
            if(subgroupMap.containsKey(key)) {
                current = subgroupMap.get(key);
                current.count += resultCount;
                subgroupMap.put(key, current);
            }
            else {                
                current.count += resultCount;
                subgroupMap.put(key, current);
            }
            String parentKey = current.priorSubgroupId;
            if(subgroupMap.containsKey(parentKey)) {
                parent = subgroupMap.get(parentKey);
                parent.subgroupChildIds.add(current.subgroupId);
            }
            else if(current.priorSubgroupId != null) {
                CatalogSubgroup subgroupParent = (CatalogSubgroup)getShopCartBusinessObjectDao().findBySinglePrimaryKey(CatalogSubgroup.class, current.priorSubgroupId);
                parent = new SubgroupResults();
                parent.groupCode = subgroupParent.getCatalogGroupCd();
                parent.subgroupId = subgroupParent.getCatalogSubgroupId();
                parent.priorSubgroupId = subgroupParent.getPriorCatalogSubgroupId();
                parent.subgroupChildIds.add(current.subgroupId);
                parent.count = 0;
            }
            else {
                parent = null;
            }
        }
        return current;
    }
    
    /**
     * A recursive method to get all child subgroup Ids.
     * 
     * @param subgroup
     * @return a list of subgroup all catalog subgroup children Ids for the given subgroup
     */
    private List<String> getSubgroupChildrenIds(CatalogSubgroup subgroup) {
        List<String> idList = new ArrayList<String>();

        idList.add(subgroup.getCatalogSubgroupId());
        subgroup.refreshReferenceObject("catalogSubgroups");
        for(CatalogSubgroup childSubgroup : subgroup.getCatalogSubgroups()) {
            idList.addAll(getSubgroupChildrenIds(childSubgroup));
        }

        return idList;
    }
	
	/**
	 * Sets the values of the BrowseManager so that search results can be correctly displayed in the view.
	 * 
	 * @param browseManager
	 * @param breadcrumb
	 * @param subgroupMap
	 * @param catalogGroupMap
	 */
	private void setBrowserValues(BrowseManager browseManager, Breadcrumb breadcrumb, Map<String, SubgroupResults> subgroupMap, Map<String, CatalogGroup> catalogGroupMap) {
	    browseManager.setSelectedGroup(null);
        browseManager.setSelectedSubgroup(null);
        browseManager.setCatalogGroups(catalogGroupMap.values()); 
        browseManager.setCatalogSubgroups(new ArrayList<CatalogSubgroup>());
        
        if(catalogGroupMap.isEmpty()) {
            browseManager.setCatalogGroups(getShopCartCatalogService().getCatalogGroups()); 
        }
        else if(catalogGroupMap.size() == 1) {
            browseManager.setSelectedGroup(catalogGroupMap.values().iterator().next());
    
            if(subgroupMap.size() == 1) {
                CatalogSubgroup selectedSubgroup = (CatalogSubgroup)getShopCartBusinessObjectDao().findBySinglePrimaryKey(CatalogSubgroup.class, subgroupMap.values().iterator().next().subgroupId);
                browseManager.setSelectedSubgroup(selectedSubgroup);
            }
            else if(ObjectUtils.isNotNull(breadcrumb.getCatalogSubgroup()) 
                    && subgroupMap.containsKey(breadcrumb.getCatalogSubgroup().getCatalogSubgroupId())) {
                String key = breadcrumb.getCatalogSubgroup().getCatalogSubgroupId();
                CatalogSubgroup selectedSubgroup = (CatalogSubgroup)getShopCartBusinessObjectDao().findBySinglePrimaryKey(CatalogSubgroup.class, subgroupMap.get(key).subgroupId);
                selectedSubgroup.setCatalogSubgroups(populateCatalogSubgroups(subgroupMap.get(key).subgroupChildIds, subgroupMap));
                browseManager.setSelectedSubgroup(selectedSubgroup);
                browseManager.setCatalogSubgroups(selectedSubgroup.getCatalogSubgroups());
            }
            else {
                if(browseManager.getSelectedGroup().getCatalogSubgroups().size() > 1) {
                    browseManager.setSelectedSubgroup(null);
                    List<String> rootSubgroupIds = new ArrayList<String>();
                    for(CatalogSubgroup subgroup : browseManager.getSelectedGroup().getCatalogSubgroups())
                        rootSubgroupIds.add(subgroup.getCatalogSubgroupId());
                    browseManager.setCatalogSubgroups(populateCatalogSubgroups(rootSubgroupIds, subgroupMap));
                }
                else {
                    String rootKey = browseManager.getSelectedGroup().getCatalogSubgroups().get(0).getCatalogSubgroupId();
                    SubgroupResults commonSubgroup = subgroupMap.get(rootKey);
                    String key = commonSubgroup.subgroupId;
                    Set<String> loopCatcher = new HashSet<String>();
                    while(subgroupMap.get(key).subgroupChildIds.size() == 1 && !loopCatcher.contains(key)) {
                        loopCatcher.add(key);
                        String childKey = commonSubgroup.subgroupChildIds.get(0);
                        commonSubgroup = subgroupMap.get(childKey);
                        key = commonSubgroup.subgroupId;
                    }
                    CatalogSubgroup selectedSubgroup = (CatalogSubgroup)getShopCartBusinessObjectDao().findBySinglePrimaryKey(CatalogSubgroup.class, subgroupMap.get(key).subgroupId);
                    selectedSubgroup.setCatalogSubgroups(populateCatalogSubgroups(subgroupMap.get(key).subgroupChildIds, subgroupMap));
                    browseManager.setSelectedSubgroup(selectedSubgroup);
                    browseManager.setCatalogSubgroups(selectedSubgroup.getCatalogSubgroups());
                }
            }
        }       
	}
	
	/**
	 * @param catalogSubgroupIds
	 * @param subgroupMap
	 * @return A list of fully populated CatalogSubgroup objects with results set counts.
	 */
	private List<CatalogSubgroup> populateCatalogSubgroups(List<String> catalogSubgroupIds, Map<String, SubgroupResults> subgroupMap) {
	    if(catalogSubgroupIds == null || catalogSubgroupIds.isEmpty())
	        return new ArrayList<CatalogSubgroup>();
	    
	    Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.CatalogSubgroup.CATALOG_SUBGRUP_ID, catalogSubgroupIds);
        Collection<CatalogSubgroup> subgroupResults = getShopCartBusinessObjectDao().findMatching(CatalogSubgroup.class, fieldValues);
        String childKey = "";
        for(CatalogSubgroup subgroup : subgroupResults) {
            childKey = subgroup.getCatalogSubgroupId();
            subgroup.setResultSetCount(subgroupMap.get(childKey).count);
        }
        return Arrays.asList(subgroupResults.toArray());
	}
	
	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartSearchService#getBestSellingCatalogItems(org.kuali.ext.mm.cart.businessobject.Breadcrumb, java.lang.Integer)
	 */
	public List<CatalogItem> getBestSellingCatalogItems(Breadcrumb breadcrumb, Integer quantity) { 
	    List<CatalogItem> catalogItems = getShopCartSearchDao().getBestSellingItems(breadcrumb.getCatalogIdList(), quantity);
	    for(CatalogItem catalogItem : catalogItems) {
	        catalogItem.refreshReferenceObject(MMConstants.CatalogItem.CATALOG_ITEM_IMAGES);
	    }
	           
        return catalogItems;
	}
	

    /**
     * This is a standard implementation but required no third party indexing package
     * 
     * @param keyword
     * @param catalogId
     * @return a list of search suggestions
     */
    public List<String> getSearchSuggestions2(String keyword, String catalogId) {
        ShopCartCatalogService catalogService = ShopCartServiceLocator.getShopCartCatalogService();
        List<String> suggestions = new ArrayList<String>();
        Breadcrumb breadcrumb = new Breadcrumb("searchSuggest");
        AdvancedSearch aSearch = new AdvancedSearch();
        aSearch.setExactKeywords(keyword);        
        breadcrumb.setCatalogIdList(catalogService.parseCatalogIdStringToList(catalogId));
        breadcrumb.setAdvancedSearch(aSearch);
        
        QueryElement queryElement = buildDescriptionSearch(breadcrumb);
        SortElement sortElement = new SortElement();
        sortElement.addField(ShopCartConstants.CatalogItemSearch.PRIORITY_NUMBER, true);
        
        if(queryElement == null)
            return new ArrayList<String>();
        
        Integer resultsLimit = 20;
        List<CatalogItem> resultList = buildResultsList(getShopCartBusinessObjectDao().findMatchingBoundedAndOrderBy(CatalogItemSearch.class, queryElement, sortElement, resultsLimit));
        for(CatalogItem item : resultList) {
            String description = item.getCatalogDesc().trim();
            int beginIndex = description.toLowerCase().indexOf(keyword.toLowerCase());
            int endIndex = (beginIndex + 40 < description.length() ? beginIndex + 40 : description.length() - 1);
            suggestions.add(item.getCatalogDesc().substring(beginIndex, endIndex));
        }
        return suggestions; 
    }
    
    /**
     * This implementation is a hook into the Apache Lucene Indexing Service
     * 
     * @see org.kuali.ext.mm.cart.service.ShopCartSearchService#getSearchSuggestions(java.lang.String, java.lang.String)
     */
    public List<String> getSearchSuggestions(String keyword, String catalogId) {
        String prfx = catalogId;
        if(catalogId!=null && catalogId.trim().contains(" ")){
            prfx = null;
        }        
        return ShopCartServiceLocator.getShopCartSuggestionService().performSearch(prfx, keyword, 20); 
    }

}