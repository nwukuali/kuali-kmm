package org.kuali.ext.mm.integration.kfs.adaptor.impl;

import org.kuali.ext.mm.integration.kfs.adaptor.KfsPurchaseOrderService;
import org.kuali.ext.mm.integration.kfs.adaptor.dao.PurchasingDao;
import org.kuali.rice.core.api.util.type.KualiDecimal;

public class KfsPurchaseOrderServiceImpl implements KfsPurchaseOrderService {
    private PurchasingDao purchasingDao;

    public int getLatestPurchaseOrderId(Integer vendorContractId) {
        return this.purchasingDao.getLatestPurchaseOrderId(vendorContractId);
    }

    public KualiDecimal getTotalAmountByContract(Integer vendorContractId) {
        return this.purchasingDao.getTotalAmountByContract(vendorContractId);
    }

    /**
     * @return the purchasingDao
     */
    public PurchasingDao getPurchasingDao() {
        return purchasingDao;
    }

    /**
     * @param purchasingDao the purchasingDao to set
     */
    public void setPurchasingDao(PurchasingDao purchasingDao) {
        this.purchasingDao = purchasingDao;
    }
}
