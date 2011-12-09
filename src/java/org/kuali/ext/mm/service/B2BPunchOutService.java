package org.kuali.ext.mm.service;

import org.kuali.ext.mm.b2b.cxml.invoice.B2bInvoiceStatus;
import org.kuali.ext.mm.b2b.cxml.types.CXML;
import org.kuali.ext.mm.b2b.cxml.types.Header;
import org.kuali.ext.mm.b2b.cxml.types.ObjectFactory;
import org.kuali.ext.mm.b2b.cxml.types.PunchOutOrderMessage;
import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.PunchOutVendor;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.document.OrderDocument;



public interface B2BPunchOutService {
    
    /**
     * Parses and populates a shopping cart and its details from a vendor's 
     * cxml order request 
     * 
     * @param customerProfile
     * @param cxml
     * @return a populated shopping cart from the vendor cxml
     */
    public ShoppingCart parsePunchOutOrderMessage(Profile customerProfile, CXML cxml);

    /**
     * @param catalog
     * @param profile
     * @param cxmlReceiveUrl - url that the vendor will use to send request back to us
     * @return CXML populated for initiating a punch out request from a vendor
     * @throws Exception
     */
    public CXML createPunchOutSetupRequest(Catalog catalog, Profile profile, String cxmlReceiveUrl) throws Exception;

    /**
     * Saves the Puchout Vendor purchase request record to the database.
     * 
     * @param orderId
     * @param payloadId
     * @param profileId
     * @param cxml
     */
    public void saveVendorPurchaseRequest(String orderId, String payloadId, String profileId, CXML cxml);
    
    public CatalogItem createPunchOutCatalogItem(Catalog punchoutCatalog);
    
    public CXML createPurchaseOrderRequest(OrderDocument orderDocument);
    
    public String sendPurchaseOrderRequest(CXML cxml, String toUrl) throws Exception;
    
    public ShoppingCart createShoppingCartFromPurchaseRequest(PunchOutOrderMessage orderMessage);
    
    /**
     * Saves the cxml invoice from the punchout vendor into the database and
     * sends proper response back to vendor.
     * 
     * @param invoice
     * @param outputStream
     */
    public B2bInvoiceStatus receiveCxmlInvoice(CXML invoice) ;    
     
    /**
     * Request types:
     * INVOICE_REQUEST = "I";
     * ORDER_MESSAGE = "O";
     * NONE = "N";  
     * 
     * @param cxml
     * @return String representation of the CXML request type, or N if none can be determined.
     */
    public String getCXMLRequestType(CXML cxml);
    
    /**
     * Override this method for vendor specific request parameters
     * 
     * @return request parameter name for cxml data in the vendor request.
     */
    public String getCXMLRequestParameter();
    
    /**
     * Creates CXML header element
     * @param punchOutVendor Vendor Info
     * @param factory Object Factory
     * @return Header element
     */
    public Header createHeader(PunchOutVendor punchOutVendor, ObjectFactory factory);
    
    /**
     * Will return TRUE if a request can be accepted by the system
     * 
     * @param cxml CXML
     * @return true when request is acceptable
     */
    public boolean canAcceptRequest(CXML cxml, String deploymentMode);
    
    public boolean isInvoiceTypeSupported(CXML cxml);
}
