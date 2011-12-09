select orderDoc.Campus_Cd as Campus,
       orderDoc.Delivery_Department_Nm as Dept#,
       orderDoc.Order_Id as Order#,
       orderDoc.Reqs_Id as Ordered_By,
       accnt.account_nbr as Account#,
       accnt.sub_acct_nbr as Sub_Acct,
       catalog.catalog_cd as Catalog,
       item.distributor_nbr as Item#,
       substr(detail.order_item_detail_desc, 0, 30) as Item_Description,
       detail.order_item_price_amt as Sold_Price,
       orderDoc.Order_Create_Dt as Order_Date,
       ((detail.order_item_qty * detail.Order_Item_Price_Amt) +
       detail.order_item_tax_amt) as Order_Total,
       (select SUM((odtl.order_item_qty * odtl.Order_Item_Price_Amt) +
                   odtl.order_item_tax_amt)
          from MM_ORDER_DOC_T odoc
          left join MM_ORDER_DETAIL_T odtl
            on odtl.order_doc_nbr = odoc.fdoc_nbr
         where odoc.profile_typ_cd = 'INTERNAL'
           and odoc.delivery_department_nm = orderDoc.Delivery_Department_Nm
           and odoc.Order_Create_Dt > to_date('01/01/2010', 'MM/DD/YYYY')) as Department_Total
  from MM_ORDER_DOC_T orderDoc
  left join MM_ORDER_DETAIL_T detail
    on detail.order_doc_nbr = orderDoc.Fdoc_Nbr
  left join MM_CATALOG_ITEM_T item
    on item.catalog_item_id = detail.catalog_item_id
  left join MM_CATALOG_T catalog
    on catalog.catalog_id = item.catalog_id
  left join MM_ACCOUNTS_T accnt
    on accnt.order_detail_id = detail.order_detail_id
 where orderDoc.Profile_Typ_Cd = 'INTERNAL'
   and orderDoc.Order_Create_Dt > to_date('01/01/2010', 'MM/DD/YYYY')
 order by orderDoc.Order_Create_Dt desc;
