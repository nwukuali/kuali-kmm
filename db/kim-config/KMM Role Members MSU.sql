---MSU Role Members

insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='rousej'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);
insert into krim_role_mbr_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select max(to_number(ATTR_DATA_ID))+1 from krim_role_mbr_attr_data_t), sys_guid(), 1, krim_role_mbr_id_s.currval, (select kim_typ_id from krim_typ_t where nm='Campus' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='campusCode' and nmspc_cd='KR-NS'), 'EL');

insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='merriott'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);
insert into krim_role_mbr_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select max(to_number(ATTR_DATA_ID))+1 from krim_role_mbr_attr_data_t), sys_guid(), 1, krim_role_mbr_id_s.currval, (select kim_typ_id from krim_typ_t where nm='Campus' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='campusCode' and nmspc_cd='KR-NS'), 'EL');

insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='phillipm'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);
insert into krim_role_mbr_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select max(to_number(ATTR_DATA_ID))+1 from krim_role_mbr_attr_data_t), sys_guid(), 1, krim_role_mbr_id_s.currval, (select kim_typ_id from krim_typ_t where nm='Campus' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='campusCode' and nmspc_cd='KR-NS'), 'EL');

insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='obriend'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);
insert into krim_role_mbr_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select max(to_number(ATTR_DATA_ID))+1 from krim_role_mbr_attr_data_t), sys_guid(), 1, krim_role_mbr_id_s.currval, (select kim_typ_id from krim_typ_t where nm='Campus' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='campusCode' and nmspc_cd='KR-NS'), 'EL');

insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='georgep'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);
insert into krim_role_mbr_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select max(to_number(ATTR_DATA_ID))+1 from krim_role_mbr_attr_data_t), sys_guid(), 1, krim_role_mbr_id_s.currval, (select kim_typ_id from krim_typ_t where nm='Campus' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='campusCode' and nmspc_cd='KR-NS'), 'EL');

insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='kokenake'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);
insert into krim_role_mbr_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select max(to_number(ATTR_DATA_ID))+1 from krim_role_mbr_attr_data_t), sys_guid(), 1, krim_role_mbr_id_s.currval, (select kim_typ_id from krim_typ_t where nm='Campus' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='campusCode' and nmspc_cd='KR-NS'), 'EL');


insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='heckam11'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);
insert into krim_role_mbr_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select max(to_number(ATTR_DATA_ID))+1 from krim_role_mbr_attr_data_t), sys_guid(), 1, krim_role_mbr_id_s.currval, (select kim_typ_id from krim_typ_t where nm='Campus' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='campusCode' and nmspc_cd='KR-NS'), 'EL');


insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 1, sys_guid(), (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select prncpl_id from krim_prncpl_t where prncpl_nm='pridgeo1'), 'P', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('01-01-2100', 'dd-mm-yyyy'), sysdate);
insert into krim_role_mbr_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select max(to_number(ATTR_DATA_ID))+1 from krim_role_mbr_attr_data_t), sys_guid(), 1, krim_role_mbr_id_s.currval, (select kim_typ_id from krim_typ_t where nm='Campus' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='campusCode' and nmspc_cd='KR-NS'), 'EL');
