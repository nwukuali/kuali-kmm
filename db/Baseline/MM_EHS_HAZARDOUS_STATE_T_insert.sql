insert into mm_ehs_hazardous_state_t (ehs_hazardous_state_cd, obj_id, ver_nbr, nm, actv_ind, last_update_dt)
values('L',sys_guid(),1,'LIQUID','Y',CURRENT_DATE)
;
insert into mm_ehs_hazardous_state_t (ehs_hazardous_state_cd, obj_id, ver_nbr, nm, actv_ind, last_update_dt)
values('S',sys_guid(),1,'SOLID','Y',CURRENT_DATE)
;
insert into mm_ehs_hazardous_state_t (ehs_hazardous_state_cd, obj_id, ver_nbr, nm, actv_ind, last_update_dt)
values('G',sys_guid(),1,'GAS','Y',CURRENT_DATE)
;