/*
 * Copyright 2011 The Kuali Foundation
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

package org.kuali.ext.mm.b2b.cxml.invoice;

import java.io.Serializable;

import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMDecimal;

/**
 * @author harsha07
 */
public class B2bInvoiceAccount implements Serializable{    
    private static final long serialVersionUID = -8161001493833824789L;
    private MMDecimal chargeAmount;
    private String chartCode;
    private String accountNumber;
    private String id;

    /**
     * Gets the chargeAmount property
     * 
     * @return Returns the chargeAmount
     */
    public MMDecimal getChargeAmount() {
        return this.chargeAmount;
    }

    /**
     * Sets the chargeAmount property value
     * 
     * @param chargeAmount The chargeAmount to set
     */
    public void setChargeAmount(MMDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    /**
     * Gets the chartCode property
     * 
     * @return Returns the chartCode
     */
    public String getChartCode() {
        return this.chartCode;
    }

    /**
     * Sets the chartCode property value
     * 
     * @param chartCode The chartCode to set
     */
    public void setChartCode(String chartCode) {
        this.chartCode = chartCode;
    }

    /**
     * Gets the accountNumber property
     * 
     * @return Returns the accountNumber
     */
    public String getAccountNumber() {
        return this.accountNumber;
    }

    /**
     * Sets the accountNumber property value
     * 
     * @param accountNumber The accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Gets the description property
     * 
     * @return Returns the description
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the description property value
     * 
     * @param description The description to set
     */
    public void setId(String description) {
        this.id = description;
    }

    public Accounts matchingAccount() {
        return MMServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Accounts.class,
                getId());
    }


}
