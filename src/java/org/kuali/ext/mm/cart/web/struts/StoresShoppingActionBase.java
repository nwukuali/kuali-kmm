package org.kuali.ext.mm.cart.web.struts;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.CatalogSubgroup;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.valueobject.Breadcrumb;
import org.kuali.ext.mm.cart.valueobject.BrowseManager;
import org.kuali.ext.mm.cart.valueobject.ConfirmAction;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.exception.AuthorizationException;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.util.UrlFactory;
import org.kuali.rice.kns.web.struts.action.KualiAction;


public class StoresShoppingActionBase extends KualiAction {

	 /**
     * Override this method to provide action-level access controls to the application.
     *
     * @param form
     * @throws AuthorizationException
     */
    @Override
    protected void checkAuthorization( ActionForm form, String methodToCall) throws AuthorizationException
    {
//    	String principalId = GlobalVariables.getUserSession().getPrincipalId();
//    	AttributeSet roleQualifier = new AttributeSet(getRoleQualification(form, methodToCall));
//		AttributeSet permissionDetails = new AttributeSet();
//		permissionDetails.put(KimAttributes.NAMESPACE_CODE, KimCommonUtils.getNamespaceCode(StoresShoppingActionBase.class));
//		permissionDetails.put(KimAttributes.ACTION_CLASS, StoresShoppingActionBase.class.getName());
//
//        if (!KIMServiceLocator.getIdentityManagementService().isAuthorizedByTemplateName(principalId, KNSConstants.KNS_NAMESPACE,
//        		KimConstants.PermissionTemplateNames.USE_SCREEN, permissionDetails, roleQualifier ))
//        {
//            throw new AuthorizationException(GlobalVariables.getUserSession().getPerson().getPrincipalName(),
//            		methodToCall,
//            		this.getClass().getSimpleName());
//        }
    }

    @Override
    protected String getReturnLocation(HttpServletRequest request, ActionMapping mapping) {
    	String mappingPath = mapping.getPath();
    	String basePath = getApplicationBaseUrl() + "/mm";
        return basePath + ("/lookup".equals(mappingPath) || "/maintenance".equals(mappingPath) || "/multipleValueLookup".equals(mappingPath) ? "/kr" : "") + mappingPath + ".do";
    }


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    StoresShoppingFormBase storeForm = (StoresShoppingFormBase)form;
	    
	    if(storeForm.requiresProfile() && storeForm.getCustomerProfile() == null) {
	        return redirectHome();
	    }
	    
        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	public ActionForward quickSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StoresShoppingFormBase storeForm = (StoresShoppingFormBase)form;

		String searchString = StringUtils.isBlank(storeForm.getBrowseManager().getQuickSearch()) ? "" : storeForm.getBrowseManager().getQuickSearch();
		String catalog = storeForm.getBrowseManager().getSelectedCatalogId();
		Properties parameters = new Properties();
		parameters.put(ShopCartConstants.ACTION_PARM_QUERY, searchString);
		parameters.put(ShopCartConstants.ACTION_PARM_CATALOG, catalog);
        parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.QUICK_SEARCH_METHOD);

		return new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.BROWSE_ACTION, parameters), true);
	}

	protected void resetBrowseManager(StoresShoppingFormBase storeForm, HttpServletRequest request) {
		 storeForm.setBrowseManager(new BrowseManager(storeForm.getCustomerProfile()));
	     request.getSession().setAttribute("browseManager", storeForm.getBrowseManager());
	}

	public ActionForward confirmAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StoresShoppingFormBase storeForm = (StoresShoppingFormBase)form;
		ConfirmAction confirmAction = storeForm.getConfirmAction();
		if(confirmAction != null) {
			Properties parameters = new Properties();
			for(String parameter : confirmAction.getConfirmParameters().keySet()) {
				parameters.put(parameter, confirmAction.getConfirmParameters().get(parameter));
			}
			parameters.put(ShopCartConstants.ACTION_PARM_CONFRIM, String.valueOf(true));

			String url = UrlFactory.parameterizeUrl(confirmAction.getConfirmAction(), parameters);
			storeForm.clearConfirmAction(request);
			return new ActionForward(url, true);
		}
		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward declineAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		StoresShoppingFormBase storeForm = (StoresShoppingFormBase)form;

		ConfirmAction confirmAction = storeForm.getConfirmAction();
		if(confirmAction != null) {
			Properties parameters = new Properties();
			for(String parameter : confirmAction.getDeclineParameters().keySet()) {
				parameters.put(parameter, confirmAction.getDeclineParameters().get(parameter));
			}

			String url = UrlFactory.parameterizeUrl(confirmAction.getDeclineAction(), parameters);
			storeForm.clearConfirmAction(request);

			return new ActionForward(url, true);
		}
		return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    request.getSession().invalidate();

		Properties parameters = new Properties();
	    parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.START_METHOD);

		return new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.HOME_ACTION, parameters), true);

	}

    protected ActionForward redirectHome() {
        Properties parameters = new Properties();
        parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.START_METHOD);

        return new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.HOME_ACTION, parameters), true);
    }
    
    protected void updateBreadcrumbs(BrowseManager browseManager, Breadcrumb breadcrumb) {
        List<Breadcrumb> breadcrumbList = new ArrayList<Breadcrumb>();
        breadcrumbList.addAll(browseManager.getBreadcrumbs());
        Integer breadcrumbRank = getGroupSubgroupRank(breadcrumb);
        if(breadcrumbRank >= 0) {
            for(int i=0; i < breadcrumbList.size(); i++) {
                if(breadcrumbRank < getGroupSubgroupRank(breadcrumbList.get(i))) {
                        if(breadcrumbList.size() < 2) {
                            breadcrumbList.clear();
                        }
                        else {
                            browseManager.setBreadcrumbs(breadcrumbList.subList(0, i));
                        }
                    break;
                }
            }
        }
        else {
            browseManager.getBreadcrumbs().clear();
        }
        if(!browseManager.getBreadcrumbs().contains(breadcrumb))
            browseManager.getBreadcrumbs().add(breadcrumb);
    }
    
    private Integer getGroupSubgroupRank(Breadcrumb breadcrumb) {
        Integer rank = -1;

        if(ObjectUtils.isNull(breadcrumb.getCatalogGroup()))
            return rank;    //no group or subgroup: -1
        rank++;
        if(ObjectUtils.isNull(breadcrumb.getCatalogSubgroup()))
            return rank;    // catalog group: 0
        rank++;
        CatalogSubgroup currentSubgroup = breadcrumb.getCatalogSubgroup();
        while(ObjectUtils.isNotNull(currentSubgroup.getPriorCatalogSubgroup())) {
            currentSubgroup = currentSubgroup.getPriorCatalogSubgroup();
            rank++;
        }

        return rank;    //subgroup tree: 1 + depth of subgroup tree
    }

}
