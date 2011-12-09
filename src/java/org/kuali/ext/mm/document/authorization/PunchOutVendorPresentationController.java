package org.kuali.ext.mm.document.authorization;

import java.util.HashSet;
import java.util.Set;

import org.kuali.ext.mm.businessobject.PunchOutVendor;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationControllerBase;
import org.kuali.rice.kns.util.ObjectUtils;


public class PunchOutVendorPresentationController extends MaintenanceDocumentPresentationControllerBase {
    
    @Override
    public Set<String> getConditionallyRequiredPropertyNames(MaintenanceDocument document) {
        HashSet<String> requiredProperties =  new HashSet<String>();
        PunchOutVendor poVendor = (PunchOutVendor)document.getDocumentBusinessObject();
        
        
        if(ObjectUtils.isNotNull(poVendor.getCatalog()) 
                && MMConstants.CatalogType.PUNCHOUT.equals(poVendor.getCatalog().getCatalogTypeCd())) {
            requiredProperties.add(MMConstants.PunchOutVendor.PUNCH_OUT_URL);          
        }
        
        
        return requiredProperties;
    }
    
    

}