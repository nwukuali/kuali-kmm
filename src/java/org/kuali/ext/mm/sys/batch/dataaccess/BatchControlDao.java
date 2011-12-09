/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.sql.Timestamp;

/**
 * @author harsha07
 */
public interface BatchControlDao {

    public void addIfNewJob(String jobName);

    public boolean canRunJob(String jobName);

    public boolean updateJobFail(String jobName, Timestamp time, String errorMessage);

    public boolean updateJobSuccess(String jobName, Timestamp time);

    public Timestamp getLastSuccessRun(String jobName);
}
