package org.kuali.ext.mm.document.authorization;

import java.util.Set;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationControllerBase;


public class BinPresentationController extends MaintenanceDocumentPresentationControllerBase {
    
	@Override
    public Set<String> getConditionallyReadOnlyPropertyNames(MaintenanceDocument document){
	    Set<String> readOnlyFields = super.getConditionallyReadOnlyPropertyNames(document);
	    if(!document.isNew()) {
	        readOnlyFields.add(MMConstants.Bin.ZONE + "." + MMConstants.Zone.ZONE_CD);
	        readOnlyFields.add(MMConstants.Bin.BIN_NBR);
	        readOnlyFields.add(MMConstants.Bin.SHELF_ID);
	        readOnlyFields.add(MMConstants.Bin.SHELF_ID_NBR);
	    }
	    
	    return readOnlyFields;
	}

}