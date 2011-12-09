/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import java.util.List;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.service.BatchControlService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * @author harsha07
 */
public class BatchJob extends QuartzJobBean {
    /**
     * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String jobName = context.getJobDetail().getName();
        BatchJobDescriptor jobDetail = (BatchJobDescriptor) SpringContext.getBean(jobName);
        List<BatchStep> batchSteps = jobDetail.getSteps();
        BatchControlService controlService = SpringContext.getBean(BatchControlService.class);
        if (jobDetail.isClustered()) {
            controlService.performJob(jobName, batchSteps, context.getFireTime());
        }
        else {
            controlService.performSystemJob(jobName, batchSteps, context.getFireTime());
        }
    }
}
