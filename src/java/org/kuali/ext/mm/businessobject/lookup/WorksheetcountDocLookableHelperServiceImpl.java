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
package org.kuali.ext.mm.businessobject.lookup;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.WorksheetCountDocumentLookable;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.util.KimCommonUtils;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;


/**
 * This class overrids the base getActionUrls method
 */
public class WorksheetcountDocLookableHelperServiceImpl extends KualiLookupableHelperServiceImpl {
    /**
     *
     */
    private static final long serialVersionUID = 5038322033765489991L;

    /**
     * Custom action urls for Asset.
     *
     * @see org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl#getCustomActionUrls(org.kuali.rice.kns.bo.BusinessObject,
     *      List pkNames)
     */

    /**
     * <li><portal:portalLink displayTitle="true" title="Cycle Count Entry Ram"
     * url="initiateCycleCountEntry.do?methodToCall=docHandler &command=displayDocSearchView&docId=3473" /></li>
     */
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject bo, List pkNames) {
        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(bo, pkNames);
        WorksheetCountDocumentLookable worksheet = (WorksheetCountDocumentLookable) bo;

        if (anchorHtmlDataList == null)
            anchorHtmlDataList = new ArrayList<HtmlData>();

        HtmlData printURLData = this.getWorksheetCountDocPrintUrl(worksheet);
        HtmlData countEnterURLData = null;
        boolean authorized = KIMServiceLocator.getIdentityManagementService().isAuthorized(
                GlobalVariables.getUserSession().getPrincipalId(), MMConstants.MM_NAMESPACE,
                MMConstants.WorksheetCountDocument.EDIT_WORKSHEET_PERMISSION, null, null);
        if (worksheet.getWorksheetStatusCode().equals("PRTD")
                || worksheet.getWorksheetStatusCode().equals("ENTR")) {
            if (authorized) {
                countEnterURLData = this.getWorksheetCountDocEnterUrl(worksheet);
            }
        }
        if (printURLData != null)
            anchorHtmlDataList.add(printURLData);

        if (countEnterURLData != null)
            anchorHtmlDataList.add(countEnterURLData);

        return anchorHtmlDataList;
    }

    protected HtmlData getWorksheetCountDocPrintUrl(WorksheetCountDocumentLookable worksheetDoc) {

        if (worksheetDoc == null || StringUtils.isEmpty(worksheetDoc.getDocumentNumber()))
            return null;

        Properties params = new Properties();
        params.setProperty(MMConstants.METHOD_TO_CALL,
                MMConstants.WorksheetCountDocument.PRINT_STATEMENT_PDF);
        params.setProperty(MMConstants.WORKSHEET_DOC_NUMBER, worksheetDoc.getDocumentNumber());
        params.setProperty(MMConstants.WORKSHEET_DOC_STATUS, worksheetDoc.getWorksheetStatusCode());

        String href = getKualiConfigurationService().getPropertyString(
                KNSConstants.APPLICATION_URL_KEY)
                + "/"
                + UrlFactory
                        .parameterizeUrl(
                                MMConstants.WorksheetCountDocument.WORKSHEET_COUNT_DOC_PRINT_ACTION,
                                params);
        AnchorHtmlData anchorHtmlData = new AnchorHtmlData(href, "",
            MMConstants.WorksheetCountDocument.PRINT_PDF);
        return anchorHtmlData;
    }

    protected AttributeSet buildPermissionDetails(Document document) {
        Map<String, String> permissionDetails = new HashMap<String, String>();
        permissionDetails.put("documentTypeName", "SWKC"); // document type name
        permissionDetails.put("routeNodeName", "CycleCountWorksheetPrint"); // route node = PreRoute
        permissionDetails.put("namespaceCode", MMConstants.MM_NAMESPACE);
        permissionDetails.putAll(KimCommonUtils.getNamespaceAndComponentSimpleName(document
                .getClass()));
        AttributeSet aa = new AttributeSet(permissionDetails);
        // permissionDetails.put(KfsKimAttributes.PROPERTY_NAME, "sourceAccountingLines"); // property = sourceAccountingLines
        return aa;
    }

    protected HtmlData getWorksheetCountDocEnterUrl(WorksheetCountDocumentLookable worksheetDoc) {

        if (worksheetDoc == null || StringUtils.isEmpty(worksheetDoc.getDocumentNumber()))
            return null;

        Properties params = new Properties();
        params
                .setProperty(KNSConstants.DISPATCH_REQUEST_PARAMETER,
                        KNSConstants.DOC_HANDLER_METHOD);
        params.setProperty(MMConstants.COMMAND, MMConstants.DISPLAY_DOC_SEARCH_VIEW);
        params.setProperty(MMConstants.DOCUMENT_ID, worksheetDoc.getDocumentNumber());

        String href = getKualiConfigurationService().getPropertyString(
                KNSConstants.APPLICATION_URL_KEY)
                + "/"
                + UrlFactory.parameterizeUrl(
                        MMConstants.WorksheetCountDocument.WORKSHEET_COUNT_DOC_DISPLAY_ACTION,
                        params);
        AnchorHtmlData anchorHtmlData = new AnchorHtmlData(href, "",
            MMConstants.WorksheetCountDocument.VIEW);
        return anchorHtmlData;

    }


}