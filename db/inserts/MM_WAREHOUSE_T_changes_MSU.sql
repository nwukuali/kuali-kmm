INSERT INTO MM_WAREHOUSE_T ( WAREHOUSE_CD, OBJ_ID, VER_NBR, WAREHOUSE_NME, ADDRESS_CD, CONSOLIDATION_CD, DEFAULT_MARKUP_CD, BUYOUT_IND, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('74', SYS_GUID(), '1', 'MAIN WAREHOUSE',  '1', 'N', '01', 'N', 'Y', CURRENT_DATE )
;
INSERT INTO MM_WAREHOUSE_T ( WAREHOUSE_CD, OBJ_ID, VER_NBR, WAREHOUSE_NME, ADDRESS_CD, CONSOLIDATION_CD, DEFAULT_MARKUP_CD, BUYOUT_IND, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('SH', SYS_GUID(), '1', 'SHOP',  '1', 'N', '04', 'N', 'Y', CURRENT_DATE )
;
INSERT INTO MM_WAREHOUSE_T ( WAREHOUSE_CD, OBJ_ID, VER_NBR, WAREHOUSE_NME, ADDRESS_CD, CONSOLIDATION_CD, DEFAULT_MARKUP_CD, BUYOUT_IND, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('NC', SYS_GUID(), '1', 'NCRS',  '1', 'N', '05', 'N', 'Y', CURRENT_DATE )
;
INSERT INTO MM_WAREHOUSE_T ( WAREHOUSE_CD, OBJ_ID, VER_NBR, WAREHOUSE_NME, ADDRESS_CD, CONSOLIDATION_CD, DEFAULT_MARKUP_CD, BUYOUT_IND, ACTV_IND, LAST_UPDATE_DT )
VALUES 
('MG', SYS_GUID(), '1', 'MISC',  '1', 'N', '06', 'N', 'Y', CURRENT_DATE )
;

COMMIT;

update MM_WAREHOUSE_T 
   set WAREHOUSE_NME = 'SPECIALTY'
where WAREHOUSE_CD = 'SI'
;

update MM_WAREHOUSE_T 
   set WAREHOUSE_NME = 'NON_STOCK'
where WAREHOUSE_CD = 'NS'
;

COMMIT;

update MM_AGREEMENT_T
   set WAREHOUSE_CD = '74'
where WAREHOUSE_CD = '63';

update MM_ZONE_T
   set WAREHOUSE_CD = '74'
where WAREHOUSE_CD = '63';

update MM_CATALOG_T
   set WAREHOUSE_CD = '74'
where WAREHOUSE_CD = '63';

update MM_MARKUP_T
   set WAREHOUSE_CD = '74'
where WAREHOUSE_CD = '63';

update MM_ORDER_DOC_T
   set WAREHOUSE_CD = '74'
where WAREHOUSE_CD = '63';

delete MM_WAREHOUSE_ACCOUNTS_T
where WAREHOUSE_CD = '63';

delete MM_WAREHOUSE_OBJECT_T
where WAREHOUSE_CD = '63';

COMMIT;

delete from MM_STOCK_BALANCE_T a
where a.bin_id in (select b.bin_id from mm_bin_t b join mm_zone_t z on z.zone_id = b.zone_id and z.warehouse_cd = 'AA');

COMMIT;

delete from MM_STOCK_COUNT_T a
where a.bin_id in (select b.bin_id from mm_bin_t b join mm_zone_t z on z.zone_id = b.zone_id and z.warehouse_cd = 'AA');

COMMIT;

delete from MM_BIN_T
where mm_bin_t.zone_id in (select zone_id from mm_zone_t where warehouse_cd = 'AA');

COMMIT;

delete from MM_STOCK_BALANCE_T a
where a.bin_id in (select b.bin_id from mm_bin_t b join mm_zone_t z on z.zone_id = b.zone_id and z.warehouse_cd = 'AB');

COMMIT;

delete from MM_STOCK_COUNT_T a
where a.bin_id in (select b.bin_id from mm_bin_t b join mm_zone_t z on z.zone_id = b.zone_id and z.warehouse_cd = 'AB');

COMMIT;

delete from MM_BIN_T 
where mm_bin_t.zone_id in (select zone_id from mm_zone_t where warehouse_cd = 'AB');

COMMIT;

delete from MM_ZONE_T
where WAREHOUSE_CD = 'AA';

delete from MM_ZONE_T
where WAREHOUSE_CD = 'AB';

COMMIT;

delete from MM_WAREHOUSE_ACCOUNTS_T
where WAREHOUSE_CD = '04';

delete from MM_WAREHOUSE_ACCOUNTS_T
where WAREHOUSE_CD = 'AA';

delete from MM_WAREHOUSE_ACCOUNTS_T
where WAREHOUSE_CD = 'AB';

COMMIT;

delete from MM_WAREHOUSE_OBJECT_T
where WAREHOUSE_CD = '04';

delete from MM_WAREHOUSE_OBJECT_T
where WAREHOUSE_CD = 'AA';

delete from MM_WAREHOUSE_OBJECT_T
where WAREHOUSE_CD = 'AB';

COMMIT;

delete from MM_WAREHOUSE_T
where WAREHOUSE_CD = '04';

delete from MM_WAREHOUSE_T
where WAREHOUSE_CD = '63';

delete from MM_WAREHOUSE_T
where WAREHOUSE_CD = 'AA';

delete from MM_WAREHOUSE_T
where WAREHOUSE_CD = 'AB';
