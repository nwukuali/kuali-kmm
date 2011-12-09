select warehouse.warehouse_nme as Warehouse,
       zn.zone_cd,
       bn.bin_nbr,
       stockBalance.Qty_On_Hand,
       stock.sales_unit_of_issue_cd,
       stock.stock_distributor_nbr,
       substr(stock.stock_desc, 1, 30) as Item_Description,
       catalogItem.Catalog_Prc as Stock_Cost,
       (catalogItem.Catalog_Prc * stockBalance.Qty_On_Hand) as Total,
       decode((stock.reorder_point_qty - stockBalance.Qty_On_Hand) -
              abs(stock.reorder_point_qty - stockBalance.Qty_On_Hand),
              0,
              stockBalance.Qty_On_Hand) as On_Hand,
       stock.actv_ind as Status
  from ${namespace}.MM_STOCK_BALANCE_T stockBalance
 right join ${namespace}.MM_STOCK_T stock
    on stock.stock_id = stockBalance.Stock_Id
  left join ${namespace}.MM_BIN_T bn
    on bn.bin_id = stockBalance.Bin_Id
  left join ${namespace}.MM_ZONE_T zn
    on zn.zone_id = bn.zone_id
  left join ${namespace}.MM_WAREHOUSE_T warehouse
    on warehouse.warehouse_cd = zn.warehouse_cd
  left join ${namespace}.MM_CATALOG_ITEM_T catalogItem
    on catalogItem.Stock_Id = stockBalance.Stock_Id
 order by stock.stock_distributor_nbr;
