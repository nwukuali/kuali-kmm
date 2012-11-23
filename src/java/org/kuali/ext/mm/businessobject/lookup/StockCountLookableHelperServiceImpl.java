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


import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.StockCount;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * This class overrids the base getActionUrls method
 */
public class StockCountLookableHelperServiceImpl extends KualiLookupableHelperServiceImpl {
    /**
     *
     */
    private static final long serialVersionUID = -7988587344090746588L;

    /**
     * This method needs to be overridden to set status code parameters
     */
    @Override
    public void setParameters(Map parameters) {
        super.setParameters(parameters);
    }

    /**
     * <li><portal:portalLink displayTitle="true" title="Cycle Count Entry Ram"
     * url="initiateCycleCountEntry.do?methodToCall=docHandler &command=displayDocSearchView&docId=3473" /></li>
     */
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject bo, List pkNames) {
        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(bo, pkNames);
        StockCount stockCount = (StockCount) bo;

        if (ObjectUtils.isNull(anchorHtmlDataList))
            anchorHtmlDataList = new ArrayList<HtmlData>();

        HtmlData aData = this.getWorksheetCountDocUrl(stockCount);

        if (aData != null)
            anchorHtmlDataList.add(this.getWorksheetCountDocUrl(stockCount));

        return anchorHtmlDataList;
    }

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {

        List<? extends BusinessObject> objList = super.getSearchResults(fieldValues);
        List<StockCount> removeList = new ArrayList<StockCount>();
        for (BusinessObject obj : objList) {
            StockCount scount = (StockCount) obj;
            if ((ObjectUtils.isNull(scount.getWorksheetCount()) || StringUtils.isEmpty(scount
                    .getWorksheetCount().getDocumentNumber())))
                removeList.add(scount);
        }

        for (BusinessObject obj : removeList) {
            objList.remove(obj);
        }

        return objList;
    }

    protected HtmlData getWorksheetCountDocUrl(StockCount stockCount) {

        if (stockCount == null || StringUtils.isEmpty(stockCount.getWorksheetCountId()))
            return null;

        Properties params = new Properties();
        params
                .setProperty(KRADConstants.DISPATCH_REQUEST_PARAMETER,
                        KRADConstants.DOC_HANDLER_METHOD);
        params.setProperty(MMConstants.COMMAND, MMConstants.DISPLAY_DOC_SEARCH_VIEW);
        params.setProperty(MMConstants.DOCUMENT_ID, stockCount.getWorksheetCountId());

        String href = getKualiConfigurationService().getPropertyValueAsString(
                KRADConstants.APPLICATION_URL_KEY)
                + "/"
                + UrlFactory.parameterizeUrl(
                        MMConstants.WorksheetCountDocument.WORKSHEET_COUNT_DOC_DISPLAY_ACTION,
                        params);
        AnchorHtmlData anchorHtmlData = new AnchorHtmlData(href, "",
            MMConstants.WorksheetCountDocument.VIEW);
        return anchorHtmlData;

    }


}