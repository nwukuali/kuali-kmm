/**
 * 
 */
package org.kuali.ext.mm.sys.batch.web.struts;

import org.kuali.ext.mm.sys.batch.service.impl.BatchStatusVO;
import org.kuali.rice.kns.web.struts.form.KualiForm;

/**
 * @author harsha07
 */
public class BatchControlForm extends KualiForm {
    private static final long serialVersionUID = 5693375668761025787L;
    private String message;
    private String jobName;
    private BatchStatusVO batchStatus;

    /**
     * Gets the jobName property
     * 
     * @return Returns the jobName
     */
    public String getJobName() {
        return this.jobName;
    }

    /**
     * Sets the jobName property value
     * 
     * @param jobName The jobName to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * Gets the message property
     * 
     * @return Returns the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message property value
     * 
     * @param message The message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the batchStatus property
     * 
     * @return Returns the batchStatus
     */
    public BatchStatusVO getBatchStatus() {
        return this.batchStatus;
    }

    /**
     * Sets the batchStatus property value
     * 
     * @param batchStatus The batchStatus to set
     */
    public void setBatchStatus(BatchStatusVO batchStatus) {
        this.batchStatus = batchStatus;
    }

}
