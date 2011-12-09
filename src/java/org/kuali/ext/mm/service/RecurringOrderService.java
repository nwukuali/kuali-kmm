/**
 * 
 */
package org.kuali.ext.mm.service;

import java.sql.Date;
import java.util.Map;

import org.kuali.ext.mm.businessobject.RecurringOrder;

/**
 * @author schneppd
 *
 */
public interface RecurringOrderService {

	/**
	 * Runs the Recurring Order process for all orders needing to recur for the current day.
	 */
	public void process();
	
	/**
	 * Adds the interval to the lastRecurringDate to compute the next date. 
	 * 
	 * @param recurringOrder
	 * @return the next recurring date for the given recurring order object
	 */
	public Date computeNextRecurringDate(RecurringOrder recurringOrder);
	
	/**
	 * @return reference to the static frequencyMap
	 */
	public Map<Integer, String> getOrderFrequencyMap();

}
