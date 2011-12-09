package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReturnDocument;


public class NoAction extends BaseReturnAction {

    NoAction(){
		super(MMConstants.ReturnActionCode.CREDIT_TRANSACTION);
	}
	@Override
	public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Inside after condition  save getReturnDetailObject IssueCreditReturnAction  ");
	}

}
