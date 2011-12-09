package org.kuali.ext.mm.document;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.ext.mm.businessobject.MMPersistableBusinessObjectBase;
import org.kuali.ext.mm.businessobject.Route;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;
import org.kuali.rice.kns.util.ObjectUtils;

public class DeliveryLabelDocumentLines extends MMPersistableBusinessObjectBase {

    /**
     *
     */
    private static final long serialVersionUID = 9169503356940184861L;
    
    @Id
    @Column(name = "PACK_LIST_LINE_ID", unique = true, nullable = false, length = 36)
    private String packListLineId;
    
    @Column(name ="NUMBER_OF_CARTONS")
    private String nbrOfCartons;  
        
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROUTE_CD", nullable = false)
    private Route route;
    
    @Column(name = "ROUTE_CD") 
    private String routeCd;
    
    @Column(name="STOP_CD")
    private String stopCd;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACK_LIST_DOC_NBR", nullable = false)
    private DeliveryLabelDocument deliveryLabelDocument;
    
    @Column(name = "PACK_LIST_DOC_NBR") 
    private String packListDocNbr;
    
    @Column(name="PACK_LIST_LINE_NBR")
    private String packListLineNbr;
    
    @Column(name="PICK_LIST_LINE_ID")
    private String pickListLineId;
    
    @Column(name="DELIVERY_CAMPUS_CD")
    private String deliveryCampusCd;
   
    @Column(name="DELIVERY_BUILDING_NM")
    private String deliveryBuildingNm;  
    
    @Column(name = "DELIVERY_BUILDING_CD", length = 4)
    private String deliveryBuildingCd;

    @Column(name = "DELIVERY_BUILDING_RM_NBR", length = 8)
    private String deliveryBuildingRmNbr;

    @Column(name = "DELIVERY_DEPARTMENT_NM", length = 45)
    private String deliveryDepartmentNm;

    @Column(name = "DELIVERY_INSTRUCTION_TXT")
    private String deliveryInstructionTxt;
    
    @Column(name = "PACK_DT")
    private Date packDate;
    
    @Column(name = "PACK_STATUS_CD")
    private String packStatusCode;
    
    @Column(name= "PACK_PRNCPL_ID")
    private String packPrincipalId;
    
    /**
     * Gets the packListLineId property
     * @return Returns the packListLineId
     */
    public String getPackListLineId() {
        return this.packListLineId;
    }

    /**
     * Sets the packListLineId property value
     * @param packListLineId The packListLineId to set
     */
    public void setPackListLineId(String packListLineId) {
        this.packListLineId = packListLineId;
    }

    /**
     * Gets the nbrOfCartons property
     * @return Returns the nbrOfCartons
     */
    public String getNbrOfCartons() {
        return this.nbrOfCartons;
    }

    /**
     * Sets the nbrOfCartons property value
     * @param nbrOfCartons The nbrOfCartons to set
     */
    public void setNbrOfCartons(String nbrOfCartons) {
        this.nbrOfCartons = nbrOfCartons;
    }

    /**
     * Gets the pickListLineId property
     * @return Returns the pickListLineId
     */
    public String getPickListLineId() {
        return this.pickListLineId;
    }

    /**
     * Sets the pickListLineId property value
     * @param pickListLineId The pickListLineId to set
     */
    public void setPickListLineId(String pickListLineId) {
        this.pickListLineId = pickListLineId;
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
     * Gets the deliveryBuildingNm property
     * @return Returns the deliveryBuildingNm
     */
    public String getDeliveryBuildingNm() {        
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext.getBean(FinancialSystemAdaptorFactory.class);
        if (financialSystemAdaptorFactory.isSystemAvailable()) { 
            FinancialBuilding building = financialSystemAdaptorFactory.getFinancialLocationService().getBuilding(deliveryCampusCd, deliveryBuildingCd);
            if(ObjectUtils.isNotNull(building)){
            deliveryBuildingNm = building.getBuildingName();
            }
        }
        return this.deliveryBuildingNm;
    }

    /**
     * Sets the deliveryBuildingNm property value
     * @param deliveryBuildingNm The deliveryBuildingNm to set
     */
    public void setDeliveryBuildingNm(String deliveryBuildingNm) {
        this.deliveryBuildingNm = deliveryBuildingNm;
    }

    /**
     * Gets the deliveryBuildingCd property
     * @return Returns the deliveryBuildingCd
     */
    public String getDeliveryBuildingCd() {
        return this.deliveryBuildingCd;
    }

    /**
     * Sets the deliveryBuildingCd property value
     * @param deliveryBuildingCd The deliveryBuildingCd to set
     */
    public void setDeliveryBuildingCd(String deliveryBuildingCd) {
        this.deliveryBuildingCd = deliveryBuildingCd;
    }

    /**
     * Gets the deliveryBuildingRmNbr property
     * @return Returns the deliveryBuildingRmNbr
     */
    public String getDeliveryBuildingRmNbr() {
        return this.deliveryBuildingRmNbr;
    }

    /**
     * Sets the deliveryBuildingRmNbr property value
     * @param deliveryBuildingRmNbr The deliveryBuildingRmNbr to set
     */
    public void setDeliveryBuildingRmNbr(String deliveryBuildingRmNbr) {
        this.deliveryBuildingRmNbr = deliveryBuildingRmNbr;
    }

    /**
     * Gets the deliveryDepartmentNm property
     * @return Returns the deliveryDepartmentNm
     */
    public String getDeliveryDepartmentNm() {
        return this.deliveryDepartmentNm;
    }

    /**
     * Sets the deliveryDepartmentNm property value
     * @param deliveryDepartmentNm The deliveryDepartmentNm to set
     */
    public void setDeliveryDepartmentNm(String deliveryDepartmentNm) {
        this.deliveryDepartmentNm = deliveryDepartmentNm;
    }

    /**
     * Gets the deliveryInstructionTxt property
     * @return Returns the deliveryInstructionTxt
     */
    public String getDeliveryInstructionTxt() {
        return this.deliveryInstructionTxt;
    }

    /**
     * Sets the deliveryInstructionTxt property value
     * @param deliveryInstructionTxt The deliveryInstructionTxt to set
     */
    public void setDeliveryInstructionTxt(String deliveryInstructionTxt) {
        this.deliveryInstructionTxt = deliveryInstructionTxt;
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
     * Gets the deliveryLabelDocument property
     * @return Returns the deliveryLabelDocument
     */
    public DeliveryLabelDocument getDeliveryLabelDocument() {
        return this.deliveryLabelDocument;
    }

    /**
     * Sets the deliveryLabelDocument property value
     * @param deliveryLabelDocument The deliveryLabelDocument to set
     */
    public void setDeliveryLabelDocument(DeliveryLabelDocument deliveryLabelDocument) {
        this.deliveryLabelDocument = deliveryLabelDocument;
    }

    /**
     * Gets the packListDocNbr property
     * @return Returns the packListDocNbr
     */
    public String getPackListDocNbr() {
        return this.packListDocNbr;
    }

    /**
     * Sets the packListDocNbr property value
     * @param packListDocNbr The packListDocNbr to set
     */
    public void setPackListDocNbr(String packListDocNbr) {
        this.packListDocNbr = packListDocNbr;
    }

    /**
     * Gets the packListLineNbr property
     * @return Returns the packListLineNbr
     */
    public String getPackListLineNbr() {
        return this.packListLineNbr;
    }

    /**
     * Sets the packListLineNbr property value
     * @param packListLineNbr The packListLineNbr to set
     */
    public void setPackListLineNbr(String packListLineNbr) {
        this.packListLineNbr = packListLineNbr;
    }

    /**
     * Gets the stopCd property
     * @return Returns the stopCd
     */
    public String getStopCd() {
        return this.stopCd;
    }

    /**
     * Sets the stopCd property value
     * @param stopCd The stopCd to set
     */
    public void setStopCd(String stopCd) {
        this.stopCd = stopCd;
    }
    

    /**
     * Gets the packDate property
     * @return Returns the packDate
     */
    public Date getPackDate() {
        return this.packDate;
    }

    /**
     * Sets the packDate property value
     * @param packDate The packDate to set
     */
    public void setPackDate(Date packDate) {
        this.packDate = packDate;
    }

    /**
     * Gets the packStatusCode property
     * @return Returns the packStatusCode
     */
    public String getPackStatusCode() {
        return this.packStatusCode;
    }

    /**
     * Sets the packStatusCode property value
     * @param packStatusCode The packStatusCode to set
     */
    public void setPackStatusCode(String packStatusCode) {
        this.packStatusCode = packStatusCode;
    }

    /**
     * Gets the packPrincipalId property
     * @return Returns the packPrincipalId
     */
    public String getPackPrincipalId() {
        return this.packPrincipalId;
    }

    /**
     * Sets the packPrincipalId property value
     * @param packPrincipalId The packPrincipalId to set
     */
    public void setPackPrincipalId(String packPrincipalId) {
        this.packPrincipalId = packPrincipalId;
    }
    
    
    
}
