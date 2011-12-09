package org.kuali.ext.mm.businessobject.options;

import org.kuali.ext.mm.businessobject.ActionCode;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.valueFinder.ValueFinder;


public class ReturnActionCodeOptionFinder extends MMKeyValuesBase implements ValueFinder {

    public ReturnActionCodeOptionFinder(){
        super(ActionCode.class,//"actionCodeValue","actionName"
                MMConstants.ReturnActionCode.RETURN_ACTION_CODE ,
                MMConstants.ReturnActionCode.RETURN_ACTION_CODE_NAME
                );
    }
}
