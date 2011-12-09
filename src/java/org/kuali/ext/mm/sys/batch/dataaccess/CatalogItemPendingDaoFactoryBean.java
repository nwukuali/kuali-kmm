/**
 * 
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author sravani
 */
public class CatalogItemPendingDaoFactoryBean implements FactoryBean {
    private CatalogItemPendingDao catalogItemPendingDaoOracle;
    private CatalogItemPendingDao catalogItemPendingDaoMysql;
    private String dbPlatform;

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public Object getObject() throws Exception {
        if (dbPlatform != null && dbPlatform.toLowerCase().contains("mysql")) {
            return this.catalogItemPendingDaoMysql;
        }
        return catalogItemPendingDaoOracle;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class getObjectType() {
        return CatalogItemPendingDao.class;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * Gets the catalogItemPendingDaoOracle property
     * 
     * @return Returns the catalogItemPendingDaoOracle
     */
    public CatalogItemPendingDao getCatalogItemPendingDaoOracle() {
        return this.catalogItemPendingDaoOracle;
    }

    /**
     * Sets the catalogItemPendingDaoOracle property value
     * 
     * @param catalogItemPendingDaoOracle The catalogItemPendingDaoOracle to set
     */
    public void setCatalogItemPendingDaoOracle(CatalogItemPendingDao catalogItemPendingDaoOracle) {
        this.catalogItemPendingDaoOracle = catalogItemPendingDaoOracle;
    }

    /**
     * Gets the catalogItemPendingDaoMysql property
     * 
     * @return Returns the catalogItemPendingDaoMysql
     */
    public CatalogItemPendingDao getCatalogItemPendingDaoMysql() {
        return this.catalogItemPendingDaoMysql;
    }

    /**
     * Sets the catalogItemPendingDaoMysql property value
     * 
     * @param catalogItemPendingDaoMysql The catalogItemPendingDaoMysql to set
     */
    public void setCatalogItemPendingDaoMysql(CatalogItemPendingDao catalogItemPendingDaoMysql) {
        this.catalogItemPendingDaoMysql = catalogItemPendingDaoMysql;
    }

    /**
     * Gets the dbPlatform property
     * 
     * @return Returns the dbPlatform
     */
    public String getDbPlatform() {
        return this.dbPlatform;
    }

    /**
     * Sets the dbPlatform property value
     * 
     * @param dbPlatform The dbPlatform to set
     */
    public void setDbPlatform(String dbPlatform) {
        this.dbPlatform = dbPlatform;
    }
}
