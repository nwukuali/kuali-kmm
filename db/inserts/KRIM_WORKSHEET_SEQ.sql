INSERT INTO KRNS_NMSPC_T ( NMSPC_CD, OBJ_ID, VER_NBR, NM, ACTV_IND,
APPL_NMSPC_CD ) VALUES ( 
'KFS-MM', sys_guid(), 1, 'Materiel Management', 'Y', NULL);

INSERT INTO KRIM_ROLE_T ( ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID,
ACTV_IND, LAST_UPDT_DT ) VALUES ( 
KRIM_ROLE_ID_S.NEXTVAL, sys_guid(), 1, 'Commodity Specialist', 'KFS-MM', 
'This role creates an prints the  worksheets'
, '1', 'Y',  TO_Date( '11/08/2008 11:55:20 AM', 'MM/DD/YYYY HH:MI:SS AM'));

INSERT INTO KRIM_PERM_TMPL_T ( PERM_TMPL_ID, OBJ_ID, VER_NBR, NMSPC_CD, NM, DESC_TXT, KIM_TYP_ID,
ACTV_IND ) VALUES ( 
'60', sys_guid(), 1, 'KR-SYS', 'Edit Worksheet', NULL, '3'
, 'Y'); 

INSERT INTO KRIM_PERM_T ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
KRIM_PERM_ID_S.NEXTVAL,  sys_guid(), 1, '60', 'KFS-MM', 'Initiate Document'
, 'Authorizes the initiation of the  Worksheet Document.', 'Y'); 

INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
KRIM_ROLE_MBR_ID_S.NEXTVAL, 1,  sys_guid(), KRIM_ROLE_ID_S.CURRVAL, '2321500336', 'P', NULL, NULL,  TO_Date( '12/08/2008 12:49:41 PM', 'MM/DD/YYYY HH:MI:SS AM')); 

INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
KRIM_ROLE_PERM_ID_S.NEXTVAL,  sys_guid(), 1, KRIM_ROLE_ID_S.CURRVAL, KRIM_PERM_ID_S.CURRVAL, 'Y'); 

INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
KRIM_ROLE_PERM_ID_S.NEXTVAL,  sys_guid(), 1, KRIM_ROLE_ID_S.CURRVAL, '168', 'Y'); 

INSERT INTO KRIM_RSP_T ( RSP_ID, OBJ_ID, VER_NBR, RSP_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
KRIM_RSP_ID_S.NEXTVAL
, sys_guid(), 0, '1', 'KFS-MM', 'Review', NULL, 'Y');

INSERT INTO KRIM_ROLE_T ( ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID,
ACTV_IND, LAST_UPDT_DT ) VALUES ( 
KRIM_ROLE_ID_S.NEXTVAL, sys_guid(), 1, 'Warehouse Manager', 'KFS-MM', 'This role reviews  worksheets'
, '1', 'Y',  TO_Date( '11/08/2008 11:55:20 AM', 'MM/DD/YYYY HH:MI:SS AM'));

INSERT INTO KRIM_ROLE_RSP_T ( ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID,
ACTV_IND ) VALUES ( 
KRIM_ROLE_RSP_ID_S.NEXTVAL, sys_guid(), 1, KRIM_ROLE_ID_S.CURRVAL, KRIM_RSP_ID_S.CURRVAL, 'Y'); 


INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, sys_guid(), KRIM_ROLE_ID_S.CURRVAL, '5594003018', 'P', NULL, NULL
,  TO_Date( '12/08/2008 12:49:41 PM', 'MM/DD/YYYY HH:MI:SS AM'));

INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
KRIM_ROLE_PERM_ID_S.NEXTVAL, sys_guid(), 1, KRIM_ROLE_ID_S.CURRVAL, '149', 'Y');

INSERT INTO KRIM_RSP_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
'505', sys_guid(), 1, KRIM_RSP_ID_S.CURRVAL, '7', '13', 'SWKC'); 

INSERT INTO KRIM_RSP_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
'502', sys_guid(), 1,  KRIM_RSP_ID_S.CURRVAL, '7', '16', 'CycleCountWorksheetReview');



INSERT INTO KRIM_ROLE_RSP_ACTN_T ( ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR,
ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN ) VALUES ( 
KRIM_RSP_RQRD_ATTR_ID_S.NEXTVAL, sys_guid(), 1, 'A', 1, 'A', '*', KRIM_ROLE_RSP_ID_S.CURRVAL, 'N'); 

INSERT INTO KRIM_ROLE_T ( ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID,
ACTV_IND, LAST_UPDT_DT ) VALUES ( 
KRIM_ROLE_ID_S.NEXTVAL, sys_guid(), 1, 'Store worker 1', 'KFS-MM', 
'This role creates an prints the  worksheets'
, '1', 'Y',  TO_Date( '11/08/2008 11:55:20 AM', 'MM/DD/YYYY HH:MI:SS AM'));

INSERT INTO KRIM_PERM_T ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
KRIM_PERM_ID_S.NEXTVAL,  sys_guid(), 1, '10', 'KFS-MM', 'Initiate Document'
, 'Authorizes the initiation of the  Worksheet Document.', 'Y'); 

INSERT INTO KRIM_PERM_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
KRIM_PERM_DATA_ATTR_ID_S.NEXTVAL,  sys_guid() , 1, KRIM_PERM_ID_S.CURRVAL, '9', '13', 'SWKC'); 

INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
KRIM_ROLE_MBR_ID_S.NEXTVAL, 1,  sys_guid(), KRIM_ROLE_ID_S.CURRVAL, '3', 'P', NULL, NULL,  TO_Date( '12/08/2008 12:49:41 PM', 'MM/DD/YYYY HH:MI:SS AM')); 

INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, sys_guid(), KRIM_ROLE_ID_S.CURRVAL, '5594003018', 'P', NULL, NULL
,  TO_Date( '12/08/2008 12:49:41 PM', 'MM/DD/YYYY HH:MI:SS AM'));

INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
KRIM_ROLE_PERM_ID_S.NEXTVAL,  sys_guid(), 1, KRIM_ROLE_ID_S.CURRVAL, KRIM_PERM_ID_S.CURRVAL, 'Y'); 

INSERT INTO KRIM_PERM_T ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, '29', 'KFS-MM', 'Use Screen', 'Allows users to create Construction Selection.'
, 'Y'); 

INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
'804', SYS_GUID(), 1, KRIM_ROLE_ID_S.CURRVAL, KRIM_PERM_ID_S.CURRVAL, 'Y'); 

INSERT INTO KRIM_PERM_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
KRIM_PERM_DATA_ATTR_ID_S.NEXTVAL , sys_guid(), 1, KRIM_PERM_ID_S.CURRVAL, '12', '2', 'org.kuali.ext.mm.document.web.struts.CountWorksheetPrintAction'); 

INSERT INTO KRIM_PERM_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
KRIM_PERM_DATA_ATTR_ID_S.NEXTVAL , sys_guid(), 1, KRIM_PERM_ID_S.CURRVAL, '12', '4', 'KFS-MM');

COMMIT;