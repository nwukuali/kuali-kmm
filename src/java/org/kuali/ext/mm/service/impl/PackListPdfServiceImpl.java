package org.kuali.ext.mm.service.impl;

import com.lowagie.text.Document;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.*;
import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;
import org.kuali.ext.mm.service.BackOrderService;
import org.kuali.ext.mm.service.PackListPdfService;
import org.kuali.ext.mm.service.PickVerifyService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class PackListPdfServiceImpl implements PackListPdfService {
	protected static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PickListServiceImpl.class);

	private ParameterService parameterService;
	private ConfigurationService KualiConfigurationService;
	private ByteArrayOutputStream pdfContent;
	private PdfCopy copy;
	
	private class PdfPackingLine {
	    private String lineNumber;
	    private String orderQuantity;
	    private String shipQuantity;
	    private String backOrderQuantity;
	    private String backOrderExpectedDate;
	    private String unitOfIssue;
	    private String itemNumber;
	    private String description;
	    private String totalPrice;
	    
        public void setLineNumber(String lineNumber) {
            this.lineNumber = lineNumber;
        }
        public String getLineNumber() {
            return lineNumber;
        }
        public void setOrderQuantity(String orderQuantity) {
            this.orderQuantity = orderQuantity;
        }
        public String getOrderQuantity() {
            return orderQuantity;
        }
        public void setShipQuantity(String shipQuantity) {
            this.shipQuantity = shipQuantity;
        }
        public String getShipQuantity() {
            return shipQuantity;
        }
        public void setBackOrderQuantity(String backOrderQuantity) {
            this.backOrderQuantity = backOrderQuantity;
        }
        public String getBackOrderQuantity() {
            return backOrderQuantity;
        }
        public void setBackOrderExpectedDate(String backOrderExpectedDate) {
            this.backOrderExpectedDate = backOrderExpectedDate;
        }
        public String getBackOrderExpectedDate() {
            return backOrderExpectedDate;
        }
        public void setUnitOfIssue(String unitOfIssue) {
            this.unitOfIssue = unitOfIssue;
        }
        public String getUnitOfIssue() {
            return unitOfIssue;
        }
        public void setItemNumber(String itemNumber) {
            this.itemNumber = itemNumber;
        }
        public String getItemNumber() {
            return itemNumber;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getDescription() {
            return description;
        }
        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }
        public String getTotalPrice() {
            return totalPrice;
        }
	}

	/* (non-Javadoc)
	 * @see org.kuali.ext.mm.service.PackListPdfService#closeDocument(java.lang.Object)
	 */
	public ByteArrayOutputStream closeDocument(Object document) {
		Document doc = (Document)document;

		try {
			doc.close();
		}
		catch (Exception e) {
			throw new ExceptionConverter(e);
		}
		return pdfContent;
	}

	/* (non-Javadoc)
	 * @see org.kuali.ext.mm.service.PackListPdfService#createDocument()
	 */
	public Object createDocument() {
		Document doc = new Document(PageSize.LETTER, 18, 18, 18, 18);

		try {
			pdfContent = new ByteArrayOutputStream();
			copy = new PdfCopy(doc, pdfContent);
            doc.open();
		}
		catch (Exception e) {
			throw new ExceptionConverter(e);
		}

		return doc;
	}

	/* (non-Javadoc)
	 * @see org.kuali.ext.mm.service.PackListPdfService#addPackingList(java.util.List, java.lang.String)
	 */
	public void addPackingList(List<PickListLine> pickListLines, String messages) {
		List<PdfPackingLine> pdfPackingLines = expandToPdfPackingLines(pickListLines);
		PdfStamper stamper = null;
		Integer maxLinesPerPage = Integer.parseInt(getParameterService().getParameterValueAsString(MMConstants.MM_NAMESPACE, MMConstants.Parameters.DOCUMENT, MMConstants.Parameters.PACKING_LIST_PDF_MAX_LINES));
		String pdfTemplate = getParameterService().getParameterValueAsString(MMConstants.MM_NAMESPACE, MMConstants.Parameters.DOCUMENT, MMConstants.Parameters.PACKING_LIST_PDF);
		try {
			//split up data that requires more than one page and repeat stamping for each necessary page
			while(!pdfPackingLines.isEmpty()) {
				ByteArrayOutputStream packingListContent = new ByteArrayOutputStream();
				PdfReader reader = new PdfReader(getKualiConfigurationService().getPropertyValueAsString(MMKeyConstants.EXTERNAL_REPORTS_DIRECTORY_KEY) + "/" + pdfTemplate);
	            stamper = new PdfStamper(reader, packingListContent);
	            stampPackingListHeader(stamper, pickListLines);	                
	            if(pdfPackingLines.size() > maxLinesPerPage) {
	                stampPackingListContent(stamper, pdfPackingLines.subList(0, maxLinesPerPage));
		            pdfPackingLines = pdfPackingLines.subList(maxLinesPerPage, pdfPackingLines.size());
	            }
	            else {
	            	stampPackingListContent(stamper, pdfPackingLines);
		            pdfPackingLines.clear();
	            }
	            stampPackingListFooter(stamper, pickListLines, messages);
	            stamper.setFormFlattening(true);
	            stamper.close();

	            //append new stamped pdf page into full packing lists pdf
	            ByteArrayInputStream readPackingListContent = new ByteArrayInputStream(packingListContent.toByteArray());
	            reader = new PdfReader(readPackingListContent);
	            PdfImportedPage page = copy.getImportedPage(reader, 1);
	            copy.addPage(page);
			}
		}
		catch (Exception e) {
			throw new ExceptionConverter(e);
		}

	}

	/**
     * @param pickListLines
     * @return 
     */
    private List<PdfPackingLine> expandToPdfPackingLines(List<PickListLine> pickListLines) {
        List<PdfPackingLine> pdfPackingLines = new ArrayList<PdfPackingLine>();
        BackOrderService backOrderService = SpringContext.getBean(BackOrderService.class);
        String serialLabel = getKualiConfigurationService().getPropertyValueAsString(MMKeyConstants.PackingList.LINE_SERIAL_LABEL);
        
        int lineNumber = 0;
        for(PickListLine pickLine : pickListLines) {
            MMDecimal lineTotal = pickLine.getOrderDetail().getOrderItemPriceAmt()
                    .add(pickLine.getOrderDetail().getOrderItemTaxAmt())
                    .multiply(new MMDecimal(pickLine.getPickStockQty()));
            
            PdfPackingLine newPackLine = new PdfPackingLine();
            newPackLine.setLineNumber(String.valueOf(++lineNumber));
            newPackLine.setOrderQuantity(String.valueOf(pickLine.getStockQty()));
            newPackLine.setShipQuantity(String.valueOf(pickLine.getPickStockQty()));
            newPackLine.setBackOrderQuantity(String.valueOf(pickLine.getBackOrderQty()));
            newPackLine.setBackOrderExpectedDate(backOrderService.getBackOrderReliefDate(pickLine));
            newPackLine.setUnitOfIssue(pickLine.getOrderDetail().getStockUnitOfIssue().getUnitOfIssueCode());
            newPackLine.setItemNumber(pickLine.getOrderDetail().getDistributorNbr());
            newPackLine.setDescription(pickLine.getOrderDetail().getOrderItemDetailDesc());       
            newPackLine.setTotalPrice(lineTotal.kualiDecimalValue().toString());
            pdfPackingLines.add(newPackLine);
            for(StockPackNote packingNote : pickLine.getStock().getStockPackNotes()) {
                PdfPackingLine noteLine = new PdfPackingLine();
                noteLine.setDescription(packingNote.getPackListAnnouncement().getPackListAnnouncementDesc());
                pdfPackingLines.add(noteLine);
            }
            for(Rental rental : pickLine.getRentals()) {
                PdfPackingLine rentalLine = new PdfPackingLine();
                rentalLine.setDescription(serialLabel + rental.getRentalSerialNumber());
                pdfPackingLines.add(rentalLine);
            }
        }
        
        return pdfPackingLines;
    }

    private void stampPackingListHeader(PdfStamper stamper, List<PickListLine> pickListLines) {
	    if(pickListLines.size() <= 0)
            return;
	    ConfigurationService configService = getKualiConfigurationService();
        OrderDocument orderDocument = pickListLines.get(0).getSalesInstance().getOrderDocument();
        Profile orderProfile = orderDocument.getCustomerProfile();
        FinancialSystemAdaptorFactory adaptorFactory = SpringContext.getBean(FinancialSystemAdaptorFactory.class);
        FinancialBuilding deliveryBuilding = adaptorFactory.getFinancialLocationService().getBuilding(orderDocument.getCampusCd(), orderDocument.getDeliveryBuildingCd());
       // FinancialRoom deliveryRoom = adaptorFactory.getFinancialLocationService().getRoom(orderDocument.getCampusCd(), orderDocument.getDeliveryBuildingCd(), orderDocument.getDeliveryBuildingRmNbr());
        try {
            DateTimeService dateTimeService = CoreApiServiceLocator.getDateTimeService();
            AcroFields formFields = stamper.getAcroFields();
            if(pickListLines.get(0).getPickTicket().getPickTicketName().contains(MMConstants.PickTicket.NAME_ZONE))
                formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_ZONE), pickListLines.get(0).getPickTicket().getPickTicketName());
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_TIMESTAMP), dateTimeService.toDateTimeString(dateTimeService.getCurrentDate()));
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_PICKING_LIST), pickListLines.get(0).getPickTicket().getPickTicketNumber());
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_ORDER_NUMBER), String.valueOf(orderDocument.getOrderId()));
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_TUB), String.valueOf(pickListLines.get(0).getPickTubNbr()));
            if(!pickListLines.get(0).getSalesInstance().getOrderDocument().getAccounts().isEmpty())
                formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_REFERENCE_NUMBER), orderDocument.getAccounts().get(0).getDepartmentReferenceText());
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_DATE_ORDERED), dateTimeService.toDateTimeString(pickListLines.get(0).getCreatedDate()));
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_DEPARTMENT), orderDocument.getDeliveryDepartmentNm());
            orderProfile.refreshReferenceObject(MMConstants.Profile.CUSTOMER);
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_REQUESTED_BY), orderProfile.getCustomer().getCustomerName());
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_REQUEST_BUILDING), (deliveryBuilding!=null && StringUtils.isNotBlank(deliveryBuilding.getBuildingName()) ? deliveryBuilding.getBuildingName(): orderDocument.getDeliveryBuildingCd()));
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_REQUEST_ROOM), orderProfile.getDeliveryBuildingRoomNumber());
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_PHONE), orderProfile.getProfilePhoneNumber());
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_DELIVER_TO), orderDocument.getShippingAddress().getAddressName());
//          formFields.setField(configService.getPropertyString(MMKeyConstants.PackingList.HEADER_FIELD_DEPARTMENT_NAME), orderDocument.getDeliveryDepartmentNm());
            //formFields.setField(configService.getPropertyString(MMKeyConstants.PackingList.HEADER_FIELD_DELIVER_BUILDING), (deliveryBuilding != null && deliveryBuilding.getBuildingName() != null ? deliveryBuilding.getBuildingName() : orderDocument.getDeliveryBuildingCd()));
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_DEPARTMENT_NAME), orderDocument.getShippingAddress().getAddressLine1());
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_DELIVER_BUILDING), orderDocument.getShippingAddress().getAddressLine2());
            String cityStateZip = orderDocument.getShippingAddress().getAddressCityName() + ", "
                + orderDocument.getShippingAddress().getAddressStateCode() + " "
                + orderDocument.getShippingAddress().getAddressPostalCode();
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_CITY_STATE_ZIP), cityStateZip);
        }
        catch (Exception e) {
            throw new ExceptionConverter(e);
        }
	}
	
	private void stampPackingListContent(PdfStamper stamper, List<PdfPackingLine> pdfPackingLines) {
        ConfigurationService configService = getKualiConfigurationService();
	    try {
            AcroFields formFields = stamper.getAcroFields();
            for(int i=0; i < pdfPackingLines.size(); i++) {
    			String index = "[" + String.valueOf(i) + "]";
    			formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_LINE) + index, pdfPackingLines.get(i).getLineNumber());
    			formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_ORDER_QUANTITY) + index, pdfPackingLines.get(i).getOrderQuantity());
    			formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_SHIP_QUANTITY) + index, pdfPackingLines.get(i).getShipQuantity());
    			formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_BACKORDER_QUANTITY) + index, pdfPackingLines.get(i).getBackOrderQuantity());
    			formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_BACKORDER_DATE) + index, pdfPackingLines.get(i).getBackOrderExpectedDate());
    			formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_UI) + index, pdfPackingLines.get(i).getUnitOfIssue());
    			formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_ITEM_NUMBER) + index, pdfPackingLines.get(i).getItemNumber());
    			formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_DESCRIPTION) + index, pdfPackingLines.get(i).getDescription());
    			formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_TOTAL_PRICE) + index, pdfPackingLines.get(i).getTotalPrice());
            }
		}
		catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}
	
	private void stampPackingListFooter(PdfStamper stamper, List<PickListLine> pickListLines, String messages) {
	    ConfigurationService configService = getKualiConfigurationService();
	    PickVerifyService pickverifyService = SpringContext.getBean(PickVerifyService.class);
	    List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();	    
	    
        for(PickListLine line : pickListLines)
	        orderDetails.add(line.getOrderDetail());
        
	    KualiDecimal totalOrder = pickverifyService.computePickedTotal(pickListLines);
        KualiDecimal totalAmountSaved = pickverifyService.computePickedAmountSaved(pickListLines);
        try {
    	    AcroFields formFields = stamper.getAcroFields();
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_TOTAL_ORDER), totalOrder.toString());
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_TOTAL_SAVED), totalAmountSaved.toString());
            formFields.setField(configService.getPropertyValueAsString(MMKeyConstants.PackingList.FOOTER_FIELD_MESSAGES), messages);
        }
        catch (Exception e) {
            throw new ExceptionConverter(e);
        }

	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setKualiConfigurationService(ConfigurationService kualiConfigurationService) {
		KualiConfigurationService = kualiConfigurationService;
	}

	public ConfigurationService getKualiConfigurationService() {
		return KualiConfigurationService;
	}

}
