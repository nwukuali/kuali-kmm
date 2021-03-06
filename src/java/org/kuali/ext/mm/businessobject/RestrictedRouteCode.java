package org.kuali.ext.mm.businessobject;

// Generated May 11, 2009 11:03:40 AM by Hibernate Tools 3.2.4.GA

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RestrictedRouteCode generated by hbm2java
 */
@Entity
@Table(name = "MM_RESTRICTED_ROUTE_CODE_T")
public class RestrictedRouteCode extends MMPersistableBusinessObjectBase {

	/**
     *
     */
    private static final long serialVersionUID = 105529503615579423L;

    @Id
	@Column(name = "RESTRICTED_ROUTE_CD", unique = true, nullable = false, length = 1)
	private String restrictedRouteCd;

	@Column(name = "NM", nullable = false, length = 45)
	private String nm;

	public RestrictedRouteCode() {
	}

	public String getRestrictedRouteCd() {
		return this.restrictedRouteCd;
	}

	public void setRestrictedRouteCd(String restrictedRouteCd) {
		this.restrictedRouteCd = restrictedRouteCd;
	}

	public String getNm() {
		return this.nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
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
