insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
       VALUES (krim_role_id_s.nextval, SYS_GUID(), 1, 'MM Order Reviewer', 'KFS-MM', 'Role for Stores Order Document approval',  '1', 'Y', SYSDATE);


insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, LAST_UPDT_DT)
       VALUES (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, SYS_GUID(), krim_role_id_s.currval, (select prncpl_id from KRIM_PRNCPL_T p where p.prncpl_nm='mmuser'), 'P', SYSDATE);

insert into Krim_Rsp_t (Rsp_Id, Obj_Id,Ver_Nbr,Rsp_Tmpl_Id,Nmspc_Cd,Nm,Desc_Txt,Actv_Ind)
       VALUES (KRIM_RSP_ID_S.nextval, SYS_GUID(), 1, 1, 'KFS-MM', 'Review', 'Review and approve Order Documents for KMM.', 'Y');

select MAX(to_number(t.Attr_Data_Id)) +1 from Krim_Rsp_Attr_Data_t t;

insert into Krim_Rsp_Attr_Data_t (Attr_Data_Id, Obj_Id, Ver_Nbr, Rsp_Id, Kim_Typ_Id, Kim_Attr_Defn_Id, Attr_Val)
       VALUES ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), SYS_GUID(), 1, KRIM_RSP_ID_S.currval, '7', '13', 'SORD');

insert into Krim_Rsp_Attr_Data_t (Attr_Data_Id, Obj_Id, Ver_Nbr, Rsp_Id, Kim_Typ_Id, Kim_Attr_Defn_Id, Attr_Val)
       VALUES ((select MAX(to_number(t.Attr_Data_Id))+1 from Krim_Rsp_Attr_Data_t t), SYS_GUID(), 1, KRIM_RSP_ID_S.currval, '7', '16', 'OrderDocumentReview');
     


insert into KRIM_ROLE_RSP_T (ROLE_RSP_ID,OBJ_ID, VER_NBR, ROLE_ID, RSP_ID, ACTV_IND)
       VALUES ((select MAX(to_number(rr.role_rsp_id))+1 from KRIM_ROLE_RSP_T rr) , SYS_GUID(), 1, (select r.role_id from Krim_Role_t r where r.role_nm='MM Order Reviewer'),(select MAX(to_number(rsp.rsp_id)) from KRIM_RSP_T rsp), 'Y'); 
insert into KRIM_ROLE_RSP_ACTN_T (ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN)
       VALUES (KRIM_ROLE_RSP_ACTN_ID_S.nextval, SYS_GUID(), 1, 'A', '', 'F', '*', (select MAX(to_number(rr.role_rsp_id)) from KRIM_ROLE_RSP_T rr), 'N' );
        
