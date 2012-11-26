/**
 *
 */
package org.kuali.ext.mm.integration.service.impl.kfs;

import org.kuali.ext.mm.integration.kfs.adaptor.KfsPurchaseOrderService;
import org.kuali.ext.mm.integration.service.FinancialPurchaseOrderService;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import javax.xml.namespace.QName;

/**
 * @author harsha07
 */
public class KfsPurchaseOrderServiceAdaptor extends KfsServiceAdaptor<KfsPurchaseOrderService>
        implements FinancialPurchaseOrderService {

    /**
     * @param name
     */
    public KfsPurchaseOrderServiceAdaptor(QName name) {
        super(name);
    }

    public int getLatestPurchaseOrderId(Integer vendorContractId) {
        return getService().getLatestPurchaseOrderId(vendorContractId);
    }

    public KualiDecimal getTotalAmountByContract(Integer vendorContractId) {
        return getService().getTotalAmountByContract(vendorContractId);
    }

}
