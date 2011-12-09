/**
 * 
 */
package edu.msu.ebsp.mm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Bin;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.PickListHelperService;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.service.impl.PickListHelperServiceImpl;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.ParameterService;
import org.kuali.rice.kns.util.ObjectUtils;


/**
 * @author schneppd
 *
 */
public class MSUPickListHelperServiceImpl implements PickListHelperService {
protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PickListHelperServiceImpl.class);
    
    private BusinessObjectService businessObjectService;
    
    private StockService stockService;
    
    private ParameterService parameterService;
        
    /**
     * @see org.kuali.ext.mm.service.PickListHelperService#getBinsForPicking(org.kuali.ext.mm.businessobject.Stock, java.lang.Integer, java.lang.String)
     */
    public List<Bin> getBinsForPicking(CatalogItem item, Integer quantity) {
        LOG.debug("Using MSU implementation to get bins for picking.");
        
        item.refreshReferenceObject(MMConstants.CatalogItem.CATALOG);
        String warehouseCode = item.getCatalog().getWarehouseCd();
        
        List<String> zoneList = getCarouselZonesForWarehouse(warehouseCode);
        List<Bin> availableBins = stockService.getAvailableBinsForStockId(item.getStockId(),
                warehouseCode, 
                MMConstants.StockBalance.LAST_CHECKIN_DATE,
                true);
        Integer remainingQtyCommitted = stockService.getCommittedCatalogItemQuantity(item.getCatalogItemId());
        
        availableBins = sortCarouselBinsToFront(availableBins, zoneList);
        List<Bin> binsForPick = new ArrayList<Bin>();
        Integer remainingQty = quantity;

        for (Bin bin : availableBins) {
            Integer binQuantity = bin.getStockBalance().getQtyOnHand();
            //Skip over committed quantity
            if(remainingQtyCommitted > 0) {
                remainingQtyCommitted -= binQuantity;
                if(remainingQtyCommitted > 0)
                    continue;
                binQuantity = Math.abs(remainingQtyCommitted);
            }
            
            if(remainingQty <= 0)
                break;
            binsForPick.add(bin);
            remainingQty -= binQuantity;
        }
        
        if(binsForPick.isEmpty() && !availableBins.isEmpty())
            binsForPick.add(availableBins.get(0));

        return binsForPick;
    }
    
    
    /**
     * Sorts a list of bins by putting all of the bins in the zone list
     * in the front.  Maintains any previous sorts as secondary.
     * 
     * @param availableBins
     * @param zoneList
     * @return a list of bins sorted with carousel zones first.
     */
    private List<Bin> sortCarouselBinsToFront(List<Bin> availableBins, List<String> zoneList) {
        List<Bin> sortedBins = new ArrayList<Bin>();
        List<Bin> nonCarouselBins = new ArrayList<Bin>();
        
        for(Bin bin : availableBins) {
            if(ObjectUtils.isNotNull(bin.getZone())
                    && zoneList.contains(bin.getZone().getZoneCd())) {
                sortedBins.add(bin);
            }
            else {
                nonCarouselBins.add(bin);
            }
        }
        sortedBins.addAll(nonCarouselBins);        
        return sortedBins;        
    }

    /**
     * Used to get all of the zones configured as carousel zones within their containing
     * warehouse as configured in System Parameters.
     *
     * @param warehouseZonesParameterValue
     * @return a list of ZonesCodes for carousel zones in a warehouse.
     */
    private List<String> getCarouselZonesForWarehouse(String warehouseCode) {
        List<String> zonesByWarehouse = KNSServiceLocator.getParameterService().getParameterValues(MMConstants.MM_NAMESPACE, MMConstants.Parameters.BATCH, MMConstants.Parameters.CAROUSEL_WAREHOUSE_ZONES);
        List<String> zoneList = new ArrayList<String>();

        for(String parm : zonesByWarehouse) {
            String[] parmSplit = parm.split("=");
            if(parmSplit.length < 2)
                continue;
            String warehouse = parmSplit[0].trim();
            if(StringUtils.equals(warehouseCode, warehouse)) {
                for(String zone : parmSplit[1].split(",")) {
                    zoneList.add(zone.trim());
                }
            }
        }

        return zoneList;
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

    public void setParameterService(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    public ParameterService getParameterService() {
        return parameterService;
    }

}
