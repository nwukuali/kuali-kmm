select t.cylinder_serial_nbr as CYLINDER_SERIAL_NBR,
       c.cylinder_gas_desc as GAS_TYPE,
       nvl(t.last_charge_dt, LAST_DAY(sysdate-32)) as PREVIOUS_CHARGE_DATE,
       Case
         when nvl(t.return_dt, sysdate) > nvl(t.last_charge_dt,  LAST_DAY(sysdate-32))
           then Case 
                  when nvl(t.return_dt, sysdate) < sysdate
                    then t.return_dt
                  else sysdate
                End
         else current_date
       End as CHARGE_DATE,
       Case
         when nvl(t.return_dt, sysdate) > nvl(t.last_charge_dt, LAST_DAY(sysdate-32))
           then Case 
                  when nvl(t.return_dt, sysdate) < sysdate
                    then trunc(t.return_dt - nvl(t.last_charge_dt, LAST_DAY(sysdate-32)))
                  else trunc(sysdate - nvl(t.last_charge_dt, LAST_DAY(sysdate-32)))
                End
         else trunc(sysdate - nvl(t.last_charge_dt, LAST_DAY(sysdate-32)))
       End as CHARGE_DAYS,
       c.daily_demurage_prc as DAILY_CHARGE,
       Case
         when nvl(t.return_dt, sysdate) > nvl(t.last_charge_dt, LAST_DAY(sysdate-32))
           then Case 
                  when nvl(t.return_dt, sysdate) < sysdate
                    then (trunc(t.return_dt - nvl(t.last_charge_dt, LAST_DAY(sysdate-32))) * c.daily_demurage_prc)
                  else (trunc(current_date - nvl(t.last_charge_dt, LAST_DAY(sysdate-32))) * c.daily_demurage_prc)
                End
         else (trunc(sysdate - nvl(t.last_charge_dt, LAST_DAY(sysdate-32))) * c.daily_demurage_prc)
       End as CHARGE_TOTAL,
       s.stock_desc,
       Case
         when nvl(da.account_pct, oa.account_pct) > 0
           then nvl(da.account_pct, oa.account_pct)
         else 0
       End as CHARGE_PERCENT,
       Case
         when nvl(da.account_fixed_amt, oa.account_fixed_amt) > 0
           then nvl(da.account_fixed_amt, oa.account_fixed_amt)
         else 0
       End as CHARGE_FIXED,
       Case
         when nvl(da.fin_coa_cd, oa.fin_coa_cd) > 0
           then nvl(da.fin_coa_cd, oa.fin_coa_cd)
         else '0'
       End as CHARGE_COA,
       Case
         when nvl(da.account_nbr, oa.account_nbr) > 0
           then nvl(da.account_nbr, oa.account_nbr)
         else '0'
       End as CHARGE_ACCOUNT_NBR,       
       Case
         when nvl(da.sub_acct_nbr, oa.sub_acct_nbr) > 0
           then nvl(da.sub_acct_nbr, oa.sub_acct_nbr)
         else '0'
       End as CHARGE_SUB_ACCT_NBR,       
       Case
         when nvl(da.fin_object_cd, oa.fin_object_cd) > 0
           then nvl(da.fin_object_cd, oa.fin_object_cd)
         else '0'
       End as CHARGE_OBJ_CD,       
       Case
         when nvl(da.fin_sub_obj_cd, oa.fin_sub_obj_cd) > 0
           then nvl(da.fin_sub_obj_cd, oa.fin_sub_obj_cd)
         else '0'
       End as CHARGE_SUB_OBJ_CD,       
       Case
         when nvl(da.project_cd, oa.project_cd) > 0
           then nvl(da.project_cd, oa.project_cd)
         else '0'
       End as CHARGE_PROJECT_CD  
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
left join mm_accounts_t oa
   on oa.order_doc_nbr = o.fdoc_nbr
where t.issued_dt is not null 
  and (nvl(t.return_dt, sysdate) > LAST_DAY(sysdate-32)) 
;
 
