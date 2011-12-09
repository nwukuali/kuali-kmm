/**
 *
 */
package org.kuali.ext.mm.document.dataaccess;

import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;

/**
 * @author harsha07
 */
public interface PickVerifyBillingDao {
    public Map<String, List<FinancialAccountingLine>> getPickVerifiedAccountingLines(
            String pickTicketNumber);

    public Map<String, List<FinancialInternalBillingItem>> getPickVerifiedOrderLines(
            String pickTicketNumber);

    public boolean hasAccountsForBilling(String pickTicketNumber);

}
