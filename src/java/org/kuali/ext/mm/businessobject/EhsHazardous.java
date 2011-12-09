package org.kuali.ext.mm.businessobject;

// Generated Apr 2, 2009 11:05:56 AM by Hibernate Tools 3.2.2.GA

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EhsHazardous generated by hbm2java
 */
@Entity
@Table(name = "MM_EHS_HAZARDOUS_T")
public class EhsHazardous extends MMPersistableBusinessObjectBase implements
		java.io.Serializable {

	private static final long serialVersionUID = -3630637973639961005L;

	@Id
	@Column(name = "EHS_HAZARDOUS_CD", unique = true, nullable = false, length = 3)
	private String ehsHazardousCd;

	@Column(name = "NM", length = 45)
	private String ehsHazardousDesc;

	public EhsHazardous() {
	}

	public EhsHazardous(String ehsHazardousCd) {
		this.ehsHazardousCd = ehsHazardousCd;
	}

	public EhsHazardous(String ehsHazardousCd, String ehsHazardousDesc) {
		this.ehsHazardousCd = ehsHazardousCd;
		this.ehsHazardousDesc = ehsHazardousDesc;
	}

	public String getEhsHazardousCd() {
		return this.ehsHazardousCd;
	}

	public void setEhsHazardousCd(String ehsHazardousCd) {
		this.ehsHazardousCd = ehsHazardousCd;
	}

	public String getEhsHazardousDesc() {
		return this.ehsHazardousDesc;
	}

	public void setEhsHazardousDesc(String ehsHazardousDesc) {
		this.ehsHazardousDesc = ehsHazardousDesc;
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
