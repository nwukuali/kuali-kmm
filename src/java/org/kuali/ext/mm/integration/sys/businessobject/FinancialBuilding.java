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

package org.kuali.ext.mm.integration.sys.businessobject;

import java.util.LinkedHashMap;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.kns.bo.ExternalizableBusinessObject;
import org.kuali.rice.kns.bo.TransientBusinessObjectBase;

/**
 * Financial Building object
 */
public class FinancialBuilding extends TransientBusinessObjectBase implements
        ExternalizableBusinessObject, FinancialSystemComponent {

    private static final long serialVersionUID = 8421886012779840412L;
    private String campusCode;
    private String buildingCode;
    private String buildingName;
    private String buildingStreetAddress;
    private String buildingAddressCityName;
    private String buildingAddressStateCode;
    private String buildingAddressZipCode;
    private String alternateBuildingCode;
    private boolean active;
    private String buildingAddressCountryCode;


    /**
     * Gets the campusCode property
     *
     * @return Returns the campusCode
     */
    public String getCampusCode() {
        return campusCode;
    }

    /**
     * Sets the campusCode property value
     *
     * @param campusCode The campusCode to set
     */
    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    /**
     * Gets the buildingCode property
     *
     * @return Returns the buildingCode
     */
    public String getBuildingCode() {
        return buildingCode;
    }

    /**
     * Sets the buildingCode property value
     *
     * @param buildingCode The buildingCode to set
     */
    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    /**
     * Gets the buildingName property
     *
     * @return Returns the buildingName
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * Sets the buildingName property value
     *
     * @param buildingName The buildingName to set
     */
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /**
     * Gets the buildingStreetAddress property
     *
     * @return Returns the buildingStreetAddress
     */
    public String getBuildingStreetAddress() {
        return buildingStreetAddress;
    }

    /**
     * Sets the buildingStreetAddress property value
     *
     * @param buildingStreetAddress The buildingStreetAddress to set
     */
    public void setBuildingStreetAddress(String buildingStreetAddress) {
        this.buildingStreetAddress = buildingStreetAddress;
    }

    /**
     * Gets the buildingAddressCityName property
     *
     * @return Returns the buildingAddressCityName
     */
    public String getBuildingAddressCityName() {
        return buildingAddressCityName;
    }

    /**
     * Sets the buildingAddressCityName property value
     *
     * @param buildingAddressCityName The buildingAddressCityName to set
     */
    public void setBuildingAddressCityName(String buildingAddressCityName) {
        this.buildingAddressCityName = buildingAddressCityName;
    }

    /**
     * Gets the buildingAddressStateCode property
     *
     * @return Returns the buildingAddressStateCode
     */
    public String getBuildingAddressStateCode() {
        return buildingAddressStateCode;
    }

    /**
     * Sets the buildingAddressStateCode property value
     *
     * @param buildingAddressStateCode The buildingAddressStateCode to set
     */
    public void setBuildingAddressStateCode(String buildingAddressStateCode) {
        this.buildingAddressStateCode = buildingAddressStateCode;
    }

    /**
     * Gets the buildingAddressZipCode property
     *
     * @return Returns the buildingAddressZipCode
     */
    public String getBuildingAddressZipCode() {
        return buildingAddressZipCode;
    }

    /**
     * Sets the buildingAddressZipCode property value
     *
     * @param buildingAddressZipCode The buildingAddressZipCode to set
     */
    public void setBuildingAddressZipCode(String buildingAddressZipCode) {
        this.buildingAddressZipCode = buildingAddressZipCode;
    }

    /**
     * Gets the alternateBuildingCode property
     *
     * @return Returns the alternateBuildingCode
     */
    public String getAlternateBuildingCode() {
        return alternateBuildingCode;
    }

    /**
     * Sets the alternateBuildingCode property value
     *
     * @param alternateBuildingCode The alternateBuildingCode to set
     */
    public void setAlternateBuildingCode(String alternateBuildingCode) {
        this.alternateBuildingCode = alternateBuildingCode;
    }

    /**
     * Gets the active property
     *
     * @return Returns the active
     */
    public boolean isActive() {
        return active;
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
     * Gets the buildingAddressCountryCode property
     *
     * @return Returns the buildingAddressCountryCode
     */
    public String getBuildingAddressCountryCode() {
        return buildingAddressCountryCode;
    }

    /**
     * Sets the buildingAddressCountryCode property value
     *
     * @param buildingAddressCountryCode The buildingAddressCountryCode to set
     */
    public void setBuildingAddressCountryCode(String buildingAddressCountryCode) {
        this.buildingAddressCountryCode = buildingAddressCountryCode;
    }

    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    @Override
    protected LinkedHashMap<String, Object> toStringMapper() {
        return null;
    }

}
