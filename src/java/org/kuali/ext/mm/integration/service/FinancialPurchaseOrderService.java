/**
 *
 */
package org.kuali.ext.mm.integration.service;

import org.kuali.rice.kns.util.KualiDecimal;

/**
 * @author harsha07
 */
public interface FinancialPurchaseOrderService {
    public int getLatestPurchaseOrderId(Integer vendorContractId);

    public KualiDecimal getTotalAmountByContract(Integer vendorContractId);
}
