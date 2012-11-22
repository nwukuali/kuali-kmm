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
import org.kuali.ext.mm.businessobject.BinLookable;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * This class overrids the base getActionUrls method
 */
public class BinLookableHelperServiceImpl extends KualiLookupableHelperServiceImpl {
    /**
     *
     */
    private static final long serialVersionUID = -2890488922029409828L;

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        List<BinLookable> results = (List<BinLookable>) super.getSearchResults(fieldValues);
        
        for (int i = 0; i < results.size(); i=i) {
            BinLookable binLookable = results.get(i);
            if(null != binLookable.getStockBalance()){
                StockBalance sBal = binLookable.getStockBalance();
                //Keep only the bins that are empty or belong to the stock
                if(ObjectUtils.isNotNull(sBal) && sBal.getQtyOnHand() != null && sBal.getQtyOnHand().intValue() != 0 
                        && StringUtils.isBlank(fieldValues.get("stockBalance.stock.stockDistributorNbr"))){
                    results.remove(i);                       
                }
                else {                
                    // Only increment i if the item was NOT removed.  Otherwise it skips the next item.
                    i++;
                }
            }                                            
        }        
        return results;
    }
}