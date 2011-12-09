/**
 *
 */
package org.kuali.ext.mm.businessobject;

/**
 * @author rponraj
 */

import java.io.Serializable;
import java.util.Date;

import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;

public class PurchaseHistory implements Serializable{

    private static final long serialVersionUID = 8579452272874556885L;

    private Integer acceptedQty = 0;

    private boolean isChargedAdditionally;

    private Integer numberOfLagTimes;

    private Integer numberOfShipments;

    private Date orderDate;

    private MMDecimal orderItemAdditionalCostAmt = MMDecimal.ZERO;

    private MMDecimal orderItemPriceAmt = MMDecimal.ZERO;

    private MMDecimal orderItemTaxAmt = MMDecimal.ZERO;

    private Integer orderQuantity;

    private String poNumber;

    private Date receivedDate;

    private Integer receivedQuantity = 0;

    private Integer rejectedQty = 0;

    private MMDecimal unitPrice = MMDecimal.ZERO;

    public Integer getAcceptedQty() {
        return this.acceptedQty;
    }

    public String getChargedAdditionallyText() {
        if (this.isChargedAdditionally)
            return "YES";
        return "NO";
    }

    public Integer getLag() {
        if (this.orderDate != null && this.receivedDate != null) {
            return MMUtil.getDateDifferenceinDays(orderDate, receivedDate);
        }
        return 0;
    }

    public Integer getNumberOfLagTimes() {
        return this.numberOfLagTimes;
    }

    public Integer getNumberOfShipments() {
        return this.numberOfShipments;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public MMDecimal getOrderItemAdditionalCostAmt() {
        return this.orderItemAdditionalCostAmt;
    }

    public MMDecimal getOrderItemPriceAmt() {
        return this.orderItemPriceAmt;
    }

    public MMDecimal getOrderItemTaxAmt() {
        return this.orderItemTaxAmt;
    }

    public Integer getOrderQuantity() {
        return this.orderQuantity;
    }

    public String getPoNumber() {
        return this.poNumber;
    }

    public Date getReceivedDate() {
        return this.receivedDate;
    }

    public Integer getReceivedQuantity() {

        if (this.rejectedQty != null)
            return this.receivedQuantity - this.rejectedQty;

        return this.receivedQuantity;
    }

    public Integer getRejectedQty() {
        return this.rejectedQty;
    }

    public MMDecimal getUnitPrice() {
        this.unitPrice = this.orderItemPriceAmt.add(this.orderItemTaxAmt).add(
                this.orderItemAdditionalCostAmt);
        return this.unitPrice;
    }

    public boolean isChargedAdditionally() {
        return this.isChargedAdditionally;
    }

    public void setAcceptedQty(Integer acceptedQty) {
        this.acceptedQty = acceptedQty;
    }

    public void setChargedAdditionally(boolean isChargedAdditionally) {
        this.isChargedAdditionally = isChargedAdditionally;
    }

    public void setNumberOfLagTimes(Integer numberOfLagTimes) {
        this.numberOfLagTimes = numberOfLagTimes;
    }

    public void setNumberOfShipments(Integer numberOfShipments) {
        this.numberOfShipments = numberOfShipments;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderItemAdditionalCostAmt(MMDecimal orderItemAdditionalCostAmt) {
        this.orderItemAdditionalCostAmt = orderItemAdditionalCostAmt;
    }

    public void setOrderItemPriceAmt(MMDecimal orderItemPriceAmt) {
        this.orderItemPriceAmt = orderItemPriceAmt;
    }

    public void setOrderItemTaxAmt(MMDecimal orderItemTaxAmt) {
        this.orderItemTaxAmt = orderItemTaxAmt;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }


    public void setRejectedQty(Integer rejectedQty) {
        this.rejectedQty = rejectedQty;
    }

    public void setUnitPrice(MMDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

}
