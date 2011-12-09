package org.kuali.ext.mm.businessobject;

// Generated Apr 2, 2009 11:18:42 AM by Hibernate Tools 3.2.2.GA


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialChart;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialOrganization;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.util.TypedArrayList;


/**
 * Warehouse generated by hbm2java
 */
@Entity
@Table(name = "MM_WAREHOUSE_T")
public class Warehouse extends MMPersistableBusinessObjectBase implements java.io.Serializable {

    private static final long serialVersionUID = -1952593607463863527L;

    @Id
    @Column(name = "WAREHOUSE_CD", unique = true, nullable = false, length = 2)
    private String warehouseCd;

    @Column(name = "WAREHOUSE_NME", length = 60)
    private String warehouseNme;

//    @Column(name = "ADDRESS_ID", length = 18)
//    private String addressId;

    @Column(name = "CONSOLIDATION_CD", length = 10)
    private String consolidationCd;

    @Column(name = "BUYOUT_IND", length = 1)
    private boolean buyoutInd;

    @Column(name = "DEFAULT_MARKUP_CD", length = 12)
    private String defaultMarkupCode;

    @Column(name = "RESALE_PERMIT_NBR", length = 20)
    private String resalePermitNbr;

    @Column(name = "BILLING_PROFILE_ID", length = 18)
    private String billingProfileId;

    @Column(name = "DEFAULT_FIN_COA_CD", length = 2)
    private String defaultChartCode;

    @Column(name = "DEFAULT_ORG_CD", length = 18)
    private String defaultOrgCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouse")
    private List<Zone> zones = new ArrayList<Zone>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouseCd")
    private List<WarehouseAccounts> warehouseAccounts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouseCd")
    private List<WarehouseObject> warehouseObjects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_MARKUP_CD", nullable = false, insertable = false, updatable = false)
    private Markup markup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BILLING_PROFILE_ID", nullable = false, insertable = false, updatable = false)
    private Profile billingProfile;

    private FinancialChart defaultChart;
    private FinancialOrganization defaultOrganization;

    public Warehouse() {
        this.warehouseAccounts = new TypedArrayList(WarehouseAccounts.class);
        this.warehouseObjects = new TypedArrayList(WarehouseObject.class);
    }

    public String getWarehouseCd() {
        return this.warehouseCd;
    }

    public void setWarehouseCd(String warehouseCd) {
        this.warehouseCd = warehouseCd;
    }

    public String getWarehouseNme() {
        return this.warehouseNme;
    }

    public void setWarehouseNme(String warehouseNme) {
        this.warehouseNme = warehouseNme;
    }

    public String getConsolidationCd() {
        return this.consolidationCd;
    }

    public void setConsolidationCd(String consolidationCd) {
        this.consolidationCd = consolidationCd;
    }

    public boolean getBuyoutInd() {
        return this.buyoutInd;
    }

    public void setBuyoutInd(boolean buyoutInd) {
        this.buyoutInd = buyoutInd;
    }

    public String getDefaultMarkupCode() {
        return this.defaultMarkupCode;
    }

    public void setDefaultMarkupCode(String defaultMarkupCode) {
        this.defaultMarkupCode = defaultMarkupCode;
    }

    public String getResalePermitNbr() {
        return this.resalePermitNbr;
    }

    public void setResalePermitNbr(String resalePermitNbr) {
        this.resalePermitNbr = resalePermitNbr;
    }

    public String getBillingProfileId() {
        return this.billingProfileId;
    }

    public void setBillingProfileId(String billingProfileId) {
        this.billingProfileId = billingProfileId;
    }


    /**
     * Gets the billingProfile property
     *
     * @return Returns the billingProfile
     */
    public Profile getBillingProfile() {
        return this.billingProfile;
    }

    /**
     * Sets the billingProfile property value
     *
     * @param billingProfile The billingProfile to set
     */
    public void setBillingProfile(Profile billingProfile) {
        this.billingProfile = billingProfile;
    }

    public List<Zone> getZones() {
        return this.zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public Markup getMarkup() {
        return this.markup;
    }

    public void setMarkup(Markup markup) {
        this.markup = markup;
    }

    /**
     * toStringMapper
     *
     * @return LinkedHashMap
     */
    @Override
    public LinkedHashMap toStringMapper() {
        LinkedHashMap propMap = new LinkedHashMap();
        // TODO: Autogenerated method

        return propMap;
    }

    /**
     * Gets the warehouseAccounts property
     *
     * @return Returns the warehouseAccounts
     */
    public List<WarehouseAccounts> getWarehouseAccounts() {
        return this.warehouseAccounts;
    }

    /**
     * Sets the warehouseAccounts property value
     *
     * @param warehouseAccounts The warehouseAccounts to set
     */
    public void setWarehouseAccounts(List<WarehouseAccounts> warehouseAccounts) {
        this.warehouseAccounts = warehouseAccounts;
    }

    /**
     * Gets the warehouseObjects property
     *
     * @return Returns the warehouseObjects
     */
    public List<WarehouseObject> getWarehouseObjects() {
        return this.warehouseObjects;
    }

    /**
     * Sets the warehouseObjects property value
     *
     * @param warehouseObjects The warehouseObjects to set
     */
    public void setWarehouseObjects(List<WarehouseObject> warehouseObjects) {
        this.warehouseObjects = warehouseObjects;
    }

    public WarehouseAccounts getResaleAccount() {
        if (ObjectUtils.isNull(getWarehouseAccounts())) {
            this.refreshReferenceObject("warehouseAccounts");
        }
        if (getWarehouseAccounts() == null && getWarehouseAccounts().isEmpty()) {
            return null;
        }
        for (WarehouseAccounts acct : getWarehouseAccounts()) {
            if (MMConstants.WAREHOUSE_RESALE_ACCT.equals(acct.getWarehouseAccountTypeCode())) {
                return acct;
            }
        }
        return null;
    }

    public WarehouseAccounts getTaxLiabilityAccount() {
        if (ObjectUtils.isNull(getWarehouseAccounts())) {
            this.refreshReferenceObject("warehouseAccounts");
        }
        if (getWarehouseAccounts() == null && getWarehouseAccounts().isEmpty()) {
            return null;
        }
        for (WarehouseAccounts acct : getWarehouseAccounts()) {
            if (MMConstants.WAREHOUSE_TAX_ACCT.equals(acct.getWarehouseAccountTypeCode())) {
                return acct;
            }
        }
        return null;
    }

    public WarehouseAccounts getCostOfGoodsAccount() {
        if (ObjectUtils.isNull(getWarehouseAccounts())) {
            this.refreshReferenceObject("warehouseAccounts");
        }
        if (getWarehouseAccounts() == null && getWarehouseAccounts().isEmpty()) {
            return null;
        }
        for (WarehouseAccounts acct : getWarehouseAccounts()) {
            if (MMConstants.WAREHOUSE_COST_GOODS_ACCT.equals(acct.getWarehouseAccountTypeCode())) {
                return acct;
            }
        }
        return null;
    }

    public WarehouseAccounts getIncomeAccount() {
        if (ObjectUtils.isNull(getWarehouseAccounts())) {
            this.refreshReferenceObject("warehouseAccounts");
        }
        if (getWarehouseAccounts() == null && getWarehouseAccounts().isEmpty()) {
            return null;
        }
        for (WarehouseAccounts acct : getWarehouseAccounts()) {
            if (MMConstants.INCOME_ACCT.equals(acct.getWarehouseAccountTypeCode())) {
                return acct;
            }
        }
        return null;
    }

    /**
     * @see org.kuali.rice.kns.bo.PersistableBusinessObjectBase#buildListOfDeletionAwareLists()
     */
    @Override
    public List buildListOfDeletionAwareLists() {
        List<List> buildListOfDeletionAwareLists = super.buildListOfDeletionAwareLists();
        buildListOfDeletionAwareLists.add(getWarehouseAccounts());
        buildListOfDeletionAwareLists.add(getWarehouseObjects());
        return buildListOfDeletionAwareLists;
    }

    /**
     * Gets the defaultChartCode property
     *
     * @return Returns the defaultChartCode
     */
    public String getDefaultChartCode() {
        return this.defaultChartCode;
    }

    /**
     * Sets the defaultChartCode property value
     *
     * @param defaultChartCode The defaultChartCode to set
     */
    public void setDefaultChartCode(String defaultChartCode) {
        this.defaultChartCode = defaultChartCode;
    }

    /**
     * Gets the defaultOrgCode property
     *
     * @return Returns the defaultOrgCode
     */
    public String getDefaultOrgCode() {
        return this.defaultOrgCode;
    }

    /**
     * Sets the defaultOrgCode property value
     *
     * @param defaultOrgCode The defaultOrgCode to set
     */
    public void setDefaultOrgCode(String defaultOrgCode) {
        this.defaultOrgCode = defaultOrgCode;
    }

    /**
     * Gets the financialChart property
     *
     * @return Returns the financialChart
     */
    public FinancialChart getDefaultChart() {
        return this.defaultChart;
    }

    /**
     * Sets the financialChart property value
     *
     * @param financialChart The financialChart to set
     */
    public void setDefaultChart(FinancialChart financialChart) {
        this.defaultChart = financialChart;
    }

    /**
     * Gets the financialOrganization property
     *
     * @return Returns the financialOrganization
     */
    public FinancialOrganization getDefaultOrganization() {
        return this.defaultOrganization;
    }

    /**
     * Sets the financialOrganization property value
     *
     * @param financialOrganization The financialOrganization to set
     */
    public void setDefaultOrganization(FinancialOrganization financialOrganization) {
        this.defaultOrganization = financialOrganization;
    }

}
