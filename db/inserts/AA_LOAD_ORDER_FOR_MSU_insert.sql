<sql
    driver="org.database.jdbcDriver"
    url="jdbc:oracle:thin:@localhost:1521:XE"
    userid="mmMSU"
    password="mmMSU" >

<transaction  src="MMdatabaseSequenceLoad.ddl"/>

<transaction  src="MM_PACK_LIST_ANNOUNCEMENT_T_insert.sql"/>

<transaction  src="MM_PACK_STATUS_CODE_T_insert.sql"/>

<transaction  src="MM_RESTRICTED_ROUTE_CODE_T_insert.sql"/>

<transaction  src="MM_DRIVER_MANIFEST_T_insert.sql"/>

<transaction  src="MM_ROUTE_T_insert.sql"/>

<transaction  src="MM_MARKUP_TYPE_T_insert.sql"/>

<transaction  src="MM_PICK_STATUS_CODE_T_insert.sql"/>

<transaction  src="MM_ACTION_T_insert.sql"/>

<transaction  src="MM_ADDITIONAL_COST_TYPE_T_insert.sql"/>

<transaction  src="MM_ADDRESS_TYPE_T_insert.sql"/>

<transaction  src="MM_CATALOG_TYPE_T_insert.sql"/>

<transaction  src="MM_COST_CODE_T_insert.sql"/>

<transaction  src="MM_CYCLE_COUNT_T_insert.sql"/>

<transaction  src="MM_DELIVERY_REASON_T_insert.sql"/>

<transaction  src="MM_DISPOSITION_T_insert_MSU.sql"/>

<transaction  src="MM_DOT_HAZARDOUS_T_insert.sql"/>

<transaction  src="MM_EHS_CONTAINER_T_insert.sql"/>

<transaction  src="MM_EHS_HAZARDOUS_T_insert.sql"/>

<transaction  src="MM_EHS_HAZARDOUS_STATE_T_insert.sql"/>

<transaction  src="MM_HAZARDOUS_UN_T_insert.sql"/>

<transaction  src="MM_ORDER_STATUS_T_insert.sql"/>

<transaction  src="MM_ORDER_TYPE_T_insert.sql"/>

<transaction  src="MM_RETURN_STATUS_CODE_T_insert.sql"/>

<transaction  src="MM_RETURN_TYPE_T_insert.sql"/>

<transaction  src="MM_STOCK_ATTRIBUTE_CODE_T_insert.sql"/>

<transaction  src="MM_STOCK_TYPE_T_insert.sql"/>

<transaction  src="MM_WORKSHEET_STATUS_T_insert.sql"/>

<transaction  src="MM_CYLINDER_GAS_T_insert_MSU.sql"/>

<transaction  src="MM_WAREHOUSE_T_insert_MSU.sql"/>

<transaction  src="MM_WAREHOUSE_ACCOUNT_TYPE_T_insert_MSU.sql"/>

<transaction  src="MM_WAREHOUSE_ACCOUNTS_T_insert_MSU.sql"/>

<transaction  src="MM_WAREHOUSE_OBJECT_TYPE_T_insert.sql"/>

<transaction  src="MM_WAREHOUSE_OBJECT_T_insert_MSU.sql"/>

<transaction  src="MM_MARKUP_T_insert_MSU.sql"/>

<transaction  src="MM_STOCK_TRANS_REASON_T_insert_MSU.sql"/>

<transaction  src="MM_CATALOG_GROUP_T_insert_MSU.sql"/>

<transaction  src="MM_CATALOG_SUBGROUP_T_insert_MSU.sql"/>

<transaction  src="MM_CATALOG_SUBGROUP_T_Prior_insert_MSU.sql"/>

<transaction  src="MM_AGREEMENT_T_insert_MSU.sql"/>

<transaction  src="MM_ZONE_T_insert_MSU.sql"/>

<transaction  src="MM_BIN_T_insert_MSU.sql"/>

<transaction  src="MM_UNIT_OF_ISSUE_T_insert_MSU.sql"/>

<transaction  src="MM_STOCK_T_insert_MSU.sql"/>

<transaction  src="MM_STOCK_BALANCE_T_insert_MSU.sql"/>

<transaction  src="MM_STOCK_COST_T_insert_MSU.sql"/>

<transaction  src="MM_STOCK_T_update_MSU.sql"/>

<transaction  src="MM_STOCK_COUNT_T_insert.sql"/>

<transaction  src="MM_CATALOG_T_insert_MSU.sql"/>

<transaction  src="MM_CATALOG_ITEM_T_fromStock_insert_MSU.sql"/>

<transaction  src="MM_CATALOG_IMAGE_T_insert_MSU.sql"/>

<transaction  src="MM_CUSTOMER_T_insert_MSU.sql"/>

<transaction  src="MM_PROFILE_T_insert_MSU.sql"/>

<transaction  src="MM_ADDRESS_T_insert_MSU.sql"/>

<transaction  src="MM_CATALOG_SUBGROUP_ITEM_T_Insert_MSU.sql"/>

<transaction  src="MM_CUSTOMER_T_Insert_MSU_A1500.sql"/>

<transaction  src="MM_PROFILE_T_insert_MSU_A1500.sql"/>

<transaction  src="MM_ADDRESS_T_insert_MSU_A1500.sql"/>

<transaction  src="MM_ORDER_DOC_T_insert_MSU_A1500.sql"/>

<transaction  src="MM_ORDER_DETAIL_T_insert_MSU_A1500.sql"/>

<transaction  src="MM_ACCOUNTS_T_insert_ODoc_MSU_A1500.sql"/>

<transaction  src="MM_ACCOUNTS_T_insert_ODtl_MSU_A1500.sql"/>

<transaction  src="MM_SALES_INSTANCE_T_insert_MSU.sql"/>

<transaction  src="MM_CYLINDER_T_insert_MSU.sql"/>

<transaction  src="MM_PICK_LIST_LINE_T_insert_MSU.sql"/>

</sql>
