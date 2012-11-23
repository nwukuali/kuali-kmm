/*
 * Copyright 2006-2007 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.businessobject.options;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.valuefinder.ValueFinder;

import java.util.ArrayList;
import java.util.List;


/**
 * An implementation of ValueFinder that allows inquirers to include no pending entries, approved pending entries,
 * or all pending entries in the results of their lookup
 */
public class PickListOptionFinder extends KeyValuesBase implements ValueFinder {

    /**
     * Returns a list of key/value pairs for this ValueFinder, in this case no pending entries, approved pending entries, and all pending entries
     * @return a List of key/value pairs to populate a control
     * @see org.kuali.rice.kns.lookup.keyvalues.KeyValuesFinder#getKeyValues()
     */
    public List<KeyValue> getKeyValues() {

        List<KeyValue> labels = new ArrayList<KeyValue>();
        labels.add(new ConcreteKeyValue(MMConstants.PickListDocument.OPTION_ORDERS, MMConstants.PickListDocument.OPTION_ORDERS));
        labels.add(new ConcreteKeyValue(MMConstants.PickListDocument.OPTION_ZONES, MMConstants.PickListDocument.OPTION_ZONES));
        labels.add(new ConcreteKeyValue(MMConstants.PickListDocument.OPTION_SINGLE_LIST, MMConstants.PickListDocument.OPTION_SINGLE_LIST));
        return labels;
    }

    /**
     * Returns the default value for this ValueFinder, in this case ORDER
     * @return the key of the default value
     * @see org.kuali.rice.kns.lookup.valueFinder.ValueFinder#getValue()
     */
    public String getValue() {
        return MMConstants.PickListDocument.OPTION_ORDERS;
    }
}
