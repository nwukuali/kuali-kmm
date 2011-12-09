-- Changes beginning after   05/04/2010

-- add Order Status records 05/5/2010
insert into MM_ORDER_STATUS_T (ORDER_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
       VALUES ('I', SYS_GUID(), 1, 'INITIATED', 'Y', sysdate);
insert into MM_ORDER_STATUS_T (ORDER_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
       VALUES ('V', SYS_GUID(), 1, 'REVIEW', 'Y', sysdate);
insert into MM_ORDER_STATUS_T (ORDER_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
       VALUES ('D', SYS_GUID(), 1, 'DISAPPROVED', 'Y', sysdate);
       
-- add fields for receipt correction 05/21/2010

alter table MM_CHECKIN_DOC_T 
   add FINAL_IND    VARCHAR2(1);

alter table MM_CREDIT_MEMO_EXPECTED_T 
   add CHECKIN_DETAIL_ID    NUMBER(18);
   
alter table MM_CREDIT_MEMO_EXPECTED_T 
  add constraint MM_CREDIT_MEMO_EXPECTED_TR1 foreign key (CHECKIN_DETAIL_ID)
  references MM_CHECKIN_DETAIL_T(CHECKIN_DETAIL_ID);

-- add fields for returns 06/08/2010

alter table MM_RETURN_STATUS_CODE_T 
   add CUSTOMER_VENDOR_RETURN_IND    VARCHAR2(1);
       
update MM_RETURN_STATUS_CODE_T
  set CUSTOMER_VENDOR_RETURN_IND = 'V';

INSERT INTO MM_RETURN_STATUS_CODE_T (RETURN_STATUS_CD, OBJ_ID, VER_NBR, NM, CUSTOMER_VENDOR_RETURN_IND, ACTV_IND, LAST_UPDATE_DT)
VALUES('31',SYS_GUID(),1,'WRONG ITEM','C','Y',CURRENT_DATE);
INSERT INTO MM_RETURN_STATUS_CODE_T (RETURN_STATUS_CD, OBJ_ID, VER_NBR, NM, CUSTOMER_VENDOR_RETURN_IND, ACTV_IND, LAST_UPDATE_DT)
VALUES('32',SYS_GUID(),1,'DAMAGED','C','Y',CURRENT_DATE);
INSERT INTO MM_RETURN_STATUS_CODE_T (RETURN_STATUS_CD, OBJ_ID, VER_NBR, NM, CUSTOMER_VENDOR_RETURN_IND, ACTV_IND, LAST_UPDATE_DT)
VALUES('33',SYS_GUID(),1,'DON''T NEED','C','Y',CURRENT_DATE);

-- corrected views for returns 06/09/2010
       
create or replace view mm_customer_return_lines_v as
select  odt.order_detail_id, 
        odt.order_detail_status_cd order_detail_status_cd, 
        oct.order_type_cd order_type_cd,
        oct.warehouse_cd warehouse_cd, 
        mct.catalog_item_id catalog_item_id,
        mct.distributor_nbr distributor_nbr, 
        odt.vendor_nm vendor_nm, 
        oct.ar_id ar_id, 
        oct.reqs_id reqs_id, 
        oct.fdoc_nbr fdoc_nbr, 
        odt.po_id po_id, 
        oct.order_id order_id,
        odt.order_item_qty - (select nvl(sum(rdt.return_qty),0) from mm_return_detail_t rdt where rdt.order_detail_id = odt.order_detail_id) balance_qty 
from mm_order_doc_t oct
join mm_order_detail_t odt
   on odt.order_doc_nbr = oct.fdoc_nbr 
left join mm_catalog_item_t mct 
   on mct.catalog_item_id = odt.catalog_item_id 
left join mm_pick_list_line_t mpt
   on mpt.order_detail_id = odt.order_detail_id
  and mpt.pick_status_cd in ('PBCK','PCKD')
where odt.order_item_qty > (select nvl(sum(mdt.return_qty),0)
                            from mm_return_detail_t mdt 
                            where mdt.order_detail_id = odt.order_detail_id)
  and oct.order_type_cd in ('WAREHS', 'HOSTED')
;

create or replace view mm_vendor_return_lines_v as
select  odt.order_detail_id, 
        odt.order_detail_status_cd order_detail_status_cd, 
        oct.order_type_cd order_type_cd,
        oct.warehouse_cd warehouse_cd, 
        mct.catalog_item_id catalog_item_id,
        mct.distributor_nbr distributor_nbr, 
        odt.vendor_nm vendor_nm, 
        oct.ar_id ar_id, 
        oct.reqs_id reqs_id, 
        oct.fdoc_nbr fdoc_nbr, 
        odt.po_id po_id, 
        oct.order_id order_id,
        odt.order_item_qty - (select nvl(sum(rdt.return_qty),0) from mm_return_detail_t rdt where rdt.order_detail_id = odt.order_detail_id) balance_qty 
from mm_order_doc_t oct
join mm_order_detail_t odt
   on odt.order_doc_nbr = oct.fdoc_nbr
left join mm_catalog_item_t mct 
   on mct.catalog_item_id = odt.catalog_item_id 
where odt.order_item_qty > (select nvl(sum(mdt.return_qty),0)
                            from mm_return_detail_t mdt 
                            where mdt.order_detail_id = odt.order_detail_id)
  and oct.order_type_cd in ('STOCK')
  and nvl(odt.po_id,0) > 0;
       
-- update mm_agreement_t 06/16/2010

alter table MM_AGREEMENT_T 
   add VNDR_CONTR_NM    VARCHAR2(20);
       
alter table MM_AGREEMENT_T 
   modify AGREEMENT_NBR    VARCHAR2(50);

-- update mm_agreement_t 06/24/2010

alter table MM_AGREEMENT_T 
   add NOPRINT_IND    VARCHAR2(1);
   
update MM_AGREEMENT_T
  set NOPRINT_IND = 'N';
  
--  update mm_return_detail_t for save  06/25/2010

alter table MM_RETURN_DETAIL_T
  modify RETURN_DETAIL_STATUS_CD null;

-- update mm_recurring_order_t to add next recurring date 07/1/2010

  alter table MM_RECURRING_ORDER_T
      add NEXT_RECURRING_DT DATE;

-- new system parameter for disabling/enabling personal use profiles in shopping  07/14/2010
-- also added to KRNS_SYSTEM_PARAMETERS.sql
insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD ) 
    values('KFS-MM', 'ShoppingCart', 'ALLOW_PERSONAL_USE', SYS_GUID(), 1, 'CONFG', 'Y', 'Determines whether personal use profiles/external users are allowed in the Shopping portal', 'A', 'KFS');

-- update to add new Pick Status Code ASND - Pick Line Assigned 07/27/2010
insert into MM_PICK_STATUS_CODE_T (PICK_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
       values ('ASND', SYS_GUID(), '1', 'PICK LINE ASSIGNED', 'Y', sysdate);
       
-- update to add two new Markup Type Codes	8/12/2010
delete from MM_MARKUP_TYPE_T t where t.markup_type_cd='03';
insert into MM_MARKUP_TYPE_T (MARKUP_TYPE_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
values ('03', SYS_GUID(), 1, 'ACCOUNT + CATALOG ITEM MARKUP', 'Y', sysdate);
insert into MM_MARKUP_TYPE_T (MARKUP_TYPE_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
values ('09', SYS_GUID(), 1, 'ITEM CATEGORY + CATALOG ITEM MARKUP', 'Y', sysdate);

-- change STOCK_BALANCE_T primary key 9/9/2010
alter table MM_STOCK_BALANCE_T
      drop constraint MM_STOCK_BALANCE_TP1;      
alter table MM_STOCK_BALANCE_T
      add constraint MM_STOCK_BALANCE_TP1 primary key (BIN_ID);
      
-- change CHARGE_DOC_ITM_NBR  9/9/2010
alter table MM_PICK_LIST_LINE_T
   add  CHARGE_DOC_ITM_NBR        NUMBER(7);
      
-- Back Order change 9/15/2010
alter table MM_BACK_ORDER_T
      add FILLED_IND VARCHAR2(1) not null;
      
alter table MM_BACK_ORDER_T
      add STOCK_ID NUMBER(18) not null;

alter table MM_BACK_ORDER_T
      drop constraint MM_BACK_ORDER_TR2;     

alter table MM_BACK_ORDER_T
      add constraint MM_BACK_ORDER_TR2 foreign key (STOCK_ID)
      references MM_STOCK_T (STOCK_ID);
      
alter table MM_BACK_ORDER_T
      drop column NEW_PICK_LIST_LINE_ID;      

-- Buy Unit of Issue  and Stock Balance change 9/21/2010

alter table MM_STOCK_T
  drop constraint MM_STOCK_TR4;

alter table MM_STOCK_BALANCE_T
      drop constraint MM_STOCK_BALANCE_TP1;      
alter table MM_STOCK_BALANCE_T
      add constraint MM_STOCK_BALANCE_TP1 primary key (BIN_ID);


