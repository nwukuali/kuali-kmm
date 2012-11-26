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

package org.kuali.ext.mm.integration;

import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

import java.util.List;
import java.util.Map;

/**
 * Basic definition of financial interfacing data
 */
@SuppressWarnings("unchecked")
public interface FinancialSystemConfiguration {

    /**
     * Financial application name for e.g. KFS
     */
    public String getAppName();

    /**
     * Financial application base web url path
     */

    public String getAppUrl();

    /**
     * List of services exposed by the financial application and naming details to look them up
     * 
     * @return List of service naming details
     */
    public List<ServiceNameInfo> getServiceNames();

    /**
     * Returns a specific service details for a give service name
     * 
     * @param name Name of the service in the configuration
     * @return Service Name details
     */
    public ServiceNameInfo getServiceNameInfo(String name);

    /**
     * Returns a list of class mapping for financial object classes
     * 
     * @return Class mapping
     */

    public Map<Class, Class> getIntegrationClassMap();


    /**
     * Implementation of Financial System Adaptor Factory used to created Service handles
     * 
     * @return Actual implementation of System Adaptor Factory
     */
    public FinancialSystemAdaptorFactory getFinancialSystemAdaptorFactory();


    /**
     * Builds and returns business object lookup url for financial business objects as per the mapping defined
     * 
     * @param businessObjectClass business class
     * @param parameters additional parameters
     * @return lookup url
     */
    public String getBusinessObjectLookupUrl(Class businessObjectClass,
            Map<String, String> parameters);

    /**
     * @param businessObject Business Object on which reference is specified
     * @param inquiryBusinessObjectClass Inquirable Buiness Object Reference class
     * @param attributeName Attribute on the business object
     * @param attributeRefName Reference attribute on the business object
     * @return Inquiry Url
     */
    public HtmlData getBusinessObjectInquiryUrl(BusinessObject businessObject,
            Class inquiryBusinessObjectClass, String attributeName, String attributeRefName);

    /**
     * Get financial system origin code
     * 
     * @return Financial system origin code
     */
    public String getFinancialSystemOriginCode();
}
