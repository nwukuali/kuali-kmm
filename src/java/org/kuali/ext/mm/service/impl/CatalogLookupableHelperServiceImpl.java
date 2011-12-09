package org.kuali.ext.mm.service.impl;

import java.util.List;
import java.util.Properties;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
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
        String nameSpaceCode = KNSConstants.KUALI_RICE_SYSTEM_NAMESPACE;
        AttributeSet permissionDetails = new AttributeSet();
        permissionDetails.put(KimAttributes.DOCUMENT_TYPE_NAME, docType);
        if ((MMConstants.CatalogType.WAREHOUSE.equalsIgnoreCase(catalog.getCatalogTypeCd())
                || MMConstants.CatalogType.HOSTED.equalsIgnoreCase(catalog.getCatalogTypeCd()))
                && SpringContext.getBean(IdentityManagementService.class)
                    .isAuthorizedByTemplateName(GlobalVariables.getUserSession().getPrincipalId(),
                        nameSpaceCode, KimConstants.PermissionTemplateNames.INITIATE_DOCUMENT,
                        permissionDetails, null)) {
            anchorHtmlDataList.add(this.getCreateItemUrl(catalog));
        }        

        return anchorHtmlDataList;

    }
    
    protected HtmlData getCreateItemUrl(Catalog catalog) {
        Properties parameters = new Properties();
        parameters.put(MMConstants.DISPATCH_REQUEST_PARAMETER,
                KNSConstants.MAINTENANCE_NEWWITHEXISTING_ACTION);
        parameters.put(MMConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, CatalogItem.class.getName());
        parameters.put(MMConstants.Catalog.CATALOG_ID, catalog.getCatalogId());
        parameters.put(MMConstants.OVERRIDE_KEYS, MMConstants.Catalog.CATALOG_ID);
        parameters.put(MMConstants.REFRESH_CALLER, "catalogLookupable");
        parameters.put(MMConstants.OVERRIDE_KEYS, MMConstants.Catalog.CATALOG_ID);
        parameters.put(KNSConstants.DOC_FORM_KEY, "88888888");
        parameters.put(KNSConstants.ANCHOR, KNSConstants.ANCHOR_TOP_OF_FORM);
        String href = getKualiConfigurationService().getPropertyString(KNSConstants.APPLICATION_URL_KEY)
                + "/kr/" + UrlFactory.parameterizeUrl(KNSConstants.MAINTENANCE_ACTION, parameters);
        return new AnchorHtmlData(href, KNSConstants.MAINTENANCE_NEWWITHEXISTING_ACTION,
                MMConstants.CATALOG_ACTION_NEW_ITEM);
    }

}