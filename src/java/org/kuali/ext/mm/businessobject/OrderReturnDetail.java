/**
 *
 */
package org.kuali.ext.mm.businessobject;

import java.sql.Date;

/**
 * @author rponraj
 */
public class OrderReturnDetail extends StoresPersistableBusinessObject implements Cloneable {

    private static final long serialVersionUID = -5738421917162342681L;

    private Integer arId;

    private Integer balanceQty;

    private CatalogItem catalogItem;

    private String catalogItemId;

    private String distributorNumber;

    private String docNumber;

    private OrderDetail orderDetail;

    private Integer orderDetailId;

    private OrderStatus orderDetailStatus;

    private String orderDetailStatusCode;

    private Integer orderId;

    private OrderType orderType;

    private String orderTypeCode;

    private Integer poId;

    private Integer reqsId;

    private String vendorName;

    private Warehouse warehouse;

    private String warehouseCode;

    private Date orderCreationDate;

    private String customerPrncplName;

    private Customer customer;

    public Integer getArId() {
        return this.arId;
    }

    public Integer getBalanceQty() {
        return this.balanceQty;
    }

    public CatalogItem getCatalogItem() {
        return this.catalogItem;
    }

    public String getCatalogItemId() {
        return this.catalogItemId;
    }

    public String getDistributorNumber() {
        return this.distributorNumber;
    }

    public String getDocNumber() {
        return this.docNumber;
    }

    public OrderDetail getOrderDetail() {
        return this.orderDetail;
    }

    public Integer getOrderDetailId() {
        return this.orderDetailId;
    }

    public OrderStatus getOrderDetailStatus() {
        return this.orderDetailStatus;
    }

    public String getOrderDetailStatusCode() {
        return this.orderDetailStatusCode;
    }


    public Integer getOrderId() {
        return this.orderId;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public String getOrderTypeCode() {
        return this.orderTypeCode;
    }

    public Integer getPoId() {
        return this.poId;
    }

    public Integer getReqsId() {
        return this.reqsId;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    public void setArId(Integer arId) {
        this.arId = arId;
    }

    public void setBalanceQty(Integer balanceQty) {
        this.balanceQty = balanceQty;
    }

    public void setCatalogItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
    }

    public void setCatalogItemId(String catalogItemId) {
        this.catalogItemId = catalogItemId;
    }

    public void setDistributorNumber(String distributorNumber) {
        this.distributorNumber = distributorNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public void setOrderDetailStatus(OrderStatus orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public void setOrderDetailStatusCode(String orderDetailStatusCode) {
        this.orderDetailStatusCode = orderDetailStatusCode;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public void setOrderTypeCode(String orderTypeCode) {
        this.orderTypeCode = orderTypeCode;
    }

    public void setPoId(Integer poId) {
        this.poId = poId;
    }

    public void setReqsId(Integer reqsId) {
        this.reqsId = reqsId;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    /**
     * Gets the orderCreationDate property
     * 
     * @return Returns the orderCreationDate
     */
    public Date getOrderCreationDate() {
        return this.orderCreationDate;
    }

    /**
     * Sets the orderCreationDate property value
     * 
     * @param orderCreationDate The orderCreationDate to set
     */
    public void setOrderCreationDate(Date orderCreationDate) {
        this.orderCreationDate = orderCreationDate;
    }

    /**
     * Gets the customerProfileId property
     * 
     * @return Returns the customerProfileId
     */
    public String getCustomerPrncplName() {
        return this.customerPrncplName;
    }

    /**
     * Sets the customerProfileId property value
     * 
     * @param customerProfileId The customerProfileId to set
     */
    public void setCustomerPrncplName(String customerProfileId) {
        this.customerPrncplName = customerProfileId;
    }

    /**
     * Gets the customer property
     * 
     * @return Returns the customer
     */
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * Sets the customer property value
     * 
     * @param customer The customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
