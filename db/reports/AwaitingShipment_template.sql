select orderDoc.Order_Id as "Order #",
       pickLine.Stock_Qty as "Order Qty to Ship",
       (select SUM(pline.Stock_Qty)
          from ${namespace}.MM_PICK_LIST_LINE_T pline
          left join ${namespace}.MM_CATALOG_ITEM_T cItem
            on pline.stock_id = cItem.Stock_Id
         where cItem.Catalog_Item_Id = detail.catalog_item_id
           and pLine.Pick_Status_Cd in ('INIT', 'ASND', 'PRTD')) as "Item # Total to Ship",
       detail.stock_unit_of_issue_cd as "U/M",
       orderDoc.Delivery_Department_Nm as "Ship To Code",
       orderDoc.Order_Create_Dt as "Date Entered",
       detail.distributor_nbr as "Item #",
       detail.order_item_detail_desc as "Item Description",
       (zn.warehouse_cd || ' / ' || zn.zone_cd || ' / ' || bn.bin_nbr ||
       ' / ' || bn.shelf_id || ' / ' || bn.shelf_id_nbr) as "Bin #"
  from ${namespace}.MM_PICK_LIST_LINE_T pickLine
  left join ${namespace}.MM_ORDER_DETAIL_T detail
    on detail.order_detail_id = pickLine.Order_Detail_Id
  left join ${namespace}.MM_ORDER_DOC_T orderDoc
    on orderDoc.Fdoc_Nbr = detail.order_doc_nbr
  left join ${namespace}.MM_BIN_T bn
    on bn.bin_id = pickLine.Bin_Id
  left join ${namespace}.MM_ZONE_T zn
    on zn.zone_id = bn.zone_id
 where pickLine.Pick_Status_Cd in ('INIT', 'ASND', 'PRTD')
 order by detail.distributor_nbr;
