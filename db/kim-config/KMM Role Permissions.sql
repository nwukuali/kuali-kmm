--SSMD
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Store Record(s)'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores MDoc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores Mdoc'), 'Y');

--SSTD
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores TDoc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores Tdoc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Save Document for Stores Tdoc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Delete Stores Tdoc Notes'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Administer Routing for Stores Tdoc'), 'Y');

--SAMU
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SAMU'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SAMU'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SAMU'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SAMU'), 'Y');

--SRET
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KFS-SYS'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Customer Return'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KFS-SYS'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Save Customer Return'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KFS-SYS'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Customer Returns Document'), 'Y');

--SSTK
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SSTK Doc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Stores SSTK Record(s)'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SSTK'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SSTK Doc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Stores SSTK Record(s)'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SSTK'), 'Y');

insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (select r.role_id from KRIM_ROLE_T r where r.role_nm='Warehouse Manager' and r.nmspc_cd='KFS-MM'), (select p.perm_id from KRIM_PERM_T p where p.nmspc_cd='KFS-MM' and p.nm='Adjust Zero Bin Balance Stock'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SSTK Doc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Stores SSTK Record(s)'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SSTK'), 'Y');

--SCIT
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SCIT Doc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Stores SCIT Record(s)'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SCIT'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SCIT Doc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Stores SCIT Record(s)'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SCIT'), 'Y');

--Worksheet Action
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Edit Worksheet'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='CountWorksheetPrintAction'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SWKC'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SWKC'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Save Document for Stores SWKC'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Route Document for Stores SWKC'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Delete Stores SWKC Notes'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Administer Routing for Stores SWKC'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SWKC'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SWKC'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Save Document for Stores SWKC'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Delete Stores SWKC Notes'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Route Document for Stores SWKC'), 'Y');

--STBO
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KUALI'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate TrueBuyout'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open TrueBuyout Document'), 'Y');

--SORD
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KUALI'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Order'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KUALI'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Order Document'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='User' and nmspc_cd='KUALI'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Save Order'), 'Y');

--SPKL & SPKV (We made SPKV a child of SPKL)
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SPKL'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SPKL'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Save Document for Stores SPKL'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Delete Stores SPKL Notes'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Administer Routing for Stores SPKL'), 'Y');

--SBCK  (Backorder Maintenance)
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SBCK Doc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Stores SBCK Record(s)'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SBCK'), 'Y');

--Stock History Lookup Permissions
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Stock History Records'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Warehouse Manager' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Stock History Records'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Stock History Records'), 'Y');

--PickTicketAction
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='PickTicketAction'), 'Y');

-- Personal Order Permission
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Role' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Personal Orders'), 'Y');

--Check In
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Order Detail Records'), 'Y');

--Reorder
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up ReorderItem Records'), 'Y');

--Vendor Return
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up VendorOrderDetailForReturns Records'), 'Y');

--Receipt Correction
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up OrderDetailForReceipts Records'), 'Y');

--Worksheet Lookup
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up WorksheetCountDocumentLookable Records'), 'Y');
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up WorksheetCountDocumentLookable Records'), 'Y');

--Pick tickets
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Worker' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up PickTicket Records'), 'Y');

-- Catalog Lookup
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Role' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Catalog Records'), 'Y');

--Catalog Lookup
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Storehouse Role' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up CatalogItem Records'), 'Y');

--Upload catalog View Reports permission
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='View CatalogUpload Reports'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='View CatalogUpload Reports'), 'Y');

-- Final approval of catalog upload
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Contract Administrator' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Final Approve Catalogs'), 'Y');

--Personal Use Shopping
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
VALUES(KRIM_ROLE_PERM_ID_S.nextval, SYS_GUID(), 1, (select r.role_id from KRIM_ROLE_T r where r.role_nm='Storehouse Worker' and r.nmspc_cd='KFS-MM'), (select p.perm_id from KRIM_PERM_T p where p.nm='Personal Use Shopping' and p.nmspc_cd='KFS-MM'), 'Y');

-------------------------------------------
------Added after Sep-29-2010--------------
-------------------------------------------
--Order Correction
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='OrderCorrectionAction'), 'Y');

--CheckIn Receivable
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up CheckIn Receivable'), 'Y');

--CheckInCorrection
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up CheckIn Correction'), 'Y');

--Batch Control
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='BatchControlAction'), 'Y');

--Catalog Image Management
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='CatalogImageAction'), 'Y');

--Unassign Bin
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='UnassignBinAction'), 'Y');

--SSFP Shopping Front Page
insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SSFP Doc'), 'Y');

insert into krim_role_perm_t (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
values (krim_role_perm_id_s.nextval, sys_guid(), 1, (select role_id from krim_role_t where role_nm='Commodity Specialist' and nmspc_cd='KFS-MM'), (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Stores SSFP Record(s)'), 'Y');
