--Run this to see how many good lines are left for pick list process
select * from MM_PICK_LIST_LINE_T t where t.pick_status_cd = 'INIT';

--run the rest of these to reset pick list lines if we run out
update MM_PICK_LIST_LINE_T SET VER_NBR = '1', PICK_TICKET_NBR = '', PICK_LIST_DOC_NBR = '', PICK_STATUS_CD = 'INIT', PICK_TUB_NBR='', PICK_STOCK_QTY='', BACK_ORDER_QTY='', BACK_ORDER_ID='';

truncate table MM_PICK_VERIFY_DOC_T;
truncate table MM_PICK_LIST_DOC_T;
truncate table MM_PICK_TICKET_T;

commit;
