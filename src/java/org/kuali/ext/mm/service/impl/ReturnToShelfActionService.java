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
public class ReturnToShelfActionService extends BaseReturnAction {

    public ReturnToShelfActionService(){
        super(MMConstants.DispositionCode.RETURN_TO_SHELF);
        objectCache.put(MMConstants.ReturnActionCode.WAREHS, new ReturnToShelfForCreateAction());
        objectCache.put(MMConstants.ReturnActionCode.REJECTED, new ReturnToShelfForRejectedAction());
        objectCache.put(MMConstants.ReturnActionCode.DEPT_CR, new ReturnToShelfForDeptNcAction());
        objectCache.put(MMConstants.ReturnActionCode.DEPT_NC, new ReturnToShelfForDeptCrAction());
        objectCache.put(MMConstants.ReturnActionCode.CUSTOMER, new ReturnToShelfForCustomerAction());
    }

    @Override
    public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{

        String actionCode = rdoc.getReturnTypeCode();

        if(StringUtils.isEmpty(actionCode)){
            return false;
        }

        return !rdetail.isVendorDispositionInd() && ((!rdetail.isVendorCreditInd()) ||
                (rdetail.isVendorCreditInd() && actionCode.equalsIgnoreCase(MMConstants.ReturnActionCode.DEPT_CR)));

    }

    private class ReturnToShelfForRejectedAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            if(rdetail.requiresDummyOrder()){
            //1) Create a dummy MM Order to Check-in the stock if > Qty Ordered
                MMServiceLocator.getReorderReturnActionService().execute(rdoc, rdetail);
            }
        }

    }

    private class ReturnToShelfForDeptNcAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            //TODO
            // If ORDER_TYPE_CODE = WAREHS, Inventory Adjustment for the cost and quantity
            MMServiceLocator.getInventoryAdjustmentActionService().execute(rdoc, rdetail);
        }

    }

    private class ReturnToShelfForCustomerAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            //TODO
            //Inventory Adjustment for the cost and quantity returned
            MMServiceLocator.getInventoryAdjustmentActionService().execute(rdoc, rdetail);
        }

    }

    /***********************************************************************************/
    /***
     *  The following inner classes are simply place holders
     *  as they don't have any implementation
     */
    /**********************************************************************************/
    private class ReturnToShelfForCreateAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            //TODO
            //Inventory Adjustment for the cost and quantity
            //updating MM_STOCK_BALANCE_T TABLE and MM_Stock_history_t table
            MMServiceLocator.getInventoryAdjustmentActionService().execute(rdoc, rdetail);
        }


    }

    private class ReturnToShelfForDeptCrAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            //TODO
            //Inventory Adjustment for the cost and quantity
            //updating MM_STOCK_BALANCE_T TABLE and MM_Stock_history_t table
            MMServiceLocator.getInventoryAdjustmentActionService().execute(rdoc, rdetail);
        }


    }

}
