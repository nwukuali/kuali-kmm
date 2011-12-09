/**
 * 
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.sys.batch.service.FamisDataBuilderService;

/**
 * @author harsha07
 */
public class FamisDataBuilderStep implements BatchStep {
    private static final Logger LOG = Logger.getLogger(FamisDataBuilderStep.class);
    public FamisDataBuilderService famisDataBuilderService;

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
        LOG.info("Starting step FamisDataBuilderStep of batch job " + jobName);
        this.famisDataBuilderService.buildFamisDataFeedFile();
        LOG.info("Finished step FamisDataBuilderStep of batch job " + jobName);
    }

    /**
     * Gets the famisDataBuilderService property
     * 
     * @return Returns the famisDataBuilderService
     */
    public FamisDataBuilderService getFamisDataBuilderService() {
        return this.famisDataBuilderService;
    }

    /**
     * Sets the famisDataBuilderService property value
     * 
     * @param famisDataBuilderService The famisDataBuilderService to set
     */
    public void setFamisDataBuilderService(FamisDataBuilderService famisDataBuilderService) {
        this.famisDataBuilderService = famisDataBuilderService;
    }
}
