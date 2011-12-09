/**
 * 
 */
package org.kuali.ext.mm.b2b.cxml.demo;

import java.io.File;
import java.util.List;

import org.kuali.ext.mm.b2b.cxml.types.CXML;
import org.kuali.ext.mm.b2b.cxml.types.DocumentReference;
import org.kuali.ext.mm.b2b.cxml.types.InvoiceDetailHeaderIndicator;
import org.kuali.ext.mm.b2b.cxml.types.InvoiceDetailItem;
import org.kuali.ext.mm.b2b.cxml.types.InvoiceDetailItemReference;
import org.kuali.ext.mm.b2b.cxml.types.InvoiceDetailOrder;
import org.kuali.ext.mm.b2b.cxml.types.InvoiceDetailOrderInfo;
import org.kuali.ext.mm.b2b.cxml.types.InvoiceDetailRequest;
import org.kuali.ext.mm.b2b.cxml.types.InvoiceDetailRequestHeader;
import org.kuali.ext.mm.b2b.cxml.types.OrderReference;
import org.kuali.ext.mm.b2b.cxml.types.Request;
import org.kuali.ext.mm.b2b.cxml.types.SubtotalAmount;
import org.kuali.ext.mm.b2b.cxml.types.UnitPrice;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;

/**
 * @author harsha07
 */
public class B2bCxmlInvoiceParser {
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            CXML cxml = CxmlUtil.unmarshal(new File(
                "C:\\java\\projects\\mm-dev\\doc\\Sample_Invoice_cXML.xml"));
            System.out.println(cxml);
            System.out.println(CxmlUtil.getSharedSecret(cxml));
            Request request = cxml.getRequest();
            InvoiceDetailRequest invoice = (InvoiceDetailRequest) request.getCxmlRequests()
                    .getValue();
            System.out.println(invoice);
            InvoiceDetailRequestHeader idrh = invoice.getInvoiceDetailRequestHeader();
            System.out.println("Purpose: " + idrh.getPurpose());
            System.out.println("Operation: " + idrh.getOperation());
            System.out.println("Invoice Id: " + idrh.getInvoiceID());
            System.out.println("Invoice Date: " + idrh.getInvoiceDate());

            InvoiceDetailHeaderIndicator idhi = idrh.getInvoiceDetailHeaderIndicator();
            String isHeaderInvoice = idhi.getIsHeaderInvoice();
            System.out.println("Is Header Invoice:" + isHeaderInvoice);

            List<InvoiceDetailOrder> invoiceDetailOrders = invoice.getInvoiceDetailOrder();
            for (InvoiceDetailOrder invoiceDetailOrder : invoiceDetailOrders) {
                InvoiceDetailOrderInfo invoiceDetailOrderInfo = invoiceDetailOrder
                        .getInvoiceDetailOrderInfo();
                Object content = invoiceDetailOrderInfo.getContent().get(0);
                if (OrderReference.class.isAssignableFrom(content.getClass())) {
                    OrderReference orderReference = (OrderReference) content;
                    System.out.println("Order Id:" + orderReference.getOrderID());
                    System.out.println("Order Date:" + orderReference.getOrderDate());
                    DocumentReference docRef = orderReference.getDocumentReference();
                    System.out.println("Original Order Cxml Id: " + docRef.getPayloadID());
                }
                List<Object> invoiceItems = invoiceDetailOrder
                        .getInvoiceDetailItemOrInvoiceDetailServiceItem();
                for (Object object : invoiceItems) {
                    if (InvoiceDetailItem.class.isAssignableFrom(object.getClass())) {
                        InvoiceDetailItem item = (InvoiceDetailItem) object;
                        System.out.println("Line:" + item.getInvoiceLineNumber());
                        System.out.println("Qty:" + item.getQuantity());
                        System.out.println("UoM:" + item.getUnitOfMeasure());
                        UnitPrice unitPrice = item.getUnitPrice();
                        System.out.println("Unit Price:" + unitPrice.getMoney().getContent());
                        InvoiceDetailItemReference itemReference = item
                                .getInvoiceDetailItemReference();
                        System.out.println("Mfr Id:" + itemReference.getManufacturerPartID());
                        System.out.println("Serial Number:" + itemReference.getSerialNumber());
                        System.out.println("Supplier Part Id:"
                                + itemReference.getItemID().getSupplierPartID());
                        SubtotalAmount subtotal = item.getSubtotalAmount();
                        System.out.println("Sub Total Amount:" + subtotal.getMoney().getContent());
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
