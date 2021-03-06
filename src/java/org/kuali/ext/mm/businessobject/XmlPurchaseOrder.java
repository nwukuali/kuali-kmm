package org.kuali.ext.mm.businessobject;

// Generated Apr 16, 2009 10:01:00 AM by Hibernate Tools 3.2.4.GA

import java.sql.Clob;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * XmlPurchaseOrder generated by hbm2java
 */
@Entity
@Table(name = "MM_XML_PURCHASE_ORDER_T")
public class XmlPurchaseOrder extends MMPersistableBusinessObjectBase implements
		java.io.Serializable {

	private static final long serialVersionUID = 8034866724665565998L;
	/* BEGIN JPA
	   @EmbeddedId

	   @AttributeOverrides( {
	       @AttributeOverride(name="keyId", column=@Column(name="KEY_ID", nullable=false, length=100) ),
	       @AttributeOverride(name="orderId", column=@Column(name="ORDER_ID", nullable=false, precision=8, scale=0) ),
	       @AttributeOverride(name="customerId", column=@Column(name="CUSTOMER_ID", nullable=false, length=12) ) } )
	private XmlPurchaseOrderId id;
	END JPA */
	//BEGIN OJB
	@Id
	@Column(name = "KEY_ID", nullable = false, length = 100)
	private String keyId;
	@Id
	@Column(name = "ORDER_ID", nullable = false, precision = 8, scale = 0)
	private Integer orderId;
	@Id
	@Column(name = "CUSTOMER_ID", nullable = false, length = 12)
	private String customerId;
	//END OJB

	@Column(name = "PURCHASE_ORDER_URL", length = 250)
	private String purchaseOrderUrl;

	@Column(name = "PURCHASE_ORDER_XML")
	private Clob purchaseOrderXml;

	public XmlPurchaseOrder() {
	}

	/* BEGIN JPA
	 public XmlPurchaseOrderId getId() {
	 return this.id;
	 }

	 public void setId(XmlPurchaseOrderId id) {
	 this.id = id;
	 }
	 END JPA */
	//BEGIN OJB
	public String getKeyId() {
		return this.keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	//END OJB
	public String getPurchaseOrderUrl() {
		return this.purchaseOrderUrl;
	}

	public void setPurchaseOrderUrl(String purchaseOrderUrl) {
		this.purchaseOrderUrl = purchaseOrderUrl;
	}

	public Clob getPurchaseOrderXml() {
		return this.purchaseOrderXml;
	}

	public void setPurchaseOrderXml(Clob purchaseOrderXml) {
		this.purchaseOrderXml = purchaseOrderXml;
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
