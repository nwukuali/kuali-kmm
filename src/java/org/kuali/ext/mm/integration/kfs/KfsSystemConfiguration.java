/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.ext.mm.integration.kfs;

import java.lang.reflect.Modifier;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.FinancialSystemConfiguration;
import org.kuali.ext.mm.integration.ServiceNameInfo;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.bo.BusinessObjectRelationship;
import org.kuali.rice.kns.datadictionary.AttributeSecurity;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.util.UrlFactory;
import org.kuali.rice.kns.web.format.Formatter;
import org.springframework.beans.factory.InitializingBean;

/**
 * KFS System configuration
 */
@SuppressWarnings("unchecked")
public class KfsSystemConfiguration implements FinancialSystemConfiguration, InitializingBean {
    private String appName;
    private String appUrl;
    private String riceAppName;
    private String financialSystemOriginCode;
    private List<ServiceNameInfo> serviceNames = new ArrayList<ServiceNameInfo>();
    private Map<String, ServiceNameInfo> serviceMap = new HashMap<String, ServiceNameInfo>();
    private Map<Class, Class> integrationClassMap = new HashMap<Class, Class>();
    private FinancialSystemAdaptorFactory financialSystemAdaptorFactory;


    /**
     * Gets the appName property
     *
     * @return Returns the appName
     */
    public String getAppName() {
        return this.appName;
    }


    /**
     * Sets the appName property value
     *
     * @param appName The appName to set
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }


    /**
     * Gets the appUrl property
     *
     * @return Returns the appUrl
     */
    public String getAppUrl() {
        return this.appUrl;
    }


    /**
     * Sets the appUrl property value
     *
     * @param appUrl The appUrl to set
     */
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }


    /**
     * Gets the riceAppName property
     *
     * @return Returns the riceAppName
     */
    public String getRiceAppName() {
        return this.riceAppName;
    }


    /**
     * Sets the riceAppName property value
     *
     * @param riceAppName The riceAppName to set
     */
    public void setRiceAppName(String riceAppName) {
        this.riceAppName = riceAppName;
    }


    /**
     * Gets the serviceNames property
     *
     * @return Returns the serviceNames
     */
    public List<ServiceNameInfo> getServiceNames() {
        return this.serviceNames;
    }


    /**
     * Sets the serviceNames property value
     *
     * @param serviceNames The serviceNames to set
     */
    public void setServiceNames(List<ServiceNameInfo> serviceNames) {
        this.serviceNames = serviceNames;
    }


    /**
     * Gets the serviceMap property
     *
     * @return Returns the serviceMap
     */
    public Map<String, ServiceNameInfo> getServiceMap() {
        return this.serviceMap;
    }


    /**
     * Sets the serviceMap property value
     *
     * @param serviceMap The serviceMap to set
     */
    public void setServiceMap(Map<String, ServiceNameInfo> serviceMap) {
        this.serviceMap = serviceMap;
    }


    /**
     * Gets the integrationClassMap property
     *
     * @return Returns the integrationClassMap
     */
    public Map<Class, Class> getIntegrationClassMap() {
        return this.integrationClassMap;
    }


    /**
     * Sets the integrationClassMap property value
     *
     * @param integrationClassMap The integrationClassMap to set
     */
    public void setIntegrationClassMap(Map<Class, Class> integrationClassMap) {
        this.integrationClassMap = integrationClassMap;
    }


    /**
     * Gets the financialSystemAdaptorFactory property
     *
     * @return Returns the financialSystemAdaptorFactory
     */
    public FinancialSystemAdaptorFactory getFinancialSystemAdaptorFactory() {
        return this.financialSystemAdaptorFactory;
    }


    /**
     * Sets the financialSystemAdaptorFactory property value
     *
     * @param financialSystemAdaptorFactory The financialSystemAdaptorFactory to set
     */
    public void setFinancialSystemAdaptorFactory(
            FinancialSystemAdaptorFactory financialSystemAdaptorFactory) {
        this.financialSystemAdaptorFactory = financialSystemAdaptorFactory;
    }


    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemConfiguration#getServiceNameInfo(java.lang.String)
     */
    public ServiceNameInfo getServiceNameInfo(String name) {
        if (this.serviceMap.isEmpty()) {
            if (!this.serviceNames.isEmpty()) {
                for (ServiceNameInfo nameInfo : this.serviceNames) {
                    this.serviceMap.put(nameInfo.getName(), nameInfo);
                }
            }
        }
        return this.serviceMap.get(name);
    }


    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        this.financialSystemAdaptorFactory.setFinancialSystemConfiguration(this);
    }


    protected String getInquiryUrl() {
        String inquiryUrl = getAppUrl() + "/" + getRiceAppName();
        if (!inquiryUrl.endsWith("/")) {
            inquiryUrl = inquiryUrl + "/";
        }
        return inquiryUrl + KNSConstants.INQUIRY_ACTION;
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemConfiguration#getBusinessObjectInquiryUrl(org.kuali.rice.kns.bo.BusinessObject,
     *      java.lang.Class, java.lang.String, java.lang.String)
     */
    public HtmlData getBusinessObjectInquiryUrl(BusinessObject businessObject,
            Class inquiryBusinessObjectClass, String attributeName, String attributeRefName) {
        Properties parameters = new Properties();
        parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, "start");
        parameters.put(KNSConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getIntegrationClassMap().get(
                inquiryBusinessObjectClass).getName());
        List<String> keys = new ArrayList<String>();
        Map<String, String> targetKeyMap = new HashMap<String, String>();
        BusinessObjectRelationship businessObjectRelationship = null;

        if (attributeRefName != null && !"".equals(attributeRefName)) {
            businessObjectRelationship = KNSServiceLocator.getBusinessObjectMetaDataService()
                    .getBusinessObjectRelationship(businessObject, attributeRefName);
            if (businessObjectRelationship != null
                    && businessObjectRelationship.getParentToChildReferences() != null) {
                Map<String, String> parentToChildReferences = businessObjectRelationship
                        .getParentToChildReferences();
                for (String targetNamePrimaryKey : parentToChildReferences.keySet()) {
                    keys.add(targetNamePrimaryKey);
                    targetKeyMap.put(targetNamePrimaryKey, parentToChildReferences
                            .get(targetNamePrimaryKey));
                }
            }
        }
        // build key value url parameters used to retrieve the business object
        String keyName = null;
        String keyConversion = null;
        Map<String, String> fieldList = new HashMap<String, String>();
        String targetKey = null;
        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            keyName = (String) iter.next();
            targetKey = targetKeyMap.get(keyName);
            keyConversion = keyName;
            if (ObjectUtils.isNestedAttribute(attributeName)) {
                keyConversion = ObjectUtils.getNestedAttributePrefix(attributeName) + "." + keyName;
            }
            else {
                keyConversion = keyName;
            }
            Object keyValue = null;
            if (keyConversion != null) {
                keyValue = ObjectUtils.getPropertyValue(businessObject, keyConversion);
            }

            if (keyValue == null) {
                keyValue = "";
            }
            else if (keyValue instanceof java.sql.Date) {
                if (Formatter.findFormatter(keyValue.getClass()) != null) {
                    Formatter formatter = Formatter.getFormatter(keyValue.getClass());
                    keyValue = formatter.format(keyValue);
                }
            }
            else {
                keyValue = keyValue.toString();
            }

            AttributeSecurity attributeSecurity = KNSServiceLocator.getDataDictionaryService()
                    .getAttributeSecurity(businessObject.getClass().getName(), keyName);
            if (attributeSecurity != null
                    && attributeSecurity.hasRestrictionThatRemovesValueFromUI()) {
                try {
                    keyValue = KNSServiceLocator.getEncryptionService().encrypt(keyValue);
                }
                catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                }
            }
            parameters.put(targetKey, keyValue);
            fieldList.put(targetKey, keyValue.toString());
        }
        return new AnchorHtmlData(UrlFactory.parameterizeUrl(getInquiryUrl(), parameters),
            KNSConstants.EMPTY_STRING);
    }

    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemConfiguration#getBusinessObjectLookupUrl(java.lang.Class, java.util.Map)
     */
    public String getBusinessObjectLookupUrl(Class businessObjectClass,
            Map<String, String> parameters) {
        Properties urlParameters = new Properties();
        String baseUrl = getAppUrl() + "/" + getRiceAppName();
        String lookupUrl = baseUrl;
        if (!lookupUrl.endsWith("/")) {
            lookupUrl = lookupUrl + "/";
        }
        if (parameters.containsKey(KNSConstants.MULTIPLE_VALUE)) {
            lookupUrl = lookupUrl + KNSConstants.MULTIPLE_VALUE_LOOKUP_ACTION;
        }
        else {
            lookupUrl = lookupUrl + KNSConstants.LOOKUP_ACTION;
        }
        for (String paramName : parameters.keySet()) {
            urlParameters.put(paramName, parameters.get(paramName));
        }

        Class implementationClass = getIntegrationClassMap().get(businessObjectClass);
        if (implementationClass == null) {
            throw new RuntimeException("Implementation class not found for " + businessObjectClass);
        }
        urlParameters.put(KNSConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, implementationClass
                .getName());
        int implClassModifiers = implementationClass.getModifiers();
        if (Modifier.isInterface(implClassModifiers) || Modifier.isAbstract(implClassModifiers)) {
            throw new RuntimeException(
                "Implementation class must be non-abstract class: ebo interface: "
                        + businessObjectClass.getName() + " impl class: "
                        + implementationClass.getName() + " module: " + "KFS");
        }
        return UrlFactory.parameterizeUrl(lookupUrl, urlParameters);
    }
    
    /**
     * @see org.kuali.ext.mm.integration.FinancialSystemConfiguration#getFinancialSystemOriginCode()
     */
    @Override
    public String getFinancialSystemOriginCode() {
        return this.financialSystemOriginCode;
    }


    /**
     * Sets the financialSystemOriginCode property value
     * @param financialSystemOriginCode The financialSystemOriginCode to set
     */
    public void setFinancialSystemOriginCode(String financialSystemOriginCode) {
        this.financialSystemOriginCode = financialSystemOriginCode;
    }
}
