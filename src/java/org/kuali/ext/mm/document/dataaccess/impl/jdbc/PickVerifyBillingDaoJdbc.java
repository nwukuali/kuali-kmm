/**
 *
 */
package org.kuali.ext.mm.document.dataaccess.impl.jdbc;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.dataaccess.PickVerifyBillingDao;
import org.kuali.ext.mm.gl.GlConstants;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author harsha07
 */
public class PickVerifyBillingDaoJdbc extends PlatformAwareDaoBaseJdbc implements
        PickVerifyBillingDao {

    public Map<String, List<FinancialAccountingLine>> getPickVerifiedAccountingLines(
            final String pickTicketNumber) {
        final HashMap<String, List<FinancialAccountingLine>> map = new HashMap<String, List<FinancialAccountingLine>>();
        getJdbcTemplate()
                .query(
                        "SELECT ORDR.FDOC_NBR ODOC, AST.ORDR_DTL_ID, ORDR.ORDER_ID, ACCT.FIN_COA_CD,"
                                + " ACCT.ACCOUNT_NBR, COALESCE(ACCT.SUB_ACCT_NBR, '') SUBACCT, ACCT.FIN_OBJECT_CD,"
                                + " COALESCE(ACCT.FIN_SUB_OBJ_CD, '') SUBOBJ, COALESCE(ACCT.PROJECT_CD, '') PRJCODE, COALESCE(ACCT.DEPARTMENT_REFERENCE_TXT, '') ORG_REF_ID,"
                                + " SUM(ROUND((COALESCE(ACCT.ACCOUNT_FIXED_AMT, 0) / COALESCE(DTL.ORDER_ITEM_QTY, 1)) * COALESCE(LINE.PICK_STOCK_QTY, 0), 2)) LINEAMT"
                                + " FROM MM_PICK_LIST_LINE_T LINE, MM_ORDER_DETAIL_T DTL LEFT OUTER JOIN MM_CPTL_AST_INFO_T AST"
                                + " ON DTL.ORDER_DETAIL_ID = AST.ORDR_DTL_ID, MM_ORDER_DOC_T ORDR, MM_ACCOUNTS_T ACCT"
                                + " WHERE LINE.ORDER_DETAIL_ID = DTL.ORDER_DETAIL_ID AND DTL.ORDER_DOC_NBR = ORDR.FDOC_NBR AND"
                                + " DTL.ORDER_DETAIL_ID = ACCT.ORDER_DETAIL_ID AND LINE.PICK_TICKET_NBR = ?"
                                + " AND LINE.PICK_STATUS_CD IN ('PBCK', 'PCKD') AND LINE.PICK_STOCK_QTY > 0 "
                                + " GROUP BY ORDR.FDOC_NBR, AST.ORDR_DTL_ID, ORDR.ORDER_ID, ACCT.FIN_COA_CD, ACCT.ACCOUNT_NBR,"
                                + " ACCT.SUB_ACCT_NBR, ACCT.FIN_OBJECT_CD, ACCT.FIN_SUB_OBJ_CD, ACCT.PROJECT_CD, ACCT.DEPARTMENT_REFERENCE_TXT ORDER BY ORDR.FDOC_NBR",
                        new PreparedStatementSetter() {
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setString(1, pickTicketNumber);
                            }
                        }, new ResultSetExtractor() {
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs != null && rs.next()) {
                                    String orderDocNbr = rs.getString("ODOC");
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
                                    finAcctLine.setOrganizationReferenceId(rs
                                            .getString("ORG_REF_ID"));
                                    finAcctLine.setSubAccountNumber(rs.getString("SUBACCT"));
                                    finAcctLine
                                            .setFinancialDocumentLineDescription("Stores Charge Order - "
                                                    + orderDocNbr);
                                    finAcctLine
                                            .setFinancialDocumentLineTypeCode(MMConstants.FIN_ACCT_LINE_TYP_TO);
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
                                    finAcctLine.setDocumentNumber(orderDocNbr);
                                    finAcctLine
                                            .setFinancialDocumentTypeCode(MMConstants.OrderDocument.STORES_ORDER_DOCUMENT);
                                    List<FinancialAccountingLine> acctLines = null;
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

    public Map<String, List<FinancialInternalBillingItem>> getPickVerifiedOrderLines(
            final String pickTicketNumber) {
        final HashMap<String, List<FinancialInternalBillingItem>> map = new HashMap<String, List<FinancialInternalBillingItem>>();
        getJdbcTemplate()
                .query(
                        "SELECT DISTINCT ORDR.FDOC_NBR, AST.ORDR_DTL_ID, ORDR.ORDER_ID, ORDR.WAREHOUSE_CD,"
                                + " DTL.ORDER_DETAIL_ID, DTL.DISTRIBUTOR_NBR, DTL.ORDER_ITEM_DETAIL_DESC,"
                                + " DTL.ORDER_ITEM_PRICE_AMT, DTL.STOCK_UNIT_OF_ISSUE_CD, LINE.PICK_STOCK_QTY"
                                + " FROM MM_PICK_LIST_LINE_T LINE, MM_ORDER_DETAIL_T DTL LEFT OUTER JOIN MM_CPTL_AST_INFO_T AST"
                                + " ON DTL.ORDER_DETAIL_ID = AST.ORDR_DTL_ID, MM_ORDER_DOC_T ORDR"
                                + " WHERE LINE.ORDER_DETAIL_ID = DTL.ORDER_DETAIL_ID AND DTL.ORDER_DOC_NBR = ORDR.FDOC_NBR"
                                + " AND LINE.PICK_TICKET_NBR = ? AND LINE.PICK_STATUS_CD IN ('PBCK', 'PCKD')"
                                + " AND LINE.PICK_STOCK_QTY > 0  ORDER BY ORDR.FDOC_NBR",
                        new PreparedStatementSetter() {
                            /**
                             * @see org.springframework.jdbc.core.PreparedStatementSetter#setValues(java.sql.PreparedStatement)
                             */
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setString(1, pickTicketNumber);

                            }
                        }, new ResultSetExtractor() {
                            /**
                             * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
                             */
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs != null && rs.next()) {
                                    FinancialInternalBillingItem item = new FinancialInternalBillingItem();
                                    item.setMmOrderDetailId(rs.getString("ORDER_DETAIL_ID"));
                                    item.setItemQuantity(rs.getInt("PICK_STOCK_QTY"));
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

    /**
     * @see org.kuali.ext.mm.document.dao.PickVerifyBillingDao#hasAccountsForBilling(java.lang.String)
     */
    public boolean hasAccountsForBilling(final String pickTicketNumber) {
        int count = getJdbcTemplate()
                .queryForInt(
                        "SELECT COUNT(ACCT.ACCOUNTS_ID) FROM MM_PICK_LIST_LINE_T LINE, MM_ORDER_DETAIL_T DTL, MM_ACCOUNTS_T ACCT "
                                + "WHERE LINE.ORDER_DETAIL_ID = DTL.ORDER_DETAIL_ID AND DTL.ORDER_DETAIL_ID = ACCT.ORDER_DETAIL_ID "
                                + "AND LINE.PICK_TICKET_NBR = " + pickTicketNumber);
        return count > 0;
    }
}
