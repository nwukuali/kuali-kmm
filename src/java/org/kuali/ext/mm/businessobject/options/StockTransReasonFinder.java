package org.kuali.ext.mm.businessobject.options;

import org.kuali.ext.mm.businessobject.StockTransReason;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.valueFinder.ValueFinder;


public class StockTransReasonFinder extends MMKeyValuesBase implements ValueFinder {
	public StockTransReasonFinder(){
		super(StockTransReason.class, MMConstants.StockTransReason.STOCK_TRANS_REASON_CODE ,
				 MMConstants.StockTransReason.STOCK_TRANS_REASON_LABEL);
	}
}
