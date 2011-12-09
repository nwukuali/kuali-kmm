/**
 * 
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.util.List;


/**
 * @author harsha
 *
 */
public interface B2bInvoiceDao {

    public List<String> findPendingInvoiceIds();
    
    public Integer findInvoicedQuantity(Integer orderDetailId);

}