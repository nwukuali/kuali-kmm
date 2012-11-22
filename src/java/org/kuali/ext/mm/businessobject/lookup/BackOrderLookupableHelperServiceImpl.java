package org.kuali.ext.mm.businessobject.lookup;

import org.kuali.ext.mm.businessobject.BackOrder;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class BackOrderLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

    /**
     * 
     */
    private static final long serialVersionUID = -4219449325418249405L;

    public BackOrderLookupableHelperServiceImpl() {
        super();
    }

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        return super.getSearchResults(fieldValues);
    }

    protected boolean allowsMaintenanceEditAction(BusinessObject businessObject) {
        boolean allow = super.allowsMaintenanceEditAction(businessObject);
        BackOrder backorder = (BackOrder)businessObject;
        return allow && !backorder.isFilled() && !backorder.isCanceled();
    }
}
