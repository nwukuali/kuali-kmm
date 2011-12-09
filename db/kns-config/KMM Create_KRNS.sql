---------------------------------------------------
-- Export file for user MMLCL                    --
-- Created by alavinas on 10/12/2010, 2:32:29 PM --
---------------------------------------------------


create table KRNS_ADHOC_RTE_ACTN_RECIP_T
(
  recip_typ_cd       NUMBER(1) not null,
  actn_rqst_cd       VARCHAR2(30) not null,
  actn_rqst_recip_id VARCHAR2(70) not null,
  obj_id             VARCHAR2(36) not null,
  ver_nbr            NUMBER(8) default 1 not null,
  doc_hdr_id         VARCHAR2(14) not null
)
;
alter table KRNS_ADHOC_RTE_ACTN_RECIP_T
  add constraint KRNS_ADHOC_RTE_ACTN_RECIP_TP1 primary key (RECIP_TYP_CD, ACTN_RQST_CD, ACTN_RQST_RECIP_ID, DOC_HDR_ID);
alter table KRNS_ADHOC_RTE_ACTN_RECIP_T
  add constraint FS_ADHOC_RTE_ACTN_RECP_TC0 unique (OBJ_ID);


create table KRNS_NTE_TYP_T
(
  nte_typ_cd   VARCHAR2(4) not null,
  obj_id       VARCHAR2(36) not null,
  ver_nbr      NUMBER(8) default 1 not null,
  typ_desc_txt VARCHAR2(100),
  actv_ind     VARCHAR2(1)
)
;
alter table KRNS_NTE_TYP_T
  add constraint KRNS_NTE_TYP_TP1 primary key (NTE_TYP_CD);
alter table KRNS_NTE_TYP_T
  add constraint SH_NTE_TYP_TC0 unique (OBJ_ID);


create table KRNS_NTE_T
(
  nte_id         NUMBER(14) not null,
  obj_id         VARCHAR2(36) not null,
  ver_nbr        NUMBER(8) default 1 not null,
  rmt_obj_id     VARCHAR2(36) not null,
  auth_prncpl_id VARCHAR2(40) not null,
  post_ts        DATE not null,
  nte_typ_cd     VARCHAR2(4) not null,
  txt            VARCHAR2(800),
  prg_cd         VARCHAR2(1),
  tpc_txt        VARCHAR2(40)
)
;
alter table KRNS_NTE_T
  add constraint KRNS_NTE_TP1 primary key (NTE_ID);
alter table KRNS_NTE_T
  add constraint SH_NTE_TC0 unique (OBJ_ID);
alter table KRNS_NTE_T
  add constraint KRNS_NTE_TR1 foreign key (NTE_TYP_CD)
  references KRNS_NTE_TYP_T (NTE_TYP_CD);


create table KRNS_ATT_T
(
  nte_id       NUMBER(14) not null,
  obj_id       VARCHAR2(36) not null,
  ver_nbr      NUMBER(8) default 1 not null,
  mime_typ     VARCHAR2(150),
  file_nm      VARCHAR2(250),
  att_id       VARCHAR2(36),
  file_sz      NUMBER(14),
  att_typ_cd   VARCHAR2(40),
  file_content BLOB
)
;
alter table KRNS_ATT_T
  add constraint KRNS_ATT_TP1 primary key (NTE_ID);
alter table KRNS_ATT_T
  add constraint SH_ATT_TC0 unique (OBJ_ID);
alter table KRNS_ATT_T
  add constraint KRNS_ATT_TR1 foreign key (NTE_ID)
  references KRNS_NTE_T (NTE_ID);


create table KRNS_CMP_TYP_T
(
  campus_typ_cd          VARCHAR2(1) not null,
  cmp_typ_nm             VARCHAR2(250),
  dobj_maint_cd_actv_ind VARCHAR2(1) not null,
  obj_id                 VARCHAR2(36) default SYS_GUID() not null,
  ver_nbr                NUMBER(8) default 1 not null,
  actv_ind               VARCHAR2(1) default 'Y' not null
)
;
alter table KRNS_CMP_TYP_T
  add constraint KRNS_CMP_TYP_TP1 primary key (CAMPUS_TYP_CD);
alter table KRNS_CMP_TYP_T
  add constraint KRNS_CMP_TYP_TC0 unique (OBJ_ID);


create table KRNS_CAMPUS_T
(
  campus_cd      VARCHAR2(2) not null,
  campus_nm      VARCHAR2(250),
  campus_shrt_nm VARCHAR2(250),
  campus_typ_cd  VARCHAR2(1),
  obj_id         VARCHAR2(36) default SYS_GUID() not null,
  ver_nbr        NUMBER(8) default 1 not null,
  actv_ind       VARCHAR2(1) default 'Y' not null
)
;
alter table KRNS_CAMPUS_T
  add constraint KRNS_CAMPUS_TP1 primary key (CAMPUS_CD);
alter table KRNS_CAMPUS_T
  add constraint KRNS_CAMPUS_TC0 unique (OBJ_ID);
alter table KRNS_CAMPUS_T
  add constraint SH_CAMPUS_TR1 foreign key (CAMPUS_TYP_CD)
  references KRNS_CMP_TYP_T (CAMPUS_TYP_CD);


create table KRNS_DOC_HDR_T
(
  doc_hdr_id      VARCHAR2(14) not null,
  obj_id          VARCHAR2(36) not null,
  ver_nbr         NUMBER(8) default 1 not null,
  fdoc_desc       VARCHAR2(40),
  org_doc_hdr_id  VARCHAR2(10),
  tmpl_doc_hdr_id VARCHAR2(14),
  explanation     VARCHAR2(400)
)
;
alter table KRNS_DOC_HDR_T
  add constraint KRNS_DOC_HDR_TP1 primary key (DOC_HDR_ID);
alter table KRNS_DOC_HDR_T
  add constraint FP_DOC_HEADER_TC0 unique (OBJ_ID);


create table KRNS_DOC_TYP_T
(
  doc_typ_cd VARCHAR2(4) not null,
  obj_id     VARCHAR2(36) default sys_guid() not null,
  ver_nbr    NUMBER(8) default 1 not null,
  nm         VARCHAR2(40),
  actv_ind   VARCHAR2(1)
)
;
alter table KRNS_DOC_TYP_T
  add constraint KRNS_DOC_TYP_TP1 primary key (DOC_TYP_CD);
alter table KRNS_DOC_TYP_T
  add constraint FP_DOC_TYPE_TC0 unique (OBJ_ID);


create table KRNS_LOOKUP_RSLT_T
(
  lookup_rslt_id VARCHAR2(14) not null,
  obj_id         VARCHAR2(36) not null,
  ver_nbr        NUMBER(8) default 1 not null,
  prncpl_id      VARCHAR2(40) not null,
  lookup_dt      DATE not null,
  serialzd_rslts CLOB
)
;
alter table KRNS_LOOKUP_RSLT_T
  add constraint KRNS_LOOKUP_RSLT_TP1 primary key (LOOKUP_RSLT_ID);
alter table KRNS_LOOKUP_RSLT_T
  add constraint FS_LOOKUP_RESULTS_MTC0 unique (OBJ_ID);


create table KRNS_LOOKUP_SEL_T
(
  lookup_rslt_id VARCHAR2(14) not null,
  obj_id         VARCHAR2(36) not null,
  ver_nbr        NUMBER(8) default 1 not null,
  prncpl_id      VARCHAR2(40) not null,
  lookup_dt      DATE not null,
  sel_obj_ids    CLOB
)
;
alter table KRNS_LOOKUP_SEL_T
  add constraint KRNS_LOOKUP_SEL_TP1 primary key (LOOKUP_RSLT_ID);
alter table KRNS_LOOKUP_SEL_T
  add constraint FS_LOOKUP_SELECTIONS_MTC0 unique (OBJ_ID);


create table KRNS_MAINT_DOC_ATT_T
(
  doc_hdr_id VARCHAR2(14) not null,
  att_cntnt  BLOB not null,
  file_nm    VARCHAR2(150),
  cntnt_typ  VARCHAR2(50),
  obj_id     VARCHAR2(36) default SYS_GUID() not null,
  ver_nbr    NUMBER(8) default 1 not null
)
;
alter table KRNS_MAINT_DOC_ATT_T
  add constraint KRNS_MAINT_DOC_ATT_TP1 primary key (DOC_HDR_ID);
alter table KRNS_MAINT_DOC_ATT_T
  add constraint FP_MAINT_DOC_ATTACHMENT_TC0 unique (OBJ_ID);


create table KRNS_MAINT_DOC_T
(
  doc_hdr_id VARCHAR2(14) not null,
  obj_id     VARCHAR2(36) not null,
  ver_nbr    NUMBER(8) default 1 not null,
  doc_cntnt  CLOB
)
;
alter table KRNS_MAINT_DOC_T
  add constraint KRNS_MAINT_DOC_TP1 primary key (DOC_HDR_ID);
alter table KRNS_MAINT_DOC_T
  add constraint FP_MAINTENANCE_DOCUMENT_TC0 unique (OBJ_ID);
alter table KRNS_MAINT_DOC_T
  add constraint KRNS_MAINT_DOC_TR1 foreign key (DOC_HDR_ID)
  references KRNS_DOC_HDR_T (DOC_HDR_ID);


create table KRNS_MAINT_LOCK_T
(
  maint_lock_id      VARCHAR2(14) not null,
  obj_id             VARCHAR2(36) not null,
  ver_nbr            NUMBER(8) default 1 not null,
  doc_hdr_id         VARCHAR2(14) not null,
  maint_lock_rep_txt VARCHAR2(500) not null
)
;
alter table KRNS_MAINT_LOCK_T
  add constraint KRNS_MAINT_LOCK_TP1 primary key (MAINT_LOCK_ID);
alter table KRNS_MAINT_LOCK_T
  add constraint KRNS_MAINT_LOCK_TC1 unique (OBJ_ID);


create table KRNS_NMSPC_T
(
  nmspc_cd      VARCHAR2(20) not null,
  obj_id        VARCHAR2(36) default SYS_GUID() not null,
  ver_nbr       NUMBER(8) default 1 not null,
  nm            VARCHAR2(40),
  actv_ind      CHAR(1) default 'Y' not null,
  appl_nmspc_cd VARCHAR2(20)
)
;
alter table KRNS_NMSPC_T
  add constraint KRNS_NMSPC_TP1 primary key (NMSPC_CD);
alter table KRNS_NMSPC_T
  add constraint KRNS_NMSPC_TC0 unique (OBJ_ID);


create table KRNS_PARM_DTL_TYP_T
(
  nmspc_cd        VARCHAR2(20) not null,
  parm_dtl_typ_cd VARCHAR2(100) not null,
  obj_id          VARCHAR2(36) default SYS_GUID() not null,
  ver_nbr         NUMBER(8) default 1 not null,
  nm              VARCHAR2(255),
  actv_ind        CHAR(1) default 'Y' not null
)
;
alter table KRNS_PARM_DTL_TYP_T
  add constraint KRNS_PARM_DTL_TYP_TP1 primary key (NMSPC_CD, PARM_DTL_TYP_CD);
alter table KRNS_PARM_DTL_TYP_T
  add constraint KRNS_PARM_DTL_TYP_TC0 unique (OBJ_ID);
alter table KRNS_PARM_DTL_TYP_T
  add constraint KRNS_PARM_DTL_TYP_TR1 foreign key (NMSPC_CD)
  references KRNS_NMSPC_T (NMSPC_CD);


create table KRNS_PARM_TYP_T
(
  parm_typ_cd VARCHAR2(5) not null,
  obj_id      VARCHAR2(36) default SYS_GUID() not null,
  ver_nbr     NUMBER(8) default 1 not null,
  nm          VARCHAR2(40),
  actv_ind    CHAR(1) default 'Y' not null
)
;
alter table KRNS_PARM_TYP_T
  add constraint KRNS_PARM_TYP_TP1 primary key (PARM_TYP_CD);
alter table KRNS_PARM_TYP_T
  add constraint KRNS_PARM_TYP_TC0 unique (OBJ_ID);


create table KRNS_PARM_T
(
  nmspc_cd        VARCHAR2(20) not null,
  parm_dtl_typ_cd VARCHAR2(100) not null,
  parm_nm         VARCHAR2(255) not null,
  obj_id          VARCHAR2(36) default SYS_GUID() not null,
  ver_nbr         NUMBER(8) default 1 not null,
  parm_typ_cd     VARCHAR2(5) not null,
  txt             VARCHAR2(4000),
  parm_desc_txt   VARCHAR2(4000),
  cons_cd         VARCHAR2(1),
  appl_nmspc_cd   VARCHAR2(20) default 'KUALI' not null
)
;
alter table KRNS_PARM_T
  add constraint KRNS_PARM_TP1 primary key (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, APPL_NMSPC_CD);
alter table KRNS_PARM_T
  add constraint KRNS_PARM_TC0 unique (OBJ_ID);
alter table KRNS_PARM_T
  add constraint KRNS_PARM_TR1 foreign key (NMSPC_CD)
  references KRNS_NMSPC_T (NMSPC_CD);
alter table KRNS_PARM_T
  add constraint KRNS_PARM_TR2 foreign key (PARM_TYP_CD)
  references KRNS_PARM_TYP_T (PARM_TYP_CD);

create table KRNS_PESSIMISTIC_LOCK_T
(
  pessimistic_lock_id NUMBER(14) not null,
  obj_id              VARCHAR2(36) default SYS_GUID() not null,
  ver_nbr             NUMBER(8) default 1 not null,
  lock_desc_txt       VARCHAR2(4000),
  doc_hdr_id          VARCHAR2(14) not null,
  gnrt_dt             DATE not null,
  prncpl_id           VARCHAR2(40) not null
)
;
alter table KRNS_PESSIMISTIC_LOCK_T
  add constraint KRNS_PESSIMISTIC_LOCK_TP1 primary key (PESSIMISTIC_LOCK_ID);
alter table KRNS_PESSIMISTIC_LOCK_T
  add constraint KNS_PESSIMISTIC_LOCK_TC0 unique (OBJ_ID);


create table KRNS_SESN_DOC_T
(
  sesn_doc_id           VARCHAR2(40) not null,
  doc_hdr_id            VARCHAR2(14) not null,
  prncpl_id             VARCHAR2(40) not null,
  ip_addr               VARCHAR2(60) not null,
  serialzd_doc_frm      BLOB,
  last_updt_dt          DATE,
  content_encrypted_ind CHAR(1) default 'N'
)
;
alter table KRNS_SESN_DOC_T
  add constraint KRNS_SESN_DOC_TP1 primary key (SESN_DOC_ID, DOC_HDR_ID, PRNCPL_ID, IP_ADDR);




