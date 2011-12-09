select distinct o.warehouse_cd warehouse,
                o.fdoc_nbr orderNo,
                o.order_id orderId,
                od.order_detail_id deatilId,
                od.distributor_nbr distNo,
                t.cylinder_serial_nbr serNo,
                t.cylinder_serial_nbr || ' ' ||
                substr(od.order_item_detail_desc, 20) itemDesc,
                c.daily_demurage_prc unitPrc,
                od.stock_unit_of_issue_cd unitIsse,
                Case
                  when coalesce(t.return_dt, current_date) >
                       coalesce(t.last_charge_dt,
                                LAST_DAY(current_date - 32)) then
                   Case
                     when coalesce(t.return_dt, current_date) < current_date then
                      trunc(t.return_dt -
                            coalesce(t.last_charge_dt,
                                     LAST_DAY(current_date - 32)))
                     else
                      trunc(current_date -
                            coalesce(t.last_charge_dt,
                                     LAST_DAY(current_date - 32)))
                   End
                  else
                   trunc(current_date -
                         coalesce(t.last_charge_dt,
                                  LAST_DAY(current_date - 32)))
                End as chargeDays
  from mm_cylinder_t t
  join mm_stock_t s
    on s.stock_id = t.stock_id
  join mm_cylinder_gas_t c
    on c.cylinder_gas_cd = s.cylinder_gas_cd
  join mm_pick_list_line_t p
    on p.pick_list_line_id = t.pick_list_line_id
  join mm_order_detail_t od
    on od.order_detail_id = p.order_detail_id
  left join mm_accounts_t da
    on da.order_detail_id = od.order_detail_id
  join mm_order_doc_t o
    on o.fdoc_nbr = od.order_doc_nbr
 where t.issued_dt is not null
   and (coalesce(t.return_dt, current_date) > last_day(current_date - 32))
 order by o.warehouse_cd, o.fdoc_nbr;