--Roles BEGIN
--role - Storehouse Worker
insert into krim_role_t (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, sys_guid(), 1, 'Storehouse Worker', 'KFS-MM', 'Storehouse Worker role.',(select kim_typ_id from krim_typ_t t where t.nm='Campus' and t.nmspc_cd='KR-NS'), 'Y', current_date);
--role - Commodity Specialist
insert into krim_role_t (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, sys_guid(), 1, 'Commodity Specialist', 'KFS-MM', 'Storehouse Commodity Specialist role.', (select kim_typ_id from krim_typ_t t where t.nm='Campus' and t.nmspc_cd='KR-NS'), 'Y', current_date);
--role - Contract Administrator
insert into krim_role_t (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, sys_guid(), 1, 'Contract Administrator', 'KFS-MM', 'Storehouse Contract Administrator role.', (select kim_typ_id from krim_typ_t t where t.nm='Campus' and t.nmspc_cd='KR-NS'), 'Y', current_date);
--role - Warehouse Manager
insert into krim_role_t (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, sys_guid(), 1, 'Warehouse Manager', 'KFS-MM', 'Storehouse Warehouse Manager role.', (select kim_typ_id from krim_typ_t t where t.nm='Campus' and t.nmspc_cd='KR-NS'), 'Y', current_date);
--Base KMM Role
insert into krim_role_t (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
values (krim_role_id_s.nextval, sys_guid(), 1, 'Storehouse Role', 'KFS-MM', 'Storehouse Base role.',(select kim_typ_id from krim_typ_t t where t.nm='Campus' and t.nmspc_cd='KR-NS'), 'Y', current_date);

--assign role memberships
--Storehouse Worker is member of KFS-SYS User
insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 2, sys_guid(), (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KFS-SYS'), (select role_id from krim_role_t where role_nm='Storehouse Worker'  and nmspc_cd='KFS-MM'), 'R', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2099', 'dd-mm-yyyy'), current_date);
--Contract Administrator is member of KFS-SYS User
insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 2, sys_guid(), (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KFS-SYS'), (select role_id from krim_role_t where role_nm='Contract Administrator'  and nmspc_cd='KFS-MM'), 'R', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2099', 'dd-mm-yyyy'), current_date);
--Warehouse Manager is member of KFS-SYS User
insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 2, sys_guid(), (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KFS-SYS'), (select role_id from krim_role_t where role_nm='Warehouse Manager'  and nmspc_cd='KFS-MM'), 'R', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2099', 'dd-mm-yyyy'), current_date);
--Commodity Specialist is member of Storehouse Worker
insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 2, sys_guid(), (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select role_id from krim_role_t where role_nm='Commodity Specialist'  and nmspc_cd='KFS-MM'), 'R', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2099', 'dd-mm-yyyy'), current_date);
--Assign all roles to KMM Base Role Storehouse Role
insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 2, sys_guid(), (select role_id from krim_role_t where role_nm='Storehouse Role' and nmspc_cd='KFS-MM'), (select role_id from krim_role_t where role_nm='Storehouse Worker'  and nmspc_cd='KFS-MM'), 'R', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2099', 'dd-mm-yyyy'), current_date);
insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 2, sys_guid(), (select role_id from krim_role_t where role_nm='Storehouse Role' and nmspc_cd='KFS-MM'), (select role_id from krim_role_t where role_nm='Contract Administrator'  and nmspc_cd='KFS-MM'), 'R', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2099', 'dd-mm-yyyy'), current_date);
insert into krim_role_mbr_t (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
values (krim_role_mbr_id_s.nextval, 2, sys_guid(), (select role_id from krim_role_t where role_nm='Storehouse Role' and nmspc_cd='KFS-MM'), (select role_id from krim_role_t where role_nm='Warehouse Manager'  and nmspc_cd='KFS-MM'), 'R', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2099', 'dd-mm-yyyy'), current_date);

