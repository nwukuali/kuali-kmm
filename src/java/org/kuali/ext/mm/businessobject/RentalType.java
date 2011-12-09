package org.kuali.ext.mm.businessobject;

import java.util.LinkedHashMap;
	
	
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
		
    /**
     * toStringMapper
     * @return LinkedHashMap
     */
    @Override
    public LinkedHashMap toStringMapper() {
        LinkedHashMap propMap = new LinkedHashMap();
        //TODO:  Autogenerated method

        return propMap;
    }
}
