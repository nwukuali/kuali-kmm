package org.kuali.ext.mm.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class AuthenticationFilter implements javax.servlet.Filter {

    private FilterConfig config;
    public static final String PUBLIC_ID = "PublicID";
    private static final String LOGIN_URL = "login.url";

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String publicId = null;
        HttpServletRequest hrequest = null;
        HttpServletResponse hresponse = null;
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            hrequest = ((HttpServletRequest) request);
            hresponse = ((HttpServletResponse) response);
        }
        else
            throw new RuntimeException("AuthenticationFilter not being used as an HttpServlet.");

        // See if posted
        publicId = hrequest.getParameter(PUBLIC_ID);
        if (!StringUtils.isBlank(publicId))
            hrequest.getSession().setAttribute(PUBLIC_ID, publicId);

        // See if not in session
        publicId = (String) hrequest.getSession().getAttribute(PUBLIC_ID);
        if (StringUtils.isBlank(publicId)) {
                String redirect = hrequest.getRequestURI() + "?" + hrequest.getQueryString();
                hresponse.sendRedirect(config.getInitParameter(LOGIN_URL) + "?" + redirect);
        }
        else {
            request = new HttpServletRequestWrapper(hrequest) {
                public String getRemoteUser() {
                    return (String) ((HttpServletRequest) getRequest()).getSession().getAttribute(
                            PUBLIC_ID);
                }};
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
//        this.shopGuest = config.getInitParameter(GUEST_USER);
    }

}
