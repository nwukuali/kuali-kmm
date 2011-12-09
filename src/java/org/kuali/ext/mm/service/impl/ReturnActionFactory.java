package org.kuali.ext.mm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.IReturnCommand;


public class ReturnActionFactory {

    private static final Map<String, IReturnCommand> objectMap = new HashMap<String, IReturnCommand>(8);

    private static final ReturnActionFactory factory = new ReturnActionFactory();

    private static boolean initialized;

    public static final ReturnActionFactory getInstance() {
        return factory;
    }

    private static Map<String, IReturnCommand> getObjectMap() {
        if (!initialized) {
            objectMap.put(MMConstants.ReturnActionCommand.DEPARTMENT_CREDIT_ACTION_SERVICE_CR,
                    (IReturnCommand) SpringContext.getBean("departmentCreditActionService"));
            objectMap.put(MMConstants.ReturnActionCommand.DEPARTMENT_CREDIT_ACTION_SERVICE_NC,
                    (IReturnCommand) SpringContext.getBean("noAction"));
            objectMap.put(MMConstants.ReturnActionCommand.NO_ACTION_COMMAND,
                    (IReturnCommand) SpringContext.getBean("noAction"));
            objectMap.put(
                    MMConstants.ReturnActionCommand.INVENTORY_ADJUSTMENT_RETURN_ACTION_SERVICE,
                    (IReturnCommand) SpringContext
                            .getBean("inventoryAdjustmentReturnActionService"));
            objectMap.put(MMConstants.ReturnActionCommand.RETURN_TO_VENDOR_RETURN_ACTION_SERVICE,
                    (IReturnCommand) SpringContext.getBean("returnToVendorReturnActionService"));
            objectMap.put(MMConstants.ReturnActionCommand.VENDOR_ISSUE_CREDIT_ACTION_SERVICE,
                    (IReturnCommand) SpringContext.getBean("vendorIssueCreditActionService"));
            objectMap.put(MMConstants.ReturnActionCommand.VENDOR_RESHIPMENT_ACTION_SERVICE,
                    (IReturnCommand) SpringContext.getBean("vendorReshipmentActionService"));
            objectMap.put(MMConstants.ReturnActionCommand.REORDER_RETURN_ACTION_SERVICE,
                    (IReturnCommand) SpringContext.getBean("reorderReturnActionService"));
            initialized = true;
        }
        return objectMap;
    }

    public IReturnCommand getReturnAction(String actionCode) {
        synchronized (getObjectMap()) {
            return getObjectMap().get(actionCode);
        }
    }

    public List<IReturnCommand> getReturnActions(ReturnDetail rdetail) {

        String actionCode = rdetail.getActionCd();
        List<IReturnCommand> result = new ArrayList<IReturnCommand>(0);

        if (!StringUtils.isEmpty(actionCode)) {
            synchronized (getObjectMap()) {

                IReturnCommand obj = getObjectMap().get(actionCode);

                if (obj != null)
                    result.add(obj);
            }
        }

        if (rdetail.isVendorCreditInd()) {
            result.add(getObjectMap().get(
                    MMConstants.ReturnActionCommand.VENDOR_ISSUE_CREDIT_ACTION_SERVICE));
        }

        if (rdetail.isVendorReshipInd()) {
            result.add(getObjectMap().get(
                    MMConstants.ReturnActionCommand.VENDOR_RESHIPMENT_ACTION_SERVICE));
        }

        if (rdetail.isDepartmentCreditInd())
            result.add(getObjectMap().get(
                    MMConstants.ReturnActionCommand.DEPARTMENT_CREDIT_ACTION_SERVICE_CR));
        else
            result.add(getObjectMap().get(
                    MMConstants.ReturnActionCommand.DEPARTMENT_CREDIT_ACTION_SERVICE_NC));

        return result;
    }


}
