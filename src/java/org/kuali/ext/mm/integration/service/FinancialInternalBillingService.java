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

import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformation;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.rice.kns.bo.DocumentHeader;

/**
 * This interfaces declares the methods required to submit an Internal Billing Document to financial system supported by MM.
 * Adaptors will have to implement this interface depending on the target financial system.
 */
public interface FinancialInternalBillingService {
    /**
     * Transaction enabled version
     *
     * @param billingProfile Billing Profile
     * @param billItems Billing Items
     * @param incomeAcctLines Income Account lines
     * @param expenseAcctLines Expense Account Lines
     * @param assetInfo Asset Info
     * @return IB Document Header
     */
    public DocumentHeader submitInternalBillingDocument(Profile billingProfile,
            List<FinancialInternalBillingItem> billItems,
            List<FinancialAccountingLine> incomeAcctLines,
            List<FinancialAccountingLine> expenseAcctLines,
            FinancialCapitalAssetInformation assetInfo);

    /**
     * Transaction disable version
     *
     * @param billingProfile Billing Profile
     * @param billItems Billing Items
     * @param incomeAcctLines Income Account lines
     * @param expenseAcctLines Expense Account Lines
     * @param assetInfo Asset Info
     * @return IB Document Header
     */
    public DocumentHeader submitBatchInternalBillingDocument(Profile billingProfile,
            List<FinancialInternalBillingItem> billItems,
            List<FinancialAccountingLine> incomeAcctLines,
            List<FinancialAccountingLine> expenseAcctLines,
            FinancialCapitalAssetInformation assetInfo);


    /**
     * Validates a property against a registered system parameter evaluator
     *
     * @param parameterName system Parameter Name
     * @param propertyValue Property Value
     * @return true if evaluation succeeds
     */
    public boolean validateProperty(String parameterName, String propertyValue);
}
