package org.kuali.ext.mm.businessobject;

import org.kuali.ext.mm.util.MMDecimal;
	
public class RentalObjectCode extends MMPersistableBusinessObjectBase {
	
	private String rentalObjectCode;
		
	private String description;
	
	private String rentalTypeCode;
		
	private MMDecimal dailyDemurragePrice;
	
	private MMDecimal depositPrice;
	
	private RentalType rentalType;
	
	
	public String getRentalObjectCode() {
		return rentalObjectCode;
	}
	
	public void setRentalObjectCode(String rentalObjectCode) {
		this.rentalObjectCode = rentalObjectCode;
	}
		
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
		
	public MMDecimal getDailyDemurragePrice() {
		return dailyDemurragePrice;
	}
	
	public void setDailyDemurragePrice(MMDecimal dailyDemurragePrice) {
		this.dailyDemurragePrice = dailyDemurragePrice;
	}

    public void setRentalTypeCode(String rentalTypeCode) {
        this.rentalTypeCode = rentalTypeCode;
    }

    public String getRentalTypeCode() {
        return rentalTypeCode;
    }

    public void setDepositPrice(MMDecimal depositPrice) {
        this.depositPrice = depositPrice;
    }

    public MMDecimal getDepositPrice() {
        return depositPrice;
    }

    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

}
