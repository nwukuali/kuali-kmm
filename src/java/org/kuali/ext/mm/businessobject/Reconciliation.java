package org.kuali.ext.mm.businessobject;

// Generated Apr 16, 2009 10:01:00 AM by Hibernate Tools 3.2.4.GA

import java.sql.Date;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.rice.kns.util.KualiDecimal;

/**
 * Reconciliation generated by hbm2java
 */
@Entity
@Table(name = "MM_RECONCILIATION_T")
public class Reconciliation extends MMPersistableBusinessObjectBase implements
		java.io.Serializable {

	private static final long serialVersionUID = 1121109633923659135L;
	/* BEGIN JPA
	   @EmbeddedId

	   @AttributeOverrides( {
	       @AttributeOverride(name="warehouseCd", column=@Column(name="WAREHOUSE_CD", nullable=false, length=2) ),
	       @AttributeOverride(name="transactionId", column=@Column(name="TRANSACTION_ID", nullable=false, length=15) ) } )
	private ReconciliationId id;
	END JPA */
	//BEGIN OJB
	@Id
	@Column(name = "WAREHOUSE_CD", nullable = false, length = 2)
	private String warehouseCd;
	@Id
	@Column(name = "TRANSACTION_ID", nullable = false, length = 15)
	private String transactionId;
	//END OJB

	@Column(name = "ORDER_ID", nullable = false, precision = 8, scale = 0)
	private Integer orderId;

	@Column(name = "LINE_NBR", nullable = false, precision = 8, scale = 0)
	private Integer lineNbr;

	@Column(name = "ITEM_NBR", nullable = false, length = 12)
	private String itemNbr;

	@Column(name = "ITEM_UNIT_OF_ISSUE_CD", nullable = false, length = 10)
	private String itemUnitOfIssueCd;

	@Column(name = "ITEM_DETAIL_DESC", nullable = false, length = 400)
	private String itemDetailDesc;

	@Column(name = "SHIPPED_ITEM_QTY", nullable = false, precision = 11, scale = 4)
	private KualiDecimal shippedItemQty;

	@Column(name = "ITEM_PRC", nullable = false, scale = 4)
	private KualiDecimal itemPrc;

	@Column(name = "ITEM_TAX", nullable = false, scale = 4)
	private KualiDecimal itemTax;

	@Column(name = "ITEM_TOTAL_AMT", nullable = false, scale = 4)
	private KualiDecimal itemTotalAmt;

	@Column(name = "RECEIVED_CD", length = 1)
	private String receivedCd;

	@Column(name = "RECEIVED_COMMENTS", length = 300)
	private String receivedComments;

	@Column(name = "CUSTOMER_ID", length = 12)
	private String customerId;
	@Temporal(TemporalType.DATE)
	@Column(name = "CHECKIN_DT", length = 7)
	private Date checkinDt;
	@Temporal(TemporalType.DATE)
	@Column(name = "SHIP_DT", length = 7)
	private Date shipDt;

	@Column(name = "MATCH_IND", length = 1)
	private boolean matchInd;

	@Column(name = "GL_PROCESS_IND", length = 1)
	private boolean glProcessInd;

	@Column(name = "INVOICE_NBR", length = 15)
	private String invoiceNbr;

	public Reconciliation() {
	}

	/* BEGIN JPA
	 public ReconciliationId getId() {
	 return this.id;
	 }

	 public void setId(ReconciliationId id) {
	 this.id = id;
	 }
	 END JPA */
	//BEGIN OJB
	public String getWarehouseCd() {
		return this.warehouseCd;
	}

	public void setWarehouseCd(String warehouseCd) {
		this.warehouseCd = warehouseCd;
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	//END OJB
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getLineNbr() {
		return this.lineNbr;
	}

	public void setLineNbr(Integer lineNbr) {
		this.lineNbr = lineNbr;
	}

	public String getItemNbr() {
		return this.itemNbr;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	public String getItemUnitOfIssueCd() {
		return this.itemUnitOfIssueCd;
	}

	public void setItemUnitOfIssueCd(String itemUnitOfIssueCd) {
		this.itemUnitOfIssueCd = itemUnitOfIssueCd;
	}

	public String getItemDetailDesc() {
		return this.itemDetailDesc;
	}

	public void setItemDetailDesc(String itemDetailDesc) {
		this.itemDetailDesc = itemDetailDesc;
	}

	public KualiDecimal getShippedItemQty() {
		return this.shippedItemQty;
	}

	public void setShippedItemQty(KualiDecimal shippedItemQty) {
		this.shippedItemQty = shippedItemQty;
	}

	public KualiDecimal getItemPrc() {
		return this.itemPrc;
	}

	public void setItemPrc(KualiDecimal itemPrc) {
		this.itemPrc = itemPrc;
	}

	public KualiDecimal getItemTax() {
		return this.itemTax;
	}

	public void setItemTax(KualiDecimal itemTax) {
		this.itemTax = itemTax;
	}

	public KualiDecimal getItemTotalAmt() {
		return this.itemTotalAmt;
	}

	public void setItemTotalAmt(KualiDecimal itemTotalAmt) {
		this.itemTotalAmt = itemTotalAmt;
	}

	public String getReceivedCd() {
		return this.receivedCd;
	}

	public void setReceivedCd(String receivedCd) {
		this.receivedCd = receivedCd;
	}

	public String getReceivedComments() {
		return this.receivedComments;
	}

	public void setReceivedComments(String receivedComments) {
		this.receivedComments = receivedComments;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getCheckinDt() {
		return this.checkinDt;
	}

	public void setCheckinDt(Date checkinDt) {
		this.checkinDt = checkinDt;
	}

	public Date getShipDt() {
		return this.shipDt;
	}

	public void setShipDt(Date shipDt) {
		this.shipDt = shipDt;
	}

	public boolean isMatchInd() {
		return this.matchInd;
	}

	public void setMatchInd(boolean matchInd) {
		this.matchInd = matchInd;
	}

	public boolean isGlProcessInd() {
		return this.glProcessInd;
	}

	public void setGlProcessInd(boolean glProcessInd) {
		this.glProcessInd = glProcessInd;
	}

	public String getInvoiceNbr() {
		return this.invoiceNbr;
	}

	public void setInvoiceNbr(String invoiceNbr) {
		this.invoiceNbr = invoiceNbr;
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