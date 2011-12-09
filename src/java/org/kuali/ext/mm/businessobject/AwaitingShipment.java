package org.kuali.ext.mm.businessobject;
	
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.BusinessObjectBase;
	
public class AwaitingShipment extends BusinessObjectBase {

    private Integer orderNumber;
		
	private Integer quantityToShip;
		
	private Integer totalToShip;
		
	private String unitOfIssue;
		
	private String shipToCode;
		
	private Timestamp dateEntered;
		
	private String distributorNumber;
		
	private String itemDescription;
		
	private String binLocation;
	
	
	public Integer getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
		
	public Integer getQuantityToShip() {
		return quantityToShip;
	}
	
	public void setQuantityToShip(Integer quantityToShip) {
		this.quantityToShip = quantityToShip;
	}
		
	public Integer getTotalToShip() {
		return totalToShip;
	}
	
	public void setTotalToShip(Integer totalToShip) {
		this.totalToShip = totalToShip;
	}
		
	public String getUnitOfIssue() {
		return unitOfIssue;
	}
	
	public void setUnitOfIssue(String unitOfIssue) {
		this.unitOfIssue = unitOfIssue;
	}
		
	public String getShipToCode() {
		return shipToCode;
	}
	
	public void setShipToCode(String shipToCode) {
		this.shipToCode = shipToCode;
	}
		
	public Timestamp getDateEntered() {
		return dateEntered;
	}
	
	public void setDateEntered(Timestamp dateEntered) {
		this.dateEntered = dateEntered;
	}
		
	public String getDistributorNumber() {
		return distributorNumber;
	}
	
	public void setDistributorNumber(String distributorNumber) {
		this.distributorNumber = distributorNumber;
	}
		
	public String getItemDescription() {
		return itemDescription;
	}
	
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
		
	public String getBinLocation() {
		return binLocation;
	}
	
	public void setBinLocation(String binLocation) {
		this.binLocation = binLocation;
	}

    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    @Override
    protected LinkedHashMap toStringMapper() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.kuali.rice.kns.bo.BusinessObject#refresh()
     */
    public void refresh() {
        // TODO Auto-generated method stub
        
    }
	
}
