package org.kuali.ext.mm.cart.web.struts;

import javax.servlet.http.HttpServletRequest;


public class LoginForm extends StoresShoppingFormBase {

	/**
     *
     */
    private static final long serialVersionUID = -8702238473238286944L;

    public final String USERPASS = "userpass";

	private String username;

	private String PublicID;

	private String userpass;

	@Override
    public void populate(HttpServletRequest request) {
        super.populate(request);

	}


	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

	public String getUserpass() {
		return userpass;
	}


	public void setPublicID(String publicID) {
		this.PublicID = publicID;
	}


	public String getPublicID() {
		return PublicID;
	}
	
	@Override
    protected boolean requiresProfile() {
        return false;
    }
}
