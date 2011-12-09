package org.kuali.ext.mm.dataaccess.impl;

import org.kuali.ext.mm.common.sys.MMConstants;

public interface StockItemLookupSQLQueries {


   String STOCK_BIN_LIST_QUERY = " SELECT MST.CYCLE_CNT_CD, MST.STOCK_ID , AAA.BIN_ID, AAA.QTY_ON_HAND FROM MM_STOCK_T MST, " +
								 " (SELECT MMM.STOCK_ID , MMM.BIN_ID , MMM.QTY_ON_HAND FROM "+
                                 " MM_STOCK_BALANCE_T MMM WHERE MMM.BIN_ID IN ( "+
                                 " SELECT MBT.BIN_ID FROM MM_BIN_T MBT WHERE MBT.ZONE_ID IN ( "+
                                 " SELECT MZT.ZONE_ID FROM MM_ZONE_T MZT WHERE (? IS NULL OR MZT.ZONE_CD = ?) "+
                                 " AND (? IS NULL OR MZT.WAREHOUSE_CD = ?) " +
                                 " ) "+
                                 " ))AAA "+
                                 " WHERE  MST.STOCK_ID = AAA.STOCK_ID "+
                                 " AND (? IS NULL OR MST.CYCLE_CNT_CD = ?) ";

   String STOCK_COUNT_PICK_QUERY = "  SELECT distinct MMM.STOCK_COUNT_ID FROM "+
									"  MM_STOCK_T MST, "+
									"  MM_STOCK_COUNT_T MMM , MM_BIN_T MBT ,MM_ZONE_T MZT  WHERE "+
									"  MMM.BIN_ID = MBT.BIN_ID  AND  MBT.ZONE_ID = MZT.ZONE_ID "+
									"  AND MST.STOCK_ID = MMM.STOCK_ID "+
									"  AND (? IS NULL OR MZT.ZONE_CD = ?) "+
									"  AND (? IS NULL OR MZT.WAREHOUSE_CD = ?) "+
									"  AND (? IS NULL OR MST.CYCLE_CNT_CD = ?) "+
									"  AND MMM.WORKSHEET_COUNT_DOc_NBR IS NULL " ;


	String STOCK_COUNT_PICK_QUERY_QTY_GRT = "   SELECT distinct MMM.STOCK_COUNT_ID FROM "+
											"  MM_STOCK_T MST, "+
											"  MM_STOCK_COUNT_T MMM , MM_BIN_T MBT  ,MM_ZONE_T MZT  WHERE "+
											"  MMM.BIN_ID = MBT.BIN_ID  AND  MBT.ZONE_ID = MZT.ZONE_ID "+
											"  AND MST.STOCK_ID = MMM.STOCK_ID "+
											"  AND (? IS NULL OR MZT.ZONE_CD = ?) "+
											"  AND (? IS NULL OR MZT.WAREHOUSE_CD = ?) "+
											"  AND (? IS NULL OR MST.CYCLE_CNT_CD = ?) "+
											"  AND MMM.WORKSHEET_COUNT_DOc_NBR IS NULL  AND MMM.BEFORE_ITEM_QTY > 0 " ;

//	   String STOCK_COUNT_PICK_QUERY = "  SELECT distinct MMM.STOCK_COUNT_ID FROM "+
//		"  MM_STOCK_T MST, "+
//		"  MM_STOCK_COUNT_T MMM , MM_BIN_T MBT ,MM_ZONE_T MZT , MM_STOCK_BALANCE_T MB WHERE "+
//		"  MMM.BIN_ID = MBT.BIN_ID  AND  MBT.ZONE_ID = MZT.ZONE_ID "+
//		"  AND MST.STOCK_ID = MMM.STOCK_ID "+
//		"  AND MMM.STOCK_ID = MB.STOCK_ID AND MMM.BIN_ID = MB.BIN_ID "+
//		"  AND (? IS NULL OR MZT.ZONE_CD = ?) "+
//		"  AND (? IS NULL OR MZT.WAREHOUSE_CD = ?) "+
//		"  AND (? IS NULL OR MST.CYCLE_CNT_CD = ?) "+
//		"  AND MMM.WORKSHEET_COUNT_DOc_NBR IS NULL " ;
//
//
//String STOCK_COUNT_PICK_QUERY_QTY_GRT = "   SELECT distinct MMM.STOCK_COUNT_ID FROM "+
//				"  MM_STOCK_T MST, "+
//				"  MM_STOCK_COUNT_T MMM , MM_BIN_T MBT  ,MM_ZONE_T MZT , MM_STOCK_BALANCE_T MB WHERE "+
//				"  MMM.BIN_ID = MBT.BIN_ID  AND  MBT.ZONE_ID = MZT.ZONE_ID "+
//				"  AND MST.STOCK_ID = MMM.STOCK_ID "+
//				"  AND MMM.STOCK_ID = MB.STOCK_ID AND MMM.BIN_ID = MB.BIN_ID "+
//				"  AND (? IS NULL OR MZT.ZONE_CD = ?) "+
//				"  AND (? IS NULL OR MZT.WAREHOUSE_CD = ?) "+
//				"  AND (? IS NULL OR MST.CYCLE_CNT_CD = ?) "+
//				"  AND MMM.WORKSHEET_COUNT_DOc_NBR IS NULL  AND MMM.BEFORE_ITEM_QTY > 0 " ;

   String STOCK_COUNT_UPDATE_QUERY = " UPDATE MM_STOCK_COUNT_T MMM SET MMM.WORKSHEET_COUNT_DOC_NBR = ?  WHERE MMM.STOCK_COUNT_ID IN ("+MMConstants.UPDATE_QUERY_PLACE_HOLDER+")";
}
