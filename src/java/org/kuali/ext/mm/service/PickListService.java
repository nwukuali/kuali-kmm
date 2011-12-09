package org.kuali.ext.mm.service;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.businessobject.BackOrder;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.PickTicket;
import org.kuali.ext.mm.document.PickListDocument;

/**
 * @author schneppd
 *
 */
public interface PickListService {

	/**
	 * Entry point from doRouteStatusChange event, which handles the creation of pick ticket objects
	 * and the assignment of pickListLines to them.
	 *
	 * @param pickListDocument
	 */
	public void processNewPickList(PickListDocument pickListDocument);

	/**
	 * Releases all of the pickListLine items held by pickListDocument
	 *
	 *  @param pickListDocument
	 */
	public void cancelPickList(PickListDocument pickListDocument);

	/**
	 * Sets the status of a pickTicket and its lines to PRTD indicating that it has been printed.
	 *
	 * @param pickTicket
	 * @return The updated pick ticket object
	 */
	public PickTicket preparePickTicketForPrint(PickTicket pickTicket);

	/**
	 * Renders the pickTicket data into a pdf.
	 *
	 * @param pickTicket
	 * @return output stream buffer containing the pdf file
	 */
	public ByteArrayOutputStream generatePdfTicket(PickTicket pickTicket);

	/**
	 * Renders a collection of pickTicket data into a single pdf.
	 *
	 * @param pickTickets
	 * @return output stream buffer containing the pdf file
	 */
	public ByteArrayOutputStream generatePdfTicket(Collection<PickTicket> pickTickets);

	/**
	 * @param lines
	 * @return the number of unique orders in a list of pickListLine items
	 */
	public int getUniqueOrderCount(List<PickListLine> lines);


	/**
	 * Sorts and groups pick list line items into a map by order ID
	 *
	 * @param lines - picklist line items
	 * @return A map with a key of Order ID and a value of pick list lines belonging to that Order
	 */
	public Map<Long, List<PickListLine>> getLinesByOrder(List<PickListLine> lines);

	/**
	 * @param ticket
	 * @return date of the oldest item on a pick ticket
	 */
	public Timestamp getOldestDate(PickTicket ticket);

	/**
	 * Checks that ALL of the pickListLine items associated to a pickListDocument are located in
	 * the SAME and CORRECT warehouse.
	 *
	 * @param pickListDocument
	 * @return true if ALL lines are in the correct warehouse, otherwise false
	 */
	public boolean pickListLinesMatchDocumentWarehouse(PickListDocument pickListDocument);

	/**
	 * Checks that ALL of the pickListLine items associated to a pickListDocument have
	 * a pick status of INIT
	 *
	 * @param pickListDocument
	 * @return true if all lines have pick status INIT
	 */
	public boolean isPickListLinesStatusInit(PickListDocument pickListDocument);

//	/**
//	 * @param line - PickListLine
//	 * @return True if the stock type of line matches the system parameter for a serial number trackable object
//	 */
//	public boolean isTrackableWithSerialNumber(PickListLine line);

	/**
	 * Convenience method used to sort a list of pickListLine items into the
	 * order desired for rendering on a pick ticket
	 *
	 * @param lines
	 */
	public void sortLinesByLocation(List<PickListLine> lines);

	/**
	 * Returns as many pick list lines (per Bin) as is needed to fulfill the back order.
	 * Or no lines if strictQuantity is true and the back order cannot be completely fulfilled.
	 * 
	 * @param backOrder
	 * @param strictQuantity
	 * @return a List of PickListLine items created from a Back Order
	 */
	public List<PickListLine> createPickListLinesFromBackOrder(BackOrder backOrder, boolean strictQuantity);

	/**
	 * Returns as many pick list lines (per Bin) as is needed to fulfill an order.
	 *
	 * @param orderDetail
	 * @return List of PickListLine items created from an Order Detail
	 */
	public List<PickListLine> createPickListLinesFromOrderDetail(OrderDetail orderDetail);
	
	/**
	 * Creates a Pick list line for the order detail in Backordered status.
	 * 
	 * @param orderDetail
	 * @return A PickListLine object created for a Truebuyout order detail
	 */
	public PickListLine createPickListLineForTrueBuyout(OrderDetail orderDetail);

	/**
	 * Calls to the businessObjectService to persist or update the pickListLine
	 *
	 * @param line
	 */
	public void savePickListLine(PickListLine line);

	/**
	 * @param pickTicketNumber
	 * @return PickTicket object identified by the pickTicketNumber
	 */
	public PickTicket getPickTicketByNumber(String pickTicketNumber);

	/**
	 * @return collection of PickTicket objects that have not yet been printed (having a pick status of INIT)	 *
	 */
	public Collection<PickTicket> getAllUnprintedTickets();

	/**
	 * @param pickTicket
	 *
	 * @returns true if any of the pick ticket's lines are associated with a trackable serial number (currently Rentals)
	 */
	public boolean ticketHasTrackableStock(PickTicket ticket);
	
//	/**
//	 * @param detail
//	 * @return The current quantity picked for the given item 
//	 */
//	public Integer getTotalPickedQuantity(OrderDetail detail);
//	
//    /**
//     * Already picked + to be picked = total
//     * 
//     * @param detail
//     * @return The total quantity of items that will be picked for the given order detail.
//     */
//    public Integer getTotalQuantityToPick(OrderDetail detail);
    
    /**
     * @param ticket
     * @return a collection of all order details on the pick ticket
     */
    public Collection<OrderDetail> getOrderDetailsFromPickTicket(PickTicket ticket);
        
}
