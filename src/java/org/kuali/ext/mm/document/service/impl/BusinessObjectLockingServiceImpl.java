package org.kuali.ext.mm.document.service.impl;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.service.BusinessObjectLockingService;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceLock;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.MaintenanceDocumentService;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.*;


/**
 * This lock mechanism is disabled right now.
 *
 * @author harsha07
 */
public class BusinessObjectLockingServiceImpl implements BusinessObjectLockingService {
    
    /**
     * @param documentNumber Locking document number
     * @param bo Business object to be locked
     * @param keyFields - single key field identifying a unique record of bo
     */
    public void createAndSaveLock(String documentNumber, PersistableBusinessObject bo,
            String keyField) {
        List<String> lockingKeys = new ArrayList<String>();
        lockingKeys.add(keyField);
        createAndSaveLock(documentNumber, bo, lockingKeys);
    }
    
    /**
     * @param documentNumber Locking document number
     * @param bo Business object to be locked
     * @param keyFields key fields identifying a unique record of bo
     */
    public void createAndSaveLock(String documentNumber, PersistableBusinessObject bo,
            List<String> keyFields) {
        if (bo != null) {
            List<MaintenanceLock> maintenanceLocks = new ArrayList<MaintenanceLock>(1);
            maintenanceLocks.add(createMaintenanceLock(documentNumber, bo, keyFields));
            SpringContext.getBean(MaintenanceDocumentService.class).storeLocks(maintenanceLocks);
        }
    }

    /**
     * @param documentNumber Locking document number
     * @param bos Business objects to be locked
     * @param keyFields key fields identifying a unique record of bo
     */
    public void createAndSaveLocks(String documentNumber, List<PersistableBusinessObject> bos,
            List<String> keyFields) {
        if (bos != null) {
            List<MaintenanceLock> maintenanceLocks = new ArrayList<MaintenanceLock>(1);
            for (PersistableBusinessObject bo : bos) {
                maintenanceLocks.add(createMaintenanceLock(documentNumber, bo, keyFields));
            }
            SpringContext.getBean(MaintenanceDocumentService.class).storeLocks(maintenanceLocks);
        }
    }

    protected MaintenanceLock createMaintenanceLock(String documentNumber,
            PersistableBusinessObject bo, List<String> keyFields) {
        StringBuffer lockRepresentation = createLockRepresentation(bo, keyFields);
        MaintenanceLock maintenanceLock = new MaintenanceLock();
        maintenanceLock.setDocumentNumber(documentNumber);
        maintenanceLock.setLockingRepresentation(lockRepresentation.toString());
        return maintenanceLock;
    }

    /**
     * Creates a lock representation using a common convention like this
     * org.xx..xx.BoClassName!!fieldName1^^fieldValue1::fieldName2^^fieldValue2
     *
     * @param bo Business object to be locked
     * @param keyFields Identification fields
     * @return Lock representation
     */
    protected StringBuffer createLockRepresentation(PersistableBusinessObject bo,
            List<String> keyFields) {
        StringBuffer lockRepresentation = new StringBuffer(bo.getClass().getName());
        lockRepresentation.append(KRADConstants.Maintenance.LOCK_AFTER_CLASS_DELIM);
        int pos = 0;
        for (String fieldName : keyFields) {
            pos++;
            Object fieldValue = ObjectUtils.getPropertyValue(bo, fieldName);
            if (fieldValue == null) {
                fieldValue = "";
            }
            lockRepresentation.append(fieldName);
            lockRepresentation.append(KRADConstants.Maintenance.LOCK_AFTER_FIELDNAME_DELIM);
            lockRepresentation.append(String.valueOf(fieldValue));
            if (pos < keyFields.size()) {
                lockRepresentation.append(KRADConstants.Maintenance.LOCK_AFTER_VALUE_DELIM);
            }
        }
        return lockRepresentation;
    }

    /**
     * @see org.kuali.ext.mm.document.service.BusinessObjectLockingService#getLockingDocumentIds(org.kuali.rice.kns.bo.PersistableBusinessObject, java.lang.String, java.lang.String[])
     */
    public List<String> getLockingDocumentIds(PersistableBusinessObject bo, String keyField,
            String... excludedDocs) {
        List<String> keyFields = new ArrayList<String>();
        keyFields.add(keyField);
        return getLockingDocumentIds(bo, keyFields, excludedDocs);
    }
    
    /**
     * @param bo Business object
     * @param keyFields identification fields
     * @return list of current locking ids
     */
    public List<String> getLockingDocumentIds(PersistableBusinessObject bo, List<String> keyFields,
            String... excludedDocs) {
        HashSet<String> excludeSet = new HashSet<String>();
        if (excludedDocs != null) {
            for (String string : excludedDocs) {
                excludeSet.add(string);
            }
        }
        List<String> docIds = new ArrayList<String>();
        StringBuffer lockRepresentation = createLockRepresentation(bo, keyFields);
        Map<String, String> fields = new HashMap<String, String>();
        fields.put("lockingRepresentation", lockRepresentation.toString());
        Collection<?> match = SpringContext.getBean(BusinessObjectService.class).findMatching(
                MaintenanceLock.class, fields);
        if (match != null) {
            Iterator<?> locks = match.iterator();
            while (locks.hasNext()) {
                String documentNumber = ((MaintenanceLock) locks.next()).getDocumentNumber();
                if (!excludeSet.contains(documentNumber)) {
                    docIds.add(documentNumber);
                }
            }
        }
        return docIds;
    }

    /**
     * @param documentNumber Deletes all the locks by this document
     */
    public void deleteLocks(String documentNumber) {
        SpringContext.getBean(MaintenanceDocumentService.class).deleteLocks(documentNumber);
    }
}
