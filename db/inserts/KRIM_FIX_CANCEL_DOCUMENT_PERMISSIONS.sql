
insert into KRIM_ROLE_MBR_T t (Role_Mbr_Id, ver_nbr, obj_id, role_id, mbr_id, mbr_typ_cd, last_updt_dt) 
     values (KRIM_ROLE_MBR_ID_S.NEXTVAL, '1', sys_guid(), 
             (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'Document Editor' and aa.NMSPC_CD = 'KR-NS' and aa.ACTV_IND = 'Y'), 
             (select aa.PRNCPL_ID from KRIM_PRNCPL_T aa where aa.PRNCPL_NM = 'admin' and aa.ACTV_IND = 'Y'), 
             'P', SYSDATE);
insert into KRIM_ROLE_MBR_T t (Role_Mbr_Id, ver_nbr, obj_id, role_id, mbr_id, mbr_typ_cd, last_updt_dt) 
     values (KRIM_ROLE_MBR_ID_S.NEXTVAL, '1', sys_guid(), 
             (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'User' and aa.NMSPC_CD = 'KFS-SYS' and aa.ACTV_IND = 'Y'), 
             (select aa.PRNCPL_ID from KRIM_PRNCPL_T aa where aa.PRNCPL_NM = 'admin' and aa.ACTV_IND = 'Y'), 
             'P', SYSDATE);
select * from krim_role_mbr_t t where t.mbr_id=(select aa.PRNCPL_ID from KRIM_PRNCPL_T aa where aa.PRNCPL_NM = 'admin' and aa.ACTV_IND = 'Y');
