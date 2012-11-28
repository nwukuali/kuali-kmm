/*
 * Copyright 2008-2009 The Kuali Foundation
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
package org.kuali.ext.mm.sys.workflow;

import org.kuali.ext.mm.businessobject.StoresTransactionalDocumentBase;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.impl.StoresMaintainableImpl;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.SplitNode;
import org.kuali.rice.kew.engine.node.SplitResult;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.service.DocumentService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SimpleBooleanSplitNode implements SplitNode, Serializable {

    private static final long serialVersionUID = 3883683104408397358L;

    /**
     * @see org.kuali.rice.kew.engine.node.SimpleNode#process(org.kuali.rice.kew.engine.RouteContext,
     *      org.kuali.rice.kew.engine.RouteHelper)
     */
    public SplitResult process(RouteContext context, RouteHelper helper) throws Exception {
        SplitResult result = null;
        String documentID = context.getDocument().getDocumentId();
        Document document = SpringContext.getBean(DocumentService.class).getByDocumentHeaderId(
                documentID);
        String nodeName = context.getNodeInstance().getRouteNode().getRouteNodeName();
        if (StoresTransactionalDocumentBase.class.isAssignableFrom(document.getClass())) {
            boolean ret = ((StoresTransactionalDocumentBase) document)
                    .answerSplitNodeQuestion(nodeName);
            result = booleanToSplitResult(ret);
        }
        else if (MaintenanceDocument.class.isAssignableFrom(document.getClass())) {
            MaintenanceDocument maintenanceDocument = (MaintenanceDocument) document;
            if (maintenanceDocument.getNewMaintainableObject() != null
                    && StoresMaintainableImpl.class.isAssignableFrom(maintenanceDocument
                            .getNewMaintainableObject().getClass())) {
                StoresMaintainableImpl newMaintainableObject = (StoresMaintainableImpl) maintenanceDocument
                        .getNewMaintainableObject();
                result = booleanToSplitResult(newMaintainableObject
                        .answerSplitNodeQuestion(nodeName));
            }
        }
        else {
            throw new IllegalArgumentException("Document " + document.getDocumentTitle()
                    + " with id " + documentID + " is not an instance of " + document.getClass()
                    + " or " + document.getClass());
        }

        return result;
    }

    /**
     * Converts a boolean value to SplitResult where the branch name is "True" or "False" based on the value of the given boolean
     *
     * @param b a boolean to convert to a SplitResult
     * @return the converted SplitResult
     */
    protected SplitResult booleanToSplitResult(boolean b) {
        List<String> branches = new ArrayList<String>();
        branches.add(b ? "True" : "False");
        return new SplitResult(branches);
    }

}
