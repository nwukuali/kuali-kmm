INSERT INTO KRIM_PERM_T 
       ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND ) 
VALUES ( KRIM_PERM_ID_S.nextval, sys_guid(), 1, 
         (select aa.PERM_TMPL_ID from KRIM_PERM_TMPL_T aa where aa.NMSPC_CD = 'KR-SYS' and aa.NM = 'Initiate Document' and aa.ACTV_IND = 'Y'),
         'KFS-MM', 'Initiate Document', 'Authorizes the initiation of the Worksheet Document.', 'Y'); 

INSERT INTO KRIM_PERM_ATTR_DATA_T 
       ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL ) 
VALUES ( KRIM_ATTR_DATA_ID_S.nextval, sys_guid(), 1, 
         (select aa.PERM_ID from KRIM_PERM_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NM = 'Initiate Document' and aa.ACTV_IND = 'Y'), 
         (select aa.KIM_TYP_ID from KRIM_TYP_T aa where aa.NM = 'Document Type & Attachment Type' and aa.SRVC_NM = 'documentTypeAndAttachmentTypePermissionTypeService' and aa.ACTV_IND = 'Y'), 
         (select aa.KIM_ATTR_DEFN_ID from KRIM_ATTR_DEFN_T aa where aa.NM = 'documentTypeName' and aa.NMSPC_CD = 'KR-WKFLW' and aa.ACTV_IND = 'Y'), 
         'SWKC');

INSERT INTO KRIM_PERM_TMPL_T 
       ( PERM_TMPL_ID, OBJ_ID, VER_NBR, NMSPC_CD, NM, DESC_TXT, KIM_TYP_ID, ACTV_IND ) 
VALUES ( KRIM_PERM_TMPL_ID_S.nextval, sys_guid(), 1, 'KR-SYS', 'Edit Worksheet', NULL, 
         (select aa.KIM_TYP_ID from KRIM_TYP_T aa where aa.NM = 'Document Type (Permission)' and aa.SRVC_NM = 'documentTypePermissionTypeService' and aa.ACTV_IND = 'Y'), 
         'Y'); 

INSERT INTO KRIM_ROLE_T 
       ( ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT ) 
VALUES ( KRIM_ROLE_ID_S.nextval, sys_guid(), 1, 'Commodity Specialist', 'KFS-MM', 'This role creates an prints the  worksheets', 
         (select aa.KIM_TYP_ID from KRIM_TYP_T aa where aa.NM = 'Default' and aa.ACTV_IND = 'Y'), 
         'Y',  SYSDATE);

INSERT INTO KRIM_PERM_T 
       ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND ) 
VALUES ( KRIM_PERM_ID_S.nextval, sys_guid(), 1, 
         (select aa.PERM_TMPL_ID from KRIM_PERM_TMPL_T aa where aa.NMSPC_CD = 'KR-SYS' and aa.NM = 'Edit Worksheet' and aa.ACTV_IND = 'Y'),
         'KFS-MM', 'Edit Worksheet', 'Authorizes the Editing of the Worksheet Document.', 'Y'); 

INSERT INTO KRIM_ROLE_PERM_T 
       ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND ) 
VALUES ( KRIM_ROLE_ID_S.nextval, sys_guid(), 1, 
         (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'Commodity Specialist' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
         (select aa.PERM_ID from KRIM_PERM_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NM = 'Edit Worksheet' and aa.ACTV_IND = 'Y'), 
         'Y'); 

INSERT INTO KRIM_ROLE_PERM_T 
       ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND ) 
VALUES ( KRIM_ROLE_ID_S.nextval, sys_guid(), 1, 
         (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'Commodity Specialist' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
         (select aa.PERM_ID from KRIM_PERM_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NMSPC_CD = 'KUALI' and aa.NM = 'Route Document' and aa.ACTV_IND = 'Y'), 
         'Y'); 

INSERT INTO KRIM_ROLE_T 
       ( ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT ) 
VALUES ( KRIM_ROLE_ID_S.nextval, sys_guid(), 1, 'Warehouse Manager', 'KFS-MM', 'This role reviews  worksheets',
         (select aa.KIM_TYP_ID from KRIM_TYP_T aa where aa.NM = 'Default' and aa.ACTV_IND = 'Y'), 
         'Y',  SYSDATE);

INSERT INTO KRIM_RSP_T 
       ( RSP_ID, OBJ_ID, VER_NBR, RSP_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND ) 
VALUES ( KRIM_RSP_ID_S.nextval, sys_guid(), 1, 
         (select aa.RSP_TMPL_ID from KRIM_RSP_TMPL_T aa where aa.NMSPC_CD = 'KR-WKFLW' and aa.NM = 'Review' and aa.ACTV_IND = 'Y'), 
         'KFS-MM', 'Review', NULL, 'Y');

INSERT INTO KRIM_RSP_ATTR_DATA_T 
       ( ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL ) 
VALUES ( KRIM_ATTR_DATA_ID_S.nextval, sys_guid(), 1, 
         (select aa.RSP_ID from KRIM_RSP_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NM = 'Review' and aa.ACTV_IND = 'Y'), 
         (select aa.KIM_TYP_ID from KRIM_TYP_T aa where aa.NM = 'Document Type, Routing Node & Action Information' and aa.SRVC_NM = 'reviewResponsibilityTypeService' and aa.ACTV_IND = 'Y'), 
         (select aa.KIM_ATTR_DEFN_ID from KRIM_ATTR_DEFN_T aa where aa.NM = 'documentTypeName' and aa.NMSPC_CD = 'KR-WKFLW' and aa.ACTV_IND = 'Y'), 
         'SWC'); 

INSERT INTO KRIM_RSP_ATTR_DATA_T 
       ( ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL ) 
VALUES ( KRIM_ATTR_DATA_ID_S.nextval, sys_guid(), 1, 
         (select aa.RSP_ID from KRIM_RSP_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NM = 'Review' and aa.ACTV_IND = 'Y'), 
         (select aa.KIM_TYP_ID from KRIM_TYP_T aa where aa.NM = 'Document Type, Routing Node & Action Information' and aa.SRVC_NM = 'reviewResponsibilityTypeService' and aa.ACTV_IND = 'Y'), 
         (select aa.KIM_ATTR_DEFN_ID from KRIM_ATTR_DEFN_T aa where aa.NM = 'routeNodeName' and aa.NMSPC_CD = 'KR-WKFLW' and aa.ACTV_IND = 'Y'), 
         'CycleCountWorksheetReview'); 

INSERT INTO KRIM_ROLE_RSP_T 
       ( ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID, ACTV_IND ) 
VALUES ( KRIM_ROLE_RSP_ID_S.nextval, sys_guid(), 1, 
         (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'Warehouse Manager' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
         (select aa.RSP_ID from KRIM_RSP_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NM = 'Review' and aa.ACTV_IND = 'Y'), 
         'Y'); 

INSERT INTO KRIM_ROLE_RSP_ACTN_T 
       ( ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN ) 
VALUES ( KRIM_ROLE_RSP_ACTN_ID_S.nextval, sys_guid(), 1, 'A', 1, 'A', '*', 
         (select aa.ROLE_RSP_ID from KRIM_ROLE_RSP_T aa where aa.ROLE_ID = (select ab.ROLE_ID from KRIM_ROLE_T ab where ab.ROLE_NM = 'Warehouse Manager' and ab.NMSPC_CD = 'KFS-MM' and ab.ACTV_IND = 'Y')
                                                          and aa.RSP_ID = (select ab.RSP_ID from KRIM_RSP_T ab where ab.NMSPC_CD = 'KFS-MM' and ab.NM = 'Review' and ab.ACTV_IND = 'Y') ), 
         'N'); 

INSERT INTO KRIM_ROLE_MBR_T 
       ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) 
VALUES ( KRIM_ROLE_MBR_ID_S.nextval, 1, sys_guid(), 
         (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'Warehouse Manager' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
         (select aa.PRNCPL_ID from KRIM_PRNCPL_T aa where aa.PRNCPL_NM = 'lminter' and aa.ACTV_IND = 'Y'), 
         'P', NULL, NULL,  SYSDATE); 

INSERT INTO KRIM_ROLE_PERM_T 
       ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND ) 
VALUES ( KRIM_ROLE_ID_S.nextval, sys_guid(), 1, 
         (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'Warehouse Manager' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
         (select aa.PERM_ID from KRIM_PERM_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NMSPC_CD = 'KFS-MM' and aa.NM = 'Initiate Document' and aa.ACTV_IND = 'Y'), 
         'Y'); 

INSERT INTO KRIM_ROLE_T 
       ( ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT ) 
VALUES ( KRIM_ROLE_ID_S.nextval, sys_guid(), 1, 'Store worker 1', 'KFS-MM', 'This role creates an prints the worksheets', 
         (select aa.KIM_TYP_ID from KRIM_TYP_T aa where aa.NM = 'Default' and aa.ACTV_IND = 'Y'), 
         'Y',  SYSDATE);

INSERT INTO KRIM_ROLE_PERM_T 
       ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND ) 
VALUES ( KRIM_ROLE_ID_S.nextval, sys_guid(), 1, 
         (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'Commodity Specialist' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
         (select aa.PERM_ID from KRIM_PERM_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NM = 'Initiate Document' and aa.ACTV_IND = 'Y'), 
         'Y'); 

INSERT INTO KRIM_ROLE_PERM_T 
       ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND ) 
VALUES ( KRIM_ROLE_ID_S.nextval, sys_guid(), 1, 
         (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'Commodity Specialist' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
         (select aa.PERM_ID from KRIM_PERM_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NM = 'Use Screen' and aa.ACTV_IND = 'Y'), 
         'Y'); 

INSERT INTO KRIM_PERM_ATTR_DATA_T 
       ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL ) 
VALUES ( KRIM_ATTR_DATA_ID_S.nextval, sys_guid(), 1, 
         (select aa.PERM_ID from KRIM_PERM_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NM = 'Use Screen' and aa.ACTV_IND = 'Y'), 
         (select aa.KIM_TYP_ID from KRIM_TYP_T aa where aa.NM = 'Namespace or Action' and aa.SRVC_NM = 'namespaceOrActionPermissionTypeService' and aa.ACTV_IND = 'Y'), 
         (select aa.KIM_ATTR_DEFN_ID from KRIM_ATTR_DEFN_T aa where aa.NM = 'actionClass' and aa.NMSPC_CD = 'KR-SYS' and aa.ACTV_IND = 'Y'), 
         'org.kuali.ext.mm.document.web.struts.CountWorksheetPrintAction');

INSERT INTO KRIM_PERM_ATTR_DATA_T 
       ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL ) 
VALUES ( KRIM_ATTR_DATA_ID_S.nextval, sys_guid(), 1, 
         (select aa.PERM_ID from KRIM_PERM_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NM = 'Use Screen' and aa.ACTV_IND = 'Y'), 
         (select aa.KIM_TYP_ID from KRIM_TYP_T aa where aa.NM = 'Namespace or Action' and aa.SRVC_NM = 'namespaceOrActionPermissionTypeService' and aa.ACTV_IND = 'Y'), 
         (select aa.KIM_ATTR_DEFN_ID from KRIM_ATTR_DEFN_T aa where aa.NM = 'namespaceCode' and aa.NMSPC_CD = 'KR-NS' and aa.ACTV_IND = 'Y'), 
         'KFS-MM');
