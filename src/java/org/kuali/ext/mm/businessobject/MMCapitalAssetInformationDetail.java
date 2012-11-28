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
package org.kuali.ext.mm.businessobject;

import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialRoom;
import org.kuali.kfs.sys.KFSPropertyConstants;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.location.api.campus.Campus;

import java.util.HashMap;
import java.util.Map;


public class MMCapitalAssetInformationDetail extends PersistableBusinessObjectBase {

    /**
     *
     */
    private static final long serialVersionUID = 1387332123547905283L;
    private Integer orderDetailId;
    private String capitalAssetInfoDetailId;
    private String campusCode;
    private String buildingCode;
    private String buildingRoomNumber;
    private String buildingSubRoomNumber;
    private String capitalAssetTagNumber;
    private String capitalAssetSerialNumber;

    private Campus campus;
    private FinancialBuilding building;
    private FinancialRoom room;
    private MMCapitalAssetInformation capitalAssetInformation;


    public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}


	public Integer getOrderDetailId() {
		return orderDetailId;
	}


	public void setCapitalAssetInfoDetailId(String capitalAssetInfoDetailId) {
		this.capitalAssetInfoDetailId = capitalAssetInfoDetailId;
	}

	public String getCapitalAssetInfoDetailId() {
		return capitalAssetInfoDetailId;
	}

	/**
     * Gets the campusCode attribute.
     *
     * @return Returns the campusCode.
     */
    public String getCampusCode() {
        return campusCode;
    }

    /**
     * Sets the campusCode attribute value.
     *
     * @param campusCode The campusCode to set.
     */
    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    /**
     * Gets the buildingCode attribute.
     *
     * @return Returns the buildingCode.
     */
    public String getBuildingCode() {
        return buildingCode;
    }

    /**
     * Sets the buildingCode attribute value.
     *
     * @param buildingCode The buildingCode to set.
     */
    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    /**
     * Gets the buildingRoomNumber attribute.
     *
     * @return Returns the buildingRoomNumber.
     */
    public String getBuildingRoomNumber() {
        return buildingRoomNumber;
    }

    /**
     * Sets the buildingRoomNumber attribute value.
     *
     * @param buildingRoomNumber The buildingRoomNumber to set.
     */
    public void setBuildingRoomNumber(String buildingRoomNumber) {
        this.buildingRoomNumber = buildingRoomNumber;
    }

    /**
     * Gets the capitalAssetTagNumber attribute.
     *
     * @return Returns the capitalAssetTagNumber.
     */
    public String getCapitalAssetTagNumber() {
        return capitalAssetTagNumber;
    }

    /**
     * Sets the capitalAssetTagNumber attribute value.
     *
     * @param capitalAssetTagNumber The capitalAssetTagNumber to set.
     */
    public void setCapitalAssetTagNumber(String capitalAssetTagNumber) {
        this.capitalAssetTagNumber = capitalAssetTagNumber;
    }

    /**
     * Gets the capitalAssetSerialNumber attribute.
     *
     * @return Returns the capitalAssetSerialNumber.
     */
    public String getCapitalAssetSerialNumber() {
        return capitalAssetSerialNumber;
    }

    /**
     * Sets the capitalAssetSerialNumber attribute value.
     *
     * @param capitalAssetSerialNumber The capitalAssetSerialNumber to set.
     */
    public void setCapitalAssetSerialNumber(String capitalAssetSerialNumber) {
        this.capitalAssetSerialNumber = capitalAssetSerialNumber;
    }


    /**
     * Sets the campus attribute value.
     *
     * @param campus The campus to set.
     */
    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    /**
     * Gets the building attribute.
     *
     * @return Returns the building.
     */
    public FinancialBuilding getBuilding() {
        return building;
    }

    /**
     * Sets the building attribute value.
     *
     * @param building The building to set.
     */
    public void setBuilding(FinancialBuilding building) {
        this.building = building;
    }

    /**
     * Gets the room attribute.
     *
     * @return Returns the room.
     */
    public FinancialRoom getRoom() {
        return room;
    }

    /**
     * Sets the room attribute value.
     *
     * @param room The room to set.
     */
    public void setRoom(FinancialRoom room) {
        this.room = room;
    }

    /**
     * Gets the capitalAssetInformation attribute.
     *
     * @return Returns the capitalAssetInformation.
     */
    public MMCapitalAssetInformation getCapitalAssetInformation() {
        return capitalAssetInformation;
    }

    /**
     * Sets the capitalAssetInformation attribute value.
     *
     * @param capitalAssetInformation The capitalAssetInformation to set.
     */
    public void setCapitalAssetInformation(MMCapitalAssetInformation capitalAssetInformation) {
        this.capitalAssetInformation = capitalAssetInformation;
    }

    /**
     * Gets the buildingSubRoomNumber attribute.
     * @return Returns the buildingSubRoomNumber.
     */
    public String getBuildingSubRoomNumber() {
        return buildingSubRoomNumber;
    }

    /**
     * Sets the buildingSubRoomNumber attribute value.
     * @param buildingSubRoomNumber The buildingSubRoomNumber to set.
     */
    public void setBuildingSubRoomNumber(String buildingSubRoomNumber) {
        this.buildingSubRoomNumber = buildingSubRoomNumber;
    }

    /**
     * Returns a map with the primitive field names as the key and the primitive values as the map value.
     *
     * @return Map a map with the primitive field names as the key and the primitive values as the map value.
     */
    public Map<String, Object> getValuesMap() {
        Map<String, Object> simpleValues = new HashMap<String, Object>();

        simpleValues.put(KFSPropertyConstants.DOCUMENT_NUMBER, this.getOrderDetailId());
        simpleValues.put(KFSPropertyConstants.CAMPUS_CODE, this.getCampusCode());
        simpleValues.put(KFSPropertyConstants.BUILDING_CODE, this.getBuildingCode());
        simpleValues.put(KFSPropertyConstants.BUILDING_ROOM_NUMBER, this.getBuildingRoomNumber());

        return simpleValues;
    }
}
