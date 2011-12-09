Insert into MM_ORDER_DOC_T
select MM_SYS_GUID_S.nextval, 
       SYS_GUID(), 
       '1',
       MM_ORDER_DOC_S.nextval,
       aa.SHOP_CART_ID,
       'WAREHS',
       NULL,
       NULL,
       NULL,
       NULL,
       NULL,
       aa.CUSTOMER_PROFILE_ID,
       'T',
       (select cz.WAREHOUSE_CD
        from MM_SHOP_CART_DETAIL_T cb
        join MM_CATALOG_ITEM_T cc
           on cc.catalog_item_id = cb.catalog_item_id
        join MM_CATALOG_T cz
           on cz.catalog_id = cc.catalog_id
        where cb.shop_cart_id = aa.shop_cart_id
           and rownum < 2),
       'ZZ',
       aa.DELIVERY_BUILDING_CD,
       aa.DELIVERY_BUILDING_RM_NBR,
       NULL,
       aa.DELIVERY_INSTRUCTION_TXT,
       aa.BILLING_ADDRESS_ID,
       aa.SHIPPING_ADDRESS_ID,
       NULL,
       NULL,
       CURRENT_DATE
from MM_SHOP_CART_T aa
where (select sum(ab.SHOP_CART_ITEM_QTY * ab.SHOP_CART_ITEM_PRICE_AMT) from MM_SHOP_CART_DETAIL_T ab where ab.SHOP_CART_ID = aa.SHOP_CART_ID) > 0
