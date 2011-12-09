/**
 *
 */
package org.kuali.ext.mm.b2b.cxml.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.kuali.ext.mm.b2b.cxml.types.Accounting;
import org.kuali.ext.mm.b2b.cxml.types.AccountingSegment;
import org.kuali.ext.mm.b2b.cxml.types.Address;
import org.kuali.ext.mm.b2b.cxml.types.Any;
import org.kuali.ext.mm.b2b.cxml.types.BillTo;
import org.kuali.ext.mm.b2b.cxml.types.BrowserFormPost;
import org.kuali.ext.mm.b2b.cxml.types.CXML;
import org.kuali.ext.mm.b2b.cxml.types.Charge;
import org.kuali.ext.mm.b2b.cxml.types.Classification;
import org.kuali.ext.mm.b2b.cxml.types.Country;
import org.kuali.ext.mm.b2b.cxml.types.CountryCode;
import org.kuali.ext.mm.b2b.cxml.types.Credential;
import org.kuali.ext.mm.b2b.cxml.types.CxmlPayment;
import org.kuali.ext.mm.b2b.cxml.types.Distribution;
import org.kuali.ext.mm.b2b.cxml.types.Email;
import org.kuali.ext.mm.b2b.cxml.types.From;
import org.kuali.ext.mm.b2b.cxml.types.Header;
import org.kuali.ext.mm.b2b.cxml.types.Identity;
import org.kuali.ext.mm.b2b.cxml.types.ItemDetail;
import org.kuali.ext.mm.b2b.cxml.types.ItemID;
import org.kuali.ext.mm.b2b.cxml.types.ItemOut;
import org.kuali.ext.mm.b2b.cxml.types.Money;
import org.kuali.ext.mm.b2b.cxml.types.Name;
import org.kuali.ext.mm.b2b.cxml.types.ObjectFactory;
import org.kuali.ext.mm.b2b.cxml.types.OrderRequest;
import org.kuali.ext.mm.b2b.cxml.types.OrderRequestHeader;
import org.kuali.ext.mm.b2b.cxml.types.Phone;
import org.kuali.ext.mm.b2b.cxml.types.PostalAddress;
import org.kuali.ext.mm.b2b.cxml.types.PunchOutSetupRequest;
import org.kuali.ext.mm.b2b.cxml.types.Request;
import org.kuali.ext.mm.b2b.cxml.types.SelectedItem;
import org.kuali.ext.mm.b2b.cxml.types.Sender;
import org.kuali.ext.mm.b2b.cxml.types.SharedSecret;
import org.kuali.ext.mm.b2b.cxml.types.ShipTo;
import org.kuali.ext.mm.b2b.cxml.types.SupplierSetup;
import org.kuali.ext.mm.b2b.cxml.types.Tax;
import org.kuali.ext.mm.b2b.cxml.types.TelephoneNumber;
import org.kuali.ext.mm.b2b.cxml.types.To;
import org.kuali.ext.mm.b2b.cxml.types.Total;
import org.kuali.ext.mm.b2b.cxml.types.URL;
import org.kuali.ext.mm.b2b.cxml.types.UnitPrice;
import org.kuali.ext.mm.b2b.cxml.util.CxmlUtil;

import sun.net.www.protocol.https.HttpsURLConnectionImpl;


/**
 * @author harsha07
 */
public class B2bCxmlBuilder {
    public static void main(String[] args) {
        try {
            createPunchOutSetupRequest();
            System.out.println();
            createOrderRequest();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createPunchOutSetupRequest() throws Exception {
        String domain = "DUNS";
        String ucdt = "ucdt";
        String dell = "dell";
        String deploymentMode = "test";
        String operation = "create";
        String buyerCookie = "DE-SZMATSIS-2009-12-08T14:1:2-08:00";
        String browserFormPostUrl = "http://aampc.ucdavis.edu/mm/returnrequest.cfm";
        String supplierSetupUrl = "https://b2bpreview.dell.com/invoke/B2BDirect.Entry/processDocument";
        String supplierPartID = "Dell";
        String supplierPartAuxiliaryID = "";

        ObjectFactory factory = new ObjectFactory();
        CXML cxml = factory.createCXML();
        createCxmlHeader(cxml);
        // create header
        Header header = createHeader(domain, ucdt, dell, factory);
        cxml.setHeader(header);
        // create PunchOutSetupRequest
        Request request = new Request();
        request.setDeploymentMode(deploymentMode);
        PunchOutSetupRequest punchOutSetupRequest = new PunchOutSetupRequest();
        punchOutSetupRequest.setOperation(operation);
        punchOutSetupRequest.setBuyerCookie(new Any(buyerCookie));
        BrowserFormPost browserFormPost = new BrowserFormPost();
        URL url = new URL();
        url.setContent(browserFormPostUrl);
        browserFormPost.setURL(url);
        punchOutSetupRequest.setBrowserFormPost(browserFormPost);
        SupplierSetup supplierSetup = new SupplierSetup();
        URL supUrl = new URL();
        supUrl.setContent(supplierSetupUrl);
        supplierSetup.setURL(supUrl);
        SelectedItem selectedItem = new SelectedItem();
        ItemID itemID = new ItemID();
        itemID.setSupplierPartID(supplierPartID);
        itemID.setSupplierPartAuxiliaryID(new Any(supplierPartAuxiliaryID));
        selectedItem.setItemID(itemID);
        punchOutSetupRequest.setSelectedItem(selectedItem);
        punchOutSetupRequest.setSupplierSetup(supplierSetup);
        request.setCxmlRequests(factory.createPunchOutSetupRequest(punchOutSetupRequest));
        cxml.setRequest(request);

        java.net.URL vendorUrl = new java.net.URL(supUrl.getContent());
        HttpsURLConnectionImpl connection = (HttpsURLConnectionImpl) vendorUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");

        CxmlUtil.marshal(cxml, connection.getOutputStream());
        CxmlUtil.marshal(cxml, System.out);
    }

    /**
     * @param cxml
     */
    private static void createCxmlHeader(CXML cxml) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        cxml.setPayloadID(fmt.format(new Date()) + "@kmm.com");
        cxml.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
        cxml.setVersion("1.2.021");
        cxml.setLang("enUS");
    }

    private static Header createHeader(String domain, String ucdt, String dell,
            ObjectFactory factory) {
        Header header = factory.createHeader();
        From from = factory.createFrom();
        from.getCredential().add(createCredential(domain, ucdt));
        header.setFrom(from);
        To to = factory.createTo();
        to.getCredential().add(createCredential(domain, dell));
        header.setTo(to);
        Sender sender = factory.createSender();
        sender.getCredential().add(createCredential(domain, ucdt));
        sender.setUserAgent("Kuali Materiel Management System 1.0");
        header.setSender(sender);
        return header;
    }

    public static void createOrderRequest() throws Exception {
        String domain = "DUNS";
        String ucdt = "ucdt";
        String dell = "dell";
        String deploymentMode = "test";
        String totalAmount = "0.00";
        String shipAddrName = "Customer Address";
        String shipDeliverTo = "deliverTo";
        String shipAddrLn1 = "street1";
        String shipAddrLn2 = "street2";
        String shipAddrLn3 = "street3";
        String shipCity = "";
        String shipState = "";
        String shipPostalCode = "";
        String shipCountryCode = "US";
        String shipCountryName = "United States of America";
        String shipEmailId = "email@msu.com";
        String shipEmailName = "default";

        String billDeliverTo = "deliverTo";
        String billAddrLn1 = "street1";
        String billAddrLn2 = "street2";
        String billAddrLn3 = "street3";
        String billCity = "";
        String billState = "";
        String billPostalCode = "";
        String billCountryCode = "US";
        String billCountryName = "United States of America";
        String billEmailId = "email@msu.com";
        String billEmailName = "default";

        String phoneName = "";
        String shipAreaCityCode = "";
        String shipPhoneNo = "";
        String shipExtn = "";

        ObjectFactory factory = new ObjectFactory();
        CXML cxml = factory.createCXML();
        createCxmlHeader(cxml);
        // create header
        Header header = createHeader(domain, ucdt, dell, factory);
        cxml.setHeader(header);
        Request request = new Request();
        request.setDeploymentMode(deploymentMode);
        OrderRequest orderRequest = factory.createOrderRequest();
        OrderRequestHeader orderRequestHeader = factory.createOrderRequestHeader();
        orderRequestHeader.setOrderID("0000015625-1");
        orderRequestHeader.setType("new");
        orderRequestHeader.setOrderDate("2009-12-08 11:02:28.0");
        Total total = factory.createTotal();
        total.setMoney(factory.createMoney(totalAmount));
        orderRequestHeader.setTotal(total);

        ShipTo shipTo = factory.createShipTo();
        Address shipAddress = factory.createAddress();
        shipAddress.setAddressID("UCD");
        Name shipAddrNameObj = factory.createName(shipAddrName);
        shipAddrNameObj.setLang("enUS");
        shipAddress.setName(shipAddrNameObj);
        shipAddress.setPostalAddress(createPostalAddress(shipDeliverTo, shipAddrLn1, shipAddrLn2,
                shipAddrLn3, shipCity, shipState, shipPostalCode, shipCountryCode, shipCountryName,
                factory));
        shipAddress.setEmail(createEmail(shipEmailId, shipEmailName, factory));
        shipAddress.setPhone(createPhone(shipCountryCode, shipCountryName, phoneName,
                shipAreaCityCode, shipPhoneNo, shipExtn, factory));
        shipTo.setAddress(shipAddress);
        orderRequestHeader.setShipTo(shipTo);
        BillTo billTo = factory.createBillTo();
        Address billAddress = factory.createAddress();
        billAddress.setPostalAddress(createPostalAddress(billDeliverTo, billAddrLn1, billAddrLn2,
                billAddrLn3, billCity, billState, billPostalCode, billCountryCode, billCountryName,
                factory));
        billAddress.setEmail(createEmail(billEmailId, billEmailName, factory));
        billTo.setAddress(billAddress);
        orderRequestHeader.setBillTo(billTo);
        // tax
        Tax tax = factory.createTax();
        tax.setDescription(factory.createDescription("taxDescription"));
        Money taxMoney = factory.createMoney("taxAmt");
        tax.setMoney(taxMoney);
        orderRequestHeader.setTax(tax);
        // payment

        CxmlPayment payment = new CxmlPayment();
        orderRequestHeader.setPayment(payment);
        orderRequestHeader.setComments(factory.createComments("Order No:232323"));
        orderRequest.setOrderRequestHeader(orderRequestHeader);
        ItemOut itemOut = createItemOut(factory);
        orderRequest.getItemOut().add(itemOut);
        request.setCxmlRequests(factory.createOrderRequest(orderRequest));
        cxml.setRequest(request);
        CxmlUtil.marshal(cxml, System.out);
    }

    public static ItemOut createItemOut(ObjectFactory factory) {
        ItemOut itemOut = factory.createItemOut();
        ItemID itemId = factory.createItemID();
        itemId.setSupplierPartAuxiliaryID(new Any("supplierPartAuxiliaryID"));
        itemId.setSupplierPartID("supplierPartID");
        itemOut.setItemID(itemId);
        ItemDetail itemDetail = factory.createItemDetail();
        UnitPrice unitPrice = factory.createUnitPrice();
        unitPrice.setMoney(factory.createMoney("unitPriceAmt"));
        itemDetail.setUnitPrice(unitPrice);
        itemDetail.getDescription().add(factory.createDescription("item description"));
        itemDetail.setUnitOfMeasure("UoM");
        Classification classification = factory.createClassification();
        classification.setContent("classification");
        classification.setDomain("domain");
        itemDetail.getClassification().add(classification);

        Distribution distribution = factory.createDistribution();
        Accounting accounting = factory.createAccounting();
        accounting.setName("AccountID");
        AccountingSegment acctSegment = factory.createAccountingSegment();
        acctSegment.setId("acctId");
        acctSegment.setName(factory.createName("acctName"));
        acctSegment.setDescription(factory.createDescription("acctDesc"));
        accounting.getAccountingSegment().add(acctSegment);
        distribution.setAccounting(accounting);
        Charge charge = factory.createCharge();
        charge.setMoney(factory.createMoney("chargeAmount"));
        distribution.setCharge(charge);
        itemOut.getDistribution().add(distribution);
        itemOut.setComments(factory.createComments("item Comments"));
        itemOut.setItemDetail(itemDetail);
        return itemOut;
    }

    private static Phone createPhone(String isoCountryCode, String countryName, String phoneName,
            String areaCityCode, String phoneNo, String extn, ObjectFactory factory) {
        Phone phone = factory.createPhone();
        phone.setName(phoneName);
        TelephoneNumber telephone = factory.createTelephoneNumber();
        CountryCode countryCode = factory.createCountryCode();
        countryCode.setIsoCountryCode(isoCountryCode);
        countryCode.setContent(countryName);
        telephone.setCountryCode(countryCode);
        telephone.setAreaOrCityCode(areaCityCode);
        telephone.setNumber(phoneNo);
        telephone.setExtension(extn);
        phone.setTelephoneNumber(telephone);
        return phone;
    }

    private static Email createEmail(String emailId, String emailName, ObjectFactory factory) {
        Email email = factory.createEmail();
        email.setContent(emailId);
        email.setName(emailName);
        return email;
    }

    private static PostalAddress createPostalAddress(String deliverTo, String addrLn1,
            String addrLn2, String addrLn3, String city, String state, String postalCode,
            String countryCode, String countryName, ObjectFactory factory) {
        PostalAddress postalAddress = factory.createPostalAddress();
        postalAddress.setName("default");
        postalAddress.getDeliverTo().add(deliverTo);
        postalAddress.getDeliverTo().add(addrLn1);
        postalAddress.getStreet().add(addrLn2);
        postalAddress.getStreet().add(addrLn3);
        postalAddress.setCity(city);
        postalAddress.setState(state);
        postalAddress.setPostalCode(postalCode);
        Country country = factory.createCountry();
        country.setIsoCountryCode(countryCode);
        country.setContent(countryName);
        postalAddress.setCountry(country);
        return postalAddress;
    }

    private static Credential createCredential(String domain, String id) {
        Credential credential = new Credential();
        credential.setDomain(domain);
        Identity identity = new Identity();
        identity.getContent().add(id);
        credential.setIdentity(identity);
        credential.setSharedSecret(new SharedSecret());
        credential.getSharedSecret().getContent().add("nopassword");
        return credential;
    }
}
