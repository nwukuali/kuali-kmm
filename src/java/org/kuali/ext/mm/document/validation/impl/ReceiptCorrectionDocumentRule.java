package org.kuali.ext.mm.document.validation.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.ext.mm.businessobject.CheckinDetail;
import org.kuali.ext.mm.businessobject.StagingRental;
import org.kuali.ext.mm.businessobject.Stock;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.ReceiptCorrectionDocument;
import org.kuali.ext.mm.document.service.BusinessObjectLockingService;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.RentalService;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rules.DocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;


public class ReceiptCorrectionDocumentRule extends DocumentRuleBase {

    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    public boolean processRouteDocument(Document document) {
        boolean retVal = SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .checkAndErrorSystemAvailability();
        ReceiptCorrectionDocument correctionDocument = (ReceiptCorrectionDocument) document;
        List<CheckinDetail> cdetails = correctionDocument.getCheckinDetails();
        int index = 0;

        Integer selectedItem = correctionDocument.getSelectedOrderDetailId();

        BusinessObjectLockingService lockService = MMServiceLocator
                .getBusinessObjectLockingService();
        
        List<String> serialNumberList = getAllRentalSerialNumbers(cdetails);

        Integer count = 0;
        boolean isLineSleected = ObjectUtils.isNull(selectedItem) || selectedItem == 0;
        for (CheckinDetail cdetail : cdetails) {
            String dispString = "[" + index + "].";
            if ((isLineSleected || cdetail.getOrderDetailId().equals(selectedItem))
                    && cdetail.isLineCorrected()) {
                count++;
                Integer acceptedItemQty = cdetail.getAcceptedItemQty();
                
                Integer origQuantity = cdetail.getCorrectedCheckinDetail().getAcceptedItemQty();
                
                if (acceptedItemQty > origQuantity 
                        || (acceptedItemQty == origQuantity && !isRentalListModified(cdetail))) {
                    GlobalVariables
                            .getMessageMap()
                            .putError(
                                    MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + dispString
                                            + MMConstants.CheckinDoc.ACCEPTED_ITEM_QUANTITY,
                                    MMKeyConstants.ReceiptCorrectionDocuemnt.INVALID_ACCEPTED_ITEM_QUANTITY,
                                    String.valueOf(acceptedItemQty),
                                    String.valueOf(origQuantity));
                    retVal = false;

                }
                // check for buy unit of issue rate to make sure that we recieved in correct package sizing
                else if (cdetail.computeAcceptedBuyQuantity() % 1d != 0) {
                    GlobalVariables
                            .getMessageMap()
                            .putError(
                                    MMConstants.CheckinDocument.DOCUMENT_CHECKIN_DETAILS + dispString
                                            + MMConstants.CheckinDoc.ACCEPTED_ITEM_QUANTITY,
                                    MMKeyConstants.CheckinDoc.ACCEPTED_ITEM_INVALID_BUY_RATE_QUANTITY,
                                    cdetail.getBuyUnitOfIssueRt().toString());
                }
                
                Stock stockObject = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Stock.class, cdetail.getStockId());
                List<String> lockingIds = lockService.getLockingDocumentIds(stockObject, 
                        MMConstants.CheckinDetail.STOCK_ID, correctionDocument.getDocumentNumber());
                
                if (!MMUtil.isCollectionEmpty(lockingIds)) {
                    MMUtil.postMMObjectLockingError(Stock.class, 
                            cdetail.getStock().getStockDistributorNbr(), 
                            lockingIds.get(0), MMConstants.DOCUMENT + "." + MMConstants.ReceiptCorrection.CHECKIN_DETAILS);
                    retVal = false;
                }

                if (cdetail.getStock().isRental()) {
                    retVal = validateRentalData(cdetail, dispString, serialNumberList);
                }
            }
            index++;
        }

        if (count < 1
                && (document.getDocumentHeader().getWorkflowDocument().stateIsInitiated() || document
                        .getDocumentHeader().getWorkflowDocument().stateIsSaved())) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.ReceiptCorrection.RECEIPT_CORRECTION_DOCUEMNT + "[" + 0 + "]."
                            + MMConstants.ReceiptCorrection.LINE_CORRECTED,
                    MMKeyConstants.ReceiptCorrectionDocuemnt.NO_LINE_SELECTED);
            retVal = false;
        }

        return retVal;
    }
    
    private boolean isRentalListModified(CheckinDetail detail) {
        if(detail.getCheckinRentals().size() != detail.getCorrectedCheckinDetail().getCheckinRentals().size())
            return true;
        
        Set<StagingRental> difference = new HashSet<StagingRental>();        
        difference.addAll(detail.getCorrectedCheckinDetail().getCheckinRentals());
        
        for(StagingRental correctingRental : detail.getCheckinRentals()) {
            difference.remove(correctingRental);
        }
        
        return !difference.isEmpty();
    }

    /**
     * @param cdetails
     * @return
     */
    private List<String> getAllRentalSerialNumbers(List<CheckinDetail> cdetails) {
        List<String> serialNumbers = new ArrayList<String>();
        for(CheckinDetail cdetail : cdetails) {
            if(!MMUtil.isCollectionEmpty(cdetail.getCheckinRentals())) {
                for(StagingRental srental : cdetail.getCheckinRentals()) {
                    serialNumbers.add(srental.getSerialNumber());
                }
            }
        }
        return serialNumbers;
    }
    
    /**
     * @param retVal
     * @param rentalSerialnumbers
     * @param cdetail
     * @param dispString
     * @return
     */
    protected boolean validateRentalData(CheckinDetail cdetail, String dispString, List<String> serialNumberList) {
        boolean retVal = true;
        List<StagingRental> checkinRentals = cdetail.getCheckinRentals();
        if ((MMUtil.isCollectionEmpty(checkinRentals) && cdetail.getAcceptedItemQty() != 0) 
                || checkinRentals.size() != cdetail.getAcceptedItemQty()) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.RentalTrackingDocument.SELECTED_ITEMS + dispString,
                    MMKeyConstants.CheckinDoc.RENTAL_COUNT_INVALID,
                    String.valueOf(cdetail.getAcceptedItemQty()) );
            retVal = false;
        } 
        RentalService rentalService = SpringContext.getBean(RentalService.class);
        for(StagingRental checkinRental : checkinRentals) {
            boolean isValidRental = rentalService.isSerialNumberValidForCheckin(
                    cdetail.getStock().getRentalObject().getRentalTypeCode(),
                    checkinRental.getSerialNumber(), cdetail.getCorrectedCheckinDetailId());
            if(!isValidRental) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.RentalTrackingDocument.SELECTED_ITEMS + dispString,
                        MMKeyConstants.CheckinDoc.RENTAL_EXISTS,
                        checkinRental.getSerialNumber() );
                retVal = false;
            }
            String serialNumber = checkinRental.getSerialNumber();
            //Serial number should not appear in this document twice
            if (serialNumberList.indexOf(serialNumber) != serialNumberList.lastIndexOf(serialNumber)) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.RentalTrackingDocument.SELECTED_ITEMS + dispString,
                        MMKeyConstants.CheckinDoc.RENTAL_SERIAL_NUMBER_ALREADY_EXISTS_IN_DOCUMENT,
                        checkinRental.getSerialNumber() );
                retVal = false;
            }
        }
        return retVal;
    }

    
}
