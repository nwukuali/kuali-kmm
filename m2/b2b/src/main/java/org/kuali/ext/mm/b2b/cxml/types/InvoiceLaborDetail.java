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
 *         &lt;element ref="{}Contractor" minOccurs="0"/>
 *         &lt;element ref="{}JobDescription" minOccurs="0"/>
 *         &lt;element ref="{}Supervisor" minOccurs="0"/>
 *         &lt;element ref="{}WorkLocation" minOccurs="0"/>
 *         &lt;element ref="{}InvoiceTimeCardDetail" minOccurs="0"/>
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
    "contractor",
    "jobDescription",
    "supervisor",
    "workLocation",
    "invoiceTimeCardDetail"
})
@XmlRootElement(name = "InvoiceLaborDetail")
public class InvoiceLaborDetail {

    @XmlElement(name = "Contractor")
    protected Contractor contractor;
    @XmlElement(name = "JobDescription")
    protected JobDescription jobDescription;
    @XmlElement(name = "Supervisor")
    protected Supervisor supervisor;
    @XmlElement(name = "WorkLocation")
    protected WorkLocation workLocation;
    @XmlElement(name = "InvoiceTimeCardDetail")
    protected InvoiceTimeCardDetail invoiceTimeCardDetail;

    /**
     * Gets the value of the contractor property.
     * 
     * @return
     *     possible object is
     *     {@link Contractor }
     *     
     */
    public Contractor getContractor() {
        return contractor;
    }

    /**
     * Sets the value of the contractor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contractor }
     *     
     */
    public void setContractor(Contractor value) {
        this.contractor = value;
    }

    /**
     * Gets the value of the jobDescription property.
     * 
     * @return
     *     possible object is
     *     {@link JobDescription }
     *     
     */
    public JobDescription getJobDescription() {
        return jobDescription;
    }

    /**
     * Sets the value of the jobDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link JobDescription }
     *     
     */
    public void setJobDescription(JobDescription value) {
        this.jobDescription = value;
    }

    /**
     * Gets the value of the supervisor property.
     * 
     * @return
     *     possible object is
     *     {@link Supervisor }
     *     
     */
    public Supervisor getSupervisor() {
        return supervisor;
    }

    /**
     * Sets the value of the supervisor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Supervisor }
     *     
     */
    public void setSupervisor(Supervisor value) {
        this.supervisor = value;
    }

    /**
     * Gets the value of the workLocation property.
     * 
     * @return
     *     possible object is
     *     {@link WorkLocation }
     *     
     */
    public WorkLocation getWorkLocation() {
        return workLocation;
    }

    /**
     * Sets the value of the workLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkLocation }
     *     
     */
    public void setWorkLocation(WorkLocation value) {
        this.workLocation = value;
    }

    /**
     * Gets the value of the invoiceTimeCardDetail property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceTimeCardDetail }
     *     
     */
    public InvoiceTimeCardDetail getInvoiceTimeCardDetail() {
        return invoiceTimeCardDetail;
    }

    /**
     * Sets the value of the invoiceTimeCardDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceTimeCardDetail }
     *     
     */
    public void setInvoiceTimeCardDetail(InvoiceTimeCardDetail value) {
        this.invoiceTimeCardDetail = value;
    }

}