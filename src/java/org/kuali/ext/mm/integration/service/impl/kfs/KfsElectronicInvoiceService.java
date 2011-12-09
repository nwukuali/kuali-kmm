/**
 *
 */
package org.kuali.ext.mm.integration.service.impl.kfs;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Header;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Header.From;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Header.Sender;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Header.To;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailOrder;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailOrder.InvoiceDetailOrderInfo;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailRequestHeader;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailRequestHeader.InvoiceDetailHeaderIndicator;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailRequestHeader.InvoiceDetailLineIndicator;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailRequestHeader.InvoiceDetailPaymentTerm;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailRequestHeader.InvoicePartner;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailSummary;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailSummary.DepositAmount;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailSummary.DueAmount;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailSummary.ShippingAmount;
import org.kuali.ext.mm.b2b.cxml.kfs.types.CXML.Request.InvoiceDetailRequest.InvoiceDetailSummary.SpecialHandlingAmount;
import org.kuali.ext.mm.b2b.cxml.kfs.types.Contact;
import org.kuali.ext.mm.b2b.cxml.kfs.types.Credential;
import org.kuali.ext.mm.b2b.cxml.kfs.types.Description;
import org.kuali.ext.mm.b2b.cxml.kfs.types.GrossAmount;
import org.kuali.ext.mm.b2b.cxml.kfs.types.InvoiceDetailDiscount;
import org.kuali.ext.mm.b2b.cxml.kfs.types.InvoiceDetailItem;
import org.kuali.ext.mm.b2b.cxml.kfs.types.InvoiceDetailItem.InvoiceDetailItemReference;
import org.kuali.ext.mm.b2b.cxml.kfs.types.InvoiceDetailItem.InvoiceDetailItemReference.ItemID;
import org.kuali.ext.mm.b2b.cxml.kfs.types.InvoiceDetailShipping;
import org.kuali.ext.mm.b2b.cxml.kfs.types.Money;
import org.kuali.ext.mm.b2b.cxml.kfs.types.Name;
import org.kuali.ext.mm.b2b.cxml.kfs.types.NetAmount;
import org.kuali.ext.mm.b2b.cxml.kfs.types.ObjectFactory;
import org.kuali.ext.mm.b2b.cxml.kfs.types.OrderReference;
import org.kuali.ext.mm.b2b.cxml.kfs.types.OrderReference.DocumentReference;
import org.kuali.ext.mm.b2b.cxml.kfs.types.Phone;
import org.kuali.ext.mm.b2b.cxml.kfs.types.Phone.TelephoneNumber;
import org.kuali.ext.mm.b2b.cxml.kfs.types.Phone.TelephoneNumber.CountryCode;
import org.kuali.ext.mm.b2b.cxml.kfs.types.PostalAddress;
import org.kuali.ext.mm.b2b.cxml.kfs.types.PostalAddress.Country;
import org.kuali.ext.mm.b2b.cxml.kfs.types.SubtotalAmount;
import org.kuali.ext.mm.b2b.cxml.kfs.types.Tax;
import org.kuali.ext.mm.b2b.cxml.kfs.types.UnitPrice;
import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CheckinDetail;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.common.sys.MMPropertyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.service.FinancialElectronicInvoiceService;
import org.kuali.ext.mm.integration.service.FinancialPurchasingService;
import org.kuali.ext.mm.integration.service.FinancialVendorService;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialPaymentTermType;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorAddress;
import org.kuali.ext.mm.integration.vnd.businessobject.FinancialVendorDetail;
import org.kuali.ext.mm.service.impl.CheckinOrderServiceImpl;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.kns.service.DateTimeService;
import org.kuali.rice.kns.service.KualiConfigurationService;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;


/**
 * @author harsha07
 */
public class KfsElectronicInvoiceService implements FinancialElectronicInvoiceService {
    private static final String PURAP_ELECTRONIC_INVOICE_XSD = "/static/xsd/purap/electronicInvoice.xsd";

    public static class PaymentMethod {
        public static final String CHECK = "P";
        public static final String WIRE = "W";
        public static final String DRAFT = "F";
    }

    private static final String ISD_CODE_1 = "1";
    private static final String USD = "USD";
    private static final String LANG_EN = "en";
    private static final Logger LOG = Logger.getLogger(KfsElectronicInvoiceService.class);
    private static final String BLANK = "";
    private static final String DEFAULT_CNTRY_US = "US";
    private static final String ROLE_SHIP_TO = "shipTo";
    private static final String ROLE_REMIT_TO = "remitTo";
    private static final String ROLE_BILL_TO = "billTo";
    private static final String PURPOSE_STANDARD = "standard";
    private static final String OPERATION_NEW = "new";
    private static final String CXML_KFS_TYPES_PKG = "org.kuali.ext.mm.b2b.cxml.kfs.types";
    private static final String DOMAIN_NETWORK_ID = "NetworkId";
    private static final String DOMAIN_VENDOR_NUMBER = "VendorNumber";
    private static final String TOTAL_TAX = "Total Tax";
    private FinancialPurchasingService financialPurchasingService;
    private FinancialVendorService financialVendorService;

    public KfsElectronicInvoiceService(FinancialPurchasingService financialPurchasingService,
            FinancialVendorService financialVendorService) {
        this.financialPurchasingService = financialPurchasingService;
        this.financialVendorService = financialVendorService;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialElectronicInvoiceService#submitInvoice(org.kuali.ext.mm.document.CheckinDocument,
     *      java.io.OutputStream)
     */
    public void submitInvoice(CheckinDocument checkinDoc, OutputStream outputStream) {
        OrderDocument orderDocument = checkinDoc.getOrderDocument();
        Address billAddress = orderDocument.getBillingAddress();
        Address shipAddress = orderDocument.getShippingAddress();
        Integer vendorHeaderGeneratedId = orderDocument.getVendorHeaderGeneratedId();
        Integer vendorDetailAssignedId = orderDocument.getVendorDetailAssignedId();
        if (vendorHeaderGeneratedId == null || vendorDetailAssignedId == null) {
            return;
        }
        FinancialVendorDetail vendorDetail = financialVendorService.getVendorDetail(
                vendorHeaderGeneratedId, vendorDetailAssignedId);
        if (vendorDetail == null) {
            return;
        }
        FinancialVendorAddress vendorAddress = financialVendorService.getVendorDefaultAddress(
                vendorHeaderGeneratedId, vendorDetailAssignedId, "RM", orderDocument.getCampusCd());
        if (vendorAddress == null) {
            vendorAddress = financialVendorService.getVendorDefaultAddress(vendorHeaderGeneratedId,
                    vendorDetailAssignedId, "PO", orderDocument.getCampusCd());
        }
        if (vendorAddress == null) {
            return;
        }
        HashMap<Integer, OrderDetail> orderDetailMap = new HashMap<Integer, OrderDetail>();
        HashMap<Integer, Integer> orderAcceptedQtyMap = new HashMap<Integer, Integer>();

        prepareOrderDetailMap(checkinDoc, orderDetailMap, orderAcceptedQtyMap);
        if (orderDetailMap.isEmpty()) {
            return;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(CXML_KFS_TYPES_PKG);
            ObjectFactory factory = new ObjectFactory();
            CXML cxml = factory.createCXML();
            cxml
                    .setPayloadID(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                            + "@kmm");
            cxml.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()));
            cxml.setVersion("1.2.014");
            // cxml.setLang("en");
            Header header = factory.createCXMLHeader();
            cxml.setHeader(header);
            // set header from
            From headerFrom = factory.createCXMLHeaderFrom();
            headerFrom.setCredential(createCredential(factory, DOMAIN_VENDOR_NUMBER,
                    vendorHeaderGeneratedId + "-" + vendorDetailAssignedId));
            header.setFrom(headerFrom);
            // set header to
            To headerTo = factory.createCXMLHeaderTo();
            headerTo.setCredential(createCredential(factory, DOMAIN_NETWORK_ID, orderDocument
                    .getCampusCd()));
            header.setTo(headerTo);
            // set header sender
            Sender headerSender = factory.createCXMLHeaderSender();
            headerSender.setCredential(createCredential(factory, DOMAIN_VENDOR_NUMBER,
                    vendorHeaderGeneratedId + "-" + vendorDetailAssignedId));
            headerSender.setUserAgent("");
            header.setSender(headerSender);
            // setup request
            Request cxmlRequest = factory.createCXMLRequest();
            cxml.setRequest(cxmlRequest);
            InvoiceDetailRequest detailRequest = new InvoiceDetailRequest();
            cxmlRequest.setInvoiceDetailRequest(detailRequest);
            // begin InvoiceDetailRequestHeader
            InvoiceDetailRequestHeader detailRequestHeader = new InvoiceDetailRequestHeader();
            detailRequest.setInvoiceDetailRequestHeader(detailRequestHeader);
            DateTimeService dateTimeService = SpringContext.getBean(DateTimeService.class);
            detailRequestHeader.setInvoiceDate(dateTimeService.toDateString(dateTimeService
                    .getCurrentDate()));
            detailRequestHeader
                    .setInvoiceID(StringUtils.isEmpty(checkinDoc.getVendorRefNbr()) ? checkinDoc
                            .getDocumentNumber() : checkinDoc.getVendorRefNbr());
            detailRequestHeader.setOperation(OPERATION_NEW);
            detailRequestHeader.setPurpose(PURPOSE_STANDARD);
            InvoiceDetailHeaderIndicator detailHeaderIndicator = new InvoiceDetailHeaderIndicator();
            detailRequestHeader.setInvoiceDetailHeaderIndicator(detailHeaderIndicator);
            InvoiceDetailLineIndicator detailLineIndicator = new InvoiceDetailLineIndicator();
            detailRequestHeader.setInvoiceDetailLineIndicator(detailLineIndicator);
            InvoicePartner invoicePartner = new InvoicePartner();
            Contact billtoContact = factory.createContact();
            billtoContact.setAddressID(orderDocument.getBillingAddressId());
            billtoContact.setRole(ROLE_BILL_TO);
            Name name = factory.createName();
            name.setValue(billAddress.getAddressName());
            billtoContact.setName(name);
            PostalAddress postalAddr = createPostalAddress(factory, billAddress.getAddressLine1(),
                    billAddress.getAddressLine2(), billAddress.getAddressCityName(), billAddress
                            .getAddressStateCode(), billAddress.getAddressPostalCode(), billAddress
                            .getAddressCountryCode(), billAddress.getAddressCountryCode());
            billtoContact.getPostalAddress().add(postalAddr);
            String[] parsedPhone = parsePhoneNumber(billAddress.getAddressPhoneNumber());
            Phone phone = createPhone(factory, DEFAULT_CNTRY_US, billAddress
                    .getAddressPhoneNumber(), parsedPhone[0], parsedPhone[1], parsedPhone[2]);
            billtoContact.getPhone().add(phone);
            invoicePartner.getContact().add(billtoContact);
            Contact remitToContact = factory.createContact();
            remitToContact.setAddressID(vendorAddress.getVendorAddressGeneratedIdentifier()
                    .toString());
            remitToContact.setRole(ROLE_REMIT_TO);
            name = factory.createName();
            name.setValue(orderDocument.getVendorNm());
            remitToContact.setName(name);
            postalAddr = createPostalAddress(factory, vendorAddress.getVendorLine1Address(),
                    vendorAddress.getVendorLine2Address(), vendorAddress.getVendorCityName(),
                    vendorAddress.getVendorStateCode(), vendorAddress.getVendorZipCode(),
                    vendorAddress.getVendorCountryCode(), vendorAddress.getVendorCountryCode());
            remitToContact.getPostalAddress().add(postalAddr);
            // phone = createPhone(factory, BLANK, BLANK, BLANK, BLANK, BLANK);
            // remitToContact.getPhone().add(phone);
            invoicePartner.getContact().add(remitToContact);
            detailRequestHeader.getInvoicePartner().add(invoicePartner);

            // create InvoiceDetailShipping
            InvoiceDetailShipping detailShipping = factory.createInvoiceDetailShipping();
            Contact shipToContact = factory.createContact();
            shipToContact.setAddressID(shipAddress.getAddressId());
            shipToContact.setRole(ROLE_SHIP_TO);
            name = factory.createName();
            name.setValue(shipAddress.getAddressName());
            shipToContact.setName(name);
            postalAddr = createPostalAddress(factory, shipAddress.getAddressLine1(), shipAddress
                    .getAddressLine2(), shipAddress.getAddressCityName(), shipAddress
                    .getAddressStateCode(), shipAddress.getAddressPostalCode(), shipAddress
                    .getAddressCountryCode(), shipAddress.getAddressCountryCode());
            shipToContact.getPostalAddress().add(postalAddr);
            parsedPhone = parsePhoneNumber(shipAddress.getAddressPhoneNumber());
            phone = createPhone(factory, DEFAULT_CNTRY_US, shipAddress.getAddressPhoneNumber(),
                    parsedPhone[0], parsedPhone[1], parsedPhone[2]);
            shipToContact.getPhone().add(phone);
            detailShipping.getContact().add(shipToContact);
            detailRequestHeader.setInvoiceDetailShipping(detailShipping);
            // set InvoiceDetailPaymentTerm
            InvoiceDetailPaymentTerm detailPaymentTerm = new InvoiceDetailPaymentTerm();
            String vendorPaymentTermsCode = vendorDetail.getVendorPaymentTermsCode();
            FinancialPaymentTermType paymentTermType = null;

            if (StringUtils.isNotBlank(vendorPaymentTermsCode)
                    && (paymentTermType = financialVendorService
                            .getPaymentTermType(vendorPaymentTermsCode)) != null) {
                detailPaymentTerm.setPaymentTermsCode(vendorPaymentTermsCode);
                detailPaymentTerm.setPayInNumberOfDays(paymentTermType.getVendorNetDueNumber());
                BigDecimal vendorPaymentTermsPercent = paymentTermType
                        .getVendorPaymentTermsPercent();
                detailPaymentTerm
                        .setPercentageRate(vendorPaymentTermsPercent == null ? BigDecimal.ZERO
                                : vendorPaymentTermsPercent);
            }
            else {
                // default is 7 days due
                detailPaymentTerm.setPaymentTermsCode("00N07");
                detailPaymentTerm.setPayInNumberOfDays(7);
                detailPaymentTerm.setPercentageRate(BigDecimal.ZERO);
            }
            detailPaymentTerm.setPaymentMethodCode(PaymentMethod.CHECK);
            detailRequestHeader.getInvoiceDetailPaymentTermOrPaymentTerm().add(detailPaymentTerm);
            // end of InvoiceDetailRequestHeader
            // begin InvoiceDetailOrder
            InvoiceDetailOrder detailOrder = new InvoiceDetailOrder();
            InvoiceDetailOrderInfo detailOrderInfo = new InvoiceDetailOrderInfo();
            OrderReference orderReference = new OrderReference();
            orderReference.setOrderDate(dateTimeService.toDateString(orderDocument
                    .getCreationDate() == null ? dateTimeService.getCurrentDate() : orderDocument
                    .getCreationDate()));
            Integer purchaseOrderId = financialPurchasingService
                    .getPurchaseOrderIdByRequisitionId(orderDocument.getReqsId());
            orderReference.setOrderID(purchaseOrderId == null ? "" : purchaseOrderId.toString());
            DocumentReference documentReference = factory.createOrderReferenceDocumentReference();
            documentReference.setPayloadID("NA");
            documentReference.setValue(BLANK);
            orderReference.setDocumentReference(documentReference);
            detailOrderInfo.getContent().add(orderReference);
            detailOrder.getContent().add(detailOrderInfo);
            KualiDecimal itemTotal = KualiDecimal.ZERO;
            KualiDecimal taxTotal = KualiDecimal.ZERO;
            for (OrderDetail orderDetail : orderDetailMap.values()) {
                // include the item in this Invoice only if belongs to the same PO and quantity is non-zero
                Integer acceptedQty = orderAcceptedQtyMap.get(orderDetail.getOrderDetailId());
                if (ObjectUtils.isNotNull(orderDetail)
                        && purchaseOrderId.equals(orderDetail.getPoId()) && acceptedQty != null
                        && acceptedQty > 0) {
                    KualiDecimal buyAcceptQuantity = getBuyAcceptQuantity(orderDetail
                            .getCatalogItem(), acceptedQty);
                    if (buyAcceptQuantity != null && buyAcceptQuantity.isNonZero()) {
                        MMDecimal buyItemCostAmt = orderDetail.getBuyItemCostAmount() == null ? MMDecimal.ZERO
                                : orderDetail.getBuyItemCostAmount();
                        KualiDecimal lineCost = new KualiDecimal(buyItemCostAmt.doubleValue()
                                * buyAcceptQuantity.doubleValue());
                        if (lineCost.isNonZero()) {
                            itemTotal = itemTotal.add(lineCost);
                            MMDecimal orderItemTaxAmt = orderDetail.getOrderItemTaxAmt() == null ? MMDecimal.ZERO
                                    : orderDetail.getOrderItemTaxAmt();
                            taxTotal = taxTotal.add(new KualiDecimal(orderItemTaxAmt.doubleValue()
                                    * buyAcceptQuantity.doubleValue()));
                            detailOrder.getContent().add(
                                    createInvoiceDetailItem(factory, orderDetail
                                            .getItemLineNumber(), new BigDecimal(buyAcceptQuantity
                                            .doubleValue()), orderDetail.getBuyUnitOfIssueCd(),
                                            buyItemCostAmt.toString(), orderDetail
                                                    .getShortOrderItemDetailDesc(), String
                                                    .valueOf(lineCost), orderDetail
                                                    .getItemLineNumber()));
                        }
                    }
                }
            }
            detailRequest.getInvoiceDetailOrder().add(detailOrder);
            // begin InvoiceDetailSummary
            KualiDecimal totalInvoiceAmount = itemTotal.add(taxTotal);
            if (totalInvoiceAmount.isZero()) {
                File invoiceXML = new File(CheckinOrderServiceImpl.invoiceXMLFileName(checkinDoc));
                if (invoiceXML.exists()) {
                    invoiceXML.delete();
                }
                return;
            }

            InvoiceDetailSummary detailSummary = factory
                    .createCXMLRequestInvoiceDetailRequestInvoiceDetailSummary();
            SubtotalAmount subtotalAmount = factory.createInvoiceDetailItemSubtotalAmount();
            subtotalAmount.setMoney(createMoney(factory, itemTotal.toString()));
            detailSummary.setSubtotalAmount(subtotalAmount);
            Tax tax = factory.createTax();
            tax.setDescription(createDescription(factory, TOTAL_TAX));
            tax.setMoney(createMoney(factory, taxTotal.toString()));
            detailSummary.setTax(tax);
            SpecialHandlingAmount specialHandlingAmount = factory
                    .createCXMLRequestInvoiceDetailRequestInvoiceDetailSummarySpecialHandlingAmount();
            specialHandlingAmount.setMoney(createMoney(factory, "0.00"));
            detailSummary.setSpecialHandlingAmount(specialHandlingAmount);
            ShippingAmount shippingAmount = factory
                    .createCXMLRequestInvoiceDetailRequestInvoiceDetailSummaryShippingAmount();
            shippingAmount.setMoney(createMoney(factory, "0.00"));
            detailSummary.setShippingAmount(shippingAmount);
            GrossAmount grossAmount = factory.createGrossAmount();
            grossAmount.setMoney(createMoney(factory, totalInvoiceAmount.toString()));
            detailSummary.setGrossAmount(grossAmount);
            InvoiceDetailDiscount detailDiscount = factory.createInvoiceDetailDiscount();
            detailDiscount.setMoney(createMoney(factory, "0.00"));
            detailSummary.setInvoiceDetailDiscount(detailDiscount);
            NetAmount netAmount = factory.createNetAmount();
            netAmount.setMoney(createMoney(factory, totalInvoiceAmount.toString()));
            detailSummary.setNetAmount(netAmount);
            DepositAmount depositAmount = factory
                    .createCXMLRequestInvoiceDetailRequestInvoiceDetailSummaryDepositAmount();
            depositAmount.setMoney(createMoney(factory, "0.00"));
            detailSummary.setDepositAmount(depositAmount);
            DueAmount dueAmount = factory
                    .createCXMLRequestInvoiceDetailRequestInvoiceDetailSummaryDueAmount();
            dueAmount.setMoney(createMoney(factory, totalInvoiceAmount.toString()));
            detailSummary.setDueAmount(dueAmount);
            detailRequest.setInvoiceDetailSummary(detailSummary);
            // end InvoiceDetailSummary
            // end InvoiceDetailOrder
            Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(
                            new URL(SpringContext.getBean(KualiConfigurationService.class)
                                    .getPropertyString(MMPropertyConstants.FINANCE_SYSTEM_URL)
                                    + PURAP_ELECTRONIC_INVOICE_XSD));
            Marshaller marshaller = context.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(cxml, outputStream);
        }
        catch (Exception e) {
            LOG.error("", e);
            throw new RuntimeException(e);
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                }
                catch (IOException e) {
                    LOG.error(e, e);
                }
            }
        }
    }

    /**
     * @param checkinDoc
     * @param orderDetailMap
     * @param orderAcceptedQtyMap
     */
    private void prepareOrderDetailMap(CheckinDocument checkinDoc,
            HashMap<Integer, OrderDetail> orderDetailMap,
            HashMap<Integer, Integer> orderAcceptedQtyMap) {
        List<CheckinDetail> checkinDetails = checkinDoc.getCheckinDetails();
        for (CheckinDetail checkinDetail : checkinDetails) {
            OrderDetail orderDetail = checkinDetail.getOrderDetail();
            if (ObjectUtils.isNotNull(orderDetail) && checkinDetail.getAcceptedItemQty() != null
                    && checkinDetail.getAcceptedItemQty() > 0) {
                if (orderDetailMap.get(orderDetail.getOrderDetailId()) == null) {
                    orderDetailMap.put(orderDetail.getOrderDetailId(), orderDetail);
                    orderAcceptedQtyMap.put(orderDetail.getOrderDetailId(), checkinDetail
                            .getAcceptedItemQty());
                }
                else {
                    orderAcceptedQtyMap.put(orderDetail.getOrderDetailId(), (orderAcceptedQtyMap
                            .get(orderDetail.getOrderDetailId()) + checkinDetail
                            .getAcceptedItemQty()));
                }
            }
        }
    }

    public KualiDecimal getBuyAcceptQuantity(CatalogItem catalogItem, Integer orderItemQty) {
        if (ObjectUtils.isNotNull(catalogItem) && ObjectUtils.isNotNull(catalogItem.getStock())
                && catalogItem.getStock().getBuyUnitOfIssueRt() != null && orderItemQty != null) {
            MMDecimal buyUnitOfIssueRt = catalogItem.getStock().getBuyUnitOfIssueRt();
            return new KualiDecimal(orderItemQty.doubleValue() / buyUnitOfIssueRt.doubleValue());
        }
        return orderItemQty == null ? KualiDecimal.ZERO : new KualiDecimal(orderItemQty.intValue());
    }

    private InvoiceDetailItem createInvoiceDetailItem(ObjectFactory factory, int invLineNumber,
            BigDecimal qty, String unitOfMeas, String unitAmt, String itmDesc, String subTotAmount,
            int lineNumber) {
        InvoiceDetailItem invoiceDetailItem = factory.createInvoiceDetailItem();
        invoiceDetailItem.setInvoiceLineNumber(invLineNumber);
        invoiceDetailItem.setQuantity(qty);
        invoiceDetailItem.setUnitOfMeasure(unitOfMeas);
        UnitPrice unitPrice = factory.createUnitPrice();
        Money money = createMoney(factory, unitAmt);
        unitPrice.setMoney(money);
        invoiceDetailItem.setUnitPrice(unitPrice);
        InvoiceDetailItemReference detailItemReference = factory
                .createInvoiceDetailItemInvoiceDetailItemReference();
        ItemID itemReferenceItemID = factory
                .createInvoiceDetailItemInvoiceDetailItemReferenceItemID();
        itemReferenceItemID.setSupplierPartAuxiliaryID(BLANK);
        itemReferenceItemID.setSupplierPartID(BLANK);
        detailItemReference.setItemID(itemReferenceItemID);
        Description desc = createDescription(factory, itmDesc);
        detailItemReference.setDescription(desc);
        detailItemReference.setLineNumber(lineNumber);
        invoiceDetailItem.setInvoiceDetailItemReference(detailItemReference);
        SubtotalAmount subtotalAmount = factory.createInvoiceDetailItemSubtotalAmount();
        subtotalAmount.setMoney(createMoney(factory, subTotAmount));
        invoiceDetailItem.setSubtotalAmount(subtotalAmount);
        return invoiceDetailItem;
    }

    /**
     * @param factory
     * @param itmDesc
     * @return
     */
    private Description createDescription(ObjectFactory factory, String itmDesc) {
        Description desc = factory.createDescription();
        desc.setLang(LANG_EN);
        desc.setValue(itmDesc);
        return desc;
    }


    private Money createMoney(ObjectFactory factory, String amount) {
        Money money = factory.createMoney();
        money.setCurrency(USD);
        money.setValue(amount);
        return money;
    }

    private PostalAddress createPostalAddress(ObjectFactory factory, String street1,
            String street2, String city, String state, String postalCode, String isoCode,
            String countryNm) {
        PostalAddress postalAddress = factory.createPostalAddress();
        postalAddress.getStreet().add(street1);
        postalAddress.getStreet().add(street2);
        postalAddress.setCity(city);
        postalAddress.setState(state);
        postalAddress.setPostalCode(postalCode);
        Country country = factory.createPostalAddressCountry();
        country.setIsoCountryCode(isoCode);
        country.setValue(countryNm);
        postalAddress.setCountry(country);
        return postalAddress;
    }

    private Phone createPhone(ObjectFactory factory, String isoCode, String phoneName,
            String cntryCodeVal, String areaCode, String number) {
        Phone phone = factory.createPhone();
        phone.setName(phoneName);
        TelephoneNumber telephoneNumber = factory.createPhoneTelephoneNumber();
        telephoneNumber.setAreaOrCityCode(areaCode);
        CountryCode countryCode = factory.createPhoneTelephoneNumberCountryCode();
        countryCode.setIsoCountryCode(isoCode);
        countryCode.setValue(cntryCodeVal);
        telephoneNumber.setCountryCode(countryCode);
        telephoneNumber.setNumber(number);
        phone.setTelephoneNumber(telephoneNumber);
        return phone;
    }


    private Credential createCredential(ObjectFactory factory, String domain, String identity) {
        Credential credential = factory.createCredential();
        credential.setDomain(domain);
        credential.setIdentity(identity);
        return credential;
    }

    private String[] parsePhoneNumber(String phoneNumber) {
        String[] phoneNos = new String[] { ISD_CODE_1, BLANK, BLANK };
        if (phoneNumber == null) {
            return null;
        }
        String phoneStr = phoneNumber.trim();
        if (phoneStr.length() == 10) {
            phoneNos[1] = phoneStr.substring(0, 3);
            phoneNos[2] = phoneStr.substring(3);
        }
        else if (phoneStr.length() == 12) {
            phoneNos[1] = phoneStr.substring(0, 3);
            phoneNos[2] = phoneStr.substring(4);
        }
        return phoneNos;
    }

    /**
     * Gets the financialPurchasingService property
     * 
     * @return Returns the financialPurchasingService
     */
    public FinancialPurchasingService getFinancialPurchasingService() {
        return this.financialPurchasingService;
    }


    /**
     * Sets the financialPurchasingService property value
     * 
     * @param financialPurchasingService The financialPurchasingService to set
     */
    public void setFinancialPurchasingService(FinancialPurchasingService financialPurchasingService) {
        this.financialPurchasingService = financialPurchasingService;
    }

}
