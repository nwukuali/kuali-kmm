package org.kuali.ext.mm.service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.businessobject.Stock;

public interface RentalService {

    /**
     * @param stock
     * @param serialNumber
     * @return A Rental item identified by the the serialNumber for the stock that 
     * has not yet been issued.  Or null if no match is found.
     */
    public Rental getAvailableRentalItem(Stock stock, String serialNumber);
    
    /**
     * @param stock
     * @param serialNumber
     * @return A collection of Rental items identified by the the serialNumbers for the stock
     */
    public Collection<Rental> getAvailableRentalItems(Stock stock, List<String> serialNumber);
    
    /**
     * @param rentalTypeCode
     * @param serialNumber
     * @return The last returned Rental item identified by the serialNumber and rentalTypeCode 
     */
    public Rental getLastReturnedRentalItem(String rentalTypeCode, String serialNumber);
    
    /**
     * @param rentalTypeCode
     * @param serialNumber
     * @param includeReturned
     * @return The current Rental item identified by the serialNumber and rentalTypeCode,
     * whether available, issued, or returned (if includeReturned is true). 
     */
    public Rental getCurrentRentalItem(String rentalTypeCode, String serialNumber, boolean includeReturned);
    
    /**
     * @param stock
     * @param serialNumber
     * @param includeReturned
     * @return The current Rental item identified by the serialNumber and stockId,
     * whether available, issued, or returned (if includeReturned is true). 
     */
    public Rental getCurrentRentalItem(Stock stock, String serialNumber, boolean includeReturned);
    
    /**
     * @param stock
     * @param serialNumbers
     * @param includeReturned
     * @return A collection of the current Rental items identified by the serialNumber for the stock
     * in either available or issued status.
     */
    public Collection<Rental> getCurrentNonReturnedRentalItems(Stock stock, List<String> serialNumbers);
    
    /**
     * @param rentalTypeCode
     * @param serialNumber
     * @param checkinDetalId
     * @return
     */
    public boolean isSerialNumberValidForCheckin(String rentalTypeCode, String serialNumber, Integer checkinDetalId);
    
	/**
	 * Persists the rental to the database
	 *
	 * @param rental
	 */
	public void saveRental(Rental rental);

	/**
	 * Persists a list of rental items to the database
	 *
	 * @param rentals
	 */
	public void saveRentalList(List<Rental> rentals);

	/**
	 * Issues the rental to the given pick list line
	 *
	 * @param rental
	 * @param line - pick list line
	 * @return The rental which has just been issued
	 */
	public Rental issueRentalItem(Rental rental, PickListLine line);
	
	/**
	 * Cleans a list of rentals to remove any non-persistable rentals.
	 * 
	 * @param rentals
	 * @return List of rentals with data necessary for persistence 
	 */
	public List<Rental> cleanupRentalList(List<Rental> rentals);
	
	/**
	 * Retrieves a list of Rental items that were verified and assigned to
	 * an order detail.
	 * 
	 * @param orderDetail
	 * @return a list of Rental items for the given order detail
	 */
	public List<Rental> getRentalsForOrderDetail(OrderDetail orderDetail);
	
	/**
	 * @param serialNumber
	 * @param rentalTypeCode
	 * @param returnDate
	 * @return the rental record for the serialNumber, rentalTypeCode, and returnDate 
	 */
	public Rental getHistoricRental(String serialNumber, String rentalTypeCode, Timestamp returnDate);

}
