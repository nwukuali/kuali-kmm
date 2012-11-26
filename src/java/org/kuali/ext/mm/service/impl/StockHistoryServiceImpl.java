package org.kuali.ext.mm.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.dataaccess.StockHistoryDAO;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.StockHistoryService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.TransactionalServiceUtils;

import java.sql.Timestamp;
import java.util.*;


public class StockHistoryServiceImpl implements StockHistoryService {

    private StockHistoryDAO stockHistoryDAO;

    public StockHistoryDAO getStockHistoryDAO() {
        return this.stockHistoryDAO;
    }

    public void setStockHistoryDAO(StockHistoryDAO stockHistoryDAO) {
        this.stockHistoryDAO = stockHistoryDAO;
    }

    /**
     * creates a list of stock history objects when worksheet gets approved
     */
    public List<StockHistory> createStockHistoryForWorksheetCountDocument(
            WorksheetCountDocument wdoc) {

        if (wdoc != null && MMUtil.isCollectionEmpty(wdoc.getStockCounts()))
            return null;

        List<StockHistory> lisResult = new ArrayList<StockHistory>(0);

        for (StockCount scount : wdoc.getStockCounts()) {
            int beforeQty = scount.getBeforeItemQty().intValue();
            double afterQty = scount.getStockCountItemQty() != null ? scount.getStockCountItemQty()
                    .intValue() : 0;
            double transQty = afterQty - beforeQty;
            // setting default value in case no reason selected by the user
            String reasonCode = StringUtils.isEmpty(scount.getStockTransReasonCd())
                    || transQty == 0 ? MMConstants.WorksheetCountDocument.STOCK_ITEMS_COUNTED
                    : scount.getStockTransReasonCd();

            if (scount.isReprinted()) {
                transQty = 0;
                afterQty = beforeQty;
                reasonCode = MMConstants.WorksheetCountDocument.WORKSHEET_STOCK_ITEM_REPRINTED;
            }
            StockHistory hisData = new StockHistory();
            hisData.setStockId(scount.getStockId());
            hisData.setBinId(scount.getBinId());
            hisData.setBeforeStockQty(beforeQty);
            hisData.setBeforeStockPrc(scount.getUnitPrice());
            hisData.setBeforeStockUnitOfIssueCd(scount.getBeforeItemUnitOfIssueCd());
            hisData.setAfterStockPrc(scount.getUnitPrice());
            hisData.setAfterStockQty(afterQty);
            hisData.setAfterStockUnitOfIssueCd(scount.getBeforeItemUnitOfIssueCd());
            hisData.setHistoryTransTimestamp(new Timestamp(new java.util.Date().getTime()));
            hisData.setWorksheetCountDocNbr(wdoc.getDocumentNumber());
            scount.setStockTransReasonCd(reasonCode);
            hisData.setStockTransReasonCode(reasonCode);
            hisData.setTransStockPrc(scount.getUnitPrice().multiply(new MMDecimal(transQty)));
            hisData.setTransStockQty(transQty);
            hisData.setTransStockUnitOfIssueCd(scount.getBeforeItemUnitOfIssueCd());
            hisData.setAfterStockUnitOfIssueCd(scount.getBeforeItemUnitOfIssueCd());
            lisResult.add(hisData);
        }
        return lisResult;
    }

    public StockHistoryLookupObject getStockHistoryLookupObjectForStockId(String stockId) {
        StockHistoryLookupObject result = new StockHistoryLookupObject();
        Stock stock = StoresPersistableBusinessObject.getObjectByPrimaryKey(Stock.class, stockId);
        result.setStock(stock);
        result.setStockHistoryObjs(this.getStockHistoryForStock(stockId));
        result.setPurchaseHistory(this.getPurchaseHistoryForStock(stockId));
        result.setSalesHistory(this.getSalesHistoryForStock(stockId));
        result.setCurrentStockHistoryInformation(this.getCurrentStockHistoryInformation(stockId));
        result.setStockId(Integer.valueOf(stockId));

        return result;
    }

    public Map<String, SalesHistory> getSalesHistoryForStock(String stockId) {
        return this.getStockHistoryDAO().getSalesHistoryForStock(stockId);
    }

    public CurrentStockHistoryInformation getCurrentStockHistoryInformation(String stockId) {
        return this.getStockHistoryDAO().getCurrentStockHistoryInformation(stockId);
    }

    public Collection<PurchaseHistory> getPurchaseHistoryForStock(String stockId) {
        return this.getStockHistoryDAO().getPurchaseHistoryForStock(stockId);
    }


    public List<StockHistory> getStockHistoryForStock(String stockId) {
        Map fieldValues = new HashMap();
        fieldValues.put(MMConstants.StockHistory.STOCK_ID, stockId);

        List<StockHistory> result = (List<StockHistory>) MMServiceLocator
                .getBusinessObjectService().findMatchingOrderBy(StockHistory.class, fieldValues,
                        MMConstants.StockHistory.HISTORY_TRANS_TIMESTAMP, false);

        return result;
    }
    
    public StockHistory getStockHistoryForStockAndCheckinDocument(String stockId, String checkinDocNumber) {
        Map fieldValues = new HashMap();
        fieldValues.put(MMConstants.StockHistory.STOCK_ID, stockId);
        fieldValues.put(MMConstants.StockHistory.CHECKIN_DOCUMENT_NUMBER, checkinDocNumber);

        Collection result = KRADServiceLocator.getBusinessObjectService().findMatching(StockHistory.class, fieldValues);

        Iterator it = result.iterator();
        if(!it.hasNext())
            return null;
        
        return (StockHistory) TransactionalServiceUtils.retrieveFirstAndExhaustIterator(it);
    }

    public StockHistory createStockHistoryForReturnLine(ReturnDocument rdoc, ReturnDetail rdetail, StockBalance afterStockBalance) {
        Double adjustmentQuantity = Double.valueOf(rdetail.getReturnQuantity());
        
        OrderDetail orderDetail = rdetail.getOrderDetail();        
        MMDecimal returnUnitCostAmount = (rdetail.getOrderDetail().getOrderItemCostAmt() != null ? orderDetail
                .getOrderItemCostAmt() : MMDecimal.ZERO);
        
        String reasonCode = null;
        if (rdoc.isCurrDocVendorReturnDoc())
            reasonCode = MMConstants.ReturnDocument.VENDOR_RETURN_STOCK_TRANS_REASON;
        else
            reasonCode = MMConstants.ReturnDocument.CUSTOMER_RETURN_STOCK_TRANS_REASON;

        
        StockHistory result = this.createStockHistoryForBalanceChange(afterStockBalance, adjustmentQuantity, reasonCode);
       
        result.setBeforeStockPrc(returnUnitCostAmount);
        result.setAfterStockPrc(returnUnitCostAmount);
        result.setTransStockPrc(returnUnitCostAmount
                .multiply(new MMDecimal(adjustmentQuantity)));

        return result;
    }

    /**
     * creates a list of stock History objects to be created when checking in document
     */
    public List<StockHistory> createStockHistoryForCheckinDocument(CheckinDocument checkinDoc,
            Map<String, MMDecimal> oldCost) {

        if (checkinDoc == null || MMUtil.isMapEmpty(oldCost)
                || MMUtil.isCollectionEmpty(checkinDoc.getCheckinDetails()))
            return null;

        List<StockHistory> lisResult = new ArrayList<StockHistory>(0);

        for (CheckinDetail cdetail : checkinDoc.getCheckinDetails()) {
            if (cdetail.getAcceptedItemQty() != null && cdetail.getAcceptedItemQty() != 0) {
                String unitOfIssueCode = cdetail.getOrderDetail().getStockUnitOfIssueCd();
                MMDecimal beforePrice = oldCost.get(cdetail.getStockId());
                Integer bquantity = cdetail.getBin() != null ? cdetail.getBin().getStockBalance() != null ? cdetail
                        .getBin().getStockBalance().getQtyOnHand()
                        - cdetail.getAcceptedItemQty()
                        : 0
                        : 0;
                Integer aquantity = cdetail.getBin() != null ? cdetail.getBin().getStockBalance() != null ? cdetail
                        .getBin().getStockBalance().getQtyOnHand()
                        : cdetail.getAcceptedItemQty()
                        : cdetail.getAcceptedItemQty();
                        
                OrderDetail odetail = cdetail.getOrderDetail();
                MMDecimal price = new MMDecimal(0.0);
                if (odetail != null) {
                    price = (odetail.getOrderItemCostAmt() == null ? MMDecimal.ZERO : odetail
                            .getOrderItemCostAmt());
                }
                else {
                    MMDecimal sprice = cdetail.getStock().getStockPrice();
                    price = ObjectUtils.isNull(sprice) ? MMDecimal.ZERO : sprice;
                }             

                StockHistory hisData = new StockHistory();
                hisData.setStockId(cdetail.getStockId());
                hisData.setBinId(cdetail.getBinId());
                hisData.setBeforeStockQty(bquantity);
                hisData.setBeforeStockPrc(beforePrice != null ? beforePrice : MMDecimal.ZERO);
                hisData.setBeforeStockUnitOfIssueCd(unitOfIssueCode);
                hisData.setAfterStockPrc(cdetail.getStock().getStockPrice());
                hisData.setAfterStockQty((double) (aquantity > 0 ? aquantity : 0));
                hisData.setAfterStockUnitOfIssueCd(unitOfIssueCode);
                hisData.setTransStockQty(Double.valueOf(cdetail.getAcceptedItemQty()));
                hisData.setTransStockPrc(price.multiply(new MMDecimal(hisData.getTransStockQty())));
                hisData.setTransStockUnitOfIssueCd(cdetail.getStock().getSalesUnitOfIssueCd());
                hisData.setHistoryTransTimestamp(new Timestamp(new java.util.Date().getTime()));
                hisData.setCheckinDocNbr(checkinDoc.getDocumentNumber());
                hisData
                        .setStockTransReasonCode(MMConstants.CheckinDocument.CHECKIN_TRANSACTION_REASON_CODE);
                lisResult.add(hisData);
            }

        }

        return lisResult;
    }

    public StockHistory createStockHistoryForBalanceChange(StockBalance afterStockBalance,
            Double adjustmentQuantity, String transReasonCode) {
        StockHistory stockHistory = new StockHistory();
        stockHistory.setBeforeStockPrc(afterStockBalance.getStock().getStockPrice());
        stockHistory.setBeforeStockQty(afterStockBalance.getQtyOnHand()
                - adjustmentQuantity.intValue());
        stockHistory.setBeforeStockUnitOfIssueCd(afterStockBalance.getStock()
                .getSalesUnitOfIssueCd());

        stockHistory.setAfterStockPrc(afterStockBalance.getStock().getStockPrice());
        stockHistory.setAfterStockQty(afterStockBalance.getQtyOnHand().doubleValue());
        stockHistory.setAfterStockUnitOfIssueCd(afterStockBalance.getStock()
                .getSalesUnitOfIssueCd());

        stockHistory.setTransStockQty(adjustmentQuantity);
        stockHistory.setTransStockPrc(stockHistory.getAfterStockPrc().multiply(
                new MMDecimal(stockHistory.getTransStockQty())));
        stockHistory.setTransStockUnitOfIssueCd(afterStockBalance.getStock()
                .getSalesUnitOfIssueCd());

        stockHistory.setStockId(afterStockBalance.getStockId());
        stockHistory.setBinId(afterStockBalance.getBinId());
        stockHistory.setHistoryTransTimestamp(CoreApiServiceLocator.getDateTimeService()
                .getCurrentTimestamp());
        stockHistory.setStockTransReasonCode(transReasonCode);

        return stockHistory;
    }
    
    public StockHistory createStockHistoryForCostAdjustment(MMDecimal stockCost, MMDecimal oldStockCost, StockBalance stockBalance) {
        StockHistory stockHistory = new StockHistory();
        
        stockHistory.setBeforeStockQty(stockBalance.getQtyOnHand());
        stockHistory.setBeforeStockPrc(oldStockCost);
        stockHistory.setBeforeStockUnitOfIssueCd(stockBalance.getStock().getSalesUnitOfIssueCd());
        stockHistory.setAfterStockQty((double) stockBalance.getQtyOnHand());
        stockHistory.setAfterStockPrc(stockCost);
        stockHistory.setAfterStockUnitOfIssueCd(stockBalance.getStock().getSalesUnitOfIssueCd());
        stockHistory.setTransStockQty((double) stockBalance.getQtyOnHand());
        MMDecimal trasactionCost = stockCost.subtract(oldStockCost).multiply(new MMDecimal(stockHistory.getTransStockQty()));
        stockHistory.setTransStockPrc(trasactionCost);
        stockHistory.setTransStockUnitOfIssueCd(stockBalance.getStock().getSalesUnitOfIssueCd());  
        
        stockHistory.setStockId(stockBalance.getStockId());
        stockHistory.setBinId(stockBalance.getBinId());
        stockHistory.setStockTransReasonCode(MMConstants.StockTransReason.AVGCOST);
        stockHistory.setHistoryTransTimestamp(CoreApiServiceLocator.getDateTimeService()
                .getCurrentTimestamp());
        
        return stockHistory;
    }


    /**
     * @see org.kuali.ext.mm.service.StockHistoryService#createStockHistoryForReceiptCorrection(org.kuali.ext.mm.businessobject.CheckinDetail, org.kuali.ext.mm.businessobject.StockBalance, org.kuali.ext.mm.util.MMDecimal)
     */
    public StockHistory createStockHistoryForReceiptCorrection(CheckinDetail correctionDetail, StockBalance afterStockBalance,
            MMDecimal beforeCost) {        
        
        Integer correctedQty = correctionDetail.getCorrectedQuantity();
        StockHistory stockHistory = this.createStockHistoryForBalanceChange(
                afterStockBalance, 
                -correctedQty.doubleValue(), 
                MMConstants.StockTransReason.DCORRECT);
        stockHistory.setBeforeStockPrc(beforeCost);
        stockHistory.setCheckinDocNbr(correctionDetail.getCheckinDocumentNumber());
        
        return stockHistory;
    }
}