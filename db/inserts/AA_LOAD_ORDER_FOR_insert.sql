<sql
    driver="org.database.jdbcDriver"
    url="jdbc:oracle:thin:@localhost:1521:XE"
    userid="mmdev"
    password="mmdev" >

<transaction  src="MMdatabaseSequenceLoad.ddl"/>

<transaction  src="mm-dev/db/baseline/MM_ACTION_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_ADDITIONAL_COST_TYPE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_ADDRESS_TYPE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_CATALOG_TYPE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_CYCLE_COUNT_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_DELIVERY_REASON_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_DISPOSITION_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_DOT_HAZARDOUS_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_EHS_CONTAINER_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_EHS_HAZARDOUS_STATE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_EHS_HAZARDOUS_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_HAZARDOUS_UN_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_MARKUP_TYPE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_ORDER_STATUS_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_ORDER_TYPE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_PACK_LIST_ANNOUNCEMENT_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_PACK_STATUS_CODE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_PICK_STATUS_CODE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_COST_CODE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_RESTRICTED_ROUTE_CODE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_RETURN_STATUS_CODE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_RETURN_TYPE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_ROUTE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_STOCK_ATTRIBUTE_CODE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_WAREOUSE_OBJECT_TYPE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_STOCK_TRANS_REASON_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_STOCK_TYPE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_UNIT_OF_ISSUE_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_WORKSHEET_STATUS_T_insert.sql"/>

<transaction  src="mm-dev/db/baseline/MM_BATCH_CTRL.sql"/>

<transaction  src="mm-dev/db/inserts/MM_WAREHOUSE_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_ZONE_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_BIN_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CATALOG_GROUP_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CATALOG_SUBGROUP_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CATALOG_IMAGE_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CATALOG_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CATALOG_ITEM_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CATALOG_ITEM_PENDING_T_INSERT.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CUSTOMER_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CYLINDER_GAS_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_DRIVER_MANIFEST_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_MARKUP_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_PROFILE_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_AGREEMENT_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_STOCK_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_STOCK_BALANCE_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_STOCK_COST_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_STOCK_COUNT_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CATALOG_ITEM_T_fromStock_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_SHOP_CART_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_SHOP_CART_DETAIL_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_ORDER_DOC_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_ORDER_DETAIL_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_ACCOUNTS_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_SALES_INSTANCE_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_ORDER_DETAIL_T_updateSalesInstance_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_AGREEMENT_T_updateStock_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_ORDER_DOC_T_reorder_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_ORDER_DETAIL_T_reorder_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_ACCOUNTS_T_reorder_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_STOCK_T_fixStockTypeCd.sql"/>

<transaction  src="mm-dev/db/inserts/MM_ADDRESS_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_ADDRESS_T_CartAndOrder_update.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CYLINDER_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_PICK_LIST_LINE_T_insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CATALOG_SUBGROUP_ITEM_T_Insert.sql"/>

<transaction  src="mm-dev/db/inserts/MM_CATALOG_SUBGROUP_T_Prior_insert.sql"/>

</sql>