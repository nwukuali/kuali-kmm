/**
 * 
 */
package org.kuali.ext.mm.cart.dataaccess;

import java.util.List;

import org.kuali.ext.mm.businessobject.CatalogItem;

/**
 * @author schneppd
 *
 */
public interface ShopCartSearchDao {

    public List<CatalogItem> getBestSellingItems(List<String> catalogIds, int numberOfResults);
    
}
