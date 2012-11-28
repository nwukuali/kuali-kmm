/**
 * 
 */
package org.kuali.ext.mm.integration.kfs.adaptor.dao.jdbc;

import org.kuali.ext.mm.integration.kfs.adaptor.dao.GeneralLedgerDao;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;

/**
 *
 */
public class GeneralLedgerDaoJdbc extends PlatformAwareDaoBaseJdbc implements GeneralLedgerDao {

    public Integer getNextEntrySequenceNumber(String originCode, String documentNumber) {
        return getJdbcTemplate().queryForInt(
                "select max(TRN_ENTR_SEQ_NBR) from gl_entry_t where FS_ORIGIN_CD='" + originCode
                        + "' and FDOC_NBR='" + documentNumber + "' ");
    }

    public Integer getNextPendingEntrySequenceNumber(String originCode, String documentNumber) {
        return getJdbcTemplate().queryForInt(
                "select max(TRN_ENTR_SEQ_NBR) from gl_pending_entry_t where FS_ORIGIN_CD='"
                        + originCode + "' and FDOC_NBR='" + documentNumber + "' ");
    }
}
