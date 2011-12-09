/**
 *
 */
package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.service.IReturnCommand;
import org.kuali.ext.mm.service.MMServiceLocator;

/**
 * @author rponraj
 */
public class VendorReshipmentActionService implements IReturnCommand {

    public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
        MMServiceLocator.getReorderReturnActionService().execute(rdoc, rdetail);
    }

    public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail) {
        // VendorDispositionInd should be ste to true
        return true;
    }

}
