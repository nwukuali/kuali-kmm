-- Create sequence 
drop sequence MM_SYS_GUID_S
;
create sequence MM_SYS_GUID_S
  minvalue 1
  maxvalue 999999999999999999999999999
  start with 1
  increment by 1
  nocache
  order
;
drop sequence MM_ACCOUNTS_S
;
 
create sequence MM_ACCOUNTS_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_ADDRESS_S
;
 
create sequence MM_ADDRESS_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_BACK_ORDER_S
;
 
create sequence MM_BACK_ORDER_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_BIN_S
;  
 
create sequence MM_BIN_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_CATALOG_IMAGE_S
;
 
create sequence MM_CATALOG_IMAGE_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_CATALOG_ITEM_IMAGE_S
;
 
create sequence MM_CATALOG_ITEM_IMAGE_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_CATALOG_ITEM_MARKUP_S
;
 
create sequence MM_CATALOG_ITEM_MARKUP_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_CATALOG_ITEM_PENDING_S
;
 
create sequence MM_CATALOG_ITEM_PENDING_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_CATALOG_ITEM_S
;
 
create sequence MM_CATALOG_ITEM_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

drop sequence MM_CATALOG_S
;
 
create sequence MM_CATALOG_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_CATALOG_SUBGROUP_ITEM_S
;
 
create sequence MM_CATALOG_SUBGROUP_ITEM_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_CATALOG_SUBGROUP_S
;
 
create sequence MM_CATALOG_SUBGROUP_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

drop sequence MM_CHECKIN_CYLINDER_S
;
 
create sequence MM_CHECKIN_CYLINDER_S 
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;                                                                                                  
  
drop sequence MM_CHECKIN_DETAIL_S
;
 
create sequence MM_CHECKIN_DETAIL_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

drop sequence MM_CPTL_AST_INFO_DTL_S 
;

create sequence MM_CPTL_AST_INFO_DTL_S 
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order
;

drop sequence MM_CREDIT_MEMO_EXPECTED_S 
;

create sequence MM_CREDIT_MEMO_EXPECTED_S 
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order
;

drop sequence MM_CUSTOMER_FAV_DETAIL_S
;
 
create sequence MM_CUSTOMER_FAV_DETAIL_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_CUSTOMER_FAV_HEADER_S
;
 
create sequence MM_CUSTOMER_FAV_HEADER_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_CYLINDER_S
;
 
create sequence MM_CYLINDER_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_FURNITURE_S
;
 
create sequence MM_FURNITURE_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_HAZARDOUS_MATERIEL_S
;
 
create sequence MM_HAZARDOUS_MATERIEL_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_ORDER_DETAIL_S
;
 
create sequence MM_ORDER_DETAIL_S
minvalue 1
maxvalue 99999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_ORDER_DOC_S
;
 
create sequence MM_ORDER_DOC_S
minvalue 1
maxvalue 99999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_PACK_LIST_LINE_S
;
 
create sequence MM_PACK_LIST_LINE_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_PICK_LIST_LINE_S
;
 
create sequence MM_PICK_LIST_LINE_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_PICK_TICKET_S
;
 
create sequence MM_PICK_TICKET_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_PROFILE_S
;
 
create sequence MM_PROFILE_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_RECONCILIATION_S
;
 
create sequence MM_RECONCILIATION_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_RECURRING_ORDER_S
;
 
create sequence MM_RECURRING_ORDER_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_RENTAL_S
;  
 
create sequence MM_RENTAL_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_RETURN_DETAIL_S
;
 
create sequence MM_RETURN_DETAIL_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_SALES_INSTANCE_S
;
 
create sequence MM_SALES_INSTANCE_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

drop sequence MM_SHOP_CART_S
;
 
create sequence MM_SHOP_CART_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;
 
drop sequence MM_SHOP_CART_DETAIL_S
;
 
create sequence MM_SHOP_CART_DETAIL_S
minvalue 1
maxvalue 99999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_SHOP_CART_DTL_ADDL_COST_S
;
 
create sequence MM_SHOP_CART_DTL_ADDL_COST_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_STOCK_ATTRIBUTE_S
;
 
create sequence MM_STOCK_ATTRIBUTE_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_STOCK_BALANCE_S
;
 
create sequence MM_STOCK_BALANCE_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_STOCK_COUNT_S
;
 
create sequence MM_STOCK_COUNT_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_STOCK_HISTORY_S
;
 
create sequence MM_STOCK_HISTORY_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_STOCK_PACK_NOTE_S
;
 
create sequence MM_STOCK_PACK_NOTE_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_STOCK_COST_S
;
 
create sequence MM_STOCK_COST_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_STOCK_S
;  
 
create sequence MM_STOCK_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_SYS_GUID_S
;
 
create sequence MM_SYS_GUID_S
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache
order;

drop sequence MM_WAREHOUSE_ACCOUNTS_S 
;

create sequence MM_WAREHOUSE_ACCOUNTS_S 
  minvalue 1
  maxvalue 999999999999999999
  start with 1
  increment by 1
  nocache
  order;

drop sequence MM_WAREHOUSE_OBJECT_S
;

create sequence MM_WAREHOUSE_OBJECT_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;  

drop sequence MM_WORKSHEET_COUNTER_S
;
 
create sequence MM_WORKSHEET_COUNTER_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

 
drop sequence MM_ZONE_S
;
 
create sequence MM_ZONE_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;

