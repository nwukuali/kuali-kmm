/*
 * Copyright 2008 The Kuali Foundation.
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
package org.kuali.ext.mm.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRParameter;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMConstants.ReportGeneration;
import org.kuali.ext.mm.report.ReportInfo;
import org.kuali.ext.mm.service.CountWorksheetReportService;
import org.kuali.ext.mm.sys.service.ReportGenerationService;
import org.kuali.rice.kns.service.DateTimeService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.Guid;

import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.SimpleBookmark;


public class CountWorksheetReportServiceImpl implements CountWorksheetReportService {
    protected ReportGenerationService reportGenerationService;
    protected ReportInfo countworksheetReportInfo;

    protected DateTimeService dateTimeService;

    /**
     * @see org.kuali.kfs.module.cab.batch.service.BatchExtractReportService#generateStatusReportPDF(org.kuali.kfs.module.cab.batch.ExtractProcessLog)
     */
    public File generateCountWorksheetReportPDF(
            org.kuali.ext.mm.document.WorksheetCountDocument worksheetDoc) {
        String reportFileName = countworksheetReportInfo.getReportFileName();
        String reportDirectoty = countworksheetReportInfo.getReportsDirectory();
        String reportTemplateClassPath = countworksheetReportInfo.getReportTemplateClassPath();
        String reportTemplateName = countworksheetReportInfo.getReportTemplateName();
        ResourceBundle resourceBundle = countworksheetReportInfo.getResourceBundle();
        String subReportTemplateClassPath = countworksheetReportInfo
                .getSubReportTemplateClassPath();
        Map<String, String> subReports = countworksheetReportInfo.getSubReports();
        Map<String, Object> reportData = new HashMap<String, Object>();
        reportData.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
        reportData.put(ReportGeneration.SUB_REPORT_TEMPLATE_CLASSPATH, subReportTemplateClassPath);
        reportData.put(ReportGeneration.PARAMETER_NAME_SUBREPORT_DIR,
                buildSubReportDirectory(subReportTemplateClassPath));
        reportData.put(ReportGeneration.PARAMETER_NAME_SUBREPORT_TEMPLATE_NAME, subReports);
        String template = reportTemplateClassPath + reportTemplateName;
        String fullReportFileName = reportGenerationService.buildFullFileName(dateTimeService
                .getCurrentDate(), reportDirectoty, reportFileName, "");
        List<org.kuali.ext.mm.document.WorksheetCountDocument> dataSource = new ArrayList<org.kuali.ext.mm.document.WorksheetCountDocument>();
        dataSource.add(worksheetDoc);
        reportGenerationService.generateReportToPdfFile(reportData, dataSource, template,
                fullReportFileName);
        return new File(fullReportFileName + ".pdf");
    }

    public File generateRTVReportPDF(org.kuali.ext.mm.document.ReturnDocument worksheetDoc) {
        String reportFileName = countworksheetReportInfo.getReportFileName();
        String reportDirectoty = countworksheetReportInfo.getReportsDirectory();
        String reportTemplateClassPath = countworksheetReportInfo.getReportTemplateClassPath();
        String reportTemplateName = countworksheetReportInfo.getReportTemplateName();
        ResourceBundle resourceBundle = countworksheetReportInfo.getResourceBundle();
        String subReportTemplateClassPath = countworksheetReportInfo
                .getSubReportTemplateClassPath();
        Map<String, String> subReports = countworksheetReportInfo.getSubReports();
        Map<String, Object> reportData = new HashMap<String, Object>();
        reportData.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
        reportData.put(ReportGeneration.SUB_REPORT_TEMPLATE_CLASSPATH, subReportTemplateClassPath);
        reportData.put(ReportGeneration.PARAMETER_NAME_SUBREPORT_DIR,
                buildSubReportDirectory(subReportTemplateClassPath));
        reportData.put(ReportGeneration.PARAMETER_NAME_SUBREPORT_TEMPLATE_NAME, subReports);
        String template = reportTemplateClassPath + reportTemplateName;
        String fullReportFileName = reportGenerationService.buildFullFileName(dateTimeService
                .getCurrentDate(), reportDirectoty, reportFileName, "");
        List<org.kuali.ext.mm.document.ReturnDocument> dataSource = new ArrayList<org.kuali.ext.mm.document.ReturnDocument>();
        dataSource.add(worksheetDoc);
        reportGenerationService.generateReportToPdfFile(reportData, dataSource, template,
                fullReportFileName);
        return new File(fullReportFileName + ".pdf");
    }

    public void removeReportFiles(List<File> fileList) {
        for (File file : fileList) {
            if (file.exists())
                file.delete();
        }

    }


    public ByteArrayOutputStream combineAndFlushReportPDFFiles(List<File> fileList)
            throws Exception {

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
        return baos;
    }

    public ByteArrayOutputStream combineAndFlushReportPDFStreams(
            List<ByteArrayOutputStream> fileList) throws Exception {

        long startTime = System.currentTimeMillis();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ArrayList master = new ArrayList();
        int pageOffset = 0;
        int f = 0;
        PdfCopy writer = null;
        com.lowagie.text.Document document = null;
        for (ByteArrayOutputStream file : fileList) {
            // we create a reader for a certain document
            // String reportName = file.getAbsolutePath();
            PdfReader reader = new PdfReader(file.toByteArray());
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
        return baos;
    }

    public String getReportFileName() {
        String tag = new Guid().toString();
        String fileName = MMConstants.WorksheetCountDocument.REPORT_ZIP_FILE_NAME + tag + ".pdf";
        return fileName;
    }

    public void generateCountWorksheetReportPDF(
            org.kuali.ext.mm.document.WorksheetCountDocument worksheetDoc, ByteArrayOutputStream bao) {
        String reportTemplateClassPath = countworksheetReportInfo.getReportTemplateClassPath();
        String reportTemplateName = countworksheetReportInfo.getReportTemplateName();
        ResourceBundle resourceBundle = countworksheetReportInfo.getResourceBundle();
        String subReportTemplateClassPath = countworksheetReportInfo
                .getSubReportTemplateClassPath();
        Map<String, String> subReports = countworksheetReportInfo.getSubReports();
        Map<String, Object> reportData = new HashMap<String, Object>();
        reportData.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
        reportData.put(ReportGeneration.SUB_REPORT_TEMPLATE_CLASSPATH, subReportTemplateClassPath);
        reportData.put(ReportGeneration.PARAMETER_NAME_SUBREPORT_DIR,
                buildSubReportDirectory(subReportTemplateClassPath));
        reportData.put(ReportGeneration.PARAMETER_NAME_SUBREPORT_TEMPLATE_NAME, subReports);
        String template = reportTemplateClassPath + reportTemplateName;
        List<org.kuali.ext.mm.document.WorksheetCountDocument> dataSource = new ArrayList<org.kuali.ext.mm.document.WorksheetCountDocument>();
        dataSource.add(worksheetDoc);
        reportGenerationService.generateReportToOutputStream(reportData, dataSource, template, bao);
    }

    /**
     * @param subReportTemplateClassPath
     * @return
     */
    private String buildSubReportDirectory(String subReportTemplateClassPath) {
        return KNSServiceLocator.getKualiConfigurationService().getPropertyString(
                MMConstants.ReportGeneration.EXTERNAL_REPORTS_DIR)
                + File.separator + subReportTemplateClassPath.replace('\\', File.separatorChar);
    }

    /**
     * Gets the reportGenerationService attribute.
     * 
     * @return Returns the reportGenerationService.
     */
    public ReportGenerationService getReportGenerationService() {
        return reportGenerationService;
    }

    /**
     * Sets the reportGenerationService attribute value.
     * 
     * @param reportGenerationService The reportGenerationService to set.
     */
    public void setReportGenerationService(ReportGenerationService reportGenerationService) {
        this.reportGenerationService = reportGenerationService;
    }

    public ReportInfo getCountworksheetReportInfo() {
        return countworksheetReportInfo;
    }

    public void setCountworksheetReportInfo(ReportInfo countworksheetReportInfo) {
        this.countworksheetReportInfo = countworksheetReportInfo;
    }


    /**
     * Gets the dateTimeService attribute.
     * 
     * @return Returns the dateTimeService.
     */
    public DateTimeService getDateTimeService() {
        return dateTimeService;
    }

    /**
     * Sets the dateTimeService attribute value.
     * 
     * @param dateTimeService The dateTimeService to set.
     */
    public void setDateTimeService(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

}
