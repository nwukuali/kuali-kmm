package org.kuali.ext.mm.businessobject.options;

import java.util.ArrayList;
import java.util.List;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;


@SuppressWarnings("unchecked")
public class YesNoValuesFinder extends KeyValuesBase {

    /*
     * @see org.kuali.keyvalues.KeyValuesFinder#getKeyValues()
     */
    public List getKeyValues() {
        List keyValues = new ArrayList();
        keyValues.add(new KeyLabelPair(
            MMConstants.OptionFinderParms.YES_OR_NO_OPTION_VALUE_NO_OR_FALSE,
            MMConstants.OptionFinderParms.YES_OR_NO_OPTION_VALUE_NO_OR_FALSE));
        keyValues.add(new KeyLabelPair(
            MMConstants.OptionFinderParms.YES_OR_NO_OPTION_VALUE_YES_OR_TRUE,
            MMConstants.OptionFinderParms.YES_OR_NO_OPTION_VALUE_YES_OR_TRUE));
        return keyValues;
    }

    public String getValue() {
        return MMConstants.OptionFinderParms.YES_OR_NO_OPTION_VALUE_NO_OR_FALSE;
    }

}
