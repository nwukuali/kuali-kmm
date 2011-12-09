/**
 * 
 */
package org.kuali.ext.mm.businessobject.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.businessobject.DeliveryLine;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.DeliveryLabelDocumentLines;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.ObjectUtils;


/**
 * @author rshrivas
 *
 */
public class DeliveryLabelLinesLookupableServiceImpl extends KualiLookupableHelperServiceImpl{
   

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        List<DeliveryLabelDocumentLines> results = (List<DeliveryLabelDocumentLines>) super.getSearchResults(fieldValues);       
        List<DeliveryLabelDocumentLines> searchResults = new ArrayList<DeliveryLabelDocumentLines>();
        for (int i = 0; i< results.size(); i++) {
            DeliveryLabelDocumentLines deliveryLabelDocumentLine = results.get(i);
            String packListLineId = deliveryLabelDocumentLine.getPackListLineId();
            HashMap<String, String> fieldVal = new HashMap<String, String>();
            fieldVal.put("packListLineId", packListLineId);
            fieldVal.put("deliveryReasonCode", "DLVD");
            DeliveryLine dLine = (DeliveryLine) bOS.findByPrimaryKey(DeliveryLine.class, fieldVal);
            if(ObjectUtils.isNull(dLine)){
                searchResults.add(deliveryLabelDocumentLine);                               
            }            
        }        
        return searchResults;
    }
}
