package org.kuali.ext.mm.document.authorization;

import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;

public class PickVerifyDocumentPresentationController extends	TransactionalDocumentPresentationControllerBase {


	@Override
	protected boolean canSendAdhocRequests(Document document) {
    	return false;
    }

	@Override
	protected boolean canSave(Document document) {
		if(canEdit(document) && !document.getDocumentHeader().getWorkflowDocument().stateIsEnroute())
			return true;

		return false;
	}

}
