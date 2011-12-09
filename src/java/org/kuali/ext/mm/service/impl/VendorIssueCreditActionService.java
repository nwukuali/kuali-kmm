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
public class VendorIssueCreditActionService implements IReturnCommand {


    public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {

        if(rdetail.getActionCd().equalsIgnoreCase(MMConstants.ReturnActionCode.CUSTOMER))
            return;

        MMServiceLocator.getReturnOrderService().createCreditMemo(rdoc, rdetail);
    }

    public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail){
        return true;
    }

}
