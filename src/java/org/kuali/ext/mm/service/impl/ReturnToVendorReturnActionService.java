package org.kuali.ext.mm.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.businessobject.StagingRental;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.ReturnDocument;
import org.kuali.ext.mm.service.IReturnCommand;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ReturnToVendorReturnActionService implements IReturnCommand {

    public void execute(ReturnDocument rdoc, ReturnDetail rdetail) throws Exception {
				//TODO: NWU - Implement return doc creation...
			throw new RuntimeException("Unimplemented method.....");
//        ReturnDocument vdoc = null;
//        if (rdoc.getVendorReturnDoc() == null && !rdoc.isChildDocsGenerated()) {
//
//            UserSession curSession = GlobalVariables.getUserSession();
//					final Set<Person> priorApprovers = getPriorApprovers(rdoc.getDocumentHeader().getWorkflowDocument());
//					String name = ObjectUtils.isNull(priorApprovers) ?
//							KimApiServiceLocator.getIdentityService()
//                    .getPrincipal(rdoc.getDocumentHeader().getWorkflowDocument()
//                                    .getInitiatorPrincipalId()).getPrincipalName() : priorApprovers.iterator()
//                    .next().getPrincipalName();
//            GlobalVariables.setUserSession(new UserSession(name));
//
//            Document dd = KRADServiceLocatorWeb.getDocumentService().getNewDocument(
//                    MMConstants.CHECKIN_VENDOR_RETURNDOC_TYPE);
//            dd.getDocumentHeader().setDocumentDescription(
//                    "Return to Vendor " + dd.getDocumentNumber());
//            dd.getDocumentHeader().getWorkflowDocument().getInitiatorPrincipalId().getRouteHeader().setInitiatorPrincipalId(
//                    GlobalVariables.getUserSession().getPrincipalId());
//            vdoc = (ReturnDocument) dd;
//
//            MMServiceLocator.getReturnOrderService().setDocParams(rdoc.getOrderDocument(), vdoc);
//            rdoc.setVendorReturnDoc(vdoc);
//            rdoc.setChildDocsGenerated(true);
//            GlobalVariables.setUserSession(curSession);
//        }
//        else {
//            vdoc = rdoc.getVendorReturnDoc();
//        }
//
//        vdoc.setReturnTypeCode(MMConstants.CheckinDocument.VENDOR_RETURN_ORDER_LINE);
//        getReturnDetailObject(rdetail, vdoc);

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
                newRental.setReturnDate(CoreApiServiceLocator.getDateTimeService().getCurrentTimestamp());
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


		private Set<Person> getPriorApprovers(WorkflowDocument workflowDocument) {
        PersonService personService = KimApiServiceLocator.getPersonService();
        List<ActionTaken> actionsTaken = workflowDocument.getActionsTaken();
        Set<String> principalIds = new HashSet<String>();
        Set<Person> persons = new HashSet<Person>();

        for (ActionTaken actionTaken : actionsTaken) {
            if (KewApiConstants.ACTION_TAKEN_APPROVED_CD.equals(actionTaken.getActionTaken())) {
                String principalId = actionTaken.getPrincipalId();
                if (!principalIds.contains(principalId)) {
                    principalIds.add(principalId);
                    persons.add(personService.getPerson(principalId));
                }
            }
        }
        return persons;
    }
}
