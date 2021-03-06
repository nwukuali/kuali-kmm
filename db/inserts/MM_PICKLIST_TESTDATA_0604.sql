insert into MM_ORDER_STATUS_T (ORDER_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
values ('O', '8594D59D9DB941A98970336F62A8B073', 1, 'Order', 'Y', to_timestamp('27-05-2009 09:29:27.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
commit;

insert into MM_ORDER_DOC_T (FDOC_NBR, OBJ_ID, VER_NBR, ORDER_ID, SHOP_CART_DOC_NBR, ORDER_TYPE_CD, RECURRING_ORDER_IND, RECURRING_ORDER_ID, VENDOR_HEADER_GENERATED_ID, VENDOR_DETAIL_ASSIGNED_ID, VENDOR_NM, ORDER_DOC_PRNCPL_ID, ORDER_DOC_STATUS_CD, WAREHOUSE_CD, CAMPUS_CD, DELIVERY_BUILDING_CD, DELIVERY_BUILDING_RM_NBR, DELIVERY_DEPARTMENT_NM, DELIVERY_INSTRUCTION_TXT, BILLING_ADDRESS_ID, SHIPPING_ADDRESS_ID, REQS_ID, AR_ID, LAST_UPDATE_DT)
values ('1000', 'FEE5C4383D61429C87FEF78CB907A236', 1, 10, 0, 'OT', 'N', 0, 0, 0, '0', '0', '0', 'W1', 'MS', 'BLD1', null, null, null, null, null, null, null, to_timestamp('27-05-2009 16:14:21.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_ORDER_DOC_T (FDOC_NBR, OBJ_ID, VER_NBR, ORDER_ID, SHOP_CART_DOC_NBR, ORDER_TYPE_CD, RECURRING_ORDER_IND, RECURRING_ORDER_ID, VENDOR_HEADER_GENERATED_ID, VENDOR_DETAIL_ASSIGNED_ID, VENDOR_NM, ORDER_DOC_PRNCPL_ID, ORDER_DOC_STATUS_CD, WAREHOUSE_CD, CAMPUS_CD, DELIVERY_BUILDING_CD, DELIVERY_BUILDING_RM_NBR, DELIVERY_DEPARTMENT_NM, DELIVERY_INSTRUCTION_TXT, BILLING_ADDRESS_ID, SHIPPING_ADDRESS_ID, REQS_ID, AR_ID, LAST_UPDATE_DT)
values ('1001', 'D72DA54B90A9424AA300DF1BE262AC57', 1, 12, 0, 'OT', 'N', 0, 0, 0, '0', '0', '0', 'W1', 'MS', 'BLD2', null, null, null, null, null, null, null, to_timestamp('27-05-2009 16:14:21.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_ORDER_DOC_T (FDOC_NBR, OBJ_ID, VER_NBR, ORDER_ID, SHOP_CART_DOC_NBR, ORDER_TYPE_CD, RECURRING_ORDER_IND, RECURRING_ORDER_ID, VENDOR_HEADER_GENERATED_ID, VENDOR_DETAIL_ASSIGNED_ID, VENDOR_NM, ORDER_DOC_PRNCPL_ID, ORDER_DOC_STATUS_CD, WAREHOUSE_CD, CAMPUS_CD, DELIVERY_BUILDING_CD, DELIVERY_BUILDING_RM_NBR, DELIVERY_DEPARTMENT_NM, DELIVERY_INSTRUCTION_TXT, BILLING_ADDRESS_ID, SHIPPING_ADDRESS_ID, REQS_ID, AR_ID, LAST_UPDATE_DT)
values ('1002', 'E154B313CA5540D5A06EFFD3F868EB95', 1, 14, 0, 'OT', 'N', 0, 0, 0, '0', '0', '0', 'W2', 'MS', 'BLD3', null, null, null, null, null, null, null, to_timestamp('27-05-2009 16:14:21.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
commit;

insert into MM_SALES_INSTANCE_T (ORDER_ID, OBJ_ID, VER_NBR, ORDER_TYPE_CD, ORDER_SALES_STATUS_CD, ORDER_LINE_TOTAL_AMT, ORDER_TAXABLE_AMT, ORDER_TOTAL_AMT, CUSTOMER_PRNCPL_ID, LAST_UPDATE_DT, ORDER_DOC_NBR)
values (10, '8F047EC9EEE44394973E5F42AAB2B55E', 1, 'OTC', 'O', 100, 6, 106, 'schneppd', to_timestamp('27-05-2009 09:37:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1000');
insert into MM_SALES_INSTANCE_T (ORDER_ID, OBJ_ID, VER_NBR, ORDER_TYPE_CD, ORDER_SALES_STATUS_CD, ORDER_LINE_TOTAL_AMT, ORDER_TAXABLE_AMT, ORDER_TOTAL_AMT, CUSTOMER_PRNCPL_ID, LAST_UPDATE_DT, ORDER_DOC_NBR)
values (12, '3F81C782DFB74390AFAF58E011EB5F09', 1, 'OTC', 'O', 50, 3, 53, 'schneppd', to_timestamp('27-05-2009 09:37:56.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1001');
insert into MM_SALES_INSTANCE_T (ORDER_ID, OBJ_ID, VER_NBR, ORDER_TYPE_CD, ORDER_SALES_STATUS_CD, ORDER_LINE_TOTAL_AMT, ORDER_TAXABLE_AMT, ORDER_TOTAL_AMT, CUSTOMER_PRNCPL_ID, LAST_UPDATE_DT, ORDER_DOC_NBR)
values (14, 'BD1D41AAEDD948409585301E82D16283', 1, 'OTC', 'O', 150, 9, 159, 'schneppd', to_timestamp('27-05-2009 09:37:56.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1002');
commit;

insert into MM_ORDER_DETAIL_T (ORDER_DETAIL_ID, OBJ_ID, VER_NBR, ORDER_DOC_NBR, ORDER_LINE_NBR, ORDER_ID, ORDER_DETAIL_STATUS_CD, SHOP_CART_DOC_NBR, SHOP_CART_LINE_NBR, CATALOG_ITEM_ID, ORDER_ITEM_QTY, STOCK_UNIT_OF_ISSUE_CD, ORDER_ITEM_COST_AMT, ORDER_ITEM_PRICE_AMT, ORDER_ITEM_MARKUP_AMT, ORDER_ITEM_TAX_AMT, ORDER_ITEM_DETAIL_DESC, MANUFACTURER_NBR, SHIPPING_WGT, SHIPPING_HT, SHIPPING_WD, SHIPPING_LH, VENDOR_HEADER_GENERATED_ID, VENDOR_DETAIL_ASSIGNED_ID, VENDOR_NM, PO_ID, LAST_UPDATE_DT, WILLCALL_IND)
values ('1', '82D896C905C04FB7ADD4F39DD59B6A17', 1, '1000', 1, 10, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', to_timestamp('01-06-2009 09:57:04.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Y');
insert into MM_ORDER_DETAIL_T (ORDER_DETAIL_ID, OBJ_ID, VER_NBR, ORDER_DOC_NBR, ORDER_LINE_NBR, ORDER_ID, ORDER_DETAIL_STATUS_CD, SHOP_CART_DOC_NBR, SHOP_CART_LINE_NBR, CATALOG_ITEM_ID, ORDER_ITEM_QTY, STOCK_UNIT_OF_ISSUE_CD, ORDER_ITEM_COST_AMT, ORDER_ITEM_PRICE_AMT, ORDER_ITEM_MARKUP_AMT, ORDER_ITEM_TAX_AMT, ORDER_ITEM_DETAIL_DESC, MANUFACTURER_NBR, SHIPPING_WGT, SHIPPING_HT, SHIPPING_WD, SHIPPING_LH, VENDOR_HEADER_GENERATED_ID, VENDOR_DETAIL_ASSIGNED_ID, VENDOR_NM, PO_ID, LAST_UPDATE_DT, WILLCALL_IND)
values ('2', '9B681C44FAB845F1936EED6692CE131C', 1, '1001', 1, 12, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', to_timestamp('01-06-2009 09:57:04.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'N');
insert into MM_ORDER_DETAIL_T (ORDER_DETAIL_ID, OBJ_ID, VER_NBR, ORDER_DOC_NBR, ORDER_LINE_NBR, ORDER_ID, ORDER_DETAIL_STATUS_CD, SHOP_CART_DOC_NBR, SHOP_CART_LINE_NBR, CATALOG_ITEM_ID, ORDER_ITEM_QTY, STOCK_UNIT_OF_ISSUE_CD, ORDER_ITEM_COST_AMT, ORDER_ITEM_PRICE_AMT, ORDER_ITEM_MARKUP_AMT, ORDER_ITEM_TAX_AMT, ORDER_ITEM_DETAIL_DESC, MANUFACTURER_NBR, SHIPPING_WGT, SHIPPING_HT, SHIPPING_WD, SHIPPING_LH, VENDOR_HEADER_GENERATED_ID, VENDOR_DETAIL_ASSIGNED_ID, VENDOR_NM, PO_ID, LAST_UPDATE_DT, WILLCALL_IND)
values ('3', 'E938FB1FEE0F477D861D69DC510C5985', 1, '1001', 2, 12, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', to_timestamp('01-06-2009 09:57:04.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'Y');
insert into MM_ORDER_DETAIL_T (ORDER_DETAIL_ID, OBJ_ID, VER_NBR, ORDER_DOC_NBR, ORDER_LINE_NBR, ORDER_ID, ORDER_DETAIL_STATUS_CD, SHOP_CART_DOC_NBR, SHOP_CART_LINE_NBR, CATALOG_ITEM_ID, ORDER_ITEM_QTY, STOCK_UNIT_OF_ISSUE_CD, ORDER_ITEM_COST_AMT, ORDER_ITEM_PRICE_AMT, ORDER_ITEM_MARKUP_AMT, ORDER_ITEM_TAX_AMT, ORDER_ITEM_DETAIL_DESC, MANUFACTURER_NBR, SHIPPING_WGT, SHIPPING_HT, SHIPPING_WD, SHIPPING_LH, VENDOR_HEADER_GENERATED_ID, VENDOR_DETAIL_ASSIGNED_ID, VENDOR_NM, PO_ID, LAST_UPDATE_DT, WILLCALL_IND)
values ('4', '6DD39E23470E4B96B8C630B244826996', 1, '1002', 1, 14, '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', to_timestamp('01-06-2009 09:57:04.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'N');
commit;

insert into MM_ADDRESS_TYPE_T (ADDRESS_TYPE_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
values ('A1', '21CFF793-9E7D-4089-EC96-FF96DF201CAF', 1, 'AddressType 1', 'Y', to_timestamp('18-05-2009 14:47:06.474000', 'dd-mm-yyyy hh24:mi:ss.ff'));
commit;

insert into MM_PICK_STATUS_CODE_T (PICK_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
values ('PSC', '63B68A17D97D41458D5408B954CB6870', 1, 'pick status code', 'Y', to_timestamp('02-06-2009 16:32:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
commit;

insert into MM_WAREHOUSE_T (WAREHOUSE_CD, OBJ_ID, VER_NBR, WAREHOUSE_NME, ADDRESS_CD, CONSOLIDATION_CD, RESALE_COA_CD, RESALE_ACCOUNT_NBR, RESALE_SUB_ACCT_NBR, DEFAULT_COA_CD, DEFAULT_ACCOUNT_NBR, DEFAULT_SUB_ACCT_NBR, DEFAULT_MARKUP_CD, AGREEMENT_NBR, AGREEMENT_LAG_DAYS, BUYOUT_IND, ACTV_IND, LAST_UPDATE_DT)
values ('W3', '2EB324E0-6B13-AE56-94B6-0565FA958283', 1, null, null, null, null, null, null, null, null, null, null, null, null, 'N', 'Y', to_timestamp('03-06-2009 15:24:37.750000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_WAREHOUSE_T (WAREHOUSE_CD, OBJ_ID, VER_NBR, WAREHOUSE_NME, ADDRESS_CD, CONSOLIDATION_CD, RESALE_COA_CD, RESALE_ACCOUNT_NBR, RESALE_SUB_ACCT_NBR, DEFAULT_COA_CD, DEFAULT_ACCOUNT_NBR, DEFAULT_SUB_ACCT_NBR, DEFAULT_MARKUP_CD, AGREEMENT_NBR, AGREEMENT_LAG_DAYS, BUYOUT_IND, ACTV_IND, LAST_UPDATE_DT)
values ('W1', '556994CD-D834-D633-E58E-A908A20F33A6', 4, 'Warehouse 1', null, null, null, null, null, null, null, null, null, null, null, 'N', 'Y', to_timestamp('03-06-2009 15:57:39.518000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_WAREHOUSE_T (WAREHOUSE_CD, OBJ_ID, VER_NBR, WAREHOUSE_NME, ADDRESS_CD, CONSOLIDATION_CD, RESALE_COA_CD, RESALE_ACCOUNT_NBR, RESALE_SUB_ACCT_NBR, DEFAULT_COA_CD, DEFAULT_ACCOUNT_NBR, DEFAULT_SUB_ACCT_NBR, DEFAULT_MARKUP_CD, AGREEMENT_NBR, AGREEMENT_LAG_DAYS, BUYOUT_IND, ACTV_IND, LAST_UPDATE_DT)
values ('W2', '77FCA7A2-AB0A-9A42-78FD-C1C113839CAB', 1, 'Warehouse 2', null, null, null, null, null, null, null, null, null, null, null, 'N', 'Y', to_timestamp('15-05-2009 14:34:27.210000', 'dd-mm-yyyy hh24:mi:ss.ff'));
commit;

insert into MM_ZONE_T (ZONE_ID, OBJ_ID, VER_NBR, ZONE_CD, ZONE_DESC, WAREHOUSE_CD, ACTV_IND, LAST_UPDATE_DT)
values ('W1Z2', '360E12A50A9142C582C3AAFBDACA169F', 3, 'Z2', 'Zone 2 W1', 'W1', 'Y', to_timestamp('03-06-2009 15:57:39.534000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_ZONE_T (ZONE_ID, OBJ_ID, VER_NBR, ZONE_CD, ZONE_DESC, WAREHOUSE_CD, ACTV_IND, LAST_UPDATE_DT)
values ('W1Z3', '5BFC023C3D64462E9CF0C46FA4EDBE3C', 3, 'Z3', 'Zone 3 W1', 'W1', 'Y', to_timestamp('03-06-2009 15:57:39.534000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_ZONE_T (ZONE_ID, OBJ_ID, VER_NBR, ZONE_CD, ZONE_DESC, WAREHOUSE_CD, ACTV_IND, LAST_UPDATE_DT)
values ('W1Z4', 'DFF8FF2421BB4D899D297CAA07DD50FF', 3, 'Z4', 'Zone 4 W1', 'W1', 'Y', to_timestamp('03-06-2009 15:57:39.534000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_ZONE_T (ZONE_ID, OBJ_ID, VER_NBR, ZONE_CD, ZONE_DESC, WAREHOUSE_CD, ACTV_IND, LAST_UPDATE_DT)
values ('W1Z5', '92CB1CF5FD854F78ADB295A85065020D', 3, 'Z5', 'Zone 5 W1', 'W1', 'Y', to_timestamp('03-06-2009 15:57:39.534000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_ZONE_T (ZONE_ID, OBJ_ID, VER_NBR, ZONE_CD, ZONE_DESC, WAREHOUSE_CD, ACTV_IND, LAST_UPDATE_DT)
values ('W1Z6', '0F98457F930642DAB56A675861685F20', 3, 'Z6', 'Zone 6 W1', 'W1', 'Y', to_timestamp('03-06-2009 15:57:39.534000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_ZONE_T (ZONE_ID, OBJ_ID, VER_NBR, ZONE_CD, ZONE_DESC, WAREHOUSE_CD, ACTV_IND, LAST_UPDATE_DT)
values ('W1Z1', '63D9E4A4-79AF-9506-659F-6D2804FA6B4D', 3, 'Z1', 'Zone 1 W1', 'W1', 'Y', to_timestamp('03-06-2009 15:57:39.518000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_ZONE_T (ZONE_ID, OBJ_ID, VER_NBR, ZONE_CD, ZONE_DESC, WAREHOUSE_CD, ACTV_IND, LAST_UPDATE_DT)
values ('W2Z1', '885F64C0-0729-4D28-0CF0-5711A212EBC4', 1, 'Z1', 'Zone 1 W2', 'W2', 'Y', to_timestamp('15-05-2009 14:36:05.335000', 'dd-mm-yyyy hh24:mi:ss.ff'));
commit;

insert into MM_BIN_T (BIN_ID, OBJ_ID, VER_NBR, ZONE_ID, BIN_NBR, SHELF_ID, SHELF_ID_NBR, MAXIMUM_SHELF_QTY, BIN_HT, BIN_WD, BIN_LH, ACTV_IND, LAST_UPDATE_DT)
values ('W1Z1B20101', '8B9A2295-A91D-3B7D-D11E-2FAB56749BD6', 2, 'W1Z1', 'B2', '01', '01', 10, 6, 6, 10, 'Y', to_timestamp('03-06-2009 15:57:38.737000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_BIN_T (BIN_ID, OBJ_ID, VER_NBR, ZONE_ID, BIN_NBR, SHELF_ID, SHELF_ID_NBR, MAXIMUM_SHELF_QTY, BIN_HT, BIN_WD, BIN_LH, ACTV_IND, LAST_UPDATE_DT)
values ('W1Z2B30101', '3AEE0BA3-D4E9-151A-C971-FA2BA6447D13', 3, 'W1Z2', 'B3', '01', '01', 4, 3, 6, 5, 'Y', to_timestamp('03-06-2009 15:57:39.534000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_BIN_T (BIN_ID, OBJ_ID, VER_NBR, ZONE_ID, BIN_NBR, SHELF_ID, SHELF_ID_NBR, MAXIMUM_SHELF_QTY, BIN_HT, BIN_WD, BIN_LH, ACTV_IND, LAST_UPDATE_DT)
values ('W1Z1B10101', '949B587F-217E-7E8F-86D6-D6B469A8670A', 2, 'W1Z1', 'B1', '01', '01', 4, 2, 4, 6, 'Y', to_timestamp('03-06-2009 15:57:38.737000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_BIN_T (BIN_ID, OBJ_ID, VER_NBR, ZONE_ID, BIN_NBR, SHELF_ID, SHELF_ID_NBR, MAXIMUM_SHELF_QTY, BIN_HT, BIN_WD, BIN_LH, ACTV_IND, LAST_UPDATE_DT)
values ('W2Z1B10101', '0A2B83B7-79F0-7D84-0C9E-FDD36CDCC08B', 1, 'W2Z1', 'B1', '01', '01', 6, 5, 2, 7, 'Y', to_timestamp('15-05-2009 14:39:33.755000', 'dd-mm-yyyy hh24:mi:ss.ff'));
commit;

insert into MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID, OBJ_ID, VER_NBR, PICK_LIST_DOC_NBR, PICK_LIST_LINE_NBR, PICK_TUB_NBR, ORDER_ID, ORDER_DETAIL_ID, BIN_ID, STOCK_ID, STOCK_QTY, STOCK_UNIT_OF_ISSUE_CD, PICK_STOCK_QTY, BACK_ORDER_QTY, BACK_ORDER_ID, PICK_STATUS_CD, ROUTE_CD, LAST_UPDATE_DT, PICK_LIST_LINE_CREATED_DT)
values ('line1', 'C26F76FD23124A6D8058BD0595CB5EE8', 1, null, 1, null, 10, '1', 'W1Z1B10101', '20', '0', '0', '0', '0', '0', '0', 'RC', to_timestamp('26-05-2009 13:06:57.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('26-05-2009 13:06:57.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID, OBJ_ID, VER_NBR, PICK_LIST_DOC_NBR, PICK_LIST_LINE_NBR, PICK_TUB_NBR, ORDER_ID, ORDER_DETAIL_ID, BIN_ID, STOCK_ID, STOCK_QTY, STOCK_UNIT_OF_ISSUE_CD, PICK_STOCK_QTY, BACK_ORDER_QTY, BACK_ORDER_ID, PICK_STATUS_CD, ROUTE_CD, LAST_UPDATE_DT, PICK_LIST_LINE_CREATED_DT)
values ('line2', 'D74692026BEC433DA2EEC130543E5019', 1, null, 2, null, 12, '2', 'W1Z1B20101', '22', '0', '0', '0', '0', '0', '0', 'RC', to_timestamp('26-05-2009 13:08:17.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('26-05-2009 13:08:17.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID, OBJ_ID, VER_NBR, PICK_LIST_DOC_NBR, PICK_LIST_LINE_NBR, PICK_TUB_NBR, ORDER_ID, ORDER_DETAIL_ID, BIN_ID, STOCK_ID, STOCK_QTY, STOCK_UNIT_OF_ISSUE_CD, PICK_STOCK_QTY, BACK_ORDER_QTY, BACK_ORDER_ID, PICK_STATUS_CD, ROUTE_CD, LAST_UPDATE_DT, PICK_LIST_LINE_CREATED_DT)
values ('line3', '7CC1E98B4B2349E4A80AB54608A6A382', 1, null, 3, null, 12, '3', 'W1Z2B30101', '23', '0', '0', '0', '0', '0', '0', 'RC', to_timestamp('26-05-2009 13:08:33.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('26-05-2009 13:08:33.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
insert into MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID, OBJ_ID, VER_NBR, PICK_LIST_DOC_NBR, PICK_LIST_LINE_NBR, PICK_TUB_NBR, ORDER_ID, ORDER_DETAIL_ID, BIN_ID, STOCK_ID, STOCK_QTY, STOCK_UNIT_OF_ISSUE_CD, PICK_STOCK_QTY, BACK_ORDER_QTY, BACK_ORDER_ID, PICK_STATUS_CD, ROUTE_CD, LAST_UPDATE_DT, PICK_LIST_LINE_CREATED_DT)
values ('line4', 'E229664941AD46A494F24D8684142B9B', 1, null, 4, null, 14, '4', 'W2Z1B10101', '24', '0', '0', '0', '0', '0', '0', 'RC', to_timestamp('26-05-2009 13:09:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('26-05-2009 13:09:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'));
commit;
