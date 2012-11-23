/**
 * 
 */
package org.kuali.ext.mm.sys.batch.dataaccess.jdbc;

import org.apache.commons.collections.map.ListOrderedMap;
import org.kuali.ext.mm.document.DiscrepancyLine;
import org.kuali.ext.mm.sys.batch.dataaccess.DiscrepancyLineDao;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author rshrivas
 *
 */
public class DiscrepancyLineDaoJdbc extends PlatformAwareDaoBaseJdbc implements DiscrepancyLineDao{

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.DiscrepancyLineDao#getDiscrepancyLines(java.lang.String, java.sql.Date, java.sql.Date)
     */
    public List<DiscrepancyLine> getDiscrepancyLines(String vendorName, Date fromDate, Date toDate) {

        ArrayList<DiscrepancyLine> returnedList = new ArrayList();
         
        String query = "SELECT DISTINCT ORDER_NUMBER, INVOICE_NUMBER FROM MM_DISCREPANCY_LINE_T";
        
        if(!vendorName.equalsIgnoreCase("Select All")){
            query = query + " WHERE VENDOR_NAME = '" + vendorName + "'";              
        }     
         
        Collection<ListOrderedMap> lOM = getJdbcTemplate().queryForList(query);
        for (Iterator iterator = lOM.iterator(); iterator.hasNext();) {
            ListOrderedMap listOrderedMap = (ListOrderedMap) iterator.next();
            String oN = (String)listOrderedMap.get("ORDER_NUMBER");
            String iN = (String)listOrderedMap.get("INVOICE_NUMBER");      
            
            String query2 = "SELECT * FROM MM_DISCREPANCY_LINE_T WHERE ORDER_NUMBER = '" + oN + 
                            "' AND INVOICE_NUMBER = '" + iN + "'";
            
            if(!vendorName.equalsIgnoreCase("Select All")){
                query2 = query2 + " AND VENDOR_NAME = '" + vendorName + "'";              
            }
            
            Collection<ListOrderedMap> vals = getJdbcTemplate().queryForList(query2);
            ArrayList<DiscrepancyLine> valLoadValues = loadValues(vals);
            
            if(valLoadValues.size() == 1){
                returnedList.add(valLoadValues.get(0));
            }else{
                String query3 = "SELECT * FROM mm_discrepancy_line_t a, mm_discrepancy_line_t b " +
                		"WHERE a.ORDER_NUMBER = '" + oN + 
                		"' AND a.INVOICE_NUMBER = '" + iN + "' AND a.ACTV_IND = 'Y' AND " + 
                		"a.DISCR_LINE_DOC_NBR > b.DISCR_LINE_DOC_NBR";

                if(!vendorName.equalsIgnoreCase("Select All")){
                    query3 = query3 + " AND a.VENDOR_NAME = '" + vendorName + "'";              
                }
                
                Collection<ListOrderedMap> lomV= getJdbcTemplate().queryForList(query3);
                ArrayList<DiscrepancyLine> lomValues = loadValues(lomV);
                if(!lomValues.isEmpty()){
                    returnedList.add(lomValues.get(0));
                }
            }
        }
        
        return returnedList;
    }    
    
    private ArrayList<DiscrepancyLine> loadValues(Collection<ListOrderedMap> values) {
        ArrayList<DiscrepancyLine> returnedList = new ArrayList<DiscrepancyLine>();
        for (ListOrderedMap lOM : values) {
            DiscrepancyLine dL = new DiscrepancyLine();
            dL.setDiscrepancyLineDocNbr((String) lOM.get("DISCR_LINE_DOC_NBR"));
            dL.setVendorName((String) lOM.get("VENDOR_NAME"));
            dL.setOrderNumber((String) lOM.get("ORDER_NUMBER"));
            dL.setInvoiceNumber((String) lOM.get("INVOICE_NUMBER"));           
            dL.setTransactionAmt((String) lOM.get("TRANSACTION_AMT"));
            dL.setTotalPCardAmt((String) lOM.get("TOTAL_PCARD_AMT"));
            dL.setInvoiceAmt((String) lOM.get("INVOICE_AMT"));
            Timestamp st = (Timestamp) lOM.get("POSTED_DATE");
            dL.setPostedDate(new java.sql.Date(st.getTime()));
            dL.setDiscrepancyComment((String) lOM.get("DISCR_COMMENT"));
            dL.setLastUpdateDate((Timestamp) lOM.get("LAST_UPDATE_DT"));
            returnedList.add(dL);
        }

        return returnedList;
    }
}
