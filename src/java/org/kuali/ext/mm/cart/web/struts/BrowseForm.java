package org.kuali.ext.mm.cart.web.struts;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.util.MMDecimal;


public class BrowseForm extends StoresShoppingFormBase {

	/**
     *
     */
    private static final long serialVersionUID = -2823835249367546295L;

    private String queryDescription;
//
//	private Integer startIndex;
//
//	private Integer endIndex;

	private Integer pageNumber;

	private Integer pageCount;
	
	private String sortOrder;

	private Map<String, MMDecimal> actualPriceMap = new HashMap<String, MMDecimal>();

	@Override
    public void populate(HttpServletRequest request) {
        super.populate(request);

		String page = request.getParameter(ShopCartConstants.ACTION_PARM_PAGE);
        if(StringUtils.isNotEmpty(page))
        	setPageNumber(Integer.parseInt(page));
        
        String sort = request.getParameter(ShopCartConstants.ACTION_PARM_SORT);
        if(StringUtils.isNotEmpty(sort))
            setSortOrder(sort);
        else if(StringUtils.isBlank(getSortOrder()))
            setSortOrder(ShopCartConstants.Sorting.RELEVANCE);

        if(getPageCount() == null) {
			Integer resultsSize = (getBrowseManager().getTotalResultCount() == null ? 0 : getBrowseManager().getTotalResultCount());			
			Double pageCount = Math.ceil(resultsSize.doubleValue() / getResultsPerPage());
			setPageCount(pageCount.intValue());
		}

        if(getPageNumber() == null || getPageNumber() < 1)
			setPageNumber(1);

        if(getPageNumber() > getPageCount() && getPageCount() != 0)
        	setPageNumber(getPageCount());
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getQueryDescription() {
		return queryDescription;
	}

	public void setQueryDescription(String queryDescription) {
		this.queryDescription = queryDescription;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

    public void setActualPriceMap(Map<String, MMDecimal> actualPriceMap) {
        this.actualPriceMap = actualPriceMap;
    }

    public Map<String, MMDecimal> getActualPriceMap() {
        return actualPriceMap;
    }

	public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public int getResultsPerPage() {
        return ShopCartConstants.MAX_RESULTS_PER_PAGE;
    }
    
    @Override
    protected boolean requiresProfile() {
        return false;
    }
}
