package org.kuali.ext.mm.cart.web.struts;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.common.sys.MMConstants;


public class ShopCartOrderHistoryAction extends ShopCartOrdersAction {

	@Override
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		refreshOrderDocumentList((ShopCartOrdersForm)form, request);
        return mapping.findForward(MMConstants.MAPPING_BASIC);
	}

    /**
     * Override this method to get order history details based on the date.
     * 
     * @param oForm
     * @param request
     */
	@Override
    protected void refreshOrderDocumentList(ShopCartOrdersForm oForm, HttpServletRequest request) {
	    ShopCartOrderHistoryForm ohForm = (ShopCartOrderHistoryForm)oForm;
	    Calendar fromDate = new GregorianCalendar(ohForm.getSelectedYear(), ohForm.getSelectedMonth(), 1);

        Timestamp fromTimestamp = new Timestamp(fromDate.getTimeInMillis());
        fromDate.add(Calendar.MONTH, 1);
        Timestamp toTimestamp = new Timestamp(fromDate.getTimeInMillis());

        ohForm.setOrderDocumentList(ShopCartServiceLocator.getShopCartOrderService().getOrderHistory(ohForm.getCustomerProfile(), fromTimestamp, toTimestamp));
        
        updatePageInfo(ohForm, request);
    }
}
