package org.kuali.ext.mm.document.validation.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kuali.ext.mm.businessobject.Zone;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;


public class ZoneRule extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = super.processCustomRouteDocumentBusinessRules(document);

        Zone newVal = (Zone) document.getNewMaintainableObject().getBusinessObject();
        Zone oldVal = (Zone) document.getOldMaintainableObject().getBusinessObject();

        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.Zone.ZONE_CD, newVal.getZoneCd());
        fieldValues.put(MMConstants.Zone.WAREHOUSE_CD, newVal.getWarehouseCd());
        Collection zoneResults = KNSServiceLocator.getBusinessObjectService().findMatching(
                Zone.class, fieldValues);
        if (document.isNew() && !zoneResults.isEmpty()) {
            GlobalVariables.getMessageMap().putError(
                    KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Zone.ZONE_CD,
                    MMKeyConstants.Zone.ERROR_ZONE_EXISTS, newVal.getWarehouseCd(),
                    newVal.getZoneCd());
            valid = false;
        }

        if (document.isEdit()
                && (!newVal.getZoneCd().equalsIgnoreCase(oldVal.getZoneCd()) || !newVal
                        .getWarehouseCd().equalsIgnoreCase(oldVal.getWarehouseCd()))
                && !zoneResults.isEmpty()) {
            GlobalVariables.getMessageMap().putError(
                    KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.Zone.ZONE_CD,
                    MMKeyConstants.Zone.ERROR_ZONE_EXISTS, newVal.getWarehouseCd(),
                    newVal.getZoneCd());
            valid = false;
        }

        return valid;
    }
}
