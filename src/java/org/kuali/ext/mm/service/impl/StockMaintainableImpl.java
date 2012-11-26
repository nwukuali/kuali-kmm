package org.kuali.ext.mm.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.gl.GeneralLedgerPostable;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.kuali.ext.mm.gl.service.GeneralLedgerProcessor;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.ext.mm.service.StockHistoryService;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.sys.batch.service.CatalogItemService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;

import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StockMaintainableImpl extends KualiMaintainableImpl implements GeneralLedgerPostable {

    private static final long serialVersionUID = -156820600923103605L;
    private List<FinancialGeneralLedgerPendingEntry> financialGeneralLedgerPendingEntries;
    private String transferStock;
    private ArrayList stockByWarehouseList;

    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> parameters) {
        String[] actionCode = parameters.get("actionCode");
        if (actionCode != null) {
            HashMap<String, String> actionMap = new HashMap<String, String>();
            actionMap.put("actionCode", actionCode[0]);
            GlobalVariables.getUserSession().addObject("actionCode", actionMap);
        }
    }

    @Override
    public Map populateBusinessObject(Map<String, String> fieldValues,
            MaintenanceDocument maintenanceDocument, String methodToCall) {
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        StockService stockService = SpringContext.getBean(StockService.class);
        Maintainable stockMaintainable = maintenanceDocument.getNewMaintainableObject();
        Maintainable oldStockMaintainable = maintenanceDocument.getOldMaintainableObject();
        Stock stock = (Stock) stockMaintainable.getBusinessObject();
        Stock oldStock = (Stock) oldStockMaintainable.getBusinessObject();
        HashMap<String, String> actionMap = (HashMap<String, String>) GlobalVariables
                .getUserSession().retrieveObject("actionCode");
        String action = null;

        if (actionMap == null) {
            if (stock.getActionCode() == null) {
                return super.populateBusinessObject(fieldValues, maintenanceDocument, methodToCall);
            }
            action = stock.getActionCode();
        }
        else {
            action = actionMap.get("actionCode");
        }

        stock.setActionCode(action);
        List<StockBalance> sBalList = stock.getStockBalances();
        List<StockBalance> oldsBalList = oldStock.getStockBalances();

        // String subAction = findSubAction(maintenanceDocument);

        if (fieldValues.size() > 0) {
            if (action.equalsIgnoreCase("transferStock")) {
                if ("refresh".equalsIgnoreCase(methodToCall)) {
                    if (!sBalList.isEmpty()) {
                        for (int i = 0; i < sBalList.size(); i++) {
                            StockBalance newStockBalanceObject = sBalList.get(i);
                            int binId = newStockBalanceObject.getBinId();
                            String key = "stockBalances[" + i + "].binId";
                            String keyValue = fieldValues.get(key);
                            if (!StringUtils.isBlank(keyValue)) {
                                if (binId != Integer.parseInt(keyValue)) {
                                    StockBalance existingStockBalance = stockService
                                            .retrieveStockBalance(Integer.parseInt(keyValue));
                                    if (existingStockBalance == null) {
                                        existingStockBalance = new StockBalance();
                                        existingStockBalance.setBinId(Integer.parseInt(keyValue));
                                        existingStockBalance.refreshReferenceObject("bin");
//                                        existingStockBalance.setBinId(Integer.parseInt(keyValue));
                                        existingStockBalance.setQtyOnHand(0);
                                    }
                                    sBalList.set(i, existingStockBalance);
                                }
                            }
                        }
                    }
                }
                else if ("route".equalsIgnoreCase(methodToCall)) {

                    if (!sBalList.isEmpty()) {
                        for (int i = 0; i < sBalList.size(); i++) {
                            StockBalance newStockBalanceObject = sBalList.get(i);
                            StockBalance oldSB = oldsBalList.get(i);
                            if (!oldSB.getBin().getBinId().equals(
                                    newStockBalanceObject.getBin().getBinId())) {
                                String key = "stockBalances[" + i
                                        + "].quantityBeingAdjustedFromOldToNew";
                                String keyValue = fieldValues.get(key);
                                if (!StringUtils.isBlank(keyValue)) {
                                    newStockBalanceObject.setQtyOnHand(Integer.parseInt(keyValue));
                                }
                                StockBalance existingStockBalance = stockService
                                        .retrieveStockBalance(newStockBalanceObject.getBinId());
                                if (existingStockBalance != null) {
                                    newStockBalanceObject.setQtyOnHand(existingStockBalance
                                            .getQtyOnHand()
                                            + Integer.parseInt(keyValue));
                                }
                                newStockBalanceObject.setTransferSourceBinId(oldSB.getBinId());
                                sBalList.set(i, newStockBalanceObject);
                            }
                            else {
                                // bin has not changed...
                            }
                        }
                    }
                }
            }
            else if (action.equalsIgnoreCase("adjustBalance")) {
                if (methodToCall.equalsIgnoreCase("route")) {

                    if (!sBalList.isEmpty()) {
                        for (int i = 0; i < sBalList.size(); i++) {
                            StockBalance oldStockBalanceObject = oldsBalList.get(i);
                            StockBalance newStockBalanceObject = sBalList.get(i);
                            int qtyOnHand = oldStockBalanceObject.getQtyOnHand();

                            String qtyBeingAdjusted = fieldValues.get("stockBalances[" + i
                                    + "].quantityBeingAdjusted");
                            if (!StringUtils.isBlank(qtyBeingAdjusted)) {
                                Pattern isInteger = Pattern.compile("\\d+");
                                Matcher m = isInteger.matcher(qtyBeingAdjusted);
                                if (m.matches() == true) {
                                    String reasonCd = newStockBalanceObject.getStockTransReason()
                                            .getStockTransReasonCd();

                                    if (!StringUtils.isBlank(reasonCd)) {
                                        StockTransReason stR = bOS.findBySinglePrimaryKey(
                                                StockTransReason.class, reasonCd);
                                        String incDec = stR.getIncrementDecrementInd();
                                        Integer adjustmentQty = Integer.parseInt(qtyBeingAdjusted);
                                        boolean isDecrement = MMConstants.StockTransReason.DECREMENT.equals(incDec);
                                        if ((isDecrement && adjustmentQty <= qtyOnHand)
                                                || !MMConstants.StockTransReason.NO_EFFECT.equals(incDec)) {
                                            adjustmentQty *= isDecrement ? -1 : 1;
                                            newStockBalanceObject.setQtyOnHand(qtyOnHand + adjustmentQty);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.populateBusinessObject(fieldValues, maintenanceDocument, methodToCall);
    }

    /**
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#doRouteStatusChange(org.kuali.rice.kns.bo.DocumentHeader)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void doRouteStatusChange(DocumentHeader documentHeader) {
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        StockService stockService = SpringContext.getBean(StockService.class);
        StockHistoryService stockHistoryService = SpringContext.getBean(StockHistoryService.class);
        PersistableBusinessObject stockObject = this.getBusinessObject();

        Stock stock = (Stock) stockObject; // Current Object
        Stock oldStockObject = stockService.retrieveStock(stock.getStockId()); // Old Stock Object

        List<StockHistory> stockHist = null;
        if (documentHeader.getWorkflowDocument().isEnroute()) {
            GlobalVariables.getUserSession().removeObject("actionCode");
        }

        if (null != stock.getActionCode()) {
            String action = stock.getActionCode();

            if (documentHeader.getWorkflowDocument().isProcessed()) {

                // Code for Adjusting Stock Balance
                if ("adjustBalance".equalsIgnoreCase(action)) {
                    stockHist = new ArrayList();
                    List<StockBalance> sBalList = stock.getStockBalances();
                    List<StockBalance> updatedBalances = new ArrayList<StockBalance>();
                    if (!sBalList.isEmpty()) {
                        for (int i = 0; i < sBalList.size(); i++) {
                            StockBalance newStockBalanceObject = sBalList.get(i);
                            String strAdjustQty = newStockBalanceObject.getQuantityBeingAdjusted();
                            StockTransReason stockTransReason = newStockBalanceObject
                                    .getStockTransReason();
                            if (!StringUtils.isBlank(strAdjustQty)
                                    && !StringUtils.isBlank(stockTransReason.getStockTransReasonCd())) {
                                String incrementInd = stockTransReason.getIncrementDecrementInd();
                                Integer intAdjustQty = (StringUtils.isNumeric(strAdjustQty) ? Integer
                                        .parseInt(strAdjustQty) : 0);
                                intAdjustQty *= (MMConstants.StockTransReason.DECREMENT
                                        .equals(incrementInd)) ? -1 : 1;
                                StockBalance currentBalance = stockService
                                        .adjustStockQuantityOnHand(newStockBalanceObject, intAdjustQty);
                                currentBalance.setQuantityBeingAdjusted(strAdjustQty);
                                currentBalance.setStockTransReason(stockTransReason);
                                stockHist.add(stockHistoryService
                                        .createStockHistoryForBalanceChange(currentBalance,
                                                intAdjustQty.doubleValue(), stockTransReason
                                                        .getStockTransReasonCd()));
                                stock.setStockHistoryList(stockHist);
                                updatedBalances.add(currentBalance);                            
                            }
                        }
                        stock.setStockBalances(updatedBalances);
                    }


                }

                // Code for Unit of Issue Change
                else if ("adjustUnitOfIssue".equalsIgnoreCase(action)) {
                    updateUIssueCodeAndCostInCatalogItemTable(stock, bOS);
                    updateQuantityOnHandInStockBalanceTable(stock, bOS, oldStockObject, action);
                }

                // Code for Transfer Stock from one to another; full or partial
                else if ("transferStock".equalsIgnoreCase(action)) {
                    List<StockBalance> sBalList = stock.getStockBalances();
                    List<StockBalance> transferToStockBalList = new ArrayList<StockBalance>();
                    List<StockBalance> transferFromStockBalList = new ArrayList<StockBalance>();
                    stockHist = new ArrayList();
                    if (!sBalList.isEmpty()) {
                        for (StockBalance sBal : sBalList) {
                            if (StringUtils.isNotBlank(sBal.getQuantityBeingAdjustedFromOldToNew())) {
                                StockBalance newStockBalanceObject = sBal;
                                StockBalance oldStockBalanceObject = stockService
                                        .retrieveStockBalance(newStockBalanceObject
                                                .getTransferSourceBinId());
                                String strTransferQty = newStockBalanceObject
                                    .getQuantityBeingAdjustedFromOldToNew();
                                Integer transferQuantity = (StringUtils.isNumeric(strTransferQty) 
                                        ? Integer.parseInt(strTransferQty) : 0);
                                Integer afterQtyOld = oldStockBalanceObject.getQtyOnHand()
                                        - transferQuantity;
                                // if full move
                                if (oldStockBalanceObject.getQtyOnHand().doubleValue() <= transferQuantity) {
                                    transferQuantity = oldStockBalanceObject.getQtyOnHand();
                                    oldStockBalanceObject.setQtyOnHand(0);
                                }
                                else { // if partial move
                                    oldStockBalanceObject.setQtyOnHand(afterQtyOld);
                                }
                                transferFromStockBalList.add(oldStockBalanceObject);

                                stockHist.add(stockHistoryService
                                        .createStockHistoryForBalanceChange(oldStockBalanceObject,
                                                -transferQuantity.doubleValue(),
                                                MMConstants.StockTransReason.TRANS_OUT));

                                newStockBalanceObject = stockService.adjustStockQuantityOnHand(newStockBalanceObject, transferQuantity);
//                                StockBalance existingStockBalance = stockService.retrieveStockBalance(binId);newStockBalanceObject.getBinId());

//                                if (newStockBalanceExisting != null) {
//                                    newStockBalanceObject = newStockBalanceExisting;
//                                    double afterQtyNew = transferQuantity
//                                            + newStockBalanceObject.getQtyOnHand();
//                                    newStockBalanceObject.setQtyOnHand((int) afterQtyNew);
//                                }

                                if (!oldStockBalanceObject.getStockId().equals(
                                        newStockBalanceObject.getStockId()))
                                    newStockBalanceObject.setLastCheckinDt(oldStockBalanceObject
                                            .getLastCheckinDt());
                                newStockBalanceObject.setStock(oldStockBalanceObject.getStock());
                                newStockBalanceObject
                                        .setStockId(oldStockBalanceObject.getStockId());
                                stockHist.add(stockHistoryService
                                        .createStockHistoryForBalanceChange(newStockBalanceObject,
                                                transferQuantity.doubleValue(),
                                                MMConstants.StockTransReason.TRANS_IN));

                                transferToStockBalList.add(newStockBalanceObject);

                                transferStock = oldStockBalanceObject.getBinId()
                                        + ":"
                                        + newStockBalanceObject.getBinId()
                                        + ":"
                                        + newStockBalanceObject
                                                .getQuantityBeingAdjustedFromOldToNew();

                                stock.getStockHistoryList().addAll(stockHist);
                            }
                        }
                        sBalList = transferToStockBalList;
                        sBalList.addAll(transferFromStockBalList);
                    }
                    stock.setStockBalances(sBalList);
                }
                // generate GL entries
                SpringContext.getBean(GeneralLedgerProcessor.class).doRouteStatusChange(this,
                        documentHeader);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void updateQuantityOnHandInStockBalanceTable(Stock stock, BusinessObjectService bOS,
            Stock oldStockObject, String action) {

        if (!stock.getSalesUnitOfIssueCd().equalsIgnoreCase(oldStockObject.getSalesUnitOfIssueCd())) {

            HashMap fieldValues = new HashMap();
            fieldValues.put("stockId", stock.getStockId());
            List<StockBalance> sBList = (List<StockBalance>) bOS.findMatching(StockBalance.class,
                    fieldValues);

            int totalQty = getTotalQty(stock.getStockId());
            stockByWarehouseList = stock.getStockQuntityOnHandPerWarehouse();

            if (!sBList.isEmpty()) {
                for (int i = 0; i < sBList.size(); i++) {
                    StockBalance stockBalanceObject = sBList.get(i);

                    List<StockHistory> stockHist = null;
                    stockHist = new ArrayList();

                    int orginalQOH = stockBalanceObject.getQtyOnHand();

                    HashMap costValues = fieldValues;
                    costValues.put(MMConstants.CostCode.STOCK_COST_CODE, Stock
                            .getCurrentStockPriceCode());
                    List<StockCost> oldStockCostObjectList = (List<StockCost>) bOS.findMatching(
                            StockCost.class, costValues);
                    StockCost oldStockCostObject = oldStockCostObjectList.get(0);

                    double totalCost = totalQty * oldStockCostObject.getStockCst().doubleValue();

                    // If "Sales Unit Of Issue Rate" is less than 1
                    if (stock.getSalesUnitOfIssueRt().doubleValue() > 1) {

                        // If "Sales Unit Of Issue Rate" is greater than 1 & there is no residual amount
                        // 2 History Lines will be generated
                        if (stock.getSalesUnitOfIssueRt().doubleValue() % 1 == 0.0) {

                            StockHistory shOld = newHistory(stock, action);
                            shOld.setBinId(stockBalanceObject.getBinId());
                            shOld.setStockTransReasonCode("UIOLD");
                            shOld.setBeforeStockQty(stockBalanceObject.getQtyOnHand());
                            shOld.setBeforeStockUnitOfIssueCd(oldStockObject
                                    .getSalesUnitOfIssueCd());
                            shOld.setBeforeStockPrc(oldStockCostObject.getStockCst());
                            shOld
                                    .setTransStockQty(-stockBalanceObject.getQtyOnHand()
                                            .doubleValue());
                            shOld.setTransStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            shOld.setTransStockPrc(oldStockCostObject.getStockCst().negated());
                            shOld.setAfterStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            shOld.setAfterStockPrc(MMDecimal.ZERO);
                            shOld.setAfterStockQty(0.0);

                            // The below line updates the stockBalanceObject with the new Quantity..
                            int newQty = orginalQOH * stock.getSalesUnitOfIssueRt().intValue();
                            stockBalanceObject.setQtyOnHand(newQty);
                            stock.setStockBalances(sBList);

                            stockHist.add(shOld);

                            StockHistory shNew = newHistory(stock, action);
                            shNew.setBinId(stockBalanceObject.getBinId());
                            shNew.setStockTransReasonCode("UINEW");

                            shNew.setBeforeStockQty(0);
                            shNew.setBeforeStockUnitOfIssueCd(oldStockObject
                                    .getSalesUnitOfIssueCd());
                            shNew.setBeforeStockPrc(MMDecimal.ZERO);

                            shNew.setTransStockQty(stockBalanceObject.getQtyOnHand().doubleValue());
                            shNew.setTransStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());

                            // The below 3 line updates the StockCost table due to change in Unit of Issue Code ..
                            double newStockCost = totalCost / newQty;
                            oldStockCostObject.setStockCst(new MMDecimal(newStockCost));
                            stock.setStockPrices(oldStockCostObjectList);

                            shNew.setTransStockPrc(new MMDecimal(newStockCost));
                            shNew.setAfterStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            shNew.setAfterStockPrc(new MMDecimal(newStockCost));
                            shNew.setAfterStockQty(orginalQOH
                                    * (stock.getSalesUnitOfIssueRt().doubleValue()));
                            stockHist.add(shNew);

                        }
                        else { // 3 History Lines

                            StockHistory shOld = newHistory(stock, action);
                            shOld.setBinId(stockBalanceObject.getBinId());
                            shOld.setStockTransReasonCode("UIOLD");

                            shOld.setBeforeStockQty(stockBalanceObject.getQtyOnHand());
                            shOld.setBeforeStockUnitOfIssueCd(oldStockObject
                                    .getSalesUnitOfIssueCd());
                            shOld.setBeforeStockPrc(oldStockCostObject.getStockCst());

                            shOld
                                    .setTransStockQty(-stockBalanceObject.getQtyOnHand()
                                            .doubleValue());
                            shOld.setTransStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            shOld.setTransStockPrc(oldStockCostObject.getStockCst().negated());

                            shOld.setAfterStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            shOld.setAfterStockPrc(MMDecimal.ZERO);
                            shOld.setAfterStockQty(0.0);
                            stockHist.add(shOld);

                            StockHistory shNew = newHistory(stock, action);
                            shNew.setBinId(stockBalanceObject.getBinId());
                            shNew.setStockTransReasonCode("UINEW");

                            shNew.setBeforeStockQty(0);
                            shNew.setBeforeStockUnitOfIssueCd(oldStockObject
                                    .getSalesUnitOfIssueCd());
                            shNew.setBeforeStockPrc(MMDecimal.ZERO);

                            shNew.setTransStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            shNew.setAfterStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            String residualQtyString2 = Double.toString(orginalQOH
                                    * stock.getSalesUnitOfIssueRt().doubleValue());
                            String[] answer = residualQtyString2.split("\\.");
                            shNew.setTransStockQty(Double.valueOf(answer[0]));
                            shNew.setAfterStockQty(Double.valueOf(answer[0]));

                            stockBalanceObject.setQtyOnHand(Integer.valueOf(answer[0]));
                            stock.setStockBalances(sBList);

                            double unitResCost = oldStockCostObject.getStockCst().doubleValue();

                            // The below 3 line updates the StockCost table due to change in Unit of Issue Code ..
                            double newStockCost = oldStockCostObject.getStockCst().doubleValue()
                                    / stock.getSalesUnitOfIssueRt().doubleValue();
                            oldStockCostObject.setStockCst(new MMDecimal(newStockCost));
                            stock.setStockPrices(oldStockCostObjectList);

                            shNew.setTransStockPrc(oldStockCostObject.getStockCst());
                            shNew.setAfterStockPrc(oldStockCostObject.getStockCst());
                            stockHist.add(shNew);

                            String residualQtyString3 = Double.toString(orginalQOH
                                    * stock.getSalesUnitOfIssueRt().doubleValue());
                            String[] answer3 = residualQtyString3.split("\\.");

                            if (Double.valueOf("." + answer3[1]) != 0.0) {

                                StockHistory shResidual = newHistory(stock, action);
                                shResidual.setBinId(stockBalanceObject.getBinId());
                                shResidual.setStockTransReasonCode("UIREMAIN");
                                shResidual.setResidualTag(stock.getStockHistory().getResidualTag());

                                shResidual.setBeforeStockQty(0);
                                shResidual.setBeforeStockUnitOfIssueCd(oldStockObject
                                        .getSalesUnitOfIssueCd());
                                shResidual.setBeforeStockPrc(MMDecimal.ZERO);

                                double residualQTy = totalQty - Double.parseDouble(answer[0])
                                        / stock.getSalesUnitOfIssueRt().doubleValue();
                                shResidual.setTransStockQty(residualQTy);
                                shResidual.setTransStockUnitOfIssueCd(oldStockObject
                                        .getSalesUnitOfIssueCd());
                                shResidual
                                        .setTransStockPrc(new MMDecimal(residualQTy * unitResCost));

                                shResidual.setAfterStockUnitOfIssueCd(oldStockObject
                                        .getSalesUnitOfIssueCd());
                                shResidual
                                        .setAfterStockPrc(new MMDecimal(residualQTy * unitResCost));
                                shResidual.setAfterStockQty(residualQTy);

                                stockHist.add(shResidual);
                            }
                        }
                    }
                    else // If "Sales Unit Of Issue Rate" is less than 1
                    if (stock.getSalesUnitOfIssueRt().doubleValue() < 1) {

                        // If "Sales Unit Of Issue Rate" is less than 1 & there is no residual amount
                        // 2 History Lines will be generated
                        if (stockBalanceObject.getQtyOnHand()
                                % stock.getSalesUnitOfIssueRt().doubleValue() == 0.0) {

                            StockHistory shOld = newHistory(stock, action);
                            shOld.setBinId(stockBalanceObject.getBinId());
                            shOld.setStockTransReasonCode("UIOLD");

                            shOld.setBeforeStockQty(stockBalanceObject.getQtyOnHand());
                            shOld.setBeforeStockUnitOfIssueCd(oldStockObject
                                    .getSalesUnitOfIssueCd());
                            shOld.setBeforeStockPrc(oldStockCostObject.getStockCst());

                            shOld
                                    .setTransStockQty(-stockBalanceObject.getQtyOnHand()
                                            .doubleValue());
                            shOld.setTransStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            shOld.setTransStockPrc(oldStockCostObject.getStockCst().negated());

                            shOld.setAfterStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            shOld.setAfterStockPrc(MMDecimal.ZERO);
                            shOld.setAfterStockQty(0.0);
                            stockBalanceObject.setQtyOnHand(0);
                            stockHist.add(shOld);

                            StockHistory shNew = newHistory(stock, action);
                            shNew.setBinId(stockBalanceObject.getBinId());
                            shNew.setStockTransReasonCode("UINEW");

                            shNew.setBeforeStockQty(0);
                            shNew.setBeforeStockUnitOfIssueCd(oldStockObject
                                    .getSalesUnitOfIssueCd());
                            shNew.setBeforeStockPrc(MMDecimal.ZERO);

                            shNew.setTransStockQty(orginalQOH
                                    / stock.getSalesUnitOfIssueRt().doubleValue());
                            shNew.setTransStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            shNew.setAfterStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());

                            double afterQty = (orginalQOH / stock.getSalesUnitOfIssueRt()
                                    .doubleValue());
                            stockBalanceObject.setQtyOnHand((int) afterQty);
                            stock.setStockBalances(sBList);

                            // The below 3 line updates the StockCost table due to change in Unit of Issue Code ..
                            double newStockCost = totalCost / (afterQty);
                            shNew.setTransStockPrc(new MMDecimal(newStockCost));
                            shNew.setAfterStockPrc(new MMDecimal(newStockCost));

                            oldStockCostObject.setStockCst(new MMDecimal(newStockCost));
                            stock.setStockPrices(oldStockCostObjectList);
                            shNew.setAfterStockQty(orginalQOH
                                    / stock.getSalesUnitOfIssueRt().doubleValue());
                            stockHist.add(shNew);

                        }
                        else {
                            // If "Sales Unit Of Issue Rate" is less than 1 & there is residual amount
                            // 3 History Lines will be generated

                            StockHistory shOld = newHistory(stock, action);
                            shOld.setBinId(stockBalanceObject.getBinId());
                            shOld.setStockTransReasonCode("UIOLD");
                            shOld.setBeforeStockQty(stockBalanceObject.getQtyOnHand());
                            shOld.setBeforeStockUnitOfIssueCd(oldStockObject
                                    .getSalesUnitOfIssueCd());
                            shOld.setBeforeStockPrc(oldStockCostObject.getStockCst());
                            shOld
                                    .setTransStockQty(-stockBalanceObject.getQtyOnHand()
                                            .doubleValue());
                            shOld.setTransStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            shOld.setTransStockPrc(oldStockCostObject.getStockCst().negated());
                            shOld.setAfterStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            shOld.setAfterStockPrc(MMDecimal.ZERO);
                            shOld.setAfterStockQty(0.0);
                            stockHist.add(shOld);

                            StockHistory shNew = newHistory(stock, action);
                            shNew.setStockId(stock.getStockId());
                            shNew.setBinId(stockBalanceObject.getBinId());
                            shNew.setStockTransReasonCode("UINEW");

                            shNew.setBeforeStockQty(0);
                            shNew.setBeforeStockUnitOfIssueCd(oldStockObject
                                    .getSalesUnitOfIssueCd());
                            shNew.setBeforeStockPrc(MMDecimal.ZERO);
                            shNew.setTransStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());

                            shNew.setAfterStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
                            String residualQtyString2 = Double.toString(orginalQOH
                                    * stock.getSalesUnitOfIssueRt().doubleValue());
                            String[] answer = residualQtyString2.split("\\.");
                            shNew.setTransStockQty(Double.valueOf(answer[0]));
                            shNew.setAfterStockQty(Double.valueOf(answer[0]));

                            stockBalanceObject.setQtyOnHand(Integer.valueOf(answer[0]));
                            stock.setStockBalances(sBList);

                            double unitResCost = oldStockCostObject.getStockCst().doubleValue();

                            // The below 3 line updates the StockCost table due to change in Unit of Issue Code ..
                            double newStockCt = oldStockCostObject.getStockCst().doubleValue()
                                    / stock.getSalesUnitOfIssueRt().doubleValue();
                            oldStockCostObject.setStockCst(new MMDecimal(newStockCt));
                            stock.setStockPrices(oldStockCostObjectList);

                            shNew.setTransStockPrc(new MMDecimal(newStockCt));
                            shNew.setAfterStockPrc(new MMDecimal(newStockCt));
                            stockHist.add(shNew);

                            String residualQtyString3 = Double.toString(orginalQOH
                                    * stock.getSalesUnitOfIssueRt().doubleValue());
                            String[] answer3 = residualQtyString3.split("\\.");
                            if (Double.valueOf("." + answer3[1]) != 0.0) {
                                StockHistory shResidual = newHistory(stock, action);
                                shResidual.setBinId(stockBalanceObject.getBinId());
                                shResidual.setStockTransReasonCode("UIREMAIN");
                                shResidual.setResidualTag(stock.getStockHistory().getResidualTag());

                                shResidual.setBeforeStockQty(0);
                                shResidual.setBeforeStockUnitOfIssueCd(oldStockObject
                                        .getSalesUnitOfIssueCd());
                                shResidual.setBeforeStockPrc(MMDecimal.ZERO);

                                double residualQTy = totalQty - Double.parseDouble(answer[0])
                                        / stock.getSalesUnitOfIssueRt().doubleValue();

                                shResidual.setTransStockQty(residualQTy);
                                shResidual.setTransStockUnitOfIssueCd(oldStockObject
                                        .getSalesUnitOfIssueCd());
                                shResidual
                                        .setTransStockPrc(new MMDecimal(residualQTy * unitResCost));

                                shResidual.setAfterStockUnitOfIssueCd(oldStockObject
                                        .getSalesUnitOfIssueCd());
                                shResidual
                                        .setAfterStockPrc(new MMDecimal(residualQTy * unitResCost));
                                shResidual.setAfterStockQty(residualQTy);
                                stockHist.add(shResidual);
                            }

                        }
                    }
                    stock.setStockHistoryList(stockHist);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void updateUIssueCodeAndCostInCatalogItemTable(Stock stock, BusinessObjectService bOS) {
        // Update in CatalogITem Table
        HashMap<String, String> fieldValues = new HashMap();
        fieldValues.put("stockId", stock.getStockId());
        CatalogItem relatedCatalogItem = (CatalogItem) bOS.findByPrimaryKey(CatalogItem.class,
                fieldValues);

        List<StockBalance> sBList = (List<StockBalance>) bOS.findMatching(StockBalance.class,
                fieldValues);

        int totalQty = 0;
        for (int i = 0; i < sBList.size(); i++) {
            StockBalance sB = sBList.get(i);
            totalQty = totalQty + sB.getQtyOnHand();
        }

        if (relatedCatalogItem != null) {
            double totalCost = totalQty * relatedCatalogItem.getCatalogPrc().doubleValue();
            double computedNewQtyAsPerIssueRate = totalQty
                    * stock.getSalesUnitOfIssueRt().doubleValue();
            double catalogPrc = totalCost / computedNewQtyAsPerIssueRate;

            relatedCatalogItem.setCatalogUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
            relatedCatalogItem.setCatalogPrc(new MMDecimal(catalogPrc));

            CatalogItemService catalogItemService = SpringContext.getBean(CatalogItemService.class);
            catalogItemService.updateCatalogItem(relatedCatalogItem.getCatalogItemId(), stock
                    .getSalesUnitOfIssueCd(), catalogPrc);
        }
    }

    private StockHistory newHistory(Stock stock, String action) {
        Date today = new Date();
        Timestamp now = new Timestamp(today.getTime());
        StockHistory historyObject = new StockHistory();

        historyObject.setStockId(stock.getStockId());
        historyObject.setHistoryTransTimestamp(now);
        historyObject.setLastUpdateDate(now);

        if ("transferStock".equalsIgnoreCase(action)) {
            historyObject.setBeforeStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
            historyObject.setTransStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
            historyObject.setAfterStockUnitOfIssueCd(stock.getSalesUnitOfIssueCd());
        }
        return historyObject;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#generateGlpeEntries()
     */
    @SuppressWarnings("unchecked")
    public List<FinancialGeneralLedgerPendingEntry> generateGlpeEntries() {

        GeneralLedgerBuilderService generalLedgerBuilderService = SpringContext
                .getBean(GeneralLedgerBuilderService.class);

        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        PersistableBusinessObject stockObject = this.getBusinessObject();

        Stock stock = (Stock) stockObject; // Current Object
        Stock oldStockObject = SpringContext.getBean(StockService.class).retrieveStock(
                stock.getStockId()); // Old Stock Object
        String action = stock.getActionCode();

        HashMap hm = new HashMap();
        hm.put(MMConstants.Stock.STOCK_ID, stock.getStockId());
        hm.put(MMConstants.CostCode.STOCK_COST_CODE, Stock.getCurrentStockPriceCode());

        List<StockCost> oldStockCostList = (List<StockCost>) bOS.findMatching(StockCost.class, hm);
        List<StockBalance> sBalList = stock.getStockBalances();
        HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups = new HashMap<WarehouseGlGroup, WarehouseGlGroup>();


        // Code for Unit of Issue Change
        if ("adjustUnitOfIssue".equalsIgnoreCase(action)) {
            if (!stock.getSalesUnitOfIssueCd().equalsIgnoreCase(
                    oldStockObject.getSalesUnitOfIssueCd())) {

                if (stockByWarehouseList != null & stockByWarehouseList.size() > 0) {
                    for (int i = 0; i < stockByWarehouseList.size(); i++) {
                        String stockWareHouse = (String) stockByWarehouseList.get(i);
                        String[] stockWarehouseSplit = stockWareHouse.split(":");
                        String warehouseCode = stockWarehouseSplit[0];
                        String qtyByWarehouse = stockWarehouseSplit[1];

                        String quantityByWarehouse = "" + Double.parseDouble(qtyByWarehouse)
                                * stock.getSalesUnitOfIssueRt().doubleValue();
                        String[] residualQtyByWarehouseArray = quantityByWarehouse.split("\\.");

                        double residualQTy = Double.parseDouble(qtyByWarehouse)
                                - (Double.parseDouble(residualQtyByWarehouseArray[0]) / stock
                                        .getSalesUnitOfIssueRt().doubleValue());

                        KualiDecimal decreasedCost = new KualiDecimal(residualQTy
                                * oldStockCostList.get(0).getStockCst().doubleValue()
                                * stock.getSalesUnitOfIssueRt().doubleValue());

                        Warehouse warehouse = bOS.findBySinglePrimaryKey(Warehouse.class,
                                warehouseCode);
                        if (decreasedCost.isNonZero()) {
                            generalLedgerBuilderService.decrementInventory(glGroups,
                                    warehouse, "UIREMAIN", decreasedCost,
                                    "Storehouse Invtry Shrinkage");
                        }
                    }
                }
            }
        }
        // Code for Adjusting Stock Balance
        else if ("adjustBalance".equalsIgnoreCase(action)) {
            if (!sBalList.isEmpty()) {
                for (int i = 0; i < sBalList.size(); i++) {
                    StockBalance newStockBalanceObject = sBalList.get(i);

                    if (!StringUtils.isBlank(newStockBalanceObject.getQuantityBeingAdjusted())
                            && !StringUtils.isBlank(newStockBalanceObject.getStockTransReason()
                                    .getStockTransReasonCd())) {
                        newStockBalanceObject.refreshReferenceObject("bin");
                        Warehouse warehouse = SpringContext.getBean(BusinessObjectService.class)
                                .findBySinglePrimaryKey(Warehouse.class,
                                        newStockBalanceObject.getBin().getZone().getWarehouseCd());

                        if (MMConstants.StockTransReason.DECREMENT
                                .equalsIgnoreCase(newStockBalanceObject.getStockTransReason()
                                        .getIncrementDecrementInd())) {

                            KualiDecimal decreasedCost = new KualiDecimal(Integer
                                    .parseInt(newStockBalanceObject.getQuantityBeingAdjusted())
                                    * oldStockCostList.get(0).getStockCst().doubleValue());
                            generalLedgerBuilderService.decrementInventory(glGroups,
                                    warehouse, newStockBalanceObject.getStockTransReason()
                                            .getStockTransReasonCd(), decreasedCost,
                                    "Storehouse Invtry Shrinkage");
                        }
                        else {
                            KualiDecimal increasedCost = new KualiDecimal(Integer
                                    .parseInt(newStockBalanceObject.getQuantityBeingAdjusted())
                                    * oldStockCostList.get(0).getStockCst().doubleValue());

                            generalLedgerBuilderService.incrementInventory(glGroups,
                                    warehouse, newStockBalanceObject.getStockTransReason()
                                            .getStockTransReasonCd(), increasedCost,
                                    "Storehouse Invtry Shrinkage");
                        }
                    }
                }
            }
        }
        // Code for Transfer Stock from one to another; full or partial
        else if ("transferStock".equalsIgnoreCase(action)) {

            if (!StringUtils.isBlank(transferStock)) {
                String[] tranferStockSplit = transferStock.split(":");
                if (tranferStockSplit.length == 3) {
                    String oldBinId = tranferStockSplit[0];
                    String newBinId = tranferStockSplit[1];
                    String qtyTransfered = tranferStockSplit[2];

                    Bin oldBin = bOS.findBySinglePrimaryKey(Bin.class, oldBinId);
                    Warehouse oldWarehouse = bOS.findBySinglePrimaryKey(Warehouse.class, oldBin
                            .getZone().getWarehouseCd());
                    String oldWarehouseCode = oldWarehouse.getWarehouseCd();

                    Bin newBin = bOS.findBySinglePrimaryKey(Bin.class, newBinId);
                    Warehouse newWarehouse = bOS.findBySinglePrimaryKey(Warehouse.class, newBin
                            .getZone().getWarehouseCd());
                    String newWarehouseCode = newWarehouse.getWarehouseCd();

                    if (!oldWarehouseCode.equalsIgnoreCase(newWarehouseCode)) {
                        KualiDecimal changedCost = new KualiDecimal(Double
                                .parseDouble(qtyTransfered)
                                * oldStockCostList.get(0).getStockCst().doubleValue());

                        if (changedCost.isNonZero()) {
                            generalLedgerBuilderService.decrementInventory(glGroups,
                                    oldWarehouse, "TRANSOUT", changedCost,
                                    "Storehouse Invtry Transfer");

                            generalLedgerBuilderService.incrementInventory(glGroups,
                                    newWarehouse, "TRANS_IN", changedCost,
                                    "Storehouse Invtry Transfer");
                        }
                    }
                }
            }
        }

        ArrayList<FinancialGeneralLedgerPendingEntry> entries = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        for (WarehouseGlGroup group : glGroups.values()) {
            FinancialGeneralLedgerPendingEntry targetEntry = group.getTargetEntry();
            if (targetEntry.getTransactionLedgerEntryAmount().isNonZero()) {
                entries.add(targetEntry);
            }
        }
        return entries;
    }

    /**
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    private int getTotalQty(String stockId) {
        BusinessObjectService bOService = SpringContext.getBean(BusinessObjectService.class);

        HashMap fieldValues = new HashMap();
        fieldValues.put("stockId", stockId);
        List<StockBalance> sBList = (List<StockBalance>) bOService.findMatching(StockBalance.class,
                fieldValues);

        int totalQty = 0;
        for (StockBalance sB : sBList) {
            totalQty = totalQty + sB.getQtyOnHand();
        }
        return totalQty;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getDocumentNumber()
     */
    public String getDocumentNumber() {
        return getDocumentNumber();
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getDocumentTypeCode()
     */
    public String getDocumentTypeCode() {
        return "SSTK";
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getFinancialGeneralLedgerPendingEntries()
     */
    public List<FinancialGeneralLedgerPendingEntry> getFinancialGeneralLedgerPendingEntries() {
        return this.financialGeneralLedgerPendingEntries;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#setFinancialGeneralLedgerPendingEntries(java.util.List)
     */
    public void setFinancialGeneralLedgerPendingEntries(
            List<FinancialGeneralLedgerPendingEntry> entries) {
        this.financialGeneralLedgerPendingEntries = entries;
    }

    public List<FinancialGeneralLedgerPendingEntry> getApprovedGeneralLedgerPendingEntries() {
        return SpringContext.getBean(GeneralLedgerProcessor.class)
                .getApprovedGeneralLedgerPendingEntries(getDocumentNumber());
    }
}