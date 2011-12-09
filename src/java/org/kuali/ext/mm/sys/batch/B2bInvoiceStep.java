/**
 * 
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.kuali.ext.mm.sys.batch.service.B2bInvoiceService;

/**
 * @author harsha
 */
public class B2bInvoiceStep implements BatchStep {
    private B2bInvoiceService b2bInvoiceService;

    /**
     * Gets the b2bInvoiceService property
     * 
     * @return Returns the b2bInvoiceService
     */
    public B2bInvoiceService getB2bInvoiceService() {
        return this.b2bInvoiceService;
    }

    /**
     * Sets the b2bInvoiceService property value
     * 
     * @param b2bInvoiceService The b2bInvoiceService to set
     */
    public void setB2bInvoiceService(B2bInvoiceService b2bInvoiceService) {
        this.b2bInvoiceService = b2bInvoiceService;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    @Override
    public void execute(String jobName, Date time) {
        this.b2bInvoiceService.processB2bInvoices();
    }

}
