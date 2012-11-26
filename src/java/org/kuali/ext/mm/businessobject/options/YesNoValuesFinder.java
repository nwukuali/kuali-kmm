package org.kuali.ext.mm.businessobject.options;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unchecked")
public class YesNoValuesFinder extends KeyValuesBase {

    /*
     * @see org.kuali.keyvalues.KeyValuesFinder#getKeyValues()
     */
    public List getKeyValues() {
        List keyValues = new ArrayList();
        keyValues.add(new ConcreteKeyValue(
            MMConstants.OptionFinderParms.YES_OR_NO_OPTION_VALUE_NO_OR_FALSE,
            MMConstants.OptionFinderParms.YES_OR_NO_OPTION_VALUE_NO_OR_FALSE));
        keyValues.add(new ConcreteKeyValue(
            MMConstants.OptionFinderParms.YES_OR_NO_OPTION_VALUE_YES_OR_TRUE,
            MMConstants.OptionFinderParms.YES_OR_NO_OPTION_VALUE_YES_OR_TRUE));
        return keyValues;
    }

    public String getValue() {
        return MMConstants.OptionFinderParms.YES_OR_NO_OPTION_VALUE_NO_OR_FALSE;
    }

}
