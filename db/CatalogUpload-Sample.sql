--first assign using unspc code

--add unique catalog group names
insert into mm_catalog_group_t
  (CATALOG_GROUP_CD,
   OBJ_ID,
   VER_NBR,
   CATALOG_GROUP_NM,
   ACTV_IND,
   LAST_UPDATE_DT)
  select '06' || mm_catalog_group_s.nextval,
         sys_guid(),
         1,
         grps.grpnm,
         'Y',
         current_date
    from (select distinct ltrim(rtrim(a.catalog_group_nm)) grpnm
            from mm_catalog_item_pending_t a
           where a.catalog_pending_doc_nbr = '1111111208') grps
    left outer join mm_catalog_group_t x
      on x.catalog_group_nm = grps.grpnm
   where x.catalog_group_cd is null
     and grps.grpnm is not null;

--Assign catalog group code to pending items
update mm_catalog_item_pending_t a
   set a.catalog_group_cd =
       (select catalog_group_cd
          from mm_catalog_group_t x
         where ltrim(rtrim(upper(x.catalog_group_nm))) =
               ltrim(rtrim(upper(a.catalog_group_nm))))
 where a.catalog_pending_doc_nbr = '1111111208'
   and a.catalog_group_cd is null;

   

--add unique catalog subgroup names   
insert into mm_catalog_subgroup_t
  (CATALOG_SUBGROUP_ID,
   OBJ_ID,
   VER_NBR,
   CATALOG_GROUP_CD,
   CATALOG_SUBGROUP_CD,
   CATALOG_SUBGROUP_DESC,
   ACTV_IND,
   LAST_UPDATE_DT,
   PRIOR_CATALOG_SUBGROUP_ID)
  select MM_CATALOG_SUBGROUP_S.NEXTVAL,
         sys_guid(),
         1,
         subrps.grp,
         '06' || MM_CATALOG_SUBGROUP_S.currval,
         subrps.grpdesc,
         'Y',
         current_date,
         null
    from (select distinct a.catalog_group_cd grp,
                          ltrim(rtrim(a.catalog_subgroup_desc)) grpdesc
            from mm_catalog_item_pending_t a
           where a.catalog_pending_doc_nbr = '1111111208') subrps
    left outer join mm_catalog_subgroup_t x
      on x.catalog_group_cd = subrps.grp
     and x.catalog_subgroup_desc = subrps.grpdesc
   where x.catalog_subgroup_id is null
     and subrps.grp is not null
     and subrps.grpdesc is not null;

--Assign the right catalog subgroup code to pending items
update mm_catalog_item_pending_t a
   set a.catalog_subgroup_cd =
       (select x.catalog_subgroup_cd
          from mm_catalog_subgroup_t x
         where rtrim(ltrim(upper(x.catalog_subgroup_desc))) =
               rtrim(ltrim(upper(a.catalog_subgroup_desc)))
           and x.catalog_group_cd = a.catalog_group_cd)
 where a.catalog_pending_doc_nbr = '1111111208'
   and a.catalog_subgroup_cd is null
   and a.catalog_subgroup_desc is not null
   and a.catalog_group_cd is not null;

--update catalog item table
update mm_catalog_item_t x
   set x.MANUFACTURER_NBR        =
       (select MANUFACTURER_NBR
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.CATALOG_UNIT_OF_ISSUE_CD =
       (select CATALOG_UNIT_OF_ISSUE_CD
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.CATALOG_PRC             =
       (select CATALOG_PRC
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.CATALOG_DESC            =
       (select CATALOG_DESC
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.RECYCLED_IND            =
       (select RECYCLED_IND
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.UNSPSC_CD               =
       (select UNSPSC_CD
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.SHIPPING_WGT            =
       (select SHIPPING_WGT
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.SHIPPING_HT             =
       (select SHIPPING_HT
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.SHIPPING_WD             =
       (select SHIPPING_WD
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.SHIPPING_LH             =
       (select SHIPPING_LH
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.CATALOG_ITEM_PND_ID     =
       (select CATALOG_ITEM_PND_ID
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.TAXABLE_IND             =
       (select TAXABLE_IND
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.SPAID_ID                =
       (select SPAID_ID
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.distributor_nbr
           and x.catalog_id = 5
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.LAST_UPDATE_DT           = current_date
 where x.catalog_item_id in
       (select a.catalog_item_id
          from mm_catalog_item_t a, mm_catalog_item_pending_t b
         where a.distributor_nbr = b.distributor_nbr
           and a.catalog_id = 5
           and b.catalog_pending_doc_nbr = '1111111208');

--update catalog image table
update mm_catalog_image_t x
   set x.CATALOG_IMAGE_URL =
       (select CATALOG_IMAGE_URL
          from mm_catalog_item_pending_t y
         where y.distributor_nbr = x.catalog_image_nm
           and y.catalog_pending_doc_nbr = '1111111208'),
       x.LAST_UPDATE_DT    = current_date
 where x.catalog_image_id in
       (select a.catalog_image_id
          from mm_catalog_image_t a, mm_catalog_item_pending_t b
         where b.distributor_nbr = a.catalog_image_nm
           and b.catalog_image_url is not null
           and b.catalog_pending_doc_nbr = '1111111208');

--update catalog item image
update mm_catalog_item_image_t d
   set d.CATALOG_IMAGE_ID =
       (select CATALOG_IMAGE_ID
          from mm_catalog_item_pending_t a
          join mm_catalog_item_t b
            on b.distributor_nbr = a.distributor_nbr
           and b.catalog_id = 5
          join mm_catalog_image_t c
            on c.catalog_image_nm = a.distributor_nbr
         where d.catalog_item_id = b.catalog_item_id
           and d.catalog_image_id = c.catalog_image_id
           and a.catalog_pending_doc_nbr = '1111111208')
 where d.catalog_item_image_id in
       (select catalog_item_image_id
          from mm_catalog_item_pending_t x
          join mm_catalog_item_t y
            on x.distributor_nbr = y.distributor_nbr
           and y.catalog_id = 5
          join mm_catalog_image_t z
            on z.catalog_image_nm = x.distributor_nbr
          join mm_catalog_item_image_t t
            on t.catalog_item_id = y.catalog_item_id
           and t.catalog_image_id = z.catalog_image_id
         where x.catalog_pending_doc_nbr = '1111111208');

--insert new catalog items
insert into mm_catalog_item_t
  (CATALOG_ITEM_ID,
   OBJ_ID,
   VER_NBR,
   DISTRIBUTOR_NBR,
   MANUFACTURER_NBR,
   CATALOG_UNIT_OF_ISSUE_CD,
   CATALOG_PRC,
   CATALOG_DESC,
   RECYCLED_IND,
   WILLCALL_IND,
   UNSPSC_CD,
   SHIPPING_WGT,
   SHIPPING_HT,
   SHIPPING_WD,
   SHIPPING_LH,
   CATALOG_ID,
   STOCK_ID,
   DISPLAYABLE_IND,
   CATALOG_ITEM_PND_ID,
   SUBSTITUTE_DISTRIBUTOR_NBR,
   TAXABLE_IND,
   SPAID_ID,
   ACTV_IND,
   LAST_UPDATE_DT)
  select MM_CATALOG_ITEM_S.Nextval,
         sys_guid(),
         1,
         a.DISTRIBUTOR_NBR,
         a.MANUFACTURER_NBR,
         a.CATALOG_UNIT_OF_ISSUE_CD,
         a.CATALOG_PRC,
         a.CATALOG_DESC,
         a.RECYCLED_IND,
         'N',
         a.UNSPSC_CD,
         a.SHIPPING_WGT,
         a.SHIPPING_HT,
         a.SHIPPING_WD,
         a.SHIPPING_LH,
         '5',
         null,
         'Y',
         a.CATALOG_ITEM_PND_ID,
         null,
         a.TAXABLE_IND,
         a.SPAID_ID,
         'Y',
         current_date
    from mm_catalog_item_pending_t a
    left outer join mm_catalog_item_t b
      on a.distributor_nbr = b.distributor_nbr
     and b.catalog_id = '5'
   where b.catalog_item_id is null
     and a.catalog_pending_doc_nbr = '1111111208';

--insert catalog images
insert into mm_catalog_image_t
  (CATALOG_IMAGE_ID,
   OBJ_ID,
   VER_NBR,
   CATALOG_IMAGE_NM,
   CATALOG_IMAGE_URL,
   ACTV_IND,
   LAST_UPDATE_DT)
  select mm_catalog_image_s.nextval,
         sys_guid(),
         1,
         a.distributor_nbr,
         a.CATALOG_IMAGE_URL,
         a.ACTV_IND,
         current_date
    from mm_catalog_item_pending_t a
    left outer join mm_catalog_image_t b
      on a.distributor_nbr = b.catalog_image_nm
   where b.catalog_image_id is null
     and a.catalog_image_url is not null
     and a.catalog_pending_doc_nbr = '1111111208';

--insert into item image table
insert into mm_catalog_item_image_t
  (CATALOG_ITEM_IMAGE_ID,
   OBJ_ID,
   VER_NBR,
   CATALOG_ITEM_ID,
   CATALOG_IMAGE_ID,
   ACTV_IND,
   LAST_UPDATE_DT)
  select mm_catalog_item_image_s.nextval,
         sys_guid(),
         1,
         b.catalog_item_id,
         c.catalog_image_id,
         'Y',
         current_date
    from mm_catalog_item_pending_t a
    join mm_catalog_item_t b
      on b.distributor_nbr = a.distributor_nbr
     and b.catalog_id = '5'
    join mm_catalog_image_t c
      on c.catalog_image_nm = a.distributor_nbr
    left outer join mm_catalog_item_image_t d
      on d.catalog_item_id = b.catalog_item_id
     and d.catalog_image_id = c.catalog_image_id
   where d.catalog_item_image_id is null
     and a.catalog_pending_doc_nbr = '1111111208';

-- deactivate deleted catalog items
update mm_catalog_item_t x
   set x.actv_ind = 'N'
 where x.catalog_item_id in
       (select a.catalog_item_id
          from mm_catalog_item_t a
          left outer join mm_catalog_item_pending_t b
            on a.distributor_nbr = b.distributor_nbr
           and a.catalog_id = 5
           and b.catalog_pending_doc_nbr = '1111111208'
         where b.catalog_item_pnd_id is null)
   and x.actv_ind = 'Y';

--update catalog sub group items 
insert into mm_catalog_subgroup_item_t
  (CATALOG_SUBGROUP_ITEM_ID,
   OBJ_ID,
   VER_NBR,
   CATALOG_ITEM_ID,
   CATALOG_SUBGROUP_ID,
   ACTV_IND,
   LAST_UPDATE_DT)
  select MM_CATALOG_SUBGROUP_ITEM_S.NEXTVAL,
         sys_guid(),
         1,
         a.catalog_item_id,
         c.catalog_subgroup_id,
         'Y',
         current_date
    from mm_catalog_item_t a
    join mm_catalog_item_pending_t b
      on a.distributor_nbr = b.distributor_nbr
     and a.catalog_id = 5
    join mm_catalog_subgroup_t c
      on b.catalog_group_cd = c.catalog_group_cd
     and b.catalog_subgroup_cd = c.catalog_subgroup_cd
    left outer join mm_catalog_subgroup_item_t d
      on a.catalog_item_id = d.catalog_item_id
     and c.catalog_subgroup_id = d.catalog_subgroup_id
   where d.catalog_subgroup_item_id is null
     and b.catalog_group_cd is not null
     and b.catalog_subgroup_cd is not null
     and b.catalog_pending_doc_nbr = '1111111208';
--rollback or commit     
commit;