/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.ext.mm.integration.service.impl.kfs;

import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.ext.mm.integration.kfs.adaptor.KfsDocumentService;
import org.kuali.ext.mm.integration.service.FinancialDocumentService;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kns.document.Document;

/**
 */
@SuppressWarnings("unchecked")
public class KfsDocumentServiceAdaptor extends KfsServiceAdaptor<KfsDocumentService> implements
        FinancialDocumentService {
    /**
     * @param name
     */
    public KfsDocumentServiceAdaptor(QName name) {
        super(name);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialDocumentService#approveDocument(org.kuali.rice.kns.document.Document,
     *      java.lang.String, java.util.List, java.lang.String)
     */
    public Document approveDocument(Document document, String annotation,
            List adHocRoutingRecipients, String principalName) throws WorkflowException {
        return getService().approveDocument(document, annotation, adHocRoutingRecipients,
                principalName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialDocumentService#blanketApproveDocument(org.kuali.rice.kns.document.Document,
     *      java.lang.String, java.util.List, java.lang.String)
     */
    public Document blanketApproveDocument(Document document, String annotation,
            List adHocRecipients, String principalName) throws WorkflowException {
        return getService().blanketApproveDocument(document, annotation, adHocRecipients,
                principalName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialDocumentService#getNewDocument(java.lang.String, java.lang.String)
     */
    public Document getNewDocument(String documentTypeName, String initialPrincipalName)
            throws WorkflowException {
        return getService().getNewDocument(documentTypeName, initialPrincipalName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialDocumentService#getNewDocument(java.lang.Class, java.lang.String)
     */
    public Document getNewDocument(Class documentClass, String initialPrincipalName)
            throws WorkflowException {
        return getService().getNewDocument(documentClass, initialPrincipalName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialDocumentService#routeDocument(org.kuali.rice.kns.document.Document,
     *      java.lang.String, java.util.List, java.lang.String)
     */
    public Document routeDocument(Document document, String annotation,
            List adHocRoutingRecipients, String principalName) throws WorkflowException {
        return getService().routeDocument(document, annotation, adHocRoutingRecipients,
                principalName);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialDocumentService#saveDocument(org.kuali.rice.kns.document.Document,
     *      java.lang.String)
     */
    public Document saveDocument(Document document, String principalName) throws WorkflowException {
        return getService().saveDocument(document, principalName);
    }


}
