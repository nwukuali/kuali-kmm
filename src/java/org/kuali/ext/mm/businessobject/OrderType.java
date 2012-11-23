package org.kuali.ext.mm.businessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Order Type
 */
@Entity
@Table(name = "MM_ORDER_TYPE_T")
public class OrderType extends MMPersistableBusinessObjectBase implements
		java.io.Serializable {

	private static final long serialVersionUID = -2311374549164867937L;

	@Id
	@Column(name = "ORDER_TYPE_CD", unique = true, nullable = false, length = 6)
	private String orderTypeCode;

	@Column(name = "NM", length = 45)
	private String orderTypeName;

	public OrderType() {
	}

	public OrderType(String orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}

	public OrderType(String orderTypeCode, String orderTypeName) {
		super();
		this.orderTypeCode = orderTypeCode;
		this.orderTypeName = orderTypeName;
	}

	/**
	 * @return the orderTypeCd
	 */
	public String getOrderTypeCode() {
		return this.orderTypeCode;
	}

	/**
	 * @param orderTypeCd the orderTypeCd to set
	 */
	public void setOrderTypeCode(String orderTypeCd) {
		this.orderTypeCode = orderTypeCd;
	}

	/**
	 * @return the orderTypeDesc
	 */
	public String getOrderTypeName() {
		return this.orderTypeName;
	}

	/**
	 * @param orderTypeDesc the orderTypeDesc to set
	 */
	public void setOrderTypeName(String name) {
		this.orderTypeName = name;
	}


}
