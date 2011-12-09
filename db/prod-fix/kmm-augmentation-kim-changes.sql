--SOAL Permissions
BEGIN
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up Order Auto Limit Records', 'Permission to Look Up Order Auto Limit Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_perm_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Order Auto Limit Records'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'OrderAutoLimit');


insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate SOAL Doc', 'Permission to initiate Order Auto Limit in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_perm_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate SOAL Doc'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SOAL');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Create / Maintain Record(s)' and nmspc_cd='KR-NS'), 'KFS-MM', 'Create / Maintain SOAL Record(s)', 'Permission to Create / Maintain SOAL Record(s) in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_perm_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain SOAL Record(s)'), (select kim_typ_id from krim_typ_t where nm='Document Type & Existing Records Only' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SOAL');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Document for SOAL Mdoc', 'Permission to maintenance documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_perm_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for SOAL Mdoc'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SOAL');

--Role permissions
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KFS-SYS'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Order Auto Limit Records'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KFS-SYS'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain SOAL Record(s)'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KFS-SYS'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate SOAL Doc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KFS-SYS'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for SOAL Mdoc'), 'Y');


--NODE:Account
--responsibility
insert into krim_rsp_t(rsp_id,obj_id,ver_nbr,rsp_tmpl_id,nmspc_cd,nm,desc_txt,actv_ind) 
values (krim_rsp_id_s.nextval,sys_guid(),1,'1','KFS-MM','Account','Fiscal Officer Approval','Y');
--responsibility attributes
insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '13', 'SOAL');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '16', 'Account');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '40', 'true');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '41', 'true');
--role responsibilities
insert into krim_role_rsp_t (ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID, ACTV_IND)
values (krim_role_rsp_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Fiscal Officer' and nmspc_cd='KFS-SYS'), krim_rsp_id_s.currval, 'Y');
--responsibility action
insert into krim_role_rsp_actn_t (ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN)
values (krim_role_rsp_actn_id_s.nextval, sys_guid(), 1, 'A', null, 'F', '*', krim_role_rsp_id_s.currval, 'Y');

--SORD Blanket approve
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Blanket Approve Document' and nmspc_cd='KR-WKFLW'), 'KFS-MM', 'Blanket Approve Document SORD', 'Permission to Blanket Approve Document SORD', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_perm_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Blanket Approve Document SORD'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SORD');

--assign SORD Blanket approve permission
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Blanket Approve Document SORD'), 'Y');

--SAMU
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Stores SAMU', 'Permission to initiate stores Agreement Mass Update documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_perm_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SAMU'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SAMU');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Document for Stores SAMU', 'Permission to Open Document for Stores Agreement Mass Update documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_perm_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SAMU'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SAMU');
 
 --SAMU
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SAMU'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Manager' and nmspc_cd='KFS-PURAP'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SAMU'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SAMU'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Manager' and nmspc_cd='KFS-PURAP'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SAMU'), 'Y');

--Markup lookup restriction
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up Markup Records', 'Permission to Look Up Markup Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_perm_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Markup Records'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'Markup');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Markup Records'), 'Y');
--commit this only if all records inserted successfully, revert otherwise and fix the errors before the reattempt
--commit -- rollback
COMMIT;
EXCEPTION
WHEN OTHERS THEN
rollback;
END;
