/**
 * 
 */
package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Route;
import org.kuali.ext.mm.businessobject.RouteMap;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author rshrivas
 *
 */
public class RouteBusinessRule extends MaintenanceDocumentRuleBase{
    
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = super.processCustomRouteDocumentBusinessRules(document);
        Maintainable maintainable = document.getNewMaintainableObject();
        Route route = (Route) maintainable.getBusinessObject();
        
        ArrayList<Integer> stopSequenceArray = new ArrayList();        
        if (route != null && !route.getRouteMaps().isEmpty()) {
            List<RouteMap> routeMapList = route.getRouteMaps();                 
            for (int i = 0; i < routeMapList.size(); i++){      
                
                RouteMap routeMap = routeMapList.get(i);                                        
                String stopSequence = routeMap.getStopSequnce();                
                if(StringUtils.isBlank(stopSequence)){
                    GlobalVariables.getMessageMap().putError(
                            "document.newMaintainableObject.routeMaps[" + i + "].stopSequnce",
                            MMKeyConstants.RouteMap.STOP_SEQUENCE_NOT_MAINTAINED);
                    valid = false;
                }else{
                    boolean isStopSeqOkay = true;
                    char[] ch = stopSequence.toCharArray();
                    
                    for( int a=0 ; a<stopSequence.length() ; a++){
                        if(!Character.isDigit(ch[a])){
                            GlobalVariables.getMessageMap().putError(
                                    "document.newMaintainableObject.routeMaps[" + i + "].stopSequnce",
                                    MMKeyConstants.RouteMap.STOP_SEQUENCE_NOT_MAINTAINED);
                            valid = false;
                            isStopSeqOkay = false;
                        }                
                    }
                    
                    if(isStopSeqOkay){
                        stopSequenceArray.add(Integer.parseInt(routeMap.getStopSequnce()));      
                    }
                }                                   
                           
            }
            Collections.sort(stopSequenceArray);
            
            for (Integer sequence = 1; sequence < stopSequenceArray.size() + 1; sequence++){
                if(sequence.compareTo(stopSequenceArray.get(sequence - 1)) != 0){
                    GlobalVariables.getMessageMap().putError(
                            "document.newMaintainableObject.routeMaps[" + (sequence -1) + "].stopSequnce",
                            MMKeyConstants.RouteMap.STOP_SEQUENCE_NOT_MAINTAINED);
                    valid = false;
                }
            }
        }
        return valid;
    }
}
