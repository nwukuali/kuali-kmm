/**
 *
 */
package org.kuali.ext.mm.businessobject.lookup;

import java.util.List;
import java.util.Properties;

import org.kuali.ext.mm.businessobject.CatalogPendingHelper;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.IdentityManagementService;
import org.kuali.rice.kim.util.KimConstants;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;


/**
 * @author rshrivas
 *
 */
@SuppressWarnings("serial")
public class CatalogPendingLookableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

    @SuppressWarnings("unchecked")
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject bo, List pkNames) {

        String nameSpaceCode = KNSConstants.KUALI_RICE_SYSTEM_NAMESPACE;
        AttributeSet permissionDetails = new AttributeSet();
        permissionDetails.put(KimAttributes.DOCUMENT_TYPE_NAME, "SUPC");

        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(bo, pkNames);
        CatalogPendingHelper cpHelper = (CatalogPendingHelper)bo;

        if (SpringContext.getBean(IdentityManagementService.class).isAuthorizedByTemplateName(
                GlobalVariables.getUserSession().getPrincipalId(), nameSpaceCode,
                KimConstants.PermissionTemplateNames.INITIATE_DOCUMENT, permissionDetails, null)) {

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
    protected AnchorHtmlData getUrlData(BusinessObject businessObject, String methodToCall, String displayText, List pkNames) {
       CatalogPendingHelper cpHelper = (CatalogPendingHelper)businessObject;

       AnchorHtmlData anchorHtmlData = null;

       if ("initiateFinalApproval".equals(methodToCall)){
           Properties params = new Properties();
           params.setProperty(KNSConstants.DISPATCH_REQUEST_PARAMETER, "initiateFinalApproval");
           params.setProperty(MMConstants.DOCUMENT_ID, cpHelper.getDocumentNumber());
           String href = getKualiConfigurationService().getPropertyString(KNSConstants.APPLICATION_URL_KEY) + "/" +UrlFactory.parameterizeUrl("initiateFinalApproval.do", params);
           anchorHtmlData = new AnchorHtmlData(href, methodToCall, displayText);
       }

       if ("getReports".equals(methodToCall)){
           Properties params = new Properties();
           params.setProperty(KNSConstants.DISPATCH_REQUEST_PARAMETER, "getReports");
           params.setProperty(MMConstants.DOCUMENT_ID, cpHelper.getDocumentNumber());
           String href = getKualiConfigurationService().getPropertyString(KNSConstants.APPLICATION_URL_KEY) + "/" +UrlFactory.parameterizeUrl("getReports.do", params);
           anchorHtmlData = new AnchorHtmlData(href, methodToCall, displayText);

       }

       if ("awaitingLoading".equals(methodToCall)){
           String href = "";
           anchorHtmlData = new AnchorHtmlData(href, methodToCall, displayText);
       }

       if ("awaitingItemLoading".equals(methodToCall)){
           String href = "";
           anchorHtmlData = new AnchorHtmlData(href, methodToCall, "");
       }

       return anchorHtmlData;
    }

}
