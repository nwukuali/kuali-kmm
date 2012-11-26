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

package org.kuali.ext.mm.integration.service;

import java.util.Collection;

/**
 * @author harsha07
 */
//TODO: NWU - Revist & only implement methods still applicable
public interface FinancialParameterService {

    /**
     * This method provides an exception free way to ensure that a parameter exists.
     *
     * @param componentClass
     * @param parameterName
     * @return boolean indicating whether or not the parameter exists
     */
    public boolean parameterExists(Class<? extends Object> componentClass, String parameterName);
//
//    /**
//     * This method provides a convenient way to access the a parameter that signifies true or false.
//     *
//     * @param componentClass
//     * @param parameterName
//     * @return boolean value of indicator parameter
//     */
//    public boolean getIndicatorParameter(Class<? extends Object> componentClass,
//            String parameterName);
//
//    /**
//     * This method provides a convenient way to access the a parameter that signifies true or false.
//     *
//     * @param namespaceCode
//     * @param detailTypeCode
//     * @param parameterName
//     * @return boolean value of indicator parameter
//     */
//    public boolean getIndicatorParameter(String namespaceCode, String detailTypeCode,
//            String parameterName);
//
//    /**
//     * This method returns the actual BusinessObject instance of a parameter.
//     *
//     * @param namespaceCode
//     * @param detailTypeCode
//     * @param parameterName
//     * @return The Parameter instance
//     */
//    public Parameter retrieveParameter(String namespaceCode, String detailTypeCode,
//            String parameterName);
//
    /**
     * This method returns the unprocessed text value of a parameter.
     *
     * @param componentClass
     * @param parameterName
     * @return unprocessed string value as a parameter
     */
    public String getParameterValueAsString(Class<? extends Object> componentClass, String parameterName);


	    /**
     * This method returns the unprocessed text values of a parameter.
     *
     * @param componentClass
     * @param parameterName
     * @return list of unprocessed string value as a parameter
     */
    public Collection<String> getParameterValuesAsString(Class<? extends Object> componentClass, String parameterName);

	    /**
     * This method returns the unprocessed boolean value of a parameter.
     *
     * @param componentClass
     * @param parameterName
     * @return unprocessed boolean value as a parameter
     */
		public Boolean getParameterValueAsBoolean(Class<? extends Object> componentClass, String parameterName);
//
//    /**
//     * This method can be used to derive a value based on another value.
//     *
//     * @param componentClass
//     * @param parameterName
//     * @param constrainingValue
//     * @return derived value
//     */
//    public String getParameterValue(Class<? extends Object> componentClass, String parameterName,
//            String constrainingValue);
//
//    /**
//     * This method returns the value of the specified parameter
//     *
//     * @param namespaceCode
//     * @param detailTypeCode
//     * @param parameterName
//     */
//    public String getParameterValue(String namespaceCode, String detailTypeCode,
//            String parameterName);
//
//    /**
//     * This method can be used to parse the value of a parameter.
//     *
//     * @param componentClass
//     * @param parameterName
//     * @return parsed List of String parameter values
//     */
//    public List<String> getParameterValues(Class<? extends Object> componentClass,
//            String parameterName);
//
//    /**
//     * This method can be used to derive a set of values based on another value.
//     *
//     * @param componentClass
//     * @param parameterName
//     * @param constrainingValue
//     * @return derived values List<String>
//     */
//    public List<String> getParameterValues(Class<? extends Object> componentClass,
//            String parameterName, String constrainingValue);
//
//    /**
//     * This method returns a list of the parameter values split on implementation specific criteria. For the default
//     * KualiConfigurationServiceImpl, the split is on a semi-colon.
//     *
//     * @param namespaceCode
//     * @param detailTypeCode
//     * @param parameterName
//     */
//    public List<String> getParameterValues(String namespaceCode, String detailTypeCode,
//            String parameterName);
//
//    /**
//     * This method will return an instance of a ParameterEvaluator implementation that will wrap a Parameter and provide convenient
//     * evaluation methods.
//     *
//     * @param componentClass
//     * @param parameterName
//     * @return ParameterEvaluator
//     */
//    public ParameterEvaluator getParameterEvaluator(Class<? extends Object> componentClass,
//            String parameterName);
//
//    /**
//     * This method will return an instance of a ParameterEvaluator implementation that will wrap a Parameter and provide convenient
//     * evaluation methods.
//     *
//     * @param namespaceCode
//     * @param detailTypeCode
//     * @param parameterName
//     * @return ParameterEvaluator
//     */
//    public ParameterEvaluator getParameterEvaluator(String namespaceCode, String detailTypeCode,
//            String parameterName);
//
//    /**
//     * This method will return an instance of a ParameterEvaluator implementation that will wrap a Parameter and constrainedValue
//     * and provide convenient evaluation methods.
//     *
//     * @param componentClass
//     * @param parameterName
//     * @return ParameterEvaluator
//     */
//    public ParameterEvaluator getParameterEvaluator(Class<? extends Object> componentClass,
//            String parameterName, String constrainedValue);
//
//    /**
//     * This method will return an instance of a ParameterEvaluator implementation that will wrap a Parameter and constrainedValue
//     * and provide convenient evaluation methods.
//     *
//     * @param namespaceCode
//     * @param detailTypeCode
//     * @param parameterName
//     * @return ParameterEvaluator
//     */
//    public ParameterEvaluator getParameterEvaluator(String namespaceCode, String detailTypeCode,
//            String parameterName, String constrainedValue);
//
//    /**
//     * This method will return an instance of a ParameterEvaluator implementation that will wrap a Parameter, constrainingValue, and
//     * constrainedValue and provide convenient evaluation methods.
//     *
//     * @param componentClass
//     * @param parameterName
//     * @return ParameterEvaluator
//     */
//    public ParameterEvaluator getParameterEvaluator(Class<? extends Object> componentClass,
//            String parameterName, String constrainingValue, String constrainedValue);
//
//    /**
//     * This method will return an instance of a ParameterEvaluator implementation that will wrap an allow Parameter, a deny
//     * Parameter, constrainingValue, and constrainedValue and provide convenient evaluation methods.
//     *
//     * @param componentClass
//     * @param parameterName
//     * @return ParameterEvaluator
//     */
//    public ParameterEvaluator getParameterEvaluator(Class<? extends Object> componentClass,
//            String allowParameterName, String denyParameterName, String constrainingValue,
//            String constrainedValue);
//
//
//    /**
//     * This method can be used to set a namespace.
//     *
//     * @param documentOrStepClass
//     */
//    public String getNamespace(Class<? extends Object> documentOrStepClass);
//
//    /**
//     * This method can be used to change the value of a Parameter for unit testing purposes.
//     *
//     * @param documentOrStepClass
//     */
//    public String getDetailType(Class<? extends Object> documentOrStepClass);
//
//    /**
//     * This method can be used to retrieve a list of parameters that match the given fieldValues criteria. You could also specify
//     * the "like" criteria in the Map.
//     *
//     * @param fieldValues The Map containing the key value pairs to be used to build the criteria.
//     * @return List of Parameters that match the criteria.
//     */
//    public List<Parameter> retrieveParametersGivenLookupCriteria(Map<String, String> fieldValues);
}
