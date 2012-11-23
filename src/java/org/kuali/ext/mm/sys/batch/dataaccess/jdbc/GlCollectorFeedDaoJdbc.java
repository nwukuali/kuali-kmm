/**
 * 
 */
package org.kuali.ext.mm.sys.batch.dataaccess.jdbc;

import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.ext.mm.sys.batch.dataaccess.GlCollectorFeedDao;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author harsha07
 */
public class GlCollectorFeedDaoJdbc extends PlatformAwareDaoBaseJdbc implements GlCollectorFeedDao {
    public List<FinancialGeneralLedgerPendingEntry> getEntries(String warehouseCode) {
        final List<FinancialGeneralLedgerPendingEntry> entries = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        String sql = "select warehouse_cd, fs_origin_cd, fdoc_nbr, fin_coa_cd, account_nbr, sub_acct_nbr, fin_object_cd, "
                + "fin_sub_obj_cd, fin_balance_typ_cd, fin_obj_typ_cd, univ_fiscal_yr, univ_fiscal_prd_cd, trn_ldgr_entr_desc, "
                + "trn_debit_crdt_cd, fdoc_typ_cd, project_cd, org_reference_id, sum(trn_ldgr_entr_amt) amt from "
                + "mm_gl_pending_entry_t where warehouse_cd = '"
                + warehouseCode
                + "' group by warehouse_cd, fs_origin_cd, fdoc_nbr, fin_coa_cd, "
                + "account_nbr, sub_acct_nbr, fin_object_cd, fin_sub_obj_cd, fin_balance_typ_cd, fin_obj_typ_cd, univ_fiscal_yr, "
                + "univ_fiscal_prd_cd, trn_ldgr_entr_desc, trn_debit_crdt_cd, fdoc_typ_cd, project_cd, org_reference_id"
                + " order by fdoc_nbr, fin_coa_cd, account_nbr";

        getJdbcTemplate().query(sql, new ResultSetExtractor() {
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    FinancialGeneralLedgerPendingEntry entry = new FinancialGeneralLedgerPendingEntry();
                    entry.setWarehouseCode(rs.getString("warehouse_cd"));
                    entry.setFinancialSystemOriginationCode(rs.getString("fs_origin_cd"));
                    entry.setDocumentNumber(rs.getString("fdoc_nbr"));
                    entry.setChartOfAccountsCode(rs.getString("fin_coa_cd"));
                    entry.setAccountNumber(rs.getString("account_nbr"));
                    entry.setSubAccountNumber(rs.getString("sub_acct_nbr"));
                    entry.setFinancialObjectCode(rs.getString("fin_object_cd"));
                    entry.setFinancialSubObjectCode(rs.getString("fin_sub_obj_cd"));
                    entry.setFinancialBalanceTypeCode(rs.getString("fin_balance_typ_cd"));
                    entry.setFinancialObjectTypeCode(rs.getString("fin_obj_typ_cd"));
                    entry.setUniversityFiscalYear(rs.getInt("univ_fiscal_yr"));
                    entry.setUniversityFiscalPeriodCode(rs.getString("univ_fiscal_prd_cd"));
                    entry.setTransactionLedgerEntryDescription(rs.getString("trn_ldgr_entr_desc"));
                    entry.setTransactionDebitCreditCode(rs.getString("trn_debit_crdt_cd"));
                    entry.setFinancialDocumentTypeCode(rs.getString("fdoc_typ_cd"));
                    entry.setProjectCode(rs.getString("project_cd"));
                    entry.setOrganizationReferenceId(rs.getString("org_reference_id"));
                    entry.setTransactionLedgerEntryAmount(new KualiDecimal(rs.getDouble("amt")));
                    entries.add(entry);
                }
                return entries;
            }
        });
        return entries;
    }

    public int clearEntries(String warehouseCode) {
        return getJdbcTemplate().update(
                "delete from mm_gl_pending_entry_t where warehouse_cd = '" + warehouseCode + "'");
    }
}
