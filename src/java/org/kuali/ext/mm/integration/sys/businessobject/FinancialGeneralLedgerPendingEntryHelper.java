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

import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import java.util.HashMap;

/**
 * Helper class to build a FinancialGeneralLedgerPendingEntry entry using Accounts and Warehouse Accounts
 */
public final class FinancialGeneralLedgerPendingEntryHelper {
    // No construction allowed
    private FinancialGeneralLedgerPendingEntryHelper() {
    }

    /**
     * Creates using specific warehouse accounting information
     * 
     * @param account Warehouse Account
     * @param finObjCd Financial Object Code
     * @param finSubObjCd Financial Sub Object Code
     * @param balTypCode Balance Type Code
     * @param desc Description
     * @param amount Amount
     * @param debitCreditCode Transaction Debit Credit Code
     * @return New FinancialGeneralLedgerPendingEntry
     */
    public static FinancialGeneralLedgerPendingEntry createGlpe(WarehouseAccounts account,
            String finObjCd, String finSubObjCd, String balTypCode, String desc,
            KualiDecimal amount, String debitCreditCode) {
        FinancialGeneralLedgerPendingEntry entry = new FinancialGeneralLedgerPendingEntry();
        entry.setChartOfAccountsCode(account.getFinCoaCd());
        entry.setAccountNumber(account.getAccountNbr());
        entry.setSubAccountNumber(account.getSubAcctNbr());
        entry.setFinancialObjectCode(finObjCd);
        entry.setFinancialSubObjectCode(finSubObjCd);
        entry.setFinancialBalanceTypeCode(balTypCode);
        entry.setTransactionLedgerEntryDescription(desc);
        entry.setTransactionLedgerEntryAmount(amount.abs());
        entry.setTransactionDebitCreditCode(debitCreditCode);
        return entry;
    }

    public static FinancialGeneralLedgerPendingEntry createGlpe(
            FinancialAccountingLine accountingLine, String finObjCd, String finSubObjCd,
            String balTypCode, String desc, KualiDecimal amount, String debitCreditCode) {
        FinancialGeneralLedgerPendingEntry entry = new FinancialGeneralLedgerPendingEntry();
        entry.setChartOfAccountsCode(accountingLine.getChartOfAccountsCode());
        entry.setAccountNumber(accountingLine.getAccountNumber());
        entry.setSubAccountNumber(accountingLine.getSubAccountNumber());
        entry.setFinancialObjectCode(finObjCd);
        entry.setFinancialSubObjectCode(finSubObjCd);
        entry.setFinancialBalanceTypeCode(balTypCode);
        entry.setTransactionLedgerEntryDescription(desc);
        entry.setTransactionLedgerEntryAmount(amount.abs());
        entry.setTransactionDebitCreditCode(debitCreditCode);
        entry.setOrganizationReferenceId(accountingLine.getOrganizationReferenceId());
        entry.setProjectCode(accountingLine.getProjectCode());
        entry.setDocumentNumber(accountingLine.getDocumentNumber());
        entry.setFinancialDocumentTypeCode(accountingLine.getFinancialDocumentTypeCode());
        return entry;
    }

    /**
     * Creates using specific order accounting information
     * 
     * @param account order Account
     * @param finObjCd Financial Object Code
     * @param finSubObjCd Financial Sub Object Code
     * @param balTypCode Balance Type Code
     * @param desc Description
     * @param amount Amount
     * @param debitCreditCode Transaction Debit Credit Code
     * @return New FinancialGeneralLedgerPendingEntry
     */
    public static FinancialGeneralLedgerPendingEntry createGlpe(Accounts account, String finObjCd,
            String finSubObjCd, String balTypCode, String desc, KualiDecimal amount,
            String debitCreditCode) {
        FinancialGeneralLedgerPendingEntry entry = new FinancialGeneralLedgerPendingEntry();
        entry.setChartOfAccountsCode(account.getFinCoaCd());
        entry.setAccountNumber(account.getAccountNbr());
        entry.setSubAccountNumber(account.getSubAcctNbr());
        entry.setFinancialObjectCode(finObjCd);
        entry.setFinancialSubObjectCode(finSubObjCd);
        entry.setFinancialBalanceTypeCode(balTypCode);
        entry.setTransactionLedgerEntryDescription(desc);
        entry.setTransactionLedgerEntryAmount(amount.abs());
        entry.setTransactionDebitCreditCode(debitCreditCode);
        return entry;
    }

    /**
     * Combines GL Groups using equals() and hash method
     * 
     * @param glGroups Groups
     * @param entry Current entry
     */
    public static void combineGlpe(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            FinancialGeneralLedgerPendingEntry entry) {
        WarehouseGlGroup source = new WarehouseGlGroup(entry);
        WarehouseGlGroup target = glGroups.get(source);
        if (target == null) {
            glGroups.put(source, source);
        }
        else {
            target.combineEntry(entry);
        }
    }
}
