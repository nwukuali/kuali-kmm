update MM_CATALOG_ITEM_T a
  set a.CATALOG_ID = (select b.CATALOG_ID 
                      from MM_STOCK_BALANCE_T c
                      join MM_BIN_T d
                         on d.bin_id = c.bin_id
                      join MM_ZONE_T e 
                         on e.zone_id = d.zone_id
                      join MM_CATALOG_T b
                         on b.WAREHOUSE_CD = nvl(e.WAREHOUSE_CD,'AB')
                      where c.Stock_Id = a.Stock_Id)
where a.catalog_id = '1'
;
