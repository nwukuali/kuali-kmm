/**
 *
 */
package org.kuali.ext.mm.service.impl;

import com.lowagie.text.Document;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.*;
import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ReturnDetail;
import org.kuali.ext.mm.businessobject.StoresPersistableBusinessObject;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;
import org.kuali.ext.mm.service.RTVPDFService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.krad.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

//import org.kuali.rice.core.api.datetime.DateTimeService;
//import org.kuali.rice.kns.service.ParameterService;


/**
 * @author rponraj
 */
public class RTVPDFServiceImpl implements RTVPDFService {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(RTVPDFServiceImpl.class);

    private ParameterService parameterService;
    private ConfigurationService kualiConfigurationService;
    private ByteArrayOutputStream pdfContent;
    private PdfCopy copy;

    public ByteArrayOutputStream closeDocument(Object document) {
        Document doc = (Document) document;

        try {
            doc.close();
        }
        catch (Exception e) {
            throw new ExceptionConverter(e);
        }
        return pdfContent;
    }

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

    public void addReturnLineToList(List<ReturnDetail> returnOrderLines, String messages) {
        List<ReturnDetail> returnListLinesLimitCheck = new ArrayList<ReturnDetail>();

        returnListLinesLimitCheck.addAll(returnOrderLines);
        int pageNum = 0;
        PdfStamper stamper = null;
        Integer maxLinesPerPage = Integer.parseInt(getParameterService().getParameterValueAsString(
					MMConstants.MM_NAMESPACE, MMConstants.Parameters.DOCUMENT,
					MMConstants.Parameters.PACKING_LIST_PDF_MAX_LINES));
        String pdfTemplate = getParameterService().getParameterValueAsString(MMConstants.MM_NAMESPACE,
					MMConstants.Parameters.DOCUMENT, MMConstants.Parameters.PACKING_LIST_PDF);
        try {
            // split up data that requires more than one page and repeat stamping for each necessary page
            while (!returnListLinesLimitCheck.isEmpty()) {

                ByteArrayOutputStream packingListContent = new ByteArrayOutputStream();
                PdfReader reader = new PdfReader(getKualiConfigurationService().getPropertyValueAsString(
                        MMKeyConstants.EXTERNAL_REPORTS_DIRECTORY_KEY)
                        + "/" + pdfTemplate);
                stamper = new PdfStamper(reader, packingListContent);
                if (returnListLinesLimitCheck.size() > maxLinesPerPage) {
                    stampReturnListContent(stamper, returnListLinesLimitCheck.subList(0,
                            maxLinesPerPage.intValue()), messages, pageNum++, maxLinesPerPage
                            .intValue());
                    returnListLinesLimitCheck = returnListLinesLimitCheck.subList(maxLinesPerPage,
                            returnListLinesLimitCheck.size());
                }
                else {
                    stampReturnListContent(stamper, returnListLinesLimitCheck, messages, pageNum,
                            maxLinesPerPage.intValue());
                    returnListLinesLimitCheck.clear();
                }
                stamper.setFormFlattening(true);
                stamper.close();

                // append new stamped pdf page into full packing lists pdf
                ByteArrayInputStream readPackingListContent = new ByteArrayInputStream(
                    packingListContent.toByteArray());
                reader = new PdfReader(readPackingListContent);
                PdfImportedPage page = copy.getImportedPage(reader, 1);
                copy.addPage(page);
            }
        }
        catch (Exception e) {
            LOG.error(e);
            throw new ExceptionConverter(e);
        }

    }

    private OrderDocument getOrderDocNumber(List<ReturnDetail> returnLines) {
        for (ReturnDetail rdetail : returnLines) {
            if (rdetail.getOrderDocumentNumber() != null
                    && ObjectUtils.isNotNull(rdetail.getOrderDetail())
                    && ObjectUtils.isNotNull(rdetail.getOrderDetail().getOrderDocument())) {
                return rdetail.getOrderDetail().getOrderDocument();
            }

            if (ObjectUtils.isNotNull(rdetail.getReturnDoc().getOrderDocument()))
                return rdetail.getReturnDoc().getOrderDocument();

        }
        return null;
    }

    private void stampReturnListContent(PdfStamper stamper, List<ReturnDetail> returnLines,
            String messages, int pageNum, int maxLinesPerPage) {
        if (returnLines.size() <= 0)
            return;

        ConfigurationService configService = SpringContext
                .getBean(ConfigurationService.class);
        try {
            DateTimeService dateTimeService = CoreApiServiceLocator.getDateTimeService();
            AcroFields formFields = stamper.getAcroFields();

            OrderDocument odoc = getOrderDocNumber(returnLines);
            formFields.setField(configService
                    .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_TIMESTAMP),
                    dateTimeService.toDateTimeString(dateTimeService.getCurrentDate()));
            Warehouse warehouse = null;

            if (odoc != null) {
                FinancialSystemAdaptorFactory adaptorFactory = SpringContext
                        .getBean(FinancialSystemAdaptorFactory.class);
                FinancialBuilding deliveryBuilding = adaptorFactory.getFinancialLocationService()
                        .getBuilding(odoc.getCampusCd(), odoc.getDeliveryBuildingCd());
                formFields.setField(configService
                        .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_ORDER_NUMBER),
                        String.valueOf(odoc.getOrderId()));
                warehouse = odoc.getWarehouse();
                // TODO: need to find the necessity
                if (ObjectUtils.isNull(warehouse) && !StringUtils.isEmpty(odoc.getWarehouseCd()))
                    warehouse = StoresPersistableBusinessObject.getObjectByPrimaryKey(
                            Warehouse.class, odoc.getWarehouseCd());

                formFields.setField(configService
                        .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_DEPARTMENT),
                        odoc.getDeliveryDepartmentNm());

                if (!odoc.getAccounts().isEmpty())
                    formFields
                            .setField(
                                    configService
                                            .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_REFERENCE_NUMBER),
                                    odoc.getAccounts().get(0).getDepartmentReferenceText());

                Profile profile = odoc.getCustomerProfile();

                if (profile != null) {
                    formFields
                            .setField(
                                    configService
                                            .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_REQUESTED_BY),
                                    profile.getPrincipalName());

                    formFields.setField(configService
                            .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_PHONE),
                            profile.getProfilePhoneNumber());
                    formFields
                            .setField(
                                    configService
                                            .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_REQUEST_BUILDING),
                                    profile.getDeliveryBuildingCode());
                    formFields
                            .setField(
                                    configService
                                            .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_REQUEST_ROOM),
                                    profile.getDeliveryBuildingRoomNumber());
                    formFields.setField(configService
                            .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_PHONE),
                            profile.getProfilePhoneNumber());

                }

                formFields
                        .setField(
                                configService
                                        .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_DEPARTMENT_NAME),
                                odoc.getDeliveryDepartmentNm());
                formFields
                        .setField(
                                configService
                                        .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_DELIVER_BUILDING),
                                odoc.getDeliveryBuildingCd());
                formFields
                        .setField(
                                configService
                                        .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_DELIVER_BUILDING),
                                (deliveryBuilding != null
                                        && deliveryBuilding.getBuildingName() != null ? deliveryBuilding
                                        .getBuildingName()
                                        : odoc.getDeliveryBuildingCd()));

                if (odoc.getShippingAddress() != null) {
                    formFields.setField(configService
                            .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_DELIVER_TO),
                            odoc.getShippingAddress().getAddressName());
                    String cityStateZip = (odoc.getShippingAddress().getAddressCityName() != null ? odoc
                            .getShippingAddress().getAddressCityName()
                            : "")
                            + ", "
                            + (odoc.getShippingAddress().getAddressStateCode() != null ? odoc
                                    .getShippingAddress().getAddressStateCode() : "")
                            + " "
                            + odoc.getShippingAddress().getAddressPostalCode() != null ? odoc
                            .getShippingAddress().getAddressPostalCode() : "";

                    formFields
                            .setField(
                                    configService
                                            .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_CITY_STATE_ZIP),
                                    cityStateZip);
                }
            }

            formFields
                    .setField(
                            configService
                                    .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_DATE_ORDERED),
                            dateTimeService.toDateTimeString((odoc == null || odoc
                                    .getCreationDate() == null) ? MMUtil.getCurrentTimestamp()
                                    : odoc.getCreationDate()));

            if (warehouse != null) {
                formFields.setField(configService
                        .getPropertyValueAsString(MMKeyConstants.PackingList.HEADER_FIELD_REQUESTED_BY),
                        warehouse.getWarehouseNme());
            }

            int i = 0;
            int lineNum = pageNum * maxLinesPerPage;
            KualiDecimal totalOrder = new KualiDecimal(0);

            KualiDecimal totalAmountSaved = new KualiDecimal(0);
            for (ReturnDetail line : returnLines) {

                String index = "[" + String.valueOf(i++) + "]";
                formFields.setField(configService
                        .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_LINE)
                        + index, String.valueOf(++lineNum));

                // TODO: need to find the correct values

                formFields.setField(configService
                        .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_ORDER_QUANTITY)
                        + index, String.valueOf(line.getReturnQuantity()));
                formFields.setField(configService
                        .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_SHIP_QUANTITY)
                        + index, String.valueOf(line.getReturnQuantity()));
                formFields
                        .setField(
                                configService
                                        .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_BACKORDER_QUANTITY)
                                        + index, String.valueOf(""));
                // TODO need to find the necessity of this field
                formFields.setField(configService
                        .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_BACKORDER_DATE)
                        + index, null);

                if (ObjectUtils.isNotNull(line.getOrderDetail())
                        && ObjectUtils.isNotNull(line.getOrderDetail().getStockUnitOfIssue()))
                    formFields.setField(configService
                            .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_UI)
                            + index, line.getOrderDetail().getStockUnitOfIssue()
                            .getUnitOfIssueCode());
                else if (ObjectUtils.isNotNull(line.getReturnUnitOfIssue()))
                    formFields.setField(configService
                            .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_UI)
                            + index, line.getReturnUnitOfIssue().getUnitOfIssueCode());

                if (ObjectUtils.isNotNull(line.getCatalogItem())
                        && ObjectUtils.isNotNull(line.getCatalogItem().getStock())) {
                    formFields.setField(configService
                            .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_ITEM_NUMBER)
                            + index, line.getCatalogItem().getStock().getStockDistributorNbr());
                    formFields.setField(configService
                            .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_DESCRIPTION)
                            + index, line.getCatalogItem().getStock().getStockDesc());
                }

                MMDecimal price = line.getReturnItemPrice();
                MMDecimal qty = new MMDecimal(line.getReturnQuantity());

                MMDecimal totLinePrice = (price != null) ? price.multiply(qty) : MMDecimal.ZERO;
                totalOrder = totalOrder.add(new KualiDecimal(totLinePrice.doubleValue()));
                formFields.setField(configService
                        .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_TOTAL_PRICE)
                        + index, totLinePrice.toString());
            }

            formFields.setField(configService
                    .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_TOTAL_ORDER),
                    totalOrder.toString());
            formFields.setField(configService
                    .getPropertyValueAsString(MMKeyConstants.PackingList.LINE_FIELD_TOTAL_SAVED),
                    totalAmountSaved.toString());
            formFields.setField(configService
                    .getPropertyValueAsString(MMKeyConstants.PackingList.FOOTER_FIELD_MESSAGES), messages);
        }
        catch (Exception e) {
            LOG.error(e);
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
        this.kualiConfigurationService = kualiConfigurationService;
    }

    public ConfigurationService getKualiConfigurationService() {
        return kualiConfigurationService;
    }


}
