package org.kuali.ext.mm.document.authorization;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationControllerBase;
import org.kuali.rice.krad.document.Document;

import java.util.Set;


	public class StockCostPresentationController extends MaintenanceDocumentPresentationControllerBase {

	/*
	 * Method makes the field as readOnly.
	 */
	@Override
    public Set<String> getConditionallyReadOnlyPropertyNames(MaintenanceDocument document){
		 Set<String> readOnlyFields =
			 super.getConditionallyHiddenPropertyNames(document.getNewMaintainableObject().getBusinessObject());
		 readOnlyFields.add(MMConstants.Stock.STOCK_ID);
		 return readOnlyFields;
	 }


    @Override
    public boolean canSave(Document document){
         return false;
    }
}