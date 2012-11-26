package org.kuali.ext.mm.document.authorization;

import org.kuali.ext.mm.businessobject.Stock;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.MaintenanceDocumentBase;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationControllerBase;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class StockPresentationController extends MaintenanceDocumentPresentationControllerBase {


    @Override
    public boolean canSave(Document document) {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getConditionallyReadOnlyPropertyNames(MaintenanceDocument document) {
        Set<String> readOnlyFields = super.getConditionallyReadOnlyPropertyNames(document);
        HashMap<String, String> actionMap = (HashMap<String, String>) GlobalVariables
                .getUserSession().retrieveObject("actionCode");

        Stock stock = (Stock) document.getOldMaintainableObject().getBusinessObject();

        if (actionMap != null) {
            String action = actionMap.get("actionCode");

            if ("adjustUnitOfIssue".equalsIgnoreCase(action)) {
                readOnlyFields.add(MMConstants.Stock.STOCK_ID);
                readOnlyFields.add("stockTransReason.incrementDecrementInd");
                readOnlyFields.add("stockId");
                readOnlyFields.add("stockDistributorNbr");
                readOnlyFields.add("distributorNbr");
                readOnlyFields.add("stockDesc");
                readOnlyFields.add("stockTypeCode");
                readOnlyFields.add("agreementNbr");
                readOnlyFields.add("buyUnitOfIssueCd");
                readOnlyFields.add("restrictedRouteCd");
                readOnlyFields.add("cycleCntCd");
                readOnlyFields.add("rentalObjectCode");
                readOnlyFields.add("upcCd");

                if (stock.getSalesUnitOfIssueRt() == null
                        || stock.getSalesUnitOfIssueRt() == new MMDecimal(0)) {
                    readOnlyFields.add("salesUnitOfIssueCd");
                    GlobalVariables.getMessageMap().putInfoForSectionId(
                            MMConstants.Stock.STOCK_MAIN_SECTION,
                            MMConstants.Stock.SALES_UNIT_OF_ISSUE_RT_MUST_BE_SPECIFIED);
                }
                else if (stock.getSalesUnitOfIssueRt().equals(new KualiDecimal(1))) {
                    readOnlyFields.add("salesUnitOfIssueCd");
                    GlobalVariables.getMessageMap().putInfoForSectionId(
                            MMConstants.Stock.STOCK_MAIN_SECTION,
                            MMConstants.Stock.SALES_UNIT_OF_ISSUE_RT_IS_ONE);
                }
            }
            else

            if ("adjustBalance".equalsIgnoreCase(action)) {

                List<StockBalance> sB = ((Stock) document.getNewMaintainableObject()
                        .getBusinessObject()).getStockBalances();
                if (!sB.isEmpty()) {
                    for (int i = 0; i < sB.size(); i++) {
                        readOnlyFields.add("stockBalances[" + i + "].binId");
                        readOnlyFields.add("stockBalances[" + i + "].bin.binDisDesc");
                    }
                }

                readOnlyFields.add("stockDistributorNbr");
                readOnlyFields.add("salesUnitOfIssueCd");
                readOnlyFields.add("stockTransReason.stockTransReasonCd");
                readOnlyFields.add("stockTransReason.stockTransReasonDesc");
                readOnlyFields.add("stockTypeCode");
                readOnlyFields.add("agreementNbr");
                readOnlyFields.add("restrictedRouteCd");
                readOnlyFields.add("cycleCntCd");
            }
            else

            if ("transferStock".equalsIgnoreCase(action)) {
                readOnlyFields.add("stockDistributorNbr");
                readOnlyFields.add("salesUnitOfIssueCd");
            }

        }
        return readOnlyFields;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getConditionallyHiddenPropertyNames(BusinessObject businessObject) {
        Set<String> hideFields = super.getConditionallyHiddenPropertyNames(businessObject);
        MaintenanceDocument document = (MaintenanceDocument) businessObject;

        Stock stock = (Stock) document.getOldMaintainableObject().getBusinessObject();
        HashMap<String, String> actionMap = (HashMap<String, String>) GlobalVariables
                .getUserSession().retrieveObject("actionCode");
        if (actionMap != null) {
            String action = actionMap.get("actionCode");

            if ("adjustUnitOfIssue".equalsIgnoreCase(action)) {
                hideFields.add(MMConstants.Stock.STOCK_ID);
                hideFields.add("stockTransReason.incrementDecrementInd");
                hideFields.add("stockTransReason.stockTransReasonCd");
                hideFields.add("stockTransReason.stockTransReasonDesc");
                hideFields.add("rentalObjectCode");
                hideFields.add("upcCd");
                hideFields.add("stockTypeCode");
                hideFields.add("agreementNbr");
                hideFields.add("restrictedRouteCd");
                hideFields.add("cycleCntCd");

                if (null == stock.getSalesUnitOfIssueRt()) {
                    hideFields.add("stockHistory.residualTag");
                }
                else {
                    int totalQty = 0;
                    List<StockBalance> sBalList = stock.getStockBalances();
                    double rate = stock.getSalesUnitOfIssueRt().doubleValue();
                    if (!sBalList.isEmpty()) {
                        for (int i = 0; i < sBalList.size(); i++) {
                            totalQty = totalQty + (sBalList.get(i).getQtyOnHand());
                        }

                        String residualQty = Double.toString(totalQty
                                * stock.getSalesUnitOfIssueRt().doubleValue());

                        String[] residualQtyArray = residualQty.split("\\.");
                        if (Double.valueOf("." + residualQtyArray[1]) == 0.0 || rate == 1.0) {
                            hideFields.add("stockHistory.residualTag");
                        }
                    }
                    else {
                        if (rate == 1.0) {
                            hideFields.add("stockHistory.residualTag");

                        }
                    }
                }
            }
            else

            if ("adjustBalance".equalsIgnoreCase(action)) {
                hideFields.add("salesUnitOfIssueRt");
                hideFields.add("stockTypeCode");
                hideFields.add("agreementNbr");
                hideFields.add("restrictedRouteCd");
                hideFields.add("cycleCntCd");
                hideFields.add("buyUnitOfIssueCd");
                hideFields.add("stockHistory.residualTag");

                MaintenanceDocumentBase mBase = (MaintenanceDocumentBase) businessObject;
                Stock stockNew = (Stock) mBase.getNewMaintainableObject().getBusinessObject();
                List<StockBalance> sB = stockNew.getStockBalances();
                if (!sB.isEmpty()) {
                    for (int i = 0; i < sB.size(); i++) {
                        hideFields.add("stockBalances[" + i + "].binId");
                        hideFields
                                .add("stockBalances[" + i + "].quantityBeingAdjustedFromOldToNew");
                        if (!stockNew.isPerishableInd()) {
                            hideFields.add("stockBalances[" + i + "].stockPerishableDt");
                        }
                    }
                }
            }
            else

            if ("transferStock".equalsIgnoreCase(action)) {
                hideFields.add("salesUnitOfIssueRt");
                hideFields.add("stockTypeCode");
                hideFields.add("agreementNbr");
                hideFields.add("restrictedRouteCd");
                hideFields.add("cycleCntCd");
                hideFields.add("buyUnitOfIssueCd");
                hideFields.add("stockHistory.residualTag");

                MaintenanceDocumentBase mBase = (MaintenanceDocumentBase) businessObject;
                Stock stockNew = (Stock) mBase.getNewMaintainableObject().getBusinessObject();
                List<StockBalance> sB = stockNew.getStockBalances();
                if (!sB.isEmpty()) {
                    for (int i = 0; i < sB.size(); i++) {
                        hideFields.add("stockBalances[" + i + "].quantityBeingAdjusted");
                        hideFields.add("stockBalances[" + i + "].stockPerishableDt");
                        hideFields.add("stockBalances[" + i
                                + "].stockTransReason.incrementDecrementInd");
                        hideFields.add("stockBalances[" + i
                                + "].stockTransReason.stockTransReasonCd");
                        hideFields.add("stockBalances[" + i
                                + "].stockTransReason.stockTransReasonDesc");
                    }
                }
            }
        }
        return hideFields;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getConditionallyHiddenSectionIds(BusinessObject businessObject) {
        Set<String> fields = super.getConditionallyHiddenSectionIds(businessObject);
        HashMap<String, String> actionMap = (HashMap<String, String>) GlobalVariables
                .getUserSession().retrieveObject("actionCode");
        if (actionMap != null) {
            String action = actionMap.get("actionCode");

            if ("adjustUnitOfIssue".equalsIgnoreCase(action)) {
                fields.add(MMConstants.Stock.STOCK_BALANCE_SECTION);
            }

        }
        return fields;
    }
}