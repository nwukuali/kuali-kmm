/**
 *
 */
package org.kuali.ext.mm.gl.service.impl;

import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.gl.GlConstants;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.TaxLiabilityGeneralLedgerService;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.HashMap;

import static org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntryHelper.combineGlpe;
import static org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntryHelper.createGlpe;

/**
 * @author harsha07
 */
public class TaxLiabilityGeneralLedgerServiceImpl implements TaxLiabilityGeneralLedgerService {

    public void incrementTaxLiability(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, KualiDecimal amount, String lineDescription) {
        String offsetDesc = "TP Generated Offset";
        WarehouseAccounts incomeAccount = warehouse.getIncomeAccount();
        WarehouseAccounts taxAccount = warehouse.getTaxLiabilityAccount();
        if (ObjectUtils.isNull(incomeAccount) || ObjectUtils.isNull(taxAccount)) {
            throw new RuntimeException(
                "Warehouse accounting information is not configured correctly.");
        }
        combineGlpe(glGroups, createGlpe(incomeAccount, incomeAccount.getFinObjectCd(),
                incomeAccount.getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription,
                amount, GlConstants.GL_DEBIT));
        combineGlpe(glGroups, createGlpe(incomeAccount, incomeAccount.getOffsetObjectCd(),
                incomeAccount.getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc,
                amount, GlConstants.GL_CREDIT));
        combineGlpe(glGroups, createGlpe(taxAccount, taxAccount.getFinObjectCd(), taxAccount
                .getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription, amount,
                GlConstants.GL_CREDIT));
        combineGlpe(glGroups, createGlpe(taxAccount, taxAccount.getOffsetObjectCd(), taxAccount
                .getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc, amount,
                GlConstants.GL_DEBIT));
    }

    public void decrementTaxLiability(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, KualiDecimal amount, String lineDescription) {
        String offsetDesc = "TP Generated Offset";
        WarehouseAccounts incomeAccount = warehouse.getIncomeAccount();
        WarehouseAccounts taxAccount = warehouse.getTaxLiabilityAccount();
        if (ObjectUtils.isNull(incomeAccount) || ObjectUtils.isNull(taxAccount)) {
            throw new RuntimeException(
                "Warehouse accounting information is not configured correctly.");
        }
        combineGlpe(glGroups, createGlpe(incomeAccount, incomeAccount.getFinObjectCd(),
                incomeAccount.getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription,
                amount, GlConstants.GL_CREDIT));
        combineGlpe(glGroups, createGlpe(incomeAccount, incomeAccount.getOffsetObjectCd(),
                incomeAccount.getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc,
                amount, GlConstants.GL_DEBIT));
        combineGlpe(glGroups, createGlpe(taxAccount, taxAccount.getFinObjectCd(), taxAccount
                .getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription, amount,
                GlConstants.GL_DEBIT));
        combineGlpe(glGroups, createGlpe(taxAccount, taxAccount.getOffsetObjectCd(), taxAccount
                .getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc, amount,
                GlConstants.GL_CREDIT));
    }
}
