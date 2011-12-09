delete from krns_parm_t t
 where t.nmspc_cd in ('KFS-MM');
delete from krns_parm_dtl_typ_t t
 where t.nmspc_cd in ('KFS-MM');      
delete from krns_nmspc_t t
 where t.nmspc_cd in ('KFS-MM');
delete from krns_campus_t;
delete from krns_maint_lock_t;
delete from krns_att_t;                      
delete from krns_nte_t;
delete from krns_sesn_doc_t;    
delete from krns_maint_doc_t;
delete from krns_doc_hdr_t;
delete from krns_lookup_rslt_t;
delete from krns_lookup_sel_t;

