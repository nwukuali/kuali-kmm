
INSERT INTO KRIM_RSP_T ( RSP_ID, OBJ_ID, VER_NBR, RSP_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
KRIM_RSP_ID_S.NEXTVAL ,sys_guid(), 0, '1', 'KFS-MM', 'Review', 'Review Responsibility for Reorder Document' , 'Y');

-- defining the Responsibility Attribute = Document Type
INSERT INTO KRIM_RSP_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES (
KRIM_RSP_RQRD_ATTR_ID_S.NEXTVAL , sys_guid(), 1, KRIM_RSP_ID_S.CURRVAL, '7', '13', 'SROR'); 

-- Defining a Node for the same responsibility (Node = ReorderDocumentReview)
INSERT INTO KRIM_RSP_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
KRIM_RSP_RQRD_ATTR_ID_S.NEXTVAL ,sys_guid(), 1, KRIM_RSP_ID_S.CURRVAL, '7', '16', 'ReorderDocumentReview'); 

-----
INSERT INTO KRIM_ROLE_RSP_T ( ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID,
ACTV_IND ) VALUES ( 
KRIM_ROLE_RSP_ID_S.NEXTVAL
, sys_guid(), 1, (select ROLE_ID from KRIM_ROLE_T where Role_NM = 'Warehouse Manager') , KRIM_RSP_ID_S.CURRVAL , 'Y'); 

-- Defining Approval Action for the Review Responsibility
INSERT INTO KRIM_ROLE_RSP_ACTN_T ( ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR,
ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN ) VALUES ( 
KRIM_ROLE_RSP_ACTN_ID_S.nextval
, sys_guid(), 1, 'A', 1, 'A', '*', KRIM_ROLE_RSP_ID_S.CURRVAL, 'N'); 

INSERT INTO KRIM_PERM_T ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
KRIM_PERM_ID_S.NEXTVAL,  sys_guid(), 1, '10', 'KFS-MM', 'Initiate Document'
, 'Authorizes the initiation of the Stores Reorder Document', 'Y'); 

INSERT INTO KRIM_PERM_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
KRIM_PERM_DATA_ATTR_ID_S.NEXTVAL,  sys_guid() , 1, KRIM_PERM_ID_S.currval, '9', '13', 'SROR'); 

INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
KRIM_ROLE_PERM_ID_S.NEXTVAL,  sys_guid(), 1, (select role_id from krim_role_t jj
where jj.ROLE_NM = 'Commodity Specialist'), KRIM_PERM_ID_S.CURRVAL, 'Y');

commit;