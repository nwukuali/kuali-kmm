-- Changes beginning after   2/26/2010

--Agreement change 3/2/2010

alter table MM_AGREEMENT_T
  add VENDOR_HEADER_GENERATED_ID NUMBER(10) null;

alter table MM_AGREEMENT_T
  add VENDOR_DETAIL_ASSIGNED_ID  NUMBER(10) null;

--Batch Control change 3/5/2010

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

--Cylinder change 3/9/2010

alter table MM_CYLINDER_T
  add RETURN_DETAIL_ID NUMBER(18) null;

alter table MM_CYLINDER_T
  add constraint MM_CYLINDER_TR4 foreign key (RETURN_DETAIL_ID)
  references MM_RETURN_DETAIL_T (RETURN_DETAIL_ID);

--Order create date change 3/12/2010

alter table MM_ORDER_DOC_T
  add ORDER_CREATE_DT TIMESTAMP null;

--Cycle Count tolerance change 3/15/2010

alter table MM_CYCLE_COUNT_T
  add TOLERANCE_AMT        NUMBER(19,4) null;

--Agreement eInvoice change 3/16/2010

alter table MM_AGREEMENT_T
  add E_INVOICE_IND   VARCHAR2(1) null;

--Pick List charges 3/16/2010

alter table MM_PICK_LIST_LINE_T
  add CHARGE_DOC_NBR   VARCHAR2(14) null;

--Address charges 3/16/2010

alter table MM_ADDRESS_T
  modify ADDRESS_LN2   NULL; 
    

--Profile changes 3/19/2010

alter table MM_PROFILE_T
  add PERSONAL_IND   VARCHAR2(1) not null;
    
--Agreement change 3/22/2010

alter table MM_AGREEMENT_T
  rename column VENDOR_HEADER_GENERATED_ID to VNDR_HDR_GNRTD_ID;

alter table MM_AGREEMENT_T
  rename column VENDOR_DETAIL_ASSIGNED_ID to VNDR_DTL_ASND_ID;

--Prncpl ID to Prncpl NM  3/22/2010

alter table MM_CUSTOMER_FAV_HEADER_T
  drop constraint MM_CUSTOMER_FAV_HEADER_TR1;

alter table MM_PROFILE_T
  drop constraint MM_PROFILE_TR1;

alter table MM_CUSTOMER_T
  drop constraint MM_CUSTOMER_TP1;

alter table MM_CUSTOMER_T
  rename column PRNCPL_ID to PRNCPL_NM;

alter table MM_CUSTOMER_FAV_HEADER_T
  rename column PRNCPL_ID to PRNCPL_NM;

alter table MM_PROFILE_T
  rename column CUSTOMER_PRNCPL_ID to CUSTOMER_PRNCPL_NM;

alter table MM_CUSTOMER_T
  add constraint MM_CUSTOMER_TP1 primary key (PRNCPL_NM);

alter table MM_CUSTOMER_FAV_HEADER_T
  add constraint MM_CUSTOMER_FAV_HEADER_TR1 foreign key (PRNCPL_NM)
  references MM_CUSTOMER_T (PRNCPL_NM);

alter table MM_PROFILE_T
  add constraint MM_PROFILE_TR1 foreign key (CUSTOMER_PRNCPL_NM)
  references MM_CUSTOMER_T (PRNCPL_NM);

-- Temporary Credit Memo table  3/22/2010

create table MM_CREDIT_MEMO_EXPECTED_T
(
  CREDIT_MEMO_EXPECTED_ID  NUMBER(18) not null,
  OBJ_ID                   VARCHAR2(36) default sys_guid() not null,
  VER_NBR                  NUMBER(8) default 1 not null,
  WAREHOUSE_CD             VARCHAR2(2) null,
  RETURN_DETAIL_ID         NUMBER(18) null,
  CM_RECEIVED_IND          VARCHAR2(1) not null,
  CM_EXPECTED_CREATE_DT    TIMESTAMP(6) not null,
  LAST_UPDATE_DT           TIMESTAMP(6) not null
);
alter table MM_CREDIT_MEMO_EXPECTED_T
  add constraint MM_CREDIT_MEMO_EXPECTED_TP1 primary key (CREDIT_MEMO_EXPECTED_ID);
alter table MM_CREDIT_MEMO_EXPECTED_T
  add constraint MM_CREDIT_MEMO_EXPECTED_TC0 unique (OBJ_ID);

create sequence MM_CREDIT_MEMO_EXPECTED_S 
  minvalue 1
  maxvalue 999999999999999999
  start with 1
  increment by 1
  nocache
  order;

--Capital Asset Info 3/22/2010

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
);

alter table MM_CPTL_AST_INFO_T
  add constraint MM_CPTL_AST_INFO_TP1 primary key (ORDR_DTL_ID);
alter table MM_CPTL_AST_INFO_T
  add constraint MM_CPTL_AST_INFO_TC0 unique (OBJ_ID);
  

create table MM_CPTL_AST_INFO_DTL_T
(
  ORDR_DTL_ID        NUMBER(18) not null,
  ITM_LN_ID          NUMBER(3) not null,
  OBJ_ID             VARCHAR2(36) not null,
  VER_NBR            NUMBER(8) default 1 not null,
  CAMPUS_CD          VARCHAR2(2),
  BLDG_CD            VARCHAR2(10),
  BLDG_ROOM_NBR      VARCHAR2(8),
  BLDG_SUB_ROOM_NBR  VARCHAR2(2),
  CPTLAST_TAG_NBR    VARCHAR2(8),
  CPTLAST_SERIAL_NBR VARCHAR2(25)
);

alter table MM_CPTL_AST_INFO_DTL_T
  add constraint MM_CPTL_AST_INFO_DTL_TP1 primary key (ORDR_DTL_ID, ITM_LN_ID);
alter table MM_CPTL_AST_INFO_DTL_T
  add constraint MM_CPTL_AST_INFO_DTL_TC0 unique (OBJ_ID);
alter table MM_CPTL_AST_INFO_DTL_T
  add constraint MM_CPTL_AST_INFO_DTL_TR1 foreign key (ORDR_DTL_ID)
  references MM_CPTL_AST_INFO_T (ORDR_DTL_ID);
  
drop table MM_CAPITAL_ASSET_T;

-- Correct Building Code 3/22/2010

alter table MM_ORDER_DOC_T
  modify   DELIVERY_BUILDING_CD     VARCHAR2(10);
  
alter table MM_PACK_LIST_LINE_T
  modify   DELIVERY_BUILDING_CD     VARCHAR2(10);

alter table MM_PROFILE_T
  modify   DELIVERY_BUILDING_CD     VARCHAR2(10);

alter table MM_RETURN_DOC_T
  modify RETRIEVAL_BUILDING_CD      VARCHAR2(10);

alter table MM_RETURN_DOC_T
  modify BILLING_BUILDING_CD        VARCHAR2(10);

alter table MM_SHOP_CART_T
  modify   DELIVERY_BUILDING_CD     VARCHAR2(10);

---Capital Asset Info Detail modify 3/22/2010
alter table MM_CPTL_AST_INFO_DTL_T
      drop primary key;

alter table MM_CPTL_AST_INFO_DTL_T
      rename column ITM_LN_ID to CPTL_AST_INFO_DTL_ID;
      
alter table MM_CPTL_AST_INFO_DTL_T
      modify CPTL_AST_INFO_DTL_ID NUMBER(18);

alter table MM_CPTL_AST_INFO_DTL_T
  add constraint MM_CPTL_AST_INFO_DTL_TP1 primary key (CPTL_AST_INFO_DTL_ID);
      
create sequence MM_CPTL_AST_INFO_DTL_S 
  minvalue 1
  maxvalue 999999999999999999
  start with 1
  increment by 1
  nocache
  order;

-- Correct Temporary Credit Memo table  3/23/2010

alter table MM_CREDIT_MEMO_EXPECTED_T
  drop constraint MM_CREDIT_MEMO_EXPECTED_TP1;
alter table MM_CREDIT_MEMO_EXPECTED_T
  drop constraint MM_CREDIT_MEMO_EXPECTED_TC0;

drop table MM_CREDIT_MEMO_EXPECTED_T;

create table MM_CREDIT_MEMO_EXPECTED_T
(
  CREDIT_MEMO_EXPECTED_ID  NUMBER(18) not null,
  OBJ_ID                   VARCHAR2(36) default sys_guid() not null,
  VER_NBR                  NUMBER(8) default 1 not null,
  WAREHOUSE_CD             VARCHAR2(2) not null,
  RETURN_DETAIL_ID         NUMBER(18) not null,
  CM_RECEIVED_IND          VARCHAR2(1) default 'N' not null,
  CM_EXPECTED_CREATE_DT    TIMESTAMP(6) default CURRENT_TIMESTAMP not null,
  LAST_UPDATE_DT           TIMESTAMP(6) not null
)
;
alter table MM_CREDIT_MEMO_EXPECTED_T
  add constraint MM_CREDIT_MEMO_EXPECTED_TP1 primary key (CREDIT_MEMO_EXPECTED_ID);
alter table MM_CREDIT_MEMO_EXPECTED_T
  add constraint MM_CREDIT_MEMO_EXPECTED_TC0 unique (OBJ_ID);

-- Add User ID to Stock History table  3/23/2010

alter table MM_STOCK_HISTORY_T 
  add PRNCPL_NM     VARCHAR2(100) NULL;

-- Add password to Customer table  3/23/2010

alter table MM_CUSTOMER_T 
  add CUSTOMER_PASSWORD     VARCHAR2(200) NULL;

-- Change Last Checkin Date to not allow nulls  3/24/2010

update MM_STOCK_BALANCE_T
   set LAST_CHECKIN_DT = LAST_UPDATE_DT;
   
commit;

alter table MM_STOCK_BALANCE_T
  modify LAST_CHECKIN_DT not null;

-- Missed this Building Code    3/24/2010

alter table MM_PROFILE_T
  modify   BILLING_BUILDING_CD     VARCHAR2(10);

-- Remove customer favorite line number and change unique constraint to OBJ_ID 3/24/2010 

alter table MM_CUSTOMER_FAV_DETAIL_T
      drop constraint MM_CUSTOMER_FAV_DETAIL_TC0;
      
alter table MM_CUSTOMER_FAV_DETAIL_T
      drop column CUSTOMER_FAV_LINE_NBR;
      
alter table MM_CUSTOMER_FAV_DETAIL_T
      add constraint MM_CUSTOMER_FAV_DETAIL_TC0 unique (OBJ_ID);
      
-- split customer_nm field into first and last name  3/25/2010

alter table MM_CUSTOMER_T
      rename column NM to FIRST_NM;
      
alter table MM_CUSTOMER_T
      add LAST_NM varchar2(45);

-- Add Credit Doc Nbr to Returns     3/26/2010
      
alter table MM_RETURN_DETAIL_T
   add CREDIT_DOC_NBR     VARCHAR2(14);
   
-- Add default chart and org to warehouse   3/30/2010

alter table MM_WAREHOUSE_T
  add DEFAULT_FIN_COA_CD     VARCHAR2(2) null;

alter table MM_WAREHOUSE_T
  add DEFAULT_ORG_CD         VARCHAR2(4) null;
  
-- Add Carousel Log Table      4/1/2010
  
create table MM_CAROUSEL_LOG_T 
(
   CAROUSEL_LINE_ID      NUMBER(18) not null,
   CAROUSEL_LINE_TYPE_CD varchar2(3) not null,
   OBJ_ID                VARCHAR2(36) default sys_guid() not null,
   VER_NBR               NUMBER(8) default 1 not null,
   LAST_UPDATE_DT        TIMESTAMP(6) not null
 );
 
alter table MM_CAROUSEL_LOG_T
   add constraint CAROUSEL_LOG_TP1 primary key(CAROUSEL_LINE_ID, CAROUSEL_LINE_TYPE_CD);
       
alter table MM_CAROUSEL_LOG_T
   add constraint CAROUSEL_LOG_TC0 unique(OBJ_ID);       

-- Fix markup rate      4/7/2010
  
alter table MM_MARKUP_T
  modify MARKUP_RT   NUMBER(8,4);

-- Fix return to profile relationship      4/8/2010
  
alter table MM_RETURN_DOC_T
  modify CUSTOMER_PROFILE_ID      NULL;

-- Fix Order Detail for KFS mapping      4/13/2010
  
alter table MM_ORDER_DETAIL_T
  add ITM_LN_NBR   NUMBER(3);

-- Fix markup for groups & subgroups  4/20/2010
  
alter table MM_MARKUP_T
  drop column MARKUP_DESC;

alter table MM_MARKUP_T
  add CATALOG_GROUP_CD     VARCHAR2(12);

alter table MM_MARKUP_T
  add CATALOG_SUBGROUP_ID    NUMBER(18);

alter table MM_MARKUP_T
  add constraint MM_MARKUP_TR3 foreign key (CATALOG_GROUP_CD)
  references MM_CATALOG_GROUP_T (CATALOG_GROUP_CD);

alter table MM_MARKUP_T
  add constraint MM_MARKUP_TR4 foreign key (CATALOG_SUBGROUP_ID)
  references MM_CATALOG_SUBGROUP_T (CATALOG_SUBGROUP_ID);

-- UNSPSC Table/Sequence Information  4/23/2010
 DROP TABLE MM_UNSPSC_T
/
CREATE TABLE mm_unspsc_t
    (unspsc_cd                     VARCHAR2(12) NOT NULL,
    obj_id                         VARCHAR2(36) DEFAULT sys_guid() NOT NULL,
    ver_nbr                        NUMBER(8,0) DEFAULT 1 NOT NULL,
    segment                        VARCHAR2(2) NOT NULL,
    segment_title                  VARCHAR2(256),
    family                         VARCHAR2(4),
    family_title                   VARCHAR2(256),
    class                          VARCHAR2(6),
    class_title                    VARCHAR2(256),
    commodity                      VARCHAR2(8),
    commodity_title                VARCHAR2(256),
    key                            VARCHAR2(6) NOT NULL,
    code                           VARCHAR2(8) NOT NULL,
    title                          VARCHAR2(256),
    actv_ind                       VARCHAR2(1) NOT NULL,
    last_update_dt                 TIMESTAMP (6) NOT NULL)
  );

-- Indexes for MM_UNSPSC_T

CREATE UNIQUE INDEX mm_unspsc_t_key ON mm_unspsc_t
  ( key  ASC );

CREATE INDEX mm_unspsc_t_family ON mm_unspsc_t
  ( family  ASC );

CREATE INDEX mm_unspsc_t_segment ON mm_unspsc_t
  ( segment ASC );

-- Constraints for MM_UNSPSC_T

ALTER TABLE mm_unspsc_t
  ADD CONSTRAINT mm_unspsc_tp1 PRIMARY KEY (unspsc_cd);

ALTER TABLE mm_unspsc_t
  ADD CONSTRAINT mm_unspsc_tc0 UNIQUE (obj_id);

-- Change MM_STOCK_T field from timestamp to date  - 04/29/10

alter table mm_stock_t
   modify reorder_remove_until_dt date;
   
-- Add MM_ORDER_DOC_T field   - 04/29/10

alter table MM_ORDER_DOC_T 
      add PROFILE_TYP_CD varchar2(10);

update MM_ORDER_DOC_T
  set PROFILE_TYP_CD = 'INTERNAL';
  
COMMIT;

-- Add MM_CATALOG_T field   - 04/29/10

alter table MM_CATALOG_T 
   add DEFAULT_OBJECT_CD   varchar2(4);

-- Add MM_CATALOG_ITEM_T field   - 04/29/10

alter table MM_ORDER_DOC_T 
   add DEFAULT_OBJECT_CD   varchar2(4);

-- Add MM_CHECKIN_DETAIL_T field   - 04/30/10

alter table MM_CHECKIN_DETAIL_T 
   add CORRECTED_CHECKIN_DETAIL_ID   NUMBER(18);
   
-- Add MM_CATALOG_PENDING_DOC_T field   - 05/04/10

alter table MM_CATALOG_PENDING_DOC_T 
   add CATALOG_UPLOAD_STATUS   VARCHAR2(12);
   