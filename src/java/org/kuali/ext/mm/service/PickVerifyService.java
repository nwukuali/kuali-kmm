package org.kuali.ext.mm.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import org.kuali.ext.mm.businessobject.Bin;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.PickTicket;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.document.PickVerifyDocument;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialCapitalAssetInformation;
import org.kuali.ext.mm.integration.fp.businessobject.FinancialInternalBillingItem;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialAccountingLine;
import org.kuali.rice.kns.bo.DocumentHeader;
import org.kuali.rice.kns.util.KualiDecimal;

public interface PickVerifyService {

    /**
     * Begins all processing of PickVerifyDocument.
     * 
     * @param document - PickVerifyDocument to be processed
     */
    public void processNewVerifyDocument(PickVerifyDocument document);

    /**
     * @param ticket - a PickTicket
     * @return a ByteArrayOutoutStream containing the packing lists created from PickTicket, as a single PDF
     */
    public ByteArrayOutputStream generatePackLists(PickTicket ticket);

    /**
     * @param line - A PickListLine
     * @return True if Rentals associated with PickListLine match the picked quantity
     */
    public boolean rentalsMatchPickQuantity(PickListLine line);

    /**
     * Sums the Picked Quantity of PickListLine and its additionalLines (if any) to determine if the quantities are valid, by the
     * formula: BackOrderedQuantity + Sum(PickStockQuantity) <= OrderedQuantity
     * 
     * @param line - A PickListLine
     * @return True if the BackOrderQuantity + PickStockQuantity is less than or equal to the Quantity Ordered.
     */
    public boolean isQuantityValid(PickListLine line);

    /**
     * @param line - A PickListLine
     * @return True if the Quantity Picked is less than the Quantity on hand, indicated potentially unnecessary BackOrders
     */
    public boolean hasPickedLessThanQuantityOnHand(PickListLine line);

//    /**
//     * @param line - a pick list line
//     * @return true if the quantity on hand is less than the quantity picked
//     */
//    public boolean hasPickExceededQuantityOnHand(PickListLine line);
    
    /**
     * @param ticket
     * @param bin
     * @return Sum of picked quantity for this bin from a given pick ticket
     */
    public Integer getTotalPickedFromBin(PickTicket ticket, Bin bin);

    /**
     * Sets the pickListService
     * 
     * @param pickListService
     */
    public void setPickListService(PickListService pickListService);


    /**
     * @return The PickListService object
     */
    public PickListService getPickListService();

    /**
     * Sets the BackOrderService
     * 
     * @param backOrderService
     */
    public void setBackOrderService(BackOrderService backOrderService);

    /**
     * @return the BackOrderService
     */
    public BackOrderService getBackOrderService();

    /**
     * @return The PackListPdfService
     */
    public PackListPdfService getPackListPdfService();

    /**
     * Sets the PackListPdfService
     * 
     * @param packListPdfService
     */
    public void setPackListPdfService(PackListPdfService packListPdfService);

    /**
     * @return RentalService
     */
    public RentalService getRentalService();

    /**
     * Sets the RentalService
     * 
     * @param rentalService
     */
    public void setRentalService(RentalService rentalService);


    /**
     * Process internal billing for a specific warehouse and order document
     * 
     * @param warehouse Warehouse
     * @param ibItems IB Line items
     * @param sourceAcctLines Source warehouse account lines
     * @param assetInformation Target financial account lines
     */
    public DocumentHeader processInternalBilling(Warehouse warehouse,
            List<FinancialInternalBillingItem> ibItems,
            List<FinancialAccountingLine> sourceAcctLines,
            FinancialCapitalAssetInformation assetInformation);

    /**
     * Process internal billing for a specific ticket number
     * 
     * @param doc
     */
    public void processInternalBilling(PickVerifyDocument doc);

    /**
     * Scan and update order detail and order status to complete
     * 
     * @param pickTicket Picket Ticket object
     */
    public void updateOrderStatus(PickTicket pickTicket);

    public KualiDecimal computePickedTotal(List<PickListLine> pickListLines);

    public KualiDecimal computePickedAmountSaved(List<PickListLine> pickListLines);

    public void processBillingGlpes(PickVerifyDocument pickVerifyDoc,
            HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups);
}
