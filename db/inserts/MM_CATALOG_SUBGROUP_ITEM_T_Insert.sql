-- Setup Office Max Catalog Item Subgroup
insert into mm_catalog_subgroup_item_t   
  select mm_catalog_subgroup_item_s.nextval catalog_subgroup_item_id, 
  SYS_GUID() obj_id, '1' ver_nbr,  
  catalog_item_id, b.catalog_subgroup_id, 'Y' actv_ind, CURRENT_DATE last_update_dt
  from mm_catalog_item_t, 
  (select catalog_subgroup_id, catalog_group_cd, catalog_subgroup_cd 
  from mm_catalog_subgroup_t) b
  where catalog_id = '5'
  and b.catalog_group_cd = '71'
  and b.catalog_subgroup_cd = '99';


-- Setup Office Max HON Furniture Item Subgroup
insert into mm_catalog_subgroup_item_t   
  select mm_catalog_subgroup_item_s.nextval catalog_subgroup_item_id, 
  SYS_GUID() obj_id, '1' ver_nbr,  
  catalog_item_id, b.catalog_subgroup_id, 'Y' actv_ind, CURRENT_DATE last_update_dt
  from mm_catalog_item_t, 
  (select catalog_subgroup_id, catalog_group_cd, catalog_subgroup_cd 
  from mm_catalog_subgroup_t) b
  where catalog_id = '8'
  and b.catalog_group_cd = '71'
  and b.catalog_subgroup_cd = '00';
  
-- Setup Fisher Item Subgroup
insert into mm_catalog_subgroup_item_t   
  select mm_catalog_subgroup_item_s.nextval catalog_subgroup_item_id, 
  SYS_GUID() obj_id, '1' ver_nbr,  
  catalog_item_id, b.catalog_subgroup_id, 'Y' actv_ind, CURRENT_DATE last_update_dt
  from mm_catalog_item_t, 
  (select catalog_subgroup_id, catalog_group_cd, catalog_subgroup_cd 
  from mm_catalog_subgroup_t) b
  where catalog_id = '7'
  and b.catalog_group_cd = '40'
  and b.catalog_subgroup_cd = '99'; 
 
-- Setup VWR Item Subgroup
insert into mm_catalog_subgroup_item_t   
  select mm_catalog_subgroup_item_s.nextval catalog_subgroup_item_id, 
  SYS_GUID() obj_id, '1' ver_nbr,  
  catalog_item_id, b.catalog_subgroup_id, 'Y' actv_ind, CURRENT_DATE last_update_dt
  from mm_catalog_item_t, 
  (select catalog_subgroup_id, catalog_group_cd, catalog_subgroup_cd 
  from mm_catalog_subgroup_t) b
  where catalog_id = '6'
  and b.catalog_group_cd = '40'
  and b.catalog_subgroup_cd = '99';  

-- Setup MSU Warehouse catalog Item Subgroup
insert into mm_catalog_subgroup_item_t   
  select mm_catalog_subgroup_item_s.nextval catalog_subgroup_item_id, 
         SYS_GUID() obj_id, 
         '1' ver_nbr,  
         s.catalog_item_id,
         ss.catalog_subgroup_id,
         'Y' actv_ind, 
         CURRENT_DATE last_update_dt
  from 
 (select ci.catalog_item_id as catalog_item_id,
         cg.catalog_group_cd as catalog_group_cd,
         CASE 
           When nvl(cg.catalog_subgroup_cd,'XXMGTXX') <> 'XXMGTXX'
             then cg.catalog_subgroup_cd
           else
             Case
               When cg.catalog_group_cd = '10' then '99'
               When cg.catalog_group_cd = '20' then '30'
               When cg.catalog_group_cd = '30' then '60'
               When cg.catalog_group_cd = '40' then '99'
               When cg.catalog_group_cd = '50' then '99'
               When cg.catalog_group_cd = '60' then '99'
               When cg.catalog_group_cd = '63' then '9A'
               When cg.catalog_group_cd = '68' then '9A'
               When cg.catalog_group_cd = '70' then '99'
               When cg.catalog_group_cd = '71' then '99'
               When cg.catalog_group_cd = '72' then '70'
               When cg.catalog_group_cd = '80' then '50'
               else '99'
             End
         End as catalog_subgroup_cd
  from mm_catalog_item_t ci
  join
  (Select CATALOG_ITEM_ID,
         Case
           When nvl(CATALOG_GROUP_CD,'XX') = 'XX'
            then Case
                   When SUBSTR(t.distributor_nbr,1,3) = '300' then '71'
                   When SUBSTR(t.distributor_nbr,1,3) = '305' then '80'
                   When SUBSTR(t.distributor_nbr,1,3) between '310' and '330' then '10'
                   When SUBSTR(t.distributor_nbr,1,3) = '335' then '63'
                   When SUBSTR(t.distributor_nbr,1,3) = '339' then '70'
                   When SUBSTR(t.distributor_nbr,1,3) between '340' and '342' then '71'
                   When SUBSTR(t.distributor_nbr,1,3) = '344' then '70'
                   When SUBSTR(t.distributor_nbr,1,3) between '345' and '350' then '40'
                   When SUBSTR(t.distributor_nbr,1,3) = '353' then '50'
                   When SUBSTR(t.distributor_nbr,1,3) between '355' and '360' then '40'
                   When SUBSTR(t.distributor_nbr,1,3) = '365' then '50'
                   When SUBSTR(t.distributor_nbr,1,3) = '370' then '60'
                   When SUBSTR(t.distributor_nbr,1,3) = '380' then '68'
                   When SUBSTR(t.distributor_nbr,1,3) = '385' then '80'
                   else '80'
                 End
           else CATALOG_GROUP_CD
         End                 as CATALOG_GROUP_CD,
         CATALOG_SUBGROUP_CD as CATALOG_SUBGROUP_CD
      from mm_catalog_item_t t
      left join mm_catalog_subgroup_t ts
        on nvl(ts.prior_catalog_subgroup_id,0) = 0
       and UPPER(t.catalog_desc) like '%'||UPPER(ts.catalog_subgroup_desc)||'%' 
   where t.catalog_id = 2 )  cg
  on cg.catalog_item_id = ci.catalog_item_id ) s
join mm_catalog_subgroup_t ss
  on ss.catalog_group_cd = s.catalog_group_cd
 and ss.catalog_subgroup_cd = s.catalog_subgroup_cd
;
  
