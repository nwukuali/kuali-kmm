/**
 * 
 */
package org.kuali.ext.mm.document;

import org.kuali.ext.mm.businessobject.DeliveryLine;
import org.kuali.ext.mm.businessobject.Route;
import org.kuali.ext.mm.businessobject.StoresTransactionalDocumentBase;
import org.springframework.util.AutoPopulatingList;

import javax.persistence.*;
import java.util.List;

/**
 * @author rshrivas
 *
 */
@Entity
@Table(name = "MM_DELIVERY_LOG_T")
public class DeliveryLogDocument extends StoresTransactionalDocumentBase{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FDOC_NBR", unique = true, nullable = false, length = 36)
    private String fdocNbr;
    
    @Column(name ="ROUTE_CD")
    private String routeCd;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROUTE_CD", nullable = false)
    private Route route;
    
    private List<DeliveryLabelDocumentLines> deliveryLabelLines;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryLogDocument")
    private List<DeliveryLine> deliveryLines;
    
    public DeliveryLogDocument() {
        super();
        this.setDeliveryLines(new AutoPopulatingList(DeliveryLine.class));
        this.setDeliveryLabelLines(new AutoPopulatingList(DeliveryLabelDocumentLines.class));
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
     * Gets the routeCd property
     * @return Returns the routeCd
     */
    public String getRouteCd() {
        return this.routeCd;
    }

    /**
     * Sets the routeCd property value
     * @param routeCd The routeCd to set
     */
    public void setRouteCd(String routeCd) {
        this.routeCd = routeCd;
    }

    /**
     * Gets the route property
     * @return Returns the route
     */
    public Route getRoute() {
        return this.route;
    }

    /**
     * Sets the route property value
     * @param route The route to set
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Gets the deliveryLines property
     * @return Returns the deliveryLines
     */
    public List<DeliveryLine> getDeliveryLines() {
        return this.deliveryLines;
    }

    /**
     * Sets the deliveryLines property value
     * @param deliveryLines The deliveryLines to set
     */
    public void setDeliveryLines(List<DeliveryLine> deliveryLines) {
        this.deliveryLines = deliveryLines;
    }

    /**
     * Gets the deliveryLabelLines property
     * @return Returns the deliveryLabelLines
     */
    public List<DeliveryLabelDocumentLines> getDeliveryLabelLines() {
        return this.deliveryLabelLines;
    }

    /**
     * Sets the deliveryLabelLines property value
     * @param deliveryLabelLines The deliveryLabelLines to set
     */
    public void setDeliveryLabelLines(List<DeliveryLabelDocumentLines> deliveryLabelLines) {
        this.deliveryLabelLines = deliveryLabelLines;
    }

   


    
}