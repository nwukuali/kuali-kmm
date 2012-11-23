/**
 *
 */
package org.kuali.ext.mm.gl.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.businessobject.WarehouseObject;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.gl.GlConstants;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.ext.mm.service.WarehouseAccountingService;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;

import static org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntryHelper.combineGlpe;
import static org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntryHelper.createGlpe;


/**
 * @author harsha07
 */
public class GeneralLedgerByAccountServiceImpl implements GeneralLedgerBuilderService {
    /**
     * Decrement inventory GLPE
     * 
     * @param glGroups GL Groups
     * @param warehouse Warehouse
     * @param stockTransReasonCd Stock Trans Reason Code
     * @param stockDiffAmt Amount changed
     * @param lineDescription Line description
     */
    public void decrementInventory(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal stockDiffAmt,
            String lineDescription) {
        String offsetDesc = "TP Generated Offset";
        WarehouseAccountingService warehouseAcctService = SpringContext
                .getBean(WarehouseAccountingService.class);
        WarehouseAccounts resaleAcct = warehouse.getResaleAccount();
        WarehouseAccounts costOfGoodsAccount = warehouse.getCostOfGoodsAccount();
        WarehouseObject currObject = warehouseAcctService.findWarehouseObjectByReason(warehouse
                .getWarehouseCd(), stockTransReasonCd);


        if (ObjectUtils.isNull(resaleAcct) || ObjectUtils.isNull(costOfGoodsAccount)
                || StringUtils.isBlank(currObject.getFinObjectCd())
                || StringUtils.isBlank(currObject.getOffsetObjectCd())) {
            throw new RuntimeException(
                "Warehouse accounting information is not configured correctly.");
        }
        combineGlpe(glGroups, createGlpe(costOfGoodsAccount, currObject.getFinObjectCd(),
                currObject.getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription,
                stockDiffAmt, GlConstants.GL_DEBIT));
        combineGlpe(glGroups, createGlpe(costOfGoodsAccount, currObject.getOffsetObjectCd(),
                currObject.getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc,
                stockDiffAmt, GlConstants.GL_CREDIT));

        combineGlpe(glGroups, createGlpe(resaleAcct, currObject.getFinObjectCd(), currObject
                .getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription, stockDiffAmt,
                GlConstants.GL_CREDIT));
        combineGlpe(glGroups, createGlpe(resaleAcct, currObject.getOffsetObjectCd(), currObject
                .getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc, stockDiffAmt,
                GlConstants.GL_DEBIT));
    }

    /**
     * Increments inventory GL value
     * 
     * @param glGroups GL groups
     * @param warehouse Warehouse
     * @param stockTransReasonCd Stock Trans Reason Code
     * @param stockDiffAmt Amount
     * @param lineDescription Line description
     */
    public void incrementInventory(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal stockDiffAmt,
            String lineDescription) {
        String offsetDesc = "TP Generated Offset";
        WarehouseAccountingService warehouseAcctService = SpringContext
                .getBean(WarehouseAccountingService.class);
        WarehouseAccounts resaleAcct = warehouse.getResaleAccount();
        WarehouseAccounts costOfGoodsAccount = warehouse.getCostOfGoodsAccount();
        WarehouseObject currObject = warehouseAcctService.findWarehouseObjectByReason(warehouse
                .getWarehouseCd(), stockTransReasonCd);


        if (ObjectUtils.isNull(resaleAcct) || ObjectUtils.isNull(costOfGoodsAccount)
                || StringUtils.isBlank(currObject.getFinObjectCd())
                || StringUtils.isBlank(currObject.getOffsetObjectCd())) {
            throw new RuntimeException(
                "Warehouse accounting information is not configured correctly.");
        }
        combineGlpe(glGroups, createGlpe(costOfGoodsAccount, currObject.getFinObjectCd(),
                currObject.getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription,
                stockDiffAmt, GlConstants.GL_CREDIT));
        combineGlpe(glGroups, createGlpe(costOfGoodsAccount, currObject.getOffsetObjectCd(),
                currObject.getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc,
                stockDiffAmt, GlConstants.GL_DEBIT));
        combineGlpe(glGroups, createGlpe(resaleAcct, currObject.getFinObjectCd(), currObject
                .getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription, stockDiffAmt,
                GlConstants.GL_DEBIT));
        combineGlpe(glGroups, createGlpe(resaleAcct, currObject.getOffsetObjectCd(), currObject
                .getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc, stockDiffAmt,
                GlConstants.GL_CREDIT));
    }

    /**
     * @see org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService#decrementCostOfGoods(org.kuali.ext.mm.businessobject.Warehouse,
     *      org.kuali.rice.kns.util.KualiDecimal, java.lang.String)
     */
    public void decrementCostOfGoods(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal amount,
            String lineDescription) {
        String offsetDesc = "TP Generated Offset";
        WarehouseAccounts costOfGoodsAcct = warehouse.getCostOfGoodsAccount();
        WarehouseAccounts resaleAccount = warehouse.getResaleAccount();
        if (ObjectUtils.isNull(costOfGoodsAcct)
                || StringUtils.isBlank(costOfGoodsAcct.getFinObjectCd())
                || StringUtils.isBlank(costOfGoodsAcct.getOffsetObjectCd())
                || ObjectUtils.isNull(resaleAccount)
                || StringUtils.isBlank(resaleAccount.getFinObjectCd())
                || StringUtils.isBlank(resaleAccount.getOffsetObjectCd())) {
            throw new RuntimeException(
                "Warehouse accounting information is not configured correctly.");
        }
        combineGlpe(glGroups, createGlpe(costOfGoodsAcct, costOfGoodsAcct.getFinObjectCd(),
                costOfGoodsAcct.getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription,
                amount, GlConstants.GL_DEBIT));
        combineGlpe(glGroups, createGlpe(costOfGoodsAcct, costOfGoodsAcct.getOffsetObjectCd(),
                costOfGoodsAcct.getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc,
                amount, GlConstants.GL_CREDIT));

        combineGlpe(glGroups, createGlpe(resaleAccount, resaleAccount.getFinObjectCd(),
                resaleAccount.getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription,
                amount, GlConstants.GL_CREDIT));
        combineGlpe(glGroups, createGlpe(resaleAccount, resaleAccount.getOffsetObjectCd(),
                resaleAccount.getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc,
                amount, GlConstants.GL_DEBIT));


    }

    /**
     * @see org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService#decrementResaleItems(org.kuali.ext.mm.businessobject.Warehouse,
     *      org.kuali.rice.kns.util.KualiDecimal, java.lang.String)
     */
    public void decrementResaleItems(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal amount,
            String lineDescription) {
        decrementInventory(glGroups, warehouse, stockTransReasonCd, amount, lineDescription);
    }


    public void incrementCostOfGoods(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal amount,
            String lineDescription) {
        String offsetDesc = "TP Generated Offset";
        WarehouseAccounts costOfGoodsAcct = warehouse.getCostOfGoodsAccount();
        WarehouseAccounts resaleAccount = warehouse.getResaleAccount();
        if (ObjectUtils.isNull(costOfGoodsAcct)
                || StringUtils.isBlank(costOfGoodsAcct.getFinObjectCd())
                || StringUtils.isBlank(costOfGoodsAcct.getOffsetObjectCd())
                || ObjectUtils.isNull(resaleAccount)
                || StringUtils.isBlank(resaleAccount.getFinObjectCd())
                || StringUtils.isBlank(resaleAccount.getOffsetObjectCd())) {
            throw new RuntimeException(
                "Warehouse accounting information is not configured correctly.");
        }
        combineGlpe(glGroups, createGlpe(costOfGoodsAcct, costOfGoodsAcct.getFinObjectCd(),
                costOfGoodsAcct.getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription,
                amount, GlConstants.GL_CREDIT));
        combineGlpe(glGroups, createGlpe(costOfGoodsAcct, costOfGoodsAcct.getOffsetObjectCd(),
                costOfGoodsAcct.getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc,
                amount, GlConstants.GL_DEBIT));

        combineGlpe(glGroups, createGlpe(resaleAccount, resaleAccount.getFinObjectCd(),
                resaleAccount.getFinSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, lineDescription,
                amount, GlConstants.GL_DEBIT));
        combineGlpe(glGroups, createGlpe(resaleAccount, resaleAccount.getOffsetObjectCd(),
                resaleAccount.getOffsetSubObjCd(), GlConstants.BALANCE_TYPE_ACTUAL, offsetDesc,
                amount, GlConstants.GL_CREDIT));


    }

    /**
     * @see org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService#incrementResaleItems(org.kuali.ext.mm.businessobject.Warehouse,
     *      org.kuali.rice.kns.util.KualiDecimal, java.lang.String)
     */
    public void incrementResaleItems(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, String stockTransReasonCd, KualiDecimal amount,
            String lineDescription) {
        incrementInventory(glGroups, warehouse, stockTransReasonCd, amount, lineDescription);
    }

    public void buildBillingGlpes(HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups,
            Warehouse warehouse, List<FinancialAccountingLine> incomeAcctLines,
            List<FinancialAccountingLine> expenseAcctLines, String lineDesc) {
        String lineDescription = "";
        WarehouseAccounts incomeAccount = warehouse.getIncomeAccount();
        if (ObjectUtils.isNull(incomeAccount)
                || StringUtils.isBlank(incomeAccount.getFinObjectCd())
                || StringUtils.isBlank(incomeAccount.getOffsetObjectCd())) {
            throw new RuntimeException(
                "Warehouse incomeAccount information is not configured correctly.");
        }
        for (FinancialAccountingLine financialAccountingLine : incomeAcctLines) {
            lineDescription = financialAccountingLine.getFinancialDocumentLineDescription();
            combineGlpe(glGroups, createGlpe(financialAccountingLine, financialAccountingLine
                    .getFinancialObjectCode(), financialAccountingLine.getFinancialSubObjectCode(),
                    GlConstants.BALANCE_TYPE_ACTUAL, lineDescription, financialAccountingLine
                            .getAmount(), GlConstants.GL_CREDIT));
            combineGlpe(glGroups, createGlpe(financialAccountingLine, incomeAccount
                    .getOffsetObjectCd(), incomeAccount.getOffsetSubObjCd(),
                    GlConstants.BALANCE_TYPE_ACTUAL, lineDescription, financialAccountingLine
                            .getAmount(), GlConstants.GL_DEBIT));
        }
        for (FinancialAccountingLine financialAccountingLine : expenseAcctLines) {
            lineDescription = financialAccountingLine.getFinancialDocumentLineDescription();
            combineGlpe(glGroups, createGlpe(financialAccountingLine, financialAccountingLine
                    .getFinancialObjectCode(), financialAccountingLine.getFinancialSubObjectCode(),
                    GlConstants.BALANCE_TYPE_ACTUAL, lineDescription, financialAccountingLine
                            .getAmount(), GlConstants.GL_DEBIT));
            combineGlpe(glGroups, createGlpe(financialAccountingLine, incomeAccount
                    .getOffsetObjectCd(), incomeAccount.getOffsetSubObjCd(),
                    GlConstants.BALANCE_TYPE_ACTUAL, lineDescription, financialAccountingLine
                            .getAmount(), GlConstants.GL_CREDIT));
        }
    }
}
