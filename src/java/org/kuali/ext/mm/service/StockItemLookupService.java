package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.StockCount;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.Zone;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.rice.kim.api.identity.Person;

import java.util.List;

public interface StockItemLookupService {

    public List<WorksheetCountDocument> getWorksheetCountDocForIDs(List<String> docIDs);

    public List<WorksheetCountDocument> getNewStockCounts(String wareHouseCode, String zoneCd,
            String countFrequency, int numOfCounters, boolean isQtyGreaterThanZero,
            boolean fullInventory);

    public void createNewStockCounts(String wareHouseCode, String zoneCd,
            boolean isQtyGreaterThanZero);

    public Warehouse getWarehouse(String WarehouseCD);

    public Zone getZone(String WarehouseCD, String zoneCd);

    public Zone getZone(String zoneCd);

    public void updateCountWorksheetStatus(String docNumber, String status);

    public WorksheetCountDocument getWorkSheetCountDocData(String docNbr);

    public List<String> getStockIDsForPickList(List<String> lisStockIds);

    public List<WorksheetCountDocument> createNewWorksheetDocuments(String warehouseCd,
            String countFrequency, int numOfCounters, List<List<StockCount>> lisCounts,
            WorksheetCountDocument wdoc, boolean fullInventory);


    public void setPickListIndicator(List<WorksheetCountDocument> lisDocs);

    public Person getPersonWithName(String name);

}
