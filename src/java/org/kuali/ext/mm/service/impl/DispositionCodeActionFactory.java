package org.kuali.ext.mm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.ReturnActionService;


public class DispositionCodeActionFactory {

    private static final Map<String, ReturnActionService> objectMap = new HashMap<String, ReturnActionService>(5);

    private static final DispositionCodeActionFactory factory = new DispositionCodeActionFactory();

    private static boolean initialized;

    public static final DispositionCodeActionFactory getInstance() {
        return factory;
    }

    public static Map<String, ReturnActionService> getObjectMap() {
        if (!initialized) {
            objectMap.put(MMConstants.DispositionCode.BARGAIN_BARN,
                    (ReturnActionService) SpringContext.getBean("bargainBarnActionService"));
            objectMap.put(MMConstants.DispositionCode.HAZARDOUS,
                    (ReturnActionService) SpringContext.getBean("hazardousActionService"));
            objectMap.put(MMConstants.DispositionCode.RETURN_TO_SHELF,
                    (ReturnActionService) SpringContext.getBean("returnToShelfActionService"));
            objectMap.put(MMConstants.DispositionCode.RETURN_TO_VENDOR,
                    (ReturnActionService) SpringContext.getBean("returnToVendorActionService"));
            objectMap.put(MMConstants.DispositionCode.TRASH, (ReturnActionService) SpringContext
                    .getBean("trashActionService"));
            initialized = true;
        }
        return objectMap;
    }

    public ReturnActionService getDispositionAction(String actionCode) {
        synchronized (getObjectMap()) {
            return getObjectMap().get(actionCode);
        }
    }

}
