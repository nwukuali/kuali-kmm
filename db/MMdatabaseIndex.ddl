create index MM_ACCOUNTS_TI1 on MM_ACCOUNTS_T (ORDER_DOC_NBR);
create index MM_ACCOUNTS_TI2 on MM_ACCOUNTS_T (ORDER_DETAIL_ID);
create index MM_ACCOUNTS_TI3 on MM_ACCOUNTS_T (SHOP_CART_DETAIL_ID);

create index MM_ADDRESS_TI1 on MM_ADDRESS_T (ADDRESS_TYPE_CD);

create index MM_BACK_ORDER_TI1 on MM_BACK_ORDER_T (STOCK_ID);

create index MM_BIN_TI1 on MM_BIN_T (ZONE_ID);

create index MM_CATALOG_IMAGE_TI1 on MM_CATALOG_IMAGE_T (CATALOG_ID);

create index MM_CATALOG_ITEM_IMAGE_TI1 on MM_CATALOG_ITEM_IMAGE_T (CATALOG_ITEM_ID);
create index MM_CATALOG_ITEM_IMAGE_TI2 on MM_CATALOG_ITEM_IMAGE_T (CATALOG_IMAGE_ID);

create index MM_CATALOG_ITEM_MARKUP_TI1 on MM_CATALOG_ITEM_MARKUP_T (CATALOG_ITEM_ID);
create index MM_CATALOG_ITEM_MARKUP_TI2 on MM_CATALOG_ITEM_MARKUP_T (MARKUP_CD);

create index MM_CATALOG_ITEM_PENDING_TI1 on MM_CATALOG_ITEM_PENDING_T (CATALOG_PENDING_DOC_NBR);

create index MM_CATALOG_ITEM_TI1 on MM_CATALOG_ITEM_T (CATALOG_ID);
create index MM_CATALOG_ITEM_TI2 on MM_CATALOG_ITEM_T (STOCK_ID);
create index MM_CATALOG_ITEM_TI3 on MM_CATALOG_ITEM_T (CATALOG_ITEM_PND_ID);

create index MM_CATALOG_SUBGROUP_ITEM_TI1 on MM_CATALOG_SUBGROUP_ITEM_T (CATALOG_ITEM_ID);
create index MM_CATALOG_SUBGROUP_ITEM_TI2 on MM_CATALOG_SUBGROUP_ITEM_T (CATALOG_SUBGROUP_ID);

create index MM_CATALOG_SUBGROUP_TI1 on MM_CATALOG_SUBGROUP_T (CATALOG_GROUP_CD);

create index MM_CATALOG_TI1 on MM_CATALOG_T (CATALOG_PENDING_DOC_NBR);
create index MM_CATALOG_TI2 on MM_CATALOG_T (CATALOG_TYPE_CD);

create index MM_CHECKIN_CYLINDER_TI1 on MM_CHECKIN_CYLINDER_T (CHECKIN_DETAIL_ID);
create index MM_CHECKIN_CYLINDER_TI2 on MM_CHECKIN_CYLINDER_T (RETURN_DETAIL_ID);

create index MM_CHECKIN_DETAIL_TI1 on MM_CHECKIN_DETAIL_T (CHECKIN_DOC_NBR);
create index MM_CHECKIN_DETAIL_TI2 on MM_CHECKIN_DETAIL_T (ORDER_DETAIL_ID);

create index MM_CHECKIN_DOC_TI1 on MM_CHECKIN_DOC_T (ORDER_DOC_NBR);
create index MM_CHECKIN_DOC_TI2 on MM_CHECKIN_DOC_T (WAREHOUSE_CD);

create index MM_CPTL_AST_INFO_DTL_TI1 on MM_CPTL_AST_INFO_DTL_T (ORDR_DTL_ID);

create index MM_CREDIT_MEMO_EXPECTED_TI1 on MM_CREDIT_MEMO_EXPECTED_T (CHECKIN_DETAIL_ID);

create index MM_CUSTOMER_FAV_DETAIL_TI1 on MM_CUSTOMER_FAV_DETAIL_T (CUSTOMER_FAV_ID);
create index MM_CUSTOMER_FAV_DETAIL_TI2 on MM_CUSTOMER_FAV_DETAIL_T (CATALOG_ITEM_ID);

create index MM_CUSTOMER_FAV_HEADER_TI1 on MM_CUSTOMER_FAV_HEADER_T (PRNCPL_NM);

create index MM_CYLINDER_TI1 on MM_CYLINDER_T (STOCK_ID);
create index MM_CYLINDER_TI2 on MM_CYLINDER_T (CHECKIN_DETAIL_ID);
create index MM_CYLINDER_TI3 on MM_CYLINDER_T (PICK_LIST_LINE_ID);
create index MM_CYLINDER_TI4 on MM_CYLINDER_T (RETURN_DETAIL_ID);

create index MM_DOT_HAZARDOUS_TI1 on MM_DOT_HAZARDOUS_T (DRIVER_MANIFEST_CD);

create index MM_FURNITURE_TI1 on MM_FURNITURE_T (SHOP_CART_ID);

create index MM_HAZARDOUS_MATERIEL_TI1 on MM_HAZARDOUS_MATERIEL_T (STOCK_ID);
create index MM_HAZARDOUS_MATERIEL_TI2 on MM_HAZARDOUS_MATERIEL_T (EHS_CONTAINER_CD);
create index MM_HAZARDOUS_MATERIEL_TI3 on MM_HAZARDOUS_MATERIEL_T (EHS_HAZARDOUS_STATE_CD);
create index MM_HAZARDOUS_MATERIEL_TI4 on MM_HAZARDOUS_MATERIEL_T (DOT_HAZARDOUS_CD);
create index MM_HAZARDOUS_MATERIEL_TI5 on MM_HAZARDOUS_MATERIEL_T (HAZARDOUS_UN_CD);
create index MM_HAZARDOUS_MATERIEL_TI6 on MM_HAZARDOUS_MATERIEL_T (EHS_HAZARDOUS_CD);
create index MM_HAZARDOUS_MATERIEL_TI7 on MM_HAZARDOUS_MATERIEL_T (EHS_UNIT_OF_ISSUE_CD);

create index MM_MARKUP_TI1 on MM_MARKUP_T (MARKUP_TYPE_CD);
create index MM_MARKUP_TI2 on MM_MARKUP_T (WAREHOUSE_CD);
create index MM_MARKUP_TI3 on MM_MARKUP_T (CATALOG_GROUP_CD);
create index MM_MARKUP_TI4 on MM_MARKUP_T (CATALOG_SUBGROUP_ID);

create index MM_ORDER_DETAIL_TI1 on MM_ORDER_DETAIL_T (ORDER_DOC_NBR);
create index MM_ORDER_DETAIL_TI2 on MM_ORDER_DETAIL_T (ORDER_DETAIL_STATUS_CD);
create index MM_ORDER_DETAIL_TI3 on MM_ORDER_DETAIL_T (SALES_INSTANCE_ID);
create index MM_ORDER_DETAIL_TI4 on MM_ORDER_DETAIL_T (CATALOG_ITEM_ID);
create index MM_ORDER_DETAIL_TI5 on MM_ORDER_DETAIL_T (ADDITIONAL_COST_TYPE_CD);

create index MM_ORDER_DOC_TI1 on MM_ORDER_DOC_T (ORDER_TYPE_CD);
create index MM_ORDER_DOC_TI2 on MM_ORDER_DOC_T (ORDER_DOC_STATUS_CD);
create index MM_ORDER_DOC_TI3 on MM_ORDER_DOC_T (SHOP_CART_ID);
create index MM_ORDER_DOC_TI4 on MM_ORDER_DOC_T (RECURRING_ORDER_ID);

create index MM_PACK_LIST_DOC_TI1 on MM_PACK_LIST_DOC_T (PACK_STATUS_CD);

create index MM_PACK_LIST_LINE_TI1 on MM_PACK_LIST_LINE_T (PACK_LIST_DOC_NBR);
create index MM_PACK_LIST_LINE_TI2 on MM_PACK_LIST_LINE_T (DELIVERY_REASON_CD);
create index MM_PACK_LIST_LINE_TI3 on MM_PACK_LIST_LINE_T (PICK_LIST_LINE_ID);
create index MM_PACK_LIST_LINE_TI4 on MM_PACK_LIST_LINE_T (PACK_STATUS_CD);
create index MM_PACK_LIST_LINE_TI5 on MM_PACK_LIST_LINE_T (ROUTE_CD);

create index MM_PICK_LIST_LINE_TI1 on MM_PICK_LIST_LINE_T (PICK_LIST_DOC_NBR);
create index MM_PICK_LIST_LINE_TI2 on MM_PICK_LIST_LINE_T (SALES_INSTANCE_ID);
create index MM_PICK_LIST_LINE_TI3 on MM_PICK_LIST_LINE_T (PICK_STATUS_CD);
create index MM_PICK_LIST_LINE_TI4 on MM_PICK_LIST_LINE_T (ORDER_DETAIL_ID);
create index MM_PICK_LIST_LINE_TI5 on MM_PICK_LIST_LINE_T (PICK_TICKET_NBR);
create index MM_PICK_LIST_LINE_TI6 on MM_PICK_LIST_LINE_T (ROUTE_CD);

create index MM_PICK_TICKET_TI1 on MM_PICK_TICKET_T (PICK_LIST_DOC_NBR);
create index MM_PICK_TICKET_TI2 on MM_PICK_TICKET_T (PICK_STATUS_CD);

create index MM_PICK_VERIFY_DOC_TI1 on MM_PICK_VERIFY_DOC_T (PICK_TICKET_NBR);

create index MM_PROFILE_TI1 on MM_PROFILE_T (CUSTOMER_PRNCPL_NM);

create index MM_RENTAL_TI1 on MM_RENTAL_T (STOCK_ID);

create index MM_RETURN_DETAIL_TI1 on MM_RETURN_DETAIL_T (RETURN_DOC_NBR);
create index MM_RETURN_DETAIL_TI2 on MM_RETURN_DETAIL_T (RETURN_DETAIL_STATUS_CD);
create index MM_RETURN_DETAIL_TI3 on MM_RETURN_DETAIL_T (ORDER_DETAIL_ID);
create index MM_RETURN_DETAIL_TI4 on MM_RETURN_DETAIL_T (CHECKIN_DETAIL_ID);
create index MM_RETURN_DETAIL_TI5 on MM_RETURN_DETAIL_T (ACTION_CD);
create index MM_RETURN_DETAIL_TI6 on MM_RETURN_DETAIL_T (DISPOSITION_CD);

create index MM_RETURN_DOC_TI1 on MM_RETURN_DOC_T (RETURN_TYPE_CD);
create index MM_RETURN_DOC_TI2 on MM_RETURN_DOC_T (ORDER_DOC_NBR);
create index MM_RETURN_DOC_TI3 on MM_RETURN_DOC_T (CUSTOMER_PROFILE_ID);
create index MM_RETURN_DOC_TI4 on MM_RETURN_DOC_T (CHECKIN_DOC_NBR);

create index MM_ROUTE_TI1 on MM_ROUTE_T (DRIVER_MANIFEST_CD);
create index MM_ROUTE_TI2 on MM_ROUTE_T (RESTRICTED_ROUTE_CD);

create index MM_SALES_INSTANCE_TI1 on MM_SALES_INSTANCE_T (ORDER_SALES_STATUS_CD);
create index MM_SALES_INSTANCE_TI2 on MM_SALES_INSTANCE_T (ORDER_TYPE_CD);
create index MM_SALES_INSTANCE_TI3 on MM_SALES_INSTANCE_T (ORDER_DOC_NBR);

create index MM_SHOP_CART_DETAIL_TI1 on MM_SHOP_CART_DETAIL_T (SHOP_CART_ID);
create index MM_SHOP_CART_DETAIL_TI2 on MM_SHOP_CART_DETAIL_T (CATALOG_ITEM_ID);

create index MM_SHOP_CART_DTL_ADDL_COST_TI1 on MM_SHOP_CART_DTL_ADDL_COST_T (SHOP_CART_DETAIL_ID);
create index MM_SHOP_CART_DTL_ADDL_COST_TI2 on MM_SHOP_CART_DTL_ADDL_COST_T (ADDITIONAL_COST_TYPE_CD);

create index MM_SHOP_CART_TI1 on MM_SHOP_CART_T (CUSTOMER_PROFILE_ID);

create index MM_STOCK_ATTRIBUTE_TI1 on MM_STOCK_ATTRIBUTE_T (STOCK_ID);
create index MM_STOCK_ATTRIBUTE_TI2 on MM_STOCK_ATTRIBUTE_T (STOCK_ATTRIBUTE_CD);

create index MM_STOCK_BALANCE_TI1 on MM_STOCK_BALANCE_T (STOCK_ID);

create index MM_STOCK_COST_TI1 on MM_STOCK_COST_T (STOCK_ID);
create index MM_STOCK_COST_TI2 on MM_STOCK_COST_T (COST_CD);

create index MM_STOCK_COUNT_TI1 on MM_STOCK_COUNT_T (WORKSHEET_COUNT_DOC_NBR);
create index MM_STOCK_COUNT_TI2 on MM_STOCK_COUNT_T (STOCK_ID);
create index MM_STOCK_COUNT_TI3 on MM_STOCK_COUNT_T (STOCK_TRANS_REASON_CD);
create index MM_STOCK_COUNT_TI4 on MM_STOCK_COUNT_T (BIN_ID);

create index MM_STOCK_HISTORY_TI1 on MM_STOCK_HISTORY_T (STOCK_ID);
create index MM_STOCK_HISTORY_TI2 on MM_STOCK_HISTORY_T (STOCK_TRANS_REASON_CD);
create index MM_STOCK_HISTORY_TI3 on MM_STOCK_HISTORY_T (CHECKIN_DOC_NBR);
create index MM_STOCK_HISTORY_TI4 on MM_STOCK_HISTORY_T (PICK_VERIFY_DOC_NBR);
create index MM_STOCK_HISTORY_TI5 on MM_STOCK_HISTORY_T (RETURN_DOC_NBR);
create index MM_STOCK_HISTORY_TI6 on MM_STOCK_HISTORY_T (WORKSHEET_COUNT_DOC_NBR);
create index MM_STOCK_HISTORY_TI7 on MM_STOCK_HISTORY_T (STOCK_COST_ID);
create index MM_STOCK_HISTORY_TI8 on MM_STOCK_HISTORY_T (BIN_ID);

create index MM_STOCK_PACK_NOTE_TI1 on MM_STOCK_PACK_NOTE_T (STOCK_ID);
create index MM_STOCK_PACK_NOTE_TI2 on MM_STOCK_PACK_NOTE_T (PACK_LIST_ANNOUNCEMENT_CD);

create index MM_STOCK_TI1 on MM_STOCK_T (SALES_UNIT_OF_ISSUE_CD);
create index MM_STOCK_TI2 on MM_STOCK_T (STOCK_TYPE_CD);
create index MM_STOCK_TI3 on MM_STOCK_T (CYCLE_CNT_CD);
create index MM_STOCK_TI4 on MM_STOCK_T (STOCK_AGREEMENT_NBR);
create index MM_STOCK_TI5 on MM_STOCK_T (RESTRICTED_ROUTE_CD);
create index MM_STOCK_TI6 on MM_STOCK_T (CYLINDER_GAS_CD);

create index MM_STOCK_TRANS_REASON_TI1 on MM_STOCK_TRANS_REASON_T (OBJECT_TYPE_CD);

create index MM_WAREHOUSE_ACCOUNTS_TI1 on MM_WAREHOUSE_ACCOUNTS_T (WAREHOUSE_ACCOUNT_TYPE_CD);
create index MM_WAREHOUSE_ACCOUNTS_TI2 on MM_WAREHOUSE_ACCOUNTS_T (WAREHOUSE_CD);

create index MM_WAREHOUSE_OBJECT_TI1 on MM_WAREHOUSE_OBJECT_T (WAREHOUSE_CD);
create index MM_WAREHOUSE_OBJECT_TI2 on MM_WAREHOUSE_OBJECT_T (WAREHOUSE_OBJECT_TYPE_CD);

create index MM_WORKSHEET_COUNT_DOC_TI1 on MM_WORKSHEET_COUNT_DOC_T (WORKSHEET_STATUS_CD);

create index MM_WORKSHEET_COUNTER_TI1 on MM_WORKSHEET_COUNTER_T (WORKSHEET_COUNT_DOC_NBR);

create index MM_ZONE_TI1 on MM_ZONE_T (WAREHOUSE_CD);