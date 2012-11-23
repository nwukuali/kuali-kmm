package org.kuali.ext.mm.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.MMBusinessObjectDao;
import org.kuali.ext.mm.dataaccess.QueryElement;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.StockHistoryService;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.TransactionalServiceUtils;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;


@Transactional
public class StockServiceImpl implements StockService {

    BusinessObjectService businessObjectService;

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.ext.mm.service.StockService#retrieveStockBalance(java.lang.String)
     */
    public StockBalance retrieveStockBalance(Integer binId) {
        return (StockBalance)ObjectUtils.deepCopy(businessObjectService.findBySinglePrimaryKey(StockBalance.class, binId));
    }


    /**
     * @see org.kuali.ext.mm.service.StockService#adjustStockQuantityOnHand(org.kuali.ext.mm.businessobject.StockBalance, java.lang.Integer)
     */
    public StockBalance adjustStockQuantityOnHand(StockBalance stockBalance, Integer quantity) {
        if (stockBalance == null)
            return null;
         
        StockBalance existingStockBalance = retrieveStockBalance(stockBalance.getBinId());
        
        if(existingStockBalance == null) {
            existingStockBalance = stockBalance;
            existingStockBalance.setQtyOnHand(quantity);
        }
        else {
            existingStockBalance.setQtyOnHand(existingStockBalance.getQtyOnHand() + quantity);
            existingStockBalance.setTransferSourceBinId(stockBalance.getTransferSourceBinId());
            existingStockBalance.setStockTransReason(stockBalance.getStockTransReason());
            existingStockBalance.setQuantityBeingAdjusted(stockBalance.getQuantityBeingAdjusted());
            existingStockBalance.setQuantityBeingAdjustedFromOldToNew(stockBalance.getQuantityBeingAdjustedFromOldToNew());
        }
        
        return existingStockBalance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.ext.mm.service.StockService#getStockByDistributorNumber(java.lang.String)
     */
    public Stock getStockByDistributorNumber(String stockDistributorNumber) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.Stock.STOCK_DISTRIBUTOR_NBR, stockDistributorNumber);
        Collection<Stock> stock = businessObjectService.findMatching(Stock.class, fieldValues);

        return TransactionalServiceUtils.retrieveFirstAndExhaustIterator(stock.iterator());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.ext.mm.service.StockService#postSaleToStockHistory(org.kuali.ext.mm.businessobject.StockBalance,
     *      org.kuali.ext.mm.businessobject.StockBalance, java.lang.String)
     */
    public void postSaleToStockHistory(StockBalance beforeStockBalance,
            StockBalance afterStockBalance, String referenceDocNbr) {
        Double transQuantity = afterStockBalance.getQtyOnHand() - beforeStockBalance.getQtyOnHand().doubleValue();
        StockHistory saleHistory = SpringContext.getBean(StockHistoryService.class).createStockHistoryForBalanceChange(afterStockBalance, transQuantity, MMConstants.StockTransReason.SALE);

        saleHistory.setPickVerifyDocNbr(referenceDocNbr);

        businessObjectService.save(saleHistory);
    }

    /**
     * calculates the stock price of an order line
     * 
     * @param unitCost of order item
     * @param stock stock for which price is calculated
     * @param quantity checked in/returned item quantity
     * @param stockQty total available quantity of the stock
     * @param stockCosts
     * @return
     */
    public List<StockCost> calculateStockPrices(MMDecimal unitCost, Stock stock, int quantityVal,
            int stockQty, List<StockCost> stockCosts) {
        int quantity = quantityVal;
        if (unitCost == null || stock == null)
            return null;

        ArrayList<StockCost> prices = new ArrayList<StockCost>(2);

        boolean needsDecr = false;

        // If the quantity is less than zero then set the flag
        if (quantity < 0) {
            quantity = quantity * -1;
            needsDecr = true;
        }

        // int weightedCost = orderCost * quantity;
        MMDecimal weightedCost = unitCost.multiply(new MMDecimal(Math.abs(quantity)));

        StockCost priceForCode1 = null;
        StockCost priceForCode2 = null;

        // retrieve the individual stock cost
        if (!MMUtil.isCollectionEmpty(stockCosts)) {
            for (StockCost scost : stockCosts) {
                if (scost.getCostCd().equalsIgnoreCase(MMConstants.CostCode.STANDARD_PRICE))
                    priceForCode1 = scost;
                else if (scost.getCostCd().equalsIgnoreCase(MMConstants.CostCode.WEIGHTED_AVERAGE))
                    priceForCode2 = scost;
            }
        }

        // if price code is null then create a new object
        if (priceForCode1 == null) {
            priceForCode1 = new StockCost();
            priceForCode1.setStockId(stock.getStockId());
            priceForCode1.setStock(stock);
            priceForCode1.setCostCd(MMConstants.CostCode.STANDARD_PRICE);
        }

        // logic for calculating price code 1
        priceForCode1.setStockCst(unitCost);
        prices.add(priceForCode1);

        // logic for caculating price code 2

        // if price code2 is null then create a new object

        if (priceForCode2 == null) {

            priceForCode2 = new StockCost();
            priceForCode2.setStockId(stock.getStockId());
            priceForCode2.setStock(stock);
            priceForCode2.setCostCd(MMConstants.CostCode.WEIGHTED_AVERAGE);
        }

        if (priceForCode2.getStockCst() == null || priceForCode2.getStockCst().isZero()
                || stockQty == 0) {
            priceForCode2.setStockCst(unitCost);
        }
        else {

            MMDecimal totPrice = null;

            // if decrement flag is set
            // then subtract the current cost from the total cost
            // else add the two costs

            if (!needsDecr)
                totPrice = priceForCode2.getStockCst().multiply(new MMDecimal(stockQty)).add(
                        weightedCost);
            else
                totPrice = priceForCode2.getStockCst().multiply(new MMDecimal(stockQty)).subtract(
                        weightedCost);

            MMDecimal totQty = MMDecimal.ZERO;

            // if needs decrement is true
            // then decrement the quantity from the total stock quantity

            if (!needsDecr)
                totQty = new MMDecimal(stockQty + quantity);
            else
                totQty = new MMDecimal(stockQty - quantity);

            if (totPrice == null || totPrice.isZero() || totQty.isZero()) {
                priceForCode2.setStockCst(MMDecimal.ZERO);
            }
            else {
                priceForCode2.setStockCst(totPrice.divide(totQty));
            }
        }

        prices.add(priceForCode2);

        return prices;
    }

    /**
     * Returns a bin a for a particular stock with available quantity greater than the passed quantity
     */
    public Bin getEmptyBinForStockId(CheckinDocument cdoc, String stockId, int quantity) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.Stock.STOCK_ID, stockId);
        Bin bin = null;
        List<Bin> bins = this.getAvailableBinsForStockId(stockId, null, 
                MMConstants.StockBalance.QTY_ON_HAND,
                false);
        List<Integer> addedBins = cdoc.getCheckedinBins();

        if (!MMUtil.isCollectionEmpty(bins)) {
            for (Bin cbin : bins) {
                if (cbin != null) {
                    if (cbin.getAvailableQty() > quantity) {
                        if (!addedBins.contains(cbin.getBinId())) {
                            bin = cbin;
                            break;
                        }
                    }
                }
            }
        }
        if (bin == null) {
            bin = MMServiceLocator.getCheckinOrderService().getEmptyBin(cdoc, quantity);
        }
        return bin;
    }

    
    /**
     * @see org.kuali.ext.mm.service.StockService#getAvailableBinsForStockId(java.lang.String, java.lang.String)
     */
    public List<Bin> getAvailableBinsForStockId(String stockId, String warehouseCode, String sortBy, boolean sortAscending) {

        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.Stock.STOCK_ID, stockId);

        List<Bin> bins = new ArrayList<Bin>(0);

        List<StockBalance> stockBalances = (List<StockBalance>) businessObjectService
                .findMatchingOrderBy(StockBalance.class, fieldValues, sortBy, sortAscending);

        if (!MMUtil.isCollectionEmpty(stockBalances)) {
            for (StockBalance sbalance : stockBalances) {
                if(ObjectUtils.isNull(sbalance.getBin()) && sbalance.getBinId() != null) {
                    sbalance.refreshReferenceObject(MMConstants.StockBalance.BIN);
                }
                Bin bin = sbalance.getBin();
                if (ObjectUtils.isNotNull(bin)) {                    
                    if(warehouseCode == null 
                            || (ObjectUtils.isNotNull(bin.getZone()) 
                                    && warehouseCode.equals(bin.getZone().getWarehouseCd()))
                            && bin.isActive() 
                            && bin.getZone().isActive() 
                            && bin.getZone().getWarehouse().isActive()) {
                        bins.add(bin);
                    }
                }
            }
        }

        return bins;

    }


    /**
     * returns the two types of stock price for the passed stock
     */
    public List<StockCost> getStockPricesForStock(String stockId) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.Stock.STOCK_ID, stockId);
        List<StockCost> stockPrices = (List<StockCost>) businessObjectService.findMatching(
                StockCost.class, fieldValues);

        return stockPrices;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.ext.mm.service.StockService#retrieveStock(java.lang.String)
     */
    public Stock retrieveStock(String stockId) {
        return businessObjectService.findBySinglePrimaryKey(Stock.class, stockId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.ext.mm.service.StockService#getBusinessObjectService()
     */
    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.ext.mm.service.StockService#setBusinessObjectService(org.kuali.rice.kns.service.BusinessObjectService)
     */
    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public List<StockCount> updateStockBalancesForApprovedItems(List<StockCount> wdocStockCounts) {

        List<StockCount> scounts = new ArrayList<StockCount>(0);

        for (StockCount scount : wdocStockCounts) {
            StockBalance sb = scount.getBin().getStockBalance();
            sb.setQtyOnHand(scount.getStockCountItemQty().intValue());
            sb.save();
        }

        return scounts;
    }

    /**
     * retrieves the stock price for the catalog item id
     * 
     * @see org.kuali.ext.mm.service.StockService#getStockPriceForCatalogItem(java.lang.String)
     */
    public MMDecimal getStockPriceForCatalogItem(String catalogItemId) {

        MMDecimal result = MMDecimal.ZERO;
        CatalogItem citem = StoresPersistableBusinessObject.getObjectByPrimaryKey(
                CatalogItem.class, catalogItemId);
        if (citem.getStock() != null) {
            result = citem.getStock().getStockPrice();
            return result;
        }
        if (!StringUtils.isEmpty(citem.getStockId())) {

            Stock stock = StoresPersistableBusinessObject.getObjectByPrimaryKey(Stock.class, citem
                    .getStockId());

            if (stock != null)
                result = stock.getStockPrice();
        }
        return result;

    }

    /**
     * retrieves the catalog item for the passed distributorNumber, manufacturerNumber, itemNumber
     * 
     * @see org.kuali.ext.mm.service.StockService#getCatalogItem(java.lang.String, java.lang.String, java.lang.String)
     */
    public CatalogItem getCatalogItem(String distributorNumber, String manufacturerNumber,
            String itemNumber) {

        if (StringUtils.isEmpty(distributorNumber) || StringUtils.isEmpty(manufacturerNumber)
                || StringUtils.isEmpty(itemNumber))
            return null;

        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.OrderDocument.STOCK_STOCK_DISTRIBUTOR_NUMBER, itemNumber);
        List<CatalogItem> catalogItems = (List<CatalogItem>) businessObjectService.findMatching(
                CatalogItem.class, fieldValues);

        for (CatalogItem item : catalogItems) {
            if (item != null && manufacturerNumber.equalsIgnoreCase(item.getManufacturerNbr())
                    && distributorNumber.equalsIgnoreCase(item.getDistributorNbr()))
                return item;
        }
        return null;
    }

   /**
     * @see org.kuali.ext.mm.service.StockService#getStockQuantityOnHand(java.lang.String, java.lang.String)
     */
    public Integer getStockQuantityOnHand(String stockId, String warehouseCode) {
        Integer quantityOnHand = 0;
        String sum = "SUM(" + MMConstants.StockBalance.QTY_ON_HAND + ")";
        String[] attributes = {MMConstants.StockBalance.STOCK_ID, sum};
        String[] groupBy = {MMConstants.StockBalance.STOCK_ID};
        
        MMBusinessObjectDao mmBoDao = SpringContext.getBean(MMBusinessObjectDao.class);
        QueryElement queryRoot = new QueryElement();
        queryRoot.getAndList().add(new QueryElement(MMConstants.StockBalance.STOCK_ID, stockId));
        queryRoot.getAndList().add(new QueryElement(
                MMConstants.StockBalance.BIN + "." + MMConstants.Bin.ZONE + "." + MMConstants.Zone.WAREHOUSE_CD, warehouseCode));
        
        Collection<Object[]> results = mmBoDao.getReport(StockBalance.class, queryRoot, attributes, groupBy, new String[0], false, -1);
        if(results.iterator().hasNext()) {
            Object[] itemQtyInfo = results.iterator().next();
            String strSum = String.valueOf(itemQtyInfo[1]); 
            quantityOnHand = (itemQtyInfo.length > 1 && StringUtils.isNumeric(strSum)) ?  Integer.parseInt(strSum): 0;
        }
        else
            quantityOnHand = 0;
        
        return quantityOnHand;
    }
    
    /**
     * @see org.kuali.ext.mm.service.StockService#getCommittedCatalogItemQuantity(java.lang.String)
     */
    public Integer getCommittedCatalogItemQuantity(String catalogItemId) {
        String catalogItemIdField = MMConstants.PickListLine.ORDER_DETAIL + "." + MMConstants.OrderDetail.CATALOG_ITEM_ID;        
        return getCommittedQuantityByField(catalogItemId, catalogItemIdField);
    }
    
    /**
     * @see org.kuali.ext.mm.service.StockService#getCommittedBinQuantity(java.lang.Integer)
     */
    public Integer getCommittedBinQuantity(Integer binId) {
        return getCommittedQuantityByField(binId, MMConstants.PickListLine.BIN_ID);
    }
    
    /**
     * @see org.kuali.ext.mm.service.StockService#getCommittedStockQuantity(java.lang.String)
     */
    public Integer getCommittedStockQuantity(String stockId) {
        return getCommittedQuantityByField(stockId, MMConstants.PickListLine.STOCK_ID);
    }
    
    /**
     * Computes the committed quantity over a particular field related to pick list lines
     * e.g. catalogItemId, binId, stockId
     * 
     * @param fieldValue
     * @param fieldName - name of a field over which to sum the stock quantities of pick list lines 
     * @return The committed item quantity for the given item identified by the given field. 
     */
    private Integer getCommittedQuantityByField(Object fieldValue, String fieldName) {
        Integer committedQuantity = 0;
        
        String sum = "SUM(" + MMConstants.PickListLine.STOCK_QTY + ")";
        
        List<String> pickStatusCodes = new ArrayList<String>();
        pickStatusCodes.add(MMConstants.PickStatusCode.PICK_STATUS_INIT);
        pickStatusCodes.add(MMConstants.PickStatusCode.PICK_STATUS_ASND);
        pickStatusCodes.add(MMConstants.PickStatusCode.PICK_STATUS_PRTD);
        
        String[] attributes = {fieldName, sum};
        String[] groupBy = {fieldName};
        
        MMBusinessObjectDao mmBoDao = SpringContext.getBean(MMBusinessObjectDao.class);
        QueryElement queryRoot = new QueryElement();
        queryRoot.getAndList().add(new QueryElement(fieldName, fieldValue));
        queryRoot.getAndList().add(new QueryElement(MMConstants.PickListLine.PICK_STATUS_CODE_CD, pickStatusCodes));
        Collection<Object[]> results = mmBoDao.getReport(PickListLine.class, queryRoot, attributes, groupBy, new String[0], false, -1);
        if(results.iterator().hasNext()) {
            Object[] qtyInfo = results.iterator().next();
            String strSum = String.valueOf(qtyInfo[1]);            
            committedQuantity = (qtyInfo.length > 1 && StringUtils.isNumeric(strSum)) ?  Integer.parseInt(strSum): 0;
        }
        
        return committedQuantity;
    }


    /**
     * @see org.kuali.ext.mm.service.StockService#isAgreementNumberValid(java.lang.String)
     */
    public boolean isAgreementNumberValid(String agreementNumber) {
        Agreement agreement = KRADServiceLocator.getBusinessObjectService()
            .findBySinglePrimaryKey(Agreement.class, agreementNumber);
        
        Date today = CoreApiServiceLocator.getDateTimeService().getCurrentSqlDate();
        
        return agreement != null 
            && agreement.isActive() ;
//            && today.compareTo(agreement.getAgreementBeginDt()) >= 1 
//            && today.compareTo(agreement.getAgreementEndDt()) <= 1;
    }


    /**
     * @see org.kuali.ext.mm.service.StockService#getCurrentStockPriceCode()
     */
    public String getCurrentStockPriceCode() {
        String currentStockPriceCode = null;
        if (SpringContext.getBean(ParameterService.class).parameterExists(
                MMConstants.Parameters.Document.class, MMConstants.Parameters.STOCK_PRICE_CODE)) {
            currentStockPriceCode = SpringContext.getBean(ParameterService.class).getParameterValueAsString(
                    MMConstants.Parameters.Document.class, MMConstants.Parameters.STOCK_PRICE_CODE);
        }
        currentStockPriceCode = StringUtils.isEmpty(currentStockPriceCode) ? MMConstants.CostCode.STANDARD_PRICE
                : currentStockPriceCode;
        return currentStockPriceCode;
    }


    /**
     * @see org.kuali.ext.mm.service.StockService#getStockCostForStock(java.lang.String)
     */
    public StockCost getStockCostForStock(String stockId) {
        return getStockCostForStock(stockId, getCurrentStockPriceCode());
    }
    
    private StockCost getStockCostForStock(String stockId, String costCode) {
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.StockCost.STOCK_ID, stockId);
        fieldValues.put(MMConstants.StockCost.COST_CODE, costCode);
        fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
        Collection results = KRADServiceLocator.getBusinessObjectService().findMatching(StockCost.class, fieldValues);
        
        StockCost stockCost = null;
        Iterator<StockCost> it = results.iterator();
        
        if(it.hasNext())
           stockCost = (TransactionalServiceUtils.retrieveFirstAndExhaustIterator(it));
        
        return stockCost;
    }
    
    /**
     * @see org.kuali.ext.mm.service.StockService#initializeStockCosts(org.kuali.ext.mm.businessobject.Stock, org.kuali.ext.mm.util.MMDecimal)
     */
    public List<StockCost> initializeStockCosts(Stock stock, MMDecimal cost) {
        List<StockCost> stockCosts = new ArrayList<StockCost>();
        
        Collection<CostCode> costCodes = getBusinessObjectService().findAll(CostCode.class);
        for(CostCode code : costCodes) {
            StockCost newCost = new StockCost();
            newCost.setCostCode(code);
            newCost.setCostCd(code.getCostCd());
            newCost.setStockCst(cost);
            newCost.setStock(stock);
            newCost.setStockId(stock.getStockId());
            newCost.setActive(true);
            stockCosts.add(newCost);
        }
        
        getBusinessObjectService().save(stockCosts);
        
        return stockCosts;
    }


    /**
     * @see org.kuali.ext.mm.service.StockService#updateStockCost(java.lang.String, org.kuali.ext.mm.util.MMDecimal, java.lang.String)
     */
    public StockCost updateStockCost(String stockId, MMDecimal newStockCost, String costCode) {
        StockCost stockCost = getStockCostForStock(stockId, costCode);
        stockCost.setStockCst(newStockCost);
//        getBusinessObjectService().save(stockCost);
        
        if(getCurrentStockPriceCode().equalsIgnoreCase(costCode)) {
            Collection<CatalogItem> catalogItems = MMServiceLocator.getCatalogItemService().getCatalogItemsForStock(stockId);
            for(CatalogItem item : catalogItems) {
                item.setCatalogPrc(newStockCost);
                getBusinessObjectService().save(item);
            }   
        }
        return stockCost;
    }


    /**
     * @see org.kuali.ext.mm.service.StockService#isTrackableWithSerialNumber(org.kuali.ext.mm.businessobject.Stock)
     */
    public boolean isTrackableWithSerialNumber(Stock stock) {
        ParameterService parameterService = SpringContext.getBean(ParameterService.class);
        List<String> trackableStockTypeCodes = new ArrayList<String>(parameterService.getParameterValuesAsString(MMConstants.MM_NAMESPACE, MMConstants.Parameters.DOCUMENT,  MMConstants.Parameters.STOCK_TYPES_WITH_SERIAL_NUM));

        if(trackableStockTypeCodes.contains(stock.getStockTypeCode()))
            return true;
        
        return false;
    }
}