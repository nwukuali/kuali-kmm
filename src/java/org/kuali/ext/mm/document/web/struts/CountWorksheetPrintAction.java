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

import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.SimpleBookmark;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.StockCount;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.Zone;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.ext.mm.service.CountWorksheetReportService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.StockItemLookupService;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.coreservice.framework.CoreFrameworkServiceLocator;
import org.kuali.rice.kns.web.struts.action.KualiAction;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;



/**
 * This is the action class for the WorksheetDoc.
 */
public class CountWorksheetPrintAction extends KualiAction {

    private static final org.apache.log4j.Logger loggerAc = org.apache.log4j.Logger
            .getLogger(CountWorksheetPrintAction.class);

    private StockItemLookupService stockItemLookupService(){
        return MMServiceLocator.getStockItemLookupService();
    }

    private CountWorksheetReportService repService(){
        return MMServiceLocator.getCountWorksheetReportService();
    }


    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.execute(mapping, form, request, response);
    }

    public ActionForward createWorksheetDocument(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<WorksheetCountDocument> lisWorksheetDocs = null;

        CountWorksheetPrintForm countWorksheetPrintForm = (CountWorksheetPrintForm) form;
        String warehouseCd = (countWorksheetPrintForm).getWarehouseCode();
        String zoneCd = (countWorksheetPrintForm).getZoneCd();
        String countFrequency = countWorksheetPrintForm.getCountFreq();
        int numOfCounters = countWorksheetPrintForm.getCountersIntVal();
        int numOfCopies = countWorksheetPrintForm.getCopiesIntVal();
        boolean isQtyGreaterThanZero = countWorksheetPrintForm.isQuantityOnHandLessThanZero();
        boolean fullInventory = false;

        if (!this.validate(warehouseCd, zoneCd, countFrequency, numOfCounters, numOfCopies,
                countWorksheetPrintForm, mapping))
            return mapping.findForward(MMConstants.MAPPING_BASIC);

        if ("yes".equals(countWorksheetPrintForm.getFullInventory())) {
            countFrequency = "";
            fullInventory = true;
            // insert all the records specific to this selection
            stockItemLookupService().createNewStockCounts(warehouseCd, zoneCd, isQtyGreaterThanZero);

        }
        lisWorksheetDocs = stockItemLookupService().getNewStockCounts(warehouseCd, zoneCd,
                countFrequency, numOfCounters, isQtyGreaterThanZero, fullInventory);


        // if map is empty then display the page again
        if (MMUtil.isCollectionEmpty(lisWorksheetDocs)) {
            populateErrorMessages(warehouseCd, zoneCd);
            countWorksheetPrintForm.setDataValid(false);
            return mapping.findForward(MMConstants.MAPPING_BASIC);
        }

        String docNbrs = getDocNumbers(lisWorksheetDocs);

        int numCount = lisWorksheetDocs != null ? lisWorksheetDocs.size() : 0;

        Map<String, String> params = new HashMap<String, String>();
        if (!StringUtils.isBlank(warehouseCd)) {
            params.put("warehouseCode", warehouseCd);
            request.setAttribute("warehouseCode", warehouseCd);
        }
        if (!StringUtils.isBlank(zoneCd)) {
            params.put("zoneCd", zoneCd);
            request.setAttribute("zoneCd", zoneCd);
        }

        if (!StringUtils.isEmpty(docNbrs)) {
            params.put("WorksheetDocNumber", docNbrs);
            request.setAttribute("WorksheetDocNumber", docNbrs);
        }

        params.put("numOfCounters", String.valueOf(numCount));
        params.put("numOfCopies", String.valueOf(numOfCopies));
        String basePath = getApplicationBaseUrl();
        String methodToCallPrintPDF = "printStatementPDF";
        String methodToCallStart = "start";
        String printPDFUrl = getUrlForPrintStatement(basePath, methodToCallPrintPDF, params);
        String displayTabbedPageUrl = getUrlForPrintStatement(basePath, methodToCallStart, params);

        for (String docNbr : docNbrs.split(","))
            this.stockItemLookupService().updateCountWorksheetStatus(docNbr,
                    MMConstants.WorksheetStatus.WORKSHEET_PRINTED);

        request.setAttribute("PrintAction", MMConstants.PRINT);
        request.setAttribute("numOfCounters", numCount);
        request.setAttribute("numOfCopies", numOfCopies);
        request.setAttribute("printLabel", "Customer Statement");
        request.setAttribute("printPDFUrl", printPDFUrl);
        request.setAttribute("displayTabbedPageUrl", displayTabbedPageUrl);
        return mapping.findForward(MMConstants.PRINT);

    }

    private void populateErrorMessages(String warehouseCd, String zoneCd) {
        if (!StringUtils.isEmpty(warehouseCd) && StringUtils.isEmpty(zoneCd)) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.WorksheetCountDocument.WAREHOUSE_CODE,
                    MMKeyConstants.MESSAGE_STOCK_ITEMS_NOT_FOUND_FOR_WAREHOUSE, warehouseCd);
        }
        else if (StringUtils.isEmpty(warehouseCd) && !StringUtils.isEmpty(zoneCd)) {
            GlobalVariables.getMessageMap().putError(MMConstants.WorksheetCountDocument.ZONE_CODE,
                    MMKeyConstants.MESSAGE_STOCK_ITEMS_NOT_FOUND_FOR_ZONE, zoneCd);

        }
        else {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.WorksheetCountDocument.WAREHOUSE_CODE,
                    MMKeyConstants.MESSAGE_STOCK_ITEMS_NOT_FOUND_FOR_ZONE_WAREHOUSE, warehouseCd,
                    zoneCd);
        }
    }

    private String getDocNumbers(List<WorksheetCountDocument> lisDocs) {

        if (MMUtil.isCollectionEmpty(lisDocs))
            return null;

        StringBuffer sb = new StringBuffer();

        for (WorksheetCountDocument wdoc : lisDocs) {
            sb.append(wdoc.getDocumentNumber()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private void removeReportFiles(List<File> fileList) {
        for (File file : fileList) {
            if (file.exists())
                file.delete();
        }

    }

    public ActionForward printStatementPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        long startTime = System.currentTimeMillis();

        List<WorksheetCountDocument> workCountDocs = new ArrayList<WorksheetCountDocument>();
        String docNumber = request.getParameter("WorksheetDocNumber");
        String printAction = request.getParameter("PrintAction");
        String passedStatus = request.getParameter(MMConstants.WORKSHEET_DOC_STATUS);
        boolean isReprintRequested = printAction != null
                && printAction.equalsIgnoreCase(MMConstants.REPRINT)
                || (passedStatus != null && passedStatus.trim().equalsIgnoreCase(
                        MMConstants.WorksheetStatus.WORKSHEET_REPRINTED));
        if (StringUtils.isEmpty(docNumber) && !isReprintRequested)
            return mapping.findForward(MMConstants.MAPPING_BASIC);
        int numOfCopies = request.getParameter("numOfCopies") != null ? Integer.valueOf(
                request.getParameter("numOfCopies")).intValue() : 0;
        if (!isReprintRequested) {
            List<String> docIds = Arrays.asList(docNumber.split(","));
            workCountDocs = stockItemLookupService().getWorksheetCountDocForIDs(docIds);
        }
        else {
            WorksheetCountDocument worksheetDoc = stockItemLookupService()
                    .getWorkSheetCountDocData(docNumber);
            List<StockCount> remList = new ArrayList<StockCount>();
            for (StockCount scount : worksheetDoc.getStockCounts()) {
                if (!scount.isReprinted())
                    remList.add(scount);
            }

            for (StockCount scount : remList) {
                worksheetDoc.getStockCounts().remove(scount);
            }
            workCountDocs.add(worksheetDoc);
        }
        setPickListIndicator(workCountDocs);
        if (MMUtil.isCollectionEmpty(workCountDocs))
            return mapping.findForward(MMConstants.MAPPING_BASIC);

        List<File> fileList = new ArrayList<File>();
        try {
            for (WorksheetCountDocument wdoc : workCountDocs) {
                List<StockCount> wlist = wdoc.getStockCounts();
                if (!ObjectUtils.isNull(wlist)) {
                    fileList.add(repService().generateCountWorksheetReportPDF(wdoc));
                }
            }

            if (MMUtil.isCollectionEmpty(fileList))
                return mapping.findForward(MMConstants.MAPPING_BASIC);
            if (numOfCopies > 0) {
                createFileCopies(fileList, numOfCopies);
            }
            this.combineAndFlushReportPDFFiles(fileList, request, response);
        }
        finally {
            this.removeReportFiles(fileList);
        }
        long endTime = System.currentTimeMillis();
        loggerAc.debug("Time taken for print statmeent PDF in action " + (endTime - startTime));
        return null;
    }

    private void setPickListIndicator(List<WorksheetCountDocument> lisDocs) {
        long startTime = System.currentTimeMillis();
        for (WorksheetCountDocument wdoc : lisDocs) {
            List<String> lisIDs = this.stockItemLookupService().getStockIDsForPickList(wdoc
                    .getStockIds());
            if (MMUtil.isCollectionEmpty(lisIDs))
                continue;
            for (StockCount scount : wdoc.getStockCounts()) {
                String stockId = scount.getStockId();
                if (lisIDs.contains(stockId))
                    scount.setPickListIndicator("*");
                scount.setItemPicked(true);
            }
        }
        long endTime = System.currentTimeMillis();
        loggerAc.debug("Time taken for set picklist indicator in action " + (endTime - startTime));
    }


    private void combineAndFlushReportPDFFiles(List<File> fileList, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        long startTime = System.currentTimeMillis();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ArrayList master = new ArrayList();
        int pageOffset = 0;
        int f = 0;
        PdfCopy writer = null;
        com.lowagie.text.Document document = null;
        for (File file : fileList) {
            // we create a reader for a certain document
            String reportName = file.getAbsolutePath();
            PdfReader reader = new PdfReader(reportName);
            reader.consolidateNamedDestinations();
            // we retrieve the total number of pages
            int n = reader.getNumberOfPages();
            List bookmarks = SimpleBookmark.getBookmark(reader);
            if (bookmarks != null) {
                if (pageOffset != 0) {
                    SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
                }
                master.addAll(bookmarks);
            }
            pageOffset += n;

            if (f == 0) {
                // step 1: creation of a document-object
                document = new com.lowagie.text.Document(reader.getPageSizeWithRotation(1));
                // step 2: we create a writer that listens to the document
                writer = new PdfCopy(document, baos);
                // step 3: we open the document
                document.open();
            }
            // step 4: we add content
            PdfImportedPage page;
            for (int i = 0; i < n;) {
                ++i;
                page = writer.getImportedPage(reader, i);
                writer.addPage(page);
            }
            writer.freeReader(reader);
            f++;
        }

        if (!master.isEmpty())
            writer.setOutlines(master);
        // step 5: we close the document
        document.close();

        StringBuffer sbContentDispValue = new StringBuffer();
        String useJavascript = request.getParameter("useJavascript");
        if (useJavascript == null || useJavascript.equalsIgnoreCase("false")) {
            sbContentDispValue.append("attachment");
        }
        else {
            sbContentDispValue.append("inline");
        }
        sbContentDispValue.append("; filename=");
        sbContentDispValue.append(MMUtil.getFileName());

        String contentDisposition = sbContentDispValue.toString();

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
        long endTime = System.currentTimeMillis();
        loggerAc.debug("Time taken for report Parameter settings in action "
                + (endTime - startTime));
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return mapping.findForward(MMConstants.MAPPING_BACK);
    }

    /**
     * creates file copies
     * 
     * @param fileListt
     * @param numOfCopies
     * @throws Exception
     */
    private void createFileCopies(List<File> fileListt, int numOfCopies) throws Exception {

        List<File> fileList = new ArrayList<File>(fileListt);
        byte[] buf = new byte[1024];
        for (File file : fileList) {

            String absPath = file.getAbsolutePath();
            String path = file.getPath();
            String newFileName = absPath.substring(0, absPath.indexOf(path));
            for (int i = 0; i < numOfCopies; i++) {
                String fileName = newFileName + MMConstants.COPY_FILE_NAME_PREFIX + i + "_"
                        + file.getName();
                BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(fileName,
                    true));
                BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
                int len;
                while ((len = fin.read(buf)) > 0) {
                    fout.write(buf, 0, len);
                }
                fout.close();
                fin.close();
                fileListt.add(new File(fileName));
            }
        }
    }

    private Warehouse getWareHouse(String warehouseCD) {
        return this.stockItemLookupService().getWarehouse(warehouseCD);
    }

    private Zone getZone(String warehouseCD, String zoneCd) {
        return this.stockItemLookupService().getZone(warehouseCD, zoneCd);
    }

    private Zone getZone(String zoneCd) {
        return this.stockItemLookupService().getZone(zoneCd);
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

    /**
     * returns the actionforward when validation fails for warehouse code or zone code
     * 
     * @param warehouseCd
     * @param zoneCd
     * @param wareHouse
     * @param numOfCounters
     * @param numOfCopies
     * @param countWorksheetPrintForm
     * @param mapping
     * @return
     */
    private boolean validate(String warehouseCd, String zoneCd, String countFrequency,
            int numOfCounters, int numOfCopies, CountWorksheetPrintForm countWorksheetPrintForm,
            ActionMapping mapping) {

        Warehouse wareHouse = null;

        if (StringUtils.isEmpty(warehouseCd) && StringUtils.isEmpty(zoneCd)) {

            GlobalVariables.getMessageMap().putError(
                    MMConstants.WorksheetCountDocument.WAREHOUSE_CODE,
                    MMKeyConstants.MESSAGE_INVALID_DATA);
            (countWorksheetPrintForm).setDataValid(false);
            return false;

        }

        if (!StringUtils.isEmpty(warehouseCd) && StringUtils.isEmpty(zoneCd)) {
            wareHouse = getWareHouse(warehouseCd);
        }
        else if (!StringUtils.isEmpty(zoneCd)) {

            Zone zone = null;

            if (StringUtils.isEmpty(warehouseCd))
                zone = getZone(zoneCd);
            else
                zone = getZone(warehouseCd, zoneCd);

            wareHouse = zone != null ? zone.getWarehouse() : null;
        }

        if (ObjectUtils.isNull(wareHouse)) {

            if (!StringUtils.isEmpty(warehouseCd) && !StringUtils.isEmpty(zoneCd)) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.WorksheetCountDocument.WAREHOUSE_CODE,
                        MMKeyConstants.MESSAGE_WAREHOUSE_ZONE_NOT_FOUND, zoneCd, warehouseCd);
            }
            else if (!StringUtils.isEmpty(warehouseCd)) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.WorksheetCountDocument.WAREHOUSE_CODE,
                        MMKeyConstants.MESSAGE_WAREHOUSE_NOT_FOUND, warehouseCd);
            }
            else if (!StringUtils.isEmpty(zoneCd)) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.WorksheetCountDocument.ZONE_CODE,
                        MMKeyConstants.MESSAGE_ZONE_NOT_FOUND, zoneCd);
            }
            countWorksheetPrintForm.setDataValid(false);
            return false;
        }

        if (StringUtils.isEmpty(countWorksheetPrintForm.getCounters())) {
            GlobalVariables.getMessageMap().putError(MMConstants.WorksheetCountDocument.COUNTERS,
                    MMKeyConstants.MESSAGE_INVALID_WORKSHEET_COUNTER);
            countWorksheetPrintForm.setDataValid(false);
            return false;
        }

        if (numOfCounters == -2 || numOfCopies == -2) {
            GlobalVariables.getMessageMap().putError(
                    numOfCounters == -2 ? MMConstants.WorksheetCountDocument.COUNTERS
                            : MMConstants.WorksheetCountDocument.COPIES,
                    numOfCounters == -2 ? MMKeyConstants.MESSAGE_COUNTER_INVALID_NUMBER
                            : MMKeyConstants.MESSAGE_COPIES_INVALID_NUMBER,
                    numOfCounters == -2 ? countWorksheetPrintForm.getCounters()
                            : countWorksheetPrintForm.getCopies());

            countWorksheetPrintForm.setDataValid(false);
            return false;
        }

        if (numOfCounters < 1) {
            GlobalVariables.getMessageMap().putError(MMConstants.WorksheetCountDocument.COUNTERS,
                    MMKeyConstants.MESSAGE_INVALID_WORKSHEET_COUNTER);
            countWorksheetPrintForm.setDataValid(false);
            return false;
        }

			//TODO: NWU - Confirm it has the same behaviour as before
        String confVal = CoreFrameworkServiceLocator.getParameterService().getParameterValuesAsString(
                MMConstants.MM_NAMESPACE, MMConstants.Parameters.DOCUMENT,
                MMConstants.Parameters.MAX_NUMBER_OF_WORKSHEET_COUNTERS).iterator().next();

        if (!StringUtils.isEmpty(confVal)) {
            int confCounter = Integer.valueOf(confVal).intValue();
            if (numOfCounters > confCounter) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.WorksheetCountDocument.COUNTERS,
                        MMKeyConstants.MESSAGE_NUMBER_OF_WORKSHEET_COUNTERS_GREATERTHAN_CONF_VALUE,
                        String.valueOf(numOfCounters), confVal);
                countWorksheetPrintForm.setDataValid(false);
                return false;
            }
        }

			  //TODO: NWU - Confirm it has the same behaviour as before
        confVal = CoreFrameworkServiceLocator.getParameterService().getParameterValuesAsString(
                MMConstants.MM_NAMESPACE, MMConstants.Parameters.DOCUMENT,
                MMConstants.Parameters.MAX_NUMBER_OF_WORKSHEET_COPIES).iterator().next();

        if (!StringUtils.isEmpty(confVal)) {
            int confCopies = Integer.valueOf(confVal).intValue();
            if (numOfCopies > confCopies) {
                GlobalVariables.getMessageMap().putError(MMConstants.WorksheetCountDocument.COPIES,
                        MMKeyConstants.MESSAGE_NUMBER_OF_WORKSHEET_COPIES_GREATERTHAN_CONF_VALUE,
                        String.valueOf(numOfCopies), confVal);
                countWorksheetPrintForm.setDataValid(false);
                return false;
            }
        }

        if (numOfCopies < 0) {
            GlobalVariables.getMessageMap().putError(MMConstants.WorksheetCountDocument.COPIES,
                    MMKeyConstants.MESSAGE_INVALID_WORKSHEET_COPIES);
            countWorksheetPrintForm.setDataValid(false);
            return false;
        }
        return true;
    }
}
