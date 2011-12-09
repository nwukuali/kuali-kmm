insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Batch', 'CAROUSEL_WAREHOUSE_ZONES', '8644CCE71B151E35E040007F010010ED', 1, 'CONFG', '74=01,02,03,04', 'List of warehouses with their corresponding carousel zone codes. (WH1=ZN1,ZN2;WH2=ZN1,ZN2)', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Batch', 'FAMIS_FEED_ACCOUNTS', '8D8E1B3A84DCA9AFE04008238A717A2C', 1, 'CONFG', 'MS-XT022907;', 'List of Accounts used for building FAMIS feed', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'DEFAULT_SORT_CD', '9C679C1D5BC34E209B9C00810A3F4626', 1, 'CONFG', 'Orders', 'The default Sort Code to be used for Pick List Generation', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'DEFAULT_WAREHOUSE_CD', '22A57C88268D4B30898FCEC638088AA9', 3, 'CONFG', '74', 'The default Warehouse Code used for prepopulation in documents', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'MAX_NUMBER_OF_WORKSHEET_COPIES', '38388F9A4C4D4DBA981C0B7A94396D73', 1, 'CONFG', '3', 'The maximum number of copies when creating worksheet', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'MAX_NUMBER_OF_WORKSHEET_COUNTERS', '817FF766FE204D19A1B8A8F5E5CD541F', 1, 'CONFG', '10', 'The maximum number of counters for creating worksheet', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'MAX_PRICE_TOLERANCE_ALLOWED', 'EE1D5F9186754EC39386B6B2FC01B791', 1, 'CONFG', '25.00', 'Maximum price Tolerance allowed for resolving items at the time of Stock Count', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'MM_ORDER_APPROVALS_BEGIN_AT_AMOUNT', '8A5AE07466649D89E04008238A713C4F', 1, 'CONFG', '2500', 'Maximum dollar amount for MM orders that does not require Fiscal officer approval', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'PACKING_LIST_PDF', '59480B8C24ED46FD959F1555C1BF0DF8', 1, 'CONFG', 'packing_list_template13.pdf', 'Name of template file to be used for Packing List printing', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'PACKING_LIST_PDF_MAX_LINES', '48A955CDFD9642478AA3B9CF4B514467', 1, 'CONFG', '13', 'The maximum number of lines available per page on the Packing List template', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'STOCK_PRICE_CODE', 'CC7186D2C96E46199A2A4F1AA9673993', 2, 'CONFG', '01', 'The Stock price code(s) that is used for calculating stock price', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Document', 'STOCK_TYPES_WITH_SERIAL_NUM', 'C29C5D1FA82A4259BF1FC5A01A647649', 2, 'CONFG', '03;02', 'The Stock Type code(s) that can be associated with trackable serial numbers', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'Lookup', 'MAX_PRINT_ALL_RESULTS', '232E3310CA434605A9660AA778EC2987', 1, 'CONFG', '200', 'The maximum number of Pick Tickets to return from the Lookup for building a Packing List', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'ShoppingCart', 'ALLOW_PERSONAL_USE', '8B5A186F47DC20F8E04008238B714CCE', 4, 'CONFG', 'N', 'Determines whether personal use profiles/external users are allowed in the Shopping portal', 'A', 'KFS');

insert into krns_parm_t (NMSPC_CD, PARM_DTL_TYP_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, TXT, PARM_DESC_TXT, CONS_CD, APPL_NMSPC_CD)
values ('KFS-MM', 'ShoppingCart', 'MAX_SHOPPING_RESULT_LIMIT', '7ABD4C7FCEC942B0B1EBB175B43D0995', 1, 'CONFG', '200', 'Maximum number of displayable results (Catalog Items stored in memory after search)
    ', 'A', 'KFS');

