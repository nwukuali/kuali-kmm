/**
 * 
 */
package org.kuali.ext.mm.sys.service.impl;

import org.apache.ojb.broker.metadata.ClassDescriptor;
import org.apache.ojb.broker.metadata.FieldDescriptor;
import org.kuali.ext.mm.sys.service.MMPersistenceStructureService;
import org.kuali.rice.krad.service.impl.PersistenceStructureServiceOjbImpl;

/**
 * @author schneppd
 *
 */
public class MMPersistenceStructureServiceOjbImpl extends PersistenceStructureServiceOjbImpl implements MMPersistenceStructureService {
    
    /**
     * @see org.kuali.ext.mm.sys.service.impl.MMPersistenceStructureService#getColumnName(java.lang.Class, java.lang.String)
     */
    public String getColumnName(Class clazz, String fieldName) {
        ClassDescriptor classDescriptor = getClassDescriptor(clazz);
        FieldDescriptor fieldDescriptors[] = classDescriptor.getFieldDescriptions();
        for (int i = 0; i < fieldDescriptors.length; ++i) {
            FieldDescriptor fieldDescriptor = fieldDescriptors[i];
            if(fieldDescriptor.getAttributeName().equals(fieldName))
                return fieldDescriptor.getColumnName();
        }
        
        return fieldName;
    }

}
