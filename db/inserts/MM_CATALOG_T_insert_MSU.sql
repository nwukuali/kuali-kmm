-- Central Storehouse Main Catalog - Stock
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','74','MAIN WAREHOUSE',
  null,null,'1', 1, null, 'Y',
  null, '74', 'Y', CURRENT_DATE);
  
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','NS','NON-STOCK',
  null,null,'1', 1, null, 'Y',
  null, 'NS', 'Y', CURRENT_DATE);
  
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','SI','SPECIALTY',
  null,null,'1', 1, null, 'Y',
  null, 'SI', 'Y', CURRENT_DATE);
  
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','SH','SHOP',
  null,null,'1', 1, null, 'Y',
  null, 'SH', 'Y', CURRENT_DATE);

  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','NC','NCRS',
  null,null,'1', 1, null, 'Y',
  null, 'NC', 'Y', CURRENT_DATE);

  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','MG','MISC',
  null,null,'1', 1, null, 'Y',
  null, 'MG', 'Y', CURRENT_DATE);
