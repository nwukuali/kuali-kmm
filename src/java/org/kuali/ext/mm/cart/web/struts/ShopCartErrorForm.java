package org.kuali.ext.mm.cart.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.cart.ShopCartConstants;



public class ShopCartErrorForm extends StoresShoppingFormBase {
    
    private static final long serialVersionUID = -7659358940778286815L;

    private String errorMessage;
    
    private String returnUrl;
    
    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
        
        setErrorMessage((String)request.getSession().getAttribute(ShopCartConstants.Session.SHOPPING_ERROR_MSG));
        setReturnUrl((String)request.getSession().getAttribute(ShopCartConstants.Session.SHOPPING_ERROR_RETURN_URL));
        
        if(StringUtils.isBlank(getErrorMessage())) {
            setErrorMessage("An unknown system error has occurred.  You may still continue to shop, but if you continue to see this error, please report the problem to the system administrator.");
            setReturnUrl(getShoppingBaseUrl());
        }
        else if(StringUtils.isBlank(getReturnUrl()))
            setReturnUrl(getShoppingBaseUrl());
        
        request.getSession().removeAttribute(ShopCartConstants.Session.SHOPPING_ERROR_MSG);
        request.getSession().removeAttribute(ShopCartConstants.Session.SHOPPING_ERROR_RETURN_URL);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    } 
    
    
}
