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

package org.kuali.ext.mm.integration.cam.businessobject;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.bo.TransientBusinessObjectBase;

/**
 * @author harsha07
 */
public class FinancialAssetType extends TransientBusinessObjectBase implements
	ExternalizableBusinessObject, FinancialSystemComponent {
    private static final long serialVersionUID = 448909253709319918L;
    private String capitalAssetTypeCode;
    private String capitalAssetTypeDescription;

    /**
     * Gets the capitalAssetTypeCode property
     *
     * @return Returns the capitalAssetTypeCode
     */
    public String getCapitalAssetTypeCode() {
        return this.capitalAssetTypeCode;
    }

    /**
     * Sets the capitalAssetTypeCode property value
     *
     * @param capitalAssetTypeCode The capitalAssetTypeCode to set
     */
    public void setCapitalAssetTypeCode(String capitalAssetTypeCode) {
        this.capitalAssetTypeCode = capitalAssetTypeCode;
    }

    /**
     * Gets the capitalAssetTypeDescription property
     *
     * @return Returns the capitalAssetTypeDescription
     */
    public String getCapitalAssetTypeDescription() {
        return this.capitalAssetTypeDescription;
    }

    /**
     * Sets the capitalAssetTypeDescription property value
     *
     * @param capitalAssetTypeDescription The capitalAssetTypeDescription to set
     */
    public void setCapitalAssetTypeDescription(String capitalAssetTypeDescription) {
        this.capitalAssetTypeDescription = capitalAssetTypeDescription;
    }

}
