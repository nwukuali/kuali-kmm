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
public interface ReturnOrderBillingDao {
    public Map<String, List<FinancialAccountingLine>> getReturnedOrderAccountingLines(
            String returnDocNumber);

    public Map<String, List<FinancialInternalBillingItem>> getReturnedOrderLines(
            String returnDocNumber);

    public int updateOrderstatus(String returnDocNumber, String orderStatus);

    public boolean hasAccountsForBilling(String returnDocNumber);
}
