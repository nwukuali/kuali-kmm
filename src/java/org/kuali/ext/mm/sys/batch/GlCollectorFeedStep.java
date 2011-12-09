/**
 * 
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.sys.batch.service.GlCollectorFeedService;

/**
 * @author harsha07
 */
public class GlCollectorFeedStep implements BatchStep {
    private static final Logger LOG = Logger.getLogger(GlCollectorFeedStep.class);
    private GlCollectorFeedService glCollectorFeedService;

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
        LOG.info("Starting step GlCollectorFeedStep");
        glCollectorFeedService.buildGlCollectorFeeds();
        LOG.info("Finished step GlCollectorFeedStep");
    }

    /**
     * Gets the glCollectorFeedService property
     * 
     * @return Returns the glCollectorFeedService
     */
    public GlCollectorFeedService getGlCollectorFeedService() {
        return this.glCollectorFeedService;
    }

    /**
     * Sets the glCollectorFeedService property value
     * 
     * @param glCollectorFeedService The glCollectorFeedService to set
     */
    public void setGlCollectorFeedService(GlCollectorFeedService glCollectorFeedService) {
        this.glCollectorFeedService = glCollectorFeedService;
    }

}
