/**
 *
 */
package org.kuali.ext.mm.businessobject;

import java.sql.Timestamp;

/**
 * @author rponraj
 *
 */
public class ReorderItemDetail extends StoresPersistableBusinessObject {

    /**
     *
     */
    private static final long serialVersionUID = 3609640125652171880L;

    private Timestamp lastReorderDate;

    public Timestamp getLastReorderDate() {
        return this.lastReorderDate;
    }

    public void setLastReorderDate(Timestamp lastReorderDate) {
        this.lastReorderDate = lastReorderDate;
    }

    public String getWarehouseCd() {
        return this.warehouseCd;
    }

    public void setWarehouseCd(String warehouseCd) {
        this.warehouseCd = warehouseCd;
    }

    public String getStockDistributorNumber() {
        return this.stockDistributorNumber;
    }

    public void setStockDistributorNumber(String stockDistributorNumber) {
        this.stockDistributorNumber = stockDistributorNumber;
    }

    public Integer getReorderedQuantity() {
        return this.reorderedQuantity;
    }

    public void setReorderedQuantity(Integer reorderedQuantity) {
        this.reorderedQuantity = reorderedQuantity;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getStockId() {
        return this.stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Stock getStock() {
        return this.stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    private Integer rowval;

    private String warehouseCd;

    private String stockDistributorNumber;

    private Integer reorderedQuantity;

    private Warehouse warehouse;

    private Integer stockId;

    private Stock stock;



    public Integer getRowval() {
        return this.rowval;
    }

    public void setRowval(Integer rowval) {
        this.rowval = rowval;
    }
}
