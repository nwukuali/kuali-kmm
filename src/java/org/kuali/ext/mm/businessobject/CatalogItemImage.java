package org.kuali.ext.mm.businessobject;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "MM_CATALOG_ITEM_IMAGE_T")
public class CatalogItemImage  extends MMPersistableBusinessObjectBase implements
java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CATALOG_ITEM_IMAGE_ID", unique=true, nullable = false, precision = 18, scale = 0)
	private Integer catalogItemImageId;

	@Column(name = "CATALOG_ITEM_ID", unique = true, nullable = false, length = 18)
	private String catalogItemId;

	@Column(name = "CATALOG_IMAGE_ID", unique=true, nullable = false, precision = 18, scale = 0)
	private Integer catalogImageId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATALOG_ITEM_ID", nullable = false)
	private CatalogItem catalogItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATALOG_IMAGE_ID", nullable = false)
	private CatalogImage catalogImage;


	public Integer getCatalogItemImageId() {
		return catalogItemImageId;
	}


	public void setCatalogItemImageId(Integer catalogItemImageId) {
		this.catalogItemImageId = catalogItemImageId;
	}


	public String getCatalogItemId() {
		return catalogItemId;
	}


	public void setCatalogItemId(String catalogItemId) {
		this.catalogItemId = catalogItemId;
	}


	public Integer getCatalogImageId() {
		return catalogImageId;
	}


	public void setCatalogImageId(Integer catalogImageId) {
		this.catalogImageId = catalogImageId;
	}


	public CatalogItem getCatalogItem() {
		return catalogItem;
	}


	public void setCatalogItem(CatalogItem catalogItem) {
		this.catalogItem = catalogItem;
	}


	public CatalogImage getCatalogImage() {
		return catalogImage;
	}


	public void setCatalogImage(CatalogImage catalogImage) {
		this.catalogImage = catalogImage;
	}


	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}
}