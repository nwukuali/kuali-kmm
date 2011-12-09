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


import java.util.List;
import java.util.Properties;

import org.kuali.ext.mm.businessobject.OrderDetailForReturns;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.util.UrlFactory;


/**
 * This class overrids the base getActionUrls method
 */
@SuppressWarnings("unchecked")
public class OrderDetailForReturnsLookableHelperServiceImpl extends
        KualiLookupableHelperServiceImpl {
    private static final long serialVersionUID = 2388981605586665879L;

    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject bo, List pkNames) {
        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(bo, pkNames);
        OrderDetailForReturns orderDetail = (OrderDetailForReturns) bo;

        HtmlData countEnterURLData = this.getReceiveOrderUrl(orderDetail);

        if (countEnterURLData != null)
            anchorHtmlDataList.add(countEnterURLData);

        return anchorHtmlDataList;
    }

    /**
     * @param orderDetail
     * @return
     */
    protected HtmlData getReceiveOrderUrl(OrderDetailForReturns orderDetail) {

        if (orderDetail == null || ObjectUtils.isNull(orderDetail.getOrderDocument()))
            return null;

        Properties params = new Properties();
        AnchorHtmlData anchorHtmlData = null;

        params
                .setProperty(KNSConstants.DISPATCH_REQUEST_PARAMETER,
                        KNSConstants.DOC_HANDLER_METHOD);
        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
        params.setProperty(MMConstants.CheckinDocument.ORDER_DOC_NUMBER, orderDetail
                .getOrderDocumentNbr());
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