//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.06.21 at 02:56:33 PM EDT 
//


package org.kuali.ext.mm.b2b.cxml.types;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}Phone"/>
 *         &lt;element ref="{}Email"/>
 *         &lt;element ref="{}Fax"/>
 *         &lt;element ref="{}URL"/>
 *         &lt;element ref="{}OtherOrderTarget"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "phone",
    "email",
    "fax",
    "url",
    "otherOrderTarget"
})
@XmlRootElement(name = "OrderTarget")
public class OrderTarget {

    @XmlElement(name = "Phone")
    protected Phone phone;
    @XmlElement(name = "Email")
    protected Email email;
    @XmlElement(name = "Fax")
    protected Fax fax;
    @XmlElement(name = "URL")
    protected URL url;
    @XmlElement(name = "OtherOrderTarget")
    protected OtherOrderTarget otherOrderTarget;

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link Phone }
     *     
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Phone }
     *     
     */
    public void setPhone(Phone value) {
        this.phone = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link Email }
     *     
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link Email }
     *     
     */
    public void setEmail(Email value) {
        this.email = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link Fax }
     *     
     */
    public Fax getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fax }
     *     
     */
    public void setFax(Fax value) {
        this.fax = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link URL }
     *     
     */
    public URL getURL() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link URL }
     *     
     */
    public void setURL(URL value) {
        this.url = value;
    }

    /**
     * Gets the value of the otherOrderTarget property.
     * 
     * @return
     *     possible object is
     *     {@link OtherOrderTarget }
     *     
     */
    public OtherOrderTarget getOtherOrderTarget() {
        return otherOrderTarget;
    }

    /**
     * Sets the value of the otherOrderTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherOrderTarget }
     *     
     */
    public void setOtherOrderTarget(OtherOrderTarget value) {
        this.otherOrderTarget = value;
    }

}
