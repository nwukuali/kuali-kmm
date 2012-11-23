package org.kuali.ext.mm.document.authorization;

import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.krad.document.Document;

public class PickListDocumentPresentationController extends	TransactionalDocumentPresentationControllerBase {


	@Override
	public boolean canSendAdhocRequests(Document document) {
    	return false;
    }

	@Override
    public boolean canSave(Document document){
    	return false;
    }

	@Override
	public boolean canBlanketApprove(Document document) {
		return false;
	}
}
