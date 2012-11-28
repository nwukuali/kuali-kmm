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
 *         &lt;element ref="{}IndexItemAdd" maxOccurs="unbounded"/>
 *         &lt;element ref="{}IndexItemDelete" maxOccurs="unbounded"/>
 *         &lt;element ref="{}IndexItemPunchout" maxOccurs="unbounded"/>
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
    "indexItemAdd",
    "indexItemDelete",
    "indexItemPunchout"
})
@XmlRootElement(name = "IndexItem")
public class IndexItem {

    @XmlElement(name = "IndexItemAdd")
    protected List<IndexItemAdd> indexItemAdd;
    @XmlElement(name = "IndexItemDelete")
    protected List<IndexItemDelete> indexItemDelete;
    @XmlElement(name = "IndexItemPunchout")
    protected List<IndexItemPunchout> indexItemPunchout;

    /**
     * Gets the value of the indexItemAdd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indexItemAdd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndexItemAdd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndexItemAdd }
     * 
     * 
     */
    public List<IndexItemAdd> getIndexItemAdd() {
        if (indexItemAdd == null) {
            indexItemAdd = new ArrayList<IndexItemAdd>();
        }
        return this.indexItemAdd;
    }

    /**
     * Gets the value of the indexItemDelete property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indexItemDelete property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndexItemDelete().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndexItemDelete }
     * 
     * 
     */
    public List<IndexItemDelete> getIndexItemDelete() {
        if (indexItemDelete == null) {
            indexItemDelete = new ArrayList<IndexItemDelete>();
        }
        return this.indexItemDelete;
    }

    /**
     * Gets the value of the indexItemPunchout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indexItemPunchout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndexItemPunchout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndexItemPunchout }
     * 
     * 
     */
    public List<IndexItemPunchout> getIndexItemPunchout() {
        if (indexItemPunchout == null) {
            indexItemPunchout = new ArrayList<IndexItemPunchout>();
        }
        return this.indexItemPunchout;
    }

}
