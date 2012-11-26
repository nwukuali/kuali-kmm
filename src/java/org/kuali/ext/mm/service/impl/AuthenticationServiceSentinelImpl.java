package org.kuali.ext.mm.service.impl;

import edu.msu.ais.d6501.sentinel.Credential;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.ext.mm.common.filter.AuthenticationFilter;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.kim.api.identity.AuthenticationService;
import org.kuali.rice.krad.dao.BusinessObjectDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthenticationServiceSentinelImpl implements AuthenticationService {

    public void setValidatePassword(boolean validatePassword) {
        this.validatePassword = validatePassword;
    }

    private ConfigurationService configService;

    public ConfigurationService getConfigService() {
        return configService;
    }

    public void setConfigService(ConfigurationService configService) {
        this.configService = configService;
    }

    private boolean validatePassword = true;
    private static Logger LOG = Logger.getLogger(AuthenticationServiceSentinelImpl.class);
    private BusinessObjectDao dao;

    public void setDao(BusinessObjectDao dao) {
        this.dao = dao;
    }

    public BusinessObjectDao getDao() {
        return dao;
    }

    public String getNetworkId(HttpServletRequest request) {
        LOG.info("Acquiring credentials object from session.");
        HttpSession session = request.getSession();
        String principalName = null;

        Credential credentials = (Credential) session.getAttribute(Credential.OBJECT_NAME);
        if (credentials != null) {
            LOG.info("Sentinel user: " + credentials.getPublicID());
            request.getSession().setAttribute(AuthenticationFilter.PUBLIC_ID,
                    credentials.getPublicID());
            return credentials.getPublicID();
        }
        if (request.getSession() != null) {
            principalName = (String) request.getSession().getAttribute(
                    AuthenticationFilter.PUBLIC_ID);
            if (StringUtils.isNotBlank(principalName))
                return principalName;
        }
        return null;
    }

    public boolean isValidatePassword() {
        return validatePassword;
    }

    /**
     * @see org.kuali.rice.kim.service.AuthenticationService#getPrincipalName(javax.servlet.http.HttpServletRequest)
     */
    public String getPrincipalName(HttpServletRequest request) {
        // Context: ASR-1983 Create prototype to support test users in QA
        // During QA testing in MSU the QA team wanted a feature that would
        // enable them to map general test users (EBSP001 ,002 etc)
        // to MSU users and when these test users login the user session will be
        // constructed for the mapped MSU principal. This will be
        // available depending on the property production.environment.code.
        String principalName = getNetworkId(request);
        // Null Check to prevent the NPE when we convert to uppercase below.. It may never by null but checking anyway
        if (principalName == null) {
            principalName = "";
        }
//        if (configService.isProductionEnvironment()) {
//
//        }
        return principalName;

    }
}
