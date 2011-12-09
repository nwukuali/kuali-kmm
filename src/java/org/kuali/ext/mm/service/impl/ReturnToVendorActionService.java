/**
 *
 */
package org.kuali.ext.mm.service.impl;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.service.IReturnCommand;
import org.kuali.ext.mm.service.MMServiceLocator;


/**
 * @author rponraj
 *
 */
public class ReturnToVendorActionService extends BaseReturnAction {

    public ReturnToVendorActionService(){
        super(MMConstants.DispositionCode.RETURN_TO_VENDOR);
        objectCache.put(MMConstants.ReturnActionCode.WAREHS, new ReturnToVendorForCreateAction());
        objectCache.put(MMConstants.ReturnActionCode.REJECTED, new ReturnToVendorForRejectedAction());
        objectCache.put(MMConstants.ReturnActionCode.DEPT_CR, new ReturnToVendorForDeptNcAction());
        objectCache.put(MMConstants.ReturnActionCode.DEPT_NC, new ReturnToVendorForDeptCrAction());
        objectCache.put(MMConstants.ReturnActionCode.CUSTOMER, new ReturnToVendorForCustomerAction());
    }

    private class ReturnToVendorForRejectedAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail){
            //Do nothing as no impleentation is needed as of now
        }

    }

    @Override
    public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{

        String actionCode = rdoc.getReturnTypeCode();

        if(StringUtils.isEmpty(actionCode)){
            return false;
        }

        if(actionCode.equalsIgnoreCase(MMConstants.ReturnActionCode.WAREHS)){
            if(rdetail.isVendorCreditInd() ||
                     rdetail.isVendorReshipInd())
                return false;
        }

        return !rdetail.isVendorDispositionInd()
        && ((!rdetail.isVendorCreditInd()) ||
                (rdetail.isVendorCreditInd() && (actionCode.equalsIgnoreCase(MMConstants.ReturnActionCode.DEPT_CR)
                        ||actionCode.equalsIgnoreCase(MMConstants.ReturnActionCode.DEPT_NC) ||
                        actionCode.equalsIgnoreCase(MMConstants.ReturnActionCode.WAREHS))));

    }

    private class ReturnToVendorForCreateAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            //TODO
            //1) Inventory Adjustment for the cost and quantity

            MMServiceLocator.getInventoryAdjustmentActionService().execute(rdoc, rdetail);
        }

    }

    private class ReturnToVendorForCustomerAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            //TODO
            //1) Create a Return to Vendor Document with Action Code = DEPT_NC
            MMServiceLocator.getReturnToVendorReturnActionService().execute(rdoc, rdetail);
        }

    }

    /***********************************************************************************/
    /***
     *  The following inner classes are simply place holders
     *  as they don't have nay implementation
     */
    /**********************************************************************************/
    private class ReturnToVendorForDeptNcAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail){

        }

    }

    private class ReturnToVendorForDeptCrAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail){
            //do Nothing as of now as no implementation is needed
        }

    }


}
