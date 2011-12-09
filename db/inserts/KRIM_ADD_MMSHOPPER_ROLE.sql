-- Please make sure you run this one after ruuning KRIM_ADD_SHOPGUEST.sql
-- Permission Use Screen for KFS-MM Shopping Portal 
insert into krim_perm_t (perm_id, obj_id, perm_tmpl_id, nmspc_cd, nm, desc_txt)
       values (krim_perm_id_s.nextval, SYS_GUID(), 
               (select aa.PERM_TMPL_ID from KRIM_PERM_TMPL_T aa where aa.NM = 'Use Screen' and aa.ACTV_IND = 'Y'),
              'KFS-MM', 'Use Screen', 'Allows users to access the Materiel Management Shopping Portal');
              
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
       values (krim_perm_data_attr_id_s.nextval, sys_guid(), 1, KRIM_PERM_ID_S.CURRVAL, '12', '4', 'KFS-MM');

insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
       values (krim_perm_data_attr_id_s.nextval, sys_guid(), 1, KRIM_PERM_ID_S.CURRVAL, '12', '2', 'org.kuali.ext.mm.cart.web.struts.StoresShoppingActionBase');

-- MMShopper role for users to access shopping portal
insert into KRIM_ROLE_T (Role_Id, OBJ_ID, ROLE_NM, NMSPC_CD,DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
       values (krim_role_id_s.nextval, sys_guid(), 'MMShopper', 'KFS-MM', 'Users who are authorized to browse the MM Shopping Portal',
               (select aa.KIM_TYP_ID from KRIM_TYP_T aa where aa.NM = 'Default' and aa.ACTV_IND = 'Y'),
               'Y', SYSDATE);

insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, ROLE_ID, PERM_ID, ACTV_IND)
       values (krim_role_id_s.nextval, SYS_GUID(), 
               (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'MMShopper' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
               krim_perm_id_s.currval, 
               'Y');

-- Permission to initiate an order document
insert into krim_perm_t (perm_id, obj_id, perm_tmpl_id, nmspc_cd, nm, desc_txt)
       values (krim_perm_id_s.nextval, SYS_GUID(), 
               (select aa.PERM_TMPL_ID from KRIM_PERM_TMPL_T aa where aa.NM = 'Initiate Document' and aa.ACTV_IND = 'Y'),
              'KFS-MM', 'Initiate Document', 'Allows users to intiate order documents in the Materiel Management Shopping Portal');
              
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
       values (krim_perm_data_attr_id_s.nextval, sys_guid(), 1, KRIM_PERM_ID_S.CURRVAL, '9', '13', 'SORD');
              
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, ROLE_ID, PERM_ID, ACTV_IND)
       values (krim_role_id_s.nextval, SYS_GUID(), 
               (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'MMShopper' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
               krim_perm_id_s.currval, 
               'Y');              

insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, LAST_UPDT_DT)
       values (krim_role_mbr_id_s.nextval, SYS_GUID(), 
               (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'MMShopper' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
               (select aa.PRNCPL_ID from KRIM_PRNCPL_T aa where aa.PRNCPL_NM = 'shopguest' and aa.ACTV_IND = 'Y'), 
               'P', SYSDATE);  
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, LAST_UPDT_DT)
       values (krim_role_mbr_id_s.nextval, SYS_GUID(), 
               (select aa.ROLE_ID from KRIM_ROLE_T aa where aa.ROLE_NM = 'MMShopper' and aa.NMSPC_CD = 'KFS-MM' and aa.ACTV_IND = 'Y'), 
               (select aa.PRNCPL_ID from KRIM_PRNCPL_T aa where aa.PRNCPL_NM = 'shopuser' and aa.ACTV_IND = 'Y'), 
               'P', SYSDATE);                   
