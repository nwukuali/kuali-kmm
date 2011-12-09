/**
 * 
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.kuali.ext.mm.sys.batch.service.SearchIndexerService;

/**
 * @author harsha07
 */
public class SearchIndexerStep implements BatchStep {
    private SearchIndexerService searchIndexerService;

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    @Override
    public void execute(String jobName, Date time) {
        searchIndexerService.indexCatalogItems();
    }

    /**
     * Gets the searchIndexerService property
     * 
     * @return Returns the searchIndexerService
     */
    public SearchIndexerService getSearchIndexerService() {
        return this.searchIndexerService;
    }

    /**
     * Sets the searchIndexerService property value
     * 
     * @param searchIndexerService The searchIndexerService to set
     */
    public void setSearchIndexerService(SearchIndexerService searchIndexerService) {
        this.searchIndexerService = searchIndexerService;
    }


}
