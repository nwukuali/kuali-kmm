/**
 * 
 */
package org.kuali.ext.mm.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Route;
import org.kuali.ext.mm.businessobject.RouteMap;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.service.FinancialLocationService;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;


/**
 * @author rshrivas
 *
 */
public class RouteMaintainableImpl extends KualiMaintainableImpl {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public void refresh(String refreshCaller, Map fieldValues, MaintenanceDocument document) {
    super.refresh(refreshCaller, fieldValues, document);
   
    Route route = (Route) getBusinessObject();
    if (route != null && !route.getRouteMaps().isEmpty()) {
        FinancialSystemAdaptorFactory factory = SpringContext.getBean(FinancialSystemAdaptorFactory.class);
            if (factory.isSystemAvailable()) {
                FinancialLocationService fLocationService = factory.getFinancialLocationService();
                List<RouteMap> routeMapList = route.getRouteMaps();
                for (Iterator iterator = routeMapList.iterator(); iterator.hasNext();) {
                    RouteMap routeMap = (RouteMap) iterator.next();                               
                    String campusCd = routeMap.getDeliveryCampusCd();
                    String buildingCd = routeMap.getDeliveryBuildingCode();
                    if(!StringUtils.isBlank(buildingCd) && !StringUtils.isBlank(campusCd)){
                        FinancialBuilding fBuilding = fLocationService.getBuilding(campusCd, buildingCd);
                        if (fBuilding!=null){
                            routeMap.setDeliveryBuildingName(fBuilding.getBuildingName());
                        }
                    }                                
                }
            }
        }  
    }    
}
