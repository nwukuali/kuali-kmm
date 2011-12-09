INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'ASHLEYAM'), 'Amy Ashley',
        '123 Service Rd', 'University Stores', 'East Lansing', 'MI', '48824',
        'US', '517.555.1234', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'ASHLEYAM'), 'Amy Ashley',
        '2323 Farm Lane Rd', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.9876', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR58'), 'Julie Botsford',
        '456 Wilson Rd', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.5555', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR58'), 'Julie Botsford',
        '456 Wilson Rd', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.5555', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR32'), 'Bill Lowe',
        '98 Shaw Ln', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.4545', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR32'), 'Bill Lowe',
        '98 Shaw Ln', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.4545', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR179'), 'Karle Delo',
        '98 Shaw Ln', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.4545', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR179'), 'Karle Delo',
        '98 Shaw Ln', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.4545', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR19'), 'William Grice',
        '1222 Chestnut Ave', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.6363', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR19'), 'William Grice',
        '1222 Chestnut Ave', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.6363', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR15'), 'Jan Rouse',
        '452 Hannah Blvd', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.1199', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR15'), 'Jan Rouse',
        '452 Hannah Blvd', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.1199', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR26'), 'Darryl Dysinger',
        '2 Mechanic Rd', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.3333', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_ID = 'STR26'), 'Darryl Dysinger',
        '2 Mechanic Rd', ' ', 'East Lansing', 'MI', '48824',
        'US', '517.555.3333', 'Y', CURRENT_DATE )
;
-- UCD Data

-- Bill-To Addresses
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
select MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', a.profile_id, b.nm,  
'202 Cousteau Place, Suite 205', ' ','Davis', 'CA', '95618-7761', 'US', 
'530.555.4545','Y', current_date
from mm_profile_t a, mm_customer_t b
where a.customer_prncpl_id = b.prncpl_id
and a.profile_email like '%ucdavis.edu';
-- Ship-TO Addresses
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
select MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', a.profile_id, b.nm,  
'615 Hopkins Road', ' ' , 'Davis', 'CA', '95616-5270', 'US', 
'530.555.4545','Y', current_date
from mm_profile_t a, mm_customer_t b
where a.customer_prncpl_id = b.prncpl_id
and a.profile_email like '%ucdavis.edu';
