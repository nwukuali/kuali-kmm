/**
 *
 */
package org.kuali.ext.mm.dataaccess.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.MMBusinessObjectDao;
import org.kuali.ext.mm.dataaccess.StockHistoryDAO;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.service.BackOrderService;
import org.kuali.ext.mm.service.CheckinOrderService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.sys.service.impl.FiscalYear;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.*;


/**
 * @author rponraj
 * 
 */
public class StockHistoryDAOOJB extends PlatformAwareDaoBaseOjb implements StockHistoryDAO {

    public CurrentStockHistoryInformation getCurrentStockHistoryInformation(String stockId) {

        Stock stock = StoresPersistableBusinessObject.getObjectByPrimaryKey(Stock.class, stockId);

        CurrentStockHistoryInformation result = new CurrentStockHistoryInformation();
        Integer qtyOnHand = stock.getTotalStockedQuantity();

        result.setQuantityOnHand(qtyOnHand);
        Integer stockSafetyQty = stock.getSafetyStockQty() != null ? stock.getSafetyStockQty()
                .intValue() : 0;

        Integer committedQty = getCommittedQty(stockId);
        result.setQuantityAllocated(committedQty);
        
        Integer availableQty = (qtyOnHand < committedQty + stockSafetyQty) ? 0 : qtyOnHand - (stockSafetyQty + committedQty);
        result.setQtyAvailable(availableQty);        

        Integer orderItemQty = getTotalOrderItemQty(stockId);
        result.setOrderQuantity(orderItemQty);

        Integer backOrderQty = getBackOrderQty(stockId);
        result.setBackOrderQty(backOrderQty);

        result.setReorderPoint(stock.getReorderPointQty() == null ? 0 : stock.getReorderPointQty()
                .intValue());
        result.setMinimumOrderQty(stock.getMinimumOrderQty() == null ? 0 : stock
                .getMinimumOrderQty().intValue());
        result.setPerishable(stock.isPerishableInd());
        result.setHazardous(!MMUtil.isCollectionEmpty(stock.getHazardousMateriels()));
        result.setLastReorderDate(this.getOrderDate(stockId));
        result.setAverageUnitCost(stock.getCurrentStockCost() == null ? MMDecimal.ZERO : stock
                .getCurrentStockCost().getStockCst());
        return result;
    }

    private Date getOrderDate(String stockId) {
        String stockIdField = MMConstants.OrderDocument.ORDER_DETAILS + "." 
            + MMConstants.OrderDetail.CATALOG_ITEM + "." 
            + MMConstants.CatalogItem.STOCK_ID;
        HashMap<String, Object> fieldValues = new HashMap<String, Object>();        
        fieldValues.put(stockIdField, stockId);
        fieldValues.put(MMConstants.OrderDocument.ORDER_TYPE_CD, MMConstants.OrderType.STOCK);
        
        String sortField = MMConstants.OrderDocument.ORDER_CREATE_DATE;
        
        Collection<OrderDocument> results = KRADServiceLocator.getBusinessObjectService().findMatchingOrderBy(OrderDocument.class, fieldValues, sortField, false);
        Date orderDate = null;
        if(results.iterator().hasNext()) {
            orderDate = results.iterator().next().getCreationDate();
        }
        
        return orderDate;
    }
    
    private Integer getBackOrderQty(String stockId) {

        Integer result = 0;
        BackOrderService backOrderService = SpringContext.getBean(BackOrderService.class);
        Collection<BackOrder> backorderList = backOrderService.getUnfilledBackOrdersForStock(stockId);
        Iterator<BackOrder> boIterator = backorderList.iterator();
        while(boIterator.hasNext()) {
            result += boIterator.next().getBackOrderStockQty();
        }
        
        return result;

    }

    private Integer getTotalOrderItemQty(String stockId) {
        Integer result = 0;

        Criteria criteria = new Criteria();

        Criteria stockCriteria = new Criteria();
        String stockIdField = MMConstants.OrderDetail.CATALOG_ITEM + "."
            + MMConstants.CatalogItem.STOCK_ID;
        stockCriteria.addEqualTo(stockIdField, stockId); 
        criteria.addAndCriteria(stockCriteria);

        Criteria orderTypeCriteria = new Criteria();
        orderTypeCriteria.addEqualTo(MMConstants.OrderDetail.ORDER_DOCUMENT + "."
                + MMConstants.OrderDocument.ORDER_TYPE_CD, MMConstants.OrderType.STOCK);
        orderTypeCriteria.addIn(MMConstants.OrderDocument.ORDER_STATUS_CD, Arrays.asList(
                MMConstants.OrderStatus.ORDER_LINE_OPEN,
                MMConstants.OrderStatus.ORDER_LINE_RECEIVING));
        criteria.addAndCriteria(orderTypeCriteria);

        String[] groupBy = new String[] { stockIdField };
        String[] attributes = new String[] { " sum(orderItemQty) " };

        ReportQueryByCriteria query = QueryFactory.newReportQuery(OrderDetail.class, criteria);
        query.setAttributes(attributes);
        query.addGroupBy(groupBy);

        Iterator iter = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(query);
        if (iter != null) {
            while (iter.hasNext()) {
                Object[] vals = (Object[]) iter.next();
                if (vals != null) {
                    result = ObjectUtils.isNull(vals[0]) ? 0 : Integer.valueOf(vals[0].toString());
                }
            }
        }

        return result;
    }

    private Integer getCommittedQty(String stockId) {
       return SpringContext.getBean(StockService.class).getCommittedStockQuantity(stockId);
    }

    public Collection<PurchaseHistory> getPurchaseHistoryForStock(String stockId) {
        Map<Integer, PurchaseHistory> resultMap = new HashMap<Integer, PurchaseHistory>();
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.CheckinDetail.STOCK_ID, stockId);
        fieldValues.put(MMConstants.PurchaseHistory.ORDER_TYPE_CODE,
                MMConstants.OrderType.STOCK);
        fieldValues.put(MMConstants.CheckinDetail.CHECKIN_DOC + "." + MMConstants.CheckinDocument.FINAL_IND, "Y");
        fieldValues.put(MMConstants.CheckinDetail.CORRECTED_CHECKIN_DETAIL_ID, null);

        Collection detailResults = SpringContext.getBean(MMBusinessObjectDao.class).findMatching(CheckinDetail.class, fieldValues);

        Iterator<CheckinDetail> it = detailResults.iterator();
        CheckinOrderService checkinService = MMServiceLocator.getCheckinOrderService();
        while (it.hasNext()) {            
            CheckinDetail detail = it.next();
            Integer acceptedQty = checkinService.getActualAcceptedQuantity(detail);
            Integer rejectedQty = checkinService.getActualRejectedQuantity(detail);
            if(resultMap.containsKey(detail.getOrderDetailId())) {
                resultMap.get(detail.getOrderDetailId()).setNumberOfShipments(
                        resultMap.get(detail.getOrderDetailId()).getNumberOfShipments() + 1);
                //Make sure to get actual quantities, meaning from corrections before committing
                resultMap.get(detail.getOrderDetailId()).setAcceptedQty(
                        resultMap.get(detail.getOrderDetailId()).getAcceptedQty() 
                        + acceptedQty);
                resultMap.get(detail.getOrderDetailId()).setRejectedQty(
                        resultMap.get(detail.getOrderDetailId()).getRejectedQty() 
                        + rejectedQty);
            }
            else {
                PurchaseHistory data = new PurchaseHistory();
                detail.refreshReferenceObject(MMConstants.CheckinDetail.ORDER_DETAIL);
                data.setPoNumber(String.valueOf(detail.getPoId()));
                data.setNumberOfShipments(1);
                data.setOrderQuantity(detail.getOrderDetail().getOrderItemQty());
                data.setAcceptedQty(acceptedQty);
                data.setRejectedQty(rejectedQty);
                data.setOrderDate(detail.getOrderDetail().getOrderDocument().getCreationDate());
                
                String docNbr = detail.getCheckinDocumentNumber();
                StockHistory stockHistory = MMServiceLocator.getStockHistoryService().getStockHistoryForStockAndCheckinDocument(stockId, docNbr);
                data.setReceivedDate((stockHistory != null ) ? stockHistory.getHistoryTransTimestamp() : null);
                MMDecimal additionalCost = detail.getOrderDetail().getOrderItemAdditionalCostAmt();
                data.setChargedAdditionally(additionalCost == null ? false 
                        : additionalCost.doubleValue() > 0);
                data.setOrderItemAdditionalCostAmt(additionalCost);
                data.setOrderItemPriceAmt(detail.getOrderDetail().getOrderItemPriceAmt());
                data.setOrderItemTaxAmt(detail.getOrderDetail().getOrderItemTaxAmt());
                resultMap.put(detail.getOrderDetailId(), data);
            }
        }
        List<PurchaseHistory> purchaseHistoryList = new ArrayList<PurchaseHistory>();
        for(PurchaseHistory pHistory : resultMap.values()) {
            purchaseHistoryList.add(pHistory);
        }

        return purchaseHistoryList;
    }

    /**
     * returns a map of saleshistory keyed by the name of criteria
     * 
     * @see org.kuali.ext.mm.dataaccess.ReturnOrderDAO#getSalesHistoryForStock(java.lang.String)
     */
    public Map<String, SalesHistory> getSalesHistoryForStock(String stockId) {

        Map<String, SalesHistory> dataCache = new HashMap<String, SalesHistory>(0);

        dataCache.put(MMConstants.SalesHistory.LAST_FISCAL_YEAR, getSalesHistoryForDate(stockId,
                FiscalYear.getLastFiscalYear().getBeginDate(), FiscalYear.getLastFiscalYear()
                        .getEndDate()));
        dataCache.put(MMConstants.SalesHistory.SECOND_FISCAL_YEAR, getSalesHistoryForDate(stockId,
                FiscalYear.getSecondFiscalYear().getBeginDate(), FiscalYear.getSecondFiscalYear()
                        .getEndDate()));
        dataCache.put(MMConstants.SalesHistory.THIRD_FISCAL_YEAR, getSalesHistoryForDate(stockId,
                FiscalYear.getThirdFiscalYear().getBeginDate(), FiscalYear.getThirdFiscalYear()
                        .getEndDate()));

        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        int curMonth = Calendar.getInstance().get(Calendar.MONTH);
        int curday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        Date currYearBeginDate = MMUtil.getDateFor(curYear, Calendar.JANUARY, 01, 00, 00, 00);
        // Date currYearEndDate = MMUtil.getDateFor(curYear , curMonth, curday , 23, 59, 59);
        Date currYearEndDate = new Date();

        dataCache.put(MMConstants.SalesHistory.CURRENT_YEAR_TO_DATE, getSalesHistoryForDate(
                stockId, currYearBeginDate, currYearEndDate));

        Date cumBeginDate = MMUtil.getDateFor(curYear - 1, curMonth, curday, 00, 00, 00);
        // Date currYearEndDate = MMUtil.getDateFor(curYear , curMonth, curday , 23, 59, 59);

        dataCache.put(MMConstants.SalesHistory.CUMMULATIVE_12_MONTHS, getSalesHistoryForDate(
                stockId, cumBeginDate, currYearEndDate));

        return dataCache;
    }

    private SalesHistory getSalesHistoryForDate(String stockId, Date beginDate, Date endDate) {

        SalesHistory result = new SalesHistory();
        result.setFormDate(beginDate);
        result.setToDate(endDate);

        if (beginDate == null || endDate == null || StringUtils.isEmpty(stockId))
            return result;

        Criteria criteria = new Criteria();

        Criteria stockCriteria = new Criteria();
        stockCriteria.addEqualTo(MMConstants.OrderDetail.CATALOG_ITEM + "."
                + MMConstants.CatalogItem.STOCK_ID, stockId);
        criteria.addAndCriteria(stockCriteria);

        Criteria orderTypeCriteria = new Criteria();
        orderTypeCriteria.addEqualTo(MMConstants.OrderDetail.ORDER_DOCUMENT + "."
                + MMConstants.OrderDocument.ORDER_TYPE_CD, MMConstants.OrderType.WAREHS);
        criteria.addAndCriteria(orderTypeCriteria);


        Timestamp btime = MMUtil.getSQLTimeStampForDate(beginDate);
        Timestamp etime = MMUtil.getSQLTimeStampForDate(endDate);


        Criteria orderCreateDateCriteria = new Criteria();
        // try{
        orderCreateDateCriteria.addBetween(MMConstants.OrderDetail.ORDER_DOCUMENT + "."
                + MMConstants.OrderDocument.CREATION_DATE, btime, etime);
        criteria.addAndCriteria(orderCreateDateCriteria);

        String[] attributes = new String[] { "sum(orderItemQty)", "sum(orderItemPriceAmt)",
                "sum(orderItemTaxAmt)" };

        /**
         * Sales History would be the MM Orders Detail records for the STOCK_ID where the MM_ORDER_DOC_T.ORDER_TYPE_CD = ‘WAREHS’.
         */

        /**
         * KFSPropertyConstants.UNIVERSITY_FISCAL_YEAR, KFSPropertyConstants.CHART_OF_ACCOUNTS_CODE,
         * KFSPropertyConstants.ACCOUNT_NUMBER, 3 KFSPropertyConstants.OBJECT_CODE, OBJ_TYP_CD,
         * "sum(currentBudgetLineBalanceAmount)", "sum(accountLineActualsBalanceAmount)", "sum(accountLineEncumbranceBalanceAmount)"
         */

        String[] groupBy = new String[] { MMConstants.OrderDetail.ORDER_DETAIL_ID };

        /**
         * KFSPropertyConstants.UNIVERSITY_FISCAL_YEAR, KFSPropertyConstants.CHART_OF_ACCOUNTS_CODE,
         * KFSPropertyConstants.ACCOUNT_NUMBER, KFSPropertyConstants.OBJECT_CODE, OBJ_TYP_CD
         */
        ReportQueryByCriteria query = QueryFactory.newReportQuery(OrderDetail.class, criteria);
        query.setAttributes(attributes);
        query.addGroupBy(groupBy);

        Iterator iter = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(query);

        if (iter == null)
            return result;

        KualiDecimal totOrderItemQty = KualiDecimal.ZERO;
        KualiDecimal totOrderItemPriceAmt = KualiDecimal.ZERO;

        int index = 0;

        while (iter.hasNext()) {
            index++;
            Object[] vals = (Object[]) iter.next();
            KualiDecimal qty = !ObjectUtils.isNull(vals[0]) ? new KualiDecimal(vals[0].toString())
                    : KualiDecimal.ZERO;
            KualiDecimal price = !ObjectUtils.isNull(vals[1]) ? new KualiDecimal(vals[1].toString())
                    : KualiDecimal.ZERO;
            KualiDecimal tax = !ObjectUtils.isNull(vals[2]) ? new KualiDecimal(vals[2].toString())
                    : KualiDecimal.ZERO;
            KualiDecimal totalPrice = price.add(tax).multiply(qty);
            totOrderItemQty = totOrderItemQty.add(qty);
            totOrderItemPriceAmt = totOrderItemPriceAmt.add(totalPrice);
        }

        result.setNumberOfTranscations(index);
        result.setNumberOfUnits(totOrderItemQty.intValue());
        result.setTotalCost(totOrderItemPriceAmt);

        return result;
    }

}
