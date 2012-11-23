package org.kuali.ext.mm.businessobject;

// Generated Apr 16, 2009 10:01:00 AM by Hibernate Tools 3.2.4.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Clob;

/**
 * XmlPurchaseRequest generated by hbm2java
 */
@Entity
@Table(name = "MM_XML_PURCHASE_REQUEST_T")
public class XmlPurchaseRequest extends MMPersistableBusinessObjectBase
		implements java.io.Serializable {

	private static final long serialVersionUID = -5842796136704833707L;
	/* BEGIN JPA
	   @EmbeddedId

	   @AttributeOverrides( {
	       @AttributeOverride(name="keyId", column=@Column(name="KEY_ID", nullable=false, length=100) ),
	       @AttributeOverride(name="orderId", column=@Column(name="ORDER_ID", nullable=false, precision=8, scale=0) ),
	       @AttributeOverride(name="customerId", column=@Column(name="CUSTOMER_ID", nullable=false, length=12) ) } )
	private XmlPurchaseRequestId id;
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

	@Column(name = "SUPPLIER_AUXILARY_ID", length = 28)
	private String supplierAuxilaryId;

	@Column(name = "PURCHASE_REQUEST_XML")
	private Clob purchaseRequestXml;

	public XmlPurchaseRequest() {
	}

	/* BEGIN JPA
	 public XmlPurchaseRequestId getId() {
	 return this.id;
	 }

	 public void setId(XmlPurchaseRequestId id) {
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
	public String getSupplierAuxilaryId() {
		return this.supplierAuxilaryId;
	}

	public void setSupplierAuxilaryId(String supplierAuxilaryId) {
		this.supplierAuxilaryId = supplierAuxilaryId;
	}

	public Clob getPurchaseRequestXml() {
		return this.purchaseRequestXml;
	}

	public void setPurchaseRequestXml(Clob purchaseRequestXml) {
		this.purchaseRequestXml = purchaseRequestXml;
	}


}
