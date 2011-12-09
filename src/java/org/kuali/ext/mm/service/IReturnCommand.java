/**
 *
 */
package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.document.ReturnDocument;

/**
 * @author rponraj
 *
 */
public interface IReturnCommand {

    public void execute(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception;

    public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception;
}
