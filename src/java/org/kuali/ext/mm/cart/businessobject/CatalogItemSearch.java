package org.kuali.ext.mm.cart.businessobject;

import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CatalogItemImage;
import org.kuali.ext.mm.businessobject.CatalogItemMarkup;
import org.kuali.ext.mm.businessobject.CatalogSubgroupItem;
import org.kuali.ext.mm.businessobject.MMPersistableBusinessObjectBase;
import org.kuali.ext.mm.businessobject.Stock;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.TypedArrayList;

/**
 * CatalogItem generated by hbm2java
 */
@Entity
@Table(name = "MM_CATALOG_ITEM_T")
public class CatalogItemSearch extends MMPersistableBusinessObjectBase  {
	/**
     *
     */
    private static final long serialVersionUID = 3639342286600907777L;
    private List<CatalogItemImage> catalogItemImages;
	private List<CatalogItemMarkup> catalogItemMarkups;
	private List<CatalogSubgroupItem> catalogSubgroupItems;

	private Stock stock;

	@Id
	@Column(name = "CATALOG_ITEM_ID", unique = true, nullable = false, length = 36)
	private String catalogItemId;

	@Column(name = "DISTRIBUTOR_NBR", nullable = false, length = 30)
	private String distributorNbr;

//	@Column(name= "SUBSTITUTE_DISTRIBUTOR_NBR", length = 30)
//	private String substituteDistributorNbr;

//	@Column(name = "MANUFACTURER_NBR", length = 30)
//	private String manufacturerNbr;

//	@Column(name = "CATALOG_UNIT_OF_ISSUE_CD", nullable = false, length = 10)
//	private String catalogUnitOfIssueCd;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "CATALOG_UNIT_OF_ISSUE_CD")
//	private UnitOfIssue salesUnitOfIssue;

	@Column(name = "CATALOG_PRC", nullable = false, scale = 4)
	private KualiDecimal catalogPrc;

	@Column(name = "CATALOG_DESC", nullable = false, length = 400)
	private String catalogDesc;

	@Column(name = "RECYCLED_IND", length = 1)
	private boolean recycledInd;


	@Column(name = "CATALOG_ID", precision = 18)
	private String catalogId;

	@Column(name = "STOCK_ID", precision = 18)
	private String stockId;

	@Column(name = "DISPLAYABLE_IND", precision = 1)
	private boolean displayableInd;

	@Column(name = "CATALOG_SUBGROUP_ID", unique = true, nullable = false, length = 36)
	private String catalogSubgroupId;

	@Column (name = "CATALOG_GROUP_CD")
	private String catalogGroupCd;

	@Column(name = "PRIOR_CATALOG_SUBGROUP_CD", length = 12)
	private String priorCatalogSubgroupId;

    private Integer priorityNbr;
    
    private Integer orderCount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATALOG_ID", nullable = false)
	private Catalog catalog;

	private CatalogItem catalogItem;


	@SuppressWarnings("unchecked")
	public CatalogItemSearch(){
		catalogItemImages 	= 	new TypedArrayList(CatalogItemImage.class);
		catalogItemMarkups 	= 	new TypedArrayList(CatalogItemMarkup.class);
		catalogSubgroupItems= 	new TypedArrayList(CatalogSubgroupItem.class);
	}

	public CatalogItem getCatalogItem() {
		return catalogItem;
	}

	public void setCatalogItem(CatalogItem catalogItem) {
		this.catalogItem = catalogItem;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}


	public String getCatalogItemId() {
		return catalogItemId;
	}

	public void setCatalogItemId(String catalogItemId) {
		this.catalogItemId = catalogItemId;
	}

	public String getDistributorNbr() {
		return distributorNbr;
	}

	public void setDistributorNbr(String distributorNbr) {
		this.distributorNbr = distributorNbr;
	}


	public KualiDecimal getCatalogPrc() {
		return catalogPrc;
	}

	public void setCatalogPrc(KualiDecimal catalogPrc) {
		this.catalogPrc = catalogPrc;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public boolean isRecycledInd() {
		return recycledInd;
	}

	public void setRecycledInd(boolean recycledInd) {
		this.recycledInd = recycledInd;
	}


	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public boolean isDisplayableInd() {
		return displayableInd;
	}

	public void setDisplayableInd(boolean displayableInd) {
		this.displayableInd = displayableInd;
	}

	public String getCatalogSubgroupId() {
		return catalogSubgroupId;
	}

	public void setCatalogSubgroupId(String catalogSubgroupId) {
		this.catalogSubgroupId = catalogSubgroupId;
	}

	public String getCatalogGroupCd() {
		return catalogGroupCd;
	}

	public void setCatalogGroupCd(String catalogGroupCd) {
		this.catalogGroupCd = catalogGroupCd;
	}

	public String getPriorCatalogSubgroupId() {
        return this.priorCatalogSubgroupId;
    }

    public void setPriorCatalogSubgroupId(String priorCatalogSubgroupId) {
        this.priorCatalogSubgroupId = priorCatalogSubgroupId;
    }

    public List<CatalogItemImage> getCatalogItemImages() {
		return catalogItemImages;
	}

	public void setCatalogItemImages(List<CatalogItemImage> catalogItemImages) {
		this.catalogItemImages = catalogItemImages;
	}

	public List<CatalogItemMarkup> getCatalogItemMarkups() {
		return catalogItemMarkups;
	}

	public void setCatalogItemMarkups(List<CatalogItemMarkup> catalogItemMarkups) {
		this.catalogItemMarkups = catalogItemMarkups;
	}

	public List<CatalogSubgroupItem> getCatalogSubgroupItems() {
		return catalogSubgroupItems;
	}

	public void setCatalogSubgroupItems(
			List<CatalogSubgroupItem> catalogSubgroupItems) {
		this.catalogSubgroupItems = catalogSubgroupItems;
	}

	
    public Integer getPriorityNbr() {
        return this.priorityNbr;
    }

    public void setPriorityNbr(Integer priorityNbr) {
        this.priorityNbr = priorityNbr;
    }

	public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public String getShortDescription() {
		return (getCatalogDesc().length() <= 30) ? getCatalogDesc() : getCatalogDesc().substring(0, 29);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List buildListOfDeletionAwareLists() {
		List deletableCollection = super.buildListOfDeletionAwareLists();
		deletableCollection.add(catalogItemImages);
		deletableCollection.add(catalogItemMarkups);
		deletableCollection.add(catalogSubgroupItems);
		return deletableCollection;
	}
	/**
	 * toStringMapper
	 * @return LinkedHashMap
	 */
	@Override
    public LinkedHashMap toStringMapper() {
		LinkedHashMap propMap = new LinkedHashMap();
		return propMap;
	}
}
