/**
 * 
 */
package org.kuali.ext.mm.businessobject;

import java.util.Map;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.dataaccess.StockCountDao;


/**
 * @author harsha07
 */
public final class StockCountMap {

    private static ThreadLocal<Map<String, Integer>> localCountMap = new ThreadLocal<Map<String, Integer>>();

    public static Integer getQuantityOnHand(String worksheetDocNbr, String stockId, Integer binId) {
        Integer count;

        if (localCountMap.get() == null
                || (count = localCountMap.get().get(stockId + "-" + binId)) == null) {
            StockCountDao stockCountDao = SpringContext.getBean(StockCountDao.class);
            localCountMap.set(stockCountDao.getQtyOnHandMap(worksheetDocNbr));
        }
        if ((count = localCountMap.get().get(stockId + "-" + binId)) != null) {
            return count;
        }
        return 0;
    }

    // Servlet threads are pooled depending on the server implementation, it is necessary to call reset at the beginning of the
    // user action to use this thread local implementation correctly.
    public static void reset() {
        localCountMap.set(null);
    }

    public static Integer getMismatchedCount(String worksheetDocNbr) {
        Integer mismatchedCount = SpringContext.getBean(StockCountDao.class).getMismatchedCount(
                worksheetDocNbr);
        if (mismatchedCount != null) {
            return mismatchedCount;
        }
        return 0;
    }

    public static Integer getTotalStockCount(String worksheetDocNbr) {

        Integer totalStockCount = SpringContext.getBean(StockCountDao.class).getTotalStockCount(
                worksheetDocNbr);
        if (totalStockCount != null) {
            return totalStockCount;
        }
        return 0;
    }

}
