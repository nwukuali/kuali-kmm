INSERT INTO KRIM_PERM_TMPL_T ( PERM_TMPL_ID, OBJ_ID, VER_NBR, NMSPC_CD, NM, DESC_TXT, KIM_TYP_ID,
ACTV_IND ) VALUES ( 
'60', sys_guid(), 1, 'KR-SYS', 'Edit Worksheet', 'This is to be used in MM module for Count Worksheet' , '3'
, 'Y'); 

INSERT INTO KRIM_PERM_T ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
'802', sys_guid(), 1, '60', 'KFS-MM', 'Edit Worksheet'
, 'Authorizes the Editing of the  Worksheet Document.', 'Y'); 



INSERT INTO KRIM_PERM_T ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
'803',  sys_guid(), 1, '10', 'KFS-MM', 'Initiate Document'
, 'Authorizes the initiation of the  Worksheet Document.', 'Y'); 

INSERT INTO KRIM_PERM_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
'800',  sys_guid() , 1, '803', '9', '13', 'SWKC');


---
INSERT INTO KRIM_ROLE_T ( ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID,
ACTV_IND, LAST_UPDT_DT ) VALUES ( 
'99' , sys_guid(),1, 'Commodity Specialist', 'KFS-MM', 
'This role creates an prints the  worksheets'
, '1', 'Y',  SYSDATE);

INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
'802',sys_guid(), 1, '99', '802', 'Y'); 

INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
'803', sys_guid(), 1, '99', '168', 'Y'); 

-- dmcummin
INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
'1710', 1, sys_guid(), '99', '2321500336', 'P', NULL, NULL,  SYSDATE); 



INSERT INTO KRIM_ROLE_T ( ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID,
ACTV_IND, LAST_UPDT_DT ) VALUES ( 
'100', sys_guid(), 1, 'Warehouse Manager', 'KFS-MM', 'This role reviews  worksheets'
, '1', 'Y',  SYSDATE);

INSERT INTO KRIM_RSP_T ( RSP_ID, OBJ_ID, VER_NBR, RSP_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
'126',sys_guid(), 0, '1', 'KFS-MM', 'Review', NULL, 'Y');

-- defining the Responsibility Attribute = Document Type
INSERT INTO KRIM_RSP_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES (
'505', sys_guid(), 1, '126', '7', '13', 'SWKC'); 

-- Defining a Node for the same responsibility (Node = CycleCountWorksheetReview)
INSERT INTO KRIM_RSP_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
'502',sys_guid(), 1, '126', '7', '16', 'CycleCountWorksheetReview'); 

INSERT INTO KRIM_ROLE_RSP_T ( ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID,
ACTV_IND ) VALUES ( 
'1127', sys_guid(), 1, '100', '126', 'Y'); 

-- Defining Approval Action for the Review Responsibility
INSERT INTO KRIM_ROLE_RSP_ACTN_T ( ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR,
ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN ) VALUES ( 
'302', sys_guid(), 1, 'A', 1, 'A', '*', '1127', 'N'); 

-- Associating the member to the role ( lminter to the Workflow Manger Role) 
INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
'1712', 1, sys_guid(), '100', '5594003018', 'P', NULL, NULL
,  SYSDATE);

-- assigning the Initiate permissin to the roles
INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
'811',  sys_guid(), 1, '100','803' , 'Y'); 
INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
'812',  sys_guid(), 1, '101','803' , 'Y'); 

-- Creating the 'Store worker 1' Role

INSERT INTO KRIM_ROLE_T ( ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID,
ACTV_IND, LAST_UPDT_DT ) VALUES ( 
'101', sys_guid(), 1, 'Store worker 1', 'KFS-MM', 
'This role creates an prints the  worksheets'
, '1', 'Y',  SYSDATE);

-- Generating "Use Screen" permission for our namespace
INSERT INTO KRIM_PERM_T ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
'804', SYS_GUID(), 1, '29', 'KFS-MM', 'Use Screen', 'Allows users to create Construction Selection.'
, 'Y'); 

-- Assigning the above permission to the rol = Store worker 1
INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
'804', SYS_GUID(), 1, '101', '804', 'Y'); 

-- adding the class name as an attribute for "Use Screen" permission
INSERT INTO KRIM_PERM_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
'801', sys_guid(), 1, '804', '12', '2', 'org.kuali.ext.mm.document.web.struts.CountWorksheetPrintAction'); 

-- Adding our namespace as an attribute for "Use Screen" permission
INSERT INTO KRIM_PERM_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
'805', sys_guid(), 1, '804', '12', '4', 'KFS-MM');

-- Adding "admin" as a  member of "Store worker 1"
INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
'1500', 1,  sys_guid(), '101', '3', 'P', NULL, NULL,  SYSDATE ); 

-- Adding "lminter" to the "Store worker 1" Role
INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
'1501', 1, sys_guid(), '101', '5594003018', 'P', NULL, NULL
,  SYSDATE );

-- Creating the "lookup" permission for the our Namespace
 INSERT INTO KRIM_PERM_T ( PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT,
ACTV_IND ) VALUES ( 
'805', sys_guid(), 1, '23', 'KFS-MM', 'lookup Document'
, 'Authorizes the lookup of the  Worksheet Document.', 'Y'); 


-- Adding ournamespace as an attribute for the "lookup Permission"
INSERT INTO KRIM_PERM_ATTR_DATA_T ( ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID,
KIM_ATTR_DEFN_ID, ATTR_VAL ) VALUES ( 
'600', sys_guid() , 1, '805', '10', '4', 'KFS-MM'); 

-- Adding "lookup" permission to our three roles:
INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
'805', SYS_GUID(), 1, '100', '805', 'Y'); 

INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
'806', SYS_GUID(), 1, '99', '805', 'Y'); 

INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
'807', SYS_GUID(), 1, '101', '805', 'Y'); 

-- Adding "Use Screen" permission to the "warehouse manager"
INSERT INTO KRIM_ROLE_PERM_T ( ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID,
ACTV_IND ) VALUES ( 
'808', SYS_GUID(), 1, '100', '804', 'Y'); 

COMMIT;