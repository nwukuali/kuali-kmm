package org.kuali.ext.mm.integration.kfs.adaptor;


import org.kuali.rice.core.api.util.type.KualiDecimal;

public interface KfsPurchaseOrderService {

    public int getLatestPurchaseOrderId(Integer vendorContractId);

    public KualiDecimal getTotalAmountByContract(Integer vendorContractId);
}
