/*
 * Copyright 2006 The Kuali Foundation
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

import org.kuali.ext.mm.integration.purap.document.FinancialRequisitionDocument;


/**
 * Defines methods that must be implemented by classes providing a RequisitionService.
 */
public interface FinancialRequisitionService {

    /**
     * Obtains the requisition document from the database given a requisition id as the input parameter
     *
     * @param id the requisition id of the document we want to obtain.
     * @return RequisitionDocument the requisition document whose requisition id is the id in the input parameter of this method.
     */
    public FinancialRequisitionDocument getRequisitionById(Integer id);


    /**
     * Returns the list of Requisitions that are awaiting contract manager assignment
     *
     * @return List<RequisitionDocument>
     */
    public List<FinancialRequisitionDocument> getRequisitionsAwaitingContractManagerAssignment();

    /**
     * Returns the count of how many Requisitions are awaiting contract manager assignment
     *
     * @return int
     */
    public int getCountOfRequisitionsAwaitingContractManagerAssignment();

}
