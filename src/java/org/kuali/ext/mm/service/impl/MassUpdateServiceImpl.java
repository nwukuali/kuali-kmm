package org.kuali.ext.mm.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.*;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.document.MassUpdateDocument;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.MassUpdateService;
import org.kuali.ext.mm.service.StockHistoryService;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.sys.valueobject.MassUpdateUploadSummary;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.ParseUtil;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author schneppd
 *
 */
@Transactional
public class MassUpdateServiceImpl implements MassUpdateService {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MassUpdateServiceImpl.class);
    

    /**
     * @see org.kuali.ext.mm.service.MassUpdateService#loadAgreementMassUpdateFile(java.io.File, java.lang.String)
     */
    public MassUpdateUploadSummary loadAgreementMassUpdateFile(File uploadFile, String errorPath) {
        MassUpdateUploadSummary summary = new MassUpdateUploadSummary();
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(uploadFile));           
            summary = initializeAgreementMassUpdateDetails(reader, errorPath);
        }
        catch (FileNotFoundException e) {
            LOG.error(e);        
            return null;
        }
        catch (IOException e) {
            LOG.error(e);        
            return null;
        }
        finally {
            if(reader != null)
                try {
                    reader.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    LOG.error("Unable to close the input stream.");
                }
        }
        return summary;
    }


    /**
     * @param reader
     * @param agreementNumber
     * @param errorPath
     * @return A MassUpdateUploadSummary containing MassUpdateDetail lines and file line count.
     * @throws IOException
     */
    private MassUpdateUploadSummary initializeAgreementMassUpdateDetails(BufferedReader reader, String errorPath) throws IOException {
        MassUpdateUploadSummary summary = new MassUpdateUploadSummary();
        
        String lineData = reader.readLine();        
        String[] headerLine = ParseUtil.parseQuoted(',', lineData);        
        Map<String, Integer> columnMap = getDataFileColumnMap(headerLine, 
                MMConstants.MassUpdateDocument.AGREEMENT_MASS_UPDATE_COLUMNS);
        if(columnMap == null) {
            GlobalVariables.getMessageMap().putError(errorPath, 
                    MMKeyConstants.AgreementMassMaintenance.ERROR_INVALID_COLUMNS,
                    String.valueOf(MMConstants.MassUpdateDocument.AGREEMENT_MASS_UPDATE_COLUMNS));
            return summary;
        }
        
        while(StringUtils.isNotBlank(lineData = reader.readLine())) {
            summary.setLineCount(summary.getLineCount() + 1);
            String[] columnData = ParseUtil.parseQuoted(',', lineData);
            MassUpdateDetail newDetail = createMassUpdateDetail(columnData, summary.getLineCount(), headerLine.length, columnMap, errorPath);
            if(newDetail != null) {          
                summary.getMassUpdateDetails().add(newDetail);
            }
        }
        return summary;
    }

    /**
     * @param columnData
     * @param lineCount
     * @param length
     * @param columnMap
     * @param errorPath
     * @returns a populated MassUpdateDetail object if the data is correct, null otherwise. 
     */
    private MassUpdateDetail createMassUpdateDetail(String[] columnData, int lineCount, int columnCount, Map<String, Integer> columnMap, String errorPath) {
        boolean validLine = true;
        if(columnData.length != columnCount) {
            GlobalVariables.getMessageMap().putError(errorPath, 
                    MMKeyConstants.AgreementMassMaintenance.ERROR_INVALID_COLUMN_COUNT,                    
                    String.valueOf(columnCount),
                    String.valueOf(lineCount+1));
            return null;
        }          
        String stockNumber = getColumnData(MMConstants.MassUpdateDocument.COLUMN_STOCK_NBR, columnData, columnMap);
        String stockCost = getColumnData(MMConstants.MassUpdateDocument.COLUMN_STOCK_CST, columnData, columnMap);
        StockService stockService = MMServiceLocator.getStockService();
        
        MassUpdateDetail massUpdateDetail = new MassUpdateDetail();
        Stock stockItem = stockService.getStockByDistributorNumber(stockNumber);
        if(stockItem == null) {
            GlobalVariables.getMessageMap().putError(errorPath, 
                    MMKeyConstants.AgreementMassMaintenance.ERROR_STOCK_ITEM_NOT_EXIST,
                    stockNumber,
                    String.valueOf(lineCount+1));
            validLine = false;
        }
        else {
            massUpdateDetail.setStock(stockItem);
            massUpdateDetail.setStockId(stockItem.getStockId());            
        }
        
        MMDecimal mmdStockCost = null;
        try {
            if(StringUtils.isNotBlank(stockCost))
                mmdStockCost = new MMDecimal(Double.parseDouble(stockCost));
            massUpdateDetail.setStockCost(mmdStockCost);
        }
        catch (NumberFormatException e) {
            GlobalVariables.getMessageMap().putError(errorPath, 
                    MMKeyConstants.AgreementMassMaintenance.ERROR_STOCK_COST_INVALID,
                    String.valueOf(lineCount+1));
            validLine = false;
        }
        
        if(!validLine) {
            massUpdateDetail = null;
        }
        return massUpdateDetail;        
    }

    /**
     * @param fullLine - all line data columns
     * @param columnMap - map of columnHeaders to column indexes
     * @param columnHeader - column header for a data column
     * @return the data contained in fullLine for the column indicated by columnHeader
     */
    private String getColumnData(String columnHeader, String[] fullLine, Map<String, Integer> columnMap) {        
        return fullLine[columnMap.get(columnHeader.toLowerCase())];
    }

    /**
     * @param columns - variable number of column names
     * @return A map relating column header names to columns indexes
     */
    private Map<String, Integer> getDataFileColumnMap(String[] dataFileColumnHeaders, String[] requiredColumns) {
        Map<String, Integer> columnMap = new HashMap<String, Integer>();
        Set<String> columnsToMap = new HashSet<String>();
        
        for(String rColumn : requiredColumns)
            columnsToMap.add(rColumn.toLowerCase());
        
        for(int i=0; i < dataFileColumnHeaders.length; i++) {
            if(columnsToMap.contains(dataFileColumnHeaders[i].toLowerCase())) {
                columnMap.put(dataFileColumnHeaders[i].toLowerCase(), i);
            }
        }
       
        return (columnMap.size() == requiredColumns.length) ? columnMap : null;
    }



    /**
     * @see org.kuali.ext.mm.service.MassUpdateService#processMassUpdateDocument(org.kuali.ext.mm.document.MassUpdateDocument)
     */
    public Map<String, MMDecimal> processMassUpdateDocument(MassUpdateDocument massUpdateDocument) {
        Map<String, MMDecimal> costChangeMap = new HashMap<String, MMDecimal>();
        
        BusinessObjectService businessObjectService = KRADServiceLocator.getBusinessObjectService();
        StockService stockService = MMServiceLocator.getStockService();
        StockHistoryService historyService = MMServiceLocator.getStockHistoryService();        
        
        for(MassUpdateDetail detail : massUpdateDocument.getMassUpdateDetails()) {
            if(ObjectUtils.isNull(detail.getStock()) && !StringUtils.isBlank(detail.getStockId()))
                detail.refreshReferenceObject(MMConstants.MassUpdateDetail.STOCK);
            Stock stock = detail.getStock();
            //Update the agreement
            stock.setAgreementNbr(massUpdateDocument.getNewAgreementNumber());
            
            //Update the stock cost
            StockCost stockCost = stockService.getStockCostForStock(detail.getStockId());
            MMDecimal oldCost = stockCost.getStockCst();
            MMDecimal newCost = detail.getStockCost();
            if(newCost != null && !oldCost.equals(newCost)) {
                stock.refreshReferenceObject(MMConstants.Stock.STOCK_BALANCES);
                for(StockBalance sbal : stock.getStockBalances()) {                    
                    String warehouseCode = sbal.getBin().getZone().getWarehouseCd();
                    if(!costChangeMap.containsKey(warehouseCode))
                        costChangeMap.put(warehouseCode, new MMDecimal(0.0));                    
                    costChangeMap.put(warehouseCode, 
                            costChangeMap.get(warehouseCode)
                                .add(newCost.subtract(oldCost)
                                        .multiply(new MMDecimal(sbal.getQtyOnHand()))));                    
                    StockHistory stockHistory = historyService.createStockHistoryForCostAdjustment(newCost, oldCost, sbal);
                    stock.getStockHistoryList().add(stockHistory);
                }
                stock.getStockPrices().add(stockService
                        .updateStockCost(detail.getStockId(), newCost, MMConstants.CostCode.STANDARD_PRICE));
            }
            businessObjectService.save(stock);            
            businessObjectService.save(stock.getStockPrices());
        }
        
        return costChangeMap;
    }
}
