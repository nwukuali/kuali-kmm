package org.kuali.ext.mm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.ext.mm.businessobject.StockCount;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.businessobject.Zone;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.StockItemLookupServiceDAO;
import org.kuali.ext.mm.document.WorksheetCountDocument;
import org.kuali.ext.mm.service.StockItemLookupService;
import org.kuali.ext.mm.sys.batch.dataaccess.StockCountDao;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.PersonService;
import org.kuali.rice.kns.UserSession;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.ObjectUtils;


public class StockItemLookupServiceImpl implements StockItemLookupService {

    private StockItemLookupServiceDAO aStockItemLookupServiceDAO;

    private org.apache.log4j.Logger loggerAc = org.apache.log4j.Logger
            .getLogger(StockItemLookupServiceImpl.class);

    private Document createDocument(String docType, WorksheetCountDocument wdoc) {
        String desc = "Worksheet Count";
        UserSession current = GlobalVariables.getUserSession();
        if (wdoc != null) {
            GlobalVariables.setUserSession(new UserSession(getInitiatorPrincipalName(wdoc)));
            desc = "Reprinted from " + wdoc.getDocumentNumber();
        }
        Document dd;
        try {
            dd = KNSServiceLocator.getDocumentService().getNewDocument(docType);
        }
        catch (WorkflowException e) {
            throw new RuntimeException(e);
        }
        GlobalVariables.setUserSession(current);
        dd.getDocumentHeader().setDocumentDescription(desc);
        return dd;
    }

    private List<WorksheetCountDocument> createNewWorksheetDocuments(String warehouseCd,
            int numOfCounters, String countFrequency, boolean fullInventory) {

        return createNewWorksheetDocuments(warehouseCd, countFrequency, numOfCounters, null, null,
                fullInventory);
    }

    /**
     * creates list of worksheets depending upon the number of counters and sets the document parameters
     * 
     * @see org.kuali.ext.mm.service.StockItemLookupService#createNewWorksheetDocuments(java.lang.String, java.lang.String, int,
     *      java.util.List, org.kuali.ext.mm.document.WorksheetCountDocument)
     */
    public List<WorksheetCountDocument> createNewWorksheetDocuments(String warehouseCd,
            String countFrequency, int numOfCounters, List<List<StockCount>> lisCounts,
            WorksheetCountDocument wdoc, boolean fullInventory) {
        List<WorksheetCountDocument> docList = new ArrayList<WorksheetCountDocument>();
        Warehouse warehouse = this.getWarehouse(warehouseCd);
        for (int wcount = 0; wcount < numOfCounters; wcount++) {
            try {
                WorksheetCountDocument workSheetCountDoc = (WorksheetCountDocument) createDocument(
                        MMConstants.WorksheetCountDocument.DOC_TYPE_NAME, wdoc);
                if (wdoc != null) {
                    workSheetCountDoc.setWorksheetCnt(wdoc.getWorksheetCnt() + 1);
                    workSheetCountDoc.setParentDocumentNumber(wdoc.getDocumentNumber());
                    workSheetCountDoc.setParentDocument(wdoc);
                    workSheetCountDoc.setCycleCntCd(wdoc.getCycleCntCd());
                }
                else {
                    workSheetCountDoc.setWorksheetCnt(0);
                    workSheetCountDoc.setCycleCntCd(countFrequency);
                }

                workSheetCountDoc.setWarehouse(warehouse);
                workSheetCountDoc.setWarehouseCd(warehouseCd);
                workSheetCountDoc
                        .setWorksheetStatusCode(MMConstants.WorksheetStatus.WORKSHEET_PRINTED);
                workSheetCountDoc.setWorksheetOriginalDt(MMUtil.getCurrentTimestamp());
                workSheetCountDoc.setFullInventory(fullInventory);
                List<WorksheetCountDocument> lisDcos = new ArrayList<WorksheetCountDocument>(0);
                saveDocument(wdoc, workSheetCountDoc);
                lisDcos.add(workSheetCountDoc);

                if (!MMUtil.isCollectionEmpty(lisCounts)) {
                    workSheetCountDoc.getStockCounts().addAll((lisCounts.get(wcount)));
                    this.aStockItemLookupServiceDAO.updateWorksheetCountDataObject(lisCounts
                            .get(wcount), lisDcos, 1);
                }
                docList.add(workSheetCountDoc);
            }
            catch (WorkflowException we) {
                throw new RuntimeException(we);
            }
        }

        return docList;

    }


    /**
     * @param wdoc
     * @return
     */
    private String getInitiatorPrincipalName(WorksheetCountDocument wdoc) {
        if (ObjectUtils.isNull(wdoc.getDocumentHeader())
                || ObjectUtils.isNull(wdoc.getDocumentHeader().getWorkflowDocument())
                || wdoc.getDocumentHeader().getWorkflowDocument().getInitiatorPrincipalId() == null) {
            return "";

        }
        String initiatorPrincipalId = wdoc.getDocumentHeader().getWorkflowDocument()
                .getInitiatorPrincipalId();
        Person person = SpringContext.getBean(PersonService.class).getPerson(initiatorPrincipalId);
        if (ObjectUtils.isNull(person)) {
            return null;
        }
        String principalName = person.getPrincipalName();
        return principalName;
    }

    public List<WorksheetCountDocument> getNewStockCounts(String wareHouseCode, String zoneCd,
            String countFrequency, int numOfCounters, boolean isQtyGreaterThanZero,
            boolean fullInventory) {
        int counters = numOfCounters;
        long startTime = System.currentTimeMillis();

        List<String> lisIds = this.aStockItemLookupServiceDAO.getStockCountsForWorksheet(
                wareHouseCode, zoneCd, countFrequency, isQtyGreaterThanZero);

        if (MMUtil.isCollectionEmpty(lisIds))
            return null;

        int size = lisIds.size();

        if (counters > size) {
            if (size > MMConstants.WorksheetCountDocument.DEFAULT_NUMBER_OF_COUNTERS)
                counters = MMConstants.WorksheetCountDocument.DEFAULT_NUMBER_OF_COUNTERS;
            else
                counters = 1;
        }

        List<WorksheetCountDocument> lisDocs = this.createNewWorksheetDocuments(wareHouseCode,
                counters, countFrequency, fullInventory);
        // List<WorksheetCountDocument> result = this.worksheetDAO.updateWorksheetCountData(lisIds, lisDocs, numOfCounters);
        List<WorksheetCountDocument> result = this.aStockItemLookupServiceDAO
                .updateWorksheetCountData(lisIds, lisDocs, counters);

        long endTime = System.currentTimeMillis();
        loggerAc.debug("Time taken for getNewStockCounts in action " + (endTime - startTime));
        return result;
    }

    public Person getPersonWithName(String name) {
        return org.kuali.rice.kim.bo.impl.PersonImpl.getPersonService().getPersonByPrincipalName(
                name);
    }

    public List<String> getStockIDsForPickList(List<String> lisStockIds) {
        return this.aStockItemLookupServiceDAO.getAllStockIdsForPickList(lisStockIds);
    }

    public StockItemLookupServiceDAO getStockItemLookupServiceDAO() {
        return aStockItemLookupServiceDAO;
    }

    public Warehouse getWarehouse(String WarehouseCD) {
        return this.aStockItemLookupServiceDAO.getWarehouse(WarehouseCD);
    }

    public WorksheetCountDocument getWorkSheetCountDocData(String docNbr) {
        return aStockItemLookupServiceDAO.getWorkSheetCountDocData(docNbr);
    }

    public List<WorksheetCountDocument> getWorksheetCountDocForIDs(List<String> docIDs) {
        return this.aStockItemLookupServiceDAO.getWorksheetCountDocForIDs(docIDs);

    }

    public Zone getZone(String zoneCd) {
        return this.aStockItemLookupServiceDAO.getZone(zoneCd);
    }

    public Zone getZone(String WarehouseCD, String zoneCd) {
        return this.aStockItemLookupServiceDAO.getZone(WarehouseCD, zoneCd);
    }

    /**
     * @param wdoc
     * @param workSheetCountDoc
     * @throws WorkflowException
     */
    private void saveDocument(WorksheetCountDocument wdoc, WorksheetCountDocument workSheetCountDoc)
            throws WorkflowException {
        UserSession current = GlobalVariables.getUserSession();
        if (wdoc != null) {
            String principalName = getInitiatorPrincipalName(wdoc);
            GlobalVariables.setUserSession(new UserSession(principalName));
        }
        KNSServiceLocator.getDocumentService().saveDocument(workSheetCountDoc);
        GlobalVariables.setUserSession(current);
    }

    public void setAStockItemLookupServiceDAO(StockItemLookupServiceDAO stockItemLookupServiceDAO) {
        aStockItemLookupServiceDAO = stockItemLookupServiceDAO;
    }

    public void setPickListIndicator(List<WorksheetCountDocument> lisDocs) {
        long startTime = System.currentTimeMillis();
        for (WorksheetCountDocument wdoc : lisDocs) {
            List<String> lisIDs = this.getStockIDsForPickList(wdoc.getStockIds());
            if (MMUtil.isCollectionEmpty(lisIDs))
                continue;
            for (StockCount scount : wdoc.getStockCounts()) {
                String stockId = scount.getStockId();
                if (lisIDs.contains(stockId))
                    scount.setPickListIndicator("*");
            }
        }
        long endTime = System.currentTimeMillis();
        loggerAc.debug("Time taken for set picklist indicator in action " + (endTime - startTime));
    }

    public void setStockItemLookupServiceDAO(StockItemLookupServiceDAO aStockItemLookupServiceDAO) {
        this.aStockItemLookupServiceDAO = aStockItemLookupServiceDAO;
    }

    public List<WorksheetCountDocument> submitDocuments(List<WorksheetCountDocument> documents)
            throws Exception {
        // docList = submitDocuments(docList);
        List<WorksheetCountDocument> lisDocs = new ArrayList<WorksheetCountDocument>(0);

        for (WorksheetCountDocument doc : documents) {
            // doc.refresh();
            String message = doc.getDocumentNumber() + " Submitted ";
            // doc.prepareForWorkflow();
            // KNSServiceLocator.getDocumentService().prepareWorkflowDocument(doc);
            doc.prepareForWorkflow();
            doc.getDocumentHeader().getWorkflowDocument().routeDocument(message);
            doc.prepareForSave();
            doc.getDocumentHeader().getWorkflowDocument().getRouteHeader().setDocRouteStatus("S");
            KNSServiceLocator.getWorkflowDocumentService().save(
                    doc.getDocumentHeader().getWorkflowDocument(), null);

            lisDocs.add(doc);
            /*
             * doc.refresh(); doc.refreshPessimisticLocks();
             * doc.getDocumentHeader().getWorkflowDocument().getRouteHeader().setDocRouteStatus("S");
             * KNSServiceLocator.getBusinessObjectService().save(doc);
             */
            // SaveDocument(doc);
        }
        return lisDocs;
    }

    public void updateCountWorksheetStatus(String docNumber, String status) {
        this.aStockItemLookupServiceDAO.updateCountWorksheetStatus(docNumber, status);
    }

    /**
     * @see org.kuali.ext.mm.service.StockItemLookupService#createNewStockCounts(java.lang.String, java.lang.String, boolean)
     */
    public void createNewStockCounts(String wareHouseCode, String zoneCd,
            boolean isQtyGreaterThanZero) {
        // insert all the records specific to this selection
        SpringContext.getBean(StockCountDao.class).fullInventoryStockCountInsert(wareHouseCode,
                zoneCd, isQtyGreaterThanZero);
    }

}
