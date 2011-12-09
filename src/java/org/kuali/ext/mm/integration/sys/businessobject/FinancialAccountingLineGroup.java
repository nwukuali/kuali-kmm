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
package org.kuali.ext.mm.integration.sys.businessobject;

import java.util.ArrayList;
import java.util.List;

import org.kuali.ext.mm.gl.GlLineGroup;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;

/**
 * Accounting line grouped data for GL Line
 */
public class FinancialAccountingLineGroup extends GlLineGroup {
    private FinancialAccountingLine targetEntry;
    private List<FinancialAccountingLine> sourceEntries = new ArrayList<FinancialAccountingLine>();

    /**
     * Constructs a GlAccountLineGroup from a GL Line Entry
     *
     * @param entry GL Line
     */
    public FinancialAccountingLineGroup(FinancialAccountingLine entry) {
        setChartOfAccountsCode(entry.getChartOfAccountsCode());
        setAccountNumber(entry.getAccountNumber());
        setSubAccountNumber(entry.getSubAccountNumber());
        setFinancialObjectCode(entry.getFinancialObjectCode());
        setFinancialSubObjectCode(entry.getFinancialSubObjectCode());
        setDocumentNumber(entry.getDocumentNumber());
        setOrganizationReferenceId(entry.getOrganizationReferenceId());
        setProjectCode(entry.getProjectCode());
        this.sourceEntries.add(entry);
        this.targetEntry = (FinancialAccountingLine) ObjectUtils.deepCopy(entry);
        KualiDecimal amount = entry.getAmount();
        setAmount(amount);
    }

    /**
     * Returns true if input GL entry belongs to this account group
     *
     * @param entry Entry
     * @return true if Entry belongs to same account line group
     */
    public boolean isAccounted(FinancialAccountingLine entry) {
        FinancialAccountingLineGroup test = new FinancialAccountingLineGroup(entry);
        return this.equals(test);
    }

    /**
     * This method will combine multiple GL entries for the same account line group, so that m:n association is prevented in the
     * database. This could be a rare case that we need to address. First GL is used as the final target and rest of the GL entries
     * are adjusted.
     *
     * @param entry
     */
    public void combineEntry(FinancialAccountingLine newEntry) {
        this.sourceEntries.add(newEntry);
        this.targetEntry.setAmount(this.targetEntry.getAmount().add(newEntry.getAmount()));
        setAmount(this.targetEntry.getAmount());
    }

    /**
     * Gets the targetEntry attribute.
     *
     * @return Returns the targetEntry
     */
    public FinancialAccountingLine getTargetEntry() {
        return targetEntry;
    }

    /**
     * Sets the targetEntry attribute.
     *
     * @param targetEntry The targetEntry to set.
     */
    public void setTargetEntry(FinancialAccountingLine targetGlEntry) {
        this.targetEntry = targetGlEntry;
    }

    /**
     * Gets the sourceEntries attribute.
     *
     * @return Returns the sourceEntries
     */
    public List<FinancialAccountingLine> getSourceEntries() {
        return sourceEntries;
    }

    /**
     * Sets the sourceEntries attribute.
     *
     * @param sourceEntries The sourceEntries to set.
     */
    public void setSourceEntries(List<FinancialAccountingLine> sourceGlEntries) {
        this.sourceEntries = sourceGlEntries;
    }
}
