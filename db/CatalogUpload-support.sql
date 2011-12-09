create sequence MM_CATALOG_S minvalue 1 start with 5000 increment by 1;
create index MM_CATALOG_ITEM_PENDING_TI1 on MM_CATALOG_ITEM_PENDING_T (UNSPSC_CD);
create index MM_UNSPSC_TI1 on MM_UNSPSC_T (CODE);