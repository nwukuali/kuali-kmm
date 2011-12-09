package org.kuali.ext.mm.document.authorization;

import java.util.Set;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.kns.workflow.service.KualiWorkflowDocument;


public class ReOrderDocumentPresentationController extends
        TransactionalDocumentPresentationControllerBase {

    @Override
    protected boolean canSendAdhocRequests(Document document) {
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
    protected boolean canSave(Document document) {
        KualiWorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();
        if (workflowDocument.stateIsInitiated() || workflowDocument.stateIsSaved()
                || workflowDocument.stateIsException()) {
            return true;
        }
        return false;
    }

}