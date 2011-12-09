package org.kuali.ext.mm.cart.web.struts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.CatalogGroup;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CatalogSubgroup;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartSearchService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.cart.valueobject.AdvancedSearch;
import org.kuali.ext.mm.cart.valueobject.Breadcrumb;
import org.kuali.ext.mm.cart.valueobject.BrowseManager;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.utility.NanoTimer;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;


public class BrowseAction extends StoresShoppingActionBase {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BrowseForm bForm = (BrowseForm)form;

		resetBrowseManager(bForm, request);

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward advancedSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BrowseForm browseForm = (BrowseForm)form;

		AdvancedSearch search = browseForm.getBrowseManager().getAdvancedSearch();
		if(!search.getWithinResults())
			resetBrowseManager(browseForm, request);

		if(search.isEmpty())
		    return redirectHome();
		    
		browseForm.getBrowseManager().setAdvancedSearch(search);
		
		ShopCartSearchService searchService = ShopCartServiceLocator.getShopCartSearchService();
		Breadcrumb breadcrumb = searchService.buildBreadcrumb(browseForm.getBrowseManager(), "Advanced Search", search.getWithinResults());

		populateResultsFromBreadcrumb(browseForm.getBrowseManager(), breadcrumb, "", true, browseForm.getPageNumber(), browseForm.getResultsPerPage());

		if(!browseForm.getBrowseManager().getResultSet().isEmpty())
			updateBreadcrumbs(browseForm.getBrowseManager(), breadcrumb);

		updateResultSetAttributes(browseForm, search.toString());

        return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	@Override
	public ActionForward quickSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BrowseForm browseForm = (BrowseForm)form;
		
		String searchString = request.getParameter(ShopCartConstants.ACTION_PARM_QUERY);
		String selectedCatalogId = request.getParameter(ShopCartConstants.ACTION_PARM_CATALOG);
		if(StringUtils.isBlank(searchString)) {
			searchString = browseForm.getBrowseManager().getQuickSearch();
			selectedCatalogId = browseForm.getBrowseManager().getSelectedCatalogId();
		}

		resetBrowseManager(browseForm, request);
		if(StringUtils.isBlank(searchString))
		    return redirectHome();
		
		browseForm.getBrowseManager().getAdvancedSearch().reset();
		browseForm.getBrowseManager().getAdvancedSearch().setAllKeywords(searchString);
		browseForm.getBrowseManager().setSelectedCatalogId(selectedCatalogId);

		ShopCartSearchService searchService = ShopCartServiceLocator.getShopCartSearchService();
		Breadcrumb breadcrumb = searchService.buildBreadcrumb(browseForm.getBrowseManager(), "\"" + searchString + "\"", false);
		populateResultsFromBreadcrumb(browseForm.getBrowseManager(), breadcrumb, "", true, browseForm.getPageNumber(), browseForm.getResultsPerPage());
		if(!browseForm.getBrowseManager().getResultSet().isEmpty())
			updateBreadcrumbs(browseForm.getBrowseManager(), breadcrumb);
		
		updateResultSetAttributes(browseForm, searchString);
        return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward showAllGroups(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BrowseForm browseForm = (BrowseForm)form;

		browseForm.getBrowseManager().setSelectedGroup(null);
		browseForm.getBrowseManager().setSelectedSubgroup(null);
		browseForm.getBrowseManager().getAdvancedSearch().reset();

		ShopCartSearchService searchService = ShopCartServiceLocator.getShopCartSearchService();
		
		Breadcrumb breadcrumb = searchService.buildBreadcrumb(browseForm.getBrowseManager(), "All Catagories", true);
		breadcrumb.setCatalogGroup(null);
		breadcrumb.setCatalogSubgroup(null);
		breadcrumb.setDescription("\"" + breadcrumb.getAdvancedSearch().toString() + "\"");
		if(!breadcrumb.getAdvancedSearch().isEmpty()) {
			populateResultsFromBreadcrumb(browseForm.getBrowseManager(), breadcrumb, "", true, browseForm.getPageNumber(), browseForm.getResultsPerPage());
			browseForm.getBrowseManager().resetBreadcrumbs();
			if(!browseForm.getBrowseManager().getResultSet().isEmpty())
				updateBreadcrumbs(browseForm.getBrowseManager(), breadcrumb);
		}
		else {
		    browseForm.getBrowseManager().setCatalogSubgroups(new ArrayList<CatalogSubgroup>());
		    browseForm.getBrowseManager().setCatalogGroups(MMServiceLocator.getCatalogService().getCatalogGroups());
		}			

		updateResultSetAttributes(browseForm, breadcrumb.getDescription());
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
	

	public ActionForward expandCatalogGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BrowseForm browseForm = (BrowseForm)form;

		String groupId = request.getParameter("groupId");
		String resultCount = request.getParameter("resultCount");

		CatalogGroup selectedGroup = ShopCartServiceLocator.getShopCartCatalogService().getCatalogGroupbyId(groupId);
		browseForm.getBrowseManager().setSelectedGroup(selectedGroup);
		browseForm.getBrowseManager().setSelectedSubgroup(null);
		browseForm.getBrowseManager().getAdvancedSearch().reset();
		
		ShopCartSearchService searchService = ShopCartServiceLocator.getShopCartSearchService();

		Breadcrumb breadcrumb;
		if(StringUtils.isBlank(resultCount)) {
			browseForm.getBrowseManager().resetBreadcrumbs();
			breadcrumb = searchService.buildBreadcrumb(browseForm.getBrowseManager(), selectedGroup.getCatalogGroupNm(), false);
		}
		else {
			if(StringUtils.isNumeric(resultCount))
				browseForm.getBrowseManager().getSelectedGroup().setResultSetCount(Integer.parseInt(resultCount));
			breadcrumb = searchService.buildBreadcrumb(browseForm.getBrowseManager(), selectedGroup.getCatalogGroupNm(), true);
		}

		populateResultsFromBreadcrumb(browseForm.getBrowseManager(), breadcrumb, "", true, browseForm.getPageNumber(), browseForm.getResultsPerPage());

		if(!browseForm.getBrowseManager().getResultSet().isEmpty())
			updateBreadcrumbs(browseForm.getBrowseManager(), breadcrumb);
		updateResultSetAttributes(browseForm, breadcrumb.getDescription());

		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward expandCatalogSubgroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BrowseForm browseForm = (BrowseForm)form;

		String subgroupId = request.getParameter("subgroupId");

		CatalogSubgroup selectedSubgroup = ShopCartServiceLocator.getShopCartCatalogService().getCatalogSubgroupbyId(subgroupId);
		browseForm.getBrowseManager().setSelectedSubgroup(selectedSubgroup);
		browseForm.getBrowseManager().getAdvancedSearch().reset();
		
		ShopCartSearchService searchService = ShopCartServiceLocator.getShopCartSearchService();
		
		Breadcrumb breadcrumb;
		if(browseForm.getBrowseManager().getSelectedGroup().getResultSetCount() == null)
			breadcrumb = searchService.buildBreadcrumb(browseForm.getBrowseManager(), selectedSubgroup.getCatalogSubgroupDesc(), false);
		else
			breadcrumb = searchService.buildBreadcrumb(browseForm.getBrowseManager(), selectedSubgroup.getCatalogSubgroupDesc(), true);

		populateResultsFromBreadcrumb(browseForm.getBrowseManager(), breadcrumb, "", true, browseForm.getPageNumber(), browseForm.getResultsPerPage());

		if(!browseForm.getBrowseManager().getResultSet().isEmpty())
			updateBreadcrumbs(browseForm.getBrowseManager(), breadcrumb);
		updateResultSetAttributes(browseForm, breadcrumb.getDescription());

		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}
	
    public ActionForward changeSortBy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BrowseForm browseForm = (BrowseForm)form;        
        List<Breadcrumb> breadcrumbList = browseForm.getBrowseManager().getBreadcrumbs();
        Breadcrumb breadcrumb = breadcrumbList.get(breadcrumbList.size()-1);
        String sortBy = ShopCartConstants.CatalogItemSearch.PRIORITY_NUMBER;
        boolean sortAscending = true;
        if(ShopCartConstants.Sorting.BEST_SELLING.equals(browseForm.getSortOrder())) {
            sortBy = ShopCartConstants.CatalogItemSearch.ORDER_COUNT;
            sortAscending = false;
        }
        else if(ShopCartConstants.Sorting.PRICE_HL.equals(browseForm.getSortOrder())) {
            sortBy = ShopCartConstants.CatalogItemSearch.CATALOG_PRC;
            sortAscending = false;
        }
        else if(ShopCartConstants.Sorting.PRICE_LH.equals(browseForm.getSortOrder())) {
            sortBy = ShopCartConstants.CatalogItemSearch.CATALOG_PRC;
            sortAscending = true;
        }
            
        populateResultsFromBreadcrumb(browseForm.getBrowseManager(), breadcrumb, sortBy, sortAscending, browseForm.getPageNumber(), browseForm.getResultsPerPage());
        
        updateResultSetAttributes(browseForm, breadcrumb.getDescription());
        
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward followBreadcrumb(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BrowseForm browseForm = (BrowseForm)form;
		Integer breadcrumbId = (!StringUtils.isNumeric(request.getParameter("breadcrumbId"))) ? null : Integer.parseInt(request.getParameter("breadcrumbId"));

		if(breadcrumbId != null && breadcrumbId >= 0 && breadcrumbId < browseForm.getBrowseManager().getBreadcrumbs().size()) {
			Breadcrumb breadcrumb = browseForm.getBrowseManager().getBreadcrumbs().get(breadcrumbId);
			List<Breadcrumb> breadcrumbList = browseForm.getBrowseManager().getBreadcrumbs();
			
			if(breadcrumb.isCatalogBreadcrumb()) {
			    //performance enhancement - better not to query the whole catalog for results
			    browseForm.getBrowseManager().setBreadcrumbs(breadcrumbList.subList(0, breadcrumbId+1));
			    return redirectToBrowseCatalogAction(breadcrumb.getCatalogIdList().get(0));
			}
			
			populateResultsFromBreadcrumb(browseForm.getBrowseManager(), breadcrumb, "", true, browseForm.getPageNumber(), browseForm.getResultsPerPage());
			updateResultSetAttributes(browseForm, breadcrumb.getAdvancedSearch().toString());
			browseForm.getBrowseManager().setBreadcrumbs(breadcrumbList.subList(0, breadcrumbId+1));
		}

		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}
	
    public ActionForward goToPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BrowseForm browseForm = (BrowseForm)form;
        
        String page = request.getParameter(ShopCartConstants.ACTION_PARM_PAGE);
        if(StringUtils.isNotEmpty(page))
            browseForm.setPageNumber(Integer.parseInt(page));
        
        List<Breadcrumb> breadcrumbList = browseForm.getBrowseManager().getBreadcrumbs();
        Breadcrumb breadcrumb = breadcrumbList.get(breadcrumbList.size()-1);
        
        populateResultsFromBreadcrumb(browseForm.getBrowseManager(), breadcrumb, "", true, browseForm.getPageNumber(), browseForm.getResultsPerPage());
        
        updateResultSetAttributes(browseForm, breadcrumb.getDescription());

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	private void updateResultSetAttributes(BrowseForm browseForm, String queryDescription) {
	    ArrayList<CatalogItem> trimmedResults = new ArrayList<CatalogItem>();
	    Integer hiddenResultCount = 0;
        Integer resultsSize = browseForm.getBrowseManager().getTotalResultCount();
		Double pageCount = Math.ceil(resultsSize.doubleValue() / browseForm.getResultsPerPage());
		browseForm.setPageCount(pageCount.intValue());
		browseForm.setQueryDescription(queryDescription);

		Set<String> uniqueItemIds = new HashSet<String>();
		if(!browseForm.getBrowseManager().getResultSet().isEmpty()) {
			if(browseForm.getCustomerProfile() != null) {
			    for(CatalogItem item : browseForm.getBrowseManager().getResultSet()) {
			        if(!uniqueItemIds.contains(item.getCatalogItemId())) {
			            uniqueItemIds.add(item.getCatalogItemId());
			            browseForm.getActualPriceMap().put(item.getCatalogItemId(), 
			                    ShopCartServiceLocator.getShopCartCatalogService()
			                    .computeCatalogItemPrice(item, browseForm.getCustomerProfile(), 1));
                        trimmedResults.add(item);
			        }
			        else {
			            hiddenResultCount++;
			        }
			        if(trimmedResults.size() >= browseForm.getResultsPerPage())
			            break;					
				}
			    browseForm.getBrowseManager().setResultSet(trimmedResults);
			    browseForm.getBrowseManager().setHiddenResultCount(hiddenResultCount);
			}
		}
	}

	private void populateResultsFromBreadcrumb(BrowseManager browseManager, Breadcrumb breadcrumb, String sortBy, boolean sortAscending, int pageNumber, int resultsPerPage) {
        ShopCartSearchService searchService = ShopCartServiceLocator.getShopCartSearchService();
        NanoTimer timer = new NanoTimer("populateResultsFromBreadcrumb");
        timer.start();        
        browseManager.setResultSet(searchService.getResultSetPageByBreadcrumb(breadcrumb, sortBy, sortAscending, pageNumber, resultsPerPage, 2));
        timer.split("getResultSetPageByBreadcrumb");
        browseManager.setTotalResultCount(searchService.getResultSetByBreadcrumbCount(breadcrumb));
        timer.split("getResultSetByBreadcrumbCount");
        searchService.synchronizeBrowserWithResultSet(browseManager, breadcrumb);
        timer.split("synchronizeBrowserWithResultSet");
        timer.stop();
    }
	
	/**
     * @param string
     * @return
     */
    private ActionForward redirectToBrowseCatalogAction(String catalogId) {
        Properties parameters = new Properties();
        parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.START_METHOD);
        parameters.put(ShopCartConstants.ACTION_PARM_CATALOG, catalogId);

        return new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.BROWSE_CATALOG_ACTION, parameters), true);
    }

}
