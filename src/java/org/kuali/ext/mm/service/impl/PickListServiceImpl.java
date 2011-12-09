package org.kuali.ext.mm.service.impl;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.BackOrder;
import org.kuali.ext.mm.businessobject.Bin;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.PickTicket;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.PickTicketDao;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.document.PickListDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;
import org.kuali.ext.mm.service.PickListHelperService;
import org.kuali.ext.mm.service.PickListPdfService;
import org.kuali.ext.mm.service.PickListService;
import org.kuali.ext.mm.service.StockService;
import org.kuali.rice.kns.bo.AdHocRouteRecipient;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.DateTimeService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.KualiConfigurationService;
import org.kuali.rice.kns.service.ParameterService;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.util.TypedArrayList;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author schneppd
 *
 */
@Transactional
public class PickListServiceImpl implements PickListService {
	protected static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PickListServiceImpl.class);

	private ParameterService parameterService;

	private StockService stockService;
	
	private PickListHelperService pickListHelperService;

	private class sortByZone implements Comparator<PickListLine> {
		public int compare(PickListLine pl1, PickListLine pl2) {
			return pl1.getBin().getZone().getZoneCd().compareTo(pl2.getBin().getZone().getZoneCd());
		}
	}

	private class sortByBin implements Comparator<PickListLine> {
		public int compare(PickListLine pl1, PickListLine pl2) {
			return pl1.getBin().getBinNbr().compareTo(pl2.getBin().getBinNbr());
		}
	}

	private class sortByShelf implements Comparator<PickListLine> {
		public int compare(PickListLine pl1, PickListLine pl2) {
			return pl1.getBin().getShelfId().compareTo(pl2.getBin().getShelfId());
		}
	}

	private class sortByShelfNumber implements Comparator<PickListLine> {
		public int compare(PickListLine pl1, PickListLine pl2) {
			return pl1.getBin().getShelfIdNbr().compareTo(pl2.getBin().getShelfIdNbr());
		}
	}

	private class BinRange {
		public PickListLine fromLine;
		public PickListLine toLine;

		public BinRange() {
			fromLine = null;
			toLine = null;
		}
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#cancelPickList(org.kuali.ext.mm.document.PickListDocument)
	 */
	public void cancelPickList(PickListDocument pickListDocument) {
		for (PickListLine line : pickListDocument.getPickListLines()) {
			releasePickListLine(line);
		}
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#processNewPickList(org.kuali.ext.mm.document.PickListDocument)
	 */
	public void processNewPickList(PickListDocument pickListDocument) {
		List<PickTicket> pickTickets;

		if (MMConstants.PickListDocument.OPTION_ZONES.equals(pickListDocument.getSortCode())) {
			pickTickets = separateForPickTickets(pickListDocument.getPickListLines(), MMConstants.PickListDocument.OPTION_ZONES);
			pickListDocument.setPickTickets(pickTickets);
		} else if (MMConstants.PickListDocument.OPTION_ORDERS.equals(pickListDocument.getSortCode())) {
			pickTickets = separateForPickTickets(pickListDocument.getPickListLines(), MMConstants.PickListDocument.OPTION_ORDERS);
			pickListDocument.setPickTickets(pickTickets);
		} else {
			pickTickets = separateForPickTickets(pickListDocument.getPickListLines(), MMConstants.PickListDocument.OPTION_SINGLE_LIST);
			pickListDocument.setPickTickets(pickTickets);
		}

		// Rebuild pick list lines from tickets to allow release of any dropped
		// lines during ticket creation
		List<PickListLine> newLineList = new TypedArrayList(PickListLine.class);

		for (PickTicket ticket : pickTickets) {
			newLineList.addAll(ticket.getPickListLines());
		}

		pickListDocument.setPickListLines(newLineList);
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#getUniqueOrderCount(java.util.List)
	 */
	public int getUniqueOrderCount(List<PickListLine> lines) {
		List<String> orders = new ArrayList<String>();

		for (PickListLine line : lines) {
			if (!orders.contains(String.valueOf(line.getSalesInstance().getOrderDocument().getOrderId())))
				orders.add(String.valueOf(line.getSalesInstance().getOrderDocument().getOrderId()));
		}

		return orders.size();
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#getLinesByOrder(java.util.List)
	 */
	public Map<Long, List<PickListLine>> getLinesByOrder(List<PickListLine> lines) {
		Map<Long, List<PickListLine>> linesByOrder = new HashMap<Long, List<PickListLine>>();
		for(PickListLine line : lines) {
			if(linesByOrder.containsKey(line.getSalesInstance().getOrderDocument().getOrderId()))
				linesByOrder.get(line.getSalesInstance().getOrderDocument().getOrderId()).add(line);
			else {
				List<PickListLine> newLineList = new ArrayList<PickListLine>();
				newLineList.add(line);
				linesByOrder.put(line.getSalesInstance().getOrderDocument().getOrderId(), newLineList);
			}
		}

		return linesByOrder;
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#getOldestDate(org.kuali.ext.mm.businessobject.PickTicket)
	 */
	public Timestamp getOldestDate(PickTicket ticket) {
		Timestamp oldestDate = null;

		for (PickListLine line : ticket.getPickListLines()) {
			if (oldestDate == null)
				oldestDate = line.getCreatedDate();

			if (oldestDate.after(line.getCreatedDate()))
				oldestDate = line.getCreatedDate();
		}
		return oldestDate;
	}

	/**
	 * Determines which orders, and their associated line items, should be dropped from the list
	 * due to the maxOrders constraint
	 *
	 * @param pickListLines
	 * @param maxOrders
	 */
	private List<PickListLine> dropExtraOrders(List<PickListLine> pickListLines, Integer maxOrders) {
		List<Long> orders = new ArrayList<Long>();
		List<PickListLine> keepLines = new ArrayList<PickListLine>();

		if (maxOrders == null)
			return pickListLines;

		for (PickListLine line : pickListLines) {
			// skip line if this line is a new order and max orders reached
			if (!orders.contains(line.getSalesInstance().getOrderDocument().getOrderId()) && orders.size() >= maxOrders) {
				if (ObjectUtils.isNotNull(line.getPickListDocument())) {
					releasePickListLine(line);
				}
				continue;
			}
			if (!orders.contains(line.getSalesInstance().getOrderDocument().getOrderId()))
				orders.add(line.getSalesInstance().getOrderDocument().getOrderId());

			keepLines.add(line);
		}
		return keepLines;
	}

	/**
	 * Releases a line from ownership by pickListDocument and pickTicket.
	 * Also resets its pick status to INIT
	 *
	 * @param line
	 */
	private void releasePickListLine(PickListLine line) {
		line.setPickListDocument(null);
		line.setPickListDocumentNumber(null);
		line.setPickTicket(null);
		line.setPickTicketNumber(null);
		line.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_INIT);
		KNSServiceLocator.getBusinessObjectService().save(line);
	}

	/**
	 * Returns the bounding bin locations in a collection of pickListLine items
	 *
	 * @param lines
	 */
	private BinRange getBinRange(List<PickListLine> lines) {
		BinRange range = new BinRange();

		for (PickListLine line : lines) {
			if (range.fromLine == null)
				range.fromLine = line;
			if (range.toLine == null)
				range.toLine = line;

			if (line.getBin().getZone().getZoneCd().compareTo(range.fromLine.getBin().getZone().getZoneCd()) <= 0) {
				if (line.getBin().getBinNbr().compareTo(range.fromLine.getBin().getBinNbr()) <= 0)
					range.fromLine = line;
			}
			if (line.getBin().getZone().getZoneCd().compareTo(range.toLine.getBin().getZone().getZoneCd()) >= 0) {
				if (line.getBin().getBinNbr().compareTo(range.toLine.getBin().getBinNbr()) >= 0)
					range.toLine = line;
			}
		}
		return range;
	}

	/**
	 * Determines whether a list of pickListLine items is a list of the same stock item
	 *
	 * @param lines
	 */
	private boolean isSingleItemList(List<PickListLine> lines) {
		String itemNumber = null;
		for (PickListLine line : lines) {
			if (itemNumber == null)
				itemNumber = line.getStock().getStockDistributorNbr();
			if (!line.getStock().getStockDistributorNbr().equals(itemNumber))
				return false;
		}

		return true;
	}


	/**
	 * Takes a list of pickListLine items and splits them into appropriate pickTicket objects.
	 * Could be: Will Call, Orders, Zones, or a Single List of items.
	 *
	 * @param pickListLines
	 * @param separateBy
	 * @return pickTickets
	 */
	protected List<PickTicket> separateForPickTickets(List<PickListLine> pickListLines, String separateBy) {
		Map<String, List<PickListLine>> separatorMap = new HashMap<String, List<PickListLine>>();
		Map<String, List<PickListLine>> separatorMapWillCall = new HashMap<String, List<PickListLine>>();
		Map<String, List<PickListLine>> separatorMapPersonalUse = new HashMap<String, List<PickListLine>>();

		// loop through lines to separate lines by the separator value
		for (PickListLine line : pickListLines) {
			String profileTypeCode = line.getOrderDetail().getOrderDocument().getProfileTypeCode();
			if (line.getOrderDetail().isWillCall()) {
				// Always pull out the will call items into their own pick tickets by order
				addLineToSeparatorMap(separatorMapWillCall, String.valueOf(line.getSalesInstance().getOrderDocument().getOrderId()), line);
			} else if (MMConstants.PickListDocument.OPTION_SINGLE_LIST.equals(separateBy)) {
				// Add all lines to single pick ticket (or split by internal and personal use if available)
				if(MMConstants.OrderDocument.PROFILE_TYPE_PERSONAL.equals(profileTypeCode))
					addLineToSeparatorMap(separatorMapPersonalUse, line.getPickListDocument().getDocumentNumber(), line);
				else
					addLineToSeparatorMap(separatorMap, line.getPickListDocument().getDocumentNumber(), line);
			} else if (MMConstants.PickListDocument.OPTION_ZONES.equals(separateBy)) {
				// Separate lines into pick tickets by Zone and Profile Type
				if(MMConstants.OrderDocument.PROFILE_TYPE_PERSONAL.equals(profileTypeCode))
					addLineToSeparatorMap(separatorMapPersonalUse, line.getBin().getZone().getZoneCd(), line);
				else
					addLineToSeparatorMap(separatorMap, line.getBin().getZone().getZoneCd(), line);
			} else if (MMConstants.PickListDocument.OPTION_ORDERS.equals(separateBy)) {
				// Separate lines into pick tickets by Order and Profile Type
				if(MMConstants.OrderDocument.PROFILE_TYPE_PERSONAL.equals(profileTypeCode))
					addLineToSeparatorMap(separatorMapPersonalUse, String.valueOf(line.getSalesInstance().getOrderDocument().getOrderId()), line);
				else
					addLineToSeparatorMap(separatorMap, String.valueOf(line.getSalesInstance().getOrderDocument().getOrderId()), line);
			}
		}

		// Create new ticket for each group
		List<PickTicket> pickTickets = new ArrayList<PickTicket>();
		for (String key : separatorMap.keySet()) {
			pickTickets.add(createPickTicket(separatorMap.get(key), separateBy, false));
		}
		for (String key : separatorMapPersonalUse.keySet()) {
			pickTickets.add(createPickTicket(separatorMapPersonalUse.get(key), separateBy, true));
		}
		for (String key : separatorMapWillCall.keySet()) {
			pickTickets.add(createPickTicket(separatorMapWillCall.get(key), MMConstants.PickListDocument.SEPARATE_WILL_CALLS, false));
		}

		return pickTickets;
	}

	/**
	 * Adds the line to the correct list mapped by the key, or creates a new key-line list map
	 * if key is not found.
	 *
	 * @param separatorMap
	 * @param key
	 * @param line
	 */
	private void addLineToSeparatorMap(Map<String, List<PickListLine>> separatorMap, String key, PickListLine line) {
		if (separatorMap.containsKey(key)) {
			separatorMap.get(key).add(line);
		} else {
			List<PickListLine> newLineList = new ArrayList<PickListLine>();
			newLineList.add(line);
			separatorMap.put(key, newLineList);
		}
	}


	/**
	 * Creates a new PickTicket object from a list of line items.
	 *
	 * @param lines
	 * @param description
	 * @param isPersonalUse
	 * @return newTicket
	 */
	protected PickTicket createPickTicket(List<PickListLine> lines, String description, boolean isPersonalUse) {
		PickTicket newTicket = new PickTicket();
		boolean checkMaxOrders = false;
		String name = "";

		//Determine the appropriate pick ticket name
		if (MMConstants.PickListDocument.OPTION_ZONES.equals(description)) {
			name = MMConstants.PickTicket.NAME_ZONE + lines.get(0).getBin().getZone().getZoneCd();
			if(isPersonalUse)
				name += " " + MMConstants.PickTicket.NAME_PERSONAL_USE;
			checkMaxOrders = true;
		} else if (MMConstants.PickListDocument.OPTION_ORDERS.equals(description)) {
			name = MMConstants.PickTicket.NAME_ORDER + lines.get(0).getSalesInstance().getOrderDocument().getOrderId();
			if(isPersonalUse)
				name += " " + MMConstants.PickTicket.NAME_PERSONAL_USE;
		}
		else if (MMConstants.PickListDocument.SEPARATE_WILL_CALLS.equals(description))
			name = MMConstants.PickTicket.NAME_WILL_CALL + lines.get(0).getSalesInstance().getOrderDocument().getOrderId();
		else if (MMConstants.PickListDocument.OPTION_SINGLE_LIST.equals(description)) {
			if (isSingleItemList(lines)) {
				name = MMConstants.PickTicket.NAME_ITEM + lines.get(0).getStock().getStockDistributorNbr();
			} else {
				BinRange range = getBinRange(lines);
				name = MMConstants.PickTicket.NAME_BIN_RANGE + range.fromLine.getBin().getZone().getZoneCd() + ":" + range.fromLine.getBin().getBinNbr() + " / " + range.toLine.getBin().getZone().getZoneCd() + ":" + range.toLine.getBin().getBinNbr();
				checkMaxOrders = true;
			}
			if(isPersonalUse)
				name += " " + MMConstants.PickTicket.NAME_PERSONAL_USE;
		}

		newTicket.setPickTicketName(name);
		newTicket.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_INIT);

		if (checkMaxOrders)
			lines = dropExtraOrders(lines, lines.get(0).getPickListDocument().getMaxOrders());

		computeAndAssignTubNumbers(lines);
		newTicket.setPickListLines(lines);

		for (PickListLine plLine : lines) {
			plLine.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_ASND);
		}
		
		newTicket.setOldestDate(getOldestDate(newTicket));
		newTicket.setOrderCount(getUniqueOrderCount(lines));
		newTicket.setLineCount(lines.size());

		return newTicket;
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#generatePdfTicket(org.kuali.ext.mm.businessobject.PickTicket)
	 */
	public ByteArrayOutputStream generatePdfTicket(PickTicket pickTicket) {

		// Sort ticket lines as they should appear on pdf
		sortLinesByLocation(pickTicket.getPickListLines());

		// Get filename from pickTicketNumber and create file
		PickListPdfService pdfService = SpringContext.getBean(PickListPdfService.class);
		String fileName = pickTicket.getPickTicketNumber() + ".pdf";
		pickTicket.setFileName(fileName);
		Object document = pdfService.createDocument(fileName);

		// Build and set the PDF header
		List<String> leftColumn = new ArrayList<String>();
		List<String> rightColumn = new ArrayList<String>();
		buildPdfHeader(pickTicket, leftColumn, rightColumn);
		pdfService.setHeader(leftColumn, rightColumn);

		buildPdfContent(document, pickTicket, pdfService);
		pdfService.completeDocument(document, pickTicket.getPickListLines().size(), getUniqueOrderCount(pickTicket.getPickListLines()));
        preparePickTicketForPrint(pickTicket);

		return pdfService.closeDocument(document);
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#generatePdfTicket(java.util.Collection)
	 */
	public ByteArrayOutputStream generatePdfTicket(Collection<PickTicket> pickTickets) {
		Iterator<PickTicket> ticketIterator = pickTickets.iterator();
		PickListPdfService pdfService = SpringContext.getBean(PickListPdfService.class);
		Object document = pdfService.createDocument("");

		List<String> leftColumn = new ArrayList<String>();
		List<String> rightColumn = new ArrayList<String>();

		while (ticketIterator.hasNext()) {
			PickTicket pickTicket = ticketIterator.next();
			
			// Sort ticket lines as they should appear on pdf
	        sortLinesByLocation(pickTicket.getPickListLines());
	        
			leftColumn.clear();
			rightColumn.clear();
			buildPdfHeader(pickTicket, leftColumn, rightColumn);
			pdfService.setHeader(leftColumn, rightColumn);
			buildPdfContent(document, pickTicket, pdfService);
			pdfService.completeDocument(document, pickTicket.getPickListLines().size(), getUniqueOrderCount(pickTicket.getPickListLines()));
			preparePickTicketForPrint(pickTicket);
			if (ticketIterator.hasNext())
				pdfService.nextPage(document);
		}
		return pdfService.closeDocument(document);
	}

	/**
	 * Builds the pdf header from the ticket data.
	 *
	 * @param ticket
	 * @param leftColumn
	 * @param rightColumn
	 */
	protected void buildPdfHeader(PickTicket ticket, List<String> leftColumn, List<String> rightColumn) {
		Map<String, String> potentialHeaderCells = new HashMap<String, String>();
		boolean willCall = true;
		boolean personalUse = false;
		Timestamp oldestDate = getOldestDate(ticket);

		
		KualiConfigurationService configService = SpringContext.getBean(KualiConfigurationService.class);
        
		String orderLabel = configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_ORDER);
		String departmentLabel = configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_DEPARTMENT);
		String buildingLabel = configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_BUILDING);
		String routeLabel = configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_ROUTE);
		String catalogNumberLabel = configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_CATALOG_NUMBER);
		String descriptionLabel = configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_DESCRIPTION);
		String willCallNote = configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_WILLCALL);
		String personalUseNote = configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_PERSONAL_USE);

		 FinancialSystemAdaptorFactory adaptorFactory = SpringContext.getBean(FinancialSystemAdaptorFactory.class);
		for (PickListLine line : ticket.getPickListLines()) {
		    OrderDocument orderDocument = line.getSalesInstance().getOrderDocument();		   
	        FinancialBuilding deliveryBuilding = adaptorFactory.getFinancialLocationService().getBuilding(orderDocument.getCampusCd(), orderDocument.getDeliveryBuildingCd());
	        String buildingName = (deliveryBuilding != null && StringUtils.isNotBlank(deliveryBuilding.getBuildingName())? deliveryBuilding.getBuildingName() : orderDocument.getDeliveryBuildingCd());
			checkHeaderFieldIsCommon(potentialHeaderCells, orderLabel, String.valueOf(line.getSalesInstance().getOrderDocument().getOrderId()));
			checkHeaderFieldIsCommon(potentialHeaderCells, departmentLabel, line.getSalesInstance().getOrderDocument().getDeliveryDepartmentNm());
			checkHeaderFieldIsCommon(potentialHeaderCells, buildingLabel, buildingName);
			checkHeaderFieldIsCommon(potentialHeaderCells, routeLabel, line.getRouteCd());
			if (checkHeaderFieldIsCommon(potentialHeaderCells, catalogNumberLabel, line.getStock().getStockDistributorNbr()))
				potentialHeaderCells.put(descriptionLabel, (line.getStock().getStockDesc().length() < 100 ? line.getStock().getStockDesc() : line.getStock().getStockDesc().substring(0, 99) + "..."));
			if (willCall && !line.getOrderDetail().isWillCall())
				willCall = false;
			if (!personalUse &&  MMConstants.OrderDocument.PROFILE_TYPE_PERSONAL.equals(line.getOrderDetail().getOrderDocument().getProfileTypeCode()))
				personalUse = true;
		}

		if (potentialHeaderCells.containsKey(orderLabel) && potentialHeaderCells.get(orderLabel) != null) {
			String orderNote = (willCall ? " " + willCallNote : "");
			orderNote += (personalUse ? " " + personalUseNote : "");
			leftColumn.add(orderLabel + potentialHeaderCells.get(orderLabel) + orderNote);
			if(StringUtils.isNotBlank(potentialHeaderCells.get(departmentLabel)))
				leftColumn.add(departmentLabel + potentialHeaderCells.get(departmentLabel));
		}
		if (willCall) {
			leftColumn.add(configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_ORDERED_BY) + ticket.getPickListLines().get(0).getSalesInstance().getCustomerProfile().getCustomer().getCustomerName());
			leftColumn.add(configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_SHIP_TO_ATTN));
		} else {
			if (potentialHeaderCells.containsKey(buildingLabel) && potentialHeaderCells.get(buildingLabel) != null) {
				leftColumn.add(buildingLabel + potentialHeaderCells.get(buildingLabel));
			}
			if (potentialHeaderCells.containsKey(routeLabel) && potentialHeaderCells.get(routeLabel) != null) {
				leftColumn.add(routeLabel + potentialHeaderCells.get(routeLabel));
			}
		}
		if (potentialHeaderCells.containsKey(catalogNumberLabel) && potentialHeaderCells.get(catalogNumberLabel) != null) {
			leftColumn.add(catalogNumberLabel + potentialHeaderCells.get(catalogNumberLabel));
			leftColumn.add(descriptionLabel + potentialHeaderCells.get(descriptionLabel));
		}
		if (ticket.getPickTicketName().contains(MMConstants.PickTicket.NAME_BIN_RANGE))
			leftColumn.add(ticket.getPickTicketName());

		DateTimeService dtService = SpringContext.getBean(DateTimeService.class);

		rightColumn.add(configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_PICKING_NUMBER) + ticket.getPickTicketNumber());
		rightColumn.add(configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_PRINT_DATE) + dtService.toDateString(dtService.getCurrentDate()));
		rightColumn.add(configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_OLDEST_DATE) + dtService.toDateString(oldestDate));
		ticket.refreshReferenceObject(MMConstants.PickListDocument.PICK_LIST_DOCUMENT);
		rightColumn.add(configService.getPropertyString(MMKeyConstants.PickTicket.HEADER_LABEL_WAREHOUSE) + ticket.getPickListDocument().getWarehouse().getWarehouseCd());
	}

	/**
	 * Builds the content for the pickTicket data and adds it to the pdf
	 *
	 * @param document
	 * @param pickTicket
	 * @param pdfService
	 */
	protected void buildPdfContent(Object document, PickTicket pickTicket, PickListPdfService pdfService) {
		KualiConfigurationService configService = SpringContext.getBean(KualiConfigurationService.class);
		String locationColumn = configService.getPropertyString(MMKeyConstants.PickTicket.COLUMN_LOCATION);
		String quantityColumn = configService.getPropertyString(MMKeyConstants.PickTicket.COLUMN_QTY);
		String unitOfIssueColumn = configService.getPropertyString(MMKeyConstants.PickTicket.COLUMN_UI);
		String descriptionColumn = configService.getPropertyString(MMKeyConstants.PickTicket.COLUMN_DESCRIPTION);
		String tubColumn = configService.getPropertyString(MMKeyConstants.PickTicket.COLUMN_TUB);
		String orderColumn = configService.getPropertyString(MMKeyConstants.PickTicket.COLUMN_ORDER);
		String itemColumn = configService.getPropertyString(MMKeyConstants.PickTicket.COLUMN_ITEM);
		String pickedColumn = configService.getPropertyString(MMKeyConstants.PickTicket.COLUMN_PICKED);

		// build zone header
		List<String> zoneHeaderColumns = new ArrayList<String>();
		zoneHeaderColumns.add(locationColumn);
		zoneHeaderColumns.add(quantityColumn);
		zoneHeaderColumns.add(unitOfIssueColumn);
		zoneHeaderColumns.add(descriptionColumn);
		zoneHeaderColumns.add(tubColumn);
		zoneHeaderColumns.add(orderColumn);
		zoneHeaderColumns.add(itemColumn);
		zoneHeaderColumns.add(pickedColumn);

		Map<String, List<String>> zoneData = new HashMap<String, List<String>>();

		String currentZone = null;
		for (PickListLine line : pickTicket.getPickListLines()) {
			if (currentZone == null || line.getBin().getZone().getZoneCd().compareTo(currentZone) != 0) {
				if (currentZone != null)
					pdfService.writeZone(document, currentZone, zoneHeaderColumns, zoneData);

				currentZone = line.getBin().getZone().getZoneCd();
				zoneData.clear();
				for (String headerCell : zoneHeaderColumns) {
					zoneData.put(headerCell, new ArrayList<String>());
				}
			}
			zoneData.get(locationColumn).add(line.getBin().getZone().getWarehouse().getWarehouseCd() + " / " + currentZone + " / " + line.getBin().getBinNbr() + " / " + line.getBin().getShelfId() + " / " + line.getBin().getShelfIdNbr());
			zoneData.get(quantityColumn).add(String.valueOf(line.getStockQty()));
			zoneData.get(unitOfIssueColumn).add(line.getOrderDetail().getStockUnitOfIssueCd());
			zoneData.get(descriptionColumn).add(line.getStock().getStockDesc());
			zoneData.get(tubColumn).add(String.valueOf(line.getPickTubNbr()));
			zoneData.get(orderColumn).add(String.valueOf(line.getSalesInstance().getOrderDocument().getOrderId()));
			zoneData.get(itemColumn).add(line.getStock().getStockDistributorNbr());
			zoneData.get(pickedColumn).add("");
		}

		// write the last zone
		if (zoneData.size() > 0) {
			pdfService.writeZone(document, currentZone, zoneHeaderColumns, zoneData);
		}
	}

	/**
	 * Determines whether or not to include a field in the pdf header of a ticket
	 * by checking to see if that field has a common value amongst all line items
	 *
	 * @param potentialHeaderCells
	 * @param fieldLabel
	 * @param fieldValue
	 * @return true if fieldValue equals the the previous values of fieldLabel in the potentialHeaderCells map
	 */
	private boolean checkHeaderFieldIsCommon(Map<String, String> potentialHeaderCells, String fieldLabel, String fieldValue) {
		if (potentialHeaderCells.containsKey(fieldLabel)) {
			if (potentialHeaderCells.get(fieldLabel) == null)
				return false;
			else if (!potentialHeaderCells.get(fieldLabel).equals(fieldValue)) {
				potentialHeaderCells.put(fieldLabel, null);
				return false;
			}
		} else {
			potentialHeaderCells.put(fieldLabel, fieldValue);
		}
		return true;
	}

	/**
	 * Computes the tub numbers that should be assigned to each line item based on the
	 * order to which it belongs.
	 *
	 * @param lines
	 */
	private void computeAndAssignTubNumbers(List<PickListLine> lines) {
		List<Long> tubList = new ArrayList<Long>();

		for (PickListLine line : lines) {

			if (!tubList.contains(line.getSalesInstance().getOrderDocument().getOrderId()))
				tubList.add(line.getSalesInstance().getOrderDocument().getOrderId());

			line.setPickTubNbr(tubList.indexOf(line.getSalesInstance().getOrderDocument().getOrderId()) + 1);
		}

	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#preparePickTicketForPrint(org.kuali.ext.mm.businessobject.PickTicket)
	 */
	public PickTicket preparePickTicketForPrint(PickTicket pickTicket) {
		BusinessObjectService boService = SpringContext.getBean(BusinessObjectService.class);		

		if(MMConstants.PickStatusCode.PICK_STATUS_INIT.equals(pickTicket.getPickStatusCodeCd())) {
			pickTicket.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_PRTD);
			for (PickListLine plLine : pickTicket.getPickListLines()) {
                plLine.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_PRTD);
			}
		}    
		
		boService.save(pickTicket);

		return pickTicket;
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#getAllUnprintedTickets()
	 */
	public Collection<PickTicket> getAllUnprintedTickets() {
		Map<String, String> fieldValues = new HashMap<String, String>();
		PickTicketDao pickTicketDao = SpringContext.getBean(PickTicketDao.class);
		Integer resultsLimit = Integer.parseInt(getParameterService().getParameterValue(MMConstants.MM_NAMESPACE, MMConstants.Parameters.LOOKUP, MMConstants.Parameters.MAX_PRINT_ALL_RESULTS));
		fieldValues.put(MMConstants.PickStatusCode.PICK_STATUS_CODE, MMConstants.PickStatusCode.PICK_STATUS_INIT);
		Collection<PickTicket> results = pickTicketDao.findMatchingBoundedAndOrderBy(PickTicket.class, fieldValues, MMConstants.PickTicket.PICK_TICKET_NUMBER, true, resultsLimit);
		return results;
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#pickListLinesMatchDocumentWarehouse(org.kuali.ext.mm.document.PickListDocument)
	 */
	public boolean pickListLinesMatchDocumentWarehouse(PickListDocument pickListDocument) {
		pickListDocument.refreshReferenceObject(MMConstants.PickListDocument.WAREHOUSE);
		for (PickListLine line : pickListDocument.getPickListLines()) {
			if (!pickListDocument.getWarehouse().getWarehouseCd().equals(line.getBin().getZone().getWarehouse().getWarehouseCd()))
				return false;
		}
		return true;
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#isPickListLinesStatusInit(org.kuali.ext.mm.document.PickListDocument)
	 */
	public boolean isPickListLinesStatusInit(PickListDocument pickListDocument) {

		for (PickListLine line : pickListDocument.getPickListLines()) {
			if (!MMConstants.PickStatusCode.PICK_STATUS_INIT.equals(line.getPickStatusCode().getPickStatusCode()))
				return false;
		}

		return true;
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#sortLinesByLocation(java.util.List)
	 */
	public void sortLinesByLocation(List<PickListLine> lines) {
		// Sort ticket lines as they should appear on pdf
		Collections.sort(lines, new sortByShelfNumber());
		Collections.sort(lines, new sortByShelf());
		Collections.sort(lines, new sortByBin());
		Collections.sort(lines, new sortByZone());

	}

	/**
	 * Convenience method to combine the two lists of ad hoc recipients into one
	 * which should be done before calling any of the document service methods
	 * that expect a list of ad hoc recipients
	 *
	 * @param kualiDocumentFormBase
	 * @return List
	 */
	protected List<AdHocRouteRecipient> combineAdHocRecipients(PickListDocument document) {
		List<AdHocRouteRecipient> adHocRecipients = new ArrayList<AdHocRouteRecipient>();
		adHocRecipients.addAll(document.getAdHocRoutePersons());
		adHocRecipients.addAll(document.getAdHocRouteWorkgroups());
		return adHocRecipients;
	}
	
    public PickListLine createPickListLineForTrueBuyout(OrderDetail orderDetail) {
        PickListLine newLine = new PickListLine();
        
        newLine.setOrderDetailId(orderDetail.getOrderDetailId());
        newLine.setSalesInstanceId(orderDetail.getSalesInstanceId());
        newLine.setStockId(orderDetail.getCatalogItem().getStockId());
        newLine.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_BACK);
        newLine.setStockQty(orderDetail.getOrderItemQty());
        newLine.setPickStockQty(0);
        newLine.setBackOrderQty(orderDetail.getOrderItemQty());
        newLine.setCreatedDate(KNSServiceLocator.getDateTimeService().getCurrentTimestamp());                
        
        
        return newLine;
    }

	/**
	 * @see org.kuali.ext.mm.service.PickListService#createPickListLinesFromBackOrder(org.kuali.ext.mm.businessobject.BackOrder, boolean)
	 */
	public List<PickListLine> createPickListLinesFromBackOrder(BackOrder backOrder, boolean strictQuantity) {	    
		List<PickListLine> createdLines = new ArrayList<PickListLine>();
	    PickListLine fromLine = backOrder.getFromPickListLine();
		
		PickListLine tempLine = new PickListLine();
		tempLine.setOrderDetailId(fromLine.getOrderDetailId());
		tempLine.setSalesInstanceId(fromLine.getSalesInstanceId());        		
		tempLine.setRouteCd(fromLine.getRouteCd());
		tempLine.setBackOrderId(backOrder.getBackOrderId());                   
        
		if(ObjectUtils.isNull(fromLine.getOrderDetail()))
		        fromLine.refreshReferenceObject(MMConstants.PickListLine.ORDER_DETAIL);
		if(ObjectUtils.isNull(fromLine.getOrderDetail().getCatalogItem()))
		        fromLine.getOrderDetail().refreshReferenceObject(MMConstants.OrderDetail.CATALOG_ITEM);
		
        CatalogItem catalogItem = fromLine.getOrderDetail().getCatalogItem();
        
        createdLines = createPickListLinesFromTemplate(catalogItem, 
                backOrder.getBackOrderStockQty(), 
                tempLine, strictQuantity);
		return createdLines;
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#createPickListLineFromOrderDetail(org.kuali.ext.mm.businessobject.OrderDetail)
	 */
	public List<PickListLine> createPickListLinesFromOrderDetail(OrderDetail orderDetail) {
	    List<PickListLine> createdLines = new ArrayList<PickListLine>();
        PickListLine tempLine = new PickListLine();
		tempLine.setOrderDetailId(orderDetail.getOrderDetailId());
		tempLine.setSalesInstanceId(orderDetail.getSalesInstanceId());
		//TODO: newLine.setRoute();
		
		if(ObjectUtils.isNull(orderDetail.getCatalogItem()))
		    orderDetail.refreshReferenceObject(MMConstants.OrderDetail.CATALOG_ITEM);
        CatalogItem catalogItem = orderDetail.getCatalogItem();
        
		createdLines = createPickListLinesFromTemplate(catalogItem, 
		        orderDetail.getOrderItemQty(), tempLine, false);
		return createdLines;
	}
	
	/**
	 * Creates a list of PickListLines from a template PickListLine.  Lines returned will be
	 * determined based on the available for each bin belonging to the the given stock.
	 * If strictQuantity is true the entire quantity must be filled by the sum of the lines
	 * or no lines are returned. Otherwise, the remaining quantity will be dumped into the 
	 * first bin of the list.
	 * 
	 * @param stock
	 * @param quantity
	 * @param lineTemplate
	 * @param strictQuantity
	 * @return list of PickListLine items
	 */
	private List<PickListLine> createPickListLinesFromTemplate(CatalogItem item, Integer quantity, PickListLine lineTemplate, boolean strictQuantity) {
	    List<PickListLine> newPickListLines = new ArrayList<PickListLine>();
	    List<Bin> bins = pickListHelperService.getBinsForPicking(item, quantity);
        Integer remainingQty = quantity;

        for(Bin bin : bins) {
            Integer binQtyCommitted = getStockService().getCommittedBinQuantity(bin.getBinId());
            Integer binQtyAvailable = bin.getStockBalance().getQtyOnHand() - binQtyCommitted;
            //make sure to add the first line no matter what
            if(binQtyAvailable > 0 || newPickListLines.isEmpty()) {
                Integer qtyToPick = (remainingQty > binQtyAvailable) ? binQtyAvailable : remainingQty;
                remainingQty = (remainingQty > binQtyAvailable) ? remainingQty - binQtyAvailable : 0;
                if(qtyToPick <= 0 && !newPickListLines.isEmpty())
                    break;
                
                PickListLine newLine = new PickListLine();
                newLine.setOrderDetailId(lineTemplate.getOrderDetailId());
                newLine.setSalesInstanceId(lineTemplate.getSalesInstanceId());
                newLine.setRouteCd(lineTemplate.getRouteCd());
                newLine.setBackOrderId(lineTemplate.getBackOrderId());
                newLine.setStockId(item.getStockId());
                newLine.setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_INIT);                
                newLine.setCreatedDate(KNSServiceLocator.getDateTimeService().getCurrentTimestamp());                
                newLine.setBinId(bin.getBinId());
                newLine.setStockQty(qtyToPick);

                newPickListLines.add(newLine);
            }
        }
        
        if(remainingQty > 0 && strictQuantity) 
            newPickListLines.clear();
        else if(remainingQty > 0 && !newPickListLines.isEmpty())    
            newPickListLines.get(0).setStockQty(newPickListLines.get(0).getStockQty() + remainingQty);           
        else if(!newPickListLines.isEmpty() && newPickListLines.get(0).getStockQty() < 1)
            newPickListLines.remove(0);
        
	    return newPickListLines;
	}
	
//	/**
//	 * @see org.kuali.ext.mm.service.PickListService#getTotalPickedQuantity(org.kuali.ext.mm.businessobject.OrderDetail)
//	 */
//	public Integer getTotalPickedQuantity(OrderDetail detail) {
//	    Integer count = 0;
//	    Map<String, Object> fieldValues = new HashMap<String, Object>();
//	    List<String> pickedStatusCodes = new ArrayList<String>();
//	    pickedStatusCodes.add(MMConstants.PickStatusCode.PICK_STATUS_PBCK);
//	    pickedStatusCodes.add(MMConstants.PickStatusCode.PICK_STATUS_PCKD);
//        fieldValues.put(MMConstants.PickListLine.ORDER_DETAIL_ID, detail.getOrderDetailId());
//        fieldValues.put(MMConstants.PickListLine.PICK_STATUS_CODE_CD, pickedStatusCodes);
//        Collection<PickListLine> results = KNSServiceLocator.getBusinessObjectService().findMatching(PickListLine.class, fieldValues);
//	    
//        Iterator<PickListLine> itPickLine = results.iterator();
//        
//        while(itPickLine.hasNext()) {
//            count += itPickLine.next().getPickStockQty();
//        }
//        
//	    return count;
//	}
	
//	/**
//	 * @see org.kuali.ext.mm.service.PickListService#getTotalQuantityToPick(org.kuali.ext.mm.businessobject.OrderDetail)
//	 */
//	public Integer getTotalQuantityToPick(OrderDetail detail) {        
//        Map<String, Object> fieldValues = new HashMap<String, Object>();
//        fieldValues.put(MMConstants.PickListLine.ORDER_DETAIL_ID, detail.getOrderDetailId());
//        Collection<PickListLine> pickLines = KNSServiceLocator.getBusinessObjectService().findMatching(PickListLine.class, fieldValues);
//        Collection<BackOrder> backOrders = SpringContext.getBean(BackOrderService.class)
//                .getBackOrdersForOrderDetail(detail.getOrderDetailId());        
//        
//        Integer initialTotal = 0;
//        Iterator<PickListLine> itPickLine = pickLines.iterator();
//        while(itPickLine.hasNext()) {
//            PickListLine line = itPickLine.next();
//            if(StringUtils.isBlank(line.getBackOrderId()))
//                initialTotal += line.getStockQty();
//        }
//        
//        Iterator<BackOrder> itBackOrder = backOrders.iterator();
//        Integer boAlteredSum = 0; 
//        while(itBackOrder.hasNext()) {
//            BackOrder bo = itBackOrder.next();
//            bo.refreshReferenceObject(MMConstants.BackOrder.FROM_PICK_LIST_LINE);
//            boAlteredSum += (bo.getFromPickListLine().getBackOrderQty() - bo.getBackOrderStockQty());               
//        }
//        
//        return initialTotal - boAlteredSum;
//    }
	
	public Collection<OrderDetail> getOrderDetailsFromPickTicket(PickTicket ticket) {
	    HashSet<Integer> orderDetailIds = new HashSet<Integer>();
	    if(ObjectUtils.isNull(ticket.getPickListLines()))
	        return new ArrayList<OrderDetail>();
	    
	    for(PickListLine line : ticket.getPickListLines()) {
	        orderDetailIds.add(line.getOrderDetailId());
	    }
	    Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(MMConstants.OrderDetail.ORDER_DETAIL_ID, orderDetailIds);
        Collection<OrderDetail> results = KNSServiceLocator.getBusinessObjectService().findMatching(OrderDetail.class, fieldValues);
	    
	    return results;
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#savePickListLine(org.kuali.ext.mm.businessobject.PickListLine)
	 */
	public void savePickListLine(PickListLine line) {
		KNSServiceLocator.getBusinessObjectService().save(line);
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#getPickTicketByNumber(java.lang.String)
	 */
	public PickTicket getPickTicketByNumber(String pickTicketNumber) {
		return SpringContext.getBean(BusinessObjectService.class).findBySinglePrimaryKey(PickTicket.class, pickTicketNumber);
	}

	/**
	 * @see org.kuali.ext.mm.service.PickListService#ticketHasTrackableStock(org.kuali.ext.mm.businessobject.PickTicket)
	 */
	public boolean ticketHasTrackableStock(PickTicket ticket) {
		for(PickListLine line : ticket.getPickListLines()) {
			if(line.isTrackableWithSerialNumber())
				return true;
		}

		return false;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public StockService getStockService() {
		return stockService;
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

    public void setPickListHelperService(PickListHelperService pickListHelperService) {
        this.pickListHelperService = pickListHelperService;
    }

    public PickListHelperService getPickListHelperService() {
        return pickListHelperService;
    }

}
