/**
 * 
 */
package org.kuali.ext.mm.sys.batch.service;

/**
 * @author harsha
 */
public interface B2bInvoiceService {
    public void processB2bInvoices();
    
    public Integer getInvoicedQuantity(Integer orderDetailId);
}
