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

package org.kuali.ext.mm.integration.service.impl.kfs;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialAccount;
import org.kuali.ext.mm.integration.service.FinancialAccountService;
import org.kuali.kfs.coa.businessobject.Account;
import org.kuali.kfs.coa.service.AccountService;

import javax.xml.namespace.QName;

/**
 * {@link FinancialAccountService}
 */
public class KfsAccountServiceAdaptor extends KfsServiceAdaptor<AccountService> implements
        FinancialAccountService {
    private static final Logger LOG = Logger.getLogger(KfsAccountServiceAdaptor.class);

    /**
     * @param serviceName
     */
    public KfsAccountServiceAdaptor(QName serviceName) {
        super(serviceName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialAccountService#getByPrimaryId(java.lang.String, java.lang.String)
     */
    public FinancialAccount getByPrimaryId(String chartOfAccountsCode, String accountNumber) {
        LOG.debug("Invoking getByPrimaryId");
        Account source = getService().getByPrimaryId(chartOfAccountsCode, accountNumber);
        if (source == null) {
            return null;
        }
        FinancialAccount target = new FinancialAccount();
        adapt(source, target);
        return target;
    }

    /**
     * Adapts from source to target system
     *
     * @param source KFS Source object
     * @param target Target KMM object
     */
    protected void adapt(Account source, FinancialAccount target) {
        target.setChartOfAccountsCode(source.getChartOfAccountsCode());
        target.setAccountNumber(source.getAccountNumber());
        target.setAccountName(source.getAccountName());
        target.setAccountsFringesBnftIndicator(source.isAccountsFringesBnftIndicator());
        target.setAccountRestrictedStatusDate(source.getAccountRestrictedStatusDate());
        target.setAccountCityName(source.getAccountCityName());
        target.setAccountStateCode(source.getAccountStateCode());
        target.setAccountStreetAddress(source.getAccountStreetAddress());
        target.setAccountZipCode(source.getAccountZipCode());
        target.setAccountCreateDate(source.getAccountCreateDate());
        target.setAccountEffectiveDate(source.getAccountEffectiveDate());
        target.setAccountExpirationDate(source.getAccountExpirationDate());
        target.setAcctIndirectCostRcvyTypeCd(source.getAcctIndirectCostRcvyTypeCd());
        target.setAcctCustomIndCstRcvyExclCd(source.getAcctCustomIndCstRcvyExclCd());
        target.setFinancialIcrSeriesIdentifier(source.getFinancialIcrSeriesIdentifier());
        target.setAccountInFinancialProcessingIndicator(source
                .getAccountInFinancialProcessingIndicator());
        target.setBudgetRecordingLevelCode(source.getBudgetRecordingLevelCode());
        target.setAccountSufficientFundsCode(source.getAccountSufficientFundsCode());
        target.setPendingAcctSufficientFundsIndicator(source
                .isPendingAcctSufficientFundsIndicator());
        target.setExtrnlFinEncumSufficntFndIndicator(source.isExtrnlFinEncumSufficntFndIndicator());
        target.setIntrnlFinEncumSufficntFndIndicator(source.isIntrnlFinEncumSufficntFndIndicator());
        target.setFinPreencumSufficientFundIndicator(source.isFinPreencumSufficientFundIndicator());
        target.setFinancialObjectivePrsctrlIndicator(source.isFinancialObjectivePrsctrlIndicator());
        target.setAccountCfdaNumber(source.getAccountCfdaNumber());
        target.setAccountOffCampusIndicator(source.isAccountOffCampusIndicator());
        target.setActive(source.isActive());
        target.setAccountFiscalOfficerSystemIdentifier(source
                .getAccountFiscalOfficerSystemIdentifier());
        target.setAccountsSupervisorySystemsIdentifier(source
                .getAccountsSupervisorySystemsIdentifier());
        target.setAccountManagerSystemIdentifier(source.getAccountManagerSystemIdentifier());
        target.setOrganizationCode(source.getOrganizationCode());
        target.setAccountTypeCode(source.getAccountTypeCode());
        target.setAccountPhysicalCampusCode(source.getAccountPhysicalCampusCode());
        target.setSubFundGroupCode(source.getSubFundGroupCode());
        target.setFinancialHigherEdFunctionCd(source.getFinancialHigherEdFunctionCd());
        target.setAccountRestrictedStatusCode(source.getAccountRestrictedStatusCode());
        target.setReportsToChartOfAccountsCode(source.getReportsToChartOfAccountsCode());
        target.setReportsToAccountNumber(source.getReportsToAccountNumber());
        target.setContinuationFinChrtOfAcctCd(source.getContinuationFinChrtOfAcctCd());
        target.setContinuationAccountNumber(source.getContinuationAccountNumber());
        target.setEndowmentIncomeAcctFinCoaCd(source.getEndowmentIncomeAcctFinCoaCd());
        target.setEndowmentIncomeAccountNumber(source.getEndowmentIncomeAccountNumber());
        target.setContractControlFinCoaCode(source.getContractControlFinCoaCode());
        target.setContractControlAccountNumber(source.getContractControlAccountNumber());
        target.setIncomeStreamFinancialCoaCode(source.getIncomeStreamFinancialCoaCode());
        target.setIncomeStreamAccountNumber(source.getIncomeStreamAccountNumber());
				//TODO: NWU - Find a way of passing list of indirect cost recovery accounts - Currently not used by MM FinancialAccount
//        target.setIndirectCostRcvyFinCoaCode(source.getIndirectCostRcvyFinCoaCode());
//        target.setIndirectCostRecoveryAcctNbr(source.getIndirectCostRecoveryAcctNbr());
        target.setContractsAndGrantsAccountResponsibilityId(source
                .getContractsAndGrantsAccountResponsibilityId());
    }
}
