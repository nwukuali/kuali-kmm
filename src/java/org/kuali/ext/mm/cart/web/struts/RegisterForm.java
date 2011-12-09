package org.kuali.ext.mm.cart.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.businessobject.Customer;


public class RegisterForm extends StoresShoppingFormBase {

	/**
     *
     */
    private static final long serialVersionUID = -7401042461146870169L;

    private Customer registerCustomer;

	private String userpass;

	private String userpassConfirm;

	public RegisterForm() {
	}

	@Override
	public void populate(HttpServletRequest request) {
		super.populate(request);


	}

	public void setRegisterCustomer(Customer registerCustomer) {
		this.registerCustomer = registerCustomer;
	}

	public Customer getRegisterCustomer() {
		return registerCustomer;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpassConfirm(String userpassConfirm) {
		this.userpassConfirm = userpassConfirm;
	}

	public String getUserpassConfirm() {
		return userpassConfirm;
	}

}
