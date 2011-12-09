/**
 * 
 */
package org.kuali.ext.mm.admin.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.OrderStatus;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.purap.document.FinancialRequisitionDocument;
import org.kuali.ext.mm.service.OrderService;
import org.kuali.rice.core.util.RiceConstants;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.web.struts.action.KualiAction;


/**
 * @author harsha07
 */
public class OrderCorrectionAction extends KualiAction {
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        OrderCorrectionForm correctionForm = (OrderCorrectionForm) form;
        String orderDocumentNumber = correctionForm.getOrderDocumentNumber();
        if (StringUtils.isNotBlank(orderDocumentNumber)) {
            populateOrderDocument(correctionForm, orderDocumentNumber);
        }
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    private void populateOrderDocument(OrderCorrectionForm correctionForm,
            String orderDocumentNumber) {
        OrderDocument orderDocument = SpringContext.getBean(BusinessObjectService.class)
                .findBySinglePrimaryKey(OrderDocument.class, orderDocumentNumber);
        if (ObjectUtils.isNull(orderDocument)) {
            GlobalVariables.getMessageMap().putError("orderDocumentNumber",
                    MMKeyConstants.ERROR_ORDER_CORRECTION_DOC);
            return;
        }
        if (ObjectUtils.isNotNull(orderDocument)) {
            correctionForm.setOrderDocument(orderDocument);
        }
    }

    public ActionForward clear(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        OrderCorrectionForm correctionForm = (OrderCorrectionForm) form;
        correctionForm.setOrderDocument(null);
        correctionForm.setOrderDocumentNumber(null);
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    public ActionForward submit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        OrderCorrectionForm correctionForm = (OrderCorrectionForm) form;
        OrderDocument docFromForm = correctionForm.getOrderDocument();
        if (docFromForm == null) {
            return mapping.findForward(RiceConstants.MAPPING_BASIC);
        }
        BusinessObjectService boService = SpringContext.getBean(BusinessObjectService.class);
        OrderDocument docFromDb = boService.findBySinglePrimaryKey(OrderDocument.class,
                correctionForm.getOrderDocumentNumber());
        if (docFromForm.getOrderStatusCd() == null
                || ObjectUtils.isNull(boService.findBySinglePrimaryKey(OrderStatus.class,
                        docFromForm.getOrderStatusCd().toUpperCase()))) {
            GlobalVariables.getMessageMap().putError("orderDocument.orderStatusCd",
                    MMKeyConstants.ERROR_ORDER_CORRECTION_STATUS);
            return mapping.findForward(RiceConstants.MAPPING_BASIC);
        }
        if (docFromForm.getReqsId() != null) {
            FinancialSystemAdaptorFactory factory = SpringContext
                    .getBean(FinancialSystemAdaptorFactory.class);
            if (!factory.checkAndErrorSystemAvailability()) {
                return mapping.findForward(RiceConstants.MAPPING_BASIC);
            }
            FinancialRequisitionDocument requisition = factory.getFinancialRequisitionService()
                    .getRequisitionById(docFromForm.getReqsId());
            if (requisition == null
                    || KEWConstants.ROUTE_HEADER_CANCEL_CD.equals(requisition
                            .getWorkflowStatusCode())
                    || KEWConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(requisition
                            .getWorkflowStatusCode())) {
                GlobalVariables.getMessageMap().putError("orderDocument.reqsId",
                        MMKeyConstants.ERROR_ORDER_CORRECTION_REQS);
                return mapping.findForward(RiceConstants.MAPPING_BASIC);
            }
        }
        SpringContext.getBean(OrderService.class).setOrderDocumentStatus(docFromDb,
                docFromForm.getOrderStatusCd().toUpperCase());
        docFromDb.setReqsId(docFromForm.getReqsId());
        boService.save(docFromDb);
        GlobalVariables.getMessageList().add(MMKeyConstants.INFO_ORDER_CORRECTION_SAVED);
        correctionForm.setReadOnly(true);
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return mapping.findForward(KNSConstants.MAPPING_PORTAL);
    }
}
