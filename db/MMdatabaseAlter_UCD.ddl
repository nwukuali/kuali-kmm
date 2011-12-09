drop table MM_DELIVERY_LINE_T;
drop table MM_DELIVERY_DOC_T;
drop table MM_PACK_LIST_LINE_T;
drop table MM_ROUTE_MAP_T;
delete from MM_PACK_LIST_DOC_T;

CREATE TABLE mm_pack_list_line_t 
(   
    pack_list_line_id              NUMBER(18,0) NOT NULL,
    obj_id                         VARCHAR2(36) DEFAULT sys_guid() NOT NULL,
    ver_nbr                        NUMBER(8,0) DEFAULT 1 NOT NULL,
    pack_list_line_nbr             NUMBER(8,0) NOT NULL,
    pack_list_doc_nbr              VARCHAR2(14) NOT NULL,
    pick_list_line_id              NUMBER(18,0) NOT NULL,
    route_cd                       VARCHAR2(2) NOT NULL,
    delivery_campus_cd             VARCHAR2(2),
    delivery_building_cd           VARCHAR2(10),
    delivery_building_rm_nbr       VARCHAR2(8),
    delivery_building_nm           VARCHAR2(45),
    delivery_department_nm         VARCHAR2(45),
    delivery_instruction_txt       VARCHAR2(255),
    stop_cd                        VARCHAR2(2) NOT NULL,
    number_of_cartons              NUMBER(8,0) NOT NULL,
    pack_prncpl_id                 VARCHAR2(100),
    pack_dt                        DATE NOT NULL,
    pack_status_cd                 VARCHAR2(4) NOT NULL,
    last_update_dt                 TIMESTAMP (6) NOT NULL
);

ALTER TABLE mm_pack_list_line_t
ADD CONSTRAINT mm_pack_list_line_tp1 PRIMARY KEY (pack_list_line_id);

ALTER TABLE mm_pack_list_line_t
ADD CONSTRAINT mm_pack_list_line_tc0 UNIQUE (pack_list_doc_nbr, pack_list_line_nbr);

ALTER TABLE mm_pack_list_line_t
ADD CONSTRAINT mm_pack_list_line_tr5 FOREIGN KEY (route_cd) 
REFERENCES mm_route_t (route_cd);

ALTER TABLE mm_pack_list_line_t
ADD CONSTRAINT mm_pack_list_line_tr4 FOREIGN KEY (pack_status_cd) 
REFERENCES mm_pack_status_code_t (pack_status_cd);

ALTER TABLE mm_pack_list_line_t
ADD CONSTRAINT mm_pack_list_line_tr3 FOREIGN KEY (pick_list_line_id) 
REFERENCES mm_pick_list_line_t (pick_list_line_id);

ALTER TABLE mm_pack_list_line_t 
ADD CONSTRAINT mm_pack_list_line_tr1 FOREIGN KEY (pack_list_doc_nbr) 
REFERENCES mm_pack_list_doc_t (fdoc_nbr);


CREATE TABLE MM_ROUTE_MAP_T
(
  ROUTE_MAP_ID        NUMBER(18) not null,
  OBJ_ID              VARCHAR2(36) default sys_guid() not null,
  VER_NBR             NUMBER(8) default 1 not null,
  ROUTE_CD            VARCHAR2(2),
  DELIVERY_CAMPUS_CD  VARCHAR2(2) not null,
  DELIVERY_BUILDING_CD VARCHAR2(10) not null,
  STOP_SEQUENCE       NUMBER(2) not null, 
  ACTV_IND            VARCHAR2(1) not null,
  LAST_UPDATE_DT      TIMESTAMP(6) not null
);

ALTER TABLE MM_ROUTE_MAP_T
  ADD CONSTRAINT MM_ROUTE_MAP_TP1 primary key (ROUTE_MAP_ID);

ALTER TABLE MM_ROUTE_MAP_T  
  ADD CONSTRAINT MM_ROUTE_MAP_TR1 FOREIGN KEY (ROUTE_CD) 
  REFERENCES MM_ROUTE_T (ROUTE_CD);            

create sequence MM_ROUTE_MAP_S 
  minvalue 1
  maxvalue 999999999999999999
  start with 1
  increment by 1
  nocache
  order;

CREATE TABLE MM_DELIVERY_DOC_T
(
   FDOC_NBR          VARCHAR(14) NOT NULL, 
   OBJ_ID            VARCHAR2(36) DEFAULT sys_guid() NOT NULL, 
   VER_NBR           NUMBER(8,0) DEFAULT 1 NOT NULL, 
   ROUTE_CD          VARCHAR(2) NOT NULL,
   LAST_UPDATE_DT    TIMESTAMP (6) NOT NULL
);

ALTER TABLE MM_DELIVERY_DOC_T
ADD CONSTRAINT MM_DELIVERY_DOC_TP1 primary key (FDOC_NBR);

ALTER TABLE MM_DELIVERY_DOC_T 
ADD CONSTRAINT MM_DELIVERY_DOC_TR1 FOREIGN KEY (ROUTE_CD) 
REFERENCES MM_ROUTE_T (ROUTE_CD);

CREATE TABLE MM_DELIVERY_LINE_T 
(
   DELIVERY_LINE_ID NUMBER(18,0) NOT NULL, 
   DELIVERY_DOC_NBR VARCHAR(14)  NOT NULL,
   OBJ_ID           VARCHAR2(36) DEFAULT sys_guid() NOT NULL, 
   VER_NBR          NUMBER(8,0)  DEFAULT 1 NOT NULL, 
   PACK_LIST_LINE_ID    NUMBER(18,0) NOT NULL ENABLE,
   LAST_UPDATE_DT   TIMESTAMP (6) NOT NULL ENABLE, 
   DEPT_RECVD_BY_NM VARCHAR2(40),
   DELIVERY_REASON_CD   VARCHAR2(10) not null,
   DELIVERY_DT      DATE,
   DRIVER_COMMENT   VARCHAR2(100)
);  

ALTER TABLE MM_DELIVERY_LINE_T 
ADD CONSTRAINT MM_DELIVERY_LINE_TP1 PRIMARY KEY (DELIVERY_LINE_ID);

ALTER TABLE MM_DELIVERY_LINE_T 
ADD CONSTRAINT MM_DELIVERY_LINE_TR1 FOREIGN KEY (DELIVERY_DOC_NBR)
REFERENCES MM_DELIVERY_DOC_T (FDOC_NBR);

ALTER TABLE MM_DELIVERY_LINE_T 
ADD CONSTRAINT MM_DELIVERY_LINE_TR2 FOREIGN KEY (PACK_LIST_LINE_ID) 
REFERENCES MM_PACK_LIST_LINE_T (PACK_LIST_LINE_ID);

ALTER TABLE MM_DELIVERY_LINE_T 
ADD CONSTRAINT MM_DELIVERY_LINE_TR3 FOREIGN KEY (DELIVERY_REASON_CD) 
REFERENCES MM_DELIVERY_REASON_T (DELIVERY_REASON_CD);

create sequence MM_DELIVERY_LINE_S 
  minvalue 1
  maxvalue 999999999999999999
  start with 1
  increment by 1
  nocache
  order;
  
-- 10/26/2010 
-- TABLE FOR DISCREPANCY DOC and DISCREPANCY LINES
  
CREATE TABLE mm_discrepancy_doc_t
    (fdoc_nbr                       VARCHAR2(36) NOT NULL,
    obj_id                         VARCHAR2(36) DEFAULT sys_guid() NOT NULL,
    ver_nbr                        NUMBER(8,0) DEFAULT 1 NOT NULL,
    last_update_dt                 TIMESTAMP (6) NOT NULL
    );

ALTER TABLE mm_discrepancy_doc_t
ADD CONSTRAINT mm_discrepancy_doc_tp1 PRIMARY KEY (fdoc_nbr);

CREATE TABLE mm_discrepancy_line_t
    (discr_line_id                  NUMBER(18,0) NOT NULL,
     discr_line_doc_nbr             VARCHAR2(36) NOT NULL,
     vendor_name                    VARCHAR2(36) NOT NULL,
     order_number                   VARCHAR2(14) NOT NULL,
     invoice_number                 VARCHAR2(14) NOT NULL,
     transaction_amt                VARCHAR2(14) NOT NULL,
     total_pcard_amt                VARCHAR2(14) NOT NULL,
     invoice_amt                    VARCHAR2(14) NOT NULL,
     posted_date                    DATE,
     discr_comment                  VARCHAR2(100),
     actv_ind                       VARCHAR2(1),
     last_update_dt                 TIMESTAMP (6) NOT NULL,
     ver_nbr                        NUMBER(8,0) DEFAULT 1 NOT NULL,
     obj_id                         VARCHAR2(36) DEFAULT sys_guid() NOT NULL
);

ALTER TABLE mm_discrepancy_line_t
ADD CONSTRAINT mm_discrepancy_line_tp1 PRIMARY KEY (discr_line_id);

ALTER TABLE mm_discrepancy_line_t
ADD CONSTRAINT mm_discrepancy_line_tr1 FOREIGN KEY (discr_line_doc_nbr)
REFERENCES mm_discrepancy_doc_t (fdoc_nbr);

ALTER TABLE mm_discrepancy_line_t
ADD CONSTRAINT mm_discrepancy_line_tC0 UNIQUE (discr_line_doc_nbr, order_number, invoice_number);
  
create sequence MM_DISCREPANCY_LINE_S 
  minvalue 1
  maxvalue 999999999999999999
  start with 1
  increment by 1
  nocache
  order;