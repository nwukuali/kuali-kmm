package org.kuali.ext.mm.cart.web.struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.Customer;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.cart.service.ShopCartCustomerService;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

//import org.kuali.rice.kim.api.identity.Person;
//import org.kuali.rice.kim.api.services.KimApiServiceLocator;


public class LoginAction extends StoresShoppingActionBase {


	/**
	 * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginForm lForm = (LoginForm)form;

        ShopCartCustomerService customerService = ShopCartServiceLocator.getShopCartCustomerService();

        request.getSession().invalidate();
        //make sure no one can log in as shopguest
        if(ShopCartConstants.User.SHOP_GUEST.equals(lForm.getUsername())) {
        	GlobalVariables.getMessageMap().putError(lForm.USERPASS, ShopCartKeyConstants.ERROR_LOGIN_INVALID);
        	return mapping.findForward(MMConstants.MAPPING_BASIC);
        }

        Person user = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(lForm.getUsername());
        Customer customer = customerService.getCustomerById(lForm.getUsername());
        if(user == null) {
        	request.getSession().setAttribute(ShopCartConstants.PUBLIC_ID, ShopCartConstants.User.SHOP_GUEST);
        	if(customerService.authenticateUser(customer, lForm.getUserpass()) && lForm.isAllowsPersonalUse()) {
        		request.getSession().setAttribute(ShopCartConstants.Session.CUSTOMER, customer);
        	}
        	else {
        		GlobalVariables.getMessageMap().putError(lForm.USERPASS, ShopCartKeyConstants.ERROR_LOGIN_INVALID);
        		return mapping.findForward(MMConstants.MAPPING_BASIC);
        	}
        }
        else {
        	request.getSession().setAttribute(ShopCartConstants.PUBLIC_ID, user.getPrincipalName());
        	if(customer == null) {
        		customer = customerService.createAndSaveNewCustomerFromPerson(user);
        	}
        	request.getSession().setAttribute(ShopCartConstants.Session.CUSTOMER, customer);
        }

        Properties parameters = new Properties();
        parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, ShopCartConstants.START_METHOD);

		return new ActionForward(UrlFactory.parameterizeUrl(ShopCartConstants.HOME_ACTION, parameters), true);
    }


}
