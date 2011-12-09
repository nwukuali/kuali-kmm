/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;

/**
 * @author harsha07
 */
public interface PickVerifyAccountDao {
    public Map<String, List<FinancialAccountingLine>> getPickVerifiedAccountingLines(
            Timestamp lastScanTime);

    public Map<String, List<FinancialInternalBillingItem>> getPickVerifiedOrderLines(
            Timestamp lastScanTime);
}
