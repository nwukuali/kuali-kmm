/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService;
import org.kuali.ext.mm.sys.batch.service.CatalogItemPendingService;
import org.kuali.ext.mm.sys.batch.service.CatalogPendingDocQueryService;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * @author rshrivas
 */
public class CatalogItemPendingServiceImpl implements CatalogItemPendingService {
    private Logger log = Logger.getLogger(CatalogItemPendingServiceImpl.class);
    private PlatformTransactionManager txManager;

    @Transactional
    public void publishApprovedCatalogs(Date time) {
        CatalogItemPendingQueryService cIPQS = SpringContext
                .getBean(CatalogItemPendingQueryService.class);
        // Get all the approved Catalogs
        ArrayList<String> catalogsToLoad = cIPQS.getCatalogCount();
        // Iterate over the catalogs
        for (String documentNumber : catalogsToLoad) {
            log.info("Starting processing Doc# "+documentNumber);
            /**
             * should start a new transaction here, Else it can easily cause transaction timeouts and fail complete load just
             * because one failed.
             */
            TransactionStatus tx = startTransaction(documentNumber);
            try {
                Catalog catalog = getCatalogFromCatalogPendingDocObject(documentNumber);
                if (catalog == null) {
                    // log error and continue
                    log.error("Skipped Doc# " + documentNumber + " because matching catalog was not found");
                    continue;
                }
                cIPQS.publishApprovedCatalogItems(documentNumber, catalog.getCatalogCd(), Long
                        .valueOf(catalog.getCatalogId()));
                catalog.setCatalogPendingDocNbr(documentNumber);
                catalog.save();
                cIPQS.updateCatalogObject(catalog);
                cIPQS.updateCatalogPendingDoc(documentNumber, "UPLOADED");
                txManager.commit(tx);
                log.info("Finished processing Doc# "+documentNumber);
            }
            catch (Throwable e) {
                log.error("Failed processing Doc# "+documentNumber, e);
                txManager.rollback(tx);
            }
        }
    }

    private Catalog getCatalogFromCatalogPendingDocObject(String catalogPendingDocNbr) {
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        CatalogPendingDocQueryService cPDQS = SpringContext
                .getBean(CatalogPendingDocQueryService.class);
        String catalogCode = cPDQS.getCatalogCodeFromPendingDocNbr(catalogPendingDocNbr);
        HashMap<String, String> primaryKeys = new HashMap<String, String>();
        primaryKeys.put("catalogCd", catalogCode);
        Catalog catalog = (Catalog) bOS.findByPrimaryKey(Catalog.class, primaryKeys);
        return catalog;
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
