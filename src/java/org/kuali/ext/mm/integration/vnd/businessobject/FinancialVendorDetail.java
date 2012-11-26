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

package org.kuali.ext.mm.integration.vnd.businessobject;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.bo.TransientBusinessObjectBase;

import java.sql.Date;

/**
 * @author harsha07
 */
public class FinancialVendorDetail extends TransientBusinessObjectBase implements
	ExternalizableBusinessObject, FinancialSystemComponent {
    /**
     *
     */
    private static final long serialVersionUID = 3267522185542413944L;
    private Integer vendorHeaderGeneratedIdentifier;
    private Integer vendorDetailAssignedIdentifier;
    private String vendorNumber; // not persisted in the db
    private boolean vendorParentIndicator;
    private String vendorName;
    private String vendorFirstName; // not persisted in the db
    private String vendorLastName; // not persisted in the db
    private String vendorStateForLookup; // not persisted in the db

    private boolean activeIndicator;
    private String vendorInactiveReasonCode;
    private String vendorDunsNumber;
    private String vendorPaymentTermsCode;
    private String vendorShippingTitleCode;
    private String vendorShippingPaymentTermsCode;
    private Boolean vendorConfirmationIndicator;
    private Boolean vendorPrepaymentIndicator;
    private Boolean vendorCreditCardIndicator;
    private KualiDecimal vendorMinimumOrderAmount;
    private String vendorUrlAddress;
    private String vendorRemitName;
    private Boolean vendorRestrictedIndicator;
    private String vendorRestrictedReasonText;
    private Date vendorRestrictedDate;
    private String vendorRestrictedPersonIdentifier;
    private String vendorSoldToNumber; // not persisted in the db
    private Integer vendorSoldToGeneratedIdentifier;
    private Integer vendorSoldToAssignedIdentifier;
    private String vendorSoldToName;
    private boolean vendorFirstLastNameIndicator;
    private boolean taxableIndicator;

    /**
     * Gets the vendorHeaderGeneratedIdentifier property
     *
     * @return Returns the vendorHeaderGeneratedIdentifier
     */
    public Integer getVendorHeaderGeneratedIdentifier() {
        return this.vendorHeaderGeneratedIdentifier;
    }

    /**
     * Sets the vendorHeaderGeneratedIdentifier property value
     *
     * @param vendorHeaderGeneratedIdentifier The vendorHeaderGeneratedIdentifier to set
     */
    public void setVendorHeaderGeneratedIdentifier(Integer vendorHeaderGeneratedIdentifier) {
        this.vendorHeaderGeneratedIdentifier = vendorHeaderGeneratedIdentifier;
    }

    /**
     * Gets the vendorDetailAssignedIdentifier property
     *
     * @return Returns the vendorDetailAssignedIdentifier
     */
    public Integer getVendorDetailAssignedIdentifier() {
        return this.vendorDetailAssignedIdentifier;
    }

    /**
     * Sets the vendorDetailAssignedIdentifier property value
     *
     * @param vendorDetailAssignedIdentifier The vendorDetailAssignedIdentifier to set
     */
    public void setVendorDetailAssignedIdentifier(Integer vendorDetailAssignedIdentifier) {
        this.vendorDetailAssignedIdentifier = vendorDetailAssignedIdentifier;
    }

    /**
     * Gets the vendorNumber property
     *
     * @return Returns the vendorNumber
     */
    public String getVendorNumber() {
        return this.vendorNumber;
    }

    /**
     * Sets the vendorNumber property value
     *
     * @param vendorNumber The vendorNumber to set
     */
    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }

    /**
     * Gets the vendorParentIndicator property
     *
     * @return Returns the vendorParentIndicator
     */
    public boolean isVendorParentIndicator() {
        return this.vendorParentIndicator;
    }

    /**
     * Sets the vendorParentIndicator property value
     *
     * @param vendorParentIndicator The vendorParentIndicator to set
     */
    public void setVendorParentIndicator(boolean vendorParentIndicator) {
        this.vendorParentIndicator = vendorParentIndicator;
    }

    /**
     * Gets the vendorName property
     *
     * @return Returns the vendorName
     */
    public String getVendorName() {
        return this.vendorName;
    }

    /**
     * Sets the vendorName property value
     *
     * @param vendorName The vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * Gets the vendorFirstName property
     *
     * @return Returns the vendorFirstName
     */
    public String getVendorFirstName() {
        return this.vendorFirstName;
    }

    /**
     * Sets the vendorFirstName property value
     *
     * @param vendorFirstName The vendorFirstName to set
     */
    public void setVendorFirstName(String vendorFirstName) {
        this.vendorFirstName = vendorFirstName;
    }

    /**
     * Gets the vendorLastName property
     *
     * @return Returns the vendorLastName
     */
    public String getVendorLastName() {
        return this.vendorLastName;
    }

    /**
     * Sets the vendorLastName property value
     *
     * @param vendorLastName The vendorLastName to set
     */
    public void setVendorLastName(String vendorLastName) {
        this.vendorLastName = vendorLastName;
    }

    /**
     * Gets the vendorStateForLookup property
     *
     * @return Returns the vendorStateForLookup
     */
    public String getVendorStateForLookup() {
        return this.vendorStateForLookup;
    }

    /**
     * Sets the vendorStateForLookup property value
     *
     * @param vendorStateForLookup The vendorStateForLookup to set
     */
    public void setVendorStateForLookup(String vendorStateForLookup) {
        this.vendorStateForLookup = vendorStateForLookup;
    }

    /**
     * Gets the activeIndicator property
     *
     * @return Returns the activeIndicator
     */
    public boolean isActiveIndicator() {
        return this.activeIndicator;
    }

    /**
     * Sets the activeIndicator property value
     *
     * @param activeIndicator The activeIndicator to set
     */
    public void setActiveIndicator(boolean activeIndicator) {
        this.activeIndicator = activeIndicator;
    }

    /**
     * Gets the vendorInactiveReasonCode property
     *
     * @return Returns the vendorInactiveReasonCode
     */
    public String getVendorInactiveReasonCode() {
        return this.vendorInactiveReasonCode;
    }

    /**
     * Sets the vendorInactiveReasonCode property value
     *
     * @param vendorInactiveReasonCode The vendorInactiveReasonCode to set
     */
    public void setVendorInactiveReasonCode(String vendorInactiveReasonCode) {
        this.vendorInactiveReasonCode = vendorInactiveReasonCode;
    }

    /**
     * Gets the vendorDunsNumber property
     *
     * @return Returns the vendorDunsNumber
     */
    public String getVendorDunsNumber() {
        return this.vendorDunsNumber;
    }

    /**
     * Sets the vendorDunsNumber property value
     *
     * @param vendorDunsNumber The vendorDunsNumber to set
     */
    public void setVendorDunsNumber(String vendorDunsNumber) {
        this.vendorDunsNumber = vendorDunsNumber;
    }

    /**
     * Gets the vendorPaymentTermsCode property
     *
     * @return Returns the vendorPaymentTermsCode
     */
    public String getVendorPaymentTermsCode() {
        return this.vendorPaymentTermsCode;
    }

    /**
     * Sets the vendorPaymentTermsCode property value
     *
     * @param vendorPaymentTermsCode The vendorPaymentTermsCode to set
     */
    public void setVendorPaymentTermsCode(String vendorPaymentTermsCode) {
        this.vendorPaymentTermsCode = vendorPaymentTermsCode;
    }

    /**
     * Gets the vendorShippingTitleCode property
     *
     * @return Returns the vendorShippingTitleCode
     */
    public String getVendorShippingTitleCode() {
        return this.vendorShippingTitleCode;
    }

    /**
     * Sets the vendorShippingTitleCode property value
     *
     * @param vendorShippingTitleCode The vendorShippingTitleCode to set
     */
    public void setVendorShippingTitleCode(String vendorShippingTitleCode) {
        this.vendorShippingTitleCode = vendorShippingTitleCode;
    }

    /**
     * Gets the vendorShippingPaymentTermsCode property
     *
     * @return Returns the vendorShippingPaymentTermsCode
     */
    public String getVendorShippingPaymentTermsCode() {
        return this.vendorShippingPaymentTermsCode;
    }

    /**
     * Sets the vendorShippingPaymentTermsCode property value
     *
     * @param vendorShippingPaymentTermsCode The vendorShippingPaymentTermsCode to set
     */
    public void setVendorShippingPaymentTermsCode(String vendorShippingPaymentTermsCode) {
        this.vendorShippingPaymentTermsCode = vendorShippingPaymentTermsCode;
    }

    /**
     * Gets the vendorConfirmationIndicator property
     *
     * @return Returns the vendorConfirmationIndicator
     */
    public Boolean getVendorConfirmationIndicator() {
        return this.vendorConfirmationIndicator;
    }

    /**
     * Sets the vendorConfirmationIndicator property value
     *
     * @param vendorConfirmationIndicator The vendorConfirmationIndicator to set
     */
    public void setVendorConfirmationIndicator(Boolean vendorConfirmationIndicator) {
        this.vendorConfirmationIndicator = vendorConfirmationIndicator;
    }

    /**
     * Gets the vendorPrepaymentIndicator property
     *
     * @return Returns the vendorPrepaymentIndicator
     */
    public Boolean getVendorPrepaymentIndicator() {
        return this.vendorPrepaymentIndicator;
    }

    /**
     * Sets the vendorPrepaymentIndicator property value
     *
     * @param vendorPrepaymentIndicator The vendorPrepaymentIndicator to set
     */
    public void setVendorPrepaymentIndicator(Boolean vendorPrepaymentIndicator) {
        this.vendorPrepaymentIndicator = vendorPrepaymentIndicator;
    }

    /**
     * Gets the vendorCreditCardIndicator property
     *
     * @return Returns the vendorCreditCardIndicator
     */
    public Boolean getVendorCreditCardIndicator() {
        return this.vendorCreditCardIndicator;
    }

    /**
     * Sets the vendorCreditCardIndicator property value
     *
     * @param vendorCreditCardIndicator The vendorCreditCardIndicator to set
     */
    public void setVendorCreditCardIndicator(Boolean vendorCreditCardIndicator) {
        this.vendorCreditCardIndicator = vendorCreditCardIndicator;
    }

    /**
     * Gets the vendorMinimumOrderAmount property
     *
     * @return Returns the vendorMinimumOrderAmount
     */
    public KualiDecimal getVendorMinimumOrderAmount() {
        return this.vendorMinimumOrderAmount;
    }

    /**
     * Sets the vendorMinimumOrderAmount property value
     *
     * @param vendorMinimumOrderAmount The vendorMinimumOrderAmount to set
     */
    public void setVendorMinimumOrderAmount(KualiDecimal vendorMinimumOrderAmount) {
        this.vendorMinimumOrderAmount = vendorMinimumOrderAmount;
    }

    /**
     * Gets the vendorUrlAddress property
     *
     * @return Returns the vendorUrlAddress
     */
    public String getVendorUrlAddress() {
        return this.vendorUrlAddress;
    }

    /**
     * Sets the vendorUrlAddress property value
     *
     * @param vendorUrlAddress The vendorUrlAddress to set
     */
    public void setVendorUrlAddress(String vendorUrlAddress) {
        this.vendorUrlAddress = vendorUrlAddress;
    }

    /**
     * Gets the vendorRemitName property
     *
     * @return Returns the vendorRemitName
     */
    public String getVendorRemitName() {
        return this.vendorRemitName;
    }

    /**
     * Sets the vendorRemitName property value
     *
     * @param vendorRemitName The vendorRemitName to set
     */
    public void setVendorRemitName(String vendorRemitName) {
        this.vendorRemitName = vendorRemitName;
    }

    /**
     * Gets the vendorRestrictedIndicator property
     *
     * @return Returns the vendorRestrictedIndicator
     */
    public Boolean getVendorRestrictedIndicator() {
        return this.vendorRestrictedIndicator;
    }

    /**
     * Sets the vendorRestrictedIndicator property value
     *
     * @param vendorRestrictedIndicator The vendorRestrictedIndicator to set
     */
    public void setVendorRestrictedIndicator(Boolean vendorRestrictedIndicator) {
        this.vendorRestrictedIndicator = vendorRestrictedIndicator;
    }

    /**
     * Gets the vendorRestrictedReasonText property
     *
     * @return Returns the vendorRestrictedReasonText
     */
    public String getVendorRestrictedReasonText() {
        return this.vendorRestrictedReasonText;
    }

    /**
     * Sets the vendorRestrictedReasonText property value
     *
     * @param vendorRestrictedReasonText The vendorRestrictedReasonText to set
     */
    public void setVendorRestrictedReasonText(String vendorRestrictedReasonText) {
        this.vendorRestrictedReasonText = vendorRestrictedReasonText;
    }

    /**
     * Gets the vendorRestrictedDate property
     *
     * @return Returns the vendorRestrictedDate
     */
    public Date getVendorRestrictedDate() {
        return this.vendorRestrictedDate;
    }

    /**
     * Sets the vendorRestrictedDate property value
     *
     * @param vendorRestrictedDate The vendorRestrictedDate to set
     */
    public void setVendorRestrictedDate(Date vendorRestrictedDate) {
        this.vendorRestrictedDate = vendorRestrictedDate;
    }

    /**
     * Gets the vendorRestrictedPersonIdentifier property
     *
     * @return Returns the vendorRestrictedPersonIdentifier
     */
    public String getVendorRestrictedPersonIdentifier() {
        return this.vendorRestrictedPersonIdentifier;
    }

    /**
     * Sets the vendorRestrictedPersonIdentifier property value
     *
     * @param vendorRestrictedPersonIdentifier The vendorRestrictedPersonIdentifier to set
     */
    public void setVendorRestrictedPersonIdentifier(String vendorRestrictedPersonIdentifier) {
        this.vendorRestrictedPersonIdentifier = vendorRestrictedPersonIdentifier;
    }

    /**
     * Gets the vendorSoldToNumber property
     *
     * @return Returns the vendorSoldToNumber
     */
    public String getVendorSoldToNumber() {
        return this.vendorSoldToNumber;
    }

    /**
     * Sets the vendorSoldToNumber property value
     *
     * @param vendorSoldToNumber The vendorSoldToNumber to set
     */
    public void setVendorSoldToNumber(String vendorSoldToNumber) {
        this.vendorSoldToNumber = vendorSoldToNumber;
    }

    /**
     * Gets the vendorSoldToGeneratedIdentifier property
     *
     * @return Returns the vendorSoldToGeneratedIdentifier
     */
    public Integer getVendorSoldToGeneratedIdentifier() {
        return this.vendorSoldToGeneratedIdentifier;
    }

    /**
     * Sets the vendorSoldToGeneratedIdentifier property value
     *
     * @param vendorSoldToGeneratedIdentifier The vendorSoldToGeneratedIdentifier to set
     */
    public void setVendorSoldToGeneratedIdentifier(Integer vendorSoldToGeneratedIdentifier) {
        this.vendorSoldToGeneratedIdentifier = vendorSoldToGeneratedIdentifier;
    }

    /**
     * Gets the vendorSoldToAssignedIdentifier property
     *
     * @return Returns the vendorSoldToAssignedIdentifier
     */
    public Integer getVendorSoldToAssignedIdentifier() {
        return this.vendorSoldToAssignedIdentifier;
    }

    /**
     * Sets the vendorSoldToAssignedIdentifier property value
     *
     * @param vendorSoldToAssignedIdentifier The vendorSoldToAssignedIdentifier to set
     */
    public void setVendorSoldToAssignedIdentifier(Integer vendorSoldToAssignedIdentifier) {
        this.vendorSoldToAssignedIdentifier = vendorSoldToAssignedIdentifier;
    }

    /**
     * Gets the vendorSoldToName property
     *
     * @return Returns the vendorSoldToName
     */
    public String getVendorSoldToName() {
        return this.vendorSoldToName;
    }

    /**
     * Sets the vendorSoldToName property value
     *
     * @param vendorSoldToName The vendorSoldToName to set
     */
    public void setVendorSoldToName(String vendorSoldToName) {
        this.vendorSoldToName = vendorSoldToName;
    }

    /**
     * Gets the vendorFirstLastNameIndicator property
     *
     * @return Returns the vendorFirstLastNameIndicator
     */
    public boolean isVendorFirstLastNameIndicator() {
        return this.vendorFirstLastNameIndicator;
    }

    /**
     * Sets the vendorFirstLastNameIndicator property value
     *
     * @param vendorFirstLastNameIndicator The vendorFirstLastNameIndicator to set
     */
    public void setVendorFirstLastNameIndicator(boolean vendorFirstLastNameIndicator) {
        this.vendorFirstLastNameIndicator = vendorFirstLastNameIndicator;
    }

    /**
     * Gets the taxableIndicator property
     *
     * @return Returns the taxableIndicator
     */
    public boolean isTaxableIndicator() {
        return this.taxableIndicator;
    }

    /**
     * Sets the taxableIndicator property value
     *
     * @param taxableIndicator The taxableIndicator to set
     */
    public void setTaxableIndicator(boolean taxableIndicator) {
        this.taxableIndicator = taxableIndicator;
    }


}
