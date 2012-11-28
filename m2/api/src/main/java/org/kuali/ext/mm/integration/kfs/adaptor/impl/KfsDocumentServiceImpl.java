/*
 * Copyright 2010 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.integration.kfs.adaptor.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.kuali.ext.mm.integration.kfs.adaptor.KfsDocumentService;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.util.AutoPopulatingList;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import org.kuali.rice.core.util.ExceptionUtils;
//import org.kuali.rice.kew.exception.WorkflowException;
//import org.kuali.rice.kns.UserSession;
//import org.kuali.rice.kns.document.Document;
//import org.kuali.rice.kns.service.DocumentService;
//import org.kuali.rice.kns.util.ErrorMessage;
//import org.kuali.rice.kns.util.GlobalVariables;
//import org.kuali.rice.kns.util.TypedArrayList;

@SuppressWarnings("unchecked")
public class KfsDocumentServiceImpl implements KfsDocumentService, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private DocumentService documentService;

    /**
     * Gets the documentService attribute.
     * 
     * @return Returns the documentService.
     */
    public DocumentService getDocumentService() {
        return documentService;
    }

    /**
     * Sets the documentService attribute value.
     * 
     * @param documentService The documentService to set.
     */
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }


    public Document getNewDocument(String documentTypeName, String initialPrincipalName)
            throws WorkflowException {
        GlobalVariables.clear();
        GlobalVariables.setUserSession(new UserSession(initialPrincipalName));
        Document doc = null;
        try {
            doc = documentService.getNewDocument(documentTypeName);
        }
        catch (Exception e) {
            throw new RuntimeException(errors(), ExceptionUtils.getRootCause(e));
        }
        return doc;
    }


    public Document getNewDocument(Class documentClass, String initialPrincipalName)
            throws WorkflowException {
        GlobalVariables.clear();
        GlobalVariables.setUserSession(new UserSession(initialPrincipalName));
        Document doc = null;
        try {
            doc = documentService.getNewDocument(documentClass);
        }
        catch (Exception e) {
            throw new RuntimeException(errors(), ExceptionUtils.getRootCause(e));
        }
        return doc;
    }

    public Document approveDocument(Document document, String annotation,
            List adHocRoutingRecipients, String principalName) throws WorkflowException {
        GlobalVariables.clear();
        GlobalVariables.setUserSession(new UserSession(principalName));
        Document doc = null;
        try {
            doc = documentService.approveDocument(document, annotation, adHocRoutingRecipients);
        }
        catch (Exception e) {
            throw new RuntimeException(errors(), ExceptionUtils.getRootCause(e));
        }
        return doc;

    }

    public Document blanketApproveDocument(Document document, String annotation,
            List adHocRecipients, String principalName) throws WorkflowException {
        GlobalVariables.clear();
        GlobalVariables.setUserSession(new UserSession(principalName));
        Document doc = null;
        try {
            doc = documentService.blanketApproveDocument(document, annotation, adHocRecipients);
        }
        catch (Exception e) {
            throw new RuntimeException(errors(), ExceptionUtils.getCause(e));
        }
        return doc;
    }

    public Document routeDocument(Document document, String annotation,
            List adHocRoutingRecipients, String principalName) throws WorkflowException {
        GlobalVariables.clear();
        GlobalVariables.setUserSession(new UserSession(principalName));
        Document doc = null;
        try {
            doc = documentService.routeDocument(document, annotation, adHocRoutingRecipients);
        }
        catch (Exception e) {
            throw new RuntimeException(errors(), ExceptionUtils.getCause(e));
        }
        return doc;
    }

    public Document saveDocument(Document document, String principalName) throws WorkflowException {
        GlobalVariables.clear();
        GlobalVariables.setUserSession(new UserSession(principalName));
        Document doc = null;
        try {
            doc = documentService.saveDocument(document);
        }
        catch (Exception e) {
            throw new RuntimeException(errors(), ExceptionUtils.getCause(e));
        }
        return doc;
    }

    @SuppressWarnings( { "unchecked", "deprecation" })
    protected String errors() {
        StringBuffer logMessage = new StringBuffer();
				//TODO : NWU - Confirm the same behaviour
        if (!GlobalVariables.getMessageMap().getErrorMessages().isEmpty()) {
            for (Iterator i = GlobalVariables.getMessageMap().getErrorMessages().entrySet().iterator(); i.hasNext();) {
                Map.Entry e = (Map.Entry) i.next();
                logMessage.append("[" + e.getKey() + "] ");
                boolean first = true;
                AutoPopulatingList errorList = (AutoPopulatingList) e.getValue();
                for (Iterator j = errorList.iterator(); j.hasNext();) {
                    ErrorMessage em = (ErrorMessage) j.next();
                    if (first) {
                        first = false;
                    }
                    else {
                        logMessage.append(";");
                    }
                    logMessage.append(em);
                }
            }
        }
        return logMessage.toString();
    }
}
