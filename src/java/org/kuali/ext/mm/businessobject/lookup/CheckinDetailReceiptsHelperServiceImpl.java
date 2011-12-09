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
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMUtil;
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
public class CheckinDetailReceiptsHelperServiceImpl extends KualiLookupableHelperServiceImpl {

    private static final long serialVersionUID = 5716442702059145256L;

    /**
     * <li><portal:portalLink displayTitle="true" title="Cycle Count Entry Ram"
     * url="initiateCycleCountEntry.do?methodToCall=docHandler &command=displayDocSearchView&docId=3473" /></li>
     */
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject bo, List pkNames) {
        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(bo, pkNames);
        OrderDetail cobj = (OrderDetail) bo;

        if (anchorHtmlDataList == null)
            anchorHtmlDataList = new ArrayList<HtmlData>();

        HtmlData countEnterURLData = this.getReceiveOrderUrl(cobj);

        if (countEnterURLData != null)
            anchorHtmlDataList.add(countEnterURLData);

        return anchorHtmlDataList;
    }

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        super.validateSearchParameters(fieldValues);
        setBackLocation(fieldValues.get("backLocation"));
        setDocFormKey(fieldValues.get("docFormKey"));
        setReferencesToRefresh(fieldValues.get(KNSConstants.REFERENCES_TO_REFRESH));
        List<? extends BusinessObject> results = MMServiceLocator.getCheckinOrderService()
                .getCheckinDocsForCorrection(fieldValues);

        for (int i = 0; i < results.size(); i++) {

            OrderDetail orderDetail = (OrderDetail) results.get(i);

            if (!MMUtil.isCollectionEmpty(orderDetail.getCheckinDetails())) {
                orderDetail.setVendorShipmentNumber(orderDetail.getCheckinDetails().get(0)
                        .getCheckinDoc().getVendorShipmentNbr());
                orderDetail.setVendorRefNumber(orderDetail.getCheckinDetails().get(0)
                        .getCheckinDoc().getVendorRefNbr());

            }
        }
        return results;
    }

    protected HtmlData getReceiveOrderUrl(OrderDetail cobj) {

        Properties params = new Properties();
        AnchorHtmlData anchorHtmlData = null;

        params
                .setProperty(KNSConstants.DISPATCH_REQUEST_PARAMETER,
                        KNSConstants.DOC_HANDLER_METHOD);
        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
        params.setProperty(MMConstants.CheckinDocument.DOCUMENT_NUMBER, cobj.getOrderDocumentNbr());
        params
                .setProperty(MMConstants.CheckinDocument.ORDER_DOC_NUMBER, cobj
                        .getOrderDocumentNbr());
        params.setProperty(KNSConstants.DOCUMENT_TYPE_NAME,
                MMConstants.ReceiptCorrection.DOCUMENT_TYPE);
        String href = getKualiConfigurationService().getPropertyString(
                KNSConstants.APPLICATION_URL_KEY)
                + "/"
                + UrlFactory.parameterizeUrl(
                        MMConstants.ReceiptCorrection.RECEIPT_CORRECTION_ACTION, params);
        anchorHtmlData = new AnchorHtmlData(href, "",
            MMConstants.ReceiptCorrection.RECEIPT_CORRECTION_LABEL);
        return anchorHtmlData;
    }

}