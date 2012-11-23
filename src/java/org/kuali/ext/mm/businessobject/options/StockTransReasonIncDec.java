package org.kuali.ext.mm.businessobject.options;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;


public class StockTransReasonIncDec extends KeyValuesBase {

	public List<KeyValue> getKeyValues() {
		List<KeyValue> newLabels = new ArrayList<KeyValue>();
		KeyValue kP1 = new ConcreteKeyValue(MMConstants.StockTransReason.INCREMENT, "Increment Quantity");
		KeyValue kP2 = new ConcreteKeyValue(MMConstants.StockTransReason.DECREMENT, "Decrement Quantity");
		newLabels.add(kP1);
		newLabels.add(kP2);
		return newLabels;
	}


}
