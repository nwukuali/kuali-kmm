package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.kuali.ext.mm.sys.batch.service.OrderReconciliationService;

/**
 * @author rshrivas
 *
 */
public class OrderReconciliationStep implements BatchStep{

    private OrderReconciliationService orderReconciliationService;



    /**
     * Gets the orderReconciliationService property
     * @return Returns the orderReconciliationService
     */
    public OrderReconciliationService getOrderReconciliationService() {
        return this.orderReconciliationService;
    }



    /**
     * Sets the orderReconciliationService property value
     * @param orderReconciliationService The orderReconciliationService to set
     */
    public void setOrderReconciliationService(OrderReconciliationService orderReconciliationService) {
        this.orderReconciliationService = orderReconciliationService;
    }



    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
       orderReconciliationService.reconcileOrders();
    }

}
