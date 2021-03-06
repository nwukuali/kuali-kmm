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

package org.kuali.ext.mm.integration.service;

import java.util.List;

import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kns.document.Document;

@SuppressWarnings("unchecked")
public interface FinancialDocumentService {

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
