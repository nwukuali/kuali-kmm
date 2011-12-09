/**
 * 
 */
package org.kuali.ext.mm.document.validation.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.RouteMap;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.DeliveryLabelDocument;
import org.kuali.ext.mm.document.DeliveryLabelDocumentLines;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rules.DocumentRuleBase;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.DocumentService;
import org.kuali.rice.kns.util.GlobalVariables;


/**
 * @author rshrivas
 *
 */
public class DeliveryLabelDocumentRule  extends DocumentRuleBase {
    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean processCustomSaveDocumentBusinessRules(Document document) {
        boolean error = true;        
        DeliveryLabelDocument dLDocument = (DeliveryLabelDocument) document;       
                   
        List<PickListLine> pLLines = dLDocument.getPickListLines(); 
        if(pLLines.isEmpty()) {
           GlobalVariables.getMessageMap().putError(MMConstants.PickListDocument.PICK_LIST_LINE_ERROR, MMKeyConstants.ERROR_PICKLIST_EMPTY_FOR_DELIVERY_LABEL);
           error = false;
        }else{            
            String nbrOfCartons = dLDocument.getNbrOfCartons();
            if(StringUtils.isBlank(nbrOfCartons)){
                GlobalVariables.getMessageMap().putErrorForSectionId(MMConstants.PickListDocument.PICK_LIST_LINE_ERROR, MMKeyConstants.DeliveryLabelDocument.NBR_OF_CARTONS_ERROR);
                error = false;
            }else{
                char[] ch = nbrOfCartons.toCharArray();
                for( int i=0 ; i<nbrOfCartons.length() ; i++){
                    if(!Character.isDigit(ch[i])){
                        GlobalVariables.getMessageMap().putErrorForSectionId(MMConstants.PickListDocument.PICK_LIST_LINE_ERROR, MMKeyConstants.DeliveryLabelDocument.NBR_OF_CARTONS_ERROR);
                        error = false;
                    }                
                }
            }            
            for (Iterator iterator = pLLines.iterator(); iterator.hasNext();) {
                
                PickListLine pickListLine = (PickListLine) iterator.next();
                String pickListLineId = pickListLine.getPickListLineId();
                BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
                
                HashMap<String, String> fieldValues = new HashMap<String, String>();
                fieldValues.put("pickListLineId", pickListLineId);
                DeliveryLabelDocumentLines dLLine = (DeliveryLabelDocumentLines) bOS.findByPrimaryKey(DeliveryLabelDocumentLines.class, fieldValues);             
                
                if(dLLine!=null){
                    GlobalVariables.getMessageMap().putError(MMConstants.PickListDocument.PICK_LIST_LINE_ERROR, MMKeyConstants.ERROR_PICKLIST_DELIVERY_LABEL_ALREADY_PRINTED);
                    error = false;
                }
                
                if(!("PCKD".equalsIgnoreCase(pickListLine.getPickStatusCodeCd()) || 
                        "PBCK".equalsIgnoreCase(pickListLine.getPickStatusCodeCd()))){
                    GlobalVariables.getMessageMap().putError(MMConstants.PickListDocument.PICK_LIST_LINE_ERROR, MMKeyConstants.ERROR_PICKLIST_NOT_VERIFIED);
                    error = false;
                }else{
                    // Does the Route Code information exist for this line ... ?
                    boolean doesRouteCodeInfoExist = doesRouteCodeInformationExist(pickListLine.getOrderDetailId());
                    if(!doesRouteCodeInfoExist)
                    {
                        GlobalVariables.getMessageMap().putError(MMConstants.PickListDocument.PICK_LIST_LINE_ERROR, MMKeyConstants.ERROR_PICKLIST_ADDRESS_NOT_ON_ROUTE_CODE);
                        error = false;
                    }
                }
                
                if(null != pickListLine.getBackOrderQty()){
                    int backOrderedQty = pickListLine.getBackOrderQty();
                    if(backOrderedQty > pickListLine.getStockQty()){
                        GlobalVariables.getMessageMap().putError(MMConstants.PickListDocument.PICK_LIST_LINE_ERROR, MMKeyConstants.ERROR_PICKLIST_BACKORDERED);
                        error = false;
                    }
                }
            }
        }

        return error;
    }
    
    private boolean doesRouteCodeInformationExist(Integer orderDetailId){
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        OrderDetail oDetail = bOS.findBySinglePrimaryKey(OrderDetail.class, orderDetailId);
        if(oDetail != null){
            String orderDocNbr = oDetail.getOrderDocumentNbr();
            DocumentService dOS = SpringContext.getBean(DocumentService.class);
                OrderDocument oDocument;
                try {
                    oDocument = (OrderDocument) dOS.getByDocumentHeaderId(orderDocNbr);
                }
                catch (WorkflowException e) {
                   throw new RuntimeException(e);
                }                
                String buildingCd = oDocument.getDeliveryBuildingCd();
                String campusCd = oDocument.getCampusCd();                
                // Find RouteCd
                HashMap<String, String> fieldValues = new HashMap<String, String>();
                fieldValues.put("deliveryBuildingCode", buildingCd);
                fieldValues.put("deliveryCampusCd", campusCd);
                List<RouteMap> routeMapList = (List<RouteMap>) bOS.findMatching(RouteMap.class, fieldValues);
                if(routeMapList.isEmpty()){
                   return false; 
                }
        }
        return true;
    }
}
