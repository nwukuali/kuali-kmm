package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.PickVerifyDocument;
import org.kuali.ext.mm.document.service.BusinessObjectLockingService;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.PickVerifyService;
import org.kuali.ext.mm.service.RentalService;
import org.kuali.ext.mm.service.StockService;
import org.kuali.rice.kns.rules.DocumentRuleBase;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


public class PickVerifyDocumentRule extends DocumentRuleBase {

    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean processCustomSaveDocumentBusinessRules(Document document) {
        PickVerifyDocument pickVerifyDocument = (PickVerifyDocument) document;
        boolean isValid = true;

        if (pickVerifyDocument.getPickTicket() == null) {
            GlobalVariables.getMessageMap().putError(MMConstants.PickVerifyDocument.PICK_TICKET_NUMBER, MMKeyConstants.ERROR_NO_PICK_TICKET);
            isValid = false;
        }
        else {
        	isValid &= pickStatusIsPrinted(pickVerifyDocument);
            isValid &= pickTicketNotLocked(pickVerifyDocument);
            if (isValid)
                isValid &= validateLines(pickVerifyDocument);
        }

        return isValid;
    }

    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomRouteDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(Document document) {
        boolean isValid = SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .checkAndErrorSystemAvailability();        
        PickVerifyDocument pickVerifyDocument = (PickVerifyDocument) document;
        
        int i = 0;
        for (PickListLine line : pickVerifyDocument.getPickTicket().getPickListLines()) {
            String index = "[" + i + "].";            
            String errorPath = MMConstants.PickVerifyDocument.PICK_LIST_LINES + index;
            isValid &= validateLineForQuantityOnHand(line, errorPath);            
            isValid &= isStockBalanceLockedForItem(pickVerifyDocument, line);
            int j = 0;
            for(PickListLine additionalLine : line.getAdditionalLines()) {
                String addErrorPath = errorPath + "additionalLines[" + j + "].";
                isValid &= validateLineForQuantityOnHand(additionalLine, addErrorPath);
                isValid &= isStockBalanceLockedForItem(pickVerifyDocument, additionalLine);
                j++;
            }
            i++;
        }
        return isValid;
    }
    
    private boolean validateLineForQuantityOnHand(PickListLine line, String errorPath) {
        boolean isValid = true;
        PickVerifyService verifyService = MMServiceLocator.getPickVerifyService();
        StockService stockService = MMServiceLocator.getStockService();
        StockBalance stockBalance = stockService.retrieveStockBalance(line.getBin().getBinId());
        Integer totalPickedFromBin = verifyService.getTotalPickedFromBin(line.getPickTicket(), line.getBin());
        if (stockBalance.getQtyOnHand() < totalPickedFromBin) {                
            GlobalVariables.getMessageMap().putError(
                    errorPath + MMConstants.PickListLine.PICK_STOCK_QTY,
                    MMKeyConstants.PickVerifyDocument.ERROR_NOT_ENOUGH_STOCK, 
                    line.getItemLocation(),
                    String.valueOf(stockBalance.getQtyOnHand()),
                    String.valueOf(totalPickedFromBin));
            isValid = false;
        }
        return isValid;
    }

    private boolean pickTicketNotLocked(PickVerifyDocument pickVerifyDocument) {
        BusinessObjectLockingService lockingService = MMServiceLocator
                .getBusinessObjectLockingService();
        List<String> fields = new ArrayList<String>();
        fields.add(MMConstants.PickTicket.PICK_TICKET_NUMBER);
        List<String> lockingIds = lockingService.getLockingDocumentIds(pickVerifyDocument
                .getPickTicket(), fields, pickVerifyDocument.getDocumentNumber());
        for (String id : lockingIds) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.PickVerifyDocument.PICK_TICKET_NUMBER,
                    MMKeyConstants.ERROR_PICK_TICKET_LOCKED,
                    pickVerifyDocument.getPickTicketNumber(), id);
        }
        return lockingIds.isEmpty();
    }

    private boolean isStockBalanceLockedForItem(PickVerifyDocument pickVerifyDocument,
            PickListLine line) {
        BusinessObjectLockingService lockingService = MMServiceLocator
                .getBusinessObjectLockingService();
        List<String> fields = new ArrayList<String>();
        fields.add(MMConstants.StockBalance.STOCK_BALANCE_ID);
        List<String> lockingIds = lockingService.getLockingDocumentIds(line.getBin()
                .getStockBalance(), fields, pickVerifyDocument.getDocumentNumber());
        if(ObjectUtils.isNull(line.getStock()))
        	line.refreshReferenceObject(MMConstants.PickListLine.STOCK);
        for (String id : lockingIds) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.PickVerifyDocument.PICK_LIST_LINES,
                    MMKeyConstants.ERROR_STOCK_BALANCE_LOCKED,
                    line.getStock().getDistributorNbr(), id);
        }
        return lockingIds.isEmpty();
    }

    /**
     * @param document
     * @return true if the pick status of the pick ticket is PRTD (Printed)
     */
    private boolean pickStatusIsPrinted(PickVerifyDocument document) {
        boolean isValid = true;
        if (document.getPickTicket() != null
                && !MMConstants.PickStatusCode.PICK_STATUS_PRTD.equals(document.getPickTicket()
                        .getPickStatusCodeCd())) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.PickVerifyDocument.PICK_TICKET_NUMBER,
                    MMKeyConstants.ERROR_PICK_STATUS_NOT_PRTD, document.getPickTicketNumber());
            isValid = false;
        }
        return isValid;
    }

    /**
     * @param document
     * @return True of the line quantities (pick, backorder, stock) match up for each line
     */
    private boolean validateLines(PickVerifyDocument document) {
        int i = 0;
        boolean isValid = true;
        if (document.getPickTicket().getPickListLines().isEmpty()) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.PickVerifyDocument.PICK_TICKET_NUMBER,
                    MMKeyConstants.ERROR_NO_PICK_TICKET_LINES, document.getPickTicketNumber());
            return false;
        }
        PickVerifyService pickVerifyService = SpringContext.getBean(PickVerifyService.class);
        List<String> uniqueRentals = new ArrayList<String>();
        for (PickListLine line : document.getPickTicket().getPickListLines()) {
            String index = "[" + i + "].";
            if (line.getBackOrderQty() == null || line.getPickStockQty() == null) {
                if (line.getPickStockQty() == null)
                    GlobalVariables.getMessageMap().putError(
                            MMConstants.PickVerifyDocument.PICK_LIST_LINES + index
                                    + MMConstants.PickListLine.PICK_STOCK_QTY,
                            MMKeyConstants.ERROR_LINES_NOT_COMPLETE, String.valueOf(i + 1));
                if (line.getBackOrderQty() == null)
                    GlobalVariables.getMessageMap().putError(
                            MMConstants.PickVerifyDocument.PICK_LIST_LINES + index
                                    + MMConstants.PickListLine.BACK_ORDER_QTY,
                            MMKeyConstants.ERROR_LINES_NOT_COMPLETE, String.valueOf(i + 1));
                isValid = false;
            }
            else {
                if (!pickVerifyService.isQuantityValid(line)) {
                    GlobalVariables.getMessageMap().putError(
                            MMConstants.PickVerifyDocument.PICK_LIST_LINES + index
                                    + MMConstants.PickListLine.PICK_STOCK_QTY,
                            MMKeyConstants.ERROR_LINE_QTY_SUM, String.valueOf(i + 1),
                            line.getTotalPickStockQty().toString(), line.getBackOrderQty().toString(),
                            line.getStockQty().toString());
                    isValid = false;
                }
            }
            
            if (line.isTrackableWithSerialNumber()) {
                isValid = validateRentals(line, i, uniqueRentals);
            }
            i++;
        }
        return isValid;
    }
    
    private boolean validateRentals(PickListLine line, int lineIndex, List<String> uniqueRentals) {
        RentalService rentalService = SpringContext.getBean(RentalService.class);  
        PickVerifyService pickVerifyService = SpringContext.getBean(PickVerifyService.class);
        boolean isValid = true;
        String index = "[" + lineIndex + "].";
        if (!pickVerifyService.rentalsMatchPickQuantity(line)) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.PickVerifyDocument.PICK_LIST_LINES + index
                            + MMConstants.PickListLine.RENTALS + "[0]."
                            + MMConstants.Rental.RENTAL_SERIAL_NUMBER,
                    MMKeyConstants.ERROR_PICK_QTY_RENTALs_MISMATCH, String.valueOf(lineIndex + 1),
                    line.getPickStockQty().toString());
            isValid = false;
        }
        else {
            int j = 0;
            for (Rental rental : line.getRentals()) {
                if (StringUtils.isBlank(rental.getRentalSerialNumber()))
                    continue;
                Rental checkRental = rentalService.getAvailableRentalItem(line.getStock(), rental.getRentalSerialNumber());
                String rentalIndex = "[" + j++ + "].";
                if (checkRental == null) {
                    GlobalVariables.getMessageMap().putError(
                            MMConstants.PickVerifyDocument.PICK_LIST_LINES + index
                                    + MMConstants.PickListLine.RENTALS + rentalIndex
                                    + MMConstants.Rental.RENTAL_SERIAL_NUMBER,
                            MMKeyConstants.ERROR_NO_RENTAL_MATCH, String.valueOf(lineIndex + 1),
                            rental.getRentalSerialNumber(),
                            line.getStock().getStockDistributorNbr());
                    isValid = false;
                }
                else if (uniqueRentals.contains(rental.getRentalSerialNumber())) {
                    GlobalVariables.getMessageMap().putError(
                            MMConstants.PickVerifyDocument.PICK_LIST_LINES + index
                                    + MMConstants.PickListLine.RENTALS + rentalIndex
                                    + MMConstants.Rental.RENTAL_SERIAL_NUMBER,
                            MMKeyConstants.ERROR_RENTAL_NON_UNIQUE, String.valueOf(lineIndex + 1),
                            rental.getRentalSerialNumber());
                    isValid = false;
                }
                else
                    uniqueRentals.add(rental.getRentalSerialNumber());
            }
        }
        return isValid;
    }    
    

}
