package org.kuali.ext.mm.businessobject.lookup;

import org.kuali.ext.mm.businessobject.PickTicket;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.*;


@SuppressWarnings("unchecked")
public class PickTicketLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

    /**
     *
     */
    private static final long serialVersionUID = -6607472514296313941L;
    private ArrayList<PickTicket> uniqueResults;

    public PickTicketLookupableHelperServiceImpl() {
        super();
        uniqueResults = null;
    }

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        List<? extends BusinessObject> results = super.getSearchResults(fieldValues);

        Map<String, PickTicket> uniqueTickets = new HashMap<String, PickTicket>();

        for (int i = 0; i < results.size(); i++) {
            PickTicket ticket = (PickTicket) results.get(i);
            if (!uniqueTickets.containsKey(ticket.getPickTicketNumber())) {
                uniqueTickets.put(ticket.getPickTicketNumber(), ticket);
            }
        }


        uniqueResults = new ArrayList<PickTicket>();
        uniqueResults.addAll(uniqueTickets.values());
        return uniqueResults;
    }

    /**
     * @see org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl#getCustomActionUrls(org.kuali.rice.kns.bo.BusinessObject,
     *      List pkNames)
     */
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
        PickTicket pickTicket = (PickTicket) businessObject;

        List<HtmlData> anchorHtmlDataList = new ArrayList<HtmlData>();

        anchorHtmlDataList.add(this.getPrintUrl(pickTicket));
        if (MMConstants.PickStatusCode.PICK_STATUS_INIT.equals(pickTicket.getPickStatusCode()
                .getPickStatusCode()))
            anchorHtmlDataList.add(this.getPrintAllUrl());
        if (MMConstants.PickStatusCode.PICK_STATUS_PRTD.equals(pickTicket.getPickStatusCode()
                .getPickStatusCode()))
            anchorHtmlDataList.add(this.getVerifyUrl(pickTicket));

        return anchorHtmlDataList;

    }

    protected HtmlData getPrintUrl(PickTicket pickTicket) {
        Properties parameters = new Properties();
        parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, MMConstants.PRINT_TICKET_METHOD);
        parameters
                .put(MMConstants.ACTION_PARM_PICK_TICKET_NUMBER, pickTicket.getPickTicketNumber());
        parameters.put(KRADConstants.DOC_FORM_KEY, this.getDocFormKey());
        parameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, this.getBackLocation());

        String href = getKualiConfigurationService().getPropertyValueAsString(
                KRADConstants.APPLICATION_URL_KEY)
                + "/" + UrlFactory.parameterizeUrl(MMConstants.PICK_TICKET_ACTION, parameters);
        return new AnchorHtmlData(href, KRADConstants.DOC_HANDLER_METHOD,
            MMConstants.PICK_TICKET_ACTION_PRINT);
    }

    protected HtmlData getVerifyUrl(PickTicket pickTicket) {
        Properties parameters = new Properties();
        parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.DOC_HANDLER_METHOD);
        parameters
                .put(MMConstants.ACTION_PARM_PICK_TICKET_NUMBER, pickTicket.getPickTicketNumber());
        parameters.put(KRADConstants.DOCUMENT_TYPE_NAME,
                MMConstants.PickVerifyDocument.PICK_VERIFY_DOC_TYPE);
        parameters.put(MMConstants.COMMAND, MMConstants.COMMAND_INITIATE);

        String href = getKualiConfigurationService().getPropertyValueAsString(
                KRADConstants.APPLICATION_URL_KEY)
                + "/"
                + UrlFactory.parameterizeUrl(MMConstants.PickVerifyDocument.PICK_VERIFY_ACTION,
                        parameters);
        return new AnchorHtmlData(href, KRADConstants.DOC_HANDLER_METHOD,
            MMConstants.PickVerifyDocument.PICK_VERIFY_ACTION_VERIFY);
    }

    protected HtmlData getPrintAllUrl() {
        Properties parameters = new Properties();
        parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, MMConstants.PRINT_ALL_METHOD);
        parameters.put(KRADConstants.DOC_FORM_KEY, this.getDocFormKey());
        parameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, this.getBackLocation());

        String href = getKualiConfigurationService().getPropertyValueAsString(
                KRADConstants.APPLICATION_URL_KEY)
                + "/" + UrlFactory.parameterizeUrl(MMConstants.PICK_TICKET_ACTION, parameters);
        return new AnchorHtmlData(href, KRADConstants.DOC_HANDLER_METHOD,
            MMConstants.PICK_TICKET_ACTION_PRINT_ALL);
    }

}
