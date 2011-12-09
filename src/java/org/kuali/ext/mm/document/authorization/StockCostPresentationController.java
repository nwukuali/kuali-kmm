package org.kuali.ext.mm.document.authorization;

import java.util.Set;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationControllerBase;


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
    protected boolean canSave(Document document){
         return false;
    }
}