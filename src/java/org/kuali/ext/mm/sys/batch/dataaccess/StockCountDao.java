/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.util.Map;


/**
 * @author harsha07
 */
public interface StockCountDao {

    public int dailyStockCountInsert();

    public Map<String, Integer> getQtyOnHandMap(String worksheetDocNbr);

    public Integer getMismatchedCount(String worksheetDocNbr);

    public Integer getTotalStockCount(String worksheetDocNbr);

    public int fullInventoryStockCountInsert(String warehouseCode, String zoneCode,
            boolean excludeEmpty);

}