select extract(MONTH from orderDoc.Order_Create_Dt) as "Period Date (Month)",
       extract(YEAR from orderDoc.Order_Create_Dt) as "Period Date (Year)",
       orderDoc.Order_Create_Dt as "Order Date",
       orderDoc.Order_Type_Cd as "Order Type",
       orderDoc.Delivery_Department_Nm as "Department",
       orderDoc.Delivery_Building_Cd as "Delivery Bldg",
       custProfile.Customer_Prncpl_Nm as "User ID",       
       detail.distributor_nbr as "Item #",
       substr(detail.order_item_detail_desc, 0, 30) as "Description",
       orderDoc.Order_Id as "Order #",       
       accnt.account_nbr as "Account #",
       accnt.sub_acct_nbr as "Sub-Account #",
       detail.stock_unit_of_issue_cd as "Unit of Issue",
       detail.order_item_cost_amt as "Item Cost",
       detail.order_item_qty as "Qty Ordered",
       (detail.order_item_qty * detail.order_item_price_amt) as "Extended Sell Price",
       salesInstance.Order_Total_Amt as "Order Total",
       (select SUM((odtl.order_item_qty * odtl.Order_Item_Price_Amt) +
                   odtl.order_item_tax_amt)
          from MM_ORDER_DOC_T odoc
          left join MM_ORDER_DETAIL_T odtl
            on odtl.order_doc_nbr = odoc.fdoc_nbr
         where odoc.profile_typ_cd = 'INTERNAL'
           and odoc.delivery_department_nm = orderDoc.Delivery_Department_Nm
           and odoc.order_create_dt between to_date('01/01/2010', 'MM/DD/YYYY') and
               to_date('01/01/2011', 'MM/DD/YYYY')) as "Department Total",
       orderDoc.Warehouse_Cd as "Warehouse"               
  from MM_ORDER_DOC_T orderDoc
  left join MM_ORDER_DETAIL_T detail
    on detail.order_doc_nbr = orderDoc.Fdoc_Nbr
  left join MM_PROFILE_T custProfile
    on custProfile.Profile_Id = orderDoc.Customer_Profile_Id
  left join MM_SALES_INSTANCE_T salesInstance
    on salesInstance.Order_Doc_Nbr = orderDoc.Fdoc_Nbr
  left join MM_ACCOUNTS_T accnt
    on accnt.order_detail_id = detail.order_detail_id
  where orderDoc.order_create_dt between to_date('01/01/2010', 'MM/DD/YYYY') 
    and to_date('01/01/2011', 'MM/DD/YYYY')
 order by orderDoc.Delivery_Department_Nm, custProfile.Customer_Prncpl_Nm, orderDoc.Order_Id;
