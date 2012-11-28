/**
 * 
 */
package org.kuali.ext.mm.integration.kfs.adaptor;

import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.document.Document;

import java.util.List;

/**
 * @author harsha07
 */
@SuppressWarnings("unchecked")
public interface KfsDocumentService {

    public Document getNewDocument(String documentTypeName, String initialPrincipalName)
            throws WorkflowException;

    public Document getNewDocument(Class documentClass, String initialPrincipalName)
            throws WorkflowException;

    public Document saveDocument(Document document, String principalName) throws WorkflowException;

    public Document routeDocument(Document document, String annotation,
            List adHocRoutingRecipients, String principalName) throws WorkflowException;

    public Document approveDocument(Document document, String annotation,
            List adHocRoutingRecipients, String principalName) throws WorkflowException;

    public Document blanketApproveDocument(Document document, String annotation,
            List adHocRecipients, String principalName) throws WorkflowException;
}
