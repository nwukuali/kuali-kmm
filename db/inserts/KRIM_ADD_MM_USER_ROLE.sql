insert into krim_perm_t (perm_id, obj_id, perm_tmpl_id, nmspc_cd, nm, desc_txt)
       values (krim_perm_id_s.nextval, SYS_GUID(), 
               (select aa.PERM_TMPL_ID from KRIM_PERM_TMPL_T aa where aa.NM = 'Use Screen' and aa.ACTV_IND = 'Y'),
              'KFS-MM', 'Use Screen', 'Allows users to access all Material Management screens');
insert into KRIM_ROLE_T (Role_Id, OBJ_ID, ROLE_NM, NMSPC_CD,DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
       values (krim_role_id_s.nextval, sys_guid(), 'MM User', 'KFS-MM', 'Users who are authorized to use the Materiels Management system',
               (select aa.KIM_TYP_ID from KRIM_TYP_T aa where aa.NM = 'Default' and aa.ACTV_IND = 'Y'),
               'Y', SYSDATE);
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, ROLE_ID, PERM_ID, ACTV_IND)
       values (krim_role_id_s.nextval, SYS_GUID(), 
               (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'MM User' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
               (select aa.PERM_ID from KRIM_PERM_T aa where aa.NMSPC_CD = 'KFS-MM' and aa.NM = 'Use Screen' and aa.ACTV_IND = 'Y'), 
               'Y');
               
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, ROLE_ID, PERM_ID, ACTV_IND)
       values (krim_role_id_s.nextval, SYS_GUID(), 
               (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'MM User' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
               (select aa.PERM_ID from KRIM_PERM_T aa where aa.NMSPC_CD = 'KR-SYS' and aa.NM = 'Initiate Document' and aa.ACTV_IND = 'Y'), 
               'Y')
            