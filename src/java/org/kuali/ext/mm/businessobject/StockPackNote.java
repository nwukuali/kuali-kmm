package org.kuali.ext.mm.businessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MM_STOCK_PACK_NOTE_T")
public class StockPackNote extends MMPersistableBusinessObjectBase{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STOCK_PACK_NOTE_ID", unique = true, length = 2)
	private String stockPackNoteId;

	@Column(name = "PACK_LIST_ANNOUNCEMENT_CD", length = 8)
	private String packListAnnouncementCode;

	@Column(name = "STOCK_ID", length = 18)
	private String stockId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PACK_LIST_ANNOUNCEMENT_CD")
	private PackListAnnouncement packListAnnouncement;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_ID")
	private Stock stock;

	public String getStockPackNoteId() {
		return stockPackNoteId;
	}

	public void setStockPackNoteId(String stockPackNoteId) {
		this.stockPackNoteId = stockPackNoteId;
	}

	public String getPackListAnnouncementCode() {
		return packListAnnouncementCode;
	}

	public void setPackListAnnouncementCode(String packListAnnouncementCode) {
		this.packListAnnouncementCode = packListAnnouncementCode;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public PackListAnnouncement getPackListAnnouncement() {
		return packListAnnouncement;
	}

	public void setPackListAnnouncement(PackListAnnouncement packListAnnouncement) {
		this.packListAnnouncement = packListAnnouncement;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}


}
