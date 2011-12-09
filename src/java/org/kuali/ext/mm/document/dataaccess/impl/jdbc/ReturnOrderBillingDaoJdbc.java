/**
 *
 */
package org.kuali.ext.mm.document.dataaccess.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.dataaccess.ReturnOrderBillingDao;
import org.kuali.ext.mm.gl.GlConstants;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.rice.kns.dao.jdbc.PlatformAwareDaoBaseJdbc;
import org.kuali.rice.kns.util.KualiDecimal;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;


/**
 * @author rponraj
 */
public class ReturnOrderBillingDaoJdbc extends PlatformAwareDaoBaseJdbc implements
        ReturnOrderBillingDao {

    public Map<String, List<FinancialAccountingLine>> getReturnedOrderAccountingLines(
            final String returnDocNumber) {
        final HashMap<String, List<FinancialAccountingLine>> map = new HashMap<String, List<FinancialAccountingLine>>();
        getJdbcTemplate()
                .query(
                        " SELECT ORDR.FDOC_NBR ODOC, AST.ORDR_DTL_ID, ORDR.ORDER_ID, ACCT.FIN_COA_CD, "
                                + " ACCT.ACCOUNT_NBR, COALESCE(ACCT.SUB_ACCT_NBR, '') SUBACCT, ACCT.FIN_OBJECT_CD, "
                                + " COALESCE(ACCT.FIN_SUB_OBJ_CD, '') SUBOBJ, COALESCE(ACCT.PROJECT_CD, '') PRJCODE, COALESCE(ACCT.DEPARTMENT_REFERENCE_TXT, '') ORG_REF_ID,"
                                + " SUM(ROUND(case when line.return_detail_status_cd='RENTAL RETURN' then"
                                + " COALESCE(DTL.ORDER_ITEM_ADDL_CST_AMT, 0) else (COALESCE(ACCT.ACCOUNT_FIXED_AMT, 0) / COALESCE(DTL.ORDER_ITEM_QTY, 1))"
                                + " end * COALESCE(LINE.RETURN_QTY, 0), 2)) as LINEAMT"
                                + " FROM MM_RETURN_DETAIL_T LINE, MM_ORDER_DETAIL_T DTL LEFT OUTER JOIN MM_CPTL_AST_INFO_T AST "
                                + " ON DTL.ORDER_DETAIL_ID = AST.ORDR_DTL_ID, MM_ORDER_DOC_T ORDR, MM_ACCOUNTS_T ACCT "
                                + " WHERE LINE.ORDER_DETAIL_ID = DTL.ORDER_DETAIL_ID AND DTL.ORDER_DOC_NBR = ORDR.FDOC_NBR AND "
                                + " DTL.ORDER_DETAIL_ID = ACCT.ORDER_DETAIL_ID "
                                + " AND LINE.RETURN_QTY > 0 AND LINE.ACTION_CD NOT IN ('REJECTED','WAREHS') AND LINE.DEPARTMENT_CREDIT_IND ='Y' AND LINE.RETURN_DOC_NBR = ?  "
                                + " GROUP BY ORDR.FDOC_NBR, AST.ORDR_DTL_ID, ORDR.ORDER_ID, ACCT.FIN_COA_CD, ACCT.ACCOUNT_NBR, "
                                + " ACCT.SUB_ACCT_NBR, ACCT.FIN_OBJECT_CD, ACCT.FIN_SUB_OBJ_CD, ACCT.PROJECT_CD, ACCT.DEPARTMENT_REFERENCE_TXT ORDER BY ORDR.FDOC_NBR ",
                        new PreparedStatementSetter() {
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setString(1, returnDocNumber);
                            }
                        }, new ResultSetExtractor() {
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs != null && rs.next()) {
                                    FinancialAccountingLine finAcctLine = new FinancialAccountingLine();
                                    finAcctLine.setAccountNumber(rs.getString("ACCOUNT_NBR"));
                                    finAcctLine
                                            .setAmount(new KualiDecimal(rs.getDouble("LINEAMT")));
                                    finAcctLine.setBalanceTypeCode(GlConstants.BALANCE_TYPE_ACTUAL);
                                    finAcctLine.setChartOfAccountsCode(rs.getString("FIN_COA_CD"));
                                    finAcctLine.setFinancialObjectCode(rs
                                            .getString("FIN_OBJECT_CD"));
                                    finAcctLine.setFinancialSubObjectCode(rs.getString("SUBOBJ"));
                                    finAcctLine.setProjectCode(rs.getString("PRJCODE"));
                                    finAcctLine.setOrganizationReferenceId(rs.getString("ORG_REF_ID"));
                                    finAcctLine.setSubAccountNumber(rs.getString("SUBACCT"));
                                    finAcctLine.setFinancialDocumentLineDescription("Stores Charge Order - "
                                            + rs.getString("ODOC"));
                                    finAcctLine
                                            .setFinancialDocumentLineTypeCode(MMConstants.FIN_ACCT_LINE_TYP_FROM);
                                    finAcctLine.setObjectBudgetOverride(false);
                                    finAcctLine.setObjectBudgetOverrideNeeded(false);
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
                                    String orderDocNbr = rs.getString("ODOC");
                                    String astDetailId = rs.getString("ORDR_DTL_ID");
                                    String key = orderDocNbr
                                            + (StringUtils.isBlank(astDetailId) ? "" : "-"
                                                    + astDetailId);
                                    if ((acctLines = map.get(key)) == null) {
                                        acctLines = new ArrayList<FinancialAccountingLine>();
                                        map.put(key, acctLines);
                                    }
                                    acctLines.add(finAcctLine);
                                }
                                return map;
                            }
                        });
        return map;
    }

    public Map<String, List<FinancialInternalBillingItem>> getReturnedOrderLines(
            final String returnDocNumber) {
        final HashMap<String, List<FinancialInternalBillingItem>> map = new HashMap<String, List<FinancialInternalBillingItem>>();
        getJdbcTemplate()
                .query(
                        "SELECT DISTINCT ORDR.FDOC_NBR, AST.ORDR_DTL_ID, ORDR.ORDER_ID, ORDR.WAREHOUSE_CD, DTL.ORDER_DETAIL_ID, "
                                + "DTL.DISTRIBUTOR_NBR, DTL.ORDER_ITEM_DETAIL_DESC, DTL.ORDER_ITEM_PRICE_AMT, DTL.STOCK_UNIT_OF_ISSUE_CD, "
                                + "LINE.RETURN_QTY  FROM MM_RETURN_DETAIL_T LINE, MM_ORDER_DETAIL_T DTL LEFT OUTER JOIN MM_CPTL_AST_INFO_T AST  "
                                + "ON DTL.ORDER_DETAIL_ID = AST.ORDR_DTL_ID, MM_ORDER_DOC_T ORDR  WHERE LINE.ORDER_DETAIL_ID = DTL.ORDER_DETAIL_ID "
                                + "AND DTL.ORDER_DOC_NBR = ORDR.FDOC_NBR  AND LINE.DEPARTMENT_CREDIT_IND ='Y' AND LINE.RETURN_QTY > 0 "
                                + "AND LINE.ACTION_CD NOT IN ('REJECTED','WAREHS') AND LINE.RETURN_DOC_NBR = ? "
                                + "ORDER BY ORDR.FDOC_NBR ", new PreparedStatementSetter() {
                            /**
                             * @see org.springframework.jdbc.core.PreparedStatementSetter#setValues(java.sql.PreparedStatement)
                             */
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setString(1, returnDocNumber);

                            }
                        }, new ResultSetExtractor() {
                            /**
                             * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
                             */
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs != null && rs.next()) {
                                    FinancialInternalBillingItem item = new FinancialInternalBillingItem();
                                    item.setItemQuantity(rs.getInt("RETURN_QTY"));
                                    item.setItemStockDescription(rs
                                            .getString("ORDER_ITEM_DETAIL_DESC"));
                                    item.setItemStockNumber(rs.getString("DISTRIBUTOR_NBR"));
                                    item.setItemUnitAmount(new KualiDecimal(rs
                                            .getBigDecimal("ORDER_ITEM_PRICE_AMT")));
                                    item.setUnitOfMeasureCode(rs
                                            .getString("STOCK_UNIT_OF_ISSUE_CD"));
                                    item.setWarehouseCode(rs.getString("WAREHOUSE_CD"));
                                    String orderDocNbr = rs.getString("FDOC_NBR");
                                    String astDetailId = rs.getString("ORDR_DTL_ID");
                                    String key = orderDocNbr
                                            + (StringUtils.isBlank(astDetailId) ? "" : "-"
                                                    + astDetailId);
                                    List<FinancialInternalBillingItem> orderList = null;
                                    if ((orderList = map.get(key)) == null) {
                                        orderList = new ArrayList<FinancialInternalBillingItem>();
                                        map.put(key, orderList);
                                    }
                                    orderList.add(item);
                                }
                                return map;
                            }
                        });

        return map;
    }


    public int updateOrderstatus(final String orderDocNumber, final String orderStatus) {
        return getJdbcTemplate().update(
                "UPDATE MM_ORDER_DOC_T HH SET HH.ORDER_DOC_STATUS_CD = ? WHERE HH.FDOC_NBR = ?",
                new PreparedStatementSetter() {
                    /**
                     * @see org.springframework.jdbc.core.PreparedStatementSetter#setValues(java.sql.PreparedStatement)
                     */
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, orderStatus);
                        ps.setString(2, orderDocNumber);
                    }
                });
    }

    /**
     * @see org.kuali.ext.mm.document.dao.ReturnOrderBillingDao#hasAccountsForBilling(java.lang.String)
     */
    public boolean hasAccountsForBilling(final String returnDocNumber) {
        int count = getJdbcTemplate()
                .queryForInt(
                        "SELECT COUNT(ACCT.ACCOUNTS_ID) FROM MM_RETURN_DETAIL_T LINE, MM_ORDER_DETAIL_T DTL, MM_ACCOUNTS_T ACCT "
                                + "WHERE LINE.ORDER_DETAIL_ID = DTL.ORDER_DETAIL_ID AND DTL.ORDER_DETAIL_ID = ACCT.ORDER_DETAIL_ID "
                                + "AND LINE.RETURN_DOC_NBR = '" + returnDocNumber + "'");
        return count > 0;
    }
}
