/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import java.io.File;
import java.util.ArrayList;

import org.apache.struts.upload.FormFile;
import org.kuali.ext.mm.businessobject.CatalogItemPending;
import org.kuali.ext.mm.businessobject.CatalogPendingDoc;
import org.kuali.ext.mm.sys.batch.dataaccess.CatalogPendingDocDao;
import org.kuali.ext.mm.sys.batch.service.CatalogPendingDocQueryService;

/**
 * @author rshrivas
 */
public class CatalogPendingDocQueryServiceImpl implements CatalogPendingDocQueryService {

    private CatalogPendingDocDao catalogPendingDocDao;


    /**
     * Gets the catalogPendingDocDao property
     * 
     * @return Returns the catalogPendingDocDao
     */
    public CatalogPendingDocDao getCatalogPendingDocDao() {
        return this.catalogPendingDocDao;
    }


    /**
     * Sets the catalogPendingDocDao property value
     * 
     * @param catalogPendingDocDao The catalogPendingDocDao to set
     */
    public void setCatalogPendingDocDao(CatalogPendingDocDao catalogPendingDocDao) {
        this.catalogPendingDocDao = catalogPendingDocDao;
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService#loadCatalogItemPending(java.util.Date)
     */
    public ArrayList<CatalogPendingDoc> loadCatalogPendingDoc(java.util.Date time) {
        return this.catalogPendingDocDao.getCatalogPendingDocs(time);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogPendingDocQueryService#getCatalogCodeFromPendingDocNbr(java.lang.String)
     */
    public String getCatalogCodeFromPendingDocNbr(String catalogPendingDocNbr) {
        return this.catalogPendingDocDao.getCatalogCodeFromPendingDocNbr(catalogPendingDocNbr);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogPendingDocQueryService#uplaodCatalogPendingDoc(java.lang.String)
     */
    public void uploadCatalogPendingDoc(String catalogPendingDocNbr,
            ArrayList<CatalogItemPending> loadList, boolean endOfFile) {
        this.catalogPendingDocDao
                .uplaodCatalogPendingDoc(catalogPendingDocNbr, loadList, endOfFile);
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogPendingDocQueryService#uploadCatalogActionSave(java.lang.String,
     *      java.lang.String, java.lang.String, org.apache.struts.upload.FormFile)
     */
    public void uploadCatalogActionSave(String docNumber, String catalogCd, String catalogDesc,
            FormFile uploadedFile){
        this.catalogPendingDocDao.uploadCatalogActionSave(docNumber, catalogCd, catalogDesc,
                uploadedFile);

    }

    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogPendingDocQueryService#createTempCatalogFile(java.lang.String)
     */
    public File createTempCatalogFile(String fdocNbr) {
        return this.catalogPendingDocDao.createTempCatalogFile(fdocNbr);
    }


}
