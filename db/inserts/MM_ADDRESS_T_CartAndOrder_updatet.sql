update MM_SHOP_CART_T
  set BILLING_ADDRESS_ID = ( select MM_ADDRESS_T.ADDRESS_ID from MM_ADDRESS_T
                                         where MM_ADDRESS_T.ADDRESS_PROFILE_ID = MM_SHOP_CART_T.CUSTOMER_PROFILE_ID
                                             and MM_ADDRESS_T.ADDRESS_TYPE_CD = '01' )
;
update MM_SHOP_CART_T
  set SHIPPING_ADDRESS_ID = ( select MM_ADDRESS_T.ADDRESS_ID from MM_ADDRESS_T
                                         where MM_ADDRESS_T.ADDRESS_PROFILE_ID = MM_SHOP_CART_T.CUSTOMER_PROFILE_ID
                                             and MM_ADDRESS_T.ADDRESS_TYPE_CD = '02' )
;
update MM_ORDER_DOC_T
  set BILLING_ADDRESS_ID = ( select MM_ADDRESS_T.ADDRESS_ID from MM_ADDRESS_T
                                         where MM_ADDRESS_T.ADDRESS_PROFILE_ID = MM_ORDER_DOC_T.CUSTOMER_PROFILE_ID
                                             and MM_ADDRESS_T.ADDRESS_TYPE_CD = '01' )
;
update MM_ORDER_DOC_T
  set SHIPPING_ADDRESS_ID = ( select MM_ADDRESS_T.ADDRESS_ID from MM_ADDRESS_T
                                         where MM_ADDRESS_T.ADDRESS_PROFILE_ID = MM_ORDER_DOC_T.CUSTOMER_PROFILE_ID
                                             and MM_ADDRESS_T.ADDRESS_TYPE_CD = '02' )
;
