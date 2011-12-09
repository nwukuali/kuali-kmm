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
 * Financial room object
 */
public class FinancialRoom extends TransientBusinessObjectBase implements
        ExternalizableBusinessObject, FinancialSystemComponent {

    private static final long serialVersionUID = -1271596817977629611L;
    private String campusCode;
    private String buildingCode;
    private String buildingRoomNumber;
    private String buildingRoomType;
    private String buildingRoomDepartment;
    private String buildingRoomDescription;
    private boolean active;


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
     * Gets the buildingRoomNumber property
     *
     * @return Returns the buildingRoomNumber
     */
    public String getBuildingRoomNumber() {
        return buildingRoomNumber;
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
     * Gets the buildingRoomType property
     *
     * @return Returns the buildingRoomType
     */
    public String getBuildingRoomType() {
        return buildingRoomType;
    }

    /**
     * Sets the buildingRoomType property value
     *
     * @param buildingRoomType The buildingRoomType to set
     */
    public void setBuildingRoomType(String buildingRoomType) {
        this.buildingRoomType = buildingRoomType;
    }

    /**
     * Gets the buildingRoomDepartment property
     *
     * @return Returns the buildingRoomDepartment
     */
    public String getBuildingRoomDepartment() {
        return buildingRoomDepartment;
    }

    /**
     * Sets the buildingRoomDepartment property value
     *
     * @param buildingRoomDepartment The buildingRoomDepartment to set
     */
    public void setBuildingRoomDepartment(String buildingRoomDepartment) {
        this.buildingRoomDepartment = buildingRoomDepartment;
    }

    /**
     * Gets the buildingRoomDescription property
     *
     * @return Returns the buildingRoomDescription
     */
    public String getBuildingRoomDescription() {
        return buildingRoomDescription;
    }

    /**
     * Sets the buildingRoomDescription property value
     *
     * @param buildingRoomDescription The buildingRoomDescription to set
     */
    public void setBuildingRoomDescription(String buildingRoomDescription) {
        this.buildingRoomDescription = buildingRoomDescription;
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
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    @Override
    protected LinkedHashMap<String, String> toStringMapper() {
        return null;
    }

}
