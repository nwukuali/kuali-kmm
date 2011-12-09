package org.kuali.ext.mm.businessobject;
	
import java.util.LinkedHashMap;

import org.kuali.ext.mm.document.TrueBuyoutDocument;
import org.kuali.ext.mm.util.MMDecimal;
	
public class TrueBuyoutDetail extends StoresPersistableBusinessObject {
	
	private String trueBuyoutDetailId;
	
	private String stockDistributorNumber;
	
	private String stockTypeCode;
	
	private StockType stockType;
	
	private boolean willCall;
		
	private String trueBuyoutDocumentNumber;
	
	private TrueBuyoutDocument trueBuyoutDocument;
		
	private String orderItemDescription;
		
	private String orderItemUnitOfIssueCode;
		
	private Integer orderItemQuantity;
		
	private MMDecimal orderItemCost;
	   
    private String catalogId;
    
    private Catalog catalog;
	
	private String catalogItemId;
	
	private CatalogItem catalogItem;
		
	private String markupCode;
	
	private Markup markup;
	
	private Accounts account;
	
	private String accountId;	
	
	private String agreementNumber;
	
	private String rentalObjectCode;
	
	private RentalObjectCode rentalObject;
	
	
	public String getTrueBuyoutDetailId() {
		return trueBuyoutDetailId;
	}
	
	public void setTrueBuyoutDetailId(String trueBuyoutDetailId) {
		this.trueBuyoutDetailId = trueBuyoutDetailId;
	}
		
	public String getTrueBuyoutDocumentNumber() {
		return trueBuyoutDocumentNumber;
	}
	
	public void setTrueBuyoutDocumentNumber(String trueBuyoutDocumentNumber) {
		this.trueBuyoutDocumentNumber = trueBuyoutDocumentNumber;
	}
		
	public String getOrderItemDescription() {
		return orderItemDescription;
	}
	
	public void setOrderItemDescription(String orderItemDescription) {
		this.orderItemDescription = orderItemDescription;
	}
		
	public String getOrderItemUnitOfIssueCode() {
		return orderItemUnitOfIssueCode;
	}
	
	public void setOrderItemUnitOfIssueCode(String orderItemUnitOfIssueCode) {
		this.orderItemUnitOfIssueCode = orderItemUnitOfIssueCode;
	}
		
	public Integer getOrderItemQuantity() {
		return orderItemQuantity;
	}
	
	public void setOrderItemQuantity(Integer orderItemQuantity) {
		this.orderItemQuantity = orderItemQuantity;
	}
		
	public MMDecimal getOrderItemCost() {
		return orderItemCost;
	}
	
	public void setOrderItemCost(MMDecimal orderItemCost) {
		this.orderItemCost = orderItemCost;
	}
		
	public String getMarkupCode() {
		return markupCode;
	}
	
	public void setMarkupCode(String markupCode) {
		this.markupCode = markupCode;
	}

    public Markup getMarkup() {
        return markup;
    }

    public void setMarkup(Markup markup) {
        this.markup = markup;
    }

    public String getStockDistributorNumber() {
        return stockDistributorNumber;
    }

    public void setStockDistributorNumber(String stockDistributorNumber) {
        this.stockDistributorNumber = stockDistributorNumber;
    }

    /**
     * Gets the stockTypeCode property
     * @return Returns the stockTypeCode
     */
    public String getStockTypeCode() {
        return this.stockTypeCode;
    }

    /**
     * Sets the stockTypeCode property value
     * @param stockTypeCode The stockTypeCode to set
     */
    public void setStockTypeCode(String stockTypeCode) {
        this.stockTypeCode = stockTypeCode;
    }

    /**
     * Gets the stockType property
     * @return Returns the stockType
     */
    public StockType getStockType() {
        return this.stockType;
    }

    /**
     * Sets the stockType property value
     * @param stockType The stockType to set
     */
    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    /**
     * Gets the catalogItemId property
     * @return Returns the catalogItemId
     */
    public String getCatalogItemId() {
        return this.catalogItemId;
    }

    /**
     * Sets the catalogItemId property value
     * @param catalogItemId The catalogItemId to set
     */
    public void setCatalogItemId(String catalogItemId) {
        this.catalogItemId = catalogItemId;
    }

    /**
     * Gets the catalogItem property
     * @return Returns the catalogItem
     */
    public CatalogItem getCatalogItem() {
        return this.catalogItem;
    }

    /**
     * Sets the catalogItem property value
     * @param catalogItem The catalogItem to set
     */
    public void setCatalogItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
    }

    public boolean isWillCall() {
        return willCall;
    }

    public void setWillCall(boolean willCall) {
        this.willCall = willCall;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public TrueBuyoutDocument getTrueBuyoutDocument() {
        return trueBuyoutDocument;
    }

    public void setTrueBuyoutDocument(TrueBuyoutDocument trueBuyoutDocument) {
        this.trueBuyoutDocument = trueBuyoutDocument;
    }
    

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }
    /**
     * Gets the rentalObjectCode property
     * @return Returns the rentalObjectCode
     */
    public String getRentalObjectCode() {
        return this.rentalObjectCode;
    }

    /**
     * Sets the rentalObjectCode property value
     * @param rentalObjectCode The rentalObjectCode to set
     */
    public void setRentalObjectCode(String rentalObjectCode) {
        this.rentalObjectCode = rentalObjectCode;
    }

    /**
     * Gets the rentalObject property
     * @return Returns the rentalObject
     */
    public RentalObjectCode getRentalObject() {
        return this.rentalObject;
    }

    /**
     * Sets the rentalObject property value
     * @param rentalObject The rentalObject to set
     */
    public void setRentalObject(RentalObjectCode rentalObject) {
        this.rentalObject = rentalObject;
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