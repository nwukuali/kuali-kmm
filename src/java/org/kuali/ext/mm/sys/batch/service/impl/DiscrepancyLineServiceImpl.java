/**
 * 
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import java.sql.Date;
import java.util.List;

import org.kuali.ext.mm.document.DiscrepancyLine;
import org.kuali.ext.mm.sys.batch.dataaccess.DiscrepancyLineDao;
import org.kuali.ext.mm.sys.batch.service.DiscrepancyLineService;

/**
 * @author rshrivas
 *
 */
public class DiscrepancyLineServiceImpl implements DiscrepancyLineService{

    private DiscrepancyLineDao discrepancyLineDao;
    
    
    /**
     * Gets the discrepancyLineDao property
     * @return Returns the discrepancyLineDao
     */
    public DiscrepancyLineDao getDiscrepancyLineDao() {
        return this.discrepancyLineDao;
    }


    /**
     * Sets the discrepancyLineDao property value
     * @param discrepancyLineDao The discrepancyLineDao to set
     */
    public void setDiscrepancyLineDao(DiscrepancyLineDao discrepancyLineDao) {
        this.discrepancyLineDao = discrepancyLineDao;
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.service.DiscrepancyLineService#getDiscrepancyLines(java.lang.String, java.sql.Date, java.sql.Date)
     */
    public List<DiscrepancyLine> getDiscrepancyLines(String vendorName, Date fromDate, Date toDate) {
        return discrepancyLineDao.getDiscrepancyLines(vendorName, fromDate, toDate);
    }

}
