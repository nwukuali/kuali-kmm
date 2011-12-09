-- Insert UNSPSC Information (Segment Information) - Catalog Group
INSERT INTO mm_catalog_group_t
select code catalog_group_cd, SYS_GUID() obj_id, 1 ver_nbr, substr(title,1,45) CATALOG_GROUP_NM, 'Y' actv_ind,CURRENT_DATE last_update_dt
  from mm_unspsc_t 
  where family is NULL
    and class is NULL
    and commodity is null
  order by key;
-- commit Transactions
Commit;
  
-- Insert UNSPSC Information (Family Information) - Catalog Subgroup  
INSERT into mm_catalog_subgroup_t
select MM_CATALOG_SUBGROUP_S.NEXTVAL, 
SYS_GUID() OBJ_ID,
1 VER_NBR, 
substr(code,1,2) || '000000' catalog_group_cd, 
code CATALOG_SUBGROUP_CD, 
substr(title,1,80) CATALOG_SUBGROUP_DESC,
'Y' actv_ind, 
CURRENT_DATE last_update_dt ,
null PRIOR_CATALOG_SUBGROUP_ID
   from mm_unspsc_t 
  where class is NULL
    and commodity is null
    and family is not null;
-- Commit Transactions
commit;

-- Insert UNSPSC Information (class Information) - Catalog Subgroup  
INSERT into mm_catalog_subgroup_t
select MM_CATALOG_SUBGROUP_S.NEXTVAL, 
SYS_GUID() OBJ_ID,
1 VER_NBR, 
substr(a.code,1,2) || '000000' catalog_group_cd, 
a.code CATALOG_SUBGROUP_CD, 
substr(a.title,1,80) CATALOG_SUBGROUP_DESC,
'Y' actv_ind, 
CURRENT_DATE last_update_dt ,
b.catalog_subgroup_id PRIOR_CATALOG_SUBGROUP_ID
   from mm_unspsc_t a, (select CATALOG_SUBGROUP_ID, CATALOG_SUBGROUP_CD from mm_catalog_subgroup_t) b
  where a.commodity is null
    and a.family is not null
    and a.class is not null
    and b.catalog_subgroup_cd = substr(a.code,1,4) || '0000' 
;
-- Commit Transactions
Commit;

-- Insert UNSPSC Information (commodity Information) - Catalog Subgroup  
INSERT into mm_catalog_subgroup_t
select MM_CATALOG_SUBGROUP_S.NEXTVAL, 
SYS_GUID() OBJ_ID,
1 VER_NBR, 
substr(a.code,1,2) || '000000' catalog_group_cd, 
a.code CATALOG_SUBGROUP_CD, 
substr(a.title,1,80) CATALOG_SUBGROUP_DESC,
'Y' actv_ind, 
CURRENT_DATE last_update_dt ,
b.catalog_subgroup_id PRIOR_CATALOG_SUBGROUP_ID
   from mm_unspsc_t a, (select CATALOG_SUBGROUP_ID, CATALOG_SUBGROUP_CD from mm_catalog_subgroup_t) b
  where a.commodity is not null
    and a.family is not null
    and a.class is not null
    and b.catalog_subgroup_cd = substr(a.code,1,6) || '00' ;
-- Commit Transactions
Commit;
