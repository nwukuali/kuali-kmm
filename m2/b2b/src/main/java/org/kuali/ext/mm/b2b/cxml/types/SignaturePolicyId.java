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
 *         &lt;element ref="{}SigPolicyId"/>
 *         &lt;element ref="{}Transforms" minOccurs="0"/>
 *         &lt;element ref="{}SigPolicyHash"/>
 *         &lt;element ref="{}SigPolicyQualifiers" minOccurs="0"/>
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
    "sigPolicyId",
    "transforms",
    "sigPolicyHash",
    "sigPolicyQualifiers"
})
@XmlRootElement(name = "SignaturePolicyId")
public class SignaturePolicyId {

    @XmlElement(name = "SigPolicyId", required = true)
    protected SigPolicyId sigPolicyId;
    @XmlElement(name = "Transforms")
    protected Transforms transforms;
    @XmlElement(name = "SigPolicyHash", required = true)
    protected SigPolicyHash sigPolicyHash;
    @XmlElement(name = "SigPolicyQualifiers")
    protected SigPolicyQualifiers sigPolicyQualifiers;

    /**
     * Gets the value of the sigPolicyId property.
     * 
     * @return
     *     possible object is
     *     {@link SigPolicyId }
     *     
     */
    public SigPolicyId getSigPolicyId() {
        return sigPolicyId;
    }

    /**
     * Sets the value of the sigPolicyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link SigPolicyId }
     *     
     */
    public void setSigPolicyId(SigPolicyId value) {
        this.sigPolicyId = value;
    }

    /**
     * Gets the value of the transforms property.
     * 
     * @return
     *     possible object is
     *     {@link Transforms }
     *     
     */
    public Transforms getTransforms() {
        return transforms;
    }

    /**
     * Sets the value of the transforms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transforms }
     *     
     */
    public void setTransforms(Transforms value) {
        this.transforms = value;
    }

    /**
     * Gets the value of the sigPolicyHash property.
     * 
     * @return
     *     possible object is
     *     {@link SigPolicyHash }
     *     
     */
    public SigPolicyHash getSigPolicyHash() {
        return sigPolicyHash;
    }

    /**
     * Sets the value of the sigPolicyHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link SigPolicyHash }
     *     
     */
    public void setSigPolicyHash(SigPolicyHash value) {
        this.sigPolicyHash = value;
    }

    /**
     * Gets the value of the sigPolicyQualifiers property.
     * 
     * @return
     *     possible object is
     *     {@link SigPolicyQualifiers }
     *     
     */
    public SigPolicyQualifiers getSigPolicyQualifiers() {
        return sigPolicyQualifiers;
    }

    /**
     * Sets the value of the sigPolicyQualifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link SigPolicyQualifiers }
     *     
     */
    public void setSigPolicyQualifiers(SigPolicyQualifiers value) {
        this.sigPolicyQualifiers = value;
    }

}
