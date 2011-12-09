update MM_ORDER_DETAIL_T
  set MM_ORDER_DETAIL_T.SALES_INSTANCE_ID = (select MM_SALES_INSTANCE_T.SALES_INSTANCE_ID
                                             from MM_SALES_INSTANCE_T
                                             where MM_ORDER_DETAIL_T.ORDER_DOC_NBR = MM_SALES_INSTANCE_T.ORDER_DOC_NBR)
;
  
