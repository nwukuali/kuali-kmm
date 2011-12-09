/**
 *
 */
package org.kuali.ext.mm.businessobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rponraj
 *
 */
public class StockHistoryLookupObject implements Serializable{

    private static final long serialVersionUID = 6219395018109776369L;

    private List<StockHistory> stockHistoryObjs = new ArrayList<StockHistory>(0);

    private Integer stockId;

    private CurrentStockHistoryInformation currentStockHistoryInformation = new CurrentStockHistoryInformation();

    private Stock stock;

    private Map<String, SalesHistory> salesHistory = new HashMap<String, SalesHistory>(0);

    private Collection<PurchaseHistory> purchaseHistory = new ArrayList<PurchaseHistory>(0);

    public Map<String, SalesHistory> getSalesHistory() {
        return this.salesHistory;
    }

    public void setSalesHistory(Map<String, SalesHistory> salesHistory) {
        this.salesHistory = salesHistory;
    }

    public Collection<PurchaseHistory> getPurchaseHistory() {
        return this.purchaseHistory;
    }

    public void setPurchaseHistory(Collection<PurchaseHistory> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public void addPurchaseHistory(PurchaseHistory purchaseHistoryObj) {
        this.purchaseHistory.add(purchaseHistoryObj);
    }

    public List<StockHistory> getStockHistoryObjs() {
        return this.stockHistoryObjs;
    }

    public String getWarehouses(){
        return this.stock.getWarehouses();
    }

    public String getDescription(){
        return  this.stock.getStockDesc();
    }

    public void setStockHistoryObjs(List<StockHistory> stockHistoryObjs) {
        this.stockHistoryObjs = stockHistoryObjs;
    }

    public void addStockHistoryObject(StockHistory hisObj){
        this.stockHistoryObjs.add(hisObj);
    }

    public Integer getStockId() {
        return this.stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Stock getStock() {
        return this.stock;
    }

    public CurrentStockHistoryInformation getCurrentStockHistoryInformation() {
        return this.currentStockHistoryInformation;
    }

    public void setCurrentStockHistoryInformation(
            CurrentStockHistoryInformation currentStockHistoryInformation) {
        this.currentStockHistoryInformation = currentStockHistoryInformation;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}
