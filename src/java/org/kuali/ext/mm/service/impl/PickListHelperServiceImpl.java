/**
 * 
 */
package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.Bin;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.PickListHelperService;
import org.kuali.ext.mm.service.StockService;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.util.ArrayList;
import java.util.List;


/**
 * Default implementation of PickLIstHelperService
 * 
 * @author schneppd
 *
 */
public class PickListHelperServiceImpl implements PickListHelperService {
    protected static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PickListHelperServiceImpl.class);
    
    private BusinessObjectService businessObjectService;
    
    private StockService stockService;
        
    /**
     * @see org.kuali.ext.mm.service.PickListHelperService#getBinsForPicking(org.kuali.ext.mm.businessobject.Stock, java.lang.Integer, java.lang.String)
     */
    public List<Bin> getBinsForPicking(CatalogItem item, Integer quantity) {
        LOG.debug("Using default implementation to get bins for picking.");
        
        item.refreshReferenceObject(MMConstants.CatalogItem.CATALOG);
        String warehouseCode = item.getCatalog().getWarehouseCd();
        
        List<Bin> availableBins = stockService.getAvailableBinsForStockId(item.getStockId(), 
                warehouseCode, 
                MMConstants.StockBalance.LAST_CHECKIN_DATE,
                true);
        List<Bin> binsForPick = new ArrayList<Bin>();
        Integer remainingQty = quantity;
        
        for (Bin bin : availableBins) {
            if(remainingQty <= 0)
                break;
            binsForPick.add(bin);
            remainingQty -= bin.getStockBalance().getQtyOnHand();
        }

        return binsForPick;
    }

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }


    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public StockService getStockService() {
        return stockService;
    }

}
