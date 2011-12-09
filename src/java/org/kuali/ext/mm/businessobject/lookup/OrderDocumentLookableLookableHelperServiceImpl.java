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

// This is for return from customer
// This is for return from customer

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.ext.mm.businessobject.OrderReturnDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;


/**
 * This class overrids the base getActionUrls method
 */
@SuppressWarnings("unchecked")
public class OrderDocumentLookableLookableHelperServiceImpl extends
        KualiLookupableHelperServiceImpl {
    /**
     *
     */
    private static final long serialVersionUID = -8010780014522590940L;

    /**
     * <li><portal:portalLink displayTitle="true" title="Cycle Count Entry Ram"
     * url="initiateCycleCountEntry.do?methodToCall=docHandler &command=displayDocSearchView&docId=3473" /></li>
     */
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject bo, List pkNames) {
        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(bo, pkNames);
        OrderReturnDetail orderDetail = (OrderReturnDetail) bo;

        HtmlData countEnterURLData = this.getReturnOrderUrl(orderDetail);

        if (countEnterURLData != null)
            anchorHtmlDataList.add(countEnterURLData);

        return anchorHtmlDataList;
    }

    // public Collection performLookup(LookupForm lookupForm, Collection resultTable, boolean bounded) {
    // Map lookupFormFields = lookupForm.getFieldsForLookup();
    //
    // if(lookupFormFields != null && lookupFormFields.size() > 0){
    // pageName = (String) lookupFormFields.get("conversionFields");
    // lookupFormFields.remove("conversionFields");
    // }
    // return super.performLookup(lookupForm, resultTable, bounded);
    // }

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {

        return super.getSearchResults(fieldValues);
    }


    /**
     * @param orderDetail
     * @return
     */
    protected HtmlData getReturnOrderUrl(OrderReturnDetail orderDetail) {

        if (orderDetail == null)
            return null;

        Properties params = new Properties();
        AnchorHtmlData anchorHtmlData = null;

        params
                .setProperty(KNSConstants.DISPATCH_REQUEST_PARAMETER,
                        KNSConstants.DOC_HANDLER_METHOD);
        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
        params
                .setProperty(MMConstants.CheckinDocument.ORDER_DOC_NUMBER, orderDetail
                        .getDocNumber());
        params.setProperty(KNSConstants.DOCUMENT_TYPE_NAME, MMConstants.CHECKIN_RETURNDOC_TYPE);
        String href = getKualiConfigurationService().getPropertyString(
                KNSConstants.APPLICATION_URL_KEY)
                + "/"
                + UrlFactory.parameterizeUrl(
                        MMConstants.ReturnDocument.RETURN_DOCUMENT_RECEIVE_ACTION, params);
        anchorHtmlData = new AnchorHtmlData(href, "",
            MMConstants.CheckinDocument.RETURN_ORDER_LABEL);
        return anchorHtmlData;
    }

}