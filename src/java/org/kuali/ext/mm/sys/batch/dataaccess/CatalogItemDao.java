/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

/**
 * @author rshrivas
 *
 */
public interface CatalogItemDao {

    public int updateCatalogItem(String catalogItemId, String unitOfIssueCd, Double catalogPrc);

}
