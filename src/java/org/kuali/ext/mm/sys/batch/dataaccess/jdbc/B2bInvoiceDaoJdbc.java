/**
 * 
 */
package org.kuali.ext.mm.sys.batch.dataaccess.jdbc;

import org.kuali.ext.mm.sys.batch.dataaccess.B2bInvoiceDao;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;

import java.util.List;

/**
 * @author harsha
 */
public class B2bInvoiceDaoJdbc extends PlatformAwareDaoBaseJdbc implements B2bInvoiceDao {
    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.B2bInvoiceDao#findPendingInvoiceIds()
     */
    @Override
    public List<String> findPendingInvoiceIds() {
        return getJdbcTemplate()
                .queryForList(
                        "select key_id from mm_xml_invoice_t where processed_ind='N' order by last_update_dt",
                        String.class);
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.B2bInvoiceDao#findInvoicedQuantity(java.lang.Integer)
     */
    @Override
    public Integer findInvoicedQuantity(Integer orderDetailId) {
        return getJdbcTemplate().queryForInt(
                "select coalesce(sum(item_qty),0) from mm_b2b_invoice_dtl_t where order_detail_id = "
                        + orderDetailId);
    }
}
