/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess.jdbc.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.sys.batch.dataaccess.StockCountDao;
import org.kuali.rice.kns.dao.jdbc.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * @author harsha07
 */
public class StockCountDaoJdbc extends PlatformAwareDaoBaseJdbc implements StockCountDao {
    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.StockCountDao#dailyStockCountInsert()
     */
    public int dailyStockCountInsert() {
        // Clearing the Unassigned Count Records
        clearUnassignedCountRecords();

        // insert records
        String sql = "insert into mm_stock_count_t (stock_count_id, obj_id, ver_nbr, stock_id, bin_id, worksheet_count_doc_nbr, "
                + "worksheet_prncpl_id, before_item_qty, before_item_unit_of_issue_cd, original_dt, stock_count_item_qty, stock_trans_reason_cd, "
                + "stock_count_note, times_counted, reprint_ind, last_update_dt) "
                + "select uuid(), 1, sbal.stock_id, sbal.bin_id, null, ' ',"
                + "sbal.qty_on_hand, stock.uom, current_date, null, null, ' ', 0, 'N', current_date from mm_stock_balance_t sbal," 
                + "(select stk.stock_id stkid, stk.sales_unit_of_issue_cd uom, round(365 / cct.times_per_year_nbr) days_btwn_cnt, "
                + "date_format(max(coalesce(hist.history_trans_timestamp, current_date - 366)), '%Y %m %d') last_cnt_date,"
                + "date_format(max(coalesce(cnt.last_update_dt, current_date - 366)), '%Y %m %d') last_wrksht_date from mm_stock_t stk"
                + "join mm_cycle_count_t cct on cct.cycle_cnt_cd = stk.cycle_cnt_cd join mm_catalog_item_t cit on cit.stock_id = stk.stock_id"
                + "and cit.actv_ind = 'Y' join mm_catalog_t ctg on ctg.catalog_id = cit.catalog_id and ctg.actv_ind = 'Y'"
                + "join mm_warehouse_t wht on wht.warehouse_cd = ctg.warehouse_cd and wht.actv_ind = 'Y'" 
                + "left join mm_stock_history_t hist on hist.stock_id = stk.stock_id"
                + "and hist.stock_trans_reason_cd = 'COUNT' left join mm_stock_count_t cnt on cnt.stock_id = stk.stock_id where"
                + "stk.actv_ind = 'Y' and cct.times_per_year_nbr > 0 group by stk.stock_id, stk.sales_unit_of_issue_cd,"
                + "round(365 / cct.times_per_year_nbr)) stock where sbal.stock_id = stock.stkid and not exists (select 1 from mm_stock_count_t unasnd where unasnd.stock_id = sbal.stock_id and unasnd.bin_id = sbal.bin_id"
                + "and unasnd.worksheet_count_doc_nbr is null)";
        return getJdbcTemplate().update(sql);
    }

    /**
     * 
     */
    private void clearUnassignedCountRecords() {
        // clear the records
        getJdbcTemplate().update(
                "delete from mm_stock_count_t where worksheet_count_doc_nbr is null");
    }

    public Map<String, Integer> getQtyOnHandMap(String worksheetDocNbr) {
        final HashMap<String, Integer> qtyOnHandMap = new HashMap<String, Integer>();
        getJdbcTemplate()
                .query(
                        "select sbt.stock_id, sbt.bin_id, sbt.qty_on_hand from mm_stock_count_t scnt, mm_stock_balance_t sbt "
                                + "where scnt.stock_id = sbt.stock_id and scnt.bin_id = sbt.bin_id and scnt.worksheet_count_doc_nbr='"
                                + worksheetDocNbr + "'", new ResultSetExtractor() {
                            /**
                             * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
                             */
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs.next()) {
                                    qtyOnHandMap.put(rs.getInt(1) + "-" + rs.getInt(2), rs
                                            .getInt(3));
                                }
                                return null;
                            }
                        });

        return qtyOnHandMap;

    }

    public Integer getMismatchedCount(String worksheetDocNbr) {
        String sql = "select count(1) from mm_stock_count_t cnt where cnt.before_item_qty <> cnt.stock_count_item_qty and nvl(cnt.reprint_ind,'N') = 'N' and cnt.worksheet_count_doc_nbr = '"
                + worksheetDocNbr + "'";
        return getJdbcTemplate().queryForInt(sql);
    }

    public Integer getTotalStockCount(String worksheetDocNbr) {
        String sql = "select count(1) from mm_stock_count_t cnt where nvl(cnt.reprint_ind,'N') = 'N' and cnt.worksheet_count_doc_nbr = '"
                + worksheetDocNbr + "'";
        return getJdbcTemplate().queryForInt(sql);
    }

    public int fullInventoryStockCountInsert(String warehouseCode, String zoneCode,
            boolean excludeEmpty) {
        String sql = "insert into mm_stock_count_t(stock_count_id, obj_id, ver_nbr, stock_id, bin_id, worksheet_count_doc_nbr, "
                + "worksheet_prncpl_id, before_item_qty, before_item_unit_of_issue_cd, original_dt, stock_count_item_qty, stock_trans_reason_cd, "
                + "stock_count_note, times_counted, reprint_ind, last_update_dt) "
                + "select mm_stock_count_s.nextval, sys_guid(), 1, sbal.stock_id, sbal.bin_id, "
                + "null, ' ', sbal.qty_on_hand, stock.sales_unit_of_issue_cd, current_date, null, null, ' ', 0, 'N', "
                + "current_date from mm_stock_balance_t sbal join mm_bin_t bn on bn.bin_id = sbal.bin_id join mm_zone_t zn on zn.zone_id = bn.zone_id "
                + "join mm_stock_t stock on sbal.stock_id = stock.stock_id join mm_catalog_item_t cit on cit.stock_id = stock.stock_id and cit.actv_ind = 'Y' "
                + "join mm_catalog_t ctg on ctg.catalog_id = cit.catalog_id and ctg.actv_ind = 'Y' "
                + "join mm_warehouse_t wht on wht.warehouse_cd = ctg.warehouse_cd and wht.actv_ind = 'Y' "
                + "left join mm_cycle_count_t cct on cct.cycle_cnt_cd = stock.cycle_cnt_cd where stock.actv_ind = 'Y' "
                + "and zn.warehouse_cd = '"
                + warehouseCode
                + "' and coalesce(cct.times_per_year_nbr , 0) > 0 ";

        if (StringUtils.isNotBlank(zoneCode)) {
            sql = sql + "and zn.zone_cd = '" + zoneCode + "' ";
        }

        if (excludeEmpty) {
            sql = sql + "and sbal.qty_on_hand > 0 ";
        }

        sql = sql
                + "and not exists (select 1 from mm_stock_count_t unasnd where unasnd.stock_id = sbal.stock_id "
                + "and unasnd.bin_id = sbal.bin_id and unasnd.worksheet_count_doc_nbr is null)";
        return getJdbcTemplate().update(sql);

    }
}
