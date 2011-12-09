/**
 * 
 */
package org.kuali.ext.mm.service.impl;

import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.businessobject.WarehouseObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

/**
 * @author harsha07
 */
public class WarehouseMaintainableImpl extends KualiMaintainableImpl {
    private static final long serialVersionUID = 1019943720620738496L;

    /**
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#processAfterCopy(org.kuali.rice.kns.document.MaintenanceDocument,
     *      java.util.Map)
     */
    @Override
    public void processAfterCopy(MaintenanceDocument document, Map<String, String[]> parameters) {
        super.processAfterCopy(document, parameters);
        // loop through and reset collection object primary keys
        Warehouse warehouse = (Warehouse) getBusinessObject();
        List<WarehouseAccounts> warehouseAccounts = warehouse.getWarehouseAccounts();
        if (warehouseAccounts != null && !warehouseAccounts.isEmpty()) {
            for (WarehouseAccounts warehouseAccount : warehouseAccounts) {
                warehouseAccount.setWarehouseAccountId(null);
            }
        }
        List<WarehouseObject> warehouseObjects = warehouse.getWarehouseObjects();
        if (warehouseObjects != null && !warehouseObjects.isEmpty()) {
            for (WarehouseObject warehouseObject : warehouseObjects) {
                warehouseObject.setWarehouseObjectId(null);
            }
        }

    }
}
