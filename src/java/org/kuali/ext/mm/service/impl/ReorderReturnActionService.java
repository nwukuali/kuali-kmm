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
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ReorderReturnActionService implements IReturnCommand {


    public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {

        if (StringUtils.isEmpty(rdoc.getOrderDocumentNumber()))
            return;

        OrderDocument odoc = null;
        // if(!rdetail.requiresDummyOrder())
        if (ObjectUtils.isNull(rdoc.getReorderDocument()) || rdetail.requiresDummyOrder()) {

            UserSession curSession = GlobalVariables.getUserSession();
            String name = ObjectUtils.isNull(getPriorApprovers(rdoc.getDocumentHeader().getWorkflowDocument())
                    ) ? KimApiServiceLocator.getIdentityService()
                    .getPrincipal(
                            rdoc.getDocumentHeader().getWorkflowDocument()
                                    .getInitiatorPrincipalId()).getPrincipalName() :
							getPriorApprovers(rdoc.getDocumentHeader().getWorkflowDocument()).iterator()
                    .next().getPrincipalName();
            GlobalVariables.setUserSession(new UserSession(name));

            odoc = (OrderDocument) KRADServiceLocatorWeb.getDocumentService().getNewDocument(
                    MMConstants.OrderDocument.STORES_ORDER_DOCUMENT);
            OrderDocument ddd = rdoc.getOrderDocument();
            if (ddd == null) {

                ddd = (OrderDocument) SpringContext.getBean(DocumentService.class).getByDocumentHeaderId(
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
        newObj.setOrderId(KRADServiceLocator.getSequenceAccessorService()
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

			private Set<Person> getPriorApprovers(WorkflowDocument workflowDocument) {
        PersonService personService = KimApiServiceLocator.getPersonService();
        List<ActionTaken> actionsTaken = workflowDocument.getActionsTaken();
        Set<String> principalIds = new HashSet<String>();
        Set<Person> persons = new HashSet<Person>();

        for (ActionTaken actionTaken : actionsTaken) {
            if (KewApiConstants.ACTION_TAKEN_APPROVED_CD.equals(actionTaken.getActionTaken())) {
                String principalId = actionTaken.getPrincipalId();
                if (!principalIds.contains(principalId)) {
                    principalIds.add(principalId);
                    persons.add(personService.getPerson(principalId));
                }
            }
        }
        return persons;
    }
}
