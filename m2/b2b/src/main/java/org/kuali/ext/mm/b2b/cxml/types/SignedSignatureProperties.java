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
 *         &lt;element ref="{}SigningTime" minOccurs="0"/>
 *         &lt;element ref="{}SigningCertificate" minOccurs="0"/>
 *         &lt;element ref="{}SignaturePolicyIdentifier" minOccurs="0"/>
 *         &lt;element ref="{}SignatureProductionPlace" minOccurs="0"/>
 *         &lt;element ref="{}SignerRole" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "signingTime",
    "signingCertificate",
    "signaturePolicyIdentifier",
    "signatureProductionPlace",
    "signerRole"
})
@XmlRootElement(name = "SignedSignatureProperties")
public class SignedSignatureProperties {

    @XmlElement(name = "SigningTime")
    protected String signingTime;
    @XmlElement(name = "SigningCertificate")
    protected SigningCertificate signingCertificate;
    @XmlElement(name = "SignaturePolicyIdentifier")
    protected SignaturePolicyIdentifier signaturePolicyIdentifier;
    @XmlElement(name = "SignatureProductionPlace")
    protected SignatureProductionPlace signatureProductionPlace;
    @XmlElement(name = "SignerRole")
    protected SignerRole signerRole;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the signingTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigningTime() {
        return signingTime;
    }

    /**
     * Sets the value of the signingTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigningTime(String value) {
        this.signingTime = value;
    }

    /**
     * Gets the value of the signingCertificate property.
     * 
     * @return
     *     possible object is
     *     {@link SigningCertificate }
     *     
     */
    public SigningCertificate getSigningCertificate() {
        return signingCertificate;
    }

    /**
     * Sets the value of the signingCertificate property.
     * 
     * @param value
     *     allowed object is
     *     {@link SigningCertificate }
     *     
     */
    public void setSigningCertificate(SigningCertificate value) {
        this.signingCertificate = value;
    }

    /**
     * Gets the value of the signaturePolicyIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link SignaturePolicyIdentifier }
     *     
     */
    public SignaturePolicyIdentifier getSignaturePolicyIdentifier() {
        return signaturePolicyIdentifier;
    }

    /**
     * Sets the value of the signaturePolicyIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignaturePolicyIdentifier }
     *     
     */
    public void setSignaturePolicyIdentifier(SignaturePolicyIdentifier value) {
        this.signaturePolicyIdentifier = value;
    }

    /**
     * Gets the value of the signatureProductionPlace property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureProductionPlace }
     *     
     */
    public SignatureProductionPlace getSignatureProductionPlace() {
        return signatureProductionPlace;
    }

    /**
     * Sets the value of the signatureProductionPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureProductionPlace }
     *     
     */
    public void setSignatureProductionPlace(SignatureProductionPlace value) {
        this.signatureProductionPlace = value;
    }

    /**
     * Gets the value of the signerRole property.
     * 
     * @return
     *     possible object is
     *     {@link SignerRole }
     *     
     */
    public SignerRole getSignerRole() {
        return signerRole;
    }

    /**
     * Sets the value of the signerRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignerRole }
     *     
     */
    public void setSignerRole(SignerRole value) {
        this.signerRole = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
