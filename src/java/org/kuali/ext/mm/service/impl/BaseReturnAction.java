package org.kuali.ext.mm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.service.IReturnCommand;
import org.kuali.ext.mm.service.ReturnActionService;

public abstract class BaseReturnAction implements ReturnActionService {

    protected  Map<String, IReturnCommand> objectCache = new HashMap<String, IReturnCommand>();

	BaseReturnAction(String actionType){
		this.actionType = actionType;
	}

	protected String actionType = null;

    public  void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {

        IReturnCommand command = objectCache.get(rdetail.getActionCd());

        if(command != null)
            command.execute(rdoc, rdetail);
    }


	public String getActionType() {
		return actionType;
	}

	/**
	 * default implementation always returns true
	 * @see org.kuali.ext.mm.service.ReturnActionService#preValidate(org.kuali.ext.mm.document.ReturnDocument, org.kuali.ext.mm.businessobject.ReturnDetail)
	 */
	public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail)throws Exception{
	    return true;
	}
}
