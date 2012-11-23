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

import org.kuali.ext.mm.businessobject.UnitOfIssue;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.service.KeyValuesService;

import java.util.*;


public class UnitOfIssueFinder extends KeyValuesBase {
    public List getKeyValues() {
        KeyValuesService boService = SpringContext.getBean(KeyValuesService.class);
        Collection codes = boService.findAll(UnitOfIssue.class);
        List labels = new ArrayList();
        for (Object code : codes) {
            UnitOfIssue unitOfIssue = (UnitOfIssue) code;
            labels.add(new ConcreteKeyValue(unitOfIssue.getUnitOfIssueCode(), unitOfIssue
                    .getUnitOfIssueDesc()));
        }
        Collections.sort(labels, new Comparator<KeyValue>() {
            /**
             * Sort by label
             */
            public int compare(KeyValue o1, KeyValue o2) {
                if (o1.getValue() == null) {
                    return -1;
                }
                if (o2.getValue() == null) {
                    return 1;
                }
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        labels.add(0, new ConcreteKeyValue("",
            MMConstants.OptionFinderParms.OPTION_FINDER_DEFAULT_LABEL));
        return labels;
    }
}
