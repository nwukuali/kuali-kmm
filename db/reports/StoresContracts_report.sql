select agreement.po_id as "Previous PO#",
       nvl((select SUM(d.order_item_qty)
          from MM_ORDER_DETAIL_T d
          left join MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
         where o.order_create_dt >= add_months(sysdate, -12)
           and d.catalog_item_id = item.catalog_item_id), 0) as "Usage",
       stock.sales_unit_of_issue_cd as "Sales U/M",
       stock.buy_unit_of_issue_cd as "Purch U/M",
       stock.stock_distributor_nbr as "Stock Number",
       stock.stock_desc as "Description",
       item.catalog_prc as "Unit Price",
       '' as "Update Image"      
  from MM_STOCK_T stock
  left join MM_CATALOG_ITEM_T item
    on item.stock_id = stock.stock_id
  left join MM_AGREEMENT_T agreement
    on stock.stock_agreement_nbr = agreement.agreement_nbr  
  where agreement.agreement_nbr = '201070'
 order by stock.stock_agreement_nbr, item.distributor_nbr;