/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess.jdbc;

import org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemDao;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;

/**
 * @author rshrivas
 */
public class CatalogItemDaoJdbc extends PlatformAwareDaoBaseJdbc implements CatalogItemDao {

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemDao#updateCatalogItem(java.lang.String, java.lang.String, java.lang.Double)
     */
    public int updateCatalogItem(String catalogItemId, String unitOfIssueCd, Double catalogPrc) {

        String updateSql = "UPDATE MM_CATALOG_ITEM_T SET CATALOG_UNIT_OF_ISSUE_CD = '"
                + unitOfIssueCd + "', CATALOG_PRC = '" + catalogPrc + "' WHERE CATALOG_ITEM_ID = '"
                + catalogItemId + "'";
        return getJdbcTemplate().update(updateSql.toString());
    }

}
