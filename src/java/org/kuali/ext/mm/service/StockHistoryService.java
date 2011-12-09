package org.kuali.ext.mm.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.businessobject.CheckinDetail;
import org.kuali.ext.mm.businessobject.CurrentStockHistoryInformation;
import org.kuali.ext.mm.businessobject.PurchaseHistory;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.businessobject.SalesHistory;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.businessobject.StockHistory;
import org.kuali.ext.mm.businessobject.StockHistoryLookupObject;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.ext.mm.util.MMDecimal;

public interface StockHistoryService {
    
    public List<StockHistory> getStockHistoryForStock(String stockId);
    
    public StockHistory getStockHistoryForStockAndCheckinDocument(String stockId, String checkinDocNumber);

    public List<StockHistory> createStockHistoryForWorksheetCountDocument(
            WorksheetCountDocument wdoc);

    public List<StockHistory> createStockHistoryForCheckinDocument(CheckinDocument chekcinDoc,
            Map<String, MMDecimal> costHistory);
    
    public StockHistory createStockHistoryForReceiptCorrection(CheckinDetail correctionDetail, StockBalance afterStockBalance,
            MMDecimal beforeCost);

    public StockHistoryLookupObject getStockHistoryLookupObjectForStockId(String stockId);

    public StockHistory createStockHistoryForReturnLine(ReturnDocument rdoc, ReturnDetail rdetail, StockBalance afterStockBalance);

    public Map<String, SalesHistory> getSalesHistoryForStock(String stockId);

    public CurrentStockHistoryInformation getCurrentStockHistoryInformation(String stockId);

    public Collection<PurchaseHistory> getPurchaseHistoryForStock(String stockId);
    
//    public StockHistory createStockHistoryForBalanceChange(StockBalance beforeStockBalance, StockBalance afterStockBalance, String transReasonCode, String referenceDocNbr);
    
    public StockHistory createStockHistoryForBalanceChange(StockBalance afterStockBalance, Double adjustmentQuantity, String transReasonCode);
    
    public StockHistory createStockHistoryForCostAdjustment(MMDecimal stockCost, MMDecimal oldStockCost, StockBalance stockBalance);

}
