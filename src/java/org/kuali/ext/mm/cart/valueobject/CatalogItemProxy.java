package org.kuali.ext.mm.cart.valueobject;

import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CatalogSubgroupItem;

public class CatalogItemProxy {

	private CatalogItem catalogItemActual;

	private CatalogSubgroupItem catalogSubgroupItem;

	public CatalogItemProxy() {
		catalogItemActual = null;
		catalogSubgroupItem = null;
	}

	public CatalogItem getCatalogItem() {
		if(catalogItemActual != null)
			return catalogItemActual;

		return catalogSubgroupItem.getCatalogItem();
	}

	public void setCatalogItem(Object catalogItem) {
		if(catalogItem instanceof CatalogItem)
			this.catalogItemActual = (CatalogItem)catalogItem;
		else if(catalogItem instanceof CatalogSubgroupItem)
			this.catalogSubgroupItem = (CatalogSubgroupItem)catalogItem;
		else {

		}

	}

}
