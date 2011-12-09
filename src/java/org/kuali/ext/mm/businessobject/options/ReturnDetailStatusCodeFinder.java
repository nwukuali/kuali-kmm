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

import java.util.HashMap;
import java.util.Map;

import org.kuali.ext.mm.businessobject.ReturnStatusCode;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.valueFinder.ValueFinder;


@SuppressWarnings("unchecked")
public class ReturnDetailStatusCodeFinder extends MMKeyValuesBase implements ValueFinder {

    /**
     * The constructor should pass the class of the object to be taken in to account for key and value generation and the getter
     * attributes for key and value
     */
    private static final Map paramVal = new HashMap();
    static {
        paramVal.put("customerVendorReturnInd", "C");
    }

    public ReturnDetailStatusCodeFinder() {

        super(ReturnStatusCode.class, MMConstants.ReturnStatusCode.RETURN_STATUS_CODE,
            MMConstants.ReturnStatusCode.RETURN_STATUS_LABEL, paramVal);
    }
}
