/**
 *
 */
package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.businessobject.WarehouseObject;


/**
 * @author harsha07
 */
public interface WarehouseAccountingService {

    public WarehouseAccounts findWarehouseAccounts(String warehouseCd,
            String warehouseAccountTypeCode);

    public WarehouseObject findWarehouseObject(String warehouseCd, String warehouseObjectTypeCode);

    public WarehouseObject findWarehouseObjectByReason(String warehouseCd, String transReasonCode);

}