package org.kuali.ext.mm.document.validation.impl;

import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.businessobject.WarehouseObject;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.service.WarehouseAccountingService;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.DocumentRuleBase;
import org.kuali.rice.krad.rules.rule.event.ApproveDocumentEvent;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;


public class WorksheetCountDocumentRule extends DocumentRuleBase {

    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomRouteDocumentBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(Document document) {
        return validateWarehouseAccountingInfo(document)
                && super.processCustomRouteDocumentBusinessRules(document);
    }

    /**
     * @see org.kuali.rice.kns.rules.DocumentRuleBase#processCustomApproveDocumentBusinessRules(org.kuali.rice.krad.rules.rule.event.ApproveDocumentEvent)
     */
    @Override
    protected boolean processCustomApproveDocumentBusinessRules(ApproveDocumentEvent approveEvent) {
        boolean canApprove = super.processCustomApproveDocumentBusinessRules(approveEvent);
        canApprove &= validateWarehouseAccountingInfo(approveEvent.getDocument());
        return canApprove & super.processCustomApproveDocumentBusinessRules(approveEvent);
    }

    /**
     * @param wdoc
     * @param warehouse
     * @param canApprove
     * @return
     */
    private boolean validateWarehouseAccountingInfo(Document doc) {
        WorksheetCountDocument wdoc = (WorksheetCountDocument) doc;
        Warehouse warehouse = wdoc.getWarehouse();
        boolean canApprove = true;
        canApprove &= SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .checkAndErrorSystemAvailability();
        WarehouseAccountingService warehouseAcctService = SpringContext
                .getBean(WarehouseAccountingService.class);
        String warehouseCd = wdoc.getWarehouseCd();
        WarehouseAccounts resaleAcct = warehouseAcctService.findWarehouseAccounts(warehouseCd,
                MMConstants.WAREHOUSE_RESALE_ACCT);
        WarehouseAccounts costOfGoodsAccount = warehouseAcctService.findWarehouseAccounts(
                warehouseCd, MMConstants.WAREHOUSE_COST_GOODS_ACCT);
        WarehouseObject shrinkageObject = warehouseAcctService.findWarehouseObject(warehouseCd,
                MMConstants.WAREHOUSE_SHRINKAGE_OBJECT);
        WarehouseObject obsoleteObject = warehouseAcctService.findWarehouseObject(warehouseCd,
                MMConstants.WAREHOUSE_OBSOLENCE_OBJECT);
        String[] parms = new String[] { warehouse.getWarehouseCd(), warehouse.getWarehouseNme() };
        if (ObjectUtils.isNull(resaleAcct)) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,
                    MMKeyConstants.Warehouse.RESALE_ACCT_UNDEFINED, parms);
            canApprove &= false;
        }
        if (ObjectUtils.isNull(costOfGoodsAccount)) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,
                    MMKeyConstants.Warehouse.COST_OF_GOODS_ACCT_UNDEFINED, parms);
            canApprove &= false;
        }
        if (ObjectUtils.isNull(shrinkageObject)) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,
                    MMKeyConstants.Warehouse.SHRINKAGE_OBJ_UNDEFINED, parms);
            canApprove &= false;
        }

        if (ObjectUtils.isNull(obsoleteObject)) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,
                    MMKeyConstants.Warehouse.OBSO_OBJ_UNDEFINED, parms);
            canApprove &= false;
        }
        return canApprove;
    }
}
