package org.kuali.ext.mm.document.validation.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.document.CatalogPending;
import org.kuali.ext.mm.util.ParseUtil;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rules.DocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;


/**
 * @author rshrivas
 */
public class UploadDocumentRule extends DocumentRuleBase {

    @Override
    protected boolean processCustomSaveDocumentBusinessRules(Document document) {

        CatalogPending catPend = (CatalogPending) document;

        boolean catalogCdErrorNotPresent = true;
        boolean dateErrorNotPresent = true;
        boolean fileErrorNotPresent = true;

        if (catPend != null) {

            Date beginDt = catPend.getCatalogBeginDt();
            Date endDt = catPend.getCatalogEndDt();


            if (endDt != null) {
                if (beginDt == null) {
                    GlobalVariables.getMessageMap().putError(
                            MMConstants.CatalogPending.BEGIN_DT_REQUIRED,
                            MMKeyConstants.CatalogPending.BEGIN_DT_REQUIRED);
                    dateErrorNotPresent = false;
                }
                else {
                    if (beginDt.after(endDt)) {
                        GlobalVariables.getMessageMap().putError(
                                MMConstants.CatalogPending.BEGIN_AFTER_END,
                                MMKeyConstants.CatalogPending.BEGIN_AFTER_END);
                        dateErrorNotPresent = false;
                    }
                }
            }

            FormFile formFile = catPend.getFileContent();

            if (formFile != null) {

                int size = formFile.getFileSize();

                if (size > 155000000) {
                    GlobalVariables.getMessageMap().putError(
                            MMConstants.CatalogPending.FILE_NOT_SUPPORTED_ERROR,
                            "File Size cannot be bigger than 155 MB");
                    fileErrorNotPresent = false;
                }
                else {
                    String fileName = formFile.getFileName();
                    if (StringUtils.isBlank(fileName)) {
                        GlobalVariables.getMessageMap().putError(
                                MMConstants.CatalogPending.FILE_REQUIRED_ERROR,
                                MMKeyConstants.CatalogPending.FILE_REQUIRED_ERROR);
                        fileErrorNotPresent = false;
                    }
                    else {
                        if (!fileName.split("\\.")[1].equalsIgnoreCase("csv")) {
                            GlobalVariables.getMessageMap().putError(
                                    MMConstants.CatalogPending.FILE_NOT_SUPPORTED_ERROR,
                                    MMKeyConstants.CatalogPending.FILE_NOT_SUPPORTED_ERROR);
                            fileErrorNotPresent = false;
                        }
                        else {
                            try {
                                InputStream is = formFile.getInputStream();
                                if (is != null) {
                                    StringBuilder sb = new StringBuilder();
                                    String line;
                                    try {
                                        BufferedReader reader = new BufferedReader(
                                            new InputStreamReader(is));
                                        while ((line = reader.readLine()) != null) {
                                            sb.append(line);
                                            break;
                                        }
                                    }
                                    finally {
                                        is.close();
                                    }
                                    String headerLine = sb.toString();
                                    String[] headers = ParseUtil.parseQuoted(',', headerLine);
                                    fileErrorNotPresent = validateFileContent(headers);
                                }
                            }
                            catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }
        return catalogCdErrorNotPresent && dateErrorNotPresent && fileErrorNotPresent;
    }

    /**
     * @param headers
     */
    private boolean validateFileContent(String[] headers) {
        boolean fileErrorNotPresent = true;
        if (!headers[5].equalsIgnoreCase("UPrice")) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.CatalogPending.FILE_UNSUPPORTED_FORMAT_COLUMN5,
                    MMKeyConstants.CatalogPending.FILE_UNSUPPORTED_FORMAT_COLUMN5);
            fileErrorNotPresent = false;
        }
        return fileErrorNotPresent;

    }
}