package org.kuali.ext.mm.businessobject;

public class RentalType extends MMPersistableBusinessObjectBase {
	
	private String rentalTypeCode;
		
	private String description;
		
		
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

    public void setRentalTypeCode(String rentalTypeCode) {
        this.rentalTypeCode = rentalTypeCode;
    }

    public String getRentalTypeCode() {
        return rentalTypeCode;
    }

}
