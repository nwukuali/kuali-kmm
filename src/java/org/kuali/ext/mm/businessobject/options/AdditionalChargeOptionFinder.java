package org.kuali.ext.mm.businessobject.options;

import org.kuali.ext.mm.businessobject.AdditionalCostType;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.valueFinder.ValueFinder;


public class AdditionalChargeOptionFinder extends MMKeyValuesBase implements ValueFinder {

    public AdditionalChargeOptionFinder(){
        super(AdditionalCostType.class,MMConstants.AdditionalCostType.ADDITIONAL_COST_TYPE_CODE ,
                MMConstants.AdditionalCostType.ADDITIONAL_COST_TYPE_NAME);
    }
}
