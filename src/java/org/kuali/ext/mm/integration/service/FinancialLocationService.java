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

package org.kuali.ext.mm.integration.service;

import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialRoom;

/**
 * Declaration of methods required by a Location Service implementor
 */
public interface FinancialLocationService {
    /**
     * Finds a building by its primary identification fields
     *
     * @param campusCode campus code
     * @param buildingCode building code
     * @return Building object
     */
    FinancialBuilding getBuilding(String campusCode, String buildingCode);

    /**
     * Finds a room by its primary identification fields
     *
     * @param campusCode campus code
     * @param buildingCode building code
     * @param roomNumber room number
     * @return Room
     */
    FinancialRoom getRoom(String campusCode, String buildingCode, String roomNumber);

}
