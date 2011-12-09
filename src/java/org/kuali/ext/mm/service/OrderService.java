package org.kuali.ext.mm.service;

import java.util.List;

import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.rice.kns.util.KualiDecimal;

public interface OrderService {
    /**
     * Line Sub-Total = (cost + markup) * qty
     * 
     * @param detail
     * @return sub-total price for a line item
     */
    public Double computeLineTotal(OrderDetail detail);

    /**
     * Line Total = Line SubTotal + (unit tax * qty)
     * 
     * @param detail
     * @return total price for a line item with tax included
     */
    public KualiDecimal computeLineTotalWithTax(OrderDetail detail);

    /**
     * Collection Sub-Total
     *  
     * @param details
     * @return sub-total for a list of OrderDetail objects 
     */
    public KualiDecimal computeSubTotal(List<OrderDetail> details);

    /**
     * Collection Tax Total
     * 
     * @param details
     * @return tax total for list of OrderDetail objects
     */
    public Double computeTaxTotal(List<OrderDetail> details);

    /**
     * Collection Total
     * 
     * @param details
     * @return total (with tax) for list of OrderDetail objects 
     */
    public KualiDecimal computeTotal(List<OrderDetail> details);
    
    /**
     * Computes the 'amount saved' for the list of Order Details.
     * 
     * @param details
     * @return sum of negative mark-ups on each detail.
     */
    public KualiDecimal computeAmountSaved(List<OrderDetail> details);

    /**
     * Takes an OrderDocument and determines how it should be handled by the system.
     * OrderDocuments with Recurring Order information are stopped here and set to CLOSED.
     * All other OrderDocuments produce a Sales Instance and Pick List Lines.
     * Creates a requisition if OrderDocument is SUPPLY or STOCK. 
     * 
     * @param orderDocument
     */
    public void processOrderDocument(OrderDocument orderDocument);

    /**
     * @param orderDocument
     * @return a List of OrderDetails from the OrderDocument which have been approved
     * (during the workflow) for purchase.  
     */
    public List<OrderDetail> getApprovedOrderDetails(OrderDocument orderDocument);

    /**
     * Creates a default accounting line which can be added automatically to an OrderDocument.
     * 
     * @param order
     * @param profile
     * @return An Accounts object with default accounting line information populated based on
     * the customer profile and the default catalog object code.
     */
    public Accounts createDefaultAccountingLine(OrderDocument order, Profile profile);

    /**
     * Computes the remaining (unaccounted for) cost of each OrderDetail on an OrderDocument
     * and adds the document-level accounting lines to those OrderDetail objects which do not
     * already account for 100% of the cost.  Ensuring that the sum of an OrderDetail accounting
     * line section is ALWAYS 100%.
     * 
     * example: 
     * document-level accounting line 1 (doc1) 100%
     * detail-level accounting line 1 (dtl1) 25%
     * detail-level accounting line 2 (dtl2) 25%
     * 
     * dtl1 + dtl2 = 50%
     * doc1 is then distributed down to the detail-level as dtl3 for 100% of the remainder:
     * detail-level accounting line 3 (dtl3) 50%
     * 
     * @param document
     */
    public void distributeDocumentLevelAccountingLines(OrderDocument document);

    /**
     * Sets the OrderStatusCode for an OrderDocument and all of its OrderDetails
     * 
     * @param document
     * @param orderStatusCode
     */
    public void setOrderDocumentStatus(OrderDocument document, String orderStatusCode);

    /**
     * Used only if line item approval is enabled.
     * 
     * @param detail
     * @return  true if order status is NOT disapproved.
     */
    public boolean isLineApproved(OrderDetail detail);

    /**
     * @param document - order document
     * @return true if the total amount for the order level accounting lines equals the order document total amount.
     */
    public boolean isValidAccountingLinesTotalAmount(OrderDocument document);
    
    /**
     * @param orderDetail
     * @return true if order detail total amount is equal to the accounting line total amount
     */
    public boolean isValidDetailLevelAccountingLinesAmount(OrderDetail orderDetail);

    /**
     * @param orderDetail
     * @return the total amount assigned to accounting lines for orderDetail. 
     */
    public KualiDecimal getOrderDetailAccountsTotalAmount(OrderDetail orderDetail);

    /**
     * @param finObjectCd
     * @return true if finObjectCd is signifies an Asset
     */
    public boolean isAssetObjectCode(String finObjectCd);

    /**
     * Validates Accounts data and writes to the GlobalVariables messageMap if
     * errors are found. 
     * 
     * @param newAccountingLine
     * @param errorPath
     * @return true if newAccountingLine contains valid data, otherwise false.
     */
    public boolean validateAccountingLine(Accounts newAccountingLine, String errorPath);

    /**
     * Computes the line cost excluding markup and tax amounts
     *
     * @param detail Order Detail
     * @return Cost amount
     */
    public KualiDecimal computeLineCostTotal(OrderDetail detail);

    /**
     * Computes the line cost for an OrderDocument excluding markup and tax amounts
     * 
     * @param order
     * @return Cost amount
     */
    public KualiDecimal computeLineCostTotal(OrderDocument order);
    
    /**
     * Determines OrderDetail completeness based on all pick list lines
     * being in a verified status and having no pending backorders.
     * 
     * @param orderDetailId
     * @return true if the order detail is complete
     */
    public boolean isOrderDetailComplete(Integer orderDetailId);
    
    /**
     * @param detail
     * @return The quantity of orderDetail that as been shipped from the warehouse. 
     */
    public Integer getOrderDetailQuantityShipped(OrderDetail detail);
}
