package org.kuali.ext.mm.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.b2b.cxml.invoice.B2bInvoice;
import org.kuali.ext.mm.b2b.cxml.invoice.B2bInvoiceStatus;
import org.kuali.ext.mm.b2b.cxml.transform.CxmlToB2bInvoice;
import org.kuali.ext.mm.b2b.cxml.transform.CxmlToShoppingCart;
import org.kuali.ext.mm.b2b.cxml.transform.OrderDocumentToCxml;
import org.kuali.ext.mm.b2b.cxml.types.*;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.service.B2BPunchOutService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.PunchOutVendorService;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.xml.bind.JAXBElement;
import java.lang.Object;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author schneppd
 *
 */
public class B2BPunchOutServiceImpl implements B2BPunchOutService {
    
    private BusinessObjectService businessObjectService;
    
    private PunchOutVendorService punchOutVendorService;

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    public void setPunchOutVendorService(PunchOutVendorService punchOutVendorService) {
        this.punchOutVendorService = punchOutVendorService;
    }

    public PunchOutVendorService getPunchOutVendorService() {
        return punchOutVendorService;
    }
    
    /**
     * @see org.kuali.ext.mm.service.B2BPunchOutService#createPunchOutSetupRequest(org.kuali.ext.mm.businessobject.Catalog, org.kuali.ext.mm.businessobject.Profile, java.lang.String)
     */
    public CXML createPunchOutSetupRequest(Catalog catalog, Profile profile, String cxmlReceiveUrl) throws Exception {        
        PunchOutVendor punchOutVendor = getPunchOutVendorService().getPunchOutVendorByCatalogId(catalog.getCatalogId());       
        String operation = "create";
        String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date());
        String buyerCookie = StringUtils.upperCase(catalog.getCatalogCd()) + "-" + StringUtils.upperCase(profile.getPrincipalName()) + "-" + timestamp; 
        
        ObjectFactory factory = new ObjectFactory();
        CXML cxml = factory.createCXML();
        CxmlUtil.createCxmlHeader(cxml,"POUT");
        // create header
        Header header = createHeader(punchOutVendor, factory);
        cxml.setHeader(header);
        // create PunchOutSetupRequest
        Request request = new Request();
        request.setDeploymentMode(CxmlUtil.getDeploymentMode());
        PunchOutSetupRequest punchOutSetupRequest = new PunchOutSetupRequest();
        punchOutSetupRequest.setOperation(operation);
        punchOutSetupRequest.setBuyerCookie(new Any(buyerCookie));
        BrowserFormPost browserFormPost = new BrowserFormPost();
        URL url = new URL();
        url.setContent(cxmlReceiveUrl);
        browserFormPost.setURL(url);
        punchOutSetupRequest.setBrowserFormPost(browserFormPost);
        SupplierSetup supplierSetup = new SupplierSetup();
        URL supUrl = new URL();
        supUrl.setContent(punchOutVendor.getPunchOutUrl());
        supplierSetup.setURL(supUrl);
        punchOutSetupRequest.setSupplierSetup(supplierSetup);
        request.setCxmlRequests(factory.createPunchOutSetupRequest(punchOutSetupRequest));
        cxml.setRequest(request);
        
        return cxml;        
    }
        
    
    
    /**
     * @see org.kuali.ext.mm.service.B2BPunchOutService#getCXMLRequestType(org.kuali.ext.mm.b2b.cxml.types.CXML)
     */
    public String getCXMLRequestType(CXML cxml) {
        Message message = cxml.getMessage();
        Request request = cxml.getRequest();
        if (message != null) {
            JAXBElement<?> cxmlMessages = message.getCxmlMessages();                
            if (cxmlMessages != null) {
                Object orderMessage = cxmlMessages.getValue();                    
                if (PunchOutOrderMessage.class.isAssignableFrom(orderMessage.getClass()))
                    return MMConstants.CXMLRequestType.ORDER_MESSAGE;                    
            }
        }
        else if(request != null) {
            JAXBElement<?> cxmlRequest = request.getCxmlRequests();
            if (cxmlRequest != null) {
                Object requestNode = cxmlRequest.getValue();                    
                if (InvoiceDetailRequest.class.isAssignableFrom(requestNode.getClass()))
                    return MMConstants.CXMLRequestType.INVOICE_REQUEST;                    
            }
        }
        return MMConstants.CXMLRequestType.NONE;
    }
    
    /**
     * @see org.kuali.ext.mm.service.B2BPunchOutService#parsePunchOutOrderMessage(org.kuali.ext.mm.businessobject.Profile, org.kuali.ext.mm.b2b.cxml.types.CXML)
     */
    public ShoppingCart parsePunchOutOrderMessage(Profile customerProfile, CXML cxml) {
        ShoppingCart punchOutCart = null;

        Message message = cxml.getMessage();
        if (message != null) {
            JAXBElement<?> cxmlMessages = message.getCxmlMessages();                
            if (cxmlMessages != null) {
                PunchOutVendor poVendor = getPunchOutVendorFromCXML(cxml);
                if(poVendor != null) {
                    B2BPunchOutService b2bPunchOutService = MMServiceLocator.getB2BPunchOutServiceLocator()
                            .getB2BPunchOutService(poVendor.getVendorCredentialDomain(), poVendor.getVendorIdentity());
                    
                    Object orderMessage = cxmlMessages.getValue();                    
                    if (PunchOutOrderMessage.class.isAssignableFrom(orderMessage.getClass())) {
                        List<ShopCartDetail> detailsWithNonZeroPrice = new ArrayList<ShopCartDetail>();                    
                        punchOutCart = b2bPunchOutService.createShoppingCartFromPurchaseRequest((PunchOutOrderMessage) orderMessage);
                        CatalogItem genericItem = getPunchOutVendorService().getDefaultPunchOutCatalogItem(poVendor);
                        
                        for(ShopCartDetail cartDetail : punchOutCart.getShopCartDetails()) {
                        	if(cartDetail.getShopCartItemPriceAmt().isGreaterThan(new KualiDecimal(0.0))
                        	        && genericItem != null) {
                                cartDetail.setCatalogItem(genericItem);
                                cartDetail.setCatalogItemId(genericItem.getCatalogItemId());
                                detailsWithNonZeroPrice.add(cartDetail);
                            }
                        }
                        punchOutCart.setShopCartDetails(detailsWithNonZeroPrice);
                    }
                }
            }         
        }
        
        return punchOutCart;
    }
    
    public void saveVendorPurchaseRequest(String orderId, String payloadId, String profileId, CXML cxml) {
        CxmlPurchaseRequest purchaseRequest = new CxmlPurchaseRequest();
        purchaseRequest.setKeyId(payloadId);
        purchaseRequest.setProfileId(profileId);
        purchaseRequest.setOrderId(orderId);
        purchaseRequest.setPurchaseRequestXml(CxmlUtil.cxmlToString(cxml));
        getBusinessObjectService().save(purchaseRequest);
    }
    
    
    /**
     * @see org.kuali.ext.mm.service.B2BPunchOutService#createPurchaseOrderRequest(org.kuali.ext.mm.document.OrderDocument)
     */
    public CXML createPurchaseOrderRequest(OrderDocument orderDocument)  {
        PunchOutVendor punchOutVendor = getPunchOutVendorService().getPunchOutVendorByCatalogId(orderDocument.getOrderDetails().get(0).getCatalogItem().getCatalogId());      
        ObjectFactory factory = new ObjectFactory();
        CXML cxml = factory.createCXML();
        CxmlUtil.createCxmlHeader(cxml, "ORD."+ orderDocument.getDocumentNumber());
        // create header
        Header header = createHeader(punchOutVendor, factory);
        cxml.setHeader(header);
        // create PunchOutSetupRequest
        Request request = new Request();
        request.setDeploymentMode(CxmlUtil.getDeploymentMode());
        OrderRequest orderRequest = new OrderDocumentToCxml().transform(orderDocument);
        request.setCxmlRequests(factory.createOrderRequest(orderRequest));
        cxml.setRequest(request);
        return cxml;
    }
    
    /**
     * @see org.kuali.ext.mm.service.B2BPunchOutService#sendPurchaseOrderRequest(org.kuali.ext.mm.b2b.cxml.types.CXML, java.lang.String)
     */
    public String sendPurchaseOrderRequest(CXML cxml, String toUrl) throws Exception {
        CXML cxmlResponse = CxmlUtil.sendRequest(cxml, toUrl);

        String errorContent = "";
        if (cxmlResponse == null)
            errorContent = "No Response.";
        else if (!cxmlResponse.getResponse().getStatus().getCode().startsWith("2")) {
            errorContent = CxmlUtil.cxmlToString(cxmlResponse);
        }

        return errorContent;
    }
    public boolean canAcceptRequest(CXML cxml, String deploymentMode) {
        if (CxmlUtil.canAccept(deploymentMode)) {
            PunchOutVendor punchOutVendor = getPunchOutVendorFromCXML(cxml);
            String sharedSecret = CxmlUtil.getSharedSecret(cxml);
            if (punchOutVendor != null
                    && StringUtils.equals(sharedSecret, punchOutVendor.getVendorSharedSecret())) {
                return true;
            }
        }
        return false;
    }
    
    public B2bInvoiceStatus receiveCxmlInvoice(CXML invoice) {
        B2bInvoiceStatus status = new B2bInvoiceStatus();
        PunchOutVendor punchOutVendor = getPunchOutVendorFromCXML(invoice);
        String sharedSecret = CxmlUtil.getSharedSecret(invoice);
        if (punchOutVendor != null && StringUtils.equals(sharedSecret, punchOutVendor.getVendorSharedSecret())) {
            CxmlToB2bInvoice transformer = new CxmlToB2bInvoice();
            B2bInvoice b2bInvoice = transformer.transform(invoice);
            if (b2bInvoice != null) {
                status = b2bInvoice.validate();
                if (status.isValid()) {
                    return saveCxmlInvoice(invoice, punchOutVendor);
                }
            }
        }
        else {
            status = new B2bInvoiceStatus(false, "401", "Not Acceptable",
                "Vendor/Credentials not recognized.");
        }
        return status;
    }
    
    /**
     * Saves a record of the cxmlInvoice received from the vendor.
     * 
     * @param invoice
     */
    protected B2bInvoiceStatus saveCxmlInvoice(CXML invoice, PunchOutVendor punchOutVendor) {                
        Object cxmlRequests = invoice.getRequest().getCxmlRequests().getValue();        
        if(InvoiceDetailRequest.class.isAssignableFrom(cxmlRequests.getClass())) {            
            InvoiceDetailRequest invoiceDetailRequest = (InvoiceDetailRequest) cxmlRequests;
            
            for(InvoiceDetailOrder detailOrder : invoiceDetailRequest.getInvoiceDetailOrder()) {
                String keyId = invoice.getPayloadID();
                CxmlInvoice exist = MMServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(CxmlInvoice.class, keyId);
                if(ObjectUtils.isNotNull(exist)){
                    return new B2bInvoiceStatus(false, "409","Conflict", "Duplicate payload id - " +keyId);
                }
                CxmlInvoice cxmlInvoice = new CxmlInvoice();
                cxmlInvoice.setKeyId(invoice.getPayloadID());
                cxmlInvoice.setInvoiceXml(CxmlUtil.cxmlToString(invoice));
                cxmlInvoice.setInvoiceNbr(invoiceDetailRequest.getInvoiceDetailRequestHeader().getInvoiceID());
                cxmlInvoice.setProcessedInd(false);
                cxmlInvoice.setPunchOutVendorId(punchOutVendor.getPunchOutVendorId());
                InvoiceDetailOrderInfo orderInfo = detailOrder.getInvoiceDetailOrderInfo();
                for(Object detailOrderInfoContent : orderInfo.getContent()) {
                    if(OrderReference.class.isAssignableFrom(detailOrderInfoContent.getClass()))
                        cxmlInvoice.setOrderId(((OrderReference)detailOrderInfoContent).getOrderID());
                }
                getBusinessObjectService().save(cxmlInvoice);
                return new B2bInvoiceStatus(true,"201","Accepted", "Acknowledged Invoice PayloadID: " + invoice.getPayloadID());
            }
        }
        return new B2bInvoiceStatus(false, "417", "Expectation failed.", "No valid invoice found.");
    }

    /**
     * @see org.kuali.ext.mm.service.B2BPunchOutService#createShoppingCartFromPurchaseRequest(org.kuali.ext.mm.b2b.cxml.types.PunchOutOrderMessage)
     */
    public ShoppingCart createShoppingCartFromPurchaseRequest(PunchOutOrderMessage orderMessage) {
        return new CxmlToShoppingCart().transform(orderMessage);
    }
    
    public CatalogItem createPunchOutCatalogItem(Catalog punchoutCatalog) {
        CatalogItem catalogItem = new CatalogItem();
        
        catalogItem.setCatalogId(punchoutCatalog.getCatalogId());
        catalogItem.setDistributorNbr(MMConstants.CatalogItem.GENERIC_DUMMY_DISTRIBUTOR_NBR);
        catalogItem.setCatalogUnitOfIssueCd(MMConstants.CatalogItem.GENERIC_DUMMY_UNIT_OF_ISSUE);
        catalogItem.setCatalogDesc(MMConstants.CatalogItem.GENERIC_DUMMY_DESCRIPTION);
        catalogItem.setActive(true);
        catalogItem.setDisplayableInd(false);
        
        return catalogItem;
    }

    /**
     * Override this method for vendor specific request parameters
     * 
     * @return request parameter name for cxml data in the vendor request.
     */
    public String getCXMLRequestParameter() {
        return MMConstants.PunchOutVendor.DEFAULT_CXML_REQUEST_PARAMETER;
    }
        
    /**
     * Creates the cxml content header, containing the from, to, and sender credentials
     * 
     * @param punchOutVendor
     * @param factory
     * @return a context Header object for the cxml data 
     */
    public Header createHeader(PunchOutVendor punchOutVendor, ObjectFactory factory) {
        Header header = factory.createHeader();
        From from = factory.createFrom();
        
        from.getCredential().add(createCredential(punchOutVendor.getLocalCredentialDomain(), 
                punchOutVendor.getLocalIdentity(), null));
        header.setFrom(from);
        
        To to = factory.createTo();
        to.getCredential().add(createCredential(punchOutVendor.getVendorCredentialDomain(), 
                punchOutVendor.getVendorIdentity(), null));
        header.setTo(to);
        
        Sender sender = factory.createSender();
        sender.getCredential().add(createCredential(punchOutVendor.getLocalCredentialDomain(), 
                punchOutVendor.getLocalIdentity(), 
                punchOutVendor.getVendorSharedSecret()));
        sender.setUserAgent(punchOutVendor.getLocalUserAgent());
        header.setSender(sender);
        
        return header;
    }
    
    /**
     * Creates a CXML Credential object.
     * 
     * @param domain
     * @param id
     * @param sharedSecret
     * @return a populated CXML Credential object
     */
    protected Credential createCredential(String domain, String id, String sharedSecret) {
        Credential credential = new Credential();
        credential.setDomain(domain);
        Identity identity = new Identity();
        identity.getContent().add(id);
        credential.setIdentity(identity);
        if(StringUtils.isNotBlank(sharedSecret)){
            credential.setSharedSecret(new SharedSecret());
            credential.getSharedSecret().getContent().add(sharedSecret);
        }
        return credential;
    }
    
    protected PunchOutVendor getPunchOutVendorFromCXML(CXML cxml) {
        String senderDomain = cxml.getHeader().getSender().getCredential().get(0).getDomain();
        String senderIdentity = cxml.getHeader().getSender().getCredential().get(0).getIdentity().getFirstContent();
        
        PunchOutVendor poVendor = getPunchOutVendorService().getPunchOutVendorByVendorCredentials(senderDomain, senderIdentity); 
        
        if(poVendor == null) {
            String fromDomain = cxml.getHeader().getFrom().getCredential().get(0).getDomain();
            String fromIdentity = cxml.getHeader().getFrom().getCredential().get(0).getIdentity().getFirstContent();
            poVendor = getPunchOutVendorService().getPunchOutVendorByVendorCredentials(fromDomain, fromIdentity);
        }
        
        return poVendor; 
    } 
    
    public boolean isInvoiceTypeSupported(CXML cxml) {
        boolean retVal = false;
        Request request = cxml.getRequest();
        Object value = request.getCxmlRequests().getValue();
        if (InvoiceDetailRequest.class.isAssignableFrom(value.getClass())) {
            InvoiceDetailRequest invoice = (InvoiceDetailRequest) value;
            InvoiceDetailRequestHeader idrh = invoice.getInvoiceDetailRequestHeader();
            String purpose = idrh.getPurpose().toLowerCase();
            String operation = idrh.getOperation();
            // We don't support header level invoices
            InvoiceDetailHeaderIndicator idhi = idrh.getInvoiceDetailHeaderIndicator();
            if (idhi != null && idhi.getIsHeaderInvoice() != null
                    && "yes".equalsIgnoreCase(idhi.getIsHeaderInvoice())) {
                return false;
            }
            if ("standard".equalsIgnoreCase(purpose) && "new".equalsIgnoreCase(operation)) {
                retVal = true;
            }// FIXME - as per CXML documentation creditMemo is only for header invoice but from the samples it looks like
                // vendors are not following this very strictly
            else if (("creditMemo".equalsIgnoreCase(purpose) || "lineLevelCreditMemo"
                    .equalsIgnoreCase(purpose))
                    && "new".equalsIgnoreCase(operation)) {
                retVal = true;
            }
            else if ("delete".equalsIgnoreCase(operation)) {
                retVal = true;
            }
        }
        return retVal;
    }
}
