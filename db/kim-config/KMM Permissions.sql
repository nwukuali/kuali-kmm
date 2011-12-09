--Permissions BEGIN
--SSMD
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Stores MDoc', 'Permission to initiate all maintenance documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores MDoc'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSMD');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Create / Maintain Record(s)' and nmspc_cd='KR-NS'), 'KFS-MM', 'Create / Maintain Store Record(s)', 'Permission to Create / Maintain Record(s) in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Store Record(s)'), (select kim_typ_id from krim_typ_t where nm='Document Type & Existing Records Only' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSMD');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Document for Stores Mdoc', 'Permission to maintenance documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores Mdoc'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSMD');

--SSTD
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Stores TDoc', 'Permission to initiate stores transactional documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores TDoc'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSTD');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Save Document' and nmspc_cd='KR-WKFLW'), 'KFS-MM', 'Save Document for Stores Tdoc', 'Permission to save transactional documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Save Document for Stores Tdoc'), (select kim_typ_id from krim_typ_t where nm='Document Type & Routing Node or State' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSTD');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Document for Stores Tdoc', 'Permission to open transactional documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores Tdoc'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSTD');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Delete Note / Attachment' and nmspc_cd='KR-NS'), 'KFS-MM', 'Delete Stores Tdoc Notes', 'Permission to delete transactional doc notes in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Delete Stores Tdoc Notes'), (select kim_typ_id from krim_typ_t where nm='Document Type & Relationship to Note Author' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSTD');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Administer Routing for Document' and nmspc_cd='KR-WKFLW'), 'KFS-MM', 'Administer Routing for Stores Tdoc', 'Permission to Administer Routing for transactional documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Administer Routing for Stores Tdoc'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSTD');

--SAMU
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Stores SAMU', 'Permission to initiate stores Agreement Mass Update documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SAMU'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SAMU');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Document for Stores SAMU', 'Permission to Open Document for Stores Agreement Mass Update documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SAMU'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SAMU');

--SRET
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Customer Return', 'Permission to initiate customer return documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Customer Return'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SRET');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Customer Returns Document', 'Permission to Open Customer Returns Documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Customer Returns Document'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SRET');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Save Document' and nmspc_cd='KR-WKFLW'), 'KFS-MM', 'Save Customer Return', 'Permission to Save Customer Returns documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Save Customer Return'), (select kim_typ_id from krim_typ_t where nm='Document Type & Routing Node or State' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SRET');

--SSTK
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Stores SSTK Doc', 'Permission to initiate Stores Adjust Stock documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SSTK Doc'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSTK');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Create / Maintain Record(s)' and nmspc_cd='KR-NS'), 'KFS-MM', 'Create / Maintain Stores SSTK Record(s)', 'Permission to Create / Maintain Stores SSTK Record(s) in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Stores SSTK Record(s)'), (select kim_typ_id from krim_typ_t where nm='Document Type & Existing Records Only' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSTK');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Document for Stores SSTK', 'Permission to Open Document for Stores Stock Adjustment in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SSTK'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSTK');

insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Default' and nmspc_cd='KUALI'), 'KFS-MM', 'Adjust Zero Bin Balance Stock', 'Allows balance adjustments to stock with a bin balance of 0.', 'Y');

--SCIT
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Stores SCIT Doc', 'Permission to initiate Catalog Items in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SCIT Doc'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SCIT');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Create / Maintain Record(s)' and nmspc_cd='KR-NS'), 'KFS-MM', 'Create / Maintain Stores SCIT Record(s)', 'Permission to Create / Maintain Stores SCIT Record(s) in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Stores SCIT Record(s)'), (select kim_typ_id from krim_typ_t where nm='Document Type & Existing Records Only' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SCIT');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Document for Stores SCIT', 'Permission to Open Catalog Item Docs in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SCIT'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SCIT');

--Worksheet Action
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Default' and nmspc_cd='KUALI'), 'KFS-MM', 'Edit Worksheet', 'Authorizes the Editing of the  Worksheet Document.', 'Y');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Use Screen' and nmspc_cd='KR-NS'), 'KFS-MM', 'CountWorksheetPrintAction', 'Permission to print Worksheets in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='CountWorksheetPrintAction'), (select kim_typ_id from krim_typ_t where nm='Namespace or Action' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='actionClass' and nmspc_cd='KR-SYS'), 'org.kuali.ext.mm.document.web.struts.CountWorksheetPrintAction');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Stores SWKC', 'Permission to initiate worksheet documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SWKC'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SWKC');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Save Document' and nmspc_cd='KR-WKFLW'), 'KFS-MM', 'Save Document for Stores SWKC', 'Permission to Save Worksheet count documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Save Document for Stores SWKC'), (select kim_typ_id from krim_typ_t where nm='Document Type & Routing Node or State' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SWKC');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Route Document' and nmspc_cd='KR-WKFLW'), 'KFS-MM', 'Route Document for Stores SWKC', 'Permission to Route Worksheet count documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Route Document for Stores SWKC'), (select kim_typ_id from krim_typ_t where nm='Document Type & Routing Node or State' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SWKC');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Document for Stores SWKC', 'Permission to Open Worksheet count documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SWKC'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SWKC');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Delete Note / Attachment' and nmspc_cd='KR-NS'), 'KFS-MM', 'Delete Stores SWKC Notes', 'Permission to delete notes Worksheet count in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Delete Stores SWKC Notes'), (select kim_typ_id from krim_typ_t where nm='Document Type & Relationship to Note Author' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SWKC');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Administer Routing for Document' and nmspc_cd='KR-WKFLW'), 'KFS-MM', 'Administer Routing for Stores SWKC', 'Permission to Administer Routing for Worksheet count documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Administer Routing for Stores SWKC'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SWKC');

--STBO
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate TrueBuyout', 'Permission to Initiate Truebuyout Documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate TrueBuyout'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'STBO');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open TrueBuyout Document', 'Permission to Open TrueBuyout Documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open TrueBuyout Document'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'STBO');

--SORD
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Order', 'Permission to initiate order documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Order'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SORD');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Order Document', 'Permission to Open Order Documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Order Document'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SORD');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Save Document' and nmspc_cd='KR-WKFLW'), 'KFS-MM', 'Save Order', 'Permission to Save Orders in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Save Order'), (select kim_typ_id from krim_typ_t where nm='Document Type & Routing Node or State' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SORD');

--SPKL & SPKV (We made SPKV a child of SPKL)
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Stores SPKL', 'Permission to initiate stores Pick List documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SPKL'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SPKL');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Save Document' and nmspc_cd='KR-WKFLW'), 'KFS-MM', 'Save Document for Stores SPKL', 'Permission to Save Document for Stores Pick List documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Save Document for Stores SPKL'), (select kim_typ_id from krim_typ_t where nm='Document Type & Routing Node or State' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SPKL');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Document for Stores SPKL', 'Permission to Open Document for Stores Pick List documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SPKL'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SPKL');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Delete Note / Attachment' and nmspc_cd='KR-NS'), 'KFS-MM', 'Delete Stores SPKL Notes', 'Permission to delete Stores Pick List documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Delete Stores SPKL Notes'), (select kim_typ_id from krim_typ_t where nm='Document Type & Relationship to Note Author' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SPKL');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Administer Routing for Document' and nmspc_cd='KR-WKFLW'), 'KFS-MM', 'Administer Routing for Stores SPKL', 'Permission to Administer Routing for Stores Pick List documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Administer Routing for Stores SPKL'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SPKL');

--SBCK  (Backorder Maintenance)
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Stores SBCK Doc', 'Permission to initiate Stores Backorder maintenance documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SBCK Doc'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SBCK');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Create / Maintain Record(s)' and nmspc_cd='KR-NS'), 'KFS-MM', 'Create / Maintain Stores SBCK Record(s)', 'Permission to Create / Maintain Stores SBCK Record(s) in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Stores SBCK Record(s)'), (select kim_typ_id from krim_typ_t where nm='Document Type & Existing Records Only' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SBCK');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Open Document' and nmspc_cd='KR-NS'), 'KFS-MM', 'Open Document for Stores SBCK', 'Permission to Open Document for Stores Backorder maintenance in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Open Document for Stores SBCK'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SBCK');

--Stock History lookup restriction
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up Stock History Records', 'Permission to Look Up Stock History Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Stock History Records'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'StockHistory');

--PickTicketAction 
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Use Screen' and nmspc_cd='KR-NS'), 'KFS-MM', 'PickTicketAction', 'Permission to print pick tickets in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='PickTicketAction'), (select kim_typ_id from krim_typ_t where nm='Namespace or Action' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='actionClass' and nmspc_cd='KR-SYS'), 'org.kuali.ext.mm.document.web.struts.PickTicketAction');

--Personal Order View permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Default' and nmspc_cd='KUALI'), 'KFS-MM', 'Open Personal Orders', 'Authorizes to open personal orders in the system.', 'Y');

--Order detail permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up Order Detail Records', 'Permission to Look Up Order Detail Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Order Detail Records'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'OrderDetail');

--Reorder Item permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up ReorderItem Records', 'Permission to Look Up ReorderItem Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up ReorderItem Records'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'ReorderItem');

--VendorOrderDetailForReturns permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up VendorOrderDetailForReturns Records', 'Permission to Look Up VendorOrderDetailForReturns Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up VendorOrderDetailForReturns Records'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'VendorOrderDetailForReturns');

--OrderDetailForReceipts permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up OrderDetailForReceipts Records', 'Permission to Look Up OrderDetailForReceipts Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up OrderDetailForReceipts Records'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'OrderDetailForReceipts');

--WorksheetCountDocumentLookable permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up WorksheetCountDocumentLookable Records', 'Permission to Look Up WorksheetCountDocumentLookable Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up WorksheetCountDocumentLookable Records'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'WorksheetCountDocumentLookable');

--PickTicket permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up PickTicket Records', 'Permission to Look Up PickTicket Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up PickTicket Records'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'PickTicket');

--Catalog permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up Catalog Records', 'Permission to Look Up Catalog Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up Catalog Records'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'Catalog');

--CatalogItem
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up CatalogItem Records', 'Permission to Look Up CatalogItem Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up CatalogItem Records'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'CatalogItem');

--Upload catalog View Reports permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Default' and nmspc_cd='KUALI'), 'KFS-MM', 'View CatalogUpload Reports', 'Authorizes to view the Catalog Upload reports.', 'Y');

--Final approval of uploaded catalogs
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Default' and nmspc_cd='KUALI'), 'KFS-MM', 'Final Approve Catalogs', 'Authorizes to final approval of the uploaded catalog', 'Y');

--Personal Use Shopping
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
       VALUES(KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, 1, 'KFS-MM', 'Personal Use Shopping', 'Allows for personal use profiles for certain users when ALLOW_PERSONAL_USE parm is set to N.', 'Y');

-------------------------------------------
------Added after Sep-29-2010--------------
------------------------------------------- 
--Order Correction Permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Use Screen' and nmspc_cd='KR-NS'), 'KFS-MM', 'OrderCorrectionAction', 'Permission to correct order documents in KFS-MM', 'Y');

insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='OrderCorrectionAction'), (select kim_typ_id from krim_typ_t where nm='Namespace or Action' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='actionClass' and nmspc_cd='KR-SYS'), 'org.kuali.ext.mm.cart.web.struts.OrderCorrectionAction');

--CheckInReceivable permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up CheckIn Receivable', 'Permission to Look Up CheckIn Receivable Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up CheckIn Receivable'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'CheckInReceivable');

--CheckInCorrection permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Look Up Records' and nmspc_cd='KR-NS'), 'KFS-MM', 'Look Up CheckIn Correction', 'Permission to Look Up CheckIn Correction Records in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Look Up CheckIn Correction'), (select kim_typ_id from krim_typ_t where nm='Namespace or Component' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='componentName' and nmspc_cd='KR-NS'), 'CheckInCorrection');

--Batch Control Permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Use Screen' and nmspc_cd='KR-NS'), 'KFS-MM', 'BatchControlAction', 'Permission to run batch jobs KFS-MM', 'Y');

insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='BatchControlAction'), (select kim_typ_id from krim_typ_t where nm='Namespace or Action' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='actionClass' and nmspc_cd='KR-SYS'), 'org.kuali.ext.mm.sys.batch.web.struts.BatchControlAction');

--CatalogImageAction
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Use Screen' and nmspc_cd='KR-NS'), 'KFS-MM', 'CatalogImageAction', 'Permission to manage Catalog Images', 'Y');

insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='CatalogImageAction'), (select kim_typ_id from krim_typ_t where nm='Namespace or Action' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='actionClass' and nmspc_cd='KR-SYS'), 'org.kuali.ext.mm.admin.web.struts.CatalogImageAction');

--Unassign Bin Permission
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Use Screen' and nmspc_cd='KR-NS'), 'KFS-MM', 'UnassignBinAction', 'Permission to unassign empty bins', 'Y');

insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='UnassignBinAction'), (select kim_typ_id from krim_typ_t where nm='Namespace or Action' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='actionClass' and nmspc_cd='KR-SYS'), 'org.kuali.ext.mm.document.web.struts.UnassignBinAction');

--SSFP  (Shopping Frontpage Maintenance)
insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Initiate Document' and nmspc_cd='KR-SYS'), 'KFS-MM', 'Initiate Stores SSFP Doc', 'Permission to initiate Stores Shopping Frontpage maintenance documents in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Initiate Stores SSFP Doc'), (select kim_typ_id from krim_typ_t where nm='Document Type (Permission)' and nmspc_cd='KR-SYS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSFP');

insert into krim_perm_t (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
values (krim_perm_id_s.nextval, sys_guid(), 1, (select perm_tmpl_id from krim_perm_tmpl_t where nm='Create / Maintain Record(s)' and nmspc_cd='KR-NS'), 'KFS-MM', 'Create / Maintain Stores SSFP Record(s)', 'Permission to Create / Maintain Stores SSFP Record(s) in KFS-MM', 'Y');
insert into krim_perm_attr_data_t (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
values (krim_attr_data_id_s.nextval, sys_guid(), 1, (select perm_id from krim_perm_t where nmspc_cd='KFS-MM' and nm='Create / Maintain Stores SSFP Record(s)'), (select kim_typ_id from krim_typ_t where nm='Document Type & Existing Records Only' and nmspc_cd='KR-NS'), (select kim_attr_defn_id from krim_attr_defn_t where nm='documentTypeName' and nmspc_cd='KR-WKFLW'), 'SSFP');

--Persmission END
