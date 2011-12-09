package org.kuali.ext.mm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.businessobject.Bin;
import org.kuali.ext.mm.businessobject.BinLookable;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CheckinDetail;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.document.web.struts.OrderDetailVo;
import org.kuali.ext.mm.util.MMDecimal;

public interface CheckinOrderService {

    public CheckinDocument createCheckinDocItems(CheckinDocument checkinDoc, String orderDocNumber,
            String orderLineNumber) throws Exception;

    /**
     * Relieves any pending back orders for all stock in CheckinDocument
     * 
     * @param cdoc
     */
    public void processBackOrders(CheckinDocument cdoc);

    public boolean createReturnDocumentForRejectedItems(CheckinDocument checkinDoc)
            throws Exception;

    public CheckinDocument addNewPo(CheckinDocument checkinDoc, List<OrderDetailVo> orderVos);

    public CatalogItem getCatalogItem(String manufNumber, String distNumber);

    public void updateStockBalance(CheckinDocument checkinDocument);

    public void activateCatalogItems(CheckinDocument checkinDocument);

    public Map<String, MMDecimal> updateStockPrice(CheckinDocument checkinDoc);

    public void checkinRentals(CheckinDocument checkinDoc);

    public void updateOrderStatus(CheckinDocument checkinDocument);

    public Map<String, MMDecimal> processCheckinDocument(CheckinDocument cdoc);

    public void createCheckinHistory(CheckinDocument cdoc, Map<String, MMDecimal> stockCosts);

    public CheckinDocument removeEmptyCheckinLines(CheckinDocument cdoc);

    public Bin getEmptyBin(CheckinDocument cdoc, int quantity);

    public OrderDetail getOrderDetailWithStock(OrderDetail odetail);

    public CheckinDocument setCheckinDocParams(CheckinDocument checkinDoc, OrderDocument orderDoc);

    public List<CheckinDetail> createNewCheckinDetail(CheckinDocument checkinDoc,
            OrderDetail orderDetail, Integer binId);

    public List<OrderDetail> getOrderLinesForLookup(Map<String, String> fieldValues);

    /**
     * Creates and saves an electronic invoice XML that will be posted later to KFS
     * 
     * @param document Document
     */
    public void createElectronicInvoiceXml(CheckinDocument document) throws IOException;

    public List<OrderDetail> getCheckinDocsForCorrection(Map<String, String> fieldValues);

    public List<BinLookable> getBinList(Map<String, String> fieldValues);
    
    /**
     * @param detail
     * @return Actual accepted item quantity after any corrections
     */
    public Integer getActualAcceptedQuantity(CheckinDetail detail);
    
    /**
     * @param detail
     * @return Actual rejected item quantity after any corrections
     */
    public Integer getActualRejectedQuantity(CheckinDetail detail);
}
