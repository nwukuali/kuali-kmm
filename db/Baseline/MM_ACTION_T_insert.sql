INSERT INTO MM_ACTION_T ( ACTION_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('CUSTOMER', SYS_GUID(), '1', 'DEPARTMENT CUSTOMER RETURN', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ACTION_T ( ACTION_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('REJECTED', SYS_GUID(), '1', 'REJECTED DURING CHECK-IN PROCESSING', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ACTION_T ( ACTION_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('WAREHS', SYS_GUID(), '1', 'WAREHOUSE CREATED RETURN', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ACTION_T ( ACTION_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('DEPT_CR', SYS_GUID(), '1', 'DEPARTMENT RETURN CREDIT ISSUED', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ACTION_T ( ACTION_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('DEPT_NC', SYS_GUID(), '1', 'DEPARTMENT RETURN NO CREDIT ISSSUED', 'Y', CURRENT_DATE )
;