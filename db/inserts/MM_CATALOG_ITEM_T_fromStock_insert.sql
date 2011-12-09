INSERT into MM_CATALOG_ITEM_T
select MM_CATALOG_ITEM_S.nextval, 
       sys_guid(), 
       1,
       MM_STOCK_T.STOCK_DISTRIBUTOR_NBR,
       MM_STOCK_T.MANUFACTURER_NBR,
       MM_STOCK_T.SALES_UNIT_OF_ISSUE_CD,
       (MM_STOCK_COST_T.STOCK_CST * 1.11),
       MM_STOCK_T.STOCK_DESC,
       MM_STOCK_T.RECYCLED_IND,
       MM_STOCK_T.WILLCALL_IND,
       ' ',
       MM_STOCK_T.SHIPPING_WGT,
       MM_STOCK_T.SHIPPING_HT,
       MM_STOCK_T.SHIPPING_WD,
       MM_STOCK_T.SHIPPING_LH,
       (select catalog_id from mm_catalog_t where catalog_cd = nvl(MM_ZONE_T.WAREHOUSE_CD,'AB')),
       MM_STOCK_T.STOCK_ID,
       'Y',
       NULL,
       NULL,
       MM_STOCK_T.TAXABLE_IND,
       NULL,
       'Y',
       CURRENT_DATE
FROM MM_STOCK_T
JOIN MM_STOCK_COST_T
    ON MM_STOCK_COST_T.STOCK_ID = MM_STOCK_T.STOCK_ID
LEFT JOIN MM_STOCK_BALANCE_T
    ON MM_STOCK_BALANCE_T.STOCK_ID = MM_STOCK_T.STOCK_ID
LEFT JOIN MM_BIN_T
    ON MM_BIN_T.BIN_ID = MM_STOCK_BALANCE_T.BIN_ID
LEFT JOIN MM_ZONE_T
    ON MM_ZONE_T.ZONE_ID = MM_BIN_T.ZONE_ID
;
COMMIT
;
update mm_catalog_item_t 
  set taxable_ind = 'N'
where nvl(taxable_ind,'N') <> 'Y'
;
COMMIT
;
update mm_catalog_item_t 
  set recycled_ind = 'N'
where nvl(recycled_ind,'N') <> 'Y'
;
COMMIT
;
update mm_catalog_item_t 
  set willcall_ind = 'N'
where nvl(willcall_ind,'N') <> 'Y'
;
COMMIT;
update mm_catalog_item_t t set t.unspsc_cd= ''  where t.unspsc_cd=' ';
COMMIT;
update mm_catalog_item_t t set t.unspsc_cd=rtrim(ltrim(unspsc_cd));
COMMIT;
update mm_catalog_item_t t set t.catalog_desc=rtrim(ltrim(catalog_desc));
