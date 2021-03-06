drop sequence MM_GL_ENTRY_ID_S;
create sequence MM_GL_ENTRY_ID_S start with 5000 increment by 1;
-- Create table
drop table MM_GL_PENDING_ENTRY_T;
create table MM_GL_PENDING_ENTRY_T
(
  GL_ENTRY_ID        NUMBER not null,
  WAREHOUSE_CD       VARCHAR2(2) not null,
  FS_ORIGIN_CD       VARCHAR2(2) not null,
  FDOC_NBR           VARCHAR2(14) not null,
  OBJ_ID             VARCHAR2(36) not null,
  VER_NBR            NUMBER(8) default 1 not null,
  FIN_COA_CD         VARCHAR2(2),
  ACCOUNT_NBR        VARCHAR2(7),
  SUB_ACCT_NBR       VARCHAR2(5),
  FIN_OBJECT_CD      VARCHAR2(4),
  FIN_SUB_OBJ_CD     VARCHAR2(3),
  FIN_BALANCE_TYP_CD VARCHAR2(2),
  FIN_OBJ_TYP_CD     VARCHAR2(2),
  UNIV_FISCAL_YR     NUMBER(4),
  UNIV_FISCAL_PRD_CD VARCHAR2(2),
  TRN_LDGR_ENTR_DESC VARCHAR2(40),
  TRN_LDGR_ENTR_AMT  NUMBER(19,2),
  TRN_DEBIT_CRDT_CD  VARCHAR2(1),
  TRANSACTION_DT     DATE,
  FDOC_TYP_CD        VARCHAR2(4),
  ORG_DOC_NBR        VARCHAR2(10),
  PROJECT_CD         VARCHAR2(10),
  ORG_REFERENCE_ID   VARCHAR2(8),
  FDOC_REF_TYP_CD    VARCHAR2(4),
  FS_REF_ORIGIN_CD   VARCHAR2(2),
  FDOC_REF_NBR       VARCHAR2(14),
  FDOC_REVERSAL_DT   DATE,
  TRN_ENCUM_UPDT_CD  VARCHAR2(1),
  FDOC_APPROVED_CD   VARCHAR2(1),
  ACCT_SF_FINOBJ_CD  VARCHAR2(4),
  TRN_ENTR_OFST_CD   VARCHAR2(1),
  TRNENTR_PROCESS_TM DATE
);
alter table MM_GL_PENDING_ENTRY_T add constraint MM_GL_PENDING_ENTRY_TP1 primary key (GL_ENTRY_ID);  
alter table MM_GL_PENDING_ENTRY_T add constraint MM_GL_PENDING_ENTRY_TC0 unique (OBJ_ID);
