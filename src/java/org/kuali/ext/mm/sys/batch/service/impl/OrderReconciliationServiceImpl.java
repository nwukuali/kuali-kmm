/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.purap.document.FinancialPurchaseOrderAccount;
import org.kuali.ext.mm.integration.purap.document.FinancialPurchaseOrderDetail;
import org.kuali.ext.mm.integration.purap.document.FinancialPurchaseOrderDocument;
import org.kuali.ext.mm.integration.purap.document.FinancialRequisitionDocument;
import org.kuali.ext.mm.integration.service.FinancialPurchasingService;
import org.kuali.ext.mm.sys.batch.service.OrderDocumentQueryService;
import org.kuali.ext.mm.sys.batch.service.OrderReconciliationService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.util.ArrayList;
import java.util.List;


/**
 * @author rshrivas
 */
public class OrderReconciliationServiceImpl implements OrderReconciliationService {

    @SuppressWarnings("unchecked")
    public void reconcileOrders() {

        BusinessObjectService boService = SpringContext.getBean(BusinessObjectService.class);
        OrderDocumentQueryService orderQueryService = SpringContext
                .getBean(OrderDocumentQueryService.class);
        // Query for all the Orders ....
        List<OrderDocument> orderDocs = orderQueryService.getOrderDocuments();
        // Obtain FinancialSystemAdaptorFactory
        FinancialSystemAdaptorFactory fsFactory = SpringContext
                .getBean(FinancialSystemAdaptorFactory.class);

        FinancialPurchasingService fPurchasingService;
        FinancialPurchaseOrderDocument financialOrder;

        // if the FinancialSystemAdaptorFactory is available
        if (fsFactory.isSystemAvailable()) {

            // Obtain FinancialPurchaseService from FinancialSystemAdaptorFactory
            fPurchasingService = fsFactory.getFinancialPurchasingService();

            // loop through the "List of Qualified Orders" to obtain individual Orders
            for (int i = 0; i < orderDocs.size(); i++) {
                OrderDocument orderDoc = orderDocs.get(i);
                // for each such individual Order, if the Requisition Id is not null
                if (orderDoc.getReqsId() != null) {
                    // get FinancialPurchaseOrderDocument by that RequisitionId
                    financialOrder = fPurchasingService.getPurchaseOrderByRequisitionId(orderDoc
                            .getReqsId());
                    if (isReadyForProcessing(financialOrder)) {
                        String orderStatusCd = null;
                        if (KewApiConstants.ROUTE_HEADER_CANCEL_CD.equals(financialOrder
                                .getWorkflowStatusCode())
                                || "VOID".equals(financialOrder.getPoStatusCode())) {
                            orderStatusCd = MMConstants.OrderStatus.ORDER_LINE_CANCELED;
                        }
                        if (KewApiConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(financialOrder
                                .getWorkflowStatusCode())) {
                            orderStatusCd = MMConstants.OrderStatus.DISAPPROVE;
                        }
                        if (orderStatusCd != null) {
                            orderDoc.setOrderStatusCd(orderStatusCd);
                        }
                        // set the below Order fields with the ones obtained from FinancialPurchaseOrderDocument
                        orderDoc.setVendorHeaderGeneratedId(financialOrder
                                .getVendorHeaderGeneratedId());
                        orderDoc.setVendorDetailAssignedId(financialOrder
                                .getVendorDetailAssignedId());
                        orderDoc.setVendorNm(financialOrder.getVendorNm());
                        orderDoc.setCampusCd(financialOrder.getCampusCd());
                        orderDoc.setDeliveryBuildingCd(financialOrder.getDeliveryBuildingCd());
                        orderDoc
                                .setDeliveryBuildingRmNbr(financialOrder.getDeliveryBuildingRmNbr());

                        // Obtain a "List of OrderDetails" from the Order Object
                        List<OrderDetail> thisOrderDetails = orderDoc.getOrderDetails();

                        // Also obtain a "List of FinancialPurchaseOrderDetail" from FinancialPurchaseOrderDocument
                        List<FinancialPurchaseOrderDetail> finPODetailList = financialOrder
                                .getOrderDetails();

                        // Loop through the List of OrderDetails and obtain individual OrderDetail Object
                        for (int a = 0; a < thisOrderDetails.size(); a++) {
                            OrderDetail orderDetail = thisOrderDetails.get(a);
                            for (int a1 = 0; a1 < finPODetailList.size(); a1++) {
                                updateOrderDetail(financialOrder, orderDoc,
                                        finPODetailList.get(a1), orderDetail, orderStatusCd);
                            }
                        }
                    }
                    else {
                        checkAndUpdateRequisitionStatus(fsFactory, orderDoc);
                    }
                }
                // Save the Order Object..
                boService.save(orderDoc);
            }
        }
    }

    /**
     * @param fsFactory
     * @param orderDoc
     */
    private void checkAndUpdateRequisitionStatus(FinancialSystemAdaptorFactory fsFactory,
            OrderDocument orderDoc) {
        // check for requisition document status for cancel or disapproved
        FinancialRequisitionDocument requisitionDoc = fsFactory.getFinancialRequisitionService()
                .getRequisitionById(orderDoc.getReqsId());
        if (requisitionDoc != null) {
            String orderStatusCd = null;
            if (KewApiConstants.ROUTE_HEADER_CANCEL_CD.equals(requisitionDoc.getWorkflowStatusCode())) {
                orderStatusCd = MMConstants.OrderStatus.ORDER_LINE_CANCELED;
            }
            if (KewApiConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(requisitionDoc
                    .getWorkflowStatusCode())) {
                orderStatusCd = MMConstants.OrderStatus.DISAPPROVE;
            }
            if (orderStatusCd != null) {
                orderDoc.setOrderStatusCd(orderStatusCd);
                List<OrderDetail> orderDetails = orderDoc.getOrderDetails();
                for (OrderDetail orderDetail : orderDetails) {
                    orderDetail.setOrderStatusCd(orderStatusCd);
                    orderDetail.save();
                }
            }
        }
    }

    /**
     * @param financialOrder
     * @return
     */
    private boolean isReadyForProcessing(FinancialPurchaseOrderDocument financialOrder) {
        return financialOrder != null
                && !KewApiConstants.ROUTE_HEADER_INITIATED_CD.equals(financialOrder
                        .getWorkflowStatusCode())
                && !KewApiConstants.ROUTE_HEADER_SAVED_CD.equals(financialOrder
                        .getWorkflowStatusCode())
                && !KewApiConstants.ROUTE_HEADER_ENROUTE_CD.equals(financialOrder
                        .getWorkflowStatusCode());
    }

    /**
     * @param financialOrder
     * @param orderDoc
     * @param finPODetailList
     * @param orderDetail
     * @param a1
     */
    protected void updateOrderDetail(FinancialPurchaseOrderDocument financialOrder,
            OrderDocument orderDoc, FinancialPurchaseOrderDetail poDetail, OrderDetail orderDetail,
            String orderStatusCd) {
        if (orderDetail.getItemLineNumber() != null
                && orderDetail.getItemLineNumber().equals(poDetail.getItemLineNumber())) {
            orderDetail.setVendorNm(financialOrder.getVendorNm());
            List<Accounts> newAccounts = new ArrayList<Accounts>();
            if (orderStatusCd != null) {
                orderDetail.setOrderStatusCd(orderStatusCd);
            }
            KualiDecimal saleOrderQuantity = orderDetail.convertToSaleOrderQuantity(poDetail
                    .getOrderItemQty());
            orderDetail.setOrderItemQty(saleOrderQuantity.intValue());
            MMDecimal saleItemCostAmount = orderDetail.convertToSaleItemCostAmount(new MMDecimal(
                poDetail.getOrderItemCostAmt().doubleValue()));
            orderDetail.setOrderItemCostAmt(saleItemCostAmount);
            if (MMConstants.OrderType.STOCK.equals(orderDoc.getOrderTypeCode())) {
                orderDetail.setOrderItemPriceAmt(saleItemCostAmount);
            }
            orderDetail.setPoId(financialOrder.getPoId());
            orderDetail.getAccounts().clear();
            List<FinancialPurchaseOrderAccount> fPOAList = poDetail.getAccounts();
            for (int b1 = 0; b1 < fPOAList.size(); b1++) {
                addAccount(orderDetail, fPOAList.get(b1), newAccounts);
            }
            orderDetail.save();
            SpringContext.getBean(BusinessObjectService.class).save(newAccounts);
        }
    }

    /**
     * @param newAccs
     * @param fPOAList
     * @param b1
     */
    protected void addAccount(OrderDetail orderDetail,
            FinancialPurchaseOrderAccount financialAccount, List<Accounts> newAccounts) {
        Accounts account = new Accounts();
        account.setOrderDetailId(orderDetail.getOrderDetailId());
        account.setAmountType(MMConstants.OrderDocument.OPTION_FXD);
        account.setFinCoaCd(financialAccount.getFinCoaCd());
        account.setAccountNbr(financialAccount.getAccountNbr());
        account.setSubAcctNbr(financialAccount.getSubAcctNbr());
        account.setFinObjectCd(financialAccount.getFinObjectCd());
        account.setFinSubObjectCd(financialAccount.getFinSubObjectCd());
        account.setProjectCd(financialAccount.getProjectCd());
        account.setAccountFixedAmt(new KualiDecimal(financialAccount.getAccountFixedAmt()));
        account.setAccountPct(financialAccount.getAccountPct());
        newAccounts.add(account);
    }
}
