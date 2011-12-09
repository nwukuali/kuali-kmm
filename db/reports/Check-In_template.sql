select orderDetail.Po_Id as "PO Number",
       orderDetail.Distributor_Nbr as "Distributor Number",
       checkinDetail.Accepted_Item_Qty as "Accepted Qty",
       checkinDetail.Rejected_Item_Qty as "Rejected Qty",
       orderDetail.Order_Item_Detail_Desc as "Description",
       cylinder.cylinder_serial_nbr as "Serial Number",
       (orderDetail.Order_Item_Price_Amt * checkinDetail.Accepted_Item_Qty) as "PO Line Total",
       (select SUM(od.order_item_price_amt * cd.accepted_item_qty)
          from ${namespace}.MM_CHECKIN_DETAIL_T cd
          left join ${namespace}.MM_ORDER_DETAIL_T od
            on cd.order_detail_id = od.order_detail_id
         where od.po_id = orderDetail.po_id) as "PO Total"
  from ${namespace}.MM_CHECKIN_DOC_T checkinDoc
  left join ${namespace}.MM_CHECKIN_DETAIL_T checkinDetail
    on checkinDetail.checkin_doc_nbr = checkinDoc.Fdoc_Nbr
  left join ${namespace}.MM_ORDER_DOC_T orderDoc
    on orderDoc.Fdoc_Nbr = checkinDoc.Order_Doc_Nbr
  left join ${namespace}.MM_ORDER_DETAIL_T orderDetail
    on orderDetail.Order_Detail_Id = checkinDetail.Order_Detail_Id
  left join ${namespace}.MM_CYLINDER_T cylinder
    on cylinder.checkin_detail_id = checkinDetail.Checkin_Detail_Id
 where orderDoc.Order_Create_Dt between to_date('${fromDate}', 'MM/DD/YYYY') and
       to_date('${toDate}', 'MM/DD/YYYY');
