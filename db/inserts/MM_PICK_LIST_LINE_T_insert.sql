INSERT into MM_PICK_LIST_LINE_T
select MM_PICK_LIST_LINE_S.nextval, 
       SYS_GUID(), 
       '1',
       NULL,
       NULL,
       NULL,
       aa.SALES_INSTANCE_ID,
       aa.ORDER_DETAIL_ID,
       (select MM_STOCK_BALANCE_T.BIN_ID 
        from MM_CATALOG_ITEM_T join MM_STOCK_BALANCE_T
           on MM_STOCK_BALANCE_T.STOCK_ID = MM_CATALOG_ITEM_T.STOCK_ID
        where MM_CATALOG_ITEM_T.CATALOG_ITEM_ID = aa.CATALOG_ITEM_ID
          and MM_CATALOG_ITEM_T.STOCK_ID > 0),
       (select MM_CATALOG_ITEM_T.STOCK_ID 
        from MM_CATALOG_ITEM_T 
        where MM_CATALOG_ITEM_T.CATALOG_ITEM_ID = aa.CATALOG_ITEM_ID
          and MM_CATALOG_ITEM_T.STOCK_ID > 0),
       aa.ORDER_ITEM_QTY,
       NULL,
       NULL,
       NULL,
       'INIT',
       'WH',
       CURRENT_DATE,
       CURRENT_DATE
from MM_ORDER_DETAIL_T aa
join MM_ORDER_DOC_T bb
   on bb.FDOC_NBR = aa.ORDER_DOC_NBR
  and bb.ORDER_TYPE_CD = 'WAREHS'
