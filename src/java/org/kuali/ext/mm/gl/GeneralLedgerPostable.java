/**
 *
 */
package org.kuali.ext.mm.gl;

import java.util.List;

import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;

/**
 * Any document or maintainable implementation that creates GL entries should implement this interface.
 */
public interface GeneralLedgerPostable {

    public String getDocumentNumber();

    /**
     * Following properties should be populated by respective implementation for each entry
     * <li>chartOfAccountsCode-required</li>
     * <li>accountNumber-required</li>
     * <li>subAccountNumber-optional</li>
     * <li>financialObjectCode-required</li>
     * <li>financialSubObjectCode-optional</li>
     * <li>financialBalanceTypeCode-required</li>
     * <li>transactionLedgerEntryDescription-required</li>
     * <li>transactionLedgerEntryAmount-required</li>
     * <li>transactionDebitCreditCode-required</li>
     *
     * @return List of populated entries
     */
    public List<FinancialGeneralLedgerPendingEntry> generateGlpeEntries();

    /**
     * @return full list of GL pending entries
     */
    public List<FinancialGeneralLedgerPendingEntry> getFinancialGeneralLedgerPendingEntries();

    /**
     * Sets full list of GL pending entries
     *
     * @param entries Entries
     */
    public void setFinancialGeneralLedgerPendingEntries(
            List<FinancialGeneralLedgerPendingEntry> entries);

    /**
     * Should return the document type code
     *
     * @return Document Type Code
     */
    public String getDocumentTypeCode();

}
