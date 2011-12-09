package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.Agreement;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.WarehouseAccounts;
import org.kuali.ext.mm.document.ReOrderDocument;

public interface ReOrderService {

    public ReOrderDocument createOrderDocument(String actionCode, String agreementNumber,
            String catalogCode, String catalogSubGroupCode, String warehouseCode,
            ReOrderDocument orderDoc) throws Exception;

    public void createAccountsForApprovedDocument(ReOrderDocument rdoc);

    public java.sql.Date getExpectedTimeValue(Agreement agreement);

    public Accounts loadAccountingInfoForOrderLine(OrderDetail odetail, WarehouseAccounts accounts);

    public OrderDetail createOrderDetail(CatalogItem item, ReOrderDocument orderDoc,
            Agreement agreement, Integer quantity, Integer index);

}
