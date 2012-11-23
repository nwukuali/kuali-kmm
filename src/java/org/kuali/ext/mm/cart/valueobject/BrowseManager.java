package org.kuali.ext.mm.cart.valueobject;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.*;


public class BrowseManager {

	private Collection<Catalog> availableCatalogs;
	private Collection<Catalog> trueBuyoutCatalogs;

	private Collection<CatalogGroup> catalogGroups;
    private Collection<CatalogSubgroup> catalogSubgroups;
	private CatalogGroup selectedGroup;
	private CatalogSubgroup selectedSubgroup;
	private String selectedCatalogId;

	private String selectedSuggestion;	
	private List<String> suggestionList;

	private List<CatalogItem> resultSet;
	private Integer totalResultCount;	
	private Integer hiddenResultCount;

	private List<Breadcrumb> breadcrumbs;
	private AdvancedSearch advancedSearch;
	private String quickSearch;

	public BrowseManager(Profile customerProfile) {
		setAvailableCatalogs(ShopCartServiceLocator.getShopCartCatalogService().getAvailableCatalogs(customerProfile));
		setTrueBuyoutCatalogs(ShopCartServiceLocator.getShopCartCatalogService().getTrueBuyoutCatalogs(customerProfile, false));
		setCatalogGroups(ShopCartServiceLocator.getShopCartCatalogService().getCatalogGroups());
		setCatalogSubgroups(new ArrayList<CatalogSubgroup>());
		setResultSet(new ArrayList<CatalogItem>());
		setBreadcrumbs(new ArrayList<Breadcrumb>());
		setAdvancedSearch(new AdvancedSearch());		
	}

	public void setAvailableCatalogs(Collection<Catalog> availableCatalogs) {
		this.availableCatalogs = availableCatalogs;
	}

	public Collection<Catalog> getAvailableCatalogs() {
		return availableCatalogs;
	}

	public void setSelectedCatalogId(String selectedCatalogId) {
		this.selectedCatalogId = selectedCatalogId;
	}

	public String getSelectedCatalogId() {
		return selectedCatalogId;
	}

	public void setSelectedSubgroup(CatalogSubgroup selectedSubgroup) {
		this.selectedSubgroup = selectedSubgroup;
	}

	public CatalogSubgroup getSelectedSubgroup() {
		return selectedSubgroup;
	}

	public void setQuickSearch(String quickSearch) {
		this.quickSearch = quickSearch;
	}

	public String getQuickSearch() {
		return quickSearch;
	}

	public Collection<CatalogGroup> getCatalogGroups() {
		return catalogGroups;
	}

	public void setCatalogGroups(Collection<CatalogGroup> catalogGroups) {
		this.catalogGroups = catalogGroups;
	}

	public Collection<CatalogSubgroup> getCatalogSubgroups() {
		return catalogSubgroups;
	}

	public void setCatalogSubgroups(Collection<CatalogSubgroup> catalogSubgroups) {
		this.catalogSubgroups = catalogSubgroups;
	}

	public CatalogGroup getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(CatalogGroup selectedGroups) {
		this.selectedGroup = selectedGroups;
	}

	public void setResultSet(List<CatalogItem> resultSet) {
		this.resultSet = resultSet;
	}

	public List<CatalogItem> getResultSet() {
		return resultSet;
	}

	public void setTotalResultCount(Integer totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	public Integer getTotalResultCount() {
		return totalResultCount == null ? 0 : totalResultCount;
	}

	public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
		this.breadcrumbs = breadcrumbs;
	}

	public List<Breadcrumb> getBreadcrumbs() {
		return breadcrumbs;
	}

	public void resetBreadcrumbs() {
		this.breadcrumbs = new ArrayList<Breadcrumb>();
	}

	public List<CatalogSubgroup> getSelectedSubgroups() {

		List<CatalogSubgroup> subgroupPath = new ArrayList<CatalogSubgroup>();
		if(ObjectUtils.isNotNull(getSelectedSubgroup())) {
			CatalogSubgroup currentSubgroup = getSelectedSubgroup();
			subgroupPath.add(currentSubgroup);
			while(ObjectUtils.isNotNull(currentSubgroup.getPriorCatalogSubgroup())) {
				currentSubgroup = currentSubgroup.getPriorCatalogSubgroup();
				subgroupPath.add(currentSubgroup);
			}
		}
		List<CatalogSubgroup> reverse = new ArrayList<CatalogSubgroup>();
		for(int i=subgroupPath.size()-1; i>=0; i--)
			reverse.add(subgroupPath.get(i));

		return reverse;
	}

	public String getAvailableCatalogIdString() {
		String idString = "";
		for(Catalog catalog : getAvailableCatalogs()) {
			idString += " " + catalog.getCatalogId();
		}
		return idString;
	}

	public List<String> getSelectedCatalogIdList() {
		return ShopCartServiceLocator.getShopCartCatalogService().parseCatalogIdStringToList(getSelectedCatalogId());
	}

	public void setAdvancedSearch(AdvancedSearch advancedSearch) {
		this.advancedSearch = advancedSearch;
	}

	public AdvancedSearch getAdvancedSearch() {
		return advancedSearch;
	}

    public void setSuggestionList(List<String> suggestionList) {
        this.suggestionList = suggestionList;
    }

    public List<String> getSuggestionList() {
        return suggestionList;
    }

    public void setSelectedSuggestion(String selectedSuggestion) {
        this.selectedSuggestion = selectedSuggestion;
    }

    public String getSelectedSuggestion() {
        return selectedSuggestion;
    }

    public void setHiddenResultCount(Integer hiddenResultCount) {
        this.hiddenResultCount = hiddenResultCount;
    }

    public Integer getHiddenResultCount() {
        return hiddenResultCount;
    }
    
    public Collection<Catalog> getTrueBuyoutCatalogs() {
        return trueBuyoutCatalogs;
    }
    
    public void setTrueBuyoutCatalogs(Collection<Catalog> trueBuyoutCatalogs) {
        this.trueBuyoutCatalogs = trueBuyoutCatalogs;
    }

    public Collection<CatalogSubgroup> getSortedSubgroupList() {
        List<CatalogSubgroup> subgroupList = new ArrayList<CatalogSubgroup>();
        if(!catalogSubgroups.isEmpty()) {
            subgroupList = Arrays.asList(catalogSubgroups.toArray(new CatalogSubgroup[0]));
            Collections.sort(subgroupList, new sortSubgroupsByCount());
        }
        return subgroupList;
    }
    
    public Collection<CatalogGroup> getSortedGroupList() {
        List<CatalogGroup> groupList = new ArrayList<CatalogGroup>();
        groupList.addAll(catalogGroups);
        if(!groupList.isEmpty()) {            
            Collections.sort(groupList, new sortCatalogGroups());
        }
        return groupList;
    }
    
    private class sortCatalogGroups implements Comparator<CatalogGroup> {
        public int compare(CatalogGroup cs1, CatalogGroup cs2) { 
            if(cs1.getCatalogGroupNm() == null && cs2.getCatalogGroupNm() == null)
                return 0;
            else if(cs1.getCatalogGroupNm() == null)
                return -1;
            else if(cs2.getCatalogGroupNm() == null)
                return 1;
            
            return StringUtils.lowerCase(cs1.getCatalogGroupNm())
                    .compareTo(StringUtils.lowerCase(cs2.getCatalogGroupNm()));
        }
    }
    
    private class sortSubgroupsByCount implements Comparator<CatalogSubgroup> {
        public int compare(CatalogSubgroup cs1, CatalogSubgroup cs2) { 
            if(cs1.getCatalogSubgroupDesc() == null && cs2.getCatalogSubgroupDesc() == null)
                return 0;
            else if(cs1.getCatalogSubgroupDesc() == null)
                return -1;
            else if(cs2.getCatalogSubgroupDesc() == null)
                return 1;
                
            return  StringUtils.lowerCase(cs1.getCatalogSubgroupDesc())
                    .compareTo( StringUtils.lowerCase(cs2.getCatalogSubgroupDesc()));
        }
    }

}
