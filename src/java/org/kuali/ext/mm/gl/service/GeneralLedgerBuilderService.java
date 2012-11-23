/**
 *
 */
package org.kuali.ext.mm.gl.service;

import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import java.util.HashMap;
import java.util.List;

/**
 * @author harsha07
 */
public interface GeneralLedgerBuilderService {
    public void decrementInventory(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal amount,
            String lineDescription);

    public void incrementInventory(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal amount,
            String lineDescription);

    public void incrementResaleItems(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal amount,
            String lineDescription);

    public void decrementResaleItems(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal amount,
            String lineDescription);

    public void incrementCostOfGoods(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal amount, String lineDescription);

    public void decrementCostOfGoods(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal amount, String lineDescription);

    public void buildBillingGlpes(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, List<FinancialAccountingLine> incomeAcctLines,
            List<FinancialAccountingLine> expenseAcctLines, String lineDescription);
}
