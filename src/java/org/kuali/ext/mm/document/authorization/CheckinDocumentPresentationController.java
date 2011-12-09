package org.kuali.ext.mm.document.authorization;

import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.kns.workflow.service.KualiWorkflowDocument;

public class CheckinDocumentPresentationController extends
        TransactionalDocumentPresentationControllerBase {

    @Override
    protected boolean canSendAdhocRequests(Document document) {
        return false;
    }


    /**
     * @see org.kuali.rice.kns.document.authorization.DocumentPresentationControllerBase#canSave(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean canSave(Document document) {
        KualiWorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();
        if (workflowDocument.stateIsInitiated() || workflowDocument.stateIsSaved()
                || workflowDocument.stateIsException()) {
            return true;
        }

        return false;
    }
}
