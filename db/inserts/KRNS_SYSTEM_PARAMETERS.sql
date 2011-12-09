insert into KRNS_NMSPC_T (NMSPC_CD, OBJ_ID, VER_NBR, NM, ACTV_IND)
       values('KFS-MM', SYS_GUID(), '1', 'Materiel Management', 'Y');

insert into KRNS_PARM_DTL_TYP_T (NMSPC_CD, PARM_DTL_TYP_CD, OBJ_ID, VER_NBR, NM, ACTV_IND)
       values('KFS-MM', 'All', SYS_GUID(), '1', 'All', 'Y');
insert into KRNS_PARM_DTL_TYP_T (NMSPC_CD, PARM_DTL_TYP_CD, OBJ_ID, VER_NBR, NM, ACTV_IND)
       values('KFS-MM', 'Document', SYS_GUID(), '1', 'Document', 'Y');
insert into KRNS_PARM_DTL_TYP_T (NMSPC_CD, PARM_DTL_TYP_CD, OBJ_ID, VER_NBR, NM, ACTV_IND)
       values('KFS-MM', 'Lookup', SYS_GUID(), '1', 'Lookup', 'Y');
insert into KRNS_PARM_DTL_TYP_T (NMSPC_CD, PARM_DTL_TYP_CD, OBJ_ID, VER_NBR, NM, ACTV_IND)
       values('KFS-MM', 'Batch', SYS_GUID(), '1', 'Batch', 'Y');
       
insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
       values('KFS-MM', 'Document', 'STOCK_TYPES_WITH_SERIAL_NUM', SYS_GUID(), '1', 'CONFG', '03', 'The Stock Type code(s) that can be associated with trackable serial numbers', 'A', 'KFS');

insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
       values('KFS-MM', 'Document', 'DEFAULT_WAREHOUSE_CD', SYS_GUID(), '1', 'CONFG', '63', 'The default Warehouse Code used for prepopulation in documents', 'A', 'KFS');
       
insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
       values('KFS-MM', 'Document', 'DEFAULT_SORT_CD', SYS_GUID(), '1', 'CONFG', 'Orders', 'The default Sort Code to be used for Pick List Generation', 'A', 'KFS');       
       
insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
       values('KFS-MM', 'Document', 'PACKING_LIST_PDF', SYS_GUID(), '1', 'CONFG', 'packing_list_template13.pdf', 'Name of template file to be used for Packing List printing', 'A', 'KFS');
       
insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
       values('KFS-MM', 'Document', 'PACKING_LIST_PDF_MAX_LINES', SYS_GUID(), '1', 'CONFG', '13', 'The maximum number of lines available per page on the Packing List template', 'A', 'KFS');                     
       
insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
       values('KFS-MM', 'Lookup', 'MAX_PRINT_ALL_RESULTS', SYS_GUID(), '1', 'CONFG', '200', 'The maximum number of Pick Tickets to return from the Lookup for building a Packing List', 'A', 'KFS');         

insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD ) 
		values('KFS-MM', 'Document', 'STOCK_PRICE_CODE', SYS_GUID(), 1, 'CONFG', '02', 'The Stock price code(s) that is used for calculating stock price', 'A', 'KFS');       

insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD ) 
		values('KFS-MM', 'Document', 'MAX_NUMBER_OF_WORKSHEET_COUNTERS', SYS_GUID(), 1, 'CONFG', '10', 'The maximum number of counters for creating worksheet', 'A', 'KFS');       

insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD ) 
		values('KFS-MM', 'Document', 'MAX_NUMBER_OF_WORKSHEET_COPIES', SYS_GUID(), 1, 'CONFG', '3', 'The maximum number of copies when creating worksheet', 'A', 'KFS');       
		
update KRNS_PARM_T set TXT = 'PACKING_LIST_PDF_MAX_LINES' where TXT='PACK_LIST_MAX_LINES';

insert into krns_parm_dtl_typ_t (NMSPC_CD, PARM_DTL_TYP_CD, OBJ_ID, VER_NBR, NM, ACTV_IND)
values ('KFS-MM', 'ShoppingCart', sys_GUID(), 1, 'ShoppingCart', 'Y');

insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD ) 
    values('KFS-MM', 'ShoppingCart', 'MAX_SHOPPING_RESULT_LIMIT', SYS_GUID(), 1, 'CONFG', '200', 'Maximum number of displayable results (Catalog Items stored in memory after search)', 'A', 'KFS');

insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD ) 
    values('KFS-MM', 'ShoppingCart', 'ALLOW_PERSONAL_USE', SYS_GUID(), 1, 'CONFG', 'Y', 'Determines whether personal use profiles/external users are allowed in the Shopping portal', 'A', 'KFS');
    
insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD ) 
    values('KFS-MM', 'Document', 'MAX_PRICE_TOLERANCE_ALLOWED', SYS_GUID(), 1, 'CONFG', '25.00', 'Maximum price Tolerance allowed for resolving items at the time of Stock Count', 'A', 'KFS');
    
insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
       values('KFS-MM', 'Batch', 'CAROUSEL_WAREHOUSE_ZONES', SYS_GUID(), '1', 'CONFG', '63=01,02,03,04', 'List of warehouses with their corresponding carousel zone codes. (WH1=ZN1,ZN2;WH2=ZN1,ZN2)', 'A', 'KFS');

insert into KRNS_PARM_T (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD) 
values('KFS-MM', 'Batch', 'FAMIS_FEED_ACCOUNTS', SYS_GUID(), '1', 'CONFG', 'MS-XT022907;', 'List of Accounts used for building FAMIS feed', 'A', 'KFS');

    
COMMIT;