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

import java.sql.Date;

public class CreditMemoExpected extends StoresPersistableBusinessObject {
    private static final long serialVersionUID = -515984463909296550L;
    private Integer creditMemoExpectedId;
    private Integer checkinDetailId;
    private CheckinDetail checkinDetail;
    private String warehouseCode;
    private Warehouse warehouse;
    private Integer returnDetailId;
    private ReturnDetail returnDetail;
    private boolean received = false;
    private Date expectedCreateDate;

    public CreditMemoExpected() {
    }

    public Integer getCreditMemoExpectedId() {
        return this.creditMemoExpectedId;
    }


    public void setCreditMemoExpectedId(Integer creditMemoExpectedId) {
        this.creditMemoExpectedId = creditMemoExpectedId;
    }


    public String getWarehouseCode() {
        return this.warehouseCode;
    }


    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }


    public Warehouse getWarehouse() {
        return this.warehouse;
    }


    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }


    public Integer getReturnDetailId() {
        return this.returnDetailId;
    }


    public void setReturnDetailId(Integer returnDetailId) {
        this.returnDetailId = returnDetailId;
    }


    public ReturnDetail getReturnDetail() {
        return this.returnDetail;
    }


    public void setReturnDetail(ReturnDetail returnDetail) {
        this.returnDetail = returnDetail;
    }


    public boolean isReceived() {
        return this.received;
    }


    public void setReceived(boolean received) {
        this.received = received;
    }


    public Date getExpectedCreateDate() {
        return this.expectedCreateDate;
    }


    public void setExpectedCreateDate(Date expectedCreateDate) {
        this.expectedCreateDate = expectedCreateDate;
    }


    public Integer getCheckinDetailId() {
        return this.checkinDetailId;
    }


    public void setCheckinDetailId(Integer checkinDetailId) {
        this.checkinDetailId = checkinDetailId;
    }


    public CheckinDetail getCheckinDetail() {
        return this.checkinDetail;
    }


    public void setCheckinDetail(CheckinDetail checkinDetail) {
        this.checkinDetail = checkinDetail;
    }


}
