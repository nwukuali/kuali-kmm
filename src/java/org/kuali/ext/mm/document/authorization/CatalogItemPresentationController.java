package org.kuali.ext.mm.document.authorization;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.MaintenanceDocumentBase;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationControllerBase;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.KRADConstants;

import java.util.List;
import java.util.Set;


public class CatalogItemPresentationController extends MaintenanceDocumentPresentationControllerBase {
    
    public boolean canCreate(Class boClass) {
        return true;
    }

	// 1  Warehouse (Display all Catalog Item + Stock fields and Hazardous, Stock Pack and Stock Balance section
	// 2  Hosted    (Display Catalog Item fields and Image, Mark-up, SubGroup section.
	// 3  Punch Out (No Item can be created for this kind. Hide out the return-link from Catalog Lookup)

	/*
	 * Method hides the input fields that aren't supposed to be shown
	 * for CatalogTypeCode = MMConstants.CatalogType.HOSTED
	 */
	@Override
    public Set<String> getConditionallyHiddenPropertyNames(BusinessObject businessObject) {
		Set<String> hideFields = super.getConditionallyHiddenPropertyNames(businessObject);
		String catalogTypeCode = returnedCatalogTypeCode(businessObject);
		String stockPrefix = MMConstants.CatalogItem.STOCK + ".";
		if (catalogTypeCode != null && catalogTypeCode.equalsIgnoreCase(MMConstants.CatalogType.HOSTED)){
			hideFields.add("catalogId");
			hideFields.add(stockPrefix + MMConstants.Stock.STOCK_ID);
            hideFields.add(stockPrefix + MMConstants.Stock.CYCLE_CNT_CD);
            hideFields.add(stockPrefix + MMConstants.Stock.RESTRICTED_ROUTE_CD);
            hideFields.add(stockPrefix + MMConstants.Stock.BUY_UNIT_OF_ISSUE_CD);
            hideFields.add(stockPrefix + MMConstants.Stock.SALES_UNIT_OF_ISSUE_RT);
            hideFields.add(stockPrefix + MMConstants.Stock.SALES_UNIT_OF_ISSUE_CD);
            hideFields.add(stockPrefix + MMConstants.Stock.BUY_UNIT_ISSUE_RT);
            hideFields.add(stockPrefix + MMConstants.Stock.AGREEMENT_NBR);
            hideFields.add(stockPrefix + MMConstants.Stock.STOCK_TYPE_CD);
            hideFields.add(stockPrefix + MMConstants.Stock.OBSOLETE_IND);
            hideFields.add(stockPrefix + MMConstants.Stock.PERISHABLE_IND);
            hideFields.add(stockPrefix + MMConstants.Stock.RECYCLED_IND);
            hideFields.add(stockPrefix + MMConstants.Stock.REORDER_POINT_QTY);
            hideFields.add(stockPrefix + MMConstants.Stock.SAFETY_STOCK_DAYS);
            hideFields.add(stockPrefix + MMConstants.Stock.SAFETY_STOCK_QTY);
            hideFields.add(stockPrefix + MMConstants.Stock.DISTRIBUTOR_NBR);
            hideFields.add(stockPrefix + MMConstants.Stock.SURCHARGE_IND);
            hideFields.add(stockPrefix + MMConstants.Stock.MINIMUM_ORDER_QTY);
            hideFields.add(stockPrefix + MMConstants.Stock.PACKAGING_UNIT_DESC);
            hideFields.add(stockPrefix + MMConstants.Stock.UPCCD);
            hideFields.add(stockPrefix + MMConstants.Stock.RENTAL_OBJECT_CODE);
            hideFields.add(stockPrefix + MMConstants.Stock.SOLE_SOURCE_IND);
            hideFields.add(stockPrefix + MMConstants.Stock.LAST_CHANGE_ACTV_IND_DT);
            hideFields.add(stockPrefix + MMConstants.Stock.MAXIMUM_ORDER_QTY);
            hideFields.add(stockPrefix + MMConstants.Stock.REMOVE_UNTIL_DATE);
		}

		if (catalogTypeCode != null && catalogTypeCode.equalsIgnoreCase(MMConstants.CatalogType.WAREHOUSE)){
	//		hideFields.add(MMConstants.CatalogItem.CATALOGPRC);
//			hideFields.add(MMConstants.CatalogItem.ACTIVE_IND);
			hideFields.add("catalogSubgroupId");
			hideFields.add("catalogId");
		}

        return hideFields;
	}

	@Override
    public Set<String> getConditionallyReadOnlyPropertyNames(MaintenanceDocument document){
	    Set<String> readOnlyFields = super.getConditionallyReadOnlyPropertyNames(document);
	    if("edit".equalsIgnoreCase(document.getNewMaintainableObject().getMaintenanceAction())){
	        readOnlyFields.add("distributorNbr");
	        readOnlyFields.add("catalogPrc");
	    }
	    else if(KRADConstants.MAINTENANCE_COPY_ACTION.equalsIgnoreCase(document.getNewMaintainableObject().getMaintenanceAction())) {
	        readOnlyFields.add(MMConstants.CatalogItem.CATALOGPRC);
	    }
	    return readOnlyFields;
	}

	/*
	 * Method hides the Sections that aren't supposed to be shown
	 * for CatalogTypeCode = MMConstants.CatalogType.HOSTED
	 */
	@Override
    @SuppressWarnings("unchecked")
	public Set<String> getConditionallyHiddenSectionIds(BusinessObject businessObject) {
       Set<String> fields = super.getConditionallyHiddenSectionIds(businessObject);

       String catalogTypeCode = returnedCatalogTypeCode(businessObject);
       if (catalogTypeCode != null && catalogTypeCode.equalsIgnoreCase(MMConstants.CatalogType.HOSTED)){
			// Add the name of sections to be hidden
	        fields.add(MMConstants.Stock.STOCK_PACK_NOTES_SECTION);
	        fields.add(MMConstants.Stock.HAZARDOUS_MATERIEL_SECTION);
	        fields.add(MMConstants.Stock.STOCK_ATTRIBUTE_SECTION);
	        fields.add(MMConstants.Stock.STOCK_BALANCE_SECTION);
		} else {
			MaintenanceDocumentBase mDB = (MaintenanceDocumentBase) businessObject;
			CatalogItem cI = (CatalogItem)mDB.getOldMaintainableObject().getBusinessObject();
			if(cI!=null && cI.getStockId()!= null && cI.getStock() != null){
				List<StockBalance> stockBal = cI.getStock().getStockBalances();
				if(stockBal !=null && stockBal.isEmpty()){
					fields.add(MMConstants.Stock.STOCK_BALANCE_SECTION);
				}
			}
		}
        return fields;
	}

	@Override
    public Set<String>getConditionallyReadOnlySectionIds(MaintenanceDocument document){
		Set<String> readOnlySections =
			 super.getConditionallyHiddenSectionIds(document.getNewMaintainableObject().getBusinessObject());
		readOnlySections.add(MMConstants.Stock.STOCK_BALANCE_SECTION);
		return readOnlySections;
	}

	private String returnedCatalogTypeCode(BusinessObject businessObject) {
		BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
		MaintenanceDocument document = (MaintenanceDocument) businessObject;
		CatalogItem catalogItem = (CatalogItem) document.getNewMaintainableObject().getBusinessObject();
		String catalogTypeCode = null;
		if (bOS != null && catalogItem != null){
			String catalogId = catalogItem.getCatalogId();
			Catalog catalog = bOS.findBySinglePrimaryKey(Catalog.class, catalogId);
			catalogTypeCode = catalog.getCatalogTypeCd();
		}
		return catalogTypeCode;
	}

   @Override
public boolean canCopy(Document document){
         return false;
    }

}