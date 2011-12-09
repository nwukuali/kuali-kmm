/**
 *
 */
package org.kuali.ext.mm.businessobject;

import java.sql.Timestamp;

/**
 * @author rponraj
 *
 */
public class ReorderCatalogItemDetail extends StoresPersistableBusinessObject {

    /**
     *
     */
    private static final long serialVersionUID = -3651017686849934902L;

    private Integer rowval;

    private String catalogGroupCode;

    private Integer allOrdersQty;

    private Integer dayOfOrders;

    private Integer agreementLagDays;

    private CatalogItem catalogItem;

    private Integer defaultQuantity;

    private String warehouseCd;

    private Integer stockId;

    private boolean descrepancyInd;

    private String stockDistributorNumber;

    private String agreementNbr;

    private Integer onHandQty;

    private Integer oneOrderQty;

    private Integer oneBackOrderQty;

    private Integer onReorderQty;

    private Integer minimumPoQty;

    private Integer maximumOrderQty;

    private Integer reorderPointQty;

    private String groupDesc;

    private String subgroupDesc;

    private Timestamp lastReorderDate;

    private Timestamp perishableDate;

    private Timestamp lastOrderDate;

    private Stock stock;

    private Warehouse warehouse;

    private String catalogSubGroupCode;

    private String catalogSubgroupId;

    public Integer getDefaultQuantity() {
        return this.defaultQuantity;
    }

    public void setDefaultQuantity(Integer defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public CatalogItem getCatalogItem() {
        return this.catalogItem;
    }

    public void setCatalogItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
    }

    public Integer getAgreementLagDays() {
        return this.agreementLagDays;
    }

    public void setAgreementLagDays(Integer agreementLagDays) {
        this.agreementLagDays = agreementLagDays;
    }

    public Integer getDayOfOrders() {
        return this.dayOfOrders;
    }

    public void setDayOfOrders(Integer dayOfOrders) {
        this.dayOfOrders = dayOfOrders;
    }

    public Integer getAllOrdersQty() {
        return this.allOrdersQty;
    }

    public void setAllOrdersQty(Integer allOrdersQty) {
        this.allOrdersQty = allOrdersQty;
    }

    public String getCatalogGroupCode() {
        return this.catalogGroupCode;
    }

    public void setCatalogGroupCode(String catalogGroupCode) {
        this.catalogGroupCode = catalogGroupCode;
    }

    public String getCatalogSubGroupCode() {
        return this.catalogSubGroupCode;
    }

    public void setCatalogSubGroupCode(String catalogSubGroupCode) {
        this.catalogSubGroupCode = catalogSubGroupCode;
    }

    public String getCatalogSubgroupId() {
        return this.catalogSubgroupId;
    }

    public void setCatalogSubgroupId(String catalogSubGroupId) {
        this.catalogSubgroupId = catalogSubGroupId;
    }

    public Integer getRowval() {
        return this.rowval;
    }
    public void setRowval(Integer rowval) {
        this.rowval = rowval;
    }
    public String getWarehouseCd() {
        return this.warehouseCd;
    }
    public void setWarehouseCd(String warehouseCd) {
        this.warehouseCd = warehouseCd;
    }
    public Integer getStockId() {
        return this.stockId;
    }
    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }
    public boolean isDescrepancyInd() {
        return this.descrepancyInd;
    }
    public void setDescrepancyInd(boolean descrepancyInd) {
        this.descrepancyInd = descrepancyInd;
    }
    public String getStockDistributorNumber() {
        return this.stockDistributorNumber;
    }
    public void setStockDistributorNumber(String stockDistributorNumber) {
        this.stockDistributorNumber = stockDistributorNumber;
    }
    public String getAgreementNbr() {
        return this.agreementNbr;
    }
    public void setAgreementNbr(String agreementNbr) {
        this.agreementNbr = agreementNbr;
    }
    public Integer getOnHandQty() {
        return this.onHandQty;
    }
    public void setOnHandQty(Integer onHandQty) {
        this.onHandQty = onHandQty;
    }
    public Integer getOneOrderQty() {
        return this.oneOrderQty;
    }
    public void setOneOrderQty(Integer oneOrderQty) {
        this.oneOrderQty = oneOrderQty;
    }
    public Integer getOneBackOrderQty() {
        return this.oneBackOrderQty;
    }
    public void setOneBackOrderQty(Integer oneBackOrderQty) {
        this.oneBackOrderQty = oneBackOrderQty;
    }
    public Integer getOnReorderQty() {
        return this.onReorderQty;
    }
    public void setOnReorderQty(Integer onReorderQty) {
        this.onReorderQty = onReorderQty;
    }
    public Integer getMinimumPoQty() {
        return this.minimumPoQty;
    }
    public void setMinimumPoQty(Integer minimumPoQty) {
        this.minimumPoQty = minimumPoQty;
    }
    public Integer getMaximumOrderQty() {
        return this.maximumOrderQty;
    }
    public void setMaximumOrderQty(Integer maximumOrderQty) {
        this.maximumOrderQty = maximumOrderQty;
    }
    public Integer getReorderPointQty() {
        return this.reorderPointQty;
    }
    public void setReorderPointQty(Integer reorderPointQty) {
        this.reorderPointQty = reorderPointQty;
    }
    public String getGroupDesc() {
        return this.groupDesc;
    }
    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }
    public String getSubgroupDesc() {
        return this.subgroupDesc;
    }
    public void setSubgroupDesc(String subgroupDesc) {
        this.subgroupDesc = subgroupDesc;
    }
    public Timestamp getLastReorderDate() {
        return this.lastReorderDate;
    }
    public void setLastReorderDate(Timestamp lastReorderDate) {
        this.lastReorderDate = lastReorderDate;
    }
    public Timestamp getPerishableDate() {
        return this.perishableDate;
    }
    public void setPerishableDate(Timestamp perishableDate) {
        this.perishableDate = perishableDate;
    }
    public Timestamp getLastOrderDate() {
        return this.lastOrderDate;
    }
    public void setLastOrderDate(Timestamp lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }
    public Stock getStock() {
        return this.stock;
    }
    public void setStock(Stock stock) {
        this.stock = stock;
    }
    public Warehouse getWarehouse() {
        return this.warehouse;
    }
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
