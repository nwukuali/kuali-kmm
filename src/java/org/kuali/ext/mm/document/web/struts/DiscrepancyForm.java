/**
 * 
 */
package org.kuali.ext.mm.document.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.DiscrepancyDocument;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;
import org.kuali.rice.kns.web.ui.ExtraButton;
import org.kuali.rice.kns.workflow.service.KualiWorkflowDocument;


/**
 * @author rshrivas
 *
 */
public class DiscrepancyForm extends KualiTransactionalDocumentFormBase{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer reportNumber;
    
    public DiscrepancyForm() {
        super();      
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
    public DiscrepancyDocument getDiscrepancyDocument() {
        return (DiscrepancyDocument) getDocument();
    }

    public List<ExtraButton> getExtraButtons() {        
        extraButtons.clear();        
        KualiWorkflowDocument workflowDoc = this.getDiscrepancyDocument().getDocumentHeader().getWorkflowDocument();
        String imageUrl = KNSServiceLocator.getKualiConfigurationService().getPropertyString(MMConstants.EXTERNALIZABLE_IMAGES_URL_KEY);
        if(!(workflowDoc.stateIsApproved() || workflowDoc.stateIsEnroute() || workflowDoc.stateIsProcessed() || workflowDoc.stateIsFinal())) {
            extraButtons.add(createPrintButton(imageUrl));
        }   
        return extraButtons;
    }
    
    private ExtraButton createPrintButton(String imageUrl) {
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.getReconciliationReport");
        printButton.setExtraButtonSource(imageUrl+"buttonsmall_search.gif");
        printButton.setExtraButtonAltText("Search Discrepancies");
        return printButton;
    }

    public boolean shouldMethodToCallParameterBeUsed
        (String methodToCallParameterName, String methodToCallParameterValue, HttpServletRequest request) {
        return true;
    }
    
    /**
     * Gets the reportNumber property
     * @return Returns the reportNumber
     */
    public Integer getReportNumber() {
        return this.reportNumber;
    }

    /**
     * Sets the reportNumber property value
     * @param reportNumber The reportNumber to set
     */
    public void setReportNumber(Integer reportNumber) {
        this.reportNumber = reportNumber;
    }
    
}
