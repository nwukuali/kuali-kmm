INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'heckam11'), 'Bill-To heckam11',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'heckam11'), 'Ship-To heckam11',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'pridgeo1'), 'Bill-To pridgeo1',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'pridgeo1'), 'Ship-To pridgeo1',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'rousej'), 'Bill-To rousej',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'rousej'), 'Ship-To rousej',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'merriott'), 'Bill-To merriott',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'merriott'), 'Ship-To merriott',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'georgep'), 'Bill-To georgep',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'georgep'), 'Ship-To georgep',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'kokenake'), 'Bill-To kokenake',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'kokenake'), 'Ship-To kokenake',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'phillipm'), 'Bill-To phillipm',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'phillipm'), 'Ship-To phillipm',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '01', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'obriend'), 'Bill-To obriend',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
INSERT INTO MM_ADDRESS_T ( ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, 
                                 ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, 
                                 ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ACTV_IND, LAST_UPDATE_DT )
VALUES (MM_ADDRESS_S.nextval, SYS_GUID(), '1', '02', (select PROFILE_ID from MM_PROFILE_T where CUSTOMER_PRNCPL_NM = 'obriend'), 'Ship-To obriend',
        'University Stores', '101 Angell Bldg', 'East Lansing', 'MI', '48824-1234',
        'US', '517.355.1700', 'Y', CURRENT_DATE )
;
