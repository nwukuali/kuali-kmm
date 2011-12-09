/**
 * 
 */
package org.kuali.ext.mm.document.authorization;

import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.kns.workflow.service.KualiWorkflowDocument;

/**
 * @author rshrivas
 *
 */
public class DeliveryLabelDocumentPresentationController extends TransactionalDocumentPresentationControllerBase{
    @Override
    protected boolean canSendAdhocRequests(Document document) {
        return false;
    }

    @Override
    protected boolean canEdit(Document document){
        boolean canEdit = false;
        KualiWorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();
        if (workflowDocument.stateIsInitiated() || workflowDocument.stateIsSaved() || workflowDocument.stateIsException()) {
            canEdit = true; 
        }        
        return canEdit;
    }

    @Override
    protected boolean canBlanketApprove(Document document) {
        return false;
    }
}
