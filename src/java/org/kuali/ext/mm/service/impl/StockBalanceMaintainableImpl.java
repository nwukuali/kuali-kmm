package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StockBalanceMaintainableImpl extends KualiMaintainableImpl{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,MaintenanceDocument maintenanceDocument, String methodToCall) {

		BusinessObjectService bObjectService = SpringContext.getBean(BusinessObjectService.class);
		StockBalance stockBalance = (StockBalance)getBusinessObject();

		if(	methodToCall!= null &&
			methodToCall.equalsIgnoreCase(MMConstants.BLANKET_APPROVE)){

				HashMap<String, String> stockBins = new HashMap();
				stockBins.put("stock_id", stockBalance.getStockId());
				List <StockBalance> sB = (List<StockBalance>) bObjectService.findMatching(StockBalance.class, stockBins);

			// Do computation
		}

		return super.populateBusinessObject(fieldValues, maintenanceDocument, methodToCall);
	}
}

