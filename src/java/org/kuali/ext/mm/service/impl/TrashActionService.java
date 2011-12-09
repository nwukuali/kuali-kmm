/**
 *
 */
package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.service.IReturnCommand;


/**
 * @author rponraj
 */
public class TrashActionService extends BaseReturnAction {

    public TrashActionService() {
        super(MMConstants.DispositionCode.TRASH);
        objectCache.put(MMConstants.ReturnActionCode.WAREHS, new TrashForCreateAction());
        objectCache.put(MMConstants.ReturnActionCode.REJECTED, new TrashForRejectedAction());
        objectCache.put(MMConstants.ReturnActionCode.DEPT_CR, new TrashForDeptNcAction());
        objectCache.put(MMConstants.ReturnActionCode.DEPT_NC, new TrashForDeptCrAction());
        objectCache.put(MMConstants.ReturnActionCode.CUSTOMER, new TrashForCustomerAction());
    }

    @Override
    public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail) {
        // VendorDispositionInd should be ste to true
        return rdetail.isVendorDispositionInd();
    }

    private class TrashForCreateAction implements IReturnCommand {

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
        }

    }

    /** ******************************************************************************** */
    /*******************************************************************************************************************************
     * The following inner classes are simply place holders as they don't have any implementation
     */
    /** ******************************************************************************* */

    private class TrashForRejectedAction implements IReturnCommand {

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail) {
        }

    }

    private class TrashForDeptNcAction implements IReturnCommand {

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
        }

    }

    private class TrashForDeptCrAction implements IReturnCommand {

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
        }


    }

    private class TrashForCustomerAction implements IReturnCommand {

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
        }


    }

}
