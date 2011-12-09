insert into mm_order_status_t (order_status_cd, obj_id, ver_nbr, nm, actv_ind, last_update_dt)
values ('O', sys_guid(),1,'OPEN','Y',CURRENT_DATE)
;
insert into mm_order_status_t (order_status_cd, obj_id, ver_nbr, nm, actv_ind, last_update_dt)
values ('C', sys_guid(),1,'CLOSED','Y',CURRENT_DATE)
;
insert into mm_order_status_t (order_status_cd, obj_id, ver_nbr, nm, actv_ind, last_update_dt)
values ('X', sys_guid(),1,'CANCELED','Y',CURRENT_DATE)
;
insert into mm_order_status_t (order_status_cd, obj_id, ver_nbr, nm, actv_ind, last_update_dt)
values ('P', sys_guid(),1,'PRINTED','Y',CURRENT_DATE)
;
insert into mm_order_status_t (order_status_cd, obj_id, ver_nbr, nm, actv_ind, last_update_dt)
values ('R', sys_guid(),1,'RECEIVING','Y',CURRENT_DATE)
;
insert into mm_order_status_t (order_status_cd, obj_id, ver_nbr, nm, actv_ind, last_update_dt)
values ('S', sys_guid(),1,'RECEIVED COMPLETE','Y',CURRENT_DATE)
;
insert into mm_order_status_t (order_status_cd, obj_id, ver_nbr, nm, actv_ind, last_update_dt)
values ('T', sys_guid(),1,'COMPLETE','Y',CURRENT_DATE)
;
insert into MM_ORDER_STATUS_T (ORDER_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
values ('I', SYS_GUID(), 1, 'INITIATED', 'Y', CURRENT_DATE)
;
insert into MM_ORDER_STATUS_T (ORDER_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
values ('V', SYS_GUID(), 1, 'REVIEW', 'Y', CURRENT_DATE)
;
insert into MM_ORDER_STATUS_T (ORDER_STATUS_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT)
values ('D', SYS_GUID(), 1, 'DISAPPROVED', 'Y', CURRENT_DATE)
;
