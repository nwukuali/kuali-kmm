package org.kuali.ext.mm.document.service;

import org.kuali.rice.krad.bo.PersistableBusinessObject;

import java.util.List;

public interface BusinessObjectLockingService {
    
    public void createAndSaveLock(String documentNumber, PersistableBusinessObject bo,
            String keyField);
    
    public void createAndSaveLock(String documentNumber, PersistableBusinessObject bo,
            List<String> keyFields);

    public void createAndSaveLocks(String documentNumber, List<PersistableBusinessObject> bos,
            List<String> keyFields);

    public List<String> getLockingDocumentIds(PersistableBusinessObject bo, String keyField,
            String... excludedDocs);
    
    public List<String> getLockingDocumentIds(PersistableBusinessObject bo, List<String> keyFields,
            String... excludedDocs);

    public void deleteLocks(String documentNumber);
}
