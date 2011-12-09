package org.kuali.ext.mm.businessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "MM_CATALOG_ITEM_MARKUP_T")
public class CatalogItemMarkup extends MMPersistableBusinessObjectBase implements
java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CATALOG_ITEM_MARKUP_ID", unique = true, nullable = false, length = 18)
	private String 	catalogItemMarkupId;

	@Column(name = "CATALOG_ITEM_ID", nullable = false, length = 18)
	private String 	catalogItemId;

	@Column(name = "MARKUP_CD", nullable = false, length = 12)
	private String 	markupCd;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MARKUP_CD", nullable = false)
	private Markup markup;

	private CatalogItem catalogItem;

	public String getCatalogItemMarkupId() {
		return catalogItemMarkupId;
	}

	public void setCatalogItemMarkupId(String catalogItemMarkupId) {
		this.catalogItemMarkupId = catalogItemMarkupId;
	}

	public String getCatalogItemId() {
		return catalogItemId;
	}

	public void setCatalogItemId(String catalogItemId) {
		this.catalogItemId = catalogItemId;
	}

	public String getMarkupCd() {
		return markupCd;
	}

	public void setMarkupCd(String markupCd) {
		this.markupCd = markupCd;
	}

	public Markup getMarkup() {
		return markup;
	}

	public void setMarkup(Markup markup) {
		this.markup = markup;
	}

	public CatalogItem getCatalogItem() {
		return catalogItem;
	}

	public void setCatalogItem(CatalogItem catalogItem) {
		this.catalogItem = catalogItem;
	}

}
