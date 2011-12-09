--delete permissions
delete from krim_role_perm_t t where t.perm_id in (select perm_id from krim_perm_t where nmspc_cd='KFS-MM');
delete from krim_perm_attr_data_t t where t.perm_id in (select perm_id from krim_perm_t where nmspc_cd='KFS-MM');
delete from krim_perm_t where nmspc_cd='KFS-MM';
--delete responsibilities
delete from krim_rsp_attr_data_t t where t.rsp_id in (select rsp_id from krim_rsp_t t where nmspc_cd='KFS-MM');
delete from krim_role_rsp_t where rsp_id in (select rsp_id from krim_rsp_t t where nmspc_cd='KFS-MM');
delete from krim_role_rsp_actn_t where role_rsp_id in (select role_rsp_id from krim_role_rsp_t where rsp_id in (select rsp_id from krim_rsp_t t where nmspc_cd='KFS-MM'));
delete from krim_rsp_t t where nmspc_cd='KFS-MM';
--delete roles and members
delete from krim_role_mbr_attr_data_t where ROLE_MBR_ID in (select ROLE_MBR_ID from krim_role_mbr_t t where t.role_id in (select role_id from krim_role_t where nmspc_cd='KFS-MM'));
delete from krim_role_mbr_t t where t.mbr_id in (select role_id from krim_role_t where nmspc_cd='KFS-MM');
delete from krim_role_mbr_t t where t.role_id in (select role_id from krim_role_t where nmspc_cd='KFS-MM');
delete from krim_role_t where nmspc_cd='KFS-MM';