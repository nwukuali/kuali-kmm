/**
 * 
 */
package org.kuali.ext.mm.cart.dataaccess.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.cart.dataaccess.ShopCartSearchDao;
import org.kuali.rice.kns.dao.jdbc.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * @author schneppd
 */
public class ShopCartSearchDaoJdbc extends PlatformAwareDaoBaseJdbc implements ShopCartSearchDao {

    private final String CATALOG_ITEM_ID = "CATALOG_ITEM_ID";
    private final String CATALOG_DESC = "CATALOG_DESC";
    private final String DISTRIBUTOR_NBR = "DISTRIBUTOR_NBR";
    
    /**
     * @see org.kuali.ext.mm.cart.dataaccess.ShopCartSearchDao#getBestSellingItems(int)
     */
    public List<CatalogItem> getBestSellingItems(List<String> catalogIds, int numberOfResults) {        
        final List<CatalogItem> results = new ArrayList<CatalogItem>();
        String catalogIdString = "";
        Iterator<String> catalogIt = catalogIds.iterator();
        while(catalogIt.hasNext()) {
            catalogIdString += "'" + catalogIt.next() + "'" + (catalogIt.hasNext() ? ", " : "");
        }        
        getJdbcTemplate()
                .query("select " + CATALOG_ITEM_ID + ", " + DISTRIBUTOR_NBR + ", " + CATALOG_DESC + " from"
                        + " (SELECT distinct A0.CATALOG_ITEM_ID,A0.DISTRIBUTOR_NBR,A0.CATALOG_DESC,A0.ORDER_COUNT"
                        + " FROM MM_CATALOG_ITEM_SEARCH_T A0" 
                        + " WHERE A0.DISPLAYABLE_IND = 'Y' AND A0.CATALOG_ID IN (" + catalogIdString +") AND A0.ACTV_IND = 'Y'"
                        + " ORDER BY A0.ORDER_COUNT DESC)"
                        + " WHERE rownum < " + (numberOfResults + 1),
                        new ResultSetExtractor() {
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs != null && rs.next()) {
                                    CatalogItem catalogItem = new CatalogItem();
                                    catalogItem.setCatalogItemId(rs.getString(CATALOG_ITEM_ID));
                                    catalogItem.setCatalogDesc(rs.getString(CATALOG_DESC));
                                    catalogItem.setDistributorNbr(rs.getString(DISTRIBUTOR_NBR));
                                    results.add(catalogItem);
                                }
                                return results;
                            }
                        });
        return results;
    }

}
