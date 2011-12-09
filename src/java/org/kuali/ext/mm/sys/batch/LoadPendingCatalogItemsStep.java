/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.kuali.ext.mm.sys.batch.service.CatalogPendingDocLoadService;



/**
 * @author rshrivas
 *
 */
public class LoadPendingCatalogItemsStep implements BatchStep {


    private CatalogPendingDocLoadService catalogPendingDocLoadService;


    /**
     * Gets the catalogPendingDocLoadService property
     * @return Returns the catalogPendingDocLoadService
     */
    public CatalogPendingDocLoadService getCatalogPendingDocLoadService() {
        return this.catalogPendingDocLoadService;
    }


    /**
     * Sets the catalogPendingDocLoadService property value
     * @param catalogPendingDocLoadService The catalogPendingDocLoadService to set
     */
    public void setCatalogPendingDocLoadService(
            CatalogPendingDocLoadService catalogPendingDocLoadService) {
        this.catalogPendingDocLoadService = catalogPendingDocLoadService;
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
        catalogPendingDocLoadService.loadCatalogPendingDocuments(time);
    }
}
