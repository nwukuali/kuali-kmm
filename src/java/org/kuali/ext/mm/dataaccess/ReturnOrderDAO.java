package org.kuali.ext.mm.dataaccess;

import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.ReorderCatalogItemDetail;
import org.kuali.ext.mm.document.ReturnDocument;

public interface ReturnOrderDAO {

    public List<CatalogItem> getCatalogItemsForStockAgreementNumber(String agreementNumber);

    public List<ReorderCatalogItemDetail> getCatalogItemsForReorder(String agreementNumber ,String catalogGroupCode, String catalogSubGroupCode, String warehouseCode);

    public List<OrderDetail> getSearchResultsForReturns(Map<String, String> criteria, boolean isVendor);

    public Map<Integer , Integer> getBalanceQuantityForOrder(ReturnDocument rdoc, boolean savedState);

}
