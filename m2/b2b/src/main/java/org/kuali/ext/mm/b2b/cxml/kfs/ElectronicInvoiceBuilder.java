/**
 * 
 */
package org.kuali.ext.mm.b2b.cxml.kfs;

import org.kuali.ext.mm.b2b.cxml.kfs.types.*;
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
import org.kuali.ext.mm.b2b.cxml.kfs.types.InvoiceDetailItem.InvoiceDetailItemReference;
import org.kuali.ext.mm.b2b.cxml.kfs.types.InvoiceDetailItem.InvoiceDetailItemReference.ItemID;
import org.kuali.ext.mm.b2b.cxml.kfs.types.OrderReference.DocumentReference;
import org.kuali.ext.mm.b2b.cxml.kfs.types.Phone.TelephoneNumber;
import org.kuali.ext.mm.b2b.cxml.kfs.types.Phone.TelephoneNumber.CountryCode;
import org.kuali.ext.mm.b2b.cxml.kfs.types.PostalAddress.Country;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author harsha07
 */
public class ElectronicInvoiceBuilder {
    private static final String SERVER_URL = "https://fin.qual.ebsp.msu.edu/kfs-qa/";

    public static void main(String[] args) {
        try {
            JAXBContext context = JAXBContext.newInstance("org.kuali.ext.mm.b2b.cxml.kfs.types");
            ObjectFactory factory = new ObjectFactory();
            CXML cxml = factory.createCXML();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            cxml.setPayloadID(fmt.format(new Date()) + "@kmm");
            cxml.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()));
            cxml.setVersion("1.2.014");
            Header header = factory.createCXMLHeader();
            cxml.setHeader(header);
            // set header from
            From headerFrom = factory.createCXMLHeaderFrom();
            headerFrom.setCredential(createCredential(factory, "VendorNumber", "4116-0"));
            header.setFrom(headerFrom);
            // set header to
            To headerTo = factory.createCXMLHeaderTo();
            headerTo.setCredential(createCredential(factory, "NetworkId", "IU"));
            header.setTo(headerTo);
            // set header sender
            Sender headerSender = factory.createCXMLHeaderSender();
            headerSender.setCredential(createCredential(factory, "VendorNumber", "4116-0"));
            headerSender.setUserAgent("dfdf");
            header.setSender(headerSender);

            // setup request
            Request cxmlRequest = factory.createCXMLRequest();
            cxml.setRequest(cxmlRequest);
            InvoiceDetailRequest detailRequest = new InvoiceDetailRequest();
            cxmlRequest.setInvoiceDetailRequest(detailRequest);
            // begin InvoiceDetailRequestHeader
            InvoiceDetailRequestHeader detailRequestHeader = new InvoiceDetailRequestHeader();
            detailRequest.setInvoiceDetailRequestHeader(detailRequestHeader);
            detailRequestHeader.setInvoiceDate("03/16/2010");
            detailRequestHeader.setInvoiceID("1621312204");
            detailRequestHeader.setOperation("new");
            detailRequestHeader.setPurpose("standard");

            InvoiceDetailHeaderIndicator detailHeaderIndicator = new InvoiceDetailHeaderIndicator();
            detailRequestHeader.setInvoiceDetailHeaderIndicator(detailHeaderIndicator);
            InvoiceDetailLineIndicator detailLineIndicator = new InvoiceDetailLineIndicator();
            detailRequestHeader.setInvoiceDetailLineIndicator(detailLineIndicator);
            InvoicePartner invoicePartner = new InvoicePartner();
            Contact billtoContact = factory.createContact();
            billtoContact.setAddressID("771457960");
            billtoContact.setRole("billTo");
            Name name = factory.createName();
            name.setValue("EL - HANNAH TECHNOLOGY CENTER");
            billtoContact.setName(name);
            PostalAddress postalAddr = createPostalAddress(factory, "4700 S HAGADORN RD", "s",
                    "E Lansing", "MI", "48823", "US", "UNITED STATES");
            billtoContact.getPostalAddress().add(postalAddr);
            Phone phone = createPhone(factory, "517-353-3121", "517", "US", "1", "353-3121");
            billtoContact.getPhone().add(phone);
            invoicePartner.getContact().add(billtoContact);

            Contact remitToContact = factory.createContact();
            remitToContact.setAddressID("1307145315");
            remitToContact.setRole("remitTo");
            name = factory.createName();
            name.setValue("OfficeMax");
            remitToContact.setName(name);
            postalAddr = createPostalAddress(factory, "2020 East Grand River Avenue", "", "Okemos",
                    "MI", "48864", "US", "UNITED STATES");
            remitToContact.getPostalAddress().add(postalAddr);
            phone = createPhone(factory, "", "", "", "", "");
            remitToContact.getPhone().add(phone);
            invoicePartner.getContact().add(remitToContact);
            detailRequestHeader.getInvoicePartner().add(invoicePartner);

            // create InvoiceDetailShipping
            InvoiceDetailShipping detailShipping = factory.createInvoiceDetailShipping();
            Contact shipToContact = factory.createContact();
            shipToContact.setAddressID("2097273543");
            shipToContact.setRole("shipTo");
            name = factory.createName();
            name.setValue("EL - HANNAH TECHNOLOGY CENTER");
            shipToContact.setName(name);
            postalAddr = createPostalAddress(factory, "4700 S HAGADORN RD", "s", "E Lansing", "MI",
                    "48823", "US", "UNITED STATES");
            shipToContact.getPostalAddress().add(postalAddr);
            phone = createPhone(factory, "517-353-3121", "517", "US", "1", "353-3121");
            shipToContact.getPhone().add(phone);
            detailShipping.getContact().add(shipToContact);
            detailRequestHeader.setInvoiceDetailShipping(detailShipping);
            // set InvoiceDetailPaymentTerm
            InvoiceDetailPaymentTerm detailPaymentTerm = new InvoiceDetailPaymentTerm();
            detailPaymentTerm.setPayInNumberOfDays(11);
            detailPaymentTerm.setPercentageRate(new BigDecimal(0));
            detailPaymentTerm.setPaymentTermsCode("00N07");
            detailPaymentTerm.setPaymentMethodCode("CHCK");
            detailRequestHeader.getInvoiceDetailPaymentTermOrPaymentTerm().add(detailPaymentTerm);
            // end of InvoiceDetailRequestHeader
            // begin InvoiceDetailOrder
            InvoiceDetailOrder detailOrder = new InvoiceDetailOrder();
            InvoiceDetailOrderInfo detailOrderInfo = new InvoiceDetailOrderInfo();
            OrderReference orderReference = new OrderReference();
            orderReference.setOrderDate("03/16/2010");
            orderReference.setOrderID("1032");
            DocumentReference documentReference = factory.createOrderReferenceDocumentReference();
            documentReference.setPayloadID("NA");
            documentReference.setValue("d");
            orderReference.setDocumentReference(documentReference);
            detailOrderInfo.getContent().add(orderReference);
            detailOrder.getContent().add(detailOrderInfo);
            detailOrder.getContent().add(
                    createInvoiceDetailItem(factory, 1, new BigDecimal(75.00), "EA", "1299.00",
                            "Stuff", "97425.00", 1));
            detailOrder.getContent().add(
                    createInvoiceDetailItem(factory, 2, new BigDecimal(100.00), "EA", "999.99",
                            "More Stuff", "99999.0000", 2));
            detailRequest.getInvoiceDetailOrder().add(detailOrder);
            // begin InvoiceDetailSummary

            InvoiceDetailSummary detailSummary = factory
                    .createCXMLRequestInvoiceDetailRequestInvoiceDetailSummary();
            SubtotalAmount subtotalAmount = factory.createInvoiceDetailItemSubtotalAmount();
            subtotalAmount.setMoney(createMoney(factory, "197424.00"));
            detailSummary.setSubtotalAmount(subtotalAmount);
            Tax tax = factory.createTax();
            tax.setDescription(createDescription(factory, "Total Tax"));
            tax.setMoney(createMoney(factory, "0.00"));
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
            grossAmount.setMoney(createMoney(factory, "197424.00"));
            detailSummary.setGrossAmount(grossAmount);
            InvoiceDetailDiscount detailDiscount = factory.createInvoiceDetailDiscount();
            detailDiscount.setMoney(createMoney(factory, "0.00"));
            detailSummary.setInvoiceDetailDiscount(detailDiscount);
            NetAmount netAmount = factory.createNetAmount();
            netAmount.setMoney(createMoney(factory, "197424.00"));
            detailSummary.setNetAmount(netAmount);
            DepositAmount depositAmount = factory
                    .createCXMLRequestInvoiceDetailRequestInvoiceDetailSummaryDepositAmount();
            depositAmount.setMoney(createMoney(factory, "0.00"));
            detailSummary.setDepositAmount(depositAmount);
            DueAmount dueAmount = factory
                    .createCXMLRequestInvoiceDetailRequestInvoiceDetailSummaryDueAmount();
            dueAmount.setMoney(createMoney(factory, "197424.00"));
            detailSummary.setDueAmount(dueAmount);
            detailRequest.setInvoiceDetailSummary(detailSummary);
            // end InvoiceDetailSummary
            // end InvoiceDetailOrder
            Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new URL("https://findev.ebsp.msu.edu/kfs-dev/static/xsd/purap/electronicInvoice.xsd"));
            Marshaller marshaller = context.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(cxml, new FileOutputStream("/temp/po-sample.xml"));
            System.out.println("DONE");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param factory
     * @param invLineNumber TODO
     * @param qty TODO
     * @param unitOfMeas TODO
     * @param unitAmt TODO
     * @param itmDesc TODO
     * @param subTotAmount TODO
     * @param lineNumber TODO
     * @return
     */
    private static InvoiceDetailItem createInvoiceDetailItem(ObjectFactory factory,
            int invLineNumber, BigDecimal qty, String unitOfMeas, String unitAmt, String itmDesc,
            String subTotAmount, int lineNumber) {
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
        itemReferenceItemID.setSupplierPartAuxiliaryID("ddd");
        itemReferenceItemID.setSupplierPartID("ddd");
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
    private static Description createDescription(ObjectFactory factory, String itmDesc) {
        Description desc = factory.createDescription();
        desc.setLang("en");
        desc.setValue(itmDesc);
        return desc;
    }

    /**
     * @param factory
     * @param amount TODO
     * @return
     */
    private static Money createMoney(ObjectFactory factory, String amount) {
        Money money = factory.createMoney();
        money.setCurrency("USD");
        money.setValue(amount);
        return money;
    }

    /**
     * @param factory
     * @param street1 TODO
     * @param street2 TODO
     * @param city TODO
     * @param state TODO
     * @param postalCode TODO
     * @param isoCode TODO
     * @param countryNm TODO
     */
    private static PostalAddress createPostalAddress(ObjectFactory factory, String street1,
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

    private static Phone createPhone(ObjectFactory factory, String phoneName, String areaCode,
            String isoCode, String cntryCodeVal, String number) {
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

    /**
     * @param factory
     * @param domain TODO
     * @param identity TODO
     * @return
     */
    private static Credential createCredential(ObjectFactory factory, String domain, String identity) {
        Credential credential = factory.createCredential();
        credential.setDomain(domain);
        credential.setIdentity(identity);
        return credential;
    }

}
