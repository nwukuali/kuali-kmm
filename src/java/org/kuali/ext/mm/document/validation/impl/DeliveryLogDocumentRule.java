/**
 * 
 */
package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.DeliveryLine;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.DeliveryLabelDocumentLines;
import org.kuali.ext.mm.document.DeliveryLogDocument;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.DocumentRuleBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * @author rshrivas
 *
 */
public class DeliveryLogDocumentRule  extends DocumentRuleBase{
    @Override
    
    protected boolean processCustomRouteDocumentBusinessRules(Document document) {
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        boolean error = true;
        
        DeliveryLogDocument dLDocument = (DeliveryLogDocument) document;
        
        if(StringUtils.isEmpty(dLDocument.getRouteCd())){            
            error = false;
        }
        
        List<DeliveryLabelDocumentLines> deliveryLabelLineList = dLDocument.getDeliveryLabelLines(); 
        
        if(deliveryLabelLineList.isEmpty()){
            error = false;
        }else{
            for (Iterator iterator = deliveryLabelLineList.iterator(); iterator.hasNext();) {
                DeliveryLabelDocumentLines deliveryLabelDocumentLines = (DeliveryLabelDocumentLines) iterator.next();
                String packListLineId = deliveryLabelDocumentLines.getPackListLineId();
                HashMap<String, String> fieldValues = new HashMap<String, String>();
                fieldValues.put("deliveryReasonCode", "DLVD");
                fieldValues.put("packListLineId", packListLineId);
                List<DeliveryLine> dLineList =  (List<DeliveryLine>) bOS.findMatching(DeliveryLine.class, fieldValues);
                
                if(!dLineList.isEmpty()){
                    GlobalVariables.getMessageMap().putError(MMConstants.DeliveryLogDocument.DELIVERY_MAIN_SECTION_COLLECTION, MMKeyConstants.ERROR_DELIVERY_LABEL_LINES_NOT_VALID);
                    error = false;
                }
            }
        }
        
        return error;
    }
}
