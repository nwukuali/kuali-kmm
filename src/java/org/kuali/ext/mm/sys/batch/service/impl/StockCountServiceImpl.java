/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import org.kuali.ext.mm.sys.batch.dataaccess.StockCountDao;
import org.kuali.ext.mm.sys.batch.service.StockCountService;

/**
 * @author harsha07
 */
public class StockCountServiceImpl implements StockCountService {
    private StockCountDao stockCountDao;

    /**
     * @see org.kuali.ext.mm.sys.batch.service.StockCountService#dailyStockCountInsert()
     */
    public int dailyStockCountInsert() {
        return this.stockCountDao.dailyStockCountInsert();
    }

    /**
     * Gets the stockCountDao property
     *
     * @return Returns the stockCountDao
     */
    public StockCountDao getStockCountDao() {
        return this.stockCountDao;
    }

    /**
     * Sets the stockCountDao property value
     *
     * @param stockCountDao The stockCountDao to set
     */
    public void setStockCountDao(StockCountDao stockCountDao) {
        this.stockCountDao = stockCountDao;
    }

}
