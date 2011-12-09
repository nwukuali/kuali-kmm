package org.kuali.ext.mm.sys.batch.dataaccess.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.sys.batch.dataaccess.FamisDataBuilderDao;
import org.kuali.rice.kns.dao.jdbc.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


/**
 * @author harsha07
 */
public class FamisDataBuilderDaoJdbc extends PlatformAwareDaoBaseJdbc implements
        FamisDataBuilderDao {

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.FamisDataBuilderDao#retrieveFamisLinesByAccount(java.lang.String, java.lang.String)
     */
    public List<String> retrieveFamisLinesByAccount(String chart, String accountNumber) {
        final ArrayList<String> feedLines = new ArrayList<String>();
        getJdbcTemplate()
                .query(
                        "select to_char(dtl.last_update_dt, 'MMDDYY') ||"
                                + " rpad(substr(nvl(acc.ACCOUNT_NBR,' '), 0, 8), 8, ' ') ||"
                                + " rpad(substr(nvl(acc.FIN_OBJECT_CD,' '),0, 4), 4, ' ') ||"
                                + " rpad(substr(nvl(dtl.ORDER_DOC_NBR,' '),0, 14), 14, ' ') ||"
                                + " rpad(substr(nvl(acc.DEPARTMENT_REFERENCE_TXT,' '),0, 15), 15, ' ') ||"
                                + " rpad(substr(nvl(dtl.DISTRIBUTOR_NBR,' '),0, 20), 20, ' ') ||"
                                + " rpad(substr((round((nvl(dtl.ORDER_ITEM_PRICE_AMT,0) * dtl.order_item_qty) * (acc.account_pct/100),2)),0, 14), 14, ' ') ||"
                                + " rpad(substr(((nvl(dtl.ORDER_ITEM_TAX_AMT,0) * dtl.order_item_qty)* (acc.account_pct/100)),0, 14), 14, ' ') ||"
                                + " rpad(substr(((nvl(dtl.ORDER_ITEM_ADDL_CST_AMT,0) * dtl.order_item_qty)* (acc.account_pct/100)),0, 14), 14, ' ') ||"
                                + " rpad(substr(nvl(dtl.order_item_qty,0),0, 10), 10, ' ') ||"
                                + " rpad(substr(nvl(dtl.ORDER_ITEM_PRICE_AMT,0),0, 10), 10, ' ') ||"
                                + " rpad(substr(nvl(dtl.STOCK_UNIT_OF_ISSUE_CD,'EA'),0, 10), 10, ' ') ||"
                                + " to_char(dtl.last_update_dt, 'YYMMDD') ||"
                                + " to_char(dtl.last_update_dt, 'HHMMSS') ||"
                                + " rpad(substr(nvl(dtl.order_item_detail_desc,' '),0, 400), 400, ' ')"
                                + " from mm_order_doc_t ord, mm_order_detail_t dtl, mm_accounts_t acc"
                                + " where ord.fdoc_nbr = dtl.order_doc_nbr"
                                + " and dtl.order_detail_id = acc.order_detail_id"
                                + " and acc.fin_coa_cd = '"
                                + chart
                                + "' and acc.account_nbr = '"
                                + accountNumber
                                + "' and ord.order_type_cd = '"
                                + MMConstants.OrderType.WAREHS
                                + "' and dtl.order_detail_status_cd = '"
                                + MMConstants.OrderStatus.ORDER_LINE_COMPLETE
                                + "' and ((select last_success from mm_batch_ctrl_t where job_nm = '"
                                + "famisDataBuilderJob"
                                + "' and last_success is not null) IS NULL OR"
                                + " ord.order_create_dt >"
                                + " (select last_success from mm_batch_ctrl_t where job_nm = '"
                                + "famisDataBuilderJob"
                                + "' and last_success is not null )) order by ord.fdoc_nbr",
                        new ResultSetExtractor() {
                            /**
                             * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
                             */
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs != null && rs.next()) {
                                    feedLines.add(rs.getString(1));
                                }
                                return feedLines;
                            }
                        });
        return feedLines;
    }
}
