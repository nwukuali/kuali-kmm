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
public class RentalStatusValuesFinder extends KeyValuesBase {

	  /**
     * Returns a list of key/value pairs for this ValueFinder
     * @return a List of key/value pairs to populate a control
     * @see org.kuali.rice.kns.lookup.keyvalues.KeyValuesFinder#getKeyValues()
     */
    public List<KeyValue> getKeyValues() {

        List<KeyValue> labels = new ArrayList<KeyValue>();
        labels.add(new ConcreteKeyValue(MMConstants.Rental.RENTAL_STATUS_AVAILABLE, MMConstants.Rental.OPTION_LABEL_AVAILABLE));
        labels.add(new ConcreteKeyValue(MMConstants.Rental.RENTAL_STATUS_ISSUED, MMConstants.Rental.OPTION__LABEL_ISSUED));
        labels.add(new ConcreteKeyValue(MMConstants.Rental.RENTAL_STATUS_RETURNED, MMConstants.Rental.OPTION__LABEL_RETURNED));
        return labels;
    }

    /**
     * Returns the default value for this ValueFinder
     * @return the key of the default value
     * @see org.kuali.rice.kns.lookup.valueFinder.ValueFinder#getValue()
     */
    public String getValue() {
        return MMConstants.Rental.RENTAL_STATUS_AVAILABLE;
    }


}
