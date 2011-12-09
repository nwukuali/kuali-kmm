/**
 * 
 */
package org.kuali.ext.mm.document.authorization;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Route;
import org.kuali.ext.mm.businessobject.RouteMap;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationControllerBase;


/**
 * @author rshrivas
 *
 */
public class RoutePresentationController extends MaintenanceDocumentPresentationControllerBase {

    protected boolean canSendAdhocRequests(Document document) {
        return false;
    }
    
    protected boolean canSave(Document document){
        return false;
    }
    
    protected boolean canReload(Document document){
        return false;
    }
    
    public Set<String> getConditionallyHiddenPropertyNames(BusinessObject businessObject) {                
        Set<String> hideFields = super.getConditionallyHiddenPropertyNames(businessObject);
        
        MaintenanceDocument document = (MaintenanceDocument) businessObject;        
        
        Route routeNew = (Route) document.getNewMaintainableObject().getBusinessObject();
        List<RouteMap> routeMapListNew = routeNew.getRouteMaps();
        sortRouteMapListByStopSequence(routeMapListNew);
        
        Route routeOld = (Route) document.getOldMaintainableObject().getBusinessObject();       
        List<RouteMap> routeMapListOld = routeOld.getRouteMaps();
        sortRouteMapListByStopSequence(routeMapListOld);
        
        return hideFields;
    }
    
    private void sortRouteMapListByStopSequence(List<RouteMap> routeMapList){
        Collections.sort(routeMapList, new Comparator<RouteMap>(){

        public int compare(RouteMap o1, RouteMap o2) {
            boolean isStopSeqOkay = true;
            
            if(o1 == null || o2 == null){                
                isStopSeqOkay = false;
            }else {            
                
                String stopSequence1 = o1.getStopSequnce();    
                String stopSequence2 = o2.getStopSequnce();
                
                if(!StringUtils.isBlank(stopSequence1) && !StringUtils.isBlank(stopSequence2)){
                    char[] ch1 = stopSequence1.toCharArray();
                    char[] ch2 = stopSequence2.toCharArray();
                    
                    for( int a=0 ; a < stopSequence1.length() ; a++){
                        if(!Character.isDigit(ch1[a])){
                            isStopSeqOkay = false;
                        }
                    }
                    
                    for( int a=0 ; a < stopSequence2.length() ; a++){
                        if(!Character.isDigit(ch2[a])){
                            isStopSeqOkay = false;
                        }
                    }
                }else{
                    isStopSeqOkay = false;
                }
            }
            
            if(isStopSeqOkay){
                Integer stopSeq1 = Integer.parseInt(o1.getStopSequnce());
                Integer stopSeq2 = Integer.parseInt(o2.getStopSequnce());
                return stopSeq1.compareTo(stopSeq2);
            }else
                return 0;
        }});
    }
    
}
