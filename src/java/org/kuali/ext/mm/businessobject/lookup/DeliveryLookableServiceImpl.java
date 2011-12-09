/**
 * 
 */
package org.kuali.ext.mm.businessobject.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.businessobject.Delivery;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.KNSConstants;


/**
 * @author rshrivas
 */
@SuppressWarnings("serial")
public class DeliveryLookableServiceImpl extends KualiLookupableHelperServiceImpl {

    /**
     * Child classes should override this method if they want to return some other action urls.
     * 
     * @returns This default implementation returns links to edit and copy maintenance action for the current maintenance record if
     *          the business object class has an associated maintenance document. Also checks value of allowsNewOrCopy in
     *          maintenance document xml before rendering the copy link.
     * @see org.kuali.rice.kns.lookup.LookupableHelperService#getCustomActionUrls(org.kuali.rice.kns.bo.BusinessObject,
     *      java.util.List, java.util.List pkNames)
     */
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);

        List<HtmlData> htmlDataList = new ArrayList<HtmlData>();
        Delivery delivery = (Delivery) businessObject;
        String routeCd = delivery.getRouteCd();
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("routeCd", routeCd);
        List<Delivery> deliveryObjectList = (List<Delivery>) bOS.findMatching(Delivery.class,
                fieldValues);

        int fDocNbr = 0;
        for (Iterator iterator = deliveryObjectList.iterator(); iterator.hasNext();) {
            Delivery delivery2 = (Delivery) iterator.next();
            int fDocNbr2 = Integer.parseInt(delivery2.getDocumentNumber());
            if (fDocNbr2 > fDocNbr) {
                fDocNbr = fDocNbr2;
            }
        }

        if (fDocNbr == Integer.parseInt(delivery.getDocumentNumber())) {
            if (allowsMaintenanceEditAction(businessObject)) {
                htmlDataList.add(getUrlData(businessObject,
                        KNSConstants.MAINTENANCE_EDIT_METHOD_TO_CALL, pkNames));
            }
        }
        return htmlDataList;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected AnchorHtmlData getUrlData(BusinessObject businessObject, String methodToCall,
            String displayText, List pkNames) {
        Delivery delivery = (Delivery) businessObject;
        AnchorHtmlData anchorHtmlData = null;
        if ("edit".equals(methodToCall)) {
            String href = "maintenance.do?documentNumber="
                    + delivery.getDocumentNumber()
                    + "&businessObjectClassName=org.kuali.ext.mm.businessobject.Delivery&methodToCall=edit";
            anchorHtmlData = new AnchorHtmlData(href, methodToCall, "Update Driver Log");
        }

        return anchorHtmlData;
    }

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        List<Delivery> results = (List<Delivery>) super.getSearchResults(fieldValues);
        sortResults(results);
        return results;
    }

    private void sortResults(List<Delivery> searchResults) {
        Collections.sort(searchResults, new Comparator<Delivery>() {
            /**
             * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
             */
            public int compare(Delivery d1, Delivery d2) {
                if (Integer.parseInt(d1.getDocumentNumber()) == Integer.parseInt(d2
                        .getDocumentNumber()))
                    return 0;
                else if (Integer.parseInt(d1.getDocumentNumber()) > Integer.parseInt(d2
                        .getDocumentNumber()))
                    return -1;
                else
                    return 1;
            }
        });
    }
}
