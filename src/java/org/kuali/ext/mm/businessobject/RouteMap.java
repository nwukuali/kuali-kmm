package org.kuali.ext.mm.businessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.service.FinancialLocationService;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;


/**
 * @author rshrivas
 *
 */
@Entity
@Table(name = "MM_ROUTE_MAP_T")
public class RouteMap extends MMPersistableBusinessObjectBase{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ROUTE_MAP_ID", unique = true, nullable = false, length = 18)
    private String routeMapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROUTE_CD", nullable = false)
    private Route route;
    
    @Column(name = "ROUTE_CD")
    private String routeCd;
   
    @Column(name = "DELIVERY_CAMPUS_CD")
    private String deliveryCampusCd;
    
    @Column(name = "DELIVERY_BUILDING_CD")
    private String deliveryBuildingCode;
    
    @Column(name = "STOP_SEQUENCE")
    private String stopSequnce;
    
    private String deliveryBuildingName;
    
    /**
     * Gets the deliveryBuildingName property
     * @return Returns the deliveryBuildingName
     */
    public String getDeliveryBuildingName() {
        
        FinancialSystemAdaptorFactory factory = SpringContext.getBean(FinancialSystemAdaptorFactory.class);
        if (factory.isSystemAvailable()) {
            FinancialLocationService fLocationService = factory.getFinancialLocationService();
            if(!StringUtils.isEmpty(this.deliveryBuildingCode) && !StringUtils.isEmpty(this.deliveryCampusCd)){
                FinancialBuilding fBuilding = fLocationService.getBuilding(this.deliveryCampusCd, this.deliveryBuildingCode);
                if (fBuilding!=null){
                    this.deliveryBuildingName = fBuilding.getBuildingName();
                }
            }
        }
        return this.deliveryBuildingName;
    }


    /**
     * Sets the deliveryBuildingName property value
     * @param deliveryBuildingName The deliveryBuildingName to set
     */
    public void setDeliveryBuildingName(String deliveryBuildingName) {
        this.deliveryBuildingName = deliveryBuildingName;
    }


    private FinancialBuilding financialBuilding;           
    
    /**
     * Gets the financialBuilding property
     * @return Returns the financialBuilding
     */
    public FinancialBuilding getFinancialBuilding() {
        return this.financialBuilding;
    }


    /**
     * Sets the financialBuilding property value
     * @param financialBuilding The financialBuilding to set
     */
    public void setFinancialBuilding(FinancialBuilding financialBuilding) {
        this.financialBuilding = financialBuilding;
    }

    /**
     * Gets the routeMapId property
     * @return Returns the routeMapId
     */
    public String getRouteMapId() {
        return this.routeMapId;
    }


    /**
     * Sets the routeMapId property value
     * @param routeMapId The routeMapId to set
     */
    public void setRouteMapId(String routeMapId) {
        this.routeMapId = routeMapId;
    }


    /**
     * Gets the route property
     * @return Returns the route
     */
    public Route getRoute() {
        return this.route;
    }


    /**
     * Sets the route property value
     * @param route The route to set
     */
    public void setRoute(Route route) {
        this.route = route;
    }


    /**
     * Gets the routeCd property
     * @return Returns the routeCd
     */
    public String getRouteCd() {
        return this.routeCd;
    }


    /**
     * Sets the routeCd property value
     * @param routeCd The routeCd to set
     */
    public void setRouteCd(String routeCd) {
        this.routeCd = routeCd;
    }


    /**
     * Gets the deliveryCampusCd property
     * @return Returns the deliveryCampusCd
     */
    public String getDeliveryCampusCd() {
        return this.deliveryCampusCd;
    }


    /**
     * Sets the deliveryCampusCd property value
     * @param deliveryCampusCd The deliveryCampusCd to set
     */
    public void setDeliveryCampusCd(String deliveryCampusCd) {
        this.deliveryCampusCd = deliveryCampusCd;
    }

    /**
     * Gets the deliveryBuildingCode property
     * @return Returns the deliveryBuildingCode
     */
    public String getDeliveryBuildingCode() {
        return this.deliveryBuildingCode;
    }


    /**
     * Sets the deliveryBuildingCode property value
     * @param deliveryBuildingCode The deliveryBuildingCode to set
     */
    public void setDeliveryBuildingCode(String deliveryBuildingCode) {
        this.deliveryBuildingCode = deliveryBuildingCode;
    }


    /**
     * Gets the stopSequnce property
     * @return Returns the stopSequnce
     */
    public String getStopSequnce() {
        return this.stopSequnce;
    }


    /**
     * Sets the stopSequnce property value
     * @param stopSequnce The stopSequnce to set
     */
    public void setStopSequnce(String stopSequnce) {
        this.stopSequnce = stopSequnce;
    }
    
    

}
