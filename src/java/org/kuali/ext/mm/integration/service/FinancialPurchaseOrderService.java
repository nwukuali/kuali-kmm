/**
 *
 */
package org.kuali.ext.mm.integration.service;

import org.kuali.rice.core.api.util.type.KualiDecimal;

/**
 * @author harsha07
 */
public interface FinancialPurchaseOrderService {
    public int getLatestPurchaseOrderId(Integer vendorContractId);

    public KualiDecimal getTotalAmountByContract(Integer vendorContractId);
}
