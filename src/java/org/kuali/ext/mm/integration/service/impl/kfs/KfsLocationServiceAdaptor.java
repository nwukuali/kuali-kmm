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

package org.kuali.ext.mm.integration.service.impl.kfs;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.ext.mm.integration.service.FinancialLocationService;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialRoom;
import org.kuali.kfs.sys.businessobject.Building;
import org.kuali.kfs.sys.businessobject.Room;
import org.kuali.rice.kns.service.BusinessObjectService;

/**
 * @author harsha07
 */
public class KfsLocationServiceAdaptor extends KfsServiceAdaptor<BusinessObjectService> implements
        FinancialLocationService {
    public KfsLocationServiceAdaptor(QName serviceName) {
        super(serviceName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialLocationService#getBuilding(java.lang.String, java.lang.String)
     */
    public FinancialBuilding getBuilding(String campusCode, String buildingCode) {
        Map<String, String> primaryKeys = new HashMap<String, String>();
        primaryKeys.put("campusCode", campusCode);
        primaryKeys.put("buildingCode", buildingCode);
        Building source = (Building) getService().findByPrimaryKey(Building.class, primaryKeys);
        if (source == null) {
            return null;
        }
        FinancialBuilding target = new FinancialBuilding();
        adapt(source, target);
        return target;
    }

    /**
     * Adapts Building to Financial Building
     *
     * @param source Building
     * @param target Financial Building
     */
    protected void adapt(Building source, FinancialBuilding target) {
        target.setCampusCode(source.getCampusCode());
        target.setBuildingCode(source.getBuildingCode());
        target.setBuildingName(source.getBuildingName());
        target.setBuildingStreetAddress(source.getBuildingStreetAddress());
        target.setBuildingAddressCityName(source.getBuildingAddressCityName());
        target.setBuildingAddressStateCode(source.getBuildingAddressStateCode());
        target.setBuildingAddressZipCode(source.getBuildingAddressZipCode());
        target.setAlternateBuildingCode(source.getAlternateBuildingCode());
        target.setBuildingAddressCountryCode(source.getBuildingAddressCountryCode());
        target.setActive(source.isActive());
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialLocationService#getRoom(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public FinancialRoom getRoom(String campusCode, String buildingCode, String roomNumber) {
        Map<String, String> primaryKeys = new HashMap<String, String>();
        primaryKeys.put("campusCode", campusCode);
        primaryKeys.put("buildingCode", buildingCode);
        Room source = (Room) getService().findByPrimaryKey(Room.class, primaryKeys);
        if (source == null) {
            return null;
        }
        FinancialRoom target = new FinancialRoom();
        adapt(source, target);
        return target;
    }

    /**
     * Adapts Room to FinancialRoom
     *
     * @param source Room
     * @param target Financial Room
     */
    protected void adapt(Room source, FinancialRoom target) {
        target.setCampusCode(source.getCampusCode());
        target.setBuildingCode(source.getBuildingCode());
        target.setBuildingRoomNumber(source.getBuildingRoomNumber());
        target.setBuildingRoomType(source.getBuildingRoomType());
        target.setBuildingRoomDepartment(source.getBuildingRoomDepartment());
        target.setBuildingRoomDescription(source.getBuildingRoomDescription());
        target.setActive(source.isActive());
    }

}
