package org.kuali.ext.mm.document.web.struts;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.PickTicket;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.PickListDocument;
import org.kuali.ext.mm.service.PickListService;
import org.kuali.ext.mm.sys.service.SegmentedLookupResultsService;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.TypedArrayList;
import org.kuali.rice.kns.util.UrlFactory;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;


public class PickListAction extends KualiTransactionalDocumentActionBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(PickListAction.class);

    /**
     * Builds action forward and sends user to a lookup for PickTickets that are ready to be printed
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    public ActionForward lookupPrintablePickTickets(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        PickListForm plForm = (PickListForm) form;

        //TODO: test this to make sure the correct path is coming
        String basePath = KNSServiceLocator.getKualiConfigurationService().getPropertyString(MMKeyConstants.KMM_URL_KEY);
        String backLocation = basePath + "/" + MMConstants.PICK_LIST_ACTION;

        Properties parameters = new Properties();

        parameters.put(MMConstants.ACTION_PARM_PICK_STATUS,
                MMConstants.PickStatusCode.PICK_STATUS_INIT);
        parameters.put(MMConstants.ACTION_PARM_PICKLIST_DOC_NBR, plForm.getDocId());
        parameters.put(KNSConstants.DOC_FORM_KEY, plForm.getFormKey());
        parameters.put(KNSConstants.DOC_NUM, plForm.getDocId());
        parameters.put(KNSConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, PickTicket.class
                .getCanonicalName());
        parameters.put(MMConstants.SHOW_MAINT_LINKS, "true");
        parameters.put(KNSConstants.HIDE_LOOKUP_RETURN_LINK, "true");
        parameters.put(KNSConstants.RETURN_LOCATION_PARAMETER, backLocation);
        parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, KNSConstants.SEARCH_METHOD);

        String lookupUrl = getKualiConfigurationService().getPropertyString(
                KNSConstants.APPLICATION_URL_KEY)
                + "/kr/" + KNSConstants.LOOKUP_ACTION;

        return new ActionForward(UrlFactory.parameterizeUrl(lookupUrl, parameters), true);
    }

    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#docHandler(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward actionForward = super.docHandler(mapping, form, request, response);
        PickListForm plForm = (PickListForm) form;

        // automatically create document description template
        plForm.getDocument().getDocumentHeader().setDocumentDescription(
                getKualiConfigurationService().getPropertyString(
                        MMKeyConstants.PickList.PICK_LIST_GENERATE_DOC_DESC));

        // Set Default Property values for document
        // TODO: is there a better place to set these?
        if (plForm.getDocument().getDocumentHeader().getWorkflowDocument().stateIsInitiated()) {
            plForm.getPickListDocument().setWarehouseCd(
                    getParameterService().getParameterValue(MMConstants.MM_NAMESPACE,
                            MMConstants.Parameters.DOCUMENT,
                            MMConstants.Parameters.DEFAULT_WAREHOUSE_CD));
            plForm.getPickListDocument().setSortCode(
                    getParameterService()
                            .getParameterValue(MMConstants.MM_NAMESPACE,
                                    MMConstants.Parameters.DOCUMENT,
                                    MMConstants.Parameters.DEFAULT_SORT_CD));
        }

        resetSummaryValues((PickListForm) form);
        computePickListSummaryValues((PickListForm) form);

        return actionForward;
    }


    /**
     * Overrides refresh method in order to allow the return of multiple items from the multiple value lookup.
     *
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#refresh(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    @Override
    public ActionForward refresh(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.refresh(mapping, form, request, response);

        PickListForm pickListForm = (PickListForm) form;

        Collection<PersistableBusinessObject> rawValues = null;
        Map<String, Set<String>> segmentedSelection = new HashMap<String, Set<String>>();

        if (StringUtils.equals(MMConstants.MULTIPLE_VALUE, pickListForm.getRefreshCaller())) {
            String lookupResultsSequenceNumber = pickListForm.getLookupResultsSequenceNumber();

            if (StringUtils.isNotBlank(lookupResultsSequenceNumber)) {

                // actually returning from a multiple value lookup
                Set<String> selectedIds = SpringContext
                        .getBean(SegmentedLookupResultsService.class)
                        .retrieveSetOfSelectedObjectIds(lookupResultsSequenceNumber,
                                GlobalVariables.getUserSession().getPerson().getPrincipalId());
                for (String selectedId : selectedIds) {
                    String selectedObjId = StringUtils.substringBefore(selectedId, ".");
                    String selectedMonthData = StringUtils.substringAfter(selectedId, ".");

                    if (!segmentedSelection.containsKey(selectedObjId)) {
                        segmentedSelection.put(selectedObjId, new HashSet<String>());
                    }
                    segmentedSelection.get(selectedObjId).add(selectedMonthData);
                }
                // Retrieving selected data from table.
                LOG.debug("Asking segmentation service for object ids "
                        + segmentedSelection.keySet());
                rawValues = SpringContext.getBean(SegmentedLookupResultsService.class)
                        .retrieveSelectedResultBOs(lookupResultsSequenceNumber,
                                segmentedSelection.keySet(), PickListLine.class,
                                GlobalVariables.getUserSession().getPerson().getPrincipalId());
            }

            // Reset line list and Summary values
            List<PickListLine> pickListLineList = new TypedArrayList(PickListLine.class);
            resetSummaryValues(pickListForm);

            if (rawValues != null) {
                for (PersistableBusinessObject bo : rawValues) {
                    PickListLine line = (PickListLine) bo;

                    boolean addIt = true;
                    for (PickListLine detail : pickListLineList) {
                        if (detail.getPickListLineId().compareTo(line.getPickListLineId()) == 0) {
                            addIt = false;
                            break;
                        }
                    }

                    if (addIt)
                        pickListLineList.add(line);
                }
                pickListForm.getPickListDocument().setPickListLines(pickListLineList);
            }
        }
        computePickListSummaryValues(pickListForm);
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    /**
     * Computes number of orders, number of lines, and oldest date from the pick list lines that have been assigned to the document.
     *
     * @param form
     */
    private void computePickListSummaryValues(PickListForm form) {
        PickListDocument doc = form.getPickListDocument();

        for (PickListLine line : doc.getPickListLines()) {
            if (form.getOldestDate() == null)
                form.setOldestDate(line.getCreatedDate());
            if (form.getOldestDate().after(line.getCreatedDate()))
                form.setOldestDate(line.getCreatedDate());

            if (!line.getBin().getZone().getWarehouseCd().equals(
                    form.getPickListDocument().getWarehouseCd()))
                form.setSingleWarehouse(false);

            if (line.getOrderDetail().isWillCall()) {
                form.setNumberOfWillCalls(form.getNumberOfWillCalls() + 1);
            }
        }
        form.setNumberOfOrders(SpringContext.getBean(PickListService.class).getUniqueOrderCount(
                doc.getPickListLines()));
        form.setNumberOfLines(doc.getPickListLines().size());
    }


    /**
     * Resets the form values for summary computation
     *
     * @param form
     */
    private void resetSummaryValues(PickListForm form) {
        form.setNumberOfLines(0);
        form.setNumberOfWillCalls(0);
        form.setNumberOfOrders(0);
        form.setSingleWarehouse(true);
        form.setOldestDate(null);
    }
}
