/*
 * Copyright 2011 The Kuali Foundation
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

package org.kuali.ext.mm.b2b.cxml.invoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.CxmlInvoice;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.kuali.ext.mm.gl.service.GeneralLedgerProcessor;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.kns.util.ObjectUtils;


/**
 * @author harsha07
 */
public class CancellationInvoice extends B2bInvoice {

    private String refPayloadId;

    private static final long serialVersionUID = 8361146608370149168L;

    /**
     * @see org.kuali.ext.mm.b2b.cxml.invoice.B2bInvoice#process()
     */
    @Override
    public void process() {
        if (!validate().isValid()) {
            // process only if validations are met
            return;
        }
        OrderDocument order = matchingOrder();
        Warehouse warehouse = MMServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
                Warehouse.class, order.getWarehouseCd());
        List<FinancialAccountingLine> customerAcctLines = createCustomerAccountLines();
        List<FinancialAccountingLine> warehouseAccountLines = createWarehouseAcctLines(order,
                warehouse);
        HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups = new HashMap<WarehouseGlGroup, WarehouseGlGroup>();
        GeneralLedgerBuilderService glpeService = SpringContext
                .getBean(GeneralLedgerBuilderService.class);
        glpeService.buildBillingGlpes(glGroups, warehouse, customerAcctLines,
                warehouseAccountLines, "Stores credit");

        ArrayList<FinancialGeneralLedgerPendingEntry> billEntries = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        for (WarehouseGlGroup group : glGroups.values()) {
            FinancialGeneralLedgerPendingEntry targetEntry = group.getTargetEntry();
            targetEntry.setWarehouseCode(warehouse.getWarehouseCd());
            billEntries.add(targetEntry);
        }
        setProcessed(true);
        SpringContext.getBean(GeneralLedgerProcessor.class).saveInternally(billEntries);
        setGlProcessed(true);
    }

    /**
     * @see org.kuali.ext.mm.b2b.cxml.invoice.B2bInvoice#validate()
     */
    @Override
    public B2bInvoiceStatus validate() {
        if (StringUtils.isBlank(getRefPayloadId())) {
            return new B2bInvoiceStatus(false, "406", "Not Acceptable",
                "Reference Invoice ID information is missing.");
        }
        CxmlInvoice matchingCxmlInvoice = matchingCxmlInvoice();

        if (ObjectUtils.isNull(matchingCxmlInvoice)) {
            return new B2bInvoiceStatus(false, "406", "Not Acceptable",
                "No matching reference invoice is found.");
        }

        if (matchingCxmlInvoice.isProcessedInd()) {
            StandardInvoice matchingB2bInvoice = matchingB2bInvoice();
            if (ObjectUtils.isNull(matchingB2bInvoice)
                    || !"standard".equalsIgnoreCase(matchingB2bInvoice.getPurpose())) {
                return new B2bInvoiceStatus(false, "406", "Not Acceptable",
                    "Matching invoice is not a Standard Invoice.");

            }
        }
        return new B2bInvoiceStatus();
    }

    /**
     * Gets the refPayloadId property
     * 
     * @return Returns the refPayloadId
     */
    public String getRefPayloadId() {
        return this.refPayloadId;
    }

    /**
     * Sets the refPayloadId property value
     * 
     * @param refPayloadId The refPayloadId to set
     */
    public void setRefPayloadId(String refPayloadId) {
        this.refPayloadId = refPayloadId;
    }

    public CxmlInvoice matchingCxmlInvoice() {
        if (StringUtils.isNotBlank(getRefPayloadId())) {
            return MMServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
                    CxmlInvoice.class, getRefPayloadId());
        }
        return null;
    }

    public StandardInvoice matchingB2bInvoice() {
        if (StringUtils.isNotBlank(getRefPayloadId())) {
            return MMServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
                    StandardInvoice.class, getRefPayloadId());
        }
        return null;
    }
}
