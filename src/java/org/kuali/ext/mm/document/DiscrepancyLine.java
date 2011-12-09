/**
 * 
 */
package org.kuali.ext.mm.document;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.ext.mm.businessobject.MMPersistableBusinessObjectBase;

/**
 * @author rshrivas
 *
 */
@Entity
@Table(name = "MM_DISCREPANCY_LINE_T")
public class DiscrepancyLine extends MMPersistableBusinessObjectBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DISCR_LINE_ID", unique = true, nullable = false)
    private String discrepancyLineId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISCR_LINE_DOC_NBR", nullable = false)
    private DiscrepancyDocument discrepancyDocument;
    
    @Column(name = "DISCR_LINE_DOC_NBR") 
    private String discrepancyLineDocNbr;
    
    @Column(name = "VENDOR_NAME") 
    private String vendorName;
    
    @Column(name = "ORDER_NUMBER")
    private String orderNumber;
    
    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;
    
    @Column(name = "TRANSACTION_AMT")
    private String transactionAmt;
    
    @Column(name = "TOTAL_PCARD_AMT")
    private String totalPCardAmt;
    
    @Column(name = "INVOICE_AMT")
    private String invoiceAmt;
        
    @Column (name = "POSTED_DATE")
    private Date postedDate;
    
    @Column(name = "DISCR_COMMENT")
    private String discrepancyComment;
    
    private String difference;
    
    private Date transactionDate;

    private boolean selected = false;     

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
     * Gets the orderNumber property
     * @return Returns the orderNumber
     */
    public String getOrderNumber() {
        return this.orderNumber;
    }
    /**
     * Sets the orderNumber property value
     * @param orderNumber The orderNumber to set
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    /**
     * Gets the invoiceNumber property
     * @return Returns the invoiceNumber
     */
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }
    /**
     * Sets the invoiceNumber property value
     * @param invoiceNumber The invoiceNumber to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    /**
     * Gets the transactionAmt property
     * @return Returns the transactionAmt
     */
    public String getTransactionAmt() {
        return this.transactionAmt;
    }
    /**
     * Sets the transactionAmt property value
     * @param transactionAmt The transactionAmt to set
     */
    public void setTransactionAmt(String transactionAmt) {
        this.transactionAmt = transactionAmt;
    }
    /**
     * Gets the totalPCardAmt property
     * @return Returns the totalPCardAmt
     */
    public String getTotalPCardAmt() {
        return this.totalPCardAmt;
    }
    /**
     * Sets the totalPCardAmt property value
     * @param totalPCardAmt The totalPCardAmt to set
     */
    public void setTotalPCardAmt(String totalPCardAmt) {
        this.totalPCardAmt = totalPCardAmt;
    }
    /**
     * Gets the invoiceAmt property
     * @return Returns the invoiceAmt
     */
    public String getInvoiceAmt() {
        return this.invoiceAmt;
    }
    /**
     * Sets the invoiceAmt property value
     * @param invoiceAmt The invoiceAmt to set
     */
    public void setInvoiceAmt(String invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }
    /**
     * Gets the difference property
     * @return Returns the difference
     */
    public String getDifference() {
        return this.difference;
    }
    /**
     * Sets the difference property value
     * @param difference The difference to set
     */
    public void setDifference(String difference) {
        this.difference = difference;
    }
    /**
     * Gets the transactionDate property
     * @return Returns the transactionDate
     */
    public Date getTransactionDate() {
        return this.transactionDate;
    }
    /**
     * Sets the transactionDate property value
     * @param transactionDate The transactionDate to set
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    /**
     * Gets the postedDate property
     * @return Returns the postedDate
     */
    public Date getPostedDate() {
        return this.postedDate;
    }
    /**
     * Sets the postedDate property value
     * @param postedDate The postedDate to set
     */
    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }
    

    /**
     * Gets the selected property
     * @return Returns the selected
     */
    public boolean isSelected() {
        return this.selected;
    }
    /**
     * Sets the selected property value
     * @param selected The selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    /**
     * Gets the discrepancyLineId property
     * @return Returns the discrepancyLineId
     */
    public String getDiscrepancyLineId() {
        return this.discrepancyLineId;
    }
    /**
     * Sets the discrepancyLineId property value
     * @param discrepancyLineId The discrepancyLineId to set
     */
    public void setDiscrepancyLineId(String discrepancyLineId) {
        this.discrepancyLineId = discrepancyLineId;
    }
    /**
     * Gets the discrepancyDocument property
     * @return Returns the discrepancyDocument
     */
    public DiscrepancyDocument getDiscrepancyDocument() {
        return this.discrepancyDocument;
    }
    /**
     * Sets the discrepancyDocument property value
     * @param discrepancyDocument The discrepancyDocument to set
     */
    public void setDiscrepancyDocument(DiscrepancyDocument discrepancyDocument) {
        this.discrepancyDocument = discrepancyDocument;
    }

    
    /**
     * Gets the discrepancyLineDocNbr property
     * @return Returns the discrepancyLineDocNbr
     */
    public String getDiscrepancyLineDocNbr() {
        return this.discrepancyLineDocNbr;
    }
    /**
     * Sets the discrepancyLineDocNbr property value
     * @param discrepancyLineDocNbr The discrepancyLineDocNbr to set
     */
    public void setDiscrepancyLineDocNbr(String discrepancyLineDocNbr) {
        this.discrepancyLineDocNbr = discrepancyLineDocNbr;
    }
    /**
     * Gets the discrepancyComment property
     * @return Returns the discrepancyComment
     */
    public String getDiscrepancyComment() {
        return this.discrepancyComment;
    }
    /**
     * Sets the discrepancyComment property value
     * @param discrepancyComment The discrepancyComment to set
     */
    public void setDiscrepancyComment(String discrepancyComment) {
        this.discrepancyComment = discrepancyComment;
    }
    
    // required for other reports
    
    private String distributorNumber;
    private String shippedDistributorNumber;
    private String unitOfIssueCode;
    private String itemDescription;
    private String qtyOrdered;
    private String qtyShipped;
    private String invoiceQtyShip;
    private String unitInvoiceAmt;
    private String taxInvoiceAmt;
    private String totalAmt;
    private Date dateShipped;
    private String poPrice;
    private String percentageDiff;

    private boolean lineReadOnly = false;
    
    /**
     * Gets the distributorNumber property
     * @return Returns the distributorNumber
     */
    public String getDistributorNumber() {
        return this.distributorNumber;
    }
    /**
     * Sets the distributorNumber property value
     * @param distributorNumber The distributorNumber to set
     */
    public void setDistributorNumber(String distributorNumber) {
        this.distributorNumber = distributorNumber;
    }
    /**
     * Gets the shippedDistributorNumber property
     * @return Returns the shippedDistributorNumber
     */
    public String getShippedDistributorNumber() {
        return this.shippedDistributorNumber;
    }
    /**
     * Sets the shippedDistributorNumber property value
     * @param shippedDistributorNumber The shippedDistributorNumber to set
     */
    public void setShippedDistributorNumber(String shippedDistributorNumber) {
        this.shippedDistributorNumber = shippedDistributorNumber;
    }
    /**
     * Gets the unitOfIssueCode property
     * @return Returns the unitOfIssueCode
     */
    public String getUnitOfIssueCode() {
        return this.unitOfIssueCode;
    }
    /**
     * Sets the unitOfIssueCode property value
     * @param unitOfIssueCode The unitOfIssueCode to set
     */
    public void setUnitOfIssueCode(String unitOfIssueCode) {
        this.unitOfIssueCode = unitOfIssueCode;
    }
    /**
     * Gets the itemDescription property
     * @return Returns the itemDescription
     */
    public String getItemDescription() {
        return this.itemDescription;
    }
    /**
     * Sets the itemDescription property value
     * @param itemDescription The itemDescription to set
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    /**
     * Gets the qtyOrdered property
     * @return Returns the qtyOrdered
     */
    public String getQtyOrdered() {
        return this.qtyOrdered;
    }
    /**
     * Sets the qtyOrdered property value
     * @param qtyOrdered The qtyOrdered to set
     */
    public void setQtyOrdered(String qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }
    /**
     * Gets the qtyShipped property
     * @return Returns the qtyShipped
     */
    public String getQtyShipped() {
        return this.qtyShipped;
    }
    /**
     * Sets the qtyShipped property value
     * @param qtyShipped The qtyShipped to set
     */
    public void setQtyShipped(String qtyShipped) {
        this.qtyShipped = qtyShipped;
    }
    /**
     * Gets the invoiceQtyShip property
     * @return Returns the invoiceQtyShip
     */
    public String getInvoiceQtyShip() {
        return this.invoiceQtyShip;
    }
    /**
     * Sets the invoiceQtyShip property value
     * @param invoiceQtyShip The invoiceQtyShip to set
     */
    public void setInvoiceQtyShip(String invoiceQtyShip) {
        this.invoiceQtyShip = invoiceQtyShip;
    }
    /**
     * Gets the unitInvoiceAmt property
     * @return Returns the unitInvoiceAmt
     */
    public String getUnitInvoiceAmt() {
        return this.unitInvoiceAmt;
    }
    /**
     * Sets the unitInvoiceAmt property value
     * @param unitInvoiceAmt The unitInvoiceAmt to set
     */
    public void setUnitInvoiceAmt(String unitInvoiceAmt) {
        this.unitInvoiceAmt = unitInvoiceAmt;
    }
    /**
     * Gets the taxInvoiceAmt property
     * @return Returns the taxInvoiceAmt
     */
    public String getTaxInvoiceAmt() {
        return this.taxInvoiceAmt;
    }
    /**
     * Sets the taxInvoiceAmt property value
     * @param taxInvoiceAmt The taxInvoiceAmt to set
     */
    public void setTaxInvoiceAmt(String taxInvoiceAmt) {
        this.taxInvoiceAmt = taxInvoiceAmt;
    }
    /**
     * Gets the totalAmt property
     * @return Returns the totalAmt
     */
    public String getTotalAmt() {
        return this.totalAmt;
    }
    /**
     * Sets the totalAmt property value
     * @param totalAmt The totalAmt to set
     */
    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }
    /**
     * Gets the dateShipped property
     * @return Returns the dateShipped
     */
    public Date getDateShipped() {
        return this.dateShipped;
    }
    /**
     * Sets the dateShipped property value
     * @param dateShipped The dateShipped to set
     */
    public void setDateShipped(Date dateShipped) {
        this.dateShipped = dateShipped;
    }
    /**
     * Gets the poPrice property
     * @return Returns the poPrice
     */
    public String getPoPrice() {
        return this.poPrice;
    }
    /**
     * Sets the poPrice property value
     * @param poPrice The poPrice to set
     */
    public void setPoPrice(String poPrice) {
        this.poPrice = poPrice;
    }
    /**
     * Gets the percentageDiff property
     * @return Returns the percentageDiff
     */
    public String getPercentageDiff() {
        return this.percentageDiff;
    }
    /**
     * Sets the percentageDiff property value
     * @param percentageDiff The percentageDiff to set
     */
    public void setPercentageDiff(String percentageDiff) {
        this.percentageDiff = percentageDiff;
    }
    /**
     * Gets the lineReadOnly property
     * @return Returns the lineReadOnly
     */
    public boolean isLineReadOnly() {
        return this.lineReadOnly;
    }
    /**
     * Sets the lineReadOnly property value
     * @param lineReadOnly The lineReadOnly to set
     */
    public void setLineReadOnly(boolean lineReadOnly) {
        this.lineReadOnly = lineReadOnly;
    }

    
    
    
}
