/**
 * 
 */
package org.kuali.ext.mm.document.web.struts;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.core.api.util.RiceConstants;
import org.kuali.rice.kns.util.KNSGlobalVariables;
import org.kuali.rice.kns.web.struts.action.KualiAction;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.TransactionalServiceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author harsha07
 */
public class UnassignBinAction extends KualiAction {
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        UnassignBinForm binForm = (UnassignBinForm) form;
        String stockId = binForm.getStockId();
        if (StringUtils.isNotBlank(stockId)) {
            BusinessObjectService bos = MMServiceLocator.getBusinessObjectService();
            HashMap<String, String> fieldValues = new HashMap<String, String>();
            fieldValues.put("stockId", stockId);
            Collection matches = bos.findMatching(CatalogItem.class, fieldValues);
            if (matches != null && !matches.isEmpty()) {
                CatalogItem catalogItem = (CatalogItem) TransactionalServiceUtils
                        .retrieveFirstAndExhaustIterator(matches.iterator());
                binForm.setCatalogItem(catalogItem);
                if (ObjectUtils.isNotNull(catalogItem.getStock())) {
                    List<StockBalance> stockBalances = catalogItem.getStock().getStockBalances();
                    if (stockBalances.isEmpty()) {
                        binForm.setReadOnly(true);
                    }
                    for (StockBalance stockBalance : stockBalances) {
                        if (stockBalance.getQtyOnHand() == 0) {
                            binForm.getEmptyLocations().add(stockBalance);
                        }
                    }
                }
            }
            else {
                binForm.setReadOnly(true);
            }

        }
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    public ActionForward submit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        UnassignBinForm binForm = (UnassignBinForm) form;
        List<StockBalance> emptyLocations = binForm.getEmptyLocations();
        boolean deleted = false;
        for (StockBalance stockBalance : emptyLocations) {
            if (stockBalance.isUnassign()) {
                stockBalance.delete();
                deleted = true;
            }
        }
        if (deleted) {
            KNSGlobalVariables.getMessageList().add(MMKeyConstants.INFO_EMPTY_BINS_UNASSIGNED);
            binForm.setReadOnly(true);
        }
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return mapping.findForward(KRADConstants.MAPPING_PORTAL);
    }
}
