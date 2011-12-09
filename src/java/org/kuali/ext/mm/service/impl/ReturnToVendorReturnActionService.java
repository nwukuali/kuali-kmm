package org.kuali.ext.mm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.businessobject.StagingRental;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.service.IReturnCommand;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.UserSession;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;


public class ReturnToVendorReturnActionService implements IReturnCommand {

    public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {

        // TODO Auto-generated method stub
        ReturnDocument vdoc = null;
        if (rdoc.getVendorReturnDoc() == null && !rdoc.isChildDocsGenerated()) {

            UserSession curSession = GlobalVariables.getUserSession();
            String name = ObjectUtils.isNull(rdoc.getDocumentHeader().getWorkflowDocument()
                    .getAllPriorApprovers()) ? KIMServiceLocator.getIdentityManagementService()
                    .getPrincipal(
                            rdoc.getDocumentHeader().getWorkflowDocument()
                                    .getInitiatorPrincipalId()).getPrincipalName() : rdoc
                    .getDocumentHeader().getWorkflowDocument().getAllPriorApprovers().iterator()
                    .next().getPrincipalName();
            GlobalVariables.setUserSession(new UserSession(name));

            Document dd = KNSServiceLocator.getDocumentService().getNewDocument(
                    MMConstants.CHECKIN_VENDOR_RETURNDOC_TYPE);
            dd.getDocumentHeader().setDocumentDescription(
                    "Return to Vendor " + dd.getDocumentNumber());
            dd.getDocumentHeader().getWorkflowDocument().getRouteHeader().setInitiatorPrincipalId(
                    GlobalVariables.getUserSession().getPrincipalId());
            vdoc = (ReturnDocument) dd;

            MMServiceLocator.getReturnOrderService().setDocParams(rdoc.getOrderDocument(), vdoc);
            rdoc.setVendorReturnDoc(vdoc);
            rdoc.setChildDocsGenerated(true);
            GlobalVariables.setUserSession(curSession);
        }
        else {
            vdoc = rdoc.getVendorReturnDoc();
        }

        vdoc.setReturnTypeCode(MMConstants.CheckinDocument.VENDOR_RETURN_ORDER_LINE);
        getReturnDetailObject(rdetail, vdoc);

    }

    private void getReturnDetailObject(ReturnDetail rd, ReturnDocument rdoc) {
        ReturnDetail newObj = rd.clone();
        newObj.setVersionNumber(0L);
        newObj.setActionCd(null);
        newObj.setBinId(null);
        newObj.setReturnCreditAmt(MMDecimal.ZERO);
        newObj.setReturnItemPercentage(MMDecimal.ZERO);
        newObj.setReturnQuantity(rd.getReturnQuantity());
        newObj.setVendorReturnQuantity(0);
        newObj.setVendorCreditInd(false);
        boolean isDocCustRetDoc = !rdoc.isCurrDocVendorReturnDoc();

        MMDecimal price = MMDecimal.ZERO;

        if (ObjectUtils.isNotNull(rd.getOrderDetail()))
            price = MMServiceLocator.getReturnOrderService().getReturnLinePrice(isDocCustRetDoc,
                    rd.getOrderDetail());
        else
            price = StringUtils.isNotEmpty(rd.getCatalogItemId()) ? MMServiceLocator
                    .getStockService().getStockPriceForCatalogItem(rd.getCatalogItemId())
                    : MMDecimal.ZERO;

        newObj.setReturnItemPrice(price);
        // newObj.setDispostitionCode(null);
        // newObj.setReturnDetailStatusCode(null);
        newObj.setReturnTypeCode(MMConstants.CheckinDocument.VENDOR_RETURN_ORDER_LINE);
        newObj.setVendorDispositionInd(false);
        newObj.setVendorReshipInd(false);
        newObj.setReturnDetailId(null);

        if (rd.isDepartmentCreditInd()) {
            newObj.setActionCd(MMConstants.ReturnActionCode.DEPT_CR);
        }
        else {
            newObj.setActionCd(MMConstants.ReturnActionCode.DEPT_NC);
        }

        if (rd.getCatalogItem().getStock().isRental()) {
            List<Rental> rentals = new ArrayList<Rental>(0);

            List<StagingRental> tempRentals = new ArrayList<StagingRental>(0);

            for (StagingRental rental : rd.getReturnRentals()) {
                StagingRental newRental = rental.clone();
                newRental.setCheckinRentalId(null);
                newRental.setReturnDetailId(null);
                newRental.setReturnDetail(newObj);
                tempRentals.add(newRental);
            }

            newObj.setReturnRentals(tempRentals);

            for (Rental rental : rd.getRentals()) {
                Rental newRental = new Rental(rental);
                newRental.setReturnDate(KNSServiceLocator.getDateTimeService().getCurrentTimestamp());
                newRental.setRentalStatusCode(MMConstants.Rental.RENTAL_STATUS_RETURNED);
                newRental.setReturnDetailId(null);
                newRental.setReturnDetail(newObj);
                rentals.add(newRental);
            }
            newObj.setRentals(rentals);
        }

        rdoc.getReturnDetails().add(newObj);
        newObj.setReturnDoc(rdoc);
        newObj.setReturnDocNumber(rdoc.getDocumentNumber());
    }

    public boolean preValidate(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
        return true;
    }
}
