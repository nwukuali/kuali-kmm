/**
 *
 */
package org.kuali.ext.mm.document.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.StockHistoryLookupObject;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.kns.web.struts.action.KualiAction;


/**
 * @author rponraj
 *
 */
public class StockHistoryLookupAction extends KualiAction{

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        StockHistoryLookupForm dataForm = (StockHistoryLookupForm) form;
        String stockId = request.getParameter(MMConstants.Stock.STOCK_ID);
        StockHistoryLookupObject result = MMServiceLocator.getStockHistoryService().getStockHistoryLookupObjectForStockId(stockId);
        dataForm.setStockHistoryLookupObject(result);
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
}