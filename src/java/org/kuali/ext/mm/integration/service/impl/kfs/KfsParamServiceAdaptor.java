/**
 *
 */
package org.kuali.ext.mm.integration.service.impl.kfs;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.ext.mm.integration.service.FinancialParameterService;
import org.kuali.rice.kns.bo.Parameter;
import org.kuali.rice.kns.service.ParameterEvaluator;
import org.kuali.rice.kns.service.ParameterService;

/**
 * @author harsha07
 */
public class KfsParamServiceAdaptor extends KfsServiceAdaptor<ParameterService> implements
        FinancialParameterService {

    /**
     * @param name
     */
    public KfsParamServiceAdaptor(QName name) {
        super(name);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getDetailType(java.lang.Class)
     */
    public String getDetailType(Class<? extends Object> documentOrStepClass) {
        return getService().getDetailType(documentOrStepClass);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getIndicatorParameter(java.lang.Class, java.lang.String)
     */
    public boolean getIndicatorParameter(Class<? extends Object> componentClass,
            String parameterName) {
        return getService().getIndicatorParameter(componentClass, parameterName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getIndicatorParameter(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public boolean getIndicatorParameter(String namespaceCode, String detailTypeCode,
            String parameterName) {
        return getService().getIndicatorParameter(namespaceCode, detailTypeCode, parameterName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getNamespace(java.lang.Class)
     */
    public String getNamespace(Class<? extends Object> documentOrStepClass) {
        return getService().getNamespace(documentOrStepClass);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterEvaluator(java.lang.Class, java.lang.String)
     */
    public ParameterEvaluator getParameterEvaluator(Class<? extends Object> componentClass,
            String parameterName) {
        return getService().getParameterEvaluator(componentClass, parameterName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterEvaluator(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public ParameterEvaluator getParameterEvaluator(String namespaceCode, String detailTypeCode,
            String parameterName) {
        return getService().getParameterEvaluator(namespaceCode, detailTypeCode, parameterName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterEvaluator(java.lang.Class, java.lang.String,
     *      java.lang.String)
     */
    public ParameterEvaluator getParameterEvaluator(Class<? extends Object> componentClass,
            String parameterName, String constrainedValue) {
        return getService().getParameterEvaluator(componentClass, parameterName, constrainedValue);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterEvaluator(java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public ParameterEvaluator getParameterEvaluator(String namespaceCode, String detailTypeCode,
            String parameterName, String constrainedValue) {
        return getService().getParameterEvaluator(namespaceCode, detailTypeCode, parameterName,
                constrainedValue);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterEvaluator(java.lang.Class, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public ParameterEvaluator getParameterEvaluator(Class<? extends Object> componentClass,
            String parameterName, String constrainingValue, String constrainedValue) {
        return getService().getParameterEvaluator(componentClass, parameterName, constrainingValue,
                constrainedValue);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterEvaluator(java.lang.Class, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public ParameterEvaluator getParameterEvaluator(Class<? extends Object> componentClass,
            String allowParameterName, String denyParameterName, String constrainingValue,
            String constrainedValue) {
        return getService().getParameterEvaluator(componentClass, allowParameterName,
                denyParameterName, constrainingValue, constrainedValue);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterValue(java.lang.Class, java.lang.String)
     */
    public String getParameterValue(Class<? extends Object> componentClass, String parameterName) {
        return getService().getParameterValue(componentClass, parameterName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterValue(java.lang.Class, java.lang.String,
     *      java.lang.String)
     */
    public String getParameterValue(Class<? extends Object> componentClass, String parameterName,
            String constrainingValue) {
        return getService().getParameterValue(componentClass, parameterName, constrainingValue);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterValue(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public String getParameterValue(String namespaceCode, String detailTypeCode,
            String parameterName) {
        return getService().getParameterValue(namespaceCode, detailTypeCode, parameterName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterValues(java.lang.Class, java.lang.String)
     */
    public List<String> getParameterValues(Class<? extends Object> componentClass,
            String parameterName) {
        return getService().getParameterValues(componentClass, parameterName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterValues(java.lang.Class, java.lang.String,
     *      java.lang.String)
     */
    public List<String> getParameterValues(Class<? extends Object> componentClass,
            String parameterName, String constrainingValue) {
        return getService().getParameterValues(componentClass, parameterName, constrainingValue);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#getParameterValues(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public List<String> getParameterValues(String namespaceCode, String detailTypeCode,
            String parameterName) {
        return getService().getParameterValues(namespaceCode, detailTypeCode, parameterName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#parameterExists(java.lang.Class, java.lang.String)
     */
    public boolean parameterExists(Class<? extends Object> componentClass, String parameterName) {
        return getService().parameterExists(componentClass, parameterName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#retrieveParameter(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public Parameter retrieveParameter(String namespaceCode, String detailTypeCode,
            String parameterName) {
        return getService().retrieveParameter(namespaceCode, detailTypeCode, parameterName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialParameterService#retrieveParametersGivenLookupCriteria(java.util.Map)
     */
    public List<Parameter> retrieveParametersGivenLookupCriteria(Map<String, String> fieldValues) {
        return getService().retrieveParametersGivenLookupCriteria(fieldValues);
    }

}
