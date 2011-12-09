package org.kuali.ext.mm.businessobject;

// Generated Apr 16, 2009 10:01:00 AM by Hibernate Tools 3.2.4.GA

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SearchLog generated by hbm2java
 */
@Entity
@Table(name = "MM_SEARCH_LOG_T")
public class SearchLog extends MMPersistableBusinessObjectBase implements
		java.io.Serializable {

	private static final long serialVersionUID = 9148981768765916946L;

	@Id
	@Column(name = "CUSTOMER_ID", unique = true, nullable = false, length = 12)
	private String customerId;

	@Column(name = "SEARCH_FLD", nullable = false, length = 45)
	private String searchFld;

	@Column(name = "ITEM_NBR", nullable = false, length = 12)
	private String itemNbr;

	public SearchLog() {
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSearchFld() {
		return this.searchFld;
	}

	public void setSearchFld(String searchFld) {
		this.searchFld = searchFld;
	}

	public String getItemNbr() {
		return this.itemNbr;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
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
