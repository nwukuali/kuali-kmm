/**
 *
 */
package org.kuali.ext.mm.service;

import org.kuali.ext.mm.document.ReceiptCorrectionDocument;

/**
 * @author rponraj
 *
 */
public interface ReceiptCorrectionService {

    public ReceiptCorrectionDocument createReceiptItems(ReceiptCorrectionDocument checkinDoc, String orderDocNumber,
            String orderLineNumber) throws Exception;

    public void processReceiptCorrectionDocument(ReceiptCorrectionDocument cdoc) throws Exception;
    
}
