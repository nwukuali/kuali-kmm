/**
 * 
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author sravani
 */
public class CatalogPendingDocDaoFactoryBean implements FactoryBean {
    private CatalogPendingDocDao catalogPendingDocDaoOracle;
    private CatalogPendingDocDao catalogPendingDocDaoMysql;
    private String dbPlatform;

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public Object getObject() throws Exception {
        if (dbPlatform != null && dbPlatform.toLowerCase().contains("mysql")) {
            return this.catalogPendingDocDaoMysql;
        }
        return catalogPendingDocDaoOracle;
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
     * Gets the catalogPendingDocDaoOracle property
     * 
     * @return Returns the catalogPendingDocDaoOracle
     */
    public CatalogPendingDocDao getCatalogPendingDocDaoOracle() {
        return this.catalogPendingDocDaoOracle;
    }

    /**
     * Sets the catalogPendingDocDaoOracle property value
     * 
     * @param catalogPendingDocDaoOracle The catalogPendingDocDaoOracle to set
     */
    public void setCatalogPendingDocDaoOracle(CatalogPendingDocDao catalogPendingDocDaoOracle) {
        this.catalogPendingDocDaoOracle = catalogPendingDocDaoOracle;
    }

    /**
     * Gets the catalogPendingDocDaoMysql property
     * 
     * @return Returns the catalogPendingDocDaoMysql
     */
    public CatalogPendingDocDao getCatalogPendingDocDaoMysql() {
        return this.catalogPendingDocDaoMysql;
    }

    /**
     * Sets the catalogItemPendingDaoMysql property value
     * 
     * @param catalogItemPendingDaoMysql The catalogItemPendingDaoMysql to set
     */
    public void setCatalogPendingDocDaoMysql(CatalogPendingDocDao catalogPendingDocDaoMysql) {
        this.catalogPendingDocDaoMysql = catalogPendingDocDaoMysql;
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
