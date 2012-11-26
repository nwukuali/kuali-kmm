/**
 *
 */
package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.CheckinOrderDAO;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.document.ReceiptCorrectionDocument;
import org.kuali.ext.mm.document.dataaccess.OrderStatusDao;
import org.kuali.ext.mm.document.service.BusinessObjectLockingService;
import org.kuali.ext.mm.service.CheckinOrderService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.ReceiptCorrectionService;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author rponraj
 * 
 */
@Transactional
public class ReceiptCorrectionServiceImpl implements ReceiptCorrectionService {

    private CheckinOrderDAO checkinOrderDAO;
    private OrderStatusDao orderStatusDao;
    private StockService stockService;
    private CheckinOrderService checkinOrderService;

    public CheckinOrderService getCheckinOrderService() {
        return this.checkinOrderService;
    }

    public void setCheckinOrderService(CheckinOrderService checkinOrderService) {
        this.checkinOrderService = checkinOrderService;
    }

    public StockService getStockService() {
        return stockService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public CheckinOrderDAO getCheckinOrderDAO() {
        return this.checkinOrderDAO;
    }

    public void setCheckinOrderDAO(CheckinOrderDAO checkinOrderDAO) {
        this.checkinOrderDAO = checkinOrderDAO;
    }

    /**
     * sets the basic parameters of the document
     * 
     * @param checkinDoc
     * @param orderDoc
     * @return
     */
    private ReceiptCorrectionDocument setDocParams(ReceiptCorrectionDocument checkinDoc,
            OrderDocument orderDoc) {
        String warehouseCode = orderDoc.getWarehouseCd();
        checkinDoc.setWarehouseCode(warehouseCode);

        Warehouse warehouse = orderDoc.getWarehouse() == null ? (Warehouse) StoresPersistableBusinessObject
                .getObjectByPrimaryKey(Warehouse.class, warehouseCode)
                : orderDoc.getWarehouse();

        checkinDoc.setWarehouse(warehouse);
        checkinDoc.setOrderDocNumber(orderDoc.getDocumentNumber());
        checkinDoc.getDocumentHeader().setDocumentDescription(
                "Receipt Correction of " + checkinDoc.getDocumentNumber());
        return checkinDoc;
    }

    /**
     * creates new receipt correction document
     * 
     * @see org.kuali.ext.mm.service.ReceiptCorrectionService#createReceiptItems(org.kuali.ext.mm.document.ReceiptCorrectionDocument,
     *      java.lang.String, java.lang.String)
     */
    public ReceiptCorrectionDocument createReceiptItems(ReceiptCorrectionDocument checkinDoc,
            String orderDocNumber, String orderLineNumber) throws Exception {
        OrderDocument orderDoc = this.checkinOrderDAO.getOrderDocument(orderDocNumber);
        setDocParams(checkinDoc, orderDoc);
        createCheckinCorrectionDetails(checkinDoc, orderDoc);
        return checkinDoc;
    }

    /**
     * creates valid checkin lines for the corrected item
     * 
     * @param checkinDoc
     * @param orderDoc
     * @param orderLineNumber
     * @return A ReceiptCorrectionDocument populated with check-in details for correction.
     * @throws Exception
     */
    private ReceiptCorrectionDocument createCheckinCorrectionDetails(ReceiptCorrectionDocument checkinDoc,
            OrderDocument orderDoc) throws Exception {

        List<OrderDetail> orderDetails = orderDoc.getOrderDetails();

        if (MMUtil.isCollectionEmpty(orderDetails))
            return checkinDoc;

        checkinDoc.getCheckinDetails().clear();
        for (OrderDetail orderDetail : orderDetails) {            
            Collection<CheckinDetail> checkinDetails = createCorrectableCheckinDetails(checkinDoc, orderDetail);
            checkinDoc.getCheckinDetails().addAll(checkinDetails);            
        }
        checkinDoc.setOrderDocNumber(orderDoc.getDocumentNumber());
        checkinDoc.setOrderDocument(orderDoc);

        return checkinDoc;
    }

    /**
     * creates list of checkin items
     * 
     * @param checkinDoc
     * @param orderDetail
     * @return A Collection of CheckinDetails for correction. 
     */
    private Collection<CheckinDetail> createCorrectableCheckinDetails(CheckinDocument checkinDoc,
            OrderDetail orderDetail) {
        Map<Integer, CheckinDetail> checkinDetailMap = new HashMap<Integer, CheckinDetail>();
        Set<Integer> correctedDetailSet = new HashSet<Integer>();

        for (CheckinDetail cdetail : orderDetail.getCheckinDetails()) {
            CheckinDetail checkinDetail = (CheckinDetail) cdetail.clone();
            checkinDetail.setCheckinDocumentNumber(checkinDoc.getDocumentNumber());
            checkinDetail.setCorrectedCheckinDetailId(cdetail.getCheckinDetailId());
            checkinDetail.setCorrectedCheckinDetail(cdetail);
            checkinDetail.setCheckinDoc(checkinDoc);
            checkinDetail.setRejectedItemQty(cdetail.getRejectedItemQty());
            checkinDetail.setAcceptedItemQty(0);
            checkinDetail.setCheckinDetailId(null);

            if (checkinDetail.getStock().isRental()) {
                checkinDetail.setCheckinRentals(new ArrayList());
                checkinDetail.setRentals(new ArrayList());
            }            
            if(isEligibleForCorrection(cdetail))
                checkinDetailMap.put(cdetail.getCheckinDetailId(), checkinDetail);
            
            if(cdetail.getCorrectedCheckinDetailId() != null)
                correctedDetailSet.add(cdetail.getCorrectedCheckinDetailId());
        }
        for(Integer detailId : correctedDetailSet) {
            checkinDetailMap.remove(detailId);
        }
        return checkinDetailMap.values();
    }

    /**
     * @param cdetail
     * @return true if cdetail is eligible for correction
     */
    private boolean isEligibleForCorrection(CheckinDetail cdetail) {
        return cdetail.getAcceptedItemQty() > 0 
            && (cdetail.getCheckinDoc().isFinalInd() 
                    || cdetail.getCheckinDoc().getDocumentHeader().getWorkflowDocument().isDisapproved()
                    || cdetail.getCheckinDoc().getDocumentHeader().getWorkflowDocument().isCanceled());
    }

    /**
     * Corrects checked in rentals.
     * Creates new corrected-in rentals and deletes corrected-out rentals.
     * 
     * @param checkinDoc
     */
    private void checkinRentals(ReceiptCorrectionDocument checkinDoc) {
        for (CheckinDetail cdetail : checkinDoc.getCheckinDetails()) {
            if (cdetail.getStock().isRental()) {
                List<StagingRental> originalRentals = cdetail.getCorrectedCheckinDetail()
                        .getCheckinRentals();
                List<StagingRental> correctedRentals = cdetail.getCheckinRentals();
                List<String> rentalsToRemove = getRentalSetDifference(correctedRentals, originalRentals);
                List<String> rentalsToAdd = getRentalSetDifference(originalRentals, correctedRentals);
                Collection<Rental> currentRentals = MMServiceLocator.getRentalService()
                    .getCurrentNonReturnedRentalItems(cdetail.getStock(), rentalsToRemove);
                for(Rental rentalToRemove : currentRentals) {
                    if(rentalToRemove.getIssueDate() == null)
                        rentalToRemove.delete();
                }
                for(String rentalToAdd : rentalsToAdd) {
                    Rental newRental = new Rental();
                    newRental.setRentalSerialNumber(rentalToAdd);
                    newRental.setCheckinDetailId(cdetail.getCheckinDetailId());
                    newRental.setStockId(cdetail.getStockId());
                    newRental.setRentalTypeCode(cdetail.getStock().getRentalObject().getRentalTypeCode());
                    newRental.setRentalStatusCode(MMConstants.Rental.RENTAL_STATUS_AVAILABLE);
                    MMServiceLocator.getRentalService().saveRental(newRental);
                }
                cdetail.getCorrectedCheckinDetail().refreshReferenceObject(MMConstants.CheckinDetail.RENTALS);
                for(Rental rental : cdetail.getCorrectedCheckinDetail().getRentals()) {
                    rental.setCheckinDetailId(cdetail.getCheckinDetailId());
                    MMServiceLocator.getRentalService().saveRental(rental);
                }
            }
        }
    }
    
    /**
     * Determines the complement of set a relative to b.
     * 
     * @param a - a sublist of rentals
     * @param b - master list of rentals
     * @return a list of rental serial numbers found in b, but not in a
     */
    private List<String> getRentalSetDifference(List<StagingRental> a, List<StagingRental> b) {
        List<String> compliment = new ArrayList<String>();
        if(b != null) {
            for(StagingRental rental : b) {
                if(a == null || a.isEmpty() 
                        || !a.contains(rental)) {
                    compliment.add(rental.getSerialNumber());
                }                        
            }
        }
        return compliment;
    }

    /**
     * @see org.kuali.ext.mm.service.ReceiptCorrectionService#processReceiptCorrectionDocument(org.kuali.ext.mm.document.ReceiptCorrectionDocument)
     */
    public void processReceiptCorrectionDocument(ReceiptCorrectionDocument cdoc) {
        Map<String, MMDecimal> stockCosts = null;
        stockCosts = MMServiceLocator.getCheckinOrderService().updateStockPrice(cdoc);
        updateStockBalance(cdoc, stockCosts);
        checkinRentals(cdoc);
        updateOrderStatus(cdoc);
    }

    /**
     * @param cdoc
     */
    private void updateOrderStatus(ReceiptCorrectionDocument cdoc) {
        for (CheckinDetail cdetail : cdoc.getCheckinDetails()) {
            if (cdetail.getCorrectedQuantity() > 0) {
                Integer orderDetailId = cdetail.getOrderDetailId();
                OrderDetail orderDtl = SpringContext.getBean(BusinessObjectService.class)
                        .findBySinglePrimaryKey(OrderDetail.class, orderDetailId);
                orderDtl.setOrderStatusCd(MMConstants.OrderStatus.ORDER_LINE_RECEIVING);
                orderDtl.save();
            }
        }
        getOrderStatusDao().updateOrderStatus(cdoc.getOrderDocNumber(),
                MMConstants.OrderStatus.ORDER_LINE_RECEIVING);
    }

    /**
     * This method updates the stock balance of the corrected items and saves the stock history.
     * 
     * @param checkinDocument
     */
    private void updateStockBalance(ReceiptCorrectionDocument checkinDocument, Map<String, MMDecimal> stockCosts) {
        List<CheckinDetail> cdetails = checkinDocument.getCheckinDetails();
        BusinessObjectLockingService lockingService = MMServiceLocator.getBusinessObjectLockingService();
        
        for (CheckinDetail cdetail : cdetails) {
            Integer correctedQty = cdetail.getCorrectedQuantity();
            if (correctedQty > 0) {
                lockingService.createAndSaveLock(checkinDocument.getDocumentNumber(), cdetail.getStock(), MMConstants.Stock.STOCK_ID);
                StockBalance sbal = stockService.retrieveStockBalance(cdetail.getBinId());
                
                if (sbal != null)
                    sbal.setQtyOnHand(sbal.getQtyOnHand() - correctedQty);
                else
                    throw new RuntimeException("Invalid Item is corrected");

                if (cdetail.getStock().isPerishableInd())
                    sbal.setStockPerishableDt(new java.sql.Date(cdetail.getStockPerishableDate().getTime()));

                sbal.setLastCheckinDt(new java.sql.Date(new java.util.Date().getTime()));                    
                sbal.save();
                lockingService.deleteLocks(checkinDocument.getDocumentNumber());
                
                StockHistory correctionHistory = MMServiceLocator.getStockHistoryService()
                    .createStockHistoryForReceiptCorrection(cdetail, sbal, stockCosts.get(cdetail.getStockId()));
                correctionHistory.save();
//                    cdetail.getStock().getStockBalances().add(sb);
            }
        }
    }

    /**
     * Gets the orderStatusDao property
     * 
     * @return Returns the orderStatusDao
     */
    public OrderStatusDao getOrderStatusDao() {
        return this.orderStatusDao;
    }

    /**
     * Sets the orderStatusDao property value
     * 
     * @param orderStatusDao The orderStatusDao to set
     */
    public void setOrderStatusDao(OrderStatusDao orderStatusDao) {
        this.orderStatusDao = orderStatusDao;
    }
}
