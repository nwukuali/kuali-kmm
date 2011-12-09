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

package org.kuali.ext.mm.integration.vnd.businessobject;

import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.kns.bo.ExternalizableBusinessObject;
import org.kuali.rice.kns.bo.TransientBusinessObjectBase;
import org.kuali.rice.kns.util.KualiDecimal;

/**
 */
public class FinancialVendorContract extends TransientBusinessObjectBase implements
        ExternalizableBusinessObject, FinancialSystemComponent {
    /**
     *
     */
    private static final long serialVersionUID = 8323832844972438383L;
    private Integer vendorContractGeneratedIdentifier;
    private Integer vendorHeaderGeneratedIdentifier;
    private Integer vendorDetailAssignedIdentifier;
    private String vendorContractName;
    private String vendorContractDescription;
    private String vendorCampusCode;
    private Date vendorContractBeginningDate;
    private Date vendorContractEndDate;
    private Integer contractManagerCode;
    private String purchaseOrderCostSourceCode;
    private String vendorPaymentTermsCode;
    private String vendorShippingPaymentTermsCode;
    private String vendorShippingTitleCode;
    private Date vendorContractExtensionDate;
    private Boolean vendorB2bIndicator;
    private KualiDecimal organizationAutomaticPurchaseOrderLimit;
    private KualiDecimal totalUsedAmt;
    private Integer poId;
    private boolean active;

    /**
     * Gets the vendorContractGeneratedIdentifier property
     *
     * @return Returns the vendorContractGeneratedIdentifier
     */
    public Integer getVendorContractGeneratedIdentifier() {
        return this.vendorContractGeneratedIdentifier;
    }

    /**
     * Sets the vendorContractGeneratedIdentifier property value
     *
     * @param vendorContractGeneratedIdentifier The vendorContractGeneratedIdentifier to set
     */
    public void setVendorContractGeneratedIdentifier(Integer vendorContractGeneratedIdentifier) {
        this.vendorContractGeneratedIdentifier = vendorContractGeneratedIdentifier;
    }

    /**
     * Gets the vendorHeaderGeneratedIdentifier property
     *
     * @return Returns the vendorHeaderGeneratedIdentifier
     */
    public Integer getVendorHeaderGeneratedIdentifier() {
        return this.vendorHeaderGeneratedIdentifier;
    }

    /**
     * Sets the vendorHeaderGeneratedIdentifier property value
     *
     * @param vendorHeaderGeneratedIdentifier The vendorHeaderGeneratedIdentifier to set
     */
    public void setVendorHeaderGeneratedIdentifier(Integer vendorHeaderGeneratedIdentifier) {
        this.vendorHeaderGeneratedIdentifier = vendorHeaderGeneratedIdentifier;
    }

    /**
     * Gets the vendorDetailAssignedIdentifier property
     *
     * @return Returns the vendorDetailAssignedIdentifier
     */
    public Integer getVendorDetailAssignedIdentifier() {
        return this.vendorDetailAssignedIdentifier;
    }

    /**
     * Sets the vendorDetailAssignedIdentifier property value
     *
     * @param vendorDetailAssignedIdentifier The vendorDetailAssignedIdentifier to set
     */
    public void setVendorDetailAssignedIdentifier(Integer vendorDetailAssignedIdentifier) {
        this.vendorDetailAssignedIdentifier = vendorDetailAssignedIdentifier;
    }


    /**
     * Gets the vendorContractName property
     *
     * @return Returns the vendorContractName
     */
    public String getVendorContractName() {
        return this.vendorContractName;
    }

    /**
     * Sets the vendorContractName property value
     *
     * @param vendorContractName The vendorContractName to set
     */
    public void setVendorContractName(String vendorContractName) {
        this.vendorContractName = vendorContractName;
    }

    /**
     * Gets the vendorContractDescription property
     *
     * @return Returns the vendorContractDescription
     */
    public String getVendorContractDescription() {
        return this.vendorContractDescription;
    }

    /**
     * Sets the vendorContractDescription property value
     *
     * @param vendorContractDescription The vendorContractDescription to set
     */
    public void setVendorContractDescription(String vendorContractDescription) {
        this.vendorContractDescription = vendorContractDescription;
    }

    /**
     * Gets the vendorCampusCode property
     *
     * @return Returns the vendorCampusCode
     */
    public String getVendorCampusCode() {
        return this.vendorCampusCode;
    }

    /**
     * Sets the vendorCampusCode property value
     *
     * @param vendorCampusCode The vendorCampusCode to set
     */
    public void setVendorCampusCode(String vendorCampusCode) {
        this.vendorCampusCode = vendorCampusCode;
    }

    /**
     * Gets the vendorContractBeginningDate property
     *
     * @return Returns the vendorContractBeginningDate
     */
    public Date getVendorContractBeginningDate() {
        return this.vendorContractBeginningDate;
    }

    /**
     * Sets the vendorContractBeginningDate property value
     *
     * @param vendorContractBeginningDate The vendorContractBeginningDate to set
     */
    public void setVendorContractBeginningDate(Date vendorContractBeginningDate) {
        this.vendorContractBeginningDate = vendorContractBeginningDate;
    }

    /**
     * Gets the vendorContractEndDate property
     *
     * @return Returns the vendorContractEndDate
     */
    public Date getVendorContractEndDate() {
        return this.vendorContractEndDate;
    }

    /**
     * Sets the vendorContractEndDate property value
     *
     * @param vendorContractEndDate The vendorContractEndDate to set
     */
    public void setVendorContractEndDate(Date vendorContractEndDate) {
        this.vendorContractEndDate = vendorContractEndDate;
    }

    /**
     * Gets the contractManagerCode property
     *
     * @return Returns the contractManagerCode
     */
    public Integer getContractManagerCode() {
        return this.contractManagerCode;
    }

    /**
     * Sets the contractManagerCode property value
     *
     * @param contractManagerCode The contractManagerCode to set
     */
    public void setContractManagerCode(Integer contractManagerCode) {
        this.contractManagerCode = contractManagerCode;
    }

    /**
     * Gets the purchaseOrderCostSourceCode property
     *
     * @return Returns the purchaseOrderCostSourceCode
     */
    public String getPurchaseOrderCostSourceCode() {
        return this.purchaseOrderCostSourceCode;
    }

    /**
     * Sets the purchaseOrderCostSourceCode property value
     *
     * @param purchaseOrderCostSourceCode The purchaseOrderCostSourceCode to set
     */
    public void setPurchaseOrderCostSourceCode(String purchaseOrderCostSourceCode) {
        this.purchaseOrderCostSourceCode = purchaseOrderCostSourceCode;
    }

    /**
     * Gets the vendorPaymentTermsCode property
     *
     * @return Returns the vendorPaymentTermsCode
     */
    public String getVendorPaymentTermsCode() {
        return this.vendorPaymentTermsCode;
    }

    /**
     * Sets the vendorPaymentTermsCode property value
     *
     * @param vendorPaymentTermsCode The vendorPaymentTermsCode to set
     */
    public void setVendorPaymentTermsCode(String vendorPaymentTermsCode) {
        this.vendorPaymentTermsCode = vendorPaymentTermsCode;
    }

    /**
     * Gets the vendorShippingPaymentTermsCode property
     *
     * @return Returns the vendorShippingPaymentTermsCode
     */
    public String getVendorShippingPaymentTermsCode() {
        return this.vendorShippingPaymentTermsCode;
    }

    /**
     * Sets the vendorShippingPaymentTermsCode property value
     *
     * @param vendorShippingPaymentTermsCode The vendorShippingPaymentTermsCode to set
     */
    public void setVendorShippingPaymentTermsCode(String vendorShippingPaymentTermsCode) {
        this.vendorShippingPaymentTermsCode = vendorShippingPaymentTermsCode;
    }

    /**
     * Gets the vendorShippingTitleCode property
     *
     * @return Returns the vendorShippingTitleCode
     */
    public String getVendorShippingTitleCode() {
        return this.vendorShippingTitleCode;
    }

    /**
     * Sets the vendorShippingTitleCode property value
     *
     * @param vendorShippingTitleCode The vendorShippingTitleCode to set
     */
    public void setVendorShippingTitleCode(String vendorShippingTitleCode) {
        this.vendorShippingTitleCode = vendorShippingTitleCode;
    }

    /**
     * Gets the vendorContractExtensionDate property
     *
     * @return Returns the vendorContractExtensionDate
     */
    public Date getVendorContractExtensionDate() {
        return this.vendorContractExtensionDate;
    }

    /**
     * Sets the vendorContractExtensionDate property value
     *
     * @param vendorContractExtensionDate The vendorContractExtensionDate to set
     */
    public void setVendorContractExtensionDate(Date vendorContractExtensionDate) {
        this.vendorContractExtensionDate = vendorContractExtensionDate;
    }

    /**
     * Gets the vendorB2bIndicator property
     *
     * @return Returns the vendorB2bIndicator
     */
    public Boolean getVendorB2bIndicator() {
        return this.vendorB2bIndicator;
    }

    /**
     * Sets the vendorB2bIndicator property value
     *
     * @param vendorB2bIndicator The vendorB2bIndicator to set
     */
    public void setVendorB2bIndicator(Boolean vendorB2bIndicator) {
        this.vendorB2bIndicator = vendorB2bIndicator;
    }

    /**
     * Gets the organizationAutomaticPurchaseOrderLimit property
     *
     * @return Returns the organizationAutomaticPurchaseOrderLimit
     */
    public KualiDecimal getOrganizationAutomaticPurchaseOrderLimit() {
        return this.organizationAutomaticPurchaseOrderLimit;
    }

    /**
     * Sets the organizationAutomaticPurchaseOrderLimit property value
     *
     * @param organizationAutomaticPurchaseOrderLimit The organizationAutomaticPurchaseOrderLimit to set
     */
    public void setOrganizationAutomaticPurchaseOrderLimit(
            KualiDecimal organizationAutomaticPurchaseOrderLimit) {
        this.organizationAutomaticPurchaseOrderLimit = organizationAutomaticPurchaseOrderLimit;
    }

    /**
     * Gets the active property
     *
     * @return Returns the active
     */
    public boolean isActive() {
        return this.active;
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
     * Gets the poId property
     * @return Returns the poId
     */
    public Integer getPoId() {
        return this.poId;
    }

    /**
     * Sets the poId property value
     * @param poId The poId to set
     */
    public void setPoId(Integer poId) {
        this.poId = poId;
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
     * Gets the totalUsedAmt property
     * @return Returns the totalUsedAmt
     */
    public KualiDecimal getTotalUsedAmt() {
        return this.totalUsedAmt;
    }

    /**
     * Sets the totalUsedAmt property value
     * @param totalUsedAmt The totalUsedAmt to set
     */
    public void setTotalUsedAmt(KualiDecimal totalUsedAmt) {
        this.totalUsedAmt = totalUsedAmt;
    }

}
