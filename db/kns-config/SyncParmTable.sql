--Report Query for differences between KFS and MM PARM table
select mmParm.Appl_Nmspc_Cd,
       mmParm.Nmspc_Cd,
       mmParm.Parm_Dtl_Typ_Cd,
       mmParm.Parm_Nm,
       mmParm.Txt             as "MMTST.KRNS_PARM_T.TXT",
       kfsParm.Txt            as "FINTST.KRNS_PARM_T.TXT"
  from MMDEV.KRNS_PARM_T mmParm
  left join FINDEV.KRNS_PARM_T kfsParm
    on kfsParm.Parm_Nm = mmParm.Parm_Nm
   and kfsParm.Nmspc_Cd = mmParm.Nmspc_Cd
   and kfsParm.Parm_Dtl_Typ_Cd = mmParm.Parm_Dtl_Typ_Cd
   and kfsParm.Appl_Nmspc_Cd = mmParm.Appl_Nmspc_Cd
 where not mmParm.Nmspc_Cd = 'KFS-MM'
   and (not mmParm.Txt = kfsParm.Txt or
       (not mmParm.txt is null and kfsParm.txt is null) or
       (mmParm.txt is null and not kfsParm.txt is null));
       
       select * from KRNS_PARM_T t;

--Update to set all TXT values in MM.KRNS_PARM_T to those found in KFS.KRNS_PARM_T
update MMDEV.KRNS_PARM_T mmParm
   set mmParm.Txt =
       (select kfsParm.Txt
          from FINDEV.KRNS_PARM_T kfsParm
         where kfsParm.Parm_Nm = mmParm.Parm_Nm
           and kfsParm.Nmspc_Cd = mmParm.Nmspc_Cd
           and kfsParm.Parm_Dtl_Typ_Cd = mmParm.Parm_Dtl_Typ_Cd
           and kfsParm.Appl_Nmspc_Cd = mmParm.Appl_Nmspc_Cd)
 where (mmParm.Appl_Nmspc_Cd, mmParm.Nmspc_Cd, mmParm.Parm_Dtl_Typ_Cd, mmParm.Parm_Nm) in (select mm.Appl_Nmspc_Cd,
       mm.Nmspc_Cd,
       mm.Parm_Dtl_Typ_Cd,
       mm.Parm_Nm
  from MMDEV.KRNS_PARM_T mm
  left join FINDEV.KRNS_PARM_T kfs
    on kfs.Parm_Nm = mm.Parm_Nm
   and kfs.Nmspc_Cd = mm.Nmspc_Cd
   and kfs.Parm_Dtl_Typ_Cd = mm.Parm_Dtl_Typ_Cd
   and kfs.Appl_Nmspc_Cd = mm.Appl_Nmspc_Cd
 where not mm.Nmspc_Cd = 'KFS-MM'
   and (not mm.Txt = kfs.Txt or
       (not mm.txt is null and kfs.txt is null) or
       (mm.txt is null and not kfs.txt is null)));


