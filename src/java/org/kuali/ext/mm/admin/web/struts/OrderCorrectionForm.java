/**
 * 
 */
package org.kuali.ext.mm.admin.web.struts;

import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.rice.kns.web.struts.form.KualiForm;

/**
 * @author harsha07
 */
public class OrderCorrectionForm extends KualiForm {
    private static final long serialVersionUID = 3868555470521652174L;
    private String orderDocumentNumber;
    private OrderDocument orderDocument;
    boolean readOnly;

    /**
     * Gets the orderDocumentNumber property
     * 
     * @return Returns the orderDocumentNumber
     */
    public String getOrderDocumentNumber() {
        return this.orderDocumentNumber;
    }

    /**
     * Sets the orderDocumentNumber property value
     * 
     * @param orderDocumentNumber The orderDocumentNumber to set
     */
    public void setOrderDocumentNumber(String orderDocumentNumber) {
        this.orderDocumentNumber = orderDocumentNumber;
    }

    /**
     * Gets the orderDocument property
     * 
     * @return Returns the orderDocument
     */
    public OrderDocument getOrderDocument() {
        return this.orderDocument;
    }

    /**
     * Sets the orderDocument property value
     * 
     * @param orderDocument The orderDocument to set
     */
    public void setOrderDocument(OrderDocument orderDocument) {
        this.orderDocument = orderDocument;
    }


    /**
     * Gets the readOnly property
     * 
     * @return Returns the readOnly
     */
    public boolean isReadOnly() {
        return this.readOnly;
    }

    /**
     * Sets the readOnly property value
     * 
     * @param readOnly The readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
