package org.kuali.ext.mm.businessobject;

// Generated Mar 3, 2009 10:13:32 AM by Hibernate Tools 3.2.2.GA

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Cycle generated by hbm2java
 */
@Entity
@Table(name = "MM_STOCK_TYPE_T")
public class StockType extends MMPersistableBusinessObjectBase implements
		java.io.Serializable {

	private static final long serialVersionUID = -2524328427681488463L;

	@Id
	@Column(name = "STOCK_TYPE_CD", unique = true, nullable = false, length = 2)
	private String stockTypeCode;

	@Column(name = "NM", length = 45)
	private String stockTypeName;

	public StockType() {
	}

	public StockType(String stockTypeCode) {
		this.stockTypeCode = stockTypeCode;
	}

	public StockType(String stockTypeCode, String stockTypeName) {
		super();
		this.stockTypeCode = stockTypeCode;
		this.stockTypeName = stockTypeName;
	}

	public String getStockTypeCode() {
		return this.stockTypeCode;
	}

	public void setStockTypeCode(String stockTypeCode) {
		this.stockTypeCode = stockTypeCode;
	}

	public String getStockTypeName() {
		return this.stockTypeName;
	}

	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
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
