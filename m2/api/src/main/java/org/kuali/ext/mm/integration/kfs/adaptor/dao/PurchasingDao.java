package org.kuali.ext.mm.integration.kfs.adaptor.dao;


import org.kuali.rice.core.api.util.type.KualiDecimal;

public interface PurchasingDao {

    public int getLatestPurchaseOrderId(Integer vendorContractId);

    public KualiDecimal getTotalAmountByContract(Integer vendorContractId);

}