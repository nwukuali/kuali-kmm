package org.kuali.ext.mm.document.authorization;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.krad.document.Document;

import java.util.Set;


public class ReOrderDocumentPresentationController extends
        TransactionalDocumentPresentationControllerBase {

    @Override
    public boolean canSendAdhocRequests(Document document) {
        return false;
    }

    @Override
    public Set<String> getEditModes(Document document) {
        Set<String> editModes = super.getEditModes(document);

        if (MMServiceLocator.getMMDocumentUtilService().isDocInMyActionList(
                document.getDocumentNumber()))
            editModes.add(MMConstants.ReturnDocEditMode.DOC_IN_MY_ACTIONLIST);

        return editModes;
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