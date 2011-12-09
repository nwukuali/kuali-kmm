package org.kuali.ext.mm.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.util.MMDecimal;

public interface ReturnOrderService {

    public ReturnDocument createReturnDocItems(ReturnDocument checkinDoc, String orderDocNumber,
            String orderLineNumber) throws Exception;

    public ReturnDetail getReturnDetailWithStock(ReturnDetail odetail);

    public void processReturnDocument(ReturnDocument rdoc);

    public ReturnDocument setDocParams(OrderDocument odoc, ReturnDocument returnDoc);

    public ByteArrayOutputStream generatePDFAfterApproval(ReturnDocument returnDoc)
            throws Exception;

    public List<OrderDetail> getSearchResultsForReturns(Map<String, String> criteria,
            boolean isVendor);

    public void processInternalBilling(ReturnDocument rdoc);

    public void processVendorReturnDocs(ReturnDocument rdoc);

    public int updateOrderstatus(final String orderDocNumber, final String orderStatus);

    public MMDecimal getReturnLinePrice(boolean isDocCustomerReturnDoc, OrderDetail odetail);

    public void createCreditMemo(ReturnDocument rdoc, ReturnDetail rdetail);

    public Map<Integer, Integer> getBalanceQuantityForOrder(ReturnDocument rdoc, boolean savedState);
    
    public Integer getQuantityAlreadyReturned(ReturnDetail returnDetail);

    public void processBillingGlpes(ReturnDocument rdoc,
            HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups);
    
    public MMDecimal getReturnLineCreditAmount(ReturnDetail rdetail);
}
