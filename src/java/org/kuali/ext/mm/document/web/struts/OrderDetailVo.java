package org.kuali.ext.mm.document.web.struts;

import java.io.Serializable;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.OrderStatus;
import org.kuali.ext.mm.businessobject.StoresPersistableBusinessObject;
import org.kuali.ext.mm.businessobject.UnitOfIssue;

public class OrderDetailVo implements Serializable{

	/**
     *
     */
    private static final long serialVersionUID = -9114333026813303704L;

    @Override
	public String toString() {
		return "OrderDetailVo [acceptedItemQuantity=" + acceptedItemQuantity
				+ ", binId=" + binId + ", binNumber=" + binNumber
				+ ", catalogItem=" + catalogItem + ", itemNumber=" + itemNumber
				+ ", manufacturerNumber=" + manufacturerNumber + ", poNumber="
				+ poNumber + ", reasonCode=" + reasonCode + ", shelfId="
				+ shelfId + ", shelfIdNumber=" + shelfIdNumber
				+ ", unitOfIssueCode=" + unitOfIssueCode + ", zoneCode="
				+ zoneCode + "]";
	}

	private String poNumber;
	private String itemNumber;
	private String manufacturerNumber;
	private Integer acceptedItemQuantity;
	private String acceptedItemQuantityVal;
	private UnitOfIssue unitOfIssue;
	private OrderStatus orderStatus;
	private String unitOfIssueCode;
	private String zoneCode;
	private String shelfId;
	private String shelfIdNumber;
	private String binNumber;
	private String reasonCode;
	private CatalogItem catalogItem;
	private Integer binId;
	private String binZoneDesc ;

	public String getBinZoneDesc() {
		return binZoneDesc;
	}

	public void setBinZoneDesc(String binZoneDesc) {
		this.binZoneDesc = binZoneDesc;
	}

	public Integer getBinId() {
		return binId;
	}

	public void setBinId(Integer binId) {
		this.binId = binId;
	}

	public CatalogItem getCatalogItem() {
		return catalogItem;
	}

	public void setCatalogItem(CatalogItem catalogItem) {
		this.catalogItem = catalogItem;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getManufacturerNumber() {
		return manufacturerNumber;
	}

	public void setManufacturerNumber(String manufacturerNumber) {
		this.manufacturerNumber = manufacturerNumber;
	}

	public Integer getAcceptedItemQuantity() {

		if(StringUtils.isEmpty(this.acceptedItemQuantityVal))
			return null;

		try{
			acceptedItemQuantity = new Integer(this.acceptedItemQuantityVal);
		}catch(NumberFormatException e){
			this.acceptedItemQuantity = -2;
		}catch(Exception e){
			this.acceptedItemQuantity = -1;
		}
		return acceptedItemQuantity;
	}

	public void setAcceptedItemQuantity(Integer acceptedItemQuantity) {
		this.acceptedItemQuantity = acceptedItemQuantity;
	}

	public String getUnitOfIssueCode() {
		return unitOfIssueCode;
	}

	public void setUnitOfIssueCode(String unitOfIssueCode) {
		this.unitOfIssueCode = unitOfIssueCode;
	}

	public String getUnitOfIssueCodeDesc(){
	    if(this.getUnitOfIssue() == null){
	        if(!StringUtils.isEmpty(this.getUnitOfIssueCode())){
	        UnitOfIssue data = StoresPersistableBusinessObject.getObjectByPrimaryKey(UnitOfIssue.class, this.getUnitOfIssueCode());
	        if(data != null){
	            this.setUnitOfIssue(data);
	            return data.getUnitOfIssueDesc();
	        }
	        }
	    }else{
	        return this.getUnitOfIssue().getUnitOfIssueDesc();
	    }
	    return "";
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getShelfId() {
		return shelfId;
	}

	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}

	public String getShelfIdNumber() {
		return shelfIdNumber;
	}

	public void setShelfIdNumber(String shelfIdNumber) {
		this.shelfIdNumber = shelfIdNumber;
	}

	public String getBinNumber() {
		return binNumber;
	}

	public void setBinNumber(String binNumber) {
		this.binNumber = binNumber;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getAcceptedItemQuantityVal() {
		return acceptedItemQuantityVal;
	}

	public void setAcceptedItemQuantityVal(String acceptedItemQuantityVal) {
		this.acceptedItemQuantityVal = acceptedItemQuantityVal;
	}

	public UnitOfIssue getUnitOfIssue() {

		if(this.unitOfIssue == null){
			if(StringUtils.isEmpty(this.unitOfIssueCode))
				this.unitOfIssue = null;
			this.unitOfIssue =  StoresPersistableBusinessObject.getObjectByPrimaryKey(UnitOfIssue.class, unitOfIssueCode);
		}
		return this.unitOfIssue;
	}

	public void setUnitOfIssue(UnitOfIssue unitOfIssue) {
		this.unitOfIssue = unitOfIssue;
	}

	public OrderStatus getOrderStatus() {
		if(this.orderStatus == null){
			if(StringUtils.isEmpty(this.reasonCode))
				this.orderStatus = null;
			this.orderStatus =  StoresPersistableBusinessObject.getObjectByPrimaryKey(OrderStatus.class, reasonCode);
		}
		return this.orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

}