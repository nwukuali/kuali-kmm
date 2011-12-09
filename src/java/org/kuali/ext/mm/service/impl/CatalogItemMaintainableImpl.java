package org.kuali.ext.mm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CatalogItemImage;
import org.kuali.ext.mm.businessobject.CatalogItemMarkup;
import org.kuali.ext.mm.businessobject.CatalogSubgroupItem;
import org.kuali.ext.mm.businessobject.HazardousMateriel;
import org.kuali.ext.mm.businessobject.StockAttribute;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.businessobject.StockPackNote;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.service.BusinessObjectLockingService;
import org.kuali.ext.mm.document.service.CatalogItemMaintenanceService;
import org.kuali.rice.kns.bo.DocumentHeader;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;


public class CatalogItemMaintainableImpl extends StoresMaintainableImpl {

    private static final long serialVersionUID = 1L;

    /**
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#getLockingDocumentId()
     */
    @Override
    public String getLockingDocumentId() {
        String lockingDocId = super.getLockingDocumentId();
        if(StringUtils.isBlank(lockingDocId)) {
            BusinessObjectLockingService lockingService = SpringContext.getBean(BusinessObjectLockingService.class);
            CatalogItem catalogItem = (CatalogItem) getBusinessObject();
            List<String> lockingKeys = new ArrayList<String>();
            lockingKeys.add(MMConstants.Stock.STOCK_ID);
            if(ObjectUtils.isNotNull(catalogItem.getStock())){
            List<String> lockingIds = lockingService.getLockingDocumentIds(catalogItem.getStock(), lockingKeys, this.documentNumber);            
            if (lockingIds!=null  &&  !lockingIds.isEmpty())
                lockingDocId = lockingIds.get(0);
            }
        }
        return lockingDocId;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void doRouteStatusChange(DocumentHeader documentHeader) {
        if(documentHeader.getWorkflowDocument().stateIsEnroute()) {
            BusinessObjectLockingService lockingService = SpringContext.getBean(BusinessObjectLockingService.class);
            CatalogItem catalogItem = (CatalogItem) getBusinessObject();
            lockingService.createAndSaveLock(documentNumber, catalogItem.getStock(), MMConstants.Stock.STOCK_ID);
        }
        else if (documentHeader.getWorkflowDocument().stateIsProcessed()) {
            applyValidationRules(this.getBusinessObject());
            CatalogItem catalogItem = (CatalogItem) getBusinessObject();

            String catalogTypeCode = null;
            if (catalogItem != null) {
                Catalog catalog = getBusinessObjectService().findBySinglePrimaryKey(Catalog.class, catalogItem.getCatalogId());
                if (catalog != null) {
                    catalogTypeCode = catalog.getCatalogTypeCd();
                    if ((MMConstants.CatalogType.WAREHOUSE).equalsIgnoreCase(catalogTypeCode)
                            || MMConstants.CatalogType.TRUE_BUYOUT.equalsIgnoreCase(catalogTypeCode)) {
                        populateStockObject(catalogItem);
                        // If action is copy null out unique ids and version number
                        // to create a new stock object as well                        
                        if(KNSConstants.MAINTENANCE_COPY_ACTION.equals(getMaintenanceAction())){
                            catalogItem.setStockId(null);
                            catalogItem.getStock().setVersionNumber(null);
                            catalogItem.getStock().setObjectId(null);
                            catalogItem.getStock().setStockId(null);
                        }                            
                    }
                    else {
                        catalogItem.setStock(null);
                        catalogItem.setStockId(null);
                    }
                }
            }
        }
    }

    private void applyValidationRules(PersistableBusinessObject persistableCatalogItem) {

        CatalogItem catalogItem = (CatalogItem) persistableCatalogItem;
        BusinessObjectService bObjectService = SpringContext.getBean(BusinessObjectService.class);
        CatalogItem oldCatalogItem = bObjectService.findBySinglePrimaryKey(
                CatalogItem.class, catalogItem.getCatalogItemId());
        Date today = new Date();
        Timestamp now = new Timestamp(today.getTime());

        // for new items/stock oldCatalogItem will be null
        if (oldCatalogItem == null) {
            // set the "Stock_create_Dt", and "Last Active Indicator change date"
            // for new stock Items
            catalogItem.getStock().setStockCreateDt(now);
            catalogItem.getStock().setLastChangeActvIndDt(now);
            // "Active Indicator" for Stock iItem is currently set to null
            // It can only be activated after Manual Adjustment has taken place.
            //Correction:  Stock should be set to true when the item is created.
            catalogItem.getStock().setActive(true);
        }
        else {
            // Business Rule: 6
            // When Active/Inactive Indicator is set to Inactive
            // Set Order Point and Order Quantity to zero
            // Set StockId to null.
            if (oldCatalogItem.isActive() && !catalogItem.isActive()) {
                catalogItem.getStock().setReorderPointQty(KualiDecimal.ZERO);
                catalogItem.getStock().setMaximumOrderQty(KualiDecimal.ZERO);
                List<StockBalance> sBalList = catalogItem.getStock().getStockBalances();
                for (StockBalance sBalObj : sBalList) {
                    sBalObj.setStockId(null);
                    bObjectService.save(sBalObj);
                }
            }

            // Business Rule: 7
            // Whenever the Active/Inactive Indicator is changed, post systems date to the
            // Last Date of Active/Inactive Indicator Change Field’.
            if (catalogItem.isActive() != oldCatalogItem.isActive()) {
                catalogItem.getStock().setLastChangeActvIndDt(now);
            }
            // Make sure that active catalog items always point to active stock
            if(catalogItem.isActive()) {
                catalogItem.getStock().setActive(true);
            }
        }

        // Set all the collection active indicator to true by default.
        List<CatalogSubgroupItem> cIS = catalogItem.getCatalogSubgroupItems();
        if (!cIS.isEmpty()) {
            for (int i = 0; i < cIS.size(); i++) {
                CatalogSubgroupItem cISubgroup = cIS.get(i);
                cISubgroup.setActive(true);
            }
        }

        List<CatalogItemMarkup> cIM = catalogItem.getCatalogItemMarkups();
        if (!cIM.isEmpty()) {
            for (int i = 0; i < cIM.size(); i++) {
                CatalogItemMarkup cIMarkup = cIM.get(i);
                cIMarkup.setActive(true);
            }
        }

        List<CatalogItemImage> cII = catalogItem.getCatalogItemImages();
        if (!cII.isEmpty()) {
            for (int i = 0; i < cII.size(); i++) {
                CatalogItemImage cIImage = cII.get(i);
                cIImage.setActive(true);
            }
        }

        if (catalogItem.getStock() != null) {
            List<StockPackNote> sPN = catalogItem.getStock().getStockPackNotes();
            if (!sPN.isEmpty()) {
                for (int i = 0; i < sPN.size(); i++) {
                    StockPackNote sPNote = sPN.get(i);
                    sPNote.setActive(true);
                }
            }

            List<StockAttribute> sA = catalogItem.getStock().getStockAttributes();
            if (!sA.isEmpty()) {
                for (int i = 0; i < sA.size(); i++) {
                    StockAttribute sAttribute = sA.get(i);
                    sAttribute.setActive(true);
                }
            }

            List<HazardousMateriel> hM = catalogItem.getStock().getHazardousMateriels();
            if (!hM.isEmpty()) {
                for (int i = 0; i < hM.size(); i++) {
                    HazardousMateriel hMat = hM.get(i);
                    hMat.setActive(true);
                }
            }
        }
    }

    private void populateStockObject(CatalogItem catalogItem) {
        catalogItem.getStock().setStockDesc(catalogItem.getCatalogDesc());
        catalogItem.getStock().setStockDistributorNbr(catalogItem.getDistributorNbr());
        catalogItem.getStock().setSalesUnitOfIssueCd(catalogItem.getCatalogUnitOfIssueCd());
        catalogItem.getStock().setManufacturerNbr(catalogItem.getManufacturerNbr());
        catalogItem.getStock().setWillcallInd(catalogItem.isWillcallInd());
        catalogItem.getStock().setRecycledInd(catalogItem.isRecycledInd());
        catalogItem.getStock().setTaxableInd(catalogItem.isTaxableInd());

        if (catalogItem.getShippingHt() != null) {
            catalogItem.getStock().setShippingHt(
                    new KualiDecimal(catalogItem.getShippingHt().toString()));
        }
        if (catalogItem.getShippingWd() != null) {
            catalogItem.getStock().setShippingWd(
                    new KualiDecimal(catalogItem.getShippingWd().toString()));
        }
        if (catalogItem.getShippingWgt() != null) {
            catalogItem.getStock().setShippingWgt(
                    new KualiDecimal(catalogItem.getShippingWgt().toString()));
        }
        if (catalogItem.getShippingLh() != null) {
            catalogItem.getStock().setShippingLh(
                    new KualiDecimal(catalogItem.getShippingLh().toString()));
        }
    }

    /**
     * @see org.kuali.ext.mm.service.impl.StoresMaintainableImpl#answerSplitNodeQuestion(java.lang.String)
     */
    @Override
    public boolean answerSplitNodeQuestion(String nodeName) {
        if ("NewCatalogItem".equals(nodeName)) {            
            return KNSConstants.MAINTENANCE_NEWWITHEXISTING_ACTION.equals(getMaintenanceAction())
                || KNSConstants.MAINTENANCE_COPY_ACTION.equals(getMaintenanceAction());
        }
        return false;
    }


    @Override
    public void processAfterCopy(MaintenanceDocument document, Map<String, String[]> parameters) {
        Maintainable mB = document.getNewMaintainableObject();
        CatalogItem cI = (CatalogItem) mB.getBusinessObject();
        cI.setDistributorNbr("");
        cI.getStock().setStockBalances(null);
        List<CatalogSubgroupItem> copySubgroupList = new ArrayList<CatalogSubgroupItem>();
        for(CatalogSubgroupItem subgroupItem : cI.getCatalogSubgroupItems()) {
            CatalogSubgroupItem subgroupCopy = new CatalogSubgroupItem();
            subgroupCopy.setCatalogItemId(cI.getCatalogItemId());
            subgroupCopy.setCatalogSubgroupId(subgroupItem.getCatalogSubgroupId());
            subgroupCopy.setCatalogSubgroup(subgroupItem.getCatalogSubgroup());
            copySubgroupList.add(subgroupCopy);
        }        
        cI.setCatalogSubgroupItems(copySubgroupList);
        Maintainable mBold = document.getOldMaintainableObject();
        CatalogItem cIold = (CatalogItem) mBold.getBusinessObject();
        if(null != cIold.getStock()){
            cIold.getStock().setStockBalances(null);
        }
    }

    /**
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#prepareForSave()
     */
    @Override
    public void prepareForSave() {
        super.prepareForSave();
        CatalogItem newObj = (CatalogItem) getBusinessObject();
        if (newObj != null && StringUtils.isBlank(newObj.getCatalogItemId())) {
            newObj.setCatalogItemId(SpringContext.getBean(CatalogItemMaintenanceService.class)
                    .getNextCatalogItemId());
            
        }
        if(ObjectUtils.isNotNull(newObj.getStock())) {
            newObj.getStock().setCatalogId(newObj.getCatalogId());
            newObj.getStock().refreshReferenceObject(MMConstants.Stock.STOCK_BALANCES);
        }
    }
}