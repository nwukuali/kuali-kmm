/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.service.RecurringOrderService;

/**
 * @author schneppd
 */
public class RecurringOrderStep implements BatchStep {
    private static final Logger LOG = Logger.getLogger(RecurringOrderStep.class);
    private RecurringOrderService recurringOrderService;

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
        LOG.info("Starting step RecurringOrderStep of batch job " + jobName);
        getRecurringOrderService().process();
        LOG.info("Finished step RecurringOrderStep of batch job " + jobName);
    }

    /**
     * Sets the RecurringOrderService
     * 
     * @param recurringOrderService
     */
    public void setRecurringOrderService(RecurringOrderService recurringOrderService) {
        this.recurringOrderService = recurringOrderService;
    }

    /**
     * Gets the RecurringOrderService
     * 
     * @return a RecurringOrderService object
     */
    public RecurringOrderService getRecurringOrderService() {
        return recurringOrderService;
    }

}
