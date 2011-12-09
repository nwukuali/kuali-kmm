/*
 * Copyright 2006-2007 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.document.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.StockCountMap;
import org.kuali.rice.kns.web.struts.form.KualiForm;

/**
 * This class is the struts form for Credit Card Receipt document.
 */
public class CountWorksheetPrintForm extends KualiForm {

    /**
     *
     */
    private static final long serialVersionUID = 1716233436133304472L;
    private String warehouseCode = null;
    private String zoneCd = null;
    private String countFreq = null;
    private String counters = null;
    private int countersIntVal = 0;
    private String copies = null;
    private int copiesIntVal = 0;
    private boolean dataValid = true;
    private boolean quantityOnHandLessThanZero = false;
    private String fullInventory;


    /**
     * Constructs a CreditCardReceiptForm.java.
     */
    public CountWorksheetPrintForm() {
        super();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getZoneCd() {
        return zoneCd;
    }

    public void setZoneCd(String zoneCd) {
        this.zoneCd = zoneCd;
    }

    public String getCountFreq() {
        return countFreq;
    }

    public void setCountFreq(String countFreq) {
        this.countFreq = countFreq;
    }

    public String getCounters() {
        return counters;
    }

    public void setCounters(String counters) {
        this.counters = counters;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String copies) {
        this.copies = copies;
    }

    public int getCountersIntVal() {
        try {
            this.countersIntVal = Integer.valueOf(this.counters.trim()).intValue();
            // this.countersIntVal = Integer.valueOf(this.counters.trim()).intValue();
        }
        catch (NumberFormatException e) {
            this.countersIntVal = -2;
        }
        catch (Exception e) {
            this.countersIntVal = -1;
        }
        return countersIntVal;
    }

    public int getCopiesIntVal() {
        try {
            if (!StringUtils.isEmpty(copies))
                this.copiesIntVal = Integer.valueOf(copies.trim()).intValue();
        }
        catch (NumberFormatException e) {
            this.copiesIntVal = -2;
        }
        catch (Exception e) {
            this.copiesIntVal = -1;
        }
        return copiesIntVal;
    }

    public boolean isDataValid() {
        return dataValid;
    }

    public void setDataValid(boolean isDataValid) {
        this.dataValid = isDataValid;
    }

    public boolean isQuantityOnHandLessThanZero() {
        return quantityOnHandLessThanZero;
    }

    public void setQuantityOnHandLessThanZero(boolean quantityOnHandLessThanZero) {
        this.quantityOnHandLessThanZero = quantityOnHandLessThanZero;
    }

    /**
     * Overrides the parent to call super.populate and then tells each line to check the associated data dictionary and modify the
     * values entered to follow all the attributes set for the values of the accounting line.
     * 
     * @see org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase#populate(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
    }

    /**
     * @see org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        StockCountMap.reset();
    }

    /**
     * Gets the fullInventory property
     * @return Returns the fullInventory
     */
    public String getFullInventory() {
        return this.fullInventory;
    }

    /**
     * Sets the fullInventory property value
     * @param fullInventory The fullInventory to set
     */
    public void setFullInventory(String fullInventory) {
        this.fullInventory = fullInventory;
    }
}
