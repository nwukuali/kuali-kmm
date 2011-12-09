create or replace view mm_stock_ordered_v as
select wh.warehouse_cd as warehouse_cd,
       s.stock_id,
       max(s.stock_distributor_nbr) as stock_distributor_nbr,
       sum(nvl(pline.stock_qty, 0)) as ordered_qty,
       sum(nvl(td.back_order_stock_qty, 0)) as back_order_qty,
       max(case
             when pline.pick_status_cd = 'INIT' or pline.pick_status_cd = 'PRTD' or
                  pline.pick_status_cd = 'ASND' then
              pline.last_update_dt
             else
              null
           end) as last_order_date,
       min(pline.last_update_dt) as first_order_date,
       sum(case
             when pline.pick_status_cd = 'INIT' or pline.pick_status_cd = 'PRTD' or
                  pline.pick_status_cd = 'ASND' then
              pline.stock_qty
             else
              0
           end) as on_order_qty
  from mm_warehouse_t wh
  join mm_catalog_t ctg
    on wh.warehouse_cd = ctg.warehouse_cd
   and ctg.actv_ind = 'Y'
  join mm_catalog_item_t cit
    on cit.catalog_id = ctg.catalog_id
  join mm_stock_t s
    on s.stock_id = cit.stock_id
  left join mm_pick_list_line_t pline
    on pline.stock_id = s.stock_id
   and pline.pick_status_cd != 'CLSD'
   and pline.pick_status_cd != 'CNCL'
  left join mm_back_order_t td
    on td.pick_list_line_id = pline.pick_list_line_id
   and td.filled_ind = 'N'
   and td.canceled_ind = 'N'
 where wh.actv_ind = 'Y'
   and ctg.actv_ind = 'Y'   
   and ctg.catalog_type_cd in ('1', '4')
 group by wh.warehouse_cd, s.stock_id;

create or replace view mm_stock_reordered_v as
select wh.warehouse_cd as warehouse_cd,
       ctitm.stock_id,
       ctitm.distributor_nbr as stock_distributor_nbr,
       max(nvl(odett.oqty - odett.cqty, 0)) as reordered_qty,
       max(reord.last_reorder_dt) last_reorder_date
  from mm_warehouse_t wh
  join mm_catalog_t ctlg
    on ctlg.warehouse_cd = wh.warehouse_cd
  join mm_catalog_item_t ctitm
    on ctitm.catalog_id = ctlg.catalog_id
  join mm_stock_t s
    on s.stock_id = ctitm.stock_id
  left join (select ord.warehouse_cd whcd,
                    odet.distributor_nbr distr_nbr,
                    odet.order_item_qty oqty,
                    sum(nvl(cdt.accepted_item_qty, 0) +
                        nvl(cdt.rejected_item_qty, 0)) cqty
               from mm_order_doc_t ord
               join mm_order_detail_t odet
                 on odet.order_doc_nbr = ord.fdoc_nbr
                and odet.order_detail_status_cd in ('O', 'R', 'P')
               left join mm_checkin_doc_t cdc
                 on cdc.order_doc_nbr = ord.fdoc_nbr
                and cdc.final_ind = 'Y'
               left join mm_checkin_detail_t cdt
                 on cdt.checkin_doc_nbr = cdc.fdoc_nbr
                and cdt.order_detail_id = odet.order_detail_id
                and not exists
              (select 1
                       from mm_checkin_doc_t icdc, mm_checkin_detail_t icdt
                      where icdc.fdoc_nbr = icdt.checkin_doc_nbr
                        and icdc.final_ind = 'Y'
                        and icdt.corrected_checkin_detail_id =
                            cdt.checkin_detail_id)
              where ord.order_type_cd = 'STOCK'
                and ord.order_doc_status_cd in ('O', 'R', 'P')
              group by ord.warehouse_cd,
                       odet.distributor_nbr,
                       odet.order_item_qty) odett
    on odett.distr_nbr = ctitm.distributor_nbr
   and odett.whcd = wh.warehouse_cd
  left join (select iord.warehouse_cd whcd,
                    idet.distributor_nbr,
                    max(iord.order_create_dt) last_reorder_dt
               from mm_order_doc_t iord, mm_order_detail_t idet
              where idet.order_doc_nbr = iord.fdoc_nbr
                and iord.order_type_cd = 'STOCK'
                and iord.order_doc_status_cd not in ('X', 'D', 'I')
              group by iord.warehouse_cd, idet.distributor_nbr) reord
    on reord.distributor_nbr = ctitm.distributor_nbr
   and reord.whcd = wh.warehouse_cd
 where wh.actv_ind = 'Y'
   and ctlg.actv_ind = 'Y'   
   and ctlg.catalog_type_cd in ('1', '4')
 group by wh.warehouse_cd, ctitm.stock_id, ctitm.distributor_nbr;

create or replace view mm_stock_balance_v as
select wh.warehouse_cd warehouse_cd,
       max(case
             when s.stock_agreement_nbr is not null then
              s.stock_agreement_nbr
             else
              null
           end) agreement_nbr,
       max(case
             when s.stock_agreement_nbr is not null then
              null
             else
              cgrp.catalog_group_nm
           end) group_desc,
       max(case
             when s.stock_agreement_nbr is not null then
              null
             else
              csgrp.catalog_subgroup_desc
           end) subgroup_desc,
       max(case
             when s.stock_agreement_nbr is not null then
              null
             else
              csgrp.catalog_subgroup_id
           end) catalog_subgroup_id,
       max(case
             when s.stock_agreement_nbr is not null then
              null
             else
              cgrp.catalog_group_cd
           end) catalog_group_cd,
       max(case
             when s.stock_agreement_nbr is not null then
              null
             else
              csgrp.catalog_subgroup_cd
           end) catalog_subgroup_cd,
       s.stock_id stock_id,
       max(s.stock_distributor_nbr) stock_distributor_nbr,
       max(nvl(sbval.qtyoh, 0)) on_hand_qty,
       max(nvl(sov.on_order_qty, 0)) on_order_qty,
       max(nvl(sov.back_order_qty, 0)) on_back_order_qty,
       max(nvl(srov.reordered_qty, 0)) on_reorder_qty,
       max(nvl(cnt.times_counted, 0)) descrepancy_ind,
       max(nvl(s.minimum_order_qty, 0)) minimum_po_qty,
       max(nvl(s.maximum_order_qty, 0)) maximum_order_qty,
       min(sbval.stock_perishable_dt) perishable_date,
       max(nvl(sov.last_order_date, sysdate - 3000)) last_order_date,
       max(nvl(srov.last_reorder_date, sysdate - 3000)) last_reorder_date,
       max(nvl(s.reorder_point_qty, 0)) reorder_point_qty,
       max(nvl(sov.first_order_date, sysdate)) first_order_date,
       max(nvl(agr.agreement_lag_days, 0)) agreement_lag_days,
       max(nvl(sov.ordered_qty, 0)) all_orders_qty,
       max(trunc(to_number(substr(current_date -
                                  nvl(sov.first_order_date, current_date),
                                  1,
                                  instr(current_date -
                                        nvl(sov.first_order_date,
                                            current_date),
                                        ' '))))) as days_of_orders,
       max(nvl(s.reorder_remove_until_dt, sysdate - 1)) remove_until_dt
  from mm_stock_t s
  join mm_catalog_item_t cit
    on cit.stock_id = s.stock_id
  join mm_catalog_t ctg
    on ctg.catalog_id = cit.catalog_id
  join mm_warehouse_t wh
    on wh.warehouse_cd = ctg.warehouse_cd
  join mm_catalog_subgroup_item_t csi
    on csi.catalog_item_id = cit.catalog_item_id
  join mm_catalog_subgroup_t csgrp
    on csgrp.catalog_subgroup_id = csi.catalog_subgroup_id
  join mm_catalog_group_t cgrp
    on cgrp.catalog_group_cd = csgrp.catalog_group_cd
  left join mm_stock_ordered_v sov
    on sov.stock_id = s.stock_id
   and sov.warehouse_cd = ctg.warehouse_cd
  left join mm_stock_reordered_v srov
    on srov.stock_id = s.stock_id
   and srov.warehouse_cd = ctg.warehouse_cd
  left join mm_agreement_t agr
    on agr.agreement_nbr = s.stock_agreement_nbr
   and agr.actv_ind = 'Y'
  left join mm_stock_count_t cnt
    on cnt.stock_id = s.stock_id
  left join (select izn.warehouse_cd whcd,
                    isb.stock_id stkid,
                    sum(isb.qty_on_hand) qtyoh,
                    min(nvl(isb.stock_perishable_dt, sysdate + 3000)) stock_perishable_dt
               from mm_stock_balance_t isb
               join mm_bin_t ibn
                 on ibn.bin_id = isb.bin_id
               join mm_zone_t izn
                 on izn.zone_id = ibn.zone_id
              group by izn.warehouse_cd, isb.stock_id) sbval
    on sbval.whcd = ctg.warehouse_cd
   and sbval.stkid = s.stock_id
 where wh.actv_ind = 'Y'
   and ctg.actv_ind = 'Y'
   and ctg.catalog_type_cd in ('1', '4')
   and s.obsolete_ind = 'N'
 group by wh.warehouse_cd, s.stock_id;


create or replace view mm_reorder_summary_v as
select warehouse_cd,
       agreement_nbr,
       group_desc,
       subgroup_desc,
       catalog_subgroup_id,
       catalog_group_cd,
       catalog_subgroup_cd,
       count(stock_distributor_nbr) as reorder,
       sum(case when on_back_order_qty > 0 then 1 else 0 end) as backorder,
       sum(case when perishable_date < current_date then 1 else 0 end) as exp,
       max(last_reorder_date) as reorder_date,
       max(case when descrepancy_ind > 0 then 1 else 0 end) as descrepancy,
       max(case when minimum_po_qty > 0 then 1 else 0 end) as minimumorderqty,
       max(case when last_order_date < current_date - 365 then 1 else 0 end) as no_sales_over_year,
       max(case when last_order_date < current_date - 31 then 1 else 0 end) as no_sales_last_month
from mm_stock_balance_v
where reorder_point_qty > 0 and reorder_point_qty >= (on_hand_qty + on_reorder_qty) - (on_order_qty + on_back_order_qty)
       and warehouse_cd is not null and sysdate > remove_until_dt
group by warehouse_cd,
         agreement_nbr,
         group_desc,
         subgroup_desc,
         catalog_subgroup_id,
         catalog_group_cd,
         catalog_subgroup_cd;

create or replace view mm_customer_return_lines_v as
select distinct odt.order_detail_id,
                odt.order_detail_status_cd order_detail_status_cd,
                oct.order_type_cd order_type_cd,
                oct.warehouse_cd warehouse_cd,
                oct.order_create_dt,
                pft.customer_prncpl_nm,
                odt.catalog_item_id catalog_item_id,
                odt.distributor_nbr distributor_nbr,
                oct.vendor_nm vendor_nm,
                oct.ar_id ar_id,
                oct.reqs_id reqs_id,
                oct.fdoc_nbr fdoc_nbr,
                odt.po_id po_id,
                oct.order_id order_id,
                (select sum(mptt.pick_stock_qty)
                   from mm_pick_list_line_t mptt
                  where mptt.order_detail_id = odt.order_detail_id
                    and mptt.pick_status_cd in ('PBCK', 'PCKD')) -
                (select nvl(sum(rdt.return_qty), 0)
                   from mm_return_detail_t rdt, mm_return_doc_t rdoc
                  where rdoc.fdoc_nbr = rdt.return_doc_nbr
                    and rdoc.return_doc_status_cd in ('O','V')
                    and rdt.order_detail_id = odt.order_detail_id) balance_qty
  from mm_order_doc_t oct
  join mm_order_detail_t odt
    on odt.order_doc_nbr = oct.fdoc_nbr
  join mm_pick_list_line_t mpt
    on mpt.order_detail_id = odt.order_detail_id
  left join mm_profile_t pft
    on pft.profile_id = oct.customer_profile_id
 where (select sum(mptt.pick_stock_qty)
          from mm_pick_list_line_t mptt
         where mptt.order_detail_id = odt.order_detail_id
           and mptt.pick_status_cd in ('PBCK', 'PCKD')) >
       (select nvl(sum(mdt.return_qty), 0)
          from mm_return_detail_t mdt, mm_return_doc_t mdoc
         where mdt.order_detail_id = odt.order_detail_id
           and mdoc.return_doc_status_cd in ('O','V')
           and mdoc.fdoc_nbr = mdt.return_doc_nbr)
   and oct.order_type_cd = 'WAREHS'
   and mpt.pick_status_cd in ('PBCK', 'PCKD')
   and oct.order_doc_status_cd in ('O', 'T', 'C');

create or replace view mm_vendor_return_lines_v as
select odt.order_detail_id,
       odt.order_detail_status_cd order_detail_status_cd,
       oct.order_type_cd order_type_cd,
       oct.warehouse_cd warehouse_cd,
       oct.order_create_dt,
       oct.customer_profile_id,
       odt.catalog_item_id catalog_item_id,
       odt.distributor_nbr distributor_nbr,
       oct.vendor_nm vendor_nm,
       oct.ar_id ar_id,
       oct.reqs_id reqs_id,
       oct.fdoc_nbr fdoc_nbr,
       odt.po_id po_id,
       oct.order_id order_id,
       odt.order_item_qty -
       (select nvl(sum(rdt.return_qty), 0)
          from mm_return_detail_t rdt, mm_return_doc_t rdoc
         where rdoc.return_doc_status_cd in ('O','V')
           and rdoc.fdoc_nbr = rdt.return_doc_nbr
           and rdt.order_detail_id = odt.order_detail_id) balance_qty
  from mm_order_doc_t oct
  join mm_order_detail_t odt
    on odt.order_doc_nbr = oct.fdoc_nbr
 where odt.order_item_qty >
       (select nvl(sum(mdt.return_qty), 0)
          from mm_return_detail_t mdt, mm_return_doc_t rdoc
         where rdoc.return_doc_status_cd in ('O','V')
           and rdoc.fdoc_nbr = mdt.return_doc_nbr
           and mdt.order_detail_id = odt.order_detail_id)
   and oct.order_type_cd = 'STOCK'
   and oct.order_doc_status_cd in ('O', 'C', 'R', 'S', 'T')
   and nvl(odt.po_id, 0) > 0;

--Added on Oct-01-2010 by Harsha
create or replace view mm_checkin_receivable_v as
select distinct ot.order_detail_id,
                ot.po_id,
                ot.distributor_nbr,
                ot.order_doc_nbr,
                od.order_id,
                od.reqs_id,
                od.warehouse_cd,
                od.vendor_nm,
                ot.order_item_qty,
                nvl(qtytbl.aqty, 0) accepted_qty,
                nvl(qtytbl.rqty, 0) rejected_qty
  from mm_order_doc_t od
  join mm_order_detail_t ot
    on od.fdoc_nbr = ot.order_doc_nbr
  left join (select ctt.order_detail_id dtid,
                    sum(ctt.accepted_item_qty) aqty,
                    sum(ctt.rejected_item_qty) rqty
               from mm_checkin_doc_t cdd, mm_checkin_detail_t ctt
              where ctt.checkin_doc_nbr = cdd.fdoc_nbr
                and cdd.final_ind = 'Y'
                and not exists
              (select 1
                       from mm_checkin_doc_t icdc, mm_checkin_detail_t icdt
                      where icdc.fdoc_nbr = icdt.checkin_doc_nbr
                        and icdc.final_ind = 'Y'
                        and icdt.corrected_checkin_detail_id =
                            ctt.checkin_detail_id)
              group by ctt.order_detail_id) qtytbl
    on ot.order_detail_id = qtytbl.dtid
 where od.order_type_cd = 'STOCK'
   and ot.order_detail_status_cd in ('O', 'R', 'P')
   and ot.po_id is not null
   and ot.order_item_qty > (nvl(qtytbl.aqty, 0) + nvl(qtytbl.rqty, 0));

create or replace view mm_checkin_correction_v as
select distinct ct.order_detail_id,
                ct.po_id,
                ot.distributor_nbr,
                ot.order_doc_nbr,
                od.order_id,
                od.reqs_id,
                od.warehouse_cd,
                cd.fdoc_nbr as checkin_doc_nbr,
                cd.vendor_ref_nbr,
                cd.vendor_shipment_nbr,
                od.vendor_nm,
                ot.order_item_qty
  from mm_checkin_detail_t ct
  left join mm_checkin_doc_t cd
    on cd.fdoc_nbr = ct.checkin_doc_nbr
   and cd.final_ind = 'Y'
  left join mm_order_doc_t od
    on od.fdoc_nbr = cd.order_doc_nbr
  left join mm_order_detail_t ot
    on ot.order_detail_id = ct.order_detail_id
 where ct.accepted_item_qty > 0
   and not exists
 (select 1
          from mm_checkin_doc_t icdc, mm_checkin_detail_t icdt
         where icdc.fdoc_nbr = icdt.checkin_doc_nbr
           and icdc.final_ind = 'Y'
           and icdt.corrected_checkin_detail_id = ct.checkin_detail_id);
   
create or replace view mm_awaiting_shipment_v as
select orderDoc.Order_Id as order_nbr,
       pickLine.Stock_Qty as qty_to_ship,
       (select SUM(pline.Stock_Qty)
          from MM_PICK_LIST_LINE_T pline
          left join MM_CATALOG_ITEM_T cItem
            on pline.stock_id = cItem.Stock_Id
         where cItem.Catalog_Item_Id = detail.catalog_item_id
           and pLine.Pick_Status_Cd in ('INIT', 'ASND', 'PRTD')) as total_to_ship,
       detail.stock_unit_of_issue_cd as ui,
       orderDoc.Delivery_Department_Nm as ship_to_cd,
       orderDoc.Order_Create_Dt as date_entered,
       detail.distributor_nbr as item_nbr,
       detail.order_item_detail_desc as item_desc,
       (zn.warehouse_cd || ' / ' || zn.zone_cd || ' / ' || bn.bin_nbr ||
       ' / ' || bn.shelf_id || ' / ' || bn.shelf_id_nbr) as bin_location
  from MM_PICK_LIST_LINE_T pickLine
  left join MM_ORDER_DETAIL_T detail
    on detail.order_detail_id = pickLine.Order_Detail_Id
  left join MM_ORDER_DOC_T orderDoc
    on orderDoc.Fdoc_Nbr = detail.order_doc_nbr
  left join MM_BIN_T bn
    on bn.bin_id = pickLine.Bin_Id
  left join MM_ZONE_T zn
    on zn.zone_id = bn.zone_id
 where pickLine.Pick_Status_Cd in ('INIT', 'ASND', 'PRTD')
 order by detail.distributor_nbr;

create or replace view MM_MASS_MAINT_STOCK_V as
select catalog.catalog_cd as catalog_cd,
       item.stock_id as stock_id,
       stock.stock_agreement_nbr as agreement_nbr,
       item.distributor_nbr as distributor_nbr,
       item.catalog_desc as stock_desc,
       item.catalog_unit_of_issue_cd as unit_of_issue,
       item.catalog_prc as item_cost,
       stock.cycle_cnt_cd as cycle_cnt_cd,
       SUM(stock_balance.qty_on_hand) as qty_on_hand,
       (item.catalog_prc * SUM(stock_balance.qty_on_hand)) as on_hand_value,
       nvl((select SUM(d.order_item_qty)
             from MM_ORDER_DETAIL_T d
             left join MM_ORDER_DOC_T o
               on o.fdoc_nbr = d.order_doc_nbr
            where d.catalog_item_id = item.catalog_item_id 
              and o.order_create_dt > ADD_MONTHS(sysdate, -12)),
           0) as annual_usage,
       nvl((select SUM(d.order_item_qty)
             from MM_ORDER_DETAIL_T d
             left join MM_ORDER_DOC_T o
               on o.fdoc_nbr = d.order_doc_nbr
            where o.order_create_dt > trunc(sysdate, 'YEAR')
              and d.catalog_item_id = item.catalog_item_id),
           0) as year_to_date,
       stock.actv_ind as ACTV_IND,
       stock.obsolete_ind as OBSOLETE_IND
  from MM_CATALOG_ITEM_T item
  left join MM_CATALOG_T catalog
    on item.catalog_id = catalog.catalog_id
  left join MM_STOCK_T stock
    on stock.stock_id = item.stock_id
  left join MM_STOCK_BALANCE_T stock_balance
    on item.stock_id = stock_balance.stock_id
 group by catalog.catalog_cd,
          item.stock_id,
          item.catalog_id,
          stock.stock_agreement_nbr,
          item.distributor_nbr,
          item.catalog_desc,
          item.catalog_unit_of_issue_cd,
          item.catalog_prc,
          item.catalog_item_id,
          stock.cycle_cnt_cd,
          stock.actv_ind,
          stock.obsolete_ind
 order by catalog.catalog_cd, item.catalog_id, item.distributor_nbr;
 
create or replace view mm_catalog_item_live_v as
select i."CATALOG_ITEM_ID",
       i."OBJ_ID",
       i."VER_NBR",
       i."DISTRIBUTOR_NBR",
       i."MANUFACTURER_NBR",
       i."CATALOG_UNIT_OF_ISSUE_CD",
       i."CATALOG_PRC",
       i."CATALOG_DESC",
       i."RECYCLED_IND",
       i."WILLCALL_IND",
       i."UNSPSC_CD",
       i."SHIPPING_WGT",
       i."SHIPPING_HT",
       i."SHIPPING_WD",
       i."SHIPPING_LH",
       i."CATALOG_ID",
       i."STOCK_ID",
       i."DISPLAYABLE_IND",
       i."CATALOG_ITEM_PND_ID",
       i."SUBSTITUTE_DISTRIBUTOR_NBR",
       i."TAXABLE_IND",
       i."SPAID_ID",
       i."ACTV_IND",
       i."LAST_UPDATE_DT",
       ca.priority_nbr,
       s.catalog_subgroup_id,
       sg.prior_catalog_subgroup_id,
       sg.catalog_group_cd,
       count(o.catalog_item_id) as order_count
from MM_CATALOG_ITEM_T i
left join MM_CATALOG_SUBGROUP_ITEM_T s
  on s.catalog_item_id = i.catalog_item_id
left join MM_CATALOG_SUBGROUP_T sg
  on sg.catalog_subgroup_id = s.catalog_subgroup_id
left join MM_CATALOG_T ca
  on ca.catalog_id=i.catalog_id
left join MM_ORDER_DETAIL_T o
  on o.catalog_item_id=i.catalog_item_id
  group by  i."CATALOG_ITEM_ID",
       i."OBJ_ID",
       i."VER_NBR",
       i."DISTRIBUTOR_NBR",
       i."MANUFACTURER_NBR",
       i."CATALOG_UNIT_OF_ISSUE_CD",
       i."CATALOG_PRC",
       i."CATALOG_DESC",
       i."RECYCLED_IND",
       i."WILLCALL_IND",
       i."UNSPSC_CD",
       i."SHIPPING_WGT",
       i."SHIPPING_HT",
       i."SHIPPING_WD",
       i."SHIPPING_LH",
       i."CATALOG_ID",
       i."STOCK_ID",
       i."DISPLAYABLE_IND",
       i."CATALOG_ITEM_PND_ID",
       i."SUBSTITUTE_DISTRIBUTOR_NBR",
       i."TAXABLE_IND",
       i."SPAID_ID",
       i."ACTV_IND",
       i."LAST_UPDATE_DT",
       ca.priority_nbr,
       s.catalog_subgroup_id,
       sg.prior_catalog_subgroup_id,
       sg.catalog_group_cd,o.catalog_item_id;
