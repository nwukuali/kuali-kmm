package org.kuali.ext.mm.document.authorization;

import java.util.Set;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.kns.util.KNSConstants;


public class ReturnDocumentPresentationController extends
		TransactionalDocumentPresentationControllerBase {

	@Override
	protected boolean canSendAdhocRequests(Document document) {
		return false;
	}

	@Override
    public Set<String> getEditModes(Document document) {
		Set<String> editModes = super.getEditModes(document);

		ReturnDocument returnDoc = (ReturnDocument) document;

		if(returnDoc.getDocumentHeader().getWorkflowDocument().stateIsInitiated() ||
		        returnDoc.getDocumentHeader().getWorkflowDocument().stateIsSaved()){

		        if(!editModes.contains(KNSConstants.KUALI_ACTION_CAN_SAVE))
    	            editModes
                    .add(KNSConstants.KUALI_ACTION_CAN_SAVE);

		}else{
		if (MMServiceLocator.getMMDocumentUtilService().isDocInMyActionList(document.getDocumentNumber()))
			editModes.add(MMConstants.ReturnDocEditMode.DOC_IN_MY_ACTIONLIST);
		}

		if (returnDoc.isDocReadyToBeReviewed())
			editModes
					.add(MMConstants.ReturnDocEditMode.DOC_READY_TO_BE_REVIEWED);

		if(returnDoc.isCurrDocVendorReturnDoc())
		    editModes.add(MMConstants.ReturnDocEditMode.DOC_RETURN_DOC_TO_VENDOR);

		if(returnDoc.getDocumentHeader().getWorkflowDocument().stateIsFinal()){
		    editModes.add(MMConstants.ReturnDocEditMode.DOC_IN_FINAL_STATE);
		}

		return editModes;
	}

}