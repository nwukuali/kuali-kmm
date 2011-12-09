/**
 *
 */
package org.kuali.ext.mm.sys.batch.service;

import java.util.Collection;

import org.kuali.ext.mm.businessobject.CatalogItem;

/**
 * @author rshrivas
 *
 */
public interface CatalogItemService {

    public int updateCatalogItem(String catalogItemId, String unitOfIssueCd, Double catalogPrc);
    
    public Collection<CatalogItem> getCatalogItemsForStock(String stockId);

}
