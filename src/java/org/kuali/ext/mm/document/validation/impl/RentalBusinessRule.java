package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.businessobject.RentalObjectCode;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.RentalService;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.service.DataDictionaryService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;


public class RentalBusinessRule extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean isValid = true;        
        DataDictionaryService dictionaryService = KRADServiceLocatorWeb.getDataDictionaryService();
        RentalService rentalService = MMServiceLocator.getRentalService();
        
        Rental oldRental = (Rental)document.getOldMaintainableObject().getBusinessObject();
        Rental newRental = (Rental)document.getNewMaintainableObject().getBusinessObject();
        
        if(document.isNew()) {
            if(!StringUtils.isBlank(newRental.getStockId())) {
                if(!newRental.getStock().isRental()) {
                    GlobalVariables.getMessageMap().putError(
                            KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Rental.STOCK + "." + MMConstants.Stock.STOCK_DISTRIBUTOR_NBR,
                            MMKeyConstants.Rental.ERROR_RENTAL_STOCK_INVALID,
                            newRental.getStock().getStockDistributorNbr());
                    isValid = false;
                }
                else {
                    RentalObjectCode rentalObjectCode = newRental.getStock().getRentalObject();
                    boolean isValidRental = rentalService.isSerialNumberValidForCheckin(
                            rentalObjectCode.getRentalTypeCode(),
                            newRental.getRentalSerialNumber(), null);
                    if(!isValidRental) {
                        GlobalVariables.getMessageMap().putError(
                                KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Rental.RENTAL_SERIAL_NUMBER,
                                MMKeyConstants.Rental.RENTAL_EXISTS,
                                newRental.getRentalSerialNumber());
                        isValid = false;
                    }
                }
            }
        }
        
        if(newRental.getIssueDate() != null) {
            if(newRental.getLastChargeDate() != null 
                    && newRental.getLastChargeDate().before(newRental.getIssueDate())) {
                GlobalVariables.getMessageMap().putError(
                        KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Rental.LAST_CHARGE_DATE,
                        MMKeyConstants.Rental.ERROR_RENTAL_DATES_OUT_OF_SEQUENCE,
                        dictionaryService.getAttributeErrorLabel(Rental.class, MMConstants.Rental.LAST_CHARGE_DATE),
                        dictionaryService.getAttributeErrorLabel(Rental.class, MMConstants.Rental.ISSUE_DATE));
                isValid = false;
            }
            if(newRental.getReturnDate()!= null 
                    && newRental.getReturnDate().before(newRental.getIssueDate())) {
                GlobalVariables.getMessageMap().putError(
                        KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Rental.RETURN_DATE,
                        MMKeyConstants.Rental.ERROR_RENTAL_DATES_OUT_OF_SEQUENCE,
                        dictionaryService.getAttributeErrorLabel(Rental.class, MMConstants.Rental.RETURN_DATE),
                        dictionaryService.getAttributeErrorLabel(Rental.class, MMConstants.Rental.ISSUE_DATE));
                isValid = false;
            }
        }
        else {
            //User cannot un-issue a rental item (from an issue date to no issue date).
            //Also cannot issue an item from this document, this rule is enforced in the presentation controller
            if(oldRental.getIssueDate() != null) {
                GlobalVariables.getMessageMap().putError(
                        KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Rental.ISSUE_DATE,
                        MMKeyConstants.Rental.ERROR_RENTAL_CANNOT_UNISSUE);
                isValid = false;
            }
        }
        if(newRental.getReturnDate()!= null && newRental.getLastChargeDate() != null
                && newRental.getReturnDate().before(newRental.getLastChargeDate())) {
            GlobalVariables.getMessageMap().putError(
                    KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Rental.RETURN_DATE,
                    MMKeyConstants.Rental.ERROR_RENTAL_DATES_OUT_OF_SEQUENCE,
                    dictionaryService.getAttributeErrorLabel(Rental.class, MMConstants.Rental.RETURN_DATE),
                    dictionaryService.getAttributeErrorLabel(Rental.class, MMConstants.Rental.LAST_CHARGE_DATE));
            isValid = false;
        }
        //User should not be able to erase the last change date
        if(newRental.getLastChargeDate() == null && oldRental.getLastChargeDate() != null) {
            GlobalVariables.getMessageMap().putError(
                    KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Rental.RETURN_DATE,
                    MMKeyConstants.Rental.ERROR_RENTAL_CANNOT_ERASE_LAST_CHARGE_DATE);
            isValid = false;
        }
        //User should not be able to "un-return" a returned rental item that is not the current item for serial number and type code
        Rental currentRental = rentalService.getCurrentRentalItem(newRental.getRentalTypeCode(), newRental.getRentalSerialNumber(), true);
        if(newRental.getReturnDate() == null && oldRental.getReturnDate() != null
                && !currentRental.getRentalId().equals(newRental.getRentalId())) {
            GlobalVariables.getMessageMap().putError(
                    KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Rental.RETURN_DATE,
                    MMKeyConstants.Rental.ERROR_RENTAL_CANNOT_ERASE_RETURN_DATE);
            isValid = false;
        }
        else if(newRental.getReturnDate() != null){
            //Cannot replace an existing historical record (unique key for rental is serial number, type code, return date).
            Rental historicRental = rentalService.getHistoricRental(newRental.getRentalSerialNumber(), 
                    newRental.getRentalTypeCode(), newRental.getReturnDate());
            if(historicRental != null) {
                GlobalVariables.getMessageMap().putError(
                        KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Rental.RETURN_DATE,
                        MMKeyConstants.Rental.ERROR_HISTORIC_RENTAL_RECORD_EXISTS,
                        newRental.getRentalSerialNumber(),
                        newRental.getRentalTypeCode(),
                        CoreApiServiceLocator.getDateTimeService().toDateTimeString(newRental.getReturnDate()));
                isValid = false;
            }
        }
        //Accounting lines validation
        if(MMConstants.Rental.RENTAL_STATUS_ISSUED.equals(newRental.getRentalStatusCode())
                || (MMConstants.Rental.RENTAL_STATUS_RETURNED.equals(newRental.getRentalStatusCode())
                        && newRental.getPickListLineId() != null)) { 
            int i=0;
            String index= "";
            String errorKey = "";
            Double costTotal = newRental.getStock().getRentalObject().getDailyDemurragePrice().doubleValue();
            KualiDecimal accountsTotal = new KualiDecimal(0.0);
            for(Accounts account : newRental.getAccountingLines()) {
                index = "[" + String.valueOf(i) + "]";
                errorKey = KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "." + MMConstants.Rental.ACCOUNTING_LINES + index + ".";
                isValid &= MMServiceLocator.getOrderService().validateAccountingLine(account, errorKey);
                accountsTotal = accountsTotal.add(account.getAccountFixedAmt());
            }
            if(costTotal != accountsTotal.doubleValue()) {
                GlobalVariables.getMessageMap().putError(
                        KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "." + MMConstants.Rental.ACCOUNTING_LINES + "[0]",
                        MMKeyConstants.Rental.ERROR_RENTAL_ACCOUNTS_INVALID_TOTAL, 
                        String.valueOf(costTotal),
                        accountsTotal.toString());
                isValid = false;
            }
        }
        return isValid;       
    }
    
    public boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document, String collectionName, PersistableBusinessObject line) {
        boolean isValid = true;
        if(MMConstants.Rental.ACCOUNTING_LINES.equals(collectionName)) {
            Accounts account = (Accounts)line;
            isValid &= MMServiceLocator.getOrderService().validateAccountingLine(account, "");
        }
        return isValid;
    }
}
