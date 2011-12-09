/**
 * 
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.sql.Date;
import java.util.List;

import org.kuali.ext.mm.document.DiscrepancyLine;

/**
 * @author rshrivas
 *
 */
public interface DiscrepancyLineDao {
        
    public List<DiscrepancyLine> getDiscrepancyLines(String vendorName, Date fromDate, Date toDate);
}
