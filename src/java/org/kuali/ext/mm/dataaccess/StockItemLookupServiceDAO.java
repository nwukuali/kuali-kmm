package org.kuali.ext.mm.dataaccess;

import java.util.List;

import org.kuali.ext.mm.businessobject.StockCount;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.Zone;
import org.kuali.ext.mm.document.WorksheetCountDocument;

public interface StockItemLookupServiceDAO {

    public Zone getZone(String zoneCd);

    public void updateCountWorksheetStatus(String docNumber, String status);

    public WorksheetCountDocument getWorkSheetCountDocData(String docNbr);

    public Warehouse getWarehouse(String WarehouseCD);

    public Zone getZone(String WarehouseCD, String zoneCd);

    public List<WorksheetCountDocument> getWorksheetCountDocForIDs(List<String> docIDs);

    public List<String> getStockCountsForWorksheet(String wareHouseCode, String zoneCode,
            String countFrequency, boolean isQtyLesserThanZero);

    public List<String> getAllStockIdsForPickList(List<String> lisData);

    public List<WorksheetCountDocument> updateWorksheetCountData(List<String> lisStockCount,
            List<WorksheetCountDocument> lisDocs, int numOfCounters);

    public List<WorksheetCountDocument> updateWorksheetCountDataObject(
            List<StockCount> lisStockCount, List<WorksheetCountDocument> lisDocs, int numOfCounters);


}
