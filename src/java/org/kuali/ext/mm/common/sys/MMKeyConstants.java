/*
 * Copyright 2007 The Kuali Foundation.
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
package org.kuali.ext.mm.common.sys;



/**
 * This class is used to define global constants.
 */
public class MMKeyConstants {

    public static final String KR_URL = "kr.url";
    public static final String KMM_URL_KEY = "kmm.url";
    public static final String LOG4J_SETTINGS_FILE_KEY = "log4j.settings.file";
    public static final String LOGS_DIRECTORY_KEY = "logs.directory";
    public static final String EXTERNAL_TEMP_DIRECTORY_KEY = "external.temp.directory";
    public static final String EXTERNAL_CAROUSEL_DIRECTORY_KEY = "external.carousel.directory";
    public static final String EXTERNAL_REPORTS_DIRECTORY_KEY = "external.reports.directory";
    public static final String EXTERNAL_EINVOICE_DIRECTORY_KEY = "external.einvoice.directory";
    public static final String LOG4J_RELOAD_MINUTES_KEY = "log4j.reload.minutes";
    public static final String B2B_COMPANY_NAME = "b2b.company.name";

    public static final String MESSAGE_INVALID_DATA = "message.countsheet.dataInvalid";
    public static final String MESSAGE_WAREHOUSE_NOT_FOUND = "message.countsheet.warehouseNotFound";
    public static final String MESSAGE_ZONE_NOT_FOUND = "message.countsheet.zoneNotFound";
    public static final String MESSAGE_WAREHOUSE_ZONE_NOT_FOUND = "message.countsheet.warehouseZoneNotFound";
    public static final String MESSAGE_INVALID_WORKSHEET_COUNTER = "message.countsheet.invalidCounter";
    public static final String MESSAGE_EMPTY_WORKSHEET_COUNT_FREQUENCY = "message.countsheet.emptyCountFrequency";
    public static final String MESSAGE_NUMBER_OF_WORKSHEET_COUNTERS_GREATERTHAN_CONF_VALUE = "message.countsheet.counterValGreaterThanConfVal";
    public static final String MESSAGE_INVALID_WORKSHEET_COPIES = "message.countsheet.invalidCopies";
    public static final String MESSAGE_NUMBER_OF_WORKSHEET_COPIES_GREATERTHAN_CONF_VALUE = "message.countsheet.copiesValGreaterThanConfVal";
    public static final String MESSAGE_COUNTERS_NOT_FOUND = "message.countsheet.countersNotFound";
    public static final String MESSAGE_COUNTER_INVALID_NUMBER = "message.countsheet.invalidCounterNumber";
    public static final String MESSAGE_COPIES_INVALID_NUMBER = "message.countsheet.invalidCopiesNumber";
    public static final String MESSAGE_STOCK_ITEMS_NOT_FOUND_FOR_WAREHOUSE = "message.countsheet.stockItemsNotFound";
    public static final String MESSAGE_STOCK_ITEMS_NOT_FOUND_FOR_ZONE_WAREHOUSE = "message.countsheet.stockItemsNotFoundForZoneAndWarehouse";
    public static final String MESSAGE_STOCK_ITEMS_NOT_FOUND_FOR_ZONE = "message.countsheet.stockItemsNotFoundForZone";
    public static final String MESSAGE_DOCUMENT_DESCRIPTION_NOT_FOUND = "message.countsheet.documentDescriptionNotFound";
    public static final String MESSAGE_STOCK_ITEMCOUNT_NOT_ENTERED = "message.stockItem.itemCountNotEntered";
    public static final String MESSAGE_COUNT_SHEET_NOT_FOUND = "message.countsheet.countSheetsNotFound";
    public static final String MESSAGE_WORKSHEET_COUNTER_PRINCIPAL_NULL = "message.worksheetCounter.principalIdnotFound";
    public static final String MESSAGE_WORKSHEET_COUNTER_INVALID = "message.worksheetCounter.principalIdInvalid";
    public static final String MESSAGE_WORKSHEET_COUNTER_NULL = "message.worksheetCounter.principalIdEmpty";
    public static final String MESSAGE_WORKSHEET_COUNTER_PRINCIPAL_ALREADY_EXISTS = "message.worksheetCounter.principalIdAlreadyExists";
    public static final String MESSAGE_WORKSHEET_COUNTER_DATE_NULL = "message.worksheetCounter.countDatenotFound";

    public static final String ERROR_MM_OBJECT_LOCKED = "error.document.mm.object.locked";

    public static final String ERROR_WAREHOUSE_NOT_UNIFORM = "error.document.pick.list.warehouse.not.uniform";
    public static final String ERROR_PICKLIST_NOT_INIT = "error.document.pick.list.not.init";
    public static final String ERROR_PICKLIST_EMPTY = "error.document.pick.list.empty";
    public static final String ERROR_LINES_NOT_COMPLETE = "error.document.pick.verify.lines.incomplete";
    public static final String ERROR_LINE_QTY_SUM = "error.document.pick.verify.sum.quantity";
    public static final String ERROR_NO_PICK_TICKET_LINES = "error.document.pick.verify.no.lines";
    public static final String ERROR_LINE_PICK_QTY = "error.document.pick.verify.bad.pick.qty";
    public static final String ERROR_NO_RENTAL_MATCH = "error.document.pick.verify.no.rental.match";
    public static final String ERROR_RENTAL_ISSUED = "error.document.pick.verify.rental.issued";
    public static final String ERROR_PICK_QTY_RENTALs_MISMATCH = "error.document.pick.verify.quantity.rental.mismatch";
    public static final String ERROR_PICK_STATUS_NOT_PRTD = "error.document.pick.verify.pick.status.not.prtd";
    public static final String ERROR_PICK_TICKET_LOCKED = "error.document.pick.verify.pick.ticket.locked";
    public static final String ERROR_NO_PICK_TICKET = "error.document.pick.verify.no.pick.ticket";
    public static final String ERROR_STOCK_BALANCE_LOCKED = "error.document.pick.verify.stock.balance.locked";
    public static final String ERROR_RENTAL_NON_UNIQUE = "error.document.pick.verify.rental.non.unique";
    public static final String ERROR_PICK_TICKET_INVALID = "error.document.pick.verify.pick.ticket.invalid";
    public static final String MM_MODULE_URL = "mm.module.url";
    public static final String FIN_SERVICES_UNAVAILABLE = "financial.system.services.unavailable";
    public static final String ERROR_ORDER_CORRECTION_DOC = "error.ordercorrection.invalidorder";
    public static final String ERROR_ORDER_CORRECTION_STATUS = "error.ordercorrection.invalidstatus";
    public static final String ERROR_ORDER_CORRECTION_REQS = "error.ordercorrection.invalidreqs";
    public static final String INFO_ORDER_CORRECTION_SAVED = "info.ordercorrection.saved";
    public static final String IMAGES_SAVED_SUCCESSFULLY = "catalog.images.saved.successfully";
    public static final String CATALOG_IMAGE_FILE_NOT_FOUND = "catalog.image.file.not.found";
    public static final String IMAGES_DELETED_SUCCESSFULLY = "catalog.images.deleted.successfully";
	public static final String ERROR_PICKLIST_NOT_VERIFIED = "error.document.pick.list.not.verified";
    public static final String ERROR_PICKLIST_ADDRESS_NOT_ON_ROUTE_CODE = "error.pickList.address.not.on.route.code";
    public static final String ERROR_PICKLIST_DELIVERY_LABEL_ALREADY_PRINTED = "error.pickList.dL.already.printed";
    public static final String ERROR_PICKLIST_EMPTY_FOR_DELIVERY_LABEL = "error.document.pick.list.empty.for.delivery.label";
    public static final String ERROR_PICKLIST_BACKORDERED="error.document.pick.list.back.ordered";
    public static final String ERROR_ROUTE_CODE_INCORRECT_DELIVERY_LABEL = "error.route.code.incorrect.for.delivery.log";
    public static final String ERROR_ROUTE_CODE_REQUIRED_DELIVERY_LABEL = "error.route.code.required.for.delivery.log";  
    public static final String ERROR_DELIVERY_REASON_NOT_SPECIFIED = "error.delivery.reasn.not.specified";
    public static final String ERROR_DEPARTMENT_RECVD_NAME_NOT_SPECIFIED = "error.depqartment.recvd.not.specified";
    public static final String ERROR_DELIVERY_DATE_NOT_SPECIFIED = "error.delivery.date.not.specified"; 
    public static final String ERROR_DELIVERY_REASON_INVALID = "error.delivery.reason.invalid";
    public static final String ERROR_DELIVERY_REASON_NOT_CHANGED ="error.delivery.reason.not.changed";
    public static final String ERROR_DELIVERY_LABEL_LINES_NOT_VALID ="error.document.deliveryLabelLines.not.valid";
    public static final String INFO_EMPTY_BINS_UNASSIGNED = "info.empty.bins.unassigned";

    public static final class Shopping {
        public static final String SHOPPING_URL = "shopping.url";
        public static final String SHOPPING_EMAIL_FROM_ADDRESS = "mail.shopping.from.address";
        public static final String SHOPPING_EMAIL_CONFIRMATION_SUBJECT = "mail.shopping.confirmation.subject";
        public static final String SHOPPING_EMAIL_CONFIRMATION_HEADER = "mail.shopping.confirmation.header";
        public static final String SHOPPING_EMAIL_CONFIRMATION_FOOTER = "mail.shopping.confirmation.footer";
    }

    public static final class CapitalAsset {

        public static final String ERROR_ASSET_TYPECODE = "error.document.order.asset.type.code";
        public static final String ERROR_ASSET_NUMBER = "error.document.order.asset.number";
        public static final String ERROR_ASSET_TAG_NUMBER = "error.document.order.asset.tag.number";
        public static final String ERROR_CAMPUS_CODE_BLANK = "error.document.order.asset.campus.code.blank";
        public static final String ERROR_BUILDING_CODE_BLANK = "error.document.order.asset.building.code.blank";
        public static final String ERROR_ROOM_NUMBER_BLANK = "error.document.order.asset.room.number.blank";
        public static final String ERROR_CAMPUS_CODE_INVALID = "error.document.order.asset.campus.code.invalid";
        public static final String ERROR_BUILDING_CODE_INVALID = "error.document.order.asset.building.code.invalid";
        public static final String ERROR_ROOM_NUMBER_INVALID = "error.document.order.asset.room.number.invalid";
        public static final String ERROR_ASSET_DATA_REQUIRED = "error.document.order.asset.data.required";
        public static final String ERROR_ASSET_TYPECODE_REQUIRED = "error.document.order.asset.type.code.required";
        public static final String ERROR_ASSET_QUANTITY_REQUIRED = "error.document.order.asset.quantity.required";
        public static final String ERROR_ASSET_VENDOR_NAME_REQUIRED = "error.document.order.asset.vendor.name.required";
        public static final String ERROR_ASSET_MANUFACTURER_NAME_REQUIRED = "error.document.order.asset.manufacturer.name.required";
        public static final String ERROR_ASSET_DESCRIPTION_REQUIRED = "error.document.order.asset.description.required";
        public static final String ERROR_ASSET_QUANTITY_NON_ZERO = "error.document.order.asset.quantity.greater.than.zero";
        public static final String ERROR_ASSET_QUANTITY_TAG_LINE_MISMATCH = "error.document.order.asset.quantity.tag.line.mismatch";
        public static final String ERROR_ASSET_DATA_NOT_REQUIRED = "error.document.order.asset.data.not.required";

    }

    public static class PackingList {
        public static final String HEADER_FIELD_TIMESTAMP = "field.pdf.packlist.header.timestamp";
        public static final String HEADER_FIELD_PICKING_LIST = "field.pdf.packlist.header.pickingList";
        public static final String HEADER_FIELD_ORDER_NUMBER = "field.pdf.packlist.header.orderNumber";
        public static final String HEADER_FIELD_ZONE = "field.pdf.packlist.header.zone";
        public static final String HEADER_FIELD_TUB = "field.pdf.packlist.header.tub";
        public static final String HEADER_FIELD_REFERENCE_NUMBER = "field.pdf.packlist.header.referenceNumber";
        public static final String HEADER_FIELD_DATE_ORDERED = "field.pdf.packlist.header.dateOrdered";
        public static final String HEADER_FIELD_DEPARTMENT = "field.pdf.packlist.header.department";
        public static final String HEADER_FIELD_REQUESTED_BY = "field.pdf.packlist.header.requestedBy";
        public static final String HEADER_FIELD_REQUEST_BUILDING = "field.pdf.packlist.header.requestBuilding";
        public static final String HEADER_FIELD_REQUEST_ROOM = "field.pdf.packlist.header.requestRoom";
        public static final String HEADER_FIELD_PHONE = "field.pdf.packlist.header.phone";
        public static final String HEADER_FIELD_DELIVER_TO = "field.pdf.packlist.header.deliverTo";
        public static final String HEADER_FIELD_DEPARTMENT_NAME = "field.pdf.packlist.header.departmentName";
        public static final String HEADER_FIELD_DELIVER_BUILDING = "field.pdf.packlist.header.deliverBuilding";
        public static final String HEADER_FIELD_CITY_STATE_ZIP = "field.pdf.packlist.header.cityStateZip";
        public static final String HEADER_FIELD_PROCESSED_BY = "field.pdf.packlist.header.processedBy";
        public static final String LINE_FIELD_LINE = "field.pdf.packlist.line.line";
        public static final String LINE_FIELD_ORDER_QUANTITY = "field.pdf.packlist.line.orderQuantity";
        public static final String LINE_FIELD_SHIP_QUANTITY = "field.pdf.packlist.line.shipQuantity";
        public static final String LINE_FIELD_BACKORDER_QUANTITY = "field.pdf.packlist.line.backOrderQuantity";
        public static final String LINE_FIELD_BACKORDER_DATE = "field.pdf.packlist.line.backOrderDate";
        public static final String LINE_FIELD_UI = "field.pdf.packlist.line.ui";
        public static final String LINE_FIELD_ITEM_NUMBER = "field.pdf.packlist.line.itemNumber";
        public static final String LINE_FIELD_DESCRIPTION = "field.pdf.packlist.line.description";
        public static final String LINE_FIELD_TOTAL_PRICE = "field.pdf.packlist.line.totalPrice";
        public static final String LINE_FIELD_TOTAL_ORDER = "field.pdf.packlist.line.totalOrder";
        public static final String LINE_FIELD_TOTAL_SAVED = "field.pdf.packlist.line.totalSaved";
        public static final String FOOTER_FIELD_MESSAGES = "field.pdf.packlist.footer.messages";
        public static final String FILE_PREFIX = "document.pickverify.packlist.file.prefix";
        public static final String MESSAGE_PACKING_LIST_ORDER_INCOMPLETE = "message.pdf.packlist.order.incomplete";
        public static final String LINE_SERIAL_LABEL = "label.pdf.packlist.serial.number";
    }


    public static class PickTicket {
        public static final String HEADER_TITLE = "label.document.pickTicket.header.title";
        public static final String COLUMN_LOCATION = "label.document.pickTicket.column.location";
        public static final String COLUMN_QTY = "label.document.pickTicket.column.quantity";
        public static final String COLUMN_UI = "label.document.pickTicket.column.unitOfIssue";
        public static final String COLUMN_DESCRIPTION = "label.document.pickTicket.column.description";
        public static final String COLUMN_TUB = "label.document.pickTicket.column.tub";
        public static final String COLUMN_ORDER = "label.document.pickTicket.column.order";
        public static final String COLUMN_ITEM = "label.document.pickTicket.column.item";
        public static final String COLUMN_PICKED = "label.document.pickTicket.column.picked";

        public static final String HEADER_LABEL_ORDER = "label.document.pickTicket.header.order";
        public static final String HEADER_LABEL_ROUTE = "label.document.pickTicket.header.route";
        public static final String HEADER_LABEL_BUILDING = "label.document.pickTicket.header.building";
        public static final String HEADER_LABEL_DEPARTMENT = "label.document.pickTicket.header.department";
        public static final String HEADER_LABEL_CATALOG_NUMBER = "label.document.pickTicket.header.catalogNumber";
        public static final String HEADER_LABEL_DESCRIPTION = "label.document.pickTicket.header.description";
        public static final String HEADER_LABEL_SHIP_TO_ATTN = "label.document.pickTicket.header.shipToAttention";
        public static final String HEADER_LABEL_ORDERED_BY = "label.document.pickTicket.header.orderedBy";
        public static final String HEADER_LABEL_PRINT_DATE = "label.document.pickTicket.header.printDate";
        public static final String HEADER_LABEL_PICKING_NUMBER = "label.document.pickTicket.header.pickingNumber";
        public static final String HEADER_LABEL_OLDEST_DATE = "label.document.pickTicket.header.oldestDate";
        public static final String HEADER_LABEL_WAREHOUSE = "label.document.pickTicket.header.warehouse";
        public static final String HEADER_PERSONAL_USE = "label.document.pickTicket.header.personal.use";
        public static final String HEADER_WILLCALL = "label.document.pickTicket.header.willcall";

    }

    public static class PickList {
        public static final String PICK_VERIFY_DOC_DESC = "field.document.pick.verify.default.doc.desc";
        public static final String PICK_LIST_GENERATE_DOC_DESC = "field.document.pick.list.default.doc.desc";
    }

    public static class PickVerifyDocument {
        public static final String SUM_QUANTITY_QUESTION = "question.document.pick.verify.sum";
        public static final String QTY_ON_HAND_QUESTION = "question.document.pick.verify.bo.qty.on.hand";
        public static final String ERROR_ADDITIONAL_LINE_PICK_QTY_EMPTY = "error.document.pick.verify.additional.line.qty.empty";
        public static final String ERROR_ADDITIONAL_LINE_PICK_QTY_INVALID = "error.document.pick.verify.additional.line.qty.invalid";
        public static final String ERROR_ADDITIONAL_LINE_STOCK_INVALID = "error.document.pick.verify.additional.line.stock.invalid";
        public static final String ERROR_ADDITIONAL_LINE_BIN_REQUIRED = "error.document.pick.verify.additional.line.bin.required";
        public static final String ERROR_NOT_ENOUGH_STOCK = "error.document.pick.verify.not.enough.stock";
    }

    public static class Accounts {
        public static final String ERROR_ACCOUNTING_LINES_TOTAL = "error.document.order.accounting.lines.total";
        public static final String ERROR_DETAIL_ACCOUNTING_LINES_TOTAL = "error.document.order.detail.accounting.lines.total";
        public static final String INVALID_CHART_CODE = "error.accounts.invalid.chart.code";
        public static final String INVALID_ACCT_NUMBER = "error.accounts.invalid.accountNumber";
        public static final String INVALID_SUB_ACCT_NUMBER = "error.accounts.invalid.sub.accountNumber";
        public static final String INVALID_OBJECT_CODE = "error.accounts.invalid.object.code";
        public static final String INVALID_SUB_OBJ_CODE = "error.accounts.invalid.sub.object.code";
        public static final String INVALID_OBJECT_TYPE_CODE = "error.accounts.invalid.object.typ.code";
        public static final String INVALID_OBJECT_LEVEL_CODE = "error.accounts.invalid.object.level.code";
        public static final String INVALID_OBJECT_SUB_TYP_CODE = "error.accounts.invalid.object.sub.typ.code";
        public static final String NOTALLOWED_OBJECT_CODE = "error.accounts.notallowed.object.code";
        public static final String ZERO_AMOUNT = "error.accounts.zero.amount";
        public static final String INVALID_PROJECT_CODE = "error.accounts.invalid.project.code";
        public static final String ERROR_ACCOUNTING_LINE_NOT_AUTHORIZED_FOR_ITEM = "error.accounts.not.authorized.for.catalog.item";
    }

    public static class OrderDocument {
        public static final String INVALID_ORDER_ITEM_QUANTITY = "error.orderdoc.invalidOrderItemQuantity";
        public static final String INVALID_BUY_QUANTITY = "error.orderdoc.invalidBuyQuantity";
        public static final String INVALID_ORDER_ITEM_STOCK_UNIT_OF_ISSUE_CODE = "error.orderdoc.invalidOrderItemStockUnitOfIssueCode";
        public static final String INVALID_ORDER_ITEM_ADDITIONAL_COST_AMOUNT = "error.orderdoc.invalidOrderItemAdditionalCostAmount";
        public static final String INVALID_ORDER_ITEM_TOTAL_COST_AMOUNT = "error.orderdoc.invalidOrderItemTotalCostAmount";
        public static final String INVALID_ORDER_ITEM_EXPECTED_DATE = "error.orderdoc.invalidOrderItemExpectedDate";
        public static final String INVALID_ORDER_ITEM_CATALOG_ITEM = "error.orderdoc.invalidOrderItemCatalogItem";
        public static final String INVALID_ORDER_ITEM_COST_AMOUNT = "error.orderdoc.invalidOrderItemCostAmount";
        public static final String INVALID_ORDER_ITEM_EXTENDED_AMOUNT = "error.orderdoc.invalidOrderItemExtendedAmount";
        public static final String INVALID_ORDER_ITEM_ADDITIONAL_COST_TYPE = "error.orderdoc.invalidOrderItemAdditionalCostType";
        public static final String INVALID_ORDER_ITEM_STOCK_REMOVE_UNTIL_DATE = "error.orderdoc.invalidOrderItemStockRemoveUntilDate";
        public static final String INVALID_ORDER_ITEM_NOT_IN_AGREEMENT = "error.orderdoc.orderItemNotInAgreement";
        public static final String INVALID_NEW_ORDER_LINE_PARAMATER = "error.orderdoc.invalidNewOrderLineParameter";
        public static final String INVALID_ITEM_NEEDED_TO_BE_REMOVED = "error.orderdoc.invalidItemToBeRemoved";
        public static final String CATALOG_ITEM_ALREADY_EXISTS = "error.orderdoc.orderItemCatalogItemAlreadyExists";
        public static final String NO_DEFAULT_PROFILE = "error.orderdoc.no.default.profile";
        public static final String RECURRING_ORDER_QUESTION = "question.document.order.recurring.order";
        public static final String ERROR_EXPECTED_DATE_INVALID ="error.document.order.expectedDate.invalid";
    }

    public static class ReceiptCorrectionDocuemnt {
        public static final String INVALID_RENTAL_SERIAL_NUMBER = "error.receipCorrectionDoc.invalidSerialNumber";
        public static final String NO_LINE_SELECTED = "error.receipCorrectionDoc.checkinLineNotSelected";
        public static final String INVALID_ACCEPTED_ITEM_QUANTITY = "error.receipCorrectionDoc.invalidAcceptedItemQty";
    }
    
    public static class RentalTrackingDocument {
        public static final String EMPTY_RENTAL_SERIAL_NUMBER = "error.document.rentalTracking.emptyRentalSerialNumber";
        public static final String INVALID_RENTAL_SERIAL_NUMBER = "error.document.rentalTracking.invalidRentalSerialNumber";
    }

    public static class CheckinDoc {
        public static final String INVALID_CRITERIA = "error.checkindoc.invalidCriteria";
        public static final String EMPTY_RENTAL_OBJECT_CODE = "error.checkindoc.emptyRentalObjectCode";
        public static final String EMPTY_ACCEPTED_ITEM_QUANTITY_ENTERED = "error.checkindoc.emptyAcceptedItemQuantity";
        public static final String EMPTY_REJECTED_ITEM_QUANTITY_ENTERED = "error.checkindoc.emptyRejectedItemQuantity";
        public static final String INVALID_ACCEPTED_ITEM_QUANTITY_ENTERED = "error.checkindoc.invalidAcceptedItemQuantity";
        public static final String ACCEPTED_ITEM_QUANTITY_EXCEEDS_ORDER_ITEM_QUANTITY = "error.checkindoc.AcceptedItemQuantity.exceedsOrderItemQuantity";
        public static final String INVALID_REJECTED_ITEM_QUANTITY_ENTERED = "error.checkindoc.invalidRejectedItemQuantity";
        public static final String NOTMATCHING_ACCEPTED_ITEM_QUANTITY_ENTERED = "error.checkindoc.notMatchingAcceptedItemQuantity";
        public static final String INVALID_BIN_SELECTED = "error.checkindoc.binInvalid";
        public static final String BIN_QUANTITY_EXCEEDED = "error.checkindoc.binQuantityExceeded";
        public static final String INVALID_DOCUMENT = "error.checkindoc.invalidDocument";
        public static final String RENTAL_COUNT_INVALID = "error.checkindoc.rental.count.invalid";
        public static final String RENTAL_SERIAL_NUMBER_ALREADY_EXISTS_IN_DOCUMENT = "error.checkindoc.rentalSerialNumberAlreadyExistsInDocument";
        public static final String RENTAL_EXISTS = "error.checkindoc.rental.exists";
        public static final String INVALID_NUMBER_OF_RENTALS = "error.checkindoc.invalidNumberOfRentals";
        public static final String EMPTY_REJECTED_ITEM_REASON_CODE = "error.checkindoc.rejectedItemReasonCode";
        public static final String EMPTY_REJECTED_ITEM_UNIT_OF_ISSUE = "error.checkindoc.rejectedItemUnitOfIssue";
        public static final String INVALID_STOCK_PERISHABLE_DATE = "error.checkindoc.invalidStockPerishableDate";
        public static final String INVALID_DISTRIBUTOR_NUMBER = "error.checkindoc.invalidDistributorNumber";
        public static final String INVALID_VALUE_PASSED = "error.checkindoc.invalidValuePassed";
        public static final String INVALID_RENTAL_OBJECT_CODE = "error.checkindoc.invalidRentalObjectCode";
        public static final String BIN_NOT_SELECTED = "error.checkindoc.binNotSelected";
        public static final String SAME_BIN_SELECTED = "error.checkindoc.sameBinSelected";
        public static final String BIN_HAS_STOCK = "error.checkindoc.binHasStock";
        public static final String BIN_ALREADY_IN_USE = "error.checkindoc.binAlreadyInUse";
        public static final String ACCEPTED_ITEM_INVALID_BUY_RATE_QUANTITY = "error.checkindoc.AcceptedItemQuantity.invalidBuyRate";
    }

    public static class ReturnDocument {
        public static final String EMPTY_RETURN_ITEM_QUANTITY = "error.returnDoc.emptyReturnItemQuantity";
        public static final String RENTAL_SERIAL_NUMBER_NOT_VALID_FOR_RETURN = "error.document.return.rentalSerialNumber.not.valid.for.return";
        public static final String EMPTY_RETURN_DETAIL_STATUS_CODE = "error.returnDoc.emptyReturnDetailStatusCode";
        public static final String EMPTY_RETURN_UNIT_OF_ISSUE_CODE = "error.returnDoc.emptyReturnUnitOfIssueCode";
        public static final String NO_RETURN_LINE_SELECTED = "error.returnDoc.returnLineNotSelected";
        public static final String EMPTY_BIN_ID = "error.returnDoc.emptyBinId";
        public static final String INVALID_BIN = "error.returnDoc.invalidBin";
        public static final String FULL_BIN = "error.returnDoc.fullBin";
        public static final String EMPTY_RETURN_UNIT_ISSUE_OF_CODE = "error.returnDoc.emptyReturnUnitIssueOfCode";
        public static final String BALANCE_FOR_ORDER_NOT_FOUND = "error.returnDoc.balanceNotFound";
        public static final String QUANTITY_GREATER_THAN_BALANCE_QUANITTY = "error.returnDoc.quantityGreaterThanBalanceQuantity";
        public static final String EMPTY_RETURN_STATUS_CODE = "error.returnDoc.emptyReturnStatusCode";
        public static final String EMPTY_STOCK_DISTRIBUTOR_NUMBER = "error.returnDoc.emptyStockDistributorNumber";
        public static final String INVALID_STOCK_DISTRIBUTOR_NUMBER = "error.returnDoc.invalidStockDistributorNumber";
        public static final String EMPTY_RETURN_ITEM_DESCRIPTION = "error.returnDoc.emptyReturnItemDescription";
        public static final String EMPTY_RETURN_ITEM_UNIT_COST = "error.returnDoc.emptyReturnItemUnitCost";
    }

    public static class CatalogItem {
        public static final String CATALOG_ID = "catalogId";
        public static final String OPEN_PENDING_ORDERS_PRESENT = "error.open.pending.orders.present";
        public static final String OPEN_BACK_ORDERS_PRESENT = "error.open.pending.back.orders.present";
        public static final String QUANTITY_ON_HAND_NOT_ZERO = "error.quantity.on.hand.not.zero";
        public static final String ALLOCATED_QTY_NOT_ZERO = "error.allocated.quantity.not.zero";
        public static final String IMAGE_VALUE_CANNOTBE_NULL = "error.Image.Value.Cannot.Be.Null";
        public static final String MARKUP_VALUE_CANNOTBE_NULL = "error.Markup.Value.Cannot.Be.Null";
        public static final String SUBGROUP_VALUE_CANNOTBE_NULL = "error.Subgroup.Value.Cannot.Be.Null";
        public static final String CATALOG_PRICE_CANNOTBE_NULL = "error.CatalogPrc.Cannot.Be.Null";
        public static final String DISTRIBUTOR_NUMBER_PRESENT = "error.distributor.number.present";
        public static final String ACTIVE_IND_CANNOT_BE_SET = "error.active.ind.cannot.be.set";
        public static final String UI_REQUIRED = "error.ui.required";
        public static final String UI_INVALID = "error.ui.invalid";
        public static final String AGREEMENT_INVALID = "error.agreement.invalid";
        public static final String RESTRICTED_ROUTE_CD_INVALID = "error.restrictedRouteCode.invalid";
        public static final String CYCLE_COUNT_CD_INVALID = "error.cycleCountCode.invalid";
        public static final String STOCK_TYPE_CD_INVALID = "error.stockTypeCode.invalid";
        public static final String RENTAL_OBJECT_CD_INVALID = "error.rentalObjectCode.invalid";
        public static final String BUY_UI_INVALID = "error.buy.ui.invalid";
        public static final String MARKUP_CANNOT_BE_APPLIED = "error.markup.cannot.be.applied";
        public static final String APPLIED_MARKUP_HAS_EXPIRED = "error.applied.markup.has.expired";

    }

    public static class StockBalance {
        public static final String STOCK_BALANCE_ID = "stockBalanceId";
        public static final String PERISHABLE_DATE_NOT_PROVIDED = "error.stockBalance.perishableDateNotSelected";
        public static final String BIN_ALREADY_ASSIGNED = "error.bin.already.assigned";
        public static final String QUANTITY_BEING_ADJUSTED_NOT_SPEFICIFIED = "error.quanitity.being.adjusted.not.specified";
        public static final String TRANSACTION_CODE_NOT_SPEFICIFIED = "transaction.code.not.specified";
        public static final String BIN_ARLREADY_BEING_UTILIZED = "error.bin.already.being.specified";
        public static final String TRANSACTION_CODE_INVALID = "error.specified.trasaction.code.invalid";
        public static final String TRANSACTION_CODE_NOT_I_OR_D = "error.specified.transaction.code.not.i.or.d";

    }
    
    public static class Stock {
        public static final String MANUFACTURER_NBR_NOT_PROVIDED = "error.stock.maufactureNumberNotProvided";
        public static final String EHSUNITOFCONVERSION_LESS_THAN_ZERO = "error.ehsUnitOfConversion.less.thank.zero";
        public static final String FOR_EHS_CODE_IS_NOT_NULL = "error.FOR_EHS_CODE_IS_NOT_NULL";
        public static final String FOR_EHS_CODE_NULL = "error.FOR_EHS_CODE_NULL";
        public static final String QUANTITY_BEING_ADJUSTED_GREATER_THAN_QOH = "error.quantity.being.adjusted.greater.than.qoh";
        public static final String RESIDUAL_TAG_MUST_BE_SPECIFIED = "error.residual.tag.not.specified";
        public static final String UNIT_OF_ISSUE_CODE_UNCHANGED = "error.unit.of.issue.code.unchanged";
        public static final String SAME_BIN_ID = "error.same.bin.id";
        public static final String QUANTITY_BEING_ADJUSTED_NOT_INTEGER = "error.quantity.being.adjusted.not.integer";
        public static final String QUANTITY_NOT_SPECIFIED = "error.quantity.not.specified";
        public static final String QUANTITY_BEING_ADJUSTED_FROM_OLD_TO_NEW_IS_REQUIRED = "error.quantity.being.adjusted.is.required";
        public static final String QUANTITY_BEING_ADJUSTED_FROM_OLD_TO_NEW_NOT_INTEGER = "error.quantity.being.transfer.not.integer";
        public static final String QUANTITY_BEING_TRANSFERED_GREATER_THAN_QOH = "error.quantity.being.tranfered.greater.than.qoh";
        public static final String ONE_ONE_BIN_TRANSFER_ALLOWED = "error.only.one.bin.transferred.allowed";
    }

    public static class HazardousMateriel {
        public static final String HAZARDOUS_UN_CODE_INVALID = "error.hazardous.un.code";
        public static final String EHS_HAZARDOUS_CODE_INVALID = "error.ehs.hazardous.code";
        public static final String EHS_CONTAINER_CODE_INVALID = "error.ehs.container.code";
        public static final String EHS_HAZARDOUS_STATE_CODE_INVALID = "error.ehs.hazardous.state.code";
        public static final String EHS_UNIT_OF_ISSUE_CODE_INVALID = "error.unit.of.issue.code";
        public static final String DOT_HAZARDOUS_CODE_INVALID = "error.dot.hazardous.code";
    }

    public static class CatalogSubgroup {
        public static final String CATALOG_GROUP_SUBGROUP_NOT_SET = "error.catalogGroup.catalogSubgroup.Notset";
        public static final String CATALOG_SUBGROUP_CD_ALREADY_PRESENT = "error.catalogSubgroup.code.already.present";
        public static final String CATALOG_SUBGROUP_REQUIRED = "error.catalog.subgroup.requried";
    }

    public static class StockPackNote {
        public static final String STOCK_PACK_NOTE_NOT_SET = " error.StockPackNote.Value.Cannot.Be.Null";
    }

	public static class OrderAutoLimit {
        public static final String CHART_NOT_VALID = "error.orderautolimit.chart.not.valid";
        public static final String ORG_NOT_VALID = "error.orderautolimit.org.not.valid";
        public static final String ACCT_NOT_VALID = "error.orderautolimit.acct.not.valid";
        public static final String ERROR_DUPLICATE_LIMIT = "error.orderautolimit.duplicate";
    }
    public static class Markup {
        public static final String CHART_NOT_VALID = "error.markup.chart.not.valid";
        public static final String ORG_NOT_VALID = "error.markup.org.not.valid";
        public static final String ACCT_NOT_VALID = "error.markup.acct.not.valid";
        public static final String ERROR_MARKUP_SUBGROUP_NOT_VALID = "error.markup.subgroup.not.valid";
        public static final String ERROR_MARKUP_AMOUNT_BLANK = "error.markup.amount.blank";
        public static final String ERROR_MARKUP_AMOUNT_CONFLICT = "error.markup.amount.conflict";
        public static final String ERROR_MARKUP_FROM_QTY_INVALID = "error.markup.from.qty.invalid";
        public static final String ERROR_MARKUP_BEGIN_DATE_INVALID = "error.markup.begin.dt.invalid";
        public static final String ERROR_MARKUP_FIELD_REQUIRED = "error.markup.field.required";
    }

    public static class Warehouse {
        public static final String CHART_NOT_VALID = "error.warehouse.chart.not.valid";
        public static final String ACCT_NOT_VALID = "error.warehouse.acct.not.valid";
        public static final String SUB_ACCT_NOT_VALID = "error.warehouse.sub.acct.not.valid";
        public static final String OBJ_CODE_NOT_VALID = "error.warehouse.obj.not.valid";
        public static final String SUB_OBJ_CODE_NOT_VALID = "error.warehouse.subobj.not.valid";
        public static final String OFFSET_OBJ_CODE_NOT_VALID = "error.warehouse.offsetobj.not.valid";
        public static final String OFFSET_SUB_OBJ_CODE_NOT_VALID = "error.warehouse.offsetsubobj.not.valid";
        public static final String RESALE_ACCT_UNDEFINED = "error.warehouse.resale.account.undefined";
        public static final String COST_OF_GOODS_ACCT_UNDEFINED = "error.warehouse.cost.account.undefined";
        public static final String SHRINKAGE_OBJ_UNDEFINED = "error.warehouse.shrinkage.obj.undefined";
        public static final String OBSO_OBJ_UNDEFINED = "error.warehouse.obsolence.obj.undefined";
        public static final String ORG_CODE_NOT_VALID = "error.warehouse.org.not.valid";
    }

    public static class CatalogPending {
        public static final String FILE_REQUIRED_ERROR = "error.file.required";
        public static final String FILE_NOT_SUPPORTED_ERROR = "error.file.not.supported";
        public static final String CATALOG_CD_REQUIRED = "error.catalog.cd.required";
        public static final String BEGIN_DT_REQUIRED = "error.begin.dt.required";
        public static final String BEGIN_AFTER_END = "error.begin.after.end";
        public static final String FILE_UNSUPPORTED_FORMAT_COLUMN5 = "error.file.unsupported.format.column5";
    }

    public static class CatalogRestriction {
        public static final String CHART_NOT_VALID = "error.catalog.restriction.chart.not.valid";
        public static final String ORG_NOT_VALID = "error.catalog.restriction.org.not.valid";
        public static final String ACCT_NOT_VALID = "error.catalog.restriction.account.not.valid";
        
    }
    
    public static class Agreement {
        public static final String CONTRACT_NOT_VALID = "agreement.contract.not.valid";
    }

    public static class RouteMap {
        public static final String STOP_SEQUENCE_NOT_MAINTAINED = "stop.sequence.not.valid";
    }

    public static class WorksheetCountDocument {
        public static final String WORKSHEET_NOT_APPROVED = "message.countsheet.documentNotApproved";
        public static final String WORKSHEET_ITEMS_CANNOT_REPRINT = "message.countsheet.cannotReprint";
        public static final String WORKSHEET_WITH_REPRINTED_ITEMS = "message.countsheet.documentNotApprovedReprintedItems";
    }

    public static class RecurringOrder {
        public static final String ERROR_RECURRING_ORDER_END_DT_INVALID = "error.recurring.order.end.date.invalid";
        public static final String ERROR_RECURRING_ORDER_END_DT_BLANK = "error.recurring.order.end.date.blank";
        public static final String ERROR_RECURRING_ORDER_END_DT_CONFLICT = "error.recurring.order.end.date.conflict";
        public static final String ERROR_RECURRING_ORDER_START_DT_REQUIRED = "error.recurring.order.start.date.required";
        public static final String ERROR_RECURRING_ORDER_TIMES_PER_YEAR_REQUIRED = "error.recurring.order.times.per.year.required";
    }

    public static class MMCapitalAssetInformation {
        public static final String ERROR_INVALID_CAPITAL_ASSET_QUANTITY = "error.document.capitalAssetEdit.invalidAssetQuantity";
    }
                
    public static class DeliveryLabelDocument {
        public static final String NBR_OF_CARTONS_ERROR = "error.nbr.of.cartons";
    }
    
    public static class DiscrepancyDocument {
        public static final String ERROR_MISSING_COMMENT = "error.missing.comment";
        public static final String ERROR_INCORRECT_DATE_RANGE = "error.incorrect.date.range";
        public static final String VENDOR_NOT_SELECTED = "error.vendorName.not.selected";
        public static final String REPORT_NOT_SELECTED = "error.reportName.not.selected";
    }

    public static class Bin {
        public static final String ERROR_BIN_LOCATION_EXISTS = "error.bin.location.exists";
    }

    public static class Zone {
        public static final String ERROR_ZONE_EXISTS = "error.zone.exists";
    }

    public static class BackOrder {
        public static final String CANCEL_QUESTION = "question.backorder.cancel";
        public static final String QUANTITY_MODIFY_QUESTION = "question.backorder.quantity.modify";
        public static final String ERROR_CANNOT_CANCEL_OR_MODIFY = "error.backorder.cannot.cancel.or.modify";
        public static final String ERROR_NEW_QUANTITY_GREATER_THAN_OLD = "error.backorder.new.quantity.greater.than.old";
        public static final String ERROR_NO_STOCK_TO_FILL = "error.backorder.no.stock.to.fill";
        public static final String REDUCE_AND_FILL_QUESTION = "question.backorder.quantity.reduce.and.fill";
        public static final String FILL_WITHOUT_STOCK_QUESTION = "question.backorder.fill.without.stock.quantity";        

    }

	public static class AgreementMassMaintenance {
        public static final String ERROR_FILE_TYPE_NOT_SUPPORTED = "error.agreement.mass.maint.file.type";
        public static final String ERROR_UPLOADING_FILE = "error.agreement.mass.maint.upload.fail";
        public static final String ERROR_NO_FILE_TO_UPLOAD = "error.agreement.mass.maint.no.file";
        public static final String ERROR_NO_LINES_TO_UPLOAD = "error.agreement.mass.maint.no.lines.to.upload";
        public static final String ERROR_FILE_BLANK_OR_WRONG_FORMAT = "error.agreement.mass.maint.file.bad.format";
        public static final String ERROR_AGREEMENT_NBR_MISMATCH = "error.agreement.mass.maint.agreementNumber.mismatch";
        public static final String ERROR_INVALID_COLUMN_COUNT = "error.agreement.mass.maint.invalid.column.count";
        public static final String ERROR_AGREEMENT_NBR_INVALID = "error.agreement.mass.maint.agreementNumber.invalid";
        public static final String ERROR_STOCK_COST_INVALID = "error.agreement.mass.maint.stockCost.invalid";
        public static final String ERROR_INVALID_COLUMNS = "error.agreement.mass.maint.invalid.columns";
        public static final String ERROR_STOCK_ITEM_NOT_EXIST = "error.agreement.mass.maint.stock.item.not.exist";
        
    }
    
    public static class Rental {
        public static final String ERROR_RENTAL_DATES_OUT_OF_SEQUENCE = "error.rental.dates.out.of.sequence";
        public static final String ERROR_RENTAL_CANNOT_UNISSUE = "error.rental.cannot.unissue";
        public static final String ERROR_RENTAL_CANNOT_ERASE_LAST_CHARGE_DATE = "error.rental.erase.last.charge.date";
        public static final String ERROR_RENTAL_CANNOT_ERASE_RETURN_DATE = "error.rental.cannot.erase.return.date";
        public static final String HISTORICAL_MODIFICATION_QUESTION = "question.rental.historical.modification";
        public static final String ERROR_RENTAL_ACCOUNTS_INVALID_TOTAL = "error.rental.accounts.invalid.total";
        public static final String ERROR_HISTORIC_RENTAL_RECORD_EXISTS = "error.rental.historic.record.exists";
        public static final String ERROR_RENTAL_STOCK_INVALID = "error.rental.stock.invalid";
        public static final String RENTAL_EXISTS = "error.rental.exists";
    }
    
    public static class PunchOutVendor {

        public static final String ERROR_VENDOR_EXISTS = "error.punchoutvendor.exists";
        public static final String ERROR_PUNCHOUT_URL_BLANK = "error.punchoutvendor.punchout.url.blank";
        
    }
    
    public static class TrueBuyoutDocument {
        public static final String ERROR_STOCK_ALREADY_EXISTS = "error.truebuyout.document.stock.already.exists";
        public static final String ERROR_CATALOG_NOT_TRUE_BUYOUT = "error.truebuyout.document.catalog.not.truebuyout";
        public static final String ERROR_AGREEMENT_NUMBER_INVALID = "error.truebuyout.document.agreement.number.invalid";
        public static final String ERROR_STOCK_TYPE_INVALID = "error.truebuyout.document.stock.type.invalid";
        public static final String ERROR_MARKUP_CODE_INVALID = "error.truebuyout.document.markup.code.invalid";
        public static final String ERROR_RENTAL_OBJECT_CODE_INVALID = "error.truebuyout.document.rental.object.code.invalid";
        public static final String ERROR_WRONG_STOCK_TYPE_FOR_RENTAL_OBJECT = "error.truebuyout.document.wrong.stock.type.for.rental.object";
    }
    
    public static class ShoppingFrontPage {
        public static final String URL_PRECEDENCE_QUESTION = "question.shoppingfrontpage.url.precedence";
    }
}
