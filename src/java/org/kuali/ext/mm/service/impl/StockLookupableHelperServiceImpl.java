package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.Stock;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.businessobject.StockCost;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StockLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

    private static final long serialVersionUID = 1L;

    /**
     * Child classes should override this method if they want to return some other action urls.
     * 
     * @returns This default implementation returns links to edit and copy maintenance action for the current maintenance record if
     *          the business object class has an associated maintenance document. Also checks value of allowsNewOrCopy in
     *          maintenance document xml before rendering the copy link.
     * @see org.kuali.rice.kns.lookup.LookupableHelperService#getCustomActionUrls (org.kuali.rice.kns.bo.BusinessObject,
     *      java.util.List, java.util.List pkNames)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {

        List<HtmlData> htmlDataList = new ArrayList<HtmlData>();
        Stock stock = (Stock) businessObject;

        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        HashMap primaryKeys = new HashMap();
        primaryKeys.put(MMConstants.Stock.STOCK_ID, stock.getStockId());
        primaryKeys.put(MMConstants.CostCode.STOCK_COST_CODE, Stock.getCurrentStockPriceCode());

        HashMap balanceKeys = new HashMap();
        balanceKeys.put("Stock_Id", stock.getStockId());
        List<StockBalance> stockBalanceList = (List<StockBalance>) bOS.findMatching(
                StockBalance.class, balanceKeys);
        boolean displayLink = false;
        boolean emptyBins = false;
        if (!stockBalanceList.isEmpty()) {
            for (int i = 0; i < stockBalanceList.size(); i++) {
                StockBalance sBal = stockBalanceList.get(i);
                if (sBal.getQtyOnHand() > 0) {
                    displayLink = true;
                }else{
                    emptyBins = true;
                }
                
            }
        }

        List<StockCost> stockCostList = (List<StockCost>) bOS.findMatching(StockCost.class,
                primaryKeys);
        if (!stockCostList.isEmpty()) {
            htmlDataList.add(getUrlData(businessObject, "edit", "Adjust Unit of Issue", pkNames));
            StockCost sC = stockCostList.get(0);
            htmlDataList.add(getUrlData(sC, "edit", "Adjust Cost", pkNames));
            boolean canAdjustZeroBinBalance = KimApiServiceLocator.getPermissionService().isAuthorized(
                    GlobalVariables.getUserSession().getPrincipalId(), MMConstants.MM_NAMESPACE,
                    MMConstants.Stock.ADJUST_ZERO_BIN_BALANCE_PERMISSION, null);
            if (displayLink  || canAdjustZeroBinBalance) {
                htmlDataList.add(getUrlData(businessObject, "edit", "Adjust Stock Balance", pkNames));
                htmlDataList.add(getUrlData(businessObject, "edit", "Transfer Stock", pkNames));
            }
        }
        if(emptyBins){
            htmlDataList.add(new AnchorHtmlData("../unassignBin.do?methodToCall=start&stockId=" + stock.getStockId(), "start", "Unassign Empty Bins"));
        }

        return htmlDataList;
    }

    /**
     * This method constructs an AnchorHtmlData. This method can be overridden by child classes if they want to construct the html
     * data in a different way. Foe example, if they want different type of html tag, like input/image.
     * 
     * @param businessObject
     * @param methodToCall
     * @param displayText
     * @param pkNames
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    protected AnchorHtmlData getUrlData(BusinessObject businessObject, String methodToCall,
            String displayText, List pkNames) {

        String href = null; // getActionUrlHref(businessObject, methodToCall, pkNames);

        if (displayText.equalsIgnoreCase("Adjust Unit of Issue")) {
            Stock stock = (Stock) businessObject;
            href = "maintenance.do?businessObjectClassName=org.kuali.ext.mm.businessobject.Stock&stockId="
                    + stock.getStockId() + "&actionCode=adjustUnitOfIssue&methodToCall=edit";
        }
        else if (displayText.equalsIgnoreCase("Adjust Cost")) {
            StockCost sC = (StockCost) businessObject;
            href = "maintenance.do?businessObjectClassName=org.kuali.ext.mm.businessobject.StockCost&stockCostId="
                    + sC.getStockCostId() + "&methodToCall=edit";
        }
        else if (displayText.equalsIgnoreCase("Adjust Stock Balance")) {
            Stock stock = (Stock) businessObject;
            href = "maintenance.do?businessObjectClassName=org.kuali.ext.mm.businessobject.Stock&stockId="
                    + stock.getStockId() + "&actionCode=adjustBalance&methodToCall=edit";
        }
        else {
            Stock stock = (Stock) businessObject;
            href = "maintenance.do?businessObjectClassName=org.kuali.ext.mm.businessobject.Stock&stockId="
                    + stock.getStockId() + "&actionCode=transferStock&methodToCall=edit";
        }

        AnchorHtmlData anchorHtmlData = new AnchorHtmlData(href, methodToCall, displayText);
        return anchorHtmlData;
    }
}
