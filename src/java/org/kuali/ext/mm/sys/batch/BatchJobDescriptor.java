/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import java.util.List;

import org.springframework.scheduling.quartz.JobDetailBean;

/**
 * @author harsha07
 */
public class BatchJobDescriptor extends JobDetailBean {
    private static final long serialVersionUID = 4519919568659518966L;
    public static final String BATCH_ENABLED = "batchEnabled";

    private List<BatchStep> steps;
    
    private boolean clustered = true;

    /**
     * Gets the clustered property
     * @return Returns the clustered
     */
    public boolean isClustered() {
        return this.clustered;
    }

    /**
     * Sets the clustered property value
     * @param clustered The clustered to set
     */
    public void setClustered(boolean clustered) {
        this.clustered = clustered;
    }

    /**
     * Gets the steps property
     *
     * @return Returns the steps
     */
    public List<BatchStep> getSteps() {
        return this.steps;
    }

    /**
     * Sets the steps property value
     *
     * @param steps The steps to set
     */
    public void setSteps(List<BatchStep> steps) {
        this.steps = steps;
    }
}
