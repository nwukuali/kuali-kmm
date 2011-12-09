select o.warehouse_cd warehouse,
       o.fdoc_nbr orderNo,
       o.order_id orderId,
       da.fin_coa_cd chart,
       da.account_nbr acct,
       da.sub_acct_nbr subAcct,
       da.fin_object_cd objCd,
       da.fin_sub_obj_cd subObjcd,
       da.project_cd prjCd,
       sum((Case
             when coalesce(t.return_dt, current_date) >
                  coalesce(t.last_charge_dt, LAST_DAY(current_date - 32)) then
              Case
                when coalesce(t.return_dt, current_date) < current_date then
                 (trunc(t.return_dt -
                        coalesce(t.last_charge_dt, LAST_DAY(current_date - 32))) *
                 c.daily_demurage_prc)
                else
                 (trunc(current_date -
                        coalesce(t.last_charge_dt, LAST_DAY(current_date - 32))) *
                 c.daily_demurage_prc)
              End
             else
              (trunc(current_date -
                     coalesce(t.last_charge_dt, LAST_DAY(current_date - 32))) *
              c.daily_demurage_prc)
           End) * (coalesce(da.account_pct, 0) / 100)) as amt
  from mm_cylinder_t t
  join mm_stock_t s
    on s.stock_id = t.stock_id
  join mm_cylinder_gas_t c
    on c.cylinder_gas_cd = s.cylinder_gas_cd
  join mm_pick_list_line_t p
    on p.pick_list_line_id = t.pick_list_line_id
  join mm_order_detail_t d
    on d.order_detail_id = p.order_detail_id
  left join mm_accounts_t da
    on da.order_detail_id = d.order_detail_id
  join mm_order_doc_t o
    on o.fdoc_nbr = d.order_doc_nbr
 where t.issued_dt is not null
   and (coalesce(t.return_dt, current_date) > LAST_DAY(current_date - 32))
 group by o.warehouse_cd,
          o.fdoc_nbr,
          o.order_id,
          da.fin_coa_cd,
          da.account_nbr,
          da.sub_acct_nbr,
          da.fin_object_cd,
          da.fin_sub_obj_cd,
          da.project_cd
 order by o.warehouse_cd, o.fdoc_nbr;