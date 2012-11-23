/**
 * 
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.b2b.cxml.invoice.B2bInvoice;
import org.kuali.ext.mm.b2b.cxml.transform.CxmlToB2bInvoice;
import org.kuali.ext.mm.b2b.cxml.types.CXML;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;
import org.kuali.ext.mm.businessobject.CxmlInvoice;
import org.kuali.ext.mm.businessobject.PunchOutVendor;
import org.kuali.ext.mm.service.B2BPunchOutService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.sys.batch.dataaccess.B2bInvoiceDao;
import org.kuali.ext.mm.sys.batch.service.B2bInvoiceService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.ObjectUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.List;

/**
 * @author harsha
 */
public class B2bInvoiceServiceImpl implements B2bInvoiceService {
    private static final Logger LOG = Logger.getLogger(B2bInvoiceServiceImpl.class);
    private PlatformTransactionManager txManager;
    private B2bInvoiceDao b2bInvoiceDao;
    private B2BPunchOutService b2bPunchOutService;


    public void processB2bInvoices() {
        BusinessObjectService bos = MMServiceLocator.getBusinessObjectService();
        CxmlToB2bInvoice transformer = new CxmlToB2bInvoice();
        // find all un-processed xml files
        List<String> pendInvoices = this.b2bInvoiceDao.findPendingInvoiceIds();
        for (String keyId : pendInvoices) {
            LOG.info("Processing cxml payload Id: " + keyId);
            // for each xml, start a new transaction block so that whole process is not rolled back
            TransactionStatus status = startTransaction(keyId);
            LOG.info("Transaction started for "+ keyId);
            try {
                CxmlInvoice cxmlInvoice = bos.findBySinglePrimaryKey(CxmlInvoice.class, keyId);
                PunchOutVendor punchOutVendor = bos.findBySinglePrimaryKey(PunchOutVendor.class,
                        cxmlInvoice.getPunchOutVendorId());
                if (ObjectUtils.isNull(punchOutVendor) || !punchOutVendor.isActive()) {
                    LOG.warn("Punchout vendor not found for " + cxmlInvoice.getPunchOutVendorId());
                }
                else {
                    String xmlString = cxmlInvoice.getInvoiceXml();
                    CXML cxml = CxmlUtil.unmarshal(new InputSource(new StringReader(xmlString)));
                    if (cxml != null) {
                        B2bInvoice b2bInvoice = transformer.transform(cxml);
                        if (b2bInvoice != null) {
                            b2bInvoice.setPayloadId(cxml.getPayloadID());
                            b2bInvoice.setPunchOutVendorId(punchOutVendor.getPunchOutVendorId());
                            b2bInvoice.process();
                            cxmlInvoice.setProcessedInd(true);
                            b2bInvoice.save();
                            cxmlInvoice.save();
                            LOG.info("Cxml payload Id: "+ keyId + " completed successfully" );
                        }
                    }
                }
                LOG.info("Transaction committed for "+ keyId);
                txManager.commit(status);
            }
            catch (Throwable th) {
                LOG.error("Error found while processing invoice key id - " + keyId, th);
                txManager.rollback(status);
                LOG.info("Transaction rollback for "+ keyId);
            }
        }

    }

    private TransactionStatus startTransaction(String keyId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("process-invoice-" + keyId + "-" + Thread.currentThread().getName());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = txManager.getTransaction(def);
        return status;
    }

    /**
     * Sets the b2bInvoiceDao property value
     * 
     * @param b2bInvoiceDao The b2bInvoiceDao to set
     */
    public void setB2bInvoiceDao(B2bInvoiceDao b2bInvoiceDao) {
        this.b2bInvoiceDao = b2bInvoiceDao;
    }

    /**
     * Sets the txManager property value
     * 
     * @param txManager The txManager to set
     */
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    /**
     * Gets the b2bPunchOutService property
     * 
     * @return Returns the b2bPunchOutService
     */
    public B2BPunchOutService getB2bPunchOutService() {
        return this.b2bPunchOutService;
    }

    /**
     * Sets the b2bPunchOutService property value
     * 
     * @param b2bPunchOutService The b2bPunchOutService to set
     */
    public void setB2bPunchOutService(B2BPunchOutService b2bPunchOutService) {
        this.b2bPunchOutService = b2bPunchOutService;
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
     * Gets the b2bInvoiceDao property
     * 
     * @return Returns the b2bInvoiceDao
     */
    public B2bInvoiceDao getB2bInvoiceDao() {
        return this.b2bInvoiceDao;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.service.B2bInvoiceService#getInvoicedQuantity(java.lang.Integer)
     */
    @Override
    public Integer getInvoicedQuantity(Integer orderDetailId) {
        return this.b2bInvoiceDao.findInvoicedQuantity(orderDetailId);
    }
}
