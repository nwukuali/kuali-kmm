-- Indexes

create unique index MM_UNSPSC_T_KEY on MM_UNSPSC_T
  ( KEY  ASC );
create index MM_UNSPSC_T_FAMILY on MM_UNSPSC_T
  ( FAMILY  ASC );
create index MM_UNSPSC_T_SEGMENT on MM_UNSPSC_T
  ( SEGMENT ASC );

-- Foreign Keys 

alter table MM_ACCOUNTS_T
  add constraint MM_ACCOUNTS_TR1 foreign key (ORDER_DOC_NBR)
  references MM_ORDER_DOC_T (FDOC_NBR);
alter table MM_ACCOUNTS_T
  add constraint MM_ACCOUNTS_TR2 foreign key (ORDER_DETAIL_ID)
  references MM_ORDER_DETAIL_T (ORDER_DETAIL_ID);
alter table MM_ACCOUNTS_T
  add constraint MM_ACCOUNTS_TR3 foreign key (SHOP_CART_DETAIL_ID)
  references MM_SHOP_CART_DETAIL_T (SHOP_CART_DETAIL_ID);

alter table MM_ADDRESS_T
  add constraint MM_ADDRESS_TR1 foreign key (ADDRESS_TYPE_CD)
  references MM_ADDRESS_TYPE_T (ADDRESS_TYPE_CD);

alter table MM_AGREEMENT_T
  add constraint MM_AGREEMENT_TR1 foreign key (WAREHOUSE_CD)
  references MM_WAREHOUSE_T (WAREHOUSE_CD);

alter table MM_BACK_ORDER_T
  add constraint MM_BACK_ORDER_TR1 foreign key (PICK_LIST_LINE_ID)
  references MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID);
alter table MM_BACK_ORDER_T
  add constraint MM_BACK_ORDER_TR2 foreign key (NEW_PICK_LIST_LINE_ID)
  references MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID);

alter table MM_BIN_T
  add constraint MM_BIN_TR1 foreign key (ZONE_ID)
  references MM_ZONE_T (ZONE_ID);

alter table MM_CATALOG_ITEM_IMAGE_T
  add constraint MM_CATALOG_ITEM_IMAGE_TR1 foreign key (CATALOG_ITEM_ID)
  references MM_CATALOG_ITEM_T (CATALOG_ITEM_ID);
alter table MM_CATALOG_ITEM_IMAGE_T
  add constraint MM_CATALOG_ITEM_IMAGE_TR2 foreign key (CATALOG_IMAGE_ID)
  references MM_CATALOG_IMAGE_T (CATALOG_IMAGE_ID);

alter table MM_CATALOG_ITEM_MARKUP_T
  add constraint MM_CATALOG_ITEM_MARKUP_TR1 foreign key (CATALOG_ITEM_ID)
  references MM_CATALOG_ITEM_T (CATALOG_ITEM_ID);
alter table MM_CATALOG_ITEM_MARKUP_T
  add constraint MM_CATALOG_ITEM_MARKUP_TR2 foreign key (MARKUP_CD)
  references MM_MARKUP_T (MARKUP_CD);

alter table MM_CATALOG_ITEM_PENDING_T
  add constraint MM_CATALOG_ITEM_PENDING_TR1 foreign key (CATALOG_PENDING_DOC_NBR)
  references MM_CATALOG_PENDING_DOC_T (FDOC_NBR);

alter table MM_CATALOG_ITEM_T
  add constraint MM_CATALOG_ITEM_TR1 foreign key (CATALOG_ID)
  references MM_CATALOG_T (CATALOG_ID);
alter table MM_CATALOG_ITEM_T
  add constraint MM_CATALOG_ITEM_TR2 foreign key (STOCK_ID)
  references MM_STOCK_T (STOCK_ID);
alter table MM_CATALOG_ITEM_T
  add constraint MM_CATALOG_ITEM_TR3 foreign key (CATALOG_ITEM_PND_ID)
  references MM_CATALOG_ITEM_PENDING_T (CATALOG_ITEM_PND_ID);

alter table MM_CATALOG_SUBGROUP_ITEM_T
  add constraint MM_CATALOG_SUBGROUP_ITEM_TR1 foreign key (CATALOG_ITEM_ID)
  references MM_CATALOG_ITEM_T (CATALOG_ITEM_ID);
alter table MM_CATALOG_SUBGROUP_ITEM_T
  add constraint MM_CATALOG_SUBGROUP_ITEM_TR2 foreign key (CATALOG_SUBGROUP_ID)
  references MM_CATALOG_SUBGROUP_T (CATALOG_SUBGROUP_ID);

alter table MM_CATALOG_SUBGROUP_T
  add constraint MM_CATALOG_SUBGROUP_TR1 foreign key (CATALOG_GROUP_CD)
  references MM_CATALOG_GROUP_T (CATALOG_GROUP_CD);

alter table MM_CATALOG_T
  add constraint MM_CATALOG_TR1 foreign key (CATALOG_PENDING_DOC_NBR)
  references MM_CATALOG_PENDING_DOC_T (FDOC_NBR);
alter table MM_CATALOG_T
  add constraint MM_CATALOG_TR2 foreign key (CATALOG_TYPE_CD)
  references MM_CATALOG_TYPE_T (CATALOG_TYPE_CD);

alter table MM_CHECKIN_DETAIL_T
  add constraint MM_CHECKIN_DETAIL_TR1 foreign key (CHECKIN_DOC_NBR)
  references MM_CHECKIN_DOC_T (FDOC_NBR);
alter table MM_CHECKIN_DETAIL_T
  add constraint MM_CHECKIN_DETAIL_TR2 foreign key (ORDER_DETAIL_ID)
  references MM_ORDER_DETAIL_T (ORDER_DETAIL_ID);

alter table MM_CHECKIN_CYLINDER_T
  add constraint MM_CHECKIN_CYLINDER_TR1 foreign key (CHECKIN_DETAIL_ID)
  references MM_CHECKIN_DETAIL_T (CHECKIN_DETAIL_ID);
alter table MM_CHECKIN_CYLINDER_T
  add constraint MM_CHECKIN_CYLINDER_TR2 foreign key (RETURN_DETAIL_ID)
  references MM_RETURN_DETAIL_T (RETURN_DETAIL_ID);

alter table MM_CHECKIN_DOC_T
  add constraint MM_CHECKIN_DOC_TR1 foreign key (ORDER_DOC_NBR)
  references MM_ORDER_DOC_T (FDOC_NBR);
alter table MM_CHECKIN_DOC_T
  add constraint MM_CHECKIN_DOC_TR2 foreign key (WAREHOUSE_CD)
  references MM_WAREHOUSE_T (WAREHOUSE_CD);

alter table MM_CPTL_AST_INFO_DTL_T
  add constraint MM_CPTL_AST_INFO_DTL_TR1 foreign key (ORDR_DTL_ID)
  references MM_CPTL_AST_INFO_T (ORDR_DTL_ID);

alter table MM_CUSTOMER_FAV_DETAIL_T
  add constraint MM_CUSTOMER_FAV_DETAIL_TR1 foreign key (CUSTOMER_FAV_ID)
  references MM_CUSTOMER_FAV_HEADER_T (CUSTOMER_FAV_ID);
alter table MM_CUSTOMER_FAV_DETAIL_T
  add constraint MM_CUSTOMER_FAV_DETAIL_TR2 foreign key (CATALOG_ITEM_ID)
  references MM_CATALOG_ITEM_T (CATALOG_ITEM_ID);

alter table MM_CUSTOMER_FAV_HEADER_T
  add constraint MM_CUSTOMER_FAV_HEADER_TR1 foreign key (PRNCPL_NM)
  references MM_CUSTOMER_T (PRNCPL_NM);

alter table MM_CYLINDER_T
  add constraint MM_CYLINDER_TR1 foreign key (STOCK_ID)
  references MM_STOCK_T (STOCK_ID);
alter table MM_CYLINDER_T
  add constraint MM_CYLINDER_TR2 foreign key (CHECKIN_DETAIL_ID)
  references MM_CHECKIN_DETAIL_T (CHECKIN_DETAIL_ID);
alter table MM_CYLINDER_T
  add constraint MM_CYLINDER_TR3 foreign key (PICK_LIST_LINE_ID)
  references MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID);
alter table MM_CYLINDER_T
  add constraint MM_CYLINDER_TR4 foreign key (RETURN_DETAIL_ID)
  references MM_RETURN_DETAIL_T (RETURN_DETAIL_ID);

alter table MM_DOT_HAZARDOUS_T
  add constraint MM_DOT_HAZARDOUS_TR1 foreign key (DRIVER_MANIFEST_CD)
  references MM_DRIVER_MANIFEST_T (DRIVER_MANIFEST_CD) on delete set null;

alter table MM_FURNITURE_T
  add constraint MM_FURNITURE_TR1 foreign key (SHOP_CART_ID)
  references MM_SHOP_CART_T (SHOP_CART_ID);

alter table MM_HAZARDOUS_MATERIEL_T
  add constraint MM_HAZARDOUS_MATERIEL_TR1 foreign key (STOCK_ID)
  references MM_STOCK_T (STOCK_ID);
alter table MM_HAZARDOUS_MATERIEL_T
  add constraint MM_HAZARDOUS_MATERIEL_TR2 foreign key (EHS_CONTAINER_CD)
  references MM_EHS_CONTAINER_T (EHS_CONTAINER_CD) on delete set null;
alter table MM_HAZARDOUS_MATERIEL_T
  add constraint MM_HAZARDOUS_MATERIEL_TR3 foreign key (EHS_HAZARDOUS_STATE_CD)
  references MM_EHS_HAZARDOUS_STATE_T (EHS_HAZARDOUS_STATE_CD) on delete set null;
alter table MM_HAZARDOUS_MATERIEL_T
  add constraint MM_HAZARDOUS_MATERIEL_TR4 foreign key (DOT_HAZARDOUS_CD)
  references MM_DOT_HAZARDOUS_T (DOT_HAZARDOUS_CD) on delete set null;
alter table MM_HAZARDOUS_MATERIEL_T
  add constraint MM_HAZARDOUS_MATERIEL_TR5 foreign key (HAZARDOUS_UN_CD)
  references MM_HAZARDOUS_UN_T (HAZARDOUS_UN_CD) on delete set null;
alter table MM_HAZARDOUS_MATERIEL_T
  add constraint MM_HAZARDOUS_MATERIEL_TR6 foreign key (EHS_HAZARDOUS_CD)
  references MM_EHS_HAZARDOUS_T (EHS_HAZARDOUS_CD) on delete set null;
alter table MM_HAZARDOUS_MATERIEL_T
  add constraint MM_HAZARDOUS_MATERIEL_TR7 foreign key (EHS_UNIT_OF_ISSUE_CD)
  references MM_UNIT_OF_ISSUE_T (UNIT_OF_ISSUE_CD) on delete set null;

alter table MM_MARKUP_T
  add constraint MM_MARKUP_TR1 foreign key (MARKUP_TYPE_CD)
  references MM_MARKUP_TYPE_T (MARKUP_TYPE_CD) on delete set null;
alter table MM_MARKUP_T
  add constraint MM_MARKUP_TR2 foreign key (WAREHOUSE_CD)
  references MM_WAREHOUSE_T (WAREHOUSE_CD) on delete set null;
alter table MM_MARKUP_T
  add constraint MM_MARKUP_TR3 foreign key (CATALOG_GROUP_CD)
  references MM_CATALOG_GROUP_T (CATALOG_GROUP_CD);
alter table MM_MARKUP_T
  add constraint MM_MARKUP_TR4 foreign key (CATALOG_SUBGROUP_ID)
  references MM_CATALOG_SUBGROUP_T (CATALOG_SUBGROUP_ID);

alter table MM_ORDER_DETAIL_T
  add constraint MM_ORDER_DETAIL_TR1 foreign key (ORDER_DOC_NBR)
  references MM_ORDER_DOC_T (FDOC_NBR);
alter table MM_ORDER_DETAIL_T
  add constraint MM_ORDER_DETAIL_TR2 foreign key (ORDER_DETAIL_STATUS_CD)
  references MM_ORDER_STATUS_T (ORDER_STATUS_CD);
alter table MM_ORDER_DETAIL_T
  add constraint MM_ORDER_DETAIL_TR3 foreign key (SALES_INSTANCE_ID)
  references MM_SALES_INSTANCE_T (SALES_INSTANCE_ID);
alter table MM_ORDER_DETAIL_T
  add constraint MM_ORDER_DETAIL_TR4 foreign key (CATALOG_ITEM_ID)
  references MM_CATALOG_ITEM_T (CATALOG_ITEM_ID);
alter table MM_ORDER_DETAIL_T
  add constraint MM_ORDER_DETAIL_TR5 foreign key (ADDITIONAL_COST_TYPE_CD)
  references MM_ADDITIONAL_COST_TYPE_T (ADDITIONAL_COST_TYPE_CD);

alter table MM_ORDER_DOC_T
  add constraint MM_ORDER_DOC_TR1 foreign key (ORDER_TYPE_CD)
  references MM_ORDER_TYPE_T (ORDER_TYPE_CD);
alter table MM_ORDER_DOC_T
  add constraint MM_ORDER_DOC_TR2 foreign key (ORDER_DOC_STATUS_CD)
  references MM_ORDER_STATUS_T (ORDER_STATUS_CD);
alter table MM_ORDER_DOC_T
  add constraint MM_ORDER_DOC_TR3 foreign key (SHOP_CART_ID)
  references MM_SHOP_CART_T (SHOP_CART_ID);
alter table MM_ORDER_DOC_T
  add constraint MM_ORDER_DOC_TR4 foreign key (RECURRING_ORDER_ID)
  references MM_RECURRING_ORDER_T (RECURRING_ORDER_ID);

alter table MM_PACK_LIST_DOC_T
  add constraint MM_PACK_LIST_DOC_TR1 foreign key (PACK_STATUS_CD)
  references MM_PACK_STATUS_CODE_T (PACK_STATUS_CD);

alter table MM_PACK_LIST_LINE_T
  add constraint MM_PACK_LIST_LINE_TR1 foreign key (PACK_LIST_DOC_NBR)
  references MM_PACK_LIST_DOC_T (FDOC_NBR);
alter table MM_PACK_LIST_LINE_T
  add constraint MM_PACK_LIST_LINE_TR2 foreign key (DELIVERY_REASON_CD)
  references MM_DELIVERY_REASON_T (DELIVERY_REASON_CD);
alter table MM_PACK_LIST_LINE_T
  add constraint MM_PACK_LIST_LINE_TR3 foreign key (PICK_LIST_LINE_ID)
  references MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID);
alter table MM_PACK_LIST_LINE_T
  add constraint MM_PACK_LIST_LINE_TR4 foreign key (PACK_STATUS_CD)
  references MM_PACK_STATUS_CODE_T (PACK_STATUS_CD);
alter table MM_PACK_LIST_LINE_T
  add constraint MM_PACK_LIST_LINE_TR5 foreign key (ROUTE_CD)
  references MM_ROUTE_T (ROUTE_CD);

alter table MM_PICK_LIST_LINE_T
  add constraint MM_PICK_LIST_LINE_TR1 foreign key (PICK_LIST_DOC_NBR)
  references MM_PICK_LIST_DOC_T (FDOC_NBR);
alter table MM_PICK_LIST_LINE_T
  add constraint MM_PICK_LIST_LINE_TR2 foreign key (SALES_INSTANCE_ID)
  references MM_SALES_INSTANCE_T (SALES_INSTANCE_ID);
alter table MM_PICK_LIST_LINE_T
  add constraint MM_PICK_LIST_LINE_TR3 foreign key (PICK_STATUS_CD)
  references MM_PICK_STATUS_CODE_T (PICK_STATUS_CD);
alter table MM_PICK_LIST_LINE_T
  add constraint MM_PICK_LIST_LINE_TR4 foreign key (ORDER_DETAIL_ID)
  references MM_ORDER_DETAIL_T (ORDER_DETAIL_ID);
alter table MM_PICK_LIST_LINE_T
  add constraint MM_PICK_LIST_LINE_TR5 foreign key (PICK_TICKET_NBR)
  references MM_PICK_TICKET_T (PICK_TICKET_NBR);
alter table MM_PICK_LIST_LINE_T
  add constraint MM_PICK_LIST_LINE_TR6 foreign key (ROUTE_CD)
  references MM_ROUTE_T (ROUTE_CD);

alter table MM_PICK_TICKET_T
  add constraint MM_PICK_TICKET_TR1 foreign key (PICK_LIST_DOC_NBR)
  references MM_PICK_LIST_DOC_T (FDOC_NBR);
alter table MM_PICK_TICKET_T
  add constraint MM_PICK_TICKET_TR2 foreign key (PICK_STATUS_CD)
  references MM_PICK_STATUS_CODE_T (PICK_STATUS_CD);

alter table MM_PICK_VERIFY_DOC_T
  add constraint MM_PICK_VERIFY_DOC_TR1 foreign key (PICK_TICKET_NBR)
  references MM_PICK_TICKET_T (PICK_TICKET_NBR);

alter table MM_PROFILE_T
  add constraint MM_PROFILE_TR1 foreign key (CUSTOMER_PRNCPL_NM)
  references MM_CUSTOMER_T (PRNCPL_NM);

alter table MM_RENTAL_T
  add constraint MM_RENTAL_TR1 foreign key (STOCK_ID)
  references MM_STOCK_T (STOCK_ID);

alter table MM_RETURN_DETAIL_T
  add constraint MM_RETURN_DETAIL_TR1 foreign key (RETURN_DOC_NBR)
  references MM_RETURN_DOC_T (FDOC_NBR);
alter table MM_RETURN_DETAIL_T
  add constraint MM_RETURN_DETAIL_TR2 foreign key (RETURN_DETAIL_STATUS_CD)
  references MM_RETURN_STATUS_CODE_T (RETURN_STATUS_CD);
alter table MM_RETURN_DETAIL_T
  add constraint MM_RETURN_DETAIL_TR3 foreign key (ORDER_DETAIL_ID)
  references MM_ORDER_DETAIL_T (ORDER_DETAIL_ID);
alter table MM_RETURN_DETAIL_T
  add constraint MM_RETURN_DETAIL_TR4 foreign key (CHECKIN_DETAIL_ID)
  references MM_CHECKIN_DETAIL_T (CHECKIN_DETAIL_ID);
alter table MM_RETURN_DETAIL_T
  add constraint MM_RETURN_DETAIL_TR5 foreign key (ACTION_CD)
  references MM_ACTION_T (ACTION_CD);
alter table MM_RETURN_DETAIL_T
  add constraint MM_RETURN_DETAIL_TR6 foreign key (DISPOSITION_CD)
  references MM_DISPOSITION_T (DISPOSITION_CD);

alter table MM_RETURN_DOC_T
  add constraint MM_RETURN_DOC_TR1 foreign key (RETURN_TYPE_CD)
  references MM_RETURN_TYPE_T (RETURN_TYPE_CD);
alter table MM_RETURN_DOC_T
  add constraint MM_RETURN_DOC_TR2 foreign key (RETURN_DOC_STATUS_CD)
  references MM_RETURN_STATUS_CODE_T (RETURN_STATUS_CD);
alter table MM_RETURN_DOC_T
  add constraint MM_RETURN_DOC_TR3 foreign key (ORDER_DOC_NBR)
  references MM_ORDER_DOC_T (FDOC_NBR) on delete set null;
alter table MM_RETURN_DOC_T
  add constraint MM_RETURN_DOC_TR4 foreign key (CUSTOMER_PROFILE_ID)
  references MM_PROFILE_T (PROFILE_ID);
alter table MM_RETURN_DOC_T
  add constraint MM_RETURN_DOC_TR5 foreign key (CHECKIN_DOC_NBR)
  references MM_CHECKIN_DOC_T (FDOC_NBR);

alter table MM_ROUTE_T
  add constraint MM_ROUTE_TR1 foreign key (DRIVER_MANIFEST_CD)
  references MM_DRIVER_MANIFEST_T (DRIVER_MANIFEST_CD) on delete set null;
alter table MM_ROUTE_T
  add constraint MM_ROUTE_TR2 foreign key (RESTRICTED_ROUTE_CD)
  references MM_RESTRICTED_ROUTE_CODE_T (RESTRICTED_ROUTE_CD);

alter table MM_SALES_INSTANCE_T
  add constraint MM_SALES_INSTANCE_TR1 foreign key (ORDER_SALES_STATUS_CD)
  references MM_ORDER_STATUS_T (ORDER_STATUS_CD);
alter table MM_SALES_INSTANCE_T
  add constraint MM_SALES_INSTANCE_TR2 foreign key (ORDER_TYPE_CD)
  references MM_ORDER_TYPE_T (ORDER_TYPE_CD);
alter table MM_SALES_INSTANCE_T
  add constraint MM_SALES_INSTANCE_TR3 foreign key (ORDER_DOC_NBR)
  references MM_ORDER_DOC_T (FDOC_NBR);

alter table MM_SHOP_CART_DETAIL_T
  add constraint MM_SHOP_CART_DETAIL_TR1 foreign key (SHOP_CART_ID)
  references MM_SHOP_CART_T (SHOP_CART_ID);
alter table MM_SHOP_CART_DETAIL_T
  add constraint MM_SHOP_CART_DETAIL_TR2 foreign key (CATALOG_ITEM_ID)
  references MM_CATALOG_ITEM_T (CATALOG_ITEM_ID);

alter table MM_SHOP_CART_DTL_ADDL_COST_T
  add constraint MM_SHOP_CART_DTL_ADDL_COST_TR1 foreign key (SHOP_CART_DETAIL_ID)
  references MM_SHOP_CART_DETAIL_T (SHOP_CART_DETAIL_ID);
alter table MM_SHOP_CART_DTL_ADDL_COST_T
  add constraint MM_SHOP_CART_DTL_ADDL_COST_TR2 foreign key (ADDITIONAL_COST_TYPE_CD)
  references MM_ADDITIONAL_COST_TYPE_T (ADDITIONAL_COST_TYPE_CD);

alter table MM_SHOP_CART_T
  add constraint MM_SHOP_CART_TR1 foreign key (CUSTOMER_PROFILE_ID)
  references MM_PROFILE_T (PROFILE_ID);

alter table MM_STOCK_ATTRIBUTE_T
  add constraint MM_STOCK_ATTRIBUTE_TR1 foreign key (STOCK_ID)
  references MM_STOCK_T (STOCK_ID);
alter table MM_STOCK_ATTRIBUTE_T
  add constraint MM_STOCK_ATTRIBUTE_TR2 foreign key (STOCK_ATTRIBUTE_CD)
  references MM_STOCK_ATTRIBUTE_CODE_T (STOCK_ATTRIBUTE_CD);

alter table MM_STOCK_BALANCE_T
  add constraint MM_STOCK_BALANCE_TR1 foreign key (BIN_ID)
  references MM_BIN_T (BIN_ID);
alter table MM_STOCK_BALANCE_T
  add constraint MM_STOCK_BALANCE_TR2 foreign key (STOCK_ID)
  references MM_STOCK_T (STOCK_ID);

alter table MM_STOCK_COST_T
  add constraint MM_STOCK_COST_TR1 foreign key (STOCK_ID)
  references MM_STOCK_T (STOCK_ID);
alter table MM_STOCK_COST_T
  add constraint MM_STOCK_COST_TR2 foreign key (COST_CD)
  references MM_COST_CODE_T (COST_CD);

alter table MM_STOCK_COUNT_T
  add constraint MM_STOCK_COUNT_TR1 foreign key (WORKSHEET_COUNT_DOC_NBR)
  references MM_WORKSHEET_COUNT_DOC_T (FDOC_NBR);
alter table MM_STOCK_COUNT_T
  add constraint MM_STOCK_COUNT_TR2 foreign key (STOCK_ID)
  references MM_STOCK_T (STOCK_ID);
alter table MM_STOCK_COUNT_T
  add constraint MM_STOCK_COUNT_TR3 foreign key (STOCK_TRANS_REASON_CD)
  references MM_STOCK_TRANS_REASON_T (STOCK_TRANS_REASON_CD);
alter table MM_STOCK_COUNT_T
  add constraint MM_STOCK_COUNT_TR4 foreign key (BIN_ID)
  references MM_BIN_T (BIN_ID);

alter table MM_STOCK_HISTORY_T
  add constraint MM_STOCK_HISTORY_TR1 foreign key (STOCK_ID)
  references MM_STOCK_T (STOCK_ID);
alter table MM_STOCK_HISTORY_T
  add constraint MM_STOCK_HISTORY_TR2 foreign key (STOCK_TRANS_REASON_CD)
  references MM_STOCK_TRANS_REASON_T (STOCK_TRANS_REASON_CD);
alter table MM_STOCK_HISTORY_T
  add constraint MM_STOCK_HISTORY_TR3 foreign key (CHECKIN_DOC_NBR)
  references MM_CHECKIN_DOC_T (FDOC_NBR);
alter table MM_STOCK_HISTORY_T
  add constraint MM_STOCK_HISTORY_TR4 foreign key (PICK_VERIFY_DOC_NBR)
  references MM_PICK_VERIFY_DOC_T (FDOC_NBR);
alter table MM_STOCK_HISTORY_T
  add constraint MM_STOCK_HISTORY_TR5 foreign key (RETURN_DOC_NBR)
  references MM_RETURN_DOC_T (FDOC_NBR);
alter table MM_STOCK_HISTORY_T
  add constraint MM_STOCK_HISTORY_TR6 foreign key (WORKSHEET_COUNT_DOC_NBR)
  references MM_WORKSHEET_COUNT_DOC_T (FDOC_NBR);
alter table MM_STOCK_HISTORY_T
  add constraint MM_STOCK_HISTORY_TR7 foreign key (STOCK_COST_ID)
  references MM_STOCK_COST_T (STOCK_COST_ID);
alter table MM_STOCK_HISTORY_T
  add constraint MM_STOCK_HISTORY_TR8 foreign key (BIN_ID)
  references MM_BIN_T (BIN_ID);

alter table MM_STOCK_PACK_NOTE_T
  add constraint MM_STOCK_PACK_NOTE_TR1 foreign key (STOCK_ID)
  references MM_STOCK_T (STOCK_ID);
alter table MM_STOCK_PACK_NOTE_T
  add constraint MM_STOCK_PACK_NOTE_TR2 foreign key (PACK_LIST_ANNOUNCEMENT_CD)
  references MM_PACK_LIST_ANNOUNCEMENT_T (PACK_LIST_ANNOUNCEMENT_CD);

alter table MM_STOCK_T
  add constraint MM_STOCK_TR1 foreign key (SALES_UNIT_OF_ISSUE_CD)
  references MM_UNIT_OF_ISSUE_T (UNIT_OF_ISSUE_CD);
alter table MM_STOCK_T
  add constraint MM_STOCK_TR2 foreign key (STOCK_TYPE_CD)
  references MM_STOCK_TYPE_T (STOCK_TYPE_CD);
alter table MM_STOCK_T
  add constraint MM_STOCK_TR3 foreign key (CYCLE_CNT_CD)
  references MM_CYCLE_COUNT_T (CYCLE_CNT_CD);
alter table MM_STOCK_T
  add constraint MM_STOCK_TR4 foreign key (BUY_UNIT_OF_ISSUE_CD)
  references MM_UNIT_OF_ISSUE_T (UNIT_OF_ISSUE_CD);
alter table MM_STOCK_T
  add constraint MM_STOCK_TR5 foreign key (STOCK_AGREEMENT_NBR)
  references MM_AGREEMENT_T (AGREEMENT_NBR);
alter table MM_STOCK_T
  add constraint MM_STOCK_TR6 foreign key (RESTRICTED_ROUTE_CD)
  references MM_RESTRICTED_ROUTE_CODE_T (RESTRICTED_ROUTE_CD);
alter table MM_STOCK_T
  add constraint MM_STOCK_TR7 foreign key (CYLINDER_GAS_CD)
  references MM_CYLINDER_GAS_T (CYLINDER_GAS_CD);

alter table MM_STOCK_TRANS_REASON_T
  add constraint MM_STOCK_TRANS_REASON_TR1 foreign key (OBJECT_TYPE_CD)
  references MM_WAREHOUSE_OBJECT_TYPE_T (OBJECT_TYPE_CD);

alter table MM_WAREHOUSE_ACCOUNTS_T
  add constraint MM_WAREHOUSE_ACCOUNTS_TR1 foreign key (WAREHOUSE_ACCOUNT_TYPE_CD)
  references MM_WAREHOUSE_ACCOUNT_TYPE_T (WAREHOUSE_ACCOUNT_TYPE_CD);
alter table MM_WAREHOUSE_ACCOUNTS_T
  add constraint MM_WAREHOUSE_ACCOUNTS_TR2 foreign key (WAREHOUSE_CD)
  references MM_WAREHOUSE_T (WAREHOUSE_CD);

alter table MM_WAREHOUSE_OBJECT_T
  add constraint MM_WAREHOUSE_OBJECT_TR1 foreign key (WAREHOUSE_CD)
  references MM_WAREHOUSE_T (WAREHOUSE_CD);
alter table MM_WAREHOUSE_OBJECT_T
  add constraint MM_WAREHOUSE_OBJECT_TR2 foreign key (WAREHOUSE_OBJECT_TYPE_CD)
  references MM_WAREHOUSE_OBJECT_TYPE_T (OBJECT_TYPE_CD);

alter table MM_WORKSHEET_COUNTER_T
  add constraint MM_WORKSHEET_COUNTER_TR1 foreign key (WORKSHEET_COUNT_DOC_NBR)
  references MM_WORKSHEET_COUNT_DOC_T (FDOC_NBR);

alter table MM_WORKSHEET_COUNT_DOC_T
  add constraint MM_WORKSHEET_COUNT_DOC_TR1 foreign key (WORKSHEET_STATUS_CD)
  references MM_WORKSHEET_STATUS_T (WORKSHEET_STATUS_CD);

alter table MM_ZONE_T
  add constraint MM_ZONE_TR1 foreign key (WAREHOUSE_CD)
  references MM_WAREHOUSE_T (WAREHOUSE_CD);
