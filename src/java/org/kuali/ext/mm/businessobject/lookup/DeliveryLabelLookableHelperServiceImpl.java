/**
 * 
 */
package org.kuali.ext.mm.businessobject.lookup;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Delivery;
import org.kuali.ext.mm.businessobject.DeliveryLine;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.DeliveryLabelDocument;
import org.kuali.ext.mm.document.DeliveryLabelDocumentLines;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

import java.util.*;


/**
 * @author rshrivas
 *
 */
public class DeliveryLabelLookableHelperServiceImpl  extends KualiLookupableHelperServiceImpl{
   
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
   
    public HtmlData getReturnUrl(BusinessObject businessObject, LookupForm lookupForm,
            List returnKeys, BusinessObjectRestrictions businessObjectRestrictions) {

        String nameSpaceCode = KRADConstants.KUALI_RICE_SYSTEM_NAMESPACE;
        Map<String, String> permissionDetails = new HashMap<String, String>();
        permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, "DMDT");
        
        BusinessObjectService bOService = SpringContext.getBean(BusinessObjectService.class);
        
        String conversionField = lookupForm.getConversionFields();
        if (StringUtils.isNotBlank(conversionField)
                && conversionField.contains("documentNumber:packListDocNbr")) {
            if (KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(
							GlobalVariables.getUserSession().getPrincipalId(),
							nameSpaceCode, KewApiConstants.INITIATE_PERMISSION,
							permissionDetails, null)) {
                
                DeliveryLabelDocument dLD = (DeliveryLabelDocument) businessObject;
                String documentNumber = dLD.getDocumentNumber();
                HashMap<String, String> primaryKeys = new HashMap<String, String>();
                primaryKeys.put("packListDocNbr", documentNumber);
                List<DeliveryLabelDocumentLines> deliveryLinesList = 
                    (List<DeliveryLabelDocumentLines>) bOService.findMatching(DeliveryLabelDocumentLines.class, primaryKeys);
                
                boolean wasEachItemDelivered = findIfEveryItemWasDeliverd(deliveryLinesList, bOService);
                boolean wasDeliveryJustInitiated = findIfDeliveryWasJustInitated(bOService);                             
                
                HtmlData returnUrl;                               
                
                if(wasEachItemDelivered){
                    returnUrl = getEmptyAnchorHtmlData();                    
                }else{
                    Properties parameters = getParameters(businessObject, lookupForm
                            .getFieldConversions(), lookupForm.getLookupableImplServiceName(), returnKeys);
                    parameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER,
                            MMConstants.MAINTENANCE_NEWWITHEXISTING_ACTION);
                    parameters.put(MMConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, Delivery.class.getName());
                    parameters.put(MMConstants.OVERRIDE_KEYS, "packListDocNbr");
                    parameters.put(MMConstants.REFRESH_CALLER, "documentNumber"
                            + "::" + documentNumber);
                    setBackLocation(MMConstants.MAINTENANCE_ACTION);
                    
                    returnUrl = getReturnAnchorHtmlData(businessObject, parameters, lookupForm, returnKeys,
                            businessObjectRestrictions);
                }
                
                if(wasDeliveryJustInitiated){
                    return getEmptyAnchorHtmlData();
                }
                
                return returnUrl;
            }
            return getEmptyAnchorHtmlData();
        }
        return super.getReturnUrl(businessObject, lookupForm, returnKeys,
                businessObjectRestrictions);
    }
    
    private boolean findIfEveryItemWasDeliverd(List<DeliveryLabelDocumentLines> deliveryLinesList, BusinessObjectService bOService){
        int trueCount = 0;
        
        for (int i = 0; i < deliveryLinesList.size(); i++) {            
            DeliveryLabelDocumentLines dLLine = deliveryLinesList.get(i);
            String packListLineId = dLLine.getPackListLineId();
        
            HashMap<String, String> fieldValues = new HashMap<String, String>();
            fieldValues.put("packListLineId", packListLineId);
            List<DeliveryLine> deliveryLines = (List<DeliveryLine>) bOService.findMatching(DeliveryLine.class, fieldValues);
        
            for (Iterator iterator = deliveryLines.iterator(); iterator.hasNext();) {
                DeliveryLine deliveryLine = (DeliveryLine) iterator.next();
                if("DLVD".equalsIgnoreCase(deliveryLine.getDeliveryReasonCode())){
                    trueCount++;
                }
            }                      
        }              
        
        if(trueCount != 0 && trueCount == deliveryLinesList.size()){
            return true;
        }
        
        return false;
    }
    
    private boolean findIfDeliveryWasJustInitated(BusinessObjectService bOService){
        int trueCount = 0;
        
        HashMap<String, Integer> fieldValues = new HashMap<String, Integer>();
        fieldValues.put("versionNumber", 1);
        List<DeliveryLine> deliveryLines = (List<DeliveryLine>) bOService.findMatching(DeliveryLine.class, fieldValues);
        
        for (Iterator iterator = deliveryLines.iterator(); iterator.hasNext();) {
            DeliveryLine deliveryLine = (DeliveryLine) iterator.next();
            if("DEST".equalsIgnoreCase(deliveryLine.getDeliveryReasonCode())){
               trueCount++;
            }
        }     
        
        if(trueCount != 0 && trueCount == deliveryLines.size()){
            return true;
        } 
        return false;             
    }
}
