package org.kuali.ext.mm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.util.KNSConstants;

@SuppressWarnings("serial")
public class CatalogPendingHelperServiceImpl extends
		KualiLookupableHelperServiceImpl {

	@Override
    @SuppressWarnings("unchecked")
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {

		List<HtmlData> htmlDataList = new ArrayList<HtmlData>();

		if (allowsMaintenanceEditAction(businessObject)) {
            htmlDataList.add(getUrlData(businessObject, KNSConstants.MAINTENANCE_EDIT_METHOD_TO_CALL, pkNames));
        }

		//if(catPend.isActive()){
    		htmlDataList.add(getUrlData(businessObject, "edit", "Approve Catalog", pkNames));
    		htmlDataList.add(getUrlData(businessObject, "edit", "View Summary", pkNames));
    		htmlDataList.add(getUrlData(businessObject, "edit", "Catalog Additions", pkNames));
    		htmlDataList.add(getUrlData(businessObject, "edit", "Catalog Deletions", pkNames));
    		htmlDataList.add(getUrlData(businessObject, "edit", "Price Increase", pkNames));
    		htmlDataList.add(getUrlData(businessObject, "edit", "Price Decrease", pkNames));
    		htmlDataList.add(getUrlData(businessObject, "edit", "5% or less Report", pkNames));
		//}

		return htmlDataList;
	}

}
