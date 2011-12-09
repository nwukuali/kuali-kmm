package org.kuali.ext.mm.dataaccess;

import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.businessobject.Bin;
import org.kuali.ext.mm.businessobject.BinLookable;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.document.OrderDocument;

public interface CheckinOrderDAO {

	public OrderDocument getOrderDocument(String orderDocNumber);

	public CatalogItem getCatalogItem(String manufNumber, String distNumber);

	public Bin getEmptyBin(CheckinDocument checkinDoc, int quantity);

	public List<OrderDetail> getOrderLinesForLookup(Map<String, String> fieldValues);

//	public void createPickListLines(CheckinDocument cdoc);

	public List<OrderDetail> getCheckinDocsForCorrection(Map<String, String> fieldValues);

	public List<OrderDetail> getOrderLinesForCustomerReturn(String docNumber);

	public List<BinLookable> getBinList(Map<String, String> fieldValues);
}
