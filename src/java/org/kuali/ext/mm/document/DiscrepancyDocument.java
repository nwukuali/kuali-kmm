/**
 * 
 */
package org.kuali.ext.mm.document;

import org.kuali.ext.mm.businessobject.StoresTransactionalDocumentBase;
import org.springframework.util.AutoPopulatingList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

/**
 * @author rshrivas
 *
 */
@Entity
@Table(name = "MM_DISCREPANCY_DOC_T")
public class DiscrepancyDocument extends StoresTransactionalDocumentBase{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FDOC_NBR", unique = true, nullable = false, length = 36)
    private String fdocNbr;

    private List<DiscrepancyLine> discrepancyLines;
    
    public DiscrepancyDocument() {
        super();      
        this.setDiscrepancyLines(new AutoPopulatingList(DiscrepancyLine.class));
    }  
    
    /**
     * Gets the fdocNbr property
     * @return Returns the fdocNbr
     */
    public String getFdocNbr() {
        return this.fdocNbr;
    }

    /**
     * Sets the fdocNbr property value
     * @param fdocNbr The fdocNbr to set
     */
    public void setFdocNbr(String fdocNbr) {
        this.fdocNbr = fdocNbr;
    }



    /**
     * Gets the discrepancyLines property
     * @return Returns the discrepancyLines
     */
    public List<DiscrepancyLine> getDiscrepancyLines() {
        return this.discrepancyLines;
    }



    /**
     * Sets the discrepancyLines property value
     * @param discrepancyLines The discrepancyLines to set
     */
    public void setDiscrepancyLines(List<DiscrepancyLine> discrepancyLines) {
        this.discrepancyLines = discrepancyLines;
    }



    private String reportName;
    private String vendorName;
    private Date discrepancyFromDate;
    private Date discrepancyToDate;
    
    /**
     * Gets the reportName property
     * @return Returns the reportName
     */
    public String getReportName() {
        return this.reportName;
    }

    /**
     * Sets the reportName property value
     * @param reportName The reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * Gets the vendorName property
     * @return Returns the vendorName
     */
    public String getVendorName() {
        return this.vendorName;
    }

    /**
     * Sets the vendorName property value
     * @param vendorName The vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * Gets the discrepancyFromDate property
     * @return Returns the discrepancyFromDate
     */
    public Date getDiscrepancyFromDate() {
        return this.discrepancyFromDate;
    }

    /**
     * Sets the discrepancyFromDate property value
     * @param discrepancyFromDate The discrepancyFromDate to set
     */
    public void setDiscrepancyFromDate(Date discrepancyFromDate) {
        this.discrepancyFromDate = discrepancyFromDate;
    }

    /**
     * Gets the discrepancyToDate property
     * @return Returns the discrepancyToDate
     */
    public Date getDiscrepancyToDate() {
        return this.discrepancyToDate;
    }

    /**
     * Sets the discrepancyToDate property value
     * @param discrepancyToDate The discrepancyToDate to set
     */
    public void setDiscrepancyToDate(Date discrepancyToDate) {
        this.discrepancyToDate = discrepancyToDate;
    }



}
