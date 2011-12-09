create table MM_B2B_INVOICE_DTL_T
(
  INV_DTL_ID          NUMBER NOT NULL,
  OBJ_ID              VARCHAR2(36) not null,
  VER_NBR             NUMBER(8) default 1 not null,
  KEY_ID              VARCHAR2(100) not null,
  SUPPLIER_PART_ID    VARCHAR2(100) not null,
  ORDER_ID            VARCHAR2(30),
  ORDER_DETAIL_ID     NUMBER(18),
  LINE_NBR            NUMBER(8),
  ITEM_QTY            NUMBER(11,4) not null,
  UOM_CODE            VARCHAR2(10),
  UNIT_COST           NUMBER(19,4),
  SUB_TOTAL_AMT       NUMBER(19,4),
  TAX_AMT             NUMBER(19,4),
  SPCL_HNDLE_AMT      NUMBER(19,4),
  SHIP_AMT            NUMBER(19,4),
  GROSS_AMT           NUMBER(19,4),
  DISCOUNT_AMT        NUMBER(19,4),
  NET_AMOUNT          NUMBER(19,4),
  DEPOSIT_AMT         NUMBER(19,4),
  MATCH_IND     	  VARCHAR2(1) default 'N',
  GL_IND       		  VARCHAR2(1) default 'N',
  LAST_UPDATE_DT      TIMESTAMP(6) not null
);
alter table MM_B2B_INVOICE_DTL_T add constraint MM_B2B_INVOICE_DTL_PK1 primary key (INV_DTL_ID);
alter table MM_B2B_INVOICE_DTL_T add constraint MM_B2B_INVOICE_DTL_TR1 foreign key (KEY_ID) references MM_B2B_INVOICE_T (KEY_ID);
alter table MM_B2B_INVOICE_DTL_T add constraint MM_B2B_INVOICE_DTL_TC0 unique (OBJ_ID);
