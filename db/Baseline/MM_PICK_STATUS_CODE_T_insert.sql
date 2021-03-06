INSERT INTO MM_PICK_STATUS_CODE_T ( PICK_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('PRTD', SYS_GUID(), '1', 'PICK LIST PRINTED', 'Y', CURRENT_DATE )
;
INSERT INTO MM_PICK_STATUS_CODE_T ( PICK_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('ASND', SYS_GUID(), '1', 'PICK LIST ASIGNED', 'Y', CURRENT_DATE )
;
INSERT INTO MM_PICK_STATUS_CODE_T ( PICK_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('INIT', SYS_GUID(), '1', 'PICK LIST INITIATED', 'Y', CURRENT_DATE )
;
INSERT INTO MM_PICK_STATUS_CODE_T ( PICK_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('PCKD', SYS_GUID(), '1', 'PICK LIST PICKED', 'Y', CURRENT_DATE )
;
INSERT INTO MM_PICK_STATUS_CODE_T ( PICK_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('BACK', SYS_GUID(), '1', 'PICK LINE BACK ORDERED', 'Y', CURRENT_DATE )
;
INSERT INTO MM_PICK_STATUS_CODE_T ( PICK_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('PBCK', SYS_GUID(), '1', 'PICK LINE PARTIALLY BACK ORDERED', 'Y', CURRENT_DATE )
;
INSERT INTO MM_PICK_STATUS_CODE_T ( PICK_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('CLSD', SYS_GUID(), '1', 'PICK LIST CLOSED CANNOT FULLFILL', 'Y', CURRENT_DATE )
;
INSERT INTO MM_PICK_STATUS_CODE_T ( PICK_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('CNCL', SYS_GUID(), '1', 'PICK LIST CANCELED', 'Y', CURRENT_DATE )
;
