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
 *         &lt;element ref="{}LegalName" minOccurs="0"/>
 *         &lt;element ref="{}TaxID" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="isExemptFromBackupWithholding">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *             &lt;enumeration value="yes"/>
 *             &lt;enumeration value="no"/>
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
    "legalName",
    "taxID"
})
@XmlRootElement(name = "TaxInformation")
public class TaxInformation {

    @XmlElement(name = "LegalName")
    protected LegalName legalName;
    @XmlElement(name = "TaxID")
    protected List<TaxID> taxID;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String isExemptFromBackupWithholding;

    /**
     * Gets the value of the legalName property.
     * 
     * @return
     *     possible object is
     *     {@link LegalName }
     *     
     */
    public LegalName getLegalName() {
        return legalName;
    }

    /**
     * Sets the value of the legalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link LegalName }
     *     
     */
    public void setLegalName(LegalName value) {
        this.legalName = value;
    }

    /**
     * Gets the value of the taxID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taxID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaxID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaxID }
     * 
     * 
     */
    public List<TaxID> getTaxID() {
        if (taxID == null) {
            taxID = new ArrayList<TaxID>();
        }
        return this.taxID;
    }

    /**
     * Gets the value of the isExemptFromBackupWithholding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsExemptFromBackupWithholding() {
        return isExemptFromBackupWithholding;
    }

    /**
     * Sets the value of the isExemptFromBackupWithholding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsExemptFromBackupWithholding(String value) {
        this.isExemptFromBackupWithholding = value;
    }

}
