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

import org.kuali.ext.mm.businessobject.OrderReturnDetailForVendor;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.List;
import java.util.Properties;


/**
 * This class overrids the base getActionUrls method
 */
public class OrderDocumentVendorLookableHelperServiceImpl extends KualiLookupableHelperServiceImpl {
    /**
     *
     */
    private static final long serialVersionUID = -3548028916529771379L;

    /**
     * <li><portal:portalLink displayTitle="true" title="Cycle Count Entry Ram"
     * url="initiateCycleCountEntry.do?methodToCall=docHandler &command=displayDocSearchView&docId=3473" /></li>
     */
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject bo, List pkNames) {
        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(bo, pkNames);
        OrderReturnDetailForVendor orderDetail = (OrderReturnDetailForVendor) bo;

        HtmlData countEnterURLData = this.getReturnOrderUrl(orderDetail);

        if (countEnterURLData != null)
            anchorHtmlDataList.add(countEnterURLData);

        return anchorHtmlDataList;
    }

    /**
     * @param orderDetail
     * @return
     */
    protected HtmlData getReturnOrderUrl(OrderReturnDetailForVendor orderDetail) {

        if (orderDetail == null)
            return null;

        Properties params = new Properties();
        AnchorHtmlData anchorHtmlData = null;

        params
                .setProperty(KRADConstants.DISPATCH_REQUEST_PARAMETER,
                        KRADConstants.DOC_HANDLER_METHOD);
        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
        params
                .setProperty(MMConstants.CheckinDocument.ORDER_DOC_NUMBER, orderDetail
                        .getDocNumber());
        params.setProperty(KRADConstants.DOCUMENT_TYPE_NAME,
                MMConstants.CHECKIN_VENDOR_RETURNDOC_TYPE);
        String href = getKualiConfigurationService().getPropertyValueAsString(
                KRADConstants.APPLICATION_URL_KEY)
                + "/"
                + UrlFactory.parameterizeUrl(
                        MMConstants.ReturnDocument.RETURN_DOCUMENT_RECEIVE_ACTION, params);
        anchorHtmlData = new AnchorHtmlData(href, "",
            MMConstants.CheckinDocument.RETURN_ORDER_LABEL);
        return anchorHtmlData;
    }

}