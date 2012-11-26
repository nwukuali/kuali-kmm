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
import org.kuali.ext.mm.sys.batch.dataaccess.PickVerifyAccountDao;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author harsha07
 */
public class PickVerifyAccountDaoJdbc extends PlatformAwareDaoBaseJdbc implements
        PickVerifyAccountDao {

    public Map<String, List<FinancialAccountingLine>> getPickVerifiedAccountingLines(
            Timestamp lastScanTime) {
        final HashMap<String, List<FinancialAccountingLine>> map = new HashMap<String, List<FinancialAccountingLine>>();
        getJdbcTemplate()
                .query(
                        "select ordr.fdoc_nbr, ordr.order_id, acct.fin_coa_cd, acct.account_nbr, COALESCE(acct.sub_acct_nbr, '') "
                                + "subAcct, acct.fin_object_cd, COALESCE(acct.fin_sub_obj_cd, '') subObj, COALESCE(acct.project_cd, '') prjcode , "
                                + "sum(round((COALESCE(acct.account_fixed_amt,0) / COALESCE(dtl.order_item_qty,1)) * COALESCE(line.pick_stock_qty,0), 2)) lineAmt "
                                + "from mm_pick_list_line_t line, mm_order_detail_t dtl, mm_order_doc_t ordr, mm_accounts_t acct "
                                + "where line.order_detail_id = dtl.order_detail_id and dtl.order_doc_nbr = ordr.fdoc_nbr and "
                                + "dtl.order_detail_id = acct.order_detail_id and line.pick_status_cd in ('PBCK', 'PCKD') and "
                                + "line.pick_stock_qty > 0 group by ordr.fdoc_nbr, ordr.order_id, acct.fin_coa_cd, acct.account_nbr, "
                                + "acct.sub_acct_nbr, acct.fin_object_cd, acct.fin_sub_obj_cd, acct.project_cd order by ordr.fdoc_nbr",
                        new PreparedStatementSetter() {
                            public void setValues(PreparedStatement ps) throws SQLException {
                                // add logic here later
                            }
                        }, new ResultSetExtractor() {
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs != null && rs.next()) {
                                    FinancialAccountingLine finAcctLine = new FinancialAccountingLine();
                                    finAcctLine.setAccountNumber(rs.getString("account_nbr"));
                                    finAcctLine
                                            .setAmount(new KualiDecimal(rs.getDouble("lineAmt")));
                                    finAcctLine.setBalanceTypeCode(GlConstants.BALANCE_TYPE_ACTUAL);
                                    finAcctLine.setChartOfAccountsCode(rs.getString("fin_coa_cd"));
                                    finAcctLine.setFinancialObjectCode(rs
                                            .getString("fin_object_cd"));
                                    finAcctLine.setFinancialSubObjectCode(rs.getString("subObj"));
                                    finAcctLine.setProjectCode(rs.getString("prjcode"));
                                    finAcctLine.setSubAccountNumber(rs.getString("subAcct"));
                                    finAcctLine.setFinancialDocumentLineDescription("Order - "
                                            + rs.getString("order_id"));
                                    finAcctLine
                                            .setFinancialDocumentLineTypeCode(MMConstants.FIN_ACCT_LINE_TYP_TO);
                                    finAcctLine.setObjectBudgetOverride(false);
                                    finAcctLine.setObjectBudgetOverrideNeeded(false);
                                    finAcctLine.setOrganizationReferenceId("");
                                    finAcctLine.setOverrideCode("");
                                    finAcctLine.setPostingYear(SpringContext.getBean(
                                            FinancialSystemAdaptorFactory.class)
                                            .getFinancialUniversityDateService()
                                            .getCurrentFiscalYear());
                                    finAcctLine
                                            .setReferenceOriginCode(GlConstants.getFinancialSystemOriginCode());
                                    finAcctLine.setReferenceTypeCode("");
                                    finAcctLine.setSalesTaxRequired(false);
                                    List<FinancialAccountingLine> acctLines = null;
                                    String orderDocNbr = rs.getString("fdoc_nbr");
                                    if ((acctLines = map.get(orderDocNbr)) == null) {
                                        acctLines = new ArrayList<FinancialAccountingLine>();
                                        map.put(orderDocNbr, acctLines);
                                    }
                                    acctLines.add(finAcctLine);
                                }
                                return map;
                            }
                        });
        return map;
    }

    public Map<String, List<FinancialInternalBillingItem>> getPickVerifiedOrderLines(
            Timestamp lastScanTime) {
        final HashMap<String, List<FinancialInternalBillingItem>> map = new HashMap<String, List<FinancialInternalBillingItem>>();
        getJdbcTemplate()
                .query(
                        "select distinct ordr.fdoc_nbr, ordr.order_id, ordr.warehouse_cd, dtl.order_detail_id, dtl.distributor_nbr, dtl.order_item_detail_desc, "
                                + "dtl.order_item_price_amt, dtl.stock_unit_of_issue_cd, line.pick_stock_qty from mm_pick_list_line_t line, "
                                + "mm_order_detail_t dtl, mm_order_doc_t ordr where line.order_detail_id = dtl.order_detail_id and "
                                + "dtl.order_doc_nbr = ordr.fdoc_nbr and line.pick_status_cd in ('PBCK', 'PCKD') and line.pick_stock_qty > 0 "
                                + "order by ordr.fdoc_nbr", new PreparedStatementSetter() {
                            /**
                             * @see org.springframework.jdbc.core.PreparedStatementSetter#setValues(java.sql.PreparedStatement)
                             */
                            public void setValues(PreparedStatement ps) throws SQLException {

                            }
                        }, new ResultSetExtractor() {
                            /**
                             * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
                             */
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs != null && rs.next()) {
                                    FinancialInternalBillingItem item = new FinancialInternalBillingItem();
                                    item.setItemQuantity(rs.getInt("pick_stock_qty"));
                                    item.setItemStockDescription(rs
                                            .getString("order_item_detail_desc"));
                                    item.setItemStockNumber(rs.getString("distributor_nbr"));
                                    item.setItemUnitAmount(new KualiDecimal(rs
                                            .getBigDecimal("order_item_price_amt")));
                                    item.setUnitOfMeasureCode(rs
                                            .getString("stock_unit_of_issue_cd"));
                                    item.setWarehouseCode(rs.getString("warehouse_cd"));
                                    String orderDocNbr = rs.getString("fdoc_nbr");
                                    List<FinancialInternalBillingItem> orderList = null;
                                    if ((orderList = map.get(orderDocNbr)) == null) {
                                        orderList = new ArrayList<FinancialInternalBillingItem>();
                                        map.put(orderDocNbr, orderList);
                                    }
                                    orderList.add(item);
                                }
                                return map;
                            }
                        });
        return map;
    }

}
