/**
 *
 */
package org.kuali.ext.mm.businessobject.inquiry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.ext.mm.integration.FinancialSystemConfiguration;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.LookupUtils;


/**
 * MM overrides {@link KualiInquirableImpl} so that it can plug in Financial System specific inquiry URL
 */
@SuppressWarnings("unchecked")
public class MmInquirableImpl extends KualiInquirableImpl {
    /**
     * @see org.kuali.rice.kns.inquiry.KualiInquirableImpl#getInquiryUrl(org.kuali.rice.kns.bo.BusinessObject, java.lang.String,
     *      boolean)
     */
    @Override
    public HtmlData getInquiryUrl(BusinessObject businessObject, String attributeName,
            boolean forceInquiry) {
        Map primitiveReference = getPrimitiveReference(businessObject, attributeName);
        if (primitiveReference != null && !primitiveReference.isEmpty()) {
            String attributeRefName = (String) primitiveReference.keySet().iterator().next();
            Class inquiryBusinessObjectClass = (Class) primitiveReference.get(attributeRefName);
            if (FinancialSystemComponent.class.isAssignableFrom(inquiryBusinessObjectClass)) {
                return SpringContext.getBean(FinancialSystemConfiguration.class)
                        .getBusinessObjectInquiryUrl(businessObject, inquiryBusinessObjectClass,
                                attributeName, attributeRefName);
            }
        }


        return super.getInquiryUrl(businessObject, attributeName, forceInquiry);
    }

    /**
     * Finds primitive reference based on the Business Object Relationships. This is very similar to implementation of
     * {@link LookupUtils}
     *
     * @param businessObject Business object
     * @param attributeName Reference key attribute
     * @return Mapping of attribute to referenced object class
     */
    private Map getPrimitiveReference(BusinessObject businessObject, String attributeName) {
        Map chosenReferenceByKeySize = new HashMap();
        Map chosenReferenceByFieldName = new HashMap();
        Map primitiveReference = null;
        Map<String, Class> referenceClasses = getBusinessObjectMetaDataService()
                .getReferencesForForeignKey(businessObject, attributeName);
        if (referenceClasses != null && !referenceClasses.isEmpty()) {
            /*
             * if field is fk to more than one reference, take the class with the least # of pk fields, this should give the correct
             * grain for the attribute
             */
            int minKeys = Integer.MAX_VALUE;
            for (Iterator iter = referenceClasses.keySet().iterator(); iter.hasNext();) {
                String attr = (String) iter.next();
                Class clazz = referenceClasses.get(attr);
                List pkNames = getBusinessObjectMetaDataService().listPrimaryKeyFieldNames(clazz);

                // Compare based on key size.
                if (pkNames.size() < minKeys) {
                    minKeys = pkNames.size();
                    chosenReferenceByKeySize.clear();
                    chosenReferenceByKeySize.put(attr, clazz);
                }

                // Compare based on field name.
                if (attributeName.startsWith(attr)) {
                    chosenReferenceByFieldName.clear();
                    chosenReferenceByFieldName.put(attr, clazz);
                }
            }
            primitiveReference = chosenReferenceByFieldName.isEmpty() ? chosenReferenceByKeySize
                    : chosenReferenceByFieldName;
        }
        return primitiveReference;
    }
}
