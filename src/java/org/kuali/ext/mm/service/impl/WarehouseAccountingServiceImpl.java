/**
 *
 */
package org.kuali.ext.mm.service.impl;

import java.util.Collection;
import java.util.HashMap;

import org.kuali.ext.mm.businessobject.StockTransReason;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.businessobject.WarehouseObject;
import org.kuali.ext.mm.service.WarehouseAccountingService;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.spring.CacheNoCopy;

/**
 * @author harsha07
 */
public class WarehouseAccountingServiceImpl implements WarehouseAccountingService {
    private BusinessObjectService businessObjectService;

    /**
     * @see org.kuali.ext.mm.service.WarehouseAccountingService#findWarehouseAccounts(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @CacheNoCopy
    public WarehouseAccounts findWarehouseAccounts(String warehouseCd,
            String warehouseAccountTypeCode) {
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("warehouseCd", warehouseCd);
        fieldValues.put("warehouseAccountTypeCode", warehouseAccountTypeCode);
        Collection matching = businessObjectService.findMatching(WarehouseAccounts.class,
                fieldValues);
        if (matching != null && !matching.isEmpty()) {
            return (WarehouseAccounts) matching.iterator().next();
        }
        return null;
    }

    /**
     * @see org.kuali.ext.mm.service.WarehouseAccountingService#findWarehouseObject(java.lang.String, java.lang.String)
     */
    public WarehouseObject findWarehouseObject(String warehouseCd, String warehouseObjectTypeCode) {
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("warehouseCd", warehouseCd);
        fieldValues.put("warehouseObjectTypeCd", warehouseObjectTypeCode);
        Collection matching = businessObjectService
                .findMatching(WarehouseObject.class, fieldValues);
        if (matching != null && !matching.isEmpty()) {
            return (WarehouseObject) matching.iterator().next();
        }
        return null;
    }

    /**
     * @see org.kuali.ext.mm.service.WarehouseAccountingService#findWarehouseObjectByReason(java.lang.String, java.lang.String)
     */
    public WarehouseObject findWarehouseObjectByReason(String warehouseCd, String transReasonCode) {
        HashMap<String, String> primaryKeys = new HashMap<String, String>();
        primaryKeys.put("stockTransReasonCd", transReasonCode);
        StockTransReason stockTransReason = (StockTransReason) businessObjectService
                .findByPrimaryKey(StockTransReason.class, primaryKeys);
        return findWarehouseObject(warehouseCd, stockTransReason.getObjectTypeCode());
    }

    /**
     * Gets the businessObjectService property
     *
     * @return Returns the businessObjectService
     */
    public BusinessObjectService getBusinessObjectService() {
        return this.businessObjectService;
    }

    /**
     * Sets the businessObjectService property value
     *
     * @param businessObjectService The businessObjectService to set
     */
    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }
}
