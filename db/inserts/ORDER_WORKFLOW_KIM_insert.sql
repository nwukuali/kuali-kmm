--NODE:Organization
--responsibility
insert into krim_rsp_t(rsp_id,obj_id,ver_nbr,rsp_tmpl_id,nmspc_cd,nm,desc_txt,actv_ind) 
values (krim_rsp_id_s.nextval,sys_guid(),1,'1','KFS-MM','Organization','KMM Order Document Content reviewer and assigns accounts','Y');
--responsibility attributes
insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '13', 'SORD');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '16', 'Organization');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '40', 'true');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '41', 'true');
--role responsibilities
insert into krim_role_rsp_t (ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID, ACTV_IND)
values (krim_role_rsp_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Content Reviewer' and nmspc_cd='KFS-PURAP'), krim_rsp_id_s.currval, 'Y');
--responsibility action
insert into krim_role_rsp_actn_t (ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN)
values (krim_role_rsp_actn_id_s.nextval, sys_guid(), 1, 'A', null, 'F', '*', krim_role_rsp_id_s.currval, 'Y');

--NODE:SubAccount
--responsibility
insert into krim_rsp_t(rsp_id,obj_id,ver_nbr,rsp_tmpl_id,nmspc_cd,nm,desc_txt,actv_ind) 
values (krim_rsp_id_s.nextval,sys_guid(),1,'1','KFS-MM','SubAccount','KMM Order Document Sub Account Reviewer','Y');
--responsibility attributes
insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '13', 'SORD');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '16', 'SubAccount');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '40', 'false');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '41', 'true');
--role responsibilities
insert into krim_role_rsp_t (ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID, ACTV_IND)
values (krim_role_rsp_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Sub-Account Reviewer' and nmspc_cd='KFS-PURAP'), krim_rsp_id_s.currval, 'Y');
--responsibility action
insert into krim_role_rsp_actn_t (ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN)
values (krim_role_rsp_actn_id_s.nextval, sys_guid(), 1, 'A', null, 'F', '*', krim_role_rsp_id_s.currval, 'N');

--NODE:Account
--responsibility
insert into krim_rsp_t(rsp_id,obj_id,ver_nbr,rsp_tmpl_id,nmspc_cd,nm,desc_txt,actv_ind) 
values (krim_rsp_id_s.nextval,sys_guid(),1,'1','KFS-MM','Account','Fiscal Officer Approval','Y');
--responsibility attributes
insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '13', 'SORD');

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
values (krim_role_rsp_actn_id_s.nextval, sys_guid(), 1, 'A', null, 'F', '*', krim_role_rsp_id_s.currval, 'N');

--NODE:AccountingOrganizationHierarchy
--responsibility
insert into krim_rsp_t(rsp_id,obj_id,ver_nbr,rsp_tmpl_id,nmspc_cd,nm,desc_txt,actv_ind) 
values (krim_rsp_id_s.nextval,sys_guid(),1,'1','KFS-MM','AccountingOrganizationHierarchy','Accounting Org Hierarchy Review','Y');
--responsibility attributes
insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '13', 'SORD');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '16', 'AccountingOrganizationHierarchy');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '40', 'false');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '41', 'true');
--role responsibilities
insert into krim_role_rsp_t (ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID, ACTV_IND)
values (krim_role_rsp_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Accounting Reviewer' and nmspc_cd='KFS-SYS'), krim_rsp_id_s.currval, 'Y');
--responsibility action
insert into krim_role_rsp_actn_t (ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN)
values (krim_role_rsp_actn_id_s.nextval, sys_guid(), 1, 'A', null, 'F', '*', krim_role_rsp_id_s.currval, 'N');

--NODE:SeparationOfDuties
--responsibility
insert into krim_rsp_t(rsp_id,obj_id,ver_nbr,rsp_tmpl_id,nmspc_cd,nm,desc_txt,actv_ind) 
values (krim_rsp_id_s.nextval,sys_guid(),1,'1','KFS-MM','SeparationOfDuties','Separation Of Duties Review','Y');
--responsibility attributes
insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '13', 'SORD');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '16', 'SeparationOfDuties');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '40', 'false');

insert into krim_rsp_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), sys_guid(), 1, krim_rsp_id_s.currval, '7', '41', 'false');
--role responsibilities
insert into krim_role_rsp_t (ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID, ACTV_IND)
values (krim_role_rsp_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Workflow Administrator' and nmspc_cd='KFS-SYS'), krim_rsp_id_s.currval, 'Y');
--responsibility action
insert into krim_role_rsp_actn_t (ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN)
values (krim_role_rsp_actn_id_s.nextval, sys_guid(), 1, 'A', null, 'F', '*', krim_role_rsp_id_s.currval, 'N');
