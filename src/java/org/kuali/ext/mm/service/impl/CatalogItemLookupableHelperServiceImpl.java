/**
 * 
 */
package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rshrivas
 *
 */
public class CatalogItemLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Child classes should override this method if they want to return some other action urls.
     *
     * @returns This default implementation returns links to edit and copy maintenance action for
     * the current maintenance record if the business object class has an associated maintenance document.
     * Also checks value of allowsNewOrCopy in maintenance document xml before rendering the copy link.
     *
     * @see org.kuali.rice.kns.lookup.LookupableHelperService#getCustomActionUrls(org.kuali.rice.kns.bo.BusinessObject, java.util.List, java.util.List pkNames)
     */
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames){
        List<HtmlData> htmlDataList = new ArrayList<HtmlData>();
        Catalog catalog = ((CatalogItem)businessObject).getCatalog();
        if (catalog != null && MMConstants.CatalogType.WAREHOUSE.equalsIgnoreCase(catalog.getCatalogTypeCd())
                || MMConstants.CatalogType.HOSTED.equalsIgnoreCase(catalog.getCatalogTypeCd())) {
            if (allowsMaintenanceEditAction(businessObject)) {
                htmlDataList.add(getUrlData(businessObject, KRADConstants.MAINTENANCE_EDIT_METHOD_TO_CALL, pkNames));
            }
           
            htmlDataList.add(getUrlData(businessObject, KRADConstants.MAINTENANCE_COPY_METHOD_TO_CALL, pkNames));
        }
        return htmlDataList;
    }
    
    /**
     * @see org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl#allowsMaintenanceNewOrCopyAction()
     */
    @Override
    public boolean allowsMaintenanceNewOrCopyAction() {
        return false;
       // return super.allowsMaintenanceNewOrCopyAction();
    }
}
