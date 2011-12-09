-- Central Storehouse Main Catalog - Stock
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','AB','Warehouse Store Catalog',
  null,null,'1', 1, null, 'Y',
  null, 'AB', 'Y', CURRENT_DATE);
  
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','63','MSU Main Warehouse Catalog',
  null,null,'1', 1, null, 'Y',
  null, '63', 'Y', CURRENT_DATE);
  
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','NS','MSU Non Stock Warehouse Catalog',
  null,null,'1', 1, null, 'Y',
  null, 'NS', 'Y', CURRENT_DATE);
  
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','SI','MSU Specialty Items Warehouse Catalog',
  null,null,'1', 1, null, 'Y',
  null, 'SI', 'Y', CURRENT_DATE);
  
-- Office Max Supply Catalog - Hosted  
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','06','OfficeMax Supply Catalog',
  null,null,'2', 2, null, 'Y',
  null, null, 'Y', CURRENT_DATE);
  
-- VWR Hosted Catalog  
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','07','VWR Catalog',
  null,null,'2', 4, null, 'Y',
  null, null, 'Y', CURRENT_DATE);
  
-- Fischer Scientific Hosted Catalog  
    insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','08','Fisher Scientific Catalog',
  null,null,'2', 3, null, 'Y',
  null, null, 'Y', CURRENT_DATE);
  
-- Office Max HON Furniture - Hosted Catalog  
   insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','09','OfficeMax HON Furniture',
  null,null,'2', 5, null, 'Y',
  null, null, 'Y', CURRENT_DATE);
  
-- Dell Computers - Punch-out Catalog  
 insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','DE','Dell Computers',
  null,null,'3', 6, null, 'Y',
  null, null, 'Y', CURRENT_DATE);
 
  -- UCD - Central Storehouse Main Catalog - Stock
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','01','UCD - CENTRAL STOREHOUSE MAIN CATALOG',
  null,null,'1', 1, null, 'Y',
  null, '01', 'Y', CURRENT_DATE);
 
   -- UCD - Supply Express Main Catalog - Stock
  insert into mm_catalog_t
  (catalog_id, obj_id, ver_nbr, catalog_cd, catalog_desc,
       catalog_begin_dt, catalog_end_dt, catalog_type_cd,
       priority_nbr, agreement_nbr, current_ind,
       catalog_pending_doc_nbr, warehouse_cd, actv_ind, last_update_dt)
  values(mm_catalog_s.nextval, SYS_GUID(),'1','04','UCD - SUPPLY EXPRESS MAIN CATALOG',
  null,null,'1', 1, null, 'Y',
  null, '04', 'Y', CURRENT_DATE);
