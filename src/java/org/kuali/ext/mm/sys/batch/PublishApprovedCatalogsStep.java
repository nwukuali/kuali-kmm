/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.kuali.ext.mm.sys.batch.service.CatalogItemPendingService;

/**
 * @author rshrivas
 *
 */
public class PublishApprovedCatalogsStep implements BatchStep{

    private CatalogItemPendingService catalogItemPendingApprovedService;
    /**
     * Gets the catalogItemPendingApprovedService property
     * @return Returns the catalogItemPendingApprovedService
     */
    public CatalogItemPendingService getCatalogItemPendingApprovedService() {
        return this.catalogItemPendingApprovedService;
    }
    /**
     * Sets the catalogItemPendingApprovedService property value
     * @param catalogItemPendingApprovedService The catalogItemPendingApprovedService to set
     */
    public void setCatalogItemPendingApprovedService(
            CatalogItemPendingService catalogItemPendingApprovedService) {
        this.catalogItemPendingApprovedService = catalogItemPendingApprovedService;
    }
    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
        this.catalogItemPendingApprovedService.publishApprovedCatalogs(time);
    }

}
