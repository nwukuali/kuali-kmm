/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemDao;
import org.kuali.ext.mm.sys.batch.service.CatalogItemService;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author rshrivas
 *
 */
public class CatalogItemServiceImpl implements CatalogItemService{

    private CatalogItemDao catalogItemDao;



    public CatalogItemDao getCatalogItemDao() {
        return this.catalogItemDao;
    }



    public void setCatalogItemDao(CatalogItemDao catalogItemDao) {
        this.catalogItemDao = catalogItemDao;
    }



    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemUpdateService#updateCatalogItem(java.lang.String, java.lang.String, java.lang.Double)
     */
    public int updateCatalogItem(String catalogItemId, String unitOfIssueCd, Double catalogPrc) {
        return this.catalogItemDao.updateCatalogItem(catalogItemId, unitOfIssueCd, catalogPrc);
    }
    
    /**
     * @see org.kuali.ext.mm.sys.batch.service.CatalogItemService#getCatalogItemsForStock(java.lang.String)
     */
    public Collection<CatalogItem> getCatalogItemsForStock(String stockId) {
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.CatalogItem.STOCK_ID, stockId);
        return KRADServiceLocator.getBusinessObjectService().findMatching(CatalogItem.class, fieldValues);
    }

}
