/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.ext.mm.businessobject.CatalogItemPending;
import org.kuali.ext.mm.businessobject.CatalogPendingDoc;
import org.kuali.ext.mm.businessobject.UnitOfIssue;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.service.CatalogPendingDocLoadService;
import org.kuali.ext.mm.sys.batch.service.CatalogPendingDocQueryService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;


/**
 * @author rshrivas
 */
public class CatalogPendingDocLoadServiceImpl implements CatalogPendingDocLoadService {
    private static final Logger LOG = Logger.getLogger(CatalogPendingDocLoadServiceImpl.class);
    private PlatformTransactionManager txManager;

    /**
     * @throws IOException
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemPendingLoadService#loadCatalogPendingDocuments()
     */
    @Transactional
    public void loadCatalogPendingDocuments(Date time) {

        BusinessObjectService boService = SpringContext.getBean(BusinessObjectService.class);

        CatalogPendingDocQueryService cpdService = SpringContext
                .getBean(CatalogPendingDocQueryService.class);
        ArrayList<CatalogItemPending> loadList = new ArrayList<CatalogItemPending>();

        // Obtain a list of pending Catalogs that need to be loaded
        ArrayList<CatalogPendingDoc> pendingDocs = cpdService.loadCatalogPendingDoc(time);

        // Iterate over the collection
        Iterator<CatalogPendingDoc> itr = pendingDocs.iterator();


        while (itr.hasNext()) {
            CatalogPendingDoc pendingDoc = itr.next();
            TransactionStatus tx = startTransaction(pendingDoc.getFdocNbr());
            File tempCatalogFile = null;
            CsvReader cReader = null;
            HashSet<String> distributorNbrs = new HashSet<String>();
            try {
                tempCatalogFile = cpdService.createTempCatalogFile(pendingDoc.getFdocNbr());
                cReader = new CsvReader(tempCatalogFile.getPath());
                LOG.info("Catalog file is saved in " + tempCatalogFile.getPath());
                cReader.readHeaders();
                int lineCount = 0;
                int total = 0;
                int recCount = 0;
                while (cReader.readRecord()) {
                    recCount++;
                    if (!StringUtils.isBlank(cReader.get("UI"))
                            && isUIValid(boService, cReader.get("UI"))
                            && !StringUtils.isBlank(cReader.get("UPRICE"))
                            && !StringUtils.isBlank(cReader.get("DESCRIPTION"))
                            && !StringUtils.isBlank(cReader.get("UNSPSC"))) {
                        String distrNbr = cReader.get("DISTRNBR");
                        if (!distributorNbrs.add(distrNbr)) {
                            LOG.info("Duplicate " + distrNbr + " is skipped at line " + recCount);
                            continue;
                        }
                        CatalogItemPending cIP = new CatalogItemPending();
                        cIP.setCatalogPendingDocNbr(pendingDoc.getFdocNbr());
                        cIP.setDistributorNbr(distrNbr);
                        cIP.setManufacturerNbr(cReader.get("MANFNBR"));
                        cIP.setCatalogUnitOfIssueCd(cReader.get("UI"));
                        cIP.setCatalogPrc(new MMDecimal(cReader.get("UPRICE")));
                        cIP.setCatalogDesc(cReader.get("DESCRIPTION"));
                        cIP.setRecycledInd("Y".equalsIgnoreCase(cReader.get("RECYCLED")) ? true
                                : false);
                        cIP.setUnspscCd(cReader.get("UNSPSC"));
                        cIP.setTaxableInd((cReader.get("TAX") == "Y") ? true : false);
                        cIP.setCatalogTimestamp(pendingDoc.getCatalogTimestamp());
                        cIP.setCatalogImageUrl(cReader.get("URL"));
                        cIP.setSpaidId(cReader.get("SPAID"));
                        String catalogGroupNm = cReader.get("GROUP");
                        cIP.setCatalogGroupNm(!StringUtils.isBlank(catalogGroupNm) ? catalogGroupNm
                                : "MISCELLANEOUS");
                        String catalogSubgroupDesc = cReader.get("SUBGROUP");
                        cIP
                                .setCatalogSubgroupDesc(!StringUtils.isBlank(catalogSubgroupDesc) ? catalogSubgroupDesc
                                        : "MISCELLANEOUS");
                        cIP.setActive(false);
                        cIP.setLastUpdateDate(new Timestamp(time.getTime()));
                        loadList.add(cIP);
                        lineCount++;
                        if (lineCount % 10000 == 0) {
                            total += lineCount;
                            cpdService.uploadCatalogPendingDoc(pendingDoc.getFdocNbr(), loadList,
                                    false);
                            LOG.info("Inserted record count: " + total);
                            lineCount = 0;
                            loadList.clear();
                        }
                    }
                }
                if (!loadList.isEmpty()) {
                    total += lineCount;
                    cpdService.uploadCatalogPendingDoc(pendingDoc.getFdocNbr(), loadList, true);
                    LOG.info("Inserted record count: " + lineCount);
                }
                txManager.commit(tx);
                LOG.info("Transaction committed...");
            }
            catch (Throwable e) {
                LOG.error("Failed to process document #" + pendingDoc.getFdocNbr(), e);
                txManager.rollback(tx);
            }
            finally {
                if (cReader != null) {
                    cReader.close();
                }
                // delete the temp file
                if (tempCatalogFile != null && tempCatalogFile.exists()) {
                    if (tempCatalogFile.delete()) {
                        LOG.info("Deleted file " + tempCatalogFile.getPath());
                    }
                }
            }
        }
    }

    private boolean isUIValid(BusinessObjectService bOS, String uI) {
        UnitOfIssue uIObject = bOS.findBySinglePrimaryKey(UnitOfIssue.class, uI);
        boolean uIValid = true;
        if (uIObject == null) {
            uIValid = false;
        }
        return uIValid;
    }

    /**
     * Gets the txManager property
     * 
     * @return Returns the txManager
     */
    public PlatformTransactionManager getTxManager() {
        return this.txManager;
    }

    /**
     * Sets the txManager property value
     * 
     * @param txManager The txManager to set
     */
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    private TransactionStatus startTransaction(String keyId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(keyId + "-" + Thread.currentThread().getName());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = txManager.getTransaction(def);
        return status;
    }
}