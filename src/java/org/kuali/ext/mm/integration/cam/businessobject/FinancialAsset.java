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

import java.util.LinkedHashMap;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.kns.bo.ExternalizableBusinessObject;
import org.kuali.rice.kns.bo.TransientBusinessObjectBase;


/**
 * @author harsha07
 */
public class FinancialAsset extends TransientBusinessObjectBase implements
        ExternalizableBusinessObject, FinancialSystemComponent {
    private static final long serialVersionUID = 15089108169666997L;
    private Long capitalAssetNumber;
    private String capitalAssetDescription;
    private String capitalAssetTypeCode;
    private String vendorName;
    private String campusCode;
    private String buildingCode;
    private String buildingRoomNumber;
    private String buildingSubRoomNumber;
    private String campusTagNumber;
    private String manufacturerName;
    private String manufacturerModelNumber;
    private String serialNumber;

    private transient Integer quantity;

    /**
     * Gets the capitalAssetNumber property
     *
     * @return Returns the capitalAssetNumber
     */
    public Long getCapitalAssetNumber() {
        return this.capitalAssetNumber;
    }

    /**
     * Sets the capitalAssetNumber property value
     *
     * @param capitalAssetNumber The capitalAssetNumber to set
     */
    public void setCapitalAssetNumber(Long capitalAssetNumber) {
        this.capitalAssetNumber = capitalAssetNumber;
    }

    /**
     * Gets the capitalAssetDescription property
     *
     * @return Returns the capitalAssetDescription
     */
    public String getCapitalAssetDescription() {
        return this.capitalAssetDescription;
    }

    /**
     * Sets the capitalAssetDescription property value
     *
     * @param capitalAssetDescription The capitalAssetDescription to set
     */
    public void setCapitalAssetDescription(String capitalAssetDescription) {
        this.capitalAssetDescription = capitalAssetDescription;
    }

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
     * Gets the campusCode property
     *
     * @return Returns the campusCode
     */
    public String getCampusCode() {
        return this.campusCode;
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
        return this.buildingCode;
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
     * Gets the buildingRoomNumber property
     *
     * @return Returns the buildingRoomNumber
     */
    public String getBuildingRoomNumber() {
        return this.buildingRoomNumber;
    }

    /**
     * Sets the buildingRoomNumber property value
     *
     * @param buildingRoomNumber The buildingRoomNumber to set
     */
    public void setBuildingRoomNumber(String buildingRoomNumber) {
        this.buildingRoomNumber = buildingRoomNumber;
    }

    /**
     * Gets the buildingSubRoomNumber property
     *
     * @return Returns the buildingSubRoomNumber
     */
    public String getBuildingSubRoomNumber() {
        return this.buildingSubRoomNumber;
    }

    /**
     * Sets the buildingSubRoomNumber property value
     *
     * @param buildingSubRoomNumber The buildingSubRoomNumber to set
     */
    public void setBuildingSubRoomNumber(String buildingSubRoomNumber) {
        this.buildingSubRoomNumber = buildingSubRoomNumber;
    }

    /**
     * Gets the campusTagNumber property
     *
     * @return Returns the campusTagNumber
     */
    public String getCampusTagNumber() {
        return this.campusTagNumber;
    }

    /**
     * Sets the campusTagNumber property value
     *
     * @param campusTagNumber The campusTagNumber to set
     */
    public void setCampusTagNumber(String campusTagNumber) {
        this.campusTagNumber = campusTagNumber;
    }

    /**
     * Gets the manufacturerName property
     *
     * @return Returns the manufacturerName
     */
    public String getManufacturerName() {
        return this.manufacturerName;
    }

    /**
     * Sets the manufacturerName property value
     *
     * @param manufacturerName The manufacturerName to set
     */
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    /**
     * Gets the manufacturerModelNumber property
     *
     * @return Returns the manufacturerModelNumber
     */
    public String getManufacturerModelNumber() {
        return this.manufacturerModelNumber;
    }

    /**
     * Sets the manufacturerModelNumber property value
     *
     * @param manufacturerModelNumber The manufacturerModelNumber to set
     */
    public void setManufacturerModelNumber(String manufacturerModelNumber) {
        this.manufacturerModelNumber = manufacturerModelNumber;
    }

    /**
     * Gets the serialNumber property
     *
     * @return Returns the serialNumber
     */
    public String getSerialNumber() {
        return this.serialNumber;
    }

    /**
     * Sets the serialNumber property value
     *
     * @param serialNumber The serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    @Override
    protected LinkedHashMap<String, String> toStringMapper() {
        return null;
    }

    /**
     * @see org.kuali.rice.kns.bo.BusinessObject#refresh()
     */
    @Override
    public void refresh() {
        // TODO Auto-generated method stub

    }

    /**
     * Gets the quantity property
     *
     * @return Returns the quantity
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * Sets the quantity property value
     *
     * @param quantity The quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
