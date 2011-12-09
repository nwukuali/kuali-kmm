create table MM_B2B_INVOICE_T
(
  KEY_ID              VARCHAR2(100) not null,
  OBJ_ID              VARCHAR2(36) not null,
  VER_NBR             NUMBER(8) default 1 not null,
  INVOICE_ID         VARCHAR2(50),
  PURPOSE             VARCHAR2(50),
  OPERATION           VARCHAR2(50),
  INVOICE_DT          TIMESTAMP,
  REF_INVOICE_KEY_ID  VARCHAR2(100),
  ORDER_XML_KEY_ID    VARCHAR2(100),
  PUNCH_OUT_VNDR_ID   NUMBER(18),
  ORDER_ID            VARCHAR2(30),
  SUB_TOTAL_AMT       NUMBER(19,4),
  TAX_AMT             NUMBER(19,4),
  SPCL_HNDLE_AMT      NUMBER(19,4),
  SHIP_AMT            NUMBER(19,4),
  GROSS_AMT           NUMBER(19,4),
  DISCOUNT_AMT        NUMBER(19,4),
  NET_AMOUNT          NUMBER(19,4),
  DEPOSIT_AMT         NUMBER(19,4),
  DUE_AMT             NUMBER(19,4),
  PROCESSED_IND       VARCHAR2(1) default 'N',
  MATCH_IND     	  VARCHAR2(1) default 'N',
  GL_IND       		  VARCHAR2(1) default 'N',
  LAST_UPDATE_DT      TIMESTAMP(6) not null
);
alter table MM_B2B_INVOICE_T add constraint MM_B2B_INVOICE_PK1 primary key (KEY_ID);
alter table MM_B2B_INVOICE_T add constraint MM_B2B_INVOICE_TR1 foreign key (KEY_ID) references MM_XML_INVOICE_T (KEY_ID);
alter table MM_B2B_INVOICE_T add constraint MM_B2B_INVOICE_TC0 unique (OBJ_ID);