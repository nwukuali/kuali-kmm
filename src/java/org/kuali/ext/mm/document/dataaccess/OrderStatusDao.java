/**
 *
 */
package org.kuali.ext.mm.document.dataaccess;

import java.util.List;

import org.kuali.ext.mm.businessobject.OrderDetailPickStatusDTO;


/**
 * @author harsha07
 */
public interface OrderStatusDao {

    /**
     * Updates order document with provided status code
     * 
     * @param orderDocNumber
     * @param orderStatusCd
     * @return
     */
    public int updateOrderStatus(String orderDocNumber, String orderStatusCd);

    /**
     * Finds out the order pick status for the order lines associate with this pick ticket
     * 
     * @param pickTicketNumber Pick Ticket Number
     * @return List with status of order details
     */
    public List<OrderDetailPickStatusDTO> getOrderDetailsPickStatus(String pickTicketNumber);

    /**
     * Updates order details to status COMPLETE
     * 
     * @param orderDetailIds List of order details
     * @return update count
     */
    public int updateOrderDetailsComplete(List<Integer> orderDetailIds);

    /**
     * Returns true if all items within order is complete
     * 
     * @param orderDocNumber
     * @return flag
     */
    public boolean isOrderComplete(String orderDocNumber);

    /**
     * Updates order doc status code to complete
     * 
     * @param orderDocNumber Specific document number
     * @return Update count
     */
    public int updateOrderComplete(String orderDocNumber);

    /**
     * Order lines received status to complete
     * 
     * @param orderDetailIds
     * @return
     */
    public int updateOrderDetailsReceiveComplete(List<Integer> orderDetailIds);

    /**
     * Returns true if all lines within are marked received complete
     * 
     * @param orderDocNumber
     * @return
     */
    public boolean isOrderReceiveComplete(String orderDocNumber);

    /**
     * Update order doc status code to received complete
     * 
     * @param orderDocNumber
     * @return
     */
    public int updateOrderReceived(String orderDocNumber);
}