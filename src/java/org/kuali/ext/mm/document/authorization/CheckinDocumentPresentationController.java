package org.kuali.ext.mm.document.authorization;

import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.krad.document.Document;

public class CheckinDocumentPresentationController extends
        TransactionalDocumentPresentationControllerBase {

    @Override
    public boolean canSendAdhocRequests(Document document) {
        return false;
    }


    /**
     * @see org.kuali.rice.kns.document.authorization.DocumentPresentationControllerBase#canSave(org.kuali.rice.kns.document.Document)
     */
    @Override
    public boolean canSave(Document document) {
        WorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();
        if (workflowDocument.isInitiated() || workflowDocument.isSaved()
                || workflowDocument.isException()) {
            return true;
        }

        return false;
    }
}
