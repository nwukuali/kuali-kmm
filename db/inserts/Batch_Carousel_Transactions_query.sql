select '100' ||
        rpad(' ',64) ||
        Case
         When length(t.order_id) = 14
          then Cast(t.order_id as char(14)) || rpad(' ',50)
         When length(t.order_id) = 13
          then Cast(t.order_id as char(13)) || rpad(' ',51)
         When length(t.order_id) = 12
          then Cast(t.order_id as char(12)) || rpad(' ',52)
         When length(t.order_id) = 11
          then Cast(t.order_id as char(11)) || rpad(' ',53)
         When length(t.order_id) = 10
          then Cast(t.order_id as char(10)) || rpad(' ',54)
         When length(t.order_id) = 9
          then Cast(t.order_id as char(9)) || rpad(' ',55)
         When length(t.order_id) = 8
          then Cast(t.order_id as char(8)) || rpad(' ',56)
         When length(t.order_id) = 7
          then Cast(t.order_id as char(7)) || rpad(' ',57)
         When length(t.order_id) = 6
          then Cast(t.order_id as char(6)) || rpad(' ',58)
         When length(t.order_id) = 5
          then Cast(t.order_id as char(5)) || rpad(' ',59)
         When length(t.order_id) = 4
          then Cast(t.order_id as char(4)) || rpad(' ',60)
         When length(t.order_id) = 3
          then Cast(t.order_id as char(3)) || rpad(' ',61)
         When length(t.order_id) = 2
          then Cast(t.order_id as char(2)) || rpad(' ',62)
         else  Cast(t.order_id as char(1)) || rpad(' ',63)
        End ||
        rpad(' ',6) ||
        '01' ||
        substr(e.zone_cd,2,1) ||
        '0' ||
        substr(d.bin_nbr,1,2) ||
        d.shelf_id ||
        d.shelf_id_nbr ||
        Case
         When length(a.stock_qty) = 6
          then Cast(a.stock_qty as char(6))
         When length(a.stock_qty) = 5
          then rpad(' ',1) || Cast(a.stock_qty as char(5))
         When length(a.stock_qty) = 4
          then rpad(' ',2) || Cast(a.stock_qty as char(4))
         When length(a.stock_qty) = 3
          then rpad(' ',3) || Cast(a.stock_qty as char(3))
         When length(a.stock_qty) = 2
          then rpad(' ',4) || Cast(a.stock_qty as char(2))
         else  rpad(' ',5) || Cast(a.stock_qty as char(1))
        End ||
        f.stock_distributor_nbr ||
        rpad(' ',64 - length(f.stock_distributor_nbr)) ||
        substr(f.stock_desc,1,192) ||
        rpad(' ',192 - length(f.stock_desc)) ||
        f.sales_unit_of_issue_cd ||
        rpad(' ',54)        as PickData
from MM_PICK_LIST_LINE_T a
join MM_PICK_TICKET_T b
    on b.pick_ticket_nbr = a.pick_ticket_nbr
   and b.pick_status_cd = 'PRTD'
join MM_STOCK_BALANCE_T c
    on c.stock_id = a.stock_id
   and c.bin_id = a.bin_id
join MM_BIN_T d
    on d.bin_id = c.bin_id
join MM_ZONE_T e
    on e.zone_id = d.zone_id
   and e.zone_cd in ('01', '02', '03', '04')
join MM_STOCK_T f
    on f.stock_id = a.stock_id
join MM_ORDER_DETAIL_T o
    on o.order_detail_id = a.order_detail_id
join MM_ORDER_DOC_T t
    on t.fdoc_nbr = o.order_doc_nbr
    
union

select distinct '101' ||
        rpad(' ',64) ||
        Case
         When length(t.order_id) = 14
          then Cast(t.order_id as char(14)) || rpad(' ',50)
         When length(t.order_id) = 13
          then Cast(t.order_id as char(13)) || rpad(' ',51)
         When length(t.order_id) = 12
          then Cast(t.order_id as char(12)) || rpad(' ',52)
         When length(t.order_id) = 11
          then Cast(t.order_id as char(11)) || rpad(' ',53)
         When length(t.order_id) = 10
          then Cast(t.order_id as char(10)) || rpad(' ',54)
         When length(t.order_id) = 9
          then Cast(t.order_id as char(9)) || rpad(' ',55)
         When length(t.order_id) = 8
          then Cast(t.order_id as char(8)) || rpad(' ',56)
         When length(t.order_id) = 7
          then Cast(t.order_id as char(7)) || rpad(' ',57)
         When length(t.order_id) = 6
          then Cast(t.order_id as char(6)) || rpad(' ',58)
         When length(t.order_id) = 5
          then Cast(t.order_id as char(5)) || rpad(' ',59)
         When length(t.order_id) = 4
          then Cast(t.order_id as char(4)) || rpad(' ',60)
         When length(t.order_id) = 3
          then Cast(t.order_id as char(3)) || rpad(' ',61)
         When length(t.order_id) = 2
          then Cast(t.order_id as char(2)) || rpad(' ',62)
         else  Cast(t.order_id as char(1)) || rpad(' ',63)
        End ||
        '0100'        as PickData
from MM_PICK_LIST_LINE_T a
join MM_PICK_TICKET_T b
    on b.pick_ticket_nbr = a.pick_ticket_nbr
   and b.pick_status_cd = 'PRTD'
join MM_STOCK_BALANCE_T c
    on c.stock_id = a.stock_id
   and c.bin_id = a.bin_id
join MM_BIN_T d
    on d.bin_id = c.bin_id
join MM_ZONE_T e
    on e.zone_id = d.zone_id
   and e.zone_cd in ('01', '02', '03', '04')
join MM_STOCK_T f
    on f.stock_id = a.stock_id
join MM_ORDER_DETAIL_T o
    on o.order_detail_id = a.order_detail_id
join MM_ORDER_DOC_T t
    on t.fdoc_nbr = o.order_doc_nbr

union

select '103' ||
        rpad(' ',64) ||
        Case
         When length(t.order_id) = 14
          then Cast(t.order_id as char(14)) || rpad(' ',50)
         When length(t.order_id) = 13
          then Cast(t.order_id as char(13)) || rpad(' ',51)
         When length(t.order_id) = 12
          then Cast(t.order_id as char(12)) || rpad(' ',52)
         When length(t.order_id) = 11
          then Cast(t.order_id as char(11)) || rpad(' ',53)
         When length(t.order_id) = 10
          then Cast(t.order_id as char(10)) || rpad(' ',54)
         When length(t.order_id) = 9
          then Cast(t.order_id as char(9)) || rpad(' ',55)
         When length(t.order_id) = 8
          then Cast(t.order_id as char(8)) || rpad(' ',56)
         When length(t.order_id) = 7
          then Cast(t.order_id as char(7)) || rpad(' ',57)
         When length(t.order_id) = 6
          then Cast(t.order_id as char(6)) || rpad(' ',58)
         When length(t.order_id) = 5
          then Cast(t.order_id as char(5)) || rpad(' ',59)
         When length(t.order_id) = 4
          then Cast(t.order_id as char(4)) || rpad(' ',60)
         When length(t.order_id) = 3
          then Cast(t.order_id as char(3)) || rpad(' ',61)
         When length(t.order_id) = 2
          then Cast(t.order_id as char(2)) || rpad(' ',62)
         else  Cast(t.order_id as char(1)) || rpad(' ',63)
        End ||
        rpad(' ',4) ||
        '01' ||
        substr(e.zone_cd,2,1) ||
        '0' ||
        substr(d.bin_nbr,1,2) ||
        d.shelf_id ||
        d.shelf_id_nbr ||
        Case
         When length(b.accepted_item_qty) = 6
          then Cast(b.accepted_item_qty as char(6))
         When length(b.accepted_item_qty) = 5
          then rpad(' ',1) || Cast(b.accepted_item_qty as char(5))
         When length(b.accepted_item_qty) = 4
          then rpad(' ',2) || Cast(b.accepted_item_qty as char(4))
         When length(b.accepted_item_qty) = 3
          then rpad(' ',3) || Cast(b.accepted_item_qty as char(3))
         When length(b.accepted_item_qty) = 2
          then rpad(' ',4) || Cast(b.accepted_item_qty as char(2))
         else rpad(' ',5) || Cast(b.accepted_item_qty as char(1))
        End ||
        f.stock_distributor_nbr ||
        rpad(' ',64 - length(f.stock_distributor_nbr)) ||
        substr(f.stock_desc,1,192) ||
        rpad(' ',192 - length(f.stock_desc)) ||
        f.sales_unit_of_issue_cd ||
        rpad(' ',54) ||
        Case
         When length(t.order_id) = 14
          then Cast(t.order_id as char(14)) || rpad(' ',50)
         When length(t.order_id) = 13
          then Cast(t.order_id as char(13)) || rpad(' ',51)
         When length(t.order_id) = 12
          then Cast(t.order_id as char(12)) || rpad(' ',52)
         When length(t.order_id) = 11
          then Cast(t.order_id as char(11)) || rpad(' ',53)
         When length(t.order_id) = 10
          then Cast(t.order_id as char(10)) || rpad(' ',54)
         When length(t.order_id) = 9
          then Cast(t.order_id as char(9)) || rpad(' ',55)
         When length(t.order_id) = 8
          then Cast(t.order_id as char(8)) || rpad(' ',56)
         When length(t.order_id) = 7
          then Cast(t.order_id as char(7)) || rpad(' ',57)
         When length(t.order_id) = 6
          then Cast(t.order_id as char(6)) || rpad(' ',58)
         When length(t.order_id) = 5
          then Cast(t.order_id as char(5)) || rpad(' ',59)
         When length(t.order_id) = 4
          then Cast(t.order_id as char(4)) || rpad(' ',60)
         When length(t.order_id) = 3
          then Cast(t.order_id as char(3)) || rpad(' ',61)
         When length(t.order_id) = 2
          then Cast(t.order_id as char(2)) || rpad(' ',62)
         else  Cast(t.order_id as char(1)) || rpad(' ',63)
        End 
        as PickData
from MM_ORDER_DETAIL_T a
join MM_CHECKIN_DETAIL_T b
    on b.order_detail_id = a.order_detail_id
join MM_STOCK_BALANCE_T c
    on c.stock_id = b.stock_id
   and c.bin_id = b.bin_id
join MM_BIN_T d
    on d.bin_id = c.bin_id
join MM_ZONE_T e
    on e.zone_id = d.zone_id
   and e.zone_cd in ('01', '02', '03', '04')
join MM_STOCK_T f
    on f.stock_id = b.stock_id
join MM_ORDER_DOC_T t
    on t.fdoc_nbr = a.order_doc_nbr
where a.ORDER_DETAIL_STATUS_CD in ('R', 'S')

union

select distinct '104' ||
        rpad(' ',64) ||
        Case
         When length(t.order_id) = 14
          then Cast(t.order_id as char(14)) || rpad(' ',50)
         When length(t.order_id) = 13
          then Cast(t.order_id as char(13)) || rpad(' ',51)
         When length(t.order_id) = 12
          then Cast(t.order_id as char(12)) || rpad(' ',52)
         When length(t.order_id) = 11
          then Cast(t.order_id as char(11)) || rpad(' ',53)
         When length(t.order_id) = 10
          then Cast(t.order_id as char(10)) || rpad(' ',54)
         When length(t.order_id) = 9
          then Cast(t.order_id as char(9)) || rpad(' ',55)
         When length(t.order_id) = 8
          then Cast(t.order_id as char(8)) || rpad(' ',56)
         When length(t.order_id) = 7
          then Cast(t.order_id as char(7)) || rpad(' ',57)
         When length(t.order_id) = 6
          then Cast(t.order_id as char(6)) || rpad(' ',58)
         When length(t.order_id) = 5
          then Cast(t.order_id as char(5)) || rpad(' ',59)
         When length(t.order_id) = 4
          then Cast(t.order_id as char(4)) || rpad(' ',60)
         When length(t.order_id) = 3
          then Cast(t.order_id as char(3)) || rpad(' ',61)
         When length(t.order_id) = 2
          then Cast(t.order_id as char(2)) || rpad(' ',62)
         else  Cast(t.order_id as char(1)) || rpad(' ',63)
        End ||
        '0400' as PickData
from MM_ORDER_DETAIL_T a
join MM_CHECKIN_DETAIL_T b
    on b.order_detail_id = a.order_detail_id
join MM_STOCK_BALANCE_T c
    on c.stock_id = b.stock_id
   and c.bin_id = b.bin_id
join MM_BIN_T d
    on d.bin_id = c.bin_id
join MM_ZONE_T e
    on e.zone_id = d.zone_id
   and e.zone_cd in ('01', '02', '03', '04')
join MM_STOCK_T f
    on f.stock_id = b.stock_id
join MM_ORDER_DOC_T t
    on t.fdoc_nbr = a.order_doc_nbr
where a.ORDER_DETAIL_STATUS_CD in ('R', 'S')

union

select '105' ||
        rpad(' ',64) ||
        Case
         When length(w.fdoc_nbr) = 14
          then Cast(w.fdoc_nbr as char(14)) || rpad(' ',50)
         When length(w.fdoc_nbr) = 13
          then Cast(w.fdoc_nbr as char(13)) || rpad(' ',51)
         When length(w.fdoc_nbr) = 12
          then Cast(w.fdoc_nbr as char(12)) || rpad(' ',52)
         When length(w.fdoc_nbr) = 11
          then Cast(w.fdoc_nbr as char(11)) || rpad(' ',53)
         When length(w.fdoc_nbr) = 10
          then Cast(w.fdoc_nbr as char(10)) || rpad(' ',54)
         When length(w.fdoc_nbr) = 9
          then Cast(w.fdoc_nbr as char(9)) || rpad(' ',55)
         When length(w.fdoc_nbr) = 8
          then Cast(w.fdoc_nbr as char(8)) || rpad(' ',56)
         When length(w.fdoc_nbr) = 7
          then Cast(w.fdoc_nbr as char(7)) || rpad(' ',57)
         When length(w.fdoc_nbr) = 6
          then Cast(w.fdoc_nbr as char(6)) || rpad(' ',58)
         When length(w.fdoc_nbr) = 5
          then Cast(w.fdoc_nbr as char(5)) || rpad(' ',59)
         When length(w.fdoc_nbr) = 4
          then Cast(w.fdoc_nbr as char(4)) || rpad(' ',60)
         When length(w.fdoc_nbr) = 3
          then Cast(w.fdoc_nbr as char(3)) || rpad(' ',61)
         When length(w.fdoc_nbr) = 2
          then Cast(w.fdoc_nbr as char(2)) || rpad(' ',62)
         else  Cast(w.fdoc_nbr as char(1)) || rpad(' ',63)
        End ||
        rpad(' ',6) ||
        '01' ||
        substr(e.zone_cd,2,1) ||
        '0' ||
        substr(d.bin_nbr,1,2) ||
        d.shelf_id ||
        d.shelf_id_nbr ||
        Case
         When length(a.before_item_qty) = 6
          then Cast(a.before_item_qty as char(6))
         When length(a.before_item_qty) = 5
          then Cast(a.before_item_qty as char(5)) || rpad(' ',1)
         When length(a.before_item_qty) = 4
          then Cast(a.before_item_qty as char(4)) || rpad(' ',2)
         When length(a.before_item_qty) = 3
          then Cast(a.before_item_qty as char(3)) || rpad(' ',3)
         When length(a.before_item_qty) = 2
          then Cast(a.before_item_qty as char(2)) || rpad(' ',4)
         else Cast(a.before_item_qty as char(1)) || rpad(' ',5)
        End ||
        f.stock_distributor_nbr ||
        rpad(' ',26) ||
        substr(f.stock_desc,1,192) ||
        a.before_item_unit_of_issue_cd ||
        rpad(' ',54)        as PickData
from MM_WORKSHEET_COUNT_DOC_T w
join MM_STOCK_COUNT_T a
    on a.worksheet_count_doc_nbr = w.fdoc_nbr
join MM_STOCK_BALANCE_T c
    on c.stock_id = a.stock_id
   and c.bin_id = a.bin_id
join MM_BIN_T d
    on d.bin_id = c.bin_id
join MM_ZONE_T e
    on e.zone_id = d.zone_id
   and e.zone_cd in ('01', '02', '03', '04')
join MM_STOCK_T f
    on f.stock_id = a.stock_id
where w.worksheet_status_cd = 'PRTD'

union

select distinct '106' ||
        rpad(' ',64) ||
        Case
         When length(w.fdoc_nbr) = 14
          then Cast(w.fdoc_nbr as char(14)) || rpad(' ',50)
         When length(w.fdoc_nbr) = 13
          then Cast(w.fdoc_nbr as char(13)) || rpad(' ',51)
         When length(w.fdoc_nbr) = 12
          then Cast(w.fdoc_nbr as char(12)) || rpad(' ',52)
         When length(w.fdoc_nbr) = 11
          then Cast(w.fdoc_nbr as char(11)) || rpad(' ',53)
         When length(w.fdoc_nbr) = 10
          then Cast(w.fdoc_nbr as char(10)) || rpad(' ',54)
         When length(w.fdoc_nbr) = 9
          then Cast(w.fdoc_nbr as char(9)) || rpad(' ',55)
         When length(w.fdoc_nbr) = 8
          then Cast(w.fdoc_nbr as char(8)) || rpad(' ',56)
         When length(w.fdoc_nbr) = 7
          then Cast(w.fdoc_nbr as char(7)) || rpad(' ',57)
         When length(w.fdoc_nbr) = 6
          then Cast(w.fdoc_nbr as char(6)) || rpad(' ',58)
         When length(w.fdoc_nbr) = 5
          then Cast(w.fdoc_nbr as char(5)) || rpad(' ',59)
         When length(w.fdoc_nbr) = 4
          then Cast(w.fdoc_nbr as char(4)) || rpad(' ',60)
         When length(w.fdoc_nbr) = 3
          then Cast(w.fdoc_nbr as char(3)) || rpad(' ',61)
         When length(w.fdoc_nbr) = 2
          then Cast(w.fdoc_nbr as char(2)) || rpad(' ',62)
         else  Cast(w.fdoc_nbr as char(1)) || rpad(' ',63)
        End ||
        '0500' as PickData
from MM_WORKSHEET_COUNT_DOC_T w
join MM_STOCK_COUNT_T a
    on a.worksheet_count_doc_nbr = w.fdoc_nbr
join MM_STOCK_BALANCE_T c
    on c.stock_id = a.stock_id
   and c.bin_id = a.bin_id
join MM_BIN_T d
    on d.bin_id = c.bin_id
join MM_ZONE_T e
    on e.zone_id = d.zone_id
   and e.zone_cd in ('01', '02', '03', '04')
join MM_STOCK_T f
    on f.stock_id = a.stock_id
where w.worksheet_status_cd = 'PRTD'
;

