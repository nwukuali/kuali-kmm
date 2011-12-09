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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.StockCount;
import org.kuali.ext.mm.businessobject.StockCountMap;
import org.kuali.ext.mm.businessobject.WorksheetCounter;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.rice.kns.service.KualiConfigurationService;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;
import org.kuali.rice.kns.web.ui.ExtraButton;
import org.kuali.rice.kns.workflow.service.KualiWorkflowDocument;



public class CycleCountLookupForm extends KualiTransactionalDocumentFormBase {
    private static final long serialVersionUID = 8530973727816193473L;
    private WorksheetCounter newWorksheetCounter;
    private List<StockCount> stockCounts = new ArrayList<StockCount>();
    private boolean documentInMyActionList = false;

    public List<StockCount> getStockCounts() {
        return stockCounts;
    }

    public void setStockCounts(List<StockCount> stockCounts) {
        this.stockCounts = stockCounts;
    }

    public void addStockCount(StockCount astockCount) {
        this.stockCounts.add(astockCount);
    }

    public CycleCountLookupForm() {
        super();
        setDocument(new WorksheetCountDocument());
        setNewWorksheetCounter(new WorksheetCounter());
    }

    public int getDocSize() {
        if (this.getDocument() != null) {
            return ((WorksheetCountDocument) getDocument()).getWorksheetCounters().size();
        }
        return 0;
    }

    public WorksheetCountDocument getWorksheetCountDocument() {
        return (WorksheetCountDocument) getDocument();
    }

    public WorksheetCounter getNewWorksheetCounter() {
        return newWorksheetCounter;
    }

    public void setNewWorksheetCounter(WorksheetCounter newWorksheetCounter) {
        this.newWorksheetCounter = newWorksheetCounter;
    }

    public boolean isDocumentInMyActionList() {
        return documentInMyActionList;
    }

    public void setDocumentInMyActionList(boolean documentInMyActionList) {
        this.documentInMyActionList = documentInMyActionList;
    }

    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
    }

    @Override
    public List<ExtraButton> getExtraButtons() {
        extraButtons.clear();
        KualiWorkflowDocument workflowDoc = this.getWorksheetCountDocument().getDocumentHeader()
                .getWorkflowDocument();
        if (!workflowDoc.stateIsApproved() && !workflowDoc.stateIsDisapproved()
                && !workflowDoc.stateIsCanceled()) {
            extraButtons.add(createPrintButton());
        }
        return extraButtons;
    }

    private ExtraButton createPrintButton() {
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.reprintDocument");
        String externalImageURL = SpringContext.getBean(KualiConfigurationService.class)
                .getPropertyString(MMConstants.EXTERNALIZABLE_IMAGES_URL_KEY);
        printButton.setExtraButtonSource(externalImageURL + "buttonsmall_print.gif");
        printButton.setExtraButtonAltText("Reprint Worksheet");
        return printButton;
    }

    /**
     * @see org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        StockCountMap.reset();
    }

}
