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
public class DeliveryLogDocumentPresentationController extends
    TransactionalDocumentPresentationControllerBase {
   
    protected boolean canSendAdhocRequests(Document document) {
        return false;
    }
    
    protected boolean canEdit(Document document){
        boolean canEdit = false;
        KualiWorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();
        if (workflowDocument.stateIsInitiated() || workflowDocument.stateIsSaved() || workflowDocument.stateIsException()) {
            canEdit = true; 
        }
        
        return canEdit;
    }
    
    protected boolean canReload(Document document){
        return false;
    }
    
    
}
