/**
 * 
 */
package org.kuali.ext.mm.businessobject.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.DeliveryLabelDocumentLines;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.ObjectUtils;


/**
 * @author sravani
 *
 */
public class PickListLineLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl{
   
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
       
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        List<PickListLine> results = (List<PickListLine>) super.getSearchResults(fieldValues);
        List<PickListLine> searchResults = new ArrayList<PickListLine>();
        for (int i = 0; i< results.size(); i++) {
            PickListLine pickListLine = results.get(i);
            String pickListLineId = pickListLine.getPickListLineId();
            HashMap<String, String> fieldVal = new HashMap<String, String>();
            fieldVal.put("pickListLineId", pickListLineId);
            DeliveryLabelDocumentLines dLine =  (DeliveryLabelDocumentLines) bOS.findByPrimaryKey(DeliveryLabelDocumentLines.class, fieldVal);
            if(ObjectUtils.isNull(dLine)){
                searchResults.add(pickListLine);                               
            }            
        }        
        return searchResults;
    }
    
}
