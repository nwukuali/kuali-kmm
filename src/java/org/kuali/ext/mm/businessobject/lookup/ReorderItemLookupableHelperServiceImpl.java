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

import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.businessobject.ReorderItem;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.kns.web.ui.Column;
import org.kuali.rice.kns.web.ui.ResultRow;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.*;


/**
 * This class overrids the base getActionUrls method
 */
@SuppressWarnings("unchecked")
public class ReorderItemLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {
    private static final long serialVersionUID = 6358902406853301543L;

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {

        String catalogGroupCode = ((String[]) getParameters().get(
                MMConstants.ReorderItem.CATALOG_GROUP_CATALOG_CODE)) != null ? ((String[]) getParameters()
                .get(MMConstants.ReorderItem.CATALOG_GROUP_CATALOG_CODE))[0]
                : null;

        String catalogSubGroupCode = ((String[]) getParameters().get(
                MMConstants.ReorderItem.CATALOG_SUBGROUP_CATALOG_SUBGROUPCODE)) != null ? ((String[]) getParameters()
                .get(MMConstants.ReorderItem.CATALOG_SUBGROUP_CATALOG_SUBGROUPCODE))[0]
                : null;

        if (!StringUtils.isEmpty(catalogGroupCode)) {
            fieldValues.remove(MMConstants.ReorderItem.CATALOG_GROUP_CATALOG_CODE);
            getParameters().remove(MMConstants.ReorderItem.CATALOG_GROUP_CATALOG_CODE);
            fieldValues.put(MMConstants.ReorderItem.CATALOG_GROUPCODE, catalogGroupCode);
            getParameters().put(MMConstants.ReorderItem.CATALOG_GROUPCODE, new String[]{catalogGroupCode});
        }

        if (!StringUtils.isEmpty(catalogSubGroupCode)) {
            fieldValues.remove(MMConstants.ReorderItem.CATALOG_SUBGROUP_CATALOG_SUBGROUPCODE);
            getParameters().remove(MMConstants.ReorderItem.CATALOG_SUBGROUP_CATALOG_SUBGROUPCODE);
            fieldValues.put(MMConstants.ReorderItem.CATALOG_SUBGROUP_CATALOG_CODE,
                    catalogSubGroupCode);
            getParameters().put(MMConstants.ReorderItem.CATALOG_SUBGROUP_CATALOG_CODE, new String[]{catalogSubGroupCode});
        }

        List<? extends BusinessObject> results = super.getSearchResults(fieldValues);

        return results;
    }

    @Override
    public Collection performLookup(LookupForm lookupForm, Collection resultTable, boolean bounded) {
        Collection result = super.performLookup(lookupForm, resultTable, bounded);
        for(Object obj : resultTable){
            ResultRow row = (ResultRow) obj;

            List<Column> columns = row.getColumns();
            for (Iterator iterator = columns.iterator(); iterator.hasNext();) {

                Column col = (Column) iterator.next();
                if(col.getColumnAnchor() != null){
                    String url = ((AnchorHtmlData)col.getColumnAnchor()).getHref();
                    if(!StringUtils.isEmpty(url)){
                        url = "kr/".concat(url);
                    }
                    ((AnchorHtmlData)col.getColumnAnchor()).setHref(url);
                }
            }

        }
        return result;
    }

    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject bo, List pkNames) {
        List<HtmlData> anchorHtmlDataList = super.getCustomActionUrls(bo, pkNames);
        ReorderItem reorderItem = (ReorderItem) bo;

        if (anchorHtmlDataList == null)
            anchorHtmlDataList = new ArrayList<HtmlData>();

        HtmlData editURLData = this.getEditItemUrl(reorderItem);
        HtmlData allURLData = this.getAllItemUrl(reorderItem);

        if (editURLData != null)
            anchorHtmlDataList.add(editURLData);

        if (allURLData != null)
            anchorHtmlDataList.add(allURLData);

        return anchorHtmlDataList;
    }

    private HtmlData getEditItemUrl(ReorderItem reorderItem) {

        if (reorderItem == null)
            return null;

        Properties params = new Properties();
        AnchorHtmlData anchorHtmlData = null;

        params
                .setProperty(KRADConstants.DISPATCH_REQUEST_PARAMETER,
                        KRADConstants.DOC_HANDLER_METHOD);
        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
        params.setProperty(MMConstants.ReorderItem.EDIT_ITEMS_ACTION,
                MMConstants.ReorderItem.EDIT_REORDERED_ITEMS_LABEL);
        params.setProperty(MMConstants.Warehouse.WAREHOUSE_CD, reorderItem.getWarehouseCd());

        // PARAMETER SETTING AGREEMENT NUMBER
        if (!StringUtils.isEmpty(reorderItem.getAgreementNbr()))
            params.setProperty(MMConstants.ReorderItem.REORDER_AGREEMENT_NUMBER, reorderItem
                    .getAgreementNbr());

        if (!ObjectUtils.isNull(reorderItem.getCatalogSubgroupId()))
            params.setProperty(MMConstants.ReorderItem.REORDER_SUB_CATALOG_GROUP_NAME, reorderItem
                    .getCatalogSubgroupId() != null ? reorderItem.getCatalogSubgroupId().toString()
                    : "");

        if (!StringUtils.isEmpty(reorderItem.getCatalogGroupCode()))
            params.setProperty(MMConstants.ReorderItem.CATALOG_SUBGROUP_CATALOG_CODE, reorderItem
                    .getCatalogGroupCode());

        if (!StringUtils.isEmpty(reorderItem.getCatalogSubGroupCode()))
            params.setProperty(MMConstants.ReorderItem.CATALOG_SUBGROUP_CATALOG_SUBGROUPCODE,
                    reorderItem.getCatalogSubGroupCode());

        params.setProperty(KRADConstants.DOCUMENT_TYPE_NAME,
                MMConstants.ReOrderDocument.STORES_REORDER_DOCUMENT);
        String href = getKualiConfigurationService().getPropertyValueAsString(
                KRADConstants.APPLICATION_URL_KEY)
                + "/"
                + UrlFactory.parameterizeUrl(MMConstants.ReorderItem.REORDER_DOCUMENT_ACTION,
                        params);
        anchorHtmlData = new AnchorHtmlData(href, "",
            MMConstants.ReorderItem.EDIT_REORDERED_ITEMS_LABEL);
        return anchorHtmlData;
    }

    private HtmlData getAllItemUrl(ReorderItem reorderItem) {

        if (reorderItem == null || StringUtils.isEmpty(reorderItem.getAgreementNbr()))
            return null;

        Properties params = new Properties();
        AnchorHtmlData anchorHtmlData = null;

        params.setProperty(MMConstants.Warehouse.WAREHOUSE_CD, reorderItem.getWarehouseCd());

        params
                .setProperty(KRADConstants.DISPATCH_REQUEST_PARAMETER,
                        KRADConstants.DOC_HANDLER_METHOD);
        params.setProperty(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);
        params.setProperty(MMConstants.ReorderItem.EDIT_ITEMS_ACTION,
                MMConstants.ReorderItem.EDIT_ALL_ITEMS_IN_AGREEMENT_LABEL);
        params.setProperty(MMConstants.ReorderItem.REORDER_AGREEMENT_NUMBER, reorderItem
                .getAgreementNbr());
        params.setProperty(KRADConstants.DOCUMENT_TYPE_NAME,
                MMConstants.ReOrderDocument.STORES_REORDER_DOCUMENT);
        String href = getKualiConfigurationService().getPropertyValueAsString(
                KRADConstants.APPLICATION_URL_KEY)
                + "/"
                + UrlFactory.parameterizeUrl(MMConstants.ReorderItem.REORDER_DOCUMENT_ACTION,
                        params);
        anchorHtmlData = new AnchorHtmlData(href, "",
            MMConstants.ReorderItem.EDIT_ALL_ITEMS_IN_AGREEMENT_LABEL);
        return anchorHtmlData;

    }
}