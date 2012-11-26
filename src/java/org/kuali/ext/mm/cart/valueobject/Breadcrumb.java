package org.kuali.ext.mm.cart.valueobject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.kuali.ext.mm.businessobject.CatalogGroup;
import org.kuali.ext.mm.businessobject.CatalogSubgroup;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


public class Breadcrumb {

	private String description;

	private CatalogGroup catalogGroup;

	private CatalogSubgroup catalogSubgroup;

//	private String searchKeywords;

	private List<String> catalogIdList;

	private AdvancedSearch advancedSearch;

	public Breadcrumb(String description) {
		this.description = description;
		setCatalogIdList(new ArrayList<String>());
		setAdvancedSearch(null);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CatalogGroup getCatalogGroup() {
		return catalogGroup;
	}

	public void setCatalogGroup(CatalogGroup catalogGroup) {
		this.catalogGroup = catalogGroup;
	}

	public CatalogSubgroup getCatalogSubgroup() {
		return catalogSubgroup;
	}

	public void setCatalogSubgroup(CatalogSubgroup catalogSubgroup) {
		this.catalogSubgroup = catalogSubgroup;
	}
//
//	public String getSearchKeywords() {
//		return searchKeywords;
//	}
//
//	public void setSearchKeywords(String searchKeywords) {
//		this.searchKeywords = searchKeywords;
//	}

	public List<String> getCatalogIdList() {
		return catalogIdList;
	}

	public void setCatalogIdList(List<String> catalogIdList) {
		this.catalogIdList = catalogIdList;
	}


	public void setAdvancedSearch(AdvancedSearch advancedSearch) {
		this.advancedSearch = advancedSearch;
	}

	public AdvancedSearch getAdvancedSearch() {
		return advancedSearch;
	}
	
	public boolean isCatalogBreadcrumb() {
	    return (advancedSearch == null || advancedSearch.isEmpty()) 
	            && catalogGroup == null && catalogSubgroup == null
	            && catalogIdList != null && catalogIdList.size() == 1;
	}

	@Override
	public boolean equals(Object breadcrumb) {
		Breadcrumb bc = (Breadcrumb)breadcrumb;
		boolean isEqual = true;
//		isEqual &= StringUtils.equals(this.description, bc.getDescription());
		isEqual &= getAdvancedSearch().equals(bc.getAdvancedSearch());
		if(ObjectUtils.isNull(this.catalogGroup) || ObjectUtils.isNull(bc.getCatalogGroup()))
			isEqual &= (ObjectUtils.isNull(this.catalogGroup) && ObjectUtils.isNull(bc.getCatalogGroup()));
		else
			isEqual &= 	StringUtils.equals(this.catalogGroup.getCatalogGroupCd(), bc.getCatalogGroup().getCatalogGroupCd());

		if(ObjectUtils.isNull(this.getCatalogSubgroup()) || ObjectUtils.isNull(bc.getCatalogSubgroup()))
			isEqual &=  ObjectUtils.isNull(this.getCatalogSubgroup()) && ObjectUtils.isNull(bc.getCatalogSubgroup());
		else
			isEqual &= getCatalogSubgroup().equals(bc.getCatalogSubgroup());

		return isEqual;
	}

	@Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(37, 41);
//        hashCodeBuilder.append(this.description);
        hashCodeBuilder.append(this.advancedSearch);
        hashCodeBuilder.append(this.catalogGroup);
        hashCodeBuilder.append(this.catalogSubgroup);
        return hashCodeBuilder.toHashCode();
    }




}
