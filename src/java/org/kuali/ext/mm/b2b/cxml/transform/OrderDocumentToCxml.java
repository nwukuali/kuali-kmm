/**
 *
 */
package org.kuali.ext.mm.b2b.cxml.transform;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.kuali.ext.mm.b2b.cxml.types.BillTo;
import org.kuali.ext.mm.b2b.cxml.types.CxmlPayment;
import org.kuali.ext.mm.b2b.cxml.types.CxmlPaymentElement;
import org.kuali.ext.mm.b2b.cxml.types.Description;
import org.kuali.ext.mm.b2b.cxml.types.Money;
import org.kuali.ext.mm.b2b.cxml.types.OrderRequest;
import org.kuali.ext.mm.b2b.cxml.types.OrderRequestHeader;
import org.kuali.ext.mm.b2b.cxml.types.ShipTo;
import org.kuali.ext.mm.b2b.cxml.types.Tax;
import org.kuali.ext.mm.b2b.cxml.types.Total;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.PunchOutVendor;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.service.PunchOutVendorService;



/**
 * @author harsha07
 */
public class OrderDocumentToCxml implements CxmlTransformer<OrderRequest, OrderDocument> {

    /**
     * @see org.kuali.ext.mm.b2b.cxml.transform.CxmlTransformer#transform(java.lang.Object)
     */
    public OrderRequest transform(OrderDocument orderDocument, Object... options) {
        OrderRequest orderRequest = new OrderRequest();
        PunchOutVendorService punchOutVendorService = SpringContext.getBean(PunchOutVendorService.class);
        PunchOutVendor punchOutVendor = punchOutVendorService.getPunchOutVendorByCatalogId(orderDocument.getOrderDetails().get(0).getCatalogItem().getCatalogId());
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        
      //TODO: remove hardcoded currency
        String currencyCode = "USD";
        OrderRequestHeader requestHeader = new OrderRequestHeader();
        requestHeader.setOrderID(String.valueOf(orderDocument.getDocumentNumber()));
        requestHeader.setType("new");
        requestHeader.setOrderDate(timestamp);
        
        requestHeader.setTotal(createTotal(orderDocument.getOrderTotalAmount().toString(), currencyCode));
        Tax tax = new Tax();
        Description taxDescription = new Description();        
        tax.setMoney(CxmlUtil.createMoneyElement(orderDocument.getTax().toString(), currencyCode));
        tax.setDescription(taxDescription);
        requestHeader.setTax(tax);
        ShipTo shipTo = new ShipTo();
        AddressToCxml addressToCxml = new AddressToCxml();
        shipTo.setAddress(addressToCxml.transform(orderDocument.getShippingAddress(), new Object[]{Boolean.TRUE}));
        requestHeader.setShipTo(shipTo);
        BillTo billTo = new BillTo();
        billTo.setAddress(addressToCxml.transform(orderDocument.getBillingAddress()));
        requestHeader.setBillTo(billTo);
        CxmlPayment payment = new CxmlPayment();
        CxmlPaymentElement paymentElement = new CxmlPaymentElement();        
        paymentElement.setName(punchOutVendor.getProcurementCard().getCardName());        
        paymentElement.setNumber(punchOutVendor.getProcurementCard().getCardNumber());        
        paymentElement.setExpiration(punchOutVendor.getProcurementCard().getCardExpireDate());
        payment.setCxmlPaymentElement(paymentElement);
        requestHeader.setPayment(payment);
        orderRequest.setOrderRequestHeader(requestHeader);
        
        for(OrderDetail detail : orderDocument.getOrderDetails())
            orderRequest.getItemOut().add(new OrderDetailToCxml().transform(detail));
        
        return orderRequest;
    }
    
    private Total createTotal(String amount, String currency) {
        Total moneyElement = new Total();
        Money money = new Money();
        money.setContent(amount);
        money.setCurrency(currency);
        moneyElement.setMoney(money);
        return moneyElement;
    }
    
}
