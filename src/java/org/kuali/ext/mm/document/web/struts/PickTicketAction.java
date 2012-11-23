package org.kuali.ext.mm.document.web.struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.PickTicket;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.service.PickListService;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.action.KualiAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Collection;



public class PickTicketAction extends KualiAction {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PickTicketAction.class);

	/**
	 * Renders the pickTicket object as a PDF and sends it to the user for download and display.
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward printTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		checkAuthorization(form, "printTicket");
		LOG.info("Printing Pick Ticket: " + request.getParameter("pickTicketNumber"));

		PickTicket ticket = SpringContext.getBean(PickListService.class).getPickTicketByNumber(request.getParameter("pickTicketNumber"));

		ByteArrayOutputStream baos = SpringContext.getBean(PickListService.class).generatePdfTicket(ticket);

		WebUtils.saveMimeOutputStreamAsFile(response, "application/pdf", baos, ticket.getFileName());

		return (null);
	}

	/**
	 * Renders a list of all unprinted pickTickets object as a single PDF
	 * and sends it to the user for download and display.
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward printAllUnprinted(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		LOG.info("Printing all unprinted tickets");
		checkAuthorization(form, "printAllUnprinted");
		PickListService pickListService = SpringContext.getBean(PickListService.class);
		Collection<PickTicket> pickTicketList = pickListService.getAllUnprintedTickets();
		ByteArrayOutputStream baos = SpringContext.getBean(PickListService.class).generatePdfTicket(pickTicketList);

		DateTimeService dtService = CoreApiServiceLocator.getDateTimeService();
		String date = dtService.toDateTimeStringForFilename(dtService.getCurrentDate());

		WebUtils.saveMimeOutputStreamAsFile(response, "application/pdf", baos, date + ".pdf");

		return (null);
	}



}
