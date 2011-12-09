package org.kuali.ext.mm.businessobject;

// Generated Apr 13, 2009 2:30:37 PM by Hibernate Tools 3.2.4.GA

import java.sql.Date;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialAccount;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialChart;
import org.kuali.ext.mm.integration.coa.businessobject.FinancialOrganization;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KualiDecimal;

/**
 * Markup generated by hbm2java
 */
@Entity
@Table(name = "MM_MARKUP_T")
public class Markup extends MMPersistableBusinessObjectBase implements java.io.Serializable {

    private static final long serialVersionUID = 6985107865970782140L;

    @Id
    @Column(name = "MARKUP_CD", unique = true, nullable = false, length = 12)
    private String markupCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKUP_TYPE_CD")
    private MarkupType markupType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAREHOUSE_CD")
    private Warehouse warehouse;

    // BEGIN OJB
    @Column(name = "WAREHOUSE_CD")
    private String warehouseCd;

    @Column(name = "MARKUP_TYPE_CD")
    private String markupTypeCd;
    // END OJB

    @Column(name = "MARKUP_NM", length = 40)
    private String markupNm;

    @Column(name = "MARKUP_DESC", length = 200)
    private String markupDesc;

    @Column(name = "MARKUP_RT", precision = 8, scale = 3)
    private KualiDecimal markupRt;

    @Column(name = "MARKUP_COA_CD", length = 10)
    private String markupCoaCd;

    @Column(name = "MARKUP_ORG_CD", length = 10)
    private String markupOrg;

    @Column(name = "MARKUP_ACCOUNT_NBR", length = 10)
    private String markupAccountNbr;

    @Column(name = "MARKUP_FIXED_AMT", precision = 8)
    private KualiDecimal markupFixed;

    @Column(name = "MARKUP_BEGIN_DT", length = 7)
    private Date markupBeginDt;

    @Column(name = "MARKUP_END_DT", length = 7)
    private Date markupEndDt;

    @Column(name = "MARKUP_FROM_QTY", precision = 8, scale = 0)
    private Integer markupFromQty;

    @Column(name = "MARKUP_TO_QTY", precision = 8, scale = 0)
    private Integer markupToQty;

    private FinancialChart markupChart;
    private FinancialOrganization markupOrganization;
    private FinancialAccount markupAccount;

    private String catalogGroupCode;
    private String catalogSubgroupId;

    private CatalogGroup catalogGroup;
    private CatalogSubgroup catalogSubgroup;

    public Markup() {
    }

    public String getMarkupCd() {
        return this.markupCd;
    }

    public void setMarkupCd(String markupCd) {
        this.markupCd = markupCd;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    // BEGIN OJB
    public String getWarehouseCd() {
        return warehouseCd;
    }

    public void setWarehouseCd(String warehouseCd) {
        this.warehouseCd = warehouseCd;
    }

    public String getMarkupTypeCd() {
        return markupTypeCd;
    }

    public void setMarkupTypeCd(String markupTypeCd) {
        this.markupTypeCd = markupTypeCd;
    }

    // END OJB

    public String getMarkupNm() {
        return this.markupNm;
    }

    public void setMarkupNm(String markupNm) {
        this.markupNm = markupNm;
    }

    public String getMarkupDesc() {
        return this.markupDesc;
    }

    public void setMarkupDesc(String markupDesc) {
        this.markupDesc = markupDesc;
    }

    public KualiDecimal getMarkupRt() {
        return this.markupRt;
    }

    public void setMarkupRt(KualiDecimal markupRt) {
        this.markupRt = markupRt;
    }

    public String getMarkupCoaCd() {
        return this.markupCoaCd;
    }

    public void setMarkupCoaCd(String markupCoaCd) {
        this.markupCoaCd = markupCoaCd;
    }

    public String getMarkupOrg() {
        return this.markupOrg;
    }

    public void setMarkupOrg(String markupOrg) {
        this.markupOrg = markupOrg;
    }

    public String getMarkupAccountNbr() {
        return this.markupAccountNbr;
    }

    public void setMarkupAccountNbr(String markupAccountNbr) {
        this.markupAccountNbr = markupAccountNbr;
    }

    public KualiDecimal getMarkupFixed() {
        return this.markupFixed;
    }

    public void setMarkupFixed(KualiDecimal markupFixed) {
        this.markupFixed = markupFixed;
    }

    public Date getMarkupBeginDt() {
        return this.markupBeginDt;
    }

    public void setMarkupBeginDt(Date markupBeginDt) {
        this.markupBeginDt = markupBeginDt;
    }

    public Date getMarkupEndDt() {
        return this.markupEndDt;
    }

    public void setMarkupEndDt(Date markupEndDt) {
        this.markupEndDt = markupEndDt;
    }

    public Integer getMarkupFromQty() {
        return this.markupFromQty;
    }

    public void setMarkupFromQty(Integer markupFromQty) {
        this.markupFromQty = markupFromQty;
    }

    public Integer getMarkupToQty() {
        return this.markupToQty;
    }

    public void setMarkupToQty(Integer markupToQty) {
        this.markupToQty = markupToQty;
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

    public FinancialChart getMarkupChart() {
        return markupChart;
    }

    public void setMarkupChart(FinancialChart financialChart) {
        this.markupChart = financialChart;
    }

    public FinancialOrganization getMarkupOrganization() {
        return markupOrganization;
    }

    public void setMarkupOrganization(FinancialOrganization financialOrganization) {
        this.markupOrganization = financialOrganization;
    }

    public FinancialAccount getMarkupAccount() {
        return markupAccount;
    }

    public void setMarkupAccount(FinancialAccount financialAccount) {
        this.markupAccount = financialAccount;
    }

	public String getCatalogGroupCode() {
		return this.catalogGroupCode;
	}

	public void setCatalogGroupCode(String markupGroupCode) {
		this.catalogGroupCode = markupGroupCode;
	}

	public String getCatalogSubgroupId() {
		return this.catalogSubgroupId;
	}

	public void setCatalogSubgroupId(String catalogSubgroupId) {
		this.catalogSubgroupId = catalogSubgroupId;
	}

	public CatalogGroup getCatalogGroup() {
		if(this.catalogGroup == null && StringUtils.isNotBlank(getCatalogGroupCode()))
			 return KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(CatalogGroup.class, getCatalogGroupCode());

		return this.catalogGroup;
	}

	public void setCatalogGroup(CatalogGroup catalogGroup) {
		this.catalogGroup = catalogGroup;
	}

	public CatalogSubgroup getCatalogSubgroup() {
		if(this.catalogSubgroup == null && StringUtils.isNotBlank(getCatalogSubgroupId()))
			 return KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(CatalogSubgroup.class, getCatalogSubgroupId());

		return this.catalogSubgroup;
	}

	public void setCatalogSubgroup(CatalogSubgroup catalogSubgroup) {
		this.catalogSubgroup = catalogSubgroup;
	}

    /**
     * Gets the markupType property
     * @return Returns the markupType
     */
    public MarkupType getMarkupType() {
        return this.markupType;
    }

    /**
     * Sets the markupType property value
     * @param markupType The markupType to set
     */
    public void setMarkupType(MarkupType markupType) {
        this.markupType = markupType;
    }

}
