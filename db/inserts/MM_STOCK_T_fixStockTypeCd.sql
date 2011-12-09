update mm_stock_t 
  set STOCK_TYPE_CD = '01';

update mm_stock_t 
  set STOCK_TYPE_CD = '03'
where SALES_UNIT_OF_ISSUE_CD = 'CY';

update MM_STOCK_T
  set WILLCALL_IND = 'N'
where WILLCALL_IND is NULL;
