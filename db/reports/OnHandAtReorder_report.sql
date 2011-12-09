select stock.stock_distributor_nbr as Stock_ID,
       stock.stock_desc as Item_Description,
       stock.actv_ind as Status,
       decode((stock.reorder_point_qty - stockBalance.Qty_On_Hand) -
              abs(stock.reorder_point_qty - stockBalance.Qty_On_Hand),
              0,
              stockBalance.Qty_On_Hand) as On_Hand,
       stock.reorder_point_qty as Reorder_Qty,
       stock.minimum_order_qty,
       stock.maximum_order_qty
  from MM_STOCK_BALANCE_T stockBalance
 right join MM_STOCK_T stock
    on stock.stock_id = stockBalance.Stock_Id
 where stockBalance.Qty_On_Hand <= stock.reorder_point_qty
 order by stock.stock_distributor_nbr;
