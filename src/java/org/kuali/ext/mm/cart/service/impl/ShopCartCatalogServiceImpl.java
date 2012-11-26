package org.kuali.ext.mm.cart.service.impl;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.PunchOutVendor;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.businessobject.CatalogItemSearch;
import org.kuali.ext.mm.cart.dataaccess.ShopCartBusinessObjectDao;
import org.kuali.ext.mm.cart.service.ShopCartCatalogService;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.QueryElement;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.service.impl.CatalogServiceImpl;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.*;


/**
 * @author schneppd
 *
 */
public class ShopCartCatalogServiceImpl extends CatalogServiceImpl implements ShopCartCatalogService {

	private ShopCartBusinessObjectDao shopCartBusinessObjectDao;

	
	public Collection<Catalog> getAvailableCatalogs(Profile customerProfile) {
	    QueryElement queryElement = new QueryElement(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
	    QueryElement qeTypeCode = new QueryElement();
	    qeTypeCode.getOrList().add(new QueryElement(MMConstants.Catalog.CATALOG_TYPE_CD, MMConstants.CatalogType.HOSTED));
	    qeTypeCode.getOrList().add(new QueryElement(MMConstants.Catalog.CATALOG_TYPE_CD, MMConstants.CatalogType.WAREHOUSE));
	    queryElement.getAndList().add(qeTypeCode);
	    
		Collection<Catalog> results = getShopCartBusinessObjectDao().findMatchingOrderBy(Catalog.class, queryElement, MMConstants.Catalog.PRIORITY_NUMBER, true);
		Collection<Catalog> availableCatalogs = new ArrayList<Catalog>();
		Iterator<Catalog> itCatalog = results.iterator();
		while(itCatalog.hasNext()) {
			Catalog catalog = itCatalog.next();
			if(isCatalogAuthorized(catalog, customerProfile)) {
				availableCatalogs.add(catalog);
			}
		}

		return availableCatalogs;
	}
	
	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartCatalogService#getAvailableCatalogsPunchOut(org.kuali.ext.mm.businessobject.Profile)
	 */
	public Collection<Catalog> getAvailableCatalogsPunchOut(Profile customerProfile) {
	    //Get a set of catalog ids belonging to active PunchOutVendors
	    QueryElement poVendorQuery = new QueryElement(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
        Collection<PunchOutVendor> poVendors = getShopCartBusinessObjectDao().findMatching(PunchOutVendor.class, poVendorQuery);
        Set<String> catalogIdSet = new HashSet<String>();
        Iterator<PunchOutVendor> poVendorIt = poVendors.iterator();
        while(poVendorIt.hasNext()) {
            catalogIdSet.add(poVendorIt.next().getCatalogId());
        }        
        
        //Get all active Punch Out Catalogs
        QueryElement queryElement = new QueryElement(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
        queryElement.getAndList().add(new QueryElement(MMConstants.Catalog.CATALOG_TYPE_CD, MMConstants.CatalogType.PUNCHOUT));        
        Collection<Catalog> results = getShopCartBusinessObjectDao().findMatchingOrderBy(Catalog.class, queryElement, MMConstants.Catalog.PRIORITY_NUMBER, true);       
        Collection<Catalog> availableCatalogs = new ArrayList<Catalog>();
        
        Iterator<Catalog> itCatalog = results.iterator();
        while(itCatalog.hasNext()) {
            Catalog catalog = itCatalog.next();
            // Only add Catalog if it is authorized for the profile and it has at least one active PunchOutVendor
            if(isCatalogAuthorized(catalog, customerProfile)
                    && catalogIdSet.contains(catalog.getCatalogId())) {
                availableCatalogs.add(catalog);
            }
        }
               
        return availableCatalogs;
    }


	public Integer getAvailableQuantity(CatalogItem item) {
		if(ObjectUtils.isNull(item) || ObjectUtils.isNull(item.getStock()) || ObjectUtils.isNull(item.getCatalog()))
			return 0;

		Integer committedQuantity = SpringContext.getBean(StockService.class).getCommittedCatalogItemQuantity(item.getCatalogItemId());
		Integer quantityOnHand = SpringContext.getBean(StockService.class).getStockQuantityOnHand(item.getStock().getStockId(), item.getCatalog().getWarehouseCd());
		return (quantityOnHand >= committedQuantity) ? quantityOnHand - committedQuantity : 0;
	}

	public void setShopCartBusinessObjectDao(ShopCartBusinessObjectDao shopCartBusinessObjectDao) {
		this.shopCartBusinessObjectDao = shopCartBusinessObjectDao;
	}

	public ShopCartBusinessObjectDao getShopCartBusinessObjectDao() {
		return shopCartBusinessObjectDao;
	}
	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartCatalogService#parseCatalogIdStringToList(java.lang.String)
	 */
	public List<String> parseCatalogIdStringToList(String catalogIdString) {
	    List<String> catalogIdList = new ArrayList<String>();

        if(catalogIdString != null) {
            for(String id : catalogIdString.split(" "))
                catalogIdList.add(id);
        }

        return catalogIdList;
	}
	
	public List<String> getCatalogGroupCodesForItem(CatalogItem item) {
        QueryElement queryElement = new QueryElement(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
        queryElement.getAndList().add(new QueryElement(MMConstants.CatalogSubgroupItem.CATALOG_ITEM_ID, item.getCatalogItemId()));
        String[] attributes = {ShopCartConstants.CatalogItemSearch.CATALOG_GROUP_CD};
        String[] orderByList = {};
        String[] groupByList = {ShopCartConstants.CatalogItemSearch.CATALOG_GROUP_CD};
        Collection<Object[]> results = getShopCartBusinessObjectDao().getReport(CatalogItemSearch.class, queryElement, attributes, groupByList, orderByList, false, -1);
        List<String> catalogCodes = new ArrayList<String>();
        for(Object[] obj : results) {
            catalogCodes.add((String)obj[0]);
        }
        return catalogCodes;
    }

}
