package org.kuali.ext.mm.cart.web.struts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogGroup;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartCatalogService;
import org.kuali.ext.mm.cart.service.ShopCartSearchService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.cart.valueobject.Breadcrumb;
import org.kuali.ext.mm.common.sys.MMConstants;


public class BrowseCatalogAction extends StoresShoppingActionBase {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BrowseCatalogForm browseForm = (BrowseCatalogForm)form;

        String selectedCatalogId = request.getParameter(ShopCartConstants.ACTION_PARM_CATALOG);
        
        resetBrowseManager(browseForm, request);
        if(StringUtils.isBlank(selectedCatalogId))
            return redirectHome();
        
        ShopCartSearchService searchService = ShopCartServiceLocator.getShopCartSearchService();
        ShopCartCatalogService catalogService = ShopCartServiceLocator.getShopCartCatalogService();
        Catalog catalog = catalogService.getCatalogById(selectedCatalogId);
        
        browseForm.getBrowseManager().setSelectedCatalogId(selectedCatalogId);
        Breadcrumb breadcrumb = searchService.buildBreadcrumb(browseForm.getBrowseManager(), catalog.getCatalogDesc(), false);
        searchService.synchronizeBrowserWithResultSet(browseForm.getBrowseManager(), breadcrumb);

        Collection<CatalogGroup> catalogGroups = browseForm.getBrowseManager().getSortedGroupList();
        Iterator<CatalogGroup> groupIterator = catalogGroups.iterator();

        int groupsPerColumn = (int)Math.ceil((double)catalogGroups.size() / (double)browseForm.getColumnCount());
        
        for(int col = 0; col < browseForm.getColumnCount(); col++) {
            List<CatalogGroup> columnGroupList = new ArrayList<CatalogGroup>(); 
            for(int i = 0; i < groupsPerColumn; i++) {
                if(groupIterator.hasNext()) {
                    columnGroupList.add(groupIterator.next());
                }
                else {
                    break;
                }
            }
            browseForm.getCatalogGroupOrganizer().add(columnGroupList);
        }
        
        updateBreadcrumbs(browseForm.getBrowseManager(), breadcrumb);
        
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
}
