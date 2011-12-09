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
import java.sql.Timestamp;

import org.kuali.rice.kns.util.KualiDecimal;

/**
 * @author harsha07
 */
public class FinancialInternalBillingItem implements Serializable{
    private static final long serialVersionUID = 8367188898770519024L;
    private String documentNumber;
    private Integer itemSequenceId;
    private String itemStockNumber;
    private String itemStockDescription;
    private Timestamp itemServiceDate;
    private Integer itemQuantity;
    private KualiDecimal itemUnitAmount;
    private String unitOfMeasureCode;
    private String warehouseCode;
    private String rentalId;
    private String serialNumber;
    private String mmOrderDetailId;
    private String pickListLineId;

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
     * Gets the itemSequenceId property
     * 
     * @return Returns the itemSequenceId
     */
    public Integer getItemSequenceId() {
        return this.itemSequenceId;
    }

    /**
     * Sets the itemSequenceId property value
     * 
     * @param itemSequenceId The itemSequenceId to set
     */
    public void setItemSequenceId(Integer itemSequenceId) {
        this.itemSequenceId = itemSequenceId;
    }

    /**
     * Gets the itemStockNumber property
     * 
     * @return Returns the itemStockNumber
     */
    public String getItemStockNumber() {
        return this.itemStockNumber;
    }

    /**
     * Sets the itemStockNumber property value
     * 
     * @param itemStockNumber The itemStockNumber to set
     */
    public void setItemStockNumber(String itemStockNumber) {
        this.itemStockNumber = itemStockNumber;
    }

    /**
     * Gets the itemStockDescription property
     * 
     * @return Returns the itemStockDescription
     */
    public String getItemStockDescription() {
        return this.itemStockDescription;
    }

    /**
     * Sets the itemStockDescription property value
     * 
     * @param itemStockDescription The itemStockDescription to set
     */
    public void setItemStockDescription(String itemStockDescription) {
        this.itemStockDescription = itemStockDescription;
    }

    /**
     * Gets the itemServiceDate property
     * 
     * @return Returns the itemServiceDate
     */
    public Timestamp getItemServiceDate() {
        return this.itemServiceDate;
    }

    /**
     * Sets the itemServiceDate property value
     * 
     * @param itemServiceDate The itemServiceDate to set
     */
    public void setItemServiceDate(Timestamp itemServiceDate) {
        this.itemServiceDate = itemServiceDate;
    }

    /**
     * Gets the itemQuantity property
     * 
     * @return Returns the itemQuantity
     */
    public Integer getItemQuantity() {
        return this.itemQuantity;
    }

    /**
     * Sets the itemQuantity property value
     * 
     * @param itemQuantity The itemQuantity to set
     */
    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    /**
     * Gets the itemUnitAmount property
     * 
     * @return Returns the itemUnitAmount
     */
    public KualiDecimal getItemUnitAmount() {
        return this.itemUnitAmount;
    }

    /**
     * Sets the itemUnitAmount property value
     * 
     * @param itemUnitAmount The itemUnitAmount to set
     */
    public void setItemUnitAmount(KualiDecimal itemUnitAmount) {
        this.itemUnitAmount = itemUnitAmount;
    }

    /**
     * Gets the unitOfMeasureCode property
     * 
     * @return Returns the unitOfMeasureCode
     */
    public String getUnitOfMeasureCode() {
        return this.unitOfMeasureCode;
    }

    /**
     * Sets the unitOfMeasureCode property value
     * 
     * @param unitOfMeasureCode The unitOfMeasureCode to set
     */
    public void setUnitOfMeasureCode(String unitOfMeasureCode) {
        this.unitOfMeasureCode = unitOfMeasureCode;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
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
     * Gets the mmOrderDetailId property
     * 
     * @return Returns the mmOrderDetailId
     */
    public String getMmOrderDetailId() {
        return this.mmOrderDetailId;
    }

    /**
     * Sets the mmOrderDetailId property value
     * 
     * @param mmOrderDetailId The mmOrderDetailId to set
     */
    public void setMmOrderDetailId(String mmOrderDetailId) {
        this.mmOrderDetailId = mmOrderDetailId;
    }

    /**
     * Gets the pickListLineId property
     * @return Returns the pickListLineId
     */
    public String getPickListLineId() {
        return this.pickListLineId;
    }

    /**
     * Sets the pickListLineId property value
     * @param pickListLineId The pickListLineId to set
     */
    public void setPickListLineId(String pickListLineId) {
        this.pickListLineId = pickListLineId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public String getRentalId() {
        return rentalId;
    }

}
