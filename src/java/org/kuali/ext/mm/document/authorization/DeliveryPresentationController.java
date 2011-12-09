/**
 * 
 */
package org.kuali.ext.mm.document.authorization;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Delivery;
import org.kuali.ext.mm.businessobject.DeliveryLine;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.DeliveryLabelDocumentLines;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationControllerBase;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.workflow.service.KualiWorkflowDocument;


/**
 * @author rshrivas
 *
 */
public class DeliveryPresentationController extends MaintenanceDocumentPresentationControllerBase{
    
    protected boolean canSendAdhocRequests(Document document) {
        return false;
    }

    protected boolean canReload(Document document){
        return false;
    }
    
    protected boolean canEdit(Document document){
        boolean canEdit = false;
        KualiWorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();
        if (workflowDocument.stateIsInitiated() || workflowDocument.stateIsSaved() || workflowDocument.stateIsException()) {
            canEdit = true; 
        }        
        return canEdit;
    }
          
    @Override
    public Set<String> getConditionallyReadOnlyPropertyNames(MaintenanceDocument document){
        Set<String> readOnlyFields = super.getConditionallyReadOnlyPropertyNames(document);         
        Delivery delivery = (Delivery) document.getNewMaintainableObject().getBusinessObject();
                               
        if("edit".equalsIgnoreCase(document.getNewMaintainableObject().getMaintenanceAction())){          
            readOnlyFields.add("routeCd");                       
            List<DeliveryLine> dLines = delivery.getDeliveryLines();            
            for (int i = 0; i < dLines.size(); i++) {
                loadSummaryInfo(dLines.get(i));
               /* if("DLVD".equalsIgnoreCase(dLines.get(i).getDeliveryReasonCode())){
                     readOnlyFields.add("deliveryLines[" + i + "].deliveryReasonCode");
                     readOnlyFields.add("deliveryLines[" + i + "].departmentReceivedByName");
                     readOnlyFields.add("deliveryLines[" + i + "].driverComment");
                     readOnlyFields.add("deliveryLines[" + i + "].deliveryDate");                                  
                } */              
            }
        }                
        return readOnlyFields;
    }
    
    private void loadSummaryInfo(DeliveryLine deliveryLine){
        DeliveryLabelDocumentLines dL = deliveryLine.getDeliveryLabelDocumentLines();
        if(dL != null){
            String summary =   "Stop Code: " + dL.getStopCd() + " | " + 
                               "PickList #: " + dL.getPackListLineId() + " | " +           
                               "Department Name: " + dL.getDeliveryDepartmentNm() + " | " +
                               "Building Code: " + dL.getDeliveryBuildingCd() + " | " +
                               "Building Room #: " + dL.getDeliveryBuildingRmNbr();
            deliveryLine.setDeliveryInfoSummary(summary);
      }
    }
    
    @Override
    public Set<String> getConditionallyHiddenPropertyNames(BusinessObject businessObject) {
        
        BusinessObjectService bOService = SpringContext.getBean(BusinessObjectService.class);
        Set<String> hideFields = super.getConditionallyHiddenPropertyNames(businessObject);     
        MaintenanceDocument document = (MaintenanceDocument) businessObject;        
        Delivery delivery = (Delivery) document.getNewMaintainableObject().getBusinessObject(); 
        Delivery deliveryOld = (Delivery) document.getOldMaintainableObject().getBusinessObject(); 
        if("edit".equalsIgnoreCase(document.getNewMaintainableObject().getMaintenanceAction())){
            if(StringUtils.isBlank(document.getDocumentHeader().getDocumentDescription())){
                document.getDocumentHeader().setDocumentDescription("Delivery Log Number: " + document.getDocumentNumber());    
            }              
            List<DeliveryLine> dLLines = delivery.getDeliveryLines();
            sortDeliveryLabelLines(dLLines, bOService);
            for (int y = 0; y < dLLines.size(); y++) {
                DeliveryLine deliveryLine = dLLines.get(y);    
                hideFields.add("deliveryLines[" + y + "].deliveryInfoSummary");    
                DeliveryLabelDocumentLines deliveryLabeLLine = bOService.findBySinglePrimaryKey(DeliveryLabelDocumentLines.class, deliveryLine.getPackListLineId());
                deliveryLine.setDeliveryLabelDocumentLines(deliveryLabeLLine);
            }     
            
            List<DeliveryLine> dLLinesOld = deliveryOld.getDeliveryLines();
            sortDeliveryLabelLines(dLLinesOld, bOService);
            for (int i = 0; i < dLLinesOld.size(); i++) {
                DeliveryLine deliveryLine = dLLinesOld.get(i);           
                hideFields.add("deliveryLines[" + i + "].deliveryInfoSummary");       
                DeliveryLabelDocumentLines deliveryLabeLLine = bOService.findBySinglePrimaryKey(DeliveryLabelDocumentLines.class, deliveryLine.getPackListLineId());
                deliveryLine.setDeliveryLabelDocumentLines(deliveryLabeLLine);
            }     
        }
        return hideFields;
    }     
    
    private void sortDeliveryLabelLines(List<DeliveryLine> searchResults, final BusinessObjectService bOS) {       
        Collections.sort(searchResults, new Comparator<DeliveryLine>() {
            /**
             * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
             */
            public int compare(DeliveryLine d1, DeliveryLine d2) {
                String packListLineId1 = d1.getPackListLineId();
                String packListLineId2 = d2.getPackListLineId();
                
                DeliveryLabelDocumentLines pickListLine1 = bOS.findBySinglePrimaryKey(DeliveryLabelDocumentLines.class, packListLineId1);
                DeliveryLabelDocumentLines pickListLine2 = bOS.findBySinglePrimaryKey(DeliveryLabelDocumentLines.class, packListLineId2);
                
                if (Integer.parseInt(pickListLine1.getStopCd()) == Integer.parseInt(pickListLine2.getStopCd()))
                    return 0;
                else if (Integer.parseInt(pickListLine2.getStopCd()) > Integer.parseInt(pickListLine1.getStopCd()))
                    return -1;
                else
                    return 1;
            }
        });
    }
}
