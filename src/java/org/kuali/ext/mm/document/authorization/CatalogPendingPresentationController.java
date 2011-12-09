/**
 *
 */
package org.kuali.ext.mm.document.authorization;

import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;

/**
 * @author rshrivas
 *
 */
public class CatalogPendingPresentationController extends TransactionalDocumentPresentationControllerBase {

    @Override
    public boolean canInitiate(String documentTypeName) {
        return true;
    }

    @Override
    protected boolean canSendAdhocRequests(Document document) {
        return false;
    }

    @Override
    protected boolean canSave(Document document){
        return false;
    }

    @Override
    protected boolean canClose(Document document){
        return true;
    }

    @Override
    protected boolean canApprove(Document document) {
        return true;
    }

}
