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

package org.kuali.ext.mm.integration.coa.businessobject;

import java.util.LinkedHashMap;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.kns.bo.ExternalizableBusinessObject;
import org.kuali.rice.kns.bo.TransientBusinessObjectBase;

public class FinancialProjectCode extends TransientBusinessObjectBase implements
        ExternalizableBusinessObject, FinancialSystemComponent {

    private static final long serialVersionUID = 4948895196195929372L;
    private String code;
    private String name;
    private boolean active;


    /**
     * Gets the code property
     *
     * @return Returns the code
     */
    public String getCode() {
        return this.code;
    }


    /**
     * Sets the code property value
     *
     * @param code The code to set
     */
    public void setCode(String code) {
        this.code = code;
    }


    /**
     * Gets the name property
     *
     * @return Returns the name
     */
    public String getName() {
        return this.name;
    }


    /**
     * Sets the name property value
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets the active property
     *
     * @return Returns the active
     */
    public boolean isActive() {
        return this.active;
    }


    /**
     * Sets the active property value
     *
     * @param active The active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }


    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    @Override
    protected LinkedHashMap<String, Object> toStringMapper() {
        return null;
    }

}
