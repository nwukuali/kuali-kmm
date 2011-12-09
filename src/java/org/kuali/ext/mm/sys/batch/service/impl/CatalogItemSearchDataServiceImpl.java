/**
 * 
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemSearchDataDao;
import org.kuali.ext.mm.sys.batch.service.CatalogItemSearchDataService;

/**
 * @author schneppd
 *
 */
public class CatalogItemSearchDataServiceImpl implements CatalogItemSearchDataService {
    private static final Logger LOG = Logger.getLogger(CatalogItemSearchDataServiceImpl.class);

    private CatalogItemSearchDataDao catalogItemSearchDataDao;
    
    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemSearchDataService#rebuildSearchData()
     */
    public void rebuildSearchData() {
        catalogItemSearchDataDao.dropIndexes();
        catalogItemSearchDataDao.truncateSearchTable();
        int insertCount = catalogItemSearchDataDao.rebuildSearchTable();
        LOG.info("Copied " + insertCount + " records into Catalog Item Search table.");
        catalogItemSearchDataDao.rebuildIndexes();
    }

    public CatalogItemSearchDataDao getCatalogItemSearchDataDao() {
        return catalogItemSearchDataDao;
    }

    public void setCatalogItemSearchDataDao(CatalogItemSearchDataDao catalogItemSearchDataDao) {
        this.catalogItemSearchDataDao = catalogItemSearchDataDao;
    }

}
