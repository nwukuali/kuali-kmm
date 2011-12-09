/**
 * 
 */
package org.kuali.ext.mm.businessobject;


/**
 * @author harsha07
 */
public class CheckInCorrection extends StoresPersistableBusinessObject {
    private static final long serialVersionUID = -1544886600316546732L;
    private Integer poId;
    private String distributorNumber;
    private String orderDocumentNumber;
    private Integer orderId;
    private Integer reqsId;
    private String warehouseCode;
    private String vendorName;
    private Integer orderItemQty;
    private String checkInDocNumber;
    private Integer orderDetailId;

    /**
     * Gets the poId property
     * 
     * @return Returns the poId
     */
    public Integer getPoId() {
        return this.poId;
    }

    /**
     * Sets the poId property value
     * 
     * @param poId The poId to set
     */
    public void setPoId(Integer poId) {
        this.poId = poId;
    }

    /**
     * Gets the distributorNumber property
     * 
     * @return Returns the distributorNumber
     */
    public String getDistributorNumber() {
        return this.distributorNumber;
    }

    /**
     * Sets the distributorNumber property value
     * 
     * @param distributorNumber The distributorNumber to set
     */
    public void setDistributorNumber(String distributorNumber) {
        this.distributorNumber = distributorNumber;
    }

    /**
     * Gets the orderDocumentNumber property
     * 
     * @return Returns the orderDocumentNumber
     */
    public String getOrderDocumentNumber() {
        return this.orderDocumentNumber;
    }

    /**
     * Sets the orderDocumentNumber property value
     * 
     * @param orderDocumentNumber The orderDocumentNumber to set
     */
    public void setOrderDocumentNumber(String orderDocumentNumber) {
        this.orderDocumentNumber = orderDocumentNumber;
    }

    /**
     * Gets the orderId property
     * 
     * @return Returns the orderId
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    /**
     * Sets the orderId property value
     * 
     * @param orderId The orderId to set
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the reqsId property
     * 
     * @return Returns the reqsId
     */
    public Integer getReqsId() {
        return this.reqsId;
    }

    /**
     * Sets the reqsId property value
     * 
     * @param reqsId The reqsId to set
     */
    public void setReqsId(Integer reqsId) {
        this.reqsId = reqsId;
    }

    /**
     * Gets the warehouseCode property
     * 
     * @return Returns the warehouseCode
     */
    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    /**
     * Sets the warehouseCode property value
     * 
     * @param warehouseCode The warehouseCode to set
     */
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    /**
     * Gets the vendorName property
     * 
     * @return Returns the vendorName
     */
    public String getVendorName() {
        return this.vendorName;
    }

    /**
     * Sets the vendorName property value
     * 
     * @param vendorName The vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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
     * Gets the checkInDocNumber property
     * 
     * @return Returns the checkInDocNumber
     */
    public String getCheckInDocNumber() {
        return this.checkInDocNumber;
    }

    /**
     * Sets the checkInDocNumber property value
     * 
     * @param checkInDocNumber The checkInDocNumber to set
     */
    public void setCheckInDocNumber(String checkInDocNumber) {
        this.checkInDocNumber = checkInDocNumber;
    }

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

}
