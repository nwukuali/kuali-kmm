/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess.jdbc;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.gl.GlConstants;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.ext.mm.sys.batch.dataaccess.RentalDemurrageDao;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author harsha07
 */
public class RentalDemurrageDaoJdbc extends PlatformAwareDaoBaseJdbc implements
        RentalDemurrageDao {
    
    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.RentalDemurrageDao#getRentalDemurrageAccountLines()
     */
    public Map<String, List<FinancialAccountingLine>> getRentalDemurrageAccountLines() {
        final HashMap<String, List<FinancialAccountingLine>> map = new HashMap<String, List<FinancialAccountingLine>>();
        final Integer fiscalYear = SpringContext.getBean(FinancialSystemAdaptorFactory.class).getFinancialUniversityDateService()
                .getCurrentFiscalYear();
        
        getJdbcTemplate()
                .query("select o.warehouse_cd warehouse,"
                        + " o.fdoc_nbr orderNo,"
                        + " o.order_id orderId,"
                        + " da.fin_coa_cd chart,"
                        + " da.account_nbr acct,"
                        + " da.sub_acct_nbr subAcct,"
                        + " da.fin_object_cd objCd,"
                        + " da.fin_sub_obj_cd subObjcd,"
                        + " da.project_cd prjCd,"
                        + " da.department_reference_txt deptRef,"
                        + " SUM(da.account_fixed_amt * round(coalesce(rental.return_dt, current_date) -"
                        + " coalesce(rental.last_charge_dt, rental.issued_dt))) amt"
                        + " from mm_rental_t rental"
                        + " join mm_stock_t s"
                        + "   on s.stock_id = rental.stock_id"
                        + " join mm_rental_object_code_t c"
                        + "   on c.rental_object_cd = s.rental_object_cd"
                        + " join mm_pick_list_line_t p"
                        +  "   on p.pick_list_line_id = rental.pick_list_line_id"
                        +  " join mm_order_detail_t d"
                        +  "   on d.order_detail_id = p.order_detail_id"
                        +  " left join mm_accounts_t da"
                        +  "   on da.rental_id = rental.rental_id"
                        +  " join mm_order_doc_t o"
                        +  "   on o.fdoc_nbr = d.order_doc_nbr"
                        +  " WHERE rental.issued_dt is not null"
                        +  " and round(coalesce(rental.return_dt, current_date) -"
                        +  "      coalesce(rental.last_charge_dt, rental.issued_dt)) >= 1"
                        +  " group by o.warehouse_cd,"
                        +  "         o.fdoc_nbr,"
                        +  "         o.order_id,"
                        +  "         da.fin_coa_cd,"
                        +  "         da.account_nbr,"
                        +  "         da.sub_acct_nbr,"
                        +  "         da.fin_object_cd,"
                        +  "         da.department_reference_txt,"
                        +  "         da.fin_sub_obj_cd,"
                        +  "         da.project_cd"
                        +  " order by o.warehouse_cd, o.fdoc_nbr",
                        new ResultSetExtractor() {
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs != null && rs.next()) {
                                    FinancialAccountingLine finAcctLine = new FinancialAccountingLine();
                                    finAcctLine.setAccountNumber(rs.getString("acct"));
                                    finAcctLine.setAmount(new KualiDecimal(rs.getDouble("amt")));
                                    finAcctLine.setBalanceTypeCode(GlConstants.BALANCE_TYPE_ACTUAL);
                                    finAcctLine.setChartOfAccountsCode(rs.getString("chart"));
                                    finAcctLine.setFinancialObjectCode(rs.getString("objCd"));
                                    finAcctLine.setFinancialSubObjectCode(rs.getString("subObjcd"));
                                    finAcctLine.setProjectCode(rs.getString("prjCd"));
                                    finAcctLine.setSubAccountNumber(rs.getString("subAcct"));
                                    finAcctLine.setFinancialDocumentLineDescription("Order - "
                                            + rs.getString("orderId"));
                                    finAcctLine
                                            .setFinancialDocumentLineTypeCode(MMConstants.FIN_ACCT_LINE_TYP_TO);
                                    finAcctLine.setObjectBudgetOverride(false);
                                    finAcctLine.setObjectBudgetOverrideNeeded(false);
                                    finAcctLine.setOrganizationReferenceId(rs.getString("deptRef"));
                                    finAcctLine.setOverrideCode("");
                                    finAcctLine.setPostingYear(fiscalYear);
                                    finAcctLine
                                            .setReferenceOriginCode(GlConstants.getFinancialSystemOriginCode());
                                    finAcctLine.setReferenceTypeCode("");
                                    finAcctLine.setSalesTaxRequired(false);
                                    List<FinancialAccountingLine> acctLines = null;
                                    String orderDocNbr = rs.getString("orderNo");
                                    String warehouse = rs.getString("warehouse");
                                    String key = warehouse + "-" + orderDocNbr;
                                    if(finAcctLine.getAmount().isGreaterThan(new KualiDecimal(0))) {
                                        if ((acctLines = map.get(key)) == null) {
                                            acctLines = new ArrayList<FinancialAccountingLine>();
                                            map.put(key, acctLines);
                                        }
                                        acctLines.add(finAcctLine);
                                    }
                                }
                                return map;
                            }
                        });
        return map;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.RentalDemurrageDao#getRentalDemurrageItemLines()
     */
    public Map<String, List<FinancialInternalBillingItem>> getRentalDemurrageItemLines() {
        final HashMap<String, List<FinancialInternalBillingItem>> map = new HashMap<String, List<FinancialInternalBillingItem>>();
        getJdbcTemplate()
                .query(
                        "select distinct rental.rental_id rentalId, o.warehouse_cd warehouse, o.fdoc_nbr orderNo, o.order_id orderId, od.order_detail_id deatilId, "
                                + "od.distributor_nbr distNo, rental.rental_serial_nbr serNo, rental.rental_serial_nbr || ' ' || substr(od.order_item_detail_desc, 20) itemDesc, "
                                + "c.daily_demurrage_prc unitPrc, od.stock_unit_of_issue_cd unitIsse, "
                                + " round(coalesce(rental.return_dt, current_date) - coalesce(rental.last_charge_dt, rental.issued_dt)) as chargeDays "
                                + "from mm_rental_t rental join mm_stock_t s on s.stock_id = rental.stock_id join mm_rental_object_code_t c on c.rental_object_cd = s.rental_object_cd "
                                + "join mm_pick_list_line_t p on p.pick_list_line_id = rental.pick_list_line_id join mm_order_detail_t od on od.order_detail_id = p.order_detail_id left "
                                + "join mm_accounts_t da on da.order_detail_id = od.order_detail_id join mm_order_doc_t o on o.fdoc_nbr = od.order_doc_nbr "
                                + "WHERE rental.issued_dt is not null and round(coalesce(rental.return_dt, current_date) - coalesce(rental.last_charge_dt, rental.issued_dt)) >= 1"
                                + "order by o.warehouse_cd, o.fdoc_nbr", new ResultSetExtractor() {
                            /**
                             * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
                             */
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs != null && rs.next()) {
                                    FinancialInternalBillingItem item = new FinancialInternalBillingItem();
                                    item.setRentalId(rs.getString("rentalId"));
                                    item.setItemQuantity(rs.getInt("chargeDays"));
                                    item.setSerialNumber(rs.getString("serNo"));
                                    item.setItemStockDescription(rs.getString("itemDesc"));
                                    item.setItemStockNumber(rs.getString("distNo"));
                                    item.setItemUnitAmount(new KualiDecimal(rs
                                            .getBigDecimal("unitPrc")));
                                    item.setUnitOfMeasureCode(rs.getString("unitIsse"));
                                    String warehouse = rs.getString("warehouse");
                                    String orderDocNbr = rs.getString("orderNo");
                                    String key = warehouse + "-" + orderDocNbr;
                                    item.setWarehouseCode(warehouse);
                                    List<FinancialInternalBillingItem> orderList = null;
                                    if(item.getItemQuantity() > 0) {
                                        if ((orderList = map.get(key)) == null) {
                                            orderList = new ArrayList<FinancialInternalBillingItem>();
                                            map.put(key, orderList);
                                        }
                                        orderList.add(item);
                                    }
                                }
                                return map;
                            }
                        });
        return map;
    }

    public int updateLastChargeDate(String rentalId) {
             
        return getJdbcTemplate().update(
                "update mm_rental_t t set t.last_charge_dt=current_date  "
                        + "where t.rental_Id = " + rentalId);
    }    
    
}
