/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.sys.batch.service.StockCountService;

/**
 * @author harsha07
 */
public class StockCountDailyInsertStep implements BatchStep {
    private static final Logger LOG = Logger.getLogger(StockCountDailyInsertStep.class);
    private StockCountService stockCountService;

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
        LOG.info("Starting step StockCountDailyInsertStep of batch job " + jobName);
        int dailyStockCountInsert = stockCountService.dailyStockCountInsert();
        LOG.info("Inserted record count - " + dailyStockCountInsert);
        LOG.info("Finished step StockCountDailyInsertStep of batch job " + jobName);
    }

    /**
     * Gets the stockCountService property
     *
     * @return Returns the stockCountService
     */
    public StockCountService getStockCountService() {
        return this.stockCountService;
    }

    /**
     * Sets the stockCountService property value
     *
     * @param stockCountService The stockCountService to set
     */
    public void setStockCountService(StockCountService stockCountService) {
        this.stockCountService = stockCountService;
    }

}
