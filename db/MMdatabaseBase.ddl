CREATE TABLE MM_ACCOUNTS_T                                                                                                                                 
(                                                                                                                                                          
  ACCOUNTS_ID          NUMBER(18) NOT NULL ,                                                                                        
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  DOC_NBR              VARCHAR2(14) NOT NULL ,                                                                                                             
  LINE_NBR             NUMBER(8) NOT NULL ,                                                                                                                
  FIN_COA_CD           VARCHAR2(2) NOT NULL ,                                                                                                             
  ACCOUNT_NBR          VARCHAR2(7) NOT NULL ,                                                                                                             
  SUB_ACCT_NBR         VARCHAR2(5) NOT NULL ,                                                                                                             
  FIN_OBJECT_CD        VARCHAR2(4) NULL ,                                                                                                                  
  PROJECT_CD           VARCHAR2(10) NULL ,                                                                                                                 
  ACCOUNT_PCT          NUMBER(8,2) NOT NULL ,                                                                                                              
  ACCOUNT_FIXED_AMT    NUMBER(19,4) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ACCOUNTS_T                                                                                                                                  
  ADD CONSTRAINT  MM_ACCOUNTS_TP1 PRIMARY KEY (ACCOUNTS_ID);                             
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ACCOUNTS_T                                                                                                                                  
ADD CONSTRAINT  MM_ACCOUNTS_TC0 UNIQUE (DOC_NBR, LINE_NBR, FIN_COA_CD, ACCOUNT_NBR, SUB_ACCT_NBR, FIN_OBJECT_CD);                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_ADDITIONAL_COST_TYPE_T                                                                                                                     
(                                                                                                                                                          
  ADDITIONAL_COST_TYPE_CD VARCHAR2(12) NOT NULL ,                                                                                                          
  OBJ_ID                  VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                 NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                      VARCHAR2(45) NULL ,                                                                                                            
  ACTV_IND                VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT          TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ADDITIONAL_COST_TYPE_T                                                                                                                      
  ADD CONSTRAINT  MM_ADDITIONAL_COST_TYPE_TP1 PRIMARY KEY (ADDITIONAL_COST_TYPE_CD);                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ADDITIONAL_COST_TYPE_T                                                                                                                      
ADD CONSTRAINT  MM_ADDITIONAL_COST_TYPE_TC0 UNIQUE (OBJ_ID);                                                                                               
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_ADDRESS_T                                                                                                                                 
(                                                                                                                                                          
  ADDRESS_ID           NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  ADDRESS_TYPE_CD      VARCHAR2(2) NOT NULL ,                                                                                                             
  ADDRESS_PROFILE_ID   VARCHAR2(36) NOT NULL ,                                                                                                                
  ADDRESS_NM           VARCHAR2(45) NOT NULL ,                                                                                                                
  ADDRESS_LN1          VARCHAR2(45) NOT NULL ,                                                                                                             
  ADDRESS_LN2          VARCHAR2(45) NOT NULL ,                                                                                                             
  ADDRESS_CITY_NM      VARCHAR2(45) NOT NULL ,                                                                                                             
  ADDRESS_STATE_CD     VARCHAR2(2) NOT NULL ,                                                                                                                  
  ADDRESS_POSTAL_CD    VARCHAR2(20) NULL ,                                                                                                                 
  ADDRESS_COUNTRY_CD   VARCHAR2(2) NOT NULL ,                                                                                                                  
  ADDRESS_PHONE_NBR    VARCHAR2(45) NOT NULL ,                                                                                                             
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ADDRESS_T                                                                                                                                  
  ADD CONSTRAINT  MM_ADDRESS_TP1 PRIMARY KEY (ADDRESS_ID);                             
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ADDRESS_T                                                                                                                                  
ADD CONSTRAINT  MM_ADDRESS_TC0 UNIQUE (ADDRESS_TYPE_CD, ADDRESS_PROFILE_ID);                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_ADDRESS_TYPE_T                                                                                                                                 
(                                                                                                                                                          
  ADDRESS_TYPE_CD      VARCHAR2(2) NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NOT NULL ,                                                                                                                
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ADDRESS_TYPE_T                                                                                                                                  
  ADD CONSTRAINT  MM_ADDRESS_TYPE_TP1 PRIMARY KEY (ADDRESS_TYPE_CD);                             
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ADDRESS_TYPE_T                                                                                                                                  
ADD CONSTRAINT  MM_ADDRESS_TYPE_TC0 UNIQUE (OBJ_ID);                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_AGREEMENT_T                                                                                                                                
(                                                                                                                                                          
  AGREEMENT_NBR        NUMBER(9) NOT NULL ,                                                                                                                
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  PO_ID                NUMBER(9) NOT NULL ,                                                                                                                
  VENDOR_HEADER_GENERATED_ID NUMBER(10) NOT NULL ,                                                                                                         
  VENDOR_DETAIL_ASSIGNED_ID NUMBER(10) NOT NULL ,                                                                                                          
  VENDOR_NM            VARCHAR2(45) NOT NULL ,                                                                                                             
  PO_TOTAL_LIMIT       NUMBER(19,2) NOT NULL ,                                                                                                             
  AGREEMENT_LAG_DAYS   NUMBER(8) NULL ,                                                                                                                    
  AGREEMENT_USED_AMT   NUMBER(19,2) NOT NULL ,                                                                                                             
  AGREEMENT_BEGIN_DT   DATE NOT NULL ,                                                                                                                     
  AGREEMENT_END_DT     DATE NOT NULL ,                                                                                                                     
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_AGREEMENT_T                                                                                                                                 
  ADD CONSTRAINT  MM_AGREEMENT_TP1 PRIMARY KEY (AGREEMENT_NBR);                                                                                                    
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_AGREEMENT_T                                                                                                                                 
ADD CONSTRAINT  MM_AGREEMENT_TC0 UNIQUE (OBJ_ID);                                                                                                          
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_BACK_ORDER_T                                                                                                                               
(                                                                                                                                                          
  BACK_ORDER_ID          NUMBER(18) NOT NULL ,
  OBJ_ID                 VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  PICK_LIST_LINE_ID      NUMBER(18) NOT NULL ,                                                                                                                
  NEW_PICK_LIST_LINE_ID  NUMBER(18) NULL ,
  PICK_TUB_NBR           NUMBER(8) NULL ,                                                                                                                    
  CUSTOMER_ID            VARCHAR2(12) NOT NULL ,                                                                                                             
  ORDER_TYPE_CD          VARCHAR2(6) NOT NULL ,                                                                                                              
  STOCK_ID               VARCHAR2(36) NOT NULL ,                                                                                                             
  BACK_ORDER_STOCK_QTY   NUMBER(18,4) NOT NULL ,                                                                                                             
  STOCK_UNIT_OF_ISSUE_CD VARCHAR2(10) NOT NULL ,                                                                                                            
  CAMPUS_CD              VARCHAR2(2) NOT NULL ,                                                                                                              
  DELIVERY_BUILDING_CD   VARCHAR2(4) NOT NULL ,                                                                                                              
  ROUTE_CD               VARCHAR2(2) NOT NULL ,                                                                                                              
  ORDER_ID               NUMBER(8) NOT NULL ,                                                                                                                
  LAST_UPDATE_DT         TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_BACK_ORDER_T                                                                                                                                
  ADD CONSTRAINT  MM_BACK_ORDER_TP1 PRIMARY KEY (BACK_ORDER_ID);                                                                          
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_BACK_ORDER_T                                                                                                                                
ADD CONSTRAINT  MM_BACK_ORDER_TC0 UNIQUE (PICK_LIST_LINE_ID);                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_BIN_T                                                                                                                                      
(                                                                                                                                                          
  BIN_ID               NUMBER(18)  NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  ZONE_ID              NUMBER(18) NOT NULL ,                                                                                        
  BIN_NBR              VARCHAR2(6) NOT NULL ,                                                                                                              
  SHELF_ID             VARCHAR2(2) NULL ,                                                                                                                  
  SHELF_ID_NBR         VARCHAR2(2) NULL ,                                                                                                                  
  MAXIMUM_SHELF_QTY    NUMBER(8) NULL ,                                                                                                                    
  BIN_HT               NUMBER(8,2) NULL ,                                                                                                                  
  BIN_WD               NUMBER(8,2) NULL ,                                                                                                                  
  BIN_LH               NUMBER(8,2) NULL ,                                                                                                                  
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_BIN_T                                                                                                                                       
  ADD CONSTRAINT  MM_BIN_TP1 PRIMARY KEY (BIN_ID);                                                                                    
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_BIN_T                                                                                                                                       
  ADD CONSTRAINT  MM_BIN_TC0 UNIQUE (ZONE_ID,BIN_NBR,SHELF_ID,SHELF_ID_NBR);                                                                                                                
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CATALOG_CODE_T                                                                                                                             
(                                                                                                                                                          
  CATALOG_CD           VARCHAR2(2) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_CODE_T                                                                                                                              
  ADD CONSTRAINT  MM_CATALOG_CODE_TP1 PRIMARY KEY (CATALOG_CD);                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_CODE_T                                                                                                                              
ADD CONSTRAINT  MM_CATALOG_CODE_TC0 UNIQUE (OBJ_ID);                                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CATALOG_GROUP_T                                                                                                                                    
(                                                                                                                                                          
  CATALOG_GROUP_CD     VARCHAR2(12) NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CATALOG_GROUP_NM     VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_GROUP_T                                                                                                                                     
  ADD CONSTRAINT  MM_CATALOG_GROUP_TP1 PRIMARY KEY (CATALOG_GROUP_CD);                                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_GROUP_T                                                                                                                                     
ADD CONSTRAINT  MM_CATALOG_GROUP_TC0 UNIQUE (OBJ_ID);                                                                                                              
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CATALOG_IMAGE_T                                                                                                                            
(                                                                                                                                                          
  CATALOG_IMAGE_NBR    NUMBER(18)  NOT NULL ,                                                                                                                
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CATALOG_IMAGE_NM     VARCHAR2(12) NOT NULL ,                                                                                                             
  CATALOG_IMAGE_URL    VARCHAR2(60) NOT NULL ,                                                                                                             
  CATALOG_ITEM_ID      NUMBER(18) NOT NULL ,                                                                                                             
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_IMAGE_T                                                                                                                             
  ADD CONSTRAINT  MM_CATALOG_IMAGE_TP1 PRIMARY KEY (CATALOG_IMAGE_NBR);
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_IMAGE_T                                                                                                                             
ADD CONSTRAINT  MM_CATALOG_IMAGE_TC0 UNIQUE (OBJ_ID);                                                                                                      
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CATALOG_ITEM_T                                                                                                                           
(                                                                                                                                                          
  CATALOG_ITEM_ID      NUMBER(18)   NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CATALOG_DOC_NBR      VARCHAR2(14) NOT NULL ,                                                                                                             
  DISTRIBUTOR_NBR      VARCHAR2(30) NOT NULL ,                                                                                                                 
  MANUFACTURER_NBR     VARCHAR2(30) NULL ,                                                                                                                 
  CATALOG_UNIT_OF_ISSUE_CD VARCHAR2(10) NOT NULL ,                                                                                                            
  CATALOG_PRC          NUMBER(19,4) NOT NULL ,                                                                                                             
  CATALOG_DESC         VARCHAR2(400) NOT NULL ,                                                                                                            
  RECYCLED_IND         VARCHAR2(1) NULL ,                                                                                                                 
  WILLCALL_IND         VARCHAR2(1) NULL ,                                                                                                                  
  UNSPSC_CD            VARCHAR2(10) NULL ,                                                                                                                 
  SHIPPING_WGT         NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_HT          NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_WD          NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_LH          NUMBER(8,2) NULL ,                                                                                                                  
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_ITEM_T                                                                                                                            
  ADD CONSTRAINT  MM_CATALOG_ITEM_TP1 PRIMARY KEY (CATALOG_ITEM_ID);                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_ITEM_T                                                                                                                            
ADD CONSTRAINT  MM_CATALOG_ITEM_TC0 UNIQUE (CATALOG_DOC_NBR, DISTRIBUTOR_NBR);                                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CATALOG_ITEM_PENDING_T                                                                                                                           
(                                                                                                                                                          
  CATALOG_ITEM_PND_ID  NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CATALOG_PND_DOC_NBR  VARCHAR2(14)  NOT NULL ,                                                                                                             
  LINE_NBR             NUMBER(8)  NOT NULL ,                                                                                                    
  DISTRIBUTOR_NBR      VARCHAR2(30) NULL ,                                                                                                                 
  MANUFACTURER_NBR     VARCHAR2(30) NULL ,                                                                                                                 
  CATALOG_UNIT_OF_ISSUE_CD VARCHAR2(10) NOT NULL ,                                                                                                            
  CATALOG_PRC          NUMBER(19,4) NOT NULL ,                                                                                                             
  CATALOG_DESC         VARCHAR2(400) NOT NULL ,                                                                                                            
  RECYCLED_IND         VARCHAR2(1) NULL ,                                                                                                                 
  UNSPSC_CD            VARCHAR2(10) NULL ,                                                                                                                 
  SHIPPING_WGT         NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_HT          NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_WD          NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_LH          NUMBER(8,2) NULL ,                                                                                                                  
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_ITEM_PENDING_T                                                                                                                            
  ADD CONSTRAINT  MM_CATALOG_ITEM_PENDING_TP1 PRIMARY KEY (CATALOG_ITEM_PND_ID);                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_ITEM_PENDING_T                                                                                                                            
ADD CONSTRAINT  MM_CATALOG_ITEM_PENDING_TC0 UNIQUE (CATALOG_PND_DOC_NBR, DISTRIBUTOR_NBR);                                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CATALOG_PENDING_DOC_T                                                                                                                                  
(                                                                                                                                                          
  FDOC_NBR             VARCHAR2(14)  NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CATALOG_CD           VARCHAR2(2) NOT NULL ,                                                                                                             
  CATALOG_DESC         VARCHAR2(200) NULL ,                                                                                                                
  CATALOG_BEGIN_DT     DATE NULL ,                                                                                                                         
  CATALOG_END_DT       DATE NULL ,                                                                                                                         
  PRIORITY_NBR         NUMBER(8)  NULL ,                                                                                                    
  AGREEMENT_NBR        NUMBER(9) NULL ,                                                                                                                    
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_PENDING_DOC_T                                                                                                                                   
  ADD CONSTRAINT  MM_CATALOG_PENDING_DOC_TP1 PRIMARY KEY (FDOC_NBR);                                                                                                 
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_PENDING_DOC_T                                                                                                                                   
ADD CONSTRAINT  MM_CATALOG_PENDING_DOC_TC0 UNIQUE (OBJ_ID);                                                                                                            
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CATALOG_SUBGROUP_T                                                                                                                                 
(                                                                                                                                                          
  CATALOG_SUBGROUP_ID       NUMBER(18) NOT NULL ,                                                                                        
  OBJ_ID                    VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                   NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CATALOG_GROUP_CD          VARCHAR2(12) NOT NULL ,                                                                                                             
  CATALOG_SUBGROUP_CD       VARCHAR2(12) NOT NULL ,                                                                                                             
  CATALOG_SUBGROUP_DESC     VARCHAR2(80) NOT NULL ,                                                                                                             
  CATALOG_ITEM_ID           NUMBER(18) NULL ,
  PRIOR_CATALOG_SUBGROUP_CD VARCHAR2(12) NULL ,                                                                                                                  
  ACTV_IND                  VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT            TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_SUBGROUP_T                                                                                                                                  
  ADD CONSTRAINT  MM_CATALOG_SUBGROUP_TP1 PRIMARY KEY (CATALOG_SUBGROUP_ID);                                                                   
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_SUBGROUP_T                                                                                                                                  
ADD CONSTRAINT  MM_CATALOG_SUBGROUP_TC0 UNIQUE (CATALOG_GROUP_CD, CATALOG_SUBGROUP_CD);                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CATALOG_DOC_T                                                                                                                                  
(                                                                                                                                                          
  FDOC_NBR             VARCHAR2(14)  NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CATALOG_CD           VARCHAR2(2) NOT NULL ,                                                                                                             
  CATALOG_DESC         VARCHAR2(200) NULL ,                                                                                                                
  CATALOG_BEGIN_DT     DATE NULL ,                                                                                                                         
  CATALOG_END_DT       DATE NULL ,                                                                                                                         
  PRIORITY_NBR         NUMBER(8)  NULL ,                                                                                                    
  AGREEMENT_NBR        NUMBER(9) NULL ,                                                                                                                    
  CURRENT_IND          VARCHAR2(1) NOT NULL ,                                                                                                              
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_DOC_T                                                                                                                                   
  ADD CONSTRAINT  MM_CATALOG_DOC_TP1 PRIMARY KEY (FDOC_NBR);                                                                                                 
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CATALOG_DOC_T                                                                                                                                   
ADD CONSTRAINT  MM_CATALOG_DOC_TC0 UNIQUE (OBJ_ID);                                                                                                            
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CHECKIN_DETAIL_T                                                                                                                           
(                                                                                                                                                          
  CHECKIN_DETAIL_ID      NUMBER(18) NOT NULL ,                                                                                        
  OBJ_ID                 VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CHECKIN_DOC_NBR        VARCHAR2(14) NOT NULL ,                                                                                                                
  CHECKIN_LINE_NBR       NUMBER(8) NOT NULL ,                                                                                                                
  STOCK_ID               NUMBER(18) NOT NULL ,                                                                                                             
  STOCK_QTY              NUMBER(19,4) NOT NULL ,                                                                                                             
  STOCK_UNIT_OF_ISSUE_CD VARCHAR2(10) NOT NULL ,                                                                                                            
  STOCK_PRC              NUMBER(19,4) NOT NULL ,                                                                                                             
  STOCK_DETAIL_DESC      VARCHAR2(400) NOT NULL ,                                                                                                            
  MANUFACTURER_NBR       VARCHAR2(30) NOT NULL ,                                                                                                             
  BIN_ID                 NUMBER(18) NOT NULL ,
  ACCEPTED_ITEM_QTY      NUMBER(19,4) NOT NULL ,                                                                                                             
  REJECTED_ITEM_QTY      NUMBER(19,4) NOT NULL ,                                                                                                             
  PO_ID                  NUMBER(9) NULL ,                                                                                                                    
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CHECKIN_DETAIL_T                                                                                                                            
  ADD CONSTRAINT  MM_CHECKIN_DETAIL_TP1 PRIMARY KEY (CHECKIN_DETAIL_ID);
                                                                                                                                                           
ALTER TABLE MM_CHECKIN_DETAIL_T
ADD CONSTRAINT  MM_CHECKIN_DETAIL_TC0 UNIQUE (CHECKIN_DOC_NBR, CHECKIN_LINE_NBR);                                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CHECKIN_DOC_T                                                                                                                           
(                                                                                                                                                          
  FDOC_NBR             VARCHAR2(14)  NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  ORDER_DOC_NBR        VARCHAR2(14) NOT NULL ,                                                                                                                
  VENDOR_REF_NBR       VARCHAR2(45) NULL ,                                                                                                                 
  VENDOR_SHIPMENT_NBR  VARCHAR2(45) NULL ,                                                                                                                 
  WAREHOUSE_CD         VARCHAR2(2) NULL ,                                                                                                                  
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CHECKIN_DOC_T                                                                                                                            
  ADD CONSTRAINT  MM_CHECKIN_DOC_TP1 PRIMARY KEY (FDOC_NBR);                                                                                
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CHECKIN_DOC_T                                                                                                                            
ADD CONSTRAINT  MM_CHECKIN_DOC_TC0 UNIQUE (OBJ_ID);                                                                                                     
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CUSTOMER_FAV_DETAIL_T                                                                                                                      
(                                                                                                                                                          
  CUSTOMER_FAV_DETAIL_ID      NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID                      VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                     NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CUSTOMER_FAV_ID             NUMBER(18) NOT NULL ,                                                                                                             
  CUSTOMER_FAV_LINE_NBR       NUMBER(8) NOT NULL ,                                                                                                                
  CATALOG_ITEM_ID             NUMBER(18) NOT NULL ,
  ACTV_IND                    VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT              TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CUSTOMER_FAV_DETAIL_T                                                                                                                       
  ADD CONSTRAINT  MM_CUSTOMER_FAV_DETAIL_TP1 PRIMARY KEY (CUSTOMER_FAV_DETAIL_ID);                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CUSTOMER_FAV_DETAIL_T                                                                                                                       
ADD CONSTRAINT  MM_CUSTOMER_FAV_DETAIL_TC0 UNIQUE (CUSTOMER_FAV_ID, CUSTOMER_FAV_LINE_NBR);                                                                                                
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CUSTOMER_FAV_HEADER_T                                                                                                                      
(                                                                                                                                                          
  CUSTOMER_FAV_ID             NUMBER(18)  NOT NULL ,                                                                                                             
  OBJ_ID                      VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                     NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  PRNCPL_ID                   VARCHAR2(100) NOT NULL ,                                                                                                             
  CUSTOMER_FAV_NM             VARCHAR2(40) NOT NULL ,                                                                
  CUSTOMER_FAV_SHARE_IND      VARCHAR2(1) NULL ,                                                                                                                
  ACTV_IND                    VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT              TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CUSTOMER_FAV_HEADER_T                                                                                                                       
  ADD CONSTRAINT  MM_CUSTOMER_FAV_HEADER_TP1 PRIMARY KEY (CUSTOMER_FAV_ID);                                                                    
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CUSTOMER_FAV_HEADER_T                                                                                                                       
ADD CONSTRAINT  MM_CUSTOMER_FAV_HEADER_TC0 UNIQUE (PRNCPL_ID, CUSTOMER_FAV_NM);                                                                                                
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CUSTOMER_PROFILE_T                                                                                                                         
(                                                                                                                                                          
  PROFILE_ID           NUMBER(18)   NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  PROFILE_NM           VARCHAR2(40) NOT NULL ,                                                                                                             
  CUSTOMER_PRNCPL_ID   VARCHAR2(100) NOT NULL ,                                                                                                             
  PROFILE_ADDRESS_ID   VARCHAR2(36) NULL ,                                                                                                                  
  PROFILE_EMAIL        VARCHAR2(200) NULL ,                                                                                                                  
  DELIVERY_BUILDING_CD VARCHAR2(4) NULL ,                                                                                                                  
  DELIVERY_BUILDING_RM_NBR VARCHAR2(8) NULL ,                                                                                                              
  BILLING_BUILDING_CD  VARCHAR2(4) NULL ,                                                                                                                  
  CAMPUS_CD            VARCHAR2(2) NULL ,                                                                                                                  
  FIN_COA_CD           VARCHAR2(2) NULL ,                                                                                                             
  ACCOUNT_NBR          VARCHAR2(7) NULL ,                                                                                                             
  SUB_ACCT_NBR         VARCHAR2(5) NULL ,                                                                                                             
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CUSTOMER_PROFILE_T                                                                                                                          
  ADD CONSTRAINT  MM_CUSTOMER_PROFILE_TP1 PRIMARY KEY (PROFILE_ID);                                                                            
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CUSTOMER_PROFILE_T                                                                                                                          
ADD CONSTRAINT  MM_CUSTOMER_PROFILE_TC0 UNIQUE (CUSTOMER_PRNCPL_ID, PROFILE_NM);                                                                                                   
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CUSTOMER_T                                                                                                                                 
(                                                                                                                                                          
  PRNCPL_ID            VARCHAR2(100) NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  DEFAULT_PROFILE_ID   NUMBER(18) NULL ,                                                                                                                   
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CUSTOMER_T                                                                                                                                  
  ADD CONSTRAINT  MM_CUSTOMER_TP1 PRIMARY KEY (PRNCPL_ID);                                                                                               
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CUSTOMER_T                                                                                                                                  
ADD CONSTRAINT  MM_CUSTOMER_TC0 UNIQUE (OBJ_ID);                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CYCLE_COUNT_T                                                                                                                                    
(                                                                                                                                                          
  CYCLE_CNT_CD         VARCHAR2(1) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CYCLE_CNT_DESC       VARCHAR2(80) NULL ,                                                                                                                 
  TIMES_PER_YEAR_NBR   NUMBER(8)  NOT NULL ,                                                                                                    
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CYCLE_COUNT_T                                                                                                                                     
  ADD CONSTRAINT  MM_CYCLE_COUNT_TP1 PRIMARY KEY (CYCLE_CNT_CD);                                                                                                 
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CYCLE_COUNT_T                                                                                                                                     
ADD CONSTRAINT  MM_CYCLE_COUNT_TC0 UNIQUE (OBJ_ID);                                                                                                              
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CYLINDER_T                                                                                                                             
(                                                                                                                                                          
  CYLINDER_ID            NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID                 VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  STOCK_ID               NUMBER(18) NOT NULL ,
  CYLINDER_SERIAL_NBR    VARCHAR2(80) NOT NULL ,
  CYLINDER_GAS_CD        VARCHAR2(2) NOT NULL ,                                                                                                              
  CHECKIN_DOC_NBR        VARCHAR2(14) NOT NULL ,
  ORDER_DOC_NBR          VARCHAR2(14) NULL ,
  ISSUED_DT              DATE NULL ,
  LAST_CHARGE_DT         DATE NULL ,
  RETURN_DT              DATE NULL ,
  ACTV_IND               VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT         TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CYLINDER_T                                                                                                                              
  ADD CONSTRAINT  MM_CYLINDER_TP1 PRIMARY KEY (CYLINDER_ID);                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CYLINDER_T
  ADD CONSTRAINT  MM_CYLINDER_TC0 UNIQUE (STOCK_ID, CYLINDER_SERIAL_NBR);                                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_CYLINDER_GAS_T                                                                                                                             
(                                                                                                                                                          
  CYLINDER_GAS_CD      VARCHAR2(2) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CYLINDER_GAS_DESC    VARCHAR2(80) NULL ,
  DAILY_DEMURAGE_PRC   DECIMAL(18,4) NULL ,
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CYLINDER_GAS_T                                                                                                                              
  ADD CONSTRAINT  MM_CYLINDER_GAS_TP1 PRIMARY KEY (CYLINDER_GAS_CD);                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_CYLINDER_GAS_T                                                                                                                              
ADD CONSTRAINT  MM_CYLINDER_GAS_TC0 UNIQUE (OBJ_ID);                                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_DELIVERY_REASON_T                                                                                                                          
(                                                                                                                                                          
  DELIVERY_REASON_CD   VARCHAR2(4) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_DELIVERY_REASON_T                                                                                                                           
  ADD CONSTRAINT  MM_DELIVERY_REASON_TP1 PRIMARY KEY (DELIVERY_REASON_CD);                                                                                 
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_DELIVERY_REASON_T                                                                                                                           
ADD CONSTRAINT  MM_DELIVERY_REASON_TC0 UNIQUE (OBJ_ID);                                                                                                    
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_DOT_HAZARDOUS_T                                                                                                                            
(                                                                                                                                                          
  DOT_HAZARDOUS_CD     VARCHAR2(5) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  DRIVER_MANIFEST_CD   VARCHAR2(2) NULL ,                                                                                                                  
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_DOT_HAZARDOUS_T                                                                                                                             
  ADD CONSTRAINT  MM_DOT_HAZARDOUS_TP1 PRIMARY KEY (DOT_HAZARDOUS_CD);                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_DOT_HAZARDOUS_T                                                                                                                             
ADD CONSTRAINT  MM_DOT_HAZARDOUS_TC0 UNIQUE (OBJ_ID);                                                                                                      
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_DRIVER_MANIFEST_T                                                                                                                          
(                                                                                                                                                          
  DRIVER_MANIFEST_CD   VARCHAR2(2) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_DRIVER_MANIFEST_T                                                                                                                           
  ADD CONSTRAINT  MM_DRIVER_MANIFEST_TP1 PRIMARY KEY (DRIVER_MANIFEST_CD);                                                                                 
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_DRIVER_MANIFEST_T                                                                                                                           
ADD CONSTRAINT  MM_DRIVER_MANIFEST_TC0 UNIQUE (OBJ_ID);                                                                                                    
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_EHS_CONTAINER_T                                                                                                                            
(                                                                                                                                                          
  EHS_CONTAINER_CD     VARCHAR2(6) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_EHS_CONTAINER_T                                                                                                                             
  ADD CONSTRAINT  MM_EHS_CONTAINER_TP1 PRIMARY KEY (EHS_CONTAINER_CD);                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_EHS_CONTAINER_T                                                                                                                             
ADD CONSTRAINT  MM_EHS_CONTAINER_TC0 UNIQUE (OBJ_ID);                                                                                                      
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_EHS_HAZARDOUS_STATE_T                                                                                                                      
(                                                                                                                                                          
  EHS_HAZARDOUS_STATE_CD VARCHAR2(1) NOT NULL ,                                                                                                            
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                             
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_EHS_HAZARDOUS_STATE_T                                                                                                                       
  ADD CONSTRAINT  MM_EHS_HAZARDOUS_STATE_TP1 PRIMARY KEY (EHS_HAZARDOUS_STATE_CD);                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_EHS_HAZARDOUS_STATE_T                                                                                                                       
ADD CONSTRAINT  MM_EHS_HAZARDOUS_STATE_TC0 UNIQUE (OBJ_ID);                                                                                                
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_EHS_HAZARDOUS_T                                                                                                                            
(                                                                                                                                                          
  EHS_HAZARDOUS_CD     VARCHAR2(3) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_EHS_HAZARDOUS_T                                                                                                                             
  ADD CONSTRAINT  MM_EHS_HAZARDOUS_TP1 PRIMARY KEY (EHS_HAZARDOUS_CD);                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_EHS_HAZARDOUS_T                                                                                                                             
ADD CONSTRAINT  MM_EHS_HAZARDOUS_TC0 UNIQUE (OBJ_ID);                                                                                                      
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_HAZARDOUS_MATERIEL_T                                                                                                                       
(                                                                                                                                                          
  HAZARDOUS_MATERIEL_ID      NUMBER(18)   NOT NULL ,                                                                                        
  OBJ_ID                     VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                    NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  HAZARDOUS_MATERIEL_CD      VARCHAR2(10) NOT NULL ,                                                                                                            
  HAZARDOUS_MATERIEL_DESC    VARCHAR2(80) NULL ,                                                                                                              
  STOCK_ID                   NUMBER(18) NOT NULL ,                                                                                                             
  HAZARDOUS_UN_CD            VARCHAR2(12) NULL ,                                                                                                                 
  HAZARDOUS_STATE_CD         VARCHAR2(10) NULL ,                                                                                                                 
  EHS_HAZARDOUS_CD           VARCHAR2(3) NULL ,                                                                                                                  
  EHS_CONTAINER_CD           VARCHAR2(6) NULL ,                                                                                                                  
  EHS_CAS_NBR                VARCHAR2(20) NULL ,                                                                                                            
  EHS_HAZARDOUS_STATE_CD     VARCHAR2(1) NULL ,                                                                                                                
  EHS_UNIT_OF_ISSUE_CD       VARCHAR2(10) NULL ,                                                                                                                 
  EHS_CONVERSION_UNIT_FACTOR NUMBER(4,3) NULL ,                                                                                                            
  DOT_HAZARDOUS_CD           VARCHAR2(5) NULL ,                                                                                                                  
  TYPE_OF_USE                VARCHAR2(10) NULL ,                                                                                                                 
  HAZARDOUS_PRESSURE         NUMBER(10,3) NULL ,                                                                                                                 
  HAZARDOUS_TEMPERATURE      NUMBER(10,3) NULL ,                                                                                                                
  ACTV_IND                   VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT             TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_MATERIEL_T                                                                                                                        
  ADD CONSTRAINT  MM_HAZARDOUS_MATERIEL_TP1 PRIMARY KEY (HAZARDOUS_MATERIEL_ID);                                                   
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_MATERIEL_T                                                                                                                        
ADD CONSTRAINT  MM_HAZARDOUS_MATERIEL_TC0 UNIQUE (STOCK_ID, HAZARDOUS_MATERIEL_CD);                                                                                                 
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_HAZARDOUS_STATE_T                                                                                                                          
(                                                                                                                                                          
  HAZARDOUS_STATE_CD   VARCHAR2(10) NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_STATE_T                                                                                                                           
  ADD CONSTRAINT  MM_HAZARDOUS_STATE_TP1 PRIMARY KEY (HAZARDOUS_STATE_CD);                                                                                 
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_STATE_T                                                                                                                           
ADD CONSTRAINT  MM_HAZARDOUS_STATE_TC0 UNIQUE (OBJ_ID);                                                                                                    
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_HAZARDOUS_UN_T                                                                                                                             
(                                                                                                                                                          
  HAZARDOUS_UN_CD       VARCHAR2(10) NOT NULL ,                                                                                                             
  OBJ_ID                VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR               NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  HAZARDOUS_UN_DESC     VARCHAR2(255) NULL ,                                                                                                                
  HAZARDOUS_UN_STD_UNIT VARCHAR2(5) NULL ,                                                                                                                 
  ACTV_IND              VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_UN_T                                                                                                                              
  ADD CONSTRAINT  MM_HAZARDOUS_UN_TP1 PRIMARY KEY (HAZARDOUS_UN_CD);                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_UN_T                                                                                                                              
ADD CONSTRAINT  MM_HAZARDOUS_UN_TC0 UNIQUE (OBJ_ID);                                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_FURNITURE_T                                                                                                                                
(                                                                                                                                                          
  FURNITURE_ID               NUMBER(18)   NOT NULL ,
  OBJ_ID                     VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                    NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  DESIGN_NBR                 VARCHAR2(10) NULL ,                                                                                                                 
  SHOP_CART_DOC_NBR          VARCHAR2(14) NULL ,                                                                                                             
  ACTV_IND                   VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT             TIMESTAMP NOT NULL 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_FURNITURE_T                                                                                                                                 
  ADD CONSTRAINT  MM_FURNITURE_TP1 PRIMARY KEY (FURNITURE_ID);
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_FURNITURE_T                                                                                                                                 
ADD CONSTRAINT  MM_FURNITURE_TC0 UNIQUE (OBJ_ID);                                                                                                          



CREATE TABLE MM_MARKUP_T                                                                                                                                   
(                                                                                                                                                          
  MARKUP_CD            VARCHAR2(12) NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  MARKUP_NM            VARCHAR2(40) NULL ,                                                                                                                 
  MARKUP_DESC          VARCHAR2(200) NULL ,                                                                                                                
  MARKUP_RT            NUMBER(8,2) NULL ,                                                                                                                  
  WAREHOUSE_CD         VARCHAR2(2) NULL ,                                                                                                                  
  MARKUP_COA_CD        VARCHAR2(2) NULL ,                                                                                                                 
  MARKUP_ORG_CD        VARCHAR2(4) NULL ,                                                                                                                 
  MARKUP_ACCOUNT_NBR   VARCHAR2(7) NULL ,                                                                                                                 
  MARKUP_FIXED_AMT     NUMBER(8,2) NULL ,                                                                                                                  
  CATALOG_DETAIL_ID    NUMBER(8) NULL ,                                                                                                                    
  MARKUP_BEGIN_DT      DATE NULL ,                                                                                                                         
  MARKUP_END_DT        DATE NULL ,                                                                                                                         
  MARKUP_TYPE_CD       VARCHAR2(2) NULL ,                                                                                                                  
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_MARKUP_T                                                                                                                                    
  ADD CONSTRAINT  MM_MARKUP_TP1 PRIMARY KEY (MARKUP_CD);                                                                                                   
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_MARKUP_T                                                                                                                                    
ADD CONSTRAINT  MM_MARKUP_TC0 UNIQUE (OBJ_ID);                                                                                                             
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_MARKUP_TYPE_T                                                                                                                              
(                                                                                                                                                          
  MARKUP_TYPE_CD       VARCHAR2(2) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_MARKUP_TYPE_T                                                                                                                               
  ADD CONSTRAINT  MM_MARKUP_TYPE_TP1 PRIMARY KEY (MARKUP_TYPE_CD);                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_MARKUP_TYPE_T                                                                                                                               
ADD CONSTRAINT  MM_MARKUP_TYPE_TC0 UNIQUE (OBJ_ID);                                                                                                        
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_ORDER_DETAIL_T                                                                                                                             
(                                                                                                                                                          
  ORDER_DETAIL_ID       VARCHAR2(14)  NOT NULL ,                                                                                        
  OBJ_ID                     VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                    NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  ORDER_DOC_NBR              VARCHAR2(14) NOT NULL ,                                                                                                                
  ORDER_LINE_NBR             NUMBER(8) NOT NULL ,                                                                                                                
  ORDER_ID                   NUMBER(18) NOT NULL ,                                                                                                                
  ORDER_DETAIL_STATUS_CD     VARCHAR2(4) NOT NULL ,                                                                                                              
  SHOP_CART_DETAIL_ID   VARCHAR2(14) NOT NULL ,                                                                                                             
  CATALOG_ITEM_ID            NUMBER(18) NOT NULL ,                                                                                                             
  ORDER_ITEM_QTY             NUMBER(11,4) NOT NULL ,                                                                                                             
  STOCK_UNIT_OF_ISSUE_CD     VARCHAR2(10) NOT NULL ,                                                                                                            
  ORDER_ITEM_COST_AMT        NUMBER(19,4) NOT NULL ,                                                                                                             
  ORDER_ITEM_PRICE_AMT       NUMBER(19,4) NOT NULL ,                                                                                                             
  ORDER_ITEM_MARKUP_AMT      NUMBER(19,4) NOT NULL ,                                                                                                             
  ORDER_ITEM_TAX_AMT         NUMBER(19,4) NOT NULL ,                                                                                                             
  ORDER_ITEM_DETAIL_DESC     VARCHAR2(400) NOT NULL ,                                                                                                            
  MANUFACTURER_NBR           VARCHAR2(30) NOT NULL ,                                                                                                             
  SHIPPING_WGT               NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_HT                NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_WD                NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_LH                NUMBER(8,2) NULL ,                                                                                                                  
  WILLCALL_IND               VARCHAR2(1) NULL ,                                                                                                                  
  VENDOR_HEADER_GENERATED_ID NUMBER(10) NULL ,                                                                                                             
  VENDOR_DETAIL_ASSIGNED_ID  NUMBER(10) NULL ,                                                                                                              
  VENDOR_NM                  VARCHAR2(45) NULL ,                                                                                                                 
  PO_ID                      NUMBER(9) NULL ,                                                                                                                    
  LAST_UPDATE_DT             TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ORDER_DETAIL_T                                                                                                                              
  ADD CONSTRAINT  MM_ORDER_DETAIL_TP1 PRIMARY KEY (ORDER_DETAIL_ID);                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ORDER_DETAIL_T                                                                                                                              
ADD CONSTRAINT  MM_ORDER_DETAIL_TC0 UNIQUE (ORDER_ID, ORDER_LINE_NBR);                                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_ORDER_DOC_T                                                                                                                             
(                                                                                                                                                          
  FDOC_NBR                   VARCHAR2(14) NOT NULL ,                                                                                                             
  OBJ_ID                     VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                    NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  ORDER_ID                   NUMBER(8) NOT NULL ,                                                                                                                
  SHOP_CART_DOC_NBR          VARCHAR2(14) NOT NULL ,                                                                                                             
  ORDER_TYPE_CD              VARCHAR2(6) NOT NULL ,                                                                                                              
  RECURRING_ORDER_IND        VARCHAR2(1) NULL ,                                                                                                                  
  RECURRING_ORDER_ID         NUMBER(18) NOT NULL ,                                                                                                                
  VENDOR_HEADER_GENERATED_ID NUMBER(10) NULL ,                                                                                                             
  VENDOR_DETAIL_ASSIGNED_ID  NUMBER(10) NULL ,                                                                                                              
  VENDOR_NM                  VARCHAR2(45) NULL ,                                                                                                                 
  ORDER_DOC_PRNCPL_ID        VARCHAR2(100) NOT NULL ,                                                                                                              
  ORDER_DOC_STATUS_CD        VARCHAR2(4) NOT NULL ,                                                                                                              
  WAREHOUSE_CD               VARCHAR2(2) NOT NULL ,                                                                                                              
  CAMPUS_CD                  VARCHAR2(2) NOT NULL ,                                                                                                              
  DELIVERY_BUILDING_CD       VARCHAR2(4) NULL ,                                                                                                                  
  DELIVERY_BUILDING_RM_NBR   VARCHAR2(8) NULL ,                                                                                                              
  DELIVERY_DEPARTMENT_NM     VARCHAR2(45) NULL ,                                                                                                               
  DELIVERY_INSTRUCTION_TXT   VARCHAR2(255) NULL ,                                                                                                            
  BILLING_ADDRESS_ID         VARCHAR2(36) NULL ,                                                                                                                  
  SHIPPING_ADDRESS_ID        VARCHAR2(36) NULL ,                                                                                                                  
  REQS_ID                    NUMBER(8) NULL ,                                                                                                                    
  AR_ID                      NUMBER(8) NULL ,                                                                                                                    
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ORDER_DOC_T                                                                                                                              
  ADD CONSTRAINT  MM_ORDER_DOC_TP1 PRIMARY KEY (FDOC_NBR);                                                                                              
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ORDER_DOC_T                                                                                                                              
ADD CONSTRAINT  MM_ORDER_DOC_TC0 UNIQUE (ORDER_ID);                                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_ORDER_STATUS_T                                                                                                                               
(                                                                                                                                                          
  ORDER_STATUS_CD      VARCHAR2(4) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ORDER_STATUS_T                                                                                                                                
  ADD CONSTRAINT  MM_ORDER_STATUS_TP1 PRIMARY KEY (ORDER_STATUS_CD);                                                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ORDER_STATUS_T                                                                                                                                
ADD CONSTRAINT  MM_ORDER_STATUS_TC0 UNIQUE (OBJ_ID);                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_ORDER_TYPE_T                                                                                                                               
(                                                                                                                                                          
  ORDER_TYPE_CD        VARCHAR2(6) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ORDER_TYPE_T                                                                                                                                
  ADD CONSTRAINT  MM_ORDER_TYPE_TP1 PRIMARY KEY (ORDER_TYPE_CD);                                                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ORDER_TYPE_T                                                                                                                                
ADD CONSTRAINT  MM_ORDER_TYPE_TC0 UNIQUE (OBJ_ID);                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_PACK_LIST_ANNOUNCEMENT_T                                                                                                                   
(                                                                                                                                                          
  PACK_LIST_ANNOUNCEMENT_CD   VARCHAR2(8) NOT NULL ,                                                                                                         
  OBJ_ID                      VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                     NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  PACK_LIST_ANNOUNCEMENT_DESC VARCHAR2(200) NULL ,                                                                                                         
  ACTV_IND                    VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PACK_LIST_ANNOUNCEMENT_T                                                                                                                    
  ADD CONSTRAINT  MM_PACK_LIST_ANNOUNCEMENT_TP1 PRIMARY KEY (PACK_LIST_ANNOUNCEMENT_CD);                                                                   
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PACK_LIST_ANNOUNCEMENT_T                                                                                                                    
ADD CONSTRAINT  MM_PACK_LIST_ANNOUNCEMENT_TC0 UNIQUE (OBJ_ID);                                                                                             
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_PACK_LIST_DOC_T
(                                                                                                                                                          
  FDOC_NBR             VARCHAR2(14) NOT NULL ,
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL , 
  PACK_STATUS_CD       VARCHAR2(4) NOT NULL ,
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         



ALTER TABLE MM_PACK_LIST_DOC_T                                                                                                                                 
  ADD CONSTRAINT  MM_PACK_LIST_DOC_TP1 PRIMARY KEY (FDOC_NBR);
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PACK_LIST_DOC_T                                                                                                                                 
ADD CONSTRAINT  MM_PACK_LIST_DOC_TC0 UNIQUE (OBJ_ID);                                                                                                          



CREATE TABLE MM_PACK_LIST_LINE_T
(                                                                                                                                                          
  PACK_LIST_LINE_ID         NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID                    VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                   NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  PACK_LIST_LINE_NBR        NUMBER(8) NOT NULL ,                                                                                                                
  PACK_LIST_DOC_NBR         VARCHAR2(14) NOT NULL ,
  PICK_LIST_LINE_ID         NUMBER(18) NOT NULL ,
  ROUTE_CD                  VARCHAR2(2) NOT NULL ,                                                                                                              
  DELIVERY_CAMPUS_CD        VARCHAR2(2) NOT NULL ,                                                                                                              
  DELIVERY_BUILDING_CD      VARCHAR2(4) NOT NULL ,                                                                                                              
  DELIVERY_BUILDING_RM_NBR  VARCHAR2(8) NULL ,                                                                                                              
  DELIVERY_BUILDING_NM      VARCHAR2(45) NULL ,                                                                                                                 
  DELIVERY_DEPARTMENT_NM    VARCHAR2(45) NULL ,                                                                                                               
  DELIVERY_INSTRUCTION_TXT  VARCHAR2(255) NULL ,                                                                                                            
  STOP_CD                   VARCHAR2(2) NOT NULL ,                                                                                                              
  NUMBER_OF_CARTONS         NUMBER(8) NOT NULL ,                                                                                                                
  DRIVER_LOG_NBR            NUMBER(8) NOT NULL ,                                                                                                                
  DRIVER_PRNCPL_ID          VARCHAR2(100) NULL ,                                                                                                                 
  DELIVERY_REASON_CD        VARCHAR2(4) NOT NULL ,                                                                                                              
  DELIVERY_DT               DATE NULL ,                                                                                                                         
  PACK_PRNCPL_ID            VARCHAR2(100) NULL ,                                                                                                                 
  PACK_DT                   DATE NULL ,                                                                                                                         
  VERIFY_PRNCPL_ID          VARCHAR2(100) NULL ,                                                                                                                 
  DEPARTMENT_RECEIVED_BY_NM VARCHAR2(45) NULL ,                                                                                                            
  PACK_STATUS_CD            VARCHAR2(4) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT            TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PACK_LIST_LINE_T                                                                                                                                 
  ADD CONSTRAINT  MM_PACK_LIST_LINE_TP1 PRIMARY KEY (PACK_LIST_LINE_ID);
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PACK_LIST_LINE_T                                                                                                                                 
ADD CONSTRAINT  MM_PACK_LIST_LINE_TC0 UNIQUE (PACK_LIST_DOC_NBR, PACK_LIST_LINE_NBR);                                                                                                          
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_PACK_STATUS_CODE_T
(                                                                                                                                                          
  PACK_STATUS_CD       VARCHAR2(4) NOT NULL ,
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL,
  NM                   VARCHAR2(45) NOT NULL ,
  ACTV_IND             VARCHAR2(1) NOT NULL , 
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         



ALTER TABLE MM_PACK_STATUS_CODE_T                                                                                                                                 
  ADD CONSTRAINT  MM_PACK_STATUS_CODE_TP1 PRIMARY KEY (PACK_STATUS_CD);
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PACK_STATUS_CODE_T                                                                                                                                 
ADD CONSTRAINT  MM_PACK_STATUS_CODE_TC0 UNIQUE (OBJ_ID);                                                                                                          



CREATE TABLE MM_PICK_LIST_DOC_T                                                                                                                                
(                                                                                                                                                          
  FDOC_NBR             VARCHAR2(14)  NOT NULL ,
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  WAREHOUSE_CD         VARCHAR2(2) NOT NULL ,                                                                                                              
  PICK_STATUS_CD       VARCHAR2(4) NOT NULL ,
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PICK_LIST_DOC_T                                                                                                                                 
  ADD CONSTRAINT  MM_PICK_LIST_DOC_TP1 PRIMARY KEY (FDOC_NBR);                                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PICK_LIST_DOC_T                                                                                                                                 
ADD CONSTRAINT  MM_PICK_LIST_DOC_TC0 UNIQUE (OBJ_ID);                                                                                                          
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_PICK_LIST_LINE_T                                                                                                                                
(                                                                                                                                                          
  PICK_LIST_LINE_ID         NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID                    VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                   NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  PICK_LIST_DOC_NBR         VARCHAR2(14) NULL ,
  PICK_LIST_LINE_NBR        NUMBER(8) NOT NULL ,                                                                                                                
  PICK_TICKET_NBR           NUMBER(18)  NULL ,
  PICK_TUB_NBR              NUMBER(8) NULL ,                                                                                                                    
  ORDER_ID                  NUMBER(8) NOT NULL ,                                                                                                                
  ORDER_DETAIL_ID      VARCHAR2(14) NOT NULL ,
  BIN_ID                    NUMBER(18) NULL ,                                                                                                                  
  STOCK_ID                  NUMBER(18) NOT NULL ,                                                                                                             
  STOCK_QTY                 NUMBER(11,4) NOT NULL ,                                                                                                             
  PICK_STOCK_QTY            NUMBER(11,4) NOT NULL ,                                                                                                             
  BACK_ORDER_QTY            NUMBER(11,4) NOT NULL ,                                                                                                             
  BACK_ORDER_ID             NUMBER(18) NULL ,
  PICK_STATUS_CD            VARCHAR2(4) NULL ,                                                                                                                  
  ROUTE_CD                  VARCHAR2(2) NULL ,                                                                                                                  
  PICK_LIST_LINE_CREATED_DT TIMESTAMP NOT NULL ,
  LAST_UPDATE_DT            TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PICK_LIST_LINE_T                                                                                                                                 
  ADD CONSTRAINT  MM_PICK_LIST_LINE_TP1 PRIMARY KEY (PICK_LIST_LINE_ID);                                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PICK_LIST_LINE_T                                                                                                                                 
ADD CONSTRAINT  MM_PICK_LIST_LINE_TC0 UNIQUE (PICK_LIST_DOC_NBR, PICK_LIST_LINE_NBR);                                                                                                          
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_PICK_STATUS_CODE_T
(                                                                                                                                                          
  PICK_STATUS_CD       VARCHAR2(4) NOT NULL ,
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL,
  NM                   VARCHAR2(45) NOT NULL ,
  ACTV_IND             VARCHAR2(1) NOT NULL , 
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         



ALTER TABLE MM_PICK_STATUS_CODE_T                                                                                                                                 
  ADD CONSTRAINT  MM_PICK_STATUS_CODE_TP1 PRIMARY KEY (PICK_STATUS_CD);
  

ALTER TABLE MM_PICK_STATUS_CODE_T                                                                                                                                 
ADD CONSTRAINT  MM_PICK_STATUS_CODE_TC0 UNIQUE (OBJ_ID);                                                                                                          

CREATE TABLE MM_PICK_TICKET_T 
(
  PICK_TICKET_NBR     NUMBER(18)  NOT NULL ,
  OBJ_ID              VARCHAR2(36) DEFAULT sys_guid() NOT NULL,
  VER_NBR             NUMBER(8)   DEFAULT 1 NOT NULL,
  NM                  VARCHAR2(45) NULL,
  PICK_LIST_DOC_NBR   VARCHAR2(14) NOT NULL,
  PICK_STATUS_CD      VARCHAR2(4) NOT NULL,
  FILE_NM             VARCHAR2(255) NULL,
  LAST_UPDATE_DT      TIMESTAMP(6) NOT NULL
);

ALTER TABLE MM_PICK_TICKET_T                                                                                                                                 
  ADD CONSTRAINT  MM_PICK_TICKET_TP1 PRIMARY KEY (PICK_TICKET_NBR);
  
ALTER TABLE MM_PICK_TICKET_T                                                                                                                                 
ADD CONSTRAINT  MM_PICK_TICKET_TC0 UNIQUE (OBJ_ID);                                                                                                          


CREATE TABLE MM_PRICE_CODE_T                                                                                                                                  
(                                                                                                                                                          
  PRICE_CD             VARCHAR2(2) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  PRICE_CODE_FORMULA   VARCHAR2(255) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PRICE_CODE_T                                                                                                                                   
  ADD CONSTRAINT  MM_PRICE_CODE_TP1 PRIMARY KEY (PRICE_CD);
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_PRICE_CODE_T                                                                                                                                   
ADD CONSTRAINT  MM_PRICE_CODE_TC0 UNIQUE (OBJ_ID);                                                                                                            
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_RECONCILIATION_T                                                                                                                           
(                                                                                                                                                          
  TRANSACTION_ID        NUMBER(18)  NOT NULL ,                                                                                                        
  OBJ_ID                VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR               NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  WAREHOUSE_CD          VARCHAR2(2) NULL ,                                                                                                              
  ORDER_DOC_NBR         VARCHAR2(14) NOT NULL ,                                                                                                                
  ORDER_LINE_NBR        NUMBER(8) NOT NULL ,                                                                                                                
  CATALOG_ITEM_ID       NUMBER(18) NULL ,                                                                                                             
  ITEM_UNIT_OF_ISSUE_CD VARCHAR2(10) NULL ,                                                                                                            
  ITEM_DETAIL_DESC      VARCHAR2(400) NULL ,                                                                                                            
  SHIPPED_ITEM_QTY      NUMBER(11,4) NULL ,                                                                                                             
  ITEM_PRC              NUMBER(19,4) NULL ,                                                                                                             
  ITEM_TAX_AMT          NUMBER(19,4) NULL ,                                                                                                             
  ITEM_TOTAL_AMT        NUMBER(19,4) NULL ,                                                                                                             
  RECEIVED_CD           VARCHAR2(1 BYTE) NULL ,                                                                                                             
  RECEIVED_COMMENTS     VARCHAR2(300 BYTE) NULL ,                                                                                                           
  CUSTOMER_ID           VARCHAR2(12) NULL ,                                                                                                                 
  CHECKIN_DT            DATE NULL ,                                                                                                                         
  SHIP_DT               DATE NULL ,                                                                                                                         
  MATCH_IND             VARCHAR2(1 BYTE) NULL ,                                                                                                             
  GL_PROCESS_IND        VARCHAR2(1 BYTE) NULL ,                                                                                                             
  INVOICE_NBR           VARCHAR2(15 BYTE) NULL ,                                                                                                            
  LAST_UPDATE_DT        TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RECONCILIATION_T                                                                                                                            
  ADD CONSTRAINT  MM_RECONCILIATION_TP1 PRIMARY KEY (TRANSACTION_ID);                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RECONCILIATION_T                                                                                                                            
ADD CONSTRAINT  MM_RECONCILIATION_TC0 UNIQUE (OBJ_ID);                                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_RECURRING_ORDER_T                                                                                                                          
(                                                                                                                                                          
  RECURRING_ORDER_ID   NUMBER(18)  NOT NULL ,                                                                                                                
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  TIMES_PER_YR         NUMBER(8) NULL ,                                                                                                                    
  NO_END_DATE_IND      VARCHAR2(1) NOT NULL ,                                                                                                              
  START_DT             DATE NOT NULL ,                                                                                                                     
  END_DT               DATE NULL ,                                                                                                                         
  LAST_RECURRING_DT    DATE NULL ,                                                                                                                         
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RECURRING_ORDER_T                                                                                                                           
  ADD CONSTRAINT  MM_RECURRING_ORDER_TP1 PRIMARY KEY (RECURRING_ORDER_ID);                                                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RECURRING_ORDER_T                                                                                                                           
ADD CONSTRAINT  MM_RECURRING_ORDER_TC0 UNIQUE (OBJ_ID);                                                                                                    
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_RENTAL_T                                                                                                                             
(                                                                                                                                                          
  RENTAL_ID              NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID                 VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  STOCK_ID               NUMBER(18) NOT NULL ,
  RENTAL_NBR             VARCHAR2(80) NOT NULL ,
  CHECKIN_DOC_NBR        VARCHAR2(14) NOT NULL ,
  ORDER_DOC_NBR          VARCHAR2(14) NULL ,
  ISSUED_DT              DATE NULL ,
  LAST_CHARGE_DT         DATE NULL ,
  RETURN_DT              DATE NULL ,
  ACTV_IND               VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT         TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RENTAL_T                                                                                                                              
  ADD CONSTRAINT  MM_RENTAL_TP1 PRIMARY KEY (RENTAL_ID);                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RENTAL_T
  ADD CONSTRAINT  MM_RENTAL_TC0 UNIQUE (STOCK_ID, RENTAL_NBR);                                                                                                       
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_RESTRICTED_ROUTE_CODE_T
(                                                                                                                                                          
  RESTRICTED_ROUTE_CD  VARCHAR2(2) NOT NULL ,
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL,
  NM                   VARCHAR2(45) NOT NULL ,
  ACTV_IND             VARCHAR2(1) NOT NULL , 
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         



ALTER TABLE MM_RESTRICTED_ROUTE_CODE_T                                                                                                                                 
  ADD CONSTRAINT  MM_RESTRICTED_ROUTE_CODE_TP1 PRIMARY KEY (RESTRICTED_ROUTE_CD);
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RESTRICTED_ROUTE_CODE_T                                                                                                                                 
ADD CONSTRAINT  MM_RESTRICTED_ROUTE_CODE_TC0 UNIQUE (OBJ_ID);                                                                                                          



CREATE TABLE MM_RETURN_DETAIL_T                                                                                                                            
(                                                                                                                                                          
  RETURN_DETAIL_ID           NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID                     VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                    NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  RETURN_DOC_NBR             VARCHAR2(14) NOT NULL ,                                                                                                                
  RETURN_LINE_NBR            NUMBER(8) NOT NULL ,                                                                                                                
  CATALOG_ITEM_ID            NUMBER(18) NOT NULL ,                                                                                                             
  RETURN_QTY                 NUMBER(11,4) NOT NULL ,                                                                                                             
  RETURN_UNIT_OF_ISSUE_CD    VARCHAR2(10) NOT NULL ,                                                                                                            
  RETURN_ITEM_PRC            NUMBER(19,4) NOT NULL ,                                                                                                             
  RETURN_ITEM_DETAIL_DESC    VARCHAR2(400) NOT NULL ,                                                                                                            
  RETURN_PCT                 NUMBER(8) NOT NULL ,                                                                                                                
  RETURN_CREDIT_AMT          NUMBER(19,4) NOT NULL ,                                                                                                             
  VENDOR_HEADER_GENERATED_ID NUMBER(10) NULL ,                                                                                                             
  VENDOR_DETAIL_ASSIGNED_ID  NUMBER(10) NULL ,                                                                                                              
  VENDOR_NM                  VARCHAR2(45) NULL ,                                                                                                                 
  ORDER_DOC_NBR              NUMBER(8) NULL ,                                                                                                                    
  ORDER_LINE_NBR             NUMBER(8) NULL ,                                                                                                                    
  RETURN_DETAIL_STATUS_CD    VARCHAR2(4) NOT NULL ,                                                                                                              
  RETURN_TYPE_CD             VARCHAR2(8) NULL ,                                                                                                                  
  VENDOR_CREDIT_IND          VARCHAR2(1) NULL ,                                                                                                                  
  VENDOR_RESHIP_IND          VARCHAR2(1) NULL ,                                                                                                                  
  VENDOR_DISPOSITION_IND     VARCHAR2(1) NULL ,                                                                                                                
  ACTION_CD                  VARCHAR2(8) NULL ,                                                                                                                  
  REQS_ID                    NUMBER(8) NULL ,                                                                                                                    
  PO_ID                      NUMBER(9) NULL ,                                                                                                                    
  LAST_UPDATE_DT             TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RETURN_DETAIL_T                                                                                                                             
  ADD CONSTRAINT  MM_RETURN_DETAIL_TP1 PRIMARY KEY (RETURN_DETAIL_ID);                                                                             
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RETURN_DETAIL_T                                                                                                                             
ADD CONSTRAINT  MM_RETURN_DETAIL_TC0 UNIQUE (RETURN_DOC_NBR, RETURN_LINE_NBR);                                                                                                      
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_RETURN_DOC_T                                                                                                                            
(                                                                                                                                                          
  FDOC_NBR                   VARCHAR2(14)  NOT NULL ,                                                                                                             
  OBJ_ID                     VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                    NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  RETURN_ORDER_ID            VARCHAR2(14) NOT NULL ,                                                                                                             
  RETURN_DOC_STATUS_CD       VARCHAR2(4) NOT NULL ,                                                                                                              
  VENDOR_HEADER_GENERATED_ID NUMBER(10) NULL ,                                                                                                             
  VENDOR_DETAIL_ASSIGNED_ID  NUMBER(10) NULL ,                                                                                                              
  VENDOR_NM                  VARCHAR2(45) NULL ,                                                                                                                 
  ORDER_ID                   NUMBER(8) NULL ,                                                                                                                    
  RETRIEVAL_BUILDING_CD      VARCHAR2(4) NULL ,                                                                                                                 
  RETRIEVAL_BUILDING_RM_NBR  VARCHAR2(8) NULL ,                                                                                                             
  RETRIEVAL_INSTRUCTION_TXT  VARCHAR2(255) NULL ,                                                                                                           
  BILLING_BUILDING_CD        VARCHAR2(4) NULL ,                                                                                                                  
  RETURN_TYPE_CD             VARCHAR2(4) NOT NULL ,                                                                                                              
  VENDOR_CREDIT_IND          VARCHAR2(1) NULL ,                                                                                                                  
  VENDOR_RESHIP_IND          VARCHAR2(1) NULL ,                                                                                                                  
  VENDOR_DISPOSITION_IND     VARCHAR2(1) NULL ,                                                                                                                
  CUSTOMER_PRNCPL_ID         VARCHAR2(100) NOT NULL ,                                                                                                             
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RETURN_DOC_T                                                                                                                             
  ADD CONSTRAINT  MM_RETURN_DOC_TP1 PRIMARY KEY (FDOC_NBR);                                                                                      
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RETURN_DOC_T                                                                                                                             
ADD CONSTRAINT  MM_RETURN_DOC_TC0 UNIQUE (RETURN_ORDER_ID);                                                                                                      
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_RETURN_STATUS_CODE_T                                                                                                                              
(                                                                                                                                                          
  RETURN_STATUS_CD      VARCHAR2(4) NOT NULL ,                                                                                                              
  OBJ_ID                VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR               NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                    VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND              VARCHAR2(1) NOT NULL ,
  LAST_UPDATE_DT        TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RETURN_STATUS_CODE_T                                                                                                                               
  ADD CONSTRAINT  MM_RETURN_STATUS_CODE_TP1 PRIMARY KEY (RETURN_STATUS_CD);                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RETURN_STATUS_CODE_T                                                                                                                               
ADD CONSTRAINT  MM_RETURN_STATUS_CODE_TC0 UNIQUE (OBJ_ID);                                                                                                        
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_RETURN_TYPE_T                                                                                                                              
(                                                                                                                                                          
  RETURN_TYPE_CD       VARCHAR2(8) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RETURN_TYPE_T                                                                                                                               
  ADD CONSTRAINT  MM_RETURN_TYPE_TP1 PRIMARY KEY (RETURN_TYPE_CD);                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_RETURN_TYPE_T                                                                                                                               
ADD CONSTRAINT  MM_RETURN_TYPE_TC0 UNIQUE (OBJ_ID);                                                                                                        
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_ROUTE_T                                                                                                                                    
(                                                                                                                                                          
  ROUTE_CD             VARCHAR2(2) NOT NULL ,                                                                                                         
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                            
  DRIVER_PRNCPL_ID     VARCHAR2(100) NULL ,                                                                                                            
  DRIVER_MANIFEST_CD   VARCHAR2(2) NULL ,                                                                                                                  
  RESTRICTED_ROUTE_CD  VARCHAR2(2) NULL ,                                                                                                                  
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ROUTE_T                                                                                                                                     
  ADD CONSTRAINT  MM_ROUTE_TP1 PRIMARY KEY (ROUTE_CD);                                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ROUTE_T                                                                                                                                     
ADD CONSTRAINT  MM_ROUTE_TC0 UNIQUE (OBJ_ID);                                                                                                              
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_SALES_INSTANCE_T                                                                                                                           
(                                                                                                                                                          
  ORDER_ID              NUMBER(8) NOT NULL ,                                                                                                                
  OBJ_ID                VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR               NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  ORDER_DOC_NBR         VARCHAR2(14) NOT NULL ,                                                                                                                
  ORDER_TYPE_CD         VARCHAR2(6) NOT NULL ,                                                                                                              
  ORDER_SALES_STATUS_CD VARCHAR2(4) NOT NULL ,                                                                                                              
  ORDER_LINE_TOTAL_AMT  NUMBER(19,2) NULL ,                                                                                                                 
  ORDER_TAXABLE_AMT     NUMBER(19,2) NULL ,                                                                                                                 
  ORDER_TOTAL_AMT       NUMBER(19,2) NULL ,                                                                                                                 
  CUSTOMER_PRNCPL_ID    VARCHAR2(100) NOT NULL ,                                                                                                             
  LAST_UPDATE_DT        TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_SALES_INSTANCE_T                                                                                                                            
  ADD CONSTRAINT  MM_SALES_INSTANCE_TP1 PRIMARY KEY (ORDER_ID);                                                                                            
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_SALES_INSTANCE_T                                                                                                                            
ADD CONSTRAINT  MM_SALES_INSTANCE_TC0 UNIQUE (OBJ_ID);                                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_SEARCH_LOG_T                                                                                                                               
(                                                                                                                                                          
  CUSTOMER_PRNCPL_ID     VARCHAR2(100) NOT NULL ,                                                                                                             
  OBJ_ID                 VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  SEARCH_FLD             VARCHAR2(45) NOT NULL ,                                                                                                             
  STOCK_DISTRIBUTION_NBR VARCHAR2(30) NOT NULL ,                                                                                                             
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_SEARCH_LOG_T                                                                                                                                
  ADD CONSTRAINT  MM_SEARCH_LOG_TP1 PRIMARY KEY (CUSTOMER_PRNCPL_ID);                                                                                             
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_SEARCH_LOG_T                                                                                                                                
  ADD CONSTRAINT  MM_SEARCH_LOG_TC0 UNIQUE (OBJ_ID);                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_SHOP_CART_DTL_ADDL_COST_T
(                                                                                                                                                          
  SHOP_CART_DTL_ADDL_COST_ID NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID                     VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                    NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  SHOP_CART_DETAIL_ID   VARCHAR2(14) NOT NULL ,                                                                                                             
  ADDITIONAL_COST_TYPE_CD    VARCHAR2(12) NOT NULL ,                                                                                                          
  ADDITIONAL_CST             NUMBER(8,2) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT             TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_SHOP_CART_DTL_ADDL_COST_T
  ADD CONSTRAINT  MM_SHOP_CART_DTL_ADDL_COST_TP1 PRIMARY KEY (SHOP_CART_DTL_ADDL_COST_ID);

                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_SHOP_CART_DTL_ADDL_COST_T                                                                                                                           
  ADD CONSTRAINT  MM_SHOP_CART_DTL_ADDL_COST_TC0 UNIQUE (SHOP_CART_DETAIL_ID, ADDITIONAL_COST_TYPE_CD);                                                                                                    
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_SHOP_CART_DETAIL_T                                                                                                                         
(                                                                                                                                                          
  SHOP_CART_DETAIL_ID   VARCHAR2(14)  NOT NULL ,                                                                                        
  OBJ_ID                     VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                    NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  SHOP_CART_DOC_NBR          VARCHAR2(14) NOT NULL ,                                                                                                             
  SHOP_CART_LINE_NBR         NUMBER(8) NOT NULL ,                                                                                                                
  CATALOG_ITEM_ID            NUMBER(18) NOT NULL ,                                                                                                             
  WILLCALL_IND               VARCHAR2(1) NULL ,                                                                                                                  
  SHOP_CART_ITEM_QTY         NUMBER(11,4) NOT NULL ,                                                                                                             
  ITEM_UNIT_OF_ISSUE_CD      VARCHAR2(10) NOT NULL ,                                                                                                            
  SHOP_CART_ITEM_COST_AMT    NUMBER(19,4) NOT NULL ,                                                                                                             
  SHOP_CART_ITEM_PRICE_AMT   NUMBER(19,4) NOT NULL ,                                                                                                             
  SHOP_CART_ITEM_MARKUP_AMT  NUMBER(19,4) NOT NULL ,                                                                                                             
  SHOP_CART_ITEM_TAX_AMT     NUMBER(19,4) NOT NULL ,                                                                                                             
  SHOP_CART_ITEM_DETAIL_DESC VARCHAR2(4000) NOT NULL ,                                                                                                            
  MANUFACTURER_NBR           VARCHAR2(30) NOT NULL ,                                                                                                             
  SHIPPING_WGT               NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_HT                NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_WD                NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_LH                NUMBER(8,2) NULL ,                                                                                                                  
  WAREHOUSE_CD               VARCHAR2(2) NULL ,                                                                                                                  
  VENDOR_HEADER_GENERATED_ID NUMBER(10) NULL ,                                                                                                             
  VENDOR_DETAIL_ASSIGNED_ID  NUMBER(10) NULL ,                                                                                                              
  VENDOR_NM                  VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND                   VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT             TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_SHOP_CART_DETAIL_T                                                                                                                          
  ADD CONSTRAINT  MM_SHOP_CART_DETAIL_TP1 PRIMARY KEY (SHOP_CART_DETAIL_ID);                                                                 
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_SHOP_CART_DETAIL_T                                                                                                                          
ADD CONSTRAINT  MM_SHOP_CART_DETAIL_TC0 UNIQUE (SHOP_CART_DOC_NBR, SHOP_CART_LINE_NBR);                                                                                                   
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_SHOP_CART_DOC_T                                                                                                                         
(                                                                                                                                                          
  FDOC_NBR                 VARCHAR2(14) NOT NULL ,                                                                                                             
  OBJ_ID                   VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                  NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CUSTOMER_PRNCPL_ID       VARCHAR2(100) NOT NULL ,                                                                                                             
  SHOP_CART_HEADER_NM      VARCHAR2(40) NULL ,                                                                                                             
  BILLING_ADDRESS_ID       NUMBER(18) NULL ,                                                                                                                  
  SHIPPING_ADDRESS_ID      NUMBER(18) NULL ,                                                                                                                  
  DELIVERY_BUILDING_CD     VARCHAR2(4) NULL ,                                                                                                                  
  DELIVERY_BUILDING_RM_NBR VARCHAR2(8) NULL ,                                                                                                              
  DELIVERY_INSTRUCTION_TXT VARCHAR2(255) NULL ,                                                                                                            
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_SHOP_CART_DOC_T                                                                                                                          
  ADD CONSTRAINT  MM_SHOP_CART_DOC_TP1 PRIMARY KEY (FDOC_NBR);                                                                          
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_SHOP_CART_DOC_T                                                                                                                          
ADD CONSTRAINT  MM_SHOP_CART_DOC_TC0 UNIQUE (OBJ_ID);                                                                                                   
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_STOCK_T                                                                                                                              
(                                                                                                                                                          
  STOCK_ID                    NUMBER(18)  NOT NULL ,                                                                                                             
  OBJ_ID                      VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                     NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  STOCK_DISTRIBUTOR_NBR       VARCHAR2(30) NOT NULL ,                                                                                                                 
  STOCK_DOC_NBR               VARCHAR2(14) NOT NULL ,                                                                                                             
  STOCK_DESC                  VARCHAR2(240) NOT NULL ,                                                                                                            
  STOCK_URL_IMG               VARCHAR2(80) NULL ,                                                                                                                 
  ACTV_IND                    VARCHAR2(1) NULL ,                                                                                                                  
  TAXABLE_IND                 VARCHAR2(1) NULL ,                                                                                                                  
  SURCHARGE_IND               VARCHAR2(1) NULL ,                                                                                                                  
  RECYCLED_IND                VARCHAR2(1) NULL ,                                                                                                                  
  PERISHABLE_IND              VARCHAR2(1) NULL ,                                                                                                                  
  OBSOLETE_IND                VARCHAR2(1) NULL ,                                                                                                                  
  WILLCALL_IND                VARCHAR2(1) NULL ,                                                                                                                  
  STOCK_TYPE_CD               VARCHAR2(2) NULL ,                                                                                                                  
  RESTRICTED_ROUTE_CD         VARCHAR2(2) NULL ,                                                                                                                  
  MANUFACTURER_NBR            VARCHAR2(30) NULL ,                                                                                                                 
  DISTRIBUTOR_NBR             VARCHAR2(30) NULL ,                                                                                                                 
  SUBSTITUTE_DISTRIBUTOR_NBR  VARCHAR2(30) NULL ,                                                                                                                 
  CYCLE_CNT_CD                VARCHAR2(1) NULL ,                                                                                                                  
  SALES_UNIT_OF_ISSUE_CD      VARCHAR2(10) NULL ,                                                                                                               
  SALES_UNIT_OF_ISSUE_RT      NUMBER(8,2) NULL ,                                                                                                                
  BUY_UNIT_OF_ISSUE_CD        VARCHAR2(10) NULL ,                                                                                                                 
  BUY_UNIT_OF_ISSUE_RT        NUMBER(8,2) NULL ,                                                                                                                  
  UPC_CD                      VARCHAR2(30) NULL ,                                                                                                             
  PACKAGING_UNIT_DESC         VARCHAR2(80) NULL ,                                                                                                                 
  SHIPPING_WGT                NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_HT                 NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_WD                 NUMBER(8,2) NULL ,                                                                                                                  
  SHIPPING_LH                 NUMBER(8,2) NULL ,                                                                                                                  
  REORDER_POINT_QTY           NUMBER(8,2) NULL ,                                                                                                                  
  SAFETY_STOCK_QTY            NUMBER(8,2) NULL ,                                                                                                                  
  SAFETY_STOCK_DAYS           NUMBER(8) NULL ,                                                                                                                    
  MINIMUM_ORDER_QTY           NUMBER(8,2) NULL ,                                                                                                                  
  STOCK_MARKUP_CD             VARCHAR2(12) NULL ,                                                                                                             
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_T                                                                                                                               
  ADD CONSTRAINT  MM_STOCK_TP1 PRIMARY KEY (STOCK_ID);                                                                                
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_T                                                                                                                               
ADD CONSTRAINT  MM_STOCK_TC0 UNIQUE (STOCK_DISTRIBUTOR_NBR);                                                                                                        
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_STOCK_ATTRIBUTE_T                                                                                                                           
(                                                                                                                                                          
  STOCK_ATTRIBUTE_ID   NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  STOCK_DOC_NBR        VARCHAR2(14) NOT NULL ,                                                                                                             
  STOCK_ATTRIBUTE_CD   VARCHAR2(2) NOT NULL ,
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_ATTRIBUTE_T                                                                                                                            
  ADD CONSTRAINT  MM_STOCK_ATTRIBUTE_TP1 PRIMARY KEY (STOCK_ATTRIBUTE_ID);         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_ATTRIBUTE_T                                                                                                                            
ADD CONSTRAINT  MM_STOCK_ATTRIBUTE_TC0 UNIQUE (STOCK_DOC_NBR, STOCK_ATTRIBUTE_CD);                                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_STOCK_ATTRIBUTE_CODE_T
(                                                                                                                                                          
  STOCK_ATTRIBUTE_CD   VARCHAR2(2) NOT NULL ,
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL,
  NM                   VARCHAR2(45) NOT NULL ,
  ACTV_IND             VARCHAR2(1) NOT NULL ,
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         



ALTER TABLE MM_STOCK_ATTRIBUTE_CODE_T                                                                                                                                 
  ADD CONSTRAINT  MM_STOCK_ATTRIBUTE_CODE_TP1 PRIMARY KEY (STOCK_ATTRIBUTE_CD);
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_ATTRIBUTE_CODE_T                                                                                                                                 
ADD CONSTRAINT  MM_STOCK_ATTRIBUTE_CODE_TC0 UNIQUE (OBJ_ID);                                                                                                          



CREATE TABLE MM_STOCK_BALANCE_T                                                                                                                            
(                                                                                                                                                          
  STOCK_BALANCE_ID     NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  STOCK_ID             NUMBER(18) NOT NULL ,                                                                                                              
  BIN_ID               NUMBER(18) NOT NULL ,                                                                                                              
  QTY_ON_HAND          NUMBER(8) NOT NULL ,                                                                                                                
  LAST_CHECKIN_DT      DATE NULL ,                                                                                                                         
  LAST_CNT_DT          DATE NULL ,                                                                                                                         
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_BALANCE_T                                                                                                                             
  ADD CONSTRAINT  MM_STOCK_BALANCE_TP1 PRIMARY KEY (STOCK_BALANCE_ID);

                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_BALANCE_T                                                                                                                             
ADD CONSTRAINT  MM_STOCK_BALANCE_TC0 UNIQUE (STOCK_ID, BIN_ID);                                                                                                      
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_STOCK_COUNT_T                                                                                                                              
(                                                                                                                                                          
  STOCK_COUNT_ID          NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID                  VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                 NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  STOCK_ID                NUMBER(18) NOT NULL ,                                                                                                              
  BIN_ID                  NUMBER(18) NOT NULL ,
  WORKSHEET_COUNT_DOC_NBR VARCHAR2(14) NULL ,                                                                                                                
  WORKSHEET_PRNCPL_ID     VARCHAR2(100) NOT NULL ,                                                                                                                
  BEFORE_ITEM_QTY         NUMBER(11,4) NULL ,                                                                                                             
  BEFORE_ITEM_UNIT_OF_ISSUE_CD VARCHAR2(10) NOT NULL ,                                                                                                     
  ORIGINAL_DT             TIMESTAMP NOT NULL ,                                                                                                                
  STOCK_COUNT_ITEM_QTY    NUMBER(11,4) NULL ,                                                                                                             
  STOCK_TRANS_REASON_CD   VARCHAR2(8) NULL ,                                                                                                             
  STOCK_COUNT_NOTE        VARCHAR2(200) NULL ,                                                                                                            
  TIMES_COUNTED           NUMBER(8) NULL ,                                                                                                             
  LAST_UPDATE_DT          TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_COUNT_T                                                                                                                               
  ADD CONSTRAINT  MM_STOCK_COUNT_TP1 PRIMARY KEY (STOCK_COUNT_ID);
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_COUNT_T                                                                                                                               
ADD CONSTRAINT  MM_STOCK_COUNT_TC0 UNIQUE (STOCK_ID, BIN_ID, WORKSHEET_COUNT_DOC_NBR);

                                                                                                                                                           
CREATE TABLE MM_STOCK_HISTORY_T                                                                                                                            
(                                                                                                                                                          
  STOCK_HISTORY_ID              NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID                        VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                       NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  STOCK_BALANCE_ID              NUMBER(18) NOT NULL ,                                                                                                              
  STOCK_TRANS_REASON_CD         VARCHAR2(8) NOT NULL ,                                                                                                             
  BEFORE_STOCK_QTY              NUMBER(11,4) NOT NULL ,                                                                                                             
  BEFORE_STOCK_UNIT_OF_ISSUE_CD VARCHAR2(10) NOT NULL ,                                                                                                     
  BEFORE_STOCK_PRC              NUMBER(19,4) NOT NULL ,                                                                                                             
  TRANS_STOCK_QTY               NUMBER(11,4) NULL ,                                                                                                                 
  TRANS_STOCK_UNIT_OF_ISSUE_CD  VARCHAR2(10) NULL ,                                                                                                                
  TRANS_STOCK_PRC               NUMBER(19,4) NULL ,                                                                                                                 
  AFTER_STOCK_QTY               NUMBER(11,4) NOT NULL ,                                                                                                             
  AFTER_STOCK_UNIT_OF_ISSUE_CD  VARCHAR2(10) NOT NULL ,                                                                                                      
  AFTER_STOCK_PRC               NUMBER(19,4) NOT NULL ,                                                                                                             
  REFERENCE_OBJ_ID              VARCHAR(36) NOT NULL ,                                                                                                              
  HISTORY_TRANS_TIMESTAMP       TIMESTAMP NOT NULL ,
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_HISTORY_T                                                                                                                             
  ADD CONSTRAINT  MM_STOCK_HISTORY_TP1 PRIMARY KEY (STOCK_HISTORY_ID);                                                      
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_HISTORY_T                                                                                                                             
ADD CONSTRAINT  MM_STOCK_HISTORY_TC0 UNIQUE (OBJ_ID);                                                                                                      
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_STOCK_DOC_T                                                                                                                              
(                                                                                                                                                          
  FDOC_NBR             VARCHAR2(14)  NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  STOCK_HEADER_DESC    VARCHAR2(400) NULL ,                                                                                                                 
  SHORT_STOCK_NM       VARCHAR2(45) NULL ,                                                                                                                 
  AGREEMENT_NBR        NUMBER(9) NULL ,
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                                    
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_DOC_T                                                                                                                               
  ADD CONSTRAINT  MM_STOCK_DOC_TP1 PRIMARY KEY (FDOC_NBR);                                                                                               
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_DOC_T                                                                                                                               
ADD CONSTRAINT  MM_STOCK_DOC_TC0 UNIQUE (OBJ_ID);                                                                                                        
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_STOCK_PACK_NOTE_T                                                                                                                   
(                                                                                                                                                          
  STOCK_PACK_NOTE_ID          NUMBER(18) NOT NULL ,                                                                                                         
  OBJ_ID                      VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                     NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  STOCK_DISTRIBUTOR_NBR       VARCHAR2(30) NOT NULL ,                                                                                                         
  PACK_LIST_ANNOUNCEMENT_CD   VARCHAR2(8) NOT NULL ,                                                                                                         
  ACTV_IND                    VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT              TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_PACK_NOTE_T                                                                                                                    
  ADD CONSTRAINT  MM_STOCK_PACK_NOTE_TP1 PRIMARY KEY (STOCK_PACK_NOTE_ID);                                                                   
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_PACK_NOTE_T                                                                                                                    
ADD CONSTRAINT  MM_STOCK_PACK_NOTE_TC0 UNIQUE (OBJ_ID);                                                                                             
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_STOCK_PRICE_T                                                                                                                               
(                                                                                                                                                          
  STOCK_PRICE_ID       NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  STOCK_ID             NUMBER(18) NOT NULL ,                                                                                                             
   PRICE_CD             VARCHAR2(2) NOT NULL ,                                                                                                              
  STOCK_PRC            NUMBER(19,4) NOT NULL ,                                                                                                             
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_PRICE_T                                                                                                                                
  ADD CONSTRAINT  MM_STOCK_PRICE_TP1 PRIMARY KEY (STOCK_PRICE_ID);                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_PRICE_T                                                                                                                                
ADD CONSTRAINT  MM_STOCK_PRICE_TC0 UNIQUE (STOCK_ID, PRICE_CD);                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_STOCK_TRANS_REASON_T                                                                                                                       
(                                                                                                                                                          
  STOCK_TRANS_REASON_CD       VARCHAR2(8) NOT NULL ,                                                                                                             
  OBJ_ID                      VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                     NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                          VARCHAR2(45) NULL ,                                                                                                              
  ACTV_IND                    VARCHAR2(1) NOT NULL ,
  LAST_UPDATE_DT              TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_TRANS_REASON_T                                                                                                                        
  ADD CONSTRAINT  MM_STOCK_TRANS_REASON_TP1 PRIMARY KEY (STOCK_TRANS_REASON_CD);                                                                           
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_TRANS_REASON_T                                                                                                                        
ADD CONSTRAINT  MM_STOCK_TRANS_REASON_TC0 UNIQUE (OBJ_ID);                                                                                                 
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_STOCK_TYPE_T                                                                                                                                
(                                                                                                                                                          
  STOCK_TYPE_CD        VARCHAR2(2) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(45) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_TYPE_T                                                                                                                                 
  ADD CONSTRAINT  MM_STOCK_TYPE_TP1 PRIMARY KEY (STOCK_TYPE_CD);

                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_STOCK_TYPE_T                                                                                                                                 
ADD CONSTRAINT  MM_STOCK_TYPE_TC0 UNIQUE (OBJ_ID);                                                                                                          
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_UNIT_OF_ISSUE_T                                                                                                                            
(                                                                                                                                                          
  UNIT_OF_ISSUE_CD     VARCHAR2(10) NOT NULL ,                                                                                                             
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  UNIT_OF_ISSUE_DESC   VARCHAR2(40) NULL ,                                                                                                                 
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_UNIT_OF_ISSUE_T                                                                                                                             
  ADD CONSTRAINT  MM_UNIT_OF_ISSUE_TP1 PRIMARY KEY (UNIT_OF_ISSUE_CD);                                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_UNIT_OF_ISSUE_T                                                                                                                             
  ADD CONSTRAINT  MM_UNIT_OF_ISSUE_TC0 UNIQUE (OBJ_ID);                                                                                                      
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_WAREHOUSE_T                                                                                                                                
(                                                                                                                                                          
  WAREHOUSE_CD         VARCHAR2(2) NOT NULL ,                                                                                                              
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  WAREHOUSE_NME        VARCHAR2(60) NULL ,                                                                                                                 
  ADDRESS_CD           VARCHAR2(10) NULL ,                                                                                                                 
  CONSOLIDATION_CD     VARCHAR2(10) NULL ,                                                                                                                 
  RESALE_COA_CD        VARCHAR2(2) NULL ,                                                                                                                 
  RESALE_ACCOUNT_NBR   VARCHAR2(7) NULL ,                                                                                                                 
  RESALE_SUB_ACCT_NBR  VARCHAR2(5) NULL ,                                                                                                               
  DEFAULT_COA_CD       VARCHAR2(2) NULL ,                                                                                                                 
  DEFAULT_ACCOUNT_NBR  VARCHAR2(7) NULL ,                                                                                                                 
  DEFAULT_SUB_ACCT_NBR VARCHAR2(5) NULL ,                                                                                                              
  DEFAULT_MARKUP_CD    VARCHAR2(12) NULL ,                                                                                                                  
  AGREEMENT_NBR        NUMBER(9) NULL ,                                                                                                                    
  AGREEMENT_LAG_DAYS   NUMBER(8) NULL ,                                                                                                                    
  BUYOUT_IND           VARCHAR2(1) NULL ,                                                                                                                  
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_WAREHOUSE_T                                                                                                                                 
  ADD CONSTRAINT  MM_WAREHOUSE_TP1 PRIMARY KEY (WAREHOUSE_CD);                                                                                             
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_WAREHOUSE_T                                                                                                                                 
ADD CONSTRAINT  MM_WAREHOUSE_TC0 UNIQUE (OBJ_ID);                                                                                                          
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_WORKSHEET_COUNT_DOC_T                                                                                                                                
(                                                                                                                                                          
  FDOC_NBR                VARCHAR2(14)  NOT NULL ,                                                                                                             
  OBJ_ID                  VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                 NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  WORKSHEET_CNT           NUMBER(8) NOT NULL ,                                                                                                                
  WORKSHEET_ORIGINAL_DT   TIMESTAMP NULL ,                                                                                                                   
  WORKSHEET_COMPLETION_DT TIMESTAMP NULL ,                                                                                                                 
  WORKSHEET_STATUS_CD     VARCHAR2(4) NULL ,                                                                                                                  
  LAST_UPDATE_DT          TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_WORKSHEET_COUNT_DOC_T                                                                                                                                 
  ADD CONSTRAINT  MM_WORKSHEET_COUNT_DOC_TP1 PRIMARY KEY (FDOC_NBR);                                                               
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_WORKSHEET_COUNT_DOC_T                                                                                                                                 
ADD CONSTRAINT  MM_WORKSHEET_COUNT_DOC_TC0 UNIQUE (OBJ_ID);                                                                                                          
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_WORKSHEET_COUNTER_T                                                                                                                        
(                                                                                                                                                          
  WORKSHEET_COUNTER_ID     NUMBER(18)  NOT NULL ,                                                                                                             
  OBJ_ID                   VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                  NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  WORKSHEET_COUNT_DOC_NBR  VARCHAR2(14) NOT NULL ,                                                                                                                
  WORKSHEET_CNTR_PRNCPL_ID VARCHAR2(100) NOT NULL ,                                                                                                             
  LAST_UPDATE_DT           TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_WORKSHEET_COUNTER_T                                                                                                                         
  ADD CONSTRAINT  MM_WORKSHEET_COUNTER_TP1 PRIMARY KEY (WORKSHEET_COUNTER_ID);                                  
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_WORKSHEET_COUNTER_T                                                                                                                         
ADD CONSTRAINT  MM_WORKSHEET_COUNTER_TC0 UNIQUE (WORKSHEET_COUNT_DOC_NBR, WORKSHEET_CNTR_PRNCPL_ID);                                                                                                  


CREATE TABLE MM_WORKSHEET_STATUS_T                                                                                                                                
(                                                                                                                                                          
  WORKSHEET_STATUS_CD  VARCHAR2(4) NULL ,                                                                                                                  
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  NM                   VARCHAR2(40) NULL ,                                                                                                                  
  ACTV_IND             VARCHAR2(1) NOT NULL ,                                                                                                              
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_WORKSHEET_STATUS_T                                                                                                                                 
  ADD CONSTRAINT  MM_WORKSHEET_STATUS_TP1 PRIMARY KEY (WORKSHEET_STATUS_CD);                                                               
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_WORKSHEET_STATUS_T                                                                                                                                 
ADD CONSTRAINT  MM_WORKSHEET_STATUS_TC0 UNIQUE (OBJ_ID);                                                                                                          
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_XML_INVOICE_T                                                                                                                              
(                                                                                                                                                          
  KEY_ID               VARCHAR2(100 BYTE) NOT NULL ,                                                                                                       
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  CATALOG_CD           VARCHAR2(12) NOT NULL ,                                                                                                             
  ORDER_ID             NUMBER(8) NOT NULL ,                                                                                                                
  INVOICE_NBR          VARCHAR2(15 BYTE) NULL ,                                                                                                            
  PROCESSED_IND        VARCHAR2(1 BYTE) NULL ,                                                                                                             
  INVOICE_XML          XMLType NULL ,                                                                                                                      
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_XML_INVOICE_T                                                                                                                               
  ADD CONSTRAINT  MM_XML_INVOICE_TP1 PRIMARY KEY (KEY_ID);                                                                                                 
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_XML_INVOICE_T                                                                                                                               
ADD CONSTRAINT  MM_XML_INVOICE_TC0 UNIQUE (OBJ_ID);                                                                                                        
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_XML_PURCHASE_ORDER_T                                                                                                                       
(                                                                                                                                                          
  XML_PURCHASE_ORDER_ID NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID                VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR               NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  KEY_ID                VARCHAR2(100 BYTE) NOT NULL ,                                                                                                       
  ORDER_ID              NUMBER(8) NOT NULL ,                                                                                                                
  CUSTOMER_ID           VARCHAR2(12) NOT NULL ,                                                                                                             
  PURCHASE_ORDER_URL    VARCHAR2(250 BYTE) NULL ,                                                                                                           
  PURCHASE_ORDER_XML    XMLType NULL ,                                                                                                                      
  LAST_UPDATE_DT        TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_XML_PURCHASE_ORDER_T                                                                                                                        
  ADD CONSTRAINT  MM_XML_PURCHASE_ORDER_TP1 PRIMARY KEY (XML_PURCHASE_ORDER_ID);                                                                     
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_XML_PURCHASE_ORDER_T                                                                                                                        
ADD CONSTRAINT  MM_XML_PURCHASE_ORDER_TC0 UNIQUE (OBJ_ID);                                                                                                 
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_XML_PURCHASE_REQUEST_T                                                                                                                     
(                                                                                                                                                          
  XML_PURCHASE_REQUEST_ID  NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID                   VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR                  NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  KEY_ID                   VARCHAR2(100 BYTE) NOT NULL ,                                                                                                       
  ORDER_ID                 NUMBER(8) NOT NULL ,                                                                                                                
  CUSTOMER_ID              VARCHAR2(12) NOT NULL ,                                                                                                             
  SUPPLIER_AUXILARY_ID     VARCHAR2(28 BYTE) NULL ,                                                                                                            
  PURCHASE_REQUEST_XML     XMLType NULL ,                                                                                                                      
  LAST_UPDATE_DT           TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_XML_PURCHASE_REQUEST_T                                                                                                                      
  ADD CONSTRAINT  MM_XML_PURCHASE_REQUEST_TP1 PRIMARY KEY (XML_PURCHASE_REQUEST_ID);                                                                   
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_XML_PURCHASE_REQUEST_T                                                                                                                      
ADD CONSTRAINT  MM_XML_PURCHASE_REQUEST_TC0 UNIQUE (OBJ_ID);                                                                                               
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
CREATE TABLE MM_ZONE_T                                                                                                                                     
(                                                                                                                                                          
  ZONE_ID              NUMBER(18)  NOT NULL ,                                                                                        
  OBJ_ID               VARCHAR2(36) DEFAULT  sys_guid()  NOT NULL ,                                                                                        
  VER_NBR              NUMBER(8) DEFAULT  1  NOT NULL ,                                                                                                    
  ZONE_CD              VARCHAR2(2) NOT NULL ,                                                                                                              
  ZONE_DESC            VARCHAR2(40) NULL ,                                                                                                                 
  WAREHOUSE_CD         VARCHAR2(2) NOT NULL ,                                                                                                              
  ACTV_IND             VARCHAR2(1) NOT NULL , 
  LAST_UPDATE_DT       TIMESTAMP NOT NULL                                                                                                                 
);                                                                                                                                                         
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ZONE_T                                                                                                                                      
  ADD CONSTRAINT  MM_ZONE_TP1 PRIMARY KEY (ZONE_ID);                                                                                          
                                                                                                                                                           
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ZONE_T                                                                                                                                      
  ADD CONSTRAINT  MM_ZONE_TC0 UNIQUE (WAREHOUSE_CD, ZONE_CD);                                                                                                               
                                                                                                                                                             
                                                                                                                                                           
                                                                                                                                                           
ALTER TABLE MM_ACCOUNTS_T                                                                                                                                  
  ADD (CONSTRAINT MM_ACCOUNTS_TR1 FOREIGN KEY (DOC_NBR) REFERENCES MM_SHOP_CART_DOC_T (FDOC_NBR));                              
                                                                                                                                                           
ALTER TABLE MM_ACCOUNTS_T                                                                                                                                  
  ADD (CONSTRAINT MM_ACCOUNTS_TR2 FOREIGN KEY (DOC_NBR) REFERENCES MM_SHOP_CART_DETAIL_T (SHOP_CART_DETAIL_ID));          
                                                                                                                                                           
ALTER TABLE MM_ACCOUNTS_T                                                                                                                                  
  ADD (CONSTRAINT MM_ACCOUNTS_TR3 FOREIGN KEY (DOC_NBR) REFERENCES MM_ORDER_DOC_T (FDOC_NBR));                                                                    
                                                                                                                                                           
ALTER TABLE MM_ACCOUNTS_T
  ADD (CONSTRAINT MM_ACCOUNTS_TR4 FOREIGN KEY (DOC_NBR) REFERENCES MM_ORDER_DETAIL_T (ORDER_DETAIL_ID));
                                                                                                                                                           
ALTER TABLE MM_ADDRESS_T                                                                                                                                  
  ADD (CONSTRAINT MM_ADDRESS_TR1 FOREIGN KEY (ADDRESS_TYPE_CD) REFERENCES MM_ADDRESS_TYPE_T (ADDRESS_TYPE_CD));
                                                                                                                                                           
ALTER TABLE MM_SHOP_CART_DTL_ADDL_COST_T                                                                                                                           
  ADD (CONSTRAINT MM_SHOP_CART_DTL_ADDL_COST_TR1 FOREIGN KEY (SHOP_CART_DETAIL_ID) REFERENCES MM_SHOP_CART_DETAIL_T (SHOP_CART_DETAIL_ID));
                                                                                                                                                           
ALTER TABLE MM_SHOP_CART_DTL_ADDL_COST_T                                                                                                                           
  ADD (CONSTRAINT MM_SHOP_CART_DTL_ADDL_COST_TR2 FOREIGN KEY (ADDITIONAL_COST_TYPE_CD) REFERENCES MM_ADDITIONAL_COST_TYPE_T (ADDITIONAL_COST_TYPE_CD));            
                                                                                                                                                           
ALTER TABLE MM_BACK_ORDER_T                                                                                                                                
  ADD (CONSTRAINT MM_BACK_ORDER_TR1 FOREIGN KEY (PICK_LIST_LINE_ID) REFERENCES MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID));          

ALTER TABLE MM_BACK_ORDER_T
  ADD (CONSTRAINT MM_BACK_ORDER_TR2 FOREIGN KEY (NEW_PICK_LIST_LINE_ID) REFERENCES MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID));
                                                                                                                                                           
ALTER TABLE MM_BIN_T                                                                                                                                       
  ADD (CONSTRAINT MM_BIN_TR1 FOREIGN KEY (ZONE_ID) REFERENCES MM_ZONE_T (ZONE_ID));                                            
                                                                                                                                                           
ALTER TABLE MM_CATALOG_ITEM_T                                                                                                                            
  ADD (CONSTRAINT MM_CATALOG_ITEM_TR1 FOREIGN KEY (CATALOG_DOC_NBR) REFERENCES MM_CATALOG_DOC_T (FDOC_NBR));                                                    
                                                                                                                                                           
ALTER TABLE MM_CATALOG_ITEM_T                                                                                                                            
  ADD (CONSTRAINT MM_CATALOG_ITEM_TR2 FOREIGN KEY (DISTRIBUTOR_NBR) REFERENCES MM_STOCK_T (STOCK_DISTRIBUTOR_NBR) ON DELETE SET NULL);
                                                                                                                                                           
ALTER TABLE MM_CATALOG_ITEM_PENDING_T                                                                                                                            
  ADD (CONSTRAINT MM_CATALOG_ITEM_PENDING_TR1 FOREIGN KEY (CATALOG_PND_DOC_NBR) REFERENCES MM_CATALOG_PENDING_DOC_T (FDOC_NBR));
                                                                                                                                                           
ALTER TABLE MM_CATALOG_IMAGE_T                                                                                                                             
  ADD (CONSTRAINT MM_CATALOG_IMAGE_TR1 FOREIGN KEY (CATALOG_ITEM_ID) REFERENCES MM_CATALOG_ITEM_T (CATALOG_ITEM_ID));          
                                                                                                                                                           
ALTER TABLE MM_CATALOG_DOC_T                                                                                                                                   
  ADD (CONSTRAINT MM_CATALOG_DOC_TR1 FOREIGN KEY (CATALOG_CD) REFERENCES MM_CATALOG_CODE_T (CATALOG_CD) ON DELETE SET NULL);                         
                                                                                                                                                           
ALTER TABLE MM_CHECKIN_DETAIL_T                                                                                                 
  ADD (CONSTRAINT MM_CHECKIN_DETAIL_TR1 FOREIGN KEY (CHECKIN_DOC_NBR) REFERENCES MM_CHECKIN_DOC_T (FDOC_NBR));                       

ALTER TABLE MM_CHECKIN_DETAIL_T
  ADD (CONSTRAINT MM_CHECKIN_DETAIL_TR2 FOREIGN KEY (BIN_ID) REFERENCES MM_BIN_T (BIN_ID));                       
                                                                                                                                                           
ALTER TABLE MM_CHECKIN_DOC_T                                                                                                                            
  ADD (CONSTRAINT MM_CHECKIN_DOC_TR1 FOREIGN KEY (ORDER_DOC_NBR) REFERENCES MM_ORDER_DOC_T (FDOC_NBR));                                                   
                                                                                                                                                           
ALTER TABLE MM_CHECKIN_DOC_T                                                                                                                            
  ADD (CONSTRAINT MM_CHECKIN_DOC_TR2 FOREIGN KEY (WAREHOUSE_CD) REFERENCES MM_WAREHOUSE_T (WAREHOUSE_CD));

ALTER TABLE MM_CUSTOMER_FAV_DETAIL_T                                                                                                                       
  ADD (CONSTRAINT MM_CUSTOMER_FAV_DETAIL_TR1 FOREIGN KEY (CUSTOMER_FAV_ID) REFERENCES MM_CUSTOMER_FAV_HEADER_T (CUSTOMER_FAV_ID));
                                                                                                                                                           
ALTER TABLE MM_CUSTOMER_FAV_DETAIL_T                                                                                                                       
  ADD (CONSTRAINT MM_CUSTOMER_FAV_DETAIL_TR2 FOREIGN KEY (CATALOG_ITEM_ID) REFERENCES MM_CATALOG_ITEM_T (CATALOG_ITEM_ID));    

ALTER TABLE MM_CUSTOMER_FAV_HEADER_T                                                                                                                       
  ADD (CONSTRAINT MM_CUSTOMER_FAV_HEADER_TR1 FOREIGN KEY (PRNCPL_ID) REFERENCES MM_CUSTOMER_T (PRNCPL_ID));
                                                         
ALTER TABLE MM_CUSTOMER_PROFILE_T                                                                                                                          
  ADD (CONSTRAINT MM_CUSTOMER_PROFILE_TR1 FOREIGN KEY (CUSTOMER_PRNCPL_ID) REFERENCES MM_CUSTOMER_T (PRNCPL_ID));                                               
                                                                                                                                                           
ALTER TABLE MM_CUSTOMER_T                                                                                                                          
  ADD (CONSTRAINT MM_CUSTOMER_TR1 FOREIGN KEY (DEFAULT_PROFILE_ID) REFERENCES MM_CUSTOMER_PROFILE_T (PROFILE_ID));                                               
                                                                                                                                                           
ALTER TABLE MM_CYLINDER_T                                                                                                                          
  ADD (CONSTRAINT MM_CYLINDER_TR1 FOREIGN KEY (STOCK_ID) REFERENCES MM_STOCK_T (STOCK_ID));                                               
                                                                                                                                                           
ALTER TABLE MM_CYLINDER_T                                                                                                                          
  ADD (CONSTRAINT MM_CYLINDER_TR2 FOREIGN KEY (CYLINDER_GAS_CD) REFERENCES MM_CYLINDER_GAS_T (CYLINDER_GAS_CD));                                               
                                                                                                                                                           
ALTER TABLE MM_DOT_HAZARDOUS_T                                                                                                                             
  ADD (CONSTRAINT MM_DOT_HAZARDOUS_TR1 FOREIGN KEY (DRIVER_MANIFEST_CD) REFERENCES MM_DRIVER_MANIFEST_T (DRIVER_MANIFEST_CD) ON DELETE SET NULL);                          
                                                                                                                                                           
ALTER TABLE MM_FURNITURE_T                                                                                                                             
  ADD (CONSTRAINT MM_FURNITURE_TR1 FOREIGN KEY (SHOP_CART_DOC_NBR) REFERENCES MM_SHOP_CART_DOC_T (FDOC_NBR) ON DELETE SET NULL);                          

ALTER TABLE MM_HAZARDOUS_MATERIEL_T                                                                                                                        
  ADD (CONSTRAINT MM_HAZARDOUS_MATERIEL_TR1 FOREIGN KEY (STOCK_ID) REFERENCES MM_STOCK_T (STOCK_ID));                
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_MATERIEL_T                                                                                                                        
  ADD (CONSTRAINT MM_HAZARDOUS_MATERIEL_TR2 FOREIGN KEY (EHS_CONTAINER_CD) REFERENCES MM_EHS_CONTAINER_T (EHS_CONTAINER_CD) ON DELETE SET NULL);           
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_MATERIEL_T                                                                                                                        
  ADD (CONSTRAINT MM_HAZARDOUS_MATERIEL_TR3 FOREIGN KEY (HAZARDOUS_STATE_CD) REFERENCES MM_HAZARDOUS_STATE_T (HAZARDOUS_STATE_CD) ON DELETE SET NULL);     
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_MATERIEL_T                                                                                                                        
  ADD (CONSTRAINT MM_HAZARDOUS_MATERIEL_TR4 FOREIGN KEY (EHS_HAZARDOUS_STATE_CD) REFERENCES MM_EHS_HAZARDOUS_STATE_T (EHS_HAZARDOUS_STATE_CD) ON DELETE SET NULL);
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_MATERIEL_T                                                                                                                        
  ADD (CONSTRAINT MM_HAZARDOUS_MATERIEL_TR5 FOREIGN KEY (DOT_HAZARDOUS_CD) REFERENCES MM_DOT_HAZARDOUS_T (DOT_HAZARDOUS_CD) ON DELETE SET NULL);           
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_MATERIEL_T                                                                                                                        
  ADD (CONSTRAINT MM_HAZARDOUS_MATERIEL_TR6 FOREIGN KEY (HAZARDOUS_UN_CD) REFERENCES MM_HAZARDOUS_UN_T (HAZARDOUS_UN_CD) ON DELETE SET NULL);              
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_MATERIEL_T                                                                                                                        
  ADD (CONSTRAINT MM_HAZARDOUS_MATERIEL_TR7 FOREIGN KEY (EHS_HAZARDOUS_CD) REFERENCES MM_EHS_HAZARDOUS_T (EHS_HAZARDOUS_CD) ON DELETE SET NULL);           
                                                                                                                                                           
ALTER TABLE MM_HAZARDOUS_MATERIEL_T                                                                                                                        
  ADD (CONSTRAINT MM_HAZARDOUS_MATERIEL_TR8 FOREIGN KEY (EHS_UNIT_OF_ISSUE_CD) REFERENCES MM_UNIT_OF_ISSUE_T (UNIT_OF_ISSUE_CD) ON DELETE SET NULL);           
                                                                                                                                                           
ALTER TABLE MM_STOCK_ATTRIBUTE_T                                                                                                                            
  ADD (CONSTRAINT MM_STOCK_ATTRIBUTE_TR1 FOREIGN KEY (STOCK_DOC_NBR) REFERENCES MM_STOCK_DOC_T (FDOC_NBR));                                                    
                                                                                                                                                           
ALTER TABLE MM_STOCK_ATTRIBUTE_T                                                                                                                            
  ADD (CONSTRAINT MM_STOCK_ATTRIBUTE_TR2 FOREIGN KEY (STOCK_ATTRIBUTE_CD) REFERENCES MM_STOCK_ATTRIBUTE_CODE_T (STOCK_ATTRIBUTE_CD));                                                    
                                                                                                                                                           
ALTER TABLE MM_STOCK_T                                                                                                                               
  ADD (CONSTRAINT MM_STOCK_TR1 FOREIGN KEY (STOCK_DOC_NBR) REFERENCES MM_STOCK_DOC_T (FDOC_NBR));                                                       
                                                                                                                                                           
ALTER TABLE MM_STOCK_T                                                                                                                               
  ADD (CONSTRAINT MM_STOCK_TR2 FOREIGN KEY (STOCK_TYPE_CD) REFERENCES MM_STOCK_TYPE_T (STOCK_TYPE_CD) ON DELETE SET NULL);                              
                                                                                                                                                           
ALTER TABLE MM_STOCK_T                                                                                                                                  
  ADD (CONSTRAINT MM_STOCK_TR3 FOREIGN KEY (STOCK_MARKUP_CD) REFERENCES MM_MARKUP_T (MARKUP_CD));
                                                          
ALTER TABLE MM_STOCK_T                                                                                                                                  
  ADD (CONSTRAINT MM_STOCK_TR4 FOREIGN KEY (CYCLE_CNT_CD) REFERENCES MM_CYCLE_COUNT_T (CYCLE_CNT_CD));
                                                          
ALTER TABLE MM_STOCK_PACK_NOTE_T
  ADD (CONSTRAINT MM_STOCK_PACK_NOTE_TR1 FOREIGN KEY (STOCK_DISTRIBUTOR_NBR) REFERENCES MM_STOCK_T (STOCK_DISTRIBUTOR_NBR));
                                                          
ALTER TABLE MM_STOCK_PACK_NOTE_T
  ADD (CONSTRAINT MM_STOCK_PACK_NOTE_TR2 FOREIGN KEY (PACK_LIST_ANNOUNCEMENT_CD) REFERENCES MM_PACK_LIST_ANNOUNCEMENT_T (PACK_LIST_ANNOUNCEMENT_CD));

ALTER TABLE MM_STOCK_PRICE_T                                                                                                                                
  ADD (CONSTRAINT MM_STOCK_PRICE_TR1 FOREIGN KEY (STOCK_ID) REFERENCES MM_STOCK_T (STOCK_ID));                        
                                                                                                                                                           
ALTER TABLE MM_STOCK_PRICE_T                                                                                                                                
  ADD (CONSTRAINT MM_STOCK_PRICE_TR2 FOREIGN KEY (PRICE_CD) REFERENCES MM_PRICE_CODE_T (PRICE_CD));                                                      
                                                                                                                                                           
ALTER TABLE MM_MARKUP_T                                                                                                                                    
  ADD (CONSTRAINT MM_MARKUP_TR1 FOREIGN KEY (MARKUP_TYPE_CD) REFERENCES MM_MARKUP_TYPE_T (MARKUP_TYPE_CD) ON DELETE SET NULL);                             
                                                                                                                                                           
ALTER TABLE MM_MARKUP_T                                                                                                                                    
  ADD (CONSTRAINT MM_MARKUP_TR2 FOREIGN KEY (WAREHOUSE_CD) REFERENCES MM_WAREHOUSE_T (WAREHOUSE_CD) ON DELETE SET NULL);                                   
                                                                                                                                                           
ALTER TABLE MM_ORDER_DETAIL_T                                                                                                                              
  ADD (CONSTRAINT MM_ORDER_DETAIL_TR1 FOREIGN KEY (ORDER_DOC_NBR) REFERENCES MM_ORDER_DOC_T (FDOC_NBR));                                                     

ALTER TABLE MM_ORDER_DETAIL_T                                                                                                                              
  ADD (CONSTRAINT MM_ORDER_DETAIL_TR2 FOREIGN KEY (ORDER_DETAIL_STATUS_CD) REFERENCES MM_ORDER_STATUS_T (ORDER_STATUS_CD));                                             

ALTER TABLE MM_ORDER_DETAIL_T                                                                                                                              
  ADD (CONSTRAINT MM_ORDER_DETAIL_TR3 FOREIGN KEY (ORDER_ID) REFERENCES MM_SALES_INSTANCE_T (ORDER_ID));                                                   

ALTER TABLE MM_ORDER_DOC_T                                                                                                                              
  ADD (CONSTRAINT MM_ORDER_DOC_TR1 FOREIGN KEY (ORDER_TYPE_CD) REFERENCES MM_ORDER_TYPE_T (ORDER_TYPE_CD));                                             

ALTER TABLE MM_ORDER_DOC_T                                                                                                                              
  ADD (CONSTRAINT MM_ORDER_DOC_TR2 FOREIGN KEY (ORDER_DOC_STATUS_CD) REFERENCES MM_ORDER_STATUS_T (ORDER_STATUS_CD));                                             

ALTER TABLE MM_ORDER_DOC_T                                                                                                                              
  ADD (CONSTRAINT MM_ORDER_DOC_TR3 FOREIGN KEY (SHOP_CART_DOC_NBR) REFERENCES MM_SHOP_CART_DOC_T (FDOC_NBR));                                                                  

ALTER TABLE MM_ORDER_DOC_T                                                                                                                              
  ADD (CONSTRAINT MM_ORDER_DOC_TR4 FOREIGN KEY (RECURRING_ORDER_ID) REFERENCES MM_RECURRING_ORDER_T (RECURRING_ORDER_ID));                                                  

ALTER TABLE MM_PACK_LIST_LINE_T                                                                                                                                 
  ADD (CONSTRAINT MM_PACK_LIST_LINE_TR1 FOREIGN KEY (PACK_LIST_DOC_NBR) REFERENCES MM_PACK_LIST_DOC_T (FDOC_NBR));                                 

ALTER TABLE MM_PACK_LIST_LINE_T                                                                                                                                 
  ADD (CONSTRAINT MM_PACK_LIST_LINE_TR2 FOREIGN KEY (DELIVERY_REASON_CD) REFERENCES MM_DELIVERY_REASON_T (DELIVERY_REASON_CD));                                 

ALTER TABLE MM_PACK_LIST_LINE_T                                                                                                                                 
  ADD (CONSTRAINT MM_PACK_LIST_LINE_TR3 FOREIGN KEY (PICK_LIST_LINE_ID) REFERENCES MM_PICK_LIST_LINE_T (PICK_LIST_LINE_ID));

ALTER TABLE MM_PACK_LIST_LINE_T                                                                                                                                 
  ADD (CONSTRAINT MM_PACK_LIST_LINE_TR4 FOREIGN KEY (PACK_STATUS_CD) REFERENCES MM_PACK_STATUS_CODE_T (PACK_STATUS_CD));

ALTER TABLE MM_PACK_LIST_LINE_T                                                                                                                                 
  ADD (CONSTRAINT MM_PACK_LIST_LINE_TR5 FOREIGN KEY (ROUTE_CD) REFERENCES MM_ROUTE_T (ROUTE_CD));

ALTER TABLE MM_PACK_LIST_DOC_T                                                                                                                                 
  ADD (CONSTRAINT MM_PACK_LIST_DOC_TR1 FOREIGN KEY (PACK_STATUS_CD) REFERENCES MM_PACK_STATUS_CODE_T (PACK_STATUS_CD));

ALTER TABLE MM_PICK_LIST_LINE_T                                                                                                                                 
  ADD (CONSTRAINT MM_PICK_LIST_LINE_TR1 FOREIGN KEY (PICK_LIST_DOC_NBR) REFERENCES MM_PICK_LIST_DOC_T (FDOC_NBR));                                                      

ALTER TABLE MM_PICK_LIST_LINE_T                                                                                                                                 
  ADD (CONSTRAINT MM_PICK_LIST_LINE_TR2 FOREIGN KEY (ORDER_ID) REFERENCES MM_SALES_INSTANCE_T (ORDER_ID));                                                      

ALTER TABLE MM_PICK_LIST_LINE_T                                                                                                                                 
  ADD (CONSTRAINT MM_PICK_LIST_LINE_TR3 FOREIGN KEY (PICK_STATUS_CD) REFERENCES MM_PICK_STATUS_CODE_T (PICK_STATUS_CD));

ALTER TABLE MM_PICK_LIST_LINE_T                                                                                                                                 
  ADD (CONSTRAINT MM_PICK_LIST_LINE_TR4 FOREIGN KEY (ORDER_DETAIL_ID) REFERENCES MM_ORDER_DETAIL_T (ORDER_DETAIL_ID));

ALTER TABLE MM_PICK_LIST_LINE_T
  ADD (CONSTRAINT MM_PICK_LIST_LINE_TR5 FOREIGN KEY (PICK_TICKET_NBR) REFERENCES MM_PICK_TICKET_T (PICK_TICKET_NBR));

ALTER TABLE MM_PICK_LIST_DOC_T                                                                                                                                 
  ADD (CONSTRAINT MM_PICK_LIST_DOC_TR1 FOREIGN KEY (PICK_STATUS_CD) REFERENCES MM_PICK_STATUS_CODE_T (PICK_STATUS_CD));

ALTER TABLE MM_PICK_TICKET_T
  ADD (CONSTRAINT MM_PICK_TICKET_TR1 FOREIGN KEY (PICK_LIST_DOC_NBR) references MM_PICK_LIST_DOC_T(FDOC_NBR));

ALTER TABLE MM_PICK_TICKET_T
  ADD (CONSTRAINT MM_PICK_TICKET_TR2 FOREIGN KEY (PICK_STATUS_CD) references MM_PICK_STATUS_CODE_T(PICK_STATUS_CD));

ALTER TABLE MM_RENTAL_T                                                                                                                             
  ADD (CONSTRAINT MM_RENTAL_TR1 FOREIGN KEY (STOCK_ID) REFERENCES MM_STOCK_T (STOCK_ID));                                     

ALTER TABLE MM_RETURN_DETAIL_T                                                                                                                             
  ADD (CONSTRAINT MM_RETURN_DETAIL_TR1 FOREIGN KEY (RETURN_DOC_NBR) REFERENCES MM_RETURN_DOC_T (FDOC_NBR));                                     

ALTER TABLE MM_RETURN_DETAIL_T                                                                                                                            
  ADD (CONSTRAINT MM_RETURN_DETAIL_TR2 FOREIGN KEY (RETURN_DETAIL_STATUS_CD) REFERENCES MM_RETURN_STATUS_CODE_T (RETURN_STATUS_CD));                       

ALTER TABLE MM_RETURN_DOC_T                                                                                                                             
  ADD (CONSTRAINT MM_RETURN_DOC_TR1 FOREIGN KEY (RETURN_TYPE_CD) REFERENCES MM_RETURN_TYPE_T (RETURN_TYPE_CD));                                         

ALTER TABLE MM_RETURN_DOC_T                                                                                                                            
  ADD (CONSTRAINT MM_RETURN_DOC_TR2 FOREIGN KEY (RETURN_DOC_STATUS_CD) REFERENCES MM_RETURN_STATUS_CODE_T (RETURN_STATUS_CD));                       

ALTER TABLE MM_RETURN_DOC_T                                                                                                                             
  ADD (CONSTRAINT MM_RETURN_DOC_TR3 FOREIGN KEY (ORDER_ID) REFERENCES MM_ORDER_DOC_T (ORDER_ID) ON DELETE SET NULL);                                 

ALTER TABLE MM_RETURN_DOC_T                                                                                                                             
  ADD (CONSTRAINT MM_RETURN_DOC_TR4 FOREIGN KEY (CUSTOMER_PRNCPL_ID) REFERENCES MM_CUSTOMER_T (PRNCPL_ID) ON DELETE SET NULL);                                               

ALTER TABLE MM_ROUTE_T                                                                                                                                     
  ADD (CONSTRAINT MM_ROUTE_TR1 FOREIGN KEY (DRIVER_MANIFEST_CD) REFERENCES MM_DRIVER_MANIFEST_T (DRIVER_MANIFEST_CD) ON DELETE SET NULL);                  

ALTER TABLE MM_ROUTE_T                                                                                                                             
  ADD (CONSTRAINT MM_ROUTE_TR2 FOREIGN KEY (RESTRICTED_ROUTE_CD) REFERENCES MM_RESTRICTED_ROUTE_CODE_T (RESTRICTED_ROUTE_CD));                                     

ALTER TABLE MM_SALES_INSTANCE_T                                                                                                                            
  ADD (CONSTRAINT MM_SALES_INSTANCE_TR1 FOREIGN KEY (ORDER_SALES_STATUS_CD) REFERENCES MM_ORDER_STATUS_T (ORDER_STATUS_CD));                       

ALTER TABLE MM_SALES_INSTANCE_T                                                                                                                            
  ADD (CONSTRAINT MM_SALES_INSTANCE_TR2 FOREIGN KEY (ORDER_TYPE_CD) REFERENCES MM_ORDER_TYPE_T (ORDER_TYPE_CD));                       

ALTER TABLE MM_SALES_INSTANCE_T                                                                                                                            
  ADD (CONSTRAINT MM_SALES_INSTANCE_TR3 FOREIGN KEY (ORDER_DOC_NBR) REFERENCES MM_ORDER_DOC_T (FDOC_NBR));                       

ALTER TABLE MM_SHOP_CART_DETAIL_T                                                                                                                          
  ADD (CONSTRAINT MM_SHOP_CART_DETAIL_TR1 FOREIGN KEY (SHOP_CART_DOC_NBR) REFERENCES MM_SHOP_CART_DOC_T (FDOC_NBR));           

ALTER TABLE MM_SHOP_CART_DOC_T                                                                                                                          
  ADD (CONSTRAINT MM_SHOP_CART_DOC_TR1 FOREIGN KEY (CUSTOMER_PRNCPL_ID) REFERENCES MM_CUSTOMER_T (PRNCPL_ID));                                                                  

ALTER TABLE MM_STOCK_BALANCE_T                                                                                                                             
  ADD (CONSTRAINT MM_STOCK_BALANCE_TR1 FOREIGN KEY (BIN_ID) REFERENCES MM_BIN_T (BIN_ID));                                   

ALTER TABLE MM_STOCK_BALANCE_T                                                                                                                             
  ADD (CONSTRAINT MM_STOCK_BALANCE_TR2 FOREIGN KEY (STOCK_ID) REFERENCES MM_STOCK_T (STOCK_ID));                                     

ALTER TABLE MM_STOCK_COUNT_T                                                                                                                               
  ADD (CONSTRAINT MM_STOCK_COUNT_TR1 FOREIGN KEY (WORKSHEET_COUNT_DOC_NBR) REFERENCES MM_WORKSHEET_COUNT_DOC_T (FDOC_NBR));

ALTER TABLE MM_STOCK_COUNT_T                                                                                                                               
  ADD (CONSTRAINT MM_STOCK_COUNT_TR2 FOREIGN KEY (STOCK_ID) REFERENCES MM_STOCK_T (STOCK_ID));                                     

ALTER TABLE MM_STOCK_COUNT_T                                                                                                                               
  ADD (CONSTRAINT MM_STOCK_COUNT_TR3 FOREIGN KEY (STOCK_TRANS_REASON_CD) REFERENCES MM_STOCK_TRANS_REASON_T (STOCK_TRANS_REASON_CD));                                     

ALTER TABLE MM_STOCK_COUNT_T                                                                                                                               
  ADD (CONSTRAINT MM_STOCK_COUNT_TR4 FOREIGN KEY (BIN_ID) REFERENCES MM_BIN_T (BIN_ID));                                     

ALTER TABLE MM_STOCK_HISTORY_T                                                                                                                             
  ADD (CONSTRAINT MM_STOCK_HISTORY_TR1 FOREIGN KEY (STOCK_BALANCE_ID) REFERENCES MM_STOCK_BALANCE_T (STOCK_BALANCE_ID) ON DELETE SET NULL);

ALTER TABLE MM_STOCK_HISTORY_T                                                                                                                             
  ADD (CONSTRAINT MM_STOCK_HISTORY_TR2 FOREIGN KEY (STOCK_TRANS_REASON_CD) REFERENCES MM_STOCK_TRANS_REASON_T (STOCK_TRANS_REASON_CD));                    

ALTER TABLE MM_CATALOG_SUBGROUP_T                                                                                                                                  
  ADD (CONSTRAINT MM_CATALOG_SUBGROUP_TR1 FOREIGN KEY (CATALOG_ITEM_ID) REFERENCES MM_CATALOG_ITEM_T (CATALOG_ITEM_ID));               

ALTER TABLE MM_CATALOG_SUBGROUP_T                                                                                                                                  
  ADD (CONSTRAINT MM_CATALOG_SUBGROUP_TR2 FOREIGN KEY (CATALOG_GROUP_CD) REFERENCES MM_CATALOG_GROUP_T (CATALOG_GROUP_CD));                                                                

ALTER TABLE MM_WORKSHEET_COUNT_DOC_T                                                                                                                                 
  ADD (CONSTRAINT MM_WORKSHEET_COUNT_DOC_TR1 FOREIGN KEY (WORKSHEET_STATUS_CD) REFERENCES MM_WORKSHEET_STATUS_T (WORKSHEET_STATUS_CD));                                 

ALTER TABLE MM_WORKSHEET_COUNTER_T                                                                                                                         
  ADD (CONSTRAINT MM_WORKSHEET_COUNTER_TR1 FOREIGN KEY (WORKSHEET_COUNT_DOC_NBR) REFERENCES MM_WORKSHEET_COUNT_DOC_T (FDOC_NBR));

ALTER TABLE MM_ZONE_T                                                                                                                                      
  ADD (CONSTRAINT MM_ZONE_TR1 FOREIGN KEY (WAREHOUSE_CD) REFERENCES MM_WAREHOUSE_T (WAREHOUSE_CD));                                                        
