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

package org.kuali.ext.mm.integration.service.impl.kfs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.service.FinancialGeneralLedgerPendingEntryService;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.kfs.sys.businessobject.GeneralLedgerPendingEntry;
import org.kuali.kfs.sys.service.GeneralLedgerPendingEntryService;


/**
 * @author harsha07
 */
public class KfsGeneralLedgerPendingEntryServiceAdaptor extends
        KfsServiceAdaptor<GeneralLedgerPendingEntryService> implements
        FinancialGeneralLedgerPendingEntryService {

    /**
     * @param qName
     */
    public KfsGeneralLedgerPendingEntryServiceAdaptor(QName qName) {
        super(qName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialGeneralLedgerPendingEntryService#delete(java.lang.String)
     */
    public void delete(String documentHeaderId) {
        getService().delete(documentHeaderId);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialGeneralLedgerPendingEntryService#findPendingEntries(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Collection<FinancialGeneralLedgerPendingEntry> findPendingEntries(String documentNumber) {
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        HashMap<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put("documentNumber", documentNumber);
        Collection sourceEntries = factory.getFinancialBusinessObjectService().findMatching(
                GeneralLedgerPendingEntry.class, fieldValues);
        if (sourceEntries == null || sourceEntries.isEmpty()) {
            return null;
        }
        GeneralLedgerPendingEntry source = null;
        FinancialGeneralLedgerPendingEntry target = null;
        Collection<FinancialGeneralLedgerPendingEntry> targetEntries = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        for (Object object : sourceEntries) {
            source = (GeneralLedgerPendingEntry) object;
            target = new FinancialGeneralLedgerPendingEntry();
            adapt(source, target);
            targetEntries.add(target);
        }
        return targetEntries;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialGeneralLedgerPendingEntryService#getByPrimaryId(java.lang.Integer,
     *      java.lang.String)
     */
    public FinancialGeneralLedgerPendingEntry getByPrimaryId(Integer transactionEntrySequenceId,
            String documentHeaderId) {
        GeneralLedgerPendingEntry source = getService().getByPrimaryId(transactionEntrySequenceId,
                documentHeaderId);
        if (source == null) {
            return null;
        }
        FinancialGeneralLedgerPendingEntry target = new FinancialGeneralLedgerPendingEntry();
        adapt(source, target);
        return target;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialGeneralLedgerPendingEntryService#save(java.util.List)
     */
    public void save(List<FinancialGeneralLedgerPendingEntry> entries) {
        FinancialGeneralLedgerPendingEntry source = null;
        GeneralLedgerPendingEntry target = null;
        for (Object object : entries) {
            source = (FinancialGeneralLedgerPendingEntry) object;
            target = new GeneralLedgerPendingEntry();
            adapt(source, target);
            getService().save(target);
        }
    }

    /**
     * Adapts GeneralLedgerPendingEntry to FinancialGeneralLedgerPendingEntry
     *
     * @param source GeneralLedgerPendingEntry
     * @param target FinancialGeneralLedgerPendingEntry
     */
    protected void adapt(GeneralLedgerPendingEntry source, FinancialGeneralLedgerPendingEntry target) {
        target.setFinancialSystemOriginationCode(source.getFinancialSystemOriginationCode());
        target.setDocumentNumber(source.getDocumentNumber());
        target.setTransactionLedgerEntrySequenceNumber(source
                .getTransactionLedgerEntrySequenceNumber());
        target.setChartOfAccountsCode(source.getChartOfAccountsCode());
        target.setAccountNumber(source.getAccountNumber());
        target.setSubAccountNumber(source.getSubAccountNumber());
        target.setFinancialObjectCode(source.getFinancialObjectCode());
        target.setFinancialSubObjectCode(source.getFinancialSubObjectCode());
        target.setFinancialBalanceTypeCode(source.getFinancialBalanceTypeCode());
        target.setFinancialObjectTypeCode(source.getFinancialObjectTypeCode());
        target.setUniversityFiscalYear(source.getUniversityFiscalYear());
        target.setUniversityFiscalPeriodCode(source.getUniversityFiscalPeriodCode());
        target.setTransactionLedgerEntryDescription(source.getTransactionLedgerEntryDescription());
        target.setTransactionLedgerEntryAmount(source.getTransactionLedgerEntryAmount());
        target.setTransactionDebitCreditCode(source.getTransactionDebitCreditCode());
        target.setTransactionDate(source.getTransactionDate());
        target.setFinancialDocumentTypeCode(source.getFinancialDocumentTypeCode());
        target.setOrganizationDocumentNumber(source.getOrganizationDocumentNumber());
        target.setProjectCode(source.getProjectCode());
        target.setOrganizationReferenceId(source.getOrganizationReferenceId());
        target
                .setReferenceFinancialDocumentTypeCode(source
                        .getReferenceFinancialDocumentTypeCode());
        target.setReferenceFinancialSystemOriginationCode(source
                .getReferenceFinancialSystemOriginationCode());
        target.setReferenceFinancialDocumentNumber(source.getReferenceFinancialDocumentNumber());
        target.setFinancialDocumentReversalDate(source.getFinancialDocumentReversalDate());
        target.setTransactionEncumbranceUpdateCode(source.getTransactionEncumbranceUpdateCode());
        target.setFinancialDocumentApprovedCode(source.getFinancialDocumentApprovedCode());
        target.setAcctSufficientFundsFinObjCd(source.getAcctSufficientFundsFinObjCd());
        target.setTransactionEntryOffsetIndicator(source.isTransactionEntryOffsetIndicator());
        target.setTransactionEntryProcessedTs(source.getTransactionEntryProcessedTs());
    }

    /**
     * Adapts FinancialGeneralLedgerPendingEntry to GeneralLedgerPendingEntry
     *
     * @param source FinancialGeneralLedgerPendingEntry
     * @param target GeneralLedgerPendingEntry
     */
    protected void adapt(FinancialGeneralLedgerPendingEntry source, GeneralLedgerPendingEntry target) {
        target.setFinancialSystemOriginationCode(source.getFinancialSystemOriginationCode());
        target.setDocumentNumber(source.getDocumentNumber());
        target.setTransactionLedgerEntrySequenceNumber(source
                .getTransactionLedgerEntrySequenceNumber());
        target.setChartOfAccountsCode(source.getChartOfAccountsCode());
        target.setAccountNumber(source.getAccountNumber());
        target.setSubAccountNumber(source.getSubAccountNumber());
        target.setFinancialObjectCode(source.getFinancialObjectCode());
        target.setFinancialSubObjectCode(source.getFinancialSubObjectCode());
        target.setFinancialBalanceTypeCode(source.getFinancialBalanceTypeCode());
        target.setFinancialObjectTypeCode(source.getFinancialObjectTypeCode());
        target.setUniversityFiscalYear(source.getUniversityFiscalYear());
        target.setUniversityFiscalPeriodCode(source.getUniversityFiscalPeriodCode());
        target.setTransactionLedgerEntryDescription(source.getTransactionLedgerEntryDescription());
        target.setTransactionLedgerEntryAmount(source.getTransactionLedgerEntryAmount());
        target.setTransactionDebitCreditCode(source.getTransactionDebitCreditCode());
        target.setTransactionDate(source.getTransactionDate());
        target.setFinancialDocumentTypeCode(source.getFinancialDocumentTypeCode());
        target.setOrganizationDocumentNumber(source.getOrganizationDocumentNumber());
        target.setProjectCode(source.getProjectCode());
        target.setOrganizationReferenceId(source.getOrganizationReferenceId());
        target
                .setReferenceFinancialDocumentTypeCode(source
                        .getReferenceFinancialDocumentTypeCode());
        target.setReferenceFinancialSystemOriginationCode(source
                .getReferenceFinancialSystemOriginationCode());
        target.setReferenceFinancialDocumentNumber(source.getReferenceFinancialDocumentNumber());
        target.setFinancialDocumentReversalDate(source.getFinancialDocumentReversalDate());
        target.setTransactionEncumbranceUpdateCode(source.getTransactionEncumbranceUpdateCode());
        target.setFinancialDocumentApprovedCode(source.getFinancialDocumentApprovedCode());
        target.setAcctSufficientFundsFinObjCd(source.getAcctSufficientFundsFinObjCd());
        target.setTransactionEntryOffsetIndicator(source.isTransactionEntryOffsetIndicator());
        target.setTransactionEntryProcessedTs(source.getTransactionEntryProcessedTs());
    }

}
