package org.kuali.ext.mm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.bo.DocumentHeader;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;


public class RentalMaintainableImpl extends KualiMaintainableImpl  {
    
    @Override
    public Map populateBusinessObject(Map<String, String> fieldValues, MaintenanceDocument maintenanceDocument, String methodToCall) {
        Map mapValues = super.populateBusinessObject(fieldValues, maintenanceDocument, methodToCall);
        Rental newRental = (Rental)maintenanceDocument.getNewMaintainableObject().getBusinessObject();
        if(maintenanceDocument.isNew()) {
            newRental.setRentalStatusCode(MMConstants.Rental.RENTAL_STATUS_AVAILABLE);
            newRental.refreshReferenceObject(MMConstants.Rental.STOCK);
            if(StringUtils.isNotEmpty(newRental.getStockId()) && ObjectUtils.isNotNull(newRental.getStock()) 
                    && newRental.getStock().isRental()) {
                newRental.setRentalType(newRental.getStock().getRentalObject().getRentalType());
                newRental.setRentalTypeCode(newRental.getRentalType().getRentalTypeCode());             
            }
        }
        
        return mapValues;
    }
    
    @Override
    public void processBeforeAddLine(String colName, Class colClass, BusinessObject addBO) {
     // get the new line from the map
        if ( addBO != null && addBO instanceof Accounts ) {
            Accounts newAccount = (Accounts)addBO;
            Rental rental = (Rental)getBusinessObject();
            Double total = rental.getStock().getRentalObject().getDailyDemurragePrice().doubleValue();
            KualiDecimal formAmount = (newAccount.getFormAmount() != null ? newAccount.getFormAmount() : KualiDecimal.ZERO);
            if (MMConstants.OrderDocument.OPTION_FXD.equals(newAccount.getAmountType())) {
                newAccount.setAccountFixedAmt(formAmount);
                Double percent = newAccount.getAccountFixedAmt().doubleValue() / total;
                newAccount.setAccountPct(new BigDecimal(percent * 100.0));
                newAccount.setFormAmount(newAccount.getAccountFixedAmt());
            }
            else {
                newAccount.setAccountPct(formAmount.bigDecimalValue());
                newAccount.setAccountFixedAmt(new KualiDecimal(total
                        * (newAccount.getAccountPct().doubleValue() * new Double(.01))));
                newAccount.setFormAmount(new KualiDecimal(newAccount.getAccountPct()));
            }
        }
    }
    
    /**
     * 
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#prepareForSave()
     */
    @Override
    public void prepareForSave() {
        super.prepareForSave();
        Rental rental = (Rental)getBusinessObject();
        
        //Update rental status code according to dates
        if(rental.getReturnDate() != null)
            rental.setRentalStatusCode(MMConstants.Rental.RENTAL_STATUS_RETURNED);
        else if(rental.getIssueDate() != null)
            rental.setRentalStatusCode(MMConstants.Rental.RENTAL_STATUS_ISSUED);
        else
            rental.setRentalStatusCode(MMConstants.Rental.RENTAL_STATUS_AVAILABLE);       
        
        updateAccountingLineData(rental);        
    }
    
    /**
     * Recomputes the percent and fixed amounts for all of the accounting lines.  Checks for penny
     * remainder and adds it to or subtracts it from the last percentage account (if present).
     * If no percent accounts are available, nothing is done and penny remainder is caught in business rule
     * validation.   
     * 
     * @param rental
     */
    private void updateAccountingLineData(Rental rental) {
        if(StringUtils.isNotEmpty(rental.getStockId()) && ObjectUtils.isNotNull(rental.getStock()) 
                && rental.getStock().isRental()) {
            rental.getStock().refreshReferenceObject(MMConstants.Stock.RENTAL_OBJECT);
               
            Double costTotal = rental.getStock().getRentalObject().getDailyDemurragePrice().doubleValue();
            KualiDecimal accountsTotal = new KualiDecimal(0.0);
            List<Accounts> percentageAccounts = new ArrayList<Accounts>();
            
            for(Accounts account : rental.getAccountingLines()) {            
                KualiDecimal formAmount = (account.getFormAmount() != null ? account.getFormAmount() : KualiDecimal.ZERO);
                if (MMConstants.OrderDocument.OPTION_FXD.equals(account.getAmountType())) {
                    account.setAccountFixedAmt(formAmount);
                    Double percent = account.getAccountFixedAmt().doubleValue() / costTotal;
                    account.setAccountPct(new BigDecimal(percent * 100.0));
                    account.setFormAmount(account.getAccountFixedAmt());
                }
                else {
                    account.setAccountPct(formAmount.bigDecimalValue());
                    account.setAccountFixedAmt(new KualiDecimal(costTotal
                            * (account.getAccountPct().doubleValue() * new Double(.01))));
                    account.setFormAmount(new KualiDecimal(account.getAccountPct()));
                    percentageAccounts.add(account);
                }
                accountsTotal = accountsTotal.add(account.getAccountFixedAmt());            
            }
            //Catch the remainder within the tolerance only if there are non-fixed accounts
            final double TOLERANCE = .02;
            double remainder = costTotal - accountsTotal.doubleValue();
            if(Math.abs(remainder) <= TOLERANCE && !percentageAccounts.isEmpty()) {
                Accounts remainderCatchAccount = percentageAccounts.get(percentageAccounts.size()-1);
                remainderCatchAccount.setAccountFixedAmt(remainderCatchAccount.getAccountFixedAmt().add(new KualiDecimal(remainder)));
            }
        }
    }
    
    /**
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#doRouteStatusChange(org.kuali.rice.kns.bo.DocumentHeader)
     */
    @Override
    public void doRouteStatusChange(DocumentHeader documentHeader) {
        super.doRouteStatusChange(documentHeader);
    }

  
    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getDocumentNumber()
     */
    public String getDocumentNumber() {
        return this.documentNumber;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getDocumentTypeCode()
     */
    public String getDocumentTypeCode() {
        return KNSServiceLocator.getMaintenanceDocumentDictionaryService().getDocumentTypeName(Rental.class);
    }
}