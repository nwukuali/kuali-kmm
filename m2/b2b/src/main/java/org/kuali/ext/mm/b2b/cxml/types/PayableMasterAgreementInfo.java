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
 *         &lt;element ref="{}MasterAgreementReference"/>
 *         &lt;element ref="{}MasterAgreementIDInfo"/>
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
    "masterAgreementReference",
    "masterAgreementIDInfo"
})
@XmlRootElement(name = "PayableMasterAgreementInfo")
public class PayableMasterAgreementInfo {

    @XmlElement(name = "MasterAgreementReference")
    protected MasterAgreementReference masterAgreementReference;
    @XmlElement(name = "MasterAgreementIDInfo")
    protected MasterAgreementIDInfo masterAgreementIDInfo;

    /**
     * Gets the value of the masterAgreementReference property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementReference }
     *     
     */
    public MasterAgreementReference getMasterAgreementReference() {
        return masterAgreementReference;
    }

    /**
     * Sets the value of the masterAgreementReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementReference }
     *     
     */
    public void setMasterAgreementReference(MasterAgreementReference value) {
        this.masterAgreementReference = value;
    }

    /**
     * Gets the value of the masterAgreementIDInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MasterAgreementIDInfo }
     *     
     */
    public MasterAgreementIDInfo getMasterAgreementIDInfo() {
        return masterAgreementIDInfo;
    }

    /**
     * Sets the value of the masterAgreementIDInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterAgreementIDInfo }
     *     
     */
    public void setMasterAgreementIDInfo(MasterAgreementIDInfo value) {
        this.masterAgreementIDInfo = value;
    }

}
