//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.06.21 at 02:56:33 PM EDT 
//


package org.kuali.ext.mm.b2b.cxml.types;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}InvoiceDetailHeaderIndicator"/>
 *         &lt;element ref="{}InvoiceDetailLineIndicator"/>
 *         &lt;element ref="{}InvoicePartner" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}DocumentReference" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceIDInfo" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceDetailShipping" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{}InvoiceDetailPaymentTerm" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}PaymentTerm" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element ref="{}Period" minOccurs="0"/>
 *         &lt;element ref="{}Comments" minOccurs="0"/>
 *         &lt;element ref="{}Extrinsic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="invoiceID" use="required" type="{}string" />
 *       &lt;attribute name="isInformationOnly">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *             &lt;enumeration value="yes"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="purpose" default="standard">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *             &lt;enumeration value="standard"/>
 *             &lt;enumeration value="creditMemo"/>
 *             &lt;enumeration value="debitMemo"/>
 *             &lt;enumeration value="lineLevelCreditMemo"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="operation" default="new">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *             &lt;enumeration value="new"/>
 *             &lt;enumeration value="delete"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="invoiceDate" use="required" type="{}datetime.tz" />
 *       &lt;attribute name="invoiceOrigin">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *             &lt;enumeration value="supplier"/>
 *             &lt;enumeration value="buyer"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "invoiceDetailHeaderIndicator",
    "invoiceDetailLineIndicator",
    "invoicePartner",
    "documentReference",
    "invoiceIDInfo",
    "invoiceDetailShipping",
    "invoiceDetailPaymentTerm",
    "paymentTerm",
    "period",
    "comments",
    "extrinsic"
})
@XmlRootElement(name = "InvoiceDetailRequestHeader")
public class InvoiceDetailRequestHeader {

    @XmlElement(name = "InvoiceDetailHeaderIndicator", required = true)
    protected InvoiceDetailHeaderIndicator invoiceDetailHeaderIndicator;
    @XmlElement(name = "InvoiceDetailLineIndicator", required = true)
    protected InvoiceDetailLineIndicator invoiceDetailLineIndicator;
    @XmlElement(name = "InvoicePartner")
    protected List<InvoicePartner> invoicePartner;
    @XmlElement(name = "DocumentReference")
    protected DocumentReference documentReference;
    @XmlElement(name = "InvoiceIDInfo")
    protected InvoiceIDInfo invoiceIDInfo;
    @XmlElement(name = "InvoiceDetailShipping")
    protected InvoiceDetailShipping invoiceDetailShipping;
    @XmlElement(name = "InvoiceDetailPaymentTerm")
    protected List<InvoiceDetailPaymentTerm> invoiceDetailPaymentTerm;
    @XmlElement(name = "PaymentTerm")
    protected List<PaymentTerm> paymentTerm;
    @XmlElement(name = "Period")
    protected Period period;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(required = true)
    protected String invoiceID;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isInformationOnly;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String purpose;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String operation;
    @XmlAttribute(required = true)
    protected String invoiceDate;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String invoiceOrigin;

    /**
     * Gets the value of the invoiceDetailHeaderIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailHeaderIndicator }
     *     
     */
    public InvoiceDetailHeaderIndicator getInvoiceDetailHeaderIndicator() {
        return invoiceDetailHeaderIndicator;
    }

    /**
     * Sets the value of the invoiceDetailHeaderIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailHeaderIndicator }
     *     
     */
    public void setInvoiceDetailHeaderIndicator(InvoiceDetailHeaderIndicator value) {
        this.invoiceDetailHeaderIndicator = value;
    }

    /**
     * Gets the value of the invoiceDetailLineIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailLineIndicator }
     *     
     */
    public InvoiceDetailLineIndicator getInvoiceDetailLineIndicator() {
        return invoiceDetailLineIndicator;
    }

    /**
     * Sets the value of the invoiceDetailLineIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailLineIndicator }
     *     
     */
    public void setInvoiceDetailLineIndicator(InvoiceDetailLineIndicator value) {
        this.invoiceDetailLineIndicator = value;
    }

    /**
     * Gets the value of the invoicePartner property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoicePartner property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoicePartner().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvoicePartner }
     * 
     * 
     */
    public List<InvoicePartner> getInvoicePartner() {
        if (invoicePartner == null) {
            invoicePartner = new ArrayList<InvoicePartner>();
        }
        return this.invoicePartner;
    }

    /**
     * Gets the value of the documentReference property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentReference }
     *     
     */
    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    /**
     * Sets the value of the documentReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReference }
     *     
     */
    public void setDocumentReference(DocumentReference value) {
        this.documentReference = value;
    }

    /**
     * Gets the value of the invoiceIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceIDInfo }
     *     
     */
    public InvoiceIDInfo getInvoiceIDInfo() {
        return invoiceIDInfo;
    }

    /**
     * Sets the value of the invoiceIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceIDInfo }
     *     
     */
    public void setInvoiceIDInfo(InvoiceIDInfo value) {
        this.invoiceIDInfo = value;
    }

    /**
     * Gets the value of the invoiceDetailShipping property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetailShipping }
     *     
     */
    public InvoiceDetailShipping getInvoiceDetailShipping() {
        return invoiceDetailShipping;
    }

    /**
     * Sets the value of the invoiceDetailShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetailShipping }
     *     
     */
    public void setInvoiceDetailShipping(InvoiceDetailShipping value) {
        this.invoiceDetailShipping = value;
    }

    /**
     * Gets the value of the invoiceDetailPaymentTerm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoiceDetailPaymentTerm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoiceDetailPaymentTerm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvoiceDetailPaymentTerm }
     * 
     * 
     */
    public List<InvoiceDetailPaymentTerm> getInvoiceDetailPaymentTerm() {
        if (invoiceDetailPaymentTerm == null) {
            invoiceDetailPaymentTerm = new ArrayList<InvoiceDetailPaymentTerm>();
        }
        return this.invoiceDetailPaymentTerm;
    }

    /**
     * Gets the value of the paymentTerm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentTerm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentTerm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentTerm }
     * 
     * 
     */
    public List<PaymentTerm> getPaymentTerm() {
        if (paymentTerm == null) {
            paymentTerm = new ArrayList<PaymentTerm>();
        }
        return this.paymentTerm;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link Period }
     *     
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link Period }
     *     
     */
    public void setPeriod(Period value) {
        this.period = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link Comments }
     *     
     */
    public Comments getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comments }
     *     
     */
    public void setComments(Comments value) {
        this.comments = value;
    }

    /**
     * Gets the value of the extrinsic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extrinsic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtrinsic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extrinsic }
     * 
     * 
     */
    public List<Extrinsic> getExtrinsic() {
        if (extrinsic == null) {
            extrinsic = new ArrayList<Extrinsic>();
        }
        return this.extrinsic;
    }

    /**
     * Gets the value of the invoiceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceID() {
        return invoiceID;
    }

    /**
     * Sets the value of the invoiceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceID(String value) {
        this.invoiceID = value;
    }

    /**
     * Gets the value of the isInformationOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsInformationOnly() {
        return isInformationOnly;
    }

    /**
     * Sets the value of the isInformationOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsInformationOnly(String value) {
        this.isInformationOnly = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        if (purpose == null) {
            return "standard";
        } else {
            return purpose;
        }
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        if (operation == null) {
            return "new";
        } else {
            return operation;
        }
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

    /**
     * Gets the value of the invoiceDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Sets the value of the invoiceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceDate(String value) {
        this.invoiceDate = value;
    }

    /**
     * Gets the value of the invoiceOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceOrigin() {
        return invoiceOrigin;
    }

    /**
     * Sets the value of the invoiceOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceOrigin(String value) {
        this.invoiceOrigin = value;
    }

}