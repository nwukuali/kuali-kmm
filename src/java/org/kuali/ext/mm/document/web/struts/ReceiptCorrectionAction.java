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

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.CheckinDetail;
import org.kuali.ext.mm.businessobject.StagingRental;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReceiptCorrectionDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.ReceiptCorrectionService;
import org.kuali.ext.mm.util.MMUtil;


public class ReceiptCorrectionAction extends RentalTrackingActionBase {

    private ReceiptCorrectionService receiptCorrectionService(){
        return MMServiceLocator.getReceiptCorrectionService();
    }

    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward ad = super.docHandler(mapping, form, request, response);
        ReceiptCorrectionForm recCorrectForm = (ReceiptCorrectionForm) form;
        ReceiptCorrectionDocument recCorrectDoc = recCorrectForm.getReceiptCorrectionDocument();
        String orderDocNumber = request.getParameter(MMConstants.CheckinDocument.ORDER_DOC_NUMBER);
        String orderLineNumber = request.getParameter(MMConstants.CheckinDocument.ORDER_DETAIL_ID);

        if (MMUtil.isCollectionEmpty(recCorrectDoc.getCheckinDetails()))
            recCorrectDoc = this.receiptCorrectionService().createReceiptItems(recCorrectDoc,
                    orderDocNumber, orderLineNumber);

        // used for disabling submit button when lines are viewed separately
        recCorrectForm.setOnlyLineViewed(StringUtils.isNotEmpty(request
                .getParameter(MMConstants.CheckinDocument.LINE_CHECKED_IN)));

        if (!StringUtils.isEmpty(orderLineNumber))
            recCorrectDoc.setSelectedOrderDetailId(Integer.valueOf(orderLineNumber));
        else
            recCorrectDoc.setSelectedOrderDetailId(null);

        createRentals(recCorrectForm);
        return ad;
    }


    @Override
    protected void createRentals(RentalTrackingFormBase trackingForm) {
        ReceiptCorrectionDocument correctionDoc = ((ReceiptCorrectionForm)trackingForm).getReceiptCorrectionDocument();
        int index = 0;
        if (!MMUtil.isCollectionEmpty(correctionDoc.getCheckinDetails())) {
            trackingForm.setAddSerialNumbers(new ArrayList<StagingRental>());
            trackingForm.setSelectedItems(new ArrayList<String>());
            for (CheckinDetail cdetail : correctionDoc.getCheckinDetails()) {
                if (cdetail.getStock().isRental()){
                    if(cdetail.getCheckinRentals().isEmpty()) {
                        for(StagingRental rental : cdetail.getCorrectedCheckinDetail().getCheckinRentals()) {
                            StagingRental newRental = new StagingRental();
                            newRental.setSerialNumber(rental.getSerialNumber());
                            cdetail.getCheckinRentals().add(newRental);
                        }
                    }
                    trackingForm.getSelectedItems().add(new String());
                    trackingForm.getAddSerialNumbers().add(new StagingRental());
                }    
                else {
                    trackingForm.getSelectedItems().add(null);
                    trackingForm.getAddSerialNumbers().add(null);
                }
                index++;
            }
        }
    }

}
