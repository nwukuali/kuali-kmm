/**
 *
 */
package org.kuali.ext.mm.cart.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kim.bo.entity.KimPrincipal;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.util.KimConstants;
import org.kuali.rice.kns.UserSession;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.Guid;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.web.struts.action.KualiRequestProcessor;

/**
 * @author schneppd
 *
 * This class extends from KualiRequestProcessor to fix the getKualiSessionId method.
 * request.getCookies() was returning null and nothing was checking for this.  If the Rice source
 * of this project changes and this bug is fixed this should probably be removed.
 *
 */
public class StoresRequestProcessor extends KualiRequestProcessor {

	private static final String MDC_USER_ALREADY_SET = "userAlreadySet";

	private static final String MDC_USER = "user";

	private static final Logger LOG = Logger.getLogger(StoresRequestProcessor.class);

	/**
	 * override of the pre process for all struts requests which will ensure
	 * that we have the appropriate state for user sessions for all of our
	 * requests, also populating the GlobalVariables class with our UserSession
	 * for convenience to the non web layer based classes and implementations
	 */
	@Override
	protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) {
		UserSession userSession = null;
		if (!isUserSessionEstablished(request)) {
			String principalName = getIdentityManagementService().getAuthenticatedPrincipalName(request);
			if ( StringUtils.isNotBlank(principalName) ) {
				KimPrincipal principal = getIdentityManagementService().getPrincipalByPrincipalName( principalName );
				if ( principal != null ) {
					AttributeSet qualification = new AttributeSet();
					qualification.put( "principalId", principal.getPrincipalId() );
					// check to see if the given principal is an active principal/entity
					if ( getIdentityManagementService().isAuthorized(
							principal.getPrincipalId(),
							KimConstants.KIM_TYPE_DEFAULT_NAMESPACE,
							KimConstants.PermissionNames.LOG_IN,
							null,
							qualification ) ) {

						// This is a temp solution to show KIM AuthN checking existence of Principals.
						// We may want to move this code to the IdentityService once it is finished.
						userSession = new UserSession(principalName);
						if ( userSession.getPerson() == null ) {
							LOG.warn("Unknown User: " + principalName);
							throw new RuntimeException("Invalid User: " + principalName);
						}

						String kualiSessionId = this.getKualiSessionId(request, response);
						if (kualiSessionId == null) {
							kualiSessionId = new Guid().toString();
							response.addCookie(new Cookie(KNSConstants.KUALI_SESSION_ID, kualiSessionId));
						}
						userSession.setKualiSessionId(kualiSessionId);
					} /* if: principal is active */ else {
						LOG.warn("Principal is Inactive: " + principalName);
						throw new RuntimeException("You cannot log in, because you are not an active Kuali user.\nPlease ask someone to activate your account, if you need to use Kuali Systems.\nThe user id provided was: " + principalName + ".\n");
					}
				} /* if: principal is null */ else {
					LOG.warn("Principal Name not found in IdentityManagementService: " + principalName);
					throw new RuntimeException("Unknown User: " + principalName);
				}
			} /* if: principalName blank */ else {
				LOG.error( "Principal Name from the authentication service was blank!" );
				throw new RuntimeException( "Blank User from AuthenticationService - This should never happen." );
			}
		} else {
			userSession = (UserSession) request.getSession().getAttribute(KNSConstants.USER_SESSION_KEY);
		}

		request.getSession().setAttribute(KNSConstants.USER_SESSION_KEY, userSession);
		GlobalVariables.setUserSession(userSession);
		GlobalVariables.clear();
		if ( StringUtils.isNotBlank( request.getParameter(KNSConstants.BACKDOOR_PARAMETER) ) ) {
			if ( !KNSServiceLocator.getKualiConfigurationService().isProductionEnvironment() ) {
				if ( KNSServiceLocator.getParameterService().getIndicatorParameter(KNSConstants.KUALI_RICE_WORKFLOW_NAMESPACE, KNSConstants.DetailTypes.BACKDOOR_DETAIL_TYPE, KEWConstants.SHOW_BACK_DOOR_LOGIN_IND) ) {
	    			userSession.setBackdoorUser(request.getParameter(KNSConstants.BACKDOOR_PARAMETER));
	    			org.kuali.rice.kew.web.session.UserSession.getAuthenticatedUser().establishBackdoorWithPrincipalName(request.getParameter(KNSConstants.BACKDOOR_PARAMETER));
				}
			}
		}

		if (MDC.get(MDC_USER) != null) {
			// abuse the MDC to prevent removing user prematurely if it was set elsewhere (e.g. UserLoginFilter)
			MDC.put(MDC_USER_ALREADY_SET, Boolean.TRUE);
		} else {
			MDC.put(MDC_USER, userSession.getPrincipalId());
		}

		return true;
	}

	/**
	 * Checks if the user who made the request has a UserSession established
	 *
	 * @param request
	 *            the HTTPServletRequest object passed in
	 * @return true if the user session has been established, false otherwise
	 */
	private boolean isUserSessionEstablished(HttpServletRequest request) {
		boolean result = (request.getSession().getAttribute(KNSConstants.USER_SESSION_KEY) != null);
		return result;
	}

	private String getKualiSessionId(HttpServletRequest request, HttpServletResponse response) {
		String kualiSessionId = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (KNSConstants.KUALI_SESSION_ID.equals(cookie.getName()))
					kualiSessionId = cookie.getValue();
			}
		}
		return kualiSessionId;
	}
}
