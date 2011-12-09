package org.kuali.ext.mm.cart.valueobject;


public class DirectEntry {

	private String catalogId;

	private String catalogNumber;

	private String unitOfIssue;

	private String quantity;

	private boolean willCall;

	private String catalogPrice;

	private String catalogDescription;
	
	private String suggestedVendor;
	
//	private String finObjectCode;

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogNumber() {
		return catalogNumber;
	}

	public void setCatalogNumber(String catalogNumber) {
		this.catalogNumber = catalogNumber;
	}

	public String getUnitOfIssue() {
		return unitOfIssue;
	}

	public void setUnitOfIssue(String unitOfIssue) {
		this.unitOfIssue = unitOfIssue;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setWillCall(boolean willCall) {
		this.willCall = willCall;
	}

	public boolean isWillCall() {
		return willCall;
	}

	public void setCatalogPrice(String catalogPrice) {
		this.catalogPrice = catalogPrice;
	}

	public String getCatalogPrice() {
		return catalogPrice;
	}

	public void setCatalogDescription(String catalogDescription) {
		this.catalogDescription = catalogDescription;
	}

	public String getCatalogDescription() {
		return catalogDescription;
	}

    public String getSuggestedVendor() {
        return suggestedVendor;
    }

    public void setSuggestedVendor(String suggestedVendor) {
        this.suggestedVendor = suggestedVendor;
    }
//
//    public String getFinObjectCode() {
//        return finObjectCode;
//    }
//
//    public void setFinObjectCode(String finObjectCode) {
//        this.finObjectCode = finObjectCode;
//    }

}
