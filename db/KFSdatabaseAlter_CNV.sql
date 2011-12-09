-- Create New Fical Year Objects
insert into ca_object_code_t
SELECT '2012', a.fin_coa_cd, a.fin_object_cd, SYS_GUID(),
       a.ver_nbr, a.fin_obj_cd_nm, a.fin_obj_cd_shrt_nm,
       a.fin_obj_level_cd, a.rpts_to_fin_coa_cd, a.rpts_to_fin_obj_cd,
       a.fin_obj_typ_cd, a.fin_obj_sub_typ_cd, a.hist_fin_object_cd,
       a.fin_obj_active_cd, a.fobj_bdgt_aggr_cd, a.fobj_mnxfr_elim_cd,
       a.fin_fed_funded_cd, a.nxt_yr_fin_obj_cd, a.rsch_bdgt_ctgry_cd,
       a.rsch_obj_cd_desc, a.rsch_on_cmp_ind
  FROM ca_object_code_t a
  where univ_fiscal_yr = '2011'
/
