package org.kuali.ext.mm.document.validation.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kuali.ext.mm.businessobject.Bin;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;


public class BinRule extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = super.processCustomRouteDocumentBusinessRules(document);
        
        if(document.isNew()) {
            Bin bin = (Bin)document.getNewMaintainableObject().getBusinessObject();
            
            Map<String, Object> fieldValues = new HashMap<String, Object>();
            fieldValues.put(MMConstants.Bin.ZONE_ID, bin.getZoneId());
            fieldValues.put(MMConstants.Bin.BIN_NBR, bin.getBinNbr());
            fieldValues.put(MMConstants.Bin.SHELF_ID, bin.getShelfId());
            fieldValues.put(MMConstants.Bin.SHELF_ID_NBR, bin.getShelfIdNbr());
            Collection binResults = KNSServiceLocator.getBusinessObjectService().findMatching(Bin.class, fieldValues);
            if(!binResults.isEmpty()) {
                GlobalVariables.getMessageMap().putError(
                        KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Bin.SHELF_ID_NBR,
                        MMKeyConstants.Bin.ERROR_BIN_LOCATION_EXISTS,
                        bin.getZone().getWarehouseCd(), bin.getZone().getZoneCd(), bin.getBinNbr(), bin.getShelfId(), bin.getShelfIdNbr());
                valid = false;
            }
        }
        
        return valid;
    }
}
