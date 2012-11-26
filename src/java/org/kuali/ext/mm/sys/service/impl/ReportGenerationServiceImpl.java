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
package org.kuali.ext.mm.sys.service.impl;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMConstants.ReportGeneration;
import org.kuali.ext.mm.sys.service.ReportGenerationService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import java.io.*;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * To provide utilities that can generate reports with JasperReport
 */
@SuppressWarnings("unchecked")
public class ReportGenerationServiceImpl implements ReportGenerationService {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(ReportGenerationServiceImpl.class);
    public final static String PARAMETER_NAME_SUBREPORT_DIR = ReportGeneration.PARAMETER_NAME_SUBREPORT_DIR;
    public final static String PARAMETER_NAME_SUBREPORT_TEMPLATE_NAME = ReportGeneration.PARAMETER_NAME_SUBREPORT_TEMPLATE_NAME;
    public final static String DESIGN_FILE_EXTENSION = ReportGeneration.DESIGN_FILE_EXTENSION;
    public final static String JASPER_REPORT_EXTENSION = ReportGeneration.JASPER_REPORT_EXTENSION;
    public final static String PDF_FILE_EXTENSION = ReportGeneration.PDF_FILE_EXTENSION;
    private final static HashSet<String> COMPILED_ONES = new HashSet<String>();

    /**
     * @see org.kuali.kfs.sys.batch.service.ReportGenerationService#generateReportToPdfFile(java.util.Map, java.lang.String,
     *      java.lang.String)
     */
    public void generateReportToPdfFile(Map<String, Object> reportData, String template,
            String reportFileName) {
        List<String> data = Arrays.asList(MMConstants.EMPTY_STRING);
        JRDataSource dataSource = new JRBeanCollectionDataSource(data);
        generateReportToPdfFile(reportData, dataSource, template, reportFileName);
    }

    /**
     * The dataSource can be an instance of JRDataSource, java.util.Collection or object array.
     *
     * @see org.kuali.kfs.sys.batch.service.ReportGenerationService#generateReportToPdfFile(java.util.Map, java.lang.Object,
     *      java.lang.String, java.lang.String)
     */
    public void generateReportToPdfFile(Map<String, Object> reportData, Object dataSource,
            String template, String reportFileNm) {
        try {
            if (reportData != null
                    && reportData.containsKey(PARAMETER_NAME_SUBREPORT_TEMPLATE_NAME)) {
                Map<String, String> subReports = (Map<String, String>) reportData
                        .get(PARAMETER_NAME_SUBREPORT_TEMPLATE_NAME);
                String subReportDirectory = (String) reportData
                        .get(MMConstants.ReportGeneration.SUB_REPORT_TEMPLATE_CLASSPATH);
                compileSubReports(subReports, subReportDirectory);
            }
            String designTemplateName = template.concat(DESIGN_FILE_EXTENSION);
            String jasperReportName = template.concat(JASPER_REPORT_EXTENSION);
            compileReportTemplate(designTemplateName, jasperReportName);
            JRDataSource jrDataSource = JasperReportsUtils.convertReportData(dataSource);
            String reportFileName = reportFileNm + PDF_FILE_EXTENSION;
            File reportDirectory = new File(StringUtils.substringBeforeLast(reportFileName,
                    File.separator));
            if (!reportDirectory.exists()) {
                reportDirectory.mkdirs();
            }
            JasperRunManager.runReportToPdfFile(buildJasperFilePath(jasperReportName)
                    .getAbsolutePath(), reportFileName, reportData, jrDataSource);
        }
        catch (Exception e) {
            LOG.error(e);
            throw new RuntimeException("Failed to generate report.", e);
        }
    }

    /**
     * @see org.kuali.kfs.sys.batch.service.ReportGenerationService#generateReportToOutputStream(java.util.Map, java.lang.Object,
     *      java.lang.String, java.io.ByteArrayOutputStream)
     */
    public void generateReportToOutputStream(Map<String, Object> reportData, Object dataSource,
            String template, ByteArrayOutputStream baos) {

        try {
            if (reportData != null
                    && reportData.containsKey(PARAMETER_NAME_SUBREPORT_TEMPLATE_NAME)) {
                Map<String, String> subReports = (Map<String, String>) reportData
                        .get(PARAMETER_NAME_SUBREPORT_TEMPLATE_NAME);
                String subReportDirectory = (String) reportData
                        .get(MMConstants.ReportGeneration.SUB_REPORT_TEMPLATE_CLASSPATH);
                compileSubReports(subReports, subReportDirectory);
            }
            String designTemplateName = template.concat(DESIGN_FILE_EXTENSION);
            String jasperReportName = template.concat(JASPER_REPORT_EXTENSION);
            compileReportTemplate(designTemplateName, jasperReportName);
            JRDataSource jrDataSource = JasperReportsUtils.convertReportData(dataSource);
            InputStream inputStream = new FileInputStream(buildJasperFilePath(jasperReportName));
            JasperRunManager.runReportToPdfStream(inputStream, baos, reportData, jrDataSource);
        }
        catch (Exception e) {
            LOG.error(e);
            throw new RuntimeException("Failed to generate report.", e);
        }
    }

    /**
     * @see org.kuali.kfs.sys.batch.service.ReportGenerationService#buildFullFileName(java.util.Date, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public String buildFullFileName(Date runDate, String directory, String fileName,
            String extension) {
        String runtimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(runDate);
        String fileNamePattern = "{0}" + File.separator + "{1}_{2}{3}";
        return MessageFormat.format(fileNamePattern, directory, fileName, runtimeStamp, extension);
    }


    /**
     * complie the report template xml file into a Jasper report file if the compiled file does not exist or is out of update
     *
     * @param designTemplate the full name of the report template xml file
     * @param jasperReport the full name of the compiled report file
     */
    protected void compileReportTemplate(String designTemplate, String jasperReport)
            throws JRException {
        URL designTemplateResource = Thread.currentThread().getContextClassLoader().getResource(
                designTemplate);
        File jasperFile = buildJasperFilePath(jasperReport);
        if ((designTemplateResource == null || designTemplateResource.getFile() == null)
                && (!jasperFile.exists())) {
            throw new RuntimeException(
                "Both the design template (*.jxml) and jasper report (*.jasper) files don't exist: ("
                        + designTemplate + ", " + jasperReport + ")");
        }

        // if first time or file doesn't exists
        if ((COMPILED_ONES.add(designTemplate))
                || (designTemplateResource != null || designTemplateResource.getFile() != null)
                && (!jasperFile.exists())) {
            try {
                JasperCompileManager.compileReportToStream(Thread.currentThread()
                        .getContextClassLoader().getResourceAsStream(designTemplate),
                        new FileOutputStream(jasperFile));
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param designTemplate
     * @return
     */
    protected File buildJasperFilePath(String jasperFileName) {
        File jasperFile = new File(KRADServiceLocator.getKualiConfigurationService()
                .getPropertyValueAsString(MMConstants.ReportGeneration.EXTERNAL_REPORTS_DIR)
                + File.separator + jasperFileName);
        if (!jasperFile.isFile()) {
            new File(jasperFile.getAbsolutePath().substring(0,
                    jasperFile.getAbsolutePath().lastIndexOf(File.separatorChar))).mkdirs();
        }
        return jasperFile;
    }

    /**
     * compile the given sub reports
     *
     * @param subReports the sub report Map that hold the sub report templete names indexed with keys
     * @param subReportDirectory the directory where sub report templates are located
     */
    protected void compileSubReports(Map<String, String> subReports, String subReportDirectory)
            throws Exception {
        for (Map.Entry<String, String> entry : subReports.entrySet()) {
            String designTemplateName = subReportDirectory
                    + entry.getValue().concat(DESIGN_FILE_EXTENSION);
            String jasperReportName = subReportDirectory
                    + entry.getValue().concat(JASPER_REPORT_EXTENSION);
            compileReportTemplate(designTemplateName, jasperReportName);
        }
    }


}
