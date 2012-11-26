/*
 * Copyright 2007-2008 The Kuali Foundation
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
package org.kuali.ext.mm.integration.sys.businessobject;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.bo.TransientBusinessObjectBase;

import java.util.LinkedHashMap;

/**
 * Unit Of Measure Business Object.
 */
public class FinancialUnitOfMeasure extends TransientBusinessObjectBase implements
	ExternalizableBusinessObject, FinancialSystemComponent {
    private static final long serialVersionUID = 2318408801326832458L;
    private String itemUnitOfMeasureCode;
    private String itemUnitOfMeasureDescription;
    private boolean active;

    /**
     * Default constructor.
     */
    public FinancialUnitOfMeasure() {

    }

    public String getItemUnitOfMeasureCode() {
        return itemUnitOfMeasureCode;
    }

    public void setItemUnitOfMeasureCode(String itemUnitOfMeasureCode) {
        this.itemUnitOfMeasureCode = itemUnitOfMeasureCode;
    }

    public String getItemUnitOfMeasureDescription() {
        return itemUnitOfMeasureDescription;
    }

    public void setItemUnitOfMeasureDescription(String itemUnitOfMeasureDescription) {
        this.itemUnitOfMeasureDescription = itemUnitOfMeasureDescription;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    protected LinkedHashMap toStringMapper() {
        LinkedHashMap m = new LinkedHashMap();
        m.put("itemUnitOfMeasureCode", this.itemUnitOfMeasureCode);
        return m;
    }

}
