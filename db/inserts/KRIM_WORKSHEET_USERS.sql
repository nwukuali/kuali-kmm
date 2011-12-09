--- adding stwkr1
select * from  krim_entity_t t;
insert into krim_entity_t t (entity_id, obj_id, Ver_nbr, Actv_ind, last_updt_dt) 
values (Krim_Entity_Id_s.Nextval, sys_guid(), 1, 'Y', sysdate);
select Krim_Entity_Id_s.Currval from dual; -- > 4301
select * from Krim_Prncpl_t t;
insert into Krim_Prncpl_t (Prncpl_Id, Obj_Id,Ver_Nbr,Prncpl_Nm,Entity_Id,Actv_Ind,Last_Updt_Dt) values
(krim_prncpl_id_s.nextval, sys_guid(), 1, 'stwkr1', 4301, 'Y', sysdate);
select krim_prncpl_id_s.currval from dual; -- > 10000000001

insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD,ENTITY_ID, OBJ_ID,VER_NBR,ACTV_IND,LAST_UPDT_DT) VAlues
('PERSON','4301', sys_guid(), 1, 'Y', Sysdate);


INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
'1502', 1,  sys_guid(), '101', '10000000001', 'P', NULL, NULL,  SYSDATE ); 
--- adding commspec 
insert into krim_entity_t t (entity_id, obj_id, Ver_nbr, Actv_ind, last_updt_dt) 
values (Krim_Entity_Id_s.Nextval, sys_guid(), 1, 'Y', sysdate);
select Krim_Entity_Id_s.Currval from dual; -- > 4302
select * from Krim_Prncpl_t t;
insert into Krim_Prncpl_t (Prncpl_Id, Obj_Id,Ver_Nbr,Prncpl_Nm,Entity_Id,Actv_Ind,Last_Updt_Dt) values
(krim_prncpl_id_s.nextval, sys_guid(), 1, 'commspec', 4302, 'Y', sysdate);
select krim_prncpl_id_s.currval from dual; -- > 10000000003

insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD,ENTITY_ID, OBJ_ID,VER_NBR,ACTV_IND,LAST_UPDT_DT) VAlues
('PERSON','4302', sys_guid(), 1, 'Y', Sysdate);


INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
'1503', 1,  sys_guid(), '99', '10000000003', 'P', NULL, NULL,  SYSDATE ); 

--- adding whmgr:
insert into krim_entity_t t (entity_id, obj_id, Ver_nbr, Actv_ind, last_updt_dt) 
values (Krim_Entity_Id_s.Nextval, sys_guid(), 1, 'Y', sysdate);
select Krim_Entity_Id_s.Currval from dual; -- > 4303
select * from Krim_Prncpl_t t;
insert into Krim_Prncpl_t (Prncpl_Id, Obj_Id,Ver_Nbr,Prncpl_Nm,Entity_Id,Actv_Ind,Last_Updt_Dt) values
(krim_prncpl_id_s.nextval, sys_guid(), 1, 'whmgr', 4303, 'Y', sysdate);
select krim_prncpl_id_s.currval from dual; -- > 10000000005

insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD,ENTITY_ID, OBJ_ID,VER_NBR,ACTV_IND,LAST_UPDT_DT) VAlues
('PERSON','4303', sys_guid(), 1, 'Y', Sysdate);


INSERT INTO KRIM_ROLE_MBR_T ( ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD,
ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT ) VALUES ( 
'1504', 1,  sys_guid(), '100', '10000000005', 'P', NULL, NULL,  SYSDATE ); 