package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.SalesInstance;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.service.OrderService;
import org.kuali.ext.mm.service.SalesInstanceService;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.KualiDecimal;


/**
 * @author schneppd
 *
 */
public class SalesInstanceServiceImpl implements SalesInstanceService {

	BusinessObjectService businessObjectService;

	public SalesInstance getNewSalesInstance(OrderDocument orderDocument) {
		SalesInstance salesInstance = new SalesInstance();

		salesInstance.setOrderDocumentNbr(orderDocument.getDocumentNumber());
		salesInstance.setOrderStatusCd(orderDocument.getOrderStatus().getOrderStatusCd());
		salesInstance.setOrderTypeCd(orderDocument.getOrderType().getOrderTypeCode());
		salesInstance.setCustomerProfileId(orderDocument.getCustomerProfile().getProfileId());
		salesInstance.setOrderLineTotalAmt(orderDocument.getDisplaySubTotal());
		salesInstance.setOrderTaxableAmt(getTaxableAmountTotal(orderDocument));
		salesInstance.setOrderTotalAmt(salesInstance.getOrderLineTotalAmt().add(salesInstance.getOrderTaxableAmt()));
		return salesInstance;
	}

	public KualiDecimal getTaxableAmountTotal(OrderDocument orderDocument) {
		KualiDecimal total = new KualiDecimal(0);
		total = new KualiDecimal(SpringContext.getBean(OrderService.class).computeTaxTotal(orderDocument.getOrderDetails()));
		return total;
	}

	public void saveSalesInstance(SalesInstance salesInstance) {
		businessObjectService.save(salesInstance);
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}



}
