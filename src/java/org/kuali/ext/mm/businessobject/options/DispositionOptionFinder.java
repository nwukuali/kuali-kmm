package org.kuali.ext.mm.businessobject.options;

import org.kuali.ext.mm.businessobject.DispositionCode;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.krad.valuefinder.ValueFinder;


public class DispositionOptionFinder extends MMKeyValuesBase implements ValueFinder {

    public DispositionOptionFinder(){
        super(DispositionCode.class,MMConstants.DispositionCode.DISPOSITION_CODE ,
                MMConstants.DispositionCode.DISPOSITION_CODE_NAME);
    }
}
