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

package org.kuali.ext.mm.integration.purap.document;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.kns.bo.ExternalizableBusinessObject;
import org.kuali.rice.kns.bo.TransientBusinessObjectBase;

/**
 * @author harsha07
 */
public class FinancialPurchaseOrderDocument extends TransientBusinessObjectBase implements
        ExternalizableBusinessObject, FinancialSystemComponent {
    private static final long serialVersionUID = -5247353248244774364L;
    private Integer vendorHeaderGeneratedId;
    private Integer vendorDetailAssignedId;
    private String vendorNm;
    private String campusCd;
    private String deliveryBuildingCd;
    private String deliveryBuildingRmNbr;
    private Integer reqsId;
    private List<FinancialPurchaseOrderDetail> orderDetails;
    private Integer poId;
    private String workflowStatusCode;
    private String poStatusCode;

    /**
     *
     */
    public FinancialPurchaseOrderDocument() {
        this.orderDetails = new ArrayList<FinancialPurchaseOrderDetail>();
    }

    /**
     * Gets the vendorHeaderGeneratedId property
     * 
     * @return Returns the vendorHeaderGeneratedId
     */
    public Integer getVendorHeaderGeneratedId() {
        return this.vendorHeaderGeneratedId;
    }

    /**
     * Sets the vendorHeaderGeneratedId property value
     * 
     * @param vendorHeaderGeneratedId The vendorHeaderGeneratedId to set
     */
    public void setVendorHeaderGeneratedId(Integer vendorHeaderGeneratedId) {
        this.vendorHeaderGeneratedId = vendorHeaderGeneratedId;
    }

    /**
     * Gets the vendorDetailAssignedId property
     * 
     * @return Returns the vendorDetailAssignedId
     */
    public Integer getVendorDetailAssignedId() {
        return this.vendorDetailAssignedId;
    }

    /**
     * Sets the vendorDetailAssignedId property value
     * 
     * @param vendorDetailAssignedId The vendorDetailAssignedId to set
     */
    public void setVendorDetailAssignedId(Integer vendorDetailAssignedId) {
        this.vendorDetailAssignedId = vendorDetailAssignedId;
    }

    /**
     * Gets the vendorNm property
     * 
     * @return Returns the vendorNm
     */
    public String getVendorNm() {
        return this.vendorNm;
    }

    /**
     * Sets the vendorNm property value
     * 
     * @param vendorNm The vendorNm to set
     */
    public void setVendorNm(String vendorNm) {
        this.vendorNm = vendorNm;
    }

    /**
     * Gets the campusCd property
     * 
     * @return Returns the campusCd
     */
    public String getCampusCd() {
        return this.campusCd;
    }

    /**
     * Sets the campusCd property value
     * 
     * @param campusCd The campusCd to set
     */
    public void setCampusCd(String campusCd) {
        this.campusCd = campusCd;
    }

    /**
     * Gets the deliveryBuildingCd property
     * 
     * @return Returns the deliveryBuildingCd
     */
    public String getDeliveryBuildingCd() {
        return this.deliveryBuildingCd;
    }

    /**
     * Sets the deliveryBuildingCd property value
     * 
     * @param deliveryBuildingCd The deliveryBuildingCd to set
     */
    public void setDeliveryBuildingCd(String deliveryBuildingCd) {
        this.deliveryBuildingCd = deliveryBuildingCd;
    }

    /**
     * Gets the deliveryBuildingRmNbr property
     * 
     * @return Returns the deliveryBuildingRmNbr
     */
    public String getDeliveryBuildingRmNbr() {
        return this.deliveryBuildingRmNbr;
    }

    /**
     * Sets the deliveryBuildingRmNbr property value
     * 
     * @param deliveryBuildingRmNbr The deliveryBuildingRmNbr to set
     */
    public void setDeliveryBuildingRmNbr(String deliveryBuildingRmNbr) {
        this.deliveryBuildingRmNbr = deliveryBuildingRmNbr;
    }

    /**
     * Gets the reqsId property
     * 
     * @return Returns the reqsId
     */
    public Integer getReqsId() {
        return this.reqsId;
    }

    /**
     * Sets the reqsId property value
     * 
     * @param reqsId The reqsId to set
     */
    public void setReqsId(Integer reqsId) {
        this.reqsId = reqsId;
    }

    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    @Override
    protected LinkedHashMap<String, String> toStringMapper() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Gets the orderDetails property
     * 
     * @return Returns the orderDetails
     */
    public List<FinancialPurchaseOrderDetail> getOrderDetails() {
        return this.orderDetails;
    }

    /**
     * Sets the orderDetails property value
     * 
     * @param orderDetails The orderDetails to set
     */
    public void setOrderDetails(List<FinancialPurchaseOrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    /**
     * Gets the poId property
     * 
     * @return Returns the poId
     */
    public Integer getPoId() {
        return this.poId;
    }

    /**
     * Sets the poId property value
     * 
     * @param poId The poId to set
     */
    public void setPoId(Integer poId) {
        this.poId = poId;
    }

    /**
     * Gets the orderStatusCode property
     * 
     * @return Returns the orderStatusCode
     */
    public String getWorkflowStatusCode() {
        return this.workflowStatusCode;
    }

    /**
     * Sets the orderStatusCode property value
     * 
     * @param orderStatusCode The orderStatusCode to set
     */
    public void setWorkflowStatusCode(String orderStatusCode) {
        this.workflowStatusCode = orderStatusCode;
    }

    public String getPoStatusCode() {
        return this.poStatusCode;
    }

    public void setPoStatusCode(String poStatusCode) {
        this.poStatusCode = poStatusCode;
    }

}
