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
 * Financial chart of account objects
 */
public class FinancialChart extends TransientBusinessObjectBase implements
	ExternalizableBusinessObject, FinancialSystemComponent {

    private static final long serialVersionUID = 6336149302112999579L;
    private String chartOfAccountsCode;
    private String finChartOfAccountDescription;
    private boolean active;
    private String finCoaManagerPrincipalId;
    private String reportsToChartOfAccountsCode;
    private String finAccountsPayableObjectCode;
    private String finExternalEncumbranceObjCd;
    private String finPreEncumbranceObjectCode;
    private String financialCashObjectCode;
    private String icrIncomeFinancialObjectCode;
    private String finAccountsReceivableObjCode;
    private String finInternalEncumbranceObjCd;
    private String icrExpenseFinancialObjectCd;
    private String incBdgtEliminationsFinObjCd;
    private String expBdgtEliminationsFinObjCd;
    private String fundBalanceObjectCode;

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
     * Gets the finChartOfAccountDescription property
     *
     * @return Returns the finChartOfAccountDescription
     */
    public String getFinChartOfAccountDescription() {
        return finChartOfAccountDescription;
    }

    /**
     * Sets the finChartOfAccountDescription property value
     *
     * @param finChartOfAccountDescription The finChartOfAccountDescription to set
     */
    public void setFinChartOfAccountDescription(String finChartOfAccountDescription) {
        this.finChartOfAccountDescription = finChartOfAccountDescription;
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
     * Gets the finCoaManagerPrincipalId property
     *
     * @return Returns the finCoaManagerPrincipalId
     */
    public String getFinCoaManagerPrincipalId() {
        return this.finCoaManagerPrincipalId;
    }

    /**
     * Sets the finCoaManagerPrincipalId property value
     *
     * @param finCoaManagerPrincipalId The finCoaManagerPrincipalId to set
     */
    public void setFinCoaManagerPrincipalId(String finCoaManagerPrincipalId) {
        this.finCoaManagerPrincipalId = finCoaManagerPrincipalId;
    }

    /**
     * Gets the reportsToChartOfAccountsCode property
     *
     * @return Returns the reportsToChartOfAccountsCode
     */
    public String getReportsToChartOfAccountsCode() {
        return this.reportsToChartOfAccountsCode;
    }

    /**
     * Sets the reportsToChartOfAccountsCode property value
     *
     * @param reportsToChartOfAccountsCode The reportsToChartOfAccountsCode to set
     */
    public void setReportsToChartOfAccountsCode(String reportsToChartOfAccountsCode) {
        this.reportsToChartOfAccountsCode = reportsToChartOfAccountsCode;
    }

    /**
     * Gets the finAccountsPayableObjectCode property
     *
     * @return Returns the finAccountsPayableObjectCode
     */
    public String getFinAccountsPayableObjectCode() {
        return this.finAccountsPayableObjectCode;
    }

    /**
     * Sets the finAccountsPayableObjectCode property value
     *
     * @param finAccountsPayableObjectCode The finAccountsPayableObjectCode to set
     */
    public void setFinAccountsPayableObjectCode(String finAccountsPayableObjectCode) {
        this.finAccountsPayableObjectCode = finAccountsPayableObjectCode;
    }

    /**
     * Gets the finExternalEncumbranceObjCd property
     *
     * @return Returns the finExternalEncumbranceObjCd
     */
    public String getFinExternalEncumbranceObjCd() {
        return this.finExternalEncumbranceObjCd;
    }

    /**
     * Sets the finExternalEncumbranceObjCd property value
     *
     * @param finExternalEncumbranceObjCd The finExternalEncumbranceObjCd to set
     */
    public void setFinExternalEncumbranceObjCd(String finExternalEncumbranceObjCd) {
        this.finExternalEncumbranceObjCd = finExternalEncumbranceObjCd;
    }

    /**
     * Gets the finPreEncumbranceObjectCode property
     *
     * @return Returns the finPreEncumbranceObjectCode
     */
    public String getFinPreEncumbranceObjectCode() {
        return this.finPreEncumbranceObjectCode;
    }

    /**
     * Sets the finPreEncumbranceObjectCode property value
     *
     * @param finPreEncumbranceObjectCode The finPreEncumbranceObjectCode to set
     */
    public void setFinPreEncumbranceObjectCode(String finPreEncumbranceObjectCode) {
        this.finPreEncumbranceObjectCode = finPreEncumbranceObjectCode;
    }

    /**
     * Gets the financialCashObjectCode property
     *
     * @return Returns the financialCashObjectCode
     */
    public String getFinancialCashObjectCode() {
        return this.financialCashObjectCode;
    }

    /**
     * Sets the financialCashObjectCode property value
     *
     * @param financialCashObjectCode The financialCashObjectCode to set
     */
    public void setFinancialCashObjectCode(String financialCashObjectCode) {
        this.financialCashObjectCode = financialCashObjectCode;
    }

    /**
     * Gets the icrIncomeFinancialObjectCode property
     *
     * @return Returns the icrIncomeFinancialObjectCode
     */
    public String getIcrIncomeFinancialObjectCode() {
        return this.icrIncomeFinancialObjectCode;
    }

    /**
     * Sets the icrIncomeFinancialObjectCode property value
     *
     * @param icrIncomeFinancialObjectCode The icrIncomeFinancialObjectCode to set
     */
    public void setIcrIncomeFinancialObjectCode(String icrIncomeFinancialObjectCode) {
        this.icrIncomeFinancialObjectCode = icrIncomeFinancialObjectCode;
    }

    /**
     * Gets the finAccountsReceivableObjCode property
     *
     * @return Returns the finAccountsReceivableObjCode
     */
    public String getFinAccountsReceivableObjCode() {
        return this.finAccountsReceivableObjCode;
    }

    /**
     * Sets the finAccountsReceivableObjCode property value
     *
     * @param finAccountsReceivableObjCode The finAccountsReceivableObjCode to set
     */
    public void setFinAccountsReceivableObjCode(String finAccountsReceivableObjCode) {
        this.finAccountsReceivableObjCode = finAccountsReceivableObjCode;
    }

    /**
     * Gets the finInternalEncumbranceObjCd property
     *
     * @return Returns the finInternalEncumbranceObjCd
     */
    public String getFinInternalEncumbranceObjCd() {
        return this.finInternalEncumbranceObjCd;
    }

    /**
     * Sets the finInternalEncumbranceObjCd property value
     *
     * @param finInternalEncumbranceObjCd The finInternalEncumbranceObjCd to set
     */
    public void setFinInternalEncumbranceObjCd(String finInternalEncumbranceObjCd) {
        this.finInternalEncumbranceObjCd = finInternalEncumbranceObjCd;
    }

    /**
     * Gets the icrExpenseFinancialObjectCd property
     *
     * @return Returns the icrExpenseFinancialObjectCd
     */
    public String getIcrExpenseFinancialObjectCd() {
        return this.icrExpenseFinancialObjectCd;
    }

    /**
     * Sets the icrExpenseFinancialObjectCd property value
     *
     * @param icrExpenseFinancialObjectCd The icrExpenseFinancialObjectCd to set
     */
    public void setIcrExpenseFinancialObjectCd(String icrExpenseFinancialObjectCd) {
        this.icrExpenseFinancialObjectCd = icrExpenseFinancialObjectCd;
    }

    /**
     * Gets the incBdgtEliminationsFinObjCd property
     *
     * @return Returns the incBdgtEliminationsFinObjCd
     */
    public String getIncBdgtEliminationsFinObjCd() {
        return this.incBdgtEliminationsFinObjCd;
    }

    /**
     * Sets the incBdgtEliminationsFinObjCd property value
     *
     * @param incBdgtEliminationsFinObjCd The incBdgtEliminationsFinObjCd to set
     */
    public void setIncBdgtEliminationsFinObjCd(String incBdgtEliminationsFinObjCd) {
        this.incBdgtEliminationsFinObjCd = incBdgtEliminationsFinObjCd;
    }

    /**
     * Gets the expBdgtEliminationsFinObjCd property
     *
     * @return Returns the expBdgtEliminationsFinObjCd
     */
    public String getExpBdgtEliminationsFinObjCd() {
        return this.expBdgtEliminationsFinObjCd;
    }

    /**
     * Sets the expBdgtEliminationsFinObjCd property value
     *
     * @param expBdgtEliminationsFinObjCd The expBdgtEliminationsFinObjCd to set
     */
    public void setExpBdgtEliminationsFinObjCd(String expBdgtEliminationsFinObjCd) {
        this.expBdgtEliminationsFinObjCd = expBdgtEliminationsFinObjCd;
    }

    /**
     * Gets the fundBalanceObjectCode property
     *
     * @return Returns the fundBalanceObjectCode
     */
    public String getFundBalanceObjectCode() {
        return this.fundBalanceObjectCode;
    }

    /**
     * Sets the fundBalanceObjectCode property value
     *
     * @param fundBalanceObjectCode The fundBalanceObjectCode to set
     */
    public void setFundBalanceObjectCode(String fundBalanceObjectCode) {
        this.fundBalanceObjectCode = fundBalanceObjectCode;
    }



}
