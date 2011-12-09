/**
 *
 */
package org.kuali.ext.mm.integration.service;

import java.io.OutputStream;

import org.kuali.ext.mm.document.CheckinDocument;

/**
 * @author harsha07
 */
public interface FinancialElectronicInvoiceService {
    public void submitInvoice(CheckinDocument checkinDoc, OutputStream outputStream);
}
