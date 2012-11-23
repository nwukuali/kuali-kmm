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


import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * This class overrids the base getActionUrls method
 */
@SuppressWarnings("unchecked")
public class OrderDetailLookableHelperServiceImpl extends KualiLookupableHelperServiceImpl {
    private static final long serialVersionUID = -473016671916831600L;

    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject bo, List pkNames) {
        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(bo, pkNames);
        OrderDetail orderDetail = (OrderDetail) bo;

        if (anchorHtmlDataList == null)
            anchorHtmlDataList = new ArrayList<HtmlData>();

        HtmlData printURLData = this.getReceiveLineUrl(orderDetail);
        HtmlData countEnterURLData = this.getReceiveOrderUrl(orderDetail);

        if (printURLData != null)
            anchorHtmlDataList.add(printURLData);

        if (countEnterURLData != null)
            anchorHtmlDataList.add(countEnterURLData);

        return anchorHtmlDataList;
    }

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        super.validateSearchParameters(fieldValues);
        setBackLocation(fieldValues.get("backLocation"));
        setDocFormKey(fieldValues.get("docFormKey"));
        setReferencesToRefresh(fieldValues.get(KRADConstants.REFERENCES_TO_REFRESH));
        List<? extends BusinessObject> results = MMServiceLocator.getCheckinOrderService()
                .getOrderLinesForLookup(fieldValues);
        for (int i = 0; i < results.size(); i++) {

            OrderDetail orderDetail = (OrderDetail) results.get(i);
            // if(!orderDetail.isLineCheckinable()){
            // remItems.add(orderDetail);
            // continue;
            // }

            if (!MMUtil.isCollectionEmpty(orderDetail.getCheckinDetails())) {
                orderDetail.setVendorShipmentNumber(orderDetail.getCheckinDetails().get(0)
                        .getCheckinDoc().getVendorShipmentNbr());
                orderDetail.setVendorRefNumber(orderDetail.getCheckinDetails().get(0)
                        .getCheckinDoc().getVendorRefNbr());

            }
        }

        return results;
    }

    protected HtmlData getReceiveLineUrl(OrderDetail orderDetail) {

        Properties params = new Properties();
        AnchorHtmlData anchorHtmlData = null;

        params
                .setProperty(KRADConstants.DISPATCH_REQUEST_PARAMETER,
                        KRADConstants.DOC_HANDLER_METHOD);
        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
        params.setProperty(KRADConstants.DOCUMENT_TYPE_NAME, MMConstants.CHECKIN_DOC_TYPE);
        params.setProperty(MMConstants.CheckinDocument.ORDER_DOC_NUMBER, orderDetail
                .getOrderDocumentNbr());
        params.setProperty(MMConstants.CheckinDocument.RECEIVE_ACTION,
                MMConstants.CheckinDocument.RECEIVE_LINE_ACTION);
        params.setProperty(MMConstants.CheckinDocument.ORDER_DETAIL_ID, String.valueOf(orderDetail
                .getOrderDetailId()));
        String href = getKualiConfigurationService().getPropertyValueAsString(
                KRADConstants.APPLICATION_URL_KEY)
                + "/"
                + UrlFactory.parameterizeUrl(
                        MMConstants.CheckinDocument.CHECKIN_DOCUMENT_RECEIVE_ACTION, params);
        anchorHtmlData = new AnchorHtmlData(href, "",
            MMConstants.CheckinDocument.RECEIVE_LINE_LABEL);
        return anchorHtmlData;
    }

    protected HtmlData getReceiveOrderUrl(OrderDetail orderDetail) {

        Properties params = new Properties();
        AnchorHtmlData anchorHtmlData = null;

        params
                .setProperty(KRADConstants.DISPATCH_REQUEST_PARAMETER,
                        KRADConstants.DOC_HANDLER_METHOD);
        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
        params.setProperty(MMConstants.CheckinDocument.ORDER_DOC_NUMBER, orderDetail
                .getOrderDocumentNbr());
        params.setProperty(KRADConstants.DOCUMENT_TYPE_NAME, MMConstants.CHECKIN_DOC_TYPE);
        String href = getKualiConfigurationService().getPropertyValueAsString(
                KRADConstants.APPLICATION_URL_KEY)
                + "/"
                + UrlFactory.parameterizeUrl(
                        MMConstants.CheckinDocument.CHECKIN_DOCUMENT_RECEIVE_ACTION, params);
        anchorHtmlData = new AnchorHtmlData(href, "",
            MMConstants.CheckinDocument.RECEIVE_ORDER_LABEL);
        return anchorHtmlData;
    }

}