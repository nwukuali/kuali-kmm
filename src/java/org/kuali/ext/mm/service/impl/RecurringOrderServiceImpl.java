/**
 * 
 */
package org.kuali.ext.mm.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.RecurringOrder;
import org.kuali.ext.mm.cart.service.ShopCartCatalogService;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.MMBusinessObjectDao;
import org.kuali.ext.mm.dataaccess.QueryElement;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.service.CatalogService;
import org.kuali.ext.mm.service.RecurringOrderService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KualiDecimal;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * @author schneppd
 *
 */

@Transactional
public class RecurringOrderServiceImpl implements RecurringOrderService {
    private static final Logger LOG = Logger.getLogger(RecurringOrderServiceImpl.class);
    private PlatformTransactionManager txManager;
	
	protected static final Map<Integer, String> frequencyMap;
	static { 
	    Map<Integer, String> fMap = new HashMap<Integer, String>();
	    fMap.put(MMConstants.RecurringOrder.OPTION_DAILY, MMConstants.RecurringOrder.OPTION_LABEL_DAILY);
	    fMap.put(MMConstants.RecurringOrder.OPTION_WEEKLY, MMConstants.RecurringOrder.OPTION_LABEL_WEEKLY);
	    fMap.put(MMConstants.RecurringOrder.OPTION_BI_WEEKLY, MMConstants.RecurringOrder.OPTION_LABEL_BI_WEEKLY);
	    fMap.put(MMConstants.RecurringOrder.OPTION_MONTHLY, MMConstants.RecurringOrder.OPTION_LABEL_MONTHLY);
	    fMap.put(MMConstants.RecurringOrder.OPTION_QUARTERLY, MMConstants.RecurringOrder.OPTION_LABEL_QUARTERLY);
	    fMap.put(MMConstants.RecurringOrder.OPTION_SEMI_ANNUALLY, MMConstants.RecurringOrder.OPTION_LABEL_SEMI_ANNUALLY);
	    fMap.put(MMConstants.RecurringOrder.OPTION_YEARLY, MMConstants.RecurringOrder.OPTION_LABEL_YEARLY);
	    frequencyMap = Collections.unmodifiableMap(fMap);
	}

//	private ShopCartBusinessObjectDao shopCartBusinessObjectDao;
	
	private MMBusinessObjectDao mmBusinessObjectDao;
	
	private BusinessObjectService businessObjectService;
	
	/**
	 * @see org.kuali.ext.mm.service.RecurringOrderService#process()
	 */
	public void process() {
		Date currentDate = KNSServiceLocator.getDateTimeService().getCurrentSqlDate();
		Collection<RecurringOrder> recurringOrdersDateMatches = getRecurringOrdersByDate(currentDate);
		
		for(RecurringOrder recOrder : recurringOrdersDateMatches) {
		    //start transaction
		    TransactionStatus tx = startTransaction(recOrder.getRecurringOrderId().toString());
		    try {
    		    recOrder.setLastRecurringDt(currentDate);
    		    recOrder.setNextRecurringDt(computeNextRecurringDate(recOrder));
    		    if(recOrder.getNextRecurringDt() == null)
    		        recOrder.setActive(false);
			    OrderDocument newDocument = createNewOrderDocumentFromRecurringOrder(recOrder);
				KNSServiceLocator.getDocumentService().routeDocument(newDocument, "", new ArrayList<Object>());
				//commit here
				txManager.commit(tx);
			}
			catch (Throwable e) {
			    LOG.error("Failed to process recurring order "+recOrder.getRecurringOrderId(), e);
			    //roll back here
			    txManager.rollback(tx);				
			}
		}
	}

	/**
	 * Creates new OrderDocuments from existing recurring order information
	 * 
	 * @param recurringOrdersToProcess
	 * @return a new OrderDocument created from the original OrderDocument placed with the recurrence.
	 */
	private OrderDocument createNewOrderDocumentFromRecurringOrder(RecurringOrder recurringOrder) {
		OrderDocument orderDoc = recurringOrder.getOrdersCreated().get(0);
		OrderDocument newOrder;
		try {
		    newOrder = (OrderDocument)KNSServiceLocator.getDocumentService().getByDocumentHeaderId(orderDoc.getDocumentNumber());
            newOrder.toCopy();
//            newOrder.setOrderId(KNSServiceLocator.getSequenceAccessorService().getNextAvailableSequenceNumber(ShopCartConstants.Sequence.ORDER_ID_SEQ));
            newOrder.setOrderId(null);
            newOrder.getDocumentHeader().setDocumentDescription(String.valueOf(newOrder.getOrderId()));         
            newOrder.setOrderStatusCd(MMConstants.OrderStatus.INITIATED);
            newOrder.setCreationDate(KNSServiceLocator.getDateTimeService().getCurrentTimestamp());
            newOrder.setLastUpdateDate(null);
            newOrder.setVersionNumber(1L);
            newOrder.setObjectId(null);
            newOrder.setReqsId(null);
            newOrder.setArId(null);
            List<OrderDetail> newOrderDetails = new ArrayList<OrderDetail>();
            for(OrderDetail detail : newOrder.getOrderDetails()) {
                OrderDetail newDetail = copyAndPrepareNewOrderDetail(detail);
                if(newDetail != null)
                    newOrderDetails.add(newDetail);
            }
            newOrder.setOrderDetails(newOrderDetails);
            
            for(Accounts account : newOrder.getAccounts()) {
                account.setOrderDocument(newOrder);
                account.setOrderDocumentNumber(newOrder.getDocumentNumber());
                account.setAccountsId(null);
                account.setVersionNumber(1L);
                account.setLastUpdateDate(null);
                account.setObjectId(null);
                account.setFormAmount(new KualiDecimal(0));
            }
        }
        catch (WorkflowException e) {
            throw new RuntimeException("Error instantiating OrderDocument", e);
        }
		
		
		return newOrder;
	}

	/**
     * @param detail
     * @return an OrderDetail object based on detail, but initialized as a new, unprocessed OrderDetail 
     */
    private OrderDetail copyAndPrepareNewOrderDetail(OrderDetail detail) {
        OrderDetail newDetail = synchronizeWithCatalogItem(detail);
        if(newDetail == null)
            return null;
        
        newDetail.setOrderDocumentNbr(detail.getOrderDocument().getDocumentNumber());
        newDetail.setOrderDetailId(null);
        newDetail.setVersionNumber(1L);
        newDetail.setLastUpdateDate(null);
        newDetail.setObjectId(null);
        newDetail.setSalesInstanceId(null);
        newDetail.setSalesInstance(null);
        newDetail.setOrderStatus(null);
        newDetail.setOrderStatusCd(MMConstants.OrderStatus.INITIATED);
        newDetail.setPoId(null);
        newDetail.setSPaidId(null);
        
        //Pricing
        CatalogService catalogService = SpringContext.getBean(CatalogService.class);        
        newDetail.setOrderItemCostAmt(newDetail.getCatalogItem().getCatalogPrc());        
        newDetail.setOrderItemPriceAmt(catalogService.computeCatalogItemPrice(newDetail.getCatalogItem(), newDetail.getOrderDocument().getCustomerProfile(), newDetail.getOrderItemQty()));
        newDetail.setOrderItemMarkupAmt(newDetail.getOrderItemPriceAmt().subtract(newDetail.getOrderItemCostAmt()));
        if(detail.getOrderItemTaxAmt() != null && detail.getOrderItemTaxAmt().isNonZero()) {
            MMDecimal lineItemTotalPrice = detail.getOrderItemPriceAmt().multiply(new MMDecimal(detail.getOrderItemQty()));
            MMDecimal lineItemTotalTax = catalogService.computeCatalogItemTax(newDetail.getCatalogItem(), lineItemTotalPrice, newDetail.getOrderDocument().getShippingAddress(), newDetail.isWillCall());
            newDetail.setOrderItemTaxAmt(lineItemTotalTax.divide(new MMDecimal(detail.getOrderItemQty())));
        }
        
        for(Accounts account : newDetail.getAccounts()) {
            account.setAccountsId(null);
            account.setOrderDetail(newDetail);
            account.setOrderDetailId(newDetail.getOrderDetailId());
            account.setVersionNumber(1L);
            account.setLastUpdateDate(null);
            account.setObjectId(null);
            account.setFormAmount(new KualiDecimal(0));
        }
        
        return newDetail;
    }

    /**
     * @param detail
     * @return An OrderDetail object that has been synchronized with its CatalogItem.  Returns null if
     * CatalogItem is no longer valid and no valid CatalogItem can be found.
     * 
     */
    private OrderDetail synchronizeWithCatalogItem(OrderDetail detail) {
        OrderDetail synchedDetail = detail;
        if (!detail.getCatalogItem().isActive()) {
            boolean foundCurrent = false;
            String catalogCode = detail.getCatalogItem().getCatalog().getCatalogCd();
            String distributorNumber = detail.getCatalogItem().getDistributorNbr();
            ShopCartCatalogService catalogService = SpringContext
                    .getBean(ShopCartCatalogService.class);
            Catalog catalog = catalogService.getCurrentCatalogByCatalogCode(catalogCode);
            if (catalog != null) {
                CatalogItem catalogItem = catalogService.getCatalogItem(distributorNumber, catalog
                        .getCatalogId());
                if (catalogItem != null) {
                    detail.setCatalogItem(catalogItem);
                    detail.setCatalogItemId(catalogItem.getCatalogItemId());
                    foundCurrent = true;
                }
            }
            if (!foundCurrent) {
                synchedDetail = null;
            }
        }
        return synchedDetail;
    }

	 /**
     * @see org.kuali.ext.mm.service.RecurringOrderService#computeNextRecurringDate(org.kuali.ext.mm.businessobject.RecurringOrder)
     */
    public Date computeNextRecurringDate(RecurringOrder recurringOrder) {
        Date nextRunDate = null;
        if(recurringOrder.getLastRecurringDt() == null) {
            nextRunDate = recurringOrder.getStartDt();      
        }
        else {
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(recurringOrder.getStartDt());
            Calendar lastCal = Calendar.getInstance();
            lastCal.setTime(recurringOrder.getLastRecurringDt());
            Calendar nextCal = null;
            if(MMConstants.RecurringOrder.OPTION_DAILY.equals(recurringOrder.getTimesPerYr())) {
                nextCal = addDaysOfYearToCalendarDate(lastCal, 1);
            }
            else if(MMConstants.RecurringOrder.OPTION_WEEKLY.equals(recurringOrder.getTimesPerYr())) {
                nextCal = addDaysOfYearToCalendarDate(lastCal, 7);
            }
            else if(MMConstants.RecurringOrder.OPTION_BI_WEEKLY.equals(recurringOrder.getTimesPerYr())) {
                nextCal = addDaysOfYearToCalendarDate(lastCal, 14);
            }
            else if(MMConstants.RecurringOrder.OPTION_MONTHLY.equals(recurringOrder.getTimesPerYr())) {
                nextCal = addMonthsToCalendarDate(lastCal, startCal, 1);
            }
            else if(MMConstants.RecurringOrder.OPTION_QUARTERLY.equals(recurringOrder.getTimesPerYr())) {
                nextCal = addMonthsToCalendarDate(lastCal, startCal, 3);
            }
            else if(MMConstants.RecurringOrder.OPTION_SEMI_ANNUALLY.equals(recurringOrder.getTimesPerYr())) {
                nextCal = addMonthsToCalendarDate(lastCal, startCal, 6);
            }
            else if(MMConstants.RecurringOrder.OPTION_YEARLY.equals(recurringOrder.getTimesPerYr())) {
                nextCal = lastCal;
                nextCal.add(Calendar.YEAR, 1);
            }
            
            if(nextCal != null) {
                nextRunDate = new Date(nextCal.getTime().getTime());
                if(!recurringOrder.isNoEndDateInd() && nextRunDate.after(recurringOrder.getEndDt()))
                    nextRunDate = null;
            }
        }
        return nextRunDate;
    }

    /**
     * @param lastCalendar
     * @param startCalendar
     * @param numberOfMonths
     * @return Calendar object with numberOfMonths added to the lastCalendar date.
     */
    private Calendar addMonthsToCalendarDate(Calendar lastCalendar, Calendar startCalendar, int numberOfMonths) {        
        Integer years = lastCalendar.get(Calendar.YEAR) -  startCalendar.get(Calendar.YEAR);
        Integer months = lastCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH) + (years * 12) + numberOfMonths;
        Calendar nextCal = startCalendar;
        nextCal.add(Calendar.MONTH, months);
        return nextCal;
    }

    /**
     * @param lastCal
     * @param numberOfDays
     * @return calendar object with numberOfDays added to the lastCalendar date.
     */
    private Calendar addDaysOfYearToCalendarDate(Calendar lastCalendar, int numberOfDays) {
        Calendar nextCal = lastCalendar;
        nextCal.add(Calendar.DAY_OF_YEAR, numberOfDays);
        return nextCal;
    }

    /**
     * @param currentDate
     * @return List of RecurringOrder objects with Next Recurring Dates less than or equal to currentDate
     */
    public Collection<RecurringOrder> getRecurringOrdersByDate(Date currentDate) {
		QueryElement root = new QueryElement(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
		//Date check
		
		QueryElement nextDateLT = new QueryElement(MMConstants.RecurringOrder.NEXT_RECURRING_DT, currentDate);
		QueryElement nextDateEQ = new QueryElement(MMConstants.RecurringOrder.NEXT_RECURRING_DT, currentDate);
		nextDateLT.setLessThan(true);
        QueryElement nextDate = new QueryElement();
        nextDate.getOrList().add(nextDateLT);
        nextDate.getOrList().add(nextDateEQ);
		
		root.getAndList().add(nextDate);	
		
		return getMmBusinessObjectDao().findMatching(RecurringOrder.class, root);
	}
	
    /**
     * @see org.kuali.ext.mm.service.RecurringOrderService#getOrderFrequencyMap()
     */
    public Map<Integer, String> getOrderFrequencyMap() {        
        return frequencyMap;
    }

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

    public void setMmBusinessObjectDao(MMBusinessObjectDao mmBusinessObjectDao) {
        this.mmBusinessObjectDao = mmBusinessObjectDao;
    }

    public MMBusinessObjectDao getMmBusinessObjectDao() {
        return mmBusinessObjectDao;
    }

    /**
     * Gets the txManager property
     * @return Returns the txManager
     */
    public PlatformTransactionManager getTxManager() {
        return this.txManager;
    }

    /**
     * Sets the txManager property value
     * @param txManager The txManager to set
     */
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }
    
    private TransactionStatus startTransaction(String keyId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(keyId + "-" + Thread.currentThread().getName());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = txManager.getTransaction(def);
        return status;
    }

}
