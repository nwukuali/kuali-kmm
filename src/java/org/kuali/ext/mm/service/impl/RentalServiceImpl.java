package org.kuali.ext.mm.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.MMBusinessObjectDao;
import org.kuali.ext.mm.service.RentalService;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.TransactionalServiceUtils;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;


@Transactional
public class RentalServiceImpl implements RentalService {

	private BusinessObjectService businessObjectService;

	/** 
	 * @see org.kuali.ext.mm.service.RentalService#issueRentalItem(org.kuali.ext.mm.businessobject.Rental, org.kuali.ext.mm.businessobject.PickListLine)
	 */
	public Rental issueRentalItem(Rental rental, PickListLine line) {
		Rental availableRental = getAvailableRentalItem(rental.getStock(), rental.getRentalSerialNumber());

		if(availableRental != null) {
		    availableRental.setRentalStatusCode(MMConstants.Rental.RENTAL_STATUS_ISSUED);
			availableRental.setIssueDate(CoreApiServiceLocator.getDateTimeService().getCurrentTimestamp());
			List<Accounts> rentalAccounts = new ArrayList<Accounts>();
			for(Accounts account : line.getOrderDetail().getAccounts()) {
			    Double total = line.getStock().getRentalObject().getDailyDemurragePrice().doubleValue();
			    Accounts copyAccount = new Accounts(account);
			    copyAccount.setAccountFixedAmt(new KualiDecimal(total
	                    * (copyAccount.getAccountPct().doubleValue() * new Double(.01))));
			    rentalAccounts.add(copyAccount);
			}
			availableRental.setAccountingLines(rentalAccounts);
		}
		return availableRental;
	}

	/**
	 * @see org.kuali.ext.mm.service.RentalService#saveRental(org.kuali.ext.mm.businessobject.Rental)
	 */
	public void saveRental(Rental rental) {
		businessObjectService.save(rental);
	}

	/** 
	 * @see org.kuali.ext.mm.service.RentalService#saveRentalList(java.util.List)
	 */
	public void saveRentalList(List<Rental> rentals) {
		List<Rental> populatedRentals = new ArrayList<Rental>();
		for(Rental rental : rentals) {
			if(rental.getRentalSerialNumber() != null && rental.getRentalSerialNumber().length() > 0)
				populatedRentals.add(rental);
		}

		businessObjectService.save(populatedRentals);

	}

    /**
     * @see org.kuali.ext.mm.service.RentalService#cleanupRentalList(java.util.List)
     */
    public List<Rental> cleanupRentalList(List<Rental> rentals) {
        List<Rental> uniqueRentals = new ArrayList<Rental>();
        for (Rental rental : rentals) {
            if (StringUtils.isBlank(rental.getRentalSerialNumber()))
                continue;
            uniqueRentals.add(rental);
        }
        return uniqueRentals;
    }

    
    /**
     * @see org.kuali.ext.mm.service.RentalService#getAvailableRentalItem(org.kuali.ext.mm.businessobject.Stock, java.lang.String)
     */
    public Rental getAvailableRentalItem(Stock stock, String serialNumber) {
       List<String> serialNumbers = new ArrayList<String>();
       serialNumbers.add(serialNumber);
       Collection results = getAvailableRentalItems(stock, serialNumbers);
       return (Rental)(TransactionalServiceUtils
                .retrieveFirstAndExhaustIterator(results.iterator()));
    }


    /**
     * @see org.kuali.ext.mm.service.RentalService#getAvailableRentalItems(org.kuali.ext.mm.businessobject.Stock, java.util.List)
     */
    public Collection<Rental> getAvailableRentalItems(Stock stock, List<String> serialNumbers) {
        if(ObjectUtils.isNull(stock) || serialNumbers == null || serialNumbers.isEmpty())
            return new ArrayList<Rental>();
        
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.Rental.RETURN_DATE, null);
        fieldValues.put(MMConstants.Rental.RENTAL_STATUS_CODE, MMConstants.Rental.RENTAL_STATUS_AVAILABLE);
        fieldValues.put(MMConstants.Rental.STOCK_ID, stock.getStockId());
        fieldValues.put(MMConstants.Rental.RENTAL_SERIAL_NUMBER, serialNumbers);

        Collection results = SpringContext.getBean(MMBusinessObjectDao.class).findMatching(Rental.class, fieldValues);

        return results;
    }

    /**
     * @see org.kuali.ext.mm.service.RentalService#getCurrentRentalItem(java.lang.String, java.lang.String, boolean)
     */
    public Rental getCurrentRentalItem(String rentalTypeCode, String serialNumber, boolean includeReturned) {
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.Rental.RENTAL_TYPE_CODE, rentalTypeCode);
        
        return getCurrentRentalItem(fieldValues, serialNumber, includeReturned);
    }
    
    /**
     * @see org.kuali.ext.mm.service.RentalService#getCurrentRentalItem(org.kuali.ext.mm.businessobject.Stock, java.lang.String, boolean)
     */
    public Rental getCurrentRentalItem(Stock stock, String serialNumber, boolean includeReturned) {
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.Rental.STOCK_ID, stock.getStockId());        

        return getCurrentRentalItem(fieldValues, serialNumber, includeReturned);
    }
    
    /**
     * Convenience method for allowing a search by rentalTypeCode or stockId.  
     * 
     * @param fieldValues
     * @param serialNumber
     * @param includeReturned
     * @return Current rental, Issued, Available, or Returned (if specified), for the given criteria 
     */
    private Rental getCurrentRentalItem(Map<String, Object> fieldValues, String serialNumber, boolean includeReturned) {
        fieldValues.put(MMConstants.Rental.RETURN_DATE, null);        
        fieldValues.put(MMConstants.Rental.RENTAL_SERIAL_NUMBER, serialNumber);
        
        Collection results = SpringContext.getBean(MMBusinessObjectDao.class).findMatching(Rental.class, fieldValues);
        if(results.isEmpty() && includeReturned) {
            String orderBy = MMConstants.Rental.RETURN_DATE;
            fieldValues.remove(MMConstants.Rental.RETURN_DATE);
            results = SpringContext.getBean(MMBusinessObjectDao.class).findMatchingOrderBy(Rental.class, fieldValues, orderBy, false);
        }

        return (Rental)(TransactionalServiceUtils
                .retrieveFirstAndExhaustIterator(results.iterator()));
    }
    
    
    public Collection<Rental> getCurrentNonReturnedRentalItems(Stock stock, List<String> serialNumbers) {
        if(serialNumbers == null || serialNumbers.isEmpty() || ObjectUtils.isNull(stock))
            return new ArrayList<Rental>();
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.Rental.RETURN_DATE, null);        
        fieldValues.put(MMConstants.Rental.STOCK_ID, stock.getStockId());
        fieldValues.put(MMConstants.Rental.RENTAL_SERIAL_NUMBER, serialNumbers);
        
        Collection results = SpringContext.getBean(MMBusinessObjectDao.class).findMatching(Rental.class, fieldValues);

        return results;
    }

    /**
     * @see org.kuali.ext.mm.service.RentalService#getLastReturnedRentalItem(java.lang.String, java.lang.String)
     */
    public Rental getLastReturnedRentalItem(String rentalTypeCode, String serialNumber) {
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.Rental.RENTAL_TYPE_CODE, rentalTypeCode);
        fieldValues.put(MMConstants.Rental.RENTAL_SERIAL_NUMBER, serialNumber);
        
        String orderBy = MMConstants.Rental.RETURN_DATE;
        fieldValues.remove(MMConstants.Rental.RETURN_DATE);
        Collection results = SpringContext.getBean(MMBusinessObjectDao.class).findMatchingOrderBy(Rental.class, fieldValues, orderBy, false);

        return (Rental)(TransactionalServiceUtils
                .retrieveFirstAndExhaustIterator(results.iterator()));
    }

    /**
     * @see org.kuali.ext.mm.service.RentalService#isSerialNumberValidForCheckin(java.lang.String, java.lang.String, java.lang.Integer)
     */
    public boolean isSerialNumberValidForCheckin(String rentalTypeCode, String serialNumber,
            Integer checkinDetailId) {
        
        Rental currentRental = getCurrentRentalItem(rentalTypeCode, serialNumber, true);
               
        if(currentRental == null)
            return true;
        
        if(checkinDetailId == null) {
            if(MMConstants.Rental.RENTAL_STATUS_RETURNED.equals(currentRental.getRentalStatusCode()))
                return true;
            
            return false;
        }        
        else if(checkinDetailId.equals(currentRental.getCheckinDetailId())) {
            return true;
        }
        
        return false;
    }
    
    public List<Rental> getRentalsForOrderDetail(OrderDetail orderDetail) {
        List<Rental> rentalResults = new ArrayList<Rental>();
        
        if(ObjectUtils.isNotNull(orderDetail)) {
            Map<String, Object> fieldValues = new HashMap<String, Object>();
            fieldValues.put(MMConstants.PickListLine.ORDER_DETAIL_ID, orderDetail.getOrderDetailId());        
            
            Collection results = SpringContext.getBean(MMBusinessObjectDao.class).findMatching(PickListLine.class, fieldValues);
            
            Iterator<PickListLine> it = results.iterator();
            while(it.hasNext()) {
                PickListLine pline = it.next();
                if(MMUtil.isCollectionEmpty(pline.getRentals()))
                    pline.refreshReferenceObject(MMConstants.PickListLine.RENTALS);
                rentalResults.addAll(pline.getRentals());
            }
        }
        return rentalResults;
    }
    
    public Rental getHistoricRental(String serialNumber, String rentalTypeCode, Timestamp returnDate) {
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.Rental.RETURN_DATE, returnDate);        
        fieldValues.put(MMConstants.Rental.RENTAL_TYPE_CODE, rentalTypeCode);
        fieldValues.put(MMConstants.Rental.RENTAL_SERIAL_NUMBER, serialNumber);
        
        Collection results = SpringContext.getBean(MMBusinessObjectDao.class).findMatching(Rental.class, fieldValues);

        return (Rental)(TransactionalServiceUtils
                .retrieveFirstAndExhaustIterator(results.iterator()));
    }
    

    /** 
     * @see org.kuali.ext.mm.service.RentalService#getBusinessObjectService()
     */
    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    /** 
     * @see org.kuali.ext.mm.service.RentalService#setBusinessObjectService(org.kuali.rice.kns.service.BusinessObjectService)
     */
    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

}
