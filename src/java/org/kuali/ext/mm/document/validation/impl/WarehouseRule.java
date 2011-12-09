package org.kuali.ext.mm.document.validation.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.businessobject.WarehouseObject;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.util.GlobalVariables;


public class WarehouseRule extends FinancialMaintenanceDocumentRuleBase {

    /**
     * @see org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase#processCustomAddCollectionLineBusinessRules(org.kuali.rice.kns.document.MaintenanceDocument,
     *      java.lang.String, org.kuali.rice.kns.bo.PersistableBusinessObject)
     */
    @Override
    public boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document,
            String collectionName, PersistableBusinessObject line) {
        boolean valid = super.processCustomAddCollectionLineBusinessRules(document, collectionName,
                line);
        valid &= SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .checkAndErrorSystemAvailability();
        if (valid && "warehouseAccounts".equals(collectionName)) {
            WarehouseAccounts warehouseAccount = (WarehouseAccounts) line;
            valid &= validateWarehouseAccount(warehouseAccount);
        }

        if (valid & "warehouseObjects".equals(collectionName)) {
            WarehouseObject warehouseObject = (WarehouseObject) line;
            valid &= validateWarehouseObject(warehouseObject);
        }
        return valid;
    }

    /**
     * @param valid
     * @param warehouseObject
     * @return
     */
    private boolean validateWarehouseObject(WarehouseObject warehouseObject) {
        boolean valid = true;
        valid &= validateChart(warehouseObject.getFinCoaCd(), "finCoaCd",
                MMKeyConstants.Warehouse.CHART_NOT_VALID);
        valid &= validateObjectCode(warehouseObject.getCurrentFiscalYear(), warehouseObject
                .getFinCoaCd(), warehouseObject.getFinObjectCd(), "finObjectCd",
                MMKeyConstants.Warehouse.OBJ_CODE_NOT_VALID);
        valid &= validateObjectCode(warehouseObject.getCurrentFiscalYear(), warehouseObject
                .getFinCoaCd(), warehouseObject.getOffsetObjectCd(), "offsetObjectCd",
                MMKeyConstants.Warehouse.OFFSET_OBJ_CODE_NOT_VALID);
        return valid;
    }

    /**
     * @param valid
     * @param warehouseAccount
     * @return
     */
    private boolean validateWarehouseAccount(WarehouseAccounts warehouseAccount) {
        boolean valid = true;
        valid &= validateChart(warehouseAccount.getFinCoaCd(), "finCoaCd",
                MMKeyConstants.Warehouse.CHART_NOT_VALID);
        valid &= validateAccount(warehouseAccount.getFinCoaCd(), warehouseAccount.getAccountNbr(),
                "accountNbr", MMKeyConstants.Warehouse.ACCT_NOT_VALID);
        valid &= validateSubAccount(warehouseAccount.getFinCoaCd(), warehouseAccount
                .getAccountNbr(), warehouseAccount.getSubAcctNbr(), "subAcctNbr",
                MMKeyConstants.Warehouse.SUB_ACCT_NOT_VALID);
        valid &= validateObjectCode(warehouseAccount.getCurrentFiscalYear(), warehouseAccount
                .getFinCoaCd(), warehouseAccount.getFinObjectCd(), "finObjectCd",
                MMKeyConstants.Warehouse.OBJ_CODE_NOT_VALID);
        valid &= validateSubObjectCode(warehouseAccount.getCurrentFiscalYear(), warehouseAccount
                .getFinCoaCd(), warehouseAccount.getAccountNbr(),
                warehouseAccount.getFinObjectCd(), warehouseAccount.getFinSubObjCd(),
                "finSubObjCd", MMKeyConstants.Warehouse.SUB_OBJ_CODE_NOT_VALID);
        valid &= validateObjectCode(warehouseAccount.getCurrentFiscalYear(), warehouseAccount
                .getFinCoaCd(), warehouseAccount.getOffsetObjectCd(), "offsetObjectCd",
                MMKeyConstants.Warehouse.OFFSET_OBJ_CODE_NOT_VALID);
        valid &= validateSubObjectCode(warehouseAccount.getCurrentFiscalYear(), warehouseAccount
                .getFinCoaCd(), warehouseAccount.getAccountNbr(), warehouseAccount
                .getOffsetObjectCd(), warehouseAccount.getOffsetSubObjCd(), "offsetSubObjCd",
                MMKeyConstants.Warehouse.OFFSET_SUB_OBJ_CODE_NOT_VALID);
        return valid;
    }

    /**
     * @see org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase#processCustomRouteDocumentBusinessRules(org.kuali.rice.kns.document.MaintenanceDocument)
     */
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        Warehouse warehouse = (Warehouse) getNewBo();
        boolean valid = super.processCustomRouteDocumentBusinessRules(document);
        valid &= SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .checkAndErrorSystemAvailability();
        if (valid) {
            GlobalVariables.getMessageMap().addToErrorPath("document.newMaintainableObject.");
            if (StringUtils.isNotBlank(warehouse.getDefaultChartCode())) {
                valid &= validateChart(warehouse.getDefaultChartCode(), "defaultChartCode",
                        MMKeyConstants.Warehouse.CHART_NOT_VALID);
            }
            if (StringUtils.isNotBlank(warehouse.getDefaultOrgCode())) {
                valid &= validateOrg(warehouse.getDefaultChartCode(),
                        warehouse.getDefaultOrgCode(), "defaultOrgCode",
                        MMKeyConstants.Warehouse.ORG_CODE_NOT_VALID);
            }
            GlobalVariables.getMessageMap().removeFromErrorPath("document.newMaintainableObject.");

            List<WarehouseAccounts> warehouseAccts = warehouse.getWarehouseAccounts();
            int i = 0;
            for (WarehouseAccounts warehouseAccount : warehouseAccts) {
                GlobalVariables.getMessageMap().addToErrorPath(
                        "document.newMaintainableObject.warehouseAccounts[" + i + "]");
                valid &= validateWarehouseAccount(warehouseAccount);
                GlobalVariables.getMessageMap().removeFromErrorPath(
                        "document.newMaintainableObject.warehouseAccounts[" + i + "]");
                i++;
            }
            i = 0;
            List<WarehouseObject> warehouseObjects = warehouse.getWarehouseObjects();
            for (WarehouseObject warehouseObject : warehouseObjects) {
                GlobalVariables.getMessageMap().addToErrorPath(
                        "document.newMaintainableObject.warehouseObjects[" + i + "]");
                valid &= validateWarehouseObject(warehouseObject);
                GlobalVariables.getMessageMap().removeFromErrorPath(
                        "document.newMaintainableObject.warehouseObjects[" + i + "]");
                i++;
            }
        }
        return valid;
    }
}
