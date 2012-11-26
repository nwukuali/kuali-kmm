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

package org.kuali.ext.mm.integration.purap.document;

import org.kuali.ext.mm.integration.FinancialSystemComponent;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.bo.TransientBusinessObjectBase;

/**
 * @author harsha07
 */
public class FinancialRequisitionDocument extends TransientBusinessObjectBase implements
	ExternalizableBusinessObject, FinancialSystemComponent {
    private Integer reqsId;
    private String documentNumber;
    private String workflowStatusCode;

    private static final long serialVersionUID = 7605772696214619909L;


    /**
     * Gets the documentNumber property
     * 
     * @return Returns the documentNumber
     */
    public String getDocumentNumber() {
        return this.documentNumber;
    }

    /**
     * Sets the documentNumber property value
     * 
     * @param documentNumber The documentNumber to set
     */
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    /**
     * Gets the reqsId property
     * 
     * @return Returns the reqsId
     */
    public Integer getReqsId() {
        return this.reqsId;
    }

    /**
     * Sets the reqsId property value
     * 
     * @param reqsId The reqsId to set
     */
    public void setReqsId(Integer reqsId) {
        this.reqsId = reqsId;
    }

    /**
     * Gets the workflowStatusCode property
     * 
     * @return Returns the workflowStatusCode
     */
    public String getWorkflowStatusCode() {
        return this.workflowStatusCode;
    }

    /**
     * Sets the workflowStatusCode property value
     * 
     * @param workflowStatusCode The workflowStatusCode to set
     */
    public void setWorkflowStatusCode(String workflowStatusCode) {
        this.workflowStatusCode = workflowStatusCode;
    }

}
