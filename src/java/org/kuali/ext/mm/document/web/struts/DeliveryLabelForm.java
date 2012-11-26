/**
 * 
 */
package org.kuali.ext.mm.document.web.struts;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.DeliveryLabelDocument;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;
import org.kuali.rice.kns.web.ui.ExtraButton;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.KRADConstants;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;


/**
 * @author rshrivas
 *
 */
public class DeliveryLabelForm extends KualiTransactionalDocumentFormBase{
   
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Boolean singleWarehouse;
    private Integer numberOfOrders;
    private Integer numberOfLines;
    private Integer numberOfWillCalls;
    private Timestamp oldestDate;
    private String lookupResultsSequenceNumber;
    private String nbrOfCartons;
    
    public DeliveryLabelForm() {
        super();
        singleWarehouse = true;
        numberOfLines = 0;
        numberOfOrders = 0;
        numberOfWillCalls = 0;
        oldestDate = null;
    }       

    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
    }

    /**
     * This method gets the Delivery Label document
     *
     * @return DeliveryLabelDocument
     */
    public DeliveryLabelDocument getDeliveryLabelDocument() {
        return (DeliveryLabelDocument) getDocument();
    }

    /**
     * Gets the singleWarehouse property
     * @return Returns the singleWarehouse
     */
    public Boolean getSingleWarehouse() {
        return this.singleWarehouse;
    }

    /**
     * Sets the singleWarehouse property value
     * @param singleWarehouse The singleWarehouse to set
     */
    public void setSingleWarehouse(Boolean singleWarehouse) {
        this.singleWarehouse = singleWarehouse;
    }

    /**
     * Gets the numberOfOrders property
     * @return Returns the numberOfOrders
     */
    public Integer getNumberOfOrders() {
        return this.numberOfOrders;
    }

    /**
     * Sets the numberOfOrders property value
     * @param numberOfOrders The numberOfOrders to set
     */
    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    /**
     * Gets the numberOfLines property
     * @return Returns the numberOfLines
     */
    public Integer getNumberOfLines() {
        return this.numberOfLines;
    }

    /**
     * Sets the numberOfLines property value
     * @param numberOfLines The numberOfLines to set
     */
    public void setNumberOfLines(Integer numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    /**
     * Gets the numberOfWillCalls property
     * @return Returns the numberOfWillCalls
     */
    public Integer getNumberOfWillCalls() {
        return this.numberOfWillCalls;
    }

    /**
     * Sets the numberOfWillCalls property value
     * @param numberOfWillCalls The numberOfWillCalls to set
     */
    public void setNumberOfWillCalls(Integer numberOfWillCalls) {
        this.numberOfWillCalls = numberOfWillCalls;
    }

    /**
     * Gets the oldestDate property
     * @return Returns the oldestDate
     */
    public Timestamp getOldestDate() {
        return this.oldestDate;
    }

    /**
     * Sets the oldestDate property value
     * @param oldestDate The oldestDate to set
     */
    public void setOldestDate(Timestamp oldestDate) {
        this.oldestDate = oldestDate;
    }

    /**
     * Gets the lookupResultsSequenceNumber property
     * @return Returns the lookupResultsSequenceNumber
     */
    public String getLookupResultsSequenceNumber() {
        return this.lookupResultsSequenceNumber;
    }

    /**
     * Sets the lookupResultsSequenceNumber property value
     * @param lookupResultsSequenceNumber The lookupResultsSequenceNumber to set
     */
    public void setLookupResultsSequenceNumber(String lookupResultsSequenceNumber) {
        this.lookupResultsSequenceNumber = lookupResultsSequenceNumber;
    }
    
    /**
     * @see org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase#addRequiredNonEditableProperties()
     */
    @Override
    public void addRequiredNonEditableProperties() {
        super.addRequiredNonEditableProperties();
        registerRequiredNonEditableProperty(KRADConstants.LOOKUP_RESULTS_SEQUENCE_NUMBER);
    }
    

    /**
     * @see org.kuali.rice.kns.web.struts.form.AccountingDocumentFormBase#getRefreshCaller()
     */
    @Override
    public String getRefreshCaller() {
        return MMConstants.MULTIPLE_VALUE;
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
    
    public List<ExtraButton> getExtraButtons() {        
        extraButtons.clear();
        WorkflowDocument workflowDoc = this.getDeliveryLabelDocument().getDocumentHeader().getWorkflowDocument();
        String imageUrl = KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(MMConstants.EXTERNALIZABLE_IMAGES_URL_KEY);
        if(workflowDoc.isApproved() || workflowDoc.isEnroute() || workflowDoc.isProcessed() || workflowDoc.isFinal()) {
             extraButtons.add(createPrintButton(imageUrl));
        }   
        return extraButtons;
    }
    
    private ExtraButton createPrintButton(String imageUrl) {
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.printDeliveryLabel");
        printButton.setExtraButtonSource(imageUrl+"buttonsmall_print.gif");
        printButton.setExtraButtonAltText("Delivery Label");
        return printButton;
    }

    public boolean shouldMethodToCallParameterBeUsed
        (String methodToCallParameterName, String methodToCallParameterValue, HttpServletRequest request) {
        return true;
    }
    
}
