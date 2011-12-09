/**
 * 
 */
package org.kuali.ext.mm.document;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.ext.mm.businessobject.DeliveryLine;
import org.kuali.ext.mm.businessobject.PackStatusCode;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.PickStatusCode;
import org.kuali.ext.mm.businessobject.StoresTransactionalDocumentBase;
import org.kuali.rice.kns.util.TypedArrayList;

/**
 * @author rshrivas
 *
 */
@Entity
@Table(name = "MM_PACK_LIST_DOC_T")
public class DeliveryLabelDocument extends StoresTransactionalDocumentBase {


    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FDOC_NBR", unique = true, nullable = false, length = 36)
    private String fdocNbr;
    
    @Column(name = "PICK_TICKET_NBR")
    private Integer pickTicketNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PICK_STATUS_CD", nullable = false)
    private PickStatusCode pickStatusCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACK_STATUS_CD", nullable = false)
    private PackStatusCode packStatusCode;
    
    @Column(name = "PICK_STATUS_CD") 
    private String pickStatusCodeCd;
    
    @Column(name ="NUMBER_OF_CARTONS")
    private String nbrOfCartons;    
    
    @Column(name = "PACK_STATUS_CD")
    private String packStatusCodeCd;
    
    private DeliveryLine deliveryLine;
                


    /**
     * Gets the deliveryLine property
     * @return Returns the deliveryLine
     */
    public DeliveryLine getDeliveryLine() {
        return this.deliveryLine;
    }

    /**
     * Sets the deliveryLine property value
     * @param deliveryLine The deliveryLine to set
     */
    public void setDeliveryLine(DeliveryLine deliveryLine) {
        this.deliveryLine = deliveryLine;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pickListDocument")
    private List<PickListLine> pickListLines;

    private List<DeliveryLabelDocumentLines> deliveryLabelDocumentLines;
    
    public DeliveryLabelDocument() {
        super();
        this.setPickListLines(new TypedArrayList(PickListLine.class));
        this.setDeliveryLabelDocumentLines(new TypedArrayList(DeliveryLabelDocumentLines.class));
    }         
    
    /**
     * Gets the pickListLines property
     * @return Returns the pickListLines
     */
    public List<PickListLine> getPickListLines() {
        return this.pickListLines;
    }

    /**
     * Sets the pickListLines property value
     * @param pickListLines The pickListLines to set
     */
    public void setPickListLines(List<PickListLine> pickListLines) {
        this.pickListLines = pickListLines;
    }

     /**
     * Gets the pickStatusCode property
     * @return Returns the pickStatusCode
     */
    public PickStatusCode getPickStatusCode() {
        return this.pickStatusCode;
    }

    /**
     * Sets the pickStatusCode property value
     * @param pickStatusCode The pickStatusCode to set
     */
    public void setPickStatusCode(PickStatusCode pickStatusCode) {
        this.pickStatusCode = pickStatusCode;
    }

    /**
     * Gets the pickTicketNumber property
     * @return Returns the pickTicketNumber
     */
    public Integer getPickTicketNumber() {
        return this.pickTicketNumber;
    }

    /**
     * Sets the pickTicketNumber property value
     * @param pickTicketNumber The pickTicketNumber to set
     */
    public void setPickTicketNumber(Integer pickTicketNumber) {
        this.pickTicketNumber = pickTicketNumber;
    }

    /**
     * Gets the pickStatusCodeCd property
     * @return Returns the pickStatusCodeCd
     */
    public String getPickStatusCodeCd() {
        return this.pickStatusCodeCd;
    }

    /**
     * Sets the pickStatusCodeCd property value
     * @param pickStatusCodeCd The pickStatusCodeCd to set
     */
    public void setPickStatusCodeCd(String pickStatusCodeCd) {
        this.pickStatusCodeCd = pickStatusCodeCd;
    }

    /**
     * Gets the nbrOfCartons property
     * @return Returns the nbrOfCartons
     */
    public String getNbrOfCartons() {
        return this.nbrOfCartons;
    }

    /**
     * Sets the nbrOfCartons property value
     * @param nbrOfCartons The nbrOfCartons to set
     */
    public void setNbrOfCartons(String nbrOfCartons) {
        this.nbrOfCartons = nbrOfCartons;
    }

    /**
     * Gets the fdocNbr property
     * @return Returns the fdocNbr
     */
    public String getFdocNbr() {
        return this.fdocNbr;
    }

    /**
     * Sets the fdocNbr property value
     * @param fdocNbr The fdocNbr to set
     */
    public void setFdocNbr(String fdocNbr) {
        this.fdocNbr = fdocNbr;
    }

    /**
     * Gets the packStatusCode property
     * @return Returns the packStatusCode
     */
    public PackStatusCode getPackStatusCode() {
        return this.packStatusCode;
    }

    /**
     * Sets the packStatusCode property value
     * @param packStatusCode The packStatusCode to set
     */
    public void setPackStatusCode(PackStatusCode packStatusCode) {
        this.packStatusCode = packStatusCode;
    }

    /**
     * Gets the packStatusCodeCd property
     * @return Returns the packStatusCodeCd
     */
    public String getPackStatusCodeCd() {
        return this.packStatusCodeCd;
    }

    /**
     * Sets the packStatusCodeCd property value
     * @param packStatusCodeCd The packStatusCodeCd to set
     */
    public void setPackStatusCodeCd(String packStatusCodeCd) {
        this.packStatusCodeCd = packStatusCodeCd;
    }

    /**
     * Gets the deliveryLabelDocumentLines property
     * @return Returns the deliveryLabelDocumentLines
     */
    public List<DeliveryLabelDocumentLines> getDeliveryLabelDocumentLines() {
        return this.deliveryLabelDocumentLines;
    }

    /**
     * Sets the deliveryLabelDocumentLines property value
     * @param deliveryLabelDocumentLines The deliveryLabelDocumentLines to set
     */
    public void setDeliveryLabelDocumentLines(
            List<DeliveryLabelDocumentLines> deliveryLabelDocumentLines) {
        this.deliveryLabelDocumentLines = deliveryLabelDocumentLines;
    }

    
    
}
