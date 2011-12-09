/**
 *
 */
package org.kuali.ext.mm.businessobject;

import java.io.Serializable;
import java.util.Date;

import org.kuali.ext.mm.util.MMDecimal;

/**
 * @author rponraj
 */
public class CurrentStockHistoryInformation implements Serializable{
    private static final long serialVersionUID = -928261417762551648L;

    private Integer qtyAvailable = 0;

    private Integer quantityOnHand = 0;

    private Integer quantityAllocated = 0;

    private Integer backOrderQty = 0;

    private Integer minimumOrderQty = 0;

    private Integer reorderPoint = 0;

    private Integer orderQuantity = 0;

    private boolean perishable = false;

    private boolean hazardous = false;

    private MMDecimal averageUnitCost = MMDecimal.ZERO;

    private Date lastReorderDate = null;

    public Integer getQtyAvailable() {
        return this.qtyAvailable;
    }

    public void setQtyAvailable(Integer qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public Integer getQuantityOnHand() {
        return this.quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Integer getQuantityAllocated() {
        return this.quantityAllocated;
    }

    public void setQuantityAllocated(Integer quantityAllocated) {
        this.quantityAllocated = quantityAllocated;
    }

    public Integer getReorderPoint() {
        return this.reorderPoint;
    }

    public void setReorderPoint(Integer reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Integer getOrderQuantity() {
        return this.orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public boolean isPerishable() {
        return this.perishable;
    }

    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }

    public boolean isHazardous() {
        return this.hazardous;
    }

    public void setHazardous(boolean hazardous) {
        this.hazardous = hazardous;
    }

    public MMDecimal getAverageUnitCost() {
        return this.averageUnitCost;
    }

    public void setAverageUnitCost(MMDecimal averageUnitCost) {
        this.averageUnitCost = averageUnitCost;
    }

    public Date getLastReorderDate() {
        return this.lastReorderDate;
    }

    public void setLastReorderDate(Date lastReorderDate) {
        this.lastReorderDate = lastReorderDate;
    }

    public Integer getBackOrderQty() {
        return this.backOrderQty;
    }

    public void setBackOrderQty(Integer backOrderQty) {
        this.backOrderQty = backOrderQty;
    }

    public Integer getMinimumOrderQty() {
        return this.minimumOrderQty;
    }

    public void setMinimumOrderQty(Integer minimumOrderQty) {
        this.minimumOrderQty = minimumOrderQty;
    }

    public String getPerishableDesc() {
        return this.isPerishable() ? "YES" : "NO";
    }

    public String getHazardousDesc() {
        return this.isHazardous() ? "YES" : "NO";
    }


}
