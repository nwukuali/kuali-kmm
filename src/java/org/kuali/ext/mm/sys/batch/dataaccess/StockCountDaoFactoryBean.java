/**
 * 
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author harsha07
 */
public class StockCountDaoFactoryBean implements FactoryBean {
    private StockCountDao stockCountDaoOracle;
    private StockCountDao stockCountDaoMysql;
    private String dbPlatform;

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public Object getObject() throws Exception {
        if (dbPlatform != null && dbPlatform.toLowerCase().contains("mysql")) {
            return this.stockCountDaoMysql;
        }
        return stockCountDaoOracle;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class getObjectType() {
        return StockCountDao.class;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * Gets the stockCountDaoOracle property
     * 
     * @return Returns the stockCountDaoOracle
     */
    public StockCountDao getStockCountDaoOracle() {
        return this.stockCountDaoOracle;
    }

    /**
     * Sets the stockCountDaoOracle property value
     * 
     * @param stockCountDaoOracle The stockCountDaoOracle to set
     */
    public void setStockCountDaoOracle(StockCountDao stockCountDaoOracle) {
        this.stockCountDaoOracle = stockCountDaoOracle;
    }

    /**
     * Gets the stockCountDaoMysql property
     * 
     * @return Returns the stockCountDaoMysql
     */
    public StockCountDao getStockCountDaoMysql() {
        return this.stockCountDaoMysql;
    }

    /**
     * Sets the stockCountDaoMysql property value
     * 
     * @param stockCountDaoMysql The stockCountDaoMysql to set
     */
    public void setStockCountDaoMysql(StockCountDao stockCountDaoMysql) {
        this.stockCountDaoMysql = stockCountDaoMysql;
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
