--defining a separate permission for initiating SCHK 
INSERT INTO KRIM_PERM_T ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
KRIM_PERM_ID_S.NEXTVAL,  sys_guid(), 1, '10', 'KFS-MM', 'Initiate Checkin Document'
, 'Authorizes the initiation of the  Checkin  Document.', 'Y');

--defining the document type attribute for the new permission created above
INSERT INTO KRIM_PERM_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
KRIM_PERM_DATA_ATTR_ID_S.NEXTVAL ,  sys_guid() , 1, KRIM_PERM_ID_S.CURRVAL, '9', '13', 'SCHK');

INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
KRIM_ROLE_PERM_ID_S.NEXTVAL ,sys_guid(), 1,(select role_id  from KRIM_role_t t where t.role_nm  = 'Store worker 1' and t.nmspc_cd = 'KFS-MM'), KRIM_PERM_ID_S.CURRVAL, 'Y'); 

--defining a separate permission for initiating SRET
INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
KRIM_ROLE_MBR_ID_S.NEXTVAL , 1, sys_guid(), (select role_id  from KRIM_role_t t where t.role_nm  = 'Store worker 1' and t.nmspc_cd = 'KFS-MM'), '1', 'P', NULL, NULL,  SYSDATE); 

commit;