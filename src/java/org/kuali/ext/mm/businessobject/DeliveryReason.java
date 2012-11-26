package org.kuali.ext.mm.businessobject;

// Generated Apr 13, 2009 2:30:37 PM by Hibernate Tools 3.2.4.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DeliveryReason generated by hbm2java
 */
@Entity
@Table(name = "MM_DELIVERY_REASON_T")
public class DeliveryReason extends MMPersistableBusinessObjectBase implements
		java.io.Serializable {

	private static final long serialVersionUID = -3561240364494539040L;

	@Id
	@Column(name = "DELIVERY_REASON_CD", unique = true, nullable = false, length = 4)
	private String deliveryReasonCode;

	@Column(name = "NM", length = 45)
	private String deliveryReasonName;

	public DeliveryReason() {
	}

	public String getDeliveryReasonCode() {
		return this.deliveryReasonCode;
	}

	public void setDeliveryReasonCode(String deliveryReasonCode) {
		this.deliveryReasonCode = deliveryReasonCode;
	}

	public String getDeliveryReasonName() {
		return this.deliveryReasonName;
	}

	public void setDeliveryReasonName(String deliveryReasonName) {
		this.deliveryReasonName = deliveryReasonName;
	}


}
