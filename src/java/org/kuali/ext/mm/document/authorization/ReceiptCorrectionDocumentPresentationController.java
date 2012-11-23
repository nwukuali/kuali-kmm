package org.kuali.ext.mm.document.authorization;

import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.krad.document.Document;

public class ReceiptCorrectionDocumentPresentationController extends	TransactionalDocumentPresentationControllerBase {

	@Override
	public boolean canSendAdhocRequests(Document document) {
    	return false;
    }

//	protected boolean canInitiate(Document doc){
//		return true;
//	}
}
