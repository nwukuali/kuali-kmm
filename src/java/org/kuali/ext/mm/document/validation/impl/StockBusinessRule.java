package org.kuali.ext.mm.document.validation.impl;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Stock;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.businessobject.StockTransReason;
import org.kuali.ext.mm.businessobject.UnitOfIssue;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.service.StockService;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.GlobalVariables;


public class StockBusinessRule extends MaintenanceDocumentRuleBase {

    @SuppressWarnings("unchecked")
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {

        boolean adjustBalanceError = true;
        boolean adjustUnitOfIssueError = true;
        boolean transferStockError = true;
        boolean isFsAvailable = SpringContext.getBean(FinancialSystemAdaptorFactory.class)
                .checkAndErrorSystemAvailability();
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        StockService stockService = SpringContext.getBean(StockService.class);
        Maintainable maintaibaleStockNew = document.getNewMaintainableObject();
        Maintainable maintaibaleStockOld = document.getOldMaintainableObject();

        Stock newStock = (Stock) maintaibaleStockNew.getBusinessObject();
        Stock oldStock = (Stock) maintaibaleStockOld.getBusinessObject();

        HashMap<String, String> actionMap = (HashMap<String, String>) GlobalVariables
                .getUserSession().retrieveObject("actionCode");
        String action = null;

        if (actionMap != null) {
            action = actionMap.get("actionCode");

            if (newStock != null) {
                List<StockBalance> sBalList = newStock.getStockBalances();


                if ("adjustBalance".equalsIgnoreCase(action)) {

                    if (!sBalList.isEmpty()) {
                        for (int i = 0; i < sBalList.size(); i++) {

                            StockBalance newStockBalanceObject = sBalList.get(i);
                            StockBalance oldStockBalanceObject = stockService.retrieveStockBalance(newStockBalanceObject.getBinId());
                            int qtyOnHand = oldStockBalanceObject.getQtyOnHand();

                            String reasonCd = newStockBalanceObject.getStockTransReason()
                                    .getStockTransReasonCd();
                            if(MMConstants.StockTransReason.NO_EFFECT.equals(newStockBalanceObject.getStockTransReason().getIncrementDecrementInd())) {
                                GlobalVariables.getMessageMap().putError(
                                        "document.newMaintainableObject.stockBalances["
                                                + i + "].stockTransReason.stockTransReasonCd",
                                        MMKeyConstants.StockBalance.TRANSACTION_CODE_NOT_I_OR_D);
                                adjustBalanceError = false;
                            }
                            else {
                                if (!StringUtils.isBlank(newStockBalanceObject
                                        .getQuantityBeingAdjusted())) {
                                    Pattern isInteger = Pattern.compile("\\d+");
                                    Matcher m = isInteger.matcher(newStockBalanceObject
                                            .getQuantityBeingAdjusted());
                                    if (m.matches() == true) {
                                        if (new Integer(newStockBalanceObject
                                                .getQuantityBeingAdjusted()) > qtyOnHand &&  MMConstants.StockTransReason.DECREMENT.equals(newStockBalanceObject.getStockTransReason().getIncrementDecrementInd())) {
                                            GlobalVariables
                                                    .getMessageMap()
                                                    .putError(
                                                            "document.newMaintainableObject.stockBalances["
                                                                    + i + "].quantityBeingAdjusted",
                                                            MMKeyConstants.Stock.QUANTITY_BEING_ADJUSTED_GREATER_THAN_QOH);
                                            adjustBalanceError = false;
                                        }
                                    }
                                    else {
                                        GlobalVariables
                                                .getMessageMap()
                                                .putError(
                                                        "document.newMaintainableObject.stockBalances["
                                                                + i + "].quantityBeingAdjusted",
                                                        MMKeyConstants.Stock.QUANTITY_BEING_ADJUSTED_NOT_INTEGER);
                                        adjustBalanceError = false;
                                    }
                                }
                                else {
                                    if (!StringUtils.isBlank(reasonCd)) {
                                        GlobalVariables.getMessageMap().putError(
                                                "document.newMaintainableObject.stockBalances[" + i
                                                        + "].quantityBeingAdjusted",
                                                MMKeyConstants.Stock.QUANTITY_NOT_SPECIFIED);
                                    }
                                }
    
    
                                if (reasonCd == null) {
                                    if (!StringUtils.isBlank(newStockBalanceObject
                                            .getQuantityBeingAdjusted())) {
                                        GlobalVariables
                                                .getMessageMap()
                                                .putError(
                                                        "document.newMaintainableObject.stockBalances["
                                                                + i
                                                                + "].stockTransReason.stockTransReasonCd",
                                                        MMKeyConstants.StockBalance.TRANSACTION_CODE_NOT_SPEFICIFIED);
                                        adjustBalanceError = false;
                                    }
                                }
                                else {
                                    StockTransReason stR = bOS.findBySinglePrimaryKey(
                                            StockTransReason.class, reasonCd);
                                    if (stR == null) {
                                        GlobalVariables.getMessageMap().putError(
                                                "document.newMaintainableObject.stockBalances[" + i
                                                        + "].stockTransReason.stockTransReasonCd",
                                                MMKeyConstants.StockBalance.TRANSACTION_CODE_INVALID);
                                        adjustBalanceError = false;
                                    }
                                    else {
                                        String incDec = stR.getIncrementDecrementInd();
                                        if (StringUtils.isBlank(incDec)) {
                                            GlobalVariables
                                                    .getMessageMap()
                                                    .putError(
                                                            "document.newMaintainableObject.stockBalances["
                                                                    + i
                                                                    + "].stockTransReason.stockTransReasonCd",
                                                            MMKeyConstants.StockBalance.TRANSACTION_CODE_INVALID);
                                            adjustBalanceError = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else

                if ("adjustUnitOfIssue".equalsIgnoreCase(action)) {
                    if (oldStock.getSalesUnitOfIssueCd().equalsIgnoreCase(
                            newStock.getSalesUnitOfIssueCd())) {
                        GlobalVariables.getMessageMap().putError(
                                "document.newMaintainableObject.salesUnitOfIssueCd",
                                MMKeyConstants.Stock.UNIT_OF_ISSUE_CODE_UNCHANGED);
                        adjustUnitOfIssueError = false;
                    }
                    else {
                        String uICode = newStock.getSalesUnitOfIssueCd();
                        UnitOfIssue uI = bOS.findBySinglePrimaryKey(UnitOfIssue.class, uICode);
                        if (uI == null) {
                            GlobalVariables.getMessageMap().putErrorForSectionId(
                                    MMConstants.Stock.STOCK_MAIN_SECTION,
                                    MMKeyConstants.CatalogItem.UI_INVALID);
                        }
                        if (newStock.getSalesUnitOfIssueRt().doubleValue() % 1 != 0.0){
                            int totalQty = 0;

                            double rate = oldStock.getSalesUnitOfIssueRt().doubleValue();
                            if (!sBalList.isEmpty()) {
                                for (int i = 0; i < sBalList.size(); i++) {
                                    totalQty = totalQty + (sBalList.get(i).getQtyOnHand());
                                }

                                String residualQty = Double.toString(totalQty
                                        * oldStock.getSalesUnitOfIssueRt().doubleValue());

                                String[] residualQtyArray = residualQty.split("\\.");
                                if (Double.valueOf("." + residualQtyArray[1]) != 0.0) {
                                    if( null == newStock.getStockHistory().getResidualTag()){
                                        GlobalVariables.getMessageMap().putErrorForSectionId(
                                                MMConstants.Stock.STOCK_MAIN_SECTION,
                                                MMKeyConstants.Stock.RESIDUAL_TAG_MUST_BE_SPECIFIED);
                                        adjustUnitOfIssueError = false;
                                    }
                                }
                            }
                        }
                    }
                }
                else

                if ("transferStock".equalsIgnoreCase(action)) {
                    if (!sBalList.isEmpty()) {

                        int noOfBinsTransfered = 0;
                        for (int i = 0; i < sBalList.size(); i++) {
                            StockBalance stockBalanceObject = sBalList.get(i);
                            if (!StringUtils.isBlank(stockBalanceObject
                                    .getQuantityBeingAdjustedFromOldToNew())) {
                                noOfBinsTransfered++;
                            }
                        }
                        if (noOfBinsTransfered > 1) {
                            GlobalVariables.getMessageMap().putErrorForSectionId("stockBalance",
                                    MMKeyConstants.Stock.ONE_ONE_BIN_TRANSFER_ALLOWED);
                            transferStockError = false;
                        }

                        List<StockBalance> oldStockBalanceObjectList = oldStock.getStockBalances();
                        for (int i = 0; i < sBalList.size(); i++) {
                            StockBalance newStockBalanceObject = sBalList.get(i);
                            
                            StockBalance oldStockBalanceObject = oldStockBalanceObjectList.get(i);
                            int qtyOnHand = oldStockBalanceObject.getQtyOnHand();

                            int returnInt = newStockBalanceObject.getBinId().compareTo(
                                    oldStockBalanceObject.getBinId());
                            if (returnInt == 0) {
                                if (!StringUtils.isBlank(newStockBalanceObject
                                        .getQuantityBeingAdjustedFromOldToNew())) {
                                    GlobalVariables.getMessageMap().putError(
                                            "document.newMaintainableObject.stockBalances[" + i
                                                    + "].bin.binDisDesc",
                                            MMKeyConstants.Stock.SAME_BIN_ID);
                                    transferStockError = false;
                                }
                            }
                            else {
                                StockBalance existingStockBalance = stockService.retrieveStockBalance(newStockBalanceObject.getBinId());
                                if(existingStockBalance != null) {
                                    int comp = oldStockBalanceObject.getStockId().compareTo(
                                            existingStockBalance.getStockId());
                                    if (comp != 0 && existingStockBalance.getQtyOnHand() != 0) {
                                        newStockBalanceObject.setQtyOnHand(existingStockBalance.getQtyOnHand());
                                        GlobalVariables
                                                .getMessageMap()
                                                .putError(
                                                        "document.newMaintainableObject.stockBalances["
                                                                + i + "].bin.binDisDesc",
                                                        MMKeyConstants.StockBalance.BIN_ARLREADY_BEING_UTILIZED);
                                        adjustBalanceError = false;
                                    }                 
                                }

                                if (!StringUtils.isBlank(newStockBalanceObject
                                        .getQuantityBeingAdjustedFromOldToNew())) {
                                    Pattern isInteger = Pattern.compile("\\d+");
                                    Matcher m = isInteger.matcher(newStockBalanceObject
                                            .getQuantityBeingAdjustedFromOldToNew());
                                    if (m.matches() == true) {
                                        if (new Integer(newStockBalanceObject
                                                .getQuantityBeingAdjustedFromOldToNew()) > qtyOnHand) {
                                            GlobalVariables
                                                    .getMessageMap()
                                                    .putError(
                                                            "document.newMaintainableObject.stockBalances["
                                                                    + i
                                                                    + "].quantityBeingAdjustedFromOldToNew",
                                                            MMKeyConstants.Stock.QUANTITY_BEING_TRANSFERED_GREATER_THAN_QOH);
                                            transferStockError = false;
                                        }
                                    }
                                    else {
                                        GlobalVariables
                                                .getMessageMap()
                                                .putError(
                                                        "document.newMaintainableObject.stockBalances["
                                                                + i
                                                                + "].quantityBeingAdjustedFromOldToNew",
                                                        MMKeyConstants.Stock.QUANTITY_BEING_ADJUSTED_FROM_OLD_TO_NEW_NOT_INTEGER);
                                        adjustBalanceError = false;
                                    }
                                }
                                else {
                                    GlobalVariables
                                            .getMessageMap()
                                            .putError(
                                                    "document.newMaintainableObject.stockBalances["
                                                            + i
                                                            + "].quantityBeingAdjustedFromOldToNew",
                                                    MMKeyConstants.Stock.QUANTITY_BEING_ADJUSTED_FROM_OLD_TO_NEW_IS_REQUIRED);
                                    transferStockError = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return isFsAvailable && adjustBalanceError && adjustUnitOfIssueError && transferStockError;
    }
}
