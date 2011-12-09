 package org.kuali.ext.mm.document.validation.impl;

import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.RentalService;
import org.kuali.rice.core.util.RiceConstants;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.rules.PromptBeforeValidationBase;
import org.kuali.rice.kns.service.KualiConfigurationService;


public class RentalPreRules extends PromptBeforeValidationBase {

	/**
	 * @see org.kuali.rice.kns.rules.PromptBeforeValidationBase#doPrompts(org.kuali.rice.kns.document.Document)
	 */
	@Override
	public boolean doPrompts(Document document) {
        Rental newRental = (Rental)((MaintenanceDocument)document).getNewMaintainableObject().getBusinessObject();
		
        if(((MaintenanceDocument)document).isEdit()) {
    		if(isModifyingHistoricalRecord(newRental)) {
    			if (!isOkModifyingHistoricalRecord()) {
                    event.setActionForwardName(RiceConstants.MAPPING_BASIC);
                    return false;
                }
    		}
        }
		
		return true;
	}

	
	/**
     * @param newRental
     * @return
     */
    private boolean isModifyingHistoricalRecord(Rental newRental) {
        RentalService rentalService = MMServiceLocator.getRentalService();
        
        Rental currentRental = rentalService.getCurrentRentalItem(newRental.getRentalTypeCode(), newRental.getRentalSerialNumber(), true);
        
        if(currentRental.getRentalId().equals(newRental.getRentalId()))
            return false;
        
        return true;
    }


    /**
	 * @return True if the user wants to proceed with modifying a historical record
	 */
	private boolean isOkModifyingHistoricalRecord() {
        KualiConfigurationService kualiConfiguration = SpringContext.getBean(KualiConfigurationService.class);
        String warningMessage = kualiConfiguration.getPropertyString(MMKeyConstants.Rental.HISTORICAL_MODIFICATION_QUESTION);
        return super.askOrAnalyzeYesNoQuestion(MMKeyConstants.Rental.HISTORICAL_MODIFICATION_QUESTION, warningMessage);
	}

}
