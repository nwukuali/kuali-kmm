package org.kuali.ext.mm.service;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.service.BusinessObjectLockingService;
import org.kuali.ext.mm.sys.batch.service.CatalogItemService;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.service.BusinessObjectService;

import javax.xml.namespace.QName;


public final class MMServiceLocator {

	private static final Logger LOG = Logger.getLogger(MMServiceLocator.class);

	public static final String BUSINESS_OBJECT_SERVICE = "businessObjectService";

	public static final String INVENTORY_ADJUSTMENT_ACTION_SERVICE = "inventoryAdjustmentReturnActionService";

	public static final String REORDER_RETURN_ACTION_SERVICE = "reorderReturnActionService";

	public static final String RETURN_TO_VENDOR_RETURN_ACTION_SERVICE = "returnToVendorReturnActionService";


    /**
	 * @param serviceName
	 *            the name of the service bean
	 * @return the service
	 */
	public static Object getService(String serviceName) {
		return getBean(serviceName);
	}
	
	public static <T> T getBean(Class<T> clazz) {
	    return SpringContext.getBean(clazz);
	}

	public static Object getBean(String serviceName) {
		if ( LOG.isDebugEnabled() ) {
			LOG.debug("Fetching service " + serviceName);
		}
		return GlobalResourceLoader.getResourceLoader().getService(new QName(serviceName));
	}

    public static RTVPDFService getRTVPDFService() {
        return getBean(RTVPDFService.class);
    }

	public static StockItemLookupService getStockItemLookupService() {
		return getBean(StockItemLookupService.class);
	}

	public static RentalService getRentalService() {
		return getBean(RentalService.class);
	}

	public static ReturnOrderService getReturnOrderService() {
		return getBean(ReturnOrderService.class);
	}
	
	public static OrderService getOrderService() {
        return getBean(OrderService.class);
    }

	public static StockService getStockService() {
		return getBean(StockService.class);
	}

	public static PickVerifyService getPickVerifyService() {
        return getBean(PickVerifyService.class);
    }

   public static RTVReportService getRtvReportService() {
        return getBean(RTVReportService.class);
    }

   public static CheckinOrderService getCheckinOrderService() {
		return getBean(CheckinOrderService.class);
	}

   public static ReOrderService getReOrderService() {
       return getBean(ReOrderService.class);
   }

	public static CountWorksheetReportService getCountWorksheetReportService() {
		return getBean(CountWorksheetReportService.class);
	}

	public static BusinessObjectLockingService getBusinessObjectLockingService() {
		return getBean(BusinessObjectLockingService.class);
	}

	public static StockHistoryService getStockHistoryService() {
		return getBean(StockHistoryService.class);
	}

    public static MMDocumentUtilService getMMDocumentUtilService() {
        return getBean(MMDocumentUtilService.class);
    }
    
    public static  ReceiptCorrectionService getReceiptCorrectionService(){
        return getBean(ReceiptCorrectionService.class);
    }
    
    public static  PunchOutVendorService getPunchOutVendorService(){
        return getBean(PunchOutVendorService.class);
    }
    
    public static  B2BPunchOutService getB2BPunchOutService(){
        return getBean(B2BPunchOutService.class);
    }
    
    public static  B2BPunchOutServiceLocator getB2BPunchOutServiceLocator(){
        return SpringContext.getBean(B2BPunchOutServiceLocator.class);
    }
    
    public static TrueBuyoutService getTrueBuyoutService() {
        return getBean(TrueBuyoutService.class);
    }
    
    public static BackOrderService getBackOrderService() {
        return getBean(BackOrderService.class);
    }
    
    public static BusinessObjectService getBusinessObjectService() {
        return (BusinessObjectService) getBean(BUSINESS_OBJECT_SERVICE);
    }

    public static  IReturnCommand getInventoryAdjustmentActionService(){
        return (IReturnCommand) getBean(INVENTORY_ADJUSTMENT_ACTION_SERVICE);
    }

    public static  IReturnCommand getReorderReturnActionService(){
        return (IReturnCommand) getBean(REORDER_RETURN_ACTION_SERVICE);
    }

    public static  IReturnCommand getReturnToVendorReturnActionService(){
        return (IReturnCommand) getBean(RETURN_TO_VENDOR_RETURN_ACTION_SERVICE);
    }

    public static CatalogItemService getCatalogItemService() {
        return getBean(CatalogItemService.class);        
    }

    /**
     * 
     */
    public static MarkupService getMarkupService() {
        return getBean(MarkupService.class);        
    }
    
    public static CatalogService getCatalogService() {
        return getBean(CatalogService.class);
    }

}
