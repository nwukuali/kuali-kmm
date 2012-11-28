/**
 * 
 */
package org.kuali.ext.mm.integration.kfs.adaptor.dao;

/**
 *
 */
public interface GeneralLedgerDao {
    public Integer getNextPendingEntrySequenceNumber(String originCode, String documentNumber);

    public Integer getNextEntrySequenceNumber(String originCode, String documentNumber);
}
