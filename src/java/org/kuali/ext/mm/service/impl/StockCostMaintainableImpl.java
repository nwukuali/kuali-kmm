package org.kuali.ext.mm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.Stock;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.businessobject.StockCost;
import org.kuali.ext.mm.businessobject.StockHistory;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.gl.GeneralLedgerPostable;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.kuali.ext.mm.gl.service.GeneralLedgerProcessor;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.ext.mm.service.StockHistoryService;
import org.kuali.rice.kns.bo.DocumentHeader;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.KualiDecimal;


public class StockCostMaintainableImpl extends KualiMaintainableImpl implements
        GeneralLedgerPostable {

    private static final long serialVersionUID = 1L;
    private List<FinancialGeneralLedgerPendingEntry> financialGeneralLedgerPendingEntries;

    @Override
    @SuppressWarnings("unchecked")
    public void doRouteStatusChange(DocumentHeader documentHeader) {

        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        PersistableBusinessObject stockCostObject = this.getBusinessObject();
        StockCost stockCost = (StockCost) stockCostObject;

        HashMap hm = new HashMap();
        hm.put(MMConstants.Stock.STOCK_ID, stockCost.getStockId());
        hm.put(MMConstants.CostCode.STOCK_COST_CODE, Stock.getCurrentStockPriceCode());
        
        List<StockCost> oldStockCostList = (List<StockCost>) bOS.findMatching(StockCost.class, hm);

        if (documentHeader.getWorkflowDocument().stateIsProcessed()) {
            updateCostInCatalogItemTable(stockCost, bOS);
            if (!oldStockCostList.isEmpty()) {
                for (int m = 0; m < oldStockCostList.size(); m++) {
                    StockCost oldStockCostObject = oldStockCostList.get(m);
                    if (oldStockCostObject.getStockCst() != stockCost.getStockCst()) {
                        loadHistory(stockCost.getStock(), stockCost, oldStockCostObject, bOS);
                    }
                }
            }
        }

        // generate GL entries
        SpringContext.getBean(GeneralLedgerProcessor.class).doRouteStatusChange(this,
                documentHeader);
    }

    private Warehouse getWarehouse(String warehouseCode) {
        Warehouse warehouse = SpringContext.getBean(BusinessObjectService.class)
                .findBySinglePrimaryKey(Warehouse.class, warehouseCode);
        return warehouse;
    }

    @SuppressWarnings("unchecked")
    private void updateCostInCatalogItemTable(StockCost stockCost, BusinessObjectService bOS) {
        HashMap<String, String> fieldValues = new HashMap();
        fieldValues.put("stockId", stockCost.getStockId());
        List<CatalogItem> catalogItemList = (List<CatalogItem>) bOS.findMatching(CatalogItem.class,
                fieldValues);
        if (!catalogItemList.isEmpty()) {
            for (int i = 0; i < catalogItemList.size(); i++) {
                CatalogItem catItem = catalogItemList.get(i);
                catItem.setCatalogPrc(stockCost.getStockCst());
                bOS.save(catItem);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadHistory(Stock stockObject, StockCost stockCostObject,
            StockCost oldStockCostObject, BusinessObjectService bOS) {
        Date today = new Date();
        HashMap<String, String> fieldValues = new HashMap();
        fieldValues.put("stockId", stockCostObject.getStockId());
        List<StockBalance> sBList = (List<StockBalance>) bOS.findMatching(StockBalance.class,
                fieldValues);

        StockHistoryService historyService = SpringContext.getBean(StockHistoryService.class);
        if (!sBList.isEmpty()) {
            for (int z = 0; z < sBList.size(); z++) {
                StockBalance stockBal = sBList.get(z);
                StockHistory sH = historyService.createStockHistoryForCostAdjustment(
                        stockCostObject.getStockCst(), 
                        oldStockCostObject.getStockCst(), stockBal);
                bOS.save(sH);
            }
        }
    }


    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#generateGlpeEntries()
     */
    @SuppressWarnings("unchecked")
    public List<FinancialGeneralLedgerPendingEntry> generateGlpeEntries() {
        GeneralLedgerBuilderService generalLedgerBuilderService = SpringContext
                .getBean(GeneralLedgerBuilderService.class);
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        PersistableBusinessObject stockCostObject = this.getBusinessObject();
        StockCost stockCost = (StockCost) stockCostObject;

        HashMap hm = new HashMap();
        hm.put(MMConstants.Stock.STOCK_ID, stockCost.getStockId());
        hm.put(MMConstants.CostCode.STOCK_COST_CODE, Stock.getCurrentStockPriceCode());
        List<StockCost> oldStockCostList = (List<StockCost>) bOS.findMatching(StockCost.class, hm);

        HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups = new HashMap<WarehouseGlGroup, WarehouseGlGroup>();

        // Added this code for posting GL Entries
        if (oldStockCostList.get(0).getStockCst() != stockCost.getStockCst()) {
            // get stock Quantity on Hand by Warehouse Code
            Stock st = bOS.findBySinglePrimaryKey(Stock.class, stockCost.getStockId());
            ArrayList stockQuntityOnHandPerWarehouseList = st.getStockQuntityOnHandPerWarehouse();

            for (int n = 0; n < stockQuntityOnHandPerWarehouseList.size(); n++) {
                String wCodeVsQOH = (String) stockQuntityOnHandPerWarehouseList.get(n);
                String[] warehouseCodeArray = wCodeVsQOH.split(":");
                String warehouseCode = warehouseCodeArray[0];
                String qtyOnHand = warehouseCodeArray[1];

                double originalCost = Integer.parseInt(qtyOnHand)
                        * oldStockCostList.get(0).getStockCst().doubleValue();
                double newCost = Integer.parseInt(qtyOnHand)
                        * stockCost.getStockCst().doubleValue();
                KualiDecimal changedCost = new KualiDecimal(newCost - originalCost);

                if (changedCost.isNonZero()) {
                    // get the accounting type code from stock transaction reason code
                    if (changedCost.isPositive()) {
                        generalLedgerBuilderService.incrementInventory(glGroups,
                                getWarehouse(warehouseCode), "AVGCOST", changedCost,
                                "Storehouse Invtry UnitCost");
                    }
                    else {
                        generalLedgerBuilderService.decrementInventory(glGroups,
                                getWarehouse(warehouseCode), "AVGCOST", changedCost,
                                "Storehouse Invtry UnitCost");
                    }
                }
            }
        }
        ArrayList<FinancialGeneralLedgerPendingEntry> entries = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        for (WarehouseGlGroup group : glGroups.values()) {
            entries.add(group.getTargetEntry());
        }
        return entries;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getDocumentNumber()
     */
    public String getDocumentNumber() {
        return this.documentNumber;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getDocumentTypeCode()
     */
    public String getDocumentTypeCode() {
        return "SSCO";
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getFinancialGeneralLedgerPendingEntries()
     */
    public List<FinancialGeneralLedgerPendingEntry> getFinancialGeneralLedgerPendingEntries() {
        return this.financialGeneralLedgerPendingEntries;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#setFinancialGeneralLedgerPendingEntries(java.util.List)
     */
    public void setFinancialGeneralLedgerPendingEntries(
            List<FinancialGeneralLedgerPendingEntry> entries) {
        this.financialGeneralLedgerPendingEntries = entries;
    }

    public List<FinancialGeneralLedgerPendingEntry> getApprovedGeneralLedgerPendingEntries() {
        return SpringContext.getBean(GeneralLedgerProcessor.class)
                .getApprovedGeneralLedgerPendingEntries(getDocumentNumber());
    }
}
