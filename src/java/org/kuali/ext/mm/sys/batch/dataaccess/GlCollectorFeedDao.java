/**
 * 
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.util.List;

import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;

/**
 * @author harsha07
 */
public interface GlCollectorFeedDao {
    public List<FinancialGeneralLedgerPendingEntry> getEntries(String warehouseCode);

    public int clearEntries(String warehouseCode);
}
