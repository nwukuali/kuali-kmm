select catalog.catalog_begin_dt as PeriodDate_Start,
       catalog.catalog_end_dt as PeriodDate_End,       
       catalog.catalog_cd as Category_Code,
       item.catalog_id as Catalog_ID,
       item.distributor_nbr as Stock_ID,
       item.catalog_desc as ITEM_DESCRIPTION,
       item.catalog_unit_of_issue_cd as UNIT_OF_ISSUE,
       item.catalog_prc,       
       stock_balance.qty_on_hand,
       (item.catalog_prc * stock_balance.qty_on_hand) as On_Hand_Valuation,
       0 as Monthly_Usage,
       stock_balance.last_update_dt,      
       catalog.warehouse_cd
  from MM_CATALOG_ITEM_T item
  left join MM_CATALOG_T catalog
    on item.catalog_id = catalog.catalog_id
  left join MM_STOCK_BALANCE_T stock_balance
    on item.stock_id = stock_balance.stock_id
  left join MM_ORDER_DETAIL_T order_item
    on order_item.catalog_item_id = item.catalog_item_id
  where nvl((select SUM(d.order_item_qty) / MONTHS_BETWEEN(to_date('01/01/2011', 'MM/DD/YYYY'), to_date('01/01/2010', 'MM/DD/YYYY'))
          from MM_ORDER_DETAIL_T d
          left join MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
         where o.order_create_dt between to_date('01/01/2010', 'MM/DD/YYYY') and
               to_date('01/01/2011', 'MM/DD/YYYY')
           and d.catalog_item_id = item.catalog_item_id), 0) <= 0
 group by catalog.catalog_begin_dt,
          catalog.catalog_end_dt,          
          catalog.catalog_cd,
          item.catalog_id,
          item.distributor_nbr,
          item.catalog_desc,
          item.catalog_unit_of_issue_cd,
          item.catalog_prc,
          item.catalog_item_id,
          stock_balance.qty_on_hand,
          stock_balance.last_update_dt,
          catalog.warehouse_cd
 order by catalog.catalog_cd, item.catalog_id, item.distributor_nbr;
