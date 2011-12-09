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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.StockCount;
import org.kuali.ext.mm.businessobject.WorksheetCounter;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.StockItemLookupService;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.ext.mm.web.KualiPasswordHandler;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;


/**
 * This is the action class for the WorksheetDoc.
 */
public class CycleCountAction extends KualiTransactionalDocumentActionBase {
    private org.apache.log4j.Logger loggerAc = org.apache.log4j.Logger
            .getLogger(KualiPasswordHandler.class);
    private StockItemLookupService stockItemLookupService(){
        return MMServiceLocator.getStockItemLookupService();
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
        loggerAc.debug("Inside execute method of StockItemLookup Action");
        CycleCountLookupForm stkLookupForm = (CycleCountLookupForm) form;
        ActionForward eforward = null;

        eforward = super.execute(mapping, stkLookupForm, request, response);
        WorksheetCountDocument workDoc = (WorksheetCountDocument) stkLookupForm.getDocument();
        boolean isDocInList = isDocInMyActionList(workDoc.getDocumentNumber());
        if (isDocToBeReviewed(workDoc) && isDocInList) {
            // if(isDocToBeReviewed(workDoc) ){
            eforward = mapping.findForward(MMConstants.MAPPING_REPRINT);
        }
        stkLookupForm.setDocumentInMyActionList(MMConstants.WorksheetStatus.WORKSHEET_PRINTED
                .equals(workDoc.getWorksheetStatusCode())
                || isDocInList);

        // stkLookupForm.setDocumentInMyActionList(isDocInList);
        loggerAc.debug("execute method of StockItemLookup Action executed successfully");
        return eforward;
    }

    private boolean isDocToBeReviewed(WorksheetCountDocument workDoc) {
        return workDoc != null
                && workDoc.getWorksheetStatusCode() != null
                && !(workDoc.getWorksheetStatusCode().equals(
                        MMConstants.WorksheetStatus.WORKSHEET_REPRINTED) || workDoc
                        .getWorksheetStatusCode().equals(
                                MMConstants.WorksheetStatus.WORKSHEET_PRINTED));
    }

    private boolean isDocInMyActionList(String docNumber) {

        Person luser = GlobalVariables.getUserSession().getPerson();

        String principalId = luser.getPrincipalId().trim();

        return MMServiceLocator.getMMDocumentUtilService().isDocInMyActionList(docNumber,
                principalId);
    }

    


    /**
     * Adds a worksheetCounter instance created from the current "new workSheetCounter" line to the document
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward addWorkSheetCounter(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        loggerAc.debug("Inside add Worksheet Counter method");
        CycleCountLookupForm ccrForm = (CycleCountLookupForm) form;
        WorksheetCountDocument worksheetCount = (WorksheetCountDocument) ccrForm.getDocument();

        WorksheetCounter newsheet = ccrForm.getNewWorksheetCounter();

        if (validateNewCounter(newsheet, worksheetCount)) {
            worksheetCount.addWorksheetCounter(newsheet);
            ccrForm.setNewWorksheetCounter(new WorksheetCounter());
        }
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    private boolean validateNewCounter(WorksheetCounter newsheet,
            WorksheetCountDocument worksheetCount) {

        boolean isCounterNotEmpty = true;
        boolean isDateValid = true;
        boolean isCounterSame = false;
        boolean isEnteredCounterValid = true;
        String prinName = null;

        if (StringUtils.isEmpty(newsheet.getWorksheetPrncplId())
                && StringUtils.isNotEmpty(newsheet.getWorksheetPrncplName())) {
            prinName = newsheet.getWorksheetPrncplName();
            Person person = this.stockItemLookupService().getPersonWithName(prinName);
            String prinId = person != null ? person.getPrincipalId() : null;
            isEnteredCounterValid = StringUtils.isNotEmpty(prinId);
            newsheet.setWorksheetPrncplId(prinId);
        }

        isCounterNotEmpty = StringUtils.isNotEmpty(newsheet.getWorksheetPrncplId());
        GlobalVariables.getMessageMap().addToErrorPath(
                MMConstants.WorksheetCountDocument.NEW_WORKSHEET_COUNTER);

        if (!isEnteredCounterValid) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.WorksheetCountDocument.WORKSHEET_COUNTERS_PRINCIPAL_ID,
                    MMKeyConstants.MESSAGE_WORKSHEET_COUNTER_INVALID, prinName);
        }

        if (isEnteredCounterValid && !isCounterNotEmpty) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.WorksheetCountDocument.WORKSHEET_COUNTERS_PRINCIPAL_ID,
                    MMKeyConstants.MESSAGE_WORKSHEET_COUNTER_PRINCIPAL_NULL);
        }

        isDateValid = ObjectUtils.isNotNull(newsheet.getLastUpdateDate());
        if (!isDateValid) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.WorksheetCountDocument.WORKSHEET_COUNTERS_COUNT_DATE,
                    MMKeyConstants.MESSAGE_WORKSHEET_COUNTER_DATE_NULL);
        }

        String newCountId = newsheet.getWorksheetPrncplId();
        for (WorksheetCounter counter : worksheetCount.getWorksheetCounters()) {
            String countId = counter.getWorksheetPrncplId();

            if (isCounterNotEmpty) {
                isCounterSame = countId.trim().equals(newCountId.trim());
                if (isCounterSame) {
                    GlobalVariables.getMessageMap().putError(
                            MMConstants.WorksheetCountDocument.WORKSHEET_COUNTERS_PRINCIPAL_ID,
                            MMKeyConstants.MESSAGE_WORKSHEET_COUNTER_PRINCIPAL_ALREADY_EXISTS,
                            newCountId);
                    break;
                }
            }
        }
        return !isCounterSame & isCounterNotEmpty & isDateValid;

    }

    /**
     * Deletes the selected worksheet Counter (line) from the document
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward deleteWorkSheetCounter(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        loggerAc.debug("Inside Worksheet Counter delete method");
        CycleCountLookupForm ccrForm = (CycleCountLookupForm) form;
        WorksheetCountDocument worksheetCount = (WorksheetCountDocument) ccrForm.getDocument();
        int index = getSelectedLine(request);
        worksheetCount.getWorksheetCounters().get(index).setWorksheetCountId(null);
        ((WorksheetCountDocument) ccrForm.getDocument()).getWorksheetCounters().remove(index);
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    /**
     * setstimes counted for each item.If the worksheet status is entered then all item's times counted attribute is incremented.
     * 
     * @param wdoc
     */
    private void setItemTimesCounted(WorksheetCountDocument wdoc) {

        if (wdoc.getWorksheetStatusCode() != null) {
            if (wdoc.getWorksheetStatusCode().equals(MMConstants.WorksheetStatus.WORKSHEET_PRINTED)) {
                for (StockCount scount : wdoc.getStockCounts()) {
                    scount.incrementTimesCounted();
                }
            }
            else if (wdoc.getWorksheetStatusCode().equals(
                    MMConstants.WorksheetStatus.WORKSHEET_REPRINTED)) {
                for (StockCount scount : wdoc.getStockCounts()) {
                    if (scount.isReprinted())
                        scount.incrementTimesCounted();
                }
            }
        }
    }

    private boolean validateCounters(WorksheetCountDocument doc) {
        List<WorksheetCounter> lisCounters = doc.getWorksheetCounters();
        if (MMUtil.isCollectionEmpty(lisCounters)) {
            GlobalVariables.getMessageMap().addToErrorPath(
                    MMConstants.WorksheetCountDocument.NEW_WORKSHEET_COUNTER);
            GlobalVariables.getMessageMap().putError(MMConstants.GLOBAL_ERRORS,
                    MMKeyConstants.MESSAGE_COUNTERS_NOT_FOUND);
            return false;
        }

        return true;
    }

    private boolean validateBeforeSubmit(WorksheetCountDocument doc) {
        int index = 0;
        for (StockCount scount : doc.getStockCounts()) {
            if (ObjectUtils.isNull(scount.getStockCountItemQty())) {
                String dispString = "[" + index + "].";
                GlobalVariables.getMessageMap().putError(
                        MMConstants.WorksheetCountDocument.STOCK_COUNTS + dispString
                                + MMConstants.WorksheetCountDocument.STOCK_ITEM_QTY,
                        MMKeyConstants.MESSAGE_STOCK_ITEMCOUNT_NOT_ENTERED);
                return false;
            }
            index++;
        }

        return validateCounters(doc);
    }

    @Override
    public ActionForward route(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        WorksheetCountDocument worksheetCount = (WorksheetCountDocument) ((CycleCountLookupForm) form)
                .getDocument();
        ActionForward ad = null;
        if (validateBeforeSubmit(worksheetCount)) {
            setItemTimesCounted((WorksheetCountDocument) ((CycleCountLookupForm) form)
                    .getDocument());
            if (worksheetCount.isWorksheetItemsMatching()) {
                worksheetCount.setWorksheetStatusCode(MMConstants.WorksheetStatus.WORKSHEET_CLOSED);
            }
            else {
                worksheetCount
                        .setWorksheetStatusCode(MMConstants.WorksheetStatus.WORKSHEET_ENTERED);
            }

            ad = super.route(mapping, form, request, response);

            return ad;
        }
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
}
