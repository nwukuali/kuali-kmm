package org.kuali.ext.mm.businessobject;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialAccount;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialChart;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialOrganization;

@Entity
@Table(name = "MM_CATALOG_RESTRICT_T")
public class CatalogRestriction extends MMPersistableBusinessObjectBase implements
		java.io.Serializable {

    private static final long serialVersionUID = 4342300744222821922L;

    @Id
	@Column(name = "CATALOG_RESTRICT_ID", unique = true, nullable = false, length = 18)
	private String 	catalogRestrictionId;

	@Column(name = "CATALOG_ID")
	private String catalogId;
	
	@Column(name = "RESTRICT_CD", length = 1)
	private String restrictionCode;
	
	@Column(name = "ORG_CD")
	private String organizationCode;
	
	@Column(name = "FIN_COA_CD", length = 2)
    private String finacialChartOfAccountsCode;

    @Column(name = "ACCOUNT_NBR")
    private String accountNumber;
    
    private Catalog catalog;

    private FinancialChart chart;
    private FinancialOrganization organization;
    private FinancialAccount account;

    public void setCatalogRestrictionId(String catalogRestrictionId) {
        this.catalogRestrictionId = catalogRestrictionId;
    }

    public String getCatalogRestrictionId() {
        return catalogRestrictionId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setRestrictionCode(String restrictionCode) {
        this.restrictionCode = restrictionCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public String getRestrictionCode() {
        return restrictionCode;
    }

    public void setFinacialChartOfAccountsCode(String finacialChartOfAccountsCode) {
        this.finacialChartOfAccountsCode = finacialChartOfAccountsCode;
    }

    public String getFinacialChartOfAccountsCode() {
        return finacialChartOfAccountsCode;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setChart(FinancialChart chart) {
        this.chart = chart;
    }

    public FinancialChart getChart() {
        return chart;
    }

    public void setOrganization(FinancialOrganization organization) {
        this.organization = organization;
    }

    public FinancialOrganization getOrganization() {
        return organization;
    }

    public void setAccount(FinancialAccount account) {
        this.account = account;
    }

    public FinancialAccount getAccount() {
        return account;
    }
    
    @Override
    public void beforeInsert(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {
        super.beforeInsert(persistenceBroker);

        if(StringUtils.isNotBlank(this.getAccountNumber())) {
            this.setOrganization(null);
            this.setOrganizationCode("");
        }
    }

    @Override
    public void beforeUpdate(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {
        super.beforeUpdate(persistenceBroker);
        
        if(StringUtils.isNotBlank(this.getAccountNumber())) {
            this.setOrganization(null);
            this.setOrganizationCode("");
        }
    }

    /**
     * toStringMapper
     * @return LinkedHashMap
     */
    @Override
    public LinkedHashMap toStringMapper() {
        LinkedHashMap propMap = new LinkedHashMap();
        return propMap;
    }


}
