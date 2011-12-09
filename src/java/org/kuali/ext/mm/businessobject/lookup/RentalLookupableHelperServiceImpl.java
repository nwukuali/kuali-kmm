package org.kuali.ext.mm.businessobject.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;


public class RentalLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

    private static final long serialVersionUID = 1359008754058486489L;

    /**
     * @see org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl#getCustomActionUrls(org.kuali.rice.kns.bo.BusinessObject,
     *      List pkNames)
     */
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
        Rental rental = (Rental) businessObject;

        List<HtmlData> anchorHtmlDataList = new ArrayList<HtmlData>();

        anchorHtmlDataList.add(this.getUpdateUrl(rental));        

        return anchorHtmlDataList;

    }

    protected HtmlData getUpdateUrl(Rental rental) {
        Properties parameters = new Properties();
        parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, KNSConstants.MAINTENANCE_EDIT_METHOD_TO_CALL);
        parameters.put(MMConstants.Rental.RENTAL_ID, rental.getRentalId());
        parameters.put(KNSConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, rental.getClass().getName());
        parameters.put(KNSConstants.RETURN_LOCATION_PARAMETER, this.getBackLocation());

        String href = getKualiConfigurationService().getPropertyString(
                MMKeyConstants.KR_URL)
                + "/" + UrlFactory.parameterizeUrl(KNSConstants.MAINTENANCE_ACTION, parameters);
        return new AnchorHtmlData(href, KNSConstants.DOC_HANDLER_METHOD,
            MMConstants.RENTAL_ACTION_UPDATE);
    }
    
}
