select agreement.agreement_nbr as "Agreement Number",
       agreement.agreement_begin_dt as "Begin Date",
       agreement.agreement_end_dt as "End Date",
       agreement.vendor_nm as "Vendor Name",
       agreement.po_total_limit as "PO Total Limit",
       agreement.agreement_used_amt as "Ageement Used Amount",
       (agreement.po_total_limit - agreement.agreement_used_amt) as "Dollars Remaining"
  from MM_AGREEMENT_T agreement
 where agreement.agreement_nbr = '201085'
   and agreement.actv_ind = 'Y';
