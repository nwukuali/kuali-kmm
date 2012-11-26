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
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.bo.TransientBusinessObjectBase;

/**
 */
public class FinancialVendorAddress extends TransientBusinessObjectBase implements
	ExternalizableBusinessObject, FinancialSystemComponent {
    /**
     *
     */
    private static final long serialVersionUID = -8496103681912094610L;
    private Integer vendorAddressGeneratedIdentifier;
    private Integer vendorHeaderGeneratedIdentifier;
    private Integer vendorDetailAssignedIdentifier;
    private String vendorAddressTypeCode;
    private String vendorLine1Address;
    private String vendorLine2Address;
    private String vendorCityName;
    private String vendorStateCode;
    private String vendorZipCode;
    private String vendorCountryCode;
    private String vendorAttentionName;
    private String vendorAddressInternationalProvinceName;
    private String vendorAddressEmailAddress;
    private String vendorBusinessToBusinessUrlAddress;
    private String vendorFaxNumber;
    private boolean vendorDefaultAddressIndicator;
    private boolean active;

    /**
     * Gets the vendorAddressGeneratedIdentifier property
     *
     * @return Returns the vendorAddressGeneratedIdentifier
     */
    public Integer getVendorAddressGeneratedIdentifier() {
        return this.vendorAddressGeneratedIdentifier;
    }

    /**
     * Sets the vendorAddressGeneratedIdentifier property value
     *
     * @param vendorAddressGeneratedIdentifier The vendorAddressGeneratedIdentifier to set
     */
    public void setVendorAddressGeneratedIdentifier(Integer vendorAddressGeneratedIdentifier) {
        this.vendorAddressGeneratedIdentifier = vendorAddressGeneratedIdentifier;
    }

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
     * Gets the vendorAddressTypeCode property
     *
     * @return Returns the vendorAddressTypeCode
     */
    public String getVendorAddressTypeCode() {
        return this.vendorAddressTypeCode;
    }

    /**
     * Sets the vendorAddressTypeCode property value
     *
     * @param vendorAddressTypeCode The vendorAddressTypeCode to set
     */
    public void setVendorAddressTypeCode(String vendorAddressTypeCode) {
        this.vendorAddressTypeCode = vendorAddressTypeCode;
    }

    /**
     * Gets the vendorLine1Address property
     *
     * @return Returns the vendorLine1Address
     */
    public String getVendorLine1Address() {
        return this.vendorLine1Address;
    }

    /**
     * Sets the vendorLine1Address property value
     *
     * @param vendorLine1Address The vendorLine1Address to set
     */
    public void setVendorLine1Address(String vendorLine1Address) {
        this.vendorLine1Address = vendorLine1Address;
    }

    /**
     * Gets the vendorLine2Address property
     *
     * @return Returns the vendorLine2Address
     */
    public String getVendorLine2Address() {
        return this.vendorLine2Address;
    }

    /**
     * Sets the vendorLine2Address property value
     *
     * @param vendorLine2Address The vendorLine2Address to set
     */
    public void setVendorLine2Address(String vendorLine2Address) {
        this.vendorLine2Address = vendorLine2Address;
    }

    /**
     * Gets the vendorCityName property
     *
     * @return Returns the vendorCityName
     */
    public String getVendorCityName() {
        return this.vendorCityName;
    }

    /**
     * Sets the vendorCityName property value
     *
     * @param vendorCityName The vendorCityName to set
     */
    public void setVendorCityName(String vendorCityName) {
        this.vendorCityName = vendorCityName;
    }

    /**
     * Gets the vendorStateCode property
     *
     * @return Returns the vendorStateCode
     */
    public String getVendorStateCode() {
        return this.vendorStateCode;
    }

    /**
     * Sets the vendorStateCode property value
     *
     * @param vendorStateCode The vendorStateCode to set
     */
    public void setVendorStateCode(String vendorStateCode) {
        this.vendorStateCode = vendorStateCode;
    }

    /**
     * Gets the vendorZipCode property
     *
     * @return Returns the vendorZipCode
     */
    public String getVendorZipCode() {
        return this.vendorZipCode;
    }

    /**
     * Sets the vendorZipCode property value
     *
     * @param vendorZipCode The vendorZipCode to set
     */
    public void setVendorZipCode(String vendorZipCode) {
        this.vendorZipCode = vendorZipCode;
    }

    /**
     * Gets the vendorCountryCode property
     *
     * @return Returns the vendorCountryCode
     */
    public String getVendorCountryCode() {
        return this.vendorCountryCode;
    }

    /**
     * Sets the vendorCountryCode property value
     *
     * @param vendorCountryCode The vendorCountryCode to set
     */
    public void setVendorCountryCode(String vendorCountryCode) {
        this.vendorCountryCode = vendorCountryCode;
    }

    /**
     * Gets the vendorAttentionName property
     *
     * @return Returns the vendorAttentionName
     */
    public String getVendorAttentionName() {
        return this.vendorAttentionName;
    }

    /**
     * Sets the vendorAttentionName property value
     *
     * @param vendorAttentionName The vendorAttentionName to set
     */
    public void setVendorAttentionName(String vendorAttentionName) {
        this.vendorAttentionName = vendorAttentionName;
    }

    /**
     * Gets the vendorAddressInternationalProvinceName property
     *
     * @return Returns the vendorAddressInternationalProvinceName
     */
    public String getVendorAddressInternationalProvinceName() {
        return this.vendorAddressInternationalProvinceName;
    }

    /**
     * Sets the vendorAddressInternationalProvinceName property value
     *
     * @param vendorAddressInternationalProvinceName The vendorAddressInternationalProvinceName to set
     */
    public void setVendorAddressInternationalProvinceName(
            String vendorAddressInternationalProvinceName) {
        this.vendorAddressInternationalProvinceName = vendorAddressInternationalProvinceName;
    }

    /**
     * Gets the vendorAddressEmailAddress property
     *
     * @return Returns the vendorAddressEmailAddress
     */
    public String getVendorAddressEmailAddress() {
        return this.vendorAddressEmailAddress;
    }

    /**
     * Sets the vendorAddressEmailAddress property value
     *
     * @param vendorAddressEmailAddress The vendorAddressEmailAddress to set
     */
    public void setVendorAddressEmailAddress(String vendorAddressEmailAddress) {
        this.vendorAddressEmailAddress = vendorAddressEmailAddress;
    }

    /**
     * Gets the vendorBusinessToBusinessUrlAddress property
     *
     * @return Returns the vendorBusinessToBusinessUrlAddress
     */
    public String getVendorBusinessToBusinessUrlAddress() {
        return this.vendorBusinessToBusinessUrlAddress;
    }

    /**
     * Sets the vendorBusinessToBusinessUrlAddress property value
     *
     * @param vendorBusinessToBusinessUrlAddress The vendorBusinessToBusinessUrlAddress to set
     */
    public void setVendorBusinessToBusinessUrlAddress(String vendorBusinessToBusinessUrlAddress) {
        this.vendorBusinessToBusinessUrlAddress = vendorBusinessToBusinessUrlAddress;
    }

    /**
     * Gets the vendorFaxNumber property
     *
     * @return Returns the vendorFaxNumber
     */
    public String getVendorFaxNumber() {
        return this.vendorFaxNumber;
    }

    /**
     * Sets the vendorFaxNumber property value
     *
     * @param vendorFaxNumber The vendorFaxNumber to set
     */
    public void setVendorFaxNumber(String vendorFaxNumber) {
        this.vendorFaxNumber = vendorFaxNumber;
    }

    /**
     * Gets the vendorDefaultAddressIndicator property
     *
     * @return Returns the vendorDefaultAddressIndicator
     */
    public boolean isVendorDefaultAddressIndicator() {
        return this.vendorDefaultAddressIndicator;
    }

    /**
     * Sets the vendorDefaultAddressIndicator property value
     *
     * @param vendorDefaultAddressIndicator The vendorDefaultAddressIndicator to set
     */
    public void setVendorDefaultAddressIndicator(boolean vendorDefaultAddressIndicator) {
        this.vendorDefaultAddressIndicator = vendorDefaultAddressIndicator;
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


}
