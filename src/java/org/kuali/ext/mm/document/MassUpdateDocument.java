package org.kuali.ext.mm.document;
	
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.kuali.ext.mm.businessobject.Agreement;
import org.kuali.ext.mm.businessobject.MassUpdateDetail;
import org.kuali.ext.mm.businessobject.StoresTransactionalDocumentBase;
import org.kuali.ext.mm.businessobject.Warehouse;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.gl.GeneralLedgerPostable;
import org.kuali.ext.mm.gl.WarehouseGlGroup;
import org.kuali.ext.mm.gl.service.GeneralLedgerBuilderService;
import org.kuali.ext.mm.gl.service.GeneralLedgerProcessor;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.ext.mm.service.MassUpdateService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.kew.dto.DocumentRouteStatusChangeDTO;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.DataDictionaryService;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.workflow.service.KualiWorkflowDocument;

	
public class MassUpdateDocument extends StoresTransactionalDocumentBase  implements
    GeneralLedgerPostable {

    private String previousAgreementNumber;
		
	private String newAgreementNumber;
	
	private String updateFileName;
	
	private boolean finalIndicator;
	
	private Agreement previousAgreement;
	
	private Agreement newAgreement;
	
	private List<MassUpdateDetail> massUpdateDetails = new ArrayList<MassUpdateDetail>();
	
	@Transient
	private Map<String, MMDecimal> warehouseCostChangeMap = new HashMap<String, MMDecimal>();
	
	private List<FinancialGeneralLedgerPendingEntry> financialGeneralLedgerPendingEntries;

	public String getPreviousAgreementNumber() {
		return previousAgreementNumber;
	}
	
	public void setPreviousAgreementNumber(String previousAgreementNumber) {
		this.previousAgreementNumber = previousAgreementNumber;
	}
		
	public String getNewAgreementNumber() {
		return newAgreementNumber;
	}
	
	public void setNewAgreementNumber(String newAgreementNumber) {
		this.newAgreementNumber = newAgreementNumber;
	}

    public void setFinalIndicator(boolean finalIndicator) {
        this.finalIndicator = finalIndicator;
    }

    public boolean isFinalIndicator() {
        return finalIndicator;
    }

    public void setMassUpdateDetails(List<MassUpdateDetail> massUpdateDetails) {
        this.massUpdateDetails = massUpdateDetails;
    }

    public List<MassUpdateDetail> getMassUpdateDetails() {
        return massUpdateDetails;
    }

    public void setPreviousAgreement(Agreement previousAgreement) {
        this.previousAgreement = previousAgreement;
    }

    public Agreement getPreviousAgreement() {
        return previousAgreement;
    }

    public void setNewAgreement(Agreement newAgreement) {
        this.newAgreement = newAgreement;
    }

    public Agreement getNewAgreement() {
        return newAgreement;
    }

    public void setUpdateFileName(String updateFileName) {
        this.updateFileName = updateFileName;
    }

    public String getUpdateFileName() {
        return updateFileName;
    }

    public void setWarehouseCostChangeMap(Map<String, MMDecimal> warehouseCostChangeMap) {
        this.warehouseCostChangeMap = warehouseCostChangeMap;
    }

    public Map<String, MMDecimal> getWarehouseCostChangeMap() {
        return warehouseCostChangeMap;
    }
    
    public void doRouteStatusChange(DocumentRouteStatusChangeDTO statusChangeEvent) {
        super.doRouteStatusChange(statusChangeEvent);
        KualiWorkflowDocument workflowDocument = getDocumentHeader().getWorkflowDocument();

        if (workflowDocument.stateIsProcessed()) {            
            setWarehouseCostChangeMap(
                    SpringContext.getBean(MassUpdateService.class).processMassUpdateDocument(this));
            this.setFinalIndicator(true);
        }
     // generate GL entries
        SpringContext.getBean(GeneralLedgerProcessor.class).doRouteStatusChange(this,
                getDocumentHeader());
    }
    
    /**
     * toStringMapper
     * 
     * @return LinkedHashMap
     */
    @Override
    public LinkedHashMap<String, String> toStringMapper() {
        LinkedHashMap<String, String> propMap = new LinkedHashMap<String, String>();
        propMap.put("documentNumber", documentNumber);
        return propMap;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#generateGlpeEntries()
     */
    public List<FinancialGeneralLedgerPendingEntry> generateGlpeEntries() {
        GeneralLedgerBuilderService generalLedgerBuilderService = SpringContext
        .getBean(GeneralLedgerBuilderService.class);
        HashMap<WarehouseGlGroup, WarehouseGlGroup> glGroups = new HashMap<WarehouseGlGroup, WarehouseGlGroup>();
        
        for (String warehouseCode : getWarehouseCostChangeMap().keySet()) {
            KualiDecimal changedCost = getWarehouseCostChangeMap().get(warehouseCode).kualiDecimalValue();
            
            if (changedCost.isNonZero()) {
                Warehouse warehouse = SpringContext.getBean(BusinessObjectService.class)
                    .findBySinglePrimaryKey(Warehouse.class, warehouseCode);
                // get the accounting type code from stock transaction reason code
                if (changedCost.isPositive()) {
                    generalLedgerBuilderService.incrementInventory(glGroups,
                            warehouse, "AVGCOST", changedCost.abs(),
                            "Storehouse Invtry UnitCost");
                }
                else {
                    generalLedgerBuilderService.decrementInventory(glGroups,
                            warehouse, "AVGCOST", changedCost.abs(),
                            "Storehouse Invtry UnitCost");
                }
            }
        }
        ArrayList<FinancialGeneralLedgerPendingEntry> entries = new ArrayList<FinancialGeneralLedgerPendingEntry>();
        for (WarehouseGlGroup group : glGroups.values()) {
            entries.add(group.getTargetEntry());
        }
        return entries;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getDocumentTypeCode()
     */
    public String getDocumentTypeCode() {
        return SpringContext.getBean(DataDictionaryService.class).getDocumentTypeNameByClass(
                getClass());
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#getFinancialGeneralLedgerPendingEntries()
     */
    public List<FinancialGeneralLedgerPendingEntry> getFinancialGeneralLedgerPendingEntries() {
        return this.financialGeneralLedgerPendingEntries;
    }

    /**
     * @see org.kuali.ext.mm.gl.GeneralLedgerPostable#setFinancialGeneralLedgerPendingEntries(java.util.List)
     */
    public void setFinancialGeneralLedgerPendingEntries(
            List<FinancialGeneralLedgerPendingEntry> entries) {
        this.financialGeneralLedgerPendingEntries = entries;

    }
    
    public List<FinancialGeneralLedgerPendingEntry> getApprovedGeneralLedgerPendingEntries() {
        return SpringContext.getBean(GeneralLedgerProcessor.class)
                .getApprovedGeneralLedgerPendingEntries(getDocumentNumber());
    }
	
}
