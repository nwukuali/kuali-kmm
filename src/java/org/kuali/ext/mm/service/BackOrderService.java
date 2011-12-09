package org.kuali.ext.mm.service;

import java.util.Collection;

import org.kuali.ext.mm.businessobject.BackOrder;
import org.kuali.ext.mm.businessobject.PickListLine;

public interface BackOrderService {

	/**
	 * @param line - PickListLine
	 * @return
	 */
	public BackOrder createBackOrder(PickListLine line);

	/**
	 * Persist backorder to database
	 *
	 * @param bo - backorder object
	 */
	public void save(BackOrder bo);

	/**
	 * Relieve a backorder in the system and create new pick list lines,
	 * unless strictQuantity is true and there is not enough stock
	 * to accommodate the entire backorder. 
	 *
	 * @param backOrder - backorder object
	 * @param strictQuantity
	 */
	public void relieveBackOrder(BackOrder backOrder, boolean strictQuantity);
	
	/**
     * Cancel a backorder to prevent lines from being created on checkin.
     *
     * @param backOrder - backorder object
     */
    public void cancelBackOrder(BackOrder backOrder);
    
    /**
     * Reduce the backorder quantity.
     *
     * @param backOrder - backorder object
     */
    public void reduceBackOrder(BackOrder backOrder);

	/**
	 * @param line - PickListLine
	 * @return A String representation of the expected back order relief date.  Or '******' if
	 * that date cannot be determined.
	 */
	public String getBackOrderReliefDate(PickListLine line);
	
	
	/**
	 * @param orderDetailId
	 * @return a collection of backorders for the given orderDetailId
	 */
	public Collection<BackOrder> getBackOrdersForOrderDetail(Integer orderDetailId);
	
	/**
	 * Gets unfilled BackOrders for given Stock
	 * 
	 * @param stockId
	 * @return a collection of unfilled BackOrders for the given stockId  
	 */
	public Collection<BackOrder> getUnfilledBackOrdersForStock(String stockId);

    /**
     * Gets unfilled and uncanceled BackOrders in the system
     * 
     * @return a collection of unfilled and uncanceled BackOrders  
     */
    public Collection<BackOrder> getAllActiveBackOrders();
    
    	
}
