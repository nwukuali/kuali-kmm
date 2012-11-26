/**
 * 
 */
package org.kuali.ext.mm.document.authorization;

import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.krad.document.Document;

/**
 * @author rshrivas
 *
 */
public class DeliveryLabelDocumentPresentationController extends TransactionalDocumentPresentationControllerBase{
    @Override
    public boolean canSendAdhocRequests(Document document) {
        return false;
    }

    @Override
    public boolean canEdit(Document document){
        boolean canEdit = false;
        WorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();
        if (workflowDocument.isInitiated() || workflowDocument.isSaved() || workflowDocument.isException()) {
            canEdit = true; 
        }        
        return canEdit;
    }

    @Override
    public boolean canBlanketApprove(Document document) {
        return false;
    }
}
