package org.kuali.ext.mm.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.businessobject.BackOrder;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.dataaccess.OrderStatusDao;
import org.kuali.ext.mm.service.BackOrderService;
import org.kuali.ext.mm.service.OrderService;
import org.kuali.ext.mm.service.PickListService;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class BackOrderServiceImpl implements BackOrderService {
    protected static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BackOrderServiceImpl.class);
    
    private BusinessObjectService businessObjectService;
    
	/* (non-Javadoc)
	 * @see org.kuali.ext.mm.service.BackOrderService#createBackOrder(org.kuali.ext.mm.businessobject.PickListLine)
	 */
	public BackOrder createBackOrder(PickListLine line) {
		BackOrder backOrder = new BackOrder();

		backOrder.setFromPickListLineId(line.getPickListLineId());
		backOrder.setBackOrderStockQty(line.getBackOrderQty());
		backOrder.setFilled(false);
		backOrder.setStock(line.getStock());
		backOrder.setStockId(line.getStockId());

		return backOrder;
	}

	/* (non-Javadoc)
	 * @see org.kuali.ext.mm.service.BackOrderService#save(org.kuali.ext.mm.businessobject.BackOrder)
	 */
	public void save(BackOrder bo) {
		KNSServiceLocator.getBusinessObjectService().save(bo);
	}

	public void relieveBackOrder(BackOrder backOrder, boolean strictQuantity) {	    
		PickListService pickListService = SpringContext.getBean(PickListService.class);		
		List<PickListLine> createdLines = pickListService.createPickListLinesFromBackOrder(backOrder, strictQuantity);
		if(createdLines != null && !createdLines.isEmpty()) {		
		    backOrder.setToPickListLines(createdLines);
		    backOrder.setFilled(true);
		    save(backOrder);
		}
	}
	
    /**
     * @see org.kuali.ext.mm.service.BackOrderService#cancelBackOrder(org.kuali.ext.mm.businessobject.BackOrder)
     */
    public void cancelBackOrder(BackOrder backOrder) {
        if(ObjectUtils.isNotNull(backOrder.getToPickListLines()) 
                && !backOrder.getToPickListLines().isEmpty()) {
            for(PickListLine line : backOrder.getToPickListLines()) {
                if(!MMConstants.PickStatusCode.PICK_STATUS_INIT.equals(line.getPickStatusCodeCd())) {
                    LOG.warn("Backorder (" + backOrder.getBackOrderId() + ") cannot be canceled.  One or more pick list lines have already been asigned to a pick ticket.");
                    backOrder.setCanceled(false);
                    return;
                }
            }
            getBusinessObjectService().delete(backOrder.getToPickListLines());            
        }
        backOrder.setFilled(false);
        backOrder.setCanceled(true);
        backOrder.setBackOrderStockQty(backOrder.getFromPickListLine().getBackOrderQty());
        backOrder.getFromPickListLine().refreshReferenceObject(MMConstants.PickListLine.ORDER_DETAIL);
        //Needs to save before checkAndUpdate to compute correct picked totals
        save(backOrder);
        checkAndUpdateOrderComplete(backOrder.getFromPickListLine().getOrderDetail());
    }
    
    /**
     * @see org.kuali.ext.mm.service.BackOrderService#reduceBackOrder(org.kuali.ext.mm.businessobject.BackOrder)
     */
    public void reduceBackOrder(BackOrder backOrder) {
        if(backOrder.getBackOrderStockQty().equals(0)) {
            cancelBackOrder(backOrder);
        }
        else if(ObjectUtils.isNotNull(backOrder.getToPickListLines()) 
                && !backOrder.getToPickListLines().isEmpty()) {
            for(PickListLine line : backOrder.getToPickListLines()) {
                if(!MMConstants.PickStatusCode.PICK_STATUS_INIT.equals(line.getPickStatusCodeCd())) {
                    LOG.warn("Backorder (" + backOrder.getBackOrderId() + ") cannot be altered.  One or more pick list lines have already been asigned to a pick ticket.");
                    return;
                }
            }            
            getBusinessObjectService().delete(backOrder.getToPickListLines());
            relieveBackOrder(backOrder, false);
        }
    }
    
    /**
     * Updates the order detail and order document if 
     * the canceled quantity + already picked total = total ordered
     * 
     * @param orderDetail
     */
    private void checkAndUpdateOrderComplete(OrderDetail detail) {
        OrderService orderService = SpringContext.getBean(OrderService.class);
        if(orderService.isOrderDetailComplete(detail.getOrderDetailId())) {
            detail.setOrderStatusCd(MMConstants.OrderStatus.ORDER_LINE_COMPLETE);
            getBusinessObjectService().save(detail);
            OrderStatusDao orderStatusDao = SpringContext.getBean(OrderStatusDao.class);
            if(orderStatusDao.isOrderComplete(detail.getOrderDocumentNbr())) {
                orderStatusDao.updateOrderComplete(detail.getOrderDocumentNbr());
            }
        }        
    }

	/* (non-Javadoc)
	 * @see org.kuali.ext.mm.service.BackOrderService#getBackOrderReliefDate(org.kuali.ext.mm.businessobject.PickListLine)
	 */
	public String getBackOrderReliefDate(PickListLine line) {
		String reliefDate = "*******";

		//TODO:  Create service to check backOrders for outstanding POs
		///If exists, get date from PO.  If none exists return ******

		return reliefDate;
	}
	
	public Collection<BackOrder> getBackOrdersForOrderDetail(Integer orderDetailId) {
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        String detailKey = MMConstants.BackOrder.FROM_PICK_LIST_LINE + "." 
                + MMConstants.PickListLine.ORDER_DETAIL_ID;
        fieldValues.put(detailKey, orderDetailId);
        String orderBy = MMConstants.BackOrder.FROM_PICK_LIST_LINE + "." 
                + MMConstants.PickListLine.CREATED_DATE;

        return getBusinessObjectService().findMatchingOrderBy(BackOrder.class, fieldValues, orderBy, true);
    }
	
	public Collection<BackOrder> getUnfilledBackOrdersForStock(String stockId) {
	    Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.BackOrder.STOCK_ID, stockId);
        fieldValues.put(MMConstants.BackOrder.FILLED, "N");
        fieldValues.put(MMConstants.BackOrder.CANCELED, "N");
        String orderBy = MMConstants.BackOrder.FROM_PICK_LIST_LINE + "." 
                + MMConstants.PickListLine.CREATED_DATE;

        return getBusinessObjectService().findMatchingOrderBy(BackOrder.class, fieldValues, orderBy, true);
	}

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    /**
     * @see org.kuali.ext.mm.service.BackOrderService#getAllActiveBackOrders()
     */
    public Collection<BackOrder> getAllActiveBackOrders() {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.BackOrder.FILLED, "N");
        fieldValues.put(MMConstants.BackOrder.CANCELED, "N");
        String orderBy = MMConstants.BackOrder.FROM_PICK_LIST_LINE + "." 
                + MMConstants.PickListLine.CREATED_DATE;
        return getBusinessObjectService().findMatchingOrderBy(BackOrder.class, fieldValues, orderBy, true);
    }

}
