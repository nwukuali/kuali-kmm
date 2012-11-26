package org.kuali.ext.mm.document.authorization;

import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.krad.document.Document;

public class PickVerifyDocumentPresentationController extends	TransactionalDocumentPresentationControllerBase {


	@Override
	public boolean canSendAdhocRequests(Document document) {
    	return false;
    }

	@Override
	public boolean canSave(Document document) {
		if(canEdit(document) && !document.getDocumentHeader().getWorkflowDocument().isEnroute())
			return true;

		return false;
	}

}
