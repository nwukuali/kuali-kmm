package org.kuali.ext.mm.businessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MM_CATALOG_SUBGROUP_ITEM_T")
public class CatalogSubgroupItem extends MMPersistableBusinessObjectBase {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CATALOG_SUBGROUP_ITEM_ID", unique = true, nullable = false, length = 36)
	private String catalogSubgroupItemId;

	@Column(name = "CATALOG_SUBGROUP_ID", unique = true, nullable = false, length = 36)
	private String catalogSubgroupId;

	@Column (name = "CATALOG_ITEM_ID")
	private String catalogItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATALOG_SUBGROUP_ID", nullable = false)
	private CatalogSubgroup catalogSubgroup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATALOG_ITEM_ID", nullable = false)
	private CatalogItem catalogItem;


	public CatalogSubgroupItem(){
	}

	public String getCatalogSubgroupItemId() {
		return catalogSubgroupItemId;
	}

	public void setCatalogSubgroupItemId(String catalogSubgroupItemId) {
		this.catalogSubgroupItemId = catalogSubgroupItemId;
	}

	public String getCatalogSubgroupId() {
		return catalogSubgroupId;
	}

	public void setCatalogSubgroupId(String catalogSubgroupId) {
		this.catalogSubgroupId = catalogSubgroupId;
	}

	public String getCatalogItemId() {
		return catalogItemId;
	}

	public void setCatalogItemId(String catalogItemId) {
		this.catalogItemId = catalogItemId;
	}

	public CatalogSubgroup getCatalogSubgroup() {
		return catalogSubgroup;
	}

	public void setCatalogSubgroup(CatalogSubgroup catalogSubgroup) {
		this.catalogSubgroup = catalogSubgroup;
	}

	public CatalogItem getCatalogItem() {
		return catalogItem;
	}

	public void setCatalogItem(CatalogItem catalogItem) {
		this.catalogItem = catalogItem;
	}


}
