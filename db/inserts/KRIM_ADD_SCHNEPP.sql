insert into krim_entity_t t (entity_id, obj_id, Ver_nbr, Actv_ind, last_updt_dt) 
values (Krim_Entity_Id_s.Nextval, sys_guid(), 1, 'Y', sysdate);
select Krim_Entity_Id_s.Currval from dual;  --> 5000
select * from  krim_entity_t t;

insert into Krim_Prncpl_t (Prncpl_Id, Obj_Id,Ver_Nbr,Prncpl_Nm,Entity_Id,Actv_Ind,Last_Updt_Dt) values
(krim_prncpl_id_s.nextval, sys_guid(), 1, 'schnepp', Krim_Entity_Id_s.Currval, 'Y', sysdate);
select * from Krim_Prncpl_t t;

insert into KRIM_ENTITY_ENT_TYP_T (ENT_TYP_CD,ENTITY_ID, OBJ_ID,VER_NBR,ACTV_IND,LAST_UPDT_DT) VAlues
('PERSON',Krim_Entity_Id_s.Currval, sys_guid(), 1, 'Y', Sysdate);

insert into Krim_Role_Mbr_t (Role_Mbr_Id, Ver_Nbr, Obj_Id, Role_Id, Mbr_Id, Mbr_Typ_Cd, Last_Updt_Dt) values
(krim_role_mbr_id_s.nextval, 1, sys_guid(),'1300', krim_prncpl_id_s.currval, 'P', sysdate);

select * from KRIM_ROLE_MBR_T m where m.mbr_id = (select p.prncpl_id from KRIM_PRNCPL_T p where p.prncpl_nm='schnepp');
select p.prncpl_id from KRIM_PRNCPL_T p where p.prncpl_nm='schnepp';
