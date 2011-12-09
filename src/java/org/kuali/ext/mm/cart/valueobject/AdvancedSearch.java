package org.kuali.ext.mm.cart.valueobject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class AdvancedSearch {

	private static final String BLANK_SPACE = " ";

//	private String selectedCatalogId;

	private String allKeywords;

	private String anyKeywords;

	private String noneKeywords;
	
	private String exactKeywords;

	private boolean withinResults;

	public AdvancedSearch() {
		reset();
	}

	public AdvancedSearch(AdvancedSearch advancedSearch) {
//		setSelectedCatalogId(advancedSearch.getSelectedCatalogId());
		setAllKeywords(advancedSearch.getAllKeywords());
		setAnyKeywords(advancedSearch.getAnyKeywords());
		setNoneKeywords(advancedSearch.getNoneKeywords());
		setWithinResults(advancedSearch.getWithinResults());
	}

//	public String getSelectedCatalogId() {
//		return selectedCatalogId;
//	}
//
//	public void setSelectedCatalogId(String selectedCatalogId) {
//		this.selectedCatalogId = selectedCatalogId;
//	}

	public String getAllKeywords() {
		return StringUtils.isBlank(allKeywords) ? StringUtils.EMPTY : allKeywords;
	}

	public void setAllKeywords(String allKeywords) {
		this.allKeywords = allKeywords;
	}

	public String getAnyKeywords() {
		return StringUtils.isBlank(anyKeywords) ? StringUtils.EMPTY : anyKeywords;
	}

	public void setAnyKeywords(String anyKeywords) {
		this.anyKeywords = anyKeywords;
	}

	public String getNoneKeywords() {
		return StringUtils.isBlank(noneKeywords) ? StringUtils.EMPTY : noneKeywords;
	}

	public void setNoneKeywords(String noneKeywords) {
		this.noneKeywords = noneKeywords;
	}

	public void setExactKeywords(String exactKeywords) {
        this.exactKeywords = exactKeywords;
    }

    public String getExactKeywords() {
        return StringUtils.isBlank(exactKeywords) ? StringUtils.EMPTY : exactKeywords;
    }

    public void setWithinResults(boolean withinResults) {
		this.withinResults = withinResults;
	}

	public boolean getWithinResults() {
		return withinResults;
	}

//	public List<String> getSelectedCatalogIdList() {
//		List<String> catalogIdList = new ArrayList<String>();
//
//		if(getSelectedCatalogId() != null) {
//			for(String id : getSelectedCatalogId().split(BLANK_SPACE))
//				catalogIdList.add(id);
//		}
//		return catalogIdList;
//	}

	public void reset() {
		setAllKeywords(StringUtils.EMPTY);
		setAnyKeywords(StringUtils.EMPTY);
		setNoneKeywords(StringUtils.EMPTY);
		setExactKeywords(StringUtils.EMPTY);
		setWithinResults(false);
	}

	@Override
	public boolean equals(Object advancedSearch) {
		boolean isEqual = true;

		isEqual &= StringUtils.equals(getAllKeywords(), ((AdvancedSearch)advancedSearch).getAllKeywords());
		isEqual &= StringUtils.equals(getAnyKeywords(), ((AdvancedSearch)advancedSearch).getAnyKeywords());
		isEqual &= StringUtils.equals(getNoneKeywords(), ((AdvancedSearch)advancedSearch).getNoneKeywords());
		isEqual &= StringUtils.equals(getExactKeywords(), ((AdvancedSearch)advancedSearch).getExactKeywords());

		return isEqual;
	}

	@Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(37, 41);
        hashCodeBuilder.append(this.allKeywords);
        hashCodeBuilder.append(this.anyKeywords);
        hashCodeBuilder.append(this.noneKeywords);
        hashCodeBuilder.append(this.exactKeywords);
        return hashCodeBuilder.toHashCode();
	}

	public boolean isEmpty() {
		return StringUtils.isEmpty(getAllKeywords()) 
		    && StringUtils.isEmpty(getAnyKeywords()) 
		    && StringUtils.isEmpty(getNoneKeywords()) 
		    && StringUtils.isEmpty(getExactKeywords());
	}

	@Override
	public String toString() {
		String toString = getAllKeywords();
		if(StringUtils.isNotBlank(getAnyKeywords()))
			toString += BLANK_SPACE + " +" + getAnyKeywords();
		if(StringUtils.isNotBlank(getNoneKeywords()))
			toString += BLANK_SPACE + " -" + getNoneKeywords();
		if(StringUtils.isNotBlank(getExactKeywords()))
            toString += BLANK_SPACE + " -" + getExactKeywords();
		return toString;
	}
}
