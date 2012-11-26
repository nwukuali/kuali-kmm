package org.kuali.ext.mm.sys.service.impl;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.ext.mm.integration.FinancialSystemConfiguration;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.impl.ModuleServiceBase;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * MM Implementation of {@link ModuleServiceBase} this will help in invoking financial system specific lookups, inquiry and services
 * when applicable.
 */
@SuppressWarnings("unchecked")
public class MmModuleServiceImpl extends ModuleServiceBase {
    private FinancialSystemConfiguration financialSystemConfiguration;


    /**
     * @see org.kuali.rice.kns.service.impl.ModuleServiceBase#getExternalizableBusinessObject(java.lang.Class, java.util.Map)
     */
    @Override
    public <T extends ExternalizableBusinessObject> T getExternalizableBusinessObject(
            Class<T> businessObjectClass, Map<String, Object> fieldValues) {
        if (FinancialSystemComponent.class.isAssignableFrom(businessObjectClass)) {
            return null;
        }
        Class<? extends ExternalizableBusinessObject> implementationClass = getExternalizableBusinessObjectImplementation(businessObjectClass);
        ExternalizableBusinessObject businessObject = (ExternalizableBusinessObject) getBusinessObjectService()
                .findByPrimaryKey(implementationClass, fieldValues);
        return (T) businessObject;
    }

    /**
     * @see org.kuali.rice.kns.service.impl.ModuleServiceBase#getExternalizableBusinessObjectsList(java.lang.Class, java.util.Map)
     */
    @Override
    public <T extends ExternalizableBusinessObject> List<T> getExternalizableBusinessObjectsList(
            Class<T> externalizableBusinessObjectClass, Map<String, Object> fieldValues) {
        if (FinancialSystemComponent.class.isAssignableFrom(externalizableBusinessObjectClass)) {
            return new ArrayList<T>();
        }
        Class<? extends ExternalizableBusinessObject> implementationClass = getExternalizableBusinessObjectImplementation(externalizableBusinessObjectClass);
        return (List<T>) getBusinessObjectService().findMatching(implementationClass, fieldValues);
    }

    /**
     * @see org.kuali.rice.kns.service.impl.ModuleServiceBase#listPrimaryKeyFieldNames(java.lang.Class)
     */
    @Override
    public List listPrimaryKeyFieldNames(Class businessObjectInterfaceClass) {
        Class clazz = getExternalizableBusinessObjectImplementation(businessObjectInterfaceClass);
        if (FinancialSystemComponent.class.isAssignableFrom(businessObjectInterfaceClass)) {
            List<String> pks = KNSServiceLocator.getDataDictionaryService().getDataDictionary()
                    .getBusinessObjectEntry(businessObjectInterfaceClass.getName())
                    .getPrimaryKeys();
            if (pks != null && !pks.isEmpty())
                return pks;

            return new ArrayList();
        }
        return KRADServiceLocator.getPersistenceStructureService().listPrimaryKeyFieldNames(clazz);
    }


    /**
     * @see org.kuali.rice.kns.service.impl.ModuleServiceBase#getExternalizableBusinessObjectLookupUrl(java.lang.Class,
     *      java.util.Map)
     */
    @Override
    public String getExternalizableBusinessObjectLookupUrl(Class inquiryBusinessObjectClass,
            Map<String, String> parameters) {
        if (FinancialSystemComponent.class.isAssignableFrom(inquiryBusinessObjectClass)) {
            return getFinancialSystemConfiguration().getBusinessObjectLookupUrl(
                    inquiryBusinessObjectClass, parameters);
        }

        Properties urlParameters = new Properties();
        String baseUrl = KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(
                KRADConstants.KUALI_RICE_URL_KEY);
        String lookupUrl = baseUrl;
        if (!lookupUrl.endsWith("/")) {
            lookupUrl = lookupUrl + "/";
        }
        if (parameters.containsKey(KRADConstants.MULTIPLE_VALUE)) {
            lookupUrl = lookupUrl + KRADConstants.MULTIPLE_VALUE_LOOKUP_ACTION;
        }
        else {
            lookupUrl = lookupUrl + KRADConstants.LOOKUP_ACTION;
        }
        for (String paramName : parameters.keySet()) {
            urlParameters.put(paramName, parameters.get(paramName));
        }

        Class clazz = getExternalizableBusinessObjectImplementation(inquiryBusinessObjectClass);
        urlParameters.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, clazz == null ? "" : clazz
                .getName());

        return UrlFactory.parameterizeUrl(lookupUrl, urlParameters);
    }

    /**
     * @see org.kuali.rice.kns.service.impl.ModuleServiceBase#getExternalizableBusinessObjectImplementation(java.lang.Class)
     */
    @Override
    public <E extends ExternalizableBusinessObject> Class<E> getExternalizableBusinessObjectImplementation(
            Class<E> externalizableBusinessObjectInterface) {
        if (getModuleConfiguration() == null) {
            throw new IllegalStateException(
                "Module configuration has not been initialized for the module service.");
        }
        if (FinancialSystemComponent.class.isAssignableFrom(externalizableBusinessObjectInterface)) {
            return getImplementationClassFromModuleConfig(externalizableBusinessObjectInterface);
        }
        int classModifiers = externalizableBusinessObjectInterface.getModifiers();
        if (!Modifier.isInterface(classModifiers) && !Modifier.isAbstract(classModifiers)) {
            // the interface is really a non-abstract class
            return externalizableBusinessObjectInterface;
        }
        if (getModuleConfiguration().getExternalizableBusinessObjectImplementations() == null) {
            return null;
        }
        return getImplementationClassFromModuleConfig(externalizableBusinessObjectInterface);

    }


    /**
     * Looks up at Externalizable Business Object Implementations mapping in Module Configuration and Integration Class Map in
     * Financial System Configuration to pick the right implementation class
     *
     * @param <E> External BusinessObject Interface
     * @param externalizableBusinessObjectInterface
     * @return External BusinessObject Implementation
     */
    protected <E> Class<E> getImplementationClassFromModuleConfig(
            Class<E> externalizableBusinessObjectInterface) {
        Class<E> implementationClass = null;
        if (FinancialSystemComponent.class.isAssignableFrom(externalizableBusinessObjectInterface)) {
            implementationClass = getFinancialSystemConfiguration().getIntegrationClassMap().get(
                    externalizableBusinessObjectInterface);

        }
        else {
            implementationClass = getModuleConfiguration()
                    .getExternalizableBusinessObjectImplementations().get(
                            externalizableBusinessObjectInterface);
        }
        int implClassModifiers = implementationClass.getModifiers();
        if (Modifier.isInterface(implClassModifiers) || Modifier.isAbstract(implClassModifiers)) {
            throw new RuntimeException(
                "Implementation class must be non-abstract class: ebo interface: "
                        + externalizableBusinessObjectInterface.getName() + " impl class: "
                        + implementationClass.getName() + " module: "
                        + getModuleConfiguration().getNamespaceCode());
        }
        return implementationClass;
    }

    /**
     * Gets the financialSystemConfiguration property
     *
     * @return Returns the financialSystemConfiguration
     */
    public FinancialSystemConfiguration getFinancialSystemConfiguration() {
        return this.financialSystemConfiguration;
    }

    /**
     * Sets the financialSystemConfiguration property value
     *
     * @param financialSystemConfiguration The financialSystemConfiguration to set
     */
    public void setFinancialSystemConfiguration(
            FinancialSystemConfiguration financialSystemConfiguration) {
        this.financialSystemConfiguration = financialSystemConfiguration;
    }
}
