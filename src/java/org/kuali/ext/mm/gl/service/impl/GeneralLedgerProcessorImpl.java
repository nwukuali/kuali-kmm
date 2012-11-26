/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.gl.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.gl.GeneralLedgerPostable;
import org.kuali.ext.mm.gl.GlConstants;
import org.kuali.ext.mm.gl.service.GeneralLedgerProcessor;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialObjectCode;
import org.kuali.ext.mm.integration.service.FinancialObjectCodeService;
import org.kuali.ext.mm.integration.service.FinancialUniversityDateService;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.util.*;


/**
 * General ledger service implementation
 */
public class GeneralLedgerProcessorImpl implements GeneralLedgerProcessor {


    /**
     * @see org.kuali.ext.mm.gl.service.GeneralLedgerProcessor#doRouteStatusChange(org.kuali.ext.mm.gl.GeneralLedgerPostable,
     *      org.kuali.rice.kns.bo.DocumentHeader)
     */
    public void doRouteStatusChange(GeneralLedgerPostable postable, DocumentHeader documentHeader) {
        WorkflowDocument workflowDocument = documentHeader.getWorkflowDocument();
        if (workflowDocument.isProcessed()) {
            // remove existing GLPE entries
            delete(documentHeader.getDocumentNumber());
            postable.setFinancialGeneralLedgerPendingEntries(null);
            // create and save approved ones
            createAndSaveGlpeEntries(postable, true);
        }
        if (workflowDocument.isCanceled() || workflowDocument.isDisapproved()) {
            // remove the GLPE entries
            delete(documentHeader.getDocumentNumber());
            postable.setFinancialGeneralLedgerPendingEntries(null);
        }
    }

    /**
     * @see org.kuali.ext.mm.gl.service.GeneralLedgerProcessor#delete(java.lang.String)
     */
    public void delete(String documentNumber) {
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (factory.isSystemAvailable()) {
            factory.getFinancialGeneralLedgerPendingEntryService().delete(documentNumber);
        }
    }


    /**
     * Invoked createExplicitGlpeEntries() and createOffsetGlpeEntries() to create the entries by the postable implementation. Then
     * it iterates the list and sets the default columns required. In addition it performs a simple balance check and required
     * fields check to ensure clean GL entries.
     * 
     * @param postable GL Postable implementation
     * @param approved Approved or not flag
     */
    protected void createAndSaveGlpeEntries(GeneralLedgerPostable postable, boolean approved) {
        List<FinancialGeneralLedgerPendingEntry> generateGlpeEntries = postable
                .generateGlpeEntries();
        List<FinancialGeneralLedgerPendingEntry> entries = cleanseGlpeEntries(generateGlpeEntries,
                postable.getDocumentNumber(), postable.getDocumentTypeCode(), approved);
        postable.setFinancialGeneralLedgerPendingEntries(entries);
        saveToFinanceSystem(entries);
    }

    protected List<FinancialGeneralLedgerPendingEntry> cleanseGlpeEntries(
            List<FinancialGeneralLedgerPendingEntry> generateGlpeEntries, String documentNumber,
            String documentTypeCode, boolean approved) {
        List<FinancialGeneralLedgerPendingEntry> entries = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        if (generateGlpeEntries == null && generateGlpeEntries.isEmpty()) {
            return entries;
        }
        KualiDecimal totalAmount = KualiDecimal.ZERO;
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (!factory.isSystemAvailable()) {
            return entries;
        }
        FinancialUniversityDateService univDtService = factory.getFinancialUniversityDateService();
        FinancialObjectCodeService financialObjectCodeService = factory
                .getFinancialObjectCodeService();
        Integer currentFiscalYear = univDtService.getCurrentFiscalYear();
        String currentFiscalPeriod = univDtService.getCurrentFiscalPeriod();
        DateTimeService dateTimeService = CoreApiServiceLocator.getDateTimeService();
        FinancialObjectCode finObjCode = null;
        int entrySequenceNum = 1;
        // for each entry set the common information
        for (FinancialGeneralLedgerPendingEntry entry : generateGlpeEntries) {
            if (entry.getTransactionLedgerEntryAmount() != null
                    && entry.getTransactionLedgerEntryAmount().isNonZero()) {
                entry.setFinancialSystemOriginationCode(GlConstants.getFinancialSystemOriginCode());
                if (StringUtils.isBlank(entry.getDocumentNumber())) {
                    entry.setDocumentNumber(documentNumber);
                }
                entry.setTransactionLedgerEntrySequenceNumber(entrySequenceNum++);
                entry.setUniversityFiscalYear(currentFiscalYear);
                entry.setUniversityFiscalPeriodCode(currentFiscalPeriod);
                entry.setTransactionDate(dateTimeService.getCurrentSqlDate());
                if (StringUtils.isBlank(entry.getFinancialDocumentTypeCode())) {
                    entry.setFinancialDocumentTypeCode(documentTypeCode);
                }
                entry.setFinancialDocumentApprovedCode(approved ? GlConstants.DOC_APPROVED_CODE_YES
                        : GlConstants.DOC_APPROVED_CODE_NO);
                entry.setTransactionEntryProcessedTs(dateTimeService.getCurrentTimestamp());
                // find financial object type code
                finObjCode = financialObjectCodeService.getByPrimaryId(entry
                        .getUniversityFiscalYear(), entry.getChartOfAccountsCode(), entry
                        .getFinancialObjectCode());
                if (finObjCode != null) {
                    entry.setFinancialObjectTypeCode(finObjCode.getFinancialObjectTypeCode());
                }
                totalAmount = totalAmount.add(entry.getTransactionDebitCreditCode().equals(
                        GlConstants.GL_CREDIT) ? entry.getTransactionLedgerEntryAmount() : entry
                        .getTransactionLedgerEntryAmount().negated());
                // add a check to make sure that all required fields are available for a GL entry
                if (entry.getUniversityFiscalYear() == null
                        || StringUtils.isBlank(entry.getChartOfAccountsCode())
                        || StringUtils.isBlank(entry.getAccountNumber())
                        // || StringUtils.isBlank(entry.getSubAccountNumber())
                        || StringUtils.isBlank(entry.getFinancialObjectCode())
                        // || StringUtils.isBlank(entry.getFinancialSubObjectCode())
                        || StringUtils.isBlank(entry.getFinancialBalanceTypeCode())
                        || StringUtils.isBlank(entry.getFinancialObjectTypeCode())
                        || StringUtils.isBlank(entry.getUniversityFiscalPeriodCode())
                        || StringUtils.isBlank(entry.getFinancialDocumentTypeCode())
                        || StringUtils.isBlank(entry.getFinancialSystemOriginationCode())
                        || StringUtils.isBlank(entry.getDocumentNumber())
                        || entry.getTransactionLedgerEntrySequenceNumber() == null
                        || StringUtils.isBlank(entry.getTransactionDebitCreditCode())) {
                    throw new RuntimeException(
                        "All required fields are not filled in for this entry "
                                + (entryString(entry)));

                }
                entries.add(entry);
            }

        }
        // make sure amount is balanced
        if (!totalAmount.isZero()) {
            // throw exception
            throw new RuntimeException("GL entries amount is not balanced");
        }
        return entries;
    }

    /**
     * @param entry Gives a string made of keys field of gl entry
     * @return String
     */
    protected String entryString(FinancialGeneralLedgerPendingEntry entry) {
        return "universityFiscalYear=" + entry.getUniversityFiscalYear() + ",chartOfAccountsCode="
                + entry.getChartOfAccountsCode() + ",accountNumber=" + entry.getAccountNumber()
                + ",subAccountNumber=" + entry.getSubAccountNumber() + ",financialObjectCode="
                + entry.getFinancialObjectCode() + ",financialSubObjectCode="
                + entry.getFinancialSubObjectCode() + ",financialBalanceTypeCode="
                + entry.getFinancialBalanceTypeCode() + ",financialObjectTypeCode="
                + entry.getFinancialObjectTypeCode() + ",universityFiscalPeriodCode="
                + entry.getUniversityFiscalPeriodCode() + ",financialDocumentTypeCode="
                + entry.getFinancialDocumentTypeCode() + ",financialSystemOriginationCode="
                + entry.getFinancialSystemOriginationCode() + ",documentNumber="
                + entry.getDocumentNumber() + ",transactionLedgerEntrySequenceNumber="
                + entry.getTransactionLedgerEntrySequenceNumber()
                + ",transactionLedgerEntryDescription="
                + entry.getTransactionLedgerEntryDescription() + ",transactionLedgerEntryAmount="
                + entry.getTransactionLedgerEntryAmount() + ",transactionDebitCreditCode="
                + entry.getTransactionDebitCreditCode();
    }

    /**
     * @see org.kuali.ext.mm.gl.service.GeneralLedgerProcessor#saveToFinanceSystem(java.util.List)
     */
    public void saveToFinanceSystem(List<FinancialGeneralLedgerPendingEntry> entries) {
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (factory.isSystemAvailable()) {
            factory.getFinancialGeneralLedgerPendingEntryService().save(entries);
        }
    }

    public void saveInternally(List<FinancialGeneralLedgerPendingEntry> entries) {
        List<FinancialGeneralLedgerPendingEntry> entriesList = cleanseGlpeEntries(entries, null,
                null, true);
        SpringContext.getBean(BusinessObjectService.class).save(entriesList);
    }

    public List<FinancialGeneralLedgerPendingEntry> getApprovedGeneralLedgerPendingEntries(
            String documentNumber) {
        ArrayList<FinancialGeneralLedgerPendingEntry> list = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (factory.isSystemAvailable()) {
            Collection<FinancialGeneralLedgerPendingEntry> pendingEntries = factory
                    .getFinancialGeneralLedgerPendingEntryService().findPendingEntries(
                            documentNumber);
            if (pendingEntries != null && !pendingEntries.isEmpty()) {
                list.addAll(pendingEntries);
            }
        }
        sortGlpes(list);
        return list;
    }

    public List<FinancialGeneralLedgerPendingEntry> getApprovedInternalGeneralLedgerPendingEntries(
            String documentNumber) {
        ArrayList<FinancialGeneralLedgerPendingEntry> list = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        BusinessObjectService bos = SpringContext.getBean(BusinessObjectService.class);
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("documentNumber", documentNumber);
        Collection match = bos.findMatching(FinancialGeneralLedgerPendingEntry.class, fieldValues);
        if (match != null && !match.isEmpty()) {
            list.addAll(match);
        }
        sortGlpes(list);
        return list;
    }

    /**
     * @param list
     */
    private void sortGlpes(ArrayList<FinancialGeneralLedgerPendingEntry> list) {
        Collections.sort(list, new Comparator<FinancialGeneralLedgerPendingEntry>() {
            public int compare(FinancialGeneralLedgerPendingEntry first,
                    FinancialGeneralLedgerPendingEntry second) {
                return (first.getAccountNumber() + ":" + first.getFinancialObjectCode())
                        .compareTo(second.getAccountNumber() + ":"
                                + second.getFinancialObjectCode());
            }
        });
    }
}
