package org.kuali.ext.mm.businessobject;

import org.kuali.ext.mm.integration.coa.businessobject.FinancialAccount;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialChart;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialOrganization;
import org.kuali.ext.mm.util.MMDecimal;

public class OrderAutoLimit extends MMPersistableBusinessObjectBase {

    private static final long serialVersionUID = 1876134900477947537L;

    private Integer autoLimitId;

    private String chartCode;

    private String orgCode;

    private String accountNumber;

    private MMDecimal autoLimitAmount;

    private FinancialChart financialChart;
    private FinancialOrganization financialOrganization;
    private FinancialAccount financialAccount;

    public Integer getAutoLimitId() {
        return autoLimitId;
    }

    public void setAutoLimitId(Integer autoLimitId) {
        this.autoLimitId = autoLimitId;
    }

    public String getChartCode() {
        return chartCode;
    }

    public void setChartCode(String chartCode) {
        this.chartCode = chartCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public MMDecimal getAutoLimitAmount() {
        return autoLimitAmount;
    }

    public void setAutoLimitAmount(MMDecimal autoLimitAmount) {
        this.autoLimitAmount = autoLimitAmount;
    }

    /**
     * Gets the financialChart property
     * 
     * @return Returns the financialChart
     */
    public FinancialChart getFinancialChart() {
        return this.financialChart;
    }

    /**
     * Sets the financialChart property value
     * 
     * @param financialChart The financialChart to set
     */
    public void setFinancialChart(FinancialChart financialChart) {
        this.financialChart = financialChart;
    }

    /**
     * Gets the financialOrganization property
     * 
     * @return Returns the financialOrganization
     */
    public FinancialOrganization getFinancialOrganization() {
        return this.financialOrganization;
    }

    /**
     * Sets the financialOrganization property value
     * 
     * @param financialOrganization The financialOrganization to set
     */
    public void setFinancialOrganization(FinancialOrganization financialOrganization) {
        this.financialOrganization = financialOrganization;
    }

    /**
     * Gets the financialAccount property
     * 
     * @return Returns the financialAccount
     */
    public FinancialAccount getFinancialAccount() {
        return this.financialAccount;
    }

    /**
     * Sets the financialAccount property value
     * 
     * @param financialAccount The financialAccount to set
     */
    public void setFinancialAccount(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
    }

}
