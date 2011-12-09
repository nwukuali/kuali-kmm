select * from  krim_entity_t t;
insert into krim_entity_t t (entity_id, obj_id, Ver_nbr, Actv_ind, last_updt_dt) 
values (Krim_Entity_Id_s.Nextval, sys_guid(), 1, 'Y', sysdate);

insert into Krim_Prncpl_t (Prncpl_Id, Obj_Id,Ver_Nbr,Prncpl_Nm,Entity_Id,Actv_Ind,Last_Updt_Dt) values
(krim_prncpl_id_s.nextval, sys_guid(), 1, 'mmuser', Krim_Entity_Id_s.currval, 'Y', sysdate);

insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD,ENTITY_ID, OBJ_ID,VER_NBR,ACTV_IND,LAST_UPDT_DT) VAlues
('PERSON',Krim_Entity_Id_s.currval, sys_guid(), 1, 'Y', Sysdate);

insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='mmuser'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);

insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='mmuser'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);

insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='mmuser'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);

insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='mmuser'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);