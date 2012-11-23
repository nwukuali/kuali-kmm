package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.service.IReturnCommand;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InventoryAdjustmentReturnActionService implements IReturnCommand {

    public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
        return true;
    }

    public void adjustInventoryForOrder(ReturnDocument rdoc, ReturnDetail rdetail) {
        StockService stockService = MMServiceLocator.getStockService();
        OrderDetail odetail = rdetail.getOrderDetail();
        StockBalance sbalance = null;
        Integer qty = rdetail.getReturnQuantity();
        Bin bin = getBinFromPickLines(odetail, qty);

        if (bin == null)
            return;

        if (rdoc.isCurrDocVendorReturnDoc())
            qty = qty * -1;

        Stock stock = odetail.getCatalogItem().getStock();
        List<StockCost> stockPrices = stock.getStockPrices();

        rdetail.setBin(bin);
        rdetail.setBinId(bin.getBinId());
        Integer stockQty = rdetail.getOrderDetail().getCatalogItem().getStock()
                .getTotalStockedQuantity();
        
        List<StockCost> newCost = new ArrayList<StockCost>();        
        //Only update weighted average for returns
        for(StockCost cost : stockService.calculateStockPrices(odetail.getOrderItemCostAmt(), stock, qty, stockQty, stockPrices)) { 
            if(MMConstants.CostCode.WEIGHTED_AVERAGE.equals(cost.getCostCode())) { 
                newCost.add(MMServiceLocator.getStockService().updateStockCost(stock.getStockId(), cost.getStockCst(), cost.getCostCd()));
            }
        }
        
        rdetail.getOrderDetail().getCatalogItem().getStock().setStockPrices(newCost);
        sbalance = bin.getStockBalance();
        if (ObjectUtils.isNull(sbalance)) {
            sbalance = new StockBalance();
            sbalance.setBinId(bin.getBinId());
            sbalance.setBin(bin);
            sbalance.setStockId(rdetail.getOrderDetail().getCatalogItem().getStockId());
        }

        sbalance.addItemQuantityToBin(qty);
        rdetail.setBinZoneDesc(bin.getBinDisDesc());
        rdetail.setBin(bin);
        rdetail.setBinId(bin.getBinId());
        StockHistory shistory = MMServiceLocator.getStockHistoryService()
                .createStockHistoryForReturnLine(rdoc, rdetail, sbalance);
        MMServiceLocator.getBusinessObjectService().save(sbalance);
        MMServiceLocator.getBusinessObjectService().save(shistory);
        MMServiceLocator.getBusinessObjectService().save(newCost);

    }

    /**
     * adjust the stock price and stock balance in MM_STOCK_BALACE_T table
     * 
     * @see org.kuali.ext.mm.service.impl.BaseReturnAction#execute(org.kuali.ext.mm.document.ReturnDocument,
     *      org.kuali.ext.mm.businessobject.ReturnDetail)
     */
    public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
        if (ObjectUtils.isNotNull(rdetail.getOrderDetailId())
                && ObjectUtils.isNotNull(rdoc.getOrderDocument())) {
            adjustInventoryForOrder(rdoc, rdetail);
            this.calculateReturnDocAmount(rdoc, rdetail);
        }
    }

    /**
     * returns the pick list line for the passed order line
     * 
     * @param odetail
     * @return
     */
    public Bin getBinFromPickLines(OrderDetail odetail, Integer qty) {

        PickListLine returnVal = null;

        BusinessObjectService boService = SpringContext.getBean(BusinessObjectService.class);
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.OrderDetail.ORDER_DETAIL_ID, String.valueOf(odetail
                .getOrderDetailId()));
        List<PickListLine> result = (List<PickListLine>) boService.findMatchingOrderBy(
                PickListLine.class, fieldValues, "LAST_UPDATE_DT", false);

        for (PickListLine line : result) {
            if (line.getPickStatusCodeCd().equalsIgnoreCase(
                    MMConstants.PickStatusCode.PICK_STATUS_PCKD)
                    || line.getPickStatusCodeCd().equalsIgnoreCase(
                            MMConstants.PickStatusCode.PICK_STATUS_BACK))
                returnVal = line;
        }

        if (returnVal == null) {
            for (PickListLine line : result) {
                if (line.getBin().getAvailableQty() >= qty)
                    returnVal = line;
            }

        }

        if (returnVal != null)
            return returnVal.getBin();
        return null;

    }

    private void calculateReturnDocAmount(ReturnDocument rdoc, ReturnDetail rdetail) {
        if (ObjectUtils.isNotNull(rdetail.getOrderDetailId())
                && ObjectUtils.isNotNull(rdoc.getOrderDocument())) {
            MMDecimal amt = rdetail.getInventoryCost();
            rdoc.setInventoryReturnAmt(rdoc.getInventoryReturnAmt().add(amt));
        }
    }

}
