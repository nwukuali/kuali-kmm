insert into krim_entity_t t (entity_id, obj_id, Ver_nbr, Actv_ind, last_updt_dt) 
values (Krim_Entity_Id_s.Nextval, sys_guid(), 1, 'Y', sysdate);

insert into Krim_Prncpl_t (Prncpl_Id, Obj_Id,Ver_Nbr,Prncpl_Nm,Entity_Id,Actv_Ind,Last_Updt_Dt) values
(krim_prncpl_id_s.nextval, sys_guid(), 1, 'shopuser', Krim_Entity_Id_s.Currval, 'Y', sysdate);
select * from Krim_Prncpl_t t;

insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD,ENTITY_ID, OBJ_ID,VER_NBR,ACTV_IND,LAST_UPDT_DT) VAlues
('PERSON',Krim_Entity_Id_s.Currval, sys_guid(), 1, 'Y', Sysdate);

insert into Krim_Role_Mbr_t (Role_Mbr_Id, Ver_Nbr, Obj_Id, Role_Id, Mbr_Id, Mbr_Typ_Cd, Last_Updt_Dt) values
(krim_role_mbr_id_s.nextval, 1, sys_guid(),(select r.role_id from KRIM_role_t r where r.role_nm='MM User'), krim_prncpl_id_s.currval, 'P', sysdate);

insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, LAST_UPDT_DT)
       values (krim_role_mbr_id_s.nextval, SYS_GUID(), 
               (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'MMShopper' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
               (select aa.PRNCPL_ID from KRIM_PRNCPL_T aa where aa.PRNCPL_NM = 'shopuser' and aa.ACTV_IND = 'Y'), 
               'P', SYSDATE);    

insert into MM_CUSTOMER_T (PRNCPL_ID, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT) values
('SHOPUSER', sys_guid(), 1, 'Shopping User', 'Y', sysdate);

insert into MM_PROFILE_T (PROFILE_ID, OBJ_ID, VER_NBR, PROFILE_NM, CUSTOMER_PRNCPL_ID, PROFILE_EMAIL, PROFILE_PHONE_NBR, DELIVERY_BUILDING_CD, DELIVERY_BUILDING_RM_NBR, BILLING_BUILDING_CD, ORG_CD, CAMPUS_CD, FIN_COA_CD, ACCOUNT_NBR, SUB_ACCT_NBR, PROFILE_DEFAULT_IND, ACTV_IND, LAST_UPDATE_DT, PROJECT_CD, NOTIFY_IND)
values (MM_CUSTOMER_PROFILE_S.NEXTVAL, sys_guid(), 1, 'Default', 'SHOPUSER', 'test@test.com', '555-444-3333', 'BLDG', 'G55', 'BLDG', '', 'MS', '', '', '', 'Y', 'Y', sysdate, '', 'N');

insert into MM_ADDRESS_T (ADDRESS_ID, OBJ_ID, VER_NBR, ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID, ADDRESS_NM, ADDRESS_LN1, ADDRESS_LN2, ADDRESS_CITY_NM, ADDRESS_STATE_CD, ADDRESS_POSTAL_CD, ADDRESS_COUNTRY_CD, ADDRESS_PHONE_NBR, ADDRESS_DEFAULT_IND, ACTV_IND, LAST_UPDATE_DT)
values (MM_ADDRESS_S.NEXTVAL, sys_guid(), 1, '02', MM_CUSTOMER_PROFILE_S.CURRVAL, 'Test User', '401 Test Lane', '', 'East lansing', 'MI', '48823', 'US', '555-444-3333', '', 'Y', sysdate);


