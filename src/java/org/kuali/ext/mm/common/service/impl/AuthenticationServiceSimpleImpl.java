package org.kuali.ext.mm.common.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.common.filter.AuthenticationFilter;
import org.kuali.rice.kim.service.AuthenticationService;


public class AuthenticationServiceSimpleImpl implements AuthenticationService {

    public String getPrincipalName(HttpServletRequest request) {
        String principalName = null;
        if (request.getSession() != null)
            principalName = (String) request.getSession().getAttribute(
                    AuthenticationFilter.PUBLIC_ID);
        return principalName;
    }

    public boolean validatePassword() {
        return false;
    }
}
