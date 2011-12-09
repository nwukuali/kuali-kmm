/**
 * 
 */
package org.kuali.ext.mm.integration.service;

/**
 *
 */
public interface FinancialGeneralLedgerService {
    /**
     * This method should query both gl_pending_entry_t and gl_entry_t to find the max sequence number for this input
     * 
     * @param originCode Origin Code
     * @param documentNumber Document Number
     * @return next sequence number
     */
    public Integer getNextTransactionLedgerEntrySequenceNumber(String originCode,
            String documentNumber);
}
