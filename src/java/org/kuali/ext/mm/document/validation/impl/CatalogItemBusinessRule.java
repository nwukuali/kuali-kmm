package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialUnitOfMeasure;
import org.kuali.ext.mm.service.BackOrderService;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/*
 * Business Rules:
 *   0.  A Bin cannot be assigned to two stock Items.
 *   0a  Catalog Price is required.
 *   1.  Date of expiration of perishable item needs to be captured.
 *   2.  Perishable items need to have individual bin locations based on expiration.
 *   3.  When deleting Commodity Group that group cannot appear on the Commodity Master
 *       table or the Commodity Subgroup Table.
 *   4.  Commodity group must be unique
 *   5.  Active Indicator can only be set to Inactive if
 *       •   there are no open purchase orders,
 *       •   no back orders,
 *       •   quantity on hand equal to zero
 *       •   quantity allocated equal to zero
 *   5a  An Inactive Indicator can only be set to Active if
 *       .   MM_CATALOG_ITEM_T.Catalog Price is present for a given stock item.
 *       .   That is only after a Check-in process has taken place.
 *   6.  When Active/Inactive Indicator is set to Inactive
 *       •   Zone/Bin/Shelf Indicator/Item Shelf Placement aka (Shelf IDNumber) fields are cleared.
 *       •   Set Order Point and Order Quantity to zero
 *   7.  Whenever the Active/Inactive Indicator is changed, post systems date to the
 *       ‘Last Date of Active/Inactive Indicator Change Field’.
 *   8.  When inserting a new commodity code or activating an inactive item number, the Unit of Issue
 *       must be equal to the Unit of Issue of matching active item number in other warehouses.
 *   9.  When the Maximum Shelf Quantity is not null, the Order Point plus Order Quantity must be
 *       equal to or less than the Maximum Quantity ----- PER ALEX, DO NOT IMPLEMENT THIS ONE!
 *   10. If Sole Source Indicator is True, the Manufacturer Number is required.
 *   11. Neither the Commodity Group nor Subgroup Code can be null if the Commodity Buyout/Stock Code is equal to Stock.
 *   12. Must have a minimum of one Commodity Group/Subgroup Record.
 *   13. Must have one and only one default (Primary) Commodity Group/Subgroup.
 *   14. EHS Unit of Issue, EHS Container, and the Storehouse Unit of Issue to EHS Unit of Issue Conversion Factor
 *       are required if there is an EHS Code; and those fields must be null if EHS code is null.
 *   15. Storehouse Unit of Issue to EHS Unit of Issue Conversion Factor must be > 0 if the EHS Hazardous Code exists.
 *   16. The Warehouse/Zone/Bin/Shelf/Shelf ID number must be unique.
 *   17. Warehouse Code, Commodity Code and UN ID Number combination must be unique.
 *   18. Items with a MAXIMUM_ORDER_QUANTITY of zero cannot be automatically ordered.
 *   19. Items with a HAZARDOUS_CODE value will not be available to customers outside the university.
 *   20. Collections need to be present to add.
*/

public class CatalogItemBusinessRule extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
        boolean isValid = true;       
        CatalogItem newCatalogItem = (CatalogItem) document.getNewMaintainableObject().getBusinessObject();
        if(ObjectUtils.isNotNull(newCatalogItem.getStock())){
        	PersistableBusinessObject pbObject = KRADServiceLocator.getBusinessObjectService().retrieve(newCatalogItem.getStock());
        	Long pbObjectVerNbr = ObjectUtils.isNull(pbObject) ? null : pbObject.getVersionNumber();
        	Long newObjectVerNbr = newCatalogItem.getStock().getVersionNumber();
        	if (pbObjectVerNbr != null && !(pbObjectVerNbr.equals(newObjectVerNbr))) {
            	GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_VERSION_MISMATCH);
            	isValid = false;
        	}
        }
        return isValid;
    }
    
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {

        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        Maintainable newMaintaibaleCatalogItem = document.getNewMaintainableObject();
				//TODO: NWU - Confirm api name change from getBu.. to getData is the same
        CatalogItem newCatalogItem = (CatalogItem) newMaintaibaleCatalogItem.getDataObject();
			  //TODO: NWU - Confirm api name change from getBu.. to getData is the same
        Maintainable oldMaintaibaleCatalogItem = document.getOldMaintainableObject();
        CatalogItem oldCatalogItem = (CatalogItem) oldMaintaibaleCatalogItem.getDataObject();

        String userAction = document.getNewMaintainableObject().getMaintenanceAction();

        if (newCatalogItem != null) {
            if ("newWithExisting".equalsIgnoreCase(userAction)
                    || "edit".equalsIgnoreCase(userAction)) {
                if (newCatalogItem.getStock() == null) {
                    validateCatalogItemData(oldCatalogItem, newCatalogItem, bOS, userAction);
                }
                else {
                    validateStockData(oldCatalogItem, newCatalogItem, bOS, userAction);
                }
            }
            else {// copy
                if (null == newCatalogItem.getStock().getStockId()) {
                    validateCatalogItemData(oldCatalogItem, newCatalogItem, bOS, userAction);
                }
                else {
                    validateStockData(oldCatalogItem, newCatalogItem, bOS, userAction);
                }
            }
        }
        return true;
    }


    @SuppressWarnings("unchecked")
    private void validateCatalogItemData(CatalogItem oldCatalogItem, CatalogItem newCatalogItem,
            BusinessObjectService bOS, String userAction) {

        String distNbr = newCatalogItem.getDistributorNbr();
        String catalogId = newCatalogItem.getCatalogId();
        HashMap hm = new HashMap();
        hm.put("distributorNbr", distNbr);
        hm.put("catalogId", catalogId);
        List<CatalogItem> catItemList = (List<CatalogItem>) bOS.findMatching(CatalogItem.class, hm);
        // The If conditions checks to see that for Catalog Items
        // Catalog ID + Distributor must be Unique system wide ...

        if ("newWithExisting".equalsIgnoreCase(userAction)) {
            if (!catItemList.isEmpty()) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.DISTRIBUTOR_NUMBER_PRESENT);
            }
        }
        else if ("copy".equalsIgnoreCase(userAction)) {
            if (oldCatalogItem.getDistributorNbr().equalsIgnoreCase(distNbr)) {
                if (oldCatalogItem.getCatalogId().equalsIgnoreCase(catalogId)) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                            MMKeyConstants.CatalogItem.DISTRIBUTOR_NUMBER_PRESENT);
                }
            }
            else {
                if (!catItemList.isEmpty()) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                            MMKeyConstants.CatalogItem.DISTRIBUTOR_NUMBER_PRESENT);
                }
            }
        }
        else if ("edit".equalsIgnoreCase(userAction)) {
            if (!oldCatalogItem.getDistributorNbr().equalsIgnoreCase(distNbr)) {
                if (!oldCatalogItem.getCatalogId().equalsIgnoreCase(catalogId)) {
                    if (!catItemList.isEmpty()) {
                        GlobalVariables.getMessageMap().putErrorForSectionId(
                                MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                                MMKeyConstants.CatalogItem.DISTRIBUTOR_NUMBER_PRESENT);
                    }
                }
            }
        }

        String uICode = newCatalogItem.getCatalogUnitOfIssueCd();
        if (StringUtils.isBlank(uICode)) {
            GlobalVariables.getMessageMap().putErrorForSectionId(
                    MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                    MMKeyConstants.CatalogItem.UI_REQUIRED);
        }
        else {
            UnitOfIssue uI = bOS.findBySinglePrimaryKey(UnitOfIssue.class, uICode);
            if (uI == null) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.UI_INVALID);
            }
        }

        // Business Rule No. 11:
        // Neither the Commodity Group nor Subgroup Code can be null if the Commodity Buyout/Stock Code is equal to Stock.
        // Business Rule No. 12:
        // Must have a minimum of one Commodity Group/Subgroup Record.


        if (newCatalogItem.getCatalogSubgroupItems() != null) {
            List<CatalogSubgroupItem> catalogItemSG = newCatalogItem.getCatalogSubgroupItems();

            if (catalogItemSG.isEmpty()) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_SUBGROUP_SECTION,
                        MMKeyConstants.CatalogSubgroup.CATALOG_GROUP_SUBGROUP_NOT_SET);
            }
            // rule 20
            for (int v = 0; v < catalogItemSG.size(); v++) {
                CatalogSubgroupItem cISubGroup = catalogItemSG.get(v);
                if (cISubGroup.getCatalogSubgroupId() == null) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.CatalogItem.CATALOG_ITEM_SUBGROUP_SECTION,
                            MMKeyConstants.CatalogItem.SUBGROUP_VALUE_CANNOTBE_NULL);
                }
            }
        }

        // rule = 20
        if (newCatalogItem.getCatalogItemImages() != null) {
            List<CatalogItemImage> cII = newCatalogItem.getCatalogItemImages();
            for (int d = 0; d < cII.size(); d++) {
                CatalogItemImage cIImage = cII.get(d);
                if (cIImage.getCatalogImageId() == null) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.CatalogItem.CATALOG_ITEM_IMAGE_SECTION,
                            MMKeyConstants.CatalogItem.IMAGE_VALUE_CANNOTBE_NULL);
                }
            }
        }

        // rule = 20
        if (newCatalogItem.getCatalogItemMarkups() != null) {
            validateMarkUpCollection(newCatalogItem.getCatalogItemMarkups());
        }
    }

    /*
     * The following methods validate Markup Collection Only Markups with Markup Type Code = 03, 04 or 09 are allowed. The Markup
     * end date, if specified should be greater than today's date
     */
    private void validateMarkUpCollection(List<CatalogItemMarkup> markupList) {
        List<CatalogItemMarkup> cIM = markupList;
        for (int e = 0; e < cIM.size(); e++) {
            CatalogItemMarkup cIMarkup = cIM.get(e);
            if (cIMarkup.getMarkupCd() == null) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MARKUP_SECTION,
                        MMKeyConstants.CatalogItem.MARKUP_VALUE_CANNOTBE_NULL);
            }
            else {
                
                String markupTypeCode = cIMarkup.getMarkup().getMarkupTypeCd();
                if (!((MMConstants.MarkupType.ACCOUNT_CATALOG_ITEM_MARKUP)
                        .equalsIgnoreCase(markupTypeCode)
                        || (MMConstants.MarkupType.CATALOG_FIX_MARKUP)
                                .equalsIgnoreCase(markupTypeCode) 
                        || (MMConstants.MarkupType.ITEM_CATEGORY_CATALOG_ITEM_MARKUP)
                                .equalsIgnoreCase(markupTypeCode)
                        || (MMConstants.MarkupType.CASH_SALE_CATALOG_ITEM_MARKUP
                                .equalsIgnoreCase(markupTypeCode)))){
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.CatalogItem.CATALOG_ITEM_MARKUP_SECTION,
                            MMKeyConstants.CatalogItem.MARKUP_CANNOT_BE_APPLIED);
                }

                Date markupEndDate = cIMarkup.getMarkup().getMarkupEndDt();
                if (markupEndDate != null && markupEndDate.before(new Date())) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.CatalogItem.CATALOG_ITEM_MARKUP_SECTION,
                            MMKeyConstants.CatalogItem.APPLIED_MARKUP_HAS_EXPIRED);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void validateStockData(CatalogItem oldCatalogItem, CatalogItem newCatalogItem,
            BusinessObjectService bOS, String userAction) {

        Stock st = oldCatalogItem.getStock();

        Stock stock = newCatalogItem.getStock();
        String distNbr = newCatalogItem.getDistributorNbr();
        HashMap hm = new HashMap();
        hm.put("stockDistributorNbr", distNbr);
        List<Stock> stockList = (List<Stock>) bOS.findMatching(Stock.class, hm);

        // The If conditions checks to see that for Stock Items
        // Stock Distributor Number must be Unique system wide ...

        if ("newWithExisting".equalsIgnoreCase(userAction)) {
            if (!stockList.isEmpty()) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.DISTRIBUTOR_NUMBER_PRESENT);
            }
        }
        else if ("copy".equalsIgnoreCase(userAction)) {

            if (st.getStockDistributorNbr().equalsIgnoreCase(distNbr)) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.DISTRIBUTOR_NUMBER_PRESENT);
            }
            else {
                if (!stockList.isEmpty()) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                            MMKeyConstants.CatalogItem.DISTRIBUTOR_NUMBER_PRESENT);
                }
            }

            // Empty the Stock BAlance Object...
            newCatalogItem.getStock().setStockBalances(null);

        }
        else if ("edit".equalsIgnoreCase(userAction)) {
            if (!st.getStockDistributorNbr().equalsIgnoreCase(distNbr)) {
                if (!stockList.isEmpty()) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                            MMKeyConstants.CatalogItem.DISTRIBUTOR_NUMBER_PRESENT);
                }
            }
        }

        String agreementNbr = stock.getAgreementNbr();
        if (!StringUtils.isBlank(agreementNbr)) {
            Agreement agreement = bOS.findBySinglePrimaryKey(Agreement.class, agreementNbr);
            if (agreement == null) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.AGREEMENT_INVALID);
            }
        }

        String restrictedRouteCd = stock.getRestrictedRouteCd();
        if (!StringUtils.isBlank(restrictedRouteCd)) {
            RestrictedRouteCode restrictedRCode = bOS.findBySinglePrimaryKey(
                    RestrictedRouteCode.class, restrictedRouteCd);
            if (restrictedRCode == null) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.RESTRICTED_ROUTE_CD_INVALID);
            }
        }

        String cycleCountCd = stock.getCycleCntCd();
        if (!StringUtils.isBlank(cycleCountCd)) {
            CycleCount cycleCount = bOS.findBySinglePrimaryKey(CycleCount.class, cycleCountCd);
            if (cycleCount == null) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.CYCLE_COUNT_CD_INVALID);
            }
        }

        String stockTypeCode = stock.getStockTypeCode();
        if (!StringUtils.isBlank(stockTypeCode)) {
            StockType stockType = bOS.findBySinglePrimaryKey(StockType.class, stockTypeCode);
            if (stockType == null) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.STOCK_TYPE_CD_INVALID);
            }
        }

        String rentalObjectCode = stock.getRentalObjectCode();
        if (!StringUtils.isBlank(rentalObjectCode)) {
            RentalObjectCode rentalObject = bOS
                    .findBySinglePrimaryKey(RentalObjectCode.class, rentalObjectCode);
            if (rentalObject == null) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.RENTAL_OBJECT_CD_INVALID);
            }
        }

        String buyUI = stock.getBuyUnitOfIssueCd();
        if (!StringUtils.isBlank(buyUI)) {
            FinancialSystemAdaptorFactory factory = SpringContext
                    .getBean(FinancialSystemAdaptorFactory.class);
            Object uI = null;
            if (factory.isSystemAvailable()) {
                uI = factory.getFinancialBusinessObjectService().findBySinglePrimaryKey(
                        FinancialUnitOfMeasure.class, buyUI);
            }
            if (uI == null) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.BUY_UI_INVALID);
            }
        }

        String uICode = newCatalogItem.getCatalogUnitOfIssueCd();
        if (StringUtils.isBlank(uICode)) {
            GlobalVariables.getMessageMap().putErrorForSectionId(
                    MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                    MMKeyConstants.CatalogItem.UI_REQUIRED);
        }
        else {
            UnitOfIssue uI = bOS.findBySinglePrimaryKey(UnitOfIssue.class, uICode);
            if (uI == null) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.UI_INVALID);
            }
        }

        // Business Rule No. 1:
        // If Stock.perishableInd is true, Date of expiration of perishable item needs to be captured.


        if (stock.isPerishableInd()) {
            List sBalance = stock.getStockBalances();
            for (int i = 0; i < sBalance.size(); i++) {
                StockBalance sBal = (StockBalance) sBalance.get(i);
                if (sBal.getQtyOnHand() > 0 && sBal.getStockPerishableDt() == null) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.Stock.STOCK_BALANCE_SECTION,
                            MMKeyConstants.StockBalance.PERISHABLE_DATE_NOT_PROVIDED);
                }
            }
        }

        // Business Rule No. 14:
        // EHS Unit of Issue, EHS Container, and the EHS Conversion Unit Factor are required if there is an EHS Code
        // EHS Unit of Issue, EHS Container, and the EHS Conversion Unit Factor must be null if the EHS code is null.

        if (stock.getHazardousMateriels() != null) {
            List hazMat = stock.getHazardousMateriels();
            for (int i = 0; i < hazMat.size(); i++) {
                HazardousMateriel hMat = (HazardousMateriel) hazMat.get(i);
                checkHazadousMaterielValues(hMat, i, bOS);
                // if EHS code is null:
                // EHS Unit of Issue, EHS Container, and the EHS Conversion Unit Factor must be null
                if (StringUtils.isBlank(hMat.getHazardousUnCode())) {
                    if (hMat.getEhsConversionUnitFactor() != null) {
                        GlobalVariables.getMessageMap().putError(
                                "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                        + "].ehsConversionUnitFactor",
                                MMKeyConstants.Stock.FOR_EHS_CODE_NULL);
                    }
                    if (!StringUtils.isBlank(hMat.getEhsContainerCode())) {
                        GlobalVariables.getMessageMap().putError(
                                "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                        + "].ehsContainerCode",
                                MMKeyConstants.Stock.FOR_EHS_CODE_NULL);
                    }
                    if (!StringUtils.isBlank(hMat.getEhsUnitOfIssueCd())) {
                        GlobalVariables.getMessageMap().putError(
                                "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                        + "].ehsUnitOfIssueCd",
                                MMKeyConstants.Stock.FOR_EHS_CODE_NULL);
                    }
                }
                // EHS Code not null
                else {
                    if (hMat.getEhsConversionUnitFactor() == null) {
                        GlobalVariables.getMessageMap().putError(
                                "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                        + "].ehsConversionUnitFactor",
                                MMKeyConstants.Stock.FOR_EHS_CODE_IS_NOT_NULL);
                    }
                    if (StringUtils.isBlank(hMat.getEhsContainerCode())) {
                        GlobalVariables.getMessageMap().putError(
                                "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                        + "].ehsContainerCode",
                                MMKeyConstants.Stock.FOR_EHS_CODE_IS_NOT_NULL);
                    }
                    if (StringUtils.isBlank(hMat.getEhsUnitOfIssueCd())) {
                        GlobalVariables.getMessageMap().putError(
                                "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                        + "].ehsUnitOfIssueCd",
                                MMKeyConstants.Stock.FOR_EHS_CODE_IS_NOT_NULL);
                    }
                }
            }
        }


        // rule = 20
        if (stock.getStockPackNotes() != null) {
            List<StockPackNote> spNotes = newCatalogItem.getStock().getStockPackNotes();

            for (int f = 0; f < spNotes.size(); f++) {
                StockPackNote spN = spNotes.get(f);
                if (spN.getPackListAnnouncementCode() == null) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.Stock.STOCK_PACK_NOTES_SECTION,
                            MMKeyConstants.StockPackNote.STOCK_PACK_NOTE_NOT_SET);
                }
            }
        }

        // Business Rule No. 5:
        // Active/Inactive Indicator can only be set to Inactive if
        // there are no open purchase orders,
        // no back orders,
        // quantity on hand equal to zero
        // quantity allocated equal to zero

        // If active is changed to inactive

        if (oldCatalogItem.isActive() && !newCatalogItem.isActive()) {
            // there are no open orders with Order Type Code = "STOCK",
            HashMap fieldValues = new HashMap();
            fieldValues.put("catalogItemId", newCatalogItem.getCatalogItemId());
            fieldValues.put(MMConstants.OrderDetail.ORDER_STATUS_CD, MMConstants.OrderStatus.ORDER_LINE_OPEN);
            List<OrderDetail> openOrders = (List<OrderDetail>) bOS.findMatching(OrderDetail.class,
                    fieldValues);
            int allocatedQty = 0;
            for (OrderDetail oD : openOrders) {
                if(ObjectUtils.isNotNull(oD.getSalesInstance())
                        && !StringUtils.isBlank(oD.getSalesInstance().getOrderTypeCd())) {
                     if (oD.getSalesInstance().getOrderTypeCd().equalsIgnoreCase("STOCK")) {
                         GlobalVariables.getMessageMap().putErrorForSectionId(
                                 MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                                 MMKeyConstants.CatalogItem.OPEN_PENDING_ORDERS_PRESENT);
                     }
        
                     // quantity allocated equal to zero
                     // i.e Sum of ORDER_DETAIL.ORDER_ITEM_QTY for all Open Order Detail with Order typde Code = "WAREHS"
                     if (oD.getSalesInstance().getOrderTypeCd().equalsIgnoreCase("WAREHS")) {
                         allocatedQty = allocatedQty + oD.getOrderItemQty().intValue();
                     }
                }    
                if (allocatedQty != 0) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                            MMKeyConstants.CatalogItem.ALLOCATED_QTY_NOT_ZERO);
                }
            }

            // no back orders,
            BackOrderService backOrderService = SpringContext.getBean(BackOrderService.class);
            Collection<BackOrder> backOrderList = backOrderService.getUnfilledBackOrdersForStock(stock.getStockId());            
            if (backOrderList != null && !backOrderList.isEmpty()) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.CatalogItem.OPEN_BACK_ORDERS_PRESENT);
            }

            // quantity on hand equal to zero
            List<StockBalance> checkQOH = stock.getStockBalances();
            if (checkQOH != null) {
                for (StockBalance checkQuantityOnHand : checkQOH) {
                    Integer qtyOnHand = (checkQuantityOnHand.getQtyOnHand() == null) ? 0 : checkQuantityOnHand.getQtyOnHand();
                    if (qtyOnHand != 0) {
                        GlobalVariables.getMessageMap().putErrorForSectionId(
                                MMConstants.Stock.STOCK_BALANCE_SECTION,
                                MMKeyConstants.CatalogItem.QUANTITY_ON_HAND_NOT_ZERO);
                    }
                }
            }
        }

        // BusinessRule No 5a: quantity on hand equal to zero Active can not be set to true
        //Is this rule really necessary? Furthermore, if it is this code is broken anyway.
//        List<StockBalance> checkQOH = stock.getStockBalances();
//        if (checkQOH != null && checkQOH.isEmpty()) {
//            if (stock.isActive()) {
//                GlobalVariables.getMessageMap().putErrorForSectionId(
//                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
//                        MMKeyConstants.CatalogItem.ACTIVE_IND_CANNOT_BE_SET);
//            }
//        }

        // Business Rule No. 6:
        // When Active/Inactive Indicator is set to Inactive
        // Zone/Bin/Shelf Indicator/Item Shelf Placement aka (Shelf IDNumber) fields are cleared.
        // Set Order Point and Order Quantity to zero
        // Implemented in CatalogItemMaintainableImpl.populateBusinessObject()

        // Business Rule No. 7:
        // Whenever the Active/Inactive Indicator is changed, post systems date to the
        // ‘Last Date of Active/Inactive Indicator Change Field’.
        // Implemented in CatalogItemMaintainableImpl.populateBusinessObject()

        // Business Rule No. 10:
        // If Sole Source Indicator is True, the Manufacturer Number is required.

        if (stock.getSoleSourceInd()) {
            if (StringUtils.isBlank(newCatalogItem.getManufacturerNbr())) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_MAIN_SECTION,
                        MMKeyConstants.Stock.MANUFACTURER_NBR_NOT_PROVIDED);
            }
        }


        // Business Rule No. 11:
        // Neither the Commodity Group nor Subgroup Code can be null if the Commodity Buyout/Stock Code is equal to Stock.
        // Business Rule No. 12:
        // Must have a minimum of one Commodity Group/Subgroup Record.


        if (newCatalogItem.getCatalogSubgroupItems() != null) {
            List<CatalogSubgroupItem> catalogItemSG = newCatalogItem.getCatalogSubgroupItems();

            if (catalogItemSG.isEmpty()) {
                GlobalVariables.getMessageMap().putErrorForSectionId(
                        MMConstants.CatalogItem.CATALOG_ITEM_SUBGROUP_SECTION,
                        MMKeyConstants.CatalogSubgroup.CATALOG_GROUP_SUBGROUP_NOT_SET);
            }
            // rule 20
            for (int v = 0; v < catalogItemSG.size(); v++) {
                CatalogSubgroupItem cISubGroup = catalogItemSG.get(v);
                if (cISubGroup.getCatalogSubgroupId() == null) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            MMConstants.CatalogItem.CATALOG_ITEM_SUBGROUP_SECTION,
                            MMKeyConstants.CatalogItem.SUBGROUP_VALUE_CANNOTBE_NULL);
                }
            }
        }

        // validate Markups
        if (newCatalogItem.getCatalogItemMarkups() != null) {
            validateMarkUpCollection(newCatalogItem.getCatalogItemMarkups());
        }
    }

    /**
     * @see org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase#processCustomAddCollectionLineBusinessRules(org.kuali.rice.kns.document.MaintenanceDocument,
     *      java.lang.String, org.kuali.rice.kns.bo.PersistableBusinessObject)
     */
    @Override
    public boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document,
            String collectionName, PersistableBusinessObject line) {
        if ("catalogItemMarkups".equals(collectionName)) {
            CatalogItemMarkup catalogItemMarkup = (CatalogItemMarkup) line;
            if (catalogItemMarkup != null) {
                catalogItemMarkup.refreshReferenceObject("markup");
            }
        }
        return super.processCustomAddCollectionLineBusinessRules(document, collectionName, line);
    }

    private void checkHazadousMaterielValues(HazardousMateriel hazMat, int i,
            BusinessObjectService bOS) {

        String hazUnCode = hazMat.getHazardousUnCode();
        if (!StringUtils.isBlank(hazUnCode)) {
            HazardousUn hUN = bOS.findBySinglePrimaryKey(HazardousUn.class, hazUnCode);
            if (hUN == null) {
                GlobalVariables.getMessageMap().putError(
                        "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                + "].hazardousUnCode",
                        MMKeyConstants.HazardousMateriel.HAZARDOUS_UN_CODE_INVALID);
            }
        }

        String ehsHazCode = hazMat.getEhsHazardousCd();
        if (!StringUtils.isBlank(ehsHazCode)) {
            EhsHazardous ehsH = bOS.findBySinglePrimaryKey(EhsHazardous.class, ehsHazCode);
            if (ehsH == null) {
                GlobalVariables.getMessageMap().putError(
                        "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                + "].ehsHazardousCd",
                        MMKeyConstants.HazardousMateriel.EHS_HAZARDOUS_CODE_INVALID);
            }
            else {

                // Business Rule No. 15:
                // Storehouse Unit of Issue to EHS Unit of Issue Conversion Factor must be > 0 if the EHS Hazardous Code exists.

                if (hazMat.getEhsConversionUnitFactor() == null
                        || hazMat.getEhsConversionUnitFactor().intValue() <= 0) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(
                            "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                    + "].ehsConversionUnitFactor",
                            MMKeyConstants.Stock.EHSUNITOFCONVERSION_LESS_THAN_ZERO);
                }
            }

        }

        String ehsContCode = hazMat.getEhsContainerCode();
        if (!StringUtils.isBlank(ehsContCode)) {
            EhsContainer ehsC = bOS.findBySinglePrimaryKey(EhsContainer.class, ehsContCode);
            if (ehsC == null) {
                GlobalVariables.getMessageMap().putError(
                        "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                + "].ehsContainerCode",
                        MMKeyConstants.HazardousMateriel.EHS_CONTAINER_CODE_INVALID);
            }
        }


        String ehsStCode = hazMat.getEhsHazardousStateCode();
        if (!StringUtils.isBlank(ehsStCode)) {
            EhsHazardousState ehsC = bOS.findBySinglePrimaryKey(EhsHazardousState.class, ehsStCode);
            if (ehsC == null) {
                GlobalVariables.getMessageMap().putError(
                        "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                + "].ehsHazardousStateCode",
                        MMKeyConstants.HazardousMateriel.EHS_HAZARDOUS_STATE_CODE_INVALID);
            }
        }

        String dotCode = hazMat.getDotHazardousCd();
        if (!StringUtils.isBlank(dotCode)) {
            DotHazardous dotHC = bOS.findBySinglePrimaryKey(DotHazardous.class, dotCode);
            if (dotHC == null) {
                GlobalVariables.getMessageMap().putError(
                        "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                + "].dotHazardousCd",
                        MMKeyConstants.HazardousMateriel.DOT_HAZARDOUS_CODE_INVALID);
            }
        }

        String ehsUICode = hazMat.getEhsUnitOfIssueCd();
        if (!StringUtils.isBlank(ehsUICode)) {
            UnitOfIssue ehsUIC = bOS.findBySinglePrimaryKey(UnitOfIssue.class, ehsUICode);
            if (ehsUIC == null) {
                GlobalVariables.getMessageMap().putError(
                        "document.newMaintainableObject.stock.hazardousMateriels[" + i
                                + "].ehsUnitOfIssueCd",
                        MMKeyConstants.HazardousMateriel.EHS_UNIT_OF_ISSUE_CODE_INVALID);
            }
        }

    }

}