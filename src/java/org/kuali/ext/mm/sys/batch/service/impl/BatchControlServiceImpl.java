/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.BatchJobDescriptor;
import org.kuali.ext.mm.sys.batch.BatchRuntimeException;
import org.kuali.ext.mm.sys.batch.BatchStep;
import org.kuali.ext.mm.sys.batch.NDCFilter;
import org.kuali.ext.mm.sys.batch.dataaccess.BatchControlDao;
import org.kuali.ext.mm.sys.batch.service.BatchControlService;
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.CharArrayWriter;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.kuali.ext.mm.common.sys.MMConstants.LF;


/**
 * @author harsha07
 */
public class BatchControlServiceImpl implements BatchControlService {
    private static final int STACK_TRACE_MAX_LENGTH = 1000;
    private static final Logger LOG = Logger.getLogger(BatchControlServiceImpl.class);
    private static final PatternLayout PATTERN_LAYOUT = new PatternLayout(
        "%d [%t] u:%X{user}/d:%X{docId} %-5p %c :: %m%n");
    private BatchControlDao batchControlDao;
    private DateTimeService dateTimeService;
    private static final ThreadLocal<BatchWriterAppender> CURRENT_APPENDER = new ThreadLocal<BatchWriterAppender>();
    private PlatformTransactionManager txManager;

    private static final class BatchWriterAppender extends WriterAppender {
        private CharArrayWriter charArrayWriter;

        /**
         * @param patternLayout
         * @param charArrayWriter2
         */
        public BatchWriterAppender(PatternLayout patternLayout, CharArrayWriter charArrayWriter2) {
            super(patternLayout, charArrayWriter2);
            this.charArrayWriter = charArrayWriter2;
        }

        /**
         * Gets the charArrayWriter property
         * 
         * @return Returns the charArrayWriter
         */
        public CharArrayWriter getCharArrayWriter() {
            return this.charArrayWriter;
        }

        /**
         * Sets the charArrayWriter property value
         * 
         * @param charArrayWriter The charArrayWriter to set
         */
        public void setCharArrayWriter(CharArrayWriter charArrayWriter) {
            this.charArrayWriter = charArrayWriter;
        }
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.service.BatchControlService#canRunJob(java.lang.String)
     */
    public boolean canRunJob(String jobName) {

        boolean ret = false;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("canRunJob-" + Thread.currentThread().getName());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionStatus status = txManager.getTransaction(def);
        try {
            ret = batchControlDao.canRunJob(jobName);
            txManager.commit(status);
        }
        catch (Throwable th) {
            txManager.rollback(status);
        }
        return ret;

    }

    /**
     * @see org.kuali.ext.mm.sys.batch.service.BatchControlService#updateJobFail(java.lang.String, java.lang.String)
     */
    public boolean updateJobFail(String jobName, String errorMessage) {
        boolean ret = false;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("updateJobFail-" + Thread.currentThread().getName());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionStatus status = txManager.getTransaction(def);
        try {
            ret = batchControlDao.updateJobFail(jobName, dateTimeService.getCurrentTimestamp(),
                    errorMessage);
            txManager.commit(status);
        }
        catch (Throwable th) {
            txManager.rollback(status);
        }
        return ret;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.service.BatchControlService#updateJobSuccess(java.lang.String)
     */
    public boolean updateJobSuccess(String jobName) {
        boolean ret = false;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("updateJobSuccess-" + Thread.currentThread().getName());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            ret = batchControlDao.updateJobSuccess(jobName, dateTimeService.getCurrentTimestamp());
            txManager.commit(status);
        }
        catch (Throwable th) {
            txManager.rollback(status);
        }
        return ret;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.service.BatchControlService#getLastSuccessRun(java.lang.String)
     */
    public Timestamp getLastSuccessRun(String jobName) {
        return batchControlDao.getLastSuccessRun(jobName);
    }

    public void addIfNewJob(String jobName) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("addIfNewJob-" + Thread.currentThread().getName());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionStatus status = txManager.getTransaction(def);
        try {
            batchControlDao.addIfNewJob(jobName);
            txManager.commit(status);
        }
        catch (Throwable th) {
            txManager.rollback(status);
        }
    }

    /**
     * Gets the batchControlDao property
     * 
     * @return Returns the batchControlDao
     */
    public BatchControlDao getBatchControlDao() {
        return this.batchControlDao;
    }

    /**
     * Sets the batchControlDao property value
     * 
     * @param batchControlDao The batchControlDao to set
     */
    public void setBatchControlDao(BatchControlDao batchControlDao) {
        this.batchControlDao = batchControlDao;
    }

    /**
     * Gets the dateTimeService property
     * 
     * @return Returns the dateTimeService
     */
    public DateTimeService getDateTimeService() {
        return this.dateTimeService;
    }

    /**
     * Sets the dateTimeService property value
     * 
     * @param dateTimeService The dateTimeService to set
     */
    public void setDateTimeService(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.service.BatchControlService#performJob(java.lang.String, java.util.List, java.util.Date)
     */
    public BatchStatusVO performJob(String jobName, List<BatchStep> batchSteps, Date time) {
        return controlAndRunJob(jobName, batchSteps, time, true);
    }

    /**
     * @param jobName
     * @param batchSteps
     * @param time
     * @return
     */
    private BatchStatusVO controlAndRunJob(String jobName, List<BatchStep> batchSteps, Date time,
            boolean clustered) {
        BatchStatusVO batchStatus = new BatchStatusVO();
        try {
            configureNDCLogging(jobName);
            if (batchSteps != null) {
                if (GlobalVariables.getUserSession() == null) {
                    UserSession session = new UserSession("kr");
                    GlobalVariables.setUserSession(session);
                    LOG.info("Batch user is " + session.getPrincipalName());
                }
                boolean sucess = true;
                try {
                    LOG.info("RUNNING JOB ### " + jobName);
                    // will add if job is never registered before in db
                    addIfNewJob(jobName);
                    // check if batch job is in current 'READY' state and mark as 'RUNNING'
                    if (!clustered || canRunJob(jobName)) {
                        try {
                            sucess = runSteps(jobName, batchSteps, time, batchStatus);
                        }
                        catch (BatchRuntimeException e) {
                            sucess = false;
                            // update last_attempt time and status back to 'READY' with error message
                            updateJobFail(jobName, buildErrorMessage(e.getMessage(), e));
                            LOG.error("Unexpected error", e);
                            batchStatus.setErrorCode("ERR01");
                            throw e;
                        }
                        // update last_success time, last_attempt time and status back to 'READY'
                        updateJobSuccess(jobName);
                    }
                    else {
                        sucess = false;
                        LOG.info("JOB " + jobName + " IS NOT IN 'READY' STATE");
                    }
                }
                finally {
                    LOG.info("FINISHED JOB ### " + jobName
                            + (sucess ? " SUCCESSFULLY" : " WITH ERRORS"));
                    batchStatus.setSuccess(sucess);
                    batchStatus.setLog4jMessage(CURRENT_APPENDER.get().getCharArrayWriter()
                            .toString());
                }
            }
        }
        finally {
            releaseNDCLogger();
        }
        return batchStatus;
    }

    public BatchStatusVO performSystemJob(String jobName, List<BatchStep> batchSteps, Date time) {
        return controlAndRunJob(jobName, batchSteps, time, false);
    }

    /**
     * @param jobName
     * @param batchSteps
     * @param time
     */
    private boolean runSteps(String jobName, List<BatchStep> batchSteps, Date time,
            BatchStatusVO batchStatus) throws BatchRuntimeException {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("runSteps-" + Thread.currentThread().getName());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // Set 5 hours as the time out because there are some jobs that could run for a very long time like catalog processing
        def.setTimeout((5 * 60 * 60 * 1000));
        TransactionStatus status = txManager.getTransaction(def);
        try {
            for (BatchStep step : batchSteps) {
                step.execute(jobName, time);
            }
            txManager.commit(status);
        }
        catch (Throwable e) {
            txManager.rollback(status);
            throw new BatchRuntimeException(e, batchStatus);
        }
        return true;
    }

    public BatchStatusVO performJob(String jobName, Date time) {
        BatchJobDescriptor batchJobDescriptor = (BatchJobDescriptor) SpringContext.getBean(jobName);
        if (batchJobDescriptor == null) {
            return null;
        }
        List<BatchStep> batchSteps = batchJobDescriptor.getSteps();
        return performJob(jobName, batchSteps, time);
    }

    /**
     *
     */
    protected void releaseNDCLogger() {
        if (CURRENT_APPENDER.get() != null) {
            Logger.getRootLogger().removeAppender(CURRENT_APPENDER.get());
            NDC.pop();
            NDC.remove();
            CURRENT_APPENDER.set(null);
        }
    }

    protected String configureNDCLogging(String jobName) {
        if (CURRENT_APPENDER.get() == null) {
            StringBuffer nestedDiagnosticContext = new StringBuffer(StringUtils.substringAfter(
                    jobName, "-").toLowerCase()).append(File.separator).append(jobName).append("-")
                    .append(
                            dateTimeService.toDateTimeStringForFilename(dateTimeService
                                    .getCurrentDate()));
            NDC.push(nestedDiagnosticContext.toString());
            CharArrayWriter charArrayWriter = new CharArrayWriter();
            CURRENT_APPENDER.set(new BatchWriterAppender(PATTERN_LAYOUT, charArrayWriter));
            CURRENT_APPENDER.get().addFilter(new NDCFilter(nestedDiagnosticContext.toString()));
            Logger.getRootLogger().addAppender(CURRENT_APPENDER.get());
            return nestedDiagnosticContext.toString();
        }
        return null;
    }

    /**
     * Builds an error message
     * 
     * @param msg Message
     * @param e Exception
     * @return error message
     */
    protected String buildErrorMessage(String msg, Throwable e) {
        StringBuilder sb = new StringBuilder(STACK_TRACE_MAX_LENGTH);
        sb.append(msg);
        sb.append(LF);
        StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace != null) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(stackTraceElement.toString());
                sb.append(LF);
            }
        }
        String string = sb.toString();
        if (string.length() > STACK_TRACE_MAX_LENGTH) {
            return string.substring(1, STACK_TRACE_MAX_LENGTH);
        }
        return string;
    }

    /**
     * Gets the txManager property
     * 
     * @return Returns the txManager
     */
    public PlatformTransactionManager getTxManager() {
        return this.txManager;
    }

    /**
     * Sets the txManager property value
     * 
     * @param txManager The txManager to set
     */
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }
}
