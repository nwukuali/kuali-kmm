/**
 * 
 */
package org.kuali.ext.mm.sys.batch.dataaccess;


/**
 * @author schneppd
 *
 */
public interface CatalogItemSearchDataDao {

    public int rebuildSearchTable();

    public void rebuildIndexes();

    public void truncateSearchTable();

    public void dropIndexes();

}