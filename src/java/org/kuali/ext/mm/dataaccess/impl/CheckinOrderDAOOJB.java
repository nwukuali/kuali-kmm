package org.kuali.ext.mm.dataaccess.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.ext.mm.businessobject.Bin;
import org.kuali.ext.mm.businessobject.BinLookable;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CheckinDetail;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.OrderReturnDetail;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.dataaccess.CheckinOrderDAO;
import org.kuali.ext.mm.document.CheckinDocument;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.dao.impl.PlatformAwareDaoBaseOjb;
import org.kuali.rice.kns.lookup.LookupUtils;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.GlobalVariables;


public class CheckinOrderDAOOJB extends PlatformAwareDaoBaseOjb implements CheckinOrderDAO {

    BusinessObjectService businessObjectService = null;

    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    /**
     * Returns the order Document for the order doc Number
     */
    public OrderDocument getOrderDocument(String orderDocNumber) {
        if (StringUtils.isEmpty(orderDocNumber))
            return null;

        Criteria criteria = new Criteria();
        criteria.addEqualTo(MMConstants.OrderDocument.DOC_NUMBER, orderDocNumber);
        return this.getBusinessObjectService().findBySinglePrimaryKey(OrderDocument.class,
                orderDocNumber);
    }

    /**
     * Gets the catalogItem object for the passed manufcaturer number and distributor number
     */
    public CatalogItem getCatalogItem(String manufNumber, String distNumber) {

        if (StringUtils.isEmpty(distNumber))
            return null;

        Criteria criteria = new Criteria();

        if (!StringUtils.isEmpty(manufNumber))
            criteria.addEqualTo(MMConstants.CatalogItem.MANUF_NUMBER, manufNumber);

        criteria.addEqualTo(MMConstants.CatalogItem.DIST_NUMBER, distNumber);

        return (CatalogItem) getPersistenceBrokerTemplate().getObjectByQuery(
                QueryFactory.newQuery(CatalogItem.class, criteria));
    }

    private boolean isValid(String val, String key) {
        boolean isValid = true;
        if (!StringUtils.isAlphanumeric(val)) {
            isValid = false;
            GlobalVariables.getMessageMap().putError(key,
                    MMKeyConstants.CheckinDoc.INVALID_CRITERIA, val);

        }
        return isValid;
    }

    public List<BinLookable> getBinList(Map<String, String> fieldValues) {
        List<BinLookable> result = new ArrayList<BinLookable>(0);
        Criteria subCriteria = new Criteria();
        boolean isValid = true;

        String stockDisNumber = null;

        for (String crit : fieldValues.keySet()) {

            String val = null;

            if (!StringUtils.isEmpty(crit))
                val = fieldValues.get(crit);

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("binNbr")) {
                isValid = isValid(val, "binNbr") && isValid;
                if (isValid) {
                    Criteria binNumberCrit = new Criteria();
                    binNumberCrit.addEqualTo("binNbr", val);
                    subCriteria.addAndCriteria(binNumberCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase("stockBalance.stock.stockDistributorNbr")) {
                isValid = isValid(val, "stockBalance.stock.stockDistributorNbr") && isValid;
                if (isValid) {
                    Criteria stockDistributorNbrCrit = new Criteria();
                    stockDistributorNbrCrit.addEqualTo("stockBalance.stock.stockDistributorNbr",
                            val);
                    subCriteria.addAndCriteria(stockDistributorNbrCrit);
                }
            }
            // Return only empty bins
            if (StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase("stockBalance.stock.stockDistributorNbr")) {
                Criteria qtyCriter1 = new Criteria();
                qtyCriter1.addLessOrEqualThan("stockBalance.qtyOnHand", 0);
                Criteria qtyCriter2 = new Criteria();
                qtyCriter2.addIsNull("stockBalance.qtyOnHand");
                Criteria qtyCriter = new Criteria();
                qtyCriter.addOrCriteria(qtyCriter1);
                qtyCriter.addOrCriteria(qtyCriter2);
                subCriteria.addAndCriteria(qtyCriter);

            }

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("zone.zoneCd")) {
                isValid = isValid(val, "zone.zoneCd") && isValid;
                if (isValid) {
                    Criteria zoneCdCrit = new Criteria();
                    zoneCdCrit.addEqualTo("zone.zoneCd", val);
                    subCriteria.addAndCriteria(zoneCdCrit);
                }
            }

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("zone.warehouseCd")) {
                isValid = isValid(val, "zone.warehouseCd") && isValid;
                if (isValid) {
                    Criteria warehouseCdCrit = new Criteria();
                    warehouseCdCrit.addEqualTo("zone.warehouseCd", val);
                    subCriteria.addAndCriteria(warehouseCdCrit);
                }
            }

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("zoneId")) {
                isValid = isValid(val, "zoneId") && isValid;
                if (isValid) {
                    Criteria zoneIdCrit = new Criteria();
                    zoneIdCrit.addEqualTo("zoneId", val);
                    subCriteria.addAndCriteria(zoneIdCrit);
                }
            }

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("shelfId")) {
                isValid = isValid(val, "shelfId") && isValid;
                if (isValid) {
                    Criteria shelfIdCrit = new Criteria();
                    shelfIdCrit.addEqualTo("shelfId", val);
                    subCriteria.addAndCriteria(shelfIdCrit);
                }
            }

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("shelfIdNbr")) {
                isValid = isValid(val, "shelfIdNbr") && isValid;
                if (isValid) {
                    Criteria shelfIdNbrCrit = new Criteria();
                    shelfIdNbrCrit.addEqualTo("shelfIdNbr", val);
                    subCriteria.addAndCriteria(shelfIdNbrCrit);
                }
            }

            /**
             * select mbt.bin_id from mm_bin_t mbt where not exists (select * from mm_stock_balance_t mst where mst.bin_id =
             * mbt.bin_id and mst.qty_on_hand > 0)
             */

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("binHeight")) {
                isValid = isValid(val, "binHeight") && isValid;
                if (isValid) {
                    Criteria binHeightCrit = new Criteria();
                    binHeightCrit.addEqualTo("binHeight", val);
                    subCriteria.addAndCriteria(binHeightCrit);
                }
            }

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("binWidth")) {
                isValid = isValid(val, "binWidth") && isValid;
                if (isValid) {
                    Criteria binWidthCrit = new Criteria();
                    binWidthCrit.addEqualTo("binWidth", val);
                    subCriteria.addAndCriteria(binWidthCrit);
                }
            }

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("binLength")) {
                isValid = isValid(val, "binLength") && isValid;
                if (isValid) {
                    Criteria binLengthCrit = new Criteria();
                    binLengthCrit.addEqualTo("binLength", val);
                    subCriteria.addAndCriteria(binLengthCrit);
                }
            }

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("active")) {
                isValid = isValid(val, "active") && isValid;
                if (isValid) {
                    Criteria activeCrit = new Criteria();
                    activeCrit.addEqualTo("active", val);
                    subCriteria.addAndCriteria(activeCrit);
                }
            }

            //

        }
        if (!isValid)
            return result;

        /**
         * select * from mm_order_detail_t mm where exists( select * from mm_order_detail_t mct , mm_checkin_detail_t mdl where
         * mm.order_detail_id = mct.order_detail_id and mct.order_detail_id = mdl.order_detail_id group by mdl.order_detail_id
         * having min(mdl.accepted_item_qty) > 0 )
         */

        Criteria mainCriteria = new Criteria();

        // Criteria subCrit = new Criteria();

        if (!StringUtils.isEmpty(stockDisNumber)) {
            Criteria mainSubCrit = new Criteria();

            Criteria havingCriteria1 = new Criteria();
            havingCriteria1.addEqualToField("binId", Criteria.PARENT_QUERY_PREFIX + "binId");
            mainSubCrit.addAndCriteria(havingCriteria1);

            Criteria correctedDocCrit = new Criteria();
            correctedDocCrit.addGreaterThanField("qtyOnHand", new Integer(0));
            mainSubCrit.addAndCriteria(correctedDocCrit);

            ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(StockBalance.class,
                    mainSubCrit);
            subQuery.setAttributes(new String[] { "1" });


            Criteria mainCriteria1 = new Criteria();

            mainCriteria1.addNotExists(subQuery);
            mainCriteria.addAndCriteria(mainCriteria1);
        }
        mainCriteria.addAndCriteria(subCriteria);

        Integer searchResultsLimit = LookupUtils.getSearchResultsLimit(BinLookable.class);
        if (searchResultsLimit != null && searchResultsLimit > 0) {
            LookupUtils.applySearchResultsLimit(BinLookable.class, mainCriteria, getDbPlatform());
        }
        QueryByCriteria finalQuery = QueryFactory.newQuery(BinLookable.class, mainCriteria);
        finalQuery.setPathOuterJoin("stockBalance");
        result = (List<BinLookable>) getPersistenceBrokerTemplate()
                .getCollectionByQuery(finalQuery);

        return result;

    }

    public List<OrderDetail> getCheckinDocsForCorrection(Map<String, String> fieldValues) {
        Criteria mainCriteria = new Criteria();
        List<OrderDetail> result = new ArrayList<OrderDetail>(0);
        Criteria subCriteria = new Criteria();
        boolean isValid = true;

        for (String crit : fieldValues.keySet()) {

            String val = null;

            if (!StringUtils.isEmpty(crit))
                val = fieldValues.get(crit);

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase(
                            "orderDocumentLookable.checkinDocs.documentNumber")) {
                isValid = isValid(val, "orderDocumentLookable.checkinDocs.documentNumber")
                        && isValid;
                if (isValid) {
                    Criteria docNumberCrit = new Criteria();
                    docNumberCrit.addEqualTo("checkinDoc.documentNumber", val);
                    subCriteria.addAndCriteria(docNumberCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase(
                            "orderDocumentLookable.checkinDocs.vendorRefNbr")) {
                isValid = isValid(val, "orderDocumentLookable.checkinDocs.vendorRefNbr") && isValid;
                if (isValid) {
                    Criteria VendorRefNumberCrit = new Criteria();
                    VendorRefNumberCrit.addEqualTo("checkinDoc.vendorRefNbr", val);
                    subCriteria.addAndCriteria(VendorRefNumberCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase(
                            "orderDocumentLookable.checkinDocs.vendorShipmentNbr")) {
                isValid = isValid(val, "orderDocumentLookable.checkinDocs.vendorShipmentNbr")
                        && isValid;
                if (isValid) {
                    Criteria vendorShipNumberCrit = new Criteria();
                    vendorShipNumberCrit.addEqualTo("checkinDoc.vendorShipmentNbr", val);
                    subCriteria.addAndCriteria(vendorShipNumberCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase("orderDocumentLookable.vendorNm")) {
                if (isValid) {
                    Criteria orderdocVendorNumberCrit = new Criteria();
                    orderdocVendorNumberCrit.addLike("checkinDoc.orderDocument.vendorNm", val
                            .replace("*", "%"));
                    subCriteria.addAndCriteria(orderdocVendorNumberCrit);
                }
            }

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("poId")) {
                isValid = isValid(val, "poId") && isValid;
                if (isValid) {
                    Criteria warehouseCodeCrit = new Criteria();
                    warehouseCodeCrit.addEqualTo("poId", val);
                    mainCriteria.addAndCriteria(warehouseCodeCrit);
                }
            }

            if (!StringUtils.isEmpty(val) && crit.trim().equalsIgnoreCase("distributorNbr")) {
                isValid = isValid(val, "distributorNbr") && isValid;
                if (isValid) {
                    Criteria warehouseCodeCrit = new Criteria();
                    warehouseCodeCrit.addEqualTo("distributorNbr", val);
                    mainCriteria.addAndCriteria(warehouseCodeCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase("orderDocumentLookable.documentNumber")) {
                isValid = isValid(val, "orderDocumentLookable.documentNumber") && isValid;
                if (isValid) {
                    Criteria orderdocNumberCrit = new Criteria();
                    orderdocNumberCrit.addEqualTo("checkinDoc.orderDocument.documentNumber", val);
                    subCriteria.addAndCriteria(orderdocNumberCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase("orderDocumentLookable.orderId")) {
                isValid = isValid(val, "orderDocumentLookable.orderId") && isValid;
                if (isValid) {
                    Criteria docNumberCrit = new Criteria();
                    docNumberCrit.addEqualTo("checkinDoc.orderDocument.orderId", val);
                    subCriteria.addAndCriteria(docNumberCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase("orderDocumentLookable.reqsId")) {
                isValid = isValid(val, "orderDocumentLookable.reqsId") && isValid;
                if (isValid) {
                    Criteria docNumberCrit = new Criteria();
                    docNumberCrit.addEqualTo("checkinDoc.orderDocument.reqsId", val);
                    subCriteria.addAndCriteria(docNumberCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase("orderDocumentLookable.warehouseCd")) {
                isValid = isValid(val, "orderDocumentLookable.warehouseCd") && isValid;
                if (isValid) {
                    Criteria docNumberCrit = new Criteria();
                    docNumberCrit.addEqualTo("checkinDoc.orderDocument.warehouseCd", val);
                    subCriteria.addAndCriteria(docNumberCrit);
                }
            }

        }

        Criteria finalDocCriteria = new Criteria();
        finalDocCriteria.addEqualTo("checkinDoc.finalInd", "Y");
        subCriteria.addAndCriteria(finalDocCriteria);

        if (!isValid)
            return result;

        /**
         * select * from mm_order_detail_t mm where exists( select * from mm_order_detail_t mct , mm_checkin_detail_t mdl where not
         * exists(select 1 form mm_checkin_detail_t kk where corrected_checkin_detail_id = mdl.checkin_detail_id) and
         * mm.order_detail_id = mct.order_detail_id and mct.order_detail_id = mdl.order_detail_id group by mdl.order_detail_id
         * having max(nvl(corrected_checkin_detail_id, 0)) = 0 )
         */

        Criteria subCrit = new Criteria();

        Criteria mainSubCrit = new Criteria();

        Criteria havingCriteria1 = new Criteria();
        havingCriteria1.addEqualToField("orderDetailId", Criteria.PARENT_QUERY_PREFIX
                + "orderDetailId");
        mainSubCrit.addAndCriteria(havingCriteria1);

        Criteria correctedDocCrit = new Criteria();
        correctedDocCrit.addSql("max(nvl(corrected_checkin_detail_id, 0)) = 0");
        mainSubCrit.addAndCriteria(correctedDocCrit);

        ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(CheckinDetail.class,
                subCriteria);
        subQuery.setAttributes(new String[] { "1" });
        String[] groupBy = new String[] { "orderDetail.orderDetailId" };
        subQuery.addGroupBy(groupBy);
        String[] groupBy1 = new String[] { "binId" };
        subQuery.addGroupBy(groupBy1);

        // / for

        Criteria subCrit1 = new Criteria();
        ReportQueryByCriteria subQuery1 = QueryFactory
                .newReportQuery(CheckinDetail.class, subCrit1);
        subQuery1.setAttributes(new String[] { "1" });

        Criteria notCriteria = new Criteria();
        notCriteria.addEqualToField("correctedCheckinDetailId", Criteria.PARENT_QUERY_PREFIX
                + "checkinDetailId");
        subCrit1.addAndCriteria(notCriteria);

        Criteria havingCriteria2 = new Criteria();
        havingCriteria2.addNotExists(subQuery1);
        subCriteria.addAndCriteria(havingCriteria2);

        // /

        subQuery.setHavingCriteria(mainSubCrit);


        Criteria mainCriteria1 = new Criteria();

        mainCriteria1.addExists(subQuery);
        mainCriteria.addAndCriteria(mainCriteria1);

        Integer searchResultsLimit = LookupUtils.getSearchResultsLimit(OrderDetail.class);
        if (searchResultsLimit != null && searchResultsLimit > 0)
            LookupUtils.applySearchResultsLimit(OrderDetail.class, mainCriteria, getDbPlatform());

        // QueryFactory.newQuery(OrderDetail.class, criteria).
        result = (List<OrderDetail>) getPersistenceBrokerTemplate().getCollectionByQuery(
                QueryFactory.newQuery(OrderDetail.class, mainCriteria));

        return result;
    }

    public List<OrderDetail> getOrderLinesForLookup(Map<String, String> fieldValues) {
        List<OrderDetail> results = new ArrayList<OrderDetail>();
        Criteria criteria = new Criteria();
        boolean isValid = true;
        for (String crit : fieldValues.keySet()) {

            String val = null;
            if (!StringUtils.isEmpty(crit))
                val = fieldValues.get(crit);


            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase(
                            MMConstants.CheckinDocument.VENDOR_NAME_FOR_LOOKUP)) {
                Criteria vendNameCrit = new Criteria();
                vendNameCrit.addLike(MMConstants.CheckinDocument.VENDOR_NAME_FOR_LOOKUP, val
                        .replace("*", "%"));
                criteria.addAndCriteria(vendNameCrit);
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase(
                            MMConstants.CheckinDocument.WAREHOUSE_CODE_FOR_LOOKUP)) {
                isValid = isValid(val, MMConstants.CheckinDocument.WAREHOUSE_CODE_FOR_LOOKUP)
                        && isValid;
                if (isValid) {
                    Criteria wareHouseCrit = new Criteria();
                    wareHouseCrit.addEqualTo(MMConstants.CheckinDocument.WAREHOUSE_CODE_FOR_LOOKUP,
                            val);
                    criteria.addAndCriteria(wareHouseCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase(MMConstants.CheckinDocument.PO_ID_LOOKUP)) {
                isValid = isValid(val, MMConstants.CheckinDocument.PO_ID_LOOKUP) && isValid;
                if (isValid) {
                    Criteria poIdCrit = new Criteria();
                    poIdCrit.addEqualTo(MMConstants.CheckinDocument.PO_ID_LOOKUP, val);
                    criteria.addAndCriteria(poIdCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase(MMConstants.CheckinDocument.REQS_ID_FOR_LOOKUP)) {
                isValid = isValid(val, MMConstants.CheckinDocument.REQS_ID_FOR_LOOKUP) && isValid;
                if (isValid) {

                    Criteria reqsIdCrit = new Criteria();
                    reqsIdCrit.addEqualTo(MMConstants.CheckinDocument.REQS_ID_FOR_LOOKUP, val);
                    criteria.addAndCriteria(reqsIdCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase(
                            MMConstants.CheckinDocument.VENDOR_SHIPMENT_NUMBER_FOR_LOOKUP)) {
                isValid = isValid(val,
                        MMConstants.CheckinDocument.VENDOR_SHIPMENT_NUMBER_FOR_LOOKUP)
                        && isValid;
                if (isValid) {

                    Criteria vendShipNumbCrit = new Criteria();
                    vendShipNumbCrit.addEqualTo(
                            MMConstants.CheckinDocument.VENDOR_SHIPMENT_NUMBER_FOR_LOOKUP, val);
                    criteria.addAndCriteria(vendShipNumbCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase(
                            MMConstants.CheckinDocument.VENDOR_REF_NUMBER_FOR_LOOKUP)) {
                isValid = isValid(val, MMConstants.CheckinDocument.VENDOR_REF_NUMBER_FOR_LOOKUP)
                        && isValid;
                if (isValid) {

                    Criteria vendRefNumbCrit = new Criteria();
                    vendRefNumbCrit.addEqualTo(
                            MMConstants.CheckinDocument.VENDOR_REF_NUMBER_FOR_LOOKUP, val);
                    criteria.addAndCriteria(vendRefNumbCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase(
                            MMConstants.CheckinDocument.ORDER_DOCUMENT_NUMBER_FOR_LOOKUP)) {
                isValid = isValid(val, MMConstants.CheckinDocument.ORDER_DOCUMENT_NUMBER_FOR_LOOKUP)
                        && isValid;
                if (isValid) {

                    Criteria orderDocNumbCrit = new Criteria();
                    orderDocNumbCrit.addEqualTo(
                            MMConstants.CheckinDocument.ORDER_DOCUMENT_NUMBER_FOR_LOOKUP, val);
                    criteria.addAndCriteria(orderDocNumbCrit);
                }
            }
            if (!StringUtils.isEmpty(val)
                    && crit.trim()
                            .equalsIgnoreCase(MMConstants.CheckinDocument.ORDER_ID_FOR_LOOKUP)) {
                isValid = isValid(val, MMConstants.CheckinDocument.ORDER_ID_FOR_LOOKUP) && isValid;
                if (isValid) {

                    Criteria orderDocNumbCrit = new Criteria();
                    orderDocNumbCrit.addEqualTo(MMConstants.CheckinDocument.ORDER_ID_FOR_LOOKUP,
                            val);
                    criteria.addAndCriteria(orderDocNumbCrit);
                }
            }

            if (!StringUtils.isEmpty(val)
                    && crit.trim().equalsIgnoreCase(
                            MMConstants.CheckinDocument.DISTRIBUTOR_NUMBER_FOR_LOOKUP)) {
                isValid = isValid(val, MMConstants.CheckinDocument.DISTRIBUTOR_NUMBER_FOR_LOOKUP)
                        && isValid;
                if (isValid) {

                    Criteria distNumbCrit = new Criteria();
                    distNumbCrit.addEqualTo(
                            MMConstants.CheckinDocument.DISTRIBUTOR_NUMBER_FOR_LOOKUP, val);
                    criteria.addAndCriteria(distNumbCrit);
                }
            }

        }

        if (!isValid)
            return results;

        Criteria orderTypeCriteria = new Criteria();
        orderTypeCriteria.addEqualTo(MMConstants.CheckinDocument.ORDER_TYPE_CODE_FOR_LOOKUP,
                MMConstants.OrderDocument.ORDER_TYPE_STOCK);
        criteria.addAndCriteria(orderTypeCriteria);

        Criteria orderLineStatusCriteria = new Criteria();
        List<String> orderLineStatus = new ArrayList<String>(0);
        orderLineStatus.add(MMConstants.OrderStatus.ORDER_LINE_OPEN);
        orderLineStatus.add(MMConstants.OrderStatus.ORDER_LINE_PRINTED);
        orderLineStatus.add(MMConstants.OrderStatus.ORDER_LINE_RECEIVING);
        orderLineStatusCriteria.addIn(MMConstants.CheckinDocument.ORDER_STATUS_CODE_FOR_LOOKUP,
                orderLineStatus);
        // PO Id should be existing
        orderLineStatusCriteria.addNotNull(MMConstants.CheckinDocument.PO_ID_LOOKUP);
        criteria.addAndCriteria(orderLineStatusCriteria);


        /**
         * select * from mm_order_detail_t mdt where not exists ( select mct.ORDER_DETAIL_ID from mm_checkin_detail_t mct where not
         * exists (select 1 from mm_checkin_detail_t ll where ll.corrected_checkin_detail_id = mct.checkin_detail_id) group by
         * mct.ORDER_DETAIL_ID having mdt.ORDER_ITEM_QTY < sum(mct.ACCEPTED_ITEM_QTY) and mct.ORDER_DETAIL_ID = mdt.ORDER_DETAIL_ID );
         */

        Criteria subCrit = new Criteria();

        Criteria mainSubCrit = new Criteria();

        Criteria havingCriteria = new Criteria();
        havingCriteria.addGreaterOrEqualThanField(
                "sum(ACCEPTED_ITEM_QTY) + sum(REJECTED_ITEM_QTY)", Criteria.PARENT_QUERY_PREFIX
                        + "orderItemQty");
        mainSubCrit.addAndCriteria(havingCriteria);


        Criteria havingCriteria1 = new Criteria();
        havingCriteria1.addEqualToField("orderDetailId", Criteria.PARENT_QUERY_PREFIX
                + "orderDetailId");
        mainSubCrit.addAndCriteria(havingCriteria1);

        Criteria finalDocCriteria = new Criteria();
        finalDocCriteria.addEqualTo("checkinDoc.finalInd", "Y");
        subCrit.addAndCriteria(finalDocCriteria);

        ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(CheckinDetail.class, subCrit);

        String[] groupBy = new String[] { MMConstants.PurchaseHistory.ORDER_DETAIL_ID };
        subQuery.addGroupBy(groupBy);
        subQuery.setHavingCriteria(mainSubCrit);

        subQuery.setAttributes(new String[] { "1" });


        Criteria subCrit1 = new Criteria();
        ReportQueryByCriteria subQuery1 = QueryFactory
                .newReportQuery(CheckinDetail.class, subCrit1);
        subQuery1.setAttributes(new String[] { "1" });

        Criteria notCriteria = new Criteria();
        notCriteria.addEqualToField("correctedCheckinDetailId", Criteria.PARENT_QUERY_PREFIX
                + "checkinDetailId");
        subCrit1.addAndCriteria(notCriteria);

        Criteria havingCriteria2 = new Criteria();
        havingCriteria2.addNotExists(subQuery1);
        subCrit.addAndCriteria(havingCriteria2);

        criteria.addNotExists(subQuery);

        Integer searchResultsLimit = LookupUtils.getSearchResultsLimit(OrderDetail.class);
        if (searchResultsLimit != null && searchResultsLimit > 0)
            LookupUtils.applySearchResultsLimit(OrderDetail.class, criteria, getDbPlatform());

        // QueryFactory.newQuery(OrderDetail.class, criteria).
        results = (List<OrderDetail>) getPersistenceBrokerTemplate().getCollectionByQuery(
                QueryFactory.newQuery(OrderDetail.class, criteria));

        return results;
    }

    /**
     * Returns an empty bin
     * 
     * @param lisBins list of Bins to be excluded
     * @return
     */
    private Bin getEmptyBin(String warehouseCode, List<Integer> lisBins, int quantity) {
        Bin result = null;
        Criteria criteria = new Criteria();
        Criteria subQueryCriteria = new Criteria();
        subQueryCriteria.addEqualToField(MMConstants.Bin.BIN_ID, Criteria.PARENT_QUERY_PREFIX
                + MMConstants.Bin.BIN_ID);
        ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(StockBalance.class,
                new String[] { MMConstants.Bin.BIN_ID }, subQueryCriteria, true);
        criteria.addNotExists(subQuery);

        if (!MMUtil.isCollectionEmpty(lisBins)) {
            Criteria excludeBins = new Criteria();
            excludeBins.addNotIn(MMConstants.Bin.BIN_ID, lisBins);
            criteria.addAndCriteria(excludeBins);
        }

        Criteria criteriaQty = new Criteria();
        criteriaQty.addGreaterThan(MMConstants.Bin.MAXIMUM_SHELF_QUANTITY, quantity);

        criteria.addAndCriteria(criteriaQty);

        if (!StringUtils.isEmpty(warehouseCode)) {
            Criteria warehouseCodeCrit = new Criteria();
            warehouseCodeCrit.addEqualTo("zone.warehouseCd", warehouseCode);
            criteria.addAndCriteria(warehouseCodeCrit);
        }

        QueryByCriteria qcriteria = new QueryByCriteria(Bin.class, criteria);

        Iterator<Bin> values = getPersistenceBrokerTemplate().getIteratorByQuery(qcriteria);

        while (values.hasNext()) {
            Object obj = values.next();
            result = (Bin) obj;
        }

        return result;
    }

    /**
     * Returns an empty bin if one is available
     */
    public Bin getEmptyBin(CheckinDocument checkinDoc, int quantity) {

        List<Integer> lisBins = new ArrayList<Integer>(0);
        for (CheckinDetail cdetail : checkinDoc.getCheckinDetails()) {
            if (cdetail.getBinId() != null)
                lisBins.add(cdetail.getBinId());
        }
        return this.getEmptyBin(checkinDoc.getWarehouseCode(), lisBins, quantity);
    }

    public List<OrderDetail> getOrderLinesForCustomerReturn(String docNumber) {
        List<OrderDetail> result = new ArrayList<OrderDetail>(0);

        Criteria criteria = new Criteria();

        Criteria newLineNullCriteria = new Criteria();
        newLineNullCriteria.addEqualTo("orderDetail.orderDocument.documentNumber", docNumber);
        criteria.addAndCriteria(newLineNullCriteria);

        org.apache.ojb.broker.query.QueryByCriteria query = QueryFactory.newQuery(
                OrderReturnDetail.class, criteria);
        List<OrderReturnDetail> results = (List<OrderReturnDetail>) getPersistenceBrokerTemplate()
                .getCollectionByQuery(query);

        if (!MMUtil.isCollectionEmpty(results)) {
            for (OrderReturnDetail data : results) {
                result.add(data.getOrderDetail());
            }
        }
        return result;
    }
}