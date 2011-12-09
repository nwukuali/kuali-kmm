package org.kuali.ext.mm.document.validation.impl;

import org.kuali.ext.mm.businessobject.BackOrder;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.ObjectUtils;


public class BackOrderRule extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = super.processCustomRouteDocumentBusinessRules(document);
       
        BackOrder backOrder = (BackOrder)document.getNewMaintainableObject().getBusinessObject();
        BackOrder oldBackOrder = (BackOrder)document.getOldMaintainableObject().getBusinessObject();
        
        if(backOrder.getBackOrderStockQty() > oldBackOrder.getBackOrderStockQty()) {
            GlobalVariables.getMessageMap().putError(
                    KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.BackOrder.BACK_ORDER_QUANTITY,
                    MMKeyConstants.BackOrder.ERROR_NEW_QUANTITY_GREATER_THAN_OLD,
                    String.valueOf(backOrder.getBackOrderStockQty()), 
                    String.valueOf(oldBackOrder.getBackOrderStockQty()));
            valid = false;
        }
        
        if(ObjectUtils.isNotNull(backOrder.getToPickListLines())) {
            for(PickListLine line : backOrder.getToPickListLines()) {
                if(!MMConstants.PickStatusCode.PICK_STATUS_INIT.equals(line.getPickStatusCodeCd())) {
                    GlobalVariables.getMessageMap().putError(
                            KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.BackOrder.BACK_ORDER_QUANTITY,
                            MMKeyConstants.BackOrder.ERROR_CANNOT_CANCEL_OR_MODIFY);
                    valid = false;
                    break;
                }
            }
        }
        
        return valid;
    }
}
