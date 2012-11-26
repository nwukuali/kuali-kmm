/**
 *
 */
package org.kuali.ext.mm.gl.service;

import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import java.util.HashMap;

/**
 * @author harsha07
 */
public interface TaxLiabilityGeneralLedgerService {
    public void incrementTaxLiability(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, KualiDecimal amount, String lineDescription);

    public void decrementTaxLiability(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, KualiDecimal amount, String lineDescription);
}
