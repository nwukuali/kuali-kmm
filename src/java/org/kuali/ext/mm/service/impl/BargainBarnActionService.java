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
public class BargainBarnActionService extends BaseReturnAction {

    public BargainBarnActionService(){
        super(MMConstants.DispositionCode.BARGAIN_BARN);
        objectCache.put(MMConstants.ReturnActionCode.WAREHS, new BargainBarnForCreateAction());
        objectCache.put(MMConstants.ReturnActionCode.REJECTED, new BargainBarnForRejectedAction());
        objectCache.put(MMConstants.ReturnActionCode.DEPT_CR, new BargainBarnForDeptNcAction());
        objectCache.put(MMConstants.ReturnActionCode.DEPT_NC, new BargainBarnForDeptCrAction());
        objectCache.put(MMConstants.ReturnActionCode.CUSTOMER, new BargainBarnForCustomerAction());
    }

    private class BargainBarnForCreateAction implements IReturnCommand{

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

    /***********************************************************************************/
    /***
     *  The following inner classes are simply place holders
     *  as they don't have any implementation  now
     */
    /**********************************************************************************/

    private class BargainBarnForRejectedAction implements IReturnCommand{

        public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
            return true;
        }

        public void execute(ReturnDocument rdoc, ReturnDetail rdetail){
            //Do Nothing As nothign as of needs to be done
            //Implementation is left blank
        }

    }

    private class BargainBarnForDeptNcAction implements IReturnCommand{

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

    private class BargainBarnForDeptCrAction implements IReturnCommand{

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

    private class BargainBarnForCustomerAction implements IReturnCommand{

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
