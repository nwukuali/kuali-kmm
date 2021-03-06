package org.kuali.ext.mm.businessobject;

// Generated May 11, 2009 11:03:40 AM by Hibernate Tools 3.2.4.GA

import java.sql.Date;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CatalogPndDoc generated by hbm2java
 */
@Entity
@Table(name = "MM_CATALOG_PND_DOC_T")
public class CatalogPndDoc extends MMPersistableBusinessObjectBase {

	/**
     *
     */
    private static final long serialVersionUID = 4941715010029794563L;

    @Id
	@Column(name = "FDOC_NBR", unique = true, nullable = false, length = 36)
	private String fdocNbr;

	@Column(name = "CATALOG_CD", nullable = false, length = 2)
	private String catalogCd;

	@Column(name = "CATALOG_DESC", length = 200)
	private String catalogDesc;
	@Temporal(TemporalType.DATE)
	@Column(name = "CATALOG_BEGIN_DT", length = 7)
	private Date catalogBeginDt;
	@Temporal(TemporalType.DATE)
	@Column(name = "CATALOG_END_DT", length = 7)
	private Date catalogEndDt;

	@Column(name = "PRIORITY_NBR", precision = 8, scale = 0)
	private Integer priorityNbr;

	@Column(name = "AGREEMENT_NBR", precision = 9, scale = 0)
	private Integer agreementNbr;

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catalogPndDoc")
//	private List<CatalogItemPnd> catalogItemPnds = new ArrayList<CatalogItemPnd>();

	public CatalogPndDoc() {
	}

	public String getFdocNbr() {
		return this.fdocNbr;
	}

	public void setFdocNbr(String fdocNbr) {
		this.fdocNbr = fdocNbr;
	}

	public String getCatalogCd() {
		return this.catalogCd;
	}

	public void setCatalogCd(String catalogCd) {
		this.catalogCd = catalogCd;
	}

	public String getCatalogDesc() {
		return this.catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public Date getCatalogBeginDt() {
		return this.catalogBeginDt;
	}

	public void setCatalogBeginDt(Date catalogBeginDt) {
		this.catalogBeginDt = catalogBeginDt;
	}

	public Date getCatalogEndDt() {
		return this.catalogEndDt;
	}

	public void setCatalogEndDt(Date catalogEndDt) {
		this.catalogEndDt = catalogEndDt;
	}

	public Integer getPriorityNbr() {
		return this.priorityNbr;
	}

	public void setPriorityNbr(Integer priorityNbr) {
		this.priorityNbr = priorityNbr;
	}

	public Integer getAgreementNbr() {
		return this.agreementNbr;
	}

	public void setAgreementNbr(Integer agreementNbr) {
		this.agreementNbr = agreementNbr;
	}

	/**
	 * toStringMapper
	 * @return LinkedHashMap
	 */
	@Override
    public LinkedHashMap toStringMapper() {
		LinkedHashMap propMap = new LinkedHashMap();
		//TODO:  Autogenerated method

		return propMap;
	}

}
