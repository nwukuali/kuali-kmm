/**
 * 
 */
package org.kuali.ext.mm.businessobject;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.ext.mm.document.DeliveryLogDocument;

/**
 * @author rshrivas
 *
 */
@Entity
@Table(name = "MM_DELIVERY_T")
public class Delivery extends DeliveryLogDocument{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Delivery() {

    }
    
}