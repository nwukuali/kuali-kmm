package org.kuali.ext.mm.dataaccess.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.OrderReturnDetail;
import org.kuali.ext.mm.businessobject.OrderReturnDetailForVendor;
import org.kuali.ext.mm.businessobject.ReorderCatalogItemDetail;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.dataaccess.ReturnOrderDAO;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.dao.impl.PlatformAwareDaoBaseOjb;
import org.kuali.rice.kns.util.ObjectUtils;



public class ReturnOrderDAOOJB extends PlatformAwareDaoBaseOjb implements ReturnOrderDAO {

    /**
     * @see org.kuali.ext.mm.dataaccess.ReturnOrderDAO#getCatalogItemsForStockAgreementNumber(java.lang.String)
     */
    public List<CatalogItem> getCatalogItemsForStockAgreementNumber(String agreementNumber) {

        Criteria criteria = new Criteria();
        Criteria stockAgreeCriteria = new Criteria();
        stockAgreeCriteria.addEqualTo(MMConstants.CatalogItem.STOCK_AGREEMENT_NUMBER,
                agreementNumber);
        criteria.addAndCriteria(stockAgreeCriteria);
        List<CatalogItem> results = (List<CatalogItem>) getPersistenceBrokerTemplate()
                .getCollectionByQuery(QueryFactory.newQuery(CatalogItem.class, criteria));
        return results;

    }

    public Map<Integer, Integer> getBalanceQuantityForOrder(ReturnDocument rdoc, boolean isRouted) {
        Map<Integer, Integer> result = new HashMap<Integer, Integer>(0);
        List<Integer> orderLines = new ArrayList<Integer>(0);

        for (ReturnDetail rdetail : rdoc.getReturnDetails()) {
            if (isRouted) {
                orderLines.add(rdetail.getOrderDetailId());
            }
            else {                
                if (rdetail.isItemReturned())
                    orderLines.add(rdetail.getOrderDetailId());
            }
        }

        Map fieldValues = new HashMap();
        fieldValues.put(MMConstants.OrderDetail.ORDER_DETAIL_ID, orderLines);
        List<OrderReturnDetailForVendor> resVend = null;
        List<OrderReturnDetail> resCust = null;

        if (rdoc.isCurrDocVendorReturnDoc())
            resVend = (List<OrderReturnDetailForVendor>) MMServiceLocator
                    .getBusinessObjectService().findMatching(
                            org.kuali.ext.mm.businessobject.OrderReturnDetailForVendor.class,
                            fieldValues);
        else
            resCust = (List<OrderReturnDetail>) MMServiceLocator.getBusinessObjectService()
                    .findMatching(org.kuali.ext.mm.businessobject.OrderReturnDetail.class,
                            fieldValues);

        if (!MMUtil.isCollectionEmpty(resVend)) {
            for (OrderReturnDetailForVendor data : resVend) {
                result.put(data.getOrderDetailId(), data.getBalanceQty());
            }
        }

        if (!MMUtil.isCollectionEmpty(resCust)) {
            for (OrderReturnDetail data : resCust) {
                result.put(data.getOrderDetailId(), data.getBalanceQty());
            }
        }

        return result;
    }

    /**
     * @see org.kuali.ext.mm.dataaccess.ReturnOrderDAO#getCatalogItemsForReorder(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public List<ReorderCatalogItemDetail> getCatalogItemsForReorder(String agreementNumber,
            String catalogGroupCode, String catalogSubGroupCode, String warehouseCode) {

        Criteria criteria = new Criteria();

        if (!StringUtils.isEmpty(warehouseCode)) {
            Criteria warehouseCrit = new Criteria();
            warehouseCrit
                    .addEqualTo(MMConstants.ReorderItem.WAREHOUSE_CD, warehouseCode);
            criteria.addAndCriteria(warehouseCrit);
        }
        if (!StringUtils.isEmpty(agreementNumber)) {
            Criteria stockAgreeCriteria = new Criteria();
            stockAgreeCriteria
                    .addEqualTo(MMConstants.ReorderItem.AGREEMENT_NUMBER, agreementNumber);
            criteria.addAndCriteria(stockAgreeCriteria);
        }
        else {

            Criteria catalogSubGroupCriteria = new Criteria();
            catalogSubGroupCriteria.addEqualTo(MMConstants.ReorderItem.CATALOG_SUBGROUPCODE,
                    (catalogSubGroupCode));
            criteria.addAndCriteria(catalogSubGroupCriteria);

            Criteria catalogCriteria = new Criteria();
            catalogCriteria.addEqualTo(MMConstants.ReorderItem.CATALOG_GROUPCODE,
                    (catalogGroupCode));
            criteria.addAndCriteria(catalogCriteria);
        }
        List<ReorderCatalogItemDetail> results = (List<ReorderCatalogItemDetail>) getPersistenceBrokerTemplate()
                .getCollectionByQuery(
                        QueryFactory.newQuery(ReorderCatalogItemDetail.class, criteria));
        List<Integer> stockIds = new ArrayList<Integer>(0);

        for (ReorderCatalogItemDetail item : results) {
            if (isValidItem(item))
                stockIds.add(item.getStockId());
        }
        if (MMUtil.isCollectionEmpty(stockIds))
            return null;


        Map params = new HashMap();
        params.put(MMConstants.Stock.STOCK_ID, stockIds);
        List<CatalogItem> result = (List<CatalogItem>) MMServiceLocator.getBusinessObjectService()
                .findMatching(CatalogItem.class, params);
        Map<String, CatalogItem> mappedData = getMapData(result);
        for (ReorderCatalogItemDetail data : results) {
            CatalogItem citem = mappedData.get(String.valueOf(data.getStockId()));
            data.setCatalogItem(citem);
            Integer qty = getDefaultQuantity(data);
            if (qty > 0)
                data.setDefaultQuantity(qty);
            else
                data.setDefaultQuantity(0);
        }
        return results;


    }

    private Map<String, CatalogItem> getMapData(List<CatalogItem> data) {
        Map<String, CatalogItem> result = new HashMap<String, CatalogItem>(0);
        for (CatalogItem item : data) {
            result.put(item.getStockId(), item);
        }
        return result;
    }

    private Integer getDefaultQuantity(ReorderCatalogItemDetail data) {

        Integer maxOrderQty = data.getMaximumOrderQty();
        Integer miniOrderQty = data.getMinimumPoQty();
        Integer onBackOrderQty = data.getOneBackOrderQty();
        Integer allOrderQty = data.getAllOrdersQty();
        Integer dayOfOrders = data.getDayOfOrders();
        Integer onReorderQty = data.getOnReorderQty();
        Integer agLagDays = data.getAgreementLagDays();
        Integer result1 = 0;

        if (dayOfOrders > 0)
            result1 = miniOrderQty + onBackOrderQty
                    + Integer.valueOf(((allOrderQty / dayOfOrders) * agLagDays)) - onReorderQty;
        else
            result1 = miniOrderQty + onBackOrderQty - onReorderQty;

        if (maxOrderQty > 0) {
            if (result1 > maxOrderQty)
                return maxOrderQty;
        }
        return result1;
    }

    private boolean isValidItem(ReorderCatalogItemDetail rdetail) {
        if (ObjectUtils.isNotNull(rdetail.getStock())
                && rdetail.getStock().getRemoveUntilDate() != null) {
            // return false if to be removed untill a future date
            if (rdetail.getStock().getRemoveUntilDate().after(new Date())) {
                return false;
            }

        }
        // REORDER_POINT_QTY > (ON_HAND_QTY + ON_REORDER_QTY) - (ON_ORDER_QTY + ON_BACK_ORDER_QTY)
        Integer rpointQty = rdetail.getReorderPointQty() == null ? 0 : rdetail.getReorderPointQty();
        Integer onHandQty = rdetail.getOnHandQty() == null ? 0 : rdetail.getOnHandQty();
        Integer onReorderQty = rdetail.getOnReorderQty() == null ? 0 : rdetail.getOnReorderQty();
        Integer onOrderQty = rdetail.getOneOrderQty() == null ? 0 : rdetail.getOneOrderQty();
        Integer backQty = rdetail.getOneBackOrderQty() == null ? 0 : rdetail.getOneBackOrderQty();
        return rpointQty > 0 && rpointQty >= ((onHandQty + onReorderQty) - (onOrderQty + backQty));
    }

    public List<OrderDetail> getSearchResultsForReturns(Map<String, String> criteria,
            boolean isVendor) {

        List<OrderDetail> results = new ArrayList<OrderDetail>(0);

        Criteria mainCriteria = new Criteria();


        QueryByCriteria query = QueryFactory.newQuery(
                org.kuali.ext.mm.businessobject.OrderReturnDetail.class, mainCriteria);
        query.setObjectProjectionAttribute("orderDetail", OrderDetail.class);
        results = (List<OrderDetail>) getPersistenceBrokerTemplate().getCollectionByQuery(query);

        return results;

    }
}
