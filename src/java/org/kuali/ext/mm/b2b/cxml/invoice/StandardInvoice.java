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

import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.kuali.ext.mm.gl.service.GeneralLedgerProcessor;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.kns.util.ObjectUtils;


/**
 * @author harsha07
 */
public class StandardInvoice extends B2bInvoice {

    private static final long serialVersionUID = -5232997731909005408L;

    /**
     * @see org.kuali.ext.mm.b2b.cxml.invoice.B2bInvoice#process()
     */
    @Override
    public void process() {
        if (!validate().isValid() || isGlProcessed()) {
            // ignore if validations are not met or GL processing is done already
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
        glpeService.buildBillingGlpes(glGroups, warehouse, warehouseAccountLines,
                customerAcctLines, "Stores charge");

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
        B2bInvoiceStatus invoiceStatus = validateCommon();
        if (!invoiceStatus.isValid()) {
            return invoiceStatus;
        }
        MMDecimal invoiceSum = MMDecimal.ZERO;
        for (B2bInvoiceDetail detail : getDetails()) {
            invoiceSum = invoiceSum.add(detail.getNetAmount());
            if (detail.getAccounts().isEmpty()) {
                return new B2bInvoiceStatus(false, "406", "Not Acceptable",
                    "Accounting distribution is missing for item : " + detail.getSupplierPartId());

            }
            for (B2bInvoiceAccount acct : detail.getAccounts()) {
                if (ObjectUtils.isNull(acct.matchingAccount())) {
                    return new B2bInvoiceStatus(false, "417", "Expectation failed.",
                        "AccountingSegment Id: [" + acct.getId() + "] is not valid.");

                }
            }
        }
        if (!getDueAmount().equals(invoiceSum)) {
            return new B2bInvoiceStatus(false, "417", "Expectation failed.",
                "Sum of Invoice Detail Amount :" + invoiceSum + " didnt match total Due Amount:"
                        + getDueAmount().toString());
        }
        return invoiceStatus;
    }
}
