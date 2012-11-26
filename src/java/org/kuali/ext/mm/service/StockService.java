package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.util.List;

public interface StockService {

	/**
	 * Retrieves the current version of StockBalance and adjusts the 
	 * quantityOnHand by quantity.  Note: Due to ojb caching stockBalance
	 * parameter is also updated with current information, but the quantity
	 * is not added to it. 
	 * 
	 * @param stockBalance
	 * @param quantity
	 * @param referenceDocNbr
	 * @return The stock balance object resulting from the adjustment
	 */
	public StockBalance adjustStockQuantityOnHand(StockBalance stockBalance, Integer quantity);
	
	/**
     * Initializes a list of stock cost objects for stock based on the cost codes found in the system.
     * 
     * @param stock
     * @param cost
     * @return a list of initialized stock cost objects
     */
    public List<StockCost> initializeStockCosts(Stock stock, MMDecimal cost);
	
	/**
	 * Updates the StockCost object for the stock with given stockId and costCode to
	 * the newStockCost.  Also updates the CatalogItem.catalogPrice to the newStockCost.   
	 * 
	 * @param stockId
	 * @param newStockCost
	 * @param costCode
	 * @return StockCost object reflecting the new stock cost.
	 */
	public StockCost updateStockCost(String stockId, MMDecimal newStockCost, String costCode);

	/**
	 * Inserts a new record into the stock history table for the given transaction
	 *
	 * @param beforeStockBalance
	 * @param afterStockBalance
	 * @param referenceDocNbr
	 */
	public void postSaleToStockHistory(StockBalance beforeStockBalance, StockBalance afterStockBalance, String referenceDocNbr);

//	/**
//	 * @param stockId
//	 * @param binId
//	 * @return A populated/refreshed stock balance object matching the stockId and binId
//	 */
//	public StockBalance retrieveStockBalance(String stockId, Integer binId);

	/**
	 * @param binId
	 * @return A populated/refreshed stock balance object matching  binId
	 */
	public StockBalance retrieveStockBalance(Integer binId);

	/**
	 * Uses the stockDistributorNumber to retrieve a stock item
	 *
	 * @param stockDistributorNumber
	 * @return A Stock object
	 */
	public Stock getStockByDistributorNumber(String stockDistributorNumber);

	/**
	 * Uses the primary key stockId to retrieve the stock item
	 *
	 * @param stockId
	 * @return A Stock object
	 */
	public Stock retrieveStock(String stockId);

	/**
	 * calculates the two types of stock price codes for a particular checkin line
	 *
	 * @param unitCost
	 * @param cdetail
	 * @param Quantity
	 * @return
	 */
	public List<StockCost> calculateStockPrices(MMDecimal unitCost, Stock stock, int Quantity, int stockQty, List<StockCost> stockPrices);


	/**
	 * retrieves the stock prices of a stock if any
	 * @param stockId
	 * @return
	 */
	public List<StockCost> getStockPricesForStock(String stockId);

	/**
	 * retrives a bin for the passed Stock ID and quantity
	 * @param stockId
	 * @return
	 */
	public Bin getEmptyBinForStockId(CheckinDocument cdoc, String stockId, int quantity);

	/**
	 * @return the BusinessObjectService
	 */
	public BusinessObjectService getBusinessObjectService();

	/**
	 * Sets the BusinessObjectService
	 *
	 * @param businessObjectService
	 */
	public void setBusinessObjectService(BusinessObjectService businessObjectService);

//	 /**
//     * returns all the bins holding the passed stock
//     * 
//     * @param stockId
//     * @return list of bins containing stock
//     */
//	public List<Bin> getAvailableBinsForStockId(String stockId);
	
	/**
     * Returns all of the bins in a warehouse holding the passed stock.
     * If warehouseCode is null, all of the bins for that stock are returned. 
     * 
     * @param stockId
     * @param warehouseCode
     * @param sortBy
     * @param sortAscending
     * @return list of bins containing stock
     */
    public List<Bin> getAvailableBinsForStockId(String stockId, String warehouseCode, String sortBy, boolean sortAscending);

	/**
	 * updates stock balance for the passed stock counts
	 * @param wdocStockCounts
	 * @return
	 */
	public List<StockCount> updateStockBalancesForApprovedItems(List<StockCount> wdocStockCounts);

	/**
	 * returns the catalog item object for the passed distributorNumber, manufacturerNumber, itemNumber
	 * @param distributorNumber
	 * @param manufacturerNumber
	 * @param itemNumber
	 * @return
	 */
	public CatalogItem getCatalogItem(String distributorNumber, String manufacturerNumber, String itemNumber);

	/**
	 * @param stockId
	 * @return A populated StockCost objected containing the current StockCost of the Stock item with stockId
	 */
	public StockCost getStockCostForStock(String stockId);
	
	/**
	 * @return the current stock price code found in the system parameter table or STANDARD_PRICE if its not found.
	 */
	public String getCurrentStockPriceCode();
	
	/**
	 * returns the price of the passed catalog item id
	 * @param catalogItemId
	 * @return
	 */
	public MMDecimal getStockPriceForCatalogItem(String catalogItemId);
	
	/**
	 * Returns quantity on hand for the stock limited to the warehouse.
	 * If no warehouse is provided, the total stock balance is returned.
	 * 
	 * @param stockId
	 * @param warehouseCode
	 * @return The quantity on hand for the stock
	 */
	public Integer getStockQuantityOnHand(String stockId, String warehouseCode);
	
	/**
	 * Returns the total quantity committed for a catalog item.  Determined by the
	 * sum of the quantity for INIT, ASND, and PRTD pick list lines.
	 * 
	 * @param catalogItemId
	 * @return The quantity committed for the given catalog item
	 */
	public Integer getCommittedCatalogItemQuantity(String catalogItemId);
	
	/**
	 * Returns the total quantity committed for a bin.  Determined by the
     * sum of the quantity for INIT, ASND, and PRTD pick list lines.
     * 
	 * @param binId
	 * @return The quantity committed for the given bin
	 */
	public Integer getCommittedBinQuantity(Integer binId);
	
	/**
	 * Returns the total quantity committed for a stock item.  Determined by the
     * sum of the quantity for INIT, ASND, and PRTD pick list lines.
     * 
	 * @param stockId
	 * @return The quantity committed for the given stock item
	 */
	public Integer getCommittedStockQuantity(String stockId);
	
	/**
	 * Agreement is valid if it exists and the current date is within the
	 * begin and end dates for the contract.
	 * 
	 * @param agreementNumber
	 * @return true if agreementNumber references an existing valid agreement
	 */
	public boolean isAgreementNumberValid(String agreementNumber);
	
	/**
	 * Stock that are trackable with serial numbers are identified by stock types
	 * found in the STOCK_TYPES_WITH_SERIAL_NUM system parameter.
	 * 
	 * @param stock - an instance of a stock item
	 * @return true if stock object is trackable with a serial number.
	 */
	public boolean isTrackableWithSerialNumber(Stock stock);
}
