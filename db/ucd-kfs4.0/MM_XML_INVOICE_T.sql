-- Create table
create table MM_XML_INVOICE_T
(
  KEY_ID            VARCHAR2(100) not null,
  OBJ_ID            VARCHAR2(36) default sys_guid() not null,
  VER_NBR           NUMBER(8) default 1 not null,
  PUNCH_OUT_VNDR_ID NUMBER(18),
  ORDER_ID          VARCHAR2(30),
  INVOICE_NBR       VARCHAR2(200),
  PROCESSED_IND     VARCHAR2(1) default 'N',
  LAST_UPDATE_DT    TIMESTAMP(6) not null,
  INVOICE_XML       CLOB
);
alter table MM_XML_INVOICE_T
  add constraint MM_XML_INVOICE_TP1 primary key (KEY_ID);
alter table MM_XML_INVOICE_T
  add constraint MM_XML_INVOICE_TC0 unique (OBJ_ID);
