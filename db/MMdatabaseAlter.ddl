-- adding Catalog Restriction table 08/05/2010
create table MM_CATALOG_RESTRICT_T (
  CATALOG_RESTRICT_ID     NUMBER(18) primary key,
  OBJ_ID                  VARCHAR2(36) default sys_guid(),
  VER_NBR                 NUMBER(8) default 1,
  CATALOG_ID              NUMBER(18) not null,
  RESTRICT_CD             VARCHAR2(1) not null,
  ORG_CD                  VARCHAR2(32),
  FIN_COA_CD              VARCHAR2(2),
  ACCOUNT_NBR             VARCHAR2(32),
  ACTV_IND                VARCHAR2(1),
  LAST_UPDATE_DT          TIMESTAMP(6)
);

alter table MM_CATALOG_RESTRICT_T
      add constraint MM_CATALOG_RESTRICT_TC0 UNIQUE(OBJ_ID);
      
alter table MM_CATALOG_RESTRICT_T
      add constraint MM_CATALOG_RESTRICT_TR1 foreign key (CATALOG_ID)
      references MM_CATALOG_T (CATALOG_ID);
      
create sequence MM_CATALOG_RESTRICT_S
minvalue 1
maxvalue 999999999999999999
start with 1
increment by 1
nocache;       


-- Changes beginning after   10/01/2010

--Data Warehouse recommended changes based on KFS Keys  10/06/2010

alter table MM_PROFILE_T 
  rename column CAMPUS_CD to DELIVERY_CAMPUS_CD; 
alter table MM_PROFILE_T 
  add BILLING_CAMPUS_CD VARCHAR2(2); 

alter table MM_SHOP_CART_T 
  add DELIVERY_CAMPUS_CD VARCHAR2(2); 

alter table MM_ORDER_DOC_T 
  add DELIVERY_CAMPUS_CD VARCHAR2(2); 

alter table MM_RETURN_DOC_T 
  add RETRIEVAL_CAMPUS_CD VARCHAR2(2); 
  
alter table MM_RETURN_DETAIL_T 
   add CREDIT_DOC_ITM_NBR NUMBER(7); 

-- add MM_PCARD_T for mainting pcard info for punch out orders 10/6/2010
create table MM_PCARD_T (
       PCARD_ID                 NUMBER(18),
       OBJ_ID                   VARCHAR2(36) default sys_guid() not null,
       VER_NBR                  NUMBER(8) default 1 not null,
       PCARD_NBR                VARCHAR2(16) not null,
       NM                       VARCHAR2(45),
       PCARD_EXP_DT             VARCHAR2(7) not null,
       ACTV_IND                 VARCHAR2(1) not null,
       LAST_UPDATE_DT           TIMESTAMP(6) not null
);

alter table MM_PCARD_T
  add constraint MM_PCARD_TP1 primary key (PCARD_ID);
alter table MM_PCARD_T
  add constraint MM_PCARD_TC0 unique (OBJ_ID);
  
create table MM_PUNCH_OUT_VNDR_T (
PUNCH_OUT_VNDR_ID                NUMBER(18) not null,
OBJ_ID                           VARCHAR2(36) default sys_guid() not null,
VER_NBR                          NUMBER(8) default 1 not null,
NM                               VARCHAR2(45),
CATALOG_ID                       NUMBER(18) not null,
VNDR_CREDENTIAL_DOMAIN           VARCHAR2(60),
VNDR_IDENTITY                    VARCHAR2(60),
VNDR_SHARE_SECRET                VARCHAR2(60),
VNDR_URL                         VARCHAR2(250),
LOCAL_CREDENTIAL_DOMAIN          VARCHAR2(60),
LOCAL_IDENTITY                   VARCHAR2(60),
LOCAL_USER_AGENT                 VARCHAR2(60),
DEPLOY_MODE                      VARCHAR2(10),
SUPPLIER_PART_ID                 VARCHAR2(60),
PCARD_ID                         NUMBER(18),
ACTV_IND                         VARCHAR2(1) not null,  
LAST_UPDATE_DT                   TIMESTAMP(6) not null
);

alter table MM_PUNCH_OUT_VNDR_T
  add constraint MM_PUNCH_OUT_VNDR_TP1 primary key (PUNCH_OUT_VNDR_ID);
alter table MM_PUNCH_OUT_VNDR_T
  add constraint MM_PUNCH_OUT_VNDR_TC0 unique (OBJ_ID);
alter table MM_PUNCH_OUT_VNDR_T
  add constraint MM_PUNCH_OUT_VNDR_TR1 foreign key (CATALOG_ID)
  references MM_CATALOG_T (Catalog_Id);
      
--fix for XML tables  10/12/2010      
alter table MM_XML_INVOICE_T
      drop column INVOICE_XML;
alter table MM_XML_INVOICE_T
      add INVOICE_XML CLOB;
alter table MM_XML_INVOICE_T
      modify ORDER_ID NUMBER(18);
      
alter table MM_XML_PURCHASE_ORDER_T
      drop column PURCHASE_ORDER_XML;
alter table MM_XML_PURCHASE_ORDER_T
      add PURCHASE_ORDER_XML CLOB;
alter table MM_XML_PURCHASE_ORDER_T
      modify ORDER_ID NUMBER(18);
      
alter table MM_XML_PURCHASE_REQUEST_T
      drop column PURCHASE_REQUEST_XML;
alter table MM_XML_PURCHASE_REQUEST_T
      add PURCHASE_REQUEST_XML CLOB;
alter table MM_XML_PURCHASE_REQUEST_T
      modify ORDER_ID NUMBER(18);
      
-- Drop Forgein Key for MM_RETURN_DOC_T  10/14/2010
-- Note !!!! The following block is not needed. 
 /*
alter table MM_RETURN_DOC_T 
  drop constraint MM_RETURN_DOC_TR2; 
  
alter table MM_RETURN_DETAIL_T
  drop constraint MM_RETURN_DETAIL_TR2;

alter table MM_RETURN_DETAIL_T
  rename column RETURN_DETAIL_STATUS_CD to RETURN_REASON_CD;

alter table MM_RETURN_STATUS_CODE_T
  drop constraint MM_RETURN_STATUS_CODE_TP1;
alter table MM_RETURN_STATUS_CODE_T
  drop constraint MM_RETURN_STATUS_CODE_TC0;

alter table MM_RETURN_STATUS_CODE_T
  rename to MM_RETURN_REASON_T;
  
alter table MM_RETURN_REASON_T
  rename column RETURN_STATUS_CD to RETURN_REASON_CD;
*/
-- Canceled column to backorder 01/26/2011
alter table MM_BACK_ORDER_T
  add CANCELED_IND VARCHAR2(1) ;
update MM_BACK_ORDER_T t set t.canceled_ind = 'N' where t.canceled_ind is null;     
-- Add permission for adjusting 0 balance stock 	1/20/2011
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
       VALUES (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, 1, 'KFS-MM', 'Adjust Zero Bin Balance Stock', 'Allows balance adjustments to stock with a bin balance of 0.', 'Y');
-- Add zero balance stock adjustment permission to Warehouse manager role       
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
       VALUES (KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (select r.role_id from KRIM_ROLE_T r where r.nmspc_cd='KFS-MM' and r.role_nm='Warehouse Manager'), (select p.perm_id from KRIM_PERM_T p where p.nmspc_cd='KFS-MM' and p.nm='Adjust Zero Bin Balance Stock'), 'Y');       

update MM_STOCK_TYPE_T t set t.nm = 'OWNED CYLINDER' where t.stock_type_cd='03';

alter table MM_RETURN_REASON_T
  add constraint MM_RETURN_REASON_TP1 primary key (RETURN_REASON_CD);
alter table MM_RETURN_REASON_T
  add constraint MM_RETURN_REASON_TC0 unique (OBJ_ID);

alter table MM_RETURN_DETAIL_T
  add constraint MM_RETURN_DETAIL_TR2 foreign key (RETURN_REASON_CD)
  references MM_RETURN_REASON_T (RETURN_REASON_CD);

--  SEARCH_LOG not required  10/18/2010

drop table MM_SEARCH_LOG_T;

--  PICK_TICKET additional reporting columns
alter table MM_PICK_TICKET_T
  add ORDER_COUNT NUMBER(8);
  
alter table MM_PICK_TICKET_T
  add LINE_COUNT NUMBER(8);
  
alter table MM_PICK_TICKET_T
  add OLDEST_DATE TIMESTAMP(6);

-- Create tables for rental process 5/11/11

-- create for potential rental type table (may replace Stock Type for rental items)
create table MM_RENTAL_TYPE_T
(
  RENTAL_TYPE_CD      VARCHAR2(4) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  NM                  VARCHAR2(80),
  ACTV_IND            VARCHAR2(1) not null,
  LAST_UPDATE_DT      TIMESTAMP(6) not null
);

-- Create/Recreate primary, unique and foreign key constraints 
alter table MM_RENTAL_TYPE_T
  add constraint MM_RENTAL_TYPE_TP1 primary key (RENTAL_TYPE_CD);
alter table MM_RENTAL_TYPE_T
  add constraint MM_RENTAL_TYPE_TC0 unique (OBJ_ID);
  
-- Create table
create table MM_RENTAL_OBJECT_CODE_T
(
  RENTAL_OBJECT_CD    VARCHAR2(4) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  RENTAL_TYPE_CD      VARCHAR2(4) not null,
  NM                  VARCHAR2(80) not null,
  DAILY_DEMURRAGE_PRC NUMBER(19,4) not null,
  DEPOSIT_PRC         NUMBER(19,4) not null,      
  ACTV_IND            VARCHAR2(1) not null,
  LAST_UPDATE_DT      TIMESTAMP(6) not null
);


-- Create/Recreate primary, unique and foreign key constraints 
alter table MM_RENTAL_OBJECT_CODE_T
  add constraint MM_RENTAL_OBJECT_CODE_TP1 primary key (RENTAL_OBJECT_CD);
alter table MM_RENTAL_OBJECT_CODE_T
  add constraint MM_RENTAL_OBJECT_CODE_TC0 unique (OBJ_ID);
alter table MM_RENTAL_OBJECT_CODE_T
  add constraint MM_RENTAL_OBJECT_CODE_TR1 foreign key (RENTAL_TYPE_CD)
  references MM_RENTAL_TYPE_T (RENTAL_TYPE_CD);

-- Create table for rental process
rename MM_CYLINDER_S to MM_RENTAL_S;
create table MM_RENTAL_T
(
  RENTAL_ID           NUMBER(18) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  STOCK_ID            NUMBER(18) not null,
  RENTAL_TYPE_CD      VARCHAR2(4) not null,
  RENTAL_SERIAL_NBR   VARCHAR2(80) not null,
  RENTAL_STATUS_CD    VARCHAR2(1) not null,
  ISSUED_DT           DATE,
  LAST_CHARGE_DT      DATE,
  RETURN_DT           DATE,
  PICK_LIST_LINE_ID   NUMBER(18),
  CHECKIN_DETAIL_ID   NUMBER(18),
  RETURN_DETAIL_ID    NUMBER(18),
  LAST_UPDATE_DT      TIMESTAMP(6) not null
);

-- Create/Recreate primary, unique and foreign key constraints 
alter table MM_RENTAL_T
  add constraint MM_RENTAL_TP1 primary key (RENTAL_ID);  
alter table MM_RENTAL_T
  add constraint MM_RENTAL_TC0 unique (RENTAL_SERIAL_NBR, RENTAL_TYPE_CD, RETURN_DT);
alter table MM_RENTAL_T
  add constraint MM_RENTAL_TR1 foreign key (STOCK_ID)
  references MM_STOCK_T (STOCK_ID);  
alter table MM_RENTAL_T
  add constraint MM_RENTAL_TR2 foreign key (CHECKIN_DETAIL_ID)
  references MM_CHECKIN_DETAIL_T (CHECKIN_DETAIL_ID);  
alter table MM_RENTAL_T
  add constraint MM_RENTAL_TR3 foreign key (PICK_LIST_LINE_ID)
  references MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID);  
alter table MM_RENTAL_T
  add constraint MM_RENTAL_TR4 foreign key (RETURN_DETAIL_ID)
  references MM_RETURN_DETAIL_T (RETURN_DETAIL_ID);
alter table MM_RENTAL_T
  add constraint MM_RENTAL_TR5 foreign key (RENTAL_TYPE_CD)
  references MM_RENTAL_TYPE_T (RENTAL_TYPE_CD);
  
--Inserts and data copy for existing cylinder to rental object codes  
insert into MM_RENTAL_TYPE_T (RENTAL_TYPE_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
       VALUES('CYL', SYS_GUID(), 1, 'Cylinder', 'Y', sysdate);  
insert into MM_RENTAL_OBJECT_CODE_T (RENTAL_OBJECT_CD, OBJ_ID, VER_NBR, RENTAL_TYPE_CD, NM, DAILY_DEMURRAGE_PRC,ACTV_IND, LAST_UPDATE_DT)
  select gas.cylinder_gas_cd, SYS_GUID(), 1, 'CYL', gas.cylinder_gas_desc, gas.daily_demurage_prc, gas.actv_ind, gas.last_update_dt from MM_CYLINDER_GAS_T gas;
  
insert into MM_RENTAL_T (RENTAL_ID, OBJ_ID, VER_NBR, STOCK_ID, RENTAL_TYPE_CD, RENTAL_SERIAL_NBR, RENTAL_STATUS_CD, ISSUED_DT, LAST_CHARGE_DT, RETURN_DT, PICK_LIST_LINE_ID, CHECKIN_DETAIL_ID, RETURN_DETAIL_ID, LAST_UPDATE_DT)
  select cy.cylinder_id, cy.obj_id, 1, cy.stock_id, 'CYL', cy.cylinder_serial_nbr, '',cy.issued_dt, cy.last_charge_dt, cy.return_dt, cy.pick_list_line_id, cy.checkin_detail_id, cy.return_detail_id, cy.last_update_dt from MM_CYLINDER_T cy;

update MM_RENTAL_T r set r.rental_status_cd='A' where r.issued_dt is null;
update MM_RENTAL_T r set r.rental_status_cd='I' where not r.issued_dt is null and r.return_dt is null;
update MM_RENTAL_T r set r.rental_status_cd='R' where not r.return_dt is null;
  
drop table MM_CYLINDER_GAS_T;

-- changes to Stock table for rental process
alter table MM_STOCK_T
  rename column CYLINDER_GAS_CD to RENTAL_OBJECT_CD;
alter table MM_STOCK_T
  drop constraint MM_STOCK_TR6;
alter table MM_STOCK_T
  add constraint MM_STOCK_TR6 foreign key (RENTAL_OBJECT_CD)
  references MM_RENTAL_OBJECT_CODE_T(RENTAL_OBJECT_CD);

-- add reference to rentals from accounts
alter table MM_ACCOUNTS_T
  add RENTAL_ID NUMBER(18);
alter table MM_ACCOUNTS_T 
  add constraint MM_ACCOUNTS_TR3 foreign key(RENTAL_ID)
  references MM_RENTAL_T(RENTAL_ID);

-- drop MM_CHECKIN_CYLINDER_T
  drop table MM_CHECKIN_CYLINDER_T
  
rename MM_CHECKIN_CYLINDER_S to MM_CHECKIN_RENTAL_S;

--add MM_CHECKIN_RENTAL_T
create table MM_CHECKIN_RENTAL_T
(
  CHECKIN_RENTAL_ID   NUMBER(18) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  CHECKIN_DETAIL_ID   NUMBER(18),
  CHECKIN_SERIAL_NBR  VARCHAR2(80) not null,
  RETURN_DETAIL_ID    NUMBER(18),
  LAST_UPDATE_DT      TIMESTAMP(6) not null
);

alter table MM_CHECKIN_RENTAL_T
  add constraint MM_CHECKIN_RENTAL_TP1 primary key (CHECKIN_RENTAL_ID);
alter table MM_CHECKIN_RENTAL_T
  add constraint MM_CHECKIN_RENTAL_TC0 unique (OBJ_ID);
alter table MM_CHECKIN_RENTAL_T
  add constraint MM_CHECKIN_RENTAL_TR1 foreign key (CHECKIN_DETAIL_ID)
  references MM_CHECKIN_DETAIL_T (CHECKIN_DETAIL_ID);
alter table MM_CHECKIN_RENTAL_T
  add constraint MM_CHECKIN_RENTAL_TR2 foreign key (RETURN_DETAIL_ID)
  references MM_RETURN_DETAIL_T (RETURN_DETAIL_ID);
     
--add column PICK_LIST_LINE_VERIFY_DT to MM_PICK_LIST_LINE_T
alter table MM_PICK_LIST_LINE_T add (PICK_LIST_LINE_VERIFY_DT timestamp);

--drop this unused column 6/1/11
alter table MM_CHECKIN_RENTAL_T
      drop column RENTAL_TYPE_CD;
    
--add return status code for rental returns 6/6/11
insert into MM_RETURN_STATUS_CODE_T (RETURN_STATUS_CD, OBJ_ID, VER_NBR, NM, CUSTOMER_VENDOR_RETURN_IND, ACTV_IND, LAST_UPDATE_DT)
	values ('34', SYS_GUID(), 1, 'RENTAL RETURN', 'C', 'Y', sysdate);

--adding required distributor number field to shop cart detail (this was done a while ago for Punch out but never made it to alter file)
--6/20/11
alter table MM_SHOP_CART_DETAIL_T
	add DISTRIBUTOR_NBR VARCHAR2(30);	

--alter to change cxmlInvoice.orderId to a string
--6/27/11
alter table MM_XML_INVOICE_T
  modify ORDER_ID VARCHAR2(18);
  
-- 6/30/11 table name change for rental process
rename MM_CHECKIN_RENTAL_S to MM_STAGING_RENTAL_S;
rename MM_CHECKIN_RENTAL_T to MM_STAGING_RENTAL_T;

--re-inserting HOSTED catalog type 6/30/122
insert into MM_CATALOG_TYPE_T (CATALOG_TYPE_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
  VALUES (2, SYS_GUID(), 1, 'HOSTED', 'Y', sysdate);
  
--7/5/2011 rename customer_id to profile_id and make order_id a varchar for consistency
truncate table MM_XML_PURCHASE_ORDER_T;
truncate table MM_XML_PURCHASE_REQUEST_T;

alter table MM_XML_PURCHASE_REQUEST_T
  rename column CUSTOMER_ID to PROFILE_ID;
  
alter table MM_XML_PURCHASE_REQUEST_T
  modify ORDER_ID VARCHAR2(18);
  
alter table MM_XML_PURCHASE_ORDER_T
  rename column CUSTOMER_ID to PROFILE_ID;
  
alter table MM_XML_PURCHASE_ORDER_T
  modify ORDER_ID VARCHAR2(18);

alter table MM_XML_INVOICE_T
  modify INVOICE_NBR VARCHAR(32);
  
--7/8/2011  Punchout vendor updates
alter table MM_PUNCH_OUT_VNDR_T
  rename column VNDR_URL to PUNCH_OUT_URL;

alter table MM_PUNCH_OUT_VNDR_T
  add PO_URL VARCHAR2(255);

alter table MM_PUNCH_OUT_VNDR_T
  add constraint MM_PUNCH_OUT_VNDR_TC1 UNIQUE(VNDR_CREDENTIAL_DOMAIN, VNDR_IDENTITY);

--7/12/2011 cxml Invoice updates
alter table MM_XML_INVOICE_T 
 drop column CATALOG_CD;
 
alter table MM_XML_INVOICE_T
 add PUNCH_OUT_VNDR_ID NUMBER(18);
 
--7/13/2011 True Buyout and hosted catalog Type
insert into MM_CATALOG_TYPE_T (CATALOG_TYPE_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT) 
  VALUES ('2', SYS_GUID(), 1, 'HOSTED', 'Y', sysdate);
insert into MM_CATALOG_TYPE_T (CATALOG_TYPE_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT) 
  VALUES ('4', SYS_GUID(), 1, 'TRUE BUYOUT', 'Y', sysdate);
  
--True Buyout Order Type
insert into MM_ORDER_TYPE_T (ORDER_TYPE_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
  VALUES('BUYOUT', SYS_GUID(), 1, 'TRUE BUYOUT ORDER', 'Y', sysdate);
  
--7/13/2011 True Buyout Document tables
drop table MM_TRUE_BUYOUT_DETAIL_T;
drop table MM_TRUE_BUYOUT_DOC_T;

create table MM_TRUE_BUYOUT_DOC_T (
  FDOC_NBR                        VARCHAR2(14) not null,
  OBJ_ID                          VARCHAR2(36) default sys_guid() not null,
  VER_NBR                         NUMBER(8) default 1 not null,
  ORDER_DOC_NBR                   VARCHAR2(14),
  PROFILE_ID                      NUMBER(18) not null,
  SHIPPING_ADDRESS_ID             NUMBER(18) not null,
  BILLING_ADDRESS_ID              NUMBER(18) not null,
  CAMPUS_CD                       VARCHAR2(2),
  DELIVERY_DEPT_NM                VARCHAR2(45),
  DELIVERY_BUILDING_CD            VARCHAR2(10),
  DELIVERY_BUILDING_RM_NBR        VARCHAR2(8),
  LAST_UPDATE_DT     TIMESTAMP(6)
);

alter table MM_TRUE_BUYOUT_DOC_T
  add constraint MM_TRUE_BUYOUT_DOC_TP1 PRIMARY KEY(fdoc_nbr);
  
alter table MM_TRUE_BUYOUT_DOC_T
  add constraint MM_TRUE_BUYOUT_DOC_TC0 UNIQUE(obj_id);
  
alter table MM_TRUE_BUYOUT_DOC_T
  add constraint MM_TRUE_BUYOUT_DOC_TR1 FOREIGN KEY(profile_id) references MM_PROFILE_T(PROFILE_ID);
alter table MM_TRUE_BUYOUT_DOC_T
  add constraint MM_TRUE_BUYOUT_DOC_TR2 FOREIGN KEY(ORDER_DOC_NBR) references MM_ORDER_DOC_T(FDOC_NBR);
alter table MM_TRUE_BUYOUT_DOC_T
  add constraint MM_TRUE_BUYOUT_DOC_TR3 FOREIGN KEY(SHIPPING_ADDRESS_ID) references MM_ADDRESS_T(ADDRESS_ID);
alter table MM_TRUE_BUYOUT_DOC_T
  add constraint MM_TRUE_BUYOUT_DOC_TR4 FOREIGN KEY(BILLING_ADDRESS_ID) references MM_ADDRESS_T(ADDRESS_ID);


create table MM_TRUE_BUYOUT_DETAIL_T (
  TRUE_BUYOUT_DETAIL_ID              NUMBER(18) not null,
  OBJ_ID                             VARCHAR2(36) default sys_guid() not null,
  VER_NBR                            NUMBER(8) default 1 not null,
  STOCK_DIST_NBR                     VARCHAR2(30),
  TRUE_BUYOUT_DOC_NBR                VARCHAR2(14) not null,
  CATALOG_ID						 NUMBER(18),
  CATALOG_ITEM_ID                    NUMBER(18),  
  AGREEMENT_NBR						 VARCHAR2(50),
  ORDER_ITEM_DESC                    VARCHAR2(400),
  ORDER_ITEM_UI                      VARCHAR2(10),
  ORDER_ITEM_QTY                     NUMBER(11,4),
  ORDER_ITEM_PRICE                   NUMBER(19,4),
  WILLCALL_IND                       VARCHAR2(1),
  MARKUP_CD                          VARCHAR2(12),
  ACCOUNT_ID		              	 NUMBER(18),
  LAST_UPDATE_DT                     TIMESTAMP(6)
);

alter table MM_TRUE_BUYOUT_DETAIL_T
  add constraint MM_TRUE_BUYOUT_DETAIL_TP1 PRIMARY KEY(TRUE_BUYOUT_DETAIL_ID);
  
alter table MM_TRUE_BUYOUT_DETAIL_T
  add constraint MM_TRUE_BUYOUT_DETAIL_TC0 UNIQUE(OBJ_ID);
  
alter table MM_TRUE_BUYOUT_DETAIL_T
  add constraint MM_TRUE_BUYOUT_DETAIL_TR1 FOREIGN KEY(TRUE_BUYOUT_DOC_NBR) references MM_TRUE_BUYOUT_DOC_T(FDOC_NBR);

alter table MM_TRUE_BUYOUT_DETAIL_T
  add constraint MM_TRUE_BUYOUT_DETAIL_TR2 FOREIGN KEY(MARKUP_CD) references MM_MARKUP_T(MARKUP_CD);

alter table MM_TRUE_BUYOUT_DETAIL_T
  add constraint MM_TRUE_BUYOUT_DETAIL_TR3 FOREIGN KEY(CATALOG_ITEM_ID) references MM_CATALOG_ITEM_T(CATALOG_ITEM_ID);

alter table MM_TRUE_BUYOUT_DETAIL_T
  add constraint MM_TRUE_BUYOUT_DETAIL_TR4 FOREIGN KEY(ACCOUNT_ID) references MM_ACCOUNTS_T(ACCOUNTS_ID);
  
alter table MM_TRUE_BUYOUT_DETAIL_T
  add constraint MM_TRUE_BUYOUT_DETAIL_TR5 FOREIGN KEY(CATALOG_ID) references MM_CATALOG_T(CATALOG_ID);

alter table MM_TRUE_BUYOUT_DETAIL_T
  add constraint MM_TRUE_BUYOUT_DETAIL_TR6 FOREIGN KEY(AGREEMENT_NBR) references MM_AGREEMENT_T(AGREEMENT_NBR);
    
--8/2/2011 adding finobjectcode to shopcart detail
alter table MM_SHOP_CART_DETAIL_T 
  add FIN_OBJECT_CD VARCHAR2(4);
  
--8/10/11  increase SPAID_ID per JIRA: EXTMM-319
alter table MM_SHOP_CART_DETAIL_T
  modify SPAID_ID VARCHAR2(200);
  
alter table MM_ORDER_DETAIL_T
  modify SPAID_ID VARCHAR2(200);
  
--8/18/2011 Shopping Search performance enhancements
rename MM_CATALOG_ITEM_REPORT_V to MM_CATALOG_ITEM_LIVE_V;

create table MM_CATALOG_ITEM_SEARCH_T (
  CATALOG_ITEM_ID                     NUMBER(18),
  DISTRIBUTOR_NBR                     VARCHAR2(30),
  CATALOG_DESC                        VARCHAR2(400),
  RECYCLED_IND                        VARCHAR2(1),
  CATALOG_PRC                         NUMBER(19,4),
  CATALOG_ID                          NUMBER(18),
  STOCK_ID                            NUMBER(18),
  DISPLAYABLE_IND                     VARCHAR2(1),
  ACTV_IND                            VARCHAR2(1),
  priority_nbr                        NUMBER(8),
  catalog_subgroup_id                 NUMBER(18),
  prior_catalog_subgroup_id           NUMBER(18),
  catalog_group_cd                    VARCHAR2(12),
  order_count                         NUMBER(18)
);

 
create index mm_catalog_item_search_idx1
  on MM_CATALOG_ITEM_SEARCH_T (catalog_subgroup_id, Prior_Catalog_Subgroup_Id, Catalog_Group_Cd);
  
create bitmap index mm_catalog_item_search_idx3
  on MM_CATALOG_ITEM_SEARCH_T (catalog_id);

truncate table MM_CATALOG_ITEM_SEARCH_T;  
insert into MM_CATALOG_ITEM_SEARCH_T
  select CATALOG_ITEM_ID, DISTRIBUTOR_NBR, LOWER(CATALOG_DESC), RECYCLED_IND, CATALOG_PRC, CATALOG_ID, STOCK_ID, DISPLAYABLE_IND, ACTV_IND, PRIORITY_NBR, CATALOG_SUBGROUP_ID, PRIOR_CATALOG_SUBGROUP_ID, CATALOG_GROUP_CD, ORDER_COUNT from MM_CATALOG_ITEM_LIVE_V v where v.ACTV_IND='Y' and v.DISPLAYABLE_IND='Y';
 
--8/26/2011  add displayable indicator to catalog group
alter table MM_CATALOG_GROUP_T
      add DISPLAYABLE_IND varchar2(1) default 'Y';
      
update MM_CATALOG_GROUP_T t set t.displayable_ind = 'Y';

-- 9/21/11 Additional search indexing performance tuning
drop index MM_CATALOG_SEARCH_IDX1;  
drop index MM_CATALOG_SEARCH_IDX2;

truncate table MM_CATALOG_ITEM_SEARCH_T;  
insert /*+ append */ into MM_CATALOG_ITEM_SEARCH_T
  select CATALOG_ITEM_ID, DISTRIBUTOR_NBR, CATALOG_DESC, RECYCLED_IND, CATALOG_PRC, CATALOG_ID, STOCK_ID, DISPLAYABLE_IND, ACTV_IND, PRIORITY_NBR, CATALOG_SUBGROUP_ID, PRIOR_CATALOG_SUBGROUP_ID, CATALOG_GROUP_CD, ORDER_COUNT from MM_CATALOG_ITEM_LIVE_V v where v.ACTV_IND='Y' and v.DISPLAYABLE_IND='Y';

CREATE INDEX MM_CATALOG_SEARCH_IDX1 ON MM_CATALOG_ITEM_SEARCH_T(catalog_desc) 
  INDEXTYPE IS CTXSYS.CTXCAT;
  
create index MM_CATALOG_SEARCH_IDX2
  on MM_CATALOG_ITEM_SEARCH_T (Distributor_Nbr);
  
-- 9/22/11 Rental support for true buyouts
alter table MM_TRUE_BUYOUT_DETAIL_T
  add STOCK_TYPE_CD VARCHAR2(2);
  
alter table MM_TRUE_BUYOUT_DETAIL_T
  add constraint MM_TRUE_BUYOUT_DETAIL_TR7 foreign key (STOCK_TYPE_CD) references MM_STOCK_TYPE_T(STOCK_TYPE_CD);
  
update MM_STOCK_T t set t.stock_type_cd = '02' where t.stock_type_cd='03';
update MM_STOCK_T t set t.stock_type_cd = '01' where not t.stock_type_cd = '01' and not t.stock_type_cd = '02';
  
delete from MM_STOCK_TYPE_T t where t.stock_type_cd not in ('01', '02');

--9/23/2011 MSU AER 0581 Shopping white space
drop table MM_SHOP_FRONT_PAGE_T;
create table MM_SHOP_FRONT_PAGE_T (
  FRONT_PAGE_ID                   NUMBER(18) not null,
  OBJ_ID                          VARCHAR2(36) default sys_guid() not null,
  VER_NBR                         NUMBER(8) default 1 not null,
  NM                              VARCHAR2(50) not null,
  FRONT_PAGE_DESC                 VARCHAR2(200),
  FRONT_PAGE_URL                  VARCHAR2(500),
  FRONT_PAGE_HTML                 CLOB,
  DISPLAY_HEIGHT                  NUMBER(8),
  CURRENT_IND                     VARCHAR2(1) not null,
  ACTV_IND                        VARCHAR2(1) not null,
  LAST_UPDATE_DT                  TIMESTAMP(6) not null
); 

alter table MM_SHOP_FRONT_PAGE_T
  add constraint MM_SHOP_FRONT_PAGE_TP1 primary key (FRONT_PAGE_ID);  
alter table MM_SHOP_FRONT_PAGE_T
  add constraint MM_SHOP_FRONT_PAGE_TC0 unique (OBJ_ID);
  
create sequence MM_SHOP_FRONT_PAGE_S
  minvalue 1
  maxvalue 999999999999999999
  start with 1
  increment by 1
  nocache
  order;
  
--10/13/2011  Rental true-buyout additions
alter table MM_TRUE_BUYOUT_DETAIL_T
  add RENTAL_OBJECT_CD VARCHAR2(4);
  
alter table MM_TRUE_BUYOUT_DETAIL_T
  add constraint MM_TRUE_BUYOUT_DETAIL_TR8 foreign key (RENTAL_OBJECT_CD) references MM_RENTAL_OBJECT_CODE_T(RENTAL_OBJECT_CD);
  
alter table MM_STOCK_T 
  modify RENTAL_OBJECT_CD VARCHAR2(4);  
  