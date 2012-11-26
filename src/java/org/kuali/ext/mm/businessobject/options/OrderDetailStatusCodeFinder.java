package org.kuali.ext.mm.businessobject.options;

import org.kuali.ext.mm.businessobject.OrderStatus;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.krad.valuefinder.ValueFinder;


public class OrderDetailStatusCodeFinder extends MMKeyValuesBase implements
	ValueFinder {

	public OrderDetailStatusCodeFinder(){
		super(OrderStatus.class, MMConstants.OrderDetailStatus.ORDER_DETAIL_STATUS_CODE ,
				MMConstants.OrderDetailStatus.ORDER_DETAIL_STATUS_LABEL);
	}

}
