package org.kuali.ext.mm.service.impl;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.service.IReturnCommand;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.UserSession;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;


public class ReorderReturnActionService implements IReturnCommand {


    public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {

        if (StringUtils.isEmpty(rdoc.getOrderDocumentNumber()))
            return;

        OrderDocument odoc = null;
        // if(!rdetail.requiresDummyOrder())
        if (ObjectUtils.isNull(rdoc.getReorderDocument()) || rdetail.requiresDummyOrder()) {

            UserSession curSession = GlobalVariables.getUserSession();
            String name = ObjectUtils.isNull(rdoc.getDocumentHeader().getWorkflowDocument()
                    .getAllPriorApprovers()) ? KIMServiceLocator.getIdentityManagementService()
                    .getPrincipal(
                            rdoc.getDocumentHeader().getWorkflowDocument()
                                    .getInitiatorPrincipalId()).getPrincipalName() : rdoc
                    .getDocumentHeader().getWorkflowDocument().getAllPriorApprovers().iterator()
                    .next().getPrincipalName();
            GlobalVariables.setUserSession(new UserSession(name));

            odoc = (OrderDocument) KNSServiceLocator.getDocumentService().getNewDocument(
                    MMConstants.OrderDocument.STORES_ORDER_DOCUMENT);
            OrderDocument ddd = rdoc.getOrderDocument();
            if (ddd == null) {

                ddd = (OrderDocument) KNSServiceLocator.getDocumentDao().findByDocumentHeaderId(
                        org.kuali.ext.mm.document.OrderDocument.class,
                        rdoc.getOrderDocumentNumber());
            }
            odoc = this.getPopulatedOrderDocument(ddd, odoc);

            GlobalVariables.setUserSession(curSession);
        }
        else {
            odoc = rdoc.getReorderDocument();
        }

        this.getPopulatedOrderLine(odoc, rdetail);

        // MMServiceLocator.getBusinessObjectService().save(odoc);

        if (!rdetail.requiresDummyOrder()) {
            rdoc.setReorderDocument(odoc);
        }
        else {
            rdoc.getDummyOrders().add(odoc);
        }


    }

    private OrderDocument getPopulatedOrderDocument(OrderDocument oldObj, OrderDocument newObj) {
        newObj.setOrderId(KNSServiceLocator.getSequenceAccessorService()
                .getNextAvailableSequenceNumber(ShopCartConstants.Sequence.ORDER_ID_SEQ));
        newObj.setOrderTypeCode(MMConstants.OrderDocument.ORDER_TYPE_STOCK);
        newObj.setRecurringOrderInd(true);
        // newObj.setRecurringOrderId(Integer.valueOf(oldObj.getDocumentNumber()));
        newObj.setVendorHeaderGeneratedId(oldObj.getVendorHeaderGeneratedId());
        newObj.setVendorDetailAssignedId(oldObj.getVendorDetailAssignedId());
        newObj.setCustomerProfileId(oldObj.getCustomerProfileId());
        newObj.setOrderStatusCd(MMConstants.OrderStatus.INITIATED);
        newObj.setWarehouseCd(oldObj.getWarehouseCd());
        newObj.setCampusCd(oldObj.getCampusCd());
        newObj.setDeliveryBuildingCd(oldObj.getDeliveryBuildingCd());
        newObj.setDeliveryBuildingRmNbr(oldObj.getDeliveryBuildingRmNbr());
        newObj.setDeliveryDepartmentNm(oldObj.getDeliveryDepartmentNm());
        newObj.setDeliveryInstructionTxt(oldObj.getDeliveryInstructionTxt());
        newObj.setBillingAddressId(oldObj.getBillingAddressId());
        newObj.setShippingAddressId(oldObj.getShippingAddressId());
        newObj.setReqsId(oldObj.getReqsId());
        newObj.setArId(oldObj.getArId());
        newObj.setVendorNm(oldObj.getVendorNm());
        newObj.setVendorDetailAssignedId(oldObj.getVendorDetailAssignedId());
        newObj.setVendorHeaderGeneratedId(oldObj.getVendorHeaderGeneratedId());
        newObj.setAccounts(oldObj.getAccounts());
        newObj.setAgreementNbr(oldObj.getAgreementNbr());
        newObj.setCreationDate(MMUtil.getCurrentTimestamp());
        return newObj;
    }

    private OrderDetail getPopulatedOrderLine(OrderDocument odoc, ReturnDetail rdetail) {
        OrderDetail oldObj = rdetail.getOrderDetail();
        OrderDetail newObj = oldObj.clone();
        newObj.setOrderDetailId(null);
        newObj.setSalesInstanceId(null);
        newObj.setOrderItemQty(rdetail.getReturnQuantity());
        newObj.setOrderDocumentNbr(odoc.getDocumentNumber());
        newObj.setShoppingCartDetailId(null);
        newObj.setVersionNumber(0L);
        newObj.setExpectedDate(null);
        newObj.setObjectId(null);
        odoc.getOrderDetails().add(newObj);
        return newObj;
    }

    public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
        return true;
    }
}
