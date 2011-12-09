/**
 *
 */
package org.kuali.ext.mm.web;

import java.io.IOException;
import java.util.logging.LogRecord;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.FinancialSystemConfiguration;


/**
 * @author harsha07
 */
public class FinanceSystemAvailabilityFilter implements Filter, java.util.logging.Filter {

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {

    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest hrequest;
        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
            hrequest = ((HttpServletRequest) req);
        }
        else
            throw new RuntimeException(
                "FinanceSystemAvailabilityFilter not being used as an HttpServlet.");
        FinancialSystemConfiguration financialSystemConfiguration = SpringContext
                .getBean(FinancialSystemConfiguration.class);
        FinancialSystemAdaptorFactory factory = financialSystemConfiguration
                .getFinancialSystemAdaptorFactory();
        Boolean checkDoneAlready = (Boolean) hrequest.getSession().getAttribute(
                "FINANCE_CHECK_DONE");
        if (factory == null || !factory.isSystemAvailable()
                && (checkDoneAlready == null || !checkDoneAlready.booleanValue())) {
            hrequest.getSession().setAttribute("FINANCE_CHECK_DONE", Boolean.TRUE);
            String redirect = hrequest.getRequestURI() + "?" + hrequest.getQueryString();
            hrequest.setAttribute("redirect", redirect);
            hrequest.setAttribute("financeApp", financialSystemConfiguration.getAppName());
            hrequest.setAttribute("financeAppurl", financialSystemConfiguration.getAppUrl());
            RequestDispatcher rd = hrequest.getRequestDispatcher("/mm/jsp/FinanceAvailability.jsp");
            rd.forward(req, res);
        }
        else {
            chain.doFilter(req, res);
        }

    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig arg0) throws ServletException {
    }

    /**
     * @see java.util.logging.Filter#isLoggable(java.util.logging.LogRecord)
     */
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
