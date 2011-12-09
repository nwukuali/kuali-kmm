package org.kuali.ext.mm.document.web.struts;


import org.apache.struts.action.ActionForm;

public class CountWorksheetDownloadForm extends ActionForm{

	/**
     *
     */
    private static final long serialVersionUID = -7917549507703281974L;
    private String filePath;
	private String warehouseCode;
	private String warehouseName;
	private String worksheetDocNumber;
	private String numOfCounters;

	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getWorksheetDocNumber() {
		return worksheetDocNumber;
	}
	public void setWorksheetDocNumber(String worksheetDocNumber) {
		this.worksheetDocNumber = worksheetDocNumber;
	}
	public String getNumOfCounters() {
		return numOfCounters;
	}
	public void setNumOfCounters(String numOfCounters) {
		this.numOfCounters = numOfCounters;
	}
}
