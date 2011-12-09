package org.kuali.ext.mm.businessobject;

// Generated Jun 11, 2009 4:51:23 PM by Hibernate Tools 3.2.4.GA

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.ext.mm.document.PickListDocument;

/**
 * PickTicket generated by hbm2java
 */
@Entity
@Table(name = "MM_PICK_TICKET_T")
public class PickTicket extends StoresPersistableBusinessObject {

	/**
     *
     */
    private static final long serialVersionUID = -1988155828505940301L;

    @Id
	@Column(name = "PICK_TICKET_NBR", unique = true, nullable = false, length = 36)
	private String pickTicketNumber;

	@Column(name = "NM")
	private String pickTicketName;

	@Column(name = "FILE_NM")
	private String fileName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PICK_LIST_DOC_NBR", nullable = false)
	private PickListDocument pickListDocument;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PICK_STATUS_CD", nullable = false)
	private PickStatusCode pickStatusCode;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pickTicket")
	private List<PickListLine> pickListLines = new ArrayList<PickListLine>();

	//BEGIN OJB
	@Column(name = "PICK_LIST_DOC_NBR")
	private String pickListDocumentNumber;
	@Column(name = "PICK_STATUS_CD")
	private String pickStatusCodeCd;
	//END OJB

	private Integer lineCount;
	
	private Integer orderCount;
	
	private Timestamp oldestDate;
	

	public PickTicket() {
	}

	public String getPickTicketNumber() {
		return this.pickTicketNumber;
	}

	public void setPickTicketNumber(String pickTicketNumber) {
		this.pickTicketNumber = pickTicketNumber;
	}

	public PickListDocument getPickListDocument() {
		return this.pickListDocument;
	}

	public void setPickListDocument(PickListDocument pickListDocument) {
		this.pickListDocument = pickListDocument;
	}

	public PickStatusCode getPickStatusCode() {
		return this.pickStatusCode;
	}

	public void setPickStatusCode(PickStatusCode pickStatusCode) {
		this.pickStatusCode = pickStatusCode;
	}

	public List<PickListLine> getPickListLines() {
		return this.pickListLines;
	}

	public void setPickListLines(List<PickListLine> pickListLines) {
		this.pickListLines = pickListLines;
	}

	//BEGIN OJB
	public String getPickListDocumentNumber() {
		return pickListDocumentNumber;
	}

	public void setPickListDocumentNumber(String pickListDocumentNumber) {
		this.pickListDocumentNumber = pickListDocumentNumber;
	}

	public String getPickStatusCodeCd() {
		return pickStatusCodeCd;
	}

	public void setPickStatusCodeCd(String pickStatusCodeCd) {
		this.pickStatusCodeCd = pickStatusCodeCd;
	}
	//END OJB

	public void setPickTicketName(String pickTicketName) {
		this.pickTicketName = pickTicketName;
	}

	public String getPickTicketName() {
		return pickTicketName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public Integer getLineCount() {
        return this.lineCount;
    }

    public void setLineCount(Integer lineCount) {
        this.lineCount = lineCount;
    }

    public Integer getOrderCount() {
        return this.orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Timestamp getOldestDate() {
        return this.oldestDate;
    }

    public void setOldestDate(Timestamp oldestDate) {
        this.oldestDate = oldestDate;
    }

    /**
	 * toStringMapper
	 * @return LinkedHashMap
	 */
	@Override
    public LinkedHashMap toStringMapper() {
		LinkedHashMap propMap = new LinkedHashMap();
		//TODO:  Autogenerated method

		return propMap;
	}

}
