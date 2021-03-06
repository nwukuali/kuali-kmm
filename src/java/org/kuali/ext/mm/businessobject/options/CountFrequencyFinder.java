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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.kuali.ext.mm.businessobject.CycleCount;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.service.KeyValuesService;


public class CountFrequencyFinder extends KeyValuesBase {
    public List getKeyValues() {
        KeyValuesService boService = SpringContext.getBean(KeyValuesService.class);
        Collection codes = boService.findAll(CycleCount.class);
        List labels = new ArrayList();
        for (Object code : codes) {
            CycleCount cycleCount = (CycleCount) code;
            labels.add(new KeyLabelPair(cycleCount.getCycleCntCd(), cycleCount.getCycleCntDesc()));
        }
        Collections.sort(labels, new Comparator<KeyLabelPair>() {
            public int compare(KeyLabelPair o1, KeyLabelPair o2) {
                return o1.getLabel().compareTo(o2.getLabel());
            }
        });
        labels.add(0, new KeyLabelPair("",
            MMConstants.OptionFinderParms.OPTION_FINDER_DEFAULT_LABEL));
        return labels;
    }
}
