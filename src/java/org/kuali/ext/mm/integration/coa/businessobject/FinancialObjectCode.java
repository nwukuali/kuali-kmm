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

import java.util.LinkedHashMap;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.kns.bo.ExternalizableBusinessObject;
import org.kuali.rice.kns.bo.TransientBusinessObjectBase;

/**
 *
 */
public class FinancialObjectCode extends TransientBusinessObjectBase implements
        ExternalizableBusinessObject, FinancialSystemComponent {
    private static final long serialVersionUID = 4832556850649108551L;
    private Integer universityFiscalYear;
    private String chartOfAccountsCode;
    private String financialObjectCode;
    private String financialObjectCodeName;
    private String financialObjectCodeShortName;
    private String historicalFinancialObjectCode;
    private boolean active;
    private String financialObjectLevelCode;
    private String reportsToChartOfAccountsCode;
    private String reportsToFinancialObjectCode;
    private String financialObjectTypeCode;
    private String financialObjectSubTypeCode;
    private String financialBudgetAggregationCd;
    private String nextYearFinancialObjectCode;
    private String finObjMandatoryTrnfrelimCd;
    private String financialFederalFundedCode;

    public Integer getUniversityFiscalYear() {
        return universityFiscalYear;
    }

    public void setUniversityFiscalYear(Integer universityFiscalYear) {
        this.universityFiscalYear = universityFiscalYear;
    }

    public String getChartOfAccountsCode() {
        return chartOfAccountsCode;
    }

    public void setChartOfAccountsCode(String chartOfAccountsCode) {
        this.chartOfAccountsCode = chartOfAccountsCode;
    }

    public String getFinancialObjectCode() {
        return financialObjectCode;
    }

    public void setFinancialObjectCode(String financialObjectCode) {
        this.financialObjectCode = financialObjectCode;
    }

    public String getFinancialObjectCodeName() {
        return financialObjectCodeName;
    }

    public void setFinancialObjectCodeName(String financialObjectCodeName) {
        this.financialObjectCodeName = financialObjectCodeName;
    }

    public String getFinancialObjectCodeShortName() {
        return financialObjectCodeShortName;
    }

    public void setFinancialObjectCodeShortName(String financialObjectCodeShortName) {
        this.financialObjectCodeShortName = financialObjectCodeShortName;
    }

    public String getHistoricalFinancialObjectCode() {
        return historicalFinancialObjectCode;
    }

    public void setHistoricalFinancialObjectCode(String historicalFinancialObjectCode) {
        this.historicalFinancialObjectCode = historicalFinancialObjectCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFinancialObjectLevelCode() {
        return financialObjectLevelCode;
    }

    public void setFinancialObjectLevelCode(String financialObjectLevelCode) {
        this.financialObjectLevelCode = financialObjectLevelCode;
    }

    public String getReportsToChartOfAccountsCode() {
        return reportsToChartOfAccountsCode;
    }

    public void setReportsToChartOfAccountsCode(String reportsToChartOfAccountsCode) {
        this.reportsToChartOfAccountsCode = reportsToChartOfAccountsCode;
    }

    public String getReportsToFinancialObjectCode() {
        return reportsToFinancialObjectCode;
    }

    public void setReportsToFinancialObjectCode(String reportsToFinancialObjectCode) {
        this.reportsToFinancialObjectCode = reportsToFinancialObjectCode;
    }

    public String getFinancialObjectTypeCode() {
        return financialObjectTypeCode;
    }

    public void setFinancialObjectTypeCode(String financialObjectTypeCode) {
        this.financialObjectTypeCode = financialObjectTypeCode;
    }

    public String getFinancialObjectSubTypeCode() {
        return financialObjectSubTypeCode;
    }

    public void setFinancialObjectSubTypeCode(String financialObjectSubTypeCode) {
        this.financialObjectSubTypeCode = financialObjectSubTypeCode;
    }

    public String getFinancialBudgetAggregationCd() {
        return financialBudgetAggregationCd;
    }

    public void setFinancialBudgetAggregationCd(String financialBudgetAggregationCd) {
        this.financialBudgetAggregationCd = financialBudgetAggregationCd;
    }

    public String getNextYearFinancialObjectCode() {
        return nextYearFinancialObjectCode;
    }

    public void setNextYearFinancialObjectCode(String nextYearFinancialObjectCode) {
        this.nextYearFinancialObjectCode = nextYearFinancialObjectCode;
    }

    public String getFinObjMandatoryTrnfrelimCd() {
        return finObjMandatoryTrnfrelimCd;
    }

    public void setFinObjMandatoryTrnfrelimCd(String finObjMandatoryTrnfrelimCd) {
        this.finObjMandatoryTrnfrelimCd = finObjMandatoryTrnfrelimCd;
    }

    public String getFinancialFederalFundedCode() {
        return financialFederalFundedCode;
    }

    public void setFinancialFederalFundedCode(String financialFederalFundedCode) {
        this.financialFederalFundedCode = financialFederalFundedCode;
    }

    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    @Override
    protected LinkedHashMap<String, Object> toStringMapper() {
        // TODO Auto-generated method stub
        return null;
    }
}
