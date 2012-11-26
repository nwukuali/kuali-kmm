package org.kuali.ext.mm.cart.web.struts;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.b2b.cxml.types.CXML;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;
import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.PunchOutVendor;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.cart.valueobject.BrowseManager;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.B2BPunchOutService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.PunchOutVendorService;
import org.kuali.rice.krad.service.KRADServiceLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class PunchOutAction extends StoresShoppingActionBase {
    
	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PunchOutForm pForm = (PunchOutForm)form;
        // proceed as usual
		if(StringUtils.isBlank(findMethodToCall(form, request))) {
		    pForm.setBrowseManager(new BrowseManager(pForm.getCustomerProfile()));
			request.getSession().setAttribute("browseManager", pForm.getBrowseManager());
		}		

        return super.execute(mapping, form, request, response);
    }
	
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    PunchOutForm pForm = (PunchOutForm)form;
	    
	    String catalogId = request.getParameter(ShopCartConstants.ACTION_PARM_CATALOG);
	    if(StringUtils.isNotBlank(catalogId)) {
    	    buildPunchOutConfirmation(pForm, request);
    	    request.getSession().setAttribute(ShopCartConstants.Session.PUNCHOUT_CATALOG_ID, catalogId);
	    }
	    else
	        return mapping.findForward(MMConstants.MAPPING_PORTAL);
	    
	    return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

	public ActionForward punchOut(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    PunchOutForm pForm = (PunchOutForm)form;

        String catalogId = (String)request.getSession().getAttribute(ShopCartConstants.Session.PUNCHOUT_CATALOG_ID);
        if(StringUtils.isNotBlank(catalogId)) {
            PunchOutVendorService punchOutVendorService = SpringContext.getBean(PunchOutVendorService.class);
            Catalog catalog = ShopCartServiceLocator.getShopCartCatalogService().getCatalogById(catalogId);
            PunchOutVendor punchOutVendor = punchOutVendorService.getPunchOutVendorByCatalogId(catalog.getCatalogId());
            //Get the punchout service for the vendor
            B2BPunchOutService punchOutService = MMServiceLocator.getB2BPunchOutServiceLocator().getB2BPunchOutService(punchOutVendor.getVendorCredentialDomain(), punchOutVendor.getVendorIdentity());
            String cxmlReceiveUrl = KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(MMKeyConstants.Shopping.SHOPPING_URL) + "/" + ShopCartConstants.PunchOut.VENDOR_REQUEST_ACTION;
            CXML cxml = punchOutService.createPunchOutSetupRequest(catalog, pForm.getCustomerProfile(), cxmlReceiveUrl);
            CXML cxmlResponse = CxmlUtil.sendRequest(cxml, punchOutVendor.getPunchOutUrl());
            String punchOutUrl = null;
            if(cxmlResponse != null && cxmlResponse.getResponse().getStatus().getCode().startsWith("2")) {
                punchOutUrl = cxmlResponse.getResponse().getPunchOutSetupResponse().getStartPage().getURL().getContent();
                if (StringUtils.isNotBlank(punchOutUrl)) {
                    ActionForward punchOutForward = new ActionForward(punchOutUrl);
                    punchOutForward.setRedirect(true);
                    request.getSession().removeAttribute(ShopCartConstants.Session.PUNCHOUT_CATALOG_ID);
                    request.getSession().setAttribute("punchoutUrl", punchOutUrl);
                    pForm.setPunchOutUrl(punchOutUrl);
                }
            }else{
                request.getSession().removeAttribute(ShopCartConstants.Session.PUNCHOUT_CATALOG_ID);
                request.getSession().setAttribute("cxmlResponse", cxmlResponse);
            }
        }        
        return mapping.findForward(MMConstants.MAPPING_BASIC);        
    }

	private void buildPunchOutConfirmation(PunchOutForm pForm, HttpServletRequest request) {
        Map<String, String> confirmParameters = new HashMap<String, String>();
        Map<String, String> declineParameters = new HashMap<String, String>();

        pForm.createNewConfirmAction(request);
        pForm.getConfirmAction().setConfirmAction(ShopCartConstants.PUNCHOUT_ACTION);
        pForm.getConfirmAction().setDeclineAction(ShopCartConstants.HOME_ACTION);
        pForm.getConfirmAction().setMessage(KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(ShopCartKeyConstants.QUESTION_CONFIRM_PUNCH_OUT));
        confirmParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.PunchOut.PUNCHOUT_ACTION_METHOD);
        declineParameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.START_METHOD);
        pForm.getConfirmAction().setConfirmParameters(confirmParameters);
        pForm.getConfirmAction().setDeclineParameters(declineParameters);
    }
	
	/**
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	public ActionForward viewSite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    String punchOutUrl = (String)request.getSession().getAttribute("punchoutUrl");
	    if(StringUtils.isNotBlank(punchOutUrl)){
	        request.getSession().removeAttribute("punchoutUrl");
	        response.sendRedirect(punchOutUrl);
	    }else{
	        CXML cxmlResponse = (CXML) request.getSession().getAttribute("cxmlResponse");
	        if(cxmlResponse != null){
	            request.getSession().removeAttribute("cxmlResponse");
	            CxmlUtil.marshal(cxmlResponse, response.getOutputStream());
	        }	        
	    }
	    return null;
	}
}
