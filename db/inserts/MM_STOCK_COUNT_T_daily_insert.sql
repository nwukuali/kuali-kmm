insert into MM_STOCK_COUNT_T
select MM_STOCK_COUNT_S.nextval, 
       sys_guid(), 
       1,
       s.STOCK_ID,
       sb.BIN_ID,
       NULL,
       ' ',
       sb.QTY_ON_HAND,
       s.SALES_UNIT_OF_ISSUE_CD,
       CURRENT_DATE,
       NULL,
       NULL,
       ' ',
       0,
       'N',
       CURRENT_DATE       
from (select t.STOCK_ID as STOCK_ID,
             t.SALES_UNIT_OF_ISSUE_CD as SALES_UNIT_OF_ISSUE_CD,
             t.cycle_cnt_cd as CYCLE_CNT_CD
      from MM_STOCK_T t
      left join MM_STOCK_COUNT_T c
         on c.stock_id = t.stock_id
        and nvl(c.worksheet_count_doc_nbr, 0) = 0 
      where nvl(c.stock_id,0) = 0 ) s
join MM_CYCLE_COUNT_T cc
   on cc.CYCLE_CNT_CD = s.CYCLE_CNT_CD
  and cc.TIMES_PER_YEAR_NBR > 0
join MM_STOCK_BALANCE_T sb
   on sb.STOCK_ID = s.STOCK_ID
left join (select STOCK_TRANS_REASON_CD, STOCK_ID, BIN_ID,
                  max(HISTORY_TRANS_TIMESTAMP) as HISTORY_TRANS_TIMESTAMP
           from MM_STOCK_HISTORY_T
           where STOCK_TRANS_REASON_CD = 'COUNT'
           group by STOCK_TRANS_REASON_CD, STOCK_ID, BIN_ID) sh
   on sh.STOCK_ID = sb.STOCK_ID
  and sh.BIN_ID = sb.BIN_ID
left join (select STOCK_ID, BIN_ID, max(LAST_UPDATE_DT) as LAST_UPDATE_DT 
           from MM_STOCK_COUNT_T 
           group by STOCK_ID, BIN_ID) sc
   on sc.STOCK_ID = s.STOCK_ID
  and sc.BIN_ID = sb.BIN_ID
where NVL(sh.HISTORY_TRANS_TIMESTAMP, (SYSTIMESTAMP - 370))
                   < (SYSTIMESTAMP - (365 / cc.TIMES_PER_YEAR_NBR))
  and NVL(sc.LAST_UPDATE_DT, (SYSTIMESTAMP - 370))
                   < (SYSTIMESTAMP - (365 / cc.TIMES_PER_YEAR_NBR))
  and rownum <= round((select count(*) 
                       from MM_STOCK_T a 
                       join MM_CYCLE_COUNT_T xx 
                          on xx.CYCLE_CNT_CD = a.CYCLE_CNT_CD 
                         and xx.TIMES_PER_YEAR_NBR > 0) / TRUNC(365 / cc.TIMES_PER_YEAR_NBR))
;