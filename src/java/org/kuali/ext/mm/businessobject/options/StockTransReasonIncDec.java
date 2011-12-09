package org.kuali.ext.mm.businessobject.options;

import java.util.ArrayList;
import java.util.List;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;


public class StockTransReasonIncDec extends KeyValuesBase {

	public List<KeyLabelPair> getKeyValues() {
		List<KeyLabelPair> newLabels = new ArrayList<KeyLabelPair>();
		KeyLabelPair kP1 = new KeyLabelPair(MMConstants.StockTransReason.INCREMENT, "Increment Quantity");
		KeyLabelPair kP2 = new KeyLabelPair(MMConstants.StockTransReason.DECREMENT, "Decrement Quantity");
		newLabels.add(kP1);
		newLabels.add(kP2);
		return newLabels;
	}


}
