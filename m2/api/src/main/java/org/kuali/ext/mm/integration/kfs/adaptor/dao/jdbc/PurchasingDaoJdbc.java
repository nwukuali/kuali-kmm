package org.kuali.ext.mm.integration.kfs.adaptor.dao.jdbc;

import org.kuali.ext.mm.integration.kfs.adaptor.dao.PurchasingDao;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchasingDaoJdbc extends PlatformAwareDaoBaseJdbc implements PurchasingDao {


    /**
     * Finds the latest PO id for a contract id
     */
    public int getLatestPurchaseOrderId(Integer vendorContractId) {
        return getJdbcTemplate().queryForInt(
                "select coalesce(max(po_id),0) from pur_po_t where vndr_contr_gnrtd_id = "
                        + vendorContractId + " and po_cur_ind='Y'");
    }

    /**
     * Calculates the total amount by org and contract
     * 
     * @param vendorContractId Contract id
     * @return Sum of po amount
     */
    public KualiDecimal getTotalAmountByContract(Integer vendorContractId) {
       return getJdbcTemplate()
                .query(
                        "select sum((coalesce(itm.itm_unit_prc,0) * coalesce(itm.itm_ord_qty,0)) + coalesce(itm.itm_tax_amt,0)) from pur_po_t po, pur_po_itm_t itm  "
                                + "where po.fdoc_nbr = itm.fdoc_nbr and po.po_cur_ind = 'Y' and po.vndr_contr_gnrtd_id = "
                                + vendorContractId, new ResultSetExtractor<KualiDecimal>() {
                            public KualiDecimal extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                if (rs != null && rs.next()) {
                                    BigDecimal val = rs.getBigDecimal(1);
                                    return new KualiDecimal(val == null ? BigDecimal.ZERO : val);
                                }
                                return null;
                            }
                        });
    }
}
