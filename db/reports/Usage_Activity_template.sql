select catalog.catalog_begin_dt as "Period Date (start)",
       catalog.catalog_end_dt as "Period Date (end)",
       catalog.catalog_cd as "Category Code",
       item.catalog_id as "Catalog ID",
       item.distributor_nbr as "Stock ID",
       substr(item.catalog_desc, 1, 40) as "Description",
       item.catalog_unit_of_issue_cd as "Unit of Issue",
       item.catalog_prc as "Item Cost",     
       stock_balance.qty_on_hand as "On-Hand Qty",
       (item.catalog_prc * stock_balance.qty_on_hand) as "On-Hand Valuation",
       nvl((select SUM(d.order_item_qty) / MONTHS_BETWEEN(to_date('${toDate}', 'MM/DD/YYYY'), to_date('${fromDate}', 'MM/DD/YYYY'))
          from ${namespace}.MM_ORDER_DETAIL_T d
          left join ${namespace}.MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
         where o.order_create_dt between to_date('${fromDate}', 'MM/DD/YYYY') and
               to_date('${toDate}', 'MM/DD/YYYY')
           and d.catalog_item_id = item.catalog_item_id), 0) as "Monthly Usage Qty",
       nvl((select MAX(sc.stock_cst)
          from ${namespace}.MM_STOCK_COST_T sc
         where sc.stock_id = item.stock_id
           and sc.cost_cd = '02') * (select SUM(d.order_item_qty) / MONTHS_BETWEEN(to_date('${toDate}', 'MM/DD/YYYY'), to_date('${fromDate}', 'MM/DD/YYYY'))
          from ${namespace}.MM_ORDER_DETAIL_T d
          left join ${namespace}.MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
         where o.order_create_dt between to_date('${fromDate}', 'MM/DD/YYYY') and
               to_date('${toDate}', 'MM/DD/YYYY')
           and d.catalog_item_id = item.catalog_item_id), 0) as "Monthly Valuation",
       stock_balance.last_update_dt as "Last Updated",
       nvl((select SUM(d.order_item_qty)
          from ${namespace}.MM_ORDER_DETAIL_T d
          left join ${namespace}.MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
         where o.order_create_dt between to_date('${fromDate}', 'MM/DD/YYYY') and
               to_date('${toDate}', 'MM/DD/YYYY')
           and d.catalog_item_id = item.catalog_item_id), 0) as "Period Activity",
       nvl((select SUM(d.order_item_qty) * MAX(scost.stock_cst)
          from ${namespace}.MM_ORDER_DETAIL_T d
          left join ${namespace}.MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
          left join ${namespace}.MM_CATALOG_ITEM_T ci
            on ci.catalog_item_id = d.catalog_item_id
          left join ${namespace}.MM_STOCK_COST_T scost
            on scost.stock_id = ci.stock_id
         where o.order_create_dt between to_date('${fromDate}', 'MM/DD/YYYY') and
               to_date('${toDate}', 'MM/DD/YYYY')
           and d.catalog_item_id = item.catalog_item_id
           and scost.cost_cd='02'), 0) as "Period Valuation",
       to_number((substr(MONTHS_BETWEEN(sysdate, stock.stock_create_dt),
                         0,
                         instr(MONTHS_BETWEEN(sysdate, stock.stock_create_dt),
                               '.') + 2))) as "Months On Hand",
       nvl((select SUM(d.order_item_qty)
          from ${namespace}.MM_ORDER_DETAIL_T d
          left join ${namespace}.MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
         where o.order_create_dt > trunc(sysdate, 'YEAR')
           and d.catalog_item_id = item.catalog_item_id), 0) as "YTD Activity",
       catalog.warehouse_cd
  from ${namespace}.MM_CATALOG_ITEM_T item
  left join ${namespace}.MM_CATALOG_T catalog
    on item.catalog_id = catalog.catalog_id
  left join ${namespace}.MM_STOCK_T stock
    on stock.stock_id = item.stock_id
  left join ${namespace}.MM_STOCK_BALANCE_T stock_balance
    on item.stock_id = stock_balance.stock_id
  left join ${namespace}.MM_ORDER_DETAIL_T order_item
    on order_item.catalog_item_id = item.catalog_item_id
  where nvl((select SUM(d.order_item_qty)
          from ${namespace}.MM_ORDER_DETAIL_T d
          left join ${namespace}.MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
         where o.order_create_dt between to_date('${fromDate}', 'MM/DD/YYYY') and
               to_date('${toDate}', 'MM/DD/YYYY')
           and d.catalog_item_id = item.catalog_item_id), 0) > 0
 group by catalog.catalog_begin_dt,
          catalog.catalog_end_dt,
          catalog.catalog_cd,
          item.stock_id,
          item.catalog_id,
          item.distributor_nbr,
          item.catalog_desc,
          item.catalog_unit_of_issue_cd,
          item.catalog_prc,
          item.catalog_item_id,
          stock.stock_create_dt,
          stock_balance.qty_on_hand,
          stock_balance.last_update_dt,
          catalog.warehouse_cd
 order by catalog.catalog_cd, item.catalog_id, item.distributor_nbr;
