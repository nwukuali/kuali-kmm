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

import org.kuali.rice.core.api.util.type.KualiDecimal;

import java.io.Serializable;

/**
 * @author harsha07
 */
public class FinancialAccountingLine implements Serializable, Cloneable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String documentNumber;
    private Integer sequenceNumber;
    private Integer postingYear;
    private KualiDecimal amount;
    private String referenceOriginCode;
    private String referenceNumber;
    private String referenceTypeCode;
    private String overrideCode;
    private boolean objectBudgetOverride;
    private boolean objectBudgetOverrideNeeded;
    private String organizationReferenceId;
    protected String financialDocumentLineTypeCode;
    protected String financialDocumentLineDescription;
    protected boolean salesTaxRequired;
    private String chartOfAccountsCode;
    private String accountNumber;
    private String financialObjectCode;
    private String subAccountNumber;
    private String financialSubObjectCode;
    private String projectCode;
    private String balanceTypeCode;
    private String financialDocumentTypeCode;

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
     * Gets the sequenceNumber property
     *
     * @return Returns the sequenceNumber
     */
    public Integer getSequenceNumber() {
        return this.sequenceNumber;
    }

    /**
     * Sets the sequenceNumber property value
     *
     * @param sequenceNumber The sequenceNumber to set
     */
    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Gets the postingYear property
     *
     * @return Returns the postingYear
     */
    public Integer getPostingYear() {
        return this.postingYear;
    }

    /**
     * Sets the postingYear property value
     *
     * @param postingYear The postingYear to set
     */
    public void setPostingYear(Integer postingYear) {
        this.postingYear = postingYear;
    }

    /**
     * Gets the amount property
     *
     * @return Returns the amount
     */
    public KualiDecimal getAmount() {
        return this.amount;
    }

    /**
     * Sets the amount property value
     *
     * @param amount The amount to set
     */
    public void setAmount(KualiDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets the referenceOriginCode property
     *
     * @return Returns the referenceOriginCode
     */
    public String getReferenceOriginCode() {
        return this.referenceOriginCode;
    }

    /**
     * Sets the referenceOriginCode property value
     *
     * @param referenceOriginCode The referenceOriginCode to set
     */
    public void setReferenceOriginCode(String referenceOriginCode) {
        this.referenceOriginCode = referenceOriginCode;
    }

    /**
     * Gets the referenceNumber property
     *
     * @return Returns the referenceNumber
     */
    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    /**
     * Sets the referenceNumber property value
     *
     * @param referenceNumber The referenceNumber to set
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Gets the referenceTypeCode property
     *
     * @return Returns the referenceTypeCode
     */
    public String getReferenceTypeCode() {
        return this.referenceTypeCode;
    }

    /**
     * Sets the referenceTypeCode property value
     *
     * @param referenceTypeCode The referenceTypeCode to set
     */
    public void setReferenceTypeCode(String referenceTypeCode) {
        this.referenceTypeCode = referenceTypeCode;
    }

    /**
     * Gets the overrideCode property
     *
     * @return Returns the overrideCode
     */
    public String getOverrideCode() {
        return this.overrideCode;
    }

    /**
     * Sets the overrideCode property value
     *
     * @param overrideCode The overrideCode to set
     */
    public void setOverrideCode(String overrideCode) {
        this.overrideCode = overrideCode;
    }

    /**
     * Gets the objectBudgetOverride property
     *
     * @return Returns the objectBudgetOverride
     */
    public boolean isObjectBudgetOverride() {
        return this.objectBudgetOverride;
    }

    /**
     * Sets the objectBudgetOverride property value
     *
     * @param objectBudgetOverride The objectBudgetOverride to set
     */
    public void setObjectBudgetOverride(boolean objectBudgetOverride) {
        this.objectBudgetOverride = objectBudgetOverride;
    }

    /**
     * Gets the objectBudgetOverrideNeeded property
     *
     * @return Returns the objectBudgetOverrideNeeded
     */
    public boolean isObjectBudgetOverrideNeeded() {
        return this.objectBudgetOverrideNeeded;
    }

    /**
     * Sets the objectBudgetOverrideNeeded property value
     *
     * @param objectBudgetOverrideNeeded The objectBudgetOverrideNeeded to set
     */
    public void setObjectBudgetOverrideNeeded(boolean objectBudgetOverrideNeeded) {
        this.objectBudgetOverrideNeeded = objectBudgetOverrideNeeded;
    }

    /**
     * Gets the organizationReferenceId property
     *
     * @return Returns the organizationReferenceId
     */
    public String getOrganizationReferenceId() {
        return this.organizationReferenceId;
    }

    /**
     * Sets the organizationReferenceId property value
     *
     * @param organizationReferenceId The organizationReferenceId to set
     */
    public void setOrganizationReferenceId(String organizationReferenceId) {
        this.organizationReferenceId = organizationReferenceId;
    }

    /**
     * Gets the financialDocumentLineTypeCode property
     *
     * @return Returns the financialDocumentLineTypeCode
     */
    public String getFinancialDocumentLineTypeCode() {
        return this.financialDocumentLineTypeCode;
    }

    /**
     * Sets the financialDocumentLineTypeCode property value
     *
     * @param financialDocumentLineTypeCode The financialDocumentLineTypeCode to set
     */
    public void setFinancialDocumentLineTypeCode(String financialDocumentLineTypeCode) {
        this.financialDocumentLineTypeCode = financialDocumentLineTypeCode;
    }

    /**
     * Gets the financialDocumentLineDescription property
     *
     * @return Returns the financialDocumentLineDescription
     */
    public String getFinancialDocumentLineDescription() {
        return this.financialDocumentLineDescription;
    }

    /**
     * Sets the financialDocumentLineDescription property value
     *
     * @param financialDocumentLineDescription The financialDocumentLineDescription to set
     */
    public void setFinancialDocumentLineDescription(String financialDocumentLineDescription) {
        this.financialDocumentLineDescription = financialDocumentLineDescription;
    }

    /**
     * Gets the salesTaxRequired property
     *
     * @return Returns the salesTaxRequired
     */
    public boolean isSalesTaxRequired() {
        return this.salesTaxRequired;
    }

    /**
     * Sets the salesTaxRequired property value
     *
     * @param salesTaxRequired The salesTaxRequired to set
     */
    public void setSalesTaxRequired(boolean salesTaxRequired) {
        this.salesTaxRequired = salesTaxRequired;
    }

    /**
     * Gets the chartOfAccountsCode property
     *
     * @return Returns the chartOfAccountsCode
     */
    public String getChartOfAccountsCode() {
        return this.chartOfAccountsCode;
    }

    /**
     * Sets the chartOfAccountsCode property value
     *
     * @param chartOfAccountsCode The chartOfAccountsCode to set
     */
    public void setChartOfAccountsCode(String chartOfAccountsCode) {
        this.chartOfAccountsCode = chartOfAccountsCode;
    }

    /**
     * Gets the accountNumber property
     *
     * @return Returns the accountNumber
     */
    public String getAccountNumber() {
        return this.accountNumber;
    }

    /**
     * Sets the accountNumber property value
     *
     * @param accountNumber The accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Gets the financialObjectCode property
     *
     * @return Returns the financialObjectCode
     */
    public String getFinancialObjectCode() {
        return this.financialObjectCode;
    }

    /**
     * Sets the financialObjectCode property value
     *
     * @param financialObjectCode The financialObjectCode to set
     */
    public void setFinancialObjectCode(String financialObjectCode) {
        this.financialObjectCode = financialObjectCode;
    }

    /**
     * Gets the subAccountNumber property
     *
     * @return Returns the subAccountNumber
     */
    public String getSubAccountNumber() {
        return this.subAccountNumber;
    }

    /**
     * Sets the subAccountNumber property value
     *
     * @param subAccountNumber The subAccountNumber to set
     */
    public void setSubAccountNumber(String subAccountNumber) {
        this.subAccountNumber = subAccountNumber;
    }

    /**
     * Gets the financialSubObjectCode property
     *
     * @return Returns the financialSubObjectCode
     */
    public String getFinancialSubObjectCode() {
        return this.financialSubObjectCode;
    }

    /**
     * Sets the financialSubObjectCode property value
     *
     * @param financialSubObjectCode The financialSubObjectCode to set
     */
    public void setFinancialSubObjectCode(String financialSubObjectCode) {
        this.financialSubObjectCode = financialSubObjectCode;
    }

    /**
     * Gets the projectCode property
     *
     * @return Returns the projectCode
     */
    public String getProjectCode() {
        return this.projectCode;
    }

    /**
     * Sets the projectCode property value
     *
     * @param projectCode The projectCode to set
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Gets the balanceTypeCode property
     *
     * @return Returns the balanceTypeCode
     */
    public String getBalanceTypeCode() {
        return this.balanceTypeCode;
    }

    /**
     * Sets the balanceTypeCode property value
     *
     * @param balanceTypeCode The balanceTypeCode to set
     */
    public void setBalanceTypeCode(String balanceTypeCode) {
        this.balanceTypeCode = balanceTypeCode;
    }

    /**
     * Gets the financialDocumentTypeCode property
     * @return Returns the financialDocumentTypeCode
     */
    public String getFinancialDocumentTypeCode() {
        return this.financialDocumentTypeCode;
    }

    /**
     * Sets the financialDocumentTypeCode property value
     * @param financialDocumentTypeCode The financialDocumentTypeCode to set
     */
    public void setFinancialDocumentTypeCode(String financialDocumentTypeCode) {
        this.financialDocumentTypeCode = financialDocumentTypeCode;
    }

}
