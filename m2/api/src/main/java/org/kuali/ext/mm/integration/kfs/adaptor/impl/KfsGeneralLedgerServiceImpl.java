/**
 * 
 */
package org.kuali.ext.mm.integration.kfs.adaptor.impl;

import org.kuali.ext.mm.integration.kfs.adaptor.KfsGeneralLedgerService;
import org.kuali.ext.mm.integration.kfs.adaptor.dao.GeneralLedgerDao;

/**
 *
 */
public class KfsGeneralLedgerServiceImpl implements KfsGeneralLedgerService {
    private GeneralLedgerDao generalLedgerDao;

    public Integer getNextTransactionLedgerEntrySequenceNumber(String originCode,
            String documentNumber) {
        Integer entrySeq = this.generalLedgerDao.getNextEntrySequenceNumber(originCode,
                documentNumber);
        Integer pendingEntrySeq = this.generalLedgerDao.getNextPendingEntrySequenceNumber(
                originCode, documentNumber);
        return (entrySeq != null && entrySeq > 0 && (pendingEntrySeq == null || entrySeq > pendingEntrySeq)) ? entrySeq + 1
                : (pendingEntrySeq != null ? pendingEntrySeq + 1 : 1);
    }

    /**
     * @return the generalLedgerDao
     */
    public GeneralLedgerDao getGeneralLedgerDao() {
        return generalLedgerDao;
    }

    /**
     * @param generalLedgerDao the generalLedgerDao to set
     */
    public void setGeneralLedgerDao(GeneralLedgerDao generalLedgerDao) {
        this.generalLedgerDao = generalLedgerDao;
    }

}
