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

import net.sf.jasperreports.engine.JRParameter;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.report.ReportInfo;
import org.kuali.ext.mm.service.RTVReportService;
import org.kuali.ext.mm.sys.service.ReportGenerationService;
import org.kuali.rice.core.api.datetime.DateTimeService;

import java.io.ByteArrayOutputStream;
import java.util.*;

public class RTVReportServiceImpl implements RTVReportService {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(RTVReportServiceImpl.class);

    protected ReportGenerationService reportGenerationService;
    protected ReportInfo rtvReportInfo;

    public ReportInfo getRtvReportInfo() {
        return this.rtvReportInfo;
    }

    public void setRtvReportInfo(ReportInfo rtvReportInfo) {
        this.rtvReportInfo = rtvReportInfo;
    }

    protected DateTimeService dateTimeService;

    public List<ByteArrayOutputStream> generateRTVLabelReportPDF(ReturnDocument returnDoc) {

        List<ByteArrayOutputStream> lisOutputdata = new ArrayList<ByteArrayOutputStream>(0);
        String reportTemplateClassPath = rtvReportInfo.getReportTemplateClassPath();
        String reportTemplateName = rtvReportInfo.getReportTemplateName();
        ResourceBundle resourceBundle = rtvReportInfo.getResourceBundle();
        Map<String, Object> reportData = new HashMap<String, Object>();
        reportData.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
        String template = reportTemplateClassPath + reportTemplateName;

        for (ReturnDetail rdetail : returnDoc.getReturnDetails()) {
            List<org.kuali.ext.mm.businessobject.ReturnDetail> dataSource = new ArrayList<org.kuali.ext.mm.businessobject.ReturnDetail>();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            dataSource.add(rdetail);
            reportGenerationService.generateReportToOutputStream(reportData, dataSource, template,
                    baos);
            lisOutputdata.add(baos);
        }

        return lisOutputdata;
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
