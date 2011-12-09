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

import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.purap.document.FinancialPurchaseOrderDocument;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.util.KualiDecimal;

/**
 * @author harsha07
 */
public interface FinancialPurchasingService {
    /**
     * Submits KMM order to Financial System as a requisition document
     *
     * @param orderDocument MM Order document
     * @return Submitted Requisition Document
     */
    public Document submitRequisition(OrderDocument orderDocument, String initiator);

    /**
     * Return purchase order document by requisition identifier
     *
     * @param requisitionId Requisition Id
     * @return PO Id
     */
    public Integer getPurchaseOrderIdByRequisitionId(Integer requisitionId);

    /**
     * Returns the Financial Purchase order document for a specific requisition id
     *
     * @param requisitionId Requisition id
     * @return Financial Purchase order document
     */
    public FinancialPurchaseOrderDocument getPurchaseOrderByRequisitionId(Integer requisitionId);

    /**
     * @return
     */
    public KualiDecimal getSeparationOfDutiesDollarAmount();
}
