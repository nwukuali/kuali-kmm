package org.kuali.ext.mm.businessobject;

// Generated Apr 16, 2009 10:01:00 AM by Hibernate Tools 3.2.4.GA

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CustomerFavDetail generated by hbm2java
 */
@Entity
@Table(name = "MM_CUSTOMER_FAV_DETAIL_T")
public class CustomerFavDetail extends MMPersistableBusinessObjectBase
		implements java.io.Serializable {

	private static final long serialVersionUID = 4819951315743468873L;

	@Id
	@Column(name = "CUSTOMER_FAV_DETAIL_ID", nullable = false, length = 12)
	private String customerFavDetailId;

	@Column(name = "CUSTOMER_FAV_ID", nullable = false, length = 12)
	private String customerFavId;

	@Column(name = "CATALOG_ITEM_ID", nullable = false, length = 12)
	private String catalogItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_FAV_ID", nullable = false)
	private CustomerFavHeader customerFavHeader;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATALOG_ITEM_ID", nullable = false)
	private CatalogItem catalogItem;

	public void setCustomerFavDetailId(String customerFavDetailId) {
		this.customerFavDetailId = customerFavDetailId;
	}

	public String getCustomerFavDetailId() {
		return customerFavDetailId;
	}

	public CustomerFavDetail() {
	}

	public String getCustomerFavId() {
		return this.customerFavId;
	}

	public void setCustomerFavId(String customerFavId) {
		this.customerFavId = customerFavId;
	}


	public CustomerFavHeader getCustomerFavHeader() {
		return this.customerFavHeader;
	}

	public void setCustomerFavHeader(CustomerFavHeader customerFavHeader) {
		this.customerFavHeader = customerFavHeader;
	}

	public void setCatalogItemId(String catalogItemId) {
		this.catalogItemId = catalogItemId;
	}

	public String getCatalogItemId() {
		return catalogItemId;
	}

	public CatalogItem getCatalogItem() {
		return this.catalogItem;
	}

	public void setCatalogItem(CatalogItem catalogItem) {
		this.catalogItem = catalogItem;
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
