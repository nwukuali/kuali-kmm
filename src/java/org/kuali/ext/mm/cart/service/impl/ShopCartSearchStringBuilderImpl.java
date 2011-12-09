/**
 * 
 */
package org.kuali.ext.mm.cart.service.impl;

import org.kuali.ext.mm.cart.service.ShopCartSearchStringBuilder;
import org.kuali.rice.core.database.platform.DatabasePlatform;
import org.kuali.rice.core.database.platform.MySQLDatabasePlatform;
import org.kuali.rice.core.database.platform.OracleDatabasePlatform;

/**
 * @author schneppd
 *
 * This implementation of ShopCartSearchStringBuilder is a proxy to choose either Oracle or MySQL
 * based on the DatabasePlatform.
 *
 */
public class ShopCartSearchStringBuilderImpl implements ShopCartSearchStringBuilder {
    
    private DatabasePlatform dbPlatform;
    private ShopCartSearchStringBuilderOracle shopCartSearchStringBuilderOracle;
    private ShopCartSearchStringBuilderMySQL shopCartSearchStringBuilderMySQL;
    
    private ShopCartSearchStringBuilder getStringBuilder() {
        if(dbPlatform instanceof OracleDatabasePlatform) {
            return shopCartSearchStringBuilderOracle;
        }
        else if(dbPlatform instanceof MySQLDatabasePlatform) {
            return shopCartSearchStringBuilderMySQL;
        }
        throw new RuntimeException("Unrecognized database platform.  This implementation only supports Oracle or MySQL.");
    }
    
    /**
     * @see org.kuali.ext.mm.cart.service.impl.ShopCartSearchStringBuilder#appendAsAllWordsString(java.lang.String, java.lang.String)
     */
    public String appendAsAllWordsString(String currentString, String addKeywords) {        
        return getStringBuilder().appendAsAllWordsString(currentString, addKeywords);
    }

    /**
     * @see org.kuali.ext.mm.cart.service.impl.ShopCartSearchStringBuilder#appendAsNotWordsString(java.lang.String, java.lang.String)
     */
    public String appendAsNotWordsString(String currentString, String addKeywords) {
        return getStringBuilder().appendAsNotWordsString(currentString, addKeywords);
    }

    /**
     * @see org.kuali.ext.mm.cart.service.impl.ShopCartSearchStringBuilder#appendAsAnyWordsString(java.lang.String, java.lang.String)
     */
    public String appendAsAnyWordsString(String currentString, String addKeywords) {
        return getStringBuilder().appendAsAnyWordsString(currentString, addKeywords);
    }
    
    /**
     * @see org.kuali.ext.mm.cart.service.impl.ShopCartSearchStringBuilder#appendAsExactWordsString(java.lang.String, java.lang.String)
     */
    public String appendAsExactWordsString(String currentString, String addKeywords) {
        return getStringBuilder().appendAsExactWordsString(currentString, addKeywords);
    }

    /**
     * Gets the dbPlatform property
     * @return Returns the dbPlatform
     */
    public DatabasePlatform getDbPlatform() {
        return this.dbPlatform;
    }

    /**
     * Sets the dbPlatform property value
     * @param dbPlatform The dbPlatform to set
     */
    public void setDbPlatform(DatabasePlatform dbPlatform) {
        this.dbPlatform = dbPlatform;
    }

    /**
     * Gets the shopCartSearchStringBuilderOracle property
     * @return Returns the shopCartSearchStringBuilderOracle
     */
    public ShopCartSearchStringBuilderOracle getShopCartSearchStringBuilderOracle() {
        return this.shopCartSearchStringBuilderOracle;
    }

    /**
     * Sets the shopCartSearchStringBuilderOracle property value
     * @param shopCartSearchStringBuilderOracle The shopCartSearchStringBuilderOracle to set
     */
    public void setShopCartSearchStringBuilderOracle(
            ShopCartSearchStringBuilderOracle shopCartSearchStringBuilderOracle) {
        this.shopCartSearchStringBuilderOracle = shopCartSearchStringBuilderOracle;
    }

    /**
     * Gets the shopCartSearchStringBuilderMySQL property
     * @return Returns the shopCartSearchStringBuilderMySQL
     */
    public ShopCartSearchStringBuilderMySQL getShopCartSearchStringBuilderMySQL() {
        return this.shopCartSearchStringBuilderMySQL;
    }

    /**
     * Sets the shopCartSearchStringBuilderMySQL property value
     * @param shopCartSearchStringBuilderMySQL The shopCartSearchStringBuilderMySQL to set
     */
    public void setShopCartSearchStringBuilderMySQL(
            ShopCartSearchStringBuilderMySQL shopCartSearchStringBuilderMySQL) {
        this.shopCartSearchStringBuilderMySQL = shopCartSearchStringBuilderMySQL;
    }
    
    
}
