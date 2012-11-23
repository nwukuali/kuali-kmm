/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class returns the list of payment medium value pairs.
 */
public class OrderFrequencyValuesFinder extends KeyValuesBase {

	  /**
     * Returns a list of key/value pairs for this ValueFinder, in this case no pending entries, approved pending entries, and all pending entries
     * @return a List of key/value pairs to populate a control
     * @see org.kuali.rice.kns.lookup.keyvalues.KeyValuesFinder#getKeyValues()
     */
    public List<KeyValue> getKeyValues() {

        List<KeyValue> labels = new ArrayList<KeyValue>();
        labels.add(new ConcreteKeyValue(MMConstants.RecurringOrder.OPTION_NONE.toString(), MMConstants.RecurringOrder.OPTION_LABEL_NONE));
        labels.add(new ConcreteKeyValue(MMConstants.RecurringOrder.OPTION_DAILY.toString(), MMConstants.RecurringOrder.OPTION_LABEL_DAILY));
        labels.add(new ConcreteKeyValue(MMConstants.RecurringOrder.OPTION_WEEKLY.toString(), MMConstants.RecurringOrder.OPTION_LABEL_WEEKLY));
        labels.add(new ConcreteKeyValue(MMConstants.RecurringOrder.OPTION_BI_WEEKLY.toString(), MMConstants.RecurringOrder.OPTION_LABEL_BI_WEEKLY));
        labels.add(new ConcreteKeyValue(MMConstants.RecurringOrder.OPTION_MONTHLY.toString(), MMConstants.RecurringOrder.OPTION_LABEL_MONTHLY));
        labels.add(new ConcreteKeyValue(MMConstants.RecurringOrder.OPTION_QUARTERLY.toString(), MMConstants.RecurringOrder.OPTION_LABEL_QUARTERLY));
        labels.add(new ConcreteKeyValue(MMConstants.RecurringOrder.OPTION_SEMI_ANNUALLY.toString(), MMConstants.RecurringOrder.OPTION_LABEL_SEMI_ANNUALLY));
        labels.add(new ConcreteKeyValue(MMConstants.RecurringOrder.OPTION_YEARLY.toString(), MMConstants.RecurringOrder.OPTION_LABEL_YEARLY));
        
        return labels;
    }

    /**
     * Returns the default value for this ValueFinder
     * @return the key of the default value
     * @see org.kuali.rice.kns.lookup.valueFinder.ValueFinder#getValue()
     */
    public String getValue() {
        return null;
    }


}
