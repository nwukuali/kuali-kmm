/**
 * 
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.sys.batch.service.CatalogItemSearchDataService;

/**
 * @author schneppd
 */
public class CatalogItemSearchDataBuilderStep implements BatchStep {
    private static final Logger LOG = Logger.getLogger(CatalogItemSearchDataBuilderStep.class);
    private CatalogItemSearchDataService catalogItemSearchDataService;

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    @Override
    public void execute(String jobName, Date time) {
        LOG.info("Starting step CatalogItemSearchDataBuilderStep of batch job " + jobName);
        catalogItemSearchDataService.rebuildSearchData();
        LOG.info("Finished step CatalogItemSearchDataBuilderStep of batch job " + jobName);
    }
    
    /**
     * Gets the catalogItemSearchDataService property
     * @return Returns the catalogItemSearchDataService
     */
    public CatalogItemSearchDataService getCatalogItemSearchDataService() {
        return this.catalogItemSearchDataService;
    }

    /**
     * Sets the catalogItemSearchDataService property value
     * @param catalogItemSearchDataService The catalogItemSearchDataService to set
     */
    public void setCatalogItemSearchDataService(
            CatalogItemSearchDataService catalogItemSearchDataService) {
        this.catalogItemSearchDataService = catalogItemSearchDataService;
    }


}
