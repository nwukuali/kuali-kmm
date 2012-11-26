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

import org.apache.cxf.common.util.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.StockCount;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.StockItemLookupService;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.ext.mm.web.KualiPasswordHandler;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;
import org.kuali.rice.krad.rules.rule.event.ApproveDocumentEvent;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KualiRuleService;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * This is the action class for the WorksheetDoc.
 */
public class ReprintWorksheetAction extends KualiTransactionalDocumentActionBase {

    private static final org.apache.log4j.Logger loggerAc = org.apache.log4j.Logger
            .getLogger(KualiPasswordHandler.class);

    private StockItemLookupService stockItemLookupService(){
        return MMServiceLocator.getStockItemLookupService();
    }

    private BusinessObjectService businessService(){
        return MMServiceLocator.getBusinessObjectService();    
    }

    /**
     * Adds handling for worksheet Doc updates.
     * 
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.execute(mapping, form, request, response);
    }

    private List<StockCount> clearReprintedStockItem(WorksheetCountDocument wdoc) {
        List<StockCount> lisData = new ArrayList<StockCount>(0);
        for (StockCount scount : wdoc.getStockCounts()) {
            if (scount.isMarkedForReprint()) {
                lisData.add(getNewStockCount(scount));
                scount.setReprinted(true);
            }
        }

        return lisData;
    }

    private StockCount getNewStockCount(StockCount scount) {

        StockCount newCount = (StockCount) scount.clone();
        newCount.setStockCountId(null);
        newCount.setStockCountItemQty(null);
        newCount.setWorksheetCount(null);
        newCount.setWorksheetCountId(null);
        newCount.setVersionNumber(0L);
        newCount.setObjectId(null);
        return newCount;
    }

    @Override
    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        CycleCountLookupForm stkLookupForm = (CycleCountLookupForm) form;

        WorksheetCountDocument wdoc = (WorksheetCountDocument) stkLookupForm.getDocument();

        this.stockItemLookupService().updateCountWorksheetStatus(wdoc.getDocumentNumber(),
                MMConstants.WorksheetStatus.WORKSHEET_CANCELED);
        return super.cancel(mapping, form, request, response);
    }


    public List<StockCount> clearStockItemData(List<StockCount> lisCounts) {
        List<StockCount> newList = new ArrayList<StockCount>(0);
        for (StockCount scount : lisCounts) {
            scount.setStockCountItemQty(null);
            // scount.setWorksheetCntrPrncplId(null);
            scount.setWorksheetCount(null);
            scount.setWorksheetCountId(null);
            newList.add(scount);
        }
        return newList;
    }

    private boolean validateReprint(List<StockCount> lisData) {
        boolean result = true;
        int index = 0;

        for (StockCount data : lisData) {
            String dispString = "[" + index + "].";
            if (data.isMarkedForReprint() && !StringUtils.isEmpty(data.getStockTransReasonCd())) {
                result = false;
                GlobalVariables.getMessageMap().putError(
                        MMConstants.WorksheetCountDocument.STOCK_COUNTS + dispString
                                + MMConstants.WorksheetCountDocument.REPRINTED,
                        MMKeyConstants.WorksheetCountDocument.WORKSHEET_ITEMS_CANNOT_REPRINT);
            }

        }
        return result;
    }

    public ActionForward reprintDocument(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        CycleCountLookupForm stkLookupForm = (CycleCountLookupForm) form;
        List<StockCount> lisCounts = new ArrayList<StockCount>(0);
        List<List<StockCount>> lisData = new ArrayList<List<StockCount>>(0);
        List<WorksheetCountDocument> lisDocs = new ArrayList<WorksheetCountDocument>(0);
        WorksheetCountDocument wdoc = (WorksheetCountDocument) stkLookupForm.getDocument();

        if (!this.validateReprint(wdoc.getStockCounts())) {
            return mapping.findForward(MMConstants.MAPPING_BASIC);
        }

        if (!MMUtil.isCollectionEmpty(wdoc.getStocksToBeReprinted())) {

            lisCounts = clearReprintedStockItem(wdoc);

            if (!MMUtil.isCollectionEmpty(lisCounts)) {
                lisData.add(lisCounts);
                lisDocs = this.stockItemLookupService().createNewWorksheetDocuments(wdoc
                        .getWarehouseCd(), null, 1, lisData, wdoc, wdoc.isFullInventory());

                setWorksheetAttributes(lisDocs, wdoc);

                this.businessService().save(wdoc);

                flushReportPDFFiles(lisDocs.get(0), request);
                return mapping.findForward(MMConstants.REPRINT);

            }


        }
        return mapping.findForward(MMConstants.MAPPING_BASIC);

    }

    /**
     * returns the index of the item whose item count is mismatching
     * 
     * @param wdoc
     * @return
     */
    public int getIndexForNonMatchingItem(WorksheetCountDocument wdoc) {
        int index = 0;

        if (MMUtil.isCollectionEmpty(wdoc.getStockCounts()))
            return index;

        for (StockCount st : wdoc.getStockCounts()) {
            if (!st.isItemCountMatching() && !st.isReprinted())
                break;
            index++;
        }
        return index;
    }

    @Override
    public ActionForward approve(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        loggerAc.debug("Inside execute method of StockItemLookup Action ");
        CycleCountLookupForm stkLookupForm = (CycleCountLookupForm) form;
        WorksheetCountDocument wdoc = (WorksheetCountDocument) stkLookupForm.getDocument();

        if (!this.validateReprint(wdoc.getStockCounts())) {
            return mapping.findForward(MMConstants.MAPPING_BASIC);
        }

        boolean rulePassed = SpringContext.getBean(KualiRuleService.class).applyRules(
                new ApproveDocumentEvent("document", wdoc));
        if (!rulePassed) {
            return mapping.findForward(MMConstants.MAPPING_BASIC);
        }

        List<StockCount> result = wdoc.getStocksToBeReprinted();
        if (!MMUtil.isCollectionEmpty(result)) {
            // populate error messages
            // and
            GlobalVariables.getMessageMap().putError(
                    MMConstants.WorksheetCountDocument.STOCK_COUNTS,
                    MMKeyConstants.WorksheetCountDocument.WORKSHEET_WITH_REPRINTED_ITEMS);

            return mapping.findForward(MMConstants.MAPPING_BASIC);
        }

        if (!wdoc.isWorksheetItemsMatching()) {
            int index = getIndexForNonMatchingItem(wdoc);
            if (wdoc.isAnyWorksheetItemReprinted())
                this.businessService().save(wdoc);

            GlobalVariables.getMessageMap().putError(
                    MMConstants.WorksheetCountDocument.STOCK_COUNTS + "[" + index + "]."
                            + MMConstants.WorksheetCountDocument.STOCK_TRANS_REASON_CODE,
                    MMKeyConstants.WorksheetCountDocument.WORKSHEET_NOT_APPROVED);

            return mapping.findForward(MMConstants.MAPPING_BASIC);

        }

        return super.approve(mapping, form, request, response);

    }

    private void setWorksheetAttributes(List<WorksheetCountDocument> lisDocs,
            WorksheetCountDocument wdoc) {
        for (WorksheetCountDocument doc : lisDocs) {
            doc.setWorksheetCnt(wdoc.getWorksheetCnt() + 1);
            doc.setWorksheetStatusCode(MMConstants.WorksheetStatus.WORKSHEET_REPRINTED);
            doc.setParentDocumentNumber(wdoc.getDocumentNumber());
            doc.setParentDocument(wdoc);
        }

        for (StockCount scount : wdoc.getStockCounts()) {
            if (scount.isReprinted())
                scount
                        .setStockTransReasonCd(MMConstants.WorksheetCountDocument.WORKSHEET_STOCK_ITEM_REPRINTED);
        }
    }

    private void flushReportPDFFiles(WorksheetCountDocument wdoc, HttpServletRequest request) {
        // this.stockItemLookupService.updateCountWorksheetStatus(wdoc.getDocumentNumber(),
        // MMConstants.WorksheetStatus.WORKSHEET_REPRINTED);
        HashMap<String, String> params = new HashMap<String, String>();

        if (ObjectUtils.isNotNull(wdoc)) {
            request.setAttribute("WorksheetDocNumber", wdoc.getDocumentNumber());
            params.put("WorksheetDocNumber", wdoc.getDocumentNumber());
        }

        params.put("PrintAction", MMConstants.PRINT);
        String basePath = getApplicationBaseUrl();// getBasePath(request);
        String methodToCallPrintPDF = "printStatementPDF";
        String methodToCallStart = "start";
        String printPDFUrl = getUrlForPrintStatement(basePath, methodToCallPrintPDF, params);
        String displayTabbedPageUrl = getUrlForStartMethod(basePath, methodToCallStart, params);


        request.setAttribute("printPDFUrl", printPDFUrl);
        request.setAttribute("displayTabbedPageUrl", displayTabbedPageUrl);
        request.setAttribute("PrintAction", MMConstants.REPRINT);
        request.setAttribute("printLabel", "Print Statement");

    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // stkLookupForm.registerEditableProperty("methodToCall");
        return mapping.findForward(MMConstants.MAPPING_BACK);
    }


    /**
     * returns the URL for requested page
     * 
     * @param basePath
     * @param methodToCall
     * @param params
     * @return
     */
    private String getUrlForPrintStatement(String basePath, String methodToCall,
            Map<String, String> params) {

        StringBuffer result = new StringBuffer(basePath);
        result.append("/initiateWorksheetDoc.do?methodToCall=").append(methodToCall);
        Set<String> keys = params.keySet();
        for (String key : keys) {
            result.append("&").append(key).append("=").append(params.get(key));
        }

        return result.toString();
    }

    private String getUrlForStartMethod(String basePath, String methodToCall,
            Map<String, String> params) {

        return "portal.do?selectedTab=main";
    }


}
