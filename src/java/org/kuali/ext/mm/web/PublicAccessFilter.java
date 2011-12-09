package org.kuali.ext.mm.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class PublicAccessFilter implements javax.servlet.Filter {

    private FilterConfig config;
    public static final String PUBLIC_ID = "PublicID";
    private static final String SHOPPING_URL = "shopping.url";
    private static final String GUEST_USER = "shop.guest.user";
    
    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        String publicId = null;
        String shopGuest = config.getInitParameter(GUEST_USER);
        HttpServletRequest hrequest = null;
        HttpServletResponse hresponse = null;
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            hrequest = ((HttpServletRequest) request);
            hresponse = ((HttpServletResponse) response);
        }
        else
            throw new RuntimeException("PublicAccessFilter not being used as an HttpServlet.");
        
        // See if posted
        publicId = hrequest.getParameter(PUBLIC_ID);
        if (!StringUtils.isBlank(publicId))
            hrequest.getSession().setAttribute(PUBLIC_ID, publicId);

        // See if not in session
        publicId = (String) hrequest.getSession().getAttribute(PUBLIC_ID);
        if (StringUtils.isBlank(publicId) 
                && StringUtils.contains(hrequest.getRequestURI(), config.getInitParameter(SHOPPING_URL))) {
            hrequest.getSession().setAttribute(PUBLIC_ID, shopGuest);
        }
        else if (StringUtils.equals(shopGuest, publicId) 
                && !StringUtils.contains(hrequest.getRequestURI(), config.getInitParameter(SHOPPING_URL))) {
            hrequest.getSession().invalidate();
            hrequest.getSession().setAttribute(PUBLIC_ID, null);
        }
       
        chain.doFilter(request, response);
        
    }
    
    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
            this.config = config;
    }

}