-- Changes for the MSU Implementation

alter table MM_MARKUP_T
  modify MARKUP_ORG_CD         VARCHAR2(32);

alter table MM_PROFILE_T
  modify ORG_CD                VARCHAR2(32);

alter table MM_WAREHOUSE_T
  modify DEFAULT_ORG_CD        VARCHAR2(32);

-- RS_CA_0008 - Account Number increased to 8 characters

alter table MM_ACCOUNTS_T
  modify ACCOUNT_NBR          VARCHAR2(32);

alter table MM_MARKUP_T
  modify MARKUP_ACCOUNT_NBR   VARCHAR2(32);

alter table MM_PROFILE_T
  modify ACCOUNT_NBR          VARCHAR2(32);

alter table MM_WAREHOUSE_ACCOUNTS_T
  modify ACCOUNT_NBR          VARCHAR2(32);

-- TS_GL_0073 - Increase length of sub-object code to 6 characters

alter table MM_ACCOUNTS_T
  modify FIN_SUB_OBJ_CD       VARCHAR2(6);
  
alter table MM_WAREHOUSE_ACCOUNTS_T
  modify FIN_SUB_OBJ_CD       VARCHAR2(6);
alter table MM_WAREHOUSE_ACCOUNTS_T
  modify OFFSET_SUB_OBJ_CD    VARCHAR2(6);

alter table MM_WAREHOUSE_OBJECT_T
  modify FIN_SUB_OBJ_CD       VARCHAR2(6);
alter table MM_WAREHOUSE_OBJECT_T
  modify OFFSET_SUB_OBJ_CD    VARCHAR2(6);

-- Need a log table to control the transactions sent to the carousel
  
create table MM_CAROUSEL_LOG_T (
       CAROUSEL_LINE_ID      NUMBER(18) not null,
       CAROUSEL_LINE_TYPE_CD varchar2(3) not null,
       OBJ_ID                VARCHAR2(36) default sys_guid() not null,
       VER_NBR               NUMBER(8) default 1 not null,
       LAST_UPDATE_DT        TIMESTAMP(6) not null
 );
 
 alter table MM_CAROUSEL_LOG_T
    add constraint CAROUSEL_LOG_TP1 
    primary key(CAROUSEL_LINE_ID, CAROUSEL_LINE_TYPE_CD);
       
 alter table MM_CAROUSEL_LOG_T
    add constraint CAROUSEL_LOG_TC0 unique(OBJ_ID);  
    