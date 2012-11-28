package org.kuali.ext.mm.b2b.cxml.kfs.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}Money&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "money" })
public class SubtotalAmount {

    @XmlElement(name = "Money", required = true, defaultValue = "0")
    protected Money money;

    /**
     * Gets the value of the money property.
     * 
     * @return possible object is {@link Money }
     */
    public Money getMoney() {
        return money;
    }

    /**
     * Sets the value of the money property.
     * 
     * @param value allowed object is {@link Money }
     */
    public void setMoney(Money value) {
        this.money = value;
    }

}