select catalog.catalog_begin_dt,
       catalog.catalog_end_dt,
       agreement.po_id,
       agreement.vendor_nm,
       catalog.catalog_cd,
       item.catalog_id,
       item.distributor_nbr,
       substr(item.catalog_desc, 1, 30) as CATALOG_ITEM_DESC,
       item.catalog_unit_of_issue_cd,
       item.catalog_prc,
       zn.zone_cd,
       bn.bin_nbr,
       stock_balance.qty_on_hand,
       (select sc.stock_cst * stock_balance.qty_on_hand
          from MM_STOCK_COST_T sc
         where sc.stock_id = item.stock_id
           and sc.cost_cd = '02') as On_Hand_Valuation,
       (select SUM(d.order_item_qty) / MONTHS_BETWEEN(to_date('11/30/2010', 'MM/DD/YYYY'), to_date('11/01/2010', 'MM/DD/YYYY'))
          from MM_ORDER_DETAIL_T d
          left join MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
         where o.order_create_dt between to_date('11/01/2010', 'MM/DD/YYYY') and
               to_date('11/30/2010', 'MM/DD/YYYY')
           and d.catalog_item_id = item.catalog_item_id) as Monthly_Usage,
       stock_balance.last_update_dt,
       (select SUM(d.order_item_qty)
          from MM_ORDER_DETAIL_T d
          left join MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
         where o.order_create_dt between to_date('11/01/2010', 'MM/DD/YYYY') and
               to_date('11/30/2010', 'MM/DD/YYYY')
           and d.catalog_item_id = item.catalog_item_id) as Period_Activity,
       (select SUM(d.order_item_qty) * MAX(scost.stock_cst)
          from MM_ORDER_DETAIL_T d
          left join MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
          left join MM_CATALOG_ITEM_T ci
            on ci.catalog_item_id = d.catalog_item_id
          left join MM_STOCK_COST_T scost
            on scost.stock_id = ci.stock_id
         where o.order_create_dt between to_date('11/01/2010', 'MM/DD/YYYY') and
               to_date('11/30/2010', 'MM/DD/YYYY')
           and d.catalog_item_id = item.catalog_item_id
           and scost.cost_cd='02') as Period_Valuation,
       to_number((substr(MONTHS_BETWEEN(sysdate, stock.stock_create_dt),
                         0,
                         instr(MONTHS_BETWEEN(sysdate, stock.stock_create_dt),
                               '.') + 2))) as Months_On_Hand,
       (select SUM(d.order_item_qty)
          from MM_ORDER_DETAIL_T d
          left join MM_ORDER_DOC_T o
            on o.fdoc_nbr = d.order_doc_nbr
         where o.order_create_dt > trunc(sysdate, 'YEAR')
           and d.catalog_item_id = item.catalog_item_id) as YTD_Activity,
       catalog.warehouse_cd
  from MM_CATALOG_ITEM_T item
  left join MM_CATALOG_T catalog
    on item.catalog_id = catalog.catalog_id
  left join MM_AGREEMENT_T agreement
    on catalog.agreement_nbr = agreement.agreement_nbr
  left join MM_STOCK_T stock
    on stock.stock_id = item.stock_id
  left join MM_STOCK_BALANCE_T stock_balance
    on item.stock_id = stock_balance.stock_id
  left join MM_BIN_T bn
    on bn.bin_id = stock_balance.bin_id
  left join MM_ZONE_T zn
    on zn.zone_id = bn.zone_id
  left join MM_ORDER_DETAIL_T order_item
    on order_item.catalog_item_id = item.catalog_item_id
 group by catalog.catalog_begin_dt,
          catalog.catalog_end_dt,
          agreement.po_id,
          agreement.vendor_nm,
          catalog.catalog_cd,
          item.catalog_id,
          item.stock_id,
          item.distributor_nbr,
          item.catalog_desc,
          item.catalog_unit_of_issue_cd,
          item.catalog_prc,
          stock.stock_create_dt,
          zn.zone_cd,
          bn.bin_nbr,
          item.catalog_item_id,
          stock_balance.qty_on_hand,
          stock_balance.last_update_dt,
          catalog.warehouse_cd
 order by catalog.catalog_cd, item.catalog_id, item.stock_id;
