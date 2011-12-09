package org.kuali.ext.mm.businessobject;
	
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.ext.mm.document.MassUpdateDocument;
import org.kuali.ext.mm.util.MMDecimal;
	
public class MassUpdateDetail extends StoresPersistableBusinessObject {

    private Integer massUpdateDetailId;
		
	private String massUpdateDocumentNumber;
		
	private String stockId;
		
	private MMDecimal stockCost;
		
	private String cycleCountCode;
	
	private MassUpdateDocument massUpdateDocument;
	
	private Stock stock;
		
	public Integer getMassUpdateDetailId() {
		return massUpdateDetailId;
	}
	
	public void setMassUpdateDetailId(Integer massUpdateDetailId) {
		this.massUpdateDetailId = massUpdateDetailId;
	}
		
	public String getMassUpdateDocumentNumber() {
		return massUpdateDocumentNumber;
	}
	
	public void setMassUpdateDocumentNumber(String massUpdateDocumentNumber) {
		this.massUpdateDocumentNumber = massUpdateDocumentNumber;
	}
		
	public String getStockId() {
		return stockId;
	}
	
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
		
	public MMDecimal getStockCost() {
		return stockCost;
	}
	
	public void setStockCost(MMDecimal stockCost) {
		this.stockCost = stockCost;
	}
		
	public String getCycleCountCode() {
		return cycleCountCode;
	}
	
	public void setCycleCountCode(String cycleCountCode) {
		this.cycleCountCode = cycleCountCode;
	}
		
	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}
	
	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

    public void setMassUpdateDocument(MassUpdateDocument massUpdateDocument) {
        this.massUpdateDocument = massUpdateDocument;
    }

    public MassUpdateDocument getMassUpdateDocument() {
        return massUpdateDocument;
    }

    public Stock getStock() {
        return this.stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
	
    @Override
    protected LinkedHashMap toStringMapper() {
        LinkedHashMap<String, String> propMap = new LinkedHashMap<String, String>();
        propMap.put("massUpdateDocument", massUpdateDocumentNumber);
        propMap.put("stockId", stockId);
        propMap.put("stockCost", stockCost.toString());
        propMap.put("cycleCountCode", cycleCountCode);
        return propMap;
    }
}
