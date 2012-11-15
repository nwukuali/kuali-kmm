-- KMM Database upgrade to Rice 2:
-- Only tables that changed
RENAME TABLE KR_COUNTRY_T TO KRLC_CNTRY_T;

RENAME TABLE KR_COUNTY_T TO KRLC_CNTY_T;

RENAME TABLE KR_POSTAL_CODE_T TO KRLC_PSTL_CD_T;

RENAME TABLE KR_STATE_T TO KRLC_ST_T;

RENAME TABLE KRNS_CAMPUS_T TO KRLC_CMP_T;

RENAME TABLE KRNS_CMP_TYP_T TO KRLC_CMP_TYP_T;

ALTER TABLE KRNS_DOC_HDR_T MODIFY FDOC_DESC VARCHAR(255);

ALTER TABLE krns_sesn_doc_t ADD (OBJ_ID VARCHAR(36));

ALTER TABLE krns_sesn_doc_t ADD (VER_NBR DECIMAL(8) DEFAULT 0);