create table MM_ACCOUNTS_T
(
  ACCOUNTS_ID              NUMBER(18) not null,
  OBJ_ID                   VARCHAR2(36) default sys_guid() not null,
  VER_NBR                  NUMBER(8) default 1 not null,
  ORDER_DOC_NBR            VARCHAR2(14),
  AMOUNT_TYP               VARCHAR2(3) not null,
  FIN_COA_CD               VARCHAR2(2) not null,
  ACCOUNT_NBR              VARCHAR2(32) not null,
  SUB_ACCT_NBR             VARCHAR2(5),
  FIN_OBJECT_CD            VARCHAR2(4),
  FIN_SUB_OBJ_CD           VARCHAR2(6),
  PROJECT_CD               VARCHAR2(10),
  ACCOUNT_FIXED_AMT        NUMBER(19,4) not null,
  ORDER_DETAIL_ID          NUMBER(18),
  DEPARTMENT_REFERENCE_TXT VARCHAR2(15),
  ACCOUNT_PCT              NUMBER(13,10),
  SHOP_CART_DETAIL_ID      NUMBER(18),
  LAST_UPDATE_DT           TIMESTAMP(6) not null
)
;
alter table MM_ACCOUNTS_T
  add constraint MM_ACCOUNTS_TP1 primary key (ACCOUNTS_ID);
alter table MM_ACCOUNTS_T
  add constraint MM_ACCOUNTS_TC0 unique (OBJ_ID);

create table MM_ACTION_T
(
  ACTION_CD      VARCHAR2(8) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  NM             VARCHAR2(45),
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_ACTION_T
  add constraint MM_ACTION_TP1 primary key (ACTION_CD);
alter table MM_ACTION_T
  add constraint MM_ACTION_TC0 unique (OBJ_ID);

create table MM_ADDITIONAL_COST_TYPE_T
(
  ADDITIONAL_COST_TYPE_CD VARCHAR2(12) not null,
  OBJ_ID                  VARCHAR2(36) default sys_guid() not null,
  VER_NBR                 NUMBER(8) default 1 not null,
  NM                      VARCHAR2(45),
  ACTV_IND                VARCHAR2(1) not null,
  LAST_UPDATE_DT          TIMESTAMP(6) not null
)
;
alter table MM_ADDITIONAL_COST_TYPE_T
  add constraint MM_ADDITIONAL_COST_TYPE_TP1 primary key (ADDITIONAL_COST_TYPE_CD);
alter table MM_ADDITIONAL_COST_TYPE_T
  add constraint MM_ADDITIONAL_COST_TYPE_TC0 unique (OBJ_ID);

create table MM_ADDRESS_T
(
  ADDRESS_ID         NUMBER(18) not null,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  ADDRESS_TYPE_CD    VARCHAR2(2) not null,
  ADDRESS_PROFILE_ID VARCHAR2(36) not null,
  ADDRESS_NM         VARCHAR2(45) not null,
  ADDRESS_LN1        VARCHAR2(45) not null,
  ADDRESS_LN2        VARCHAR2(45),
  ADDRESS_CITY_NM    VARCHAR2(45) not null,
  ADDRESS_STATE_CD   VARCHAR2(2) not null,
  ADDRESS_POSTAL_CD  VARCHAR2(20),
  ADDRESS_COUNTRY_CD VARCHAR2(2) not null,
  ADDRESS_PHONE_NBR  VARCHAR2(45) not null,
  ACTV_IND           VARCHAR2(1) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_ADDRESS_T
  add constraint MM_ADDRESS_TP1 primary key (ADDRESS_ID);
alter table MM_ADDRESS_T
  add constraint MM_ADDRESS_TC0 unique (OBJ_ID);

create table MM_ADDRESS_TYPE_T
(
  ADDRESS_TYPE_CD VARCHAR2(2) not null,
  OBJ_ID          VARCHAR2(36) default sys_guid() not null,
  VER_NBR         NUMBER(8) default 1 not null,
  NM              VARCHAR2(45) not null,
  ACTV_IND        VARCHAR2(1) not null,
  LAST_UPDATE_DT  TIMESTAMP(6) not null
)
;
alter table MM_ADDRESS_TYPE_T
  add constraint MM_ADDRESS_TYPE_TP1 primary key (ADDRESS_TYPE_CD);
alter table MM_ADDRESS_TYPE_T
  add constraint MM_ADDRESS_TYPE_TC0 unique (OBJ_ID);

create table MM_AGREEMENT_T
(
  AGREEMENT_NBR       VARCHAR2(15) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  PO_ID               NUMBER(18),
  VNDR_CONTR_GNRTD_ID NUMBER(10) not null,
  VENDOR_NM           VARCHAR2(45) not null,
  PO_TOTAL_LIMIT      NUMBER(19,2) not null,
  AGREEMENT_LAG_DAYS  NUMBER(8),
  AGREEMENT_USED_AMT  NUMBER(19,2) not null,
  AGREEMENT_BEGIN_DT  DATE not null,
  AGREEMENT_END_DT    DATE not null,
  WAREHOUSE_CD        VARCHAR2(2),
  VNDR_HDR_GNRTD_ID   NUMBER(10),
  VNDR_DTL_ASND_ID    NUMBER(10),
  B2B_IND             VARCHAR2(1) not null,
  GHOST_CARD_IND      VARCHAR2(1) not null,
  E_INVOICE_IND       VARCHAR2(1),
  ACTV_IND            VARCHAR2(1) not null,
  LAST_UPDATE_DT      TIMESTAMP(6) not null
)
;
alter table MM_AGREEMENT_T
  add constraint MM_AGREEMENT_TP1 primary key (AGREEMENT_NBR);
alter table MM_AGREEMENT_T
  add constraint MM_AGREEMENT_TC0 unique (OBJ_ID);

create table MM_BACK_ORDER_T
(
  BACK_ORDER_ID         NUMBER(18) not null,
  OBJ_ID                VARCHAR2(36) default sys_guid() not null,
  VER_NBR               NUMBER(8) default 1 not null,
  PICK_LIST_LINE_ID     NUMBER(18) not null,
  NEW_PICK_LIST_LINE_ID NUMBER(18),
  BACK_ORDER_STOCK_QTY  NUMBER(18,4) not null,
  LAST_UPDATE_DT        TIMESTAMP(6) not null
)
;
alter table MM_BACK_ORDER_T
  add constraint MM_BACK_ORDER_TP1 primary key (BACK_ORDER_ID);
alter table MM_BACK_ORDER_T
  add constraint MM_BACK_ORDER_TC0 unique (PICK_LIST_LINE_ID);

create table MM_BATCH_CTRL_T
(
  JOB_NM         VARCHAR2(50) not null,
  JOB_DESC       VARCHAR2(200),
  LAST_SUCCESS   TIMESTAMP(6),
  LAST_ATTEMPT   TIMESTAMP(6),
  CURRENT_STATUS VARCHAR2(30),
  ERROR_DETAIL   VARCHAR2(1000)
)
;
alter table MM_BATCH_CTRL_T
  add constraint MM_BATCH_CTRL_TP1 primary key (JOB_NM);

create table MM_BIN_T
(
  BIN_ID            NUMBER(18) not null,
  OBJ_ID            VARCHAR2(36) default sys_guid() not null,
  VER_NBR           NUMBER(8) default 1 not null,
  ZONE_ID           NUMBER(18) not null,
  BIN_NBR           VARCHAR2(6) not null,
  SHELF_ID          VARCHAR2(2),
  SHELF_ID_NBR      VARCHAR2(2),
  MAXIMUM_SHELF_QTY NUMBER(8),
  BIN_HT            NUMBER(8,2),
  BIN_WD            NUMBER(8,2),
  BIN_LH            NUMBER(8,2),
  ACTV_IND          VARCHAR2(1) not null,
  LAST_UPDATE_DT    TIMESTAMP(6) not null
)
;
alter table MM_BIN_T
  add constraint MM_BIN_TP1 primary key (BIN_ID);
alter table MM_BIN_T
  add constraint MM_BIN_TC0 unique (ZONE_ID, BIN_NBR, SHELF_ID, SHELF_ID_NBR);

create table MM_CAROUSEL_LOG_T
(
  CAROUSEL_LINE_ID      NUMBER(18) not null,
  CAROUSEL_LINE_TYPE_CD VARCHAR2(3) not null,
  OBJ_ID                VARCHAR2(36) default sys_guid() not null,
  VER_NBR               NUMBER(8) default 1 not null,
  LAST_UPDATE_DT        TIMESTAMP(6) not null
)
;
alter table MM_CAROUSEL_LOG_T
  add constraint CAROUSEL_LOG_TP1 primary key (CAROUSEL_LINE_ID, CAROUSEL_LINE_TYPE_CD);
alter table MM_CAROUSEL_LOG_T
  add constraint CAROUSEL_LOG_TC0 unique (OBJ_ID);

create table MM_CATALOG_GROUP_T
(
  CATALOG_GROUP_CD VARCHAR2(12) not null,
  OBJ_ID           VARCHAR2(36) default sys_guid() not null,
  VER_NBR          NUMBER(8) default 1 not null,
  CATALOG_GROUP_NM VARCHAR2(45),
  ACTV_IND         VARCHAR2(1) not null,
  LAST_UPDATE_DT   TIMESTAMP(6) not null
)
;
alter table MM_CATALOG_GROUP_T
  add constraint MM_CATALOG_GROUP_TP1 primary key (CATALOG_GROUP_CD);
alter table MM_CATALOG_GROUP_T
  add constraint MM_CATALOG_GROUP_TC0 unique (OBJ_ID);

create table MM_CATALOG_IMAGE_T
(
  CATALOG_IMAGE_ID  NUMBER(18) not null,
  OBJ_ID            VARCHAR2(36) default sys_guid() not null,
  VER_NBR           NUMBER(8) default 1 not null,
  CATALOG_IMAGE_NM  VARCHAR2(30) not null,
  CATALOG_IMAGE_URL VARCHAR2(250) not null,
  ACTV_IND          VARCHAR2(1) not null,
  LAST_UPDATE_DT    TIMESTAMP(6) not null
)
;
alter table MM_CATALOG_IMAGE_T
  add constraint MM_CATALOG_IMAGE_TP1 primary key (CATALOG_IMAGE_ID);
alter table MM_CATALOG_IMAGE_T
  add constraint MM_CATALOG_IMAGE_TC0 unique (OBJ_ID);

create table MM_CATALOG_ITEM_IMAGE_T
(
  CATALOG_ITEM_IMAGE_ID NUMBER(18) not null,
  OBJ_ID                VARCHAR2(36) default sys_guid() not null,
  VER_NBR               NUMBER(8) default 1 not null,
  CATALOG_ITEM_ID       NUMBER(18) not null,
  CATALOG_IMAGE_ID      NUMBER(18) not null,
  ACTV_IND              VARCHAR2(1) not null,
  LAST_UPDATE_DT        TIMESTAMP(6) not null
)
;
alter table MM_CATALOG_ITEM_IMAGE_T
  add constraint MM_CATALOG_ITEM_IMAGE_TP1 primary key (CATALOG_ITEM_IMAGE_ID);
alter table MM_CATALOG_ITEM_IMAGE_T
  add constraint MM_CATALOG_ITEM_IMAGE_TC0 unique (OBJ_ID);

create table MM_CATALOG_ITEM_MARKUP_T
(
  CATALOG_ITEM_MARKUP_ID NUMBER(18) not null,
  OBJ_ID                 VARCHAR2(36) default sys_guid() not null,
  VER_NBR                NUMBER(8) default 1 not null,
  CATALOG_ITEM_ID        NUMBER(18) not null,
  MARKUP_CD              VARCHAR2(12) not null,
  ACTV_IND               VARCHAR2(1) not null,
  LAST_UPDATE_DT         TIMESTAMP(6) not null
)
;
alter table MM_CATALOG_ITEM_MARKUP_T
  add constraint MM_CATALOG_ITEM_MARKUP_TP1 primary key (CATALOG_ITEM_MARKUP_ID);
alter table MM_CATALOG_ITEM_MARKUP_T
  add constraint MM_CATALOG_ITEM_MARKUP_TC0 unique (OBJ_ID);

create table MM_CATALOG_ITEM_PENDING_T
(
  CATALOG_ITEM_PND_ID      NUMBER(18) not null,
  OBJ_ID                   VARCHAR2(36) default sys_guid() not null,
  VER_NBR                  NUMBER(8) default 1 not null,
  CATALOG_PENDING_DOC_NBR  VARCHAR2(14) not null,
  DISTRIBUTOR_NBR          VARCHAR2(30),
  MANUFACTURER_NBR         VARCHAR2(30),
  CATALOG_UNIT_OF_ISSUE_CD VARCHAR2(10) not null,
  CATALOG_PRC              NUMBER(19,4) not null,
  CATALOG_DESC             VARCHAR2(400) not null,
  RECYCLED_IND             VARCHAR2(1),
  UNSPSC_CD                VARCHAR2(10),
  SHIPPING_WGT             NUMBER(8,2),
  SHIPPING_HT              NUMBER(8,2),
  SHIPPING_WD              NUMBER(8,2),
  SHIPPING_LH              NUMBER(8,2),
  TAXABLE_IND              VARCHAR2(1),
  CATALOG_TIMESTAMP        TIMESTAMP(6),
  CATALOG_GROUP_CD         VARCHAR2(12),
  CATALOG_SUBGROUP_CD      VARCHAR2(12),
  CATALOG_IMAGE_URL        VARCHAR2(250),
  SPAID_ID                 VARCHAR2(28),
  CATALOG_SUBGROUP_DESC    VARCHAR2(80),
  CATALOG_GROUP_NM         VARCHAR2(45),
  ACTV_IND                 VARCHAR2(1) not null,
  LAST_UPDATE_DT           TIMESTAMP(6) not null
)
;
alter table MM_CATALOG_ITEM_PENDING_T
  add constraint MM_CATALOG_ITEM_PENDING_TP1 primary key (CATALOG_ITEM_PND_ID);
alter table MM_CATALOG_ITEM_PENDING_T
  add constraint MM_CATALOG_ITEM_PENDING_TC0 unique (CATALOG_PENDING_DOC_NBR, DISTRIBUTOR_NBR);

create table MM_CATALOG_ITEM_T
(
  CATALOG_ITEM_ID            NUMBER(18) not null,
  OBJ_ID                     VARCHAR2(36) default sys_guid() not null,
  VER_NBR                    NUMBER(8) default 1 not null,
  DISTRIBUTOR_NBR            VARCHAR2(30) not null,
  MANUFACTURER_NBR           VARCHAR2(30),
  CATALOG_UNIT_OF_ISSUE_CD   VARCHAR2(10) not null,
  CATALOG_PRC                NUMBER(19,4),
  CATALOG_DESC               VARCHAR2(400) not null,
  RECYCLED_IND               VARCHAR2(1),
  WILLCALL_IND               VARCHAR2(1),
  UNSPSC_CD                  VARCHAR2(10),
  SHIPPING_WGT               NUMBER(8,2),
  SHIPPING_HT                NUMBER(8,2),
  SHIPPING_WD                NUMBER(8,2),
  SHIPPING_LH                NUMBER(8,2),
  CATALOG_ID                 NUMBER(18) not null,
  STOCK_ID                   NUMBER(18),
  DISPLAYABLE_IND            VARCHAR2(1) not null,
  CATALOG_ITEM_PND_ID        NUMBER(18),
  SUBSTITUTE_DISTRIBUTOR_NBR VARCHAR2(30),
  TAXABLE_IND                VARCHAR2(1),
  SPAID_ID                   VARCHAR2(28),
  ACTV_IND                   VARCHAR2(1) not null,
  LAST_UPDATE_DT             TIMESTAMP(6) not null
)
;
alter table MM_CATALOG_ITEM_T
  add constraint MM_CATALOG_ITEM_TP1 primary key (CATALOG_ITEM_ID);
alter table MM_CATALOG_ITEM_T
  add constraint MM_CATALOG_ITEM_TC0 unique (CATALOG_ID, DISTRIBUTOR_NBR);

create table MM_CATALOG_PENDING_DOC_T
(
  FDOC_NBR                VARCHAR2(14) not null,
  OBJ_ID                  VARCHAR2(36) default sys_guid() not null,
  VER_NBR                 NUMBER(8) default 1 not null,
  CATALOG_CD              VARCHAR2(2) not null,
  CATALOG_DESC            VARCHAR2(200),
  CATALOG_BEGIN_DT        DATE,
  CATALOG_END_DT          DATE,
  PRIORITY_NBR            NUMBER(8),
  AGREEMENT_NBR           VARCHAR2(15),
  CATALOG_UPLOAD_STATUS   VARCHAR2(12),
  CATALOG_TIMESTAMP       TIMESTAMP(6),
  CATALOG_FILE            CLOB,
  BATCH_LOG               CLOB,
  LAST_UPDATE_DT          TIMESTAMP(6) not null
)
;
alter table MM_CATALOG_PENDING_DOC_T
  add constraint MM_CATALOG_PENDING_DOC_TP1 primary key (FDOC_NBR);
alter table MM_CATALOG_PENDING_DOC_T
  add constraint MM_CATALOG_PENDING_DOC_TC0 unique (OBJ_ID);

create table MM_CATALOG_SUBGROUP_ITEM_T
(
  CATALOG_SUBGROUP_ITEM_ID NUMBER(18) not null,
  OBJ_ID                   VARCHAR2(36) default sys_guid() not null,
  VER_NBR                  NUMBER(8) default 1 not null,
  CATALOG_ITEM_ID          NUMBER(18) not null,
  CATALOG_SUBGROUP_ID      NUMBER(18) not null,
  ACTV_IND                 VARCHAR2(1) not null,
  LAST_UPDATE_DT           TIMESTAMP(6) not null
)
;
alter table MM_CATALOG_SUBGROUP_ITEM_T
  add constraint MM_CATALOG_SUBGROUP_ITEM_TP1 primary key (CATALOG_SUBGROUP_ITEM_ID);
alter table MM_CATALOG_SUBGROUP_ITEM_T
  add constraint MM_CATALOG_SUBGROUP_ITEM_TC0 unique (CATALOG_ITEM_ID, CATALOG_SUBGROUP_ID);

create table MM_CATALOG_SUBGROUP_T
(
  CATALOG_SUBGROUP_ID       NUMBER(18) not null,
  OBJ_ID                    VARCHAR2(36) default sys_guid() not null,
  VER_NBR                   NUMBER(8) default 1 not null,
  CATALOG_GROUP_CD          VARCHAR2(12) not null,
  CATALOG_SUBGROUP_CD       VARCHAR2(12) not null,
  CATALOG_SUBGROUP_DESC     VARCHAR2(80) not null,
  PRIOR_CATALOG_SUBGROUP_ID NUMBER(18),
  ACTV_IND                  VARCHAR2(1) not null,
  LAST_UPDATE_DT            TIMESTAMP(6) not null
)
;
alter table MM_CATALOG_SUBGROUP_T
  add constraint MM_CATALOG_SUBGROUP_TP1 primary key (CATALOG_SUBGROUP_ID);
alter table MM_CATALOG_SUBGROUP_T
  add constraint MM_CATALOG_SUBGROUP_TC0 unique (CATALOG_GROUP_CD, CATALOG_SUBGROUP_CD);

create table MM_CATALOG_T
(
  CATALOG_ID              NUMBER(18) not null,
  OBJ_ID                  VARCHAR2(36) default sys_guid() not null,
  VER_NBR                 NUMBER(8) default 1 not null,
  CATALOG_CD              VARCHAR2(2) not null,
  CATALOG_DESC            VARCHAR2(200) not null,
  CATALOG_BEGIN_DT        DATE,
  CATALOG_END_DT          DATE,
  CATALOG_TYPE_CD         VARCHAR2(1) not null,
  PRIORITY_NBR            NUMBER(8),
  AGREEMENT_NBR           VARCHAR2(15),
  CURRENT_IND             VARCHAR2(1) not null,
  CATALOG_PENDING_DOC_NBR VARCHAR2(14),
  WAREHOUSE_CD            VARCHAR2(2),
  DEFAULT_OBJECT_CD       VARCHAR2(4),
  ACTV_IND                VARCHAR2(1) not null,
  LAST_UPDATE_DT          TIMESTAMP(6) not null
)
;
alter table MM_CATALOG_T
  add constraint MM_CATALOG_TP1 primary key (CATALOG_ID);
alter table MM_CATALOG_T
  add constraint MM_CATALOG_TC0 unique (OBJ_ID);

create table MM_CATALOG_TYPE_T
(
  CATALOG_TYPE_CD VARCHAR2(1) not null,
  OBJ_ID          VARCHAR2(36) default sys_guid() not null,
  VER_NBR         NUMBER(8) default 1 not null,
  NM              VARCHAR2(45),
  ACTV_IND        VARCHAR2(1) not null,
  LAST_UPDATE_DT  TIMESTAMP(6) not null
)
;
alter table MM_CATALOG_TYPE_T
  add constraint MM_CATALOG_TYPE_TP1 primary key (CATALOG_TYPE_CD);
alter table MM_CATALOG_TYPE_T
  add constraint MM_CATALOG_TYPE_TC0 unique (OBJ_ID);

create table MM_CHECKIN_CYLINDER_T
(
  CHECKIN_CYLINDER_ID NUMBER(18) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  CHECKIN_DETAIL_ID   NUMBER(18),
  CHECKIN_SERIAL_NBR  VARCHAR2(80) not null,
  RETURN_DETAIL_ID    NUMBER(18),
  LAST_UPDATE_DT      TIMESTAMP(6) not null
)
;
alter table MM_CHECKIN_CYLINDER_T
  add constraint MM_CHECKIN_CYLINDER_TP1 primary key (CHECKIN_CYLINDER_ID);
alter table MM_CHECKIN_CYLINDER_T
  add constraint MM_CHECKIN_CYLINDER_TC0 unique (OBJ_ID);

create table MM_CHECKIN_DETAIL_T
(
  CHECKIN_DETAIL_ID           NUMBER(18) not null,
  OBJ_ID                      VARCHAR2(36) default sys_guid() not null,
  VER_NBR                     NUMBER(8) default 1 not null,
  CHECKIN_DOC_NBR             VARCHAR2(14) not null,
  ACCEPTED_ITEM_QTY           NUMBER(19,4) not null,
  REJECTED_ITEM_QTY           NUMBER(19,4) not null,
  PO_ID                       NUMBER(9),
  ORDER_DETAIL_ID             NUMBER(18) not null,
  STOCK_ID                    NUMBER(18),
  BIN_ID                      NUMBER(18),
  RETURN_UNIT_OF_ISSUE_CD     VARCHAR2(10),
  RETURN_DETAIL_STATUS_CD     VARCHAR2(4),
  CLOSED_IND                  VARCHAR2(1),
  STOCK_PERISHABLE_DT         TIMESTAMP(6),
  CORRECTED_CHECKIN_DETAIL_ID NUMBER(18),
  LAST_UPDATE_DT              TIMESTAMP(6) not null
)
;
alter table MM_CHECKIN_DETAIL_T
  add constraint MM_CHECKIN_DETAIL_TP1 primary key (CHECKIN_DETAIL_ID);
alter table MM_CHECKIN_DETAIL_T
  add constraint MM_CHECKIN_DETAIL_TC0 unique (OBJ_ID);

create table MM_CHECKIN_DOC_T
(
  FDOC_NBR            VARCHAR2(14) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  ORDER_DOC_NBR       VARCHAR2(14) not null,
  VENDOR_REF_NBR      VARCHAR2(45),
  VENDOR_SHIPMENT_NBR VARCHAR2(45),
  WAREHOUSE_CD        VARCHAR2(2),
  LAST_UPDATE_DT      TIMESTAMP(6) not null
)
;
alter table MM_CHECKIN_DOC_T
  add constraint MM_CHECKIN_DOC_TP1 primary key (FDOC_NBR);
alter table MM_CHECKIN_DOC_T
  add constraint MM_CHECKIN_DOC_TC0 unique (OBJ_ID);

create table MM_COST_CODE_T
(
  COST_CD        VARCHAR2(2) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  NM             VARCHAR2(45),
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_COST_CODE_T
  add constraint MM_COST_CODE_TP1 primary key (COST_CD);
alter table MM_COST_CODE_T
  add constraint MM_COST_CODE_TC0 unique (OBJ_ID);

create table MM_CPTL_AST_INFO_T
(
  ORDR_DTL_ID        NUMBER(18) not null,
  OBJ_ID             VARCHAR2(36) not null,
  VER_NBR            NUMBER(8) default 1 not null,
  VNDR_HDR_GNRTD_ID  NUMBER(10),
  VNDR_DTL_ASND_ID   NUMBER(10),
  CPTLAST_NBR        NUMBER(12),
  CPTLAST_TYP_CD     VARCHAR2(7),
  CPTLAST_MFR_NM     VARCHAR2(40),
  CPTLAST_DESC       VARCHAR2(2000),
  CPTLAST_MFRMDL_NBR VARCHAR2(25),
  VNDR_NM            VARCHAR2(45)
)
;
alter table MM_CPTL_AST_INFO_T
  add constraint MM_CPTL_AST_INFO_TP1 primary key (ORDR_DTL_ID);
alter table MM_CPTL_AST_INFO_T
  add constraint MM_CPTL_AST_INFO_TC0 unique (OBJ_ID);

create table MM_CPTL_AST_INFO_DTL_T
(
  ORDR_DTL_ID          NUMBER(18) not null,
  CPTL_AST_INFO_DTL_ID NUMBER(18) not null,
  OBJ_ID               VARCHAR2(36) not null,
  VER_NBR              NUMBER(8) default 1 not null,
  CAMPUS_CD            VARCHAR2(2),
  BLDG_CD              VARCHAR2(10),
  BLDG_ROOM_NBR        VARCHAR2(8),
  BLDG_SUB_ROOM_NBR    VARCHAR2(2),
  CPTLAST_TAG_NBR      VARCHAR2(8),
  CPTLAST_SERIAL_NBR   VARCHAR2(25)
)
;
alter table MM_CPTL_AST_INFO_DTL_T
  add constraint MM_CPTL_AST_INFO_DTL_TP1 primary key (CPTL_AST_INFO_DTL_ID);
alter table MM_CPTL_AST_INFO_DTL_T
  add constraint MM_CPTL_AST_INFO_DTL_TC0 unique (OBJ_ID);

create table MM_CREDIT_MEMO_EXPECTED_T
(
  CREDIT_MEMO_EXPECTED_ID NUMBER(18) not null,
  OBJ_ID                  VARCHAR2(36) default sys_guid() not null,
  VER_NBR                 NUMBER(8) default 1 not null,
  WAREHOUSE_CD            VARCHAR2(2) not null,
  RETURN_DETAIL_ID        NUMBER(18) not null,
  CM_RECEIVED_IND         VARCHAR2(1) default 'N' not null,
  CM_EXPECTED_CREATE_DT   TIMESTAMP(6) default CURRENT_TIMESTAMP not null,
  LAST_UPDATE_DT          TIMESTAMP(6) not null
)
;
alter table MM_CREDIT_MEMO_EXPECTED_T
  add constraint MM_CREDIT_MEMO_EXPECTED_TP1 primary key (CREDIT_MEMO_EXPECTED_ID);
alter table MM_CREDIT_MEMO_EXPECTED_T
  add constraint MM_CREDIT_MEMO_EXPECTED_TC0 unique (OBJ_ID);

create table MM_CUSTOMER_FAV_DETAIL_T
(
  CUSTOMER_FAV_DETAIL_ID NUMBER(18) not null,
  OBJ_ID                 VARCHAR2(36) default sys_guid() not null,
  VER_NBR                NUMBER(8) default 1 not null,
  CUSTOMER_FAV_ID        NUMBER(18) not null,
  CATALOG_ITEM_ID        NUMBER(18) not null,
  ACTV_IND               VARCHAR2(1) not null,
  LAST_UPDATE_DT         TIMESTAMP(6) not null
)
;
alter table MM_CUSTOMER_FAV_DETAIL_T
  add constraint MM_CUSTOMER_FAV_DETAIL_TP1 primary key (CUSTOMER_FAV_DETAIL_ID);
alter table MM_CUSTOMER_FAV_DETAIL_T
  add constraint MM_CUSTOMER_FAV_DETAIL_TC0 unique (OBJ_ID);

create table MM_CUSTOMER_FAV_HEADER_T
(
  CUSTOMER_FAV_ID        NUMBER(18) not null,
  OBJ_ID                 VARCHAR2(36) default sys_guid() not null,
  VER_NBR                NUMBER(8) default 1 not null,
  PRNCPL_NM              VARCHAR2(100) not null,
  CUSTOMER_FAV_NM        VARCHAR2(40) not null,
  CUSTOMER_FAV_SHARE_IND VARCHAR2(1),
  ACTV_IND               VARCHAR2(1) not null,
  LAST_UPDATE_DT         TIMESTAMP(6) not null
)
;
alter table MM_CUSTOMER_FAV_HEADER_T
  add constraint MM_CUSTOMER_FAV_HEADER_TP1 primary key (CUSTOMER_FAV_ID);
alter table MM_CUSTOMER_FAV_HEADER_T
  add constraint MM_CUSTOMER_FAV_HEADER_TC0 unique (PRNCPL_NM, CUSTOMER_FAV_NM);

create table MM_CUSTOMER_T
(
  PRNCPL_NM         VARCHAR2(100) not null,
  OBJ_ID            VARCHAR2(36) default sys_guid() not null,
  VER_NBR           NUMBER(8) default 1 not null,
  FIRST_NM          VARCHAR2(45),
  CUSTOMER_PASSWORD VARCHAR2(200),
  LAST_NM           VARCHAR2(45),
  ACTV_IND          VARCHAR2(1) not null,
  LAST_UPDATE_DT    TIMESTAMP(6) not null
)
;
alter table MM_CUSTOMER_T
  add constraint MM_CUSTOMER_TP1 primary key (PRNCPL_NM);
alter table MM_CUSTOMER_T
  add constraint MM_CUSTOMER_TC0 unique (OBJ_ID);

create table MM_CYCLE_COUNT_T
(
  CYCLE_CNT_CD       VARCHAR2(1) not null,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  CYCLE_CNT_DESC     VARCHAR2(80),
  TIMES_PER_YEAR_NBR NUMBER(8) not null,
  TOLERANCE_AMT      NUMBER(19,4),
  ACTV_IND           VARCHAR2(1) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_CYCLE_COUNT_T
  add constraint MM_CYCLE_COUNT_TP1 primary key (CYCLE_CNT_CD);
alter table MM_CYCLE_COUNT_T
  add constraint MM_CYCLE_COUNT_TC0 unique (OBJ_ID);

create table MM_CYLINDER_GAS_T
(
  CYLINDER_GAS_CD    VARCHAR2(2) not null,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  CYLINDER_GAS_DESC  VARCHAR2(80),
  DAILY_DEMURAGE_PRC NUMBER(18,4),
  ACTV_IND           VARCHAR2(1) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_CYLINDER_GAS_T
  add constraint MM_CYLINDER_GAS_TP1 primary key (CYLINDER_GAS_CD);
alter table MM_CYLINDER_GAS_T
  add constraint MM_CYLINDER_GAS_TC0 unique (OBJ_ID);

create table MM_CYLINDER_T
(
  CYLINDER_ID         NUMBER(18) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  STOCK_ID            NUMBER(18) not null,
  CYLINDER_SERIAL_NBR VARCHAR2(80) not null,
  ISSUED_DT           DATE,
  LAST_CHARGE_DT      DATE,
  RETURN_DT           DATE,
  PICK_LIST_LINE_ID   NUMBER(18),
  CHECKIN_DETAIL_ID   NUMBER(18),
  RETURN_DETAIL_ID    NUMBER(18),
  ACTV_IND            VARCHAR2(1) not null,
  LAST_UPDATE_DT      TIMESTAMP(6) not null
)
;
alter table MM_CYLINDER_T
  add constraint MM_CYLINDER_TP1 primary key (CYLINDER_ID);
alter table MM_CYLINDER_T
  add constraint MM_CYLINDER_TC0 unique (CYLINDER_SERIAL_NBR, RETURN_DT);

create table MM_DELIVERY_REASON_T
(
  DELIVERY_REASON_CD VARCHAR2(4) not null,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  NM                 VARCHAR2(45),
  ACTV_IND           VARCHAR2(1) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_DELIVERY_REASON_T
  add constraint MM_DELIVERY_REASON_TP1 primary key (DELIVERY_REASON_CD);
alter table MM_DELIVERY_REASON_T
  add constraint MM_DELIVERY_REASON_TC0 unique (OBJ_ID);

create table MM_DISPOSITION_T
(
  DISPOSITION_CD VARCHAR2(12) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  NM             VARCHAR2(45),
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_DISPOSITION_T
  add constraint MM_DISPOSITION_TP1 primary key (DISPOSITION_CD);
alter table MM_DISPOSITION_T
  add constraint MM_DISPOSITION_TC0 unique (OBJ_ID);

create table MM_DOT_HAZARDOUS_T
(
  DOT_HAZARDOUS_CD   VARCHAR2(5) not null,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  NM                 VARCHAR2(45),
  DRIVER_MANIFEST_CD VARCHAR2(2),
  ACTV_IND           VARCHAR2(1) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_DOT_HAZARDOUS_T
  add constraint MM_DOT_HAZARDOUS_TP1 primary key (DOT_HAZARDOUS_CD);
alter table MM_DOT_HAZARDOUS_T
  add constraint MM_DOT_HAZARDOUS_TC0 unique (OBJ_ID);

create table MM_DRIVER_MANIFEST_T
(
  DRIVER_MANIFEST_CD VARCHAR2(2) not null,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  NM                 VARCHAR2(45),
  ACTV_IND           VARCHAR2(1) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_DRIVER_MANIFEST_T
  add constraint MM_DRIVER_MANIFEST_TP1 primary key (DRIVER_MANIFEST_CD);
alter table MM_DRIVER_MANIFEST_T
  add constraint MM_DRIVER_MANIFEST_TC0 unique (OBJ_ID);

create table MM_EHS_CONTAINER_T
(
  EHS_CONTAINER_CD VARCHAR2(6) not null,
  OBJ_ID           VARCHAR2(36) default sys_guid() not null,
  VER_NBR          NUMBER(8) default 1 not null,
  NM               VARCHAR2(45),
  ACTV_IND         VARCHAR2(1) not null,
  LAST_UPDATE_DT   TIMESTAMP(6) not null
)
;
alter table MM_EHS_CONTAINER_T
  add constraint MM_EHS_CONTAINER_TP1 primary key (EHS_CONTAINER_CD);
alter table MM_EHS_CONTAINER_T
  add constraint MM_EHS_CONTAINER_TC0 unique (OBJ_ID);

create table MM_EHS_HAZARDOUS_STATE_T
(
  EHS_HAZARDOUS_STATE_CD VARCHAR2(1) not null,
  OBJ_ID                 VARCHAR2(36) default sys_guid() not null,
  VER_NBR                NUMBER(8) default 1 not null,
  NM                     VARCHAR2(45),
  ACTV_IND               VARCHAR2(1) not null,
  LAST_UPDATE_DT         TIMESTAMP(6) not null
)
;
alter table MM_EHS_HAZARDOUS_STATE_T
  add constraint MM_EHS_HAZARDOUS_STATE_TP1 primary key (EHS_HAZARDOUS_STATE_CD);
alter table MM_EHS_HAZARDOUS_STATE_T
  add constraint MM_EHS_HAZARDOUS_STATE_TC0 unique (OBJ_ID);

create table MM_EHS_HAZARDOUS_T
(
  EHS_HAZARDOUS_CD VARCHAR2(3) not null,
  OBJ_ID           VARCHAR2(36) default sys_guid() not null,
  VER_NBR          NUMBER(8) default 1 not null,
  NM               VARCHAR2(45),
  ACTV_IND         VARCHAR2(1) not null,
  LAST_UPDATE_DT   TIMESTAMP(6) not null
)
;
alter table MM_EHS_HAZARDOUS_T
  add constraint MM_EHS_HAZARDOUS_TP1 primary key (EHS_HAZARDOUS_CD);
alter table MM_EHS_HAZARDOUS_T
  add constraint MM_EHS_HAZARDOUS_TC0 unique (OBJ_ID);

create table MM_FURNITURE_T
(
  FURNITURE_ID   NUMBER(18) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  DESIGN_NBR     VARCHAR2(10),
  SHOP_CART_ID   NUMBER(18),
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_FURNITURE_T
  add constraint MM_FURNITURE_TP1 primary key (FURNITURE_ID);
alter table MM_FURNITURE_T
  add constraint MM_FURNITURE_TC0 unique (OBJ_ID);

create table MM_HAZARDOUS_MATERIEL_T
(
  HAZARDOUS_MATERIEL_ID      NUMBER(18) not null,
  OBJ_ID                     VARCHAR2(36) default sys_guid() not null,
  VER_NBR                    NUMBER(8) default 1 not null,
  STOCK_ID                   NUMBER(18) not null,
  HAZARDOUS_UN_CD            VARCHAR2(12),
  EHS_HAZARDOUS_CD           VARCHAR2(3),
  EHS_CONTAINER_CD           VARCHAR2(6),
  EHS_CAS_NBR                VARCHAR2(20),
  EHS_HAZARDOUS_STATE_CD     VARCHAR2(1),
  EHS_UNIT_OF_ISSUE_CD       VARCHAR2(10),
  EHS_CONVERSION_UNIT_FACTOR NUMBER(8,5),
  DOT_HAZARDOUS_CD           VARCHAR2(5),
  TYPE_OF_USE                VARCHAR2(10),
  HAZARDOUS_PRESSURE         NUMBER(10,3),
  HAZARDOUS_TEMPERATURE      NUMBER(10,3),
  ACTV_IND                   VARCHAR2(1) not null,
  LAST_UPDATE_DT             TIMESTAMP(6) not null
)
;
alter table MM_HAZARDOUS_MATERIEL_T
  add constraint MM_HAZARDOUS_MATERIEL_TP1 primary key (HAZARDOUS_MATERIEL_ID);
alter table MM_HAZARDOUS_MATERIEL_T
  add constraint MM_HAZARDOUS_MATERIEL_TC0 unique (OBJ_ID);

create table MM_HAZARDOUS_UN_T
(
  HAZARDOUS_UN_CD       VARCHAR2(10) not null,
  OBJ_ID                VARCHAR2(36) default sys_guid() not null,
  VER_NBR               NUMBER(8) default 1 not null,
  HAZARDOUS_UN_DESC     VARCHAR2(255),
  HAZARDOUS_UN_STD_UNIT VARCHAR2(5),
  ACTV_IND              VARCHAR2(1) not null,
  LAST_UPDATE_DT        TIMESTAMP(6) not null
)
;
alter table MM_HAZARDOUS_UN_T
  add constraint MM_HAZARDOUS_UN_TP1 primary key (HAZARDOUS_UN_CD);
alter table MM_HAZARDOUS_UN_T
  add constraint MM_HAZARDOUS_UN_TC0 unique (OBJ_ID);

create table MM_MARKUP_T
(
  MARKUP_CD           VARCHAR2(12) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  MARKUP_NM           VARCHAR2(40),
  MARKUP_RT           NUMBER(8,4),
  WAREHOUSE_CD        VARCHAR2(2),
  MARKUP_COA_CD       VARCHAR2(2),
  MARKUP_ORG_CD       VARCHAR2(32),
  MARKUP_ACCOUNT_NBR  VARCHAR2(32),
  MARKUP_FIXED_AMT    NUMBER(8,2),
  MARKUP_BEGIN_DT     DATE,
  MARKUP_END_DT       DATE,
  MARKUP_TYPE_CD      VARCHAR2(2),
  MARKUP_FROM_QTY     NUMBER(8),
  MARKUP_TO_QTY       NUMBER(8),
  CATALOG_GROUP_CD    VARCHAR2(12),
  CATALOG_SUBGROUP_ID NUMBER(18),
  ACTV_IND            VARCHAR2(1) not null,
  LAST_UPDATE_DT      TIMESTAMP(6) not null
)
;
alter table MM_MARKUP_T
  add constraint MM_MARKUP_TP1 primary key (MARKUP_CD);
alter table MM_MARKUP_T
  add constraint MM_MARKUP_TC0 unique (OBJ_ID);

create table MM_MARKUP_TYPE_T
(
  MARKUP_TYPE_CD VARCHAR2(2) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  NM             VARCHAR2(45),
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_MARKUP_TYPE_T
  add constraint MM_MARKUP_TYPE_TP1 primary key (MARKUP_TYPE_CD);
alter table MM_MARKUP_TYPE_T
  add constraint MM_MARKUP_TYPE_TC0 unique (OBJ_ID);

create table MM_ORDER_DETAIL_T
(
  ORDER_DETAIL_ID            NUMBER(18) not null,
  OBJ_ID                     VARCHAR2(36) default sys_guid() not null,
  VER_NBR                    NUMBER(8) default 1 not null,
  ORDER_DOC_NBR              VARCHAR2(14) not null,
  SALES_INSTANCE_ID          NUMBER(18),
  ORDER_DETAIL_STATUS_CD     VARCHAR2(4) not null,
  SHOP_CART_DETAIL_ID        VARCHAR2(14),
  CATALOG_ITEM_ID            NUMBER(18) not null,
  ORDER_ITEM_QTY             NUMBER(11,4) not null,
  STOCK_UNIT_OF_ISSUE_CD     VARCHAR2(10) not null,
  ORDER_ITEM_COST_AMT        NUMBER(19,4) not null,
  ORDER_ITEM_PRICE_AMT       NUMBER(19,4) not null,
  ORDER_ITEM_MARKUP_AMT      NUMBER(19,4) not null,
  ORDER_ITEM_TAX_AMT         NUMBER(19,4) not null,
  ORDER_ITEM_ADDL_CST_AMT    NUMBER(19,4),
  ORDER_ITEM_DETAIL_DESC     VARCHAR2(400) not null,
  DISTRIBUTOR_NBR            VARCHAR2(30),
  SHIPPING_WGT               NUMBER(8,2),
  SHIPPING_HT                NUMBER(8,2),
  SHIPPING_WD                NUMBER(8,2),
  SHIPPING_LH                NUMBER(8,2),
  WILLCALL_IND               VARCHAR2(1),
  VENDOR_HEADER_GENERATED_ID NUMBER(10),
  VENDOR_DETAIL_ASSIGNED_ID  NUMBER(10),
  VENDOR_NM                  VARCHAR2(45),
  PO_ID                      NUMBER(9),
  ITM_LN_NBR                 NUMBER(3),
  SPAID_ID                   VARCHAR2(28),
  ADDITIONAL_COST_TYPE_CD    VARCHAR2(12),
  AGREEMENT_ORDER_ITEM_QTY   NUMBER(11,4),
  AGREEMENT_UNIT_OF_ISSUE_CD VARCHAR2(10),
  ORDER_EXPECTED_DT          TIMESTAMP(6),
  LAST_UPDATE_DT             TIMESTAMP(6) not null
)
;
alter table MM_ORDER_DETAIL_T
  add constraint MM_ORDER_DETAIL_TP1 primary key (ORDER_DETAIL_ID);
alter table MM_ORDER_DETAIL_T
  add constraint MM_ORDER_DETAIL_TC0 unique (OBJ_ID);

create table MM_ORDER_DOC_T
(
  FDOC_NBR                   VARCHAR2(14) not null,
  OBJ_ID                     VARCHAR2(36) default sys_guid() not null,
  VER_NBR                    NUMBER(8) default 1 not null,
  ORDER_ID                   NUMBER(18) not null,
  SHOP_CART_ID               NUMBER(18),
  ORDER_TYPE_CD              VARCHAR2(6) not null,
  RECURRING_ORDER_IND        VARCHAR2(1),
  RECURRING_ORDER_ID         NUMBER(18),
  VENDOR_HEADER_GENERATED_ID NUMBER(10),
  VENDOR_DETAIL_ASSIGNED_ID  NUMBER(10),
  VENDOR_NM                  VARCHAR2(45),
  CUSTOMER_PROFILE_ID        VARCHAR2(100) not null,
  ORDER_DOC_STATUS_CD        VARCHAR2(4) not null,
  WAREHOUSE_CD               VARCHAR2(2) not null,
  CAMPUS_CD                  VARCHAR2(2),
  DELIVERY_BUILDING_CD       VARCHAR2(10),
  DELIVERY_BUILDING_RM_NBR   VARCHAR2(8),
  DELIVERY_DEPARTMENT_NM     VARCHAR2(45),
  DELIVERY_INSTRUCTION_TXT   VARCHAR2(255),
  BILLING_ADDRESS_ID         VARCHAR2(36),
  SHIPPING_ADDRESS_ID        VARCHAR2(36),
  AGREEMENT_NBR              VARCHAR2(15),
  ORDER_CREATE_DT            TIMESTAMP(6),
  REQS_ID                    NUMBER(8),
  AR_ID                      NUMBER(8),
  PROFILE_TYP_CD             VARCHAR2(10),
  DEFAULT_OBJECT_CD          VARCHAR2(4),
  LAST_UPDATE_DT             TIMESTAMP(6) not null
)
;
alter table MM_ORDER_DOC_T
  add constraint MM_ORDER_DOC_TP1 primary key (FDOC_NBR);
alter table MM_ORDER_DOC_T
  add constraint MM_ORDER_DOC_TC0 unique (ORDER_ID);

create table MM_ORDER_STATUS_T
(
  ORDER_STATUS_CD VARCHAR2(4) not null,
  OBJ_ID          VARCHAR2(36) default sys_guid() not null,
  VER_NBR         NUMBER(8) default 1 not null,
  NM              VARCHAR2(45),
  ACTV_IND        VARCHAR2(1) not null,
  LAST_UPDATE_DT  TIMESTAMP(6) not null
)
;
alter table MM_ORDER_STATUS_T
  add constraint MM_ORDER_STATUS_TP1 primary key (ORDER_STATUS_CD);
alter table MM_ORDER_STATUS_T
  add constraint MM_ORDER_STATUS_TC0 unique (OBJ_ID);

create table MM_ORDER_TYPE_T
(
  ORDER_TYPE_CD  VARCHAR2(6) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  NM             VARCHAR2(45),
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_ORDER_TYPE_T
  add constraint MM_ORDER_TYPE_TP1 primary key (ORDER_TYPE_CD);
alter table MM_ORDER_TYPE_T
  add constraint MM_ORDER_TYPE_TC0 unique (OBJ_ID);

create table MM_PACK_LIST_ANNOUNCEMENT_T
(
  PACK_LIST_ANNOUNCEMENT_CD   VARCHAR2(8) not null,
  OBJ_ID                      VARCHAR2(36) default sys_guid() not null,
  VER_NBR                     NUMBER(8) default 1 not null,
  PACK_LIST_ANNOUNCEMENT_DESC VARCHAR2(200),
  ACTV_IND                    VARCHAR2(1) not null,
  LAST_UPDATE_DT              TIMESTAMP(6) not null
)
;
alter table MM_PACK_LIST_ANNOUNCEMENT_T
  add constraint MM_PACK_LIST_ANNOUNCEMENT_TP1 primary key (PACK_LIST_ANNOUNCEMENT_CD);
alter table MM_PACK_LIST_ANNOUNCEMENT_T
  add constraint MM_PACK_LIST_ANNOUNCEMENT_TC0 unique (OBJ_ID);

create table MM_PACK_LIST_DOC_T
(
  FDOC_NBR       VARCHAR2(14) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  PACK_STATUS_CD VARCHAR2(4) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_PACK_LIST_DOC_T
  add constraint MM_PACK_LIST_DOC_TP1 primary key (FDOC_NBR);
alter table MM_PACK_LIST_DOC_T
  add constraint MM_PACK_LIST_DOC_TC0 unique (OBJ_ID);

create table MM_PACK_LIST_LINE_T
(
  PACK_LIST_LINE_ID         NUMBER(18) not null,
  OBJ_ID                    VARCHAR2(36) default sys_guid() not null,
  VER_NBR                   NUMBER(8) default 1 not null,
  PACK_LIST_LINE_NBR        NUMBER(8) not null,
  PACK_LIST_DOC_NBR         VARCHAR2(14) not null,
  PICK_LIST_LINE_ID         NUMBER(18) not null,
  ROUTE_CD                  VARCHAR2(2) not null,
  DELIVERY_CAMPUS_CD        VARCHAR2(2) not null,
  DELIVERY_BUILDING_CD      VARCHAR2(10) not null,
  DELIVERY_BUILDING_RM_NBR  VARCHAR2(8),
  DELIVERY_BUILDING_NM      VARCHAR2(45),
  DELIVERY_DEPARTMENT_NM    VARCHAR2(45),
  DELIVERY_INSTRUCTION_TXT  VARCHAR2(255),
  STOP_CD                   VARCHAR2(2) not null,
  NUMBER_OF_CARTONS         NUMBER(8) not null,
  DRIVER_LOG_NBR            NUMBER(8) not null,
  DRIVER_PRNCPL_ID          VARCHAR2(100),
  DELIVERY_REASON_CD        VARCHAR2(4) not null,
  DELIVERY_DT               DATE,
  PACK_PRNCPL_ID            VARCHAR2(100),
  PACK_DT                   DATE,
  VERIFY_PRNCPL_ID          VARCHAR2(100),
  DEPARTMENT_RECEIVED_BY_NM VARCHAR2(45),
  PACK_STATUS_CD            VARCHAR2(4) not null,
  LAST_UPDATE_DT            TIMESTAMP(6) not null
)
;
alter table MM_PACK_LIST_LINE_T
  add constraint MM_PACK_LIST_LINE_TP1 primary key (PACK_LIST_LINE_ID);
alter table MM_PACK_LIST_LINE_T
  add constraint MM_PACK_LIST_LINE_TC0 unique (PACK_LIST_DOC_NBR, PACK_LIST_LINE_NBR);

create table MM_PACK_STATUS_CODE_T
(
  PACK_STATUS_CD VARCHAR2(4) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  NM             VARCHAR2(45) not null,
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_PACK_STATUS_CODE_T
  add constraint MM_PACK_STATUS_CODE_TP1 primary key (PACK_STATUS_CD);
alter table MM_PACK_STATUS_CODE_T
  add constraint MM_PACK_STATUS_CODE_TC0 unique (OBJ_ID);

create table MM_PICK_LIST_DOC_T
(
  FDOC_NBR       VARCHAR2(14) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  WAREHOUSE_CD   VARCHAR2(2) not null,
  SORT_CD        VARCHAR2(12),
  MAX_ORDERS_QTY NUMBER(8),
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_PICK_LIST_DOC_T
  add constraint MM_PICK_LIST_DOC_TP1 primary key (FDOC_NBR);
alter table MM_PICK_LIST_DOC_T
  add constraint MM_PICK_LIST_DOC_TC0 unique (OBJ_ID);

create table MM_PICK_LIST_LINE_T
(
  PICK_LIST_LINE_ID         NUMBER(18) not null,
  OBJ_ID                    VARCHAR2(36) default sys_guid() not null,
  VER_NBR                   NUMBER(8) default 1 not null,
  PICK_LIST_DOC_NBR         VARCHAR2(14),
  PICK_TICKET_NBR           NUMBER(18),
  PICK_TUB_NBR              NUMBER(8),
  SALES_INSTANCE_ID         NUMBER(8) not null,
  ORDER_DETAIL_ID           NUMBER(18) not null,
  BIN_ID                    NUMBER(18),
  STOCK_ID                  NUMBER(18) not null,
  STOCK_QTY                 NUMBER(11,4) not null,
  PICK_STOCK_QTY            NUMBER(11,4),
  BACK_ORDER_QTY            NUMBER(11,4),
  BACK_ORDER_ID             NUMBER(18),
  PICK_STATUS_CD            VARCHAR2(4),
  ROUTE_CD                  VARCHAR2(2),
  CHARGE_DOC_NBR            VARCHAR2(14),
  PICK_LIST_LINE_CREATED_DT TIMESTAMP(6) not null,
  LAST_UPDATE_DT            TIMESTAMP(6) not null
)
;
alter table MM_PICK_LIST_LINE_T
  add constraint MM_PICK_LIST_LINE_TP1 primary key (PICK_LIST_LINE_ID);
alter table MM_PICK_LIST_LINE_T
  add constraint MM_PICK_LIST_LINE_TC0 unique (OBJ_ID);

create table MM_PICK_STATUS_CODE_T
(
  PICK_STATUS_CD VARCHAR2(4) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  NM             VARCHAR2(45) not null,
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_PICK_STATUS_CODE_T
  add constraint MM_PICK_STATUS_CODE_TP1 primary key (PICK_STATUS_CD);
alter table MM_PICK_STATUS_CODE_T
  add constraint MM_PICK_STATUS_CODE_TC0 unique (OBJ_ID);

create table MM_PICK_TICKET_T
(
  PICK_TICKET_NBR   NUMBER(18) not null,
  OBJ_ID            VARCHAR2(36) default sys_guid() not null,
  VER_NBR           NUMBER(8) default 1 not null,
  NM                VARCHAR2(45),
  PICK_LIST_DOC_NBR VARCHAR2(14) not null,
  PICK_STATUS_CD    VARCHAR2(4) not null,
  FILE_NM           VARCHAR2(255),
  LAST_UPDATE_DT    TIMESTAMP(6) not null
)
;
alter table MM_PICK_TICKET_T
  add constraint MM_PICK_TICKET_TP1 primary key (PICK_TICKET_NBR);
alter table MM_PICK_TICKET_T
  add constraint MM_PICK_TICKET_TC0 unique (OBJ_ID);

create table MM_PICK_VERIFY_DOC_T
(
  FDOC_NBR        VARCHAR2(36) not null,
  OBJ_ID          VARCHAR2(36) default sys_guid() not null,
  VER_NBR         NUMBER(8) default 1 not null,
  PICK_TICKET_NBR NUMBER(18) not null,
  LAST_UPDATE_DT  TIMESTAMP(6) not null
)
;
alter table MM_PICK_VERIFY_DOC_T
  add constraint MM_PICK_VERIFY_DOC_TP1 primary key (FDOC_NBR);
alter table MM_PICK_VERIFY_DOC_T
  add constraint MM_PICK_VERIFY_DOC_TC0 unique (OBJ_ID);

create table MM_PROFILE_T
(
  PROFILE_ID               NUMBER(18) not null,
  OBJ_ID                   VARCHAR2(36) default sys_guid() not null,
  VER_NBR                  NUMBER(8) default 1 not null,
  PROFILE_NM               VARCHAR2(45) not null,
  CUSTOMER_PRNCPL_NM       VARCHAR2(100) not null,
  PROFILE_EMAIL            VARCHAR2(200),
  PROFILE_PHONE_NBR        VARCHAR2(45),
  DELIVERY_BUILDING_CD     VARCHAR2(10),
  DELIVERY_BUILDING_RM_NBR VARCHAR2(8),
  BILLING_BUILDING_CD      VARCHAR2(10),
  ORG_CD                   VARCHAR2(32),
  CAMPUS_CD                VARCHAR2(2),
  FIN_COA_CD               VARCHAR2(2),
  ACCOUNT_NBR              VARCHAR2(32),
  SUB_ACCT_NBR             VARCHAR2(5),
  PROFILE_DEFAULT_IND      VARCHAR2(1),
  PROJECT_CD               VARCHAR2(10),
  NOTIFY_IND               VARCHAR2(1),
  PERSONAL_IND             VARCHAR2(1) not null,
  ACTV_IND                 VARCHAR2(1) not null,
  LAST_UPDATE_DT           TIMESTAMP(6) not null
)
;
alter table MM_PROFILE_T
  add constraint MM_PROFILE_TP1 primary key (PROFILE_ID);
alter table MM_PROFILE_T
  add constraint MM_PROFILE_TC0 unique (CUSTOMER_PRNCPL_NM, PROFILE_NM);

create table MM_RECONCILIATION_T
(
  TRANSACTION_ID        NUMBER(18) not null,
  OBJ_ID                VARCHAR2(36) default sys_guid() not null,
  VER_NBR               NUMBER(8) default 1 not null,
  WAREHOUSE_CD          VARCHAR2(2),
  ORDER_DOC_NBR         VARCHAR2(14) not null,
  ORDER_LINE_NBR        NUMBER(8) not null,
  CATALOG_ITEM_ID       NUMBER(18),
  ITEM_UNIT_OF_ISSUE_CD VARCHAR2(10),
  ITEM_DETAIL_DESC      VARCHAR2(400),
  SHIPPED_ITEM_QTY      NUMBER(11,4),
  ITEM_PRC              NUMBER(19,4),
  ITEM_TAX_AMT          NUMBER(19,4),
  ITEM_TOTAL_AMT        NUMBER(19,4),
  RECEIVED_CD           VARCHAR2(1),
  RECEIVED_COMMENTS     VARCHAR2(300),
  CUSTOMER_ID           VARCHAR2(12),
  CHECKIN_DT            DATE,
  SHIP_DT               DATE,
  MATCH_IND             VARCHAR2(1),
  GL_PROCESS_IND        VARCHAR2(1),
  INVOICE_NBR           VARCHAR2(15),
  LAST_UPDATE_DT        TIMESTAMP(6) not null
)
;
alter table MM_RECONCILIATION_T
  add constraint MM_RECONCILIATION_TP1 primary key (TRANSACTION_ID);
alter table MM_RECONCILIATION_T
  add constraint MM_RECONCILIATION_TC0 unique (OBJ_ID);

create table MM_RECURRING_ORDER_T
(
  RECURRING_ORDER_ID NUMBER(18) not null,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  TIMES_PER_YR       NUMBER(8),
  NO_END_DATE_IND    VARCHAR2(1) not null,
  START_DT           DATE not null,
  END_DT             DATE,
  LAST_RECURRING_DT  DATE,
  ACTV_IND           VARCHAR2(1) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_RECURRING_ORDER_T
  add constraint MM_RECURRING_ORDER_TP1 primary key (RECURRING_ORDER_ID);
alter table MM_RECURRING_ORDER_T
  add constraint MM_RECURRING_ORDER_TC0 unique (OBJ_ID);

create table MM_RENTAL_T
(
  RENTAL_ID       NUMBER(18) not null,
  OBJ_ID          VARCHAR2(36) default sys_guid() not null,
  VER_NBR         NUMBER(8) default 1 not null,
  STOCK_ID        NUMBER(18) not null,
  RENTAL_NBR      VARCHAR2(80) not null,
  CHECKIN_DOC_NBR VARCHAR2(14) not null,
  ORDER_DOC_NBR   VARCHAR2(14),
  ISSUED_DT       DATE,
  LAST_CHARGE_DT  DATE,
  RETURN_DT       DATE,
  ACTV_IND        VARCHAR2(1) not null,
  LAST_UPDATE_DT  TIMESTAMP(6) not null
)
;
alter table MM_RENTAL_T
  add constraint MM_RENTAL_TP1 primary key (RENTAL_ID);
alter table MM_RENTAL_T
  add constraint MM_RENTAL_TC0 unique (STOCK_ID, RENTAL_NBR);

create table MM_RESTRICTED_ROUTE_CODE_T
(
  RESTRICTED_ROUTE_CD VARCHAR2(2) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  NM                  VARCHAR2(45) not null,
  ACTV_IND            VARCHAR2(1) not null,
  LAST_UPDATE_DT      TIMESTAMP(6) not null
)
;
alter table MM_RESTRICTED_ROUTE_CODE_T
  add constraint MM_RESTRICTED_ROUTE_CODE_TP1 primary key (RESTRICTED_ROUTE_CD);
alter table MM_RESTRICTED_ROUTE_CODE_T
  add constraint MM_RESTRICTED_ROUTE_CODE_TC0 unique (OBJ_ID);

create table MM_RETURN_DETAIL_T
(
  RETURN_DETAIL_ID           NUMBER(18) not null,
  OBJ_ID                     VARCHAR2(36) default sys_guid() not null,
  VER_NBR                    NUMBER(8) default 1 not null,
  RETURN_DOC_NBR             VARCHAR2(14) not null,
  CATALOG_ITEM_ID            NUMBER(18) not null,
  RETURN_QTY                 NUMBER(11,4) not null,
  RETURN_UNIT_OF_ISSUE_CD    VARCHAR2(10) not null,
  RETURN_ITEM_PRC            NUMBER(19,4) not null,
  RETURN_ITEM_DETAIL_DESC    VARCHAR2(400) not null,
  RETURN_PCT                 NUMBER(8) not null,
  RETURN_CREDIT_AMT          NUMBER(19,4) not null,
  VENDOR_HEADER_GENERATED_ID NUMBER(10),
  VENDOR_DETAIL_ASSIGNED_ID  NUMBER(10),
  VENDOR_NM                  VARCHAR2(45),
  ORDER_LINE_NBR             NUMBER(8),
  RETURN_DETAIL_STATUS_CD    VARCHAR2(4) not null,
  RETURN_TYPE_CD             VARCHAR2(8),
  VENDOR_CREDIT_IND          VARCHAR2(1),
  VENDOR_RESHIP_IND          VARCHAR2(1),
  VENDOR_DISPOSITION_IND     VARCHAR2(1),
  ACTION_CD                  VARCHAR2(8),
  DISPOSITION_CD             VARCHAR2(12),
  REQS_ID                    NUMBER(8),
  PO_ID                      NUMBER(9),
  ORDER_DETAIL_ID            NUMBER(18),
  CHECKIN_DETAIL_ID          NUMBER(18),
  BIN_ID                     NUMBER(18),
  DEPARTMENT_CREDIT_IND      VARCHAR2(1),
  CREDIT_DOC_NBR             VARCHAR2(14),
  LAST_UPDATE_DT             TIMESTAMP(6) not null
)
;
alter table MM_RETURN_DETAIL_T
  add constraint MM_RETURN_DETAIL_TP1 primary key (RETURN_DETAIL_ID);
alter table MM_RETURN_DETAIL_T
  add constraint MM_RETURN_DETAIL_TC0 unique (OBJ_ID);

create table MM_RETURN_DOC_T
(
  FDOC_NBR                   VARCHAR2(14) not null,
  OBJ_ID                     VARCHAR2(36) default sys_guid() not null,
  VER_NBR                    NUMBER(8) default 1 not null,
  RETURN_ORDER_ID            VARCHAR2(14) not null,
  RETURN_DOC_STATUS_CD       VARCHAR2(4) not null,
  VENDOR_HEADER_GENERATED_ID NUMBER(10),
  VENDOR_DETAIL_ASSIGNED_ID  NUMBER(10),
  VENDOR_NM                  VARCHAR2(45),
  RETRIEVAL_BUILDING_CD      VARCHAR2(10),
  RETRIEVAL_BUILDING_RM_NBR  VARCHAR2(8),
  RETRIEVAL_INSTRUCTION_TXT  VARCHAR2(255),
  BILLING_BUILDING_CD        VARCHAR2(10),
  RETURN_TYPE_CD             VARCHAR2(4) not null,
  VENDOR_CREDIT_IND          VARCHAR2(1),
  VENDOR_RESHIP_IND          VARCHAR2(1),
  VENDOR_DISPOSITION_IND     VARCHAR2(1),
  CUSTOMER_PROFILE_ID        NUMBER(18),
  VENDOR_REF_NBR             VARCHAR2(45),
  ORDER_DOC_NBR              VARCHAR2(14),
  CHECKIN_DOC_NBR            VARCHAR2(14),
  LAST_UPDATE_DT             TIMESTAMP(6) not null
)
;
alter table MM_RETURN_DOC_T
  add constraint MM_RETURN_DOC_TP1 primary key (FDOC_NBR);
alter table MM_RETURN_DOC_T
  add constraint MM_RETURN_DOC_TC0 unique (RETURN_ORDER_ID);

create table MM_RETURN_STATUS_CODE_T
(
  RETURN_STATUS_CD VARCHAR2(4) not null,
  OBJ_ID           VARCHAR2(36) default sys_guid() not null,
  VER_NBR          NUMBER(8) default 1 not null,
  NM               VARCHAR2(45),
  ACTV_IND         VARCHAR2(1) not null,
  LAST_UPDATE_DT   TIMESTAMP(6) not null
)
;
alter table MM_RETURN_STATUS_CODE_T
  add constraint MM_RETURN_STATUS_CODE_TP1 primary key (RETURN_STATUS_CD);
alter table MM_RETURN_STATUS_CODE_T
  add constraint MM_RETURN_STATUS_CODE_TC0 unique (OBJ_ID);

create table MM_RETURN_TYPE_T
(
  RETURN_TYPE_CD VARCHAR2(8) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  NM             VARCHAR2(45),
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_RETURN_TYPE_T
  add constraint MM_RETURN_TYPE_TP1 primary key (RETURN_TYPE_CD);
alter table MM_RETURN_TYPE_T
  add constraint MM_RETURN_TYPE_TC0 unique (OBJ_ID);

create table MM_ROUTE_T
(
  ROUTE_CD            VARCHAR2(2) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  NM                  VARCHAR2(45),
  DRIVER_PRNCPL_ID    VARCHAR2(100),
  DRIVER_MANIFEST_CD  VARCHAR2(2),
  RESTRICTED_ROUTE_CD VARCHAR2(2),
  ACTV_IND            VARCHAR2(1) not null,
  LAST_UPDATE_DT      TIMESTAMP(6) not null
)
;
alter table MM_ROUTE_T
  add constraint MM_ROUTE_TP1 primary key (ROUTE_CD);
alter table MM_ROUTE_T
  add constraint MM_ROUTE_TC0 unique (OBJ_ID);

create table MM_SALES_INSTANCE_T
(
  SALES_INSTANCE_ID     NUMBER(8) not null,
  OBJ_ID                VARCHAR2(36) default sys_guid() not null,
  VER_NBR               NUMBER(8) default 1 not null,
  ORDER_DOC_NBR         VARCHAR2(14) not null,
  ORDER_TYPE_CD         VARCHAR2(6) not null,
  ORDER_SALES_STATUS_CD VARCHAR2(4) not null,
  ORDER_LINE_TOTAL_AMT  NUMBER(19,2),
  ORDER_TAXABLE_AMT     NUMBER(19,2),
  ORDER_TOTAL_AMT       NUMBER(19,2),
  CUSTOMER_PROFILE_ID   NUMBER(18) not null,
  LAST_UPDATE_DT        TIMESTAMP(6) not null
)
;
alter table MM_SALES_INSTANCE_T
  add constraint MM_SALES_INSTANCE_TP1 primary key (SALES_INSTANCE_ID);
alter table MM_SALES_INSTANCE_T
  add constraint MM_SALES_INSTANCE_TC0 unique (OBJ_ID);

create table MM_SEARCH_LOG_T
(
  CUSTOMER_PRNCPL_ID     VARCHAR2(100) not null,
  OBJ_ID                 VARCHAR2(36) default sys_guid() not null,
  VER_NBR                NUMBER(8) default 1 not null,
  SEARCH_FLD             VARCHAR2(45) not null,
  STOCK_DISTRIBUTION_NBR VARCHAR2(30) not null,
  LAST_UPDATE_DT         TIMESTAMP(6) not null
)
;
alter table MM_SEARCH_LOG_T
  add constraint MM_SEARCH_LOG_TP1 primary key (CUSTOMER_PRNCPL_ID);
alter table MM_SEARCH_LOG_T
  add constraint MM_SEARCH_LOG_TC0 unique (OBJ_ID);

create table MM_SHOP_CART_DETAIL_T
(
  SHOP_CART_DETAIL_ID         NUMBER(18) not null,
  OBJ_ID                      VARCHAR2(36) default sys_guid() not null,
  VER_NBR                     NUMBER(8) default 1 not null,
  SHOP_CART_ID                NUMBER(18) not null,
  CATALOG_ITEM_ID             NUMBER(18),
  WILLCALL_IND                VARCHAR2(1),
  SHOP_CART_ITEM_QTY          NUMBER(11,4) not null,
  ITEM_UNIT_OF_ISSUE_CD       VARCHAR2(10) not null,
  SHOP_CART_ITEM_COST_AMT     NUMBER(19,4) not null,
  SHOP_CART_ITEM_PRICE_AMT    NUMBER(19,4) not null,
  SHOP_CART_ITEM_MARKUP_AMT   NUMBER(19,4) not null,
  SHOP_CART_ITEM_TAX_AMT      NUMBER(19,4) not null,
  SHOP_CART_ITEM_ADDL_CST_AMT NUMBER(19,4),
  SHOP_CART_ITEM_DETAIL_DESC  VARCHAR2(4000) not null,
  SHIPPING_WGT                NUMBER(8,2),
  SHIPPING_HT                 NUMBER(8,2),
  SHIPPING_WD                 NUMBER(8,2),
  SHIPPING_LH                 NUMBER(8,2),
  VENDOR_HEADER_GENERATED_ID  NUMBER(10),
  VENDOR_DETAIL_ASSIGNED_ID   NUMBER(10),
  VENDOR_NM                   VARCHAR2(45),
  FIN_ITM_TYP_CD              VARCHAR2(4),
  SPAID_ID                    VARCHAR2(28),
  ACTV_IND                    VARCHAR2(1) not null,
  LAST_UPDATE_DT              TIMESTAMP(6) not null
)
;
alter table MM_SHOP_CART_DETAIL_T
  add constraint MM_SHOP_CART_DETAIL_TP1 primary key (SHOP_CART_DETAIL_ID);
alter table MM_SHOP_CART_DETAIL_T
  add constraint MM_SHOP_CART_DETAIL_TC0 unique (OBJ_ID);

create table MM_SHOP_CART_DTL_ADDL_COST_T
(
  SHOP_CART_DTL_ADDL_COST_ID NUMBER(18) not null,
  OBJ_ID                     VARCHAR2(36) default sys_guid() not null,
  VER_NBR                    NUMBER(8) default 1 not null,
  SHOP_CART_DETAIL_ID        NUMBER(18) not null,
  ADDITIONAL_COST_TYPE_CD    VARCHAR2(12) not null,
  ADDITIONAL_CST             NUMBER(19,4) not null,
  LAST_UPDATE_DT             TIMESTAMP(6) not null
)
;
alter table MM_SHOP_CART_DTL_ADDL_COST_T
  add constraint MM_SHOP_CART_DTL_ADDL_COST_TP1 primary key (SHOP_CART_DTL_ADDL_COST_ID);
alter table MM_SHOP_CART_DTL_ADDL_COST_T
  add constraint MM_SHOP_CART_DTL_ADDL_COST_TC0 unique (SHOP_CART_DETAIL_ID, ADDITIONAL_COST_TYPE_CD);

create table MM_SHOP_CART_T
(
  SHOP_CART_ID             NUMBER(18) not null,
  OBJ_ID                   VARCHAR2(36) default sys_guid() not null,
  VER_NBR                  NUMBER(8) default 1 not null,
  CUSTOMER_PROFILE_ID      NUMBER(18) not null,
  SHOP_CART_HEADER_NM      VARCHAR2(40),
  BILLING_ADDRESS_ID       NUMBER(18),
  SHIPPING_ADDRESS_ID      NUMBER(18),
  DELIVERY_BUILDING_CD     VARCHAR2(10),
  DELIVERY_BUILDING_RM_NBR VARCHAR2(8),
  DELIVERY_INSTRUCTION_TXT VARCHAR2(255),
  ORDERED_IND              VARCHAR2(1) not null,
  LAST_UPDATE_DT           TIMESTAMP(6) not null
)
;
alter table MM_SHOP_CART_T
  add constraint MM_SHOP_CART_TP1 primary key (SHOP_CART_ID);
alter table MM_SHOP_CART_T
  add constraint MM_SHOP_CART_TC0 unique (OBJ_ID);

create table MM_STOCK_ATTRIBUTE_CODE_T
(
  STOCK_ATTRIBUTE_CD VARCHAR2(2) not null,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  NM                 VARCHAR2(45) not null,
  ACTV_IND           VARCHAR2(1) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_STOCK_ATTRIBUTE_CODE_T
  add constraint MM_STOCK_ATTRIBUTE_CODE_TP1 primary key (STOCK_ATTRIBUTE_CD);
alter table MM_STOCK_ATTRIBUTE_CODE_T
  add constraint MM_STOCK_ATTRIBUTE_CODE_TC0 unique (OBJ_ID);

create table MM_STOCK_ATTRIBUTE_T
(
  STOCK_ATTRIBUTE_ID NUMBER(18) not null,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  STOCK_ATTRIBUTE_CD VARCHAR2(2) not null,
  STOCK_ID           NUMBER(18) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_STOCK_ATTRIBUTE_T
  add constraint MM_STOCK_ATTRIBUTE_TP1 primary key (STOCK_ATTRIBUTE_ID);
alter table MM_STOCK_ATTRIBUTE_T
  add constraint MM_STOCK_ATTRIBUTE_TC0 unique (STOCK_ID, STOCK_ATTRIBUTE_CD);

create table MM_STOCK_BALANCE_T
(
  STOCK_BALANCE_ID    NUMBER(18) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  STOCK_ID            NUMBER(18),
  BIN_ID              NUMBER(18) not null,
  QTY_ON_HAND         NUMBER(8) not null,
  LAST_CHECKIN_DT     DATE not null,
  LAST_CNT_DT         DATE,
  STOCK_PERISHABLE_DT TIMESTAMP(6),
  LAST_UPDATE_DT      TIMESTAMP(6) not null
)
;
alter table MM_STOCK_BALANCE_T
  add constraint MM_STOCK_BALANCE_TP1 primary key (STOCK_BALANCE_ID);
alter table MM_STOCK_BALANCE_T
  add constraint MM_STOCK_BALANCE_TC0 unique (OBJ_ID);

create table MM_STOCK_COUNT_T
(
  STOCK_COUNT_ID               NUMBER(18) not null,
  OBJ_ID                       VARCHAR2(36) default sys_guid() not null,
  VER_NBR                      NUMBER(8) default 1 not null,
  STOCK_ID                     NUMBER(18) not null,
  BIN_ID                       NUMBER(18) not null,
  WORKSHEET_COUNT_DOC_NBR      VARCHAR2(14),
  WORKSHEET_PRNCPL_ID          VARCHAR2(100) not null,
  BEFORE_ITEM_QTY              NUMBER(11,4),
  BEFORE_ITEM_UNIT_OF_ISSUE_CD VARCHAR2(10) not null,
  ORIGINAL_DT                  TIMESTAMP(6) not null,
  STOCK_COUNT_ITEM_QTY         NUMBER(11,4),
  STOCK_TRANS_REASON_CD        VARCHAR2(8),
  STOCK_COUNT_NOTE             VARCHAR2(200),
  TIMES_COUNTED                NUMBER(8),
  REPRINT_IND                  VARCHAR2(1),
  LAST_UPDATE_DT               TIMESTAMP(6) not null
)
;
alter table MM_STOCK_COUNT_T
  add constraint MM_STOCK_COUNT_TP1 primary key (STOCK_COUNT_ID);
alter table MM_STOCK_COUNT_T
  add constraint MM_STOCK_COUNT_TC0 unique (STOCK_ID, BIN_ID, WORKSHEET_COUNT_DOC_NBR);

create table MM_STOCK_COST_T
(
  STOCK_COST_ID  NUMBER(18) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  STOCK_ID       NUMBER(18) not null,
  COST_CD        VARCHAR2(2) not null,
  STOCK_CST      NUMBER(19,4) not null,
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_STOCK_COST_T
  add constraint MM_STOCK_COST_TP1 primary key (STOCK_COST_ID);
alter table MM_STOCK_COST_T
  add constraint MM_STOCK_COST_TC0 unique (STOCK_ID, COST_CD);

create table MM_STOCK_HISTORY_T
(
  STOCK_HISTORY_ID              NUMBER(18) not null,
  OBJ_ID                        VARCHAR2(36) default sys_guid() not null,
  VER_NBR                       NUMBER(8) default 1 not null,
  STOCK_TRANS_REASON_CD         VARCHAR2(8) not null,
  BEFORE_STOCK_QTY              NUMBER(11,4) not null,
  BEFORE_STOCK_UNIT_OF_ISSUE_CD VARCHAR2(10) not null,
  BEFORE_STOCK_CST              NUMBER(19,4) not null,
  TRANS_STOCK_QTY               NUMBER(11,4),
  TRANS_STOCK_UNIT_OF_ISSUE_CD  VARCHAR2(10),
  TRANS_STOCK_CST               NUMBER(19,4),
  AFTER_STOCK_QTY               NUMBER(11,4) not null,
  AFTER_STOCK_UNIT_OF_ISSUE_CD  VARCHAR2(10) not null,
  AFTER_STOCK_CST               NUMBER(19,4) not null,
  HISTORY_TRANS_TIMESTAMP       TIMESTAMP(6) not null,
  CHECKIN_DOC_NBR               VARCHAR2(14),
  PICK_VERIFY_DOC_NBR           VARCHAR2(14),
  RETURN_DOC_NBR                VARCHAR2(14),
  WORKSHEET_COUNT_DOC_NBR       VARCHAR2(14),
  STOCK_COST_ID                 NUMBER(18),
  STOCK_ID                      NUMBER(18) not null,
  BIN_ID                        NUMBER(18) not null,
  RESIDUAL_TAG                  VARCHAR2(20),
  PRNCPL_NM                     VARCHAR2(100),
  LAST_UPDATE_DT                TIMESTAMP(6) not null
)
;
alter table MM_STOCK_HISTORY_T
  add constraint MM_STOCK_HISTORY_TP1 primary key (STOCK_HISTORY_ID);
alter table MM_STOCK_HISTORY_T
  add constraint MM_STOCK_HISTORY_TC0 unique (OBJ_ID);

create table MM_STOCK_PACK_NOTE_T
(
  STOCK_PACK_NOTE_ID        NUMBER(18) not null,
  OBJ_ID                    VARCHAR2(36) default sys_guid() not null,
  VER_NBR                   NUMBER(8) default 1 not null,
  PACK_LIST_ANNOUNCEMENT_CD VARCHAR2(8) not null,
  STOCK_ID                  NUMBER(18) not null,
  ACTV_IND                  VARCHAR2(1) not null,
  LAST_UPDATE_DT            TIMESTAMP(6) not null
)
;
alter table MM_STOCK_PACK_NOTE_T
  add constraint MM_STOCK_PACK_NOTE_TP1 primary key (STOCK_PACK_NOTE_ID);
alter table MM_STOCK_PACK_NOTE_T
  add constraint MM_STOCK_PACK_NOTE_TC0 unique (OBJ_ID);

create table MM_STOCK_T
(
  STOCK_ID                  NUMBER(18) not null,
  OBJ_ID                    VARCHAR2(36) default sys_guid() not null,
  VER_NBR                   NUMBER(8) default 1 not null,
  STOCK_DISTRIBUTOR_NBR     VARCHAR2(30) not null,
  STOCK_DESC                VARCHAR2(400) not null,
  ACTV_IND                  VARCHAR2(1),
  TAXABLE_IND               VARCHAR2(1),
  SURCHARGE_IND             VARCHAR2(1),
  RECYCLED_IND              VARCHAR2(1),
  PERISHABLE_IND            VARCHAR2(1),
  OBSOLETE_IND              VARCHAR2(1),
  WILLCALL_IND              VARCHAR2(1),
  STOCK_TYPE_CD             VARCHAR2(2),
  RESTRICTED_ROUTE_CD       VARCHAR2(2),
  MANUFACTURER_NBR          VARCHAR2(30),
  AGREEMENT_DISTRIBUTOR_NBR VARCHAR2(30),
  CYCLE_CNT_CD              VARCHAR2(1),
  SALES_UNIT_OF_ISSUE_CD    VARCHAR2(10),
  SALES_UNIT_OF_ISSUE_RT    NUMBER(8,5),
  BUY_UNIT_OF_ISSUE_CD      VARCHAR2(10),
  BUY_UNIT_OF_ISSUE_RT      NUMBER(8,5),
  UPC_CD                    VARCHAR2(30),
  PACKAGING_UNIT_DESC       VARCHAR2(80),
  SHIPPING_WGT              NUMBER(8,2),
  SHIPPING_HT               NUMBER(8,2),
  SHIPPING_WD               NUMBER(8,2),
  SHIPPING_LH               NUMBER(8,2),
  REORDER_POINT_QTY         NUMBER(8,2),
  SAFETY_STOCK_QTY          NUMBER(8,2),
  SAFETY_STOCK_DAYS         NUMBER(8),
  MINIMUM_ORDER_QTY         NUMBER(8,2),
  STOCK_AGREEMENT_NBR       VARCHAR2(15),
  CYLINDER_GAS_CD           VARCHAR2(2),
  SOLE_SOURCE_IND           VARCHAR2(1),
  MAXIMUM_ORDER_QTY         NUMBER(8,2),
  STOCK_CREATE_DT           TIMESTAMP(6),
  REORDER_REMOVE_UNTIL_DT   DATE,
  LAST_CHANGE_ACTV_IND_DT   TIMESTAMP(6),
  LAST_UPDATE_DT            TIMESTAMP(6) not null
)
;
alter table MM_STOCK_T
  add constraint MM_STOCK_TP1 primary key (STOCK_ID);
alter table MM_STOCK_T
  add constraint MM_STOCK_TC0 unique (STOCK_DISTRIBUTOR_NBR);

create table MM_STOCK_TRANS_REASON_T
(
  STOCK_TRANS_REASON_CD   VARCHAR2(8) not null,
  OBJ_ID                  VARCHAR2(36) default sys_guid() not null,
  VER_NBR                 NUMBER(8) default 1 not null,
  NM                      VARCHAR2(45),
  INCREMENT_DECREMENT_IND VARCHAR2(1),
  OBJECT_TYPE_CD          VARCHAR2(4),
  ACTV_IND                VARCHAR2(1) not null,
  LAST_UPDATE_DT          TIMESTAMP(6) not null
)
;
alter table MM_STOCK_TRANS_REASON_T
  add constraint MM_STOCK_TRANS_REASON_TP1 primary key (STOCK_TRANS_REASON_CD);
alter table MM_STOCK_TRANS_REASON_T
  add constraint MM_STOCK_TRANS_REASON_TC0 unique (OBJ_ID);

create table MM_STOCK_TYPE_T
(
  STOCK_TYPE_CD  VARCHAR2(2) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  NM             VARCHAR2(45),
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_STOCK_TYPE_T
  add constraint MM_STOCK_TYPE_TP1 primary key (STOCK_TYPE_CD);
alter table MM_STOCK_TYPE_T
  add constraint MM_STOCK_TYPE_TC0 unique (OBJ_ID);

create table MM_UNIT_OF_ISSUE_T
(
  UNIT_OF_ISSUE_CD   VARCHAR2(10) not null,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  UNIT_OF_ISSUE_DESC VARCHAR2(40),
  ACTV_IND           VARCHAR2(1) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_UNIT_OF_ISSUE_T
  add constraint MM_UNIT_OF_ISSUE_TP1 primary key (UNIT_OF_ISSUE_CD);
alter table MM_UNIT_OF_ISSUE_T
  add constraint MM_UNIT_OF_ISSUE_TC0 unique (OBJ_ID);

create table MM_UNSPSC_T
(
  UNSPSC_CD          VARCHAR2(12) NOT NULL,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  SEGMENT            VARCHAR2(2) NOT NULL,
  SEGMENT_TITLE      VARCHAR2(256),
  FAMILY             VARCHAR2(4),
  FAMILY_TITLE       VARCHAR2(256),
  CLASS              VARCHAR2(6),
  CLASS_TITLE        VARCHAR2(256),
  COMMODITY          VARCHAR2(8),
  COMMODITY_TITLE    VARCHAR2(256),
  KEY                VARCHAR2(6) NOT NULL,
  CODE               VARCHAR2(8) NOT NULL,
  TITLE              VARCHAR2(256),
  ACTV_IND           VARCHAR2(1) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_UNSPSC_T
  add constraint MM_UNSPSC_TP1 primary key (UNSPSC_CD);
alter table MM_UNSPSC_T
  add constraint MM_UNSPSC_TC0 unique (OBJ_ID);

create table MM_WAREHOUSE_ACCOUNTS_T
(
  WAREHOUSE_ACCOUNT_ID      NUMBER(18) not null,
  OBJ_ID                    VARCHAR2(36) default sys_guid() not null,
  VER_NBR                   NUMBER(8) default 1 not null,
  WAREHOUSE_CD              VARCHAR2(2) not null,
  WAREHOUSE_ACCOUNT_TYPE_CD VARCHAR2(2) not null,
  FIN_COA_CD                VARCHAR2(2) not null,
  ACCOUNT_NBR               VARCHAR2(32) not null,
  SUB_ACCT_NBR              VARCHAR2(5),
  FIN_OBJECT_CD             VARCHAR2(4),
  FIN_SUB_OBJ_CD            VARCHAR2(6),
  OFFSET_OBJECT_CD          VARCHAR2(4),
  OFFSET_SUB_OBJ_CD         VARCHAR2(6),
  PROJECT_CD                VARCHAR2(10),
  ACTV_IND                  VARCHAR2(1) not null,
  LAST_UPDATE_DT            TIMESTAMP(6) not null
)
;
alter table MM_WAREHOUSE_ACCOUNTS_T
  add constraint MM_WAREHOUSE_ACCOUNTS_TP1 primary key (WAREHOUSE_ACCOUNT_ID);
alter table MM_WAREHOUSE_ACCOUNTS_T
  add constraint MM_WAREHOUSE_ACCOUNTS_TC0 unique (WAREHOUSE_CD, WAREHOUSE_ACCOUNT_TYPE_CD);

create table MM_WAREHOUSE_ACCOUNT_TYPE_T
(
  WAREHOUSE_ACCOUNT_TYPE_CD VARCHAR2(2) not null,
  OBJ_ID                    VARCHAR2(36) default sys_guid() not null,
  VER_NBR                   NUMBER(8) default 1 not null,
  NM                        VARCHAR2(45),
  ACTV_IND                  VARCHAR2(1) not null,
  LAST_UPDATE_DT            TIMESTAMP(6) not null
)
;
alter table MM_WAREHOUSE_ACCOUNT_TYPE_T
  add constraint MM_WAREHOUSE_ACCOUNT_TYPE_TP1 primary key (WAREHOUSE_ACCOUNT_TYPE_CD);
alter table MM_WAREHOUSE_ACCOUNT_TYPE_T
  add constraint MM_WAREHOUSE_ACCOUNT_TC0 unique (OBJ_ID);

create table MM_WAREHOUSE_OBJECT_T
(
  WAREHOUSE_OBJECT_ID      NUMBER(18) not null,
  OBJ_ID                   VARCHAR2(36) default sys_guid() not null,
  VER_NBR                  NUMBER(8) default 1 not null,
  WAREHOUSE_CD             VARCHAR2(2) not null,
  WAREHOUSE_OBJECT_TYPE_CD VARCHAR2(4) not null,
  FIN_COA_CD               VARCHAR2(2) not null,
  FIN_OBJECT_CD            VARCHAR2(4),
  FIN_SUB_OBJ_CD           VARCHAR2(6),
  OFFSET_OBJECT_CD         VARCHAR2(4),
  OFFSET_SUB_OBJ_CD        VARCHAR2(6),
  ACTV_IND                 VARCHAR2(1) not null,
  LAST_UPDATE_DT           TIMESTAMP(6) not null
)
;
alter table MM_WAREHOUSE_OBJECT_T
  add constraint MM_WAREHOUSE_OBJECT_TP1 primary key (WAREHOUSE_OBJECT_ID);
alter table MM_WAREHOUSE_OBJECT_T
  add constraint MM_WAREHOUSE_OBJECT_TC0 unique (WAREHOUSE_CD, WAREHOUSE_OBJECT_TYPE_CD);

create table MM_WAREHOUSE_OBJECT_TYPE_T
(
  OBJECT_TYPE_CD VARCHAR2(4) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  NM             VARCHAR2(45),
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_WAREHOUSE_OBJECT_TYPE_T
  add constraint MM_WAREHOUSE_OBJECT_TYPE_TP1 primary key (OBJECT_TYPE_CD);
alter table MM_WAREHOUSE_OBJECT_TYPE_T
  add constraint MM_WAREHOUSE_OBJECT_TYPE_TC0 unique (OBJ_ID);

create table MM_WAREHOUSE_T
(
  WAREHOUSE_CD       VARCHAR2(2) not null,
  OBJ_ID             VARCHAR2(36) default sys_guid() not null,
  VER_NBR            NUMBER(8) default 1 not null,
  WAREHOUSE_NME      VARCHAR2(60),
  ADDRESS_CD         VARCHAR2(10),
  CONSOLIDATION_CD   VARCHAR2(10),
  DEFAULT_MARKUP_CD  VARCHAR2(12),
  BUYOUT_IND         VARCHAR2(1),
  RESALE_PERMIT_NBR  VARCHAR2(20),
  BILLING_PROFILE_ID NUMBER(18),
  DEFAULT_FIN_COA_CD VARCHAR2(2),
  DEFAULT_ORG_CD     VARCHAR2(32),
  ACTV_IND           VARCHAR2(1) not null,
  LAST_UPDATE_DT     TIMESTAMP(6) not null
)
;
alter table MM_WAREHOUSE_T
  add constraint MM_WAREHOUSE_TP1 primary key (WAREHOUSE_CD);
alter table MM_WAREHOUSE_T
  add constraint MM_WAREHOUSE_TC0 unique (OBJ_ID);

create table MM_WORKSHEET_COUNTER_T
(
  WORKSHEET_COUNTER_ID     NUMBER(18) not null,
  OBJ_ID                   VARCHAR2(36) default sys_guid() not null,
  VER_NBR                  NUMBER(8) default 1 not null,
  WORKSHEET_COUNT_DOC_NBR  VARCHAR2(14) not null,
  WORKSHEET_CNTR_PRNCPL_ID VARCHAR2(100) not null,
  LAST_UPDATE_DT           TIMESTAMP(6) not null
)
;
alter table MM_WORKSHEET_COUNTER_T
  add constraint MM_WORKSHEET_COUNTER_TP1 primary key (WORKSHEET_COUNTER_ID);
alter table MM_WORKSHEET_COUNTER_T
  add constraint MM_WORKSHEET_COUNTER_TC0 unique (WORKSHEET_COUNT_DOC_NBR, WORKSHEET_CNTR_PRNCPL_ID);

create table MM_WORKSHEET_COUNT_DOC_T
(
  FDOC_NBR                VARCHAR2(14) not null,
  OBJ_ID                  VARCHAR2(36) default sys_guid() not null,
  VER_NBR                 NUMBER(8) default 1 not null,
  WORKSHEET_CNT           NUMBER(8) not null,
  WORKSHEET_ORIGINAL_DT   TIMESTAMP(6),
  WORKSHEET_COMPLETION_DT TIMESTAMP(6),
  WORKSHEET_STATUS_CD     VARCHAR2(4),
  WAREHOUSE_CD            VARCHAR2(2),
  PARENT_FDOC_NBR         VARCHAR2(14),
  CYCLE_CNT_CD            VARCHAR2(1),
  LAST_UPDATE_DT          TIMESTAMP(6) not null
)
;
alter table MM_WORKSHEET_COUNT_DOC_T
  add constraint MM_WORKSHEET_COUNT_DOC_TP1 primary key (FDOC_NBR);
alter table MM_WORKSHEET_COUNT_DOC_T
  add constraint MM_WORKSHEET_COUNT_DOC_TC0 unique (OBJ_ID);

create table MM_WORKSHEET_STATUS_T
(
  WORKSHEET_STATUS_CD VARCHAR2(4) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  NM                  VARCHAR2(40),
  ACTV_IND            VARCHAR2(1) not null,
  LAST_UPDATE_DT      TIMESTAMP(6) not null
)
;
alter table MM_WORKSHEET_STATUS_T
  add constraint MM_WORKSHEET_STATUS_TP1 primary key (WORKSHEET_STATUS_CD);
alter table MM_WORKSHEET_STATUS_T
  add constraint MM_WORKSHEET_STATUS_TC0 unique (OBJ_ID);

create table MM_XML_INVOICE_T
(
  KEY_ID         VARCHAR2(100) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  CATALOG_CD     VARCHAR2(12) not null,
  ORDER_ID       NUMBER(8) not null,
  INVOICE_NBR    VARCHAR2(15),
  PROCESSED_IND  VARCHAR2(1),
  INVOICE_XML    XMLTYPE,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_XML_INVOICE_T
  add constraint MM_XML_INVOICE_TP1 primary key (KEY_ID);
alter table MM_XML_INVOICE_T
  add constraint MM_XML_INVOICE_TC0 unique (OBJ_ID);

create table MM_XML_PURCHASE_ORDER_T
(
  XML_PURCHASE_ORDER_ID NUMBER(18) not null,
  OBJ_ID                VARCHAR2(36) default sys_guid() not null,
  VER_NBR               NUMBER(8) default 1 not null,
  KEY_ID                VARCHAR2(100) not null,
  ORDER_ID              NUMBER(8) not null,
  CUSTOMER_ID           VARCHAR2(12) not null,
  PURCHASE_ORDER_URL    VARCHAR2(250),
  PURCHASE_ORDER_XML    XMLTYPE,
  LAST_UPDATE_DT        TIMESTAMP(6) not null
)
;
alter table MM_XML_PURCHASE_ORDER_T
  add constraint MM_XML_PURCHASE_ORDER_TP1 primary key (XML_PURCHASE_ORDER_ID);
alter table MM_XML_PURCHASE_ORDER_T
  add constraint MM_XML_PURCHASE_ORDER_TC0 unique (OBJ_ID);

create table MM_XML_PURCHASE_REQUEST_T
(
  XML_PURCHASE_REQUEST_ID NUMBER(18) not null,
  OBJ_ID                  VARCHAR2(36) default sys_guid() not null,
  VER_NBR                 NUMBER(8) default 1 not null,
  KEY_ID                  VARCHAR2(100) not null,
  ORDER_ID                NUMBER(8) not null,
  CUSTOMER_ID             VARCHAR2(12) not null,
  SUPPLIER_AUXILARY_ID    VARCHAR2(28),
  PURCHASE_REQUEST_XML    XMLTYPE,
  LAST_UPDATE_DT          TIMESTAMP(6) not null
)
;
alter table MM_XML_PURCHASE_REQUEST_T
  add constraint MM_XML_PURCHASE_REQUEST_TP1 primary key (XML_PURCHASE_REQUEST_ID);
alter table MM_XML_PURCHASE_REQUEST_T
  add constraint MM_XML_PURCHASE_REQUEST_TC0 unique (OBJ_ID);

create table MM_ZONE_T
(
  ZONE_ID        NUMBER(18) not null,
  OBJ_ID         VARCHAR2(36) default sys_guid() not null,
  VER_NBR        NUMBER(8) default 1 not null,
  ZONE_CD        VARCHAR2(2) not null,
  ZONE_DESC      VARCHAR2(40),
  WAREHOUSE_CD   VARCHAR2(2) not null,
  ACTV_IND       VARCHAR2(1) not null,
  LAST_UPDATE_DT TIMESTAMP(6) not null
)
;
alter table MM_ZONE_T
  add constraint MM_ZONE_TP1 primary key (ZONE_ID);
alter table MM_ZONE_T
  add constraint MM_ZONE_TC0 unique (WAREHOUSE_CD, ZONE_CD);

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
