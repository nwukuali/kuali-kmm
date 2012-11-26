package org.kuali.ext.mm.cart.web.struts;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.b2b.cxml.invoice.B2bInvoiceStatus;
import org.kuali.ext.mm.b2b.cxml.types.CXML;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ShopCartDetail;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.cart.valueobject.BrowseManager;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.B2BPunchOutService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.krad.util.UrlFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


public class B2BReceiveAction extends StoresShoppingActionBase {
    private static final Logger log = Logger.getLogger(B2BReceiveAction.class);

	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    try{
	    StoresShoppingFormBase sForm = (StoresShoppingFormBase)form;
	    // proceed as usual
		if(StringUtils.isBlank(findMethodToCall(form, request))) {
		    sForm.setBrowseManager(new BrowseManager(sForm.getCustomerProfile()));
			request.getSession().setAttribute("browseManager", sForm.getBrowseManager());
		}    
			
		B2BPunchOutService punchOutService = MMServiceLocator.getB2BPunchOutService();
		CXML cxml = null;
		log.info("Receiving file from request");
		cxml = getCXMLFromRequest(request, response.getOutputStream());		
		if(cxml != null) {
		    String cxmlType = punchOutService.getCXMLRequestType(cxml);		    
		    if(MMConstants.CXMLRequestType.ORDER_MESSAGE.equals(cxmlType)) {
		        ShoppingCart punchOutCart = processOrderMessage(sForm.getCustomerProfile(), sForm.getSessionCart(), cxml);
		        Properties parameters = new Properties();
		        request.getSession().setAttribute(ShopCartConstants.Session.PUNCHOUT_RETURN_CART, punchOutCart);
		        return new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.PUNCHOUT_RETURN_ACTION, parameters), true);
		    }
		    else if (MMConstants.CXMLRequestType.INVOICE_REQUEST.equals(cxmlType)) {
                if (punchOutService.isInvoiceTypeSupported(cxml)) {
                    if(punchOutService.canAcceptRequest(cxml, cxml.getRequest().getDeploymentMode())){
                        B2bInvoiceStatus invoiceStatus = MMServiceLocator.getB2BPunchOutService().receiveCxmlInvoice(cxml);
                        if(invoiceStatus != null){
                            CxmlUtil.sendResponse(response.getOutputStream(), invoiceStatus.getResponseCode(), invoiceStatus.getResponeText(), invoiceStatus.getMessage());
                            if(!invoiceStatus.isValid()){
                                //Rejected CXML files will be logged
                                CxmlUtil.log(cxml);
                            }
                        }
                    }else{
                        log.warn("Request is not acceptable due to one of these reasons [Vendor, Credential or Deployment Mode]");
                        CxmlUtil.sendResponse(response.getOutputStream(),"401","Not Acceptable","Vendor, Credential or Deployment Mode didnt match.");
                    }
                }
                else {
                    log.warn("Server doesn't support this Invoice type.");
                    CxmlUtil.sendResponse(response.getOutputStream(),"450","Not Implemented","Server doesn't support this Invoice type.");
                }
            }else{
		        log.warn("Server doesn't support this request.");
		        CxmlUtil.sendResponse(response.getOutputStream(),"450","Not Implemented","Server doesn't support this request.");
		    }
		     
		}
	    }
        catch (Throwable e) {
            log.error("Error!!!", e);
            CxmlUtil.sendResponse(response.getOutputStream(),"450","Internal Server Error","Failed to receive the request, please contact technical team.");            
        }
        return null;
    }
	
    /**
     * Process incoming cxml request as an OrderMessage to populate the users local shopping cart 
     * 
     * @param customerProfile - profile of the customer placing the order
     * @param sessionCart - shopping cart of the customer stored in the session
     * @param cxml - cxml from the vendor request
     * @return An instance of ShoppingCart representing the punchout items returned
     */
    protected ShoppingCart processOrderMessage(Profile customerProfile, ShoppingCart seesionCart, CXML cxml) {
        B2BPunchOutService punchOutService = MMServiceLocator.getB2BPunchOutService();
        ShoppingCart punchOutCart = punchOutService.parsePunchOutOrderMessage(customerProfile, cxml);
        if(punchOutCart != null) {
            ShopCartService shopCartService = ShopCartServiceLocator.getShopCartService();
            for(ShopCartDetail detail : punchOutCart.getShopCartDetails()) {
                shopCartService.addPunchOutShoppingCartDetailToSessionCart(detail, seesionCart, cxml);
            }
        }       
        return punchOutCart;
    }
	
    /**
     * Retrieves the CXML data from the request and marshals it into a CXML object. 
     * 
     * @param request
     * @return A CXML object populated by the cxml data from the vendor request.  
     */
    protected CXML getCXMLFromRequest(HttpServletRequest request, OutputStream os) {
        CXML cxml = null;
        try {
            InputStream is = null;
            String requestParam = MMServiceLocator.getB2BPunchOutService().getCXMLRequestParameter();
            String cxmlString = request.getParameter(requestParam);
            if (StringUtils.isNotBlank(cxmlString)) {
                is = new ByteArrayInputStream(cxmlString.getBytes("UTF-8"));
            }
            else {
                is = request.getInputStream();
            }
            try {
                cxml = CxmlUtil.unmarshal(is);
            }
            catch (Exception pe) {
                log.error("",pe);
                CxmlUtil.sendResponse(os, "406", "Not Acceptable", "CXML content could not be found/parsed");
                cxml = null;
            }

        }
        catch (Throwable e) {
            // send a response that CXML could not be parsed or didn't pass the schema validation
            log.error("Unexpected error!!!", e);
            CxmlUtil.sendResponse(os, "500", "Internal Server Error", "Server was unable to complete the request");
            cxml = null;
        }
        return cxml;
    }
}
