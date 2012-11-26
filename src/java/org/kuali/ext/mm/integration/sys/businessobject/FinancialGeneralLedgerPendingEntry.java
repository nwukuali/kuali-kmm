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

import org.kuali.ext.mm.businessobject.StoresPersistableBusinessObject;
import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static org.kuali.ext.mm.common.sys.MMConstants.LF;

/**
 * @author harsha07
 */
public class FinancialGeneralLedgerPendingEntry extends StoresPersistableBusinessObject implements
	ExternalizableBusinessObject, FinancialSystemComponent {

    private static final long serialVersionUID = -1007528720894915767L;
    private Integer entryId;
    private String warehouseCode;
    private String financialSystemOriginationCode;
    private String documentNumber;
    private Integer transactionLedgerEntrySequenceNumber;
    private String chartOfAccountsCode;
    private String accountNumber;
    private String subAccountNumber;
    private String financialObjectCode;
    private String financialSubObjectCode;
    private String financialBalanceTypeCode;
    private String financialObjectTypeCode;
    private Integer universityFiscalYear;
    private String universityFiscalPeriodCode;
    private String transactionLedgerEntryDescription;
    private KualiDecimal transactionLedgerEntryAmount;
    private String transactionDebitCreditCode;
    private Date transactionDate;
    private String financialDocumentTypeCode;
    private String organizationDocumentNumber;
    private String projectCode;
    private String organizationReferenceId;
    private String referenceFinancialDocumentTypeCode;
    private String referenceFinancialSystemOriginationCode;
    private String referenceFinancialDocumentNumber;
    private Date financialDocumentReversalDate;
    private String transactionEncumbranceUpdateCode;
    private String financialDocumentApprovedCode;
    private String acctSufficientFundsFinObjCd;
    private boolean transactionEntryOffsetIndicator;
    private Timestamp transactionEntryProcessedTs;


    /**
     * Gets the financialSystemOriginationCode property
     * 
     * @return Returns the financialSystemOriginationCode
     */
    public String getFinancialSystemOriginationCode() {
        return this.financialSystemOriginationCode;
    }


    /**
     * Sets the financialSystemOriginationCode property value
     * 
     * @param financialSystemOriginationCode The financialSystemOriginationCode to set
     */
    public void setFinancialSystemOriginationCode(String financialSystemOriginationCode) {
        this.financialSystemOriginationCode = financialSystemOriginationCode;
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
     * Gets the transactionLedgerEntrySequenceNumber property
     * 
     * @return Returns the transactionLedgerEntrySequenceNumber
     */
    public Integer getTransactionLedgerEntrySequenceNumber() {
        return this.transactionLedgerEntrySequenceNumber;
    }


    /**
     * Sets the transactionLedgerEntrySequenceNumber property value
     * 
     * @param transactionLedgerEntrySequenceNumber The transactionLedgerEntrySequenceNumber to set
     */
    public void setTransactionLedgerEntrySequenceNumber(Integer transactionLedgerEntrySequenceNumber) {
        this.transactionLedgerEntrySequenceNumber = transactionLedgerEntrySequenceNumber;
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
     * Gets the financialBalanceTypeCode property
     * 
     * @return Returns the financialBalanceTypeCode
     */
    public String getFinancialBalanceTypeCode() {
        return this.financialBalanceTypeCode;
    }


    /**
     * Sets the financialBalanceTypeCode property value
     * 
     * @param financialBalanceTypeCode The financialBalanceTypeCode to set
     */
    public void setFinancialBalanceTypeCode(String financialBalanceTypeCode) {
        this.financialBalanceTypeCode = financialBalanceTypeCode;
    }


    /**
     * Gets the financialObjectTypeCode property
     * 
     * @return Returns the financialObjectTypeCode
     */
    public String getFinancialObjectTypeCode() {
        return this.financialObjectTypeCode;
    }


    /**
     * Sets the financialObjectTypeCode property value
     * 
     * @param financialObjectTypeCode The financialObjectTypeCode to set
     */
    public void setFinancialObjectTypeCode(String financialObjectTypeCode) {
        this.financialObjectTypeCode = financialObjectTypeCode;
    }


    /**
     * Gets the universityFiscalYear property
     * 
     * @return Returns the universityFiscalYear
     */
    public Integer getUniversityFiscalYear() {
        return this.universityFiscalYear;
    }


    /**
     * Sets the universityFiscalYear property value
     * 
     * @param universityFiscalYear The universityFiscalYear to set
     */
    public void setUniversityFiscalYear(Integer universityFiscalYear) {
        this.universityFiscalYear = universityFiscalYear;
    }


    /**
     * Gets the universityFiscalPeriodCode property
     * 
     * @return Returns the universityFiscalPeriodCode
     */
    public String getUniversityFiscalPeriodCode() {
        return this.universityFiscalPeriodCode;
    }


    /**
     * Sets the universityFiscalPeriodCode property value
     * 
     * @param universityFiscalPeriodCode The universityFiscalPeriodCode to set
     */
    public void setUniversityFiscalPeriodCode(String universityFiscalPeriodCode) {
        this.universityFiscalPeriodCode = universityFiscalPeriodCode;
    }


    /**
     * Gets the transactionLedgerEntryDescription property
     * 
     * @return Returns the transactionLedgerEntryDescription
     */
    public String getTransactionLedgerEntryDescription() {
        return this.transactionLedgerEntryDescription;
    }


    /**
     * Sets the transactionLedgerEntryDescription property value
     * 
     * @param transactionLedgerEntryDescription The transactionLedgerEntryDescription to set
     */
    public void setTransactionLedgerEntryDescription(String transactionLedgerEntryDescription) {
        this.transactionLedgerEntryDescription = transactionLedgerEntryDescription;
    }


    /**
     * Gets the transactionLedgerEntryAmount property
     * 
     * @return Returns the transactionLedgerEntryAmount
     */
    public KualiDecimal getTransactionLedgerEntryAmount() {
        return this.transactionLedgerEntryAmount;
    }


    /**
     * Sets the transactionLedgerEntryAmount property value
     * 
     * @param transactionLedgerEntryAmount The transactionLedgerEntryAmount to set
     */
    public void setTransactionLedgerEntryAmount(KualiDecimal transactionLedgerEntryAmount) {
        this.transactionLedgerEntryAmount = transactionLedgerEntryAmount;
    }


    /**
     * Gets the transactionDebitCreditCode property
     * 
     * @return Returns the transactionDebitCreditCode
     */
    public String getTransactionDebitCreditCode() {
        return this.transactionDebitCreditCode;
    }


    /**
     * Sets the transactionDebitCreditCode property value
     * 
     * @param transactionDebitCreditCode The transactionDebitCreditCode to set
     */
    public void setTransactionDebitCreditCode(String transactionDebitCreditCode) {
        this.transactionDebitCreditCode = transactionDebitCreditCode;
    }


    /**
     * Gets the transactionDate property
     * 
     * @return Returns the transactionDate
     */
    public Date getTransactionDate() {
        return this.transactionDate;
    }


    /**
     * Sets the transactionDate property value
     * 
     * @param transactionDate The transactionDate to set
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }


    /**
     * Gets the financialDocumentTypeCode property
     * 
     * @return Returns the financialDocumentTypeCode
     */
    public String getFinancialDocumentTypeCode() {
        return this.financialDocumentTypeCode;
    }


    /**
     * Sets the financialDocumentTypeCode property value
     * 
     * @param financialDocumentTypeCode The financialDocumentTypeCode to set
     */
    public void setFinancialDocumentTypeCode(String financialDocumentTypeCode) {
        this.financialDocumentTypeCode = financialDocumentTypeCode;
    }


    /**
     * Gets the organizationDocumentNumber property
     * 
     * @return Returns the organizationDocumentNumber
     */
    public String getOrganizationDocumentNumber() {
        return this.organizationDocumentNumber;
    }


    /**
     * Sets the organizationDocumentNumber property value
     * 
     * @param organizationDocumentNumber The organizationDocumentNumber to set
     */
    public void setOrganizationDocumentNumber(String organizationDocumentNumber) {
        this.organizationDocumentNumber = organizationDocumentNumber;
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
     * Gets the organizationReferenceId property
     * 
     * @return Returns the organizationReferenceId
     */
    public String getOrganizationReferenceId() {
        if (this.organizationReferenceId != null && this.organizationReferenceId.length() > 8) {
            return this.organizationReferenceId.substring(0, 8);
        }
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
     * Gets the referenceFinancialDocumentTypeCode property
     * 
     * @return Returns the referenceFinancialDocumentTypeCode
     */
    public String getReferenceFinancialDocumentTypeCode() {
        return this.referenceFinancialDocumentTypeCode;
    }


    /**
     * Sets the referenceFinancialDocumentTypeCode property value
     * 
     * @param referenceFinancialDocumentTypeCode The referenceFinancialDocumentTypeCode to set
     */
    public void setReferenceFinancialDocumentTypeCode(String referenceFinancialDocumentTypeCode) {
        this.referenceFinancialDocumentTypeCode = referenceFinancialDocumentTypeCode;
    }


    /**
     * Gets the referenceFinancialSystemOriginationCode property
     * 
     * @return Returns the referenceFinancialSystemOriginationCode
     */
    public String getReferenceFinancialSystemOriginationCode() {
        return this.referenceFinancialSystemOriginationCode;
    }


    /**
     * Sets the referenceFinancialSystemOriginationCode property value
     * 
     * @param referenceFinancialSystemOriginationCode The referenceFinancialSystemOriginationCode to set
     */
    public void setReferenceFinancialSystemOriginationCode(
            String referenceFinancialSystemOriginationCode) {
        this.referenceFinancialSystemOriginationCode = referenceFinancialSystemOriginationCode;
    }


    /**
     * Gets the referenceFinancialDocumentNumber property
     * 
     * @return Returns the referenceFinancialDocumentNumber
     */
    public String getReferenceFinancialDocumentNumber() {
        return this.referenceFinancialDocumentNumber;
    }


    /**
     * Sets the referenceFinancialDocumentNumber property value
     * 
     * @param referenceFinancialDocumentNumber The referenceFinancialDocumentNumber to set
     */
    public void setReferenceFinancialDocumentNumber(String referenceFinancialDocumentNumber) {
        this.referenceFinancialDocumentNumber = referenceFinancialDocumentNumber;
    }


    /**
     * Gets the financialDocumentReversalDate property
     * 
     * @return Returns the financialDocumentReversalDate
     */
    public Date getFinancialDocumentReversalDate() {
        return this.financialDocumentReversalDate;
    }


    /**
     * Sets the financialDocumentReversalDate property value
     * 
     * @param financialDocumentReversalDate The financialDocumentReversalDate to set
     */
    public void setFinancialDocumentReversalDate(Date financialDocumentReversalDate) {
        this.financialDocumentReversalDate = financialDocumentReversalDate;
    }


    /**
     * Gets the transactionEncumbranceUpdateCode property
     * 
     * @return Returns the transactionEncumbranceUpdateCode
     */
    public String getTransactionEncumbranceUpdateCode() {
        return this.transactionEncumbranceUpdateCode;
    }


    /**
     * Sets the transactionEncumbranceUpdateCode property value
     * 
     * @param transactionEncumbranceUpdateCode The transactionEncumbranceUpdateCode to set
     */
    public void setTransactionEncumbranceUpdateCode(String transactionEncumbranceUpdateCode) {
        this.transactionEncumbranceUpdateCode = transactionEncumbranceUpdateCode;
    }


    /**
     * Gets the financialDocumentApprovedCode property
     * 
     * @return Returns the financialDocumentApprovedCode
     */
    public String getFinancialDocumentApprovedCode() {
        return this.financialDocumentApprovedCode;
    }


    /**
     * Sets the financialDocumentApprovedCode property value
     * 
     * @param financialDocumentApprovedCode The financialDocumentApprovedCode to set
     */
    public void setFinancialDocumentApprovedCode(String financialDocumentApprovedCode) {
        this.financialDocumentApprovedCode = financialDocumentApprovedCode;
    }


    /**
     * Gets the acctSufficientFundsFinObjCd property
     * 
     * @return Returns the acctSufficientFundsFinObjCd
     */
    public String getAcctSufficientFundsFinObjCd() {
        return this.acctSufficientFundsFinObjCd;
    }


    /**
     * Sets the acctSufficientFundsFinObjCd property value
     * 
     * @param acctSufficientFundsFinObjCd The acctSufficientFundsFinObjCd to set
     */
    public void setAcctSufficientFundsFinObjCd(String acctSufficientFundsFinObjCd) {
        this.acctSufficientFundsFinObjCd = acctSufficientFundsFinObjCd;
    }


    /**
     * Gets the transactionEntryOffsetIndicator property
     * 
     * @return Returns the transactionEntryOffsetIndicator
     */
    public boolean isTransactionEntryOffsetIndicator() {
        return this.transactionEntryOffsetIndicator;
    }


    /**
     * Sets the transactionEntryOffsetIndicator property value
     * 
     * @param transactionEntryOffsetIndicator The transactionEntryOffsetIndicator to set
     */
    public void setTransactionEntryOffsetIndicator(boolean transactionEntryOffsetIndicator) {
        this.transactionEntryOffsetIndicator = transactionEntryOffsetIndicator;
    }


    /**
     * Gets the transactionEntryProcessedTs property
     * 
     * @return Returns the transactionEntryProcessedTs
     */
    public Timestamp getTransactionEntryProcessedTs() {
        return this.transactionEntryProcessedTs;
    }


    /**
     * Sets the transactionEntryProcessedTs property value
     * 
     * @param transactionEntryProcessedTs The transactionEntryProcessedTs to set
     */
    public void setTransactionEntryProcessedTs(Timestamp transactionEntryProcessedTs) {
        this.transactionEntryProcessedTs = transactionEntryProcessedTs;
    }


    /**
     * Gets the entryId property
     * 
     * @return Returns the entryId
     */
    public Integer getEntryId() {
        return this.entryId;
    }


    /**
     * Sets the entryId property value
     * 
     * @param entryId The entryId to set
     */
    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }

    public String toCollectorXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<glEntry>" + LF);
        sb.append("<universityFiscalYear>" + getUniversityFiscalYear() + "</universityFiscalYear>"
                + LF);
        sb.append("<chartOfAccountsCode>" + getChartOfAccountsCode() + "</chartOfAccountsCode>"
                + LF);
        sb.append("<accountNumber>" + getAccountNumber() + "</accountNumber>" + LF);
        sb.append("<subAccountNumber>"
                + (getSubAccountNumber() == null ? "" : getSubAccountNumber())
                + "</subAccountNumber>" + LF);
        sb.append("<objectCode>" + getFinancialObjectCode() + "</objectCode>" + LF);
        sb.append("<subObjectCode>"
                + (getFinancialSubObjectCode() == null ? "" : getFinancialSubObjectCode())
                + "</subObjectCode>" + LF);
        sb.append("<balanceTypeCode>" + getFinancialBalanceTypeCode() + "</balanceTypeCode>"
                + LF);
        sb
                .append("<objectTypeCode>" + getFinancialObjectTypeCode() + "</objectTypeCode>"
                        + LF);
        sb.append("<universityFiscalAccountingPeriod>" + getUniversityFiscalPeriodCode()
                + "</universityFiscalAccountingPeriod>" + LF);
        sb.append("<documentTypeCode>" + getFinancialDocumentTypeCode() + "</documentTypeCode>"
                + LF);
        sb.append("<originationCode>" + getFinancialSystemOriginationCode() + "</originationCode>"
                + LF);
        sb.append("<documentNumber>" + getDocumentNumber() + "</documentNumber>" + LF);
        sb.append("<transactionEntrySequenceId>" + getTransactionLedgerEntrySequenceNumber()
                + "</transactionEntrySequenceId>" + LF);
        sb.append("<transactionLedgerEntryDescription>" + getTransactionLedgerEntryDescription()
                + "</transactionLedgerEntryDescription>" + LF);
        sb.append("<transactionLedgerEntryAmount>" + getTransactionLedgerEntryAmount()
                + "</transactionLedgerEntryAmount>" + LF);
        sb.append("<debitOrCreditCode>" + getTransactionDebitCreditCode() + "</debitOrCreditCode>"
                + LF);
        sb.append("<transactionDate>"
                + new SimpleDateFormat("yyyy-MM-dd").format(getTransactionDate())
                + "</transactionDate>" + LF);
        sb.append("<organizationDocumentNumber>"
                + (getOrganizationDocumentNumber() == null ? "" : getOrganizationDocumentNumber())
                + "</organizationDocumentNumber>" + LF);
        sb.append("<organizationReferenceId>"
                + (getOrganizationReferenceId() == null ? "" : getOrganizationReferenceId())
                + "</organizationReferenceId>" + LF);
        sb.append("<projectCode>" + (getProjectCode() == null ? "" : getProjectCode())
                + "</projectCode>" + LF);
        sb.append("<referenceDocumentTypeCode>"
                + (getReferenceFinancialDocumentTypeCode() == null ? ""
                        : getReferenceFinancialDocumentTypeCode()) + "</referenceDocumentTypeCode>"
                + LF);
        sb.append("<referenceOriginationCode>"
                + (getReferenceFinancialSystemOriginationCode() == null ? ""
                        : getReferenceFinancialSystemOriginationCode())
                + "</referenceOriginationCode>" + LF);
        sb.append("<referenceDocumentNumber>"
                + (getReferenceFinancialDocumentNumber() == null ? ""
                        : getReferenceFinancialDocumentNumber()) + "</referenceDocumentNumber>"
                + LF);
        //sb.append("<documentReversalDate></documentReversalDate>" + newLine);
        sb.append("</glEntry>");
        return sb.toString();
    }


    /**
     * Gets the warehouseCode property
     * 
     * @return Returns the warehouseCode
     */
    public String getWarehouseCode() {
        return this.warehouseCode;
    }


    /**
     * Sets the warehouseCode property value
     * 
     * @param warehouseCode The warehouseCode to set
     */
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
}
