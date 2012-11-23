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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.RentalTrackingDetail;
import org.kuali.ext.mm.businessobject.StagingRental;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;
import org.kuali.rice.krad.util.GlobalVariables;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class RentalTrackingActionBase extends KualiTransactionalDocumentActionBase {
    private org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RentalTrackingActionBase.class);
    
    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.docHandler(mapping, form, request, response);
        
        return forward;
    }
    
    /**
     * Method for adding a new rental line
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addRental(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RentalTrackingFormBase trackingForm = (RentalTrackingFormBase) form;
        Integer index = getSelectedLine(request);
        StagingRental newRental = trackingForm.getAddSerialNumbers().get(index);
        
        RentalTrackingDetail trackingDetail = (RentalTrackingDetail)trackingForm.getRentalTrackingDocument()
            .getRentalTrackingDetails().get(index);
        
        if (validateNewRental(newRental.getSerialNumber(), trackingDetail.getStagingRentals(), index)) {            
            newRental.setRentalTrackingDetailId(trackingDetail, trackingDetail.getRentalTrackingDetailId());
            newRental.setRentalTrackingDetail(trackingDetail);
            trackingDetail.getStagingRentals().add(newRental);
            StagingRental rental = new StagingRental();
            trackingForm.getAddSerialNumbers().set(index, rental);
        }
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    /**
     * Method for removing a rental 
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteRental(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RentalTrackingFormBase trackingForm = (RentalTrackingFormBase) form;
        Integer index = getSelectedLine(request);
        String serialNumber = trackingForm.getSelectedItems().get(index);
        
        if (StringUtils.isEmpty(serialNumber))
            throw new RuntimeException("Invalid Item is selected ");
        
        RentalTrackingDetail trackingDetail = (RentalTrackingDetail)trackingForm.getRentalTrackingDocument()
        .getRentalTrackingDetails().get(index);
        
        StagingRental delRental = null;
        for (StagingRental rental : trackingDetail.getStagingRentals()) {
            if (rental.getSerialNumber().equalsIgnoreCase(serialNumber)) {
                delRental = rental;
                break;
            }
        }

        trackingDetail.getStagingRentals().remove(delRental);
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    protected void createRentals(RentalTrackingFormBase trackingForm) {
        int index = 0;
        List<RentalTrackingDetail> trackingDetails = trackingForm.getRentalTrackingDocument().getRentalTrackingDetails();
        if (!MMUtil.isCollectionEmpty(trackingDetails)) {
            trackingForm.setAddSerialNumbers(new ArrayList<StagingRental>());
            trackingForm.setSelectedItems(new ArrayList<String>());
            for (RentalTrackingDetail rdetail : trackingDetails) {
                if (rdetail.isTrackableStock()) {
                    trackingForm.getAddSerialNumbers().add(new StagingRental());
                    trackingForm.getSelectedItems().add(new String());
                }
                else {
                    trackingForm.getAddSerialNumbers().add(null);
                    trackingForm.getSelectedItems().add(null);
                }
                index++;
            }
        }
    }
    
    public static boolean validateNewRental(String serialNumber,
            List<StagingRental> rentals, int index) {

        String dispString = "[" + index + "].";
        boolean retVal = true;
        if (StringUtils.isEmpty(serialNumber)) {
            GlobalVariables.getMessageMap().putError(
                    MMConstants.RentalTrackingDocument.ADD_SERIAL_NUMBER_ERROR_PATH 
                        + dispString 
                        + MMConstants.StagingRental.SERIAL_NUMBER,
                    MMKeyConstants.RentalTrackingDocument.EMPTY_RENTAL_SERIAL_NUMBER);
            retVal = false;
        }

        for (StagingRental rental : rentals) {
            if (rental.getSerialNumber().equals(serialNumber)) {
                GlobalVariables.getMessageMap().putError(
                        MMConstants.RentalTrackingDocument.ADD_SERIAL_NUMBER_ERROR_PATH 
                            + dispString 
                            + MMConstants.StagingRental.SERIAL_NUMBER,
                        MMKeyConstants.RentalTrackingDocument.INVALID_RENTAL_SERIAL_NUMBER,
                        serialNumber);
                retVal = false;

            }
        }

        return retVal;
    }

}
