package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class CatalogLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {
    private static final long serialVersionUID = 1L;

    /**
     * @see org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl#getCustomActionUrls(org.kuali.rice.kns.bo.BusinessObject,
     *      List pkNames)
     */
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
        Catalog catalog = (Catalog)businessObject;
        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(businessObject, pkNames);
        String docType = getMaintenanceDocumentTypeName();
        String nameSpaceCode = KRADConstants.KUALI_RICE_SYSTEM_NAMESPACE;
        Map<String, String> permissionDetails = new HashMap<String, String>();
        permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, docType);
        if ((MMConstants.CatalogType.WAREHOUSE.equalsIgnoreCase(catalog.getCatalogTypeCd())
                || MMConstants.CatalogType.HOSTED.equalsIgnoreCase(catalog.getCatalogTypeCd()))
                && KimApiServiceLocator.getPermissionService()
                    .isAuthorizedByTemplate(GlobalVariables.getUserSession().getPrincipalId(),
                        nameSpaceCode, KewApiConstants.INITIATE_PERMISSION,
                        permissionDetails, null)) {
            anchorHtmlDataList.add(this.getCreateItemUrl(catalog));
        }        

        return anchorHtmlDataList;

    }
    
    protected HtmlData getCreateItemUrl(Catalog catalog) {
        Properties parameters = new Properties();
        parameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER,
                KRADConstants.MAINTENANCE_NEWWITHEXISTING_ACTION);
        parameters.put(MMConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, CatalogItem.class.getName());
        parameters.put(MMConstants.Catalog.CATALOG_ID, catalog.getCatalogId());
        parameters.put(MMConstants.OVERRIDE_KEYS, MMConstants.Catalog.CATALOG_ID);
        parameters.put(MMConstants.REFRESH_CALLER, "catalogLookupable");
        parameters.put(MMConstants.OVERRIDE_KEYS, MMConstants.Catalog.CATALOG_ID);
        parameters.put(KRADConstants.DOC_FORM_KEY, "88888888");
        parameters.put(KRADConstants.ANCHOR, KRADConstants.ANCHOR_TOP_OF_FORM);
        String href = getKualiConfigurationService().getPropertyValueAsString(KRADConstants.APPLICATION_URL_KEY)
                + "/kr/" + UrlFactory.parameterizeUrl(KRADConstants.MAINTENANCE_ACTION, parameters);
        return new AnchorHtmlData(href, KRADConstants.MAINTENANCE_NEWWITHEXISTING_ACTION,
                MMConstants.CATALOG_ACTION_NEW_ITEM);
    }

}