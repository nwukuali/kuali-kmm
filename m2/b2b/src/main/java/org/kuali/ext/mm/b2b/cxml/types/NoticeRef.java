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
 *       &lt;sequence>
 *         &lt;element ref="{}Organization"/>
 *         &lt;element ref="{}NoticeNumbers"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "organization",
    "noticeNumbers"
})
@XmlRootElement(name = "NoticeRef")
public class NoticeRef {

    @XmlElement(name = "Organization", required = true)
    protected Organization organization;
    @XmlElement(name = "NoticeNumbers", required = true)
    protected NoticeNumbers noticeNumbers;

    /**
     * Gets the value of the organization property.
     * 
     * @return
     *     possible object is
     *     {@link Organization }
     *     
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * Sets the value of the organization property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organization }
     *     
     */
    public void setOrganization(Organization value) {
        this.organization = value;
    }

    /**
     * Gets the value of the noticeNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link NoticeNumbers }
     *     
     */
    public NoticeNumbers getNoticeNumbers() {
        return noticeNumbers;
    }

    /**
     * Sets the value of the noticeNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link NoticeNumbers }
     *     
     */
    public void setNoticeNumbers(NoticeNumbers value) {
        this.noticeNumbers = value;
    }

}