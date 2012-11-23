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

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.bo.TransientBusinessObjectBase;

/**
 * Financial sub account class
 */
public class FinancialSubAccount extends TransientBusinessObjectBase implements
	ExternalizableBusinessObject, FinancialSystemComponent {


    private static final long serialVersionUID = 5610733128679013524L;
    private String chartOfAccountsCode;
    private String accountNumber;
    private String subAccountNumber;
    private String subAccountName;
    private boolean active;
    private String financialReportChartCode;
    private String finReportOrganizationCode;
    private String financialReportingCode;

    /**
     * Gets the chartOfAccountsCode property
     *
     * @return Returns the chartOfAccountsCode
     */
    public String getChartOfAccountsCode() {
        return chartOfAccountsCode;
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
        return accountNumber;
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
        return subAccountNumber;
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
     * Gets the subAccountName property
     *
     * @return Returns the subAccountName
     */
    public String getSubAccountName() {
        return subAccountName;
    }

    /**
     * Sets the subAccountName property value
     *
     * @param subAccountName The subAccountName to set
     */
    public void setSubAccountName(String subAccountName) {
        this.subAccountName = subAccountName;
    }

    /**
     * Gets the active property
     *
     * @return Returns the active
     */
    public boolean isActive() {
        return active;
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
     * Gets the financialReportChartCode property
     *
     * @return Returns the financialReportChartCode
     */
    public String getFinancialReportChartCode() {
        return this.financialReportChartCode;
    }

    /**
     * Sets the financialReportChartCode property value
     *
     * @param financialReportChartCode The financialReportChartCode to set
     */
    public void setFinancialReportChartCode(String financialReportChartCode) {
        this.financialReportChartCode = financialReportChartCode;
    }

    /**
     * Gets the finReportOrganizationCode property
     *
     * @return Returns the finReportOrganizationCode
     */
    public String getFinReportOrganizationCode() {
        return this.finReportOrganizationCode;
    }

    /**
     * Sets the finReportOrganizationCode property value
     *
     * @param finReportOrganizationCode The finReportOrganizationCode to set
     */
    public void setFinReportOrganizationCode(String finReportOrganizationCode) {
        this.finReportOrganizationCode = finReportOrganizationCode;
    }

    /**
     * Gets the financialReportingCode property
     *
     * @return Returns the financialReportingCode
     */
    public String getFinancialReportingCode() {
        return this.financialReportingCode;
    }

    /**
     * Sets the financialReportingCode property value
     *
     * @param financialReportingCode The financialReportingCode to set
     */
    public void setFinancialReportingCode(String financialReportingCode) {
        this.financialReportingCode = financialReportingCode;
    }


}
