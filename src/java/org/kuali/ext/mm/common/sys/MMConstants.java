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

import java.math.BigDecimal;

import org.kuali.rice.core.util.JSTLConstants;
import org.kuali.rice.kns.authorization.AuthorizationConstants.EditMode;
import org.kuali.rice.kns.service.ParameterConstants.COMPONENT;
import org.kuali.rice.kns.service.ParameterConstants.NAMESPACE;


/**
 * This class is used to define global constants.
 */
public class MMConstants extends JSTLConstants {
    private static final long serialVersionUID = 2882277719647128949L;

    public static final String MM_NAMESPACE = "KFS-MM";

    public static final String EXTERNALIZABLE_IMAGES_URL_KEY = "externalizable.images.url";

    public static final String GLOBAL_ERRORS = "GLOBAL_ERRORS";
    public static final String GLOBAL_MESSAGES = "GlobalMessages";

    public static final int MAX_RECORDS_TO_UPDATE = 100;
    public static final int INDEX_TO_STOP_LOOPING = MAX_RECORDS_TO_UPDATE - 1;

    public static final String SHOPPING_COMMAND = "displayShoppingView";
    public static final String DISPLAY_DOC_SEARCH_VIEW = "displayDocSearchView";
    public static final String DISPLAY_RESULT = "DISPLAY_RESULT";
    public static final String METHOD_TO_CALL = "methodToCall";
    public static final String BLANKET_APPROVE = "blanketApprove";
    public static final String APPROVE = "Approve";
    public static final String WORKSHEET_DOC_NUMBER = "WorksheetDocNumber";
    public static final String WORKSHEET_DOC_STATUS = "WorksheetDocStatus";
    public static final String DOCUMENT_ID = "docId";
    public static final String MAPPING_BASIC = "basic";
    public static final String MAPPING_PORTAL = "portal";
    public static final String MAPPING_BACK = "back";
    public static final String MAPPING_REPRINT = "reprint";
    public static final String MAPPING_SHOW = "show";
    public static final String MAPPING_CANCEL = "cancel";
    public static final String EMPTY_STRING = "";
    public static final String PRINT = "print";
    public static final String REPRINT = "reprint";
    public static final String COMMAND = "command";
    public static final String CREATE_CHECKIN_DOCS = "createCheckinDocs";
    public static final String CHECKIN_LINE = "checkInOrderLine";
    public static final String COMMAND_INITIATE = "initiate";
    public static final String SHOW_MAINT_LINKS = "showMaintenanceLinks";
    public static final String COPY_FILE_NAME_PREFIX = "Worksheet_Copy_";
    public static final String LOOKUP_METHOD_NAME = "performLookup";
    public static final String CHECKIN_ACTION = "checkinOrder.do";
    public static final String CHECKIN_DOC_TYPE = "SCHK";
    public static final String CHECKIN_RETURNDOC_TYPE = "SRET";
    // public static final String CHECKIN_RETURNDOC_TYPE = "StoresReturnDocument";
    public static final String CHECKIN_VENDOR_RETURNDOC_TYPE = "SVRT";
    // public static final String CHECKIN_VENDOR_RETURNDOC_TYPE = "StoresVendorReturnDocument";

    public static final String RENTAL_ACTION_UPDATE = "override";
    public static final String ORDER_ACTION = "order.do";
    public static final String ACTION_PARM_PICK_STATUS = "pickStatusCode.pickStatusCode";
    public static final String ACTION_PARM_PICKLIST_DOC_NBR = "pickListDocumentNumber";
    public static final String ACTION_PARM_PICK_TICKET_NUMBER = "pickTicketNumber";
    public static final String PICK_LIST_ACTION = "pickList.do";
    public static final String PICK_TICKET_ACTION = "pickTicket.do";
    public static final String PICK_TICKET_ACTION_PRINT = "Print";
    public static final String PICK_TICKET_ACTION_PRINT_ALL = "Print All New";
    public static final String PRINT_TICKET_METHOD = "printTicket";
    public static final String PRINT_ALL_METHOD = "printAllUnprinted";
    public static final String UPDATE_QUERY_PLACE_HOLDER = "#";
    
    public static final String MULTIPLE_VALUE = "multipleValues";
    public static final String MULTIPLE_VALUE_LABEL = "Lookup initial values";
    public static final String MULTIPLE_VALUE_NAME = "Multiple Value Name";
    public static final String REFRESH_CALLER = "refreshCaller";
    public static final String KUALI_LOOKABLE = "kualiLookupable";
    public static final String DATA_EDIT_LOCK_ERROR = "message.error.data.edit.lock";


    public static final String DISPATCH_REQUEST_PARAMETER = "methodToCall";
    public static final String MAINTENANCE_NEWWITHEXISTING_ACTION = "newWithExisting";
    public static final String BUSINESS_OBJECT_CLASS_ATTRIBUTE = "businessObjectClassName";
    public static final String OVERRIDE_KEYS = "overrideKeys";
    public static final String MAINTENANCE_ACTION = "maintenance.do";

    public static final int PERCENT_MIN_SCALE = 10;
    public static final int AMOUNT_MIN_SCALE = 4;

    public static final String VENDOR_NBR_SEPARATOR = "-";

    public static final BigDecimal PERCENT_ERROR_ALLOWANCE = new BigDecimal(0);
    public static final BigDecimal AMOUNT_ERROR = new BigDecimal(.01);

    public static final String WAREHOUSE_RESALE_ACCT = "WR";
    public static final String WAREHOUSE_COST_GOODS_ACCT = "CG";
    public static final String INCOME_ACCT = "IC";
    public static final String WAREHOUSE_TAX_ACCT = "TX";
    public static final String WAREHOUSE_OBSOLENCE_OBJECT = "OBSO";
    public static final String WAREHOUSE_SHRINKAGE_OBJECT = "SHRO";

    public static final String DOCUMENT = "document";
    
    public static final String GL_MODIFIED_INQUIRY_ACTION = "glModifiedInquiry.do";

    public static final String FIN_ACCT_LINE_TYP_FROM = "F";
    public static final String FIN_ACCT_LINE_TYP_TO = "T";

    public static final String CATALOG_ACTION_NEW_ITEM = "Add New Item";

    public static class Services {
        public static final String FINANCIAL_VENDOR_SERVICE = "FinancialVendorService";
    }

    // These should eventually be assessed and moved into the System Parameter table
    // **Put ALL constants that will be system parameters here**
    public static class Parameters {
        public static final String DOCUMENT = "Document";
        public static final String LOOKUP = "Lookup";
        public static final String BATCH = "Batch";
        public static final String MAX_PRINT_ALL_RESULTS = "MAX_PRINT_ALL_RESULTS";
        public static final String STOCK_TYPES_WITH_SERIAL_NUM = "STOCK_TYPES_WITH_SERIAL_NUM";
        public static final String DEFAULT_WAREHOUSE_CD = "DEFAULT_WAREHOUSE_CD";
        public static final String DEFAULT_SORT_CD = "DEFAULT_SORT_CD";
        public static final String PACKING_LIST_PDF = "PACKING_LIST_PDF";
        public static final String PACKING_LIST_PDF_MAX_LINES = "PACKING_LIST_PDF_MAX_LINES";
        public static final String STOCK_PRICE_CODE = "STOCK_PRICE_CODE";
        public static final String MAX_NUMBER_OF_WORKSHEET_COUNTERS = "MAX_NUMBER_OF_WORKSHEET_COUNTERS";
        public static final String MAX_NUMBER_OF_WORKSHEET_COPIES = "MAX_NUMBER_OF_WORKSHEET_COPIES";
        public static final String ASSET_OBJECT_CODES = "ASSET_OBJECT_CODES";
        public static final String MAX_PRICE_TOLERANCE_ALLOWED = "MAX_PRICE_TOLERANCE_ALLOWED";
        public static final String CAROUSEL_WAREHOUSE_ZONES = "CAROUSEL_WAREHOUSE_ZONES";
        // Temporary until cash net is available
        public static final boolean FORCE_WILLCALL_ON_PERSONAL_USE = true;
        public static final String MM_ORDER_APPROVALS_BEGIN_AT_AMOUNT = "MM_ORDER_APPROVALS_BEGIN_AT_AMOUNT";
        public static final String FAMIS_FEED_ACCOUNTS = "FAMIS_FEED_ACCOUNTS";

        @NAMESPACE(namespace = MM_NAMESPACE)
        @COMPONENT(component = "Document")
        public static final class OrderDocument {
        }

        @NAMESPACE(namespace = MM_NAMESPACE)
        @COMPONENT(component = "Document")
        public static final class Document {
        }
    }
     public static class Agreement {

        public static final String AGREEMENT_NUMBER = "agreementNbr";
        
    }
    public static class Delivery{
        public static final String DELIVERY_REASON_CD = "DEST";
        public static final String DOC_ID = "docId";
    }
    
    public static class PackStatusCode {
        public static final String PACK_STATUS_INIT = "INIT";
        public static final String PACK_LIST_DOC_NBR = "packListDocNbr";
        public static final String DOC_ID = "docId";
        public static final String DELIVERY_BLDG_CD = "deliveryBuildingCode";
        public static final String DELIVERY_CAMPUS_CD = "deliveryCampusCd";
    }
    
    public static class PickStatusCode {
        public static final String PICK_STATUS_CODE = "pickStatusCode";
        public static final String PICK_STATUS_INIT = "INIT";
        public static final String PICK_STATUS_ASND = "ASND";
        public static final String PICK_STATUS_PRTD = "PRTD";
        public static final String PICK_STATUS_PCKD = "PCKD";
        public static final String PICK_STATUS_BACK = "BACK";
        public static final String PICK_STATUS_PBCK = "PBCK";
        public static final String PICK_STATUS_CLSD = "CLSD";
        public static final String PICK_STATUS_CNCL = "CNCL";
        public static final String PICK_STATUS_COMP = "COMP";
    }

    public static class PickListDocument {
        public static final String PICK_LIST_DOCUMENT = "pickListDocument";
        public static final String PICK_LIST_LINE = "pickListLine";
        public static final String PICK_LIST_LINES = "pickListLines";
        public static final String PICK_LIST_LINE_ERROR = "document.pickListLines";
        public static final String SALES_INSTANCE = "salesInstance";
        public static final String BIN = "bin";
        public static final String ORDER_DETAIL = "orderDetail";
        public static final String WAREHOUSE_CD = "document.warehouseCd";
        public static final String WAREHOUSE = "warehouse";

        public static final String OPTION_ORDERS = "Orders";
        public static final String OPTION_ZONES = "Zones";
        public static final String OPTION_SINGLE_LIST = "Single List";
        public static final String SEPARATE_WILL_CALLS = "Will Calls";
    }
    
    public static class DeliveryLogDocument{
        public static final String DELIVERY_MAIN_SECTION ="DeliveryMainSection";
        public static final String DELIVERY_MAIN_SECTION_COLLECTION ="document.deliveryLabelLines";
        public static final String DELIVERY_LINE_SECTION = "document.newMaintainableObject.deliveryLine";
    }
    
    public static class DiscrepancyDocument{
        public static final String OPTION_SUBSTITUTION = "Substitutions";
        public static final String OPTION_OVER_SHIPMENT = "Over Shipments";
        public static final String OPTION_PRICE_DISCREPANCY = "Price Discrepancies";        
        public static final String OPTION_NO_ORDERMATCH = "Invoice - No Order Match";
        public static final String OPTION_TRAN_DISCREPANCY = "PCard/Invoice - Transaction Discrepancies";       
        public static final String SECTION_RECO_DISCREPANCY = "document.discrepancyLines";
        public static final String VENDOR_NOT_SELECTED = "document.vendorName";
        public static final String REPORT_NOT_SELECTED = "document.reportName";
        public static final String FROMDATE_NOT_SELECTED = "document.discrepancyFromDate";
        public static final String TODATE_NOT_SELECTED = "document.discrepancyToDate";
        public static final String FROM_ACTION = "/removeDiscrepancy";
    }
    
    public static class PickVerifyDocument {
        public static final String PICK_VERIFY_ACTION = "pickVerify.do";
        public static final String PICK_VERIFY_ACTION_VERIFY = "Verify";
        public static final String PICK_VERIFY_DOC_TYPE = "SPKV";
        public static final String PICK_LIST_LINES = "document.pickTicket.pickListLines";
        public static final String BACK_ORDER_QTY = "backOrderQty";
        public static final String STOCK_QTY = "stockQty";
        public static final String SUM_QUANTITY_QUESTION = "sumQuantityQuestion";
        public static final String QTY_ON_HAND_QUESTION = "qtyOnHandQuestion";
        public static final String PICK_TICKET_NUMBER = "document.pickTicketNumber";
        public static final String PICK_TICKET = "pickTicket";
    }

    public static class PickTicket {
        public static final String PICK_TICKET = "pickTicket";
        public static final String PICK_TICKET_NUMBER = "pickTicketNumber";
        public static final String NAME_ZONE = "Zone - ";
        public static final String NAME_ORDER = "Order - ";
        public static final String NAME_WILL_CALL = "Will Call - ";
        public static final String NAME_BIN_RANGE = "Bin Range - ";
        public static final String NAME_ITEM = "Item - ";
        public static final String NAME_PERSONAL_USE = " - Personal";
    }
    
    public static class BackOrder {
        public static final String STOCK_ID = "stockId";
        public static final String FILLED = "filled";
        public static final String FROM_PICK_LIST_LINE = "fromPickListLine";
        public static final String CANCELED = "canceled";
        public static final String TO_PICK_LIST_LINES = "toPickListLines";
        public static final String BACK_ORDER_QUANTITY = "backOrderStockQty";
        public static final String OPTION_FILL = "FILL";
        public static final String OPTION_CANCEL = "CANCEL ALL";
        public static final String OPTION_REDUCE = "REDUCE TO";
    }
    
    public static class Rental {
        public static final String RETURN_DATE = "returnDate";
        public static final String STOCK_ID = "stockId";
        public static final String STOCK = "stock";
        public static final String RENTAL_SERIAL_NUMBER = "rentalSerialNumber";
        public static final String RENTAL_STATUS_AVAILABLE = "A";
        public static final String RENTAL_STATUS_ISSUED = "I";
        public static final String RENTAL_STATUS_RETURNED = "R";
        public static final String OPTION_LABEL_AVAILABLE = "Available (A)";
        public static final String OPTION__LABEL_ISSUED = "Issued (I)";
        public static final String OPTION__LABEL_RETURNED = "Returned (R)";
        public static final Object RENTAL_ID = "rentalId";
        public static final String RENTAL_STATUS_CODE = "rentalStatusCode";
        public static final String ISSUE_DATE = "issueDate";
        public static final String LAST_CHARGE_DATE = "lastChargeDate";
        public static final String RENTAL_TYPE_CODE = "rentalTypeCode";
        public static final String ACCOUNTING_LINES = "accountingLines";
    }
    
    public static final class StagingRental {
        public static final String SERIAL_NUMBER = "serialNumber";
    }
    
    public static class RentalTrackingDocument {
        public static final String ADD_SERIAL_NUMBER_ERROR_PATH = "addSerialNumbers";
        public static final String SELECTED_ITEMS = "selectedItems";
    }

    public static final class ReceiptCorrection {
        public final static String DOCUMENT_TYPE = "SRCN";
        public static final String RECEIPT_CORRECTION_ACTION = "receiptCorrection.do";
        public static final String RECEIPT_CORRECTION_LABEL = "CORRECT RECEIPT";
        public static final String RECEIPT_CORRECTION_DOCUMENT = "RECEIPT_CORRECTION_DOCUMENT";
        public static final String RECEIPT_CORRECTION_DOCUEMNT = "document.checkinDetails";
        public static final String CHECKIN_RENTALS = "correctedCheckinDetail.checkinRentals";
        public static final String LINE_CORRECTED = "lineCorrected";
        public static final String CHECKIN_DETAILS = "checkinDetails";
    }
    
    public static final class CheckInCorrection {
        public static final String ORDER_DOCUMENT_NUMBER = "orderDocumentNumber";
    }

    public static final class ReportGeneration {
        public final static String PARAMETER_NAME_SUBREPORT_DIR = "SUBREPORT_DIR";
        public final static String PARAMETER_NAME_SUBREPORT_TEMPLATE_NAME = "SUBREPORT_TEMPLATE_NAMES";
        public final static String DESIGN_FILE_EXTENSION = ".jrxml";
        public final static String JASPER_REPORT_EXTENSION = ".jasper";
        public final static String PDF_FILE_EXTENSION = ".pdf";
        public final static String PDF_MIME_TYPE = "application/pdf";
        public final static String TEXT_MIME_TYPE = "text/plain";
        public final static String ACCOUNT_EXPORT_FILE_NAME = "account_export.txt";
        public final static String MONTHLY_EXPORT_FILE_NAME = "monthly_export.txt";
        public final static String FUNDING_EXPORT_FILE_NAME = "funding_export.txt";
        public final static String EXTERNAL_REPORTS_DIR = "external.reports.directory";
        public static final String SUB_REPORT_TEMPLATE_CLASSPATH = "SUB_REPORT_TEMPLATE_CLASSPATH";
    }

    public static class SalesHistory {
        public static final String CURRENT_YEAR_TO_DATE = "CURRENT_YEAR_TO_DATE";
        public static final String CUMMULATIVE_12_MONTHS = "CUMMULATIVE_12_MONTHS";
        public static final String LAST_FISCAL_YEAR = "LAST_FISCAL_YEAR";
        public static final String SECOND_FISCAL_YEAR = "SECOND_FISCAL_YEAR";
        public static final String THIRD_FISCAL_YEAR = "THIRD_FISCAL_YEAR";
        public static final String ORDER_TYPE_CODE = "";

    }

    public static class PurchaseHistory {
        public static final String ORDER_TYPE_CODE = "orderDetail.orderDocument.orderType.orderTypeCode";
        public static final String ORDER_DETAIL_ID = "orderDetailId";
    }
    
    public static class StockType {
        public static final String STOCK = "01";
        public static final String RENTAL = "02";
    }

    public static class Stock {
        public static final String STOCK_ID = "stockId";
        public static final String STOCK_DISTRIBUTOR_NBR = "stockDistributorNbr";
        public static final String CYCLE_CNT_CD = "cycleCntCd";
        public static final String RESTRICTED_ROUTE_CD = "restrictedRouteCd";
        public static final String BUY_UNIT_OF_ISSUE_CD = "buyUnitOfIssueCd";
        public static final String SALES_UNIT_OF_ISSUE_RT = "salesUnitOfIssueRt";
        public static final String SALES_UNIT_OF_ISSUE_CD = "salesUnitOfIssueCd";
        public static final String BUY_UNIT_ISSUE_RT = "buyUnitOfIssueRt";
        public static final String AGREEMENT_NBR = "agreementNbr";
        public static final String STOCK_TYPE_CD = "stockTypeCode";
        public static final String OBSOLETE_IND = "obsoleteInd";
        public static final String PERISHABLE_IND = "perishableInd";
        public static final String RECYCLED_IND = "recycledInd";
        public static final String REORDER_POINT_QTY = "reorderPointQty";
        public static final String SAFETY_STOCK_DAYS = "safetyStockDays";
        public static final String SAFETY_STOCK_QTY = "safetyStockQty";
        public static final String DISTRIBUTOR_NBR = "distributorNbr";
        public static final String SURCHARGE_IND = "surchargeInd";
        public static final String MINIMUM_ORDER_QTY = "minimumOrderQty";
        public static final String PACKAGING_UNIT_DESC = "packagingUnitDesc";
        public static final String TAXABLE_IND = "taxableInd";
        public static final String RENTAL_OBJECT_CODE = "rentalObjectCode";
        public static final String SOLE_SOURCE_IND = "soleSourceInd";
        public static final String LAST_CHANGE_ACTV_IND_DT = "lastChangeActvIndDt";
        public static final String MAXIMUM_ORDER_QTY = "maximumOrderQty";
        public static final String STOCK_PACK_NOTES_SECTION = "StockPackNotes";
        public static final String HAZARDOUS_MATERIEL_SECTION = "HazardousMateriel";
        public static final String STOCK_BALANCE_SECTION = "stockBalance";
        public static final String STOCK_ATTRIBUTE_SECTION = "stockAttribute";
        public static final String SALES_UNIT_OF_ISSUE_RT_MUST_BE_SPECIFIED = "salesUnitOfIssueRt.must.be.specified";
        public static final String SALES_UNIT_OF_ISSUE_RT_IS_ONE = "sales.unit.of.issue.rate.is.one";
        public static final String STOCK_MAIN_SECTION = "StockMainSection";
        public static final String UPCCD = "upcCd";
        public static final String ADJUST_ZERO_BIN_BALANCE_PERMISSION = "Adjust Zero Bin Balance Stock";
        public static final String STOCK_BALANCES = "stockBalances";
        public static final String RENTAL_OBJECT = "rentalObject";
        public static final String STOCK_PRICES = "stockPrices";
        public static final String REMOVE_UNTIL_DATE = "removeUntilDate";
    }
    
    public static class StockCost {
        public static final String STOCK_ID = "stockId";
        public static final String COST_CODE = "costCd";
    }

    public static class ReturnStatusCode {
        public static final String RETURN_STATUS_CODE = "returnStatusCode";
        public static final String RETURN_STATUS_LABEL = "returnStatusCodeName";
        public static final String RENTAL_RETURN = "34";
    }

    public static class StockTransReason {
        public static final String SALE = "SALE";
        public static final String STOCK_TRANS_REASON_CODE = "stockTransReasonCd";
        public static final String STOCK_TRANS_REASON_LABEL = "stockTransReasonDesc";
        public static final String STOCK_CUSTOMER_RETURN = "CST_RTRN";
        public static final String STOCK_VENDOR_RETURN = "VND_RTRN";
        public static final String WITH_IN_TOLERANCE = "TOLERANC";
        public static final String TRANS_IN = "TRANS_IN";
        public static final String TRANS_OUT = "TRANSOUT";
        public static final String AVGCOST = "AVGCOST";
        public static final String DCORRECT = "DCORRECT";
        public static final String INCREMENT = "I";
        public static final String DECREMENT = "D";
        public static final String NO_EFFECT = "N";
    }

    public static class Bin {
        public static final String BIN_ID = "binId";
        public static final String ZONE_ID = "zoneId";
        public static final String QTY_ON_HAND = "qtyOnHand";
        public static final String STOCK_DISTRIBUTOR_NUMBER = "stockDistributorNbr";
        public static final String MAXIMUM_SHELF_QUANTITY = "maximumShelfQty";
        public static final String ZONE = "zone";
        public static final String BIN_NBR = "binNbr";
        public static final String SHELF_ID = "shelfId";
        public static final String SHELF_ID_NBR = "shelfIdNbr";
        public static final String STOCK_BALANCE = "stockBalance";
    }

    public static class CatalogItem {
        public static final String MANUF_NUMBER = "manufacturerNbr";
        public static final String DIST_NUMBER = "distributorNbr";
        public static final String CATALOGPRC = "catalogPrc";
        public static final String ACTIVE_IND = "active";
        public static final String CATALOG_ITEM_IMAGE_SECTION = "CatalogItemImages";
        public static final String CATALOG_ITEM_MARKUP_SECTION = "CatalogItemMarkups";
        public static final String CATALOG_ITEM_SUBGROUP_SECTION = "CatalogSubgroupItems";
        public static final String CATALOG_ITEM_MAIN_SECTION = "CatalogItemMainSection";
        public static final String STOCK_AGREEMENT_NUMBER = "stock.agreementNbr";
        public static final String CATALOG_DESC = "catalogDesc";
        public static final String SUBSTITUTE_DIST_NBR = "substituteDistributorNbr";
        public static final String CATALOG_ID = "catalogId";
        public static final String CATALOG_ITEM_ID = "catalogItemId";
        public static final String CATALOG = "catalog";
        public static final String STOCK = "stock";
        public static final String STOCK_ID = "stockId";
        
        public static final String GENERIC_DUMMY_DISTRIBUTOR_NBR ="000000";
        public static final String GENERIC_DUMMY_UNIT_OF_ISSUE = "NA";
        public static final String GENERIC_DUMMY_DESCRIPTION = "This is a generic description for a dummy catalog item, used by punchout and true buyout.  No one should ever see this.";
        public static final String CATALOG_ITEM_IMAGES = "catalogItemImages";
    }

    public static class CatalogType {
        public static final String WAREHOUSE = "1";
        public static final String HOSTED = "2";
        public static final String PUNCHOUT = "3";
        public static final String TRUE_BUYOUT = "4";
    }

    public static class Catalog {
        public static final String CATALOG_ID = "catalogId";
        public static final String CATALOG_CD = "catalogCd";
        public static final String CURRENT_IND = "currentInd";
        public static final String PRIORITY_NUMBER = "priorityNbr";
		public static final String CATALOG_IMAGE_MANAGE_PERMISSION = "Manage Catalog Images";
        public static final String CATALOG_TYPE_CD = "catalogTypeCd";
    }
    
    public static class CatalogRestriction {
        public static final String CATALOG_ID = "catalogId";
        public static final String RESTRICTION_CODE = "restrictionCode";
        public static final String OPTION_ALLOW = "A";
        public static final String OPTION_DISALLOW = "D";
        public static final String OPTION_ALLOW_LABEL = "Allow";
        public static final String OPTION_DISALLOW_LABEL = "Disallow";
        
    }

    public static class Warehouse {
        public static final String WAREHOUSE_CD = "warehouseCd";
    }

    public static class Zone {
        public static final String ZONE_CD = "zoneCd";
        public static final String WAREHOUSE_CD = "warehouseCd";
    }

    public static class CostCode {
        public static final String STOCK_COST_CODE = "costCd";
        public static final String STANDARD_PRICE = "01";
        public static final String WEIGHTED_AVERAGE = "02";
    }

    public static class StockCount {
        public static final String STOCK_COUNT_ID = "stockCountId";
        public static final String STOCK_PRICE_CODE = "01";
        public static final String WORKSHEET_DOC_NUMBER = "worksheetCountId";
        public static final String STOCK_ID = "stockId";
    }


    public static class StockBalance {
        public static final String STOCK_BALANCE_ID = "stockBalanceId";
        public static final String STOCK_BALANCE_DISTRIBUTOR_NUMBER = "stockBalance.stock.stockDistributorNbr";
        public static final String STOCK_BALANCE_QTY_ON_HAND = "stockBalance.qtyOnHand";
        public static final String STOCK_PERISHABLE_DT = "stockPerishableDt";
        public static final String QUANTITY_BEING_ADJUSTED = "quantityBeingAdjusted";
        public static final String STOCK_BALANCE_SECTION = "stockBalance";
        public static final String LAST_CHECKIN_DATE = "lastCheckinDt";
        public static final String STOCK_ID = "stockId";
        public static final String QTY_ON_HAND = "qtyOnHand";
        public static final String BIN = "bin";
        public static final String STOCK = "stock";
    }

    public static class WorksheetCounter {
        public static final String WORKSHEET_COUNTER_PRINCIPAL = "worksheetPrncplId";
        public static final String WORKSHEET_COUNTER_DATE = "lastUpdateDate";
    }

    public static class WorksheetStatus {
        public static final String WORKSHEET_INITIATED = "INIT";
        public static final String WORKSHEET_PRINTED = "PRTD";
        public static final String WORKSHEET_REPRINTED = "REPRNTED";
        public static final String WORKSHEET_ENTERED = "ENTR";
        public static final String WORKSHEET_CLOSED = "CLSD";
        public static final String WORKSHEET_CANCELED = "CNCL";
    }

    public static class ReorderItem {
        public static final String EDIT_REORDERED_ITEMS = "EDIT_REORDERED_ITEMS";
        public static final String EDIT_ALL_ITEMS_IN_AGREEMENT = "EDIT_ALL_ITEMS_IN_AGREEMENT";
        public static final String EDIT_ITEMS_ACTION = "EDIT_ITEMS_ACTION";
        public static final String EDIT_REORDERED_ITEMS_LABEL = "edit";
        public static final String EDIT_ALL_ITEMS_IN_AGREEMENT_LABEL = "all";
        public static final String REORDER_AGREEMENT_NUMBER = "REORDER_AGREEMENT_NUMBER";
        public static final String REORDER_CATALOG_GROUP_NAME = "REORDER_CATALOG_GROUP_NAME";
        public static final String REORDER_SUB_CATALOG_GROUP_NAME = "REORDER_SUB_CATALOG_GROUP_NAME";
        public static final String AGREEMENT_NUMBER = "agreementNbr";
        public static final String CATALOG_CODE = "catalogCode";
        public static final String CATALOG_SUB_GROUP_ID = "catalogSubgroupId";
        public static final String REORDER_DOCUMENT_ACTION = "reorderItems.do";
        public static final String CATALOG_GROUP_CATALOG_CODE = "catalogGroup.catalogGroupCd";
        public static final String CATALOG_SUBGROUP_CATALOG_SUBGROUPCODE = "catalogSubGroup.catalogSubgroupCd";
        public static final String CATALOG_SUBGROUPCODE = "catalogSubGroupCode";
        public static final String CATALOG_GROUPCODE = "catalogGroupCode";
        public static final String CATALOG_SUBGROUP_CATALOG_CODE = "catalogSubGroup.catalogGroupCd";
        public static final String WAREHOUSE_CD = "warehouseCd";
    }

    public static class WorksheetCountDocument {
        public static final String DOC_NUMBER = "documentNumber";
        public static final int DEFAULT_NUMBER_OF_COUNTERS = 3;
        public static final String WORKSHEET_COUNT_DOC_DISPLAY_ACTION = "initiateCycleCountEntry.do";
        public static final String WORKSHEET_ITEM_PICKED_INDICATOR = "*";
        public static final String WORKSHEET_COUNT_DOC_PRINT_ACTION = "initiateWorksheetDoc.do";
        public static final String VIEW = "Enter Count";
        public static final String PRINT_PDF = "Print";
        public static final String PRINT_STATEMENT_PDF = "printStatementPDF";
        public static final String STOCK_ID_SEPARATOR = "|";
        public static final String ZIP_FILE_EXTENSION = ".zip";
        public static final String PDF_FILE_EXTENSION = ".pdf";
        public static final String DOC_TYPE_NAME = "SWKC";
        public static final String REPORT_ZIP_FILE_NAME = "count_worksheet_document";
        public static final String NEW_WORKSHEET_COUNTER = "newWorksheetCounter";
        public static final String WORKSHEET_COUNTERS_COUNT_DATE = "lastUpdateDate";
        public static final String STOCK_COUNTS = "document.stockCounts";
        public static final String STOCK_TRANS_REASON_CODE = "stockTransReasonCd";
        public static final String STOCK_ITEM_QTY = "stockCountItemQty";
        public static final String WAREHOUSE_CODE = "warehouseCode";
        public static final String ZONE_CODE = "zoneCd";
        public static final String COUNTERS = "counters";
        public static final String COUNT_FREQUENCY = "countFreq";
        public static final String COPIES = "copies";
        public static final String EDIT_WORKSHEET_PERMISSION = "Edit Worksheet";
        public static final String STOCK_ITEMS_COUNTED = "COUNT";
        public static final String REPRINTED = "reprinted";
        public static final String WORKSHEET_STOCK_ITEM_REPRINTED = "REPRNTED";
        public static final String WORKSHEET_COUNTERS_PRINCIPAL_ID = "worksheetPrncplName";// "newWorksheetCounter.worksheetPrncplId";
    }

    public static class OrderDetailStatus {
        public static final String ORDER_DETAIL_STATUS_CODE = "orderStatusCd";
        public static final String ORDER_DETAIL_STATUS_LABEL = "orderStatusDesc";
    }

    public static class OrderStatus {
        public static final String ORDER_LINE_OPEN = "O";
        public static final String ORDER_LINE_CLOSED = "C";
        public static final String ORDER_LINE_CANCELED = "X";
        public static final String ORDER_LINE_PRINTED = "P";
        public static final String ORDER_LINE_RECEIVING = "R";
        public static final String ORDER_LINE_RECEIVED_COMPLETE = "S";
        public static final String ORDER_LINE_COMPLETE = "T";
        public static final String INITIATED = "I";
        public static final String REVIEW = "V";
        public static final String DISAPPROVE = "D";
    }

    public static class OrderType {
        public static final String WAREHS = "WAREHS";
        public static final String PUNCH = "PUNCH";
        public static final String STOCK = "STOCK";
        public static final String HOSTED = "HOSTED";
        public static final String TRUE_BUYOUT = "BUYOUT";
    }

    public static class UnitOfIssue {
        public static final String UNIT_OF_ISSUE_CODE = "unitOfIssueCode";
        public static final String UNIT_OF_ISSUE_LABEL = "unitOfIssueDesc";
    }

    public static class ReturnDocument {
        public static final String RETURN_DOCUMENT_RECEIVE_ACTION = "returnOrder.do";
        public static final String VENDOR_RETURN_DOCUMENT = "SVRT";
        // public static final String VENDOR_RETURN_DOCUMENT = "StoresVendorReturnDocument";
        public static final String VENDOR_RETURN_DOCUMENT_RECEIVE_ACTION = "vendorReturnOrder.do";
        public static final String NEW_ORDER_DOCUMENT = "NEW";
        public static final String RETURN_RENTALS = "rentals";
        public static final String RETURN_DETAILS = "document.returnDetails";
        public static final String NEW_RETURN_DETAIL = "newReturnDetail";
        public static final String NEW_RETURN_DETAILS = "document.newReturnDetails";
        public static final String RETURN_QUANTITY = "returnQuantity";
        public static final String NEW_RETURN_DETAIL_RETURN_QUANTITY = "newReturnDetail.returnQuantity";
        public static final String NEW_RETURN_RETURN_UNIT_ISSUE_OF_CODE = "newReturnDetail.returnUnitOfIssueOfCode";
        public static final String NEW_RETURN_RETURN_STATUS_CODE = "newReturnDetail.returnDetailStatusCode";
        public static final String NEW_RETURN_STOCK_DISTRIBUTOR_NUMBER = "newReturnDetail.stockDistributorNumber";
        public static final String NEW_RETURN_RETURN_ITEM_DESCRIPTION = "newReturnDetail.returnItemDetailDescription";
        public static final String NEW_RETURN_RETURN_ITEM_UNIT_COST = "newReturnDetail.returnItemPrice";
        public static final String CUSTOMER_PRINCIPAL_ID = "principalId";
        public static final String CUSTOMER_PRINCIPAL_NAME = "principalName";
        public static final String REPORT_ZIP_FILE_NAME = "packing_list";
        public static final String RTV_REPORT_FILE_NAME = "Rtv_Manifest_report";
        public static final String RETURN_DOC_TYPE = "returnDocType";
        public static final String RETURN_DETAIL_STATUS_CODE = "returnDetailStatusCode";
        public static final String RETURN_UNIT_OF_ISSUE_CODE = "returnUnitOfIssueOfCode";
        public static final String RETURN_DETAIL_LINE_SELECTED = "itemReturned";
        public static final String BIN_ID = "binId";
        public static final String BIN_ZONE_DESC = "binZoneDesc";
        public static final String CUSTOMER_RETURN_STOCK_TRANS_REASON = "CST_RTRN";
        public static final String VENDOR_RETURN_STOCK_TRANS_REASON = "VND_RTRN";
        public static final String RETURN_DOCUMENT_STATUS_CODE = "returnDocumentStatusCode";
    }
    
    public static class ReturnDetail {
        public static final String CATALOG_ITEM = "catalogItem";
        public static final String DISPOSITION_CODE = "dispostitionCode";
        public static final String DEPARTMENT_CREDIT_IND = "departmentCreditInd";
        public static final String VENDOR_CREDIT_IND = "vendorCreditInd";
        public static final String VENDOR_RESHIP_IND = "vendorReshipInd";
        public static final String VENDOR_DISPOSITION_IND = "vendorDispositionInd";
        public static final String DEPARTMENT_CREDIT_STRING_IND = "departmentCreditStringInd";
        public static final String RETURN_DOCUMENT = "returnDoc";
        public static final String ORDER_DETAIL_ID = "orderDetailId";
    }

    public static class ReturnActionCode {
        public static final String ISSUE_CREDIT_MEMO = "CR_MEMO";
        public static final String OVER_SHIPMENT_CHECKED_IN = "05";
        public static final String OVER_SHIPMENT_NOT_CHECKED_IN = "06";
        public static final String REORDER_ITEM = "REORDER";
        public static final String CREDIT_TRANSACTION = "DEPT_CR";
        public static final String INVENTORY_ADJUSTMENT = "Receipt Correction";
        public static final String RETURN_TO_VENDOR = "RESHIP";
        public static final String RETURN_ACTION_CODE = "actionCodeValue";
        public static final String RETURN_ACTION_CODE_NAME = "actionName";
        public static final String REJECTED = "REJECTED";
        public static final String WAREHS = "WAREHS";
        public static final String DEPT_NC = "DEPT_NC";
        public static final String DEPT_CR = "DEPT_CR";
        public static final String CUSTOMER = "CUSTOMER";
        public static final String DISPOSITION_NEEDED = "DISPOSITION_NEEDED";
        public static final String VENDOR_ISSUE_CREDIT = "VENDOR_ISSUE_CREDIT";
        public static final String VENDOR_RESHIP_NEEDED = "VENDOR_RESHIP_NEEDED";
        public static final String DEPARTMENT_CREDIT_NEEDED = "DEPARTMENT_CREDIT_NEEDED";
    }

    public static class ReturnActionCommand {
        public static final String INVENTORY_ADJUSTMENT_RETURN_ACTION_SERVICE = "INVENTORY_ADJUSTMENT_RETURN_ACTION_SERVICE";
        public static final String REORDER_RETURN_ACTION_SERVICE = "REORDER_RETURN_ACTION_SERVICE";
        public static final String RETURN_TO_VENDOR_RETURN_ACTION_SERVICE = "RETURN_TO_VENDOR_RETURN_ACTION_SERVICE";
        public static final String NO_ACTION_COMMAND = "NO_ACTION_COMMAND";
        public static final String VENDOR_ISSUE_CREDIT = "VENDOR_ISSUE_CREDIT";
        public static final String VENDOR_RESHIPMENT = "VENDOR_RESHIPMENT";
        public static final String DEPARTMENT_CREDIT_ACTION_SERVICE_CR = "DEPT_CR";
        public static final String DEPARTMENT_CREDIT_ACTION_SERVICE_NC = "DEPT_NC";
        public static final String VENDOR_ISSUE_CREDIT_ACTION_SERVICE = "VENDOR_ISSUE_CREDIT_ACTION_SERVICE";
        public static final String VENDOR_RESHIPMENT_ACTION_SERVICE = "VENDOR_RESHIPMENT_ACTION_SERVICE";
    }

    public static class DispositionCode {
        public static final String DISPOSITION_CODE = "dispositionCode";
        public static final String DISPOSITION_CODE_NAME = "dispositionName";
        public static final String RETURN_TO_VENDOR = "RTRN_VENDOR";
        public static final String RETURN_TO_SHELF = "RTRN_SHELF";
        public static final String TRASH = "TRASH";
        public static final String HAZARDOUS = "HAZARDOUS";
        public static final String BARGAIN_BARN = "BARGAIN_BARN";
    }

    public static class AdditionalCostType {
        public static final String ADDITIONAL_COST_TYPE_CODE = "additionalCostTypeCode";
        public static final String ADDITIONAL_COST_TYPE_NAME = "additionalCostTypeName";
        public static final String DEPOSIT = "D";
    }

    public static class ReOrderDocument {
        public static final String STORES_REORDER_DOCUMENT = "SROR";
		public static final String REQS_SOURCE_REORDER = "SROR";
        public static final String NEW_ORDER_LINE = "newOrderDetail.";
        public static final String MANUFACTURER_NUMBER = "manufacturerNumber";
        public static final String DISTRIBUTOR_NUMBER = "distributorNumber";
        public static final String ITEM_NUMBER = "itemNumber";
    }

    public static class CheckinDocument {
        public static final String MANUFACTURER_NUMBER = "manufacturerNbr";
        public static final String RETURN_DETAIL_STATUS_CODE = "returnDetailStatusCode";
        public static final String RETUNR_UNIT_ISSUE_OF_CODE = "returnUnitOfIssueOfCode";
        public static final String STOCK_PERISHABLE_DATE = "stockPerishableDate";
        public static final String DISTRIBUTOR_NUMBER = "stockDistributorNbr";
        public static final String DOCUMENT_CHECKIN_DETAILS = "document.checkinDetails";
        public static final String ORDER_DETAIL_ID = "orderDetailId";
        public static final String LINE_CHECKED_IN = "LineCheckedIn";
        public static final String ORDER_DOC_NUMBER = "orderDocNumber";
        public static final String RECEIVE_LINE_LABEL = "Receive Line";
        public static final String RECEIVE_ORDER_LABEL = "Receive Order";
        public static final String ACCEPTED_ITEM_QTY = "ACCEPTED_ITEM_QTY";
        public static final String REJECTED_ITEM_QTY = "REJECTED_ITEM_QTY";
        public static final String RETURN_ORDER_LABEL = "Return";
        public static final String RECEIVE_ACTION = "ReceiveAction";
        public static final String RECEIVE_LINE_ACTION = "ReceiveLine";
        public static final String RECEIVE_ORDER_ACTION = "ReceiveOrder";
        public static final int DEFAULT_NUMBER_OF_COUNTERS = 3;
        public static final String CHECKIN_DOCUMENT_RECEIVE_ACTION = "checkinReceive.do";
        public static final String WORKSHEET_COUNT_DOC_PRINT_ACTION = "initiateWorksheetDoc.do";
        public static final String VIEW = "Enter Count";
        public static final String DOCUMENT_NUMBER = "documentNumber";
        public static final String PRINT_PDF = "Print Worksheet";
        public static final String PRINT_STATEMENT_PDF = "printStatementPDF";
        public static final String STOCK_ID_SEPARATOR = "|";
        public static final String ZIP_FILE_EXTENSION = ".zip";
        public static final String PDF_FILE_EXTENSION = ".pdf";
        public static final String DOC_TYPE_NAME = "SWKC";
        public static final String REPORT_ZIP_FILE_NAME = "count_worksheet_document";
        public static final String VENDOR_RETURN_ORDER_LINE = "VR";
        public static final String CHECKIN_TRANSACTION_REASON_CODE = "TRANS_IN";
        public static final String CHECKIN_DOCUMENT = "CHECKIN_DOCUMENT";
        public static final String CUSTOMER_ORDER_RETURN = "CR";

        public static final String LOOKUP_DOC_NUM = "docNum";
        public static final String VENDOR_NAME_FOR_LOOKUP = "orderDocument.vendorNm";
        public static final String ORDER_TYPE_CODE_FOR_LOOKUP = "orderDocument.orderTypeCode";
        public static final String ORDER_STATUS_CODE_FOR_LOOKUP = "orderStatusCd";
        public static final String WAREHOUSE_CODE_FOR_LOOKUP = "orderDocument.warehouseCd";
        public static final String PO_ID_LOOKUP = "poId";
        public static final String REQS_ID_FOR_LOOKUP = "orderDocument.reqsId";
        public static final String VENDOR_SHIPMENT_NUMBER_FOR_LOOKUP = "orderDocument.checkinDocs.vendorShipmentNbr";
        public static final String VENDOR_REF_NUMBER_FOR_LOOKUP = "orderDocument.checkinDocs.vendorRefNbr";
        public static final String ORDER_DOCUMENT_NUMBER_FOR_LOOKUP = "orderDocumentNbr";
        public static final String ORDER_ID_FOR_LOOKUP = "orderDocument.orderId";
        public static final String DISTRIBUTOR_NUMBER_FOR_LOOKUP = "distributorNbr";
        public static final String FROM_PICK_LIST_LINE_STOCK_ID = "fromPickListLine.stock.stockId";
        public static final String FROM_PICK_LIST_LINE_LAST_UPDATE = "fromPickListLine.lastUpdateDate";
        public static final String FROM_PICK_LIST_LINE_STATUS_CODE = "fromPickListLine.pickStatusCodeCd";
        public static final String ORDER_DOCUMENT = "orderDocument";
        public static final String FINAL_IND = "finalInd";
    }
    
    public static class CheckinDetail {
        public static final String STOCK_ID = "stockId";
        public static final String STOCK = "stock";
        public static final String ORDER_DETAIL = "orderDetail";
        public static final String CORRECTED_CHECKIN_DETAIL_ID = "correctedCheckinDetailId";
        public static final String RENTALS = "rentals";
        public static final String CHECKIN_DOC = "checkinDoc";        
    }

    public static class CheckinDoc {
        public static final String ACCEPTED_ITEM_QUANTITY = "acceptedItemQty";
        public static final String REJECTED_ITEM_QUANTITY = "rejectedItemQty";
        public static final String RENTAL_CHECKIN_SERIAL_NUMBER = "checkinSerialNbr";
        public static final String BIN_NUMBER = "binZoneDesc";
        public static final String REJECTED_ITEMS = "rejectedItems";
        public static final String REJECTED_ITEM_REASON_CODE = "reasonCode";
        public static final String REJECTED_ITEM_UNIT_OF_ISSUE = "unitOfIssue";
        public static final String NEW_ORDER_DETAIL = "newOrderDetailVo.";
        public static final String NEW_PO_SHELFID = "shelfId";
        public static final String NEW_PO_PONUMBER = "poNumber";
        public static final String NEW_PO_ITEM_NUMBER = "itemNumber";
        public static final String NEW_PO_MANUF_NUMBER = "manufacturerNumber";
        public static final String NEW_PO_ACCEPTED_ITEM_QUANTITY = "acceptedItemQuantity";
        public static final String NEW_PO_ACCEPTED_ITEM_QUANTITY_VAL = "acceptedItemQuantityVal";
        public static final String NEW_PO_UNIT_OF_ISSUE_CODE = "unitOfIssueCode";
        public static final String NEW_PO_BIN_NUMBER = "binNumber";
        public static final String NEW_PO_SHELF_ID_NUMBER = "shelfIdNumber";
        public static final String NEW_PO_REASON_CODE = "reasonCode";
        public static final String FROM_PICK_LIST_LINE_STOCK_ID = "fromPickListLine.stock.stockId";
        public static final String FROM_PICK_LIST_LINE_LAST_UPDATE = "fromPickListLine.lastUpdateDate";
        public static final String FROM_PICK_LIST_LINE_STATUS_CODE = "fromPickListLine.pickStatusCodeCd";

    }

    public static class PickListLine {
        public static final String STOCK_ID = "stockId";
        public static final String PICK_STOCK_QTY = "pickStockQty";
        public static final String BACK_ORDER_QTY = "backOrderQty";
        public static final String RENTALS = "rentals";
        public static final String PICK_STATUS_CODE_CD = "pickStatusCodeCd";
        public static final String PICK_STATUS_CODE = "pickStatusCode";
        public static final String STOCK = "stock";
        public static final String CREATED_DATE = "createdDate";
        public static final String ORDER_DETAIL_ID = "orderDetailId";
        public static final String ORDER_DETAIL = "orderDetail";
        public static final String BACK_ORDER_ID = "backOrderId";
        public static final String STOCK_QTY = "stockQty";
        public static final String BIN = "bin";
        public static final String ITEM_LOCATION = "itemLocation";
        public static final String ADDITIONAL_LINES = "additionalLines";
        public static final String BIN_ID = "binId";
    }

    public static class Disposition {
        public static final String RECEIPT_CORRECTION = "Receipt Correction";
        public static final String REPLACEMENT_EXPECTED_NOT_CHECKED_IT = "Replacement expected (not checked iT)";
        public static final String REPLACEMENT_EXPECTED_CHECKED_IT = "Replacement expected (checked it)";
        public static final String NO_REPLACEMENT_EXPECTED_NOT_CHECKED_IT = "No replacement expected (not checked it)";
        public static final String NO_REPLACEMENT_EXPECTED_CHECKED_IT = "No replacement expected (checked it)";
        public static final String CREDIT_DIFFERENT_THAN_RECEIPT_NOT_CHECKED_IT = "Credit different than receipt (not checked it)";
        public static final String CREDIT_DIFFERENT_THAN_RECEIPT_CHECKED_IT = "Credit different than receipt (checked it)";
    }

    public static class RTVAction {
        public static final String RTV_REPLACEMENT_EXPECTED_NOT_CHECKED_IT = "Replacement expected (not checked iT)";
        public static final String RTV_REPLACEMENT_EXPECTED_CHECKED_IT = "Replacement expected (checked it)";
        public static final String RTV_NO_REPLACEMENT_EXPECTED_NOT_CHECKED_IT = "No replacement expected (not checked it)";
        public static final String RTV_NO_REPLACEMENT_EXPECTED_CHECKED_IT = "No replacement expected (checked it)";
        public static final String RTV_CREDIT_DIFFERENT_THAN_RECEIPT_NOT_CHECKED_IT = "Credit different than receipt (not checked it)";
        public static final String RTV_CREDIT_DIFFERENT_THAN_RECEIPT_CHECKED_IT = "Credit different than receipt (checked it)";
    }

    public static class PriceCode {
        public static final String PRICE_CODE_1 = "01";
        public static final String PRICE_CODE_2 = "02";
    }

    public static class CountWorksheetEditMode extends EditMode {
        public static final String DOC_NUMBER_ENTRY = "documentNumberEntry";
        public static final String WAREHOUSE_CODE_ENTRY = "warehouseCodeEntry";
        public static final String ZONE_CODE_ENTRY = "zoneCodeEntry";
        public static final String WORKSHEET_COUNT_NBR_ENTRY = "worksheetCountNumberEntry";
        public static final String COUNT_FREQUENCY_ENTRY = "countFreqEntry";
        public static final String QTY_GRT_THAN_ZERO_ENTRY = "qtyGrtThanEntry";
        public static final String COPIES_ENTRY = "copiesEntry";
        public static final String STOCK_ITEMS_DISPLAY_ENTRY = "stockItemsDisplayEntry";
    }

    public static class WorksheetDocument {
        public static final String DOC_TYPE_NAME = "SWKD";
    }
    
    public static final class Accounts {
        public static final String OPTION_FXD = "FXD";
        public static final String OPTION_PCT = "PCT";
        public static final String ORDER_DETAIL_ID = "orderDetailId";
        public static final String ACCOUNT_NUMBER = "accountNbr";
    }

    public static final class OrderDocument {
        public static final String DOC_NUMBER = "documentNumber";
        public static final String STOCK_STOCK_DISTRIBUTOR_NUMBER = "stock.stockDistributorNbr";
        public static final String ORDER_DETAILS_CATALOGITEM_STOCKDIST_NUMBER = "orderDetails.catalogItem.stock.stockDistributorNbr";
        public static final String ORDER_TYPE_STOCK = "STOCK";

        public static final String PROFILE_TYPE_PERSONAL = "PERSONAL";
        public static final String PROFILE_TYPE_INTERNAL = "INTERNAL";

        public static final String OPTION_FXD = "FXD";
        public static final String OPTION_PCT = "PCT";
        public static final String OPTION_LABEL_FIXED = "Fixed";
        public static final String OPTION_LABEL_PERCENT = "Percent";

        public static final String STORES_ORDER_DOCUMENT = "SORD";
        public static final String ORDER_DETAILS = "document.orderDetails";
        public static final String ACCOUNTS = "accounts";
        public static final String NEW_ORDER_DETAIL_ACCOUNTING_LINES = "newOrderDetailAccountingLines";
        public static final String NEW_ACCOUNTING_LINE = "newAccountingLine";

        public static final String ORDER_ITEM_QUANTITY = "orderItemQty";
        public static final String ORDER_ITEM_EXPECTED_DATE = "expectedDate";
        public static final String STOCK_UNIT_OF_ISSUE_CODE = "stockUnitOfIssueCd";
        public static final String ORDER_ITEM_COST_AMOUNT = "orderItemCostAmt";
        public static final String WAREHOUSE = "warehouse";
        public static final String CUSTOMER_PROFILE = "customerProfile";
        public static final String EXTENDED_COST = "extendedCost";
        public static final String ORDER_ITEM_ADDITIONAL_COST_AMOUNT = "orderItemAdditionalCostAmt";
        public static final String ORDER_ITEM_TOTAL_COST_AMOUNT = "totalCost";
        public static final String EXPECTED_DATE = "expectedDate";
        public static final String ORDER_ITEM_ADDITIONAL_COST_TYPE = "additionalCostTypeCode";
        public static final String STOCK_REMOVE_UNTIL_DATE = "catalogItem.stock.removeUntilDate";
        public static final String APPROVED_ITEMS = "approvedItems";
        public static final String ORDER_DOCUMENT = "orderDocument";
        public static final String ITEM_TO_BE_REMOVED = "itemToBeRemoved";
        public static final String ORDER_STATUS_CD = "orderStatusCd";
        public static final String RETURN_TO_SENDER_PARAM = "returnToSenderUrl";
        public static final String CREATION_DATE = "creationDate";
        public static final String OPEN_PERSONAL_ORDER_PERMISSION = "Open Personal Orders";
        public static final String CUSTOMER_PROFILE_PRINCIPAL_NAME = "customerProfile.principalName";
        public static final String RECURRING_ORDER = "recurringOrder";
        public static final String RECURRING_ORDER_QUESTION = "recurringOrderQuestion";
        public static final String SHIPPING_ADDRESS = "shippingAddress";
        public static final String BILLING_ADDRESS = "billingAddress";
		public static final String ORDER_STATUS = "orderStatus";
        public static final String ORDER_TYPE_CD = "orderTypeCode";
        public static final String ORDER_CREATE_DATE = "creationDate";        
    }

    public static final class OrderDetail {
        public static final String ORDER_DETAIL_ID = "orderDetailId";
        public static final String CAPITAL_ASSET_INFO = "capitalAssetInformation";
        public static final String CATALOG_ITEM_ID = "catalogItemId";
        public static final String ORDER_STATUS = "orderStatus";
        public static final String ORDER_DOCUMENT = "orderDocument";
        public static final String ORDER_STATUS_CD = "orderStatusCd";
        public static final String ORDER_ITEM_QTY = "orderItemQty";
        public static final String CATALOG_ITEM = "catalogItem";
    }

    public static class OptionFinderParms {
        public static final String OPTION_FINDER_DEFAULT_KEY = "-1";
        public static final String OPTION_FINDER_DEFAULT_LABEL = "SELECT VALUE";
        public static final String YES_OR_NO_OPTION_VALUE_YES_OR_TRUE = "Y";
        public static final String YES_OR_NO_OPTION_VALUE_NO_OR_FALSE = "N";
        public static final String OPTION_NO_VALUE_BLANK_KEY = null;
        public static final String OPTION_NO_VALUE_BLANK_LABEL = "";
    }

    public static class ReturnDocEditMode extends EditMode {
        public static final String DOC_READY_TO_BE_REVIEWED = "docReadyToBeReviewed";
        public static final String DOC_IN_MY_ACTIONLIST = "docInMyActonList";
        public static final String DOC_RETURN_DOC_TO_VENDOR = "isVendorReturnDoc";
        public static final String DOC_CAN_BE_EDITED = "docCanBeEdited";
        public static final String DOC_IN_FINAL_STATE = "docInFinalState";
    }

    public static class CountParameter {
        public static final String COUNT_PARAMETER_DAILY_KEY = "A";
        public static final String COUNT_PARAMETER_WEEKLY_KEY = "B";
        public static final String COUNT_PARAMETER_MONTHLY_KEY = "C";
        public static final String COUNT_PARAMETER_ANNUALLY_KEY = "Z";
        public static final String COUNT_PARAMETER_DEFAULT_KEY = "-1";

        public static final String COUNT_PARAMETER_DAILY = "DAILY";
        public static final String COUNT_PARAMETER_WEEKLY = "WEEKLY";
        public static final String COUNT_PARAMETER_MONTHLY = "MONTHLY";
        public static final String COUNT_PARAMETER_ANNUALLY = "ANNUALLY";
        public static final String COUNT_PARAMETER_DEFAULT = "SELECT FREQUENCY";
    }

    public static class CatalogGroup {
        public static final String CATALOG_GROUP_CD = "catalogGroupCd";
        public static final String CATALOG_GROUP_NM = "catalogGroupNm";
        public static final String DISPLAYABLE_IND = "displayableInd";
        
        public static final String TRUE_BUYOUT_GROUP_CODE = "TB001";
        public static final String TRUE_BUYOUT_GROUP_NAME = "True-Buyout";
    }

    public static final class CatalogSubgroup {
        public static final String CATALOG_SUBGROUPS = "catalogSubgroups";
        public static final String CATALOG_SUBGRUP_ID = "catalogSubgroupId";
        public static final String CATALOG_SUBGROUP_CD = "catalogSubgroupCd";
        public static final String CATALOG_GROUP_CD = "catalogGroupCd";
        
        public static final String TRUE_BUYOUT_SUBGROUP_CODE = "TB001";
        public static final String TRUE_BUYOUT_SUBGROUP_DESC = "True-Buyout";
    }

    public static class CatalogSubgroupItem {
        public static final String CATALOG_SUBGROUP_ID = "catalogSubgroupId";
        public static final String CATALOG_SUBGROUP_ITEM_ID = "catalogSubgroupItemId";
        public static final String CATALOG_SUBGROUP_SECTION = "document.newMaintainableObject.catalogSubgroupCd";
        public static final String CATALOG_ITEM_ID = "catalogItemId";
    }

    public static class MMPersistableBusinessObject {
        public static final String ACTIVE = "active";
        public static final String LAST_UPDATE_DT = "lastUpdateDate";
    }

    public static class CatalogPending {
        public static final String FILE_REQUIRED_ERROR = "document.catalogFile";
        public static final String FILE_NOT_SUPPORTED_ERROR = "document.catalogFile";
        public static final String CATALOG_CD_REQUIRED = "document.catalogCd";
        public static final String BEGIN_DT_REQUIRED = "document.catalogBeginDt";
        public static final String BEGIN_AFTER_END = "document.catalogBeginDt";
        public static final String CATALOG_UPLAOD_STATUS_UPLOAD = "UPLOAD";
        public static final String CATALOG_UPLAOD_STATUS_UPLOADING = "UPLOADING";
        public static final String CATALOG_UPLAOD_STATUS_UPLOADED = "UPLOADED";
        public static final String FILE_UNSUPPORTED_FORMAT_COLUMN5 = "document.catalogFile";
    }

    public static class CustomerFavHeader {
        public static final String PRINCIPAL_NAME = "principalName";
        public static final String SHARED_IND = "customerFavShareInd";
        public static final String CUSTOMER_FAV_NM = "customerFavName";
    }

    public static class Profile {
        public static final String PROFILE_ID = "profileId";
        public static final String PRINCIPAL_NAME = "principalName";
        public static final String PROFILE_DEFAULT_IND = "profileDefaultIndicator";
        public static final String PROFILE_NAME = "profileName";
        public static final String FIN_COA_CODE = "finacialChartOfAccountsCode";
        public static final String ACCOUNT_NBR = "accountNumber";
        public static final String CAMPUS_CODE = "campusCode";
        public static final String DELIVERY_BUILDING_CODE = "deliveryBuildingCode";
        public static final String DELIVERY_BUILDING_ROOM = "deliveryBuildingRoomNumber";
        public static final String BILLING_BUILDING_CODE = "billingBuildingCode";
        public static final String ORGANIZATION_CODE = "organizationCode";
        public static final String SUB_ACCOUNT_NUMBER = "subAccountNumber";
        public static final String PROJECT_CODE = "projectCode";
        public static final String CUSTOMER = "customer";
        public static final String PERSONAL_USE_IND = "personalUseIndicator";
        public static final String PROFILE_PHONE_NUMBER = "profilePhoneNumber";
    }

    public static class Address {
        public static final String ADDRESS_PROFILE_ID = "addressProfileId";
        public static final String ADDRESS_TYPE_CODE = "addressTypeCode";
        public static final String ADDRESS_LINE_1 = "addressLine1";
        public static final String ADDRESS_LINE_2 = "addressLine2";
        public static final String ADDRESS_CITY_NAME = "addressCityName";
        public static final String ADDRESS_STATE = "addressStateCode";
        public static final String ADDRESS_COUNTRY = "addressCountryCode";
        public static final String ADDRESS_POSTAL_CODE = "addressPostalCode";
        public static final String ADDRESS_PHONE_NUMBER = "addressPhoneNumber";
        public static final String ADDRESS_NAME = "addressName";
        public static final String PHONE_NUMBER_DELIM = "-";
        public static final String PHONE_NUMBER_EXT_DELIM = "x";
    }

    public static class AddressType {
        public static final String BILL_TO_ADDRESS = "01";
        public static final String SHIP_TO_ADDRESS = "02";
        public static final String OTHER_ADDRESS = "03";
        public static final String PREVIOUS_ADDRESS = "04";

    }

    public static class StockHistory {
        public static final String STOCK_ID = "stockId";
        public static final String CHECKIN_DOCUMENT_NUMBER = "checkinDocNbr";
        public static final String HISTORY_TRANS_TIMESTAMP = "historyTransTimestamp";
    }

    public static final class CatalogItemMarkup {
        public static final String MARKUP_CODE = "markupCd";
        public static final String CATALOG_ITEM_ID = "catalogItemId";
    }

    public static final class MarkupType {
        public static final String ACCOUNT_MARKUP = "01";
        public static final String ORGANIZATION_MARKUP = "02";
        public static final String ACCOUNT_CATALOG_ITEM_MARKUP = "03";
        public static final String CATALOG_FIX_MARKUP = "04";
        public static final String PASS_THROUGH = "05";
        public static final String ITEM_CATEGORY_MARKUP = "06";
        public static final String WAREHOUSE_MARKUP = "07";
        public static final String CASH_SALE_MARKUP = "08";
        public static final String ITEM_CATEGORY_CATALOG_ITEM_MARKUP = "09";
        public static final String CASH_SALE_CATALOG_ITEM_MARKUP = "10";
    }

    public static final class Markup {
        public static final String WAREHOUSE_CD = "warehouseCd";
        public static final String MARKUP_COA_CD = "markupCoaCd";
        public static final String MARKUP_CD = "markupCd";
        public static final String MARKUP_TYPE_CD = "markupTypeCd";
        public static final String ACCOUNT_NUMBER = "markupAccountNbr";
        public static final String COA_CODE = "markupCoaCd";
        public static final String ORG_CODE = "markupOrg";
        public static final String END_DATE = "markupEndDt";
        public static final String BEGIN_DATE = "markupBeginDt";
        public static final String TO_QTY = "markupToQty";
        public static final String FROM_QTY = "markupFromQty";
        public static final String CATALOG_GROUP_CD = "catalogGroupCode";
        public static final String CATALOG_SUBGROUP_ID = "catalogSubgroupId";
        public static final String MARKUP_FIXED = "markupFixed";
        public static final String MARKUP_FROM_QTY = "markupFromQty";
        public static final String MARKUP_BEGIN_DT = "markupBeginDt";
        public static final String CATALOG_SUBGROUP_CD = "catalogSubgroup.catalogSubgroupCd";
    }
    
    public static final class PackListAnnouncement {
        public static final String PACK_LIST_ANNOUNCEMENT_CD = "packListAnnouncementCode";
        public static final String DEFAULT_CODE = "01";
    }

    public static final class ShopCartDetailAdditionalCost {
        public static final String SHOP_CART_DETAIL_ID = "shopCartDetailId";
    }
    
    public static final class ShoppingCart {
        public static final String SHOP_CART_DETAILS = "shopCartDetails";
    }

    public static final class Customer {
        public static final String PROFILES = "profiles";
        public static final String PRINCIPAL_NAME = "principalName";
        public static final String CUSTOMER_PASSWORD = "customerPassword";
        public static final String CONFIRM_PASSWORD = "confirmPassword";
    }

    public static final class MMCapitalAssetInformation {
        public static final String CAPITAL_ASSET_INFORMATION = "capitalAssetInformation";
        public static final String ORDER_DETAIL_ID = "orderDetailId";
        public static final String VENDOR_HEADER_GENERATED_ID = "vendorHeaderGeneratedId";
        public static final String VENDOR_DETAIL_ASSIGNED_ID = "vendorDetailAssignedId";
        public static final String VENDOR_NAME = "vendorName";
        public static final String CAPITAL_ASSET_NUMBER = "capitalAssetNumber";
        public static final String CAPITAL_ASSET_TYPE_CODE = "capitalAssetTypeCode";


    }

    public static final class CapitalAssetInfo {
        public static final String CAPITAL_ASSET_NUMBER = "capitalAssetNumber";
        public static final String CAPITAL_ASSET_TYPE_CODE = "capitalAssetTypeCode";
        public static final String CAPITAL_ASSET_DETAIL = "capitalAssetInformationDetails";
        public static final String CAPITAL_ASSET_TAG_NUMBER = "capitalAssetTagNumber";
        public static final String CAPITAL_ASSET_CAMPUS_CODE = "campusCode";
        public static final String CAPITAL_ASSET_BUILDING_CODE = "buildingCode";
        public static final String CAPITAL_ASSET_ROOM_NUMBER = "buildingRoomNumber";
        public static final String CAPITAL_ASSET_QUANTITY = "capitalAssetQuantity";
        public static final String CAPITAL_ASSET_VENDOR_NAME = "vendorName";
        public static final String CAPITAL_ASSET_MANUFACTUREER_NAME = "capitalAssetManufacturerName";
        public static final String CAPITAL_ASSET_DESCRIPTION = "capitalAssetDescription";

    }

    public static final class PunchOutVendor {
        public static final String VENDOR_IDENTITY = "vendorIdentity";
        public static final String VENDOR_CREDENTIAL_DOMAIN = "vendorCredentialDomain";
        public static final String DEFAULT_CXML_REQUEST_PARAMETER = "cxml-urlencoded";
        public static final String PUNCH_OUT_URL = "punchOutUrl";
        public static final String CATALOG_ID = "catalogId";
    }
    
    public static final class RecurringOrder {
        public static final String END_DT = "endDt";
        public static final String START_DT = "startDt";
        public static final String TIMES_PER_YEAR = "timesPerYr";
        public static final String NO_END_DT = "Y";
        public static final String NEXT_RECURRING_DT = "nextRecurringDt";
        public static final Integer OPTION_NONE = null;
        public static final Integer OPTION_DAILY = 365;
        public static final Integer OPTION_WEEKLY = 52;
        public static final Integer OPTION_BI_WEEKLY = 26;
        public static final Integer OPTION_MONTHLY = 12;
        public static final Integer OPTION_QUARTERLY = 4;
        public static final Integer OPTION_SEMI_ANNUALLY = 2;
        public static final Integer OPTION_YEARLY = 1;
        public static final String OPTION_LABEL_NONE = "";
        public static final String OPTION_LABEL_DAILY = "Daily";
        public static final String OPTION_LABEL_WEEKLY = "Weekly";
        public static final String OPTION_LABEL_BI_WEEKLY = "Bi-weekly";
        public static final String OPTION_LABEL_MONTHLY = "Monthly";
        public static final String OPTION_LABEL_QUARTERLY = "Quarterly";
        public static final String OPTION_LABEL_SEMI_ANNUALLY = "Semi-annually";
        public static final String OPTION_LABEL_YEARLY = "Yearly";
    }

    
    public static final class MassUpdateDocument {
        public static final String COLUMN_STOCK_NBR = "Stock Number";
        public static final String COLUMN_STOCK_CST = "Unit Price";
        public static final String[] AGREEMENT_MASS_UPDATE_COLUMNS = {COLUMN_STOCK_NBR, COLUMN_STOCK_CST};
        public static final String PREVIOUS_AGREEMENT_NUMBER = "previousAgreementNumber";
        public static final String NEW_AGREEMENT_NUMBER = "newAgreementNumber";
        public static final String UPLOAD_SUMMARY = "uploadSummary";
        public static final String FILE = "file";
    }
    
    public static final class MassUpdateDetail {
        public static final String STOCK = "stock";
        
    }

    public static class DeliveryLabelDocument {
        public static final String NBR_OF_CARTONS = "document.nbrOfCartons";
    }
    
    public static class B2BCxml {
        public static final String ADDRESS_EMAIL_NAME = "default";
        public static final String POSTAL_ADDRESS_NAME = "default";
        
    }
    
    public static class CXMLRequestType {
        public static final String INVOICE_REQUEST = "I";
        public static final String ORDER_MESSAGE = "O";
        public static final String NONE = "N";   
        
    }
    
    public static class TrueBuyoutDocument {

        public static final String CUSTOMER_PROFILE = "customerProfile";
        public static final String TRUE_BUYOUT_DETAILS = "trueBuyoutDetails";
        
    }
    
    public static class TrueBuyoutDetail {
        public static final String ORDER_ITEM_QTY = "orderItemQuantity";
        public static final String ORDER_ITEM_UI = "orderItemUnitOfIssue";
        public static final String STOCK_DISTRIBUTOR_NUMBER = "stockDistributorNumber";
        public static final String ORDER_ITEM_COST = "orderItemCost";
        public static final String MARKUP_CODE = "markupCode";
        public static final String CATALOG_ID = "catalogId";
        public static final String CATALOG = "catalog";
        public static final String AGREEMENT_NUMBER = "agreementNumber";
        public static final String STOCK_TYPE_CODE = "stockTypeCode";
        public static final String RENTAL_OBJECT_CODE = "rentalObjectCode";
    }
    
    public static class SalesInstance {

        public static final String PICK_LIST_LINES = "pickListLines";
        
    }
    
    public static final class ShoppingFrontPage {
        public static final String CURRENT = "current";
    }
    
    public static class LuceneIndexing {
        public static final String CATALOG_ITEM_ID = "catalog_item_id";
        public static final String CATALOG_ID  = "catalog_id";
        public static final String CATALOG_DESC = "catalog_desc";
        public static final String DISTRIBUTOR_NBR = "distributor_nbr";
        
        public static final String SAVED_DESC = "saved_desc";
        
    }
    
    //System special characters
    public static final String LF = System.getProperty("line.separator");
    public static final String CR = "\r";
    public static final String TAB =  "\t";
    
    
   

}