insert into mm_pick_list_doc_t (FDOC_NBR, OBJ_ID, VER_NBR, WAREHOUSE_CD, SORT_CD, MAX_ORDERS_QTY, LAST_UPDATE_DT) 
 VALUES (MM_SYS_GUID_S.nextval, SYS_GUID(), '1', '74', NULL, NULL, CURRENT_DATE);

Commit;

insert into mm_pick_ticket_t (PICK_TICKET_NBR, OBJ_ID, VER_NBR, NM, PICK_LIST_DOC_NBR, PICK_STATUS_CD, FILE_NM, LAST_UPDATE_DT)
 VALUES (MM_PICK_TICKET_S.nextval, SYS_GUID(), '1', 'A1500', MM_SYS_GUID_S.currval, 'PCKD', 'A1500', CURRENT_DATE);
  
Commit;

INSERT into MM_PICK_LIST_LINE_T
 select MM_PICK_LIST_LINE_S.nextval, 
       SYS_GUID(), 
       '1',
       MM_SYS_GUID_S.currval,
       MM_PICK_TICKET_S.currval,
       1,
       aa.SALES_INSTANCE_ID,
       aa.ORDER_DETAIL_ID,
       (select MM_STOCK_BALANCE_T.BIN_ID 
        from MM_CATALOG_ITEM_T join MM_STOCK_BALANCE_T
           on MM_STOCK_BALANCE_T.STOCK_ID = MM_CATALOG_ITEM_T.STOCK_ID
        where MM_CATALOG_ITEM_T.CATALOG_ITEM_ID = aa.CATALOG_ITEM_ID
          and MM_CATALOG_ITEM_T.STOCK_ID > 0),
       (select MM_CATALOG_ITEM_T.STOCK_ID 
        from MM_CATALOG_ITEM_T 
        where MM_CATALOG_ITEM_T.CATALOG_ITEM_ID = aa.CATALOG_ITEM_ID
          and MM_CATALOG_ITEM_T.STOCK_ID > 0),
       aa.ORDER_ITEM_QTY,
       aa.ORDER_ITEM_QTY,
       0,
       NULL,
       'PCKD',
       'WH',
       NULL,
       CURRENT_DATE,
       CURRENT_DATE
 from MM_ORDER_DETAIL_T aa
 join MM_ORDER_DOC_T bb
   on bb.FDOC_NBR = aa.ORDER_DOC_NBR
  and bb.ORDER_TYPE_CD = 'WAREHS'
;
Commit;

insert into mm_pick_verify_doc_t (FDOC_NBR, OBJ_ID, VER_NBR, PICK_TICKET_NBR, LAST_UPDATE_DT)
 VALUES (MM_SYS_GUID_S.nextval, SYS_GUID(), '1', MM_PICK_TICKET_S.currval, CURRENT_DATE);
 
Commit;

update mm_cylinder_t z
  set z.pick_list_line_id = (select p.pick_list_line_id
                             from mm_cylinder_t c 
                             left join mm_order_detail_t t
                                on substr(t.order_item_detail_desc, (instr(t.order_item_detail_desc,' - ') + 3), 5) = c.cylinder_serial_nbr
                             left join mm_pick_list_line_t p
                                on p.order_detail_id = t.order_detail_id
                              where c.cylinder_id = z.cylinder_id)
;

Commit;

update MM_STOCK_T
  set CYLINDER_GAS_CD = '20'
where STOCK_DISTRIBUTOR_NBR = '16092000';
commit;
update MM_STOCK_T
  set CYLINDER_GAS_CD = '20'
where STOCK_DISTRIBUTOR_NBR = '16097580';
commit;
update MM_STOCK_T
  set CYLINDER_GAS_CD = '22'
where STOCK_DISTRIBUTOR_NBR = '16098980';
commit;
update MM_STOCK_T
  set CYLINDER_GAS_CD = '22'
where STOCK_DISTRIBUTOR_NBR = '16062560';
commit;
update MM_STOCK_T
  set CYLINDER_GAS_CD = '09'
where STOCK_DISTRIBUTOR_NBR = '16055200';
commit;

update MM_ORDER_DETAIL_T o
  set DISTRIBUTOR_NBR = (select c.DISTRIBUTOR_NBR from MM_CATALOG_ITEM_T c
                          where c.catalog_item_id = o.CATALOG_ITEM_ID);
