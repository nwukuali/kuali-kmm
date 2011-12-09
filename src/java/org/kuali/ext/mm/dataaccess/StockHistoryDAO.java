/**
 *
 */
package org.kuali.ext.mm.dataaccess;

import java.util.Collection;
import java.util.Map;

import org.kuali.ext.mm.businessobject.CurrentStockHistoryInformation;
import org.kuali.ext.mm.businessobject.PurchaseHistory;
import org.kuali.ext.mm.businessobject.SalesHistory;

/**
 * @author rponraj
 *
 */
public interface StockHistoryDAO {

    public Map<String, SalesHistory> getSalesHistoryForStock(String stockId);

    public CurrentStockHistoryInformation getCurrentStockHistoryInformation(String stockId);

    public Collection<PurchaseHistory> getPurchaseHistoryForStock(String stockId);


}
