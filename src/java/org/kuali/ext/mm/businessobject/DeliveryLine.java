/**
 * 
 */
package org.kuali.ext.mm.businessobject;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.kuali.ext.mm.document.DeliveryLabelDocumentLines;

/**
 * @author rshrivas
 *
 */
@Entity
@Table(name = "MM_DELIVERY_LINE_T")
public class DeliveryLine extends MMPersistableBusinessObjectBase implements
java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DELIVERY_LINE_ID", unique = true, nullable = false)
    private String deliveryLineId;
    
    @Column(name= "DELIVERY_DOC_NBR")
    private String deliveryDocNbr;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID", nullable = false)
    private Delivery delivery;    
    
    @Column(name="DEPT_RECVD_BY_NM")
    private String departmentReceivedByName;
        
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_REASON_CD", nullable = false)
    private DeliveryReason deliveryReason;    
    
    @Column(name="DELIVERY_REASON_CD")
    private String deliveryReasonCode;
    
    @Column(name="DELIVERY_DT")
    private Date deliveryDate;
    
    @Column(name="DRIVER_COMMENT")
    private String driverComment;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACK_LIST_LINE_ID", nullable = false)
    private DeliveryLabelDocumentLines deliveryLabelDocumentLines;    
    
    @Column(name="PACK_LIST_LINE_ID")
    private String packListLineId;    
    
    private String deliveryInfoSummary;

    /**
     * Gets the deliveryInfoSummary property
     * @return Returns the deliveryInfoSummary
     */
    public String getDeliveryInfoSummary() {        
        return this.deliveryInfoSummary;
    }

    /**
     * Sets the deliveryInfoSummary property value
     * @param deliveryInfoSummary The deliveryInfoSummary to set
     */
    public void setDeliveryInfoSummary(String deliveryInfoSummary) {
        this.deliveryInfoSummary = deliveryInfoSummary;
    }

    /**
     * Gets the deliveryDocNbr property
     * @return Returns the deliveryDocNbr
     */
    public String getDeliveryDocNbr() {
        return this.deliveryDocNbr;
    }

    /**
     * Sets the deliveryDocNbr property value
     * @param deliveryDocNbr The deliveryDocNbr to set
     */
    public void setDeliveryDocNbr(String deliveryDocNbr) {
        this.deliveryDocNbr = deliveryDocNbr;
    }

    /**
     * Gets the deliveryLineId property
     * @return Returns the deliveryLineId
     */
    public String getDeliveryLineId() {
        return this.deliveryLineId;
    }

    /**
     * Sets the deliveryLineId property value
     * @param deliveryLineId The deliveryLineId to set
     */
    public void setDeliveryLineId(String deliveryLineId) {
        this.deliveryLineId = deliveryLineId;
    }

    /**
     * Gets the delivery property
     * @return Returns the delivery
     */
    public Delivery getDelivery() {
        return this.delivery;
    }

    /**
     * Sets the delivery property value
     * @param delivery The delivery to set
     */
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    /**
     * Gets the departmentReceivedByName property
     * @return Returns the departmentReceivedByName
     */
    public String getDepartmentReceivedByName() {
        return this.departmentReceivedByName;
    }

    /**
     * Sets the departmentReceivedByName property value
     * @param departmentReceivedByName The departmentReceivedByName to set
     */
    public void setDepartmentReceivedByName(String departmentReceivedByName) {
        this.departmentReceivedByName = departmentReceivedByName;
    }

    /**
     * Gets the deliveryReason property
     * @return Returns the deliveryReason
     */
    public DeliveryReason getDeliveryReason() {
        return this.deliveryReason;
    }

    /**
     * Sets the deliveryReason property value
     * @param deliveryReason The deliveryReason to set
     */
    public void setDeliveryReason(DeliveryReason deliveryReason) {
        this.deliveryReason = deliveryReason;
    }

    /**
     * Gets the deliveryReasonCode property
     * @return Returns the deliveryReasonCode
     */
    public String getDeliveryReasonCode() {
        return this.deliveryReasonCode;
    }

    /**
     * Sets the deliveryReasonCode property value
     * @param deliveryReasonCode The deliveryReasonCode to set
     */
    public void setDeliveryReasonCode(String deliveryReasonCode) {
        this.deliveryReasonCode = deliveryReasonCode;
    }

    /**
     * Gets the deliveryDate property
     * @return Returns the deliveryDate
     */
    public Date getDeliveryDate() {
        return this.deliveryDate;
    }

    /**
     * Sets the deliveryDate property value
     * @param deliveryDate The deliveryDate to set
     */
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * Gets the driverComment property
     * @return Returns the driverComment
     */
    public String getDriverComment() {
        return this.driverComment;
    }

    /**
     * Sets the driverComment property value
     * @param driverComment The driverComment to set
     */
    public void setDriverComment(String driverComment) {
        this.driverComment = driverComment;
    }

    /**
     * Gets the deliveryLabelDocumentLines property
     * @return Returns the deliveryLabelDocumentLines
     */
    public DeliveryLabelDocumentLines getDeliveryLabelDocumentLines() {
        return this.deliveryLabelDocumentLines;
    }

    /**
     * Sets the deliveryLabelDocumentLines property value
     * @param deliveryLabelDocumentLines The deliveryLabelDocumentLines to set
     */
    public void setDeliveryLabelDocumentLines(DeliveryLabelDocumentLines deliveryLabelDocumentLines) {
        this.deliveryLabelDocumentLines = deliveryLabelDocumentLines;
    }

    /**
     * Gets the packListLineId property
     * @return Returns the packListLineId
     */
    public String getPackListLineId() {
        return this.packListLineId;
    }

    /**
     * Sets the packListLineId property value
     * @param packListLineId The packListLineId to set
     */
    public void setPackListLineId(String packListLineId) {
        this.packListLineId = packListLineId;
    }
    
}
