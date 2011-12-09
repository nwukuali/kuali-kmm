Insert into MM_SALES_INSTANCE_T
select MM_SALES_INSTANCE_S.nextval, 
       SYS_GUID(), 
       '1',
       aa.FDOC_NBR, 
       aa.ORDER_TYPE_CD,
       aa.ORDER_DOC_STATUS_CD,
       (select round(sum(ab.ORDER_ITEM_QTY * (ab.ORDER_ITEM_PRICE_AMT + ab.ORDER_ITEM_MARKUP_AMT)),2) from MM_ORDER_DETAIL_T ab where ab.ORDER_DOC_NBR = aa.FDOC_NBR),
       (select round(sum(ab.ORDER_ITEM_QTY * ab.ORDER_ITEM_TAX_AMT),2) from MM_ORDER_DETAIL_T ab where ab.ORDER_DOC_NBR = aa.FDOC_NBR),
       (select round(sum(ab.ORDER_ITEM_QTY * (ab.ORDER_ITEM_PRICE_AMT + ab.ORDER_ITEM_MARKUP_AMT + ab.ORDER_ITEM_TAX_AMT)),2) from MM_ORDER_DETAIL_T ab where ab.ORDER_DOC_NBR = aa.FDOC_NBR),
       aa.CUSTOMER_PROFILE_ID,
       CURRENT_DATE
from MM_ORDER_DOC_T aa
where aa.ORDER_TYPE_CD = 'WAREHS'
