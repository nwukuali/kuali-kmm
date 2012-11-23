/*
 * Copyright 2006-2007 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.document.web.struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.businessobject.VendorReturnDoc;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.document.validation.impl.ReturnOrderValidator;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.ReturnOrderService;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * This is the action class for the WorksheetDoc.
 */
public class ReturnOrderAction extends RentalTrackingActionBase {

    public ReturnOrderAction() {
        super();
    }

    private org.apache.log4j.Logger loggerAc = org.apache.log4j.Logger
            .getLogger(ReturnOrderAction.class);

    private ReturnOrderService returnOrderService(){
        return MMServiceLocator.getReturnOrderService();    
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionForward ad = super.execute(mapping, form, request, response);

        ReturnOrderForm returnOrderForm = (ReturnOrderForm) form;
        String docNumber = returnOrderForm.getReturnDoc().getDocumentNumber();
        Person luser = GlobalVariables.getUserSession().getPerson();
        String principalId = luser.getPrincipalId().trim();
        returnOrderForm.setDocInMyActionList(MMServiceLocator.getMMDocumentUtilService()
                .isDocInMyActionList(docNumber, principalId));

        return ad;
    }

    // protected boolean canSave(ActionForm form){
    // return true;
    // }

    public ActionForward splitReturnLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReturnOrderForm returnOrderForm = (ReturnOrderForm) form;
        int index = getSelectedLine(request);
        ReturnDocument rdoc = returnOrderForm.getReturnDoc();
        ReturnDetail selDetail = rdoc.getReturnDetails().get(index);
        ReturnDetail newLine = nullifySelectedLine(selDetail.clone());
        rdoc.getReturnDetails().add(newLine);
        return mapping.findForward(MMConstants.MAPPING_BASIC);

    }

    public ActionForward removeReturnLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReturnOrderForm returnOrderForm = (ReturnOrderForm) form;
        int index = getSelectedLine(request);
        ReturnDocument rdoc = returnOrderForm.getReturnDoc();
        ReturnDetail selDetail = rdoc.getReturnDetails().remove(index);
        selDetail.setReturnDocNumber(null);
        selDetail.setReturnDoc(null);
        return mapping.findForward(MMConstants.MAPPING_BASIC);

    }

    public ActionForward addReturnLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReturnOrderForm returnOrderForm = (ReturnOrderForm) form;
        ReturnDocument rdoc = returnOrderForm.getReturnDoc();
        ReturnDetail newLine = returnOrderForm.getNewReturnDetail();
        if (validateNewReturn(newLine)) {
            newLine.setReturnDoc(rdoc);
            newLine.setItemReturned(true);
            newLine.setReturnDocNumber(rdoc.getDocumentNumber());
            rdoc.addNewReturnDetail(newLine);
            returnOrderForm.addNewReturn();
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);

    }

    private boolean validateNewReturn(ReturnDetail odetail) {
        return ReturnOrderValidator.validateNewReturnDetail(odetail);
    }

    public ActionForward deleteReturnLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReturnOrderForm returnOrderForm = (ReturnOrderForm) form;
        ReturnDocument rdoc = returnOrderForm.getReturnDoc();
        Integer index = getSelectedLine(request);
        ReturnDetail rdetail = rdoc.getNewReturnDetails().get(index);
        rdoc.getNewReturnDetails().remove(index);
        if(rdetail != null ){
            rdetail.setReturnDoc(null);
            rdetail.setReturnDocNumber(null);
            rdetail = null;
        }
        rdoc.getNewReturnDetails().set(index, null);
        return mapping.findForward(MMConstants.MAPPING_BASIC);

    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("show");
    }

    private String getUrlForPrintStatement(String basePath, String methodToCall, Map<String, String> params) {

        StringBuffer result = new StringBuffer(basePath);
        result.append("/returnOrder.do?methodToCall=").append(methodToCall);
        Set<String> keys = params.keySet();
        for(String key : keys) {
            result.append("&").append(key).append("=").append(params.get(key));
        }

        return result.toString();
    }

    @Override
    public ActionForward approve(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        ReturnOrderForm returnOrderForm = (ReturnOrderForm) form;
        for(ReturnDetail detail : returnOrderForm.getReturnDoc().getReturnDetails()) {
            detail.setDepartmentCreditInd(MMConstants.OptionFinderParms.YES_OR_NO_OPTION_VALUE_YES_OR_TRUE
                    .equals(detail.getDepartmentCreditStringInd()));
        }
        
        ActionForward aforward = super.approve(mapping, form, request, response);        

        ReturnDocument document = returnOrderForm.getReturnDoc();
        // new code
        Map<String, String> params = new HashMap<String, String>();
        String documentId = document.getDocumentNumber();
        params.put("documentId", documentId);
        request.setAttribute("documentId", documentId);
//        params.put("numOfCopies", String.valueOf(numOfCopies));
        String basePath = getApplicationBaseUrl();//(request);

        String methodToCallPrintPDF = "display";
        String methodToCallStart = "start";
        String printPDFUrl = getUrlForPrintStatement(basePath, methodToCallPrintPDF, params);
        String displayTabbedPageUrl = getUrlForPrintStatement(basePath, methodToCallStart, params);

        request.setAttribute("PrintAction", MMConstants.PRINT);
        request.setAttribute("printLabel", "Print RTV");
        request.setAttribute("printPDFUrl", printPDFUrl);
        request.setAttribute("displayTabbedPageUrl", displayTabbedPageUrl);
        return mapping.findForward(MMConstants.PRINT);

        //new code ends here
        // }
//
//        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    private ReturnDetail nullifySelectedLine(ReturnDetail rdetail) {
        rdetail.setReturnDetailId(null);
        rdetail.setActionCd(null);
        rdetail.setDispostitionCode(null);
        rdetail.setVendorCreditInd(false);
        rdetail.setVendorReshipInd(false);
        rdetail.setVendorDispositionInd(false);
        return rdetail;
    }

    public void display(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ReturnOrderForm returnOrderForm = (ReturnOrderForm) form;
        ReturnDocument document = returnOrderForm.getReturnDoc();

        StringBuffer sbContentDispValue = new StringBuffer();
        String useJavascript = request.getParameter("useJavascript");
        ByteArrayOutputStream baos = null;
        if (useJavascript == null || useJavascript.equalsIgnoreCase("false")) {
            sbContentDispValue.append("attachment");
        }
        else {
            sbContentDispValue.append("inline");
        }

        sbContentDispValue.append("; filename=");
        sbContentDispValue.append(MMUtil.getFileNameForPackingList());

        String contentDisposition = sbContentDispValue.toString();
//        if(ObjectUtils.isNull(document)){
            String documentId = request.getParameter("documentId");
            document = (ReturnDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);
//        }
        baos = MMServiceLocator.getReturnOrderService().generatePDFAfterApproval(document);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", contentDisposition);
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentLength(baos.size());

        // write to output
        ServletOutputStream sos = response.getOutputStream();
        baos.writeTo(sos);
        sos.flush();
        baos.close();
        sos.close();
    }

    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ReturnOrderForm rform = (ReturnOrderForm) form;

        if (rform.isVendorReturnForm()) {
            VendorReturnOrderForm vform = (VendorReturnOrderForm) form;

            if (vform.getDocument() == null) {
                vform.setDocument(new VendorReturnDoc());
                vform.setDocTypeName(MMConstants.CHECKIN_VENDOR_RETURNDOC_TYPE);
            }

        }
        else {

            if (rform.getDocument() == null) {
                rform.setDocument(new ReturnDocument());
                rform.setDocTypeName(MMConstants.CHECKIN_RETURNDOC_TYPE);
            }
        }

        ActionForward ad = super.docHandler(mapping, rform, request, response);

        ReturnOrderForm returnOrderform = (ReturnOrderForm) form;
        ReturnDocument returnDoc = returnOrderform.getReturnDoc();
        String orderDocNumber = request.getParameter(MMConstants.CheckinDocument.ORDER_DOC_NUMBER);
        String orderLineNumber = request.getParameter(MMConstants.CheckinDocument.ORDER_DETAIL_ID);

        if (MMUtil.isCollectionEmpty(returnDoc.getReturnDetails()))
            returnDoc = returnOrderService().createReturnDocItems(returnDoc, orderDocNumber,
                    orderLineNumber);

        createRentals(returnOrderform);

        return ad;
    }

    @Override
    public ActionForward performRouteReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward(MMConstants.MAPPING_BACK);
    }


    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ActionForward ad = super.save(mapping, form, request, response);
        return ad;
    }

}
