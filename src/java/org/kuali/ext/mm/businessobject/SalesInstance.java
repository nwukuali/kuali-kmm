package org.kuali.ext.mm.businessobject;

// Generated Apr 16, 2009 10:01:00 AM by Hibernate Tools 3.2.4.GA

import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.TypedArrayList;

/**
 * SalesInstance generated by hbm2java
 */
@Entity
@Table(name = "MM_SALES_INSTANCE_T")
public class SalesInstance extends StoresPersistableBusinessObject {

	private static final long serialVersionUID = 1252813065084453794L;
	@Id
	@Column(name = "SALES_INSTANCE_ID", unique = true, nullable = false, precision = 8, scale = 0)
	private Integer salesInstanceId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_STATUS_CD", nullable = false)
	private OrderStatus orderStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_TYPE_CD", nullable = false)
	private OrderType orderType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_DOC_NBR", nullable = false)
	private OrderDocument orderDocument;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_PROFILE_ID", nullable = false)
	private Profile customerProfile;

	//BEGIN OJB
	@Column(name = "ORDER_STATUS_CD", unique = true, nullable = false, length = 4)
	private String orderStatusCd;

	@Column(name = "ORDER_DOC_NBR")
	private String orderDocumentNbr;

	@Column(name = "CUSTOMER_PROFILE_ID", nullable = false, length = 20)
	private String customerProfileId;

	@Column(name = "ORDER_TYPE_CD", nullable = false, length = 6)
	private String orderTypeCd;
	//END OJB

	@Column(name = "ORDER_LINE_TOTAL_AMT")
	private KualiDecimal orderLineTotalAmt;

	@Column(name = "ORDER_TAXABLE_AMT")
	private KualiDecimal orderTaxableAmt;

	@Column(name = "ORDER_TOTAL_AMT")
	private KualiDecimal orderTotalAmt;

	@Column(name = "SALES_INSTANCE_AR_ID", precision = 8, scale = 0)
	private Integer salesInstanceArId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "salesInstance")
	private List<OrderDetail> orderDetails = new TypedArrayList(OrderDetail.class);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "salesInstance")
	private List<PickListLine> pickListLines = new TypedArrayList(PickListLine.class);

	public SalesInstance() {
	}

	public Integer getSalesInstanceId() {
		return this.salesInstanceId;
	}

	public void setSalesInstanceId(Integer orderId) {
		this.salesInstanceId = orderId;
	}

	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}


	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setCustomerProfile(Profile customerProfile) {
		this.customerProfile = customerProfile;
	}

	public Profile getCustomerProfile() {
		return customerProfile;
	}

	//BEGIN OJB
	public void setOrderStatusCd(String orderStatusCd) {
		this.orderStatusCd = orderStatusCd;
	}

	public String getOrderStatusCd() {
		return orderStatusCd;
	}
	public void setOrderDocumentNbr(String orderDocumentNbr) {
		this.orderDocumentNbr = orderDocumentNbr;
	}

	public String getOrderDocumentNbr() {
		return orderDocumentNbr;
	}

	public String getCustomerProfileId() {
		return this.customerProfileId;
	}

	public void setCustomerProfileId(String customerProfileId) {
		this.customerProfileId = customerProfileId;
	}
	//END OJB

	public String getOrderTypeCd() {
		return this.orderTypeCd;
	}

	public void setOrderTypeCd(String orderTypeCd) {
		this.orderTypeCd = orderTypeCd;
	}

	public KualiDecimal getOrderLineTotalAmt() {
		return this.orderLineTotalAmt;
	}

	public void setOrderLineTotalAmt(KualiDecimal orderLineTotalAmt) {
		this.orderLineTotalAmt = orderLineTotalAmt;
	}

	public KualiDecimal getOrderTaxableAmt() {
		return this.orderTaxableAmt;
	}

	public void setOrderTaxableAmt(KualiDecimal orderTaxableAmt) {
		this.orderTaxableAmt = orderTaxableAmt;
	}

	public KualiDecimal getOrderTotalAmt() {
		return this.orderTotalAmt;
	}

	public void setOrderTotalAmt(KualiDecimal orderTotalAmt) {
		this.orderTotalAmt = orderTotalAmt;
	}

	public Integer getSalesInstanceArId() {
		return this.salesInstanceArId;
	}

	public void setSalesInstanceArId(Integer salesInstanceArId) {
		this.salesInstanceArId = salesInstanceArId;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDocument(OrderDocument orderDocument) {
		this.orderDocument = orderDocument;
	}

	public OrderDocument getOrderDocument() {
		return orderDocument;
	}


	public void setPickListLines(List<PickListLine> pickListLines) {
		this.pickListLines = pickListLines;
	}

	public List<PickListLine> getPickListLines() {
		return pickListLines;
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