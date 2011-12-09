package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.SalesInstance;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.rice.kns.util.KualiDecimal;

/**
 * @author schneppd
 *
 */
public interface SalesInstanceService {

	public SalesInstance getNewSalesInstance(OrderDocument orderDocument);

	public KualiDecimal getTaxableAmountTotal(OrderDocument orderDocument);

	public void saveSalesInstance(SalesInstance salesInstance);

}
