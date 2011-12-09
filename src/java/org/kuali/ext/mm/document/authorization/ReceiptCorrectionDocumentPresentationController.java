package org.kuali.ext.mm.document.authorization;

import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;

public class ReceiptCorrectionDocumentPresentationController extends	TransactionalDocumentPresentationControllerBase {

	@Override
	protected boolean canSendAdhocRequests(Document document) {
    	return false;
    }

//	protected boolean canInitiate(Document doc){
//		return true;
//	}
}
