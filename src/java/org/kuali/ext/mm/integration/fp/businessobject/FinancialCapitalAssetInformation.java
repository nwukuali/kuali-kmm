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

import org.springframework.util.AutoPopulatingList;

import java.io.Serializable;
import java.util.List;

/**
 * @author harsha07
 */
public class FinancialCapitalAssetInformation implements Serializable{
    private static final long serialVersionUID = -1922819352175600909L;
    private String documentNumber;
    private Integer vendorHeaderGeneratedIdentifier;
    private Integer vendorDetailAssignedIdentifier;
    private String vendorName;
    private Long capitalAssetNumber;
    private Integer capitalAssetQuantity;
    private String capitalAssetTypeCode;
    private String capitalAssetManufacturerName;
    private String capitalAssetDescription;
    private String capitalAssetManufacturerModelNumber;

    private List<FinancialCapitalAssetInformationDetail> capitalAssetInformationDetails;

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public FinancialCapitalAssetInformation() {
        this.capitalAssetInformationDetails = new AutoPopulatingList(
            FinancialCapitalAssetInformationDetail.class);
    }

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
     * Gets the vendorName property
     *
     * @return Returns the vendorName
     */
    public String getVendorName() {
        return this.vendorName;
    }

    /**
     * Sets the vendorName property value
     *
     * @param vendorName The vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * Gets the capitalAssetNumber property
     *
     * @return Returns the capitalAssetNumber
     */
    public Long getCapitalAssetNumber() {
        return this.capitalAssetNumber;
    }

    /**
     * Sets the capitalAssetNumber property value
     *
     * @param capitalAssetNumber The capitalAssetNumber to set
     */
    public void setCapitalAssetNumber(Long capitalAssetNumber) {
        this.capitalAssetNumber = capitalAssetNumber;
    }

    /**
     * Gets the capitalAssetQuantity property
     *
     * @return Returns the capitalAssetQuantity
     */
    public Integer getCapitalAssetQuantity() {
        return this.capitalAssetQuantity;
    }

    /**
     * Sets the capitalAssetQuantity property value
     *
     * @param capitalAssetQuantity The capitalAssetQuantity to set
     */
    public void setCapitalAssetQuantity(Integer capitalAssetQuantity) {
        this.capitalAssetQuantity = capitalAssetQuantity;
    }

    /**
     * Gets the capitalAssetTypeCode property
     *
     * @return Returns the capitalAssetTypeCode
     */
    public String getCapitalAssetTypeCode() {
        return this.capitalAssetTypeCode;
    }

    /**
     * Sets the capitalAssetTypeCode property value
     *
     * @param capitalAssetTypeCode The capitalAssetTypeCode to set
     */
    public void setCapitalAssetTypeCode(String capitalAssetTypeCode) {
        this.capitalAssetTypeCode = capitalAssetTypeCode;
    }

    /**
     * Gets the capitalAssetManufacturerName property
     *
     * @return Returns the capitalAssetManufacturerName
     */
    public String getCapitalAssetManufacturerName() {
        return this.capitalAssetManufacturerName;
    }

    /**
     * Sets the capitalAssetManufacturerName property value
     *
     * @param capitalAssetManufacturerName The capitalAssetManufacturerName to set
     */
    public void setCapitalAssetManufacturerName(String capitalAssetManufacturerName) {
        this.capitalAssetManufacturerName = capitalAssetManufacturerName;
    }

    /**
     * Gets the capitalAssetDescription property
     *
     * @return Returns the capitalAssetDescription
     */
    public String getCapitalAssetDescription() {
        return this.capitalAssetDescription;
    }

    /**
     * Sets the capitalAssetDescription property value
     *
     * @param capitalAssetDescription The capitalAssetDescription to set
     */
    public void setCapitalAssetDescription(String capitalAssetDescription) {
        this.capitalAssetDescription = capitalAssetDescription;
    }

    /**
     * Gets the capitalAssetManufacturerModelNumber property
     *
     * @return Returns the capitalAssetManufacturerModelNumber
     */
    public String getCapitalAssetManufacturerModelNumber() {
        return this.capitalAssetManufacturerModelNumber;
    }

    /**
     * Sets the capitalAssetManufacturerModelNumber property value
     *
     * @param capitalAssetManufacturerModelNumber The capitalAssetManufacturerModelNumber to set
     */
    public void setCapitalAssetManufacturerModelNumber(String capitalAssetManufacturerModelNumber) {
        this.capitalAssetManufacturerModelNumber = capitalAssetManufacturerModelNumber;
    }

    /**
     * Gets the capitalAssetInformationDetails property
     *
     * @return Returns the capitalAssetInformationDetails
     */
    public List<FinancialCapitalAssetInformationDetail> getCapitalAssetInformationDetails() {
        return this.capitalAssetInformationDetails;
    }

    /**
     * Sets the capitalAssetInformationDetails property value
     *
     * @param capitalAssetInformationDetails The capitalAssetInformationDetails to set
     */
    public void setCapitalAssetInformationDetails(
            List<FinancialCapitalAssetInformationDetail> capitalAssetInformationDetails) {
        this.capitalAssetInformationDetails = capitalAssetInformationDetails;
    }

}
