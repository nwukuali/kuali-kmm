/**
 *
 */
package org.kuali.ext.mm.document.authorization;

import org.kuali.rice.kns.document.authorization.TransactionalDocumentPresentationControllerBase;
import org.kuali.rice.krad.document.Document;

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
    public boolean canSendAdhocRequests(Document document) {
        return false;
    }

    @Override
    public boolean canSave(Document document){
        return false;
    }

    @Override
    public boolean canClose(Document document){
        return true;
    }

    @Override
    public boolean canApprove(Document document) {
        return true;
    }

}
