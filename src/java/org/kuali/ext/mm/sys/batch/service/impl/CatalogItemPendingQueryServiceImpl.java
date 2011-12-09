/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItemPending;
import org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemPendingDao;
import org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService;

/**
 * @author rshrivas
 */
public class CatalogItemPendingQueryServiceImpl implements CatalogItemPendingQueryService {

    private CatalogItemPendingDao catalogItemPendingDao;


    /**
     * Gets the catalogItemPendingDao property
     * 
     * @return Returns the catalogItemPendingDao
     */
    public CatalogItemPendingDao getCatalogItemPendingDao() {
        return this.catalogItemPendingDao;
    }


    /**
     * Sets the catalogItemPendingDao property value
     * 
     * @param catalogItemPendingDao The catalogItemPendingDao to set
     */
    public void setCatalogItemPendingDao(CatalogItemPendingDao catalogItemPendingDao) {
        this.catalogItemPendingDao = catalogItemPendingDao;
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#queryCatalogSubgroupCode(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public String queryCatalogSubgroupCode(String catalogGroupCd, String derivedCatalogSubgroupCd,
            String derivedCatalogSubgroupCd10) {
        return this.catalogItemPendingDao.queryCatalogSubgroupCode(catalogGroupCd,
                derivedCatalogSubgroupCd, derivedCatalogSubgroupCd10);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#updateCatalogPendingDoc(java.lang.String,
     *      java.lang.String)
     */
    public void updateCatalogPendingDoc(String docNumber, String catalogUplaodStatus) {
        catalogItemPendingDao.updateCatalogPendingDoc(docNumber, catalogUplaodStatus);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#queryCatalogCode(java.lang.String, java.lang.String)
     */
    public String queryCatalogCode(String catalogGroupCd8, String catalogGroupCd10) {
        return this.catalogItemPendingDao.queryCatalogCode(catalogGroupCd8, catalogGroupCd10);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#getPreviousCatalogTimeStamp(java.lang.String,
     *      java.lang.String)
     */
    public String getPreviousCatalogTimeStamp(String catalogCd, String fDocNbr) {
        return this.catalogItemPendingDao.getPreviousCatalogTimeStamp(catalogCd, fDocNbr);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#getCollectionForCatalogAdditionsReport(java.lang.String,
     *      java.lang.String)
     */
    public Collection<CatalogItemPending> getCollectionForCatalogAdditionsReport(String docNbr,
            String docNbr2) {
        return this.catalogItemPendingDao.getCollectionForCatalogAdditionsReport(docNbr, docNbr2);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#getCollectionForCatalogDeletionsReport(java.lang.String,
     *      java.lang.String)
     */
    public Collection<CatalogItemPending> getCollectionForCatalogDeletionsReport(String docNbr,
            String docNbr2) {
        return this.catalogItemPendingDao.getCollectionForCatalogDeletionsReport(docNbr, docNbr2);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#getCollectionForFivePercentOrLessReport(java.lang.String,
     *      java.lang.String)
     */
    public Collection<CatalogItemPending> getCollectionForFivePercentOrLessReport(String docNbr,
            String docNbr2) {
        return this.catalogItemPendingDao.getCollectionForFivePercentOrLessReport(docNbr, docNbr2);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#getCollectionForPriceDecreaseReport(java.lang.String,
     *      java.lang.String)
     */
    public Collection<CatalogItemPending> getCollectionForPriceDecreaseReport(String docNbr,
            String docNbr2) {
        return this.catalogItemPendingDao.getCollectionForPriceDecreaseReport(docNbr, docNbr2);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#getCollectionForPriceIncreaseReport(java.lang.String,
     *      java.lang.String)
     */
    public Collection<CatalogItemPending> getCollectionForPriceIncreaseReport(String docNbr,
            String docNbr2) {
        return this.catalogItemPendingDao.getCollectionForPriceIncreaseReport(docNbr, docNbr2);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#getItemCount()
     */
    public int getItemCount(String fDocNbr) {
        return this.catalogItemPendingDao.getItemCount(fDocNbr);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#getCatalogCount()
     */
    public ArrayList<String> getCatalogCount() {
        return this.catalogItemPendingDao.getCatalogCount();
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#updateCatalogObject(org.kuali.ext.mm.businessobject.Catalog)
     */
    public void updateCatalogObject(Catalog catalog) {
        this.catalogItemPendingDao.updateCatalogObject(catalog);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#getTotalCatalogPrc(java.lang.String)
     */
    public String getTotalCatalogPrc(String docNbr) {
        return this.catalogItemPendingDao.getTotalCatalogPrc(docNbr);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#getCatalogPriceDecreasesMap(java.util.Collection)
     */
    public HashMap<String, Double> getCatalogPriceDecreasesMap(Collection<CatalogItemPending> cip) {
        return this.catalogItemPendingDao.getCatalogPriceDecreasesMap(cip);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#getCatalogPriceIncreasesMap(java.util.Collection)
     */
    public HashMap<String, Double> getCatalogPriceIncreasesMap(Collection<CatalogItemPending> cip) {
        return this.catalogItemPendingDao.getCatalogPriceIncreasesMap(cip);
    }

    public void publishApprovedCatalogItems(String fdocNbr, String catalogCode, Long catalogId) {
        this.catalogItemPendingDao.publishApprovedCatalogItems(fdocNbr, catalogCode, catalogId);
    }
}
