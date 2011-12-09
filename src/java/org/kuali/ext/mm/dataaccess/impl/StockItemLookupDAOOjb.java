package org.kuali.ext.mm.dataaccess.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.StockCount;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.Zone;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.dataaccess.StockItemLookupServiceDAO;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.dao.impl.PlatformAwareDaoBaseOjb;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.util.TransactionalServiceUtils;


public class StockItemLookupDAOOjb extends PlatformAwareDaoBaseOjb implements
        StockItemLookupServiceDAO, StockItemLookupSQLQueries {

    public StockItemLookupDAOOjb(){
    }

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(StockItemLookupDAOOjb.class);


    public Warehouse getWarehouse(String WarehouseCD) {

        if (StringUtils.isEmpty(WarehouseCD))
            return null;

        Criteria criteria = new Criteria();
        criteria.addEqualTo(MMConstants.Warehouse.WAREHOUSE_CD, WarehouseCD);

        return (Warehouse) getPersistenceBrokerTemplate().getObjectByQuery(
                QueryFactory.newQuery(Warehouse.class, criteria));

    }

    /**
     * Returns the list of Stock count Ids to be included in the worksheet
     */
    public List<String> getStockCountsForWorksheet(String wareHouseCode, String zoneCode,
            String countFrequency, boolean isQtyLesserThanZero) {

        List<String> scountIds = new ArrayList<String>(0);
        Criteria stockCountCriteria = new Criteria();
        if (!StringUtils.isEmpty(wareHouseCode)) {
            Criteria wareHouseCodeCriteria = new Criteria();
            wareHouseCodeCriteria.addEqualTo("bin.zone.warehouseCd", wareHouseCode);
            stockCountCriteria.addAndCriteria(wareHouseCodeCriteria);
        }

        if (!StringUtils.isEmpty(zoneCode)) {
            Criteria zoneCodeCriteria = new Criteria();
            zoneCodeCriteria.addEqualTo("bin.zone.zoneCd", zoneCode);
            stockCountCriteria.addAndCriteria(zoneCodeCriteria);
        }

        if (!StringUtils.isEmpty(countFrequency)) {
            Criteria countFrequencyCriteria = new Criteria();
            countFrequencyCriteria.addEqualTo("stock.cycleCntCd", countFrequency);
            stockCountCriteria.addAndCriteria(countFrequencyCriteria);
        }

        Criteria worksheetCountIdCriteria = new Criteria();
        worksheetCountIdCriteria.addIsNull("worksheetCountId");
        stockCountCriteria.addAndCriteria(worksheetCountIdCriteria);

        if (isQtyLesserThanZero) {
            Criteria nonZeroBinCriteria = new Criteria();
            nonZeroBinCriteria.addGreaterThan("snapshotQty", 0);
            stockCountCriteria.addAndCriteria(nonZeroBinCriteria);
        }

        ReportQueryByCriteria reportQuery = QueryFactory.newReportQuery(StockCount.class,
                stockCountCriteria, true);
        reportQuery.addOrderBy("stock.stockDistributorNbr", true);
        reportQuery.addOrderBy("bin.zone.warehouseCd", true);
        reportQuery.addOrderBy("bin.zone.zoneCd", true);
        reportQuery.addOrderBy("bin.binNbr", true);
        reportQuery.addOrderBy("bin.shelfIdNbr", true);
        reportQuery.addOrderBy("bin.shelfId", true);
        reportQuery.setAttributes(new String[] { "stockCountId" });

        for (Iterator iterator = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(
                reportQuery); iterator.hasNext();) {
            scountIds.add(((java.math.BigDecimal) ((Object[]) iterator.next())[0]).toPlainString());
        }

        return removeDuplicates(scountIds);
    }

    public List<List<String>> getSubLists(List<String> stockIds, int numOfCounters) {

        List<List<String>> subLists = new ArrayList<List<String>>();

        if (numOfCounters < 1) {
            subLists.add(stockIds);
            return subLists;
        }

        int size = stockIds.size();
        int index = size / numOfCounters;
        int remNumOfObj = size % numOfCounters;
        int startIndex = 0;
        int endIndex = index;
        List<String> dupIds = null;

        for (int i = 0; i < numOfCounters; i++) {
            List<String> subList = stockIds.subList(startIndex, endIndex);
            List<String> ll = new ArrayList<String>(subList);
            subLists.add(ll);
            startIndex = endIndex;
            endIndex = endIndex + index;
        }

        if (remNumOfObj > 0) {
            endIndex = endIndex - index;
            dupIds = stockIds.subList(endIndex, stockIds.size());
        }

        for (int i = 0; i < remNumOfObj; i++) {
            String val = dupIds.get(i);
            int listIndex = i < numOfCounters ? i : i % numOfCounters;
            subLists.get(listIndex).add(val);
        }

        return subLists;
    }

    public List<List<StockCount>> getSubListsData(List<StockCount> stockIds, int numOfCounters) {

        List<List<StockCount>> subLists = new ArrayList<List<StockCount>>();

        if (numOfCounters < 1) {
            subLists.add(stockIds);
            return subLists;
        }

        int size = stockIds.size();
        int index = size / numOfCounters;
        int remNumOfObj = size % numOfCounters;
        int startIndex = 0;
        int endIndex = index;
        List<StockCount> dupIds = null;

        for (int i = 0; i < numOfCounters; i++) {
            List<StockCount> subList = stockIds.subList(startIndex, endIndex);
            List<StockCount> ll = new ArrayList<StockCount>(subList);
            subLists.add(ll);
            startIndex = endIndex;
            endIndex = endIndex + index;
        }

        if (remNumOfObj > 0) {
            endIndex = endIndex - index;
            dupIds = stockIds.subList(endIndex, stockIds.size());
        }

        for (int i = 0; i < remNumOfObj; i++) {
            StockCount val = dupIds.get(i);
            int listIndex = i < numOfCounters ? i : i % numOfCounters;
            subLists.get(listIndex).add(val);
        }

        return subLists;
    }


    public List<WorksheetCountDocument> updateWorksheetCountData(List<String> lisStockCount,
            List<WorksheetCountDocument> lisDocs, int numOfCounters) {
        long startTime = System.currentTimeMillis();

        List<List<String>> subList = this.getSubLists(lisStockCount, numOfCounters);

        if (subList.size() != lisDocs.size())
            return null;

        int size = lisDocs.size();


        for (int i = 0; i < size; i++) {
            String docNbr = lisDocs.get(i).getDocumentNumber();
            List<String> stockIds = subList.get(i);

            int blockSize = stockIds.size();

            int index = blockSize > MMConstants.MAX_RECORDS_TO_UPDATE ? blockSize
                    / MMConstants.MAX_RECORDS_TO_UPDATE : 0;

            int remObjSize = index > 0 ? blockSize % MMConstants.MAX_RECORDS_TO_UPDATE : blockSize;

            int listCount = 0;

            if (index > 0) {
                List<String> scountIds = new ArrayList<String>(0);
                int uindex = 1;

                for (int k = 0; k < index; k++) {
                    uindex = 1;

                    for (int j = 0; j < MMConstants.MAX_RECORDS_TO_UPDATE; j++) {
                        scountIds.add(stockIds.get(listCount));
                        listCount++;
                        uindex++;
                    }
                    updateStockCount(scountIds, docNbr);
                }
            }

            if (remObjSize > 0) {
                List<String> scountIds = new ArrayList<String>(0);

                int uindex = 1;

                for (int j = 0; j < remObjSize; j++) {
                    scountIds.add(stockIds.get(listCount));
                    listCount++;
                    uindex++;
                }
                updateStockCount(scountIds, docNbr);
            }
        }
        long endTime = System.currentTimeMillis();
        LOG.debug("Time taken   updateCountWorksheetCountdata  " + (endTime - startTime));

        return lisDocs;
    }

    public List<WorksheetCountDocument> updateWorksheetCountDataObject(
            List<StockCount> lisStockCount, List<WorksheetCountDocument> lisDocs, int numOfCounters) {
        long startTime = System.currentTimeMillis();

        List<List<StockCount>> subList = this.getSubListsData(lisStockCount, numOfCounters);

        if (subList.size() != lisDocs.size())
            return null;

        int size = lisDocs.size();


        for (int i = 0; i < size; i++) {
            String docNbr = lisDocs.get(i).getDocumentNumber();
            List<StockCount> stockIds = subList.get(i);

            int blockSize = stockIds.size();

            int index = blockSize > MMConstants.MAX_RECORDS_TO_UPDATE ? blockSize
                    / MMConstants.MAX_RECORDS_TO_UPDATE : 0;

            int remObjSize = index > 0 ? blockSize % MMConstants.MAX_RECORDS_TO_UPDATE : blockSize;

            int listCount = 0;

            if (index > 0) {
                List<StockCount> scount = new ArrayList<StockCount>(0);
                int uindex = 1;

                for (int k = 0; k < index; k++) {
                    uindex = 1;

                    for (int j = 0; j < MMConstants.MAX_RECORDS_TO_UPDATE; j++) {
                        scount.add(stockIds.get(listCount));
                        listCount++;
                        uindex++;
                    }
                    updateStockCountObject(scount, docNbr);
                }
            }

            if (remObjSize > 0) {
                List<StockCount> scount = new ArrayList<StockCount>(0);

                int uindex = 1;

                for (int j = 0; j < remObjSize; j++) {
                    scount.add(stockIds.get(listCount));
                    listCount++;
                    uindex++;
                }
                updateStockCountObject(scount, docNbr);
            }
        }
        long endTime = System.currentTimeMillis();
        LOG.debug("Time taken   updateCountWorksheetCountdata  " + (endTime - startTime));

        return lisDocs;
    }

    private void updateStockCount(List<String> scountIds, String docNbr) {

        List<StockCount> scount = new ArrayList<StockCount>(0);

        Map<String, List<String>> criteria = new HashMap<String, List<String>>();

        criteria.put(MMConstants.StockCount.STOCK_COUNT_ID, scountIds);
        scount = (List<StockCount>) MMServiceLocator.getBusinessObjectService().findMatching(
                StockCount.class, criteria);

        if (!MMUtil.isCollectionEmpty(scount)) {
            scount = updateDocnumbersForStockCount(scount, docNbr);
            MMServiceLocator.getBusinessObjectService().save(scount);
        }

    }

    private void updateStockCountObject(List<StockCount> scountIds, String docNbr) {

        if (!MMUtil.isCollectionEmpty(scountIds)) {
            scountIds = updateDocnumbersForStockCount(scountIds, docNbr);
            MMServiceLocator.getBusinessObjectService().save(scountIds);
        }

    }

    private List<StockCount> updateDocnumbersForStockCount(List<StockCount> listData,
            String docNumber) {

        for (StockCount scount : listData) {
            scount.setWorksheetCountId(docNumber);
        }
        return listData;
    }

    private List<String> removeDuplicates(List<String> lisVals) {
        List<String> result = new ArrayList<String>(0);
        for (String st : lisVals) {
            if (!result.contains(st))
                result.add(st);
        }
        return result;
    }

    public Zone getZone(String WarehouseCD, String zoneCd) {

        if (StringUtils.isEmpty(zoneCd) || StringUtils.isEmpty(WarehouseCD))
            return null;

        Criteria criteria = new Criteria();
        criteria.addEqualTo(MMConstants.Warehouse.WAREHOUSE_CD, WarehouseCD);
        criteria.addEqualTo(MMConstants.Zone.ZONE_CD, zoneCd);
        return (Zone) getPersistenceBrokerTemplate().getObjectByQuery(
                QueryFactory.newQuery(Zone.class, criteria));

    }

    public List<String> getAllStockIdsForPickList(List<String> lisData) {
        List<String> stockIds = new ArrayList<String>();
        if (MMUtil.isCollectionEmpty(lisData))
            return null;
        Criteria criteria = new Criteria();
        criteria.addIn(MMConstants.PickListLine.STOCK_ID, lisData);

        ReportQueryByCriteria reportQuery = QueryFactory.newReportQuery(PickListLine.class,
                criteria);
        reportQuery.setAttributes(new String[] { MMConstants.PickListLine.STOCK_ID });

        for (Iterator iterator = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(
                reportQuery); iterator.hasNext(); stockIds
                .add(((java.math.BigDecimal) ((Object[]) TransactionalServiceUtils
                        .retrieveFirstAndExhaustIterator(iterator))[0]).toPlainString()))
            ;

        return stockIds;

    }

    public WorksheetCountDocument getWorkSheetCountDocData(String docNbr) {

        if (StringUtils.isEmpty(docNbr))
            return null;

        Criteria criteria = new Criteria();
        criteria.addEqualTo("documentNumber", docNbr);

        return (WorksheetCountDocument) getPersistenceBrokerTemplate().getObjectByQuery(
                QueryFactory.newQuery(WorksheetCountDocument.class, criteria));

    }

    public void updateCountWorksheetStatus(String docNumber, String status) {

        WorksheetCountDocument worksheetDocument = this.getWorkSheetCountDocData(docNumber);
        if (ObjectUtils.isNotNull(worksheetDocument)) {
            if (status != null && status.equals(MMConstants.WorksheetStatus.WORKSHEET_CLOSED))
                worksheetDocument.setWorksheetCompletionDt(new Timestamp(new java.util.Date()
                        .getTime()));

            worksheetDocument.setWorksheetStatusCode(status);
            this.getPersistenceBrokerTemplate().store(worksheetDocument);
        }
    }


    public Zone getZone(String zoneCd) {
        Zone zone = null;
        Criteria criteria = new Criteria();
        criteria.addEqualTo(MMConstants.Zone.ZONE_CD, zoneCd);
        zone = (Zone) getPersistenceBrokerTemplate().getObjectByQuery(
                QueryFactory.newQuery(Zone.class, criteria));
        return zone;
    }

    public List<WorksheetCountDocument> getWorksheetCountDocForIDs(List<String> docIDs) {

        Collection<WorksheetCountDocument> colDocs = null;

        if (MMUtil.isCollectionEmpty(docIDs))
            return null;

        Criteria criteria = new Criteria();
        criteria.addIn(MMConstants.WorksheetCountDocument.DOC_NUMBER, docIDs);

        colDocs = getPersistenceBrokerTemplate().getCollectionByQuery(
                QueryFactory.newQuery(WorksheetCountDocument.class, criteria));
        return (List) (colDocs);

    }

}
