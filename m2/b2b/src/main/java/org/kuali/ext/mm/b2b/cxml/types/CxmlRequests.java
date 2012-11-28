//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.06.21 at 02:56:33 PM EDT 
//


package org.kuali.ext.mm.b2b.cxml.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for cxml.requests complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cxml.requests">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}cxml.requests"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cxml.requests", propOrder = {
    "cxmlRequests"
})
@XmlSeeAlso({
    Request.class
})
public class CxmlRequests {

    @XmlElementRef(name = "cxml.requests", type = JAXBElement.class)
    protected JAXBElement<?> cxmlRequests;

    /**
     * Gets the value of the cxmlRequests property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link OrganizationDataRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link SubscriptionListRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link PunchOutSetupRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link java.lang.Object }{@code >}
     *     {@link JAXBElement }{@code <}{@link StatusUpdateRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link CatalogUploadRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link GetPendingRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link OrderRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link CopyRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link SubscriptionStatusUpdateRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link MasterAgreementRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link ProfileRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link ProviderSetupRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link AuthRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link SupplierListRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link DataRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link SupplierDataRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link InvoiceDetailRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link SubscriptionContentRequest }{@code >}
     *     
     */
    public JAXBElement<?> getCxmlRequests() {
        return cxmlRequests;
    }

    /**
     * Sets the value of the cxmlRequests property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link OrganizationDataRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link SubscriptionListRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link PunchOutSetupRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link java.lang.Object }{@code >}
     *     {@link JAXBElement }{@code <}{@link StatusUpdateRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link CatalogUploadRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link GetPendingRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link OrderRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link CopyRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link SubscriptionStatusUpdateRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link MasterAgreementRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link ProfileRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link ProviderSetupRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link AuthRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link SupplierListRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link DataRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link SupplierDataRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link InvoiceDetailRequest }{@code >}
     *     {@link JAXBElement }{@code <}{@link SubscriptionContentRequest }{@code >}
     *     
     */
    public void setCxmlRequests(JAXBElement<?> value) {
        this.cxmlRequests = ((JAXBElement<?> ) value);
    }

}
