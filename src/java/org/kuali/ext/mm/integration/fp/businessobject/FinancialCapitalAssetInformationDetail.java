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

package org.kuali.ext.mm.integration.fp.businessobject;

import java.io.Serializable;

/**
 * @author harsha07
 */
public class FinancialCapitalAssetInformationDetail implements Serializable{
    private static final long serialVersionUID = 5115288625139063698L;
    private String documentNumber;
    private Integer itemLineNumber;
    private String campusCode;
    private String buildingCode;
    private String buildingRoomNumber;
    private String buildingSubRoomNumber;
    private String capitalAssetTagNumber;
    private String capitalAssetSerialNumber;

    /**
     * Gets the documentNumber property
     *
     * @return Returns the documentNumber
     */
    public String getDocumentNumber() {
        return this.documentNumber;
    }

    /**
     * Sets the documentNumber property value
     *
     * @param documentNumber The documentNumber to set
     */
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    /**
     * Gets the itemLineNumber property
     *
     * @return Returns the itemLineNumber
     */
    public Integer getItemLineNumber() {
        return this.itemLineNumber;
    }

    /**
     * Sets the itemLineNumber property value
     *
     * @param itemLineNumber The itemLineNumber to set
     */
    public void setItemLineNumber(Integer itemLineNumber) {
        this.itemLineNumber = itemLineNumber;
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
     * Gets the capitalAssetTagNumber property
     *
     * @return Returns the capitalAssetTagNumber
     */
    public String getCapitalAssetTagNumber() {
        return this.capitalAssetTagNumber;
    }

    /**
     * Sets the capitalAssetTagNumber property value
     *
     * @param capitalAssetTagNumber The capitalAssetTagNumber to set
     */
    public void setCapitalAssetTagNumber(String capitalAssetTagNumber) {
        this.capitalAssetTagNumber = capitalAssetTagNumber;
    }

    /**
     * Gets the capitalAssetSerialNumber property
     *
     * @return Returns the capitalAssetSerialNumber
     */
    public String getCapitalAssetSerialNumber() {
        return this.capitalAssetSerialNumber;
    }

    /**
     * Sets the capitalAssetSerialNumber property value
     *
     * @param capitalAssetSerialNumber The capitalAssetSerialNumber to set
     */
    public void setCapitalAssetSerialNumber(String capitalAssetSerialNumber) {
        this.capitalAssetSerialNumber = capitalAssetSerialNumber;
    }


}
