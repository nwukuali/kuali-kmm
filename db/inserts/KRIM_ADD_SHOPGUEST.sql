insert into krim_entity_t t (entity_id, obj_id, Ver_nbr, Actv_ind, last_updt_dt) 
values (Krim_Entity_Id_s.Nextval, sys_guid(), 1, 'Y', sysdate);
select Krim_Entity_Id_s.Currval from dual;  --> 5020
select * from  krim_entity_t t;

insert into Krim_Prncpl_t (Prncpl_Id, Obj_Id,Ver_Nbr,Prncpl_Nm,Entity_Id,Actv_Ind,Last_Updt_Dt) values
(krim_prncpl_id_s.nextval, sys_guid(), 1, 'shopguest', Krim_Entity_Id_s.Currval, 'Y', sysdate);
select * from Krim_Prncpl_t t;

insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD,ENTITY_ID, OBJ_ID,VER_NBR,ACTV_IND,LAST_UPDT_DT) VAlues
('PERSON',Krim_Entity_Id_s.Currval, sys_guid(), 1, 'Y', Sysdate);

insert into MM_CUSTOMER_T (PRNCPL_ID, OBJ_ID, VER_NBR, NM, ACTV_IND, LAST_UPDATE_DT) values
('SHOPGUEST', sys_guid(), 1, 'Shopping guest', 'Y', sysdate);

select * from KRIM_ROLE_MBR_T m where m.mbr_id=(select p.prncpl_id from Krim_Prncpl_t p where p.prncpl_nm='schnepp');
select * from KRIM_ROLE_PERM_T t where t.role_id='1300';
select * from KRIM_PERM_T t where t.perm_id in (1000, 805);
select * from KRIM_ROLE_T t where t.role_id in (1300, 63);
