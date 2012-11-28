package org.kuali.ext.mm.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.businessobject.BackOrder;
import org.kuali.ext.mm.businessobject.Bin;
import org.kuali.ext.mm.businessobject.BinLookable;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CheckinDetail;
import org.kuali.ext.mm.businessobject.CreditMemoExpected;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.businessobject.StagingRental;
import org.kuali.ext.mm.businessobject.Stock;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.businessobject.StockCost;
import org.kuali.ext.mm.businessobject.StockHistory;
import org.kuali.ext.mm.businessobject.StoresPersistableBusinessObject;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.CheckinOrderDAO;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.document.dataaccess.OrderStatusDao;
import org.kuali.ext.mm.document.web.struts.OrderDetailVo;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.service.BackOrderService;
import org.kuali.ext.mm.service.CheckinOrderService;
import org.kuali.ext.mm.service.StockHistoryService;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class CheckinOrderServiceImpl implements CheckinOrderService {
    protected static org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(CheckinOrderServiceImpl.class);

    private CheckinOrderDAO checkinOrderDAO = null;
    private OrderStatusDao orderStatusDao;

    private StockService stockService = null;

    private StockHistoryService stockHistoryService = null;

    private BusinessObjectService businessObjectService = null;

    public BusinessObjectService getBusinessObjectService() {
        return this.businessObjectService;
    }

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public StockHistoryService getStockHistoryService() {
        return this.stockHistoryService;
    }

    public void setStockHistoryService(StockHistoryService stockHistoryService) {
        this.stockHistoryService = stockHistoryService;
    }

    public StockService getStockService() {
        return stockService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public CheckinOrderDAO getCheckinOrderDAO() {
        return this.checkinOrderDAO;
    }

    public void setCheckinOrderDAO(CheckinOrderDAO checkinOrderDAO) {
        this.checkinOrderDAO = checkinOrderDAO;
    }

    /**
     * handles set of operations to be done after submitting the document
     * 
     * @see org.kuali.ext.mm.service.CheckinOrderService#processCheckinDocument(org.kuali.ext.mm.document.CheckinDocument)
     */
    public Map<String, MMDecimal> processCheckinDocument(CheckinDocument cdoc) {

        Map<String, MMDecimal> stockCosts = null;
        stockCosts = this.updateStockPrice(cdoc);
        updateStockBalance(cdoc);
        checkinRentals(cdoc);
        processBackOrders(cdoc);
        activateCatalogItems(cdoc);
        // update stock balance
        // create return doc
        updateOrderStatus(cdoc);
        return stockCosts;
    }


    /**
     * @see org.kuali.ext.mm.service.CheckinOrderService#processBackOrders(org.kuali.ext.mm.document.CheckinDocument)
     */
    public void processBackOrders(CheckinDocument cdoc) {
        List<String> stockids = cdoc.getStockIds();
        BackOrderService backOrderService = SpringContext.getBean(BackOrderService.class);
        for (String stockId : stockids) {
            Collection<BackOrder> backOrdersForStock = backOrderService
                    .getUnfilledBackOrdersForStock(stockId);
            if (backOrdersForStock != null) {
                for (BackOrder backOrder : backOrdersForStock) {
                    backOrderService.relieveBackOrder(backOrder, true);
                }
            }
        }
    }

    /**
     * creates Stock History records for the corrected checkin lines
     * 
     * @see org.kuali.ext.mm.service.CheckinOrderService#createCheckinHistory(org.kuali.ext.mm.document.CheckinDocument,
     *      java.util.Map)
     */
    public void createCheckinHistory(CheckinDocument cdoc, Map<String, MMDecimal> stockCosts) {

        List<StockHistory> lisHistory = null;
        if (!MMUtil.isMapEmpty(stockCosts)) {
            lisHistory = this.getStockHistoryService().createStockHistoryForCheckinDocument(cdoc,
                    stockCosts);
            if (!MMUtil.isCollectionEmpty(lisHistory))
                this.getBusinessObjectService().save(lisHistory);
        }

    }

    /**
     * used by the lookup framework for displaying all the valid order lines that can be corrected
     * 
     * @see org.kuali.ext.mm.service.CheckinOrderService#getOrderLinesForLookup(java.util.Map)
     */
    public List<OrderDetail> getOrderLinesForLookup(Map<String, String> fieldValues) {
        return this.checkinOrderDAO.getOrderLinesForLookup(fieldValues);
    }

    /**
     * creates checkin LineItems for the checkin Document
     */
    public CheckinDocument createCheckinDocItems(CheckinDocument checkinDoc, String orderDocNumber,
            String orderLineNumber) throws Exception {
        OrderDocument orderDoc = this.checkinOrderDAO.getOrderDocument(orderDocNumber);
        setCheckinDocParams(checkinDoc, orderDoc);
        createCheckinLineItems(checkinDoc, orderDoc, orderLineNumber);
        return checkinDoc;
    }


    /**
     * sets basic checkin Doc parameters like warehouse code
     * 
     * @param checkinDoc
     * @param orderDoc
     * @return
     */
    public CheckinDocument setCheckinDocParams(CheckinDocument checkinDoc, OrderDocument orderDoc) {
        String warehouseCode = orderDoc.getWarehouseCd();
        checkinDoc.setWarehouseCode(warehouseCode);
        Warehouse warehouse = (Warehouse) StoresPersistableBusinessObject.getObjectByPrimaryKey(
                Warehouse.class, warehouseCode);
        checkinDoc.setWarehouse(warehouse);
        checkinDoc.setOrderDocNumber(orderDoc.getDocumentNumber());
        checkinDoc.getDocumentHeader().setDocumentDescription(
                "Check In " + checkinDoc.getDocumentNumber());
        return checkinDoc;
    }

    /**
     * creates Checkin Line items for the passed Order Document
     * 
     * @param checkinDoc
     * @param orderDoc
     * @param orderLineNumber
     * @return
     * @throws Exception
     */
    public CheckinDocument createCheckinLineItems(CheckinDocument checkinDoc,
            OrderDocument orderDoc, String orderLineNumber) throws Exception {

        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>(0);

        if (StringUtils.isEmpty(orderLineNumber)) {
            for (OrderDetail odetail : orderDoc.getOrderDetails()) {
                if (odetail.isLineCheckinable() && odetail.isLineStatusCheckinable())
                    orderDetails.add(odetail);
            }
        }
        else {
            orderDetails = new ArrayList<OrderDetail>(0);
            for (OrderDetail odetail : orderDoc.getOrderDetails()) {
                if (odetail.getOrderDetailId().equals(Integer.valueOf(orderLineNumber))) {
                    orderDetails.add(odetail);
                }
            }
        }

        if (MMUtil.isCollectionEmpty(orderDetails))
            return checkinDoc;

        for (OrderDetail orderDetail : orderDetails) {
            if ((MMUtil.isCollectionEmpty(orderDetail.getCheckinDetails()))
                    || (orderDetail.getRemainingItemQuantity() > 0)
                    && orderDetail.isLineCheckinable()) {
                List<CheckinDetail> checkinDetails = createNewCheckinDetail(checkinDoc,
                        orderDetail, null);
                orderDetail.getCatalogItem().setActive(true);
                Stock stock = orderDetail.getCatalogItem().getStock();
                if (ObjectUtils.isNotNull(stock)) {
                    stock.setActive(true);
                }
                checkinDoc.getCheckinDetails().addAll(checkinDetails);
            }

        }
        checkinDoc.setOrderDocNumber(orderDoc.getDocumentNumber());
        checkinDoc.setOrderDocument(orderDoc);

        return checkinDoc;
    }

    /**
     * creates a new checkin Line item for the passed order detail object
     * 
     * @param checkinDoc
     * @param orderDetail
     * @param binId
     * @return
     */
    public List<CheckinDetail> createNewCheckinDetail(CheckinDocument checkinDoc,
            OrderDetail orderDetail, Integer binId) {

        List<Bin> bins = new ArrayList<Bin>(0);

        getOrderDetailWithStock(orderDetail);

        if (orderDetail.getCatalogItem().getStock().isPerishableInd()) {
            Bin emptyBin = this.checkinOrderDAO.getEmptyBin(checkinDoc,
                    orderDetail.getRemainingItemQuantity());
            if (emptyBin != null) {
                bins.add(emptyBin);
            }
        }
        else {
            bins = getBin(checkinDoc, orderDetail, binId);
        }

        List<CheckinDetail> cdetails = new ArrayList<CheckinDetail>(0);

        if (!MMUtil.isCollectionEmpty(bins)) {
            cdetails = getCheckinDetailsForBins(checkinDoc, orderDetail, bins);
        }

        return cdetails;
    }

    public Bin getEmptyBin(CheckinDocument cdoc, int quantity) {
        return this.checkinOrderDAO.getEmptyBin(cdoc, quantity);
    }

    public OrderDetail getOrderDetailWithStock(OrderDetail odetail) {
        Stock stock = null;
        if (StringUtils.isEmpty(odetail.getCatalogItem().getStockId())
                || ObjectUtils.isNull(odetail.getCatalogItem().getStock())) {

            stock = this.stockService.getStockByDistributorNumber(odetail.getCatalogItem()
                    .getDistributorNbr());

            if (stock == null) {
                throw new RuntimeException("stock cannot be null");
            }
            odetail.getCatalogItem().setStock(stock);
            odetail.getCatalogItem().setStockId(stock.getStockId());
        }
        else {
            stock = odetail.getCatalogItem().getStock();
        }
        return odetail;
    }

    public CheckinDocument removeEmptyCheckinLines(CheckinDocument cdoc) {
        List<CheckinDetail> lisData = new ArrayList<CheckinDetail>(0);
        for (CheckinDetail cdetail : cdoc.getCheckinDetails()) {
            if ((cdetail.getAcceptedItemQty() == null || cdetail.getAcceptedItemQty() < 1)
                    && (cdetail.getRejectedItemQty() == null || cdetail.getRejectedItemQty() < 1)
                    && !cdetail.isClosedInd())
                lisData.add(cdetail);
        }

        for (CheckinDetail cdetail : lisData) {
            cdoc.getCheckinDetails().remove(cdetail);
            cdetail.delete();
        }
        return cdoc;
    }

    private List<CheckinDetail> getCheckinDetailsForBins(CheckinDocument checkinDoc,
            OrderDetail orderDetail, List<Bin> bins) {

        List<CheckinDetail> cdetails = new ArrayList<CheckinDetail>(0);

        int remQty = orderDetail.getRemainingItemQuantity();

        if (MMUtil.isCollectionEmpty(bins)) {
            CheckinDetail checkinDetail = getPopulatedCheckinDetail(checkinDoc, orderDetail, null,
                    0);
            cdetails.add(checkinDetail);
        }
        else {

            for (Bin bin : bins) {
                if (ObjectUtils.isNotNull(bin)) {
                    int availableQty = bin.getAvailableQty();
                    Integer quantity = 0;
                    if (availableQty > 0) {
                        if (availableQty > remQty) {
                            quantity = remQty;
                        }

                        CheckinDetail checkinDetail = getPopulatedCheckinDetail(checkinDoc,
                                orderDetail, bin, quantity);
                        cdetails.add(checkinDetail);

                        if (availableQty > remQty) {
                            break;
                        }
                    }
                }
            }
        }
        return cdetails;
    }

    private CheckinDetail getPopulatedCheckinDetail(CheckinDocument checkinDoc,
            OrderDetail orderDetail, Bin bin, Integer quantity) {

        CheckinDetail checkinDetail = createCheckinDetail(checkinDoc, orderDetail);

        if (bin != null) {
            checkinDetail.setBinId(bin.getBinId());
            checkinDetail.setBin(bin);
        }
        else {
            checkinDetail.setBinId(null);
            checkinDetail.setBin(null);
        }

        checkinDetail.setAcceptedItemQty(quantity);
        checkinDetail.setOrderDetail(orderDetail);
        orderDetail.getCheckinDetails().add(checkinDetail);
        return checkinDetail;

    }

    private CheckinDetail createCheckinDetail(CheckinDocument checkinDoc, OrderDetail orderDetail) {
        CheckinDetail checkinDetail = new CheckinDetail();
        checkinDetail.setCheckinDocumentNumber(checkinDoc.getDocumentNumber());
        checkinDetail.setStockId(orderDetail.getCatalogItem().getStockId());
        checkinDetail.setStock(this.getOrderDetailWithStock(orderDetail).getCatalogItem()
                .getStock());
        checkinDetail.setPoId(orderDetail.getPoId());
        checkinDetail.setRejectedItemQty(0);
        checkinDetail.setOrderDetailId(orderDetail.getOrderDetailId());
        checkinDetail.setOrderDetail(orderDetail);
        return checkinDetail;
    }

    /**
     * returns a Bin for the the passed order line
     * 
     * @param odetail
     * @param binId
     * @return
     */
    private List<Bin> getBin(CheckinDocument checkinDoc, OrderDetail odetail, Integer binId) {

        List<Bin> lisBins = new ArrayList<Bin>(0);
        if (ObjectUtils.isNull(binId) || binId == 0) {
            lisBins = this.getEmptyBinForStockId(checkinDoc, odetail.getCatalogItem().getStock()
                    .getStockId(), odetail.getOrderItemQty() != null ? odetail.getOrderItemQty()
                    .intValue() : 0);
        }

        else {
            Bin bino = StoresPersistableBusinessObject.getObjectByPrimaryKey(Bin.class, binId);
            if (ObjectUtils.isNotNull(bino)) {
                lisBins.add(bino);
            }
        }

        return lisBins;
    }

    private List<Bin> getEmptyBinForStockId(CheckinDocument checkinDoc, String stockId, int quantity) {
        List<Bin> bins = new ArrayList<Bin>(0);

        Bin bin = this.stockService.getEmptyBinForStockId(checkinDoc, stockId, quantity);
        if (bin != null)
            bins.add(bin);
        return bins;
    }

    private boolean isAnyItemsToBeRejected(CheckinDocument cdoc) {
        for (CheckinDetail cdetail : cdoc.getCheckinDetails()) {
            if (cdetail.getRejectedItemQty() != null && cdetail.getRejectedItemQty() > 0)
                return true;
        }
        return false;
    }

    /**
     * creates Return document for rejected items while checking in items
     */
    public boolean createReturnDocumentForRejectedItems(CheckinDocument checkinDoc)
            throws Exception {
        int index = 0;
        List<ReturnDetail> retDetails = new ArrayList<ReturnDetail>();
        if (!isAnyItemsToBeRejected(checkinDoc))
            return true;
        ReturnDocument rdoc = createReturnDoc(checkinDoc);
        rdoc.setReturnTypeCode(MMConstants.CheckinDocument.VENDOR_RETURN_ORDER_LINE);
        for (CheckinDetail cdetail : checkinDoc.getCheckinDetails()) {
            if (cdetail.getRejectedItemQty() == null || cdetail.getRejectedItemQty() < 1)
                continue;
            ReturnDetail rdetail = createReturnDetail(checkinDoc, cdetail, index, rdoc);
            rdetail.setActionCd(MMConstants.ReturnActionCode.REJECTED);
            rdetail.setItemReturned(true);
            retDetails.add(rdetail);
            index++;
        }

        for (ReturnDetail rd : retDetails) {
            rd.setReturnDoc(rdoc);
        }

        return true;
    }

    /**
     * Adds a new PO the checkin document
     */
    public CheckinDocument addNewPo(CheckinDocument checkinDoc, List<OrderDetailVo> orderVos) {
        for (OrderDetailVo vo : orderVos) {
            addNewPo(checkinDoc, vo);
        }
        return checkinDoc;
    }

    /**
     * Add a new PO object
     */
    private CheckinDocument addNewPo(CheckinDocument checkinDoc, OrderDetailVo vo) {
        CatalogItem citem = vo.getCatalogItem();
        OrderDetail odetail = this.createOrderDetail(checkinDoc, vo, citem);
        List<CheckinDetail> cdetails = this.createNewCheckinDetail(checkinDoc, odetail,
                vo.getBinId());
        CheckinDetail cdetail = cdetails.get(0);
        cdetail.setAcceptedItemQty(vo.getAcceptedItemQuantity());
        cdetail.setInitalAcceptedItemQty(vo.getAcceptedItemQuantity());
        odetail.setOrderDocument(checkinDoc.getOrderDocument());
        odetail.setOrderItemDetailDesc(citem.getStock().getStockDesc());
        checkinDoc.getCheckinDetails().add(cdetail);
        return checkinDoc;
    }

    /**
     * creates an order line for new PO item
     * 
     * @param checkinDoc
     * @param vo
     * @param citem
     * @return
     */
    private OrderDetail createOrderDetail(CheckinDocument checkinDoc, OrderDetailVo vo,
            CatalogItem citem) {
        OrderDetail odetail = new OrderDetail();
        odetail.setOrderDocumentNbr(checkinDoc.getOrderDocNumber());
        // odetail.setOrderDocument(checkinDoc.getOrderDocument());
        odetail.setOrderStatusCd(vo.getReasonCode());
        odetail.setCatalogItemId(citem.getCatalogItemId());
        citem.setActive(true);
        Stock stock = citem.getStock();
        if (ObjectUtils.isNotNull(stock)) {
            stock.setActive(true);
        }
        odetail.setCatalogItem(citem);
        odetail.setItemLineNumber(checkinDoc.getOrderDocument() != null ? checkinDoc
                .getOrderDocument().getOrderDetailsSize() : 0);
        odetail.setStockUnitOfIssueCd(vo.getUnitOfIssueCode());
        odetail.setOrderItemQty(vo.getAcceptedItemQuantity());

        MMDecimal defValue = MMDecimal.ZERO;
        MMDecimal price = MMDecimal.ZERO;

        if (ObjectUtils.isNotNull(citem.getStock())) {
            price = citem.getStock().getStockPrice();
        }
        else {
            stock = this.stockService.getStockByDistributorNumber(vo.getItemNumber());
            if (stock != null)
                price = stock.getStockPrice();
            else
                price = MMDecimal.ZERO;
        }

        odetail.setOrderItemCostAmt(price);
        odetail.setOrderItemPriceAmt(price);
        odetail.setOrderItemMarkupAmt(defValue);
        odetail.setOrderItemTaxAmt(defValue);
        return odetail;

    }


    /**
     * Activates all the catalog items in the passed checkin document and marks all True-Buyout items as obsolete, to prevent future
     * reorders.
     */
    public void activateCatalogItems(CheckinDocument checkinDocument) {
        List<CatalogItem> result = new ArrayList<CatalogItem>(0);

        for (CheckinDetail cdetail : checkinDocument.getCheckinDetails()) {
            if (cdetail.getAcceptedItemQty() > 0) {
                CatalogItem citem = cdetail.getOrderDetail().getCatalogItem();
                citem.setActive(true);
                Stock stock = citem.getStock();
                if (ObjectUtils.isNotNull(stock)) {
                    citem.refreshReferenceObject(MMConstants.CatalogItem.CATALOG);
                    if (MMConstants.CatalogType.TRUE_BUYOUT.equals(citem.getCatalog()
                            .getCatalogTypeCd())) {
                        stock.setObsoleteInd(true);
                    }
                    stock.setActive(true);
                }
                result.add(citem);
            }
        }
        this.getBusinessObjectService().save(result);
    }

    /**
     * @param sourceStockPrices
     * @param targetStockPrices
     * @return
     */
    private List<StockCost> updateStockPrices(List<StockCost> sourceStockPrices,
            List<StockCost> targetStockPrices) {

        if (MMUtil.isCollectionEmpty(targetStockPrices))
            return sourceStockPrices;

        for (StockCost scost : sourceStockPrices) {
            for (StockCost tcost : targetStockPrices) {
                if (tcost.getCostCd().equals(scost.getCostCd())) {
                    scost.setStockCst(tcost.getStockCst());
                    break;
                }
            }
        }
        return sourceStockPrices;
    }

    /**
     *
     */
    public Map<String, MMDecimal> updateStockPrice(CheckinDocument checkinDoc) {

        Map<String, List<StockCost>> dataMap = new HashMap<String, List<StockCost>>();
        Map<String, Integer> qtyMap = new HashMap<String, Integer>();
        Map<String, MMDecimal> oldStockPrice = new HashMap<String, MMDecimal>();

        String curPriceCode = Stock.getCurrentStockPriceCode();

        boolean isCorrectionDoc = checkinDoc.isCorrectedDocument();

        for (CheckinDetail cdetail : checkinDoc.getCheckinDetails()) {
            if ((ObjectUtils.isNull(checkinDoc.getSelectedOrderDetailId()) || checkinDoc
                    .getSelectedOrderDetailId().equals(cdetail.getOrderDetailId()))
                    && (cdetail.getAcceptedItemQty() > 0 || isCorrectionDoc)) {
                String stockId = cdetail.getStockId();
                List<StockCost> stockPrices = new ArrayList<StockCost>(0);

                stockPrices = cdetail.getStock().getStockPrices();
                stockPrices = MMUtil.isCollectionEmpty(stockPrices) ? this.stockService
                        .getStockPricesForStock(stockId) : stockPrices;

                for (StockCost sprice : stockPrices) {
                    if (sprice.getCostCd().equalsIgnoreCase(curPriceCode))
                        oldStockPrice.put(stockId, sprice.getStockCst());
                }

                if (MMConstants.CostCode.WEIGHTED_AVERAGE.equals(curPriceCode)) {
                    // if (!dataMap.containsKey(stockId)) {
                    List<StockCost> tempStock = dataMap.get(stockId);
                    stockPrices = updateStockPrices(stockPrices, tempStock);
                    // oldStockPrice.put(stockId, cdetail.getStock().getStockPrice());
                    // }

                    if (!qtyMap.containsKey(stockId)) {
                        Integer stockQty = cdetail.getStock().getTotalStockedQuantity();
                        qtyMap.put(stockId, stockQty);
                    }
                    OrderDetail odetail = cdetail.getOrderDetail();
                    MMDecimal unitCost = (odetail.getOrderItemPriceAmt() != null ? odetail
                            .getOrderItemPriceAmt() : MMDecimal.ZERO).add(
                            odetail.getOrderItemTaxAmt() != null ? odetail.getOrderItemTaxAmt()
                                    : MMDecimal.ZERO).add(
                            odetail.getOrderItemAdditionalCostAmt() != null ? odetail
                                    .getOrderItemAdditionalCostAmt() : MMDecimal.ZERO);

                    if (isCorrectionDoc)
                        stockPrices = this.stockService.calculateStockPrices(unitCost,
                                cdetail.getStock(), (-1 * cdetail.getCorrectedQuantity()),
                                qtyMap.get(stockId), stockPrices);
                    else
                        stockPrices = this.stockService.calculateStockPrices(unitCost,
                                cdetail.getStock(), cdetail.getAcceptedItemQty(),
                                qtyMap.get(stockId), stockPrices);

                    dataMap.put(stockId, stockPrices);

                    Integer newQty = 0;

                    if (isCorrectionDoc) {
                        newQty = qtyMap.get(stockId) - cdetail.getAcceptedItemQty();
                    }
                    else {
                        newQty = qtyMap.get(stockId) + cdetail.getAcceptedItemQty();
                    }

                    qtyMap.put(stockId, newQty);

                    // updating the catalog item price

                    cdetail.getStock().setStockPrices(stockPrices);

                    this.getBusinessObjectService().save(stockPrices);
                    cdetail.getOrderDetail().getCatalogItem()
                            .setCatalogPrc(cdetail.getStock().getStockPrice());
                }
                this.getBusinessObjectService().save(cdetail.getOrderDetail().getCatalogItem());
            }
        }

        return oldStockPrice;
    }

    /**
     * updates/creates stock balances for all the checkin line items in the checkin document
     */
    public void updateStockBalance(CheckinDocument checkinDocument) {
        List<CheckinDetail> cdetails = checkinDocument.getCheckinDetails();

        Integer selectedItem = checkinDocument.getSelectedOrderDetailId();

        boolean isLineSleected = ObjectUtils.isNull(selectedItem);

        List<StockBalance> sbalances = new ArrayList<StockBalance>(0);

        // validation for checking stock balance and bin number
        for (CheckinDetail cdetail : cdetails) {
            if (isLineSleected || cdetail.getOrderDetailId().equals(selectedItem)) {
                Integer acceptedItemQty = cdetail.getAcceptedItemQty();
                if (acceptedItemQty > 0) {
                    Bin bin = cdetail.getBin();

                    if (bin == null)
                        throw new RuntimeException("SLECEDT BIN IS NOT VALID");

                    StockBalance sb = bin.getStockBalance();
                    if (ObjectUtils.isNotNull(sb)) {
                        sb.addItemQuantityToBin(acceptedItemQty);
                    }
                    else {
                        sb = new StockBalance();
                        sb.setBinId(cdetail.getBinId());
                        sb.setStockId(cdetail.getStockId());
                        sb.setQtyOnHand(acceptedItemQty);
                    }

                    if (cdetail.getStock().isPerishableInd())
                        sb.setStockPerishableDt(new java.sql.Date(cdetail.getStockPerishableDate()
                                .getTime()));

                    sb.setLastCheckinDt(new java.sql.Date(new java.util.Date().getTime()));
                    sb.save();
                    sbalances.add(sb);
                    cdetail.getStock().getStockBalances().add(sb);
                }
            }
        }


    }

    /**
     * updates the order status of the order associated with the checkin document if required
     */
    public void updateOrderStatus(CheckinDocument checkinDocument) {
        List<CheckinDetail> cdetails = checkinDocument.getCheckinDetails();
        Integer selectedItem = checkinDocument.getSelectedOrderDetailId();
        boolean fullOrderCheckIn = ObjectUtils.isNull(selectedItem);
        HashMap<Integer, Integer> orderDetailQtyMap = new HashMap<Integer, Integer>();
        HashSet<Integer> closedLines = new HashSet<Integer>();
        HashMap<Integer, OrderDetail> orderDetailMap = new HashMap<Integer, OrderDetail>();
        for (CheckinDetail cdetail : cdetails) {
            Integer orderDetailId = cdetail.getOrderDetailId();
            orderDetailMap.put(orderDetailId, cdetail.getOrderDetail());
            if (fullOrderCheckIn || orderDetailId.equals(selectedItem)) {
                if (cdetail.isClosedInd()) {
                    closedLines.add(orderDetailId);
                }
                Integer accpetedItemQty = cdetail.getAcceptedItemQty() == null ? 0 : cdetail
                        .getAcceptedItemQty();
                Integer rejectedItemQty = cdetail.getRejectedItemQty() == null ? 0 : cdetail
                        .getRejectedItemQty();
                if (orderDetailQtyMap.get(orderDetailId) == null) {
                    orderDetailQtyMap.put(orderDetailId, accpetedItemQty + rejectedItemQty);
                }
                else {
                    orderDetailQtyMap.put(orderDetailId, orderDetailQtyMap.get(orderDetailId)
                            + (accpetedItemQty + rejectedItemQty));
                }
            }
        }
        Set<Integer> orderDetailIds = orderDetailMap.keySet();
        for (Integer orderDetailId : orderDetailIds) {
            OrderDetail orderDetail = orderDetailMap.get(orderDetailId);
            Integer orderRemainingQty = orderDetail.getRemainingItemQuantity();
            Integer orderAcceptedQty = orderDetailQtyMap.get(orderDetailId);
            if (closedLines.contains(orderDetailId) || orderAcceptedQty >= orderRemainingQty) {
                orderDetail.setOrderStatusCd(MMConstants.OrderStatus.ORDER_LINE_RECEIVED_COMPLETE);
            }
            else {
                orderDetail.setOrderStatusCd(MMConstants.OrderStatus.ORDER_LINE_RECEIVING);
            }
            orderDetail.save();
        }
        String orderDocNumber = checkinDocument.getOrderDocNumber();
        if (getOrderStatusDao().isOrderReceiveComplete(orderDocNumber)) {
            getOrderStatusDao().updateOrderReceived(orderDocNumber);
        }
    }

    /**
     * checks in the rental associated with the checkin document
     */
    public void checkinRentals(CheckinDocument checkinDoc) {

        List<Rental> rentals = new ArrayList<Rental>(0);

        for (CheckinDetail cdetail : checkinDoc.getCheckinDetails()) {
            if (cdetail.getStock().isRental()) {
                List<StagingRental> checkinRentals = cdetail.getCheckinRentals();
                if (!MMUtil.isCollectionEmpty(checkinRentals)) {
                    for (StagingRental checkinRental : checkinRentals) {
                        Rental rental = new Rental();
                        rental.setStockId(cdetail.getStockId());
                        rental.setCheckinDetailId(cdetail.getCheckinDetailId());
                        rental.setRentalSerialNumber(checkinRental.getSerialNumber());
                        rental.setRentalTypeCode(cdetail.getStock().getRentalObject()
                                .getRentalTypeCode());
                        rental.setRentalStatusCode(MMConstants.Rental.RENTAL_STATUS_AVAILABLE);
                        rentals.add(rental);
                    }
                }
                cdetail.setRentals(rentals);
                getBusinessObjectService().save(cdetail.getRentals());
            }
        }

    }

    /**
     * creates Return detail records for the rejected items
     * 
     * @param checkinDoc
     * @param ritem
     * @param index
     * @param rdoc
     * @return
     */
    private ReturnDetail createReturnDetail(CheckinDocument checkinDoc, CheckinDetail cdetail,
            int index, ReturnDocument rdoc) {
        ReturnDetail rdetail = new ReturnDetail();
        OrderDetail odetail = checkinDoc.getCheckinDetails().get(index).getOrderDetail();

        rdetail.setCatalogItemId(odetail.getCatalogItemId());
        rdetail.setReturnQuantity(checkinDoc.getCheckinDetails().get(index).getRejectedItemQty());
        rdetail.setReturnUnitOfIssueOfCode(cdetail.getReturnUnitOfIssueOfCode());
        rdetail.setReturnDetailStatusCode(cdetail.getReturnDetailStatusCode());
        rdetail.setReturnItemPrice(odetail.getOrderItemCostAmt());
        rdetail.setReturnItemDetailDescription(odetail.getOrderItemDetailDesc());
        rdetail.setReturnItemPercentage(new MMDecimal(1));
        rdetail.setReturnCreditAmt(MMDecimal.ZERO);
        rdetail.setCheckinDetailId(cdetail.getCheckinDetailId());
        rdetail.setVendorHeaderGeneratedId(odetail.getVendorHeaderGeneratedId());
        rdetail.setVendorDetailAssignedId(odetail.getVendorDetailAssignedId());
        rdetail.setCheckinDetail(cdetail);
        rdetail.setOrderDetailId(odetail.getOrderDetailId());
        cdetail.getReturnDetails().add(rdetail);
        rdetail.setReturnDocNumber(rdoc.getDocumentNumber());
        rdetail.setReturnDoc(rdoc);
        return rdetail;
    }

    /**
     * creates Return document for teh rejected items
     * 
     * @param rdoc
     * @param checkinDoc
     * @return
     * @throws Exception
     */
    private ReturnDocument createReturnDoc(CheckinDocument checkinDoc) throws Exception {
        Document dd = KRADServiceLocatorWeb.getDocumentService().getNewDocument(
                MMConstants.CHECKIN_VENDOR_RETURNDOC_TYPE);
        dd.getDocumentHeader().setDocumentDescription("Return " + dd.getDocumentNumber());
        // TODO: NWU-Find solution to set the initiator Id.
        // dd.getDocumentHeader().getWorkflowDocument().getRouteHeader().setInitiatorPrincipalId(
        // GlobalVariables.getUserSession().getPrincipalId());
        ReturnDocument rdoc = (ReturnDocument) dd;
        rdoc.setReturnOrderId(rdoc.getDocumentNumber());
        rdoc.setOrderDocumentNumber(checkinDoc.getOrderDocument().getDocumentNumber());
        rdoc.setOrderDocument(checkinDoc.getOrderDocument());
        rdoc.setReturnDocumentStatusCode(MMConstants.OrderStatus.INITIATED);
        rdoc.setReturnTypeCode(MMConstants.CheckinDocument.VENDOR_RETURN_ORDER_LINE);
        rdoc.setCustomerProfileId(checkinDoc.getOrderDocument().getCustomerProfileId());

        if (rdoc.getDocumentHeader().getWorkflowDocument().isInitiated())
            rdoc = KRADServiceLocator.getBusinessObjectService().save(rdoc);

        rdoc.setCheckinDocumentNumber(checkinDoc.getDocumentNumber());
        checkinDoc.getReturnDocuments().add(rdoc);
        return rdoc;
    }

    public CatalogItem getCatalogItem(String manufNumber, String distNumber) {
        return this.checkinOrderDAO.getCatalogItem(manufNumber, distNumber);
    }

    /**
     * @see org.kuali.ext.mm.service.CheckinOrderService#createElectronicInvoiceXml(org.kuali.ext.mm.document.CheckinDocument)
     */
    public void createElectronicInvoiceXml(CheckinDocument document) throws IOException {
        FinancialSystemAdaptorFactory factory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);
        if (document.requireElectronicInvoice()) {
            if (factory.isSystemAvailable()) {
                try {
                    OutputStream os = createEInvoiceOutputStream(document);
                    factory.getFinancialElectronicInvoiceService().submitInvoice(document, os);
                }
                catch (Exception e) {
                    File invoiceXML = new File(invoiceXMLFileName(document));
                    if (invoiceXML.exists()) {
                        invoiceXML.delete();
                    }
                    throw new RuntimeException(e);
                }
            }
            else {
                if (document.isCorrectedDocument()) {
                    createCreditMemo(document);
                }
                else {
                    throw new RuntimeException("Could not generate an electronic invoice XML for "
                            + document.getDocumentNumber());
                }
            }
        }
    }

    private void createCreditMemo(CheckinDocument cdoc) {
        for (CheckinDetail cdetail : cdoc.getCheckinDetails()) {
            cdetail.getCreditMemoExpected().add(getCreditMemoExpected(cdoc, cdetail));
        }

    }

    private CreditMemoExpected getCreditMemoExpected(CheckinDocument cdoc, CheckinDetail cdetail) {
        CreditMemoExpected result = new CreditMemoExpected();
        result.setExpectedCreateDate(new java.sql.Date(new java.util.Date().getTime()));
        result.setReceived(false);
        result.setCheckinDetailId(cdetail.getCheckinDetailId());
        result.setCheckinDetail(cdetail);
        result.setWarehouseCode(cdoc.getWarehouseCode());
        return result;
    }

    public List<OrderDetail> getCheckinDocsForCorrection(Map<String, String> fieldValues) {
        return this.checkinOrderDAO.getCheckinDocsForCorrection(fieldValues);
    }

    public List<BinLookable> getBinList(Map<String, String> fieldValues) {
        return this.checkinOrderDAO.getBinList(fieldValues);
    }

    /**
     * Creates the output stream based on the e-invoice directoy configured
     * 
     * @return
     */
    protected OutputStream createEInvoiceOutputStream(CheckinDocument document) {
        OutputStream os;
        try {
            String invoiceDirectory = SpringContext.getBean(ConfigurationService.class)
                    .getPropertyValueAsString(MMKeyConstants.EXTERNAL_EINVOICE_DIRECTORY_KEY);
            // prepare the directory
            new File(invoiceDirectory).mkdirs();
            os = new BufferedOutputStream(new FileOutputStream(invoiceXMLFileName(document)));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return os;
    }

    /**
     * @param document
     * @param invoiceDirectory
     * @return
     */
    public static String invoiceXMLFileName(CheckinDocument document) {
        String invoiceDirectory = SpringContext.getBean(ConfigurationService.class)
                .getPropertyValueAsString(MMKeyConstants.EXTERNAL_EINVOICE_DIRECTORY_KEY);
        return invoiceDirectory
                + File.separator
                + document.getDocumentNumber()
                + (document.getVendorShipmentNbr() != null ? ("-" + document.getVendorShipmentNbr())
                        : "") + ".xml";
    }

    /**
     * Gets the orderStatusDao property
     * 
     * @return Returns the orderStatusDao
     */
    public OrderStatusDao getOrderStatusDao() {
        return this.orderStatusDao;
    }

    /**
     * Sets the orderStatusDao property value
     * 
     * @param orderStatusDao The orderStatusDao to set
     */
    public void setOrderStatusDao(OrderStatusDao orderStatusDao) {
        this.orderStatusDao = orderStatusDao;
    }

    /**
     * @see org.kuali.ext.mm.service.CheckinOrderService#getActualAcceptedQuantity(org.kuali.ext.mm.businessobject.CheckinDetail)
     */
    public Integer getActualAcceptedQuantity(CheckinDetail detail) {
        CheckinDetail uncorrectedDetail = detail;
        while (!MMUtil.isCollectionEmpty(uncorrectedDetail.getLineCorrections())) {
            // Current implementation should only have 1 item in the list
            uncorrectedDetail = uncorrectedDetail.getLineCorrections().get(0);
        }
        return uncorrectedDetail.getAcceptedItemQty();
    }

    /**
     * @see org.kuali.ext.mm.service.CheckinOrderService#getActualRejectedQuantity(org.kuali.ext.mm.businessobject.CheckinDetail)
     */
    public Integer getActualRejectedQuantity(CheckinDetail detail) {
        CheckinDetail uncorrectedDetail = detail;
        while (!MMUtil.isCollectionEmpty(uncorrectedDetail.getLineCorrections())) {
            // Current implementation should only have 1 item in the list
            uncorrectedDetail = uncorrectedDetail.getLineCorrections().get(0);
        }
        return uncorrectedDetail.getRejectedItemQty();
    }

}
