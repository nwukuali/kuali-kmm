package org.kuali.ext.mm.document.authorization;

import java.util.Set;

import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.MaintenanceDocumentBase;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationControllerBase;


public class RentalPresentationController extends MaintenanceDocumentPresentationControllerBase {


    @Override
    protected boolean canSave(Document document) {
        return false;
    }

    @Override
    public Set<String> getConditionallyReadOnlyPropertyNames(MaintenanceDocument document) {
        Set<String> readOnlyFields = super.getConditionallyReadOnlyPropertyNames(document);
        Rental oldRental = (Rental)document.getOldMaintainableObject().getBusinessObject();
        
        if(document.isNew()) {
            readOnlyFields.add(MMConstants.Rental.ISSUE_DATE);
            readOnlyFields.add(MMConstants.Rental.LAST_CHARGE_DATE);
        }
        else if(MMConstants.Rental.RENTAL_STATUS_AVAILABLE.equals(oldRental.getRentalStatusCode())) {
            readOnlyFields.add(MMConstants.Rental.STOCK + "." + MMConstants.Stock.STOCK_DISTRIBUTOR_NBR);
            readOnlyFields.add(MMConstants.Rental.RENTAL_SERIAL_NUMBER);
            readOnlyFields.add(MMConstants.Rental.ISSUE_DATE);
            readOnlyFields.add(MMConstants.Rental.LAST_CHARGE_DATE);
        }
        
        return readOnlyFields;
    }

    @Override
    public Set<String> getConditionallyHiddenPropertyNames(BusinessObject businessObject) {
        Set<String> hideFields = super.getConditionallyHiddenPropertyNames(businessObject);
        return hideFields;
    }

    @Override
    public Set<String> getConditionallyHiddenSectionIds(BusinessObject businessObject) {
        Rental oldRental = (Rental)((MaintenanceDocumentBase)businessObject).getOldMaintainableObject().getBusinessObject();
        Set<String> hiddenSections = super.getConditionallyHiddenSectionIds(businessObject);
        
        if(oldRental.getPickListLineId() == null 
                || MMConstants.Rental.RENTAL_STATUS_AVAILABLE.equals(oldRental.getRentalStatusCode())) {
            hiddenSections.add(MMConstants.Rental.ACCOUNTING_LINES);
        }
        return hiddenSections;
    }
}