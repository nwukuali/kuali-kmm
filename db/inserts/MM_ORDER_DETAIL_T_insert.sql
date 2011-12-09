Insert into MM_ORDER_DETAIL_T
select MM_ORDER_DETAIL_S.nextval, 
       SYS_GUID(), 
       '1',
       ab.FDOC_NBR,
       NULL,
       ab.ORDER_DOC_STATUS_CD,
       aa.SHOP_CART_DETAIL_ID,
       aa.CATALOG_ITEM_ID,
       aa.SHOP_CART_ITEM_QTY,
       aa.ITEM_UNIT_OF_ISSUE_CD,
       aa.SHOP_CART_ITEM_COST_AMT,
       aa.SHOP_CART_ITEM_PRICE_AMT,
       aa.SHOP_CART_ITEM_MARKUP_AMT,
       aa.SHOP_CART_ITEM_TAX_AMT,
       aa.SHOP_CART_ITEM_ADDL_CST_AMT,
       aa.SHOP_CART_ITEM_DETAIL_DESC,
       (select ba.DISTRIBUTOR_NBR
        from MM_CATALOG_ITEM_T ba
        where ba.catalog_item_id = aa.catalog_item_id),
       aa.SHIPPING_WGT,
       aa.SHIPPING_HT,
       aa.SHIPPING_WD,
       aa.SHIPPING_LH,
       aa.WILLCALL_IND,
       aa.VENDOR_HEADER_GENERATED_ID,
       aa.VENDOR_DETAIL_ASSIGNED_ID,
       aa.VENDOR_NM,
       NULL,
       NULL,
       CURRENT_DATE,
       NULL,
       NULL,
       NULL,
       NULL
from MM_SHOP_CART_DETAIL_T aa
join MM_ORDER_DOC_T ab
   on ab.SHOP_CART_ID = aa.SHOP_CART_ID
