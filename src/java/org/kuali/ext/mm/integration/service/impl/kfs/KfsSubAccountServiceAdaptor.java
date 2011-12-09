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

import javax.xml.namespace.QName;

import org.kuali.ext.mm.integration.coa.businessobject.FinancialSubAccount;
import org.kuali.ext.mm.integration.service.FinancialSubAccountService;
import org.kuali.kfs.coa.businessobject.SubAccount;
import org.kuali.kfs.coa.service.SubAccountService;

/**
 * {@link FinancialSubAccountService}
 */
public class KfsSubAccountServiceAdaptor extends KfsServiceAdaptor<SubAccountService> implements
        FinancialSubAccountService {


    public KfsSubAccountServiceAdaptor(QName serviceName) {
        super(serviceName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialSubAccountService#getByPrimaryId(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public FinancialSubAccount getByPrimaryId(String chartOfAccountsCode, String accountNumber,
            String subAccountNumber) {
        SubAccount source = getService().getByPrimaryId(chartOfAccountsCode, accountNumber,
                subAccountNumber);
        if (source == null) {
            return null;
        }
        FinancialSubAccount target = new FinancialSubAccount();
        adapt(source, target);
        return target;
    }

    protected void adapt(SubAccount source, FinancialSubAccount target) {
        target.setChartOfAccountsCode(source.getChartOfAccountsCode());
        target.setAccountNumber(source.getAccountNumber());
        target.setSubAccountNumber(source.getSubAccountNumber());
        target.setSubAccountName(source.getSubAccountName());
        target.setActive(source.isActive());
        target.setFinancialReportChartCode(source.getFinancialReportChartCode());
        target.setFinReportOrganizationCode(source.getFinReportOrganizationCode());
        target.setFinancialReportingCode(source.getFinancialReportingCode());
    }
}
