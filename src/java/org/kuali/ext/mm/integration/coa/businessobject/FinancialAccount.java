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

package org.kuali.ext.mm.integration.coa.businessobject;

import org.apache.commons.lang.time.DateUtils;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.bo.TransientBusinessObjectBase;

import java.sql.Date;
import java.util.Calendar;

/**
 *
 */
public class FinancialAccount extends TransientBusinessObjectBase implements
	ExternalizableBusinessObject, FinancialSystemComponent {
    private static final long serialVersionUID = 6650842976182882495L;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(FinancialAccount.class);

    private String chartOfAccountsCode;
    private String accountNumber;
    private String accountName;
    private boolean accountsFringesBnftIndicator;
    private Date accountRestrictedStatusDate;
    private String accountCityName;
    private String accountStateCode;
    private String accountStreetAddress;
    private String accountZipCode;
    private Date accountCreateDate;
    private Date accountEffectiveDate;
    private Date accountExpirationDate;
    private String acctIndirectCostRcvyTypeCd;
    private String acctCustomIndCstRcvyExclCd;
    private String financialIcrSeriesIdentifier;
    private boolean accountInFinancialProcessingIndicator;
    private String budgetRecordingLevelCode;
    private String accountSufficientFundsCode;
    private boolean pendingAcctSufficientFundsIndicator;
    private boolean extrnlFinEncumSufficntFndIndicator;
    private boolean intrnlFinEncumSufficntFndIndicator;
    private boolean finPreencumSufficientFundIndicator;
    private boolean financialObjectivePrsctrlIndicator;
    private String accountCfdaNumber;
    private boolean accountOffCampusIndicator;
    private boolean active;

    private String accountFiscalOfficerSystemIdentifier;
    private String accountsSupervisorySystemsIdentifier;
    private String accountManagerSystemIdentifier;
    private String organizationCode;
    private String accountTypeCode;
    private String accountPhysicalCampusCode;
    private String subFundGroupCode;
    private String financialHigherEdFunctionCd;
    private String accountRestrictedStatusCode;
    private String reportsToChartOfAccountsCode;
    private String reportsToAccountNumber;
    private String continuationFinChrtOfAcctCd;
    private String continuationAccountNumber;
    private String endowmentIncomeAcctFinCoaCd;
    private String endowmentIncomeAccountNumber;
    private String contractControlFinCoaCode;
    private String contractControlAccountNumber;
    private String incomeStreamFinancialCoaCode;
    private String incomeStreamAccountNumber;
    private String indirectCostRcvyFinCoaCode;
    private String indirectCostRecoveryAcctNbr;
    private Integer contractsAndGrantsAccountResponsibilityId;


    public String getChartOfAccountsCode() {
        return chartOfAccountsCode;
    }


    public void setChartOfAccountsCode(String chartOfAccountsCode) {
        this.chartOfAccountsCode = chartOfAccountsCode;
    }


    public String getAccountNumber() {
        return accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public String getAccountName() {
        return accountName;
    }


    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public boolean isAccountsFringesBnftIndicator() {
        return accountsFringesBnftIndicator;
    }


    public void setAccountsFringesBnftIndicator(boolean accountsFringesBnftIndicator) {
        this.accountsFringesBnftIndicator = accountsFringesBnftIndicator;
    }


    public Date getAccountRestrictedStatusDate() {
        return accountRestrictedStatusDate;
    }


    public void setAccountRestrictedStatusDate(Date accountRestrictedStatusDate) {
        this.accountRestrictedStatusDate = accountRestrictedStatusDate;
    }


    public String getAccountCityName() {
        return accountCityName;
    }


    public void setAccountCityName(String accountCityName) {
        this.accountCityName = accountCityName;
    }


    public String getAccountStateCode() {
        return accountStateCode;
    }


    public void setAccountStateCode(String accountStateCode) {
        this.accountStateCode = accountStateCode;
    }


    public String getAccountStreetAddress() {
        return accountStreetAddress;
    }


    public void setAccountStreetAddress(String accountStreetAddress) {
        this.accountStreetAddress = accountStreetAddress;
    }


    public String getAccountZipCode() {
        return accountZipCode;
    }


    public void setAccountZipCode(String accountZipCode) {
        this.accountZipCode = accountZipCode;
    }


    public Date getAccountCreateDate() {
        return accountCreateDate;
    }


    public void setAccountCreateDate(Date accountCreateDate) {
        this.accountCreateDate = accountCreateDate;
    }


    public Date getAccountEffectiveDate() {
        return accountEffectiveDate;
    }


    public void setAccountEffectiveDate(Date accountEffectiveDate) {
        this.accountEffectiveDate = accountEffectiveDate;
    }


    public Date getAccountExpirationDate() {
        return accountExpirationDate;
    }


    public void setAccountExpirationDate(Date accountExpirationDate) {
        this.accountExpirationDate = accountExpirationDate;
    }


    public String getAcctIndirectCostRcvyTypeCd() {
        return acctIndirectCostRcvyTypeCd;
    }


    public void setAcctIndirectCostRcvyTypeCd(String acctIndirectCostRcvyTypeCd) {
        this.acctIndirectCostRcvyTypeCd = acctIndirectCostRcvyTypeCd;
    }


    public String getAcctCustomIndCstRcvyExclCd() {
        return acctCustomIndCstRcvyExclCd;
    }


    public void setAcctCustomIndCstRcvyExclCd(String acctCustomIndCstRcvyExclCd) {
        this.acctCustomIndCstRcvyExclCd = acctCustomIndCstRcvyExclCd;
    }


    public String getFinancialIcrSeriesIdentifier() {
        return financialIcrSeriesIdentifier;
    }


    public void setFinancialIcrSeriesIdentifier(String financialIcrSeriesIdentifier) {
        this.financialIcrSeriesIdentifier = financialIcrSeriesIdentifier;
    }


    public boolean isAccountInFinancialProcessingIndicator() {
        return accountInFinancialProcessingIndicator;
    }


    public void setAccountInFinancialProcessingIndicator(
            boolean accountInFinancialProcessingIndicator) {
        this.accountInFinancialProcessingIndicator = accountInFinancialProcessingIndicator;
    }


    public String getBudgetRecordingLevelCode() {
        return budgetRecordingLevelCode;
    }


    public void setBudgetRecordingLevelCode(String budgetRecordingLevelCode) {
        this.budgetRecordingLevelCode = budgetRecordingLevelCode;
    }


    public String getAccountSufficientFundsCode() {
        return accountSufficientFundsCode;
    }


    public void setAccountSufficientFundsCode(String accountSufficientFundsCode) {
        this.accountSufficientFundsCode = accountSufficientFundsCode;
    }


    public boolean isPendingAcctSufficientFundsIndicator() {
        return pendingAcctSufficientFundsIndicator;
    }


    public void setPendingAcctSufficientFundsIndicator(boolean pendingAcctSufficientFundsIndicator) {
        this.pendingAcctSufficientFundsIndicator = pendingAcctSufficientFundsIndicator;
    }


    public boolean isExtrnlFinEncumSufficntFndIndicator() {
        return extrnlFinEncumSufficntFndIndicator;
    }


    public void setExtrnlFinEncumSufficntFndIndicator(boolean extrnlFinEncumSufficntFndIndicator) {
        this.extrnlFinEncumSufficntFndIndicator = extrnlFinEncumSufficntFndIndicator;
    }


    public boolean isIntrnlFinEncumSufficntFndIndicator() {
        return intrnlFinEncumSufficntFndIndicator;
    }


    public void setIntrnlFinEncumSufficntFndIndicator(boolean intrnlFinEncumSufficntFndIndicator) {
        this.intrnlFinEncumSufficntFndIndicator = intrnlFinEncumSufficntFndIndicator;
    }


    public boolean isFinPreencumSufficientFundIndicator() {
        return finPreencumSufficientFundIndicator;
    }


    public void setFinPreencumSufficientFundIndicator(boolean finPreencumSufficientFundIndicator) {
        this.finPreencumSufficientFundIndicator = finPreencumSufficientFundIndicator;
    }


    public boolean isFinancialObjectivePrsctrlIndicator() {
        return financialObjectivePrsctrlIndicator;
    }


    public void setFinancialObjectivePrsctrlIndicator(boolean financialObjectivePrsctrlIndicator) {
        this.financialObjectivePrsctrlIndicator = financialObjectivePrsctrlIndicator;
    }


    public String getAccountCfdaNumber() {
        return accountCfdaNumber;
    }


    public void setAccountCfdaNumber(String accountCfdaNumber) {
        this.accountCfdaNumber = accountCfdaNumber;
    }


    public boolean isAccountOffCampusIndicator() {
        return accountOffCampusIndicator;
    }


    public void setAccountOffCampusIndicator(boolean accountOffCampusIndicator) {
        this.accountOffCampusIndicator = accountOffCampusIndicator;
    }


    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    public String getAccountFiscalOfficerSystemIdentifier() {
        return accountFiscalOfficerSystemIdentifier;
    }


    public void setAccountFiscalOfficerSystemIdentifier(String accountFiscalOfficerSystemIdentifier) {
        this.accountFiscalOfficerSystemIdentifier = accountFiscalOfficerSystemIdentifier;
    }


    public String getAccountsSupervisorySystemsIdentifier() {
        return accountsSupervisorySystemsIdentifier;
    }


    public void setAccountsSupervisorySystemsIdentifier(String accountsSupervisorySystemsIdentifier) {
        this.accountsSupervisorySystemsIdentifier = accountsSupervisorySystemsIdentifier;
    }


    public String getAccountManagerSystemIdentifier() {
        return accountManagerSystemIdentifier;
    }


    public void setAccountManagerSystemIdentifier(String accountManagerSystemIdentifier) {
        this.accountManagerSystemIdentifier = accountManagerSystemIdentifier;
    }


    public String getOrganizationCode() {
        return organizationCode;
    }


    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }


    public String getAccountTypeCode() {
        return accountTypeCode;
    }


    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }


    public String getAccountPhysicalCampusCode() {
        return accountPhysicalCampusCode;
    }


    public void setAccountPhysicalCampusCode(String accountPhysicalCampusCode) {
        this.accountPhysicalCampusCode = accountPhysicalCampusCode;
    }


    public String getSubFundGroupCode() {
        return subFundGroupCode;
    }


    public void setSubFundGroupCode(String subFundGroupCode) {
        this.subFundGroupCode = subFundGroupCode;
    }


    public String getFinancialHigherEdFunctionCd() {
        return financialHigherEdFunctionCd;
    }


    public void setFinancialHigherEdFunctionCd(String financialHigherEdFunctionCd) {
        this.financialHigherEdFunctionCd = financialHigherEdFunctionCd;
    }


    public String getAccountRestrictedStatusCode() {
        return accountRestrictedStatusCode;
    }


    public void setAccountRestrictedStatusCode(String accountRestrictedStatusCode) {
        this.accountRestrictedStatusCode = accountRestrictedStatusCode;
    }


    public String getReportsToChartOfAccountsCode() {
        return reportsToChartOfAccountsCode;
    }


    public void setReportsToChartOfAccountsCode(String reportsToChartOfAccountsCode) {
        this.reportsToChartOfAccountsCode = reportsToChartOfAccountsCode;
    }


    public String getReportsToAccountNumber() {
        return reportsToAccountNumber;
    }


    public void setReportsToAccountNumber(String reportsToAccountNumber) {
        this.reportsToAccountNumber = reportsToAccountNumber;
    }


    public String getContinuationFinChrtOfAcctCd() {
        return continuationFinChrtOfAcctCd;
    }


    public void setContinuationFinChrtOfAcctCd(String continuationFinChrtOfAcctCd) {
        this.continuationFinChrtOfAcctCd = continuationFinChrtOfAcctCd;
    }


    public String getContinuationAccountNumber() {
        return continuationAccountNumber;
    }


    public void setContinuationAccountNumber(String continuationAccountNumber) {
        this.continuationAccountNumber = continuationAccountNumber;
    }


    public String getEndowmentIncomeAcctFinCoaCd() {
        return endowmentIncomeAcctFinCoaCd;
    }


    public void setEndowmentIncomeAcctFinCoaCd(String endowmentIncomeAcctFinCoaCd) {
        this.endowmentIncomeAcctFinCoaCd = endowmentIncomeAcctFinCoaCd;
    }


    public String getEndowmentIncomeAccountNumber() {
        return endowmentIncomeAccountNumber;
    }


    public void setEndowmentIncomeAccountNumber(String endowmentIncomeAccountNumber) {
        this.endowmentIncomeAccountNumber = endowmentIncomeAccountNumber;
    }


    public String getContractControlFinCoaCode() {
        return contractControlFinCoaCode;
    }


    public void setContractControlFinCoaCode(String contractControlFinCoaCode) {
        this.contractControlFinCoaCode = contractControlFinCoaCode;
    }


    public String getContractControlAccountNumber() {
        return contractControlAccountNumber;
    }


    public void setContractControlAccountNumber(String contractControlAccountNumber) {
        this.contractControlAccountNumber = contractControlAccountNumber;
    }


    public String getIncomeStreamFinancialCoaCode() {
        return incomeStreamFinancialCoaCode;
    }


    public void setIncomeStreamFinancialCoaCode(String incomeStreamFinancialCoaCode) {
        this.incomeStreamFinancialCoaCode = incomeStreamFinancialCoaCode;
    }


    public String getIncomeStreamAccountNumber() {
        return incomeStreamAccountNumber;
    }


    public void setIncomeStreamAccountNumber(String incomeStreamAccountNumber) {
        this.incomeStreamAccountNumber = incomeStreamAccountNumber;
    }


    public String getIndirectCostRcvyFinCoaCode() {
        return indirectCostRcvyFinCoaCode;
    }


    public void setIndirectCostRcvyFinCoaCode(String indirectCostRcvyFinCoaCode) {
        this.indirectCostRcvyFinCoaCode = indirectCostRcvyFinCoaCode;
    }


    public String getIndirectCostRecoveryAcctNbr() {
        return indirectCostRecoveryAcctNbr;
    }


    public void setIndirectCostRecoveryAcctNbr(String indirectCostRecoveryAcctNbr) {
        this.indirectCostRecoveryAcctNbr = indirectCostRecoveryAcctNbr;
    }


    public Integer getContractsAndGrantsAccountResponsibilityId() {
        return contractsAndGrantsAccountResponsibilityId;
    }


    public void setContractsAndGrantsAccountResponsibilityId(
            Integer contractsAndGrantsAccountResponsibilityId) {
        this.contractsAndGrantsAccountResponsibilityId = contractsAndGrantsAccountResponsibilityId;
    }

    /**
     * This method determines whether the account is expired or not. Note that if Expiration Date is the same as today, then this
     * will return false. It will only return true if the account expiration date is one day earlier than today or earlier. Note
     * that this logic ignores all time components when doing the comparison. It only does the before/after comparison based on date
     * values, not time-values.
     * 
     * @return true or false based on the logic outlined above
     */
    public boolean isExpired() {
        if (this.accountExpirationDate == null) {
            return false;
        }

        return this.isExpired(SpringContext.getBean(DateTimeService.class).getCurrentCalendar());
    }

    /**
     * This method determines whether the account is expired or not. Note that if Expiration Date is the same date as testDate, then
     * this will return false. It will only return true if the account expiration date is one day earlier than testDate or earlier.
     * Note that this logic ignores all time components when doing the comparison. It only does the before/after comparison based on
     * date values, not time-values.
     * 
     * @param testDate - Calendar instance with the date to test the Account's Expiration Date against. This is most commonly set to
     *        today's date.
     * @return true or false based on the logic outlined above
     */
    public boolean isExpired(Calendar testDt) {
        if (this.accountExpirationDate == null) {
            return false;
        }
        Calendar testDate = DateUtils.truncate(testDt, Calendar.DAY_OF_MONTH);
        Calendar acctDate = Calendar.getInstance();
        acctDate.setTime(this.accountExpirationDate);
        acctDate = DateUtils.truncate(acctDate, Calendar.DAY_OF_MONTH);
        // if the Account Expiration Date is before the testDate
        if (acctDate.before(testDate)) {
            return true;
        }
        return false;
    }


}
