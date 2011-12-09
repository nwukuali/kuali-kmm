/*
 * Copyright 2006-2007 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.document.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReturnDocument;


public class ReturnOrderForm extends RentalTrackingFormBase {

    /**
     *
     */
    private static final long serialVersionUID = -8900520717552069582L;
    private boolean vendorReturnForm = false;
    private ReturnDetail newReturnDetail = new ReturnDetail();
	private boolean docInMyActionList = false;
	
	public ReturnOrderForm() {
        super();
        setDocument(new ReturnDocument());
        setDocTypeName(MMConstants.CHECKIN_RETURNDOC_TYPE);

    }

    public ReturnOrderForm(boolean vendorReturnform){
        this();
        this.vendorReturnForm = vendorReturnform;
    }
    
    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
    }
	
	public boolean isVendorReturnForm() {
        return vendorReturnForm;
    }

    public void setVendorReturnForm(boolean vendorReturnForm) {
        this.vendorReturnForm = vendorReturnForm;
    }

	public boolean isDocInMyActionList() {
		return docInMyActionList;
	}

	public void setDocInMyActionList(boolean docInMyActionList) {
		this.docInMyActionList = docInMyActionList;
	}

	public ReturnDetail getNewReturnDetail() {
	    newReturnDetail.setItemReturned(true);
		return newReturnDetail;
	}

	public void setNewReturnDetail(ReturnDetail newReturnDetail) {
		this.newReturnDetail = newReturnDetail;
	}

	public void addNewReturn(){
		 ReturnDetail temp = new ReturnDetail();
		this.newReturnDetail = temp;
	}

	public ReturnDocument getReturnDoc() {
		return (ReturnDocument) getDocument();
	}

    @Override
    public boolean shouldMethodToCallParameterBeUsed(String methodToCallParameterName,
            String methodToCallParameterValue, HttpServletRequest request) {
                  return true;
    }

}