/**
 * 
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.common.sys.MMPropertyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.ext.mm.sys.batch.dataaccess.GlCollectorFeedDao;
import org.kuali.ext.mm.sys.batch.service.GlCollectorFeedService;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.kuali.ext.mm.common.sys.MMConstants.LF;


/**
 * @author harsha07
 */
public class GlCollectorFeedServiceImpl implements GlCollectorFeedService {
    private static final Logger LOG = Logger.getLogger(GlCollectorFeedServiceImpl.class);
    private GlCollectorFeedDao glCollectorFeedDao;
    private PlatformTransactionManager txManager;

    public void buildGlCollectorFeeds() {
        if (!SpringContext.getBean(FinancialSystemAdaptorFactory.class).isSystemAvailable()) {
            LOG.error("This service failed because Finance system services are not available.");
            throw new RuntimeException("Financial System Services are unavailable.");
        }
        // find all active warehouses
        BusinessObjectService bos = SpringContext.getBean(BusinessObjectService.class);
        HashMap<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put("active", Boolean.TRUE);
        Collection<Warehouse> warehouses = bos.findMatching(Warehouse.class, fieldValues);
        LOG.info("Total number of active warehouses found - " + warehouses.size());

        String glCollectorDir = KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(
                MMPropertyConstants.KMM_GL_COLLECTOR_DIR);
        LOG.info("GL Collector directory location is " + glCollectorDir);
        if (!new File(glCollectorDir).exists()) {
            new File(glCollectorDir).mkdirs();
        }
        for (Warehouse wh : warehouses) {
            processWarehouseGlEntries(wh, glCollectorDir);
        }
    }

    public void processWarehouseGlEntries(Warehouse wh, String glCollectorDir) {
        TransactionStatus txn = startTransaction(Thread.currentThread().getName() + "-"
                + wh.getWarehouseCd());
        String fileName = buildCollectorFileName(wh);
        File collectorFile = new File(glCollectorDir, fileName);
        try {
            HashMap<String, Integer> sequenceMap = new HashMap<String, Integer>();
            String warehouseCd = wh.getWarehouseCd();
            LOG.info("Processing GL entries for warehouse - " + warehouseCd);
            // for each warehouse build GL file
            List<FinancialGeneralLedgerPendingEntry> entries = this.glCollectorFeedDao
                    .getEntries(warehouseCd);
            if (entries == null || entries.isEmpty()) {
                if (collectorFile.exists()) {
                    collectorFile.delete();
                }
                LOG.warn("No GL entries found for warehouse " + warehouseCd);
                return;
            }
            LOG.info("Count of GL entries found - " + entries.size());
            BufferedWriter writer = new BufferedWriter(new FileWriter(collectorFile));
            java.sql.Date today = new java.sql.Date(new Date().getTime());
            try {
                buildXMLheader(writer);
                writer.newLine();
                writer.append(buildGLCollectorHeader(wh));
                writer.newLine();
                KualiDecimal amount = KualiDecimal.ZERO;
                for (FinancialGeneralLedgerPendingEntry entry : entries) {
                    amount = amount.add(entry.getTransactionLedgerEntryAmount());
                    Integer seq = null;
                    String documentNumber = entry.getDocumentNumber();
                    if ((seq = sequenceMap.get(documentNumber)) == null) {
                        sequenceMap.put(documentNumber, getNextSequenceNumber(entry));
                    }
                    seq = sequenceMap.get(documentNumber);
                    entry.setTransactionLedgerEntrySequenceNumber(seq);
                    entry.setTransactionDate(today);
                    String glEntryXML = entry.toCollectorXML();
                    writer.write(glEntryXML);
                    writer.newLine();
                    sequenceMap.put(documentNumber, ++seq);
                }
                writer.append(buildGlCollectorTrailer(entries.size(), amount));
                writer.newLine();
                writer.append("</batch>");
            }
            finally {
                writer.flush();
                writer.close();
                LOG.info("Finished writing file " + fileName);
            }
            validateXMLContent(collectorFile);
            // clear entries for each warehouse
            this.glCollectorFeedDao.clearEntries(warehouseCd);
            LOG.info("Clear entries from database for warehouse " + wh.getWarehouseCd());
            txManager.commit(txn);
        }
        catch (Throwable e) {
            collectorFile.delete();
            LOG.error("GL entries for warehouse - " + wh.getWarehouseCd() + " failed. ", e);
            txManager.rollback(txn);
        }

    }

    /**
     * @param writer
     * @throws IOException
     */
    private void buildXMLheader(BufferedWriter writer) throws IOException {
        writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.newLine();
        writer.append("<batch xmlns=\"http://www.kuali.org/kfs/gl/collector\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xsi:schemaLocation=\"" + glCollectorSchemaLocation() + "\">");

    }

    /**
     * @param collectorFile
     * @throws SAXException
     * @throws MalformedURLException
     * @throws IOException
     */
    private void validateXMLContent(File collectorFile) throws SAXException, MalformedURLException,
            IOException {
        // validate against schema
        LOG.info("Validating content of " + collectorFile.getName());
        Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
                new URL(glCollectorSchemaLocation()));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(collectorFile));
    }

    /**
     * @return
     */
    private String glCollectorSchemaLocation() {
        return KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(
                MMPropertyConstants.FINANCE_SYSTEM_URL)
                + "/static/xsd/gl/collector.xsd";
    }

    /**
     * @param wh
     * @param warehouseCd
     * @return
     */
    private String buildCollectorFileName(Warehouse wh) {
        String fileName = "collector_"
                + wh.getWarehouseCd()
                + "_"
                + wh.getDefaultChartCode()
                + wh.getDefaultOrgCode()
                + "_"
                + ((ObjectUtils.isNotNull(wh.getBillingProfile())) ? wh.getBillingProfile()
                        .getPrincipalName()
                        + "_" : "") + new SimpleDateFormat("yyyyMMdd-HH-mm-ss").format(new Date())
                + ".xml";
        LOG.info("Collector file name - " + fileName);
        return fileName;
    }

    private String buildGLCollectorHeader(Warehouse wh) {
        StringBuilder sb = new StringBuilder();
        sb.append("<header>" + LF);
        sb.append("<chartOfAccountsCode>" + wh.getDefaultChartCode() + "</chartOfAccountsCode>"
                + LF);
        sb.append("<organizationCode>" + wh.getDefaultOrgCode() + "</organizationCode>" + LF);
        sb.append("<transmissionDate>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                + "</transmissionDate>" + LF);
        sb.append("<batchSequenceNumber>1</batchSequenceNumber>" + LF);
        sb.append("<personUserId>"
                + ((ObjectUtils.isNotNull(wh.getBillingProfile())) ? wh.getBillingProfile()
                        .getPrincipalName() : "kr") + "</personUserId>" + LF);
        sb.append("<emailAddress>"
                + ((ObjectUtils.isNotNull(wh.getBillingProfile())) ? wh.getBillingProfile()
                        .getProfileEmail() : "noreply@kuali.org") + "</emailAddress>" + LF);
        sb.append("<campusCode>"
                + ((ObjectUtils.isNotNull(wh.getBillingProfile())) ? wh.getBillingProfile()
                        .getCampusCode() : "NA") + "</campusCode>" + LF);
        sb.append("<departmentName>" + wh.getWarehouseNme() + "</departmentName>" + LF);
        sb.append("<mailingAddress>"
                + ((ObjectUtils.isNotNull(wh.getBillingProfile())) ? wh.getBillingProfile()
                        .getBillingBuildingCode() : "NA") + "</mailingAddress>" + LF);
        sb.append("<phoneNumber>"
                + ((ObjectUtils.isNotNull(wh.getBillingProfile())) ? wh.getBillingProfile()
                        .getProfilePhoneNumber() : "888-888-8888") + "</phoneNumber>" + LF);
        sb.append("</header>");
        return sb.toString();
    }

    private String buildGlCollectorTrailer(int recordCount, KualiDecimal totalAmount) {
        StringBuilder sb = new StringBuilder();
        sb.append("<trailer>" + LF);
        sb.append("<totalRecords>" + recordCount + "</totalRecords>" + LF);
        sb.append("<totalAmount>" + totalAmount.toString() + "</totalAmount>" + LF);
        sb.append("</trailer>");
        return sb.toString();
    }

    private TransactionStatus startTransaction(String keyId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("process-invoice-" + keyId + "-" + Thread.currentThread().getName());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = txManager.getTransaction(def);
        return status;
    }

    /**
     * @param entry
     * @return
     */
    private Integer getNextSequenceNumber(FinancialGeneralLedgerPendingEntry entry) {
        return SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .getFinancialGeneralLedgerService().getNextTransactionLedgerEntrySequenceNumber(
                        entry.getFinancialSystemOriginationCode(), entry.getDocumentNumber());
    }

    /**
     * Gets the glCollectorFeedDao property
     * 
     * @return Returns the glCollectorFeedDao
     */
    public GlCollectorFeedDao getGlCollectorFeedDao() {
        return this.glCollectorFeedDao;
    }

    /**
     * Sets the glCollectorFeedDao property value
     * 
     * @param glCollectorFeedDao The glCollectorFeedDao to set
     */
    public void setGlCollectorFeedDao(GlCollectorFeedDao glCollectorFeedDao) {
        this.glCollectorFeedDao = glCollectorFeedDao;
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
}
