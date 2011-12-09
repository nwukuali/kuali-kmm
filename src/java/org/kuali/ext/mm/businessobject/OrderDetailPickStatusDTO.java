/**
 *
 */
package org.kuali.ext.mm.businessobject;

import java.io.Serializable;

/**
 * @author harsha07
 */
public class OrderDetailPickStatusDTO implements Serializable{
    private static final long serialVersionUID = -7026137408992404878L;
    private Integer orderDetailId;
    private String orderDocumentNbr;
    private Integer orderItemQty = new Integer(0);
    private Integer pickedQty = new Integer(0);

    /**
     * Gets the orderDetailId property
     *
     * @return Returns the orderDetailId
     */
    public Integer getOrderDetailId() {
        return this.orderDetailId;
    }

    /**
     * Sets the orderDetailId property value
     *
     * @param orderDetailId The orderDetailId to set
     */
    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    /**
     * Gets the orderDocumentNbr property
     *
     * @return Returns the orderDocumentNbr
     */
    public String getOrderDocumentNbr() {
        return this.orderDocumentNbr;
    }

    /**
     * Sets the orderDocumentNbr property value
     *
     * @param orderDocumentNbr The orderDocumentNbr to set
     */
    public void setOrderDocumentNbr(String orderDocumentNbr) {
        this.orderDocumentNbr = orderDocumentNbr;
    }

    /**
     * Gets the orderItemQty property
     *
     * @return Returns the orderItemQty
     */
    public Integer getOrderItemQty() {
        return this.orderItemQty;
    }

    /**
     * Sets the orderItemQty property value
     *
     * @param orderItemQty The orderItemQty to set
     */
    public void setOrderItemQty(Integer orderItemQty) {
        this.orderItemQty = orderItemQty;
    }

    /**
     * Gets the pickedQty property
     *
     * @return Returns the pickedQty
     */
    public Integer getPickedQty() {
        return this.pickedQty;
    }

    /**
     * Sets the pickedQty property value
     *
     * @param pickedQty The pickedQty to set
     */
    public void setPickedQty(Integer pickedQty) {
        this.pickedQty = pickedQty;
    }


}
