/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.businessobject.BackOrder;
import org.kuali.ext.mm.service.BackOrderService;

/**
 * @author harsha07
 */
public class BackOrderReleaseStep implements BatchStep {
    private static final Logger LOG = Logger.getLogger(BackOrderReleaseStep.class);
    private BackOrderService backOrderService;

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
        LOG.info("Starting step BackOrderReleaseStep of batch job " + jobName);
        Collection<BackOrder> activeBackOrders = getBackOrderService().getAllActiveBackOrders();
        for(BackOrder backOrder : activeBackOrders) {
            getBackOrderService().relieveBackOrder(backOrder, true);
        }
        LOG.info("Finished step BackOrderReleaseStep of batch job " + jobName);
    }

    /**
     * Setter for BackOrderService
     * 
     * @param backOrderService
     */
    public void setBackOrderService(BackOrderService backOrderService) {
        this.backOrderService = backOrderService;
    }

    /**
     * Getter for BackOrderService
     * 
     * @return backOrderService
     */
    public BackOrderService getBackOrderService() {
        return backOrderService;
    }

	

}
