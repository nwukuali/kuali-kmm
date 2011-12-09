package org.kuali.ext.mm.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.web.UserLoginFilter;
import org.kuali.rice.kew.web.session.UserSession;
import org.kuali.rice.kim.bo.entity.KimPrincipal;
import org.kuali.rice.kim.service.IdentityManagementService;
import org.kuali.rice.kim.service.KIMServiceLocator;

public class DummyUserLoginFilter extends UserLoginFilter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		if (!(req instanceof HttpServletRequest && res instanceof HttpServletResponse)) {
			chain.doFilter(req, res);
			return;
		}

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		final UserSession userSession;
		if (!isUserSessionEstablished(request)) {
			userSession = login();
			if (userSession != null) {
				request.getSession().setAttribute(
						KEWConstants.USER_SESSION_KEY, userSession);
			}
		} else {
			userSession = (UserSession) request.getSession().getAttribute(
					KEWConstants.USER_SESSION_KEY);
		}

		if (userSession != null) {
			// Override the HttpServletRequest with one that provides
			// our logged-in user. This allows any engine-agnostic webapp code
			// that may be living in the context to obtain remote user
			// traditionally
			request = new HttpServletRequestWrapper(request) {
				@Override
                public String getRemoteUser() {
					return userSession.getPrincipalName();
				}
			};
		}

		// set up the thread local reference to the current authenticated user
		// and then forward to next filter in the chain
		try {
			UserSession.setAuthenticatedUser(userSession);
			chain.doFilter(request, response);
		} finally {
			UserSession.setAuthenticatedUser(null);
		}

	}

	/**
	 * create a UserSession object for the workflow user
	 *
	 * @param request
	 *            the servlet request
	 * @return UserSession object if authentication was successful, null
	 *         otherwise
	 */
	private UserSession login() {
		String principalName = null;
		KimPrincipal principal = null;
		IdentityManagementService idmService = KIMServiceLocator
				.getIdentityManagementService();
		principalName = "khuntley";
		principal = idmService.getPrincipalByPrincipalName(principalName);
		UserSession userSession = new UserSession(principal);
		return userSession;
	}
}
