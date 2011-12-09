package org.kuali.ext.mm.document.web.struts;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.PickTicket;
import org.kuali.ext.mm.businessobject.Rental;
import org.kuali.ext.mm.businessobject.StockBalance;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.PickVerifyDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.PickListService;
import org.kuali.ext.mm.service.PickVerifyService;
import org.kuali.ext.mm.service.RentalService;
import org.kuali.ext.mm.service.StockService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.KualiConfigurationService;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;


public class PickVerifyAction extends KualiTransactionalDocumentActionBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(PickVerifyAction.class);

    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        // proceed as usual
        return super.execute(mapping, form, request, response);
    }

    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#docHandler(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward actionForward = super.docHandler(mapping, form, request, response);
        PickVerifyForm pvForm = (PickVerifyForm) form;

        PickVerifyDocument document = pvForm.getPickVerifyDocument();

        // Set Default Property values for document
        if (document.getDocumentHeader().getWorkflowDocument().stateIsInitiated()) {
            // automatically create document description template
            document.getDocumentHeader().setDocumentDescription(
                    getKualiConfigurationService().getPropertyString(
                            MMKeyConstants.PickList.PICK_VERIFY_DOC_DESC));
        }

        if (request.getParameter(MMConstants.ACTION_PARM_PICK_TICKET_NUMBER) != null)
            document.setPickTicketNumber(request
                    .getParameter(MMConstants.ACTION_PARM_PICK_TICKET_NUMBER));

        loadPickTicket(pvForm);

        return actionForward;
    }

    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#refresh(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward refresh(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.refresh(mapping, form, request, response);

        PickVerifyForm pvForm = (PickVerifyForm) form;

        for(PickListLine line : pvForm.getNewPickLinesToAdd().values()) {
            if(ObjectUtils.isNull(line.getBin()) && line.getBinId() != null)              
                line.refreshReferenceObject(MMConstants.PickListLine.BIN);
            else if(ObjectUtils.isNotNull(line.getBin()) 
                    && !line.getBin().getBinId().equals(line.getBinId())) {
                line.refreshReferenceObject(MMConstants.PickListLine.BIN);
            }
            
        }
        
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
    
    public ActionForward addTicket(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.refresh(mapping, form, request, response);

        PickVerifyForm pvForm = (PickVerifyForm) form;

        loadPickTicket(pvForm);

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }    

    /**
     * Builds packing list pdf and sends it to the user for download
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward printPackingLists(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        PickVerifyForm pvForm = (PickVerifyForm) form;

        PickVerifyDocument document = pvForm.getPickVerifyDocument();

        LOG.info("Printing Packing Slips for Pick Ticket " + document.getPickTicketNumber());
        ByteArrayOutputStream baos = SpringContext.getBean(PickVerifyService.class)
                .generatePackLists(document.getPickTicket());

        String filename = SpringContext.getBean(KualiConfigurationService.class).getPropertyString(
                MMKeyConstants.PackingList.FILE_PREFIX)
                + document.getPickTicketNumber() + ".pdf";
        WebUtils.saveMimeOutputStreamAsFile(response, "application/pdf", baos, filename);

        return (null);

    }

    /**
     * Clears the ticket to start over
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward clearTicket(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        PickVerifyForm pvForm = (PickVerifyForm) form;
        pvForm.getPickVerifyDocument().setPickTicket(null);
        pvForm.getPickVerifyDocument().setPickTicketNumber(null);
        pvForm.setPickTicketPopulated(false);
        pvForm.setNewPickLinesToAdd(new HashMap<String, PickListLine>());
        
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
    
    /**
     * Clears the ticket to start over
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward clearNewPickLineData(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        PickVerifyForm pvForm = (PickVerifyForm) form;
        String line = String.valueOf(getSelectedLine(request));
        pvForm.getNewPickLinesToAdd().get(line).setBin(null);
        pvForm.getNewPickLinesToAdd().get(line).setBinId(null);
        pvForm.getNewPickLinesToAdd().get(line).setPickStockQty(null);
        
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
    
    public ActionForward addPickLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        PickVerifyForm pvForm = (PickVerifyForm) form;
        
        String line = String.valueOf(getSelectedLine(request));
        PickListLine newLine = pvForm.getNewPickLinesToAdd().get(line);
        
        String errorPath="newPickLinesToAdd(" + line + ")";
        PickListLine originalLine = null;
        for(PickListLine oLine : pvForm.getPickVerifyDocument().getPickTicket().getPickListLines()) {
            if(line.equals(oLine.getPickListLineId())) {
                originalLine = oLine;
                break;
            }
        }
        if(validateAdditionalPickLine(originalLine, newLine, errorPath)) {        
            originalLine.getAdditionalLines().add(newLine);
            pvForm.getNewPickLinesToAdd().put(line, prepareAdditionalPickLine(newLine));
        }
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
    
    public ActionForward deletePickLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        PickVerifyForm pvForm = (PickVerifyForm) form;
        
        String line = String.valueOf(getSelectedLine(request));
        Integer lineItem = this.getSelectedLineItem(request);
        
        PickListLine originalLine = null;
        for(PickListLine oLine : pvForm.getPickVerifyDocument().getPickTicket().getPickListLines()) {
            if(line.equals(oLine.getPickListLineId())) {
                originalLine = oLine;
                break;
            }
        }
        
        if(originalLine.getAdditionalLines().size() > lineItem
                && lineItem >= 0) {
            originalLine.getAdditionalLines().remove(lineItem.intValue());
        }

        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#route(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward route(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        PickVerifyForm pvForm = (PickVerifyForm) form;

        if (pvForm.getPickVerifyDocument().getPickTicket() != null) {
            refreshRentals(pvForm.getPickVerifyDocument().getPickTicket());
        }
        
        ActionForward actionForward = super.route(mapping, form, request, response);
        return actionForward;
    }

    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ActionForward actionForward = super.save(mapping, form, request, response);
        return actionForward;
    }
    
      /**
      * Refresh Rentals associated with pick list line items from the database.
      * If the item cannot be refreshed (it does not exist) the original placeholder rental
      * still gets added to be handled by the validation service. 
      * 
      * @param ticket - PickTicket object containing lines that may require rentals
      */
     private void refreshRentals(PickTicket ticket) {        
         RentalService rentalService = MMServiceLocator.getRentalService();
         for (PickListLine line : ticket.getPickListLines()) {
             List<Rental> refreshedRentals = new ArrayList<Rental>();
             for (Rental rental : line.getRentals()) {
                 rental.setStock(line.getStock());
                 rental.setStockId(line.getStockId());
                 Rental fetchRental = rentalService.getAvailableRentalItem(rental.getStock(), rental.getRentalSerialNumber());
                 if (fetchRental != null)
                     refreshedRentals.add(fetchRental);
                 else
                     refreshedRentals.add(rental);
             }
             line.getRentals().clear();
             line.getRentals().addAll(refreshedRentals);
         }
     }

    /**
     * Adds all of the ticket lines to verify document for the selected pick ticket number
     *
     * @param form
     * @param document
     */
    private void populateLinesToVerify(PickVerifyForm form, PickTicket ticket) {
        ticket.refreshReferenceObject(MMConstants.PickListDocument.PICK_LIST_LINES);
        form.setPickTicketPopulated(!ticket.getPickListLines().isEmpty());
        PickListService pickListService = SpringContext.getBean(PickListService.class);
        StockService stockService = SpringContext.getBean(StockService.class);
        pickListService.sortLinesByLocation(ticket.getPickListLines());
        form.setHasTrackableStock(pickListService.ticketHasTrackableStock(ticket));
        for (PickListLine line : ticket.getPickListLines()) {
            if (ObjectUtils.isNull(line.getPickStockQty())
                    && ObjectUtils.isNull(line.getBackOrderQty())) {
            	Integer qtyOnHand = line.getBin().getStockBalance().getQtyOnHand();
                line.setPickStockQty((line.getStockQty() <=  qtyOnHand) ? line.getStockQty() : qtyOnHand);
                line.setBackOrderQty(line.getStockQty() - line.getPickStockQty());
            }
            if(stockService.getAvailableBinsForStockId(line.getStockId(), 
                    line.getBin().getZone().getWarehouseCd(), 
                    MMConstants.StockBalance.QTY_ON_HAND, 
                    false).size() > 1) {
                if(!form.getNewPickLinesToAdd().containsKey(line.getPickListLineId()))
                    form.getNewPickLinesToAdd().put(line.getPickListLineId(), prepareAdditionalPickLine(line));
                line.setAdditionalLines(new ArrayList<PickListLine>());
            }
        }
    }
    
    /**
     * Prepares a new PickListLine from the static data in another line, to add it
     * as an additional pick list line to a pick ticket.
     * 
     * @param originalLine - Original PickListLine to copy
     * @return a new PickListLine with all static data copied from original line.
     */
    public PickListLine prepareAdditionalPickLine(PickListLine originalLine) {
        PickListLine newLine = new PickListLine();
        newLine.setBackOrder(originalLine.getBackOrder());
        newLine.setBackOrderId(originalLine.getBackOrderId());
        newLine.setCreatedDate(KNSServiceLocator.getDateTimeService().getCurrentTimestamp());
        newLine.setOrderDetail(originalLine.getOrderDetail());
        newLine.setOrderDetailId(originalLine.getOrderDetailId());
        newLine.setOrderLineNbr(originalLine.getOrderLineNbr());
        newLine.setPickListDocument(originalLine.getPickListDocument());
        newLine.setPickListDocumentNumber(originalLine.getPickListDocumentNumber());
        newLine.setPickStatusCode(originalLine.getPickStatusCode());
        newLine.setPickStatusCodeCd(originalLine.getPickStatusCodeCd());
        newLine.setPickTicket(originalLine.getPickTicket());
        newLine.setPickTicketNumber(originalLine.getPickTicketNumber());
        newLine.setPickTubNbr(originalLine.getPickTubNbr());
        newLine.setRoute(originalLine.getRoute());
        newLine.setRouteCd(originalLine.getRouteCd());
        newLine.setSalesInstance(originalLine.getSalesInstance());
        newLine.setSalesInstanceId(originalLine.getSalesInstanceId());
        newLine.setStock(originalLine.getStock());
        newLine.setStockId(originalLine.getStockId());
        newLine.setStockQty(0);
        newLine.setBackOrderQty(0);
        return newLine;
    }

    /**
     * creates empty rental fields for trackable items (items that need serial numbers)
     *
     * @param form
     * @param document
     */
    private void createRentalFields(PickVerifyForm form, PickVerifyDocument document) {
        for (PickListLine line : document.getPickTicket().getPickListLines()) {
            if (line.isTrackableWithSerialNumber()) {
                if (line.getRentals().size() > 0)
                    continue;
                for (int i = 0; i < line.getStockQty(); i++) {
                    Rental newRental = new Rental();
                    newRental.setStockId(line.getStockId());
                    line.getRentals().add(newRental);
                }
            }
        }
    }

    /**
     * loads the Pick Ticket to the form based on the pickTicketNumber
     *
     * @param form
     * @return true if Pick Ticket loaded successfully
     */
    private boolean loadPickTicket(PickVerifyForm pvForm) {
        PickVerifyDocument document = pvForm.getPickVerifyDocument();
        if (StringUtils.isNotBlank(document.getPickTicketNumber())) {
            PickTicket ticket = SpringContext.getBean(PickListService.class).getPickTicketByNumber(
                    document.getPickTicketNumber());
            if (ticket == null) {
            	GlobalVariables.getMessageMap().putError(
                        MMConstants.DOCUMENT + "." + MMConstants.PickTicket.PICK_TICKET_NUMBER,
                        MMKeyConstants.ERROR_PICK_TICKET_INVALID, document.getPickTicketNumber());
                pvForm.setPickTicketPopulated(false);
                return false;
            }
            else if(!MMConstants.PickStatusCode.PICK_STATUS_PRTD.equals(ticket.getPickStatusCodeCd())
            		&& (document.getDocumentHeader().getWorkflowDocument().stateIsInitiated()
            				|| document.getDocumentHeader().getWorkflowDocument().stateIsSaved())) {
            	GlobalVariables.getMessageMap().putError(
            			MMConstants.DOCUMENT + "." + MMConstants.PickTicket.PICK_TICKET_NUMBER,
                        MMKeyConstants.ERROR_PICK_STATUS_NOT_PRTD, ticket.getPickTicketNumber());
                pvForm.setPickTicketPopulated(false);
                return false;
            }
            else {
                document.setPickTicket(ticket);
                populateLinesToVerify(pvForm, ticket);
                createRentalFields(pvForm, document);
            }
        }
        else {
            pvForm.setPickTicketPopulated(false);
            return false;
        }

        return true;
    }
    
    protected int getSelectedLineItem(HttpServletRequest request) {
        int selectedLine = -1;
        String parameterName = (String) request.getAttribute(KNSConstants.METHOD_TO_CALL_ATTRIBUTE);
        if (StringUtils.isNotBlank(parameterName)) {
            String lineNumber = StringUtils.substringBetween(parameterName, ".item", ".");
            try {
                selectedLine = Integer.parseInt(lineNumber);
            }
            catch (NumberFormatException e) {
                selectedLine = -1;                
            }
        }

        return selectedLine;
    }
    
    private boolean validateAdditionalPickLine(PickListLine originalLine, PickListLine newLine, String errorPath) {
        boolean isValid = true;
        PickVerifyService verifyService = SpringContext.getBean(PickVerifyService.class);
        if(ObjectUtils.isNull(newLine.getBin()) && newLine.getBinId() != null)
            newLine.refreshReferenceObject(MMConstants.PickListLine.BIN);
        if(ObjectUtils.isNotNull(newLine.getBin()) 
                && ObjectUtils.isNull(newLine.getBin().getStockBalance())) {
            newLine.getBin().refreshReferenceObject(MMConstants.Bin.STOCK_BALANCE);
        }
        
        if(ObjectUtils.isNotNull(newLine.getBin()) 
                && ObjectUtils.isNotNull(newLine.getBin().getStockBalance())) {           
            StockBalance balance = newLine.getBin().getStockBalance();
            
            if(!StringUtils.equals(balance.getStockId(), newLine.getStockId())) {
                if(ObjectUtils.isNull(newLine.getStock()))
                    newLine.refreshReferenceObject(MMConstants.PickListLine.STOCK);
                GlobalVariables.getMessageMap().putError(
                        errorPath + "." + MMConstants.PickListLine.ITEM_LOCATION,
                        MMKeyConstants.PickVerifyDocument.ERROR_ADDITIONAL_LINE_STOCK_INVALID, 
                        newLine.getItemLocation(), newLine.getStock().getStockDistributorNbr());
                isValid = false;
            }
            Integer totalPickedFromBin = verifyService.getTotalPickedFromBin(originalLine.getPickTicket(), newLine.getBin());
            if(newLine.getPickStockQty() == null) {
                GlobalVariables.getMessageMap().putError(
                        errorPath + "." + MMConstants.PickListLine.PICK_STOCK_QTY,
                        MMKeyConstants.PickVerifyDocument.ERROR_ADDITIONAL_LINE_PICK_QTY_EMPTY);
                isValid = false;
            }
            else if(balance.getQtyOnHand().compareTo(totalPickedFromBin + newLine.getPickStockQty()) < 0) {
                GlobalVariables.getMessageMap().putError(
                        errorPath + "." + MMConstants.PickListLine.PICK_STOCK_QTY,
                        MMKeyConstants.PickVerifyDocument.ERROR_NOT_ENOUGH_STOCK, 
                        newLine.getItemLocation(),
                        String.valueOf(balance.getQtyOnHand()),
                        String.valueOf(totalPickedFromBin + newLine.getPickStockQty()));
                isValid = false;
            }
            else if(originalLine.getStockQty().compareTo(newLine.getPickStockQty()) < 0) {
                GlobalVariables.getMessageMap().putError(
                        errorPath + "." + MMConstants.PickListLine.PICK_STOCK_QTY,
                        MMKeyConstants.PickVerifyDocument.ERROR_ADDITIONAL_LINE_PICK_QTY_INVALID, 
                        String.valueOf(newLine.getPickStockQty()),
                        String.valueOf(originalLine.getStockQty()));
                isValid = false;
            }
        }
        else {
            GlobalVariables.getMessageMap().putError(
                    errorPath + "." + MMConstants.PickListLine.ITEM_LOCATION,
                    MMKeyConstants.PickVerifyDocument.ERROR_ADDITIONAL_LINE_BIN_REQUIRED);
            isValid = false;
        }
            
        
        
        return isValid;
    }
    
}
