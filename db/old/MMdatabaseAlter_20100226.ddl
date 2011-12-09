-- Reorder changes  12/29/2009

alter table MM_AGREEMENT_T
  add WAREHOUSE_CD         VARCHAR2(2) null;

alter table MM_AGREEMENT_T
  add constraint MM_AGREEMENT_TR1 foreign key (WAREHOUSE_CD)
  references MM_WAREHOUSE_T (WAREHOUSE_CD);

alter table MM_WAREHOUSE_T
  drop constraint MM_WAREHOUSE_TR1;

-- Account changes 1/5/2010
alter table MM_ACCOUNTS_T
 DROP COLUMN ACCOUNT_PCT;

alter table MM_ACCOUNTS_T
 ADD ACCOUNT_PCT NUMBER(13,10);
 
-- Warehouse changes 1/6/2010

alter table MM_WAREHOUSE_T
  drop column AGREEMENT_NBR;
alter table MM_WAREHOUSE_T
  drop column AGREEMENT_LAG_DAYS;

-- Catalog Subgoup and Return Detail changes 1/7/2010

alter table MM_CATALOG_SUBGROUP_T
  drop column PRIOR_CATALOG_SUBGROUP_CD;
alter table MM_CATALOG_SUBGROUP_T
  add PRIOR_CATALOG_SUBGROUP_ID    NUMBER(18) null;

alter table MM_RETURN_DETAIL_T
  add BIN_ID    NUMBER(18) null;

-- Stock and Order Detail changes 1/13/2010

alter table MM_STOCK_T
  add REORDER_REMOVE_UNTIL_DT    TIMESTAMP(6) null;

alter table MM_ORDER_DETAIL_T
  add ADDITIONAL_COST_TYPE_CD    VARCHAR2(12) null;

alter table MM_ORDER_DETAIL_T
  add constraint MM_ORDER_DETAIL_TR5 foreign key (ADDITIONAL_COST_TYPE_CD)
  references MM_ADDITIONAL_COST_TYPE_T (ADDITIONAL_COST_TYPE_CD);

-- Catalog Pending changes 1/13/2010

alter table MM_CATALOG_ITEM_T
  drop constraint MM_CATALOG_ITEM_TR3;

truncate table MM_CATALOG_ITEM_PENDING_T;

truncate table MM_CATALOG_PENDING_T;

alter table MM_CATALOG_ITEM_T
  add constraint MM_CATALOG_ITEM_TR3 foreign key (CATALOG_ITEM_PND_ID)
  references MM_CATALOG_ITEM_PENDING_T (CATALOG_ITEM_PND_ID);

alter table MM_CATALOG_T
  drop constraint MM_CATALOG_TR1;
alter table MM_CATALOG_ITEM_PENDING_T
  drop constraint MM_CATALOG_ITEM_PENDING_TR1;
alter table MM_CATALOG_ITEM_PENDING_T
  drop constraint MM_CATALOG_ITEM_PENDING_TC0;
alter table MM_CATALOG_PENDING_T
  drop constraint MM_CATALOG_PENDING_TP1;

alter table MM_CATALOG_PENDING_T
  rename to MM_CATALOG_PENDING_DOC_T;
alter table MM_CATALOG_PENDING_DOC_T
  rename column CATALOG_PENDING_ID to FDOC_NBR;
alter table MM_CATALOG_PENDING_DOC_T
  modify FDOC_NBR     VarChar2(14);

alter table MM_CATALOG_ITEM_PENDING_T
  rename column CATALOG_PENDING_ID to CATALOG_PENDING_DOC_NBR;
alter table MM_CATALOG_ITEM_PENDING_T
  modify CATALOG_PENDING_DOC_NBR     VarChar2(14);

alter table MM_CATALOG_T
  rename column CATALOG_PENDING_ID to CATALOG_PENDING_DOC_NBR;
alter table MM_CATALOG_T
  modify CATALOG_PENDING_DOC_NBR     VarChar2(14);

alter table MM_CATALOG_PENDING_DOC_T
  add constraint MM_CATALOG_PENDING_DOC_TP1 primary key (FDOC_NBR);
alter table MM_CATALOG_T
  add constraint MM_CATALOG_TR1 foreign key (CATALOG_PENDING_DOC_NBR)
  references MM_CATALOG_PENDING_DOC_T (FDOC_NBR);
alter table MM_CATALOG_ITEM_PENDING_T
  add constraint MM_CATALOG_ITEM_PENDING_TR1 foreign key (CATALOG_PENDING_DOC_NBR)
  references MM_CATALOG_PENDING_DOC_T (FDOC_NBR);
alter table MM_CATALOG_ITEM_PENDING_T
  add constraint MM_CATALOG_ITEM_PENDING_TC0 unique (CATALOG_PENDING_DOC_NBR, DISTRIBUTOR_NBR);

drop sequence MM_CATALOG_PENDING_S;
drop sequence MM_CATALOG_ITEM_PENDING_S;

create sequence MM_CATALOG_ITEM_PENDING_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;  

-- Run the insert for MM_CATALOG_ITEM_PENDING_T INSERT.sql

drop sequence MM_CATALOG_ITEM_PENDING_S;

create sequence MM_CATALOG_ITEM_PENDING_S
minvalue 1
maxvalue 999999999999999999
start with 5000
increment by 1
nocache
order;  

--- MM_CATALOG_ITEM_SUBGROUPS_V view 1/19/2010
create or replace view mm_catalog_item_subgroups_v as
select i."CATALOG_ITEM_ID",
       i."OBJ_ID",
       i."VER_NBR",
       i."DISTRIBUTOR_NBR",
       i."MANUFACTURER_NBR",
       i."CATALOG_UNIT_OF_ISSUE_CD",
       i."CATALOG_PRC",
       i."CATALOG_DESC",
       i."RECYCLED_IND",
       i."WILLCALL_IND",
       i."UNSPSC_CD",
       i."SHIPPING_WGT",
       i."SHIPPING_HT",
       i."SHIPPING_WD",
       i."SHIPPING_LH",
       i."CATALOG_ID",
       i."STOCK_ID",
       i."DISPLAYABLE_IND",
       i."CATALOG_ITEM_PND_ID",
       i."SUBSTITUTE_DISTRIBUTOR_NBR",
       i."TAXABLE_IND",
       i."SPAID_ID",
       i."ACTV_IND",
       i."LAST_UPDATE_DT", 
       s.catalog_subgroup_id, 
       sg.prior_catalog_subgroup_id, 
       sg.catalog_group_cd
from MM_CATALOG_ITEM_T i
left join MM_CATALOG_SUBGROUP_ITEM_T s
  on s.catalog_item_id = i.catalog_item_id
left join MM_CATALOG_SUBGROUP_T sg
  on sg.catalog_subgroup_id = s.catalog_subgroup_id
; 

-- Fix Order Detail for Expected Delivery 01/22/2010

alter table MM_ORDER_DETAIL_T
  add ORDER_EXPECTED_DT    TIMESTAMP(6) null;

-- Fix unique key for MM_CATALOG_SUBGROUP_T  01/26/2010

truncate table MM_CATALOG_SUBGROUP_ITEM_T;

alter table MM_CATALOG_SUBGROUP_ITEM_T
  drop constraint MM_CATALOG_SUBGROUP_ITEM_TC0;

alter table MM_CATALOG_SUBGROUP_ITEM_T
  add constraint MM_CATALOG_SUBGROUP_ITEM_TC0 unique (CATALOG_ITEM_ID, CATALOG_SUBGROUP_ID);

-- Run the insert for MM_CATALOG_SUBGROUP_ITEM_T_Insert.sql
  
alter table MM_CATALOG_SUBGROUP_T
  drop constraint MM_CATALOG_SUBGROUP_TC0;

alter table MM_CATALOG_SUBGROUP_T
  add constraint MM_CATALOG_SUBGROUP_TC0 unique (CATALOG_GROUP_CD, CATALOG_SUBGROUP_CD);

-- Fix agreement info for MM_ORDER_DETAIL_T  01/26/2010

alter table MM_ORDER_DETAIL_T
   add AGREEMENT_ORDER_ITEM_QTY      NUMBER(11,4);
alter table MM_ORDER_DETAIL_T
   add AGREEMENT_UNIT_OF_ISSUE_CD    VARCHAR2(10);

-- Fix SUB_ACCT_NBR in MM_WAREHOUSE_ACCOUNTS_T  02/02/2010

alter table MM_WAREHOUSE_ACCOUNTS_T
   modify SUB_ACCT_NBR  NULL;
   
-- Fix for Customer favorite detail 2/3/2010
alter table MM_CUSTOMER_FAV_DETAIL_T
      drop constraint MM_CUSTOMER_FAV_DETAIL_TC0;

alter table MM_CUSTOMER_FAV_DETAIL_T
      add constraint MM_CUSTOMER_FAV_DETAIL_TC0 unique (CUSTOMER_FAV_ID, CATALOG_ITEM_ID);

alter table MM_CUSTOMER_FAV_DETAIL_T
      drop column CUSTOMER_FAV_LINE_NBR;

-- Add CYCLE_CNT_CD in MM_WORKSHEET_COUNT_DOC_T  02/03/2010

alter table MM_WORKSHEET_COUNT_DOC_T
   add CYCLE_CNT_CD       VARCHAR2(1) null;
   
-- Change MM_STOCK_BALANCE_TC0  02/04/2010

alter table MM_STOCK_BALANCE_T
  drop constraint MM_STOCK_BALANCE_TC0;
alter table MM_STOCK_BALANCE_T
  add constraint MM_STOCK_BALANCE_TC0 unique (OBJ_ID);

-- Add agreement to MM_ORDER_DOC_T  02/04/2010

alter table MM_ORDER_DOC_T
   add AGREEMENT_NBR              VARCHAR2(15) null;

-- Fix campus_cd on MM_ORDER_DOC_T  02/04/2010

alter table MM_ORDER_DOC_T
   modify CAMPUS_CD null;

-- Add WAREHOUSE OBJECT changes  02/12/2010

create table MM_WAREHOUSE_OBJECT_TYPE_T
(
  OBJECT_TYPE_CD            VARCHAR2(4) not null,
  OBJ_ID                    VARCHAR2(36) default sys_guid() not null,
  VER_NBR                   NUMBER(8) default 1 not null,
  NM                        VARCHAR2(45),
  ACTV_IND                  VARCHAR2(1) not null,
  LAST_UPDATE_DT            TIMESTAMP(6) not null
)
;
alter table MM_WAREHOUSE_OBJECT_TYPE_T
  add constraint MM_WAREHOUSE_OBJECT_TYPE_TP1 primary key (OBJECT_TYPE_CD);
alter table MM_WAREHOUSE_OBJECT_TYPE_T
  add constraint MM_WAREHOUSE_OBJECT_TYPE_TC0 unique (OBJ_ID);

create table MM_WAREHOUSE_OBJECT_T
(
  WAREHOUSE_OBJECT_ID       NUMBER(18) not null,
  OBJ_ID                    VARCHAR2(36) default sys_guid() not null,
  VER_NBR                   NUMBER(8) default 1 not null,
  WAREHOUSE_CD              VARCHAR2(2) not null,
  WAREHOUSE_OBJECT_TYPE_CD  VARCHAR2(4) not null,
  FIN_COA_CD                VARCHAR2(2) not null,
  FIN_OBJECT_CD             VARCHAR2(4),
  FIN_SUB_OBJ_CD            VARCHAR2(3),
  OFFSET_OBJECT_CD          VARCHAR2(4),
  OFFSET_SUB_OBJ_CD         VARCHAR2(3),
  ACTV_IND                  VARCHAR2(1) not null,
  LAST_UPDATE_DT            TIMESTAMP(6) not null
)
;
alter table MM_WAREHOUSE_OBJECT_T
  add constraint MM_WAREHOUSE_OBJECT_TP1 primary key (WAREHOUSE_OBJECT_ID);
alter table MM_WAREHOUSE_OBJECT_T
  add constraint MM_WAREHOUSE_OBJECT_TC0 unique (WAREHOUSE_CD, WAREHOUSE_OBJECT_TYPE_CD);
alter table MM_WAREHOUSE_OBJECT_T
  add constraint MM_WAREHOUSE_OBJECT_TR1 foreign key (WAREHOUSE_CD)
  references MM_WAREHOUSE_T (WAREHOUSE_CD);
alter table MM_WAREHOUSE_OBJECT_T
  add constraint MM_WAREHOUSE_OBJECT_TR2 foreign key (WAREHOUSE_OBJECT_TYPE_CD)
  references MM_WAREHOUSE_OBJECT_TYPE_T (OBJECT_TYPE_CD);

create sequence MM_WAREHOUSE_OBJECT_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache
order;  

alter table MM_STOCK_TRANS_REASON_T 
  add  OBJECT_TYPE_CD    varchar2(4);
alter table MM_STOCK_TRANS_REASON_T
  add constraint MM_STOCK_TRANS_REASON_TR1 foreign key (OBJECT_TYPE_CD)
  references MM_WAREHOUSE_OBJECT_TYPE_T (WAREHOUSE_OBJECT_TYPE_CD);

-- Add Profile e-mail notify changes  02/15/2010

alter table MM_PROFILE_T
  add NOTIFY_IND   varchar2(1) null;

-- Add Department Credit Ind for Returns changes  02/17/2010

alter table MM_RETURN_DETAIL_T
  add DEPARTMENT_CREDIT_IND   varchar2(1) null;

-- Updates from meeting   2/18/2010 

alter table MM_ADDRESS_T
  drop column ADDRESS_DEFAULT_IND;
  
alter table MM_ADDRESS_T
  drop constraint MM_ADDRESS_TC0;
alter table MM_ADDRESS_T
  add constraint MM_ADDRESS_TC0 unique (OBJ_ID);
  
alter table MM_PROFILE_T
  add PROJECT_CD          VARCHAR2(10);
  
alter table MM_WAREHOUSE_T
  add BILLING_PROFILE_ID  NUMBER(18);
  
-- Updates from meeting   2/19/2010 

alter table MM_ADDRESS_T
  drop constraint MM_ADDRESS_TC0;
alter table MM_ADDRESS_T
  add constraint MM_ADDRESS_TC0 unique (OBJ_ID);

-- Accounts corrections  2/25/2010

alter table MM_ACCOUNTS_T
  modify SUB_ACCT_NBR NULL;
  
alter table MM_ACCOUNTS_T
  add SHOP_CART_DETAIL_ID   NUMBER(18) NULL;

alter table MM_ACCOUNTS_T
  add constraint MM_ACCOUNTS_TR3 foreign key (SHOP_CART_DETAIL_ID)
  references MM_SHOP_CART_DETAIL_T (SHOP_CART_DETAIL_ID);

-- Cylinder Return change   2/25/2010
  
alter table MM_CHECKIN_CYLINDER_T
  modify CHECKIN_DETAIL_ID   NULL;

alter table MM_CHECKIN_CYLINDER_T
  add RETURN_DETAIL_ID   NUMBER(18) NULL;

alter table MM_CHECKIN_CYLINDER_T
  add constraint MM_CHECKIN_CYLINDER_TR2 foreign key (RETURN_DETAIL_ID)
  references MM_RETURN_DETAIL_T (RETURN_DETAIL_ID);
  
-- Agreement changes  2/25/2010

alter table MM_AGREEMENT_T
  drop column PO_ID;

-- Decimal Percentage changes  2/25/2010

alter table MM_HAZARDOUS_MATERIEL_T
  modify EHS_CONVERSION_UNIT_FACTOR    NUMBER(8,5);

alter table MM_SHOP_CART_DTL_ADDL_COST_T
  modify ADDITIONAL_CST            NUMBER(19,4);

-- whoops too soon Agreement changes  2/25/2010

alter table MM_AGREEMENT_T
  add PO_ID    NUMBER(18) null;

