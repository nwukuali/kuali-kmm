//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.06.21 at 02:56:33 PM EDT 
//


package org.kuali.ext.mm.b2b.cxml.types;

import javax.xml.bind.annotation.*;
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
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element ref="{}OrderReference"/>
 *           &lt;choice minOccurs="0">
 *             &lt;element ref="{}MasterAgreementReference"/>
 *             &lt;element ref="{}MasterAgreementIDInfo"/>
 *           &lt;/choice>
 *           &lt;element ref="{}SupplierOrderInfo" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element ref="{}MasterAgreementReference"/>
 *           &lt;element ref="{}OrderIDInfo" minOccurs="0"/>
 *           &lt;element ref="{}SupplierOrderInfo" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element ref="{}MasterAgreementIDInfo"/>
 *           &lt;element ref="{}OrderIDInfo" minOccurs="0"/>
 *           &lt;element ref="{}SupplierOrderInfo" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element ref="{}OrderIDInfo"/>
 *           &lt;element ref="{}SupplierOrderInfo" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;element ref="{}SupplierOrderInfo"/>
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
    "content"
})
@XmlRootElement(name = "InvoiceDetailOrderInfo")
public class InvoiceDetailOrderInfo {

    @XmlElementRefs({
        @XmlElementRef(name = "OrderIDInfo", type = OrderIDInfo.class),
        @XmlElementRef(name = "OrderReference", type = OrderReference.class),
        @XmlElementRef(name = "MasterAgreementIDInfo", type = MasterAgreementIDInfo.class),
        @XmlElementRef(name = "SupplierOrderInfo", type = SupplierOrderInfo.class),
        @XmlElementRef(name = "MasterAgreementReference", type = MasterAgreementReference.class)
    })
    protected List<java.lang.Object> content;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "MasterAgreementReference" is used by two different parts of a schema. See: 
     * line 4252 of file:/C:/java/projects/kmm-cxml/reference/cXML.xsd
     * line 4246 of file:/C:/java/projects/kmm-cxml/reference/cXML.xsd
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MasterAgreementIDInfo }
     * {@link OrderIDInfo }
     * {@link SupplierOrderInfo }
     * {@link MasterAgreementReference }
     * {@link OrderReference }
     * 
     * 
     */
    public List<java.lang.Object> getContent() {
        if (content == null) {
            content = new ArrayList<java.lang.Object>();
        }
        return this.content;
    }

}