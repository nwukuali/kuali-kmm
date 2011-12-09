select orderDoc.Order_Id as "Order #",
       pickLine.Back_Order_Qty as "Backorder Qty",
       (select SUM(pline.back_order_qty)
          from ${namespace}.MM_PICK_LIST_LINE_T pline
          left join ${namespace}.MM_CATALOG_ITEM_T cItem
            on pline.stock_id = cItem.Stock_Id
         where cItem.Catalog_Item_Id = detail.catalog_item_id) as "Item # Total to Ship",
       detail.stock_unit_of_issue_cd as "U/M",
       orderDoc.Delivery_Department_Nm as "Ship To Code",
       orderDoc.Order_Create_Dt as "Date Entered",
       detail.order_expected_dt as "Date Due",
       detail.distributor_nbr as "Item #",
       detail.order_item_detail_desc as "Item Description",
       (zn.warehouse_cd || ' / ' || zn.zone_cd || ' / ' || bn.bin_nbr ||
       ' / ' || bn.shelf_id || ' / ' || bn.shelf_id_nbr) as "Bin #"
  from ${namespace}.MM_BACK_ORDER_T backorders
  left join ${namespace}.MM_PICK_LIST_LINE_T pickLine
    on pickLine.Pick_List_Line_Id = backorders.pick_list_line_id
  left join ${namespace}.MM_ORDER_DETAIL_T detail
    on detail.order_detail_id = pickLine.Order_Detail_Id
  left join ${namespace}.MM_ORDER_DOC_T orderDoc
    on orderDoc.Fdoc_Nbr = detail.order_doc_nbr
  left join ${namespace}.MM_BIN_T bn
    on bn.bin_id = pickLine.Bin_Id
  left join ${namespace}.MM_ZONE_T zn
    on zn.zone_id = bn.zone_id
 where backorders.filled_ind = 'N'
   and backorders.canceled_ind = 'N'
 order by detail.distributor_nbr;
