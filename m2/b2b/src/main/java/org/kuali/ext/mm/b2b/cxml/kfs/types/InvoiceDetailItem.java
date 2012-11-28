//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.17 at 09:51:12 AM EDT 
//


package org.kuali.ext.mm.b2b.cxml.kfs.types;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


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
 *         &lt;element name=&quot;UnitOfMeasure&quot; type=&quot;{http://www.kuali.org/kfs/purap/types}uomType&quot;/&gt;
 *         &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}UnitPrice&quot;/&gt;
 *         &lt;element name=&quot;InvoiceDetailItemReference&quot;&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name=&quot;ItemID&quot; minOccurs=&quot;0&quot;&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name=&quot;SupplierPartID&quot; type=&quot;{http://www.kuali.org/kfs/purap/types}catalogNumberType&quot;/&gt;
 *                             &lt;element name=&quot;SupplierPartAuxiliaryID&quot; type=&quot;{http://www.kuali.org/kfs/purap/types}auxiliaryIDType&quot; minOccurs=&quot;0&quot;/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}Description&quot; minOccurs=&quot;0&quot;/&gt;
 *                   &lt;choice minOccurs=&quot;0&quot;&gt;
 *                     &lt;sequence&gt;
 *                       &lt;element name=&quot;ManufacturerPartID&quot; type=&quot;{http://www.kuali.org/kfs/purap/types}hundredCharsType&quot;/&gt;
 *                       &lt;element name=&quot;ManufacturerName&quot;&gt;
 *                         &lt;complexType&gt;
 *                           &lt;simpleContent&gt;
 *                             &lt;extension base=&quot;&lt;http://www.kuali.org/kfs/purap/types&gt;hundredCharsType&quot;&gt;
 *                               &lt;attribute ref=&quot;{http://www.w3.org/XML/1998/namespace}lang&quot;/&gt;
 *                             &lt;/extension&gt;
 *                           &lt;/simpleContent&gt;
 *                         &lt;/complexType&gt;
 *                       &lt;/element&gt;
 *                     &lt;/sequence&gt;
 *                   &lt;/choice&gt;
 *                   &lt;element name=&quot;Country&quot; minOccurs=&quot;0&quot;&gt;
 *                     &lt;complexType&gt;
 *                       &lt;simpleContent&gt;
 *                         &lt;extension base=&quot;&lt;http://www.kuali.org/kfs/purap/types&gt;addressType&quot;&gt;
 *                           &lt;attribute name=&quot;isoCountryCode&quot; use=&quot;required&quot; type=&quot;{http://www.kuali.org/kfs/sys/types}oneToFourCharType&quot; /&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/simpleContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name=&quot;lineNumber&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}unsignedByte&quot; /&gt;
 *                 &lt;attribute name=&quot;serialNumber&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name=&quot;SubtotalAmount&quot; minOccurs=&quot;0&quot;&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}Money&quot;/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}Tax&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;InvoiceDetailLineSpecialHandling&quot; minOccurs=&quot;0&quot;&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}Money&quot;/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name=&quot;InvoiceDetailLineShipping&quot; minOccurs=&quot;0&quot;&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}InvoiceDetailShipping&quot; minOccurs=&quot;0&quot;/&gt;
 *                   &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}Money&quot;/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}GrossAmount&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}InvoiceDetailDiscount&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}NetAmount&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}Comments&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}Extrinsic&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name=&quot;invoiceLineNumber&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}unsignedByte&quot; /&gt;
 *       &lt;attribute name=&quot;quantity&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}decimal&quot; /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "unitOfMeasure", "unitPrice", "invoiceDetailItemReference",
        "subtotalAmount", "tax", "invoiceDetailLineSpecialHandling", "invoiceDetailLineShipping",
        "grossAmount", "invoiceDetailDiscount", "netAmount", "comments", "extrinsic" })
@XmlRootElement(name = "InvoiceDetailItem")
public class InvoiceDetailItem {

    @XmlElement(name = "UnitOfMeasure", required = true)
    protected String unitOfMeasure;
    @XmlElement(name = "UnitPrice", required = true)
    protected UnitPrice unitPrice;
    @XmlElement(name = "InvoiceDetailItemReference", required = true)
    protected InvoiceDetailItem.InvoiceDetailItemReference invoiceDetailItemReference;
    @XmlElement(name = "SubtotalAmount")
    protected SubtotalAmount subtotalAmount;
    @XmlElement(name = "Tax")
    protected Tax tax;
    @XmlElement(name = "InvoiceDetailLineSpecialHandling")
    protected InvoiceDetailItem.InvoiceDetailLineSpecialHandling invoiceDetailLineSpecialHandling;
    @XmlElement(name = "InvoiceDetailLineShipping")
    protected InvoiceDetailItem.InvoiceDetailLineShipping invoiceDetailLineShipping;
    @XmlElement(name = "GrossAmount")
    protected GrossAmount grossAmount;
    @XmlElement(name = "InvoiceDetailDiscount")
    protected InvoiceDetailDiscount invoiceDetailDiscount;
    @XmlElement(name = "NetAmount")
    protected NetAmount netAmount;
    @XmlElement(name = "Comments")
    protected Comments comments;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected Integer invoiceLineNumber;
    @XmlAttribute(required = true)
    protected BigDecimal quantity;

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return possible object is {@link String }
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the unitPrice property.
     * 
     * @return possible object is {@link UnitPrice }
     */
    public UnitPrice getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value allowed object is {@link UnitPrice }
     */
    public void setUnitPrice(UnitPrice value) {
        this.unitPrice = value;
    }

    /**
     * Gets the value of the invoiceDetailItemReference property.
     * 
     * @return possible object is {@link InvoiceDetailItem.InvoiceDetailItemReference }
     */
    public InvoiceDetailItem.InvoiceDetailItemReference getInvoiceDetailItemReference() {
        return invoiceDetailItemReference;
    }

    /**
     * Sets the value of the invoiceDetailItemReference property.
     * 
     * @param value allowed object is {@link InvoiceDetailItem.InvoiceDetailItemReference }
     */
    public void setInvoiceDetailItemReference(InvoiceDetailItem.InvoiceDetailItemReference value) {
        this.invoiceDetailItemReference = value;
    }

    /**
     * Gets the value of the subtotalAmount property.
     * 
     * @return possible object is {@link SubtotalAmount }
     */
    public SubtotalAmount getSubtotalAmount() {
        return subtotalAmount;
    }

    /**
     * Sets the value of the subtotalAmount property.
     * 
     * @param value allowed object is {@link SubtotalAmount }
     */
    public void setSubtotalAmount(SubtotalAmount value) {
        this.subtotalAmount = value;
    }

    /**
     * Gets the value of the tax property.
     * 
     * @return possible object is {@link Tax }
     */
    public Tax getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     * @param value allowed object is {@link Tax }
     */
    public void setTax(Tax value) {
        this.tax = value;
    }

    /**
     * Gets the value of the invoiceDetailLineSpecialHandling property.
     * 
     * @return possible object is {@link InvoiceDetailItem.InvoiceDetailLineSpecialHandling }
     */
    public InvoiceDetailItem.InvoiceDetailLineSpecialHandling getInvoiceDetailLineSpecialHandling() {
        return invoiceDetailLineSpecialHandling;
    }

    /**
     * Sets the value of the invoiceDetailLineSpecialHandling property.
     * 
     * @param value allowed object is {@link InvoiceDetailItem.InvoiceDetailLineSpecialHandling }
     */
    public void setInvoiceDetailLineSpecialHandling(
            InvoiceDetailItem.InvoiceDetailLineSpecialHandling value) {
        this.invoiceDetailLineSpecialHandling = value;
    }

    /**
     * Gets the value of the invoiceDetailLineShipping property.
     * 
     * @return possible object is {@link InvoiceDetailItem.InvoiceDetailLineShipping }
     */
    public InvoiceDetailItem.InvoiceDetailLineShipping getInvoiceDetailLineShipping() {
        return invoiceDetailLineShipping;
    }

    /**
     * Sets the value of the invoiceDetailLineShipping property.
     * 
     * @param value allowed object is {@link InvoiceDetailItem.InvoiceDetailLineShipping }
     */
    public void setInvoiceDetailLineShipping(InvoiceDetailItem.InvoiceDetailLineShipping value) {
        this.invoiceDetailLineShipping = value;
    }

    /**
     * Gets the value of the grossAmount property.
     * 
     * @return possible object is {@link GrossAmount }
     */
    public GrossAmount getGrossAmount() {
        return grossAmount;
    }

    /**
     * Sets the value of the grossAmount property.
     * 
     * @param value allowed object is {@link GrossAmount }
     */
    public void setGrossAmount(GrossAmount value) {
        this.grossAmount = value;
    }

    /**
     * Gets the value of the invoiceDetailDiscount property.
     * 
     * @return possible object is {@link InvoiceDetailDiscount }
     */
    public InvoiceDetailDiscount getInvoiceDetailDiscount() {
        return invoiceDetailDiscount;
    }

    /**
     * Sets the value of the invoiceDetailDiscount property.
     * 
     * @param value allowed object is {@link InvoiceDetailDiscount }
     */
    public void setInvoiceDetailDiscount(InvoiceDetailDiscount value) {
        this.invoiceDetailDiscount = value;
    }

    /**
     * Gets the value of the netAmount property.
     * 
     * @return possible object is {@link NetAmount }
     */
    public NetAmount getNetAmount() {
        return netAmount;
    }

    /**
     * Sets the value of the netAmount property.
     * 
     * @param value allowed object is {@link NetAmount }
     */
    public void setNetAmount(NetAmount value) {
        this.netAmount = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return possible object is {@link Comments }
     */
    public Comments getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value allowed object is {@link Comments }
     */
    public void setComments(Comments value) {
        this.comments = value;
    }

    /**
     * Gets the value of the extrinsic property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the extrinsic
     * property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getExtrinsic().add(newItem);
     * </pre>
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Extrinsic }
     */
    public List<Extrinsic> getExtrinsic() {
        if (extrinsic == null) {
            extrinsic = new ArrayList<Extrinsic>();
        }
        return this.extrinsic;
    }

    /**
     * Gets the value of the invoiceLineNumber property.
     */
    public int getInvoiceLineNumber() {
        return invoiceLineNumber;
    }

    /**
     * Sets the value of the invoiceLineNumber property.
     */
    public void setInvoiceLineNumber(int value) {
        this.invoiceLineNumber = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return possible object is {@link BigDecimal }
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value allowed object is {@link BigDecimal }
     */
    public void setQuantity(BigDecimal value) {
        this.quantity = value;
    }


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
     *         &lt;element name=&quot;ItemID&quot; minOccurs=&quot;0&quot;&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name=&quot;SupplierPartID&quot; type=&quot;{http://www.kuali.org/kfs/purap/types}catalogNumberType&quot;/&gt;
     *                   &lt;element name=&quot;SupplierPartAuxiliaryID&quot; type=&quot;{http://www.kuali.org/kfs/purap/types}auxiliaryIDType&quot; minOccurs=&quot;0&quot;/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}Description&quot; minOccurs=&quot;0&quot;/&gt;
     *         &lt;choice minOccurs=&quot;0&quot;&gt;
     *           &lt;sequence&gt;
     *             &lt;element name=&quot;ManufacturerPartID&quot; type=&quot;{http://www.kuali.org/kfs/purap/types}hundredCharsType&quot;/&gt;
     *             &lt;element name=&quot;ManufacturerName&quot;&gt;
     *               &lt;complexType&gt;
     *                 &lt;simpleContent&gt;
     *                   &lt;extension base=&quot;&lt;http://www.kuali.org/kfs/purap/types&gt;hundredCharsType&quot;&gt;
     *                     &lt;attribute ref=&quot;{http://www.w3.org/XML/1998/namespace}lang&quot;/&gt;
     *                   &lt;/extension&gt;
     *                 &lt;/simpleContent&gt;
     *               &lt;/complexType&gt;
     *             &lt;/element&gt;
     *           &lt;/sequence&gt;
     *         &lt;/choice&gt;
     *         &lt;element name=&quot;Country&quot; minOccurs=&quot;0&quot;&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base=&quot;&lt;http://www.kuali.org/kfs/purap/types&gt;addressType&quot;&gt;
     *                 &lt;attribute name=&quot;isoCountryCode&quot; use=&quot;required&quot; type=&quot;{http://www.kuali.org/kfs/sys/types}oneToFourCharType&quot; /&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name=&quot;lineNumber&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}unsignedByte&quot; /&gt;
     *       &lt;attribute name=&quot;serialNumber&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "itemID", "description", "manufacturerPartID",
            "manufacturerName", "country" })
    public static class InvoiceDetailItemReference {

        @XmlElement(name = "ItemID")
        protected InvoiceDetailItem.InvoiceDetailItemReference.ItemID itemID;
        @XmlElement(name = "Description")
        protected Description description;
        @XmlElement(name = "ManufacturerPartID")
        protected String manufacturerPartID;
        @XmlElement(name = "ManufacturerName")
        protected InvoiceDetailItem.InvoiceDetailItemReference.ManufacturerName manufacturerName;
        @XmlElement(name = "Country")
        protected InvoiceDetailItem.InvoiceDetailItemReference.Country country;
        @XmlAttribute(required = true)
        @XmlSchemaType(name = "unsignedByte")
        protected int lineNumber;
        @XmlAttribute
        protected String serialNumber;

        /**
         * Gets the value of the itemID property.
         * 
         * @return possible object is {@link InvoiceDetailItem.InvoiceDetailItemReference.ItemID }
         */
        public InvoiceDetailItem.InvoiceDetailItemReference.ItemID getItemID() {
            return itemID;
        }

        /**
         * Sets the value of the itemID property.
         * 
         * @param value allowed object is {@link InvoiceDetailItem.InvoiceDetailItemReference.ItemID }
         */
        public void setItemID(InvoiceDetailItem.InvoiceDetailItemReference.ItemID value) {
            this.itemID = value;
        }

        /**
         * Gets the value of the description property.
         * 
         * @return possible object is {@link Description }
         */
        public Description getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value allowed object is {@link Description }
         */
        public void setDescription(Description value) {
            this.description = value;
        }

        /**
         * Gets the value of the manufacturerPartID property.
         * 
         * @return possible object is {@link String }
         */
        public String getManufacturerPartID() {
            return manufacturerPartID;
        }

        /**
         * Sets the value of the manufacturerPartID property.
         * 
         * @param value allowed object is {@link String }
         */
        public void setManufacturerPartID(String value) {
            this.manufacturerPartID = value;
        }

        /**
         * Gets the value of the manufacturerName property.
         * 
         * @return possible object is {@link InvoiceDetailItem.InvoiceDetailItemReference.ManufacturerName }
         */
        public InvoiceDetailItem.InvoiceDetailItemReference.ManufacturerName getManufacturerName() {
            return manufacturerName;
        }

        /**
         * Sets the value of the manufacturerName property.
         * 
         * @param value allowed object is {@link InvoiceDetailItem.InvoiceDetailItemReference.ManufacturerName }
         */
        public void setManufacturerName(
                InvoiceDetailItem.InvoiceDetailItemReference.ManufacturerName value) {
            this.manufacturerName = value;
        }

        /**
         * Gets the value of the country property.
         * 
         * @return possible object is {@link InvoiceDetailItem.InvoiceDetailItemReference.Country }
         */
        public InvoiceDetailItem.InvoiceDetailItemReference.Country getCountry() {
            return country;
        }

        /**
         * Sets the value of the country property.
         * 
         * @param value allowed object is {@link InvoiceDetailItem.InvoiceDetailItemReference.Country }
         */
        public void setCountry(InvoiceDetailItem.InvoiceDetailItemReference.Country value) {
            this.country = value;
        }

        /**
         * Gets the value of the lineNumber property.
         */
        public int getLineNumber() {
            return lineNumber;
        }

        /**
         * Sets the value of the lineNumber property.
         */
        public void setLineNumber(int value) {
            this.lineNumber = value;
        }

        /**
         * Gets the value of the serialNumber property.
         * 
         * @return possible object is {@link String }
         */
        public String getSerialNumber() {
            return serialNumber;
        }

        /**
         * Sets the value of the serialNumber property.
         * 
         * @param value allowed object is {@link String }
         */
        public void setSerialNumber(String value) {
            this.serialNumber = value;
        }


        /**
         * <p>
         * Java class for anonymous complex type.
         * <p>
         * The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;simpleContent&gt;
         *     &lt;extension base=&quot;&lt;http://www.kuali.org/kfs/purap/types&gt;addressType&quot;&gt;
         *       &lt;attribute name=&quot;isoCountryCode&quot; use=&quot;required&quot; type=&quot;{http://www.kuali.org/kfs/sys/types}oneToFourCharType&quot; /&gt;
         *     &lt;/extension&gt;
         *   &lt;/simpleContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "value" })
        public static class Country {

            @XmlValue
            protected String value;
            @XmlAttribute(required = true)
            protected String isoCountryCode;

            /**
             * Gets the value of the value property.
             * 
             * @return possible object is {@link String }
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value allowed object is {@link String }
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the isoCountryCode property.
             * 
             * @return possible object is {@link String }
             */
            public String getIsoCountryCode() {
                return isoCountryCode;
            }

            /**
             * Sets the value of the isoCountryCode property.
             * 
             * @param value allowed object is {@link String }
             */
            public void setIsoCountryCode(String value) {
                this.isoCountryCode = value;
            }

        }


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
         *         &lt;element name=&quot;SupplierPartID&quot; type=&quot;{http://www.kuali.org/kfs/purap/types}catalogNumberType&quot;/&gt;
         *         &lt;element name=&quot;SupplierPartAuxiliaryID&quot; type=&quot;{http://www.kuali.org/kfs/purap/types}auxiliaryIDType&quot; minOccurs=&quot;0&quot;/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "supplierPartID", "supplierPartAuxiliaryID" })
        public static class ItemID {

            @XmlElement(name = "SupplierPartID", required = true)
            protected String supplierPartID;
            @XmlElement(name = "SupplierPartAuxiliaryID")
            protected String supplierPartAuxiliaryID;

            /**
             * Gets the value of the supplierPartID property.
             * 
             * @return possible object is {@link String }
             */
            public String getSupplierPartID() {
                return supplierPartID;
            }

            /**
             * Sets the value of the supplierPartID property.
             * 
             * @param value allowed object is {@link String }
             */
            public void setSupplierPartID(String value) {
                this.supplierPartID = value;
            }

            /**
             * Gets the value of the supplierPartAuxiliaryID property.
             * 
             * @return possible object is {@link String }
             */
            public String getSupplierPartAuxiliaryID() {
                return supplierPartAuxiliaryID;
            }

            /**
             * Sets the value of the supplierPartAuxiliaryID property.
             * 
             * @param value allowed object is {@link String }
             */
            public void setSupplierPartAuxiliaryID(String value) {
                this.supplierPartAuxiliaryID = value;
            }

        }


        /**
         * <p>
         * Java class for anonymous complex type.
         * <p>
         * The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;simpleContent&gt;
         *     &lt;extension base=&quot;&lt;http://www.kuali.org/kfs/purap/types&gt;hundredCharsType&quot;&gt;
         *       &lt;attribute ref=&quot;{http://www.w3.org/XML/1998/namespace}lang&quot;/&gt;
         *     &lt;/extension&gt;
         *   &lt;/simpleContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "value" })
        public static class ManufacturerName {

            @XmlValue
            protected String value;
            @XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "language")
            protected String lang;

            /**
             * Gets the value of the value property.
             * 
             * @return possible object is {@link String }
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value allowed object is {@link String }
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the lang property.
             * 
             * @return possible object is {@link String }
             */
            public String getLang() {
                return lang;
            }

            /**
             * Sets the value of the lang property.
             * 
             * @param value allowed object is {@link String }
             */
            public void setLang(String value) {
                this.lang = value;
            }

        }

    }


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
     *         &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}InvoiceDetailShipping&quot; minOccurs=&quot;0&quot;/&gt;
     *         &lt;element ref=&quot;{http://www.kuali.org/kfs/purap/electronicInvoice}Money&quot;/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "invoiceDetailShipping", "money" })
    public static class InvoiceDetailLineShipping {

        @XmlElement(name = "InvoiceDetailShipping")
        protected InvoiceDetailShipping invoiceDetailShipping;
        @XmlElement(name = "Money", required = true, defaultValue = "0")
        protected Money money;

        /**
         * Gets the value of the invoiceDetailShipping property.
         * 
         * @return possible object is {@link InvoiceDetailShipping }
         */
        public InvoiceDetailShipping getInvoiceDetailShipping() {
            return invoiceDetailShipping;
        }

        /**
         * Sets the value of the invoiceDetailShipping property.
         * 
         * @param value allowed object is {@link InvoiceDetailShipping }
         */
        public void setInvoiceDetailShipping(InvoiceDetailShipping value) {
            this.invoiceDetailShipping = value;
        }

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
    public static class InvoiceDetailLineSpecialHandling {

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

}
