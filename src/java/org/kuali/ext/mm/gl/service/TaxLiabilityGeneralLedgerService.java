/**
 *
 */
package org.kuali.ext.mm.gl.service;

import java.util.HashMap;

import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.rice.kns.util.KualiDecimal;

/**
 * @author harsha07
 */
public interface TaxLiabilityGeneralLedgerService {
    public void incrementTaxLiability(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, KualiDecimal amount, String lineDescription);

    public void decrementTaxLiability(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, KualiDecimal amount, String lineDescription);
}
