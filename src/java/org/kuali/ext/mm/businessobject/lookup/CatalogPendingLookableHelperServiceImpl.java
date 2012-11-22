/**
 *
 */
package org.kuali.ext.mm.businessobject.lookup;

import org.kuali.ext.mm.businessobject.CatalogPendingHelper;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @author rshrivas
 *
 */
@SuppressWarnings("serial")
public class CatalogPendingLookableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

    @SuppressWarnings("unchecked")
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject bo, List pkNames) {

        String nameSpaceCode = KRADConstants.KUALI_RICE_SYSTEM_NAMESPACE;
        Map<String, String> permissionDetails = new HashMap<String, String>();
        permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, "SUPC");

        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(bo, pkNames);
        CatalogPendingHelper cpHelper = (CatalogPendingHelper)bo;

				if (KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(
					GlobalVariables.getUserSession().getPrincipalId(),
					nameSpaceCode, KewApiConstants.INITIATE_PERMISSION,
					permissionDetails, null)) {

            if("UPLOAD".equalsIgnoreCase(cpHelper.getCatalogUploadStatus())){
                anchorHtmlDataList.add(getUrlData(bo, "getReports", "Review & Approve", pkNames));
                //anchorHtmlDataList.add(getUrlData(bo, "initiateFinalApproval", "Load Approved Catalog", pkNames));
            }else if ("UPLOADING".equalsIgnoreCase(cpHelper.getCatalogUploadStatus())){
                anchorHtmlDataList.add(getUrlData(bo, "getReports", "Reports", pkNames));
                anchorHtmlDataList.add(getUrlData(bo, "awaitingItemLoading", "Catalog Items are being loaded ..", pkNames));
            }else if ("UPLOADED".equalsIgnoreCase(cpHelper.getCatalogUploadStatus())){
                anchorHtmlDataList.add(getUrlData(bo, "getReports", "Reports", pkNames));
            }else{
                anchorHtmlDataList.add(getUrlData(bo, "awaitingLoading", "awaitingLoading", pkNames));
            }
        }
        return anchorHtmlDataList;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected HtmlData.AnchorHtmlData getUrlData(BusinessObject businessObject, String methodToCall, String displayText, List pkNames) {
       CatalogPendingHelper cpHelper = (CatalogPendingHelper)businessObject;

       HtmlData.AnchorHtmlData anchorHtmlData = null;

       if ("initiateFinalApproval".equals(methodToCall)){
           Properties params = new Properties();
           params.setProperty(KRADConstants.DISPATCH_REQUEST_PARAMETER, "initiateFinalApproval");
           params.setProperty(MMConstants.DOCUMENT_ID, cpHelper.getDocumentNumber());
           String href = getKualiConfigurationService().getPropertyValueAsString(KRADConstants.APPLICATION_URL_KEY) + "/" +UrlFactory.parameterizeUrl("initiateFinalApproval.do", params);
           anchorHtmlData = new HtmlData.AnchorHtmlData(href, methodToCall, displayText);
       }

       if ("getReports".equals(methodToCall)){
           Properties params = new Properties();
           params.setProperty(KRADConstants.DISPATCH_REQUEST_PARAMETER, "getReports");
           params.setProperty(MMConstants.DOCUMENT_ID, cpHelper.getDocumentNumber());
           String href = getKualiConfigurationService().getPropertyValueAsString(KRADConstants.APPLICATION_URL_KEY) + "/" +UrlFactory.parameterizeUrl("getReports.do", params);
           anchorHtmlData = new HtmlData.AnchorHtmlData(href, methodToCall, displayText);

       }

       if ("awaitingLoading".equals(methodToCall)){
           String href = "";
           anchorHtmlData = new HtmlData.AnchorHtmlData(href, methodToCall, displayText);
       }

       if ("awaitingItemLoading".equals(methodToCall)){
           String href = "";
           anchorHtmlData = new HtmlData.AnchorHtmlData(href, methodToCall, "");
       }

       return anchorHtmlData;
    }

}
