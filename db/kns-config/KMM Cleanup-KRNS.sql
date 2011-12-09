delete from krns_parm_t t
 where t.nmspc_cd in ('KFS-GL',
                      'KFS-VND',
                      'KFS-CG',
                      'KFS-LD',
                      'KFS-COA',
                      'KFS-FP',
                      'KFS-SYS',
                      'KFS-CAB',
                      'KFS-PURAP',
                      'KFS-AR',
                      'KFS-BC',
                      'KFS-PDP',
                      'KFS-CAM',
                      'KFS-EC');
delete from krns_parm_dtl_typ_t t
 where t.nmspc_cd in ('KFS-AR',
                      'KFS-BC',
                      'KFS-CAB',
                      'KFS-CAM',
                      'KFS-CG',
                      'KFS-COA',
                      'KFS-EC',
                      'KFS-FP',
                      'KFS-GL',
                      'KFS-LD',
                      'KFS-PDP',
                      'KFS-PURAP',
                      'KFS-SYS',
                      'KFS-VND');      
delete from krns_nmspc_t t
 where t.nmspc_cd in ('KFS-AR',
                      'KFS-BC',
                      'KFS-CAB',
                      'KFS-CAM',
                      'KFS-CG',
                      'KFS-COA',
                      'KFS-EC',
                      'KFS-FP',
                      'KFS-GL',
                      'KFS-LD',
                      'KFS-PDP',
                      'KFS-PURAP',
                      'KFS-SYS',
                      'KFS-VND');
delete from krns_maint_lock_t;
delete from krns_pessimistic_lock_t;
delete from krns_att_t;                      
delete from krns_nte_t;
delete from krns_campus_t t where t.campus_cd <> 'EL';
delete from krns_sesn_doc_t;    
delete from krns_maint_doc_att_t;
delete from krns_maint_doc_t;
delete from krns_adhoc_rte_actn_recip_t;
delete from krns_doc_hdr_t;
delete from krns_doc_typ_t;
delete from krns_lookup_rslt_t;
delete from krns_lookup_sel_t;

