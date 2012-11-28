//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.06.21 at 02:56:33 PM EDT 
//


package org.kuali.ext.mm.b2b.cxml.types;

import org.kuali.ext.mm.b2b.cxml.adapters.CleanStringAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="currency" use="required" type="{}isoCurrencyCode" />
 *       &lt;attribute name="alternateAmount" type="{}number" />
 *       &lt;attribute name="alternateCurrency" type="{}isoCurrencyCode" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "Money")
public class Money {
    @XmlValue
    @XmlJavaTypeAdapter(value=CleanStringAdapter.class)
    protected String content;
    @XmlAttribute
    @XmlJavaTypeAdapter(value=CleanStringAdapter.class)
    protected String currency;
    @XmlAttribute
    @XmlJavaTypeAdapter(value=CleanStringAdapter.class)
    protected String alternateAmount;
    @XmlAttribute
    @XmlJavaTypeAdapter(value=CleanStringAdapter.class)
    protected String alternateCurrency;

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the alternateAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternateAmount() {
        return alternateAmount;
    }

    /**
     * Sets the value of the alternateAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternateAmount(String value) {
        this.alternateAmount = value;
    }

    /**
     * Gets the value of the alternateCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternateCurrency() {
        return alternateCurrency;
    }

    /**
     * Sets the value of the alternateCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternateCurrency(String value) {
        this.alternateCurrency = value;
    }

}
