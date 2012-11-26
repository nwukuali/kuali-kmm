/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess.jdbc;

import org.kuali.ext.mm.sys.batch.dataaccess.BatchControlDao;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author harsha07
 */
public class BatchControlDaoJdbc extends PlatformAwareDaoBaseJdbc implements BatchControlDao {

    public void addIfNewJob(final String jobName) {
        int count = getJdbcTemplate().queryForInt(
                "select count(1) from mm_batch_ctrl_t where job_nm='" + jobName + "'");
        if (count == 0) {
            getJdbcTemplate()
                    .update(
                            "insert into mm_batch_ctrl_t (JOB_NM, JOB_DESC, LAST_SUCCESS, LAST_ATTEMPT, CURRENT_STATUS, ERROR_DETAIL) values ('"
                                    + jobName
                                    + "', 'Job inserted by batch control service', null, null, 'READY', null)");
        }
    }

    /**
     * Returns true if job can start, also marks status as "RUNNING" to prevent parallel running
     *
     * @param jobName Job Name
     * @return boolean
     */
    public boolean canRunJob(final String jobName) {
        int count = getJdbcTemplate()
                .update(
                        "update mm_batch_ctrl_t set current_status='RUNNING', last_attempt=current_timestamp where job_nm=? and current_status='READY'",
                        new PreparedStatementSetter() {
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setString(1, jobName);
                            }
                        });
        return count == 1;
    }

    /**
     * Updates job fail status and return the status as boolean
     *
     * @param jobName Job name
     * @param time Run time
     * @param errorMessage Error detail
     * @return true if updated one record
     */
    public boolean updateJobFail(final String jobName, final Timestamp time,
            final String errorMessage) {
        int count = getJdbcTemplate()
                .update(
                        "update mm_batch_ctrl_t set last_attempt=?, current_status='READY', error_detail=? where job_nm=? and current_status='RUNNING'",
                        new PreparedStatementSetter() {
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setTimestamp(1, time);
                                ps.setString(2, errorMessage);
                                ps.setString(3, jobName);
                            }
                        });
        return count == 1;
    }

    /**
     * Updates job success
     *
     * @param jobName job name
     * @param time Run time
     * @return true if updated exactly one record
     */
    public boolean updateJobSuccess(final String jobName, final Timestamp time) {
        int count = getJdbcTemplate()
                .update(
                        "update mm_batch_ctrl_t set last_success=?, current_status='READY', error_detail=null where job_nm=? and current_status='RUNNING'",
                        new PreparedStatementSetter() {
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setTimestamp(1, time);
                                ps.setString(2, jobName);
                            }
                        });
        return count == 1;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.BatchControlDao#getLastSuccessRun(java.lang.String)
     */
    public Timestamp getLastSuccessRun(final String jobName) {
        Timestamp lastSuccess = (Timestamp) getJdbcTemplate().query(
                "select last_success from mm_batch_ctrl_t where job_nm=?",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, jobName);
                    }
                }, new ResultSetExtractor() {
                    public Object extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        if (rs != null && rs.next()) {
                            return rs.getTimestamp(1);
                        }
                        return null;
                    }
                });
        return lastSuccess;
    }
}
