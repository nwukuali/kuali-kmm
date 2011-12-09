select customerProfile.Customer_Prncpl_Nm as Customer_Id,
       (customer.last_nm || ', ' || customer.first_nm) as Customer_Name,
       customerProfile.Profile_Email,
       address.address_ln1,
       address.address_ln2,
       address.address_city_nm,
       address.address_state_cd,
       address.address_postal_cd,
       address.address_phone_nbr,
       orderDoc.Order_Id,
       orderDoc.Reqs_Id,
       catalog.catalog_cd as Catalog,
       item.distributor_nbr as Item#,
       substr(detail.order_item_detail_desc, 0, 30) as Item_Description,
       detail.order_item_price_amt as Sold_Price,
       detail.order_item_qty as Order_Qty,
       (detail.order_item_qty * detail.Order_Item_Price_Amt) as Extended_Cost,
       (detail.order_item_tax_amt * detail.order_item_qty) as Tax_Amount,
       ((detail.order_item_qty * detail.Order_Item_Price_Amt) + detail.order_item_tax_amt) as Order_Total,
       orderDoc.Order_Create_Dt as System_Date
  from  ${namespace}.MM_ORDER_DOC_T orderDoc
  left join ${namespace}.MM_ORDER_DETAIL_T detail
    on detail.order_doc_nbr = orderDoc.Fdoc_Nbr
  left join ${namespace}.MM_CATALOG_ITEM_T item
    on item.catalog_item_id = detail.catalog_item_id
  left join ${namespace}.MM_CATALOG_T catalog
    on catalog.catalog_id = item.catalog_id
  left join ${namespace}.MM_PROFILE_T customerProfile
    on customerProfile.profile_id = orderDoc.Customer_Profile_Id
  left join ${namespace}.MM_CUSTOMER_T customer
    on customer.prncpl_nm = customerProfile.Customer_Prncpl_Nm
  left join ${namespace}.MM_ADDRESS_T address 
    on address.address_id = orderDoc.Shipping_Address_Id
  where orderDoc.Profile_Typ_Cd = 'PERSONAL'
  and orderDoc.Order_Create_Dt > to_date('${fromDate}', 'MM/DD/YYYY')
  order by orderDoc.Order_Create_Dt desc;
