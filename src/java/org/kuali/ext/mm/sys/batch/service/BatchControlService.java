/**
 *
 */
package org.kuali.ext.mm.sys.batch.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.kuali.ext.mm.sys.batch.BatchStep;
import org.kuali.ext.mm.sys.batch.service.impl.BatchStatusVO;


/**
 * @author harsha07
 */
public interface BatchControlService {

    public void addIfNewJob(String jobName);

    public boolean canRunJob(String jobName);

    public boolean updateJobFail(String jobName, String errorMessage);

    public boolean updateJobSuccess(String jobName);

    public Timestamp getLastSuccessRun(String jobName);

    public BatchStatusVO performJob(String jobName, List<BatchStep> batchSteps, Date time);

    public BatchStatusVO performJob(String jobName, Date time);
    
    public BatchStatusVO performSystemJob(String jobName, List<BatchStep> batchSteps, Date time);
}
