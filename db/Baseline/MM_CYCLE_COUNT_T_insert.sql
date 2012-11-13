INSERT INTO MM_CYCLE_COUNT_T ( CYCLE_CNT_CD, OBJ_ID, VER_NBR, CYCLE_CNT_DESC, TIMES_PER_YEAR_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('A', SYS_GUID(), '1', 'STOCK TO COUNT ON A DAILY BASIS', '250', 'Y', CURRENT_DATE )
; 
INSERT INTO MM_CYCLE_COUNT_T ( CYCLE_CNT_CD, OBJ_ID, VER_NBR, CYCLE_CNT_DESC, TIMES_PER_YEAR_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('B', SYS_GUID(), '1', 'STOCK TO COUNT ON A WEEKLY BASIS', '52', 'Y', CURRENT_DATE )
; 
INSERT INTO MM_CYCLE_COUNT_T ( CYCLE_CNT_CD, OBJ_ID, VER_NBR, CYCLE_CNT_DESC, TIMES_PER_YEAR_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('C', SYS_GUID(), '1', 'STOCK TO COUNT ON A MONTHLY BASIS', '12', 'Y', CURRENT_DATE )
;
INSERT INTO MM_CYCLE_COUNT_T ( CYCLE_CNT_CD, OBJ_ID, VER_NBR, CYCLE_CNT_DESC, TIMES_PER_YEAR_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('D', SYS_GUID(), '1', 'STOCK TO COUNT ON A QUARTERLY BASIS', '90', 'Y', CURRENT_DATE )
; 
INSERT INTO MM_CYCLE_COUNT_T ( CYCLE_CNT_CD, OBJ_ID, VER_NBR, CYCLE_CNT_DESC, TIMES_PER_YEAR_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('E', SYS_GUID(), '1', 'STOCK TO COUNT ON A SEMI-ANNUAL BASIS', '130', 'Y', CURRENT_DATE )
;
INSERT INTO MM_CYCLE_COUNT_T ( CYCLE_CNT_CD, OBJ_ID, VER_NBR, CYCLE_CNT_DESC, TIMES_PER_YEAR_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('F', SYS_GUID(), '1', 'SPECIAL COUNT', '60', 'Y', CURRENT_DATE )
;
INSERT INTO MM_CYCLE_COUNT_T ( CYCLE_CNT_CD, OBJ_ID, VER_NBR, CYCLE_CNT_DESC, TIMES_PER_YEAR_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('N', SYS_GUID(), '1', 'DO NOT COUNT', '0', 'Y', CURRENT_DATE )
;
INSERT INTO MM_CYCLE_COUNT_T ( CYCLE_CNT_CD, OBJ_ID, VER_NBR, CYCLE_CNT_DESC, TIMES_PER_YEAR_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('Z', SYS_GUID(), '1', 'ANNUAL INVENTORY', '250', 'Y', CURRENT_DATE )
;