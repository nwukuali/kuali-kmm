/**
 * 
 */
package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Delivery;
import org.kuali.ext.mm.businessobject.DeliveryLine;
import org.kuali.ext.mm.businessobject.DeliveryReason;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.ArrayList;
import java.util.List;


/**
 * @author rshrivas
 *
 */
public class DeliveryBusinessRule extends MaintenanceDocumentRuleBase{
    
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {

        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        Delivery delivery = (Delivery) document.getNewMaintainableObject().getBusinessObject();
        String routeCd = delivery.getRouteCd();       
        if(StringUtils.isBlank(routeCd)){
            GlobalVariables.getMessageMap().putError(MMConstants.DeliveryLogDocument.DELIVERY_MAIN_SECTION, MMKeyConstants.ERROR_ROUTE_CODE_REQUIRED_DELIVERY_LABEL);
        }
        
        List<DeliveryLine> dLines = delivery.getDeliveryLines();        
        
        if("edit".equalsIgnoreCase(document.getNewMaintainableObject().getMaintenanceAction())){
            
            ArrayList<String> deliveryCodeList = new ArrayList<String>();
            for (int a = 0; a < dLines.size(); a++){
                DeliveryLine deLine = dLines.get(a);
                String deliveryRCode = deLine.getDeliveryReasonCode();
                if("DEST".equalsIgnoreCase(deliveryRCode)){
                    deliveryCodeList.add(deliveryRCode);
                }
            }
            
            if(deliveryCodeList.size() == dLines.size()){
                GlobalVariables.getMessageMap().putError(MMConstants.DeliveryLogDocument.DELIVERY_MAIN_SECTION, MMKeyConstants.ERROR_DELIVERY_REASON_NOT_CHANGED);
            }else{            
                for (int i = 0; i < dLines.size(); i ++){
                    DeliveryLine deliveryLine = dLines.get(i);
                    
                    if(StringUtils.isBlank(deliveryLine.getDeliveryReasonCode())) {
                        GlobalVariables.getMessageMap().putError("document.newMaintainableObject.deliveryLines[" + i + "].deliveryReasonCode", MMKeyConstants.ERROR_DELIVERY_REASON_NOT_SPECIFIED);
                    }else{
                        
                        DeliveryReason dreason = bOS.findBySinglePrimaryKey(DeliveryReason.class, deliveryLine.getDeliveryReasonCode());
                        if(null == dreason){
                            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.deliveryLines[" + i + "].deliveryReasonCode", MMKeyConstants.ERROR_DELIVERY_REASON_INVALID);
                        }else{
                            if(!"DEST".equalsIgnoreCase(deliveryLine.getDeliveryReasonCode())){                                                
                               if(null == deliveryLine.getDeliveryDate()) {
                                   GlobalVariables.getMessageMap().putError("document.newMaintainableObject.deliveryLines[" + i + "].deliveryDate", MMKeyConstants.ERROR_DELIVERY_DATE_NOT_SPECIFIED);   
                               } 
                            }                            
                        }                    
                    }                                                               
                } 
            }
        }
        
        return true;
    }
}
