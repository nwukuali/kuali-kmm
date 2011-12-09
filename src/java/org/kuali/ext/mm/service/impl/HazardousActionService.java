/**
 *
 */
package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.service.IReturnCommand;
import org.kuali.ext.mm.service.MMServiceLocator;


/**
 * @author rponraj
 *
 */
public class HazardousActionService extends BaseReturnAction {

    public HazardousActionService(){
        super(MMConstants.DispositionCode.HAZARDOUS);
        objectCache.put(MMConstants.ReturnActionCode.WAREHS, new HazardousForCreateAction());
        objectCache.put(MMConstants.ReturnActionCode.REJECTED, new HazardousForRejectedAction());
        objectCache.put(MMConstants.ReturnActionCode.DEPT_CR, new HazardousForDeptNcAction());
        objectCache.put(MMConstants.ReturnActionCode.DEPT_NC, new HazardousForDeptCrAction());
        objectCache.put(MMConstants.ReturnActionCode.CUSTOMER, new HazardousForCustomerAction());
    }

    private class HazardousForCreateAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            //TODO
            //Inventory Adjustment for the cost and quantity
            MMServiceLocator.getInventoryAdjustmentActionService().execute(rdoc, rdetail);
        }

    }

    /***********************************************************************************/
    /***
     *  The following inner classes are simply place holders
     *  as they don't have nay implementation
     */
    /**********************************************************************************/

    private class HazardousForRejectedAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return rdetail.isVendorDispositionInd();
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail){
            //Nothing needs to be done
            //Implementation is left out
        }

    }

    private class HazardousForDeptNcAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail){
        }

    }

    private class HazardousForDeptCrAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail){
        }

    }

    private class HazardousForCustomerAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail){
        }

    }

}
