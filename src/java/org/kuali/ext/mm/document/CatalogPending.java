package org.kuali.ext.mm.document;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.apache.struts.upload.FormFile;
import org.kuali.ext.mm.businessobject.StoresTransactionalDocumentBase;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.service.CatalogPendingDocQueryService;
import org.kuali.rice.kew.dto.DocumentRouteStatusChangeDTO;
import org.kuali.rice.kns.workflow.service.KualiWorkflowDocument;


@SuppressWarnings("serial")
@Entity
@Table(name = "MM_CATALOG_PENDING_T")
public class CatalogPending extends StoresTransactionalDocumentBase {

    public CatalogPending() {
        super();
    }

	@Column(name = "CATALOG_CD", nullable = false, length = 2)
	private String catalogCd;

	@Column(name = "CATALOG_DESC", length = 200)
	private String catalogDesc;

	@Temporal(TemporalType.DATE)
	@Column(name = "CATALOG_BEGIN_DT", length = 7)
	private Date catalogBeginDt;

	@Temporal(TemporalType.DATE)
	@Column(name = "CATALOG_END_DT", length = 7)
	private Date catalogEndDt;

	@Column(name = "PRIORITY_NBR", precision = 8, scale = 0)
	private Integer priorityNbr;

	@Column(name = "AGREEMENT_NBR", length=15)
	private String agreementNbr;

	@Column(name = "CATALOG_TIMESTAMP", length=7)
	private Timestamp catalogTimestamp;

	@Column (name= "CATALOG_FILE" )
	private transient String catalogFile;

	@Column (name="BATCH_LOG")
	private String batchLog;

	@Column (name = "CATALOG_UPLOAD_STATUS")
	private String catalogUploadStatus;

	/**
     * Gets the catalogUploadStatus property
     * @return Returns the catalogUploadStatus
     */
    public String getCatalogUploadStatus() {
        return this.catalogUploadStatus;
    }

    /**
     * Sets the catalogUploadStatus property value
     * @param catalogUploadStatus The catalogUploadStatus to set
     */
    public void setCatalogUploadStatus(String catalogUploadStatus) {
        this.catalogUploadStatus = catalogUploadStatus;
    }

    public Timestamp getCatalogTimestamp() {
		return catalogTimestamp;
	}

	public void setCatalogTimestamp(Timestamp catalogTimestamp) {
		this.catalogTimestamp = catalogTimestamp;
	}

	public String getCatalogFile() {
        return this.catalogFile;
    }

    public void setCatalogFile(String catalogFile) {
        this.catalogFile = catalogFile;
    }



	/**
     * Gets the batchLog property
     * @return Returns the batchLog
     */
    public String getBatchLog() {
        return this.batchLog;
    }

    /**
     * Sets the batchLog property value
     * @param batchLog The batchLog to set
     */
    public void setBatchLog(String batchLog) {
        this.batchLog = batchLog;
    }

    public String getCatalogCd() {
		return catalogCd;
	}


	public void setCatalogCd(String catalogCd) {
		this.catalogCd = catalogCd;
	}



	public String getCatalogDesc() {
		return catalogDesc;
	}



	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}



	public Date getCatalogBeginDt() {
		return catalogBeginDt;
	}



	public void setCatalogBeginDt(Date catalogBeginDt) {
		this.catalogBeginDt = catalogBeginDt;
	}



	public Date getCatalogEndDt() {
		return catalogEndDt;
	}



	public void setCatalogEndDt(Date catalogEndDt) {
		this.catalogEndDt = catalogEndDt;
	}



	public Integer getPriorityNbr() {
		return priorityNbr;
	}



	public void setPriorityNbr(Integer priorityNbr) {
		this.priorityNbr = priorityNbr;
	}



	public String getAgreementNbr() {
		return agreementNbr;
	}



	public void setAgreementNbr(String agreementNbr) {
		this.agreementNbr = agreementNbr;
	}

	public FormFile fileContent;

    public FormFile getFileContent() {
        return this.fileContent;
    }

    public void setFileContent(FormFile fileContent) {
        this.fileContent = fileContent;
    }


    private String fileName;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * toStringMapper
     * @return LinkedHashMap
     */
    @Override
    @SuppressWarnings("unchecked")
    public LinkedHashMap toStringMapper() {
        LinkedHashMap propMap = new LinkedHashMap();
        propMap.put("documentNumber", documentNumber);
        return propMap;
    }

    @Override
    public void doRouteStatusChange(DocumentRouteStatusChangeDTO statusChangeEvent) {
        super.doRouteStatusChange(statusChangeEvent);
        KualiWorkflowDocument workflowDocument = getDocumentHeader().getWorkflowDocument();
        if (workflowDocument.stateIsProcessed()) {
            this.setCatalogUploadStatus("UPLOAD_READY");
        }
    }

    /**
     * @see org.kuali.rice.kns.bo.PersistableBusinessObjectBase#afterInsert(org.apache.ojb.broker.PersistenceBroker)
     */
    @Override
    public void afterInsert(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {

        super.afterInsert(persistenceBroker);

        CatalogPendingDocQueryService cPDQS = SpringContext
                .getBean(CatalogPendingDocQueryService.class);

        FormFile uploadedFile = this.getFileContent();
        if (uploadedFile != null) {
            cPDQS.uploadCatalogActionSave(this.getDocumentNumber(), this.getCatalogCd(), this
                    .getCatalogDesc(), uploadedFile);
        }
    }
}
