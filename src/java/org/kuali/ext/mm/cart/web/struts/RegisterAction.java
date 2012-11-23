package org.kuali.ext.mm.cart.web.struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.Customer;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.service.ShopCartCustomerService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;


public class RegisterAction extends StoresShoppingActionBase {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RegisterForm rForm = (RegisterForm)form;
        
        if(!rForm.isAllowsPersonalUse()) {
            request.getSession().setAttribute(ShopCartConstants.Session.SHOPPING_ERROR_MSG, "You are trying to access functionality which has been disabled by the administrator.");
            return mapping.findForward(ShopCartConstants.MAPPING_SYSTEM_ERROR);
        }

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RegisterForm rForm = (RegisterForm)form;

        rForm.setRegisterCustomer(new Customer());

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward register(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RegisterForm rForm = (RegisterForm)form;

        ShopCartCustomerService customerService = ShopCartServiceLocator.getShopCartCustomerService();
        Customer newCustomer = rForm.getRegisterCustomer();
        ActionForward action = mapping.findForward(MMConstants.MAPPING_BASIC);
        if(customerService.validateNewCustomer(newCustomer, "registerCustomer")) {
        	newCustomer = customerService.createAndSaveNewCustomer(newCustomer.getPrincipalName(), newCustomer.getFirstName(), newCustomer.getLastName(), newCustomer.getCustomerPassword());
        	request.getSession().setAttribute(ShopCartConstants.Session.CUSTOMER, newCustomer);
        	Properties parameters = new Properties();
            parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.START_METHOD);
            action = new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.PROFILE_ACTION, parameters), true);
        }

		return action;
    }



}
