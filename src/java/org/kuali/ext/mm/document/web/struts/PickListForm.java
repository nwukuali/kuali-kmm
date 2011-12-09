package org.kuali.ext.mm.document.web.struts;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.PickListDocument;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;
import org.kuali.rice.kns.web.ui.ExtraButton;
import org.kuali.rice.kns.workflow.service.KualiWorkflowDocument;


public class PickListForm extends KualiTransactionalDocumentFormBase {

    /**
     *
     */
    private static final long serialVersionUID = 4491469505034594379L;

    // Indicates which result set we are using when refreshing/returning from a multi-value lookup.
    private String lookupResultsSequenceNumber;

    // Type of result returned by the multi-value lookup. ?to be persisted in the lookup results service instead?
    private String lookupResultsBOClassName;

    // The name of the collection looked up (by a multiple value lookup)
    private String lookedUpCollectionName;

    private Boolean singleWarehouse;
	private Integer numberOfOrders;
    private Integer numberOfLines;
    private Integer numberOfWillCalls;
    private Timestamp oldestDate;

    public PickListForm() {
    	super();

//    	setDocument(new PickListDocument());

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
     * This method gets the Pick List document
     *
     * @return PickListDocument
     */
    public PickListDocument getPickListDocument() {
        return (PickListDocument) getDocument();
    }

    public String getLookupResultsSequenceNumber() {
		return lookupResultsSequenceNumber;
	}

	public void setLookupResultsSequenceNumber(String lookupResultsSequenceNumber) {
		this.lookupResultsSequenceNumber = lookupResultsSequenceNumber;
	}

	public String getLookupResultsBOClassName() {
		return lookupResultsBOClassName;
	}

	public void setLookupResultsBOClassName(String lookupResultsBOClassName) {
		this.lookupResultsBOClassName = lookupResultsBOClassName;
	}

	public String getLookedUpCollectionName() {
		return lookedUpCollectionName;
	}

	public void setLookedUpCollectionName(String lookedUpCollectionName) {
		this.lookedUpCollectionName = lookedUpCollectionName;
	}

	public Integer getNumberOfOrders() {
		return numberOfOrders;
	}

	public void setNumberOfOrders(Integer numberOfOrders) {
		this.numberOfOrders = numberOfOrders;
	}

	public void setSingleWarehouse(Boolean singleWarehouse) {
		this.singleWarehouse = singleWarehouse;
	}

	public Boolean getSingleWarehouse() {
		return singleWarehouse;
	}

	public Integer getNumberOfLines() {
		return numberOfLines;
	}

	public void setNumberOfLines(Integer numberOfLines) {
		this.numberOfLines = numberOfLines;
	}

	public void setNumberOfWillCalls(Integer numberOfWillCalls) {
		this.numberOfWillCalls = numberOfWillCalls;
	}

	public Integer getNumberOfWillCalls() {
		return numberOfWillCalls;
	}

	public Timestamp getOldestDate() {
		return oldestDate;
	}

	public void setOldestDate(Timestamp oldestDate) {
		this.oldestDate = oldestDate;
	}

	/**
     * Override the superclass method to add appropriate buttons for
     * PickListDocument
     *
     * @see org.kuali.rice.kns.web.struts.form.KualiForm#getExtraButtons()
     */
    @Override
    public List<ExtraButton> getExtraButtons() {
        extraButtons.clear();

        KualiWorkflowDocument workflowDoc = this.getPickListDocument().getDocumentHeader().getWorkflowDocument();

        if(workflowDoc.stateIsApproved() || workflowDoc.stateIsEnroute() || workflowDoc.stateIsProcessed() || workflowDoc.stateIsFinal()) {
        	 extraButtons.add(createPrintButton());
        }

        return extraButtons;
    }

    /**
     * Creates the Print button action to be displayed in the document controls
     */
    private ExtraButton createPrintButton(){
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.lookupPrintablePickTickets");
        printButton.setExtraButtonSource("${" + MMConstants.EXTERNALIZABLE_IMAGES_URL_KEY + "}buttonsmall_print.gif");
        printButton.setExtraButtonAltText("Print");
        return printButton;
    }

	/**
	 * @see org.kuali.rice.kns.web.struts.form.AccountingDocumentFormBase#getRefreshCaller()
	 */
	@Override
	public String getRefreshCaller() {
	    return MMConstants.MULTIPLE_VALUE;
	}

	/**
	 * @see org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase#addRequiredNonEditableProperties()
	 */
	@Override
	public void addRequiredNonEditableProperties() {
	    super.addRequiredNonEditableProperties();
	    registerRequiredNonEditableProperty(KNSConstants.LOOKUP_RESULTS_SEQUENCE_NUMBER);
	}

}
