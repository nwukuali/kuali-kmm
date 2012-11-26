package org.kuali.ext.mm.document.web.struts;

import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.PickVerifyDocument;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;
import org.kuali.rice.kns.web.ui.ExtraButton;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PickVerifyForm extends KualiTransactionalDocumentFormBase {

    /**
     *
     */
    private static final long serialVersionUID = -8112311324866621190L;
    private boolean pickTicketPopulated;
    private boolean hasTrackableStock;
    
    private Map<String, PickListLine> newPickLinesToAdd = new HashMap<String, PickListLine>();

    public PickVerifyForm() {
        super();
        setPickTicketPopulated(false);
        setHasTrackableStock(false);
    }

    /**
     * This method gets the Pick List document
     *
     * @return PickListDocument
     */
    public PickVerifyDocument getPickVerifyDocument() {
        return (PickVerifyDocument) getDocument();
    }

    public void setPickTicketPopulated(boolean pickTicketPopulated) {
        this.pickTicketPopulated = pickTicketPopulated;
    }

    public boolean isPickTicketPopulated() {
        return pickTicketPopulated;
    }

    public void setHasTrackableStock(boolean hasTrackableStock) {
        this.hasTrackableStock = hasTrackableStock;
    }

    public boolean isHasTrackableStock() {
        return hasTrackableStock;
    }

    /**
     * @return True if verify document has gone beyond initiated state
     */
    public boolean getCanPrintPackingList() {
        return !getDocument().getDocumentHeader().getWorkflowDocument().isInitiated();
    }

    /*******************************************************************************************************************************
     * @see org.kuali.rice.kns.web.struts.form.KualiForm#addRequiredNonEditableProperties()
     */
    @Override
    public void addRequiredNonEditableProperties() {
        super.addRequiredNonEditableProperties();
        registerRequiredNonEditableProperty("printPackingLists");
    }

    /**
     * Override the superclass method to add appropriate buttons for PickListDocument
     *
     * @see org.kuali.rice.kns.web.struts.form.KualiForm#getExtraButtons()
     */
    @Override
    public List<ExtraButton> getExtraButtons() {
        extraButtons.clear();

        WorkflowDocument workflowDoc = this.getPickVerifyDocument().getDocumentHeader()
                .getWorkflowDocument();

        if (!workflowDoc.isInitiated()) {
            extraButtons.add(createPrintButton());
        }

        return extraButtons;
    }

    public boolean isPrintReady() {
        if (this.getPickVerifyDocument() != null
                && this.getPickVerifyDocument().getDocumentHeader() != null
                && this.getPickVerifyDocument().getDocumentHeader().getWorkflowDocument() != null
                && !this.getPickVerifyDocument().getDocumentHeader().getWorkflowDocument()
                        .isInitiated()) {
            return true;
        }
        return false;
    }

    /**
     * Creates the Print button action to be displayed in the document controls
     */
    private ExtraButton createPrintButton() {
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.printPackingLists");
        printButton.setExtraButtonSource("${" + MMConstants.EXTERNALIZABLE_IMAGES_URL_KEY
                + "}buttonsmall_print.gif");
        printButton.setExtraButtonAltText("Print Packing List");
        return printButton;
    }

    /**
     * @see org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase#shouldMethodToCallParameterBeUsed(java.lang.String,
     *      java.lang.String, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public boolean shouldMethodToCallParameterBeUsed(String methodToCallParameterName,
            String methodToCallParameterValue, HttpServletRequest request) {
        if (isPrintReady()) {
            return true;
        }
        return super.shouldMethodToCallParameterBeUsed(methodToCallParameterName,
                methodToCallParameterValue, request);
    }

    public void setNewPickLinesToAdd(Map<String, PickListLine> newPickLinesToAdd) {
        this.newPickLinesToAdd = newPickLinesToAdd;
    }

    public Map<String, PickListLine> getNewPickLinesToAdd() {
        return newPickLinesToAdd;
    }

}
