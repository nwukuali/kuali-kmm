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
 *
 */
public class DepartmentCreditActionService implements IReturnCommand {


    public void execute(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception {

        String actionCode = rdetail.getActionCd();

        if((actionCode != null && !(actionCode.equalsIgnoreCase(MMConstants.ReturnActionCode.CUSTOMER) ||
                actionCode.equalsIgnoreCase(MMConstants.ReturnActionCode.DEPT_NC))) || actionCode == null  )
            return;

    }

    /**
     *
     * @see org.kuali.ext.mm.service.impl.BaseReturnAction#preValidate(org.kuali.ext.mm.document.ReturnDocument, org.kuali.ext.mm.businessobject.ReturnDetail)
     */
    public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail){
        //VendorDispositionInd should be ste to true
        String actionCode  = rdoc.getReturnTypeCode();

        if(actionCode != null && (actionCode.equalsIgnoreCase(MMConstants.ReturnActionCode.DEPT_CR)
                    || actionCode.equalsIgnoreCase(MMConstants.ReturnActionCode.DEPT_NC) ||
                    actionCode.equalsIgnoreCase(MMConstants.ReturnActionCode.CUSTOMER)))
            return true;
        else
            return false;

    }

}
