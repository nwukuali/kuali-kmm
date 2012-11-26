/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess.jdbc;

import org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemSearchDataDao;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;

/**
 * @author schneppd
 *
 */
public class CatalogItemSearchDataDaoJdbc extends PlatformAwareDaoBaseJdbc implements CatalogItemSearchDataDao  {
    
	/**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.jdbc.CatalogItemSearchDataDao#rebuildSearchTable()
     */
	public int rebuildSearchTable() {
	    return getJdbcTemplate().update("insert into MM_CATALOG_ITEM_SEARCH_T"
	            + " select CATALOG_ITEM_ID, DISTRIBUTOR_NBR, CATALOG_DESC," 
	            + " RECYCLED_IND, CATALOG_PRC, CATALOG_ID, STOCK_ID, DISPLAYABLE_IND, ACTV_IND," 
	            + " PRIORITY_NBR, CATALOG_SUBGROUP_ID, PRIOR_CATALOG_SUBGROUP_ID, CATALOG_GROUP_CD, ORDER_COUNT"
	            + " from MM_CATALOG_ITEM_LIVE_V v where v.ACTV_IND='Y' and v.DISPLAYABLE_IND='Y'");	    
	}
	
	/**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.jdbc.CatalogItemSearchDataDao#rebuildIndexes()
     */
	public void rebuildIndexes() {
	    getJdbcTemplate().execute("create index MM_CATALOG_SEARCH_IDX1 on MM_CATALOG_ITEM_SEARCH_T(catalog_desc) INDEXTYPE IS CTXSYS.CTXCAT");
        getJdbcTemplate().execute("create index MM_CATALOG_SEARCH_IDX2 on MM_CATALOG_ITEM_SEARCH_T (Distributor_Nbr)");
	}
	
	/**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.jdbc.CatalogItemSearchDataDao#truncateSearchTable()
     */
	public void truncateSearchTable() {
	    getJdbcTemplate().update("truncate table MM_CATALOG_ITEM_SEARCH_T");
	}
	
	/**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.jdbc.CatalogItemSearchDataDao#dropIndexes()
     */
	public void dropIndexes() {
	    getJdbcTemplate().execute("drop index mm_catalog_search_idx1");
        getJdbcTemplate().execute("drop index mm_catalog_search_idx2");
	}
}
