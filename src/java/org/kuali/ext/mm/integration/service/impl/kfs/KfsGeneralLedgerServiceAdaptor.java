/**
 * 
 */
package org.kuali.ext.mm.integration.service.impl.kfs;

import javax.xml.namespace.QName;

import org.kuali.ext.mm.integration.kfs.adaptor.KfsGeneralLedgerService;
import org.kuali.ext.mm.integration.service.FinancialGeneralLedgerService;

/**
 * @author harsha07
 */
public class KfsGeneralLedgerServiceAdaptor extends KfsServiceAdaptor<KfsGeneralLedgerService>
        implements FinancialGeneralLedgerService {

    /**
     * @param name
     */
    public KfsGeneralLedgerServiceAdaptor(QName name) {
        super(name);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialGeneralLedgerService#getNextTransactionLedgerEntrySequenceNumber(java.lang.String,
     *      java.lang.String)
     */
    public Integer getNextTransactionLedgerEntrySequenceNumber(String originCode,
            String documentNumber) {
        return getService().getNextTransactionLedgerEntrySequenceNumber(originCode, documentNumber);
    }

}
