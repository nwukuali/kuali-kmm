insert into krim_entity_t (ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT)
values (krim_entity_id_s.nextval, sys_guid(), 1, 'Y', current_date);

insert into krim_entity_ent_typ_t (ENT_TYP_CD, ENTITY_ID, OBJ_ID, VER_NBR, ACTV_IND, LAST_UPDT_DT)
values ('PERSON', krim_entity_id_s.currval, sys_guid(), 1, 'Y', current_date);

insert into krim_entity_nm_t (ENTITY_NM_ID, OBJ_ID, VER_NBR, ENTITY_ID, NM_TYP_CD, FIRST_NM, MIDDLE_NM, LAST_NM, SUFFIX_NM, TITLE_NM, DFLT_IND, ACTV_IND, LAST_UPDT_DT)
values (krim_entity_nm_id_s.nextval, sys_guid(), 1, krim_entity_id_s.currval, 'PRM', 'SHOP GUEST', 'Y', 'GUEST', '', '', 'Y', 'Y', current_date);

insert into krim_prncpl_t (PRNCPL_ID, OBJ_ID, VER_NBR, PRNCPL_NM, ENTITY_ID, PRNCPL_PSWD, ACTV_IND, LAST_UPDT_DT)
values (krim_prncpl_id_s.nextval, sys_guid(), 1, 'shopguest', krim_entity_id_s.currval, '', 'Y', current_date);


