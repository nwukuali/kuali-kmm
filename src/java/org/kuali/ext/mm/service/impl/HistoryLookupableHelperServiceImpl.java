package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.StockHistory;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.*;

public class HistoryLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {
    private static final long serialVersionUID = -7848259047868378853L;

    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject bo, List pkNames) {
        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(bo, pkNames);
        StockHistory stockHistory = (StockHistory) bo;

        if (anchorHtmlDataList == null)
            anchorHtmlDataList = new ArrayList<HtmlData>();

        if (ObjectUtils.isNull(stockHistory))
            return anchorHtmlDataList;

        Properties params = new Properties();
        params.setProperty("stockId", stockHistory.getStockId());

        String href = getKualiConfigurationService().getPropertyValueAsString(
                KRADConstants.APPLICATION_URL_KEY)
                + "/" + UrlFactory.parameterizeUrl("stockHistoryLookup.do", params);

        AnchorHtmlData anchorHtmlData = new AnchorHtmlData(href, "", "Stock History");
        anchorHtmlData.setTarget("_blank");
        anchorHtmlData.setDisplayText("Stock History");
        anchorHtmlData.setTitle("Stock History");

        anchorHtmlDataList.add(anchorHtmlData);

        return anchorHtmlDataList;
    }

    /**
     * @see org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl#getSearchResults(java.util.Map)
     */
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        List<StockHistory> searchResults = (List<StockHistory>) super.getSearchResults(fieldValues);
        sortResults(searchResults);
        return searchResults;
    }

    /**
     * @param searchResults
     */
    private void sortResults(List<StockHistory> searchResults) {
        Collections.sort(searchResults, new Comparator<StockHistory>() {
            /**
             * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
             */
            public int compare(StockHistory o1, StockHistory o2) {
                int stockCompare = o1.getStockId().compareTo(o2.getStockId());
                if (stockCompare == 0) {
                    return o2.getHistoryTransTimestamp().compareTo(o1.getHistoryTransTimestamp());
                }
                return stockCompare;
            }
        });
    }

    /**
     * @see org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl#getSearchResultsUnbounded(java.util.Map)
     */
    @Override
    public List<? extends BusinessObject> getSearchResultsUnbounded(Map<String, String> fieldValues) {
        List<StockHistory> searchResultsUnbounded = (List<StockHistory>) super
                .getSearchResultsUnbounded(fieldValues);
        sortResults(searchResultsUnbounded);
        return searchResultsUnbounded;
    }

}
